package nc.bs.pub.action;

import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.pub.compiler.AbstractCompiler2;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.qualitypro.QualityProB2VO;
import nc.vo.xcgl.qualitypro.QualityProHVO;
/**
 * )
 * @author mlr
 */
public class N_XC60_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	public N_XC60_UNAPPROVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
		  // ####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
			setParameter("currentVo", vo.m_preValueVo);
			checkNexctExist(vo.m_preValueVo);
			procUnApproveFlow(vo);
			Object retObj = runClass("nc.bs.xcgl.pub.HYBillUnApprove",
					"unApproveHYBill", "nc.vo.pub.AggregatedValueObject:01",
					vo, m_keyHas, m_methodReturnHas);
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
	private void checkNexctExist(AggregatedValueObject bill) throws BusinessException {
		if(bill==null ||bill.getParentVO()==null){
			return;
		}
		
		String pk=bill.getParentVO().getPrimaryKey();
		
		BaseDAO  dao=new BaseDAO();
		String wsql="  isnull(dr,0)=0 and  pk_qualitygrade_h ='"+pk+"'";
		List list=(List) dao.retrieveByClause(QualityProB2VO.class, wsql);
		
		if(list==null || list.size()==0){
			return;
		}
		
		QualityProB2VO bvo= (QualityProB2VO) list.get(0);
		
		List list1= (List) dao.retrieveByClause(QualityProHVO.class, " pk_qualitypro_h ='"+bvo.getPk_qualitypro_h()+"' and isnull(dr,0)=0 ");
		
		
		if(list1==null || list1.size()==0){
			return;
		}
		
		QualityProHVO hvo=(QualityProHVO) list1.get(0);
		
		throw new BusinessException("�������ż۷�������:"+hvo.getVqualityprocode());
		
		
	}
	/*
	 * ��ע��ƽ̨��дԭʼ�ű�
	 */
	public String getCodeRemark() {
		return "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n procUnApproveFlow (vo); \nObject retObj=runClassCom@ \"nc.bs.pp.pub.comstatus.HYBillUnApprove\", \"unApproveHYBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\nreturn retObj;\n";
	}

	/*
	 * ��ע�����ýű�������HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}
}
