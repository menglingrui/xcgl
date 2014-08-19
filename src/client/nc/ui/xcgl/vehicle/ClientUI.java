package nc.ui.xcgl.vehicle;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.xcgl.pub.bill.XCBillCardUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.xcgl.vehicle.checkClassInterface;
/**
 * 车辆档案
 * @author lxh
 */

public class ClientUI extends XCBillCardUI{
	private static final long serialVersionUID = 1L;

	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
	
	public java.lang.Object getUserObject() {
		return new checkClassInterface();
	}
	
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}
	/**
	 * 实例化界面编辑前后事件处理,
	 * 如果进行事件处理需要重载该方法
	 * 创建日期：(2004-1-3 18:13:36)
	 */
	protected CardEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}


	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}
	

	@Override
	public String getRefBillType() {
		return null;
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


}
