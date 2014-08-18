package nc.ui.xcgl.pub.bill;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.itf.zmpub.pub.ISonVO;
import nc.itf.zmpub.pub.ISonVOH;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.zmpub.pub.bill.FlowManageEventHandler;
import nc.ui.zmpub.pub.bill.SonDefBillManageUI;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.itf.ISonExtendVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
import nc.vo.zmpub.pub.bill.HYChildSuperVO;
public class XCExtFlowManageEventHandler extends FlowManageEventHandler{
	public XCExtFlowManageEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		if(getBillUI() instanceof XCSonExtDefBillManageUI){
			 if(getBillUI()!= null && intBtn == PuBtnConst.ckmx){
				 quotaQuery1();
			 }
		}
		super.onBoElse(intBtn);
	}	
	/**
	 * 查看孙表明细
	 * @throws Exception
	 */
	protected void quotaQuery1() throws Exception{
		ClientLink cl = new ClientLink(ClientEnvironment.getInstance());
		String corp = PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_corp"));
		XCSonExtDefBillManageUI ui = (XCSonExtDefBillManageUI) getBillUI();		
		XCExtQueryUI qui=new XCExtQueryUI(ui.getSonQueryBillType1(), cl.getUser(), corp, null, ui, false,getSonQueryDigTile1());
		qui.showModal();
	}
	/**
	 * 孙表查看明细对话框标题
	 * @return
	 */
	public  String getSonQueryDigTile1(){
	  return "明细查看";	
	}
	/**
	 * 复制孙表数据
	 * @throws Exception 
	 */
	public void copySonDatas() throws Exception {
		super.copySonDatas();
		if(getBillManageUI() instanceof XCSonExtDefBillManageUI){
			XCSonExtDefBillManageUI ui=(XCSonExtDefBillManageUI) getBillManageUI();		    
			Map<String, Map<String,SuperVO>> map=(Map<String, Map<String, SuperVO>>) ObjectUtils.serializableClone(ui.getHl12());
			Map<String, Map<String,SuperVO>> cmap=new HashMap<String, Map<String,SuperVO>> ();
			if(map==null||map.size()==0)
				return;
			for(String key:map.keySet()){				
				String crowno=key.substring(0,key.length()-20);
				String nkey=crowno+"null";
				Map<String,SuperVO> cnmap=new HashMap<String, SuperVO>();
				cmap.put(nkey, cnmap);
				
				Map<String,SuperVO> smap=map.get(key);
				if(smap!=null&&smap.size()>0){
					for(String skey:smap.keySet()){
						String srowno=skey.substring(20, skey.length());
						String nskey="null"+srowno;
						HYChildSuperVO svo=(HYChildSuperVO) smap.get(skey);
						svo.setAttributeValue(svo.getParentPKFieldName(), null);
						svo.setStatus(VOStatus.NEW);
						svo.setAttributeValue(svo.getPKFieldName(), null);
						cnmap.put(nskey,svo);
					}
				}	
			}	
			ui.setHl12(cmap);
		}
	}
	protected void onBoCancel() throws Exception {
		String billcode = null;
		String pk_billtype = null;
		String pk_corp = null;
		if (isAdding()) {
			billcode = (String) getBillCardPanelWrapper().getBillCardPanel()
					.getHeadItem("vbillno").getValueObject();
			pk_billtype = getBillManageUI().getUIControl().getBillType();
			pk_corp = _getCorp().getPrimaryKey();
		}
		
		if (getBufferData().isVOBufferEmpty()) {
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
			if(getBillUI() instanceof SonDefBillManageUI){
				SonDefBillManageUI ui=(SonDefBillManageUI) getBillUI();
				if(ui.getHl()!=null)
				ui.getHl().clear();
				if(ui.getHl1()!=null)
				ui.getHl1().clear();
			}
			if(getBillUI() instanceof XCSonExtDefBillManageUI){
				XCSonExtDefBillManageUI ui=(XCSonExtDefBillManageUI) getBillUI();
				if(ui.getHl12()!=null)
				ui.getHl12().clear();
				if(ui.getSonMap1()!=null)
				ui.getSonMap1().clear();
			}
			
		} else {
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
			
            if(getBufferData().getCurrentRow()==-1){
               getBufferData().setCurrentRow(0);
            }else{
               getBufferData().setCurrentRow(getBufferData().getCurrentRow());
            }			
			
		}

		if (billcode != null && !"".equals(billcode)) {
			returnBillNo(billcode, pk_billtype, pk_corp);
		}
	}
	/**
	 * 将来也孙表信息设置到目的孙表信息 子类实现
	 * @param list
	 * @return
	 */
	public List changeSon1(List list) {
		
		return list;
	}
	/**
     * 数据交换设置孙表信息
     * @throws Exception 
     */
	public void setSonDatas() throws Exception {
	    HYBillVO sbill=(HYBillVO) PfUtilClient.getRetOldVo();
	    HYBillVO tbill=(HYBillVO) getBillManageUI().getVOFromUI();
	    Map<String, Map<String,SuperVO>> map=new HashMap<String, Map<String,SuperVO>>();
	    if(sbill instanceof ISonVOH && tbill instanceof ISonVOH ){
	    	if(sbill.getChildrenVO()!=null&&sbill.getChildrenVO().length>0){
	    		for(int i=0;i<sbill.getChildrenVO().length;i++){
	    			ISonVO bvo=(ISonVO) sbill.getChildrenVO()[i];
	    			List list=bvo.getSonVOS();
	    			List dlist=changeSon(list);
	    			String crowno=(String) tbill.getChildrenVO()[i].getAttributeValue("crowno");
	    			String pk=null;
	    			String key=crowno+pk;
	    			Map<String,SuperVO> smap=new HashMap<String,SuperVO>();
	    			if(dlist!=null&&dlist.size()>0){
	    				for(int j=0;j<dlist.size();j++){
	    					HYChildSuperVO sbvo=(HYChildSuperVO) dlist.get(j);
	    					sbvo.setPrimaryKey(null);
	    					sbvo.setAttributeValue(sbvo.getParentPKFieldName(), null);
	    					sbvo.setStatus(VOStatus.NEW);
	    					String skey=sbvo.getPrimaryKey()+sbvo.getCrowno();
	    					smap.put(skey, sbvo);
	    				}
	    			}
	    			map.put(key, smap);
	    		}
	    		if(getBillManageUI() instanceof SonDefBillManageUI){
	    			((SonDefBillManageUI)getBillManageUI()).setHl1(map);
	    		}
	    	}
	    }
	    //设置第二种孙表
	    Map<String, Map<String,SuperVO>> map1=new HashMap<String, Map<String,SuperVO>>();
	    if(sbill instanceof ISonExtendVOH && tbill instanceof ISonExtendVOH ){
	    	if(sbill.getChildrenVO()!=null&&sbill.getChildrenVO().length>0){
	    		for(int i=0;i<sbill.getChildrenVO().length;i++){
	    			ISonExtendVO bvo=(ISonExtendVO) sbill.getChildrenVO()[i];
	    			List list=bvo.getSonVOS1();
	    			List dlist=changeSon(list);
	    			String crowno=(String) tbill.getChildrenVO()[i].getAttributeValue("crowno");
	    			String pk=null;
	    			String key=crowno+pk;
	    			Map<String,SuperVO> smap=new HashMap<String,SuperVO>();
	    			if(dlist!=null&&dlist.size()>0){
	    				for(int j=0;j<dlist.size();j++){
	    					HYChildSuperVO sbvo=(HYChildSuperVO) dlist.get(j);
	    					sbvo.setPrimaryKey(null);
	    					sbvo.setAttributeValue(sbvo.getParentPKFieldName(), null);
	    					sbvo.setStatus(VOStatus.NEW);
	    					String skey=sbvo.getPrimaryKey()+sbvo.getCrowno();
	    					smap.put(skey, sbvo);
	    				}
	    			}
	    			map1.put(key, smap);
	    		}
	    		if(getBillManageUI() instanceof XCSonExtDefBillManageUI){
	    			((XCSonExtDefBillManageUI)getBillManageUI()).setHl12(map1);
	    		}
	    	}
	    }
	}
}
