package nc.ui.xcgl.salepresettle;

import java.util.ArrayList;
import java.util.List;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.bill.DefBillManageUI;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;

/**
 * 销售预结算
 * @author yangtao
 * @date 2014-3-26 下午03:39:25
 */
public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
    
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		DefBillManageUI ui = null;
		if(getBillUI() instanceof DefBillManageUI){
			ui = ((DefBillManageUI) getBillUI());
			 if(ui != null && intBtn == PuBtnConst.btn_redassayres){
				 ReadAssayres();//读化验结果。。。。yangtao
			 }
			 if(ui != null && intBtn == PuBtnConst.btn_countPrice){
				 CountPrice();//计算价格。。。。yangtao
			 }
		}
	}
    
	/**
	 * 取化验结果
	 * 
	 * @author yangtao
	 * @date 2014-3-26 下午03:47:08
	 */
	public  void ReadAssayres() throws Exception {
	
		super.ReadAssayres(true);
		
	}


	 /**
     * 计算价格
     * 
     * @author yangtao
     * @date 2014-3-27 下午05:32:31
     */
	public void CountPrice()throws Exception{
		super.CountPrice(true);
	}
	
	protected void onBoSave() throws Exception {
		AggSalepresettleVO billvo=(AggSalepresettleVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
//			SalepresettleBVO []bvos=(SalepresettleBVO[])billvo.getChildrenVO();
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{""}, new String[]{""});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
	
	
	/**
	 * 进行合单
	 * 
	 * nc.vo.pub.AggregatedValueObject
	 */
	protected AggregatedValueObject refVOChange(AggregatedValueObject[] vos)
			throws Exception {
		List<SalepresettleBVO> list=new ArrayList<SalepresettleBVO>();
		
		for(int i=0;i<vos.length;i++){
		    SuperVO[] bvos=(SuperVO[]) vos[i].getChildrenVO();
			if(bvos==null || bvos.length==0)
				continue;
			for(int j=0;j<bvos.length;j++){
				list.add((SalepresettleBVO) bvos[j]);
			}
		}
		vos[0].setChildrenVO(list.toArray(new SalepresettleBVO[0]));	
		return vos[0];
	}
	
	
}
