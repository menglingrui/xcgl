package nc.ui.xcgl.qualitypro;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.qualitypro.ExAggQualityProVO;
import nc.vo.xcgl.qualitypro.QualityPorB1VO;
import nc.vo.xcgl.qualitypro.QualityProB2VO;
import nc.vo.xcgl.qualitypro.QualityProHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

/**
 * 优质优价方案Controller
 * @author ddk
 *
 */
public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Add,		//增加
				IBillButton.Edit,       //修改
				IBillButton.Query,      //查询
				IBillButton.Copy,       //复制
				IBillButton.Save,       //保存
				IBillButton.Line,       //行操作
				IBillButton.Cancel,	    //取消
				IBillButton.Commit,     //提交
				IBillButton.Action,	    //执行
				IBillButton.Delete,     //删除	
				IBillButton.Refresh,  	//刷新
				IBillButton.Return,     //返回
				ZmpubBtnConst.ASSPRINT, //打印管理
				ZmpubBtnConst.ASSQUERY  //辅助查询
				};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return PubBillTypeConst.billtype_qualitypro;
	}

	public String[] getBillVoName() {
		return new String[]{
				ExAggQualityProVO.class.getName(),
				QualityProHVO.class.getName(),
				QualityProB2VO.class.getName(),
				QualityPorB1VO.class.getName(),
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
		return "pk_qualitypro_b1";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_qualitypro_h";
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
				IBillButton.Add,		//增加
				IBillButton.Edit,       //修改
				IBillButton.Query,      //查询
				IBillButton.Copy,       //复制
				IBillButton.Save,       //保存
				IBillButton.Line,       //行操作
				IBillButton.Cancel,	    //取消
				IBillButton.Commit,     //提交
				IBillButton.Action,	    //执行
				IBillButton.Delete,     //删除	
				IBillButton.Refresh,  	//刷新
				IBillButton.Card,       //卡片
				ZmpubBtnConst.ASSPRINT, //打印管理
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
		return false;
	}

}
