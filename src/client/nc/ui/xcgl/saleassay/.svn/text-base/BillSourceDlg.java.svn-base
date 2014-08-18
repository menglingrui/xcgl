package nc.ui.xcgl.saleassay;

import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG;

public class BillSourceDlg extends XCMBillSourceDLG{
	String businessType =null;
	/**
	 * @date 2014-1-2下午02:57:01
	 */
	private static final long serialVersionUID = 3886184900807493717L;
	public BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
		this.businessType=businessType;;
	}

//	
//	protected AggregatedValueObject[] spiltBillVos = null;
//	//分担维度 表头
//	protected String[] spiltFields=null;
//	//分担维度 表体
//	protected String[] spiltFields1=null;
//
//	public BillSourceDlg(String pkField, String pkCorp, String operator,
//			String funNode, String queryWhere, String billType,
//			String businessType, String templateId, String currentBillType,
//			Container parent) {
//		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
//				businessType, templateId, currentBillType, parent);
//
//	}
//	
//	public AggregatedValueObject[] getSpiltBillVos() {
//		return spiltBillVos;
//	}
//
//	public void setSpiltBillVos(AggregatedValueObject[] spiltBillVos) {
//		this.spiltBillVos = spiltBillVos;
//	}
//
//	public String[] getSpiltFields() {
//		return spiltFields;
//	}
//
//	public void setSpiltFields(String[] spiltFields) {
//		this.spiltFields = spiltFields;
//	}
//
//	public String[] getSpiltFields1() {
//		return spiltFields1;
//	}
//
//	public void setSpiltFields1(String[] spiltFields1) {
//		this.spiltFields1 = spiltFields1;
//	}
//
//	/**
//	 * 修改过滤查询条件；
//	 * yangtao
//	 */
	public String getHeadCondition() {
		String whersql=" xcgl_sample.vbillstatus='1' and xcgl_sample.isref='N' ";
		if(businessType!=null&& businessType.length()>0){
			whersql=whersql+" and xcgl_sample.pk_busitype='"+businessType+"'";
		}
		return whersql;
	}          
//	
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
//
//	@Override
//	public String getHeadSpitChar() {
//		
//		return "x";
//	}
//
//	@Override
//	public String getRefBillType() {
//		
//		return PubBillTypeConst.billtype_sample_ref;
//	}
//
//	@Override
//	public IControllerBase getUIController() {
//	
//		return new nc.ui.xcgl.salesample.Controller();
//	}
//
//	@Override
//	public String getPk_invbasdocName() {
//	
//		return null;
//	}
//
//	@Override
//	public String getPk_invmandocName() {
//	
//		return null;
//	}

	@Override
	public String getPk_invbasdocName() {
		
		return null;
	}

	@Override
	public String getPk_invmandocName() {
		
		return null;
	}

	@Override
	public IControllerBase getUIController() {
		
		return new nc.ui.xcgl.salesample.Controller();
	}
}
