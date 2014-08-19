package nc.ui.xcgl.cttermtype;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.xcgl.cttermtype.checkClassInterface;

/**
 *类说明：合同条款类型定义ClientUI
 *@author jay
 *@version 1.0   
 *创建时间：2014-2-12上午10:24:26
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
	public java.lang.Object getUserObject() {
		return new checkClassInterface();
	}

}
