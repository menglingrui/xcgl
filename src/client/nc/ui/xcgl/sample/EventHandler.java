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
 * ������
 * @author yangtao
 * @date 2013-12-10 ����09:16:54
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	
	/**
	 * ����Ϊ��Ʒ���븳ֵ��
	 * @author yangtao
	 * �������ڣ�2014-01-06 
	 * 
	 * �˷����ݲ�ʹ�ã���Ϊ�ں�̨�����ʱ����Ʒ����  
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
			//������У��
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			//������Ŀ����ҵ�ظ���У��
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"������Ŀ","��ҵ"});
		}
		else{
			throw new Exception("���岻��Ϊ��");
			}
//		SampleHVO headvo=(SampleHVO) billvo.getParentVO();
//		BsUniqueCheck.FieldUniqueCheck(headvo, 
//				new String[]{"pk_billtype","dbilldate","pk_factory","pk_minarea","pk_beltline","pk_classorder","pk_invmandoc"},
//				"�������ͣ��������ڣ�ѡ�������ţ������ߣ���Σ��������ظ�");
	   
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
			return;// �û������˲�ѯ

		// ֧�ְ�����������ѯ
		SuperVO[] queryVos = null;
		try {
			queryVos = queryHeadVOs(strWhere.toString());
		} catch (Exception e) {
			queryVos = queryHeadAndBodyVOs(strWhere.toString());
		}
		getBufferData().clear();
		// �������ݵ�Buffer
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
