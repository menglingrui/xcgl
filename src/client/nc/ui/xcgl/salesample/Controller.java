package nc.ui.xcgl.salesample;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.sample.AggSampleVO;
import nc.vo.xcgl.sample.SampleBVO;
import nc.vo.xcgl.sample.SampleHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * 销售送样单 Controller
 * @author ddk
 *
 */
public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Busitype,
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Copy,
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,	
				IBillButton.Commit,
				IBillButton.Action,	
				IBillButton.Delete,			
				IBillButton.Refresh,			 
				IBillButton.Return,
				ZmpubBtnConst.ASSPRINT, //打印管理
				ZmpubBtnConst.ASSQUERY,  //辅助查询
				};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return PubBillTypeConst.billtype_salesample;
	}

	public String[] getBillVoName() {
		return new String []{
				AggSampleVO.class.getName(),
				SampleHVO.class.getName(),
				SampleBVO.class.getName()
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
		return "pk_sample_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_sample";
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
		return  new int[]{
				IBillButton.Busitype,
				 IBillButton.Add,
					IBillButton.Edit,
					IBillButton.Query,
					IBillButton.Copy,
					IBillButton.Save,
					IBillButton.Line,
					IBillButton.Cancel,	
					IBillButton.Commit,
					IBillButton.Action,	
					IBillButton.Delete,			
					IBillButton.Refresh,			 
					IBillButton.Card,
					ZmpubBtnConst.ASSPRINT, //打印管理
					ZmpubBtnConst.ASSQUERY , //辅助查询
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
