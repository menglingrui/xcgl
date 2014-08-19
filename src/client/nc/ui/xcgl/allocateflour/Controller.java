package nc.ui.xcgl.allocateflour;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.allocateflour.AggallocateVO;
import nc.vo.xcgl.allocateflour.AllocateBVO;
import nc.vo.xcgl.allocateflour.AllocateHVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * ���۵�����
 * @author Jay
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
				//IBillButton.Copy,       //����
				IBillButton.Save,       //����
				IBillButton.Line,       //�в���
				IBillButton.Cancel,	    //ȡ��
				IBillButton.Commit,     //�ύ
				IBillButton.Action,	    //ִ��
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��
				IBillButton.Return,     //����
				ZmpubBtnConst.ASSPRINT, //��ӡ
				ZmpubBtnConst.ASSQUERY, //������ѯ
		};
	}

	public boolean isShowCardRowNo() {
		
		return false;
	}

	public boolean isShowCardTotal() {
		
		return false;
	}

	public String getBillType() {
	
		return PubBillTypeConst.billtype_allocateofflour;
	}

	public String[] getBillVoName() {
		
		return new String []{
				AggallocateVO.class.getName(),
				AllocateHVO.class.getName(),
				AllocateBVO.class.getName()
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
	
		return null;
	}

	public String getHeadZYXKey() {
		
		return null;
	}

	public String getPkField() {
		
		return null;
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
				IBillButton.Add,		//����
				IBillButton.Edit,       //�޸�
				IBillButton.Query,      //��ѯ
				//IBillButton.Copy,       //����
				IBillButton.Save,       //����
				IBillButton.Line,       //�в���
				IBillButton.Cancel,	    //ȡ��
				IBillButton.Commit,     //�ύ
				IBillButton.Action,	    //ִ��
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��
				IBillButton.Return,     //����
				ZmpubBtnConst.ASSPRINT, //��ӡ
				ZmpubBtnConst.ASSQUERY, //������ѯ
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
