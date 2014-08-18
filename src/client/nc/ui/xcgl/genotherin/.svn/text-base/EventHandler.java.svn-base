package nc.ui.xcgl.genotherin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.genotherin.AggGenotherinVO;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected void onBoSave() throws Exception {
		AggGenotherinVO billvo=( AggGenotherinVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
		
		
	}
	

	@Override
	public void onBillRef() throws Exception {
		try {
			super.onBillRef();
			if (PfUtilClient.isCloseOK()) {
				getBillUI().setBillOperate(IBillOperate.OP_REFADD);
				getBillUI().setDefaultData();
				getBillCardPanel().setBodyMenuShow(false);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 参照完成之后还原参照按钮tag
			ButtonObject btn = getButtonManager()
					.getButton(IBillButton.Refbill);
			btn.setTag(String.valueOf(IBillButton.Refbill));
		}
	}
	@Override
	protected void onBoLineDel() throws Exception {	
		List list=new ArrayList();
		Map<String,String> map=new HashMap<String,String>();
		/**
		 * 表体选择的行的数据
		 */
		CircularlyAccessibleValueObject[] bvos = getBillCardPanelWrapper().getSelectedBodyVOs();
		/**
		 * 表体所有的行的数据
		 */
		CircularlyAccessibleValueObject[] bodyvos = 
    		getBillCardPanelWrapper().getBillCardPanel().getBillModel().getBodyValueVOs(
    				getUIController().getBillVoName()[2]);
		if(bvos!=null&&bvos.length!=0){
			  for(int i=0;i<bvos.length;i++){
				  map.put(PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("crowno")),
						  PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("crowno")));
				  //list.add(PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("crowno")));
			  }
		}
		if(bodyvos!=null&&bodyvos.length!=0){
			for(int j=0;j<bodyvos.length;j++){
				if(map.containsKey(PuPubVO.getString_TrimZeroLenAsNull(
						bodyvos[j].getAttributeValue("vcrowno")))){
					list.add(j);
				}
			}
		}
		int[] a=listtoInt(list);
		if(a!=null&&a.length!=0){
			//删除多行
			getBillCardPanel().getBillModel().delLine(a);
		}
		super.onBoLineDel();
		
	}
	private int[] listtoInt(List list) {
		int[] a;
		if(list==null||list.size()==0){
			return null;
		}
		else{
			a=new int[list.size()];
			for(int i=0;i<list.size();i++){
				a[i]=PuPubVO.getInteger_NullAs(list.get(i), -1);
			}
			return a;
		}
	}
	
	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
	}
	
}
