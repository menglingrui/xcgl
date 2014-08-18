package nc.ui.xcgl.endclosing;


import javax.swing.JComponent;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;

public class EndClosingUI extends BillCardUI implements BillCardBeforeEditListener{
	
	private static final long serialVersionUID = 1L;	
	private String accountyear = null;
	//��д���캯��
	public EndClosingUI(){
		super();
		//��ʼ������ ����
		init();
		//��ʼ�� ����
	try {
			initData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	protected ICardController createController() {
		return new EndClosingUICtrl();
	}
	

	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}
	/**
	 * ʵ��������༭ǰ���¼�����,
	 * ��������¼�������Ҫ���ظ÷���
	 * �������ڣ�(2004-1-3 18:13:36)
	 */
	@Override
	protected CardEventHandler createEventHandler() {
		return new EndClosingUIEH(this,getUIControl());
	}


	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}
	

	@Override
	public String getRefBillType() {
		return null;
	}

	@Override
	protected void initSelfData() {
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
	}


	//ע���Զ��尴ť
	@Override
	protected void initPrivateButton() {		
		//ע���Զ���ť
		//����
		ButtonVO btnVo1 = new ButtonVO();
		btnVo1.setBtnNo(PuBtnConst.btn_closing);
		btnVo1.setBtnCode(null);
		btnVo1.setBtnName("����");
		btnVo1.setBtnChinaName("����");
		btnVo1.setBtnName("����");
		btnVo1.setHintStr("����");
		btnVo1.setOperateStatus(new 
				int[]{IBillOperate.OP_NOTEDIT,
						IBillOperate.OP_INIT});
		btnVo1.setBusinessStatus(new 
				int[]{IBillStatus.FREE});
		addPrivateButton(btnVo1);
		//ȡ������
		ButtonVO btnVo2 = new ButtonVO();
		btnVo2.setBtnNo(PuBtnConst.btn_disclosing);
		btnVo2.setBtnCode(null);
		btnVo2.setBtnName("ȡ������");
		btnVo2.setBtnChinaName("ȡ������");
		btnVo2.setBtnName("ȡ������");
		btnVo2.setHintStr("�����ȡ������");
		addPrivateButton(btnVo2);
		//��ĩ���� ˢ��
		ButtonVO btnVo3 = new ButtonVO();
		btnVo3.setBtnNo(PuBtnConst.btn_closingrefresh);
		btnVo3.setBtnCode(null);
		btnVo3.setBtnName("ˢ��");
		btnVo3.setBtnChinaName("ˢ��");
		btnVo3.setBtnName("ˢ��");
		btnVo3.setHintStr("ˢ��");
		addPrivateButton(btnVo3);
	}
	
	@Override
	protected void initEventListener() {
		super.initEventListener();
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	}
	public void initData() throws Exception{
		//��ȡ��ǰ����   ��ǰ�����   
		UFDate date=_getDate();
		//����ת�� Ϊ�ַ�����ʽ��PuPubVO�������ʹ����߰�
		if(PuPubVO.getString_TrimZeroLenAsNull(accountyear)==null)
			accountyear = String.valueOf(date.getYear());
		String corp = _getCorp().getPrimaryKey();
		AggregatedValueObject bill = MonthCloseHelper.getCloseMonByYear(accountyear, corp);
		if(bill == null){
		 bill = EndClosingHelper.initCloseMonByDate(accountyear, corp);
		}
		getBillCardPanel().setBillValueVO(bill);
	}	
	private void init(){
		getBillCardPanel().setEnabled(true);
		getBillCardPanel().getBillTable().removeSortListener();
	}
	
	// �༭�� �¼�����
	@Override
	public void afterEdit(BillEditEvent e) {
		String key = e.getKey();		

		if("pk_accperiod".equalsIgnoreCase(key)){
			Object keyValue = e.getValue();
			if(null == keyValue || "".equals(keyValue.toString()) ){
				getBillCardPanel().getBillModel().clearBodyData();
				return;
			}
			try {
				accountyear = keyValue.toString();
				initData();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				showErrorMessage(e1.getMessage()==null?"":e1.getMessage());
			}
		}
		super.afterEdit(e);
	}

	public boolean beforeEdit(BillItemEvent e) {
		if(e.getItem().getKey().equals("pk_accperiod")){
			String key = e.getItem().getKey();
				//	String kuangqu = getBillCardPanel().getHeadItem("pk_accperiod").getValue();
	          
					JComponent jf = getBillCardPanel().getHeadItem(key).getComponent();
					if (jf instanceof UIRefPane) {
					UIRefPane panel = (UIRefPane) jf;
					panel.setNotLeafSelectedEnabled(false);
					panel
							.getRefModel()
							.addWherePart(
									" and bd_accperiod.pk_accperiodscheme ='0001AA00000000000001' "	);		
									}
		   
	       return true;
		}
		return true;
	}	
}

