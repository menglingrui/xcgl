package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.logger.XCLogger;
/**
 * 成本核算单(会计平台)
 * @author mlr
 */
public class N_XC47_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC47_WRITE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	//为样品编码赋值；
	CircularlyAccessibleValueObject[] bvos= vo.m_preValueVo.getChildrenVO();
	if(bvos!=null&&bvos.length>0){
	 String vlastbillid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillid"));
	 if(vlastbillid!=null&&vlastbillid.trim()!=""){
		 String sql="update xcgl_sample set isref='Y'"+ " where pk_sample='"+vlastbillid+"'";
		 XCZmPubDAO.getDAO().executeUpdate(sql); 
	 }
	}
//	for(int i=0;i<bvos.length;i++){
//		if((String)bvos[i].getAttributeValue("vsamplenumber")==null||
//				(String)bvos[i].getAttributeValue("vsamplenumber").toString().trim()==""){
//			bvos[i].setAttributeValue("vsamplenumber", new HYPubBO().getBillNo(
//					PubBillTypeConst.billtype_salesampleNo, SQLHelper.getCorpPk(), null, null));
//		}
//	}
	super.m_tmpVo=vo;
	try {
			super.m_tmpVo = vo;
			Object retObj = null;
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
