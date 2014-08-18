package nc.ui.xcgl.qualitypro;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.qualitypro.ExAggQualityProVO;

/**
 * 优质优价方案EventHandler
 * @author ddk
 *
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	@Override
	protected void onBoSave() throws Exception {
		ExAggQualityProVO billvo = (ExAggQualityProVO) getBillCardPanelWrapper().getBillVOFromUI();
		if(billvo.getChildrenVO()!=null){
			if(billvo==null)
				return;
			if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){
				// 必输项校验
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			}else
			throw new BusinessException("表体不能为空!");
			super.onBoSave();
		}	
	}
}
