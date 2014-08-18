package nc.ui.xcgl.runrecord;


import java.util.*;
import java.text.*; 

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.ui.xcgl.runrecord.Controller;
import nc.ui.xcgl.runrecord.EventHandler;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.runrecord.RunrecordBVO;
/**
 * 设备运行记录
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_runrecord);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_runrecord, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
	}
	public boolean beforeEdit(BillItemEvent e) {
		String key = e.getItem().getKey();
		String xuanchang = (String) getBillCardPanel().getHeadItem(
		"pk_beltline").getValueObject();
		if (key.equals("pk_factory")) {
			if(xuanchang==null||xuanchang.length()==0){
			
				this.showWarningMessage("");
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String key=e.getKey();
		if("boottime".equals(key)||"offtime".equals(key)){
			this.showHintMessage("输入日期格式为：yyyy-MM-dd HH:mm:ss");
		}
		return super.beforeEdit(e);
	}
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		
		int p  = e.getPos();
		
		if(BillItem.HEAD == p){
			 String key = e.getKey();
			 
			 if("pk_beltline".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
			 }
		}
		
		
		
		String sItemKey = e.getKey();
		//开机时间、关机时间校验
		if (sItemKey.equals("boottime")||sItemKey.equals("offtime")) {
			CircularlyAccessibleValueObject[] bvos = getBillCardPanel().getBillModel().getBodyValueVOs(
							"nc.vo.xcgl.runrecord.RunrecordBVO");
			for (int i = 0; i < bvos.length; i++) {
				int a = e.getRow();
				if (a == i) {
					RunrecordBVO bvo = (RunrecordBVO) bvos[i];
					UFDateTime boottime = bvo.getBoottime();
					UFDateTime offtime = bvo.getOfftime();
					if (boottime != null && offtime != null) {
						String s1 = boottime.toString();
						String s2 = offtime.toString();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						ParsePosition pos = new ParsePosition(0);
						ParsePosition pos1 = new ParsePosition(0);
						Date dt1 = formatter.parse(s1, pos);
						Date dt2 = formatter.parse(s2, pos1);
						long l = dt2.getTime() - dt1.getTime();
						if(l<0){
							this.showErrorMessage("关机时间不能早于开机时间！");
							double x = (double) l;
							double y = x / 3600000;
							getBillCardPanel().getBillModel().setValueAt(y, i,"nduration");
						}else{
							double x = (double) l;
							double y = x / 3600000;
							getBillCardPanel().getBillModel().setValueAt(y, i,"nduration");
						}
					}
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
