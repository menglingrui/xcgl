package nc.ui.xcgl.qualitypro;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCMutiDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 * 优质优价方案UI
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class ClientUI extends XCMutiDefBillManageUI{

	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}
	
	public ClientUI() {
		super();
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
		//生效日期编辑后事件
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
					try {
						throw new BusinessException("生效日期不能小于失效日期");
					} catch (BusinessException e1) {
						nc.ui.pub.beans.MessageDialog.showHintDlg(this, "提示",e1.toString());
					}
				}
			}
		}
		//失效日期编辑后事件
		if("denddate".equals(e.getKey())){
			String start=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("dstartdate").getValueObject());
			if(start==null){
				try {
					throw new BusinessException("生效日期为空，请输入生效日期");
				} catch (BusinessException e1) {
					nc.ui.pub.beans.MessageDialog.showHintDlg(this, "提示",e1.toString());
				}
			}else{
				String end=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("denddate").getValueObject());
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
				ParsePosition pos1 = new ParsePosition(0);
				ParsePosition pos2 = new ParsePosition(0);
				Date dt1=formatter.parse(start,pos1);
				Date dt2=formatter.parse(end,pos2);
				double l = dt2.getTime() - dt1.getTime();
				if(l<0.0){
					try {
						throw new BusinessException("生效日期不能小于失效日期");
					} catch (BusinessException e1) {
						nc.ui.pub.beans.MessageDialog.showHintDlg(this, "提示",e1.toString());
					}
				}
			}
		}
	}
	
	@Override
	protected void initSelfData() {
		//除去行操作多余按钮
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
	}
	/**
	 * 前台界面的业务委托类
	 */
	protected BusinessDelegator createBusinessDelegator() {
		return new ProAcceptDelegator();
	}
	
	
	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
	
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_qualitypro);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_qualitypro,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}

	@Override
	public boolean beforeEdit(BillEditEvent e) {
		return true;
	}
	
	 public String getRefBillType(){
		 return null;
	 }
}
