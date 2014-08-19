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
 * 销售出库矿区分摊计算eventhandler
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
						"正在执行...", 1,"nc.bs.xcgl.saleshareout.PlanSolutionBO" , null, "planSolution",
						ParameterTypes, ParameterValues);
			getBillUI().showHintMessage("规划求解结束");
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
	protected void onBoElse1(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.ckmx){
				 quotaQuery();
			 }
		}
		super.onBoElse(intBtn);
	}
	/**
	 * 查看孙表明细
	 * @throws Exception
	 */
	protected void quotaQuery() throws Exception{
		ClientLink cl = new ClientLink(ClientEnvironment.getInstance());
		String corp = PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_corp"));
		SonDefBillManageUI ui = (SonDefBillManageUI) getBillUI();		
		QueryUI qui=new QueryUI(PubNodeModelConst.NodeModel_share, cl.getUser(), corp, null, ui, false,"分摊结果查看");
		qui.showModal();
	}
	/**
	 *矿区分摊按钮事件 
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
    		//dig.setTitle("矿区分摊");
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
     *分摊核心算法 ：
     *需要把精粉量和对应的金属量都分配到相应的矿区中去，同时也要关联到现存量当中去
     *采用规划求解
     *规划求解将对直接或间接与目标单元格中公式相关联的一组单元格中的数值进行调整，最终在目标单元格公式中求得期望的结果。
     *根据表体的存货类型取到相对应的品味值，
     *矿区待定（可以取能产生这种精粉的算有矿区，不建议，如果某种精粉所有矿区都能生产，计算量将会相当大，不易实现）
     *根据精粉量和金属量会生成两个方程组，求出放出的最优解即可
     *每个矿区分摊的精粉量和金属量是可以改变的，得到最理想的分摊总量即现存量当中的精粉量和金属量
     *从精粉入库中取到相应矿区下的精粉量和金属量，销售出库矿区分摊计算中取到要分摊的精粉数量，在销售化验单中取到精粉的品味
     *由精粉数量和金属量建立起两个等式，求解这两个等式的最优解
     */
    protected void share(UFDouble ndryweight) {
    	if(ndryweight!=null&&ndryweight.doubleValue()!=0){
    		//分摊的具体算法
    	}
    	else{
    		getBillUI().showHintMessage("干重为0或空,不能分摊");
    		return;
    	};
	}
    @Override
    public void onBoAdd(ButtonObject bo) throws Exception {
    	
    	super.onBoAdd(bo);
    }
}
