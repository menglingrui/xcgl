package nc.ui.xcgl.classorder;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.classorder.ClassorderVO;
import nc.vo.xcgl.pub.bill.XCHYBillVO;

public class Eventhandler extends XCSingleBodyEventHandler{

	public Eventhandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	
	@Override
	protected void onBoSave() throws Exception {
		XCHYBillVO xcbillvo= (XCHYBillVO) getBillCardPanelWrapper().getBillVOFromUI();
		if(xcbillvo.getChildrenVO()!=null){
			if(xcbillvo==null)
				return;
			if(xcbillvo.getChildrenVO()!=null&&xcbillvo.getChildrenVO().length>0){	
				ClassorderVO []bvos=(ClassorderVO[])xcbillvo.getChildrenVO();
				// 必输项校验
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
				//唯一性校验
				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vclassordercode"}, new String[]{"公司","编码"});
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
