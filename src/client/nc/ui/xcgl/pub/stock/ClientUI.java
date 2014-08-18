package nc.ui.xcgl.pub.stock;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.ui.xcgl.pub.stock.Controller;

/**
 * œ÷¥Ê¡øUI
 * @author ddk
 *
 */
public class ClientUI extends XCBillCardUI{
	private static final long serialVersionUID = 1L;

	@Override
	protected ICardController createController() {
		return new Controller();
	}
	
	@Override
	protected CardEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	
	@Override
	public String getRefBillType() {
		return null;
	}

	@Override
	protected void initSelfData() {
		
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}
	
}
