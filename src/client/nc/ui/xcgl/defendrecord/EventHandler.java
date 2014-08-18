package nc.ui.xcgl.defendrecord;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.defendrecord.AggDefendrecordVO;
import nc.vo.xcgl.defendrecord.DefendrecordBVO;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	protected void onBoSave() throws Exception {
		AggDefendrecordVO billvo=(AggDefendrecordVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		//������У��
		BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		DefendrecordBVO []bvos=(DefendrecordBVO[])billvo.getChildrenVO();
		if(bvos!=null&&bvos.length>0){
			//У�������豸���벻�ظ�
			BeforeSaveValudate.FieldBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTable(), 
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTableModel(), "equipcode", "�豸����");
			//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"equipcode"}, new String[]{"�豸����"});
		}
		else
			throw new Exception("���岻��Ϊ��");
		super.onBoSave();
	}
	
	
}
