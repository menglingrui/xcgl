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
 *��˵����������
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-26����04:47:34
 */
public class EventHandler extends XCFlowManageEventHandler{
	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	protected void onBoSave() throws Exception {
		AggAssayResVO billvo = (AggAssayResVO)getBillUI().getVOFromUI();
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
			//������У��
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			//������Ŀ����ҵ�ظ���У��
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"������Ŀ","��ҵ"});
		}
		else{
			throw new Exception("���岻��Ϊ��");
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
//	 * ����Ϊ��Ʒ���븳ֵ��
//	 * @author yangtao
//	 * �������ڣ�2014-01-06 
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