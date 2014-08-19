package nc.ui.xcgl.endclosing;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.endclosing.ClosingGetCheck;
import nc.vo.xcgl.endclosing.EndClosingBVO;
import nc.vo.xcgl.endclosing.EndClosingHVO;
import nc.vo.xcgl.endclosing.EndclosingVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;

public class EndClosingUIEH extends CardEventHandler {

	public EndClosingUIEH(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}	
//	//�Զ��尴ť ������
//	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch(intBtn){
			case PuBtnConst.btn_closing:
				doclosing();
				break;
			case PuBtnConst.btn_disclosing:
				dodisclosing();
				break;
			case PuBtnConst.btn_closingrefresh:
				onBoRefresh();
				break;
			default: 	
				break;
		}		
	}
	
	private void doclosing() throws Exception {	
		getBillCardPanelWrapper().getBillCardPanel().stopEditing();
		getBillCardPanelWrapper().getBillCardPanel().dataNotNullValidate();
		EndclosingVO billVo = null;
		billVo = (EndclosingVO)getBillCardPanelWrapper().getBillVOFromUI();
		if(billVo == null){
			getBillUI().showWarningMessage("����Ϊ��");
		}
		EndClosingBVO[] vos = billVo.getItems();		
		if(vos == null || vos.length == 0){
			getBillUI().showWarningMessage("����Ϊ��");
		}
		//		����������У��
		boolean iscontinue = false;
		boolean issel = false;
		boolean isadd = PuPubVO.getString_TrimZeroLenAsNull(billVo.getHeader().getPrimaryKey()) == null;
		for(EndClosingBVO vo:vos){
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue()){
				if(!PuPubVO.getUFBoolean_NullAs(vo.getIsclosing(), UFBoolean.FALSE).booleanValue()){
					vo.setIsclosing(UFBoolean.TRUE);
					iscontinue = true;
				}else{
					vo.setFlag(UFBoolean.FALSE);
				}	
				issel = true;
			}
			if(isadd)
				vo.setStatus(VOStatus.NEW);
			else
				vo.setStatus(VOStatus.UPDATED);
		}
		if(!issel){
			getBillUI().showErrorMessage("δѡ������");
			return;
		}
		if(!iscontinue){
			getBillUI().showErrorMessage("ѡ�����ݾ��ѹ���");
			return;
		}		
		billVo.validation();
		//��У�� �����ѹ���
		//У�������Ƿ�isaccount
		if(!isUpperMonAccount(billVo).booleanValue()){
			getBillUI().showErrorMessage("ѡ������֮ǰ���·���δ����");
			return;
		}
		billVo.setIsclosing(UFBoolean.TRUE);
		HYPubBO_Client.saveBD(billVo,getCheckClass());
		onBoRefresh();
	}
	
	
	private void dodisclosing() throws Exception {	
		getBillCardPanelWrapper().getBillCardPanel().stopEditing();
		getBillCardPanelWrapper().getBillCardPanel().dataNotNullValidate();		
		EndclosingVO billVo = null;
		billVo = (EndclosingVO)getBillCardPanelWrapper().getBillVOFromUI();
		if(billVo == null){
			getBillUI().showWarningMessage("����Ϊ��");
		}		
		EndClosingBVO[] vos = billVo.getItems();	
    	if(vos == null || vos.length == 0){
			getBillUI().showWarningMessage("����Ϊ��");
		}		
//		����������У��
		boolean iscontinue = false;
		boolean issel = false;
		boolean isadd = PuPubVO.getString_TrimZeroLenAsNull(billVo.getHeader().getPrimaryKey()) == null;
		for(EndClosingBVO vo:vos){
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue()){
				if(PuPubVO.getUFBoolean_NullAs(vo.getIsclosing(), UFBoolean.FALSE).booleanValue()){
					vo.setIsclosing(UFBoolean.FALSE);
					iscontinue = true;
				}else{
					vo.setFlag(UFBoolean.FALSE);
				}		
				issel = true;
			}
			if(isadd)
				vo.setStatus(VOStatus.NEW);
			else
				vo.setStatus(VOStatus.UPDATED);
		}
		
		if(!issel){
			getBillUI().showErrorMessage("δѡ������");
			return;
		}
		if(!iscontinue){
			getBillUI().showErrorMessage("ѡ�����ݾ�δ����");
			return;
		}
		billVo.validation();
		//ѡ�� �·� ֮����·��Ƿ��Ѿ�����
		if(isSubMonAccount(billVo).booleanValue()){
			getBillUI().showErrorMessage("ѡ������֮����·��ѹ���");
			return;
		}
		billVo.setIsclosing(UFBoolean.FALSE);
		HYPubBO_Client.saveBD(billVo,getCheckClass());
		
		onBoRefresh();
	}
	
	private UFBoolean isSubMonAccount(EndclosingVO billVo) throws Exception {
		EndClosingBVO[] vos = billVo.getItems();
		EndClosingHVO hvo = billVo.getHeader();
		UFBoolean isAccount = UFBoolean.FALSE;
		for(EndClosingBVO vo:vos){
			//δ����״̬ ���� ѡ�в���
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue() 
					&& !PuPubVO.getUFBoolean_NullAs(vo.getIsclosing(), UFBoolean.FALSE).booleanValue()
				){
				if(PuPubVO.getInteger_NullAs(vo.getVmonth(), -1).intValue() == 12){
					String accountyear = hvo.getCyear();
					accountyear = PuPubVO.getString_TrimZeroLenAsNull(PuPubVO.getInteger_NullAs(accountyear, -1).intValue()-1);
					isAccount = isYearAccount(billVo,accountyear);
					if(isAccount == null){
						isAccount = UFBoolean.FALSE;
					}
				}else{
					Integer month = PuPubVO.getInteger_NullAs(vo.getVmonth(), -1) + 1;
					isAccount = isMonAccount(vos, month);
				}
			}
			
			if(isAccount.booleanValue()){
				break;
			}
		}
		return isAccount;
	}
	private UFBoolean isYearAccount(EndclosingVO billVo,String accountyear) throws Exception {
		EndClosingHVO hvo = billVo.getHeader();
		String corp = hvo.getPk_corp();
		EndclosingVO vo = (EndclosingVO) MonthCloseHelper.getCloseMonByYear(accountyear, corp);
		//���û��ȥ��Ľ�����Ϣ�� ������ У��
		if(vo == null){
			return null;
		}
		EndClosingBVO[] bodys = vo.getItems();
		for (EndClosingBVO body : bodys) {
			if(body.getVmonth().trim().equals("12")){
				return PuPubVO.getUFBoolean_NullAs(body.getIsclosing(), UFBoolean.FALSE);
			}
		}
		return UFBoolean.TRUE;
	}


	private UFBoolean isMonAccount(EndClosingBVO[] vos,Integer month) {
		for (EndClosingBVO body : vos) {
			Integer uppermonth = PuPubVO.getInteger_NullAs(body.getVmonth(),-1);
			if(month.intValue() == uppermonth.intValue() ){
				return PuPubVO.getUFBoolean_NullAs(body.getIsclosing(), UFBoolean.FALSE);
			}
		}
		return UFBoolean.TRUE;
	}
	
	
	private ClosingGetCheck cc  = null;
	private ClosingGetCheck getCheckClass(){
		if(cc == null){
			cc = new ClosingGetCheck();
		}
		return cc;
	}	
	private UFBoolean hasNotApprove(EndclosingVO billVo) throws Exception {
		EndClosingBVO[] vos = billVo.getItems();
		EndClosingHVO hvo = billVo.getHeader();
//		  ���ϵ�     �����ɹ���
//		  ���ĵ�     ����
//		  �����     ����
//		  ���յ�     ����
//		  ������     ����
//		  ת�Ƶ�     �������Ǳ���    ת�뷽������ͨ��
//		  �����˻�   ����
//		  ��������   ����
		//���� ���Ƶ���
		UFBoolean hasNotApp = UFBoolean.FALSE;
		for (EndClosingBVO body : vos) {
			//ͨ����ʼ���ڲ�ѯ ����״̬
			if(PuPubVO.getUFBoolean_NullAs(body.getFlag(), UFBoolean.FALSE).booleanValue() 
					&& PuPubVO.getUFBoolean_NullAs(body.getIsclosing(), UFBoolean.FALSE).booleanValue()
					){
				hasNotApp = PuPubVO.getUFBoolean_NullAs(EndClosingHelper.hasNotApprove(body,hvo.getPk_corp()), UFBoolean.FALSE);
				if(hasNotApp.booleanValue()){
					return hasNotApp;
				}
			}
		}
		return UFBoolean.FALSE;
	}


	private UFBoolean isUpperMonAccount(EndclosingVO billVo) throws Exception {
		EndClosingBVO[] vos = billVo.getItems();
		EndClosingHVO hvo = billVo.getHeader();
		UFBoolean isAccount = UFBoolean.TRUE;
		for(EndClosingBVO vo:vos){
			//δ����״̬ ���� ѡ�в���
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue() 
					&& PuPubVO.getUFBoolean_NullAs(vo.getIsclosing(), UFBoolean.FALSE).booleanValue()
				){
				if(PuPubVO.getInteger_NullAs(vo.getVmonth(), -1).intValue() == 1){
					String accountyear = hvo.getCyear();
					accountyear = PuPubVO.getString_TrimZeroLenAsNull(PuPubVO.getInteger_NullAs(accountyear, -1).intValue()-1);
					isAccount = isYearAccount(billVo,accountyear);
					if(isAccount == null){
						isAccount = UFBoolean.TRUE;
					}
				}else{
					Integer month = PuPubVO.getInteger_NullAs(vo.getVmonth(), -1) - 1;
					isAccount = isMonAccount(vos, month);
				}
			}
			
			if(!isAccount.booleanValue()){
				break;
			}
		}
		return isAccount;
	}

	protected void onBoRefresh() throws Exception {
		((EndClosingUI)getBillUI()).initData();
	}
	
	
}

