package nc.ui.xcgl.factory;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.factory.Controller;
import nc.ui.xcgl.factory.EventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.xcgl.factory.checkClassInterface;
/**
 * Ñ¡³§µµ°¸UI
 * @author ddk
 *
 */
public class ClientUI extends XCBillCardUI{
	private static final long serialVersionUID = -7835330581388633651L;

	@Override
	protected ICardController createController() {
		return new Controller();
	}
	
	@Override
	protected CardEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
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
