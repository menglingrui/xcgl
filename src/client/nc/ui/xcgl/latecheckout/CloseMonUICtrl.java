package nc.ui.xcgl.latecheckout;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.vo.xcgl.latecheckout.CloseMonBodyVO;
import nc.vo.xcgl.latecheckout.CloseMonHeaderVO;
import nc.vo.xcgl.latecheckout.CloseMonVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

public class CloseMonUICtrl  implements ICardController {

	
	public String[] getCardBodyHideCol() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				PuBtnConst.btn_closemon,
				PuBtnConst.btn_disclosemon,
//				IBillButton.Refresh,
				PuBtnConst.btn_refresh
		};
	}

	public boolean isShowCardRowNo() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isShowCardTotal() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getBillType() {
		return PubBillTypeConst.billtype_latecheckout;
	}

	public String[] getBillVoName() {
		return new String[]{
				CloseMonVO.class.getName(),
				CloseMonHeaderVO.class.getName(),
				CloseMonBodyVO.class.getName()
		};
	}

	public String getBodyCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBodyZYXKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	public String getChildPkField() {
		return "pk_closemonb";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_closemon";
	}

	public Boolean isEditInGoing() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isExistBillStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLoadCardFormula() {
		// TODO Auto-generated method stub
		return false;
	}

}
