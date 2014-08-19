package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.autoicbill.AutoIcBillBO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.constant.ScmConst;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * 重选入库单
 * @author Jay
 */
public class N_XC78_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;

	public N_XC78_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			AutoIcBillBO icbo=new AutoIcBillBO();
			icbo.dealOnUnApprove(getVo(), 
		    		vo.m_currentDate, 
		    		vo.m_operator,
		    		true,
		    		ScmConst.m_otherIn,
		    		false,
		    		ScmConst.m_otherOut,
		    		true,
		    		false);
		  // ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			setParameter("currentVo", vo.m_preValueVo);
			//判断是否存在下游，若存在不允许弃审
			ZmPubTool.checkExitNextBill(PubBillTypeConst.billtype_gravity,vo.m_preValueVo.getParentVO().getPrimaryKey());
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
