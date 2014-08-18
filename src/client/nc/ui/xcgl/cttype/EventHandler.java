package nc.ui.xcgl.cttype;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 *类说明：合同类型EventHandler
 *@author jay
 *@version 1.0   
 *创建时间：2014-2-13下午04:10:37
 */
public class EventHandler extends XCSingleBodyEventHandler{

	public EventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
		
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
				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"typecode"},new String[]{"类型编码"});
			}
		}
		else{
		  throw new Exception("数据为空!");	
		}
		super.onBoSave();
	}

}
