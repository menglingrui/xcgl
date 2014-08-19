package nc.ui.xcgl.soct;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.soct.ExAggSoctVO;

/**
 * ���ۺ�ͬ
 * @author yangtao
 * @date 2014-2-19 ����05:25:34
 */
public class EventHandler  extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	
	}
	@Override
	protected void onBoSave() throws Exception {
		super.onBoSave();
		super.onBoRefresh();
	}
	
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch(intBtn){
		case PuBtnConst.open:
			setOpen();
			break;
		case PuBtnConst.close:
			setClose();
			break;
		case PuBtnConst.revise:
			onBoEdit1();
			break;
		}
	}
	/**
	 * ��ʱû��ִ���κβ���
	 * ��ťm_boEdit���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	protected void onBoEdit1() throws Exception {
	    
		
		super.onBoEdit1();
	}
	/**
	 * �����򿪰�ť
	 * @throws Exception
	 */
	protected void setOpen() throws Exception{
		//������б�����
		if( getBillManageUI().isListPanelSelected() ){
			AggregatedValueObject billvo=getBillListPanelWrapper().getVOFromUI();
			if(billvo==null)
				return;
			CircularlyAccessibleValueObject hvo=billvo.getParentVO(); 
			if(hvo==null)
				return;
			String isclose=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("isclose"));
			if("Y".equals(isclose)){
					Class[] ParameterTypes = new Class[]{ExAggSoctVO.class};
					Object[] ParameterValues = new Object[]{billvo};
					Object o = LongTimeTask.calllongTimeService(
							"xcgl",null,"���ڼ���...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose", ParameterTypes, ParameterValues);
				onBoRefresh();
			}
		}
		// ����ǿ�Ƭ���棬ʹ��CardPanelPRTS����Դ
		else{
			AggregatedValueObject billvo=getBillCardPanelWrapper().getBillVOFromUI();
			if(billvo==null)
				return;
			CircularlyAccessibleValueObject hvo=billvo.getParentVO(); 
			if(hvo==null)
				return;
			String isclose=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("isclose"));
			if("Y".equals(isclose)){
				Class[] ParameterTypes = new Class[]{ExAggSoctVO.class};
				Object[] ParameterValues = new Object[]{billvo};
				Object o = LongTimeTask.calllongTimeService(
						"xcgl",null,"���ڼ���...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose", ParameterTypes, ParameterValues);
				onBoRefresh();
			}
		}
	}
	/**
	 * �����رհ�ť
	 * @throws Exception
	 */
	protected void setClose() throws Exception{
		//������б�����
		if( getBillManageUI().isListPanelSelected() ){
			AggregatedValueObject billvo=getBillListPanelWrapper().getVOFromUI();
			if(billvo==null)
				return;
			CircularlyAccessibleValueObject hvo=billvo.getParentVO(); 
			if(hvo==null)
				return;
			String isclose=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("isclose"));
			if("N".equals(isclose)){
				Class[] ParameterTypes = new Class[]{ExAggSoctVO.class};
				Object[] ParameterValues = new Object[]{billvo};
				Object o = LongTimeTask.calllongTimeService(
						"xcgl",null,"���ڼ���...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose1", ParameterTypes, ParameterValues);
				onBoRefresh();
				
			}
		}
		//����ǿ�Ƭ����
		else{
			AggregatedValueObject billvo=getBillCardPanelWrapper().getBillVOFromUI();
			if(billvo==null)
				return;
			CircularlyAccessibleValueObject hvo=billvo.getParentVO(); 
			if(hvo==null)
				return;
			String isclose=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("isclose"));
			if("N".equals(isclose)){
				Class[] ParameterTypes = new Class[]{ExAggSoctVO.class};
				Object[] ParameterValues = new Object[]{billvo};
				Object o = LongTimeTask.calllongTimeService(
						"xcgl",null,"���ڼ���...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose1", ParameterTypes, ParameterValues);
				onBoRefresh();
			}
		}
	}
}
