package nc.ui.xcgl.produceset;
import nc.bs.zmpub.formula.FormulaPath;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.pub.bill.BillModel;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonExtDefBillManageUI;
import nc.ui.zmpub.formula.LoadFormula;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.button.ButtonVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * 生产流程设置
 * @author mlr
 */
public class ClientUI extends XCSonExtDefBillManageUI{

	private static final long serialVersionUID = 6673917648452305655L;
	
	public ClientUI() {
		super();
	}

	@Override
	public boolean isLinkQueryEnable() {
		return false;
	}

	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}

	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}

	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}
	
	@Override
	protected void initSelfData() {
		
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(cl.getCorp());
	}
	
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	public boolean beforeEdit(BillItemEvent e) {
		return true;
	}

	@Override
	public String getQueryDetailBtnName() {
		return "投入明细查看";
	}

	@Override
	public String getSonBillType() {
		return PubNodeModelConst.NodeModel_Produceset_inwrite;
	}

	@Override
	public String getSonDigName() {
		return "produceinset";
	}

	@Override
	public String getSonDigTile() {
		return "投入设置";
	}

	@Override
	public String getSonQueryBillType() {
		return PubNodeModelConst.NodeModel_Produceset_in;
	}
	
	public void SonafterButtonClick(String btg) throws BusinessException {
		
	}

	public void SonbeforeButtonClick(String btg) throws BusinessException {
		
	}
	
	public boolean SonbeforeEdit(BillEditEvent e) {
		
		return true;
	}
	
	public void SonafterEdit(BillEditEvent e) {
		
	}
	
	public void SonbodyRowChange(BillEditEvent e) {
		
	}
	
	//第二种孙表设置start
	public String getQueryDetailBtnName1() {
		return "产出明细查看";
	}

	public String getSonBillType1() {
		return PubNodeModelConst.NodeModel_Producese_outwrite;
	}

	public String getSonDigName1() {
		return "produceoutset";
	}

	public String getSonDigTile1() {
		return "产出设置";
	}

	public String getSonQueryBillType1() {
		return PubNodeModelConst.NodeModel_Produceset_out;
	}
	
	public void SonafterButtonClick1(String btg) throws BusinessException {
		
	}

	public void SonbeforeButtonClick1(String btg) throws BusinessException {
		
	}
	
	public boolean SonbeforeEdit1(BillEditEvent e) {
		String key=e.getKey();
		int row=e.getRow();
		/**
		 * 回收率
		 */
		if (key.equals("vformulaname1")) {
			setEditFormula(getSonBillCardPanel1(),"vformulacode1","vformulaname1",row,"pk_produceset_bb2");
		}
		/**      
		 * 金属产量
		 */
		if (key.equals("vformulaname2")) {
			setEditFormula(getSonBillCardPanel1(),"vformulacode2","vformulaname2",row,"pk_produceset_bb2");
		}
		/**
		 * 精粉产量
		 */
		if (key.equals("vformulaname3")) {
			setEditFormula(getSonBillCardPanel1(),"vformulacode3","vformulaname3",row,"pk_produceset_bb2");
		}
		return true;
	}	
	/**
	 * 编辑前公示框初始化设置
	 * @param panel 
	 * @param vcode
	 * @param vname
	 * @param row
	 * @param pkname
	 */
	public void setEditFormula(BillCardPanel panel, String vcode, String vname, int row,String pkname) {
		String formuDesc = (String)panel.getBillModel()
				.getValueAt(row, vname);
		String formuCode = (String)panel.getBillModel()
				.getValueAt(row, vcode);
		String l=FormulaPath.file1;
		LoadFormula la = new LoadFormula(this, "计算设置公式", formuDesc, formuCode);
		la.showModal();
		panel.getBillModel().setValueAt(la.getFormuDesc(), row,
				vname);
		panel.getBillModel().setValueAt(la.getFormuCode(), row,
				vcode);
		String pk_b = (String) panel.getBillModel().getValueAt(
				row, pkname);
		if (pk_b == null || "".equals(pk_b)) {
			panel.getBillModel().setRowState(row, BillModel.ADD);// 新增状态
		} else {
			panel.getBillModel().setRowState(row,
					BillModel.MODIFICATION);// 修改状态
		}		
	}
	public void SonafterEdit1(BillEditEvent e) {
		
	}
	public void SonbodyRowChange1(BillEditEvent e) {
		
	}
	protected void initPrivateButton() {

		
		
		ButtonVO btnvo61 = new ButtonVO();
		btnvo61.setBtnNo(ZmpubBtnConst.clmx);
		btnvo61.setBtnName(getQueryDetailBtnName());
		btnvo61.setBtnChinaName(getQueryDetailBtnName());
		btnvo61.setBtnCode(null);// code最好设置为空
//		btnvo61.setBusinessStatus(new int[]{IBillStatus.FREE});
		btnvo61.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT,
				IBillOperate.OP_NO_ADDANDEDIT });
		addPrivateButton(btnvo61);
		
		ButtonVO btnvo6 = new ButtonVO();
		btnvo6.setBtnNo(PuBtnConst.ckmx);
		btnvo6.setBtnName(getQueryDetailBtnName1());
		btnvo6.setBtnChinaName(getQueryDetailBtnName1());
		btnvo6.setBtnCode(null);// code最好设置为空
//		btnvo6.setBusinessStatus(new int[]{IBillStatus.FREE});
		btnvo6.setOperateStatus(new int[] { IBillOperate.OP_NOTEDIT,
				IBillOperate.OP_NO_ADDANDEDIT });
		addPrivateButton(btnvo6);

		super.initPrivateButton();
	}
}
