package nc.ui.xcgl.saleminenanteil;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.ui.xcgl.saleminenanteil.Controller;
import nc.ui.xcgl.saleminenanteil.EventHandler;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 * 销售按矿区比例分摊计算
 * @author lxh
 */
@SuppressWarnings("serial")
public class ClientUI extends XCDefBillManageUI{
	
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
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
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
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vmonth").setValue(
				ClientEnvironment.getInstance().getDate().toString().substring(0, 7));
		getBillCardPanel().getTailItem("voperatorid").setValue(
				ClientEnvironment.getInstance().getUser().getPrimaryKey());
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_saleminenanteil);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_saleminenanteil, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		return true;
	}

	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}
}
	

