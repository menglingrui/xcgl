package nc.bs.pub.action;
import java.util.Hashtable;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.tool.stock.BillStockBO;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.zmpub.pub.tool.WriteBackTool;
/**
 * 
 * @author lxh
 */
public class N_XC69_DELETE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;


	public N_XC69_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			
			BillStockBO bo =new BillStockTool();
			if(vo.m_preValueVo!=null){
				bo.updateStockByBillForDelete((AggregatedValueObject)ObjectUtils.serializableClone(vo.m_preValueVo), PubBillTypeConst.billtype_genotherin);
			}
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
		   WriteBackTool.writeBack(vos, "xcgl_general_b", "pk_general_b",
				   new String[]{"nwetweight"}, new String[]{"noutnum"},new String[]{"nwetweight"});	
		}
}
