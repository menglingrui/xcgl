package nc.bs.pub.action;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.logger.XCLogger;
/**
 * 成本核算单(会计平台)
 * @author mlr
 */
public class N_XC60_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC60_WRITE() {
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
			Object retObj = null;
			CircularlyAccessibleValueObject vos=vo.m_preValueVo.getParentVO();
			String start=PuPubVO.getString_TrimZeroLenAsNull(vos.getAttributeValue("dstartdate"));
			String end=PuPubVO.getString_TrimZeroLenAsNull(vos.getAttributeValue("denddate"));
			if(start!=null&&end!=null){
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
			ParsePosition pos1 = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			Date dt1=formatter.parse(start,pos1);
			Date dt2=formatter.parse(end,pos2);
//			double l = dt2.getTime() - dt1.getTime();
			if(dt2.getTime()<dt1.getTime()){
				throw new BusinessException("生效日期不能大于失效日期");
			}

			}
			BsUniqueCheck.FieldUniqueChecks((SuperVO)vo.m_preValueVo.getParentVO(), new String[]{"pk_corp","vqualitygradecode","vqualitygradename"},null,"信息重复");
			
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
