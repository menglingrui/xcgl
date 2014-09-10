package nc.ui.xcgl.saleassay;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.base.IBillOperate;
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
 * 销售化验单UI
 * @author ddk
 *
 */
public class ClientUI extends XCDefBillManageUI{
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_saleassay);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_saleassay, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}

//	@Override
//	public String getQueryDetailBtnName() {
//		return "化验指标查看";
//	}

//	@Override
//	public String getSonBillType() {
//		return PubBillTypeConst.billtype_saleindexentry;
//	}

//	@Override
//	public String getSonDigName() {
//		return "vindexentry";
//	}

//	@Override
//	public String getSonDigTile() {
//		return "指标录入";
//	}

//	@Override
//	public String getSonQueryBillType() {
//		return PubNodeModelConst.NodeModel_saleindexentryview;
//	}

	public boolean beforeEdit(BillItemEvent e) {
		return true;
	}

	public void SonafterButtonClick(String btg) throws BusinessException {
	}

	public void SonbeforeButtonClick(String btg) throws BusinessException {
	}
//	protected void initPrivateButton() {
//		ButtonVO btnvo1 = new ButtonVO();
//		btnvo1.setBtnNo(PuBtnConst.ckmx);
//		btnvo1.setBtnName(getQueryDetailBtnName());
//		btnvo1.setBtnChinaName(getQueryDetailBtnName());
//		btnvo1.setBtnCode(null);//code最好设置为空
//		addPrivateButton(btnvo1);
//		super.initPrivateButton();
//	}
	
	@Override
	  public String getRefBillType(){
	        return PubBillTypeConst.billtype_salesample;
	}
	
//	@Override
//	public List<SuperVO> beforeEditSetSonDatas(List<SuperVO> nlist, int row) {
//		String vindexentry = (String) getBillCardPanel().getBodyValueAt(row,
//				"vindexentry");
//		String Pk_invmandoc = (String) getBillCardPanel().getBodyValueAt(row,
//				"pk_invmandoc");
//		String pk_minarea = (String) getBillCardPanel().getHeadItem(
//				"pk_minarea").getValueObject();
//		if (vindexentry != null && vindexentry.trim().equals("Y")
//				|| Pk_invmandoc == null || pk_minarea == null) {
//			return null;
//		} else {
//			List list = new ArrayList();
//			try {
//				IndexParaVO para = new IndexParaVO();
//				para.setPk_corp((String) getBillCardPanel().getHeadItem(
//						"pk_corp").getValueObject());
//				para.setPk_invmandoc((String) getBillCardPanel()
//						.getBodyValueAt(row, "pk_invmandoc"));
//				para.setPk_minarea((String) getBillCardPanel().getHeadItem(
//						"pk_minarea").getValueObject());
//				list = (List) IndexFineHeler.getIndexFine(para);
//			} catch (BusinessException e) {
//				e.printStackTrace();
//			}
//			return list;
//		}
//	}
	protected void initPrivateButton() {
		
		ButtonVO btnvo3 = new ButtonVO();
		btnvo3.setBtnNo(PuBtnConst.revise);
		btnvo3.setBtnName("修订");
		btnvo3.setBtnChinaName("修订");
		btnvo3.setBtnCode(null);//code最好设置为空
		btnvo3.setOperateStatus(new int[]{
				IBillOperate.OP_NOTEDIT,
				});
		btnvo3.setBusinessStatus(new int[]{
				IBillStatus.CHECKPASS,
		});
		addPrivateButton(btnvo3);
		
		super.initPrivateButton();
	}

	@Override
	public boolean isStockBill() {
		
		return false;
	}
}
