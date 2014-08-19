package nc.ui.xcgl.cttermset;

import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.treecard.ITreeCardController;
import nc.vo.xcgl.cttermset.CtTermsetVO;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 *类说明：合同条款Controller
 *@author jay
 *@version 1.0   
 *创建时间：2014-2-17下午04:03:26
 */
public class Controller  implements ITreeCardController,ISingleController{

	public boolean isAutoManageTree() {
		
		return true;
	}

	public boolean isChildTree() {
		
		return false;
	}

	public boolean isTableTree() {
		
		return false;
	}

	public String[] getCardBodyHideCol() {
		
		return null;
	}

	public int[] getCardButtonAry() {
		

		return new int[]{
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Delete,
				IBillButton.Refresh,
//				IBillButton.Return,
			};
	}

	public boolean isShowCardRowNo() {
		
		return false;
	}

	public boolean isShowCardTotal() {
		
		return false;
	}

	public String getBillType() {
		
		return PubBillTypeConst.billtype_cttermset;
	}

	public String[] getBillVoName() {
		
		return new String[]{
				XCHYBillVO.class.getName(),
				CtTermsetVO .class.getName(),
				CtTermsetVO .class.getName(),
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
		
		return null;
	}

	public String getHeadZYXKey() {
		
		return null;
	}

	public String getPkField() {
		
		return "pk_cttermset";
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

	public boolean isSingleDetail() {
		
		return false;
	}


}
