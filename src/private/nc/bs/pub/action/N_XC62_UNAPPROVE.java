package nc.bs.pub.action;

import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.pub.compiler.AbstractCompiler2;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.qualitypro.QualityPorB1VO;
import nc.vo.xcgl.qualitypro.QualityProHVO;
import nc.vo.xcgl.soct.SoctHVO;
/**
 * 优质优价方案
 * @author mlr
 */
public class N_XC62_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	public N_XC62_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
		  // ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
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
		String wsql="  isnull(dr,0)=0 and vreserve1 ='"+pk+"'";
		List list=(List) dao.retrieveByClause(SoctHVO.class, wsql);
		
		if(list==null || list.size()==0){
			return;
		}
		

		
		SoctHVO hvo=(SoctHVO) list.get(0);
		
		throw new BusinessException("被销售合同引用:"+hvo.getCt_code());
		
		
	}
	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n procUnApproveFlow (vo); \nObject retObj=runClassCom@ \"nc.bs.pp.pub.comstatus.HYBillUnApprove\", \"unApproveHYBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\nreturn retObj;\n";
	}

	/*
	 * 备注：设置脚本变量的HAS
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
