package nc.ui.xcgl.gendrugconsume;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.gendrugconsume.AggDrugConsume;
import nc.vo.xcgl.gendrugconsume.GenDrugConsumeBVO;
import nc.vo.xcgl.gendrugconsume.GenDrugConsumeHVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
//				IBillButton.Refbill,    //参照
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
				PuBtnConst.flowset, 	//取流程设置
				ZmpubBtnConst.ASSPRINT, //打印管理
				ZmpubBtnConst.ASSQUERY, //辅助查询
				};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_Drugconsume;
	}

	public String[] getBillVoName() {
		return  new String[]{
				AggDrugConsume.class.getName(),
				GenDrugConsumeHVO.class.getName(),
				GenDrugConsumeBVO.class.getName()
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
		return "pk_general_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_general_h";
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
//				IBillButton.Refbill,    //参照
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
				PuBtnConst.flowset, 	//取流程设置
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
