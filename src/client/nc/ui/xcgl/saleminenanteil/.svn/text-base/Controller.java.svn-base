package nc.ui.xcgl.saleminenanteil;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.saleminenanteil.AggSaleminenanteilVO;
import nc.vo.xcgl.saleminenanteil.SaleminenanteilBVO;
import nc.vo.xcgl.saleminenanteil.SaleminenanteilHVO;
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
				IBillButton.Brow,
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
		return PubNodeModelConst.NodeModel_saleminenanteil;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggSaleminenanteilVO.class.getName(),
				SaleminenanteilHVO.class.getName(),
				SaleminenanteilBVO.class.getName(),
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
		return "pk_saleminenanteil_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_saleminenanteil_h";
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
				IBillButton.Line,
				IBillButton.Save,
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
