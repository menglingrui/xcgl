package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.dao.BaseDAO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.pub.compiler.IWorkFlowRet;
import nc.bs.xcgl.saleshareout.SaleShareoutBO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 
 * @author ddk
 */
public class N_XC29_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;
	private BaseDAO dao=null;
	public BaseDAO getDao() {
	if (dao == null) {
		dao = new BaseDAO();
	}
	return dao;
	}
	public N_XC29_APPROVE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			
			Object m_sysflowObj = procActionFlow(vo);
			if (m_sysflowObj != null) {
				nc.bs.pub.compiler.IWorkFlowRet work=(IWorkFlowRet) m_sysflowObj;
				AggregatedValueObject billvo=  (AggregatedValueObject) work.m_inVo;
				SuperVO hvo=(SuperVO) billvo.getParentVO();
				//�������ص��Ƶ��� ����״̬����
				if(PuPubVO.getInteger_NullAs(hvo.getAttributeValue("vbillstatus"), -2)==-1){
					hvo.setAttributeValue("vbillstatus", IBillStatus.FREE);
					getDao().updateVO(hvo);
					return m_sysflowObj;
				}
				return m_sysflowObj;
			}
			// ####�����Ϊ��������������������...���ܽ����޸�####
			Object retObj = null;
			setParameter("currentVo", vo.m_preValueVo);
			//����ʱ�������۳��ⵥ
			SaleShareoutBO bo=new SaleShareoutBO();
			bo.sendMessage(vo.m_preValueVo,vo);
			retObj = runClass("nc.bs.xcgl.pub.HYBillApprove", "approveHYBill",
					"nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,
					m_methodReturnHas);
			
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/*
	 * ��ע��ƽ̨��дԭʼ�ű�
	 */
	public String getCodeRemark() {
		return "	//####�����Ϊ������������������ʼ...���ܽ����޸�####\nprocActionFlow@@;\n//####�����Ϊ��������������������...���ܽ����޸�####\nObject  retObj  =null;\n setParameter(\"currentVo\",vo.m_preValueVo);           \nretObj =runClassCom@ \"nc.bs.pp.pp0201.ApproveAction\", \"approveHYBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\n            ArrayList ls = (ArrayList)getUserObj();\n       \n        setParameter(\"userOpt\",ls.get(1));               \n            runClassCom@ \"nc.bs.pp.pp0201.ApproveAction\", \"afterApprove\", \"&userOpt:java.lang.Integer,nc.vo.pub.AggregatedValueObject:01\"@;               \nreturn retObj;\n";
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