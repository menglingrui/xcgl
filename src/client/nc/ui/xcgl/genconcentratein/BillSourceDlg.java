package nc.ui.xcgl.genconcentratein;

import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.zmpub.pub.bill.MBillSourceDLG;

public class BillSourceDlg extends MBillSourceDLG{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4791039515957546124L;
	public BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
	}
	//xew_proaccept  xew_proaccept_b
	public String getHeadCondition() {
			String sql=null;
			return sql=" ";
	}
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
	protected boolean isBodyCanMultiSelect() {
		return false;
	}
	protected boolean isBodyCanSelected() {
		return false;
	}
	
	@Override
	public String getPk_invbasdocName() {
	
		return "pk_invbasdoc";
	}

	@Override
	public String getPk_invmandocName() {
		return "pk_invmandoc";
	}
	
	@Override
	public IControllerBase getUIController() {
		return new nc.ui.xcgl.flouryield.Controller();
	}																																																																																																																																																																														
}
