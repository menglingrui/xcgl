package nc.ui.xcgl.genprcessout;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
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
 *类说明：原矿加工出库单
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-11下午03:29:03
 */
public class ClientUI extends XCDefBillManageUI implements BillCardBeforeEditListener{
	private static final long serialVersionUID = 1L;
	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}
	public  boolean isStockOutBill(){
		return true;
	}
	/**
	 * 实例化界面编辑前后事件处理, 如果进行事件处理需要重载该方法 创建日期：(2004-1-3 18:13:36)
	 */
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
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
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_Generalout);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_Generalout, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	protected void initPrivateButton() {
		ButtonVO btnvo1 = new ButtonVO();
		btnvo1.setBtnNo(PuBtnConst.flowset);
		btnvo1.setBtnName("取流程设置");
		btnvo1.setBtnChinaName("取流程设置");
		btnvo1.setBtnCode(null);//code最好设置为空
		btnvo1.setOperateStatus(new int[]{IBillOperate.OP_ADD});
		addPrivateButton(btnvo1);
		super.initPrivateButton();
	}
	@Override
	public void afterEdit(BillEditEvent e) {
     super.afterEdit(e);
	 String key = e.getKey();
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
	public boolean beforeEdit(BillEditEvent e) {
		return super.beforeEdit(e);
	}
	public boolean beforeEdit(BillItemEvent e) {		
		return true;
	}
	@Override
	public boolean isStockBill() {
		return true;
	}
		
	}
	

