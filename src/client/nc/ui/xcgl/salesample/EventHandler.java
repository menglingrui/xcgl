package nc.ui.xcgl.salesample;

import nc.ui.pub.ButtonObject;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.vo.pub.CircularlyAccessibleValueObject;
/**
 * 销售送样单 EventHandler
 * @author ddk
 *
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	public void onBillRef() throws Exception {
		
		super.onBillRef();
	}
	@Override
	protected void onBoLineAdd() throws Exception {
		/**
		 * 表体所有的行的数据
		 */
		CircularlyAccessibleValueObject[] bodyvos = 
    		getBillCardPanelWrapper().getBillCardPanel().getBillModel().getBodyValueVOs(
    				getUIController().getBillVoName()[2]);
		if(bodyvos==null||bodyvos.length==0){
		super.onBoLineAdd();}
		else{
			// 增加行之前调用的抽象方法
			getBillCardPanelWrapper().addLine();
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("csourcebillcode"), bodyvos.length, "csourcebillcode");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vsourcebillid"), bodyvos.length, "vsourcebillid");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vsourcebillrowid"), bodyvos.length, "vsourcebillrowid");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vsourcebilltype"), bodyvos.length, "vsourcebilltype");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vlastbillcode"), bodyvos.length, "vlastbillcode");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vlastbillid"), bodyvos.length, "vlastbillid");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vlastbillrowid"), bodyvos.length, "vlastbillrowid");
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
					bodyvos[0].getAttributeValue("vlastbilltype"), bodyvos.length, "vlastbilltype");
		}
	}
	public  void onBoBusiTypeAdd(ButtonObject bo, String sourceBillId)
	throws Exception {
		super.onBoBusiTypeAdd(bo, sourceBillId);
		getButtonManager().getButton(IBillButton.Line)
		.setEnabled(true);
		getBillManageUI().updateButtons();
	}
   @Override
protected void onBoSave() throws Exception {
	super.onBoSave();
}
}
