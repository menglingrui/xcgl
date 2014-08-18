package nc.ui.xcgl.saleassayres;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 *类说明：化验结果UI
 *@author ddk
 */
public class ClientUI extends XCDefBillManageUI{
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}
	
	public ClientUI() {
		super();
	}
	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
//	@Override
//	public String getQueryDetailBtnName() {
//		return "化验指标查看";
//	}
//
//	@Override
//	public String getSonBillType() {
//		return PubBillTypeConst.billtype_saleassayquota;
//	}
//
//	@Override
//	public String getSonDigName() {
//		return "assayquota";
//	}
//
//	@Override
//	public String getSonDigTile() {
//		return "指标定义";
//	}
//
//	@Override
//	public String getSonQueryBillType() {
//		return PubBillTypeConst.billtype_saleassayquotaview;
//	}

	public boolean beforeEdit(BillItemEvent e) {
		return false;
	}

	public void SonafterButtonClick(String btg) throws BusinessException {
	}

	public void SonbeforeButtonClick(String btg) throws BusinessException {
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_saleassayres);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_saleassayres, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
//	protected void initPrivateButton() {
//		ButtonVO btnvo1 = new ButtonVO();
//		btnvo1.setBtnNo(PuBtnConst.ckmx);
//		btnvo1.setBtnName(getQueryDetailBtnName());
//		btnvo1.setBtnChinaName(getQueryDetailBtnName());
//		btnvo1.setBtnCode(null);//code最好设置为空
//		addPrivateButton(btnvo1);
//		super.initPrivateButton();
//		
//	
//	}
	
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
//		System.out.println("");
//		if(this.isListPanelSelected()){
//			int RowCount =this.getBillListPanel().getBodyTable().getRowCount();
//			if(RowCount>0){
//				for(int i=0;i<RowCount;i++){
//					String sampleno=(String)this.getBillListPanel().getBodyBillModel().getValueAt(i, "vsamplenumber");
//					if(sampleno.length()==32){
//						 sampleno=XcPubTool.decrypt(sampleno);
//					}
//					this.getBillListPanel().getBodyBillModel().setValueAt(sampleno, i, "vsamplenumber");
//				}
//			}
//		}else{
//			int RowCount =this.getBillCardPanel().getBodyPanel().getTable().getRowCount();
//			if(RowCount>0){
//				for(int i=0;i<RowCount;i++){
//					String sampleno=(String)this.getBillCardPanel().getBodyValueAt(i, "vsamplenumber");
//					if(sampleno.length()==32){
//					sampleno=XcPubTool.decrypt(sampleno);
//					}
//					this.getBillCardPanel().setBodyValueAt(sampleno, i, "vsamplenumber");
//				}
//			}
//		}
	}
	@Override
	public boolean isStockBill() {
		
		return false;
	}
	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
	}
	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}
}
