package nc.ui.xcgl.teamgroup;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.teamgroup.AggTeamGroupVO;
import nc.vo.xcgl.teamgroup.TeamGroupBVO;

public class EventHandler extends ManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected void onBoSave() throws Exception {
		AggTeamGroupVO billvo=(AggTeamGroupVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			TeamGroupBVO []bvos=(TeamGroupBVO[])billvo.getChildrenVO();
			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vpsncode"}, new String[]{"公司","人员编码"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
	
	
}
