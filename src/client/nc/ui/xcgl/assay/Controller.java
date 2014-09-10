package nc.ui.xcgl.assay;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.xcgl.assay.AggassayVO;
import nc.vo.xcgl.assay.AssayBVO;
import nc.vo.xcgl.assay.AssayHVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.consts.ZmpubBtnConst;
/**
 *��˵�������鵥Controller
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-20����09:43:07
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
				PuBtnConst.revise,
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��
				IBillButton.Return,     //����
				//PuBtnConst.ckmx,
				ZmpubBtnConst.ASSPRINT, //��ӡ
				ZmpubBtnConst.ASSQUERY, //������ѯ
				};
	}

	public boolean isShowCardRowNo() {	
		return true;
	}
	public boolean isShowCardTotal() {
		return false;
	}
	public String getBillType() {
		return PubBillTypeConst.billtype_assay;
	}
	public String[] getBillVoName() {
		return new String []{
				AggassayVO.class.getName(),
				AssayHVO.class.getName(),
				AssayBVO.class.getName()
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
				PuBtnConst.revise,
				IBillButton.Delete,     //ɾ��	
				IBillButton.Refresh,  	//ˢ��		 
				IBillButton.Card,       //��Ƭ
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
