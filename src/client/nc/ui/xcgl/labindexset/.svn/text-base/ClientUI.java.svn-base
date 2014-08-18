package nc.ui.xcgl.labindexset;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.labindexset.Controller;
import nc.ui.xcgl.labindexset.EventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.xcgl.labindexset.checkClassInterface;
/**
 * 化验指标定义档案
 * @author lxh
 */
public class ClientUI extends XCDefBillManageUI{

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}
	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}

	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}

	public java.lang.Object getUserObject() {
		return new checkClassInterface();
	}
	
	
	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}
	
	@Override
	protected ManageEventHandler createEventHandler() {
	
		return new EventHandler(this,getUIControl());
	}

	
	
	@Override
	protected void initSelfData() {
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
		super.initSelfData();
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		
		int pos  = e.getPos();
		
		if(BillItem.HEAD == pos){
			 String key = e.getKey();
			 
			 if("pk_invmandoc".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
			 }
		}
	}
	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}

}
