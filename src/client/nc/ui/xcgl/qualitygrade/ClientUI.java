package nc.ui.xcgl.qualitygrade;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.ui.zmpub.formula.LoadFormula;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;


/**
 * 优质优价—品位UI
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class ClientUI extends XCDefBillManageUI{

	@Override
	public boolean isLinkQueryEnable() {
		return true;
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
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getTailItem("voperatorid").setValue(
				ClientEnvironment.getInstance().getUser().getPrimaryKey());
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_qualitygrade);
		getBillCardPanel().getHeadItem("vtype").setValue(1);
	}
	
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_qualitygrade,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		
		String key=e.getKey();
		int row=e.getRow();
		if (key.equals("vformula")) {
			setEditFormula(getBillCardPanel(),"vformulacode","vformula",row,"pk_qualitygrade_b");
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
	
	public String getRefBillType(){
        return null;
    }
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		int pos  = e.getPos();
		if(BillItem.HEAD == pos){
			 String key = e.getKey();
			 if("pk_invmandoc".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
			 }
		}
		if("dstartdate".equals(e.getKey())){
			String end=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("denddate").getValueObject());
			if(end!=null){
				String start=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("dstartdate").getValueObject());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
				ParsePosition pos1 = new ParsePosition(0);
				ParsePosition pos2 = new ParsePosition(0);
				Date dt1=formatter.parse(start,pos1);
				Date dt2=formatter.parse(end,pos2);
				double l = dt2.getTime() - dt1.getTime();
				if(l<0.0){
					this.showErrorMessage("生效日期不能大于失效日期");
					return;
				}
			}
		}
		if("denddate".equals(e.getKey())){
			String start=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("dstartdate").getValueObject());
			if(start==null){
				this.showErrorMessage("生效日期为空，请输入生效日期");
				return;
			}else{
				String end=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("denddate").getValueObject());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
				ParsePosition pos1 = new ParsePosition(0);
				ParsePosition pos2 = new ParsePosition(0);
				Date dt1=formatter.parse(start,pos1);
				Date dt2=formatter.parse(end,pos2);
				double l = dt2.getTime() - dt1.getTime();
				if(l<0.0){
					this.showErrorMessage("生效日期不能大于失效日期");
					return;
				}
			}
		}
	}
	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}
}
