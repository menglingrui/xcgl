package nc.ui.xcgl.pub.stock;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;

public class EventHandler extends XCSingleBodyEventHandler{

	public EventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	@Override
	protected void onBoSave() throws Exception {
		super.onBoSave();
	}

}
