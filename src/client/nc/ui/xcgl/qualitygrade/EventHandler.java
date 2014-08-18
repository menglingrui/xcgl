package nc.ui.xcgl.qualitygrade;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.qualitygrade.AggQualityGradeVO;
/**
 * 优质优价—品位EventHandler
 * @author ddk
 *
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	@Override
	protected void onBoSave() throws Exception {
		AggQualityGradeVO billvo =(AggQualityGradeVO) getBillCardPanelWrapper().getBillVOFromUI();
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
