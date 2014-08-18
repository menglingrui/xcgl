package nc.ui.xcgl.asssy;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 *类说明：化验单UI
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-20上午09:42:51
 */
public class ClientUI extends XCSonDefBillManageUI{
	
	private static final long serialVersionUID = 1L;

	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}

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
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_assay);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_assay, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}

	@Override
	public String getQueryDetailBtnName() {
		
		return null;
	}

	@Override
	public String getSonBillType() {
		
		return "PubBillTypeConsts.billtype_vindexentry";
	}

	@Override
	public String getSonDigName() {
		
		return "vindexentry";
	}

	@Override
	public String getSonDigTile() {
		
		return "指标录入";
	}

	@Override
	public String getSonQueryBillType() {
		
		return "PubBillTypeConsts.billtype_vindexentry";
	}

	public boolean beforeEdit(BillItemEvent e) {
		
		return false;
	}

	public void SonafterButtonClick(String btg) throws BusinessException {
	}

	public void SonbeforeButtonClick(String btg) throws BusinessException {
	}
}
