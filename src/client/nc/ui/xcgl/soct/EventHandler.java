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
 * 销售合同
 * @author yangtao
 * @date 2014-2-19 下午05:25:34
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
	 * 暂时没有执行任何操作
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit1() throws Exception {
	    
		
		super.onBoEdit1();
	}
	/**
	 * 单击打开按钮
	 * @throws Exception
	 */
	protected void setOpen() throws Exception{
		//如果是列表下面
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
							"xcgl",null,"正在计算...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose", ParameterTypes, ParameterValues);
				onBoRefresh();
			}
		}
		// 如果是卡片界面，使用CardPanelPRTS数据源
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
						"xcgl",null,"正在计算...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose", ParameterTypes, ParameterValues);
				onBoRefresh();
			}
		}
	}
	/**
	 * 单击关闭按钮
	 * @throws Exception
	 */
	protected void setClose() throws Exception{
		//如果是列表下面
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
						"xcgl",null,"正在计算...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose1", ParameterTypes, ParameterValues);
				onBoRefresh();
				
			}
		}
		//如果是卡片界面
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
						"xcgl",null,"正在计算...",1,"nc.bs.xcgl.soct.Update", null,"setIsclose1", ParameterTypes, ParameterValues);
				onBoRefresh();
			}
		}
	}
}
