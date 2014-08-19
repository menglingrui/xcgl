package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.dao.ZmBaseDAO;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.bs.zmpub.pub.tool.stock.BillStockBO;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.genotherin.GenotherinBVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.zmpub.pub.tool.WriteBackTool;
/**
 * 成本核算单(会计平台)
 * @author lxh
 */
public class N_XC69_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
private static ZmBaseDAO dao=null;
public static ZmBaseDAO getDAO(){
	if(dao==null){
		dao=new ZmPubDAO();
	}
	return dao;
}
public N_XC69_WRITE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
//	try {
//			super.m_tmpVo = vo;
			
		
			BillStockBO bo =new BillStockTool();
			if(vo.m_preValueVo!=null){
				bo.updateStockByBillForSave((AggregatedValueObject)ObjectUtils.serializableClone(vo.m_preValueVo), PubBillTypeConst.billtype_genotherin);
			}
			
			
//			CircularlyAccessibleValueObject hvo=vo.m_preValueVo.getParentVO();
//			CircularlyAccessibleValueObject[] bvos=vo.m_preValueVo.getChildrenVO();
//			
////			String minarea=(String)hvo.getAttributeValue("pk_minarea");
//			String factory=(String)hvo.getAttributeValue("pk_factory");
//			String stordoc=(String)hvo.getAttributeValue("pk_stordoc");
//			
//			for(int i=0;i<bvos.length;i++){
//				GenotherinBVO bvo=(GenotherinBVO)bvos[i];
//				String vlastbillid=(String) bvo.getAttributeValue("vlastbillid");
//				
//				if (vlastbillid!=null){
//						UFDouble nnum=(UFDouble) bvo.getAttributeValue("nwetweight");
//						Double nwetweight1=nnum.doubleValue();
//						String pk_invmandoc=bvo.getPk_invmandoc();
//						String sql="select b.pk_general_b from xcgl_general_b b join  xcgl_general_h h on "+
//							" b.pk_general_h=h.pk_general_h where h.pk_billtype = 'XC68' " +
//									" and  h.pk_factory ='"+factory+"' and h.pk_stordoc ='"+stordoc+"'"+
//									" and b.pk_invmandoc='"+pk_invmandoc+"' and isnull(h.dr,0)=0  and isnull(b.dr,0)=0";
//						Object[] o;
//						try {
//							o = (Object [])QueryHelper.getInstrance().executeQuery(sql,new ArrayProcessor());
//							if(o==null)continue;
//							String pk=PuPubVO.getString_TrimZeroLenAsNull(o[0]);
//							String sql1="select nwetweight from xcgl_general_b  where pk_general_b = '"+pk+"' and isnull(dr,0)=0";
//							Object []o1 = (Object [])QueryHelper.getInstrance().executeQuery(sql1,new ArrayProcessor());
//							if(o1!=null){
//							double nwetweight=PuPubVO.getUFDouble_NullAsZero(o1[0]).doubleValue();
//							String sql2="update xcgl_general_b set nwetweight = '"+(nwetweight-nwetweight1)+"' where pk_general_b = '"+pk+"'";
//							ZmPubDAO.getDAO().executeUpdate(sql2);
//							}
//							String sql3="select noutnum  from xcgl_general_b where pk_general_b='"+pk+"' and isnull(dr,0)=0";
//							Object []o3 = (Object [])QueryHelper.getInstrance().executeQuery(sql3,new ArrayProcessor());
//							if(o3!=null){
//							double noutnum=PuPubVO.getUFDouble_NullAsZero(o3[0]).doubleValue();
//							
//							String sql4="update xcgl_general_b set noutnum ='"+(noutnum+nwetweight1)+"' where pk_general_b = '"+pk+"'";
//							ZmPubDAO.getDAO().executeUpdate(sql4);
//							}
//							
//						} catch (BusinessException e1) {
//							e1.printStackTrace();
//						}
//				
//				
//					}
//				}
			
			Object retObj = null;
			writeBack(vo.m_preValueVo);
			retObj = runClass("nc.bs.xcgl.pub.HYBillSave", "saveBill","nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,	m_methodReturnHas);
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	} 
//catch (Exception ex) {
//	if (ex instanceof BusinessException)
//		throw (BusinessException) ex;
//	else 
//    throw new PFBusinessException(ex.getMessage(), ex);
//}
//}
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

	private void writeBack(AggregatedValueObject bill) throws Exception {
	   if(bill==null || bill.getChildrenVO()==null || bill.getChildrenVO().length==0)
		   return;
	   SuperVO[] vos=(SuperVO[]) bill.getChildrenVO();
	   WriteBackTool.setVsourcebillid("vlastbillid");
	   WriteBackTool.setVsourcebillrowid("vlastbillrowid");
	   WriteBackTool.setVsourcebilltype("vlastbilltype");
	   WriteBackTool.writeBack(vos, "xcgl_general_b", "pk_general_b",
			   new String[]{"nwetweight"}, new String[]{"noutnum"},new String[]{"nwetweight"});	
	}
}
