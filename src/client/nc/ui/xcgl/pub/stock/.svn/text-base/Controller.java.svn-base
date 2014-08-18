package nc.ui.xcgl.pub.stock;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;

public class Controller extends AbstractManageController implements ISingleController{

	public boolean isSingleDetail() {
		return true;
	}

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Query,
//				IBillButton.Add,
//				IBillButton.Edit,
//				IBillButton.Line,
//				IBillButton.Save,
//				IBillButton.Cancel,
//				IBillButton.Delete,
				IBillButton.Refresh,
//				ZmpubBtnConst.ASSPRINT, //打印管理
//				ZmpubBtnConst.ASSQUERY  //辅助查询
		};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_stockonhand;
	}

	public String[] getBillVoName() {
		return  new String[]{
				XCHYBillVO.class.getName(),
				PubStockOnHandVO.class.getName(),
				PubStockOnHandVO.class.getName()
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
		return "pk_stockonhand";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_stockonhand";
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
//				IBillButton.Add,
//				IBillButton.Edit,
//				IBillButton.Line,
//				IBillButton.Save,
//				IBillButton.Cancel,
//				IBillButton.Delete,
				IBillButton.Refresh,
//				ZmpubBtnConst.ASSPRINT, //打印管理
//				ZmpubBtnConst.ASSQUERY  //辅助查询
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
