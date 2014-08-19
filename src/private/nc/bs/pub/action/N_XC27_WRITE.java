package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.dao.ZmBaseDAO;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.bs.zmpub.pub.tool.stock.BillStockBO;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
import nc.vo.xcgl.stocklock.StocklockBVO;
import nc.vo.xcgl.stocklock.StocklockHVO;
/**
 * 成本核算单(会计平台)
 * @author mlr
 */
public class N_XC27_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
private static ZmBaseDAO dao=null;
public static ZmBaseDAO getDAO(){
	if(dao==null){
		dao=new ZmPubDAO();
	}
	return dao;
}
public N_XC27_WRITE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	try {
			super.m_tmpVo = vo;
			
			BillStockBO bo =new BillStockTool();
			if(vo.m_preValueVo!=null){
				bo.updateStockByBillForSave((AggregatedValueObject)ObjectUtils.serializableClone(vo.m_preValueVo), PubBillTypeConst.billtype_stocklock);
			}
			
			
//			StocklockHVO hvo=(StocklockHVO )vo.m_preValueVo.getParentVO();
//			String pk_stordoc=hvo.getPk_stordoc();
//			String pk_factory=hvo.getPk_factory();
//			StocklockBVO []bvos=(StocklockBVO [])vo.m_preValueVo.getChildrenVO();
//			for(int i=0;i<bvos.length;i++){
//				if(PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getNlock())==null){
//				    throw new BusinessException("锁定量为空");	
//				}	
//				String pk_invmandoc=bvos[i].getPk_invmandoc();
//				String sql=" isnull(dr,0)=0 and pk_stordoc='"+pk_stordoc+"'"+
//				           " and pk_factory='"+pk_factory+"' and pk_invmandoc= '"+pk_invmandoc+"'";
//				BillStockBO bo=new BillStockTool();
//				PubStockOnHandVO []vos=(PubStockOnHandVO [])bo.queryStock(sql);
//				if(vos!=null){
//				bo.updateStockByBill(vos, PubBillTypeConst.billtype_stocklock);
//				}
//				
//			}
			
			
			
			
			Object retObj = null;
//			if(vo.m_preValueVo.getParentVO()!=null){
//				StocklockHVO cvo=(StocklockHVO)vo.m_preValueVo.getParentVO();
//				BsUniqueCheck.FieldUniqueChecks(cvo, new String[]{"pk_corp","pk_factory"},null,"信息重复");
//			}
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
