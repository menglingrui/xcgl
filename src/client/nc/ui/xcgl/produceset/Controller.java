package nc.ui.xcgl.produceset;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.produceset.AggProduceSetVO;
import nc.vo.xcgl.produceset.ProductSetBVO;
import nc.vo.xcgl.produceset.ProductSetVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Query,      //查询
				IBillButton.Add,		//增加
				IBillButton.Edit,       //修改
//				IBillButton.Brow,       //浏览
				IBillButton.Line,       //行操作
				IBillButton.Save,       //保存
				IBillButton.Cancel,	    //取消
				IBillButton.Delete,     //删除	
				IBillButton.Refresh,  	//刷新
				IBillButton.Copy,       //复制
				IBillButton.Return,     //返回
				ZmpubBtnConst.clmx,
				PuBtnConst.ckmx,
				};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_Produceset;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggProduceSetVO.class.getName(),
				ProductSetVO.class.getName(),
				ProductSetBVO.class.getName()
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
		return "xcgl_weighdoc_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "xcgl_weighdoc";
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
				IBillButton.Query,      //查询
				IBillButton.Add,		//增加
				IBillButton.Edit,       //修改
				IBillButton.Line,       //行操作
				IBillButton.Save,       //保存
				IBillButton.Cancel,	    //取消
				IBillButton.Delete,     //删除	
				IBillButton.Refresh,  	//刷新		 
				IBillButton.Copy,       //复制
				IBillButton.Card,       //卡片
				ZmpubBtnConst.clmx,
				PuBtnConst.ckmx,
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
