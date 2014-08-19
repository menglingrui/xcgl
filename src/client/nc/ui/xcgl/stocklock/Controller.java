package nc.ui.xcgl.stocklock;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.stocklock.AggStocklockVO;
import nc.vo.xcgl.stocklock.StocklockBVO;
import nc.vo.xcgl.stocklock.StocklockHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

public class Controller extends   AbstractManageController{


	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Copy,       //¸´ÖÆ
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,	
				IBillButton.Commit,
				IBillButton.Action,	
				IBillButton.Delete,			
				IBillButton.Refresh,			 
				IBillButton.Return,
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY,
				};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_stocklock;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggStocklockVO.class.getName(),
				StocklockHVO.class.getName(),
				StocklockBVO.class.getName(),
		};
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.PLATFORM;
	}

	public String getChildPkField() {
		return "pk_stocklock_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_stocklock_h";
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isExistBillStatus() {
		return true;
	}

	public boolean isLoadCardFormula() {
		return true;
	}

	public String[] getListBodyHideCol() {
		return null;
	}

	public int[] getListButtonAry() {
		return new int[]{
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Copy,       //¸´ÖÆ
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,	
				IBillButton.Commit,
				IBillButton.Action,	
				IBillButton.Delete,			
				IBillButton.Refresh,
				IBillButton.Card,
				ZmpubBtnConst.ASSPRINT,
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
