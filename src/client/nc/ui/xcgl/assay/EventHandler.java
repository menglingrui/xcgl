package nc.ui.xcgl.assay;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonDefBillManageUI;
import nc.ui.zmpub.pub.bill.QueryUI;
import nc.ui.zmpub.pub.bill.SonDefBillManageUI;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.xcgl.assay.AggassayVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 *类说明：化验单Eventhandler
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-20上午09:43:39
 */
public class EventHandler extends XCFlowManageEventHandler{
	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	@Override
	protected void onBoSave() throws Exception {
		AggassayVO billvo = (AggassayVO)getBillUI().getVOFromUI();
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
	
	/**
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit() throws Exception {
		super.onBoEdit2();
	
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.ckmx){
				 quotaQuery();
			 }
		}
		switch(intBtn){
		case PuBtnConst.revise:
			onBoEdit1();
			break;
		}
		super.onBoElse(intBtn);
	}
	/**
	 * 暂时没有执行任何操作
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit1() throws Exception {		
		super.onBoEdit1();
		getBillCardPanel().getHeadItem("ureserve10").setValue(true);
	}
	/**
	 * 查看孙表明细
	 * @throws Exception
	 */
	protected void quotaQuery() throws Exception{
		ClientLink cl = new ClientLink(ClientEnvironment.getInstance());
		String corp = PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_corp"));
		SonDefBillManageUI ui = (SonDefBillManageUI) getBillUI();		
		QueryUI qui=new QueryUI(ui.getSonQueryBillType(), cl.getUser(), corp, null, ui, false,getSonQueryDigTile());
		
		
		qui.showModal();
	}
	
	
	
	/**
	 * //参照单据进行制单
	 * yangtao
	 */
	@Override
	public void onBillRef() throws Exception {
		try {
		
			super.onBillRef();
			if (PfUtilClient.isCloseOK()) {
				getBillUI().setBillOperate(IBillOperate.OP_REFADD);
				getBillUI().setDefaultData();
				getButtonManager().getButton(IBillButton.Line).setEnabled(false);
				getBillManageUI().updateButtons();
				getBillCardPanel().setBodyMenuShow(false);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 参照完成之后还原参照按钮tag
			ButtonObject btn = getButtonManager()
					.getButton(IBillButton.Refbill);
			btn.setTag(String.valueOf(IBillButton.Refbill));
		}
	}
	
	/**
	 * 增行为样品编码赋值；
	 * @author yangtao
	 * 创建日期：2014-01-06 
	 */
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
    @Override
    /**
     *删除单据的时候还原是否参照标志 
     */
    protected void onBoDelete() throws Exception {
//    	CircularlyAccessibleValueObject[] bvos = getBufferData().getCurrentVO().getChildrenVO();
//    	String pk_sample=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillid"));
//    	if(pk_sample!=null&&pk_sample.trim()!=""){
//    		 String sql="update xcgl_sample set isref='N'"+ " where pk_sample='"+pk_sample+"'";
//			 XCZmPubDAO.getDAO().executeUpdate(sql); 
//    	}
    	super.onBoDelete();
    }
    @Override
    public void onBoQuery() throws Exception {
    	super.onBoQuery();
    }
	
}
