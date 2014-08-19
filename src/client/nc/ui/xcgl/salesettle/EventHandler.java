package nc.ui.xcgl.salesettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.pub.print.version55.print.output.excel.core.ExcelException;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.bill.DefBillManageUI;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;

/**
 * 销售结算
 * @author yangtao
 * @date 2014-3-27 下午02:23:37
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
				 CountPrice();
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
	    super.ReadAssayres(false);		
	}
	
    /**
     * 计算价格
     * 
     * @author yangtao
     * @throws Exception 
     * @date 2014-3-27 下午05:32:31
     */
	public void CountPrice() throws Exception{
		super.CountPrice(false);
	}

	protected void onBoSave() throws Exception {
		AggSalepresettleVO billvo=(AggSalepresettleVO) getBillUI().getVOFromUI();
		dealZC(billvo);
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
	
	public void dealZC(AggSalepresettleVO billvo) throws BusinessException {
		if(billvo==null || billvo.getChildrenVO()==null || billvo.getChildrenVO().length==0){
			return;
		}
		SalepresettleBVO[] bvos=(SalepresettleBVO[]) billvo.getChildrenVO();
		SalepresettleBVO[][] bvoss=(SalepresettleBVO[][]) SplitBillVOs.getSplitVOs(bvos, new String[]{"vreserve2"});
		Map<String,UFDouble>  map=new HashMap<String, UFDouble>();
		
		for(int i=0;i<bvoss.length;i++){
			SalepresettleBVO[] svos=bvoss[i];
			if(svos==null || svos.length==0){
				continue;
			}
			UFDouble zc=new UFDouble(0);
			String pk_invmandoc=null;
			for(int j=0;j<svos.length;j++){
				if(PuPubVO.getUFBoolean_NullAs(svos[j].getUreserve2(), UFBoolean.FALSE).booleanValue()==false){
					zc=zc.add(PuPubVO.getUFDouble_NullAsZero(svos[j].getNreserve1()));
				}else{
					pk_invmandoc=svos[j].getPk_invmandoc();
				}
			}
			map.put(pk_invmandoc, zc);
		}	
		
		int count=getBillCardPanel().getRowCount();
		for(String key:map.keySet()){
			UFDouble zc=PuPubVO.getUFDouble_NullAsZero(map.get(key));
			if(zc.doubleValue()==0){
				continue;
			}
			for(int i=0;i<count;i++){
				if(key.equals(getBillCardPanel().getBodyValueAt(i, "pk_invmandoc"))){
					if(PuPubVO.getUFBoolean_NullAs(getBillCardPanel().getBodyValueAt(i, "ureserve1"), UFBoolean.FALSE).booleanValue()==false){
						getBillCardPanel().getBillModel().setValueAt(zc, i, "nreserve8");
						String sql=" update xcgl_salepresettle_b b set b.nreserve8="+ zc+" " +
								" where b.pk_salepresettle_b='"+getBillCardPanel().getBodyValueAt(i, "pk_salepresettle_b")+"'";
						QueryHelper.executeUpdate(sql);
					}
				}
			}
			
		}
	
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
