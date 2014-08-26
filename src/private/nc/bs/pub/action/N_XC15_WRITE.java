package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.stock.BillStockBO;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
/**
 * 原矿加工出库单
 * @author jay
 * 
 */
public class N_XC15_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC15_WRITE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass (PfParameterVO vo)  throws BusinessException {
try{
	super.m_tmpVo=vo;
	try {
			super.m_tmpVo = vo;
			Object retObj = null;
			//同步现存量
			BillStockBO bo =new BillStockTool();
			if(vo.m_preValueVo!=null){
				bo.updateStockByBillForSave((AggregatedValueObject)ObjectUtils.serializableClone(vo.m_preValueVo), PubBillTypeConst.billtype_Generalout);
			}	
			SuperVO headvo=(SuperVO)vo.m_preValueVo.getParentVO();
			BsUniqueCheck.FieldUniqueCheck(headvo, 
					new String[]{"pk_billtype","dbilldate","pk_factory","pk_beltline","pk_minarea","pk_classorder","vreserve1"},
					"单据类型，单据日期，选厂，生产线，部门，矿石类型，班次组合重复!");
			retObj = runClass("nc.bs.xcgl.pub.HYBillSave", "saveBill","nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,	m_methodReturnHas);
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
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
public String getCodeRemark(){
	return null;
}
//	return "	try {\n			super.m_tmpVo = vo;\n			Object retObj = null;\n			// 保存即提交\n			retObj = runClass(\"nc.bs.gt.pub.HYBillSave\", \"saveBill\",\"nc.vo.pub.AggregatedValueObject:01\", vo, m_keyHas,	m_methodReturnHas);\n			return retObj;\n		} catch (Exception ex) {\n			if (ex instanceof BusinessException)\n				throw (BusinessException) ex;\n			else\n				throw new PFBusinessException(ex.getMessage(), ex);\n		}\n";}
/*
* 备注：设置脚本变量的HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}
