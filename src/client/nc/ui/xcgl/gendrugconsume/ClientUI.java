package nc.ui.xcgl.gendrugconsume;

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
import nc.vo.xcgl.pub.consts.PubNodeModelConst;

/**
 * 药剂消耗岗位记录UI
 * @author ddk
 *
 */
public class ClientUI extends XCDefBillManageUI{
	private static final long serialVersionUID = -3408122692180226368L;
	
	EventHandler h = (EventHandler) createEventHandler();
	
	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}
	public  boolean isStockOutBill(){
		return true;
	}
	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}

	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
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
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_Drugconsume);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubNodeModelConst.NodeModel_Drugconsume, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	
	protected void initPrivateButton() {
		ButtonVO btnvo1 = new ButtonVO();
		btnvo1.setBtnNo(PuBtnConst.flowset);
		btnvo1.setBtnName("取流程设置");
		btnvo1.setBtnChinaName("取流程设置");
		btnvo1.setOperateStatus(new int[]{IBillOperate.OP_ADD});
		btnvo1.setBtnCode(null);
		addPrivateButton(btnvo1);
		super.initPrivateButton();
	}
	

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		return  super.beforeEdit(e);
	}
	@Override
	public void afterEdit(BillEditEvent e) {
     super.afterEdit(e);
	}
	

	 
	

	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return true;
	}
}

