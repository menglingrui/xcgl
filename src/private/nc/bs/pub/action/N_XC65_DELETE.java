package nc.bs.pub.action;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 
 * @author lxh
 */
public class N_XC65_DELETE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;


	public N_XC65_DELETE() {
		super();
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
			Object retObj = null;
			//ɾ��ʱ���Ƿ����ֵ��ԭ
			CircularlyAccessibleValueObject[] vos=vo.m_preValueVo.getChildrenVO();
			checkSource(vos);	

			
			
			// ����˵��:��ҵ����ɾ��
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
	public void checkSource(CircularlyAccessibleValueObject[] vos) throws BusinessException {
		List<CircularlyAccessibleValueObject> list=getPowder(vos);
		
		for(int i=0;i<list.size();i++){
			String vid=(String) list.get(i).getAttributeValue("vlastbillid");
			String sql="update xcgl_weighdoc set ISSALEREF='N' where pk_weighdoc='"+vid+"'";
			ZmPubDAO.getDAO().executeUpdate(sql);
		}	
	}
	/*
	 * ��ע��ƽ̨��дԭʼ�ű�
	 */
	public String getCodeRemark() {
		return "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\nObject retObj  =null;\n//����˵��:��ҵ����ɾ��\nretObj  =runClassCom@ \"nc.bs.trade.comdelete.BillDelete\", \"deleteBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\n//##################################################\nreturn retObj;\n";
	}

	/*
	 * ��ע�����ýű�������HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}
}
