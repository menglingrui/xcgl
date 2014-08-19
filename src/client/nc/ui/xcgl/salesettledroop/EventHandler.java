package nc.ui.xcgl.salesettledroop;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.salesettledroop.AggSalesettledroopVO;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	protected void onBoSave() throws Exception {
		AggSalesettledroopVO billvo=(AggSalesettledroopVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
//			SalesettledroopBVO []bvos=(SalesettledroopBVO[])billvo.getChildrenVO();
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{""}, new String[]{""});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
	
	
}
