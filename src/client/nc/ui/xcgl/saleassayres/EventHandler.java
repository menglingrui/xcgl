package nc.ui.xcgl.saleassayres;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonDefBillManageUI;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.assayres.AggAssayResVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

public class EventHandler extends XCFlowManageEventHandler{
	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	protected void onBoSave() throws Exception {
		AggAssayResVO billvo = (AggAssayResVO)getBillUI().getVOFromUI();
		if(billvo==null)//not null check
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
			//必输项校验
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
		super.onBoRefresh();
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.ckmx){
				 quotaQuery();
			 }
		}
		super.onBoElse(intBtn);
	}
    protected String getSampleNo()throws Exception{
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_salesampleNo,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null
		);
	} 
}
