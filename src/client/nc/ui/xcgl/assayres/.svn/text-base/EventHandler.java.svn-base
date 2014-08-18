package nc.ui.xcgl.assayres;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonDefBillManageUI;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.xcgl.assayres.AggAssayResVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 *类说明：化验结果
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-26下午04:47:34
 */
public class EventHandler extends XCFlowManageEventHandler{
	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	protected void onBoSave() throws Exception {
		AggAssayResVO billvo = (AggAssayResVO)getBillUI().getVOFromUI();
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
			//必输项校验
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			//工程项目和作业重复性校验
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"工程项目","作业"});
		}
		else{
			throw new Exception("表体不能为空");
			}
		super.onBoSave();
		super.onBoRefresh();
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.ckmx){
				 quotaQuery();
			 }
		}
		super.onBoElse(intBtn);
	}
//	/**
//	 * 增行为样品编码赋值；
//	 * @author yangtao
//	 * 创建日期：2014-01-06 
//	 */
//    @Override
//    protected void onBoLineAdd() throws Exception {
//    	
//    	super.onBoLineAdd();
//    	int row = getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
//    	getBillCardPanel().setBodyValueAt(getSampleNo(), row, "vsamplenumber");
//    	
//    }
    protected String getSampleNo()throws Exception{
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_SampleNo,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null
		);
	} 
}
