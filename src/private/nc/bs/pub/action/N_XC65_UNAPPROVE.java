package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.jdbc.framework.processor.ColumnProcessor;

import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * )
 * @author lxh
 */
public class N_XC65_UNAPPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;
	private BaseDAO dao=null;
	public BaseDAO getDao() {
	if (dao == null) {
		dao = new BaseDAO();
	}
	return dao;
	}
	public N_XC65_UNAPPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
		  // ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			setParameter("currentVo", vo.m_preValueVo);
			checkSource(vo.m_preValueVo.getChildrenVO());
			procUnApproveFlow(vo);
			ZmPubTool.checkExitNextBill( (String) vo.m_preValueVo.getParentVO().getAttributeValue("pk_billtype"), vo.m_preValueVo.getParentVO().getPrimaryKey());
			Object retObj = runClass("nc.bs.xcgl.pub.HYBillUnApprove",
					"unApproveHYBill", "nc.vo.pub.AggregatedValueObject:01",
					vo, m_keyHas, m_methodReturnHas);
			return retObj;
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
			//查看是否已经销售结算
			check(vid);	
		}	
	}
	
	public List<CircularlyAccessibleValueObject> getPowder(
			CircularlyAccessibleValueObject[] vos) {		
		List<CircularlyAccessibleValueObject> list=new ArrayList<CircularlyAccessibleValueObject>();		
	    if(vos==null || vos.length==0){
	    	return list;
	    }		
	    for(int i=0;i<vos.length;i++){
	    	if(PuPubVO.getUFBoolean_NullAs(vos[i].getAttributeValue("ureserve1"), new UFBoolean(false)).booleanValue()==true){
	    			list.add(vos[i]);
	    	}
	    }
	    		
		return list;
	}
	
	public void check(String pk_weight) throws BusinessException {
		String sql="select  count(0) from xcgl_salepresettle_b b join  xcgl_salepresettle_h h" +
				"  on b.pk_salepresettle_h=h.pk_salepresettle_h " +
				" where b.vlastbillid='"+pk_weight+"' " +
				" and isnull(b.dr,0)=0 " +
				" and isnull(h.dr,0)=0 " +
				" and h.pk_billtype='"+PubBillTypeConst.billtype_salesettle+"'";			
		Integer count=PuPubVO.getInteger_NullAs(getDao().executeQuery(sql, new ColumnProcessor()), -1);
		if(count>0){
			String sql1="select h.vbillno from xcgl_salepresettle_b b join  xcgl_salepresettle_h h" +
			"  on b.pk_salepresettle_h=h.pk_salepresettle_h " +
			" where b.vlastbillid='"+pk_weight+"' " +
			" and isnull(b.dr,0)=0 " +
			" and isnull(h.dr,0)=0 " +
			" and h.pk_billtype='"+PubBillTypeConst.billtype_salesettle+"'";			
	        String vbillno=PuPubVO.getString_TrimZeroLenAsNull(getDao().executeQuery(sql1, new ColumnProcessor()));
			throw new BusinessException("已经销售结算，不能弃审预结算,结算单号为:["+vbillno+"]");
		}
	}
	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n procUnApproveFlow (vo); \nObject retObj=runClassCom@ \"nc.bs.pp.pub.comstatus.HYBillUnApprove\", \"unApproveHYBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\nreturn retObj;\n";
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
