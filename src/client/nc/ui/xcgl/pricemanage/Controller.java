package nc.ui.xcgl.pricemanage;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pricemanage.AggPriceManageVO;
import nc.vo.xcgl.pricemanage.PriceManageBVO;
import nc.vo.xcgl.pricemanage.PriceManageHVO;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * 价格维护Controller
 * @author ddk
 *
 */
public class Controller extends   AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}
	
	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Add,
				IBillButton.Edit,
//				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
				IBillButton.Return,
				ZmpubBtnConst.ASSPRINT, //打印管理
//				ZmpubBtnConst.ASSQUERY,  
		};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_PriceManage;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggPriceManageVO.class.getName(),
				PriceManageHVO.class.getName(),
				PriceManageBVO.class.getName(),
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
		return "pk_pricemanage_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_pricemanage_h";
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
//				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
				IBillButton.Card,
				ZmpubBtnConst.ASSPRINT, //打印管理
//				ZmpubBtnConst.ASSQUERY,  
			};
	}

	public String[] getListHeadHideCol() {
		return null;
	}

	public boolean isShowListRowNo() {
		return true;
	}

	public boolean isShowListTotal() {
		return false;
	}

}
