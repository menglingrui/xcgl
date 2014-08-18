package nc.ui.xcgl.cttype;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.xcgl.cttype.checkClassInterface;


/**
 *类说明：合同类型ClientUI
 *@author jay
 *@version 1.0   
 *创建时间：2014-2-13下午04:09:56
 */
public class ClientUI extends XCBillCardUI{
	private static final long serialVersionUID = 1L;

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
