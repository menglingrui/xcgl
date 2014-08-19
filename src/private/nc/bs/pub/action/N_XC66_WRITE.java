package nc.bs.pub.action;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.dao.ZmBaseDAO;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 * ���۽���
 * @author yangtao
 * @date 2014-3-27 ����03:57:07
 */
public class N_XC66_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
private static ZmBaseDAO dao=null;
public static ZmBaseDAO getDAO(){
	if(dao==null){
		dao=new ZmPubDAO();
	}
	return dao;
}
public N_XC66_WRITE() {
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
			CircularlyAccessibleValueObject[] vos=vo.m_preValueVo.getChildrenVO();
			checkSource(vos);	
//			if(vos!=null&&vos.length>0){
//			String vid=(String) vos[0].getAttributeValue("vlastbillid");
//			String sql="update xcgl_weighdoc set ISSALEREF1='Y' where pk_weighdoc='"+vid+"'";
//			getDAO().executeUpdate(sql);
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
public void checkSource(CircularlyAccessibleValueObject[] vos) throws BusinessException {
	List<CircularlyAccessibleValueObject> list=getPowder(vos);
	
	for(int i=0;i<list.size();i++){
		String vid=(String) list.get(i).getAttributeValue("vlastbillid");
		//�鿴�Ƿ��Ѿ����۽���
//		check(vid);	
		String sql="update xcgl_weighdoc set ISSALEREF1='Y' where pk_weighdoc='"+vid+"'";		
		getDAO().executeUpdate(sql);
	}	
}
//public void check(String pk_weight) throws BusinessException {
//	String sql="select  count(0) from xcgl_salepresettle_b b join  xcgl_salepresettle_h h" +
//			"  on b.pk_salepresettle_h=h.pk_salepresettle_h " +
//			" where b.vlastbillid='"+pk_weight+"' " +
//			" and isnull(b.dr,0)=0 " +
//			" and isnull(h.dr,0)=0 " +
//			" and h.pk_billtype='"+PubBillTypeConst.billtype_salesettle+"'";
//			
//	Integer count=PuPubVO.getInteger_NullAs(getDAO().executeQuery(sql, new ColumnProcessor()), -1);
//	if(count>0){
//
//		String sql1="select h.vbillno from xcgl_salepresettle_b b join  xcgl_salepresettle_h h" +
//		"  on b.pk_salepresettle_h=h.pk_salepresettle_h " +
//		" where b.vlastbillid='"+pk_weight+"' " +
//		" and isnull(b.dr,0)=0 " +
//		" and isnull(h.dr,0)=0 " +
//		" and h.pk_billtype='"+PubBillTypeConst.billtype_salesettle+"'";			
//        String vbillno=PuPubVO.getString_TrimZeroLenAsNull(getDAO().executeQuery(sql1, new ColumnProcessor()));
//		throw new BusinessException("�Ѿ����۽��㣬��������Ԥ����,���㵥��Ϊ:["+vbillno+"]");
//	}
//}
public List<CircularlyAccessibleValueObject> getPowder(
		CircularlyAccessibleValueObject[] vos) {
	
	List<CircularlyAccessibleValueObject> list=new ArrayList<CircularlyAccessibleValueObject>();
	
    if(vos==null || vos.length==0){
    	return list;
    }
	
    for(int i=0;i<vos.length;i++){
    	if(PuPubVO.getUFBoolean_NullAs(vos[i].getAttributeValue("ureserve2"), new UFBoolean(false)).booleanValue()==true){
    			list.add(vos[i]);
    	}
    }
    
	
	return list;
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
