package nc.ui.xcgl.latecheckout;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.latecheckout.CloseGetCheck;
import nc.vo.xcgl.latecheckout.CloseMonBodyVO;
import nc.vo.xcgl.latecheckout.CloseMonHeaderVO;
import nc.vo.xcgl.latecheckout.CloseMonVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;

public class CloseMonUIEH extends CardEventHandler {

	public CloseMonUIEH(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}	
	//�Զ��尴ť ������
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch(intBtn){
			case PuBtnConst.btn_closemon:
				doAccount();
				break;
			case PuBtnConst.btn_disclosemon:
				doDisAccount();
				break;
			case PuBtnConst.btn_refresh:
				onBoRefresh();
				break;
			default: 	
				break;
		}		
	}
	private void doDisAccount() throws Exception {		
		getBillCardPanelWrapper().getBillCardPanel().stopEditing();
		getBillCardPanelWrapper().getBillCardPanel().dataNotNullValidate();		
		CloseMonVO billVo = null;
		billVo = (CloseMonVO)getBillCardPanelWrapper().getBillVOFromUI();
		if(billVo == null){
			getBillUI().showWarningMessage("����Ϊ��");
		}		
		CloseMonBodyVO[] vos = billVo.getItems();	
    	if(vos == null || vos.length == 0){
			getBillUI().showWarningMessage("����Ϊ��");
		}		
//		����������У��
		boolean iscontinue = false;
		boolean issel = false;
		boolean isadd = PuPubVO.getString_TrimZeroLenAsNull(billVo.getHeader().getPrimaryKey()) == null;
		for(CloseMonBodyVO vo:vos){
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue()){
				if(PuPubVO.getUFBoolean_NullAs(vo.getIsaccount(), UFBoolean.FALSE).booleanValue()){
					vo.setIsaccount(UFBoolean.FALSE);
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
			getBillUI().showErrorMessage("ѡ������֮����·��ѽ���");
			return;
		}
		billVo.setIsaccount(UFBoolean.FALSE);
		HYPubBO_Client.saveBD(billVo,getCheckClass());
		
		onBoRefresh();
	}

	private UFBoolean isSubMonAccount(CloseMonVO billVo) throws Exception {
		CloseMonBodyVO[] vos = billVo.getItems();
		CloseMonHeaderVO hvo = billVo.getHeader();
		UFBoolean isAccount = UFBoolean.FALSE;
		for(CloseMonBodyVO vo:vos){
			//δ����״̬ ���� ѡ�в���
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue() 
					&& !PuPubVO.getUFBoolean_NullAs(vo.getIsaccount(), UFBoolean.FALSE).booleanValue()
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
	private UFBoolean isYearAccount(CloseMonVO billVo,String accountyear) throws Exception {
		CloseMonHeaderVO hvo = billVo.getHeader();
		String corp = hvo.getPk_corp();
		CloseMonVO vo = (CloseMonVO) CloseMonHelper.getCloseMonByYear(accountyear, corp);
		//���û��ȥ��Ľ�����Ϣ�� ������ У��
		if(vo == null){
			return null;
		}
		CloseMonBodyVO[] bodys = vo.getItems();
		for (CloseMonBodyVO body : bodys) {
			if(body.getVmonth().trim().equals("12")){
				return PuPubVO.getUFBoolean_NullAs(body.getIsaccount(), UFBoolean.FALSE);
			}
		}
		return UFBoolean.TRUE;
	}


	private UFBoolean isMonAccount(CloseMonBodyVO[] vos,Integer month) {
		for (CloseMonBodyVO body : vos) {
			Integer uppermonth = PuPubVO.getInteger_NullAs(body.getVmonth(),-1);
			if(month.intValue() == uppermonth.intValue() ){
				return PuPubVO.getUFBoolean_NullAs(body.getIsaccount(), UFBoolean.FALSE);
			}
		}
		return UFBoolean.TRUE;
	}
	
	

	private void doAccount() throws Exception {

		getBillCardPanelWrapper().getBillCardPanel().stopEditing();
		getBillCardPanelWrapper().getBillCardPanel().dataNotNullValidate();
		CloseMonVO billVo = null;
		billVo = (CloseMonVO)getBillCardPanelWrapper().getBillVOFromUI();
		if(billVo == null){
			getBillUI().showWarningMessage("����Ϊ��");
		}
		CloseMonBodyVO[] vos = billVo.getItems();		
		if(vos == null || vos.length == 0){
			getBillUI().showWarningMessage("����Ϊ��");
		}
		//		����������У��
		boolean iscontinue = false;
		boolean issel = false;
		boolean isadd = PuPubVO.getString_TrimZeroLenAsNull(billVo.getHeader().getPrimaryKey()) == null;
		for(CloseMonBodyVO vo:vos){
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue()){
				if(!PuPubVO.getUFBoolean_NullAs(vo.getIsaccount(), UFBoolean.FALSE).booleanValue()){
					vo.setIsaccount(UFBoolean.TRUE);
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
			getBillUI().showErrorMessage("ѡ�����ݾ��ѽ���");
			return;
		}		
		billVo.validation();
		//��У�� �����ѽ���
		//У�������Ƿ�isaccount
		if(!isUpperMonAccount(billVo).booleanValue()){
			getBillUI().showErrorMessage("ѡ������֮ǰ���·���δ����");
			return;
		}
		billVo.setIsaccount(UFBoolean.TRUE);
		HYPubBO_Client.saveBD(billVo,getCheckClass());
		onBoRefresh();
	}
	
	private CloseGetCheck cc  = null;
	private CloseGetCheck getCheckClass(){
		if(cc == null){
			cc = new CloseGetCheck();
		}
		return cc;
	}	
	private UFBoolean hasNotApprove(CloseMonVO billVo) throws Exception {
		CloseMonBodyVO[] vos = billVo.getItems();
		CloseMonHeaderVO hvo = billVo.getHeader();
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
		for (CloseMonBodyVO body : vos) {
			//ͨ����ʼ���ڲ�ѯ ����״̬
			if(PuPubVO.getUFBoolean_NullAs(body.getFlag(), UFBoolean.FALSE).booleanValue() 
					&& PuPubVO.getUFBoolean_NullAs(body.getIsaccount(), UFBoolean.FALSE).booleanValue()
					){
				hasNotApp = PuPubVO.getUFBoolean_NullAs(CloseMonHelper.hasNotApprove(body,hvo.getPk_corp()), UFBoolean.FALSE);
				if(hasNotApp.booleanValue()){
					return hasNotApp;
				}
			}
		}
		return UFBoolean.FALSE;
	}


	private UFBoolean isUpperMonAccount(CloseMonVO billVo) throws Exception {
		CloseMonBodyVO[] vos = billVo.getItems();
		CloseMonHeaderVO hvo = billVo.getHeader();
		UFBoolean isAccount = UFBoolean.TRUE;
		for(CloseMonBodyVO vo:vos){
			//δ����״̬ ���� ѡ�в���
			if(PuPubVO.getUFBoolean_NullAs(vo.getFlag(), UFBoolean.FALSE).booleanValue() 
					&& PuPubVO.getUFBoolean_NullAs(vo.getIsaccount(), UFBoolean.FALSE).booleanValue()
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
		((CloseMonUI)getBillUI()).initData();
	}
	
}
