package nc.ui.xcgl.saleassayres;

import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.assayres.AggAssayResVO;
import nc.vo.xcgl.assayres.AssayResBVO;
import nc.vo.xcgl.assayres.AssayResHVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 *��˵���������� Controller
 *@author ddk
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
//				PuBtnConst.ckmx,
				IBillButton.Return,     //����
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
		
		return PubBillTypeConst.billtype_saleassayres;
	}

	public String[] getBillVoName() {
		
		return new String []{
				AggAssayResVO.class.getName(),
				AssayResHVO.class.getName(),
				AssayResBVO.class.getName()
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
//				PuBtnConst.ckmx,
				IBillButton.Card,       //��Ƭ
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

