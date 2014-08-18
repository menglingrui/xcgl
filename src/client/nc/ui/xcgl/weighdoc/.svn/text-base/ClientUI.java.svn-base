package nc.ui.xcgl.weighdoc;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.ui.xcgl.weighdoc.Controller;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 * 过磅登记UI
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_weighdoc);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_weighdoc, 
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
		if (key.equals("ngrossweight")) {
			UFDouble a = PuPubVO.getUFDouble_NullAsZero(
					getBillCardPanel().getBodyValueAt(e.getRow(),
							"ngrossweight")).sub(
					PuPubVO.getUFDouble_NullAsZero(getBillCardPanel()
							.getBodyValueAt(e.getRow(), "ntare")));
			getBillCardPanel().getBillModel().setValueAt(a, e.getRow(), "nnetweight");
			if (a.doubleValue() < 0) {
				showErrorMessage("毛重-净重的值不能小于0！");
			}
			
		}
		
		if(key.equals("ntare")){
			UFDouble b = PuPubVO.getUFDouble_NullAsZero(
					getBillCardPanel().getBodyValueAt(e.getRow(),
					"ngrossweight")).sub(
			PuPubVO.getUFDouble_NullAsZero(getBillCardPanel()
					.getBodyValueAt(e.getRow(), "ntare")));
			getBillCardPanel().getBillModel().setValueAt(b, e.getRow(), "nnetweight");
			if(b.doubleValue()<0){
				showErrorMessage("净重不能为负数！");
			}
		}
		
	}
	
	public String getRefBillType(){
        return "5X";
    }

	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}
}
