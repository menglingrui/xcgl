package nc.ui.xcgl.defendrecord;



import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
/**
 * 设备维护记录
 * @author lxh
 */
@SuppressWarnings("serial")
public class ClientUI extends XCDefBillManageUI implements BillCardBeforeEditListener { 
	

	protected void initEventListener() {
		super.initEventListener();
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	}
	
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
	protected void initSelfData() {
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
		super.initSelfData();
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.biltype_defendrecord);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.biltype_defendrecord, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	public boolean beforeEdit(BillItemEvent e) {
		String key=e.getItem().getKey();
		if("shifttime".equals(key)){
			this.showHintMessage("时间格式为:HH:mm:ss");
		}
		return true;
	}
	@Override
	/**
	 *判断表体的维护时间字段和下次维护时间字段输入的格式是否正确
	 *维护时间字段主键：defendtime
	 *下次维护时间字段主键： nextdefendtime
	 *表头的搅拌时间字段主键：shifttime
	 */
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
//		if(e.getPos()==IBillItem.BODY&&
//				(e.getKey().equalsIgnoreCase("defendtime")||e.getKey().equalsIgnoreCase("nextdefendtime"))){
//			
//			
//		}
//		else if (e.getPos()==IBillItem.HEAD&&e.getKey().equalsIgnoreCase("shifttime")){
//			
//		}
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String key=e.getKey();
		if("defendtime".equals(key)||"nextdefendtime".equals(key)){
			this.showHintMessage("输入日期格式为：yyyy-MM-dd HH:mm:ss");
		}
		return super.beforeEdit(e);
	}
	@Override
	public boolean isStockBill() {
		return false;
	}
}
