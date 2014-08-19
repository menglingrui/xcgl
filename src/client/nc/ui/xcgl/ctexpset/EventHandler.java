package nc.ui.xcgl.ctexpset;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.ctexpset.CtExpsetVO;
import nc.vo.xcgl.pub.bill.XCHYBillVO;

/**
 * ��ͬ����EventHandler
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
			if(xcbillvo.getChildrenVO()!=null && xcbillvo.getChildrenVO().length>0){
				CtExpsetVO [] bvos=(CtExpsetVO[]) xcbillvo.getChildrenVO();
				// ������У��
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
				//Ψһ��У��
				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vexpitemcode"}, new String[]{"��˾","���ñ���"});
			}
		}
		super.onBoSave();
	}
}
