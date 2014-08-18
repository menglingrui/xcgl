package nc.ui.xcgl.watertest;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.watertest.AggwatertestVO;
import nc.vo.xcgl.watertest.WaterTestBVO;
import nc.vo.xcgl.watertest.WaterTestHVO;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 * ˮ�ֻ��鵥
 * @author Jay
 *
 */

public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Refbill,    //����
				//IBillButton.Add,		//����
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
				//PuBtnConst.ckmx,
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
		
		return PubNodeModelConst.NodeModel_watertest;
	}

	public String[] getBillVoName() {
		
		return new String[]{
				AggwatertestVO.class.getName(),
				WaterTestHVO.class.getName(),
				WaterTestBVO.class.getName(),
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
				IBillButton.Refbill,    //����
				//IBillButton.Add,		//����
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
				//PuBtnConst.ckmx,
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
