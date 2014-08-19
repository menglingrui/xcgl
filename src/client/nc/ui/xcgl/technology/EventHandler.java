package nc.ui.xcgl.technology;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.technology.TechnologyVO;

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
				TechnologyVO []bvos=(TechnologyVO[])xcbillvo.getChildrenVO();
				// ������У��
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
				//Ψһ��У��
				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vtechnologycode"}, new String[]{"��˾","���ձ���"});
			}
			super.onBoSave();
		}
	}
	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
		int selectRow = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("false", selectRow, "isclose");
	}
}
