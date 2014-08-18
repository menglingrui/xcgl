package nc.ui.xcgl.cashonhand;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
/**
 * 结存量EventHandler
 * @author ddk
 *
 */
public class EventHandler extends XCSingleBodyEventHandler{

	public EventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	@Override
	protected void onBoSave() throws Exception {
		XCHYBillVO xcbillvo= (XCHYBillVO) getBillCardPanelWrapper().getBillVOFromUI();
		if(xcbillvo.getChildrenVO()!=null){
			if(xcbillvo==null)
				return;
			if(xcbillvo.getChildrenVO()!=null&&xcbillvo.getChildrenVO().length>0){	
				// 必输项校验
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			}
			super.onBoSave();
		}
	}
}
