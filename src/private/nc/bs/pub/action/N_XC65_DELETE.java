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
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			//删除时将是否参照值还原
			CircularlyAccessibleValueObject[] vos=vo.m_preValueVo.getChildrenVO();
			checkSource(vos);	

			
			
			// 方法说明:行业公共删除
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
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\nObject retObj  =null;\n//方法说明:行业公共删除\nretObj  =runClassCom@ \"nc.bs.trade.comdelete.BillDelete\", \"deleteBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\n//##################################################\nreturn retObj;\n";
	}

	/*
	 * 备注：设置脚本变量的HAS
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
