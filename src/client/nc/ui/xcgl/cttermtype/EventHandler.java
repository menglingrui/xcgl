package nc.ui.xcgl.cttermtype;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 *��˵������ͬ��������EventHandler
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2014-2-12����10:26:34
 */
public class EventHandler extends XCSingleBodyEventHandler{

	public EventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		super.onBoAdd(bo);
		int row=getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTable().getRowCount();
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(),
				row-1,
				"pk_corp");
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
				"root",
				row-1,
				"pk_parent");
		
	}
	@Override
	protected void onBoSave() throws Exception {
		AggregatedValueObject aggvo = getBillUI().getVOFromUI();
		if(aggvo==null){
			return;
		}
		BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		CircularlyAccessibleValueObject[] bvos = aggvo.getChildrenVO();
		if(bvos!=null&&bvos.length>0){
			for(int i=0;i<bvos.length;i++){
				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"termtypecode"},new String[]{"�������ͱ���"});
			}
			
		}
		else{
		  throw new Exception("����Ϊ��!");	
		}
		super.onBoSave();
	}


}
