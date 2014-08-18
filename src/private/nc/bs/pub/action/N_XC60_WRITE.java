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
 * �ɱ����㵥(���ƽ̨)
 * @author mlr
 */
public class N_XC60_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC60_WRITE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
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
				throw new BusinessException("��Ч���ڲ��ܴ���ʧЧ����");
			}

			}
			BsUniqueCheck.FieldUniqueChecks((SuperVO)vo.m_preValueVo.getParentVO(), new String[]{"pk_corp","vqualitygradecode","vqualitygradename"},null,"��Ϣ�ظ�");
			
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
* ��ע��ƽ̨��дԭʼ�ű�
*/
public String getCodeRemark(){
	return null;
}
//	return "	try {\n			super.m_tmpVo = vo;\n			Object retObj = null;\n			// ���漴�ύ\n			retObj = runClass(\"nc.bs.gt.pub.HYBillSave\", \"saveBill\",\"nc.vo.pub.AggregatedValueObject:01\", vo, m_keyHas,	m_methodReturnHas);\n			return retObj;\n		} catch (Exception ex) {\n			if (ex instanceof BusinessException)\n				throw (BusinessException) ex;\n			else\n				throw new PFBusinessException(ex.getMessage(), ex);\n		}\n";}
/*
* ��ע�����ýű�������HAS
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
