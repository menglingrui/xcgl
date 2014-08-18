package nc.ui.xcgl.process;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.process.ProcessVO;
import nc.vo.xcgl.pub.bill.XCHYBillVO;

public class EventHandler extends XCSingleBodyEventHandler{

	public EventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	@Override
	protected void onBoSave() throws Exception {
		XCHYBillVO xcbillvo= (XCHYBillVO) getBillCardPanelWrapper().getBillVOFromUI();
		if(xcbillvo==null)
			return;
		//if(xcbillvo.getChildrenVO()!=null){
			if(xcbillvo.getChildrenVO()!=null&&xcbillvo.getChildrenVO().length>0){	
//				int selectrow = getBillCardPanelWrapper().getBillCardPanel()
//				.getBodyPanel().getTable().getSelectedRow();
				ProcessVO []bvos=(ProcessVO[])xcbillvo.getChildrenVO();
				// 必输项校验
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
				//唯一性校验
				//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vprocesscode"}, new String[]{"公司","工序编码"});
			}
//			else 
//				throw new BusinessException("表体不能为空!");
			super.onBoSave();
			
		}
	//}
	
	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
		int selectRow = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("false", selectRow, "isclose");
	}

}
