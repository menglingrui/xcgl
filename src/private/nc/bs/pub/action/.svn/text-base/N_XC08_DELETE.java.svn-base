package nc.bs.pub.action;
import java.util.Hashtable;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.zmpub.pub.tool.WriteBackTool;
/**
 * 
 * @author ddk
 */
public class N_XC08_DELETE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;


	public N_XC08_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
				// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			writeBack(vo.m_preValueVo);
			// 方法说明:行业公共删除
			retObj = runClass("nc.bs.trade.comdelete.BillDelete", "deleteBill",
					"nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,
					m_methodReturnHas);
			// ##################################################
			
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}
	private void writeBack(AggregatedValueObject bill) throws Exception {
		   if(bill==null || bill.getChildrenVO()==null || bill.getChildrenVO().length==0)
			   return;	 
		   SuperVO[] vos=(SuperVO[]) bill.getChildrenVO();
		   for(int i=0;i<vos.length;i++){
			   vos[i].setStatus(VOStatus.DELETED);
		   }
		   WriteBackTool.setVsourcebillid("vlastbillid");
		   WriteBackTool.setVsourcebillrowid("vlastbillrowid");
		   WriteBackTool.setVsourcebilltype("vlastbilltype");
		   WriteBackTool.writeBack(vos, "to_bill_b", "cbill_bid",
				   new String[]{"ngrossweight"}, new String[]{"vbdef20"},new String[]{"nnum"});	
		}
	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\nObject retObj  =null;\n//方法说明:行业公共删除\nretObj  =runClassCom@ \"nc.bs.trade.comdelete.BillDelete\", \"deleteBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\n//##################################################\nreturn retObj;\n";
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
