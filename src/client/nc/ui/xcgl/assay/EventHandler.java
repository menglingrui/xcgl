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
 *��˵�������鵥Eventhandler
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-20����09:43:39
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
			//������У��
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
//			//������Ŀ����ҵ�ظ���У��
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"������Ŀ","��ҵ"});
		}
		else
			throw new Exception("���岻��Ϊ��");
		super.onBoSave();
		super.onBoRefresh();
	}
	
	/**
	 * ��ťm_boEdit���ʱִ�еĶ���,���б�Ҫ���븲��.
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
	 * ��ʱû��ִ���κβ���
	 * ��ťm_boEdit���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	protected void onBoEdit1() throws Exception {		
		super.onBoEdit1();
		getBillCardPanel().getHeadItem("ureserve10").setValue(true);
	}
	/**
	 * �鿴�����ϸ
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
	 * //���յ��ݽ����Ƶ�
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
			// �������֮��ԭ���հ�ťtag
			ButtonObject btn = getButtonManager()
					.getButton(IBillButton.Refbill);
			btn.setTag(String.valueOf(IBillButton.Refbill));
		}
	}
	
	/**
	 * ����Ϊ��Ʒ���븳ֵ��
	 * @author yangtao
	 * �������ڣ�2014-01-06 
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
     *ɾ�����ݵ�ʱ��ԭ�Ƿ���ձ�־ 
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
