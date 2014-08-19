package nc.bs.pub.action;
import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * @author ddk
 */
public class N_XC29_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC29_WRITE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
//try{
//	super.m_tmpVo=vo;
	try {   
		CircularlyAccessibleValueObject[] bvos= vo.m_preValueVo.getChildrenVO();
		 String vlastbillid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillid"));
		 if(vlastbillid!=null&&vlastbillid.trim()!=""){
			 String sql="update xcgl_weighdoc set isref='Y'"+ " where pk_weighdoc='"+vlastbillid+"'";
			 XCZmPubDAO.getDAO().executeUpdate(sql); 
			 }
			super.m_tmpVo = vo;
			Object retObj = null;
			retObj = runClass("nc.bs.xcgl.pub.bill.XCSonBillSave", "saveBill1","nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,	m_methodReturnHas);
			return retObj;
//		} catch (Exception ex) {
//			if (ex instanceof BusinessException)
//				throw (BusinessException) ex;
//			else
//				throw new PFBusinessException(ex.getMessage(), ex);
//		}
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
