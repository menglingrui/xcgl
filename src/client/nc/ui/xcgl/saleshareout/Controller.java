package nc.ui.xcgl.saleshareout;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.saleshareout.AggSaleShareoutVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBVO;
import nc.vo.xcgl.saleshareout.SaleShareoutHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * 销售出库矿区分摊计算controller
 * @author ddk
 *
 */
public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Refbill,    //参照
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
				//PuBtnConst.btn_solver,  //规划求解
				PuBtnConst.areashare,   //矿区分摊
				PuBtnConst.ckmx,  //分摊明细查看
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY,
				};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_saleshareout;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggSaleShareoutVO.class.getName(),
				SaleShareoutHVO.class.getName(),
				SaleShareoutBVO.class.getName()
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
				IBillButton.Refbill,    //参照
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
				//PuBtnConst.btn_solver,  //规划求解
				PuBtnConst.areashare,   //矿区分摊
				PuBtnConst.ckmx,  //分摊明细查看
				ZmpubBtnConst.ASSPRINT,
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
		return true;
	}

}
