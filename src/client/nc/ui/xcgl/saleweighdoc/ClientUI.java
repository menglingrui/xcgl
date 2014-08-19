package nc.ui.xcgl.saleweighdoc;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.ui.xcgl.saleweighdoc.Controller;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;

/**
 * 销售过磅登记UI
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class ClientUI extends XCDefBillManageUI{

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
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}
	@Override
	protected void initSelfData() {
		super.initSelfData();

		//除去行操作多余按钮
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.AddLine));
		    btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
		//	btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
	
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_saleweighdoc);
	}
	@Override
	protected String getBillNo() throws java.lang.Exception {
		return HYPubBO_Client.getBillNo(PubNodeModelConst.NodeModel_saleweighdoc, 
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
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		String key=e.getKey();
//		if (key.equals("ngrossweight")) {
//			UFDouble a = PuPubVO.getUFDouble_NullAsZero(
//					getBillCardPanel().getBodyValueAt(e.getRow(),
//							"ngrossweight")).sub(
//					PuPubVO.getUFDouble_NullAsZero(getBillCardPanel()
//							.getBodyValueAt(e.getRow(), "ntare")));
//			getBillCardPanel().getBillModel().setValueAt(a, e.getRow(), "nnetweight");
//			if (a.doubleValue() < 0) {
//				showErrorMessage("净重不能为负数！");
//			}
//		}
		
//		if(key.equals("ntare")){
//			UFDouble b = PuPubVO.getUFDouble_NullAsZero(
//					getBillCardPanel().getBodyValueAt(e.getRow(),
//					"ngrossweight")).sub(
//			PuPubVO.getUFDouble_NullAsZero(getBillCardPanel()
//					.getBodyValueAt(e.getRow(), "ntare")));
//			getBillCardPanel().getBillModel().setValueAt(b, e.getRow(), "nnetweight");
//			if(b.doubleValue()<0){
//				showErrorMessage("净重不能为负数！");
//			}
//		}
		
	}

	@Override
	public boolean isStockBill() {
		return false;
	}
	public String getRefBillType(){
        return "30";
    }


}
