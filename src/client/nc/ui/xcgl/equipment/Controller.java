package nc.ui.xcgl.equipment;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.equipment.EquipMentVO;
/**
 * �豸����
 * @author lxh
 */
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;

public class Controller extends   AbstractManageController   implements ISingleController{
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
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
		};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_Equipment;
	}

	public String[] getBillVoName() {
		return new String[]{
				XCHYBillVO.class.getName(),
				EquipMentVO.class.getName(),
				EquipMentVO.class.getName(),
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
		return "pk_equipment";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_equipment";
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
	/**
	 * ��ʾ�к�
	 */
	public boolean isShowListRowNo() {
		return true;
	}
	/**
	 * �б��Ƿ���ʾ�ϼ���
	 */
	public boolean isShowListTotal() {
		return true;
	}
	/**
	 * �������ж�
	 */
	public boolean isSingleDetail() {
		return true;
	}

	public int[] getListButtonAry() {
		return new int[]{
				IBillButton.Query,
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
			};
	}

}

