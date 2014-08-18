package nc.ui.xcgl.flouryield;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 * 精粉产量计算
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
		getBillCardPanel().getTailItem("voperatorid").setValue(
				ClientEnvironment.getInstance().getUser().getPrimaryKey());
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_flouryield);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_flouryield, 
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
	protected void initPrivateButton() {
		super.initPrivateButton();		
		ButtonVO btnvo61 = new ButtonVO();
		btnvo61.setBtnNo(PuBtnConst.calYield);
		btnvo61.setBtnName("产量计算");
		btnvo61.setBtnChinaName("产量计算");
		btnvo61.setBtnCode(null);// code最好设置为空
		btnvo61.setBusinessStatus(new int[]{IBillStatus.FREE});
		btnvo61.setOperateStatus(new int[] { IBillOperate.OP_ADD,
				IBillOperate.OP_EDIT });
		addPrivateButton(btnvo61);
	}

	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}
}
	

