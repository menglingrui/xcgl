package nc.ui.xcgl.salewatertest;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
/**
 * ����ˮ�ֻ��鵥
 * @author Jay
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

}
