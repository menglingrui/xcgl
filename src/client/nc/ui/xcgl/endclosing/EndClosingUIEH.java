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
//	//自定义按钮 处理方法
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
			getBillUI().showWarningMessage("数据为空");
		}
		EndClosingBVO[] vos = billVo.getItems();		
		if(vos == null || vos.length == 0){
			getBillUI().showWarningMessage("数据为空");
		}
		//		数据完整性校验
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
			getBillUI().showErrorMessage("未选中数据");
			return;
		}
		if(!iscontinue){
			getBillUI().showErrorMessage("选中数据均已关账");
			return;
		}		
		billVo.validation();
		//逐级校验 上月已关账
		//校验上月是否isaccount
		if(!isUpperMonAccount(billVo).booleanValue()){
			getBillUI().showErrorMessage("选中数据之前的月份尚未关账");
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
			getBillUI().showWarningMessage("数据为空");
		}		
		EndClosingBVO[] vos = billVo.getItems();	
    	if(vos == null || vos.length == 0){
			getBillUI().showWarningMessage("数据为空");
		}		
//		数据完整性校验
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
			getBillUI().showErrorMessage("未选中数据");
			return;
		}
		if(!iscontinue){
			getBillUI().showErrorMessage("选中数据均未关账");
			return;
		}
		billVo.validation();
		//选择 月份 之后的月份是否已经结账
		if(isSubMonAccount(billVo).booleanValue()){
			getBillUI().showErrorMessage("选中数据之后的月份已关账");
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
			//未结算状态 并且 选中操作
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
		//如果没有去年的结账信息则 不进行 校验
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
//		  领料单     审批成功后
//		  消耗单     保存
//		  变更单     保存
//		  回收单     保存
//		  核销单     保存
//		  转移单     调出量是保存    转入方是审批通过
//		  领料退回   保存
//		  交旧消耗   保存
		//结账 控制单据
		UFBoolean hasNotApp = UFBoolean.FALSE;
		for (EndClosingBVO body : vos) {
			//通过起始日期查询 单据状态
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
			//未结算状态 并且 选中操作
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

