package nc.ui.xcgl.ctexpset;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.xcgl.ctexpset.checkClassInterface;
/**
 * 合同费用UI
 * @author ddk
 *
 */
public class ClientUI extends XCBillCardUI{
	private static final long serialVersionUID = -4440757664438701249L;

	@Override
	protected ICardController createController() {
		return new Controller();
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
		
	}
	
	@Override
	protected CardEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	public java.lang.Object getUserObject() {
		return new checkClassInterface();
	}

}
