package nc.ui.xcgl.saleshareout;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCSonDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;

/**
 * 销售出库矿区分摊计算UI
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class ClientUI extends XCSonDefBillManageUI{


	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}

	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
	
	@Override
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this,getUIControl());
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
	protected void initPrivateButton() {
		//modify by jay
		//取消规划求解按钮 ClientUI 和controller
//		ButtonVO btnvo3 = new ButtonVO();
//		btnvo3.setBtnNo(PuBtnConst.btn_solver);
//		btnvo3.setBtnName("规划求解");
//		btnvo3.setBtnChinaName("规划求解");
//		btnvo3.setBtnCode(null);
//		btnvo3.setBusinessStatus(new int[]{IBillStatus.FREE});
//		btnvo3.setOperateStatus(new int[]{IBillOperate.OP_INIT,IBillOperate.OP_NO_ADDANDEDIT});
//		addPrivateButton(btnvo3);
		
		ButtonVO btnvo1 = new ButtonVO();
		btnvo1.setBtnNo(PuBtnConst.areashare);
		btnvo1.setBtnName("矿区分摊");
		btnvo1.setBtnChinaName("矿区分摊");
		btnvo1.setBtnCode(null);
		btnvo1.setBusinessStatus(new int[]{IBillStatus.FREE});
		btnvo1.setOperateStatus(new int[]{IBillOperate.OP_INIT,IBillOperate.OP_NO_ADDANDEDIT});
		addPrivateButton(btnvo1);
		
		ButtonVO btnvo2 = new ButtonVO();
		btnvo2.setBtnNo(PuBtnConst.ckmx);
		btnvo2.setBtnName("分摊结果查看");
		btnvo2.setBtnChinaName("分摊结果查看");
		btnvo2.setBtnCode(null);
		//btnvo1.setBusinessStatus(new int[]{IBillStatus.FREE});
		btnvo1.setOperateStatus(new int[]{IBillOperate.OP_INIT,IBillOperate.OP_NO_ADDANDEDIT});
		addPrivateButton(btnvo2);
		super.initPrivateButton();
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
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_saleshareout);
	}
	
	@Override
	protected String getBillNo() throws java.lang.Exception {
		return HYPubBO_Client.getBillNo(PubNodeModelConst.NodeModel_saleshareout,
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}

	@Override
	public String getQueryDetailBtnName() {
		
		return "分摊结果查看";
	}

	@Override
	public String getSonBillType() {
		
		return PubBillTypeConst.billtype_minareashare;
	}

	@Override
	public String getSonDigName() {
		
		return "share";
	}

	@Override
	public String getSonDigTile() {
		
		return "矿区分摊";
	}

	@Override
	public String getSonQueryBillType() {
		
		return PubNodeModelConst.NodeNodel_minareashareview;
	}

	public boolean beforeEdit(BillItemEvent e) {
		
		return true;
	}

	@Override
	  public String getRefBillType(){
	        return PubBillTypeConst.billtype_saleweighdoc;
	    }
	public void SonafterButtonClick(String btg) throws BusinessException {
	}

	public void SonbeforeButtonClick(String btg) throws BusinessException {
	}

//	@Override
//	public boolean isStockBill() {
//		
//		return false;
//	}

}
