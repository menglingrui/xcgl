package nc.ui.xcgl.sample;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.sample.AggSampleVO;
/**
 * 送样单
 * @author yangtao
 * @date 2013-12-10 上午09:16:54
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	
	/**
	 * 增行为样品编码赋值；
	 * @author yangtao
	 * 创建日期：2014-01-06 
	 * 
	 * 此方法暂不使用，改为在后台保存的时候赋样品编码  
	 */
//    @Override
//    protected void onBoLineAdd() throws Exception {
//    	
//    	super.onBoLineAdd();
//    	int row = getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
//    	getBillCardPanel().setBodyValueAt(getSampleNo(), row, "vsamplenumber");
//    	
//    }
//    
   protected void onBoSave() throws Exception {
	   AggSampleVO billvo = (AggSampleVO) getBillUI().getVOFromUI();
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
			//必输项校验
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			//工程项目和作业重复性校验
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"工程项目","作业"});
		}
		else{
			throw new Exception("表体不能为空");
			}
//		SampleHVO headvo=(SampleHVO) billvo.getParentVO();
//		BsUniqueCheck.FieldUniqueCheck(headvo, 
//				new String[]{"pk_billtype","dbilldate","pk_factory","pk_minarea","pk_beltline","pk_classorder","pk_invmandoc"},
//				"单据类型，单据日期，选厂，部门，生产线，班次，存货组合重复");
	   
	   super.onBoSave();
   };
    protected String getSampleNo()throws Exception{
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_SampleNo,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null
		);
	}
    protected String getSampleNoView()throws Exception{
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_SampleNoView,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null
		);
	}
    
       @Override
    protected void onBoEdit() throws Exception {
    	
    	super.onBoEdit();
    }
    String strwhere=null; 
       @Override
    public void onBoQuery() throws Exception {
		StringBuffer strWhere = new StringBuffer();
       
		if (askForQueryCondition(strWhere) == false)
			return;// 用户放弃了查询

		// 支持按表体条件查询
		SuperVO[] queryVos = null;
		try {
			queryVos = queryHeadVOs(strWhere.toString());
		} catch (Exception e) {
			queryVos = queryHeadAndBodyVOs(strWhere.toString());
		}
		getBufferData().clear();
		// 增加数据到Buffer
		addDataToBuffer(queryVos);

		updateBuffer();
		strwhere = strWhere.toString();
	}
       
       @Override
    protected UIDialog getQueryUI() throws Exception {
    	// TODO Auto-generated method stub
    	return super.getQueryUI();
    }
}
