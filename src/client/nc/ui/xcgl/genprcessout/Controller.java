package nc.ui.xcgl.genprcessout;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.genprcessout.AggGeneralVO;
import nc.vo.xcgl.genprcessout.GenPrcOutBVO;
import nc.vo.xcgl.genprcessout.GenPrcOutHVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;

/**
 *��˵����ԭ��ӹ����ⵥ
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-11����03:29:30
 */
public class Controller extends AbstractManageController{

	public String[] getCardBodyHideCol() {
		
		return null;
	}

	public int[] getCardButtonAry() {
	
		return new int[]{
				//IBillButton.Refbill,    //����
				IBillButton.Add,		//����
				IBillButton.Edit,       //�޸�
				IBillButton.Query,      //��ѯ
				IBillButton.Copy,       //����
//				IBillButton.Brow,       //���
				IBillButton.Save,       //����
				IBillButton.Line,       //�в���
				IBillButton.Cancel,	    //ȡ��
				IBillButton.Commit,     //�ύ
				IBillButton.Action,	    //ִ��
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��
				IBillButton.Return,     //����
				PuBtnConst.flowset,    //ȡ��������
				ZmpubBtnConst.ASSPRINT,
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
		
		return PubBillTypeConst.billtype_Generalout;
	}

	public String[] getBillVoName() {
		
		return new String []{
				AggGeneralVO.class.getName(),
				GenPrcOutHVO.class.getName(),
				GenPrcOutBVO.class.getName()
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
		
		return false;
	}

	public String[] getListBodyHideCol() {
		
		return null;
	}

	public int[] getListButtonAry() {
		
		return new int[]{
				//IBillButton.Refbill,    //����
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
				PuBtnConst.flowset,    //ȡ��������
				ZmpubBtnConst.ASSPRINT,
				ZmpubBtnConst.ASSQUERY,
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
