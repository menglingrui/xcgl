package nc.ui.xcgl.soct;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.soct.ExAggSoctVO;
import nc.vo.xcgl.soct.SoctB2VO;
import nc.vo.xcgl.soct.SoctB3VO;
import nc.vo.xcgl.soct.SoctB4VO;
import nc.vo.xcgl.soct.SoctB7VO;
import nc.vo.xcgl.soct.SoctBVO;
import nc.vo.xcgl.soct.SoctHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

/**
 * |销售合同
 * @author yangtao
 * @date 2014-2-19 下午05:23:33
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
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,
				IBillButton.Commit,     //提交
				IBillButton.Action,	    //执行
				IBillButton.Delete,
				IBillButton.Refresh,
				IBillButton.Return,
				PuBtnConst.open,  //打开
				PuBtnConst.close, //关闭
				PuBtnConst.revise,
				ZmpubBtnConst.ASSPRINT, //打印管理
				ZmpubBtnConst.ASSQUERY, 
				
		};
	}

	public boolean isShowCardRowNo() {
		
		return false;
	}

	public boolean isShowCardTotal() {
		
		return false;
	}

	public String getBillType() {
		
		return PubBillTypeConst.billtype_soct;
	}

	public String[] getBillVoName() {
		
		return new String []{
			ExAggSoctVO.class.getName(),
			SoctHVO.class.getName(),
			SoctBVO.class.getName(),
			SoctB2VO.class.getName(),
			SoctB3VO.class.getName(),
			SoctB4VO.class.getName(),
			SoctB7VO.class.getName(),
//			SoctB5VO.class.getName(),
//			SoctB6VO.class.getName()
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
		
		return "pk_soct_b";
	}

	public String getHeadZYXKey() {
		
		return null;
	}

	public String getPkField() {
		
		return "pk_soct";
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
		
		return new int []{
				IBillButton.Busitype,
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Query,
				IBillButton.Save,
				IBillButton.Line,
				IBillButton.Cancel,
				IBillButton.Commit,     //提交
				IBillButton.Action,	    //执行
				IBillButton.Delete,
				IBillButton.Refresh,
				IBillButton.Card,
				PuBtnConst.open,  //打开
				PuBtnConst.close, //关闭
				PuBtnConst.revise,
				ZmpubBtnConst.ASSPRINT, //打印管理
				ZmpubBtnConst.ASSQUERY, 
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
