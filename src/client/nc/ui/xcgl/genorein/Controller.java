package nc.ui.xcgl.genorein;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.genorein.AggGenOreInVO;
import nc.vo.xcgl.genorein.GenOreInBVO;
import nc.vo.xcgl.genorein.GenOreInHVO;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return  new int[]{
				IBillButton.Add,		//����
				IBillButton.Edit,       //�޸�
				IBillButton.Query,      //��ѯ
				IBillButton.Copy,       //����
				IBillButton.Save,       //����
				IBillButton.Line,       //�в���
				IBillButton.Cancel,	    //ȡ��
				IBillButton.Commit,     //�ύ
				IBillButton.Action,	    //ִ��
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��
				IBillButton.Return,     //����
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY
				};
	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return PubNodeModelConst.NodeModel_General_in;
	}

	public String[] getBillVoName() {
		return new String []{
				AggGenOreInVO.class.getName(),
				GenOreInHVO.class.getName(),
				GenOreInBVO.class.getName()
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
		return  new int[]{
				IBillButton.Add,		//����
				IBillButton.Edit,       //�޸�
				IBillButton.Query,      //��ѯ
				IBillButton.Copy,       //����
				IBillButton.Save,       //����
				IBillButton.Line,       //�в���
				IBillButton.Cancel,	    //ȡ��
				IBillButton.Commit,     //�ύ
				IBillButton.Action,	    //ִ��
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��		 
				IBillButton.Card,       //��Ƭ
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY
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
