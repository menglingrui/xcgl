package nc.ui.xcgl.saleshareout;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonDefBillManageUI;
import nc.ui.zmpub.pub.bill.SonDefBillManageUI;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.saleshareout.AggSaleShareoutVO;
/**
 * ���۳��������̯����eventhandler
 * @author ddk
 *
 */
public class EventHandler extends XCFlowManageEventHandler{
	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
		case PuBtnConst.areashare:
			movAverage();
			break;
		case PuBtnConst.ckmx:
			onBoElse1(intBtn);
		case PuBtnConst.btn_solver:
			onBoElse2(intBtn);
			
		}
	}
	private void onBoElse2(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.btn_solver){
				 solver();
			 }
		}
		super.onBoElse(intBtn);
		
	}
	private void solver() {
		try {
			AggSaleShareoutVO billvo=(AggSaleShareoutVO)getBillUI().getVOFromUI();
			Class[] ParameterTypes = new Class[] { AggSaleShareoutVO.class};
			Object[] ParameterValues = new Object[] {billvo};
			LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"����ִ��...", 1,"nc.bs.xcgl.saleshareout.PlanSolutionBO" , null, "planSolution",
						ParameterTypes, ParameterValues);
			getBillUI().showHintMessage("�滮������");
		} catch (Exception e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(e.getMessage());
		}
	
		
	}
	@Override
	protected void onBoDelete() throws Exception {
		
		super.onBoDelete();
	}
	@Override
	protected void onBoSave() throws Exception {
		AggSaleShareoutVO billvo = (AggSaleShareoutVO)getBillUI().getVOFromUI();
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
	protected void onBoElse1(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.ckmx){
				 quotaQuery();
			 }
		}
		super.onBoElse(intBtn);
	}
	/**
	 * �鿴�����ϸ
	 * @throws Exception
	 */
	protected void quotaQuery() throws Exception{
		ClientLink cl = new ClientLink(ClientEnvironment.getInstance());
		String corp = PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_corp"));
		SonDefBillManageUI ui = (SonDefBillManageUI) getBillUI();		
		QueryUI qui=new QueryUI(PubNodeModelConst.NodeModel_share, cl.getUser(), corp, null, ui, false,"��̯����鿴");
		qui.showModal();
	}
	/**
	 *������̯��ť�¼� 
	 */
    private void movAverage(){
    	//UFDouble ndryweight=getBillListPanel().getSelectedVO(billVOName, headVOName, bodyVOName);
    	try {
    		AggSaleShareoutVO billvo=(AggSaleShareoutVO)getBillUI().getVOFromUI();
    		//Map<String, List<AssayResBVO>> map = findGrade(billvo);
    		ZMEditDlg dig=new ZMEditDlg("XC44",  null,
    				null,  null,  this.getBillUI(),
    				 true);
    		dig.showModal();
    		//dig.setTitle("������̯");
    		//dig.loadHeadData(map);
//			if(billvo!=null){
//				SaleShareoutBVO []bvos=(SaleShareoutBVO[]) billvo.getChildrenVO();
//				if(bvos!=null&&bvos.length!=0){
//					for(int i=0;i<bvos.length;i++){
//						UFDouble ndryweight=PuPubVO.getUFDouble_NullAsZero(bvos[i].getNdryweight());
////						share(ndryweight);
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
     *��̯�����㷨 ��
     *��Ҫ�Ѿ������Ͷ�Ӧ�Ľ����������䵽��Ӧ�Ŀ�����ȥ��ͬʱҲҪ�������ִ�������ȥ
     *���ù滮���
     *�滮��⽫��ֱ�ӻ�����Ŀ�굥Ԫ���й�ʽ�������һ�鵥Ԫ���е���ֵ���е�����������Ŀ�굥Ԫ��ʽ����������Ľ����
     *���ݱ���Ĵ������ȡ�����Ӧ��Ʒζֵ��
     *��������������ȡ�ܲ������־��۵����п����������飬���ĳ�־������п������������������������൱�󣬲���ʵ�֣�
     *���ݾ������ͽ��������������������飬����ų������Ž⼴��
     *ÿ��������̯�ľ������ͽ������ǿ��Ըı�ģ��õ�������ķ�̯�������ִ������еľ������ͽ�����
     *�Ӿ��������ȡ����Ӧ�����µľ������ͽ����������۳��������̯������ȡ��Ҫ��̯�ľ��������������ۻ��鵥��ȡ�����۵�Ʒζ
     *�ɾ��������ͽ�����������������ʽ�������������ʽ�����Ž�
     */
    protected void share(UFDouble ndryweight) {
    	if(ndryweight!=null&&ndryweight.doubleValue()!=0){
    		//��̯�ľ����㷨
    	}
    	else{
    		getBillUI().showHintMessage("����Ϊ0���,���ܷ�̯");
    		return;
    	};
	}
    @Override
    public void onBoAdd(ButtonObject bo) throws Exception {
    	
    	super.onBoAdd(bo);
    }
}
