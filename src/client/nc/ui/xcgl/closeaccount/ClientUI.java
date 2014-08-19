package nc.ui.xcgl.closeaccount;

import nc.ui.pub.bill.BillEditEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.button.ButtonVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;

@SuppressWarnings("serial")
public class ClientUI extends XCDefBillManageUI{

	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
	
	@Override
	protected ManageEventHandler createEventHandler() {
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
		
	}
	
	protected void initPrivateButton() {
		ButtonVO btnvo1 = new ButtonVO();
		btnvo1.setBtnNo(PuBtnConst.open);
		btnvo1.setBtnName("打开");
		btnvo1.setBtnChinaName("打开");
		btnvo1.setBtnCode(null);
		addPrivateButton(btnvo1);
		
		ButtonVO btnvo2 = new ButtonVO();
		btnvo2.setBtnNo(PuBtnConst.close);
		btnvo2.setBtnName("关闭");
		btnvo2.setBtnChinaName("关闭");
		btnvo2.setBtnCode(null);
		addPrivateButton(btnvo2);
		super.initPrivateButton();
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		return true;
	}

	@Override
	public boolean isLinkQueryEnable() {
		return false;
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
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
