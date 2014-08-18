package nc.ui.xcgl.runrecord;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.runrecord.AggRunrecordHVO;
import nc.vo.xcgl.runrecord.RunrecordBVO;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	protected void onBoSave() throws Exception {
		AggRunrecordHVO billvo=(AggRunrecordHVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		//必输项校验
		BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		RunrecordBVO [] bvos=(RunrecordBVO[])billvo.getChildrenVO();
		if(bvos!=null&&bvos.length>0){
			//设备编码字段不为空校验
			BeforeSaveValudate.FieldBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTable(), 
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTableModel(), "equipcode", "设备编码");
			//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"equipcode"}, new String[]{"设备编码"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
	
	
}
