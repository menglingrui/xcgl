package nc.ui.xcgl.resetype;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.resetype.ResetypeVO;
/**
 * 收发类别EventHandler
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
				ResetypeVO []bvos = (ResetypeVO[]) xcbillvo.getChildrenVO();
				// 必输项校验
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
				//唯一性校验
				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vtypecode"}, new String[]{"公司","类型编码"});
			}
			super.onBoSave();
		}
	}
	/**
	 *增行时默认给是否关闭赋值N 
	 */
	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
		int selectRow = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("false", selectRow, "isclose");
	}
}
