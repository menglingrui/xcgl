package nc.ui.xcgl.email;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.emaill.AggEmaillVO;
import nc.vo.xcgl.emaill.EmaillHVO;

public class Controller  extends AbstractManageController{

	public String[] getCardBodyHideCol() {
	
		return null;
	}

	public int[] getCardButtonAry() {
		
		return new int[]{
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Return,
		};
	}

	public boolean isShowCardRowNo() {
	
		return false;
	}

	public boolean isShowCardTotal() {
		
		return false;
	}

	public String getBillType() {
		
		return "XC71";
	}

	public String[] getBillVoName() {
		
		return new String[]{
				AggEmaillVO.class.getName(),
				EmaillHVO.class.getName(),
				EmaillHVO.class.getName()
				};
		
	}

	public String getBodyCondition() {
		
		return null;
	}

	public String getBodyZYXKey() {
		
		return null;
	}

	public int getBusinessActionType() {
	
		return 0;
	}

	public String getChildPkField() {
		
		return null;
	}

	public String getHeadZYXKey() {
	
		return null;
	}

	public String getPkField() {
	
		return null;
	}

	public Boolean isEditInGoing() throws Exception {
		
		return null;
	}

	public boolean isExistBillStatus() {
	
		return false;
	}

	public boolean isLoadCardFormula() {
	
		return false;
	}

	public String[] getListBodyHideCol() {

		return null;
	}

	public int[] getListButtonAry() {

		return new int[]{
				IBillButton.Edit,
				IBillButton.Query,
		};
	}

	public String[] getListHeadHideCol() {
		
		return null;
	}

	public boolean isShowListRowNo() {
	
		return false;
	}

	public boolean isShowListTotal() {
	
		return false;
	}

}
