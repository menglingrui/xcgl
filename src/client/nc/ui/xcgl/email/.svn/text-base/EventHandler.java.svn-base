package nc.ui.xcgl.email;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.vo.xcgl.pub.tool.XcPubTool;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		
	}

	@Override
	protected void onBoEdit() throws Exception {
		
		super.onBoEdit();
		String password=XcPubTool.decrypt((String)getBillCardPanel().getHeadItem("password").getValueObject());
		getBillCardPanel().getHeadItem("password").setValue(password);
		
		
	}
}
