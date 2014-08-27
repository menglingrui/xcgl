package nc.ui.xcgl.closeaccount;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.closeaccount.AggCloseAccountVO;
import nc.vo.xcgl.closeaccount.CloseAccountBVO;
import nc.vo.xcgl.closeaccount.CloseAccountHVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;

public class Controller extends AbstractManageController{

	public boolean isSingleDetail() {
		return true;
	}

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Delete,
				IBillButton.Cancel,
				PuBtnConst.open,
				PuBtnConst.close,
				IBillButton.Print,
		};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return null;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggCloseAccountVO.class.getName(),
				CloseAccountHVO.class.getName(),
				CloseAccountBVO.class.getName()
		};
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	public String getChildPkField() {
		return "pk_closeaccount_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_closeaccount";
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isExistBillStatus() {
		return false;
	}

	public boolean isLoadCardFormula() {
		return true;
	}

	public String[] getListBodyHideCol() {
		return null;
	}

	public int[] getListButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Delete,
				IBillButton.Cancel,
				PuBtnConst.open,
				PuBtnConst.close,
				IBillButton.Print,
		};
	}

	public String[] getListHeadHideCol() {
		return null;
	}

	public boolean isShowListRowNo() {
		return true;
	}

	public boolean isShowListTotal() {
		return true;
	}

}
