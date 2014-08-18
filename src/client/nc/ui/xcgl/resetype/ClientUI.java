package nc.ui.xcgl.resetype;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.xcgl.resetype.checkClassInterface;



/**
 * 收发类别UI
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
