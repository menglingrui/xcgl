package nc.ui.xcgl.salesettle;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;
import nc.vo.xcgl.salepresettle.SalepresettleHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

public class Controller extends   AbstractManageController{


	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Busitype,    //参照
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Copy,       //复制
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,	
				IBillButton.Commit,
				IBillButton.Action,	
				IBillButton.Delete,			
				IBillButton.Refresh,			 
				IBillButton.Return,
				PuBtnConst.btn_redassayres,//取化验结果
				PuBtnConst.btn_countPrice,//计算价格
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY,  //辅助查询
				};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_salesettle;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggSalepresettleVO.class.getName(),
				SalepresettleHVO.class.getName(),
				SalepresettleBVO.class.getName(),
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
		return "pk_salepresettle_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_salepresettle_h";
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
				IBillButton.Busitype, 
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Copy,       //复制
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,	
				IBillButton.Commit,
				IBillButton.Action,	
				IBillButton.Delete,			
				IBillButton.Refresh,
				IBillButton.Card,
				PuBtnConst.btn_redassayres,//取化验结果
				PuBtnConst.btn_countPrice,//计算价格
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY,  //辅助查询
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
