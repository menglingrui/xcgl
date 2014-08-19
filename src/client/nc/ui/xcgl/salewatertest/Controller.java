package nc.ui.xcgl.salewatertest;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.salewatertest.AggsalewatertestVO;
import nc.vo.xcgl.salewatertest.SaleWaterTestBVO;
import nc.vo.xcgl.salewatertest.SaleWaterTestHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * 销售水分化验单
 * @author Jay
 *
 */

public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Refbill,    //参照
				//IBillButton.Add,		//增加
				IBillButton.Edit,       //修改
				IBillButton.Query,      //查询
				//IBillButton.Copy,       //复制
				IBillButton.Save,       //保存
				IBillButton.Line,       //行操作
				IBillButton.Cancel,	    //取消
				IBillButton.Commit,     //提交
				IBillButton.Action,	    //执行
				IBillButton.Delete,     //删除	
				IBillButton.Refresh,  	//刷新
				IBillButton.Return,     //返回
				//PuBtnConst.ckmx,
				ZmpubBtnConst.ASSPRINT, //打印
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
		return PubNodeModelConst.NodeModel_salewatertest;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggsalewatertestVO.class.getName(),
				SaleWaterTestHVO.class.getName(),
				SaleWaterTestBVO.class.getName(),
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

		return false;
	}

	public String[] getListBodyHideCol() {

		return null;
	}

	public int[] getListButtonAry() {
		return new int[]{
				IBillButton.Refbill,    //参照
				//IBillButton.Add,		//增加
				IBillButton.Edit,       //修改
				IBillButton.Query,      //查询
				//IBillButton.Copy,       //复制
				IBillButton.Save,       //保存
				IBillButton.Line,       //行操作
				IBillButton.Cancel,	    //取消
				IBillButton.Commit,     //提交
				IBillButton.Action,	    //执行
				IBillButton.Delete,     //删除	
				IBillButton.Refresh,  	//刷新
				IBillButton.Return,     //返回
				//PuBtnConst.ckmx,
				ZmpubBtnConst.ASSPRINT, //打印
				ZmpubBtnConst.ASSQUERY, //辅助查询
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
