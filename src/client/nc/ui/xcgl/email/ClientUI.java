package nc.ui.xcgl.email;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;

import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * 邮件设置入口类
 * @author yangtao
 * @date 2014-2-25 下午04:24:15
 */
public class ClientUI  extends XCDefBillManageUI implements BillCardBeforeEditListener{

	/**
	 * @date 2014-2-25下午04:24:09
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isLinkQueryEnable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}

	/**
	 * 实例化界面编辑前后事件处理, 如果进行事件处理需要重载该方法 创建日期：(2004-1-3 18:13:36)
	 */
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
	}

	public boolean beforeEdit(BillItemEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}

}
