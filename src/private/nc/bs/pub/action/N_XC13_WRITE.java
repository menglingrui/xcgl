package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.trade.business.HYPubBO;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.tool.XcPubTool;
import nc.vo.xcgl.sample.SampleHVO;
/**
 * 送样单保存后台动作脚本；
 * @author mlr
 */
public class N_XC13_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC13_WRITE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{	
	//为样品编码赋值；
//	CircularlyAccessibleValueObject[] bvos= vo.m_preValueVo.getChildrenVO();
//	 for(int i=0;i<bvos.length;i++){
//		 
//		if((String)bvos[i].getAttributeValue("vsamplenumber")==null||
//				(String)bvos[i].getAttributeValue("vsamplenumber").toString().trim()==""){
//		    bvos[i].setAttributeValue("vsamplenumber", new HYPubBO().getBillNo(
//		    		PubBillTypeConst.billtype_SampleNo, SQLHelper.getCorpPk(), null, null));
//		}
//	        }
	SampleHVO hvo= (SampleHVO) vo.m_preValueVo.getParentVO();
	hvo.setAttributeValue("vsamplenumber", new HYPubBO().getBillNo(
	    		PubBillTypeConst.billtype_SampleNo, SQLHelper.getCorpPk(), null, null));
	hvo.setAttributeValue("vsampleno", new HYPubBO().getBillNo(
    		PubBillTypeConst.billtype_SampleNoView, SQLHelper.getCorpPk(), null, null));
	//检验系统参数是否对样品编码加密;
//         String prara=PuPubVO.getString_TrimZeroLenAsNull(
//			XCZmPubDAO.getDAO().executeQuery("select value from pub_sysinit where  " +
//			" initcode='SAMPNO' and  pk_org='"+SQLHelper.getCorpPk()+"'", 
//			new ColumnProcessor()));
//        if(prara!=null&&prara.equals("Y")){
//	    	  if(hvo.getAttributeValue("vsamplenumber").toString().length()<32){
//		    String newno=XcPubTool.encrypt((String)hvo.getAttributeValue("vsamplenumber"));
//		    hvo.setAttributeValue("vsamplenumber", newno);
//	    	  }
//	        }
	      
//	SampleHVO headvo=(SampleHVO) billvo.getParentVO();
	BsUniqueCheck.FieldUniqueCheck(hvo, 
			new String[]{"pk_billtype","dbilldate","pk_factory","pk_minarea","pk_beltline","pk_classorder","pk_invmandoc","isresel","vdef20"},
			"单据类型，单据日期，选厂，部门，生产线，班次，存货，是否二次选组合重复,关联矿石 ");
	

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
