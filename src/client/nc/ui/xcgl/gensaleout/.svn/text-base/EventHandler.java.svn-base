package nc.ui.xcgl.gensaleout;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.gensaleout.AggSaleOutVO;

/**
 *类说明：销售出库
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-16下午02:36:51
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
		
	}
	@Override
	protected void onBoSave() throws Exception {
		AggSaleOutVO billvo = (AggSaleOutVO)getBillUI().getVOFromUI();
		if(billvo==null)//not null check
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
			//必输项校验
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
//			//工程项目和作业重复性校验
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"工程项目","作业"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
		super.onBoRefresh();
	}

}
