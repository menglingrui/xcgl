package nc.ui.xcgl.defendrecord;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.defendrecord.AggDefendrecordVO;
import nc.vo.xcgl.defendrecord.DefendrecordBVO;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	protected void onBoSave() throws Exception {
		AggDefendrecordVO billvo=(AggDefendrecordVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		//必输项校验
		BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		DefendrecordBVO []bvos=(DefendrecordBVO[])billvo.getChildrenVO();
		if(bvos!=null&&bvos.length>0){
			//校验表体的设备编码不重复
			BeforeSaveValudate.FieldBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTable(), 
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTableModel(), "equipcode", "设备编码");
			//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"equipcode"}, new String[]{"设备编码"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
	
	
}
