package nc.ui.xcgl.allocateflour;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 * ¾«·Ûµ÷²¦µ¥
 * @author Jay
 *
 */
public class ClientUI extends XCDefBillManageUI{

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isStockBill() {
		
		return false;
	}

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
     @Override
    protected ManageEventHandler createEventHandler() {
    	
    	return new EventHandler(this,getUIControl());
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
		getBillCardPanel().getTailItem("voperatorid").setValue(
				ClientEnvironment.getInstance().getUser().getPrimaryKey());
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_allocateofflour);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_allocateofflour, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}

}
