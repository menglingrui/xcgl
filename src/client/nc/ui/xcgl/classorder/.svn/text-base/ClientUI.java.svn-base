package nc.ui.xcgl.classorder;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.classorder.Controller;
import nc.ui.xcgl.classorder.Eventhandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.xcgl.classorder.checkClassInterface;

/**
 * °à´Îµµ°¸UI
 * @author ddk
 *
 */
public class ClientUI extends XCBillCardUI{

	@Override
	protected ICardController createController() {
		return new Controller();
	}
	
	@Override
	protected CardEventHandler createEventHandler() {
		return new Eventhandler(this,getUIControl());
	}
	@Override
	public String getRefBillType() {
		return null;
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
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}
	
	public java.lang.Object getUserObject() {
		return new checkClassInterface();
	}

}
