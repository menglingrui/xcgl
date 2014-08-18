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
 * ����Ԥ����
 * @author yangtao
 * @date 2014-3-26 ����03:39:25
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
				 ReadAssayres();//����������������yangtao
			 }
			 if(ui != null && intBtn == PuBtnConst.btn_countPrice){
				 CountPrice();//����۸񡣡�����yangtao
			 }
		}
	}
    
	/**
	 * ȡ������
	 * 
	 * @author yangtao
	 * @date 2014-3-26 ����03:47:08
	 */
	public  void ReadAssayres() throws Exception {
	
		super.ReadAssayres(true);
		
	}


	 /**
     * ����۸�
     * 
     * @author yangtao
     * @date 2014-3-27 ����05:32:31
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
			throw new Exception("���岻��Ϊ��");
		super.onBoSave();
	}
	
	
	/**
	 * ���кϵ�
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
