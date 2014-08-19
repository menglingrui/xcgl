package nc.ui.xcgl.labindexset;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.labindexset.AggLabIndexSetVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.labindexset.LabIndexSetHVO;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;

public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}
	
	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Add,
				IBillButton.Copy,
				IBillButton.Edit,
				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
				IBillButton.Return,
		};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_Labindexset;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggLabIndexSetVO.class.getName(),
				LabIndexSetHVO.class.getName(),
				LabIndexSetBVO.class.getName(),
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
		return "pk_labindexset_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_labindexset_h";
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



	public String[] getListHeadHideCol() {
		return null;
	}
	public boolean isShowListRowNo() {
		return false;
	}
	/**
	 * 列表是否显示合计行
	 */
	public boolean isShowListTotal() {
		return true;
	}
	

	public int[] getListButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Add,
				IBillButton.Copy,
				IBillButton.Edit,
				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
				IBillButton.Card,
			};
	}


}
