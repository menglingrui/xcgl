package nc.ui.xcgl.produceset;

import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCExtFlowManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonExtDefBillManageUI;
import nc.ui.zmpub.pub.bill.SonDefBillManageUI;
import nc.vo.pub.AggregatedValueObject;

public class EventHandler extends XCExtFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	@Override
	protected void onBoSave() throws Exception {
		super.onBoSave();
		super.onBoRefresh();
	}
	@Override
	protected void onBoDelete() throws Exception {

		// 界面没有数据或者有数据但是没有选中任何行
		if (getBufferData().getCurrentVO() == null)
			return;

		if (MessageDialog.showOkCancelDlg(getBillUI(),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
						"UPPuifactory-000064")/* @res "档案删除" */,
			"是否确认删除该生产流程设置?如果正常业务已经执行会影响正常业务,建议封存"/* @res "是否确认删除该基本档案?如果正常业务已经执行会影响正常业务,建议封存" */
				, MessageDialog.ID_CANCEL) != UIDialog.ID_OK)
			return;

		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		getBusinessAction().delete(modelVo, getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (PfUtilClient.isSuccess()) {
			// 清除界面数据
			getBillUI().removeListHeadData(getBufferData().getCurrentRow());
			if (getUIController() instanceof ISingleController) {
				ISingleController sctl = (ISingleController) getUIController();
				if (!sctl.isSingleDetail())
					getBufferData().removeCurrentRow();
			} else {
				getBufferData().removeCurrentRow();
			}

		}
		if (getBufferData().getVOBufferSize() == 0)
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
		else
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
		getBufferData().setCurrentRow(getBufferData().getCurrentRow());	
	}
	
	protected void onBoCancel() throws Exception {	
	  if (getBufferData().isVOBufferEmpty()) {
		getBillUI().setBillOperate(IBillOperate.OP_INIT);
		if(getBillUI() instanceof SonDefBillManageUI){
			SonDefBillManageUI ui=(SonDefBillManageUI) getBillUI();
			if(ui.getHl()!=null)
			ui.getHl().clear();
			if(ui.getHl1()!=null)
			ui.getHl1().clear();
		}
		if(getBillUI() instanceof XCSonExtDefBillManageUI){
			XCSonExtDefBillManageUI ui=(XCSonExtDefBillManageUI) getBillUI();
			if(ui.getHl12()!=null)
			ui.getHl12().clear();
			if(ui.getSonMap1()!=null)
			ui.getSonMap1().clear();
		}
	  } else {
		getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
		
        if(getBufferData().getCurrentRow()==-1){
           getBufferData().setCurrentRow(0);
        }else{
           getBufferData().setCurrentRow(getBufferData().getCurrentRow());
        }			
		
	}}
}
