package nc.ui.xcgl.qualitygrade;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.qualitygrade.AggQualityGradeVO;
import nc.vo.xcgl.qualitygrade.QualityGradeBVO;
import nc.vo.xcgl.qualitygrade.QualityGradeHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * �����żۡ�ƷλController
 * @author ddk
 *
 */
public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
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
				ZmpubBtnConst.ASSPRINT, //��ӡ����
				ZmpubBtnConst.ASSQUERY  //������ѯ
				};
	}

	public boolean isShowCardRowNo() {
		return false;
	}

	public boolean isShowCardTotal() {
		return false;
	}

	public String getBillType() {
		return PubBillTypeConst.billtype_qualitygrade;
	}

	public String[] getBillVoName() {
		return new String[]{
				AggQualityGradeVO.class.getName(),
				QualityGradeHVO.class.getName(),
				QualityGradeBVO.class.getName(),
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
		return "pk_qualitygrade_b";
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return "pk_qualitygrade_h";
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
				ZmpubBtnConst.ASSPRINT, //��ӡ����
				ZmpubBtnConst.ASSQUERY,  //������ѯ
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
