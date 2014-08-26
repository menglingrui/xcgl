package nc.ui.xcgl.flouryield;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.flouryield.AggFlouryieldVO;
import nc.vo.xcgl.flouryield.FlouryieldBVO;
import nc.vo.xcgl.genprcessout.GenPrcOutBVO;
import nc.vo.xcgl.genprcessout.GenPrcOutHVO;
import nc.vo.xcgl.pub.bill.CalYieldVO;
import nc.vo.xcgl.pub.bill.ProSetEnum;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.helper.CalProduceHelper;
import nc.vo.xcgl.pub.tool.CalProduceTool;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
	    if(intBtn==PuBtnConst.calYield){
	    	doCalYield();	    	
	    
	    }
	}
	/**
	 * 计算产量
	 */
	public void doCalYield() {
        try {       
            ProSetParaVO[] paras=createParas();    
            //Map<String,List<ProSetFormulaVO>> formap=CalProduceHelper.calPowderYield1(paras);
            //FlouryieldBVO[] vos=CalProduceHelper.parseFormulaVO(formap, paras);
            Map<String, Map<ProSetEnum, List<CalYieldVO>>> topMap=CalProduceHelper.calPowderYield(paras);
            FlouryieldBVO[] vos=new CalProduceTool().parseTopMap(topMap, paras);
//            // delete pre lines
            int count1=getBillCardPanel().getRowCount();
            int[] rows=new int[count1];
            for(int i=0;i<rows.length;i++){
            	rows[i]=i;
            }
            getBillCardPanel().getBillModel().delLine(rows);
             //all lines  set body value
            if(vos!=null && vos.length>0){
            	for(int i=0;i<vos.length;i++){
            		try {
						getBillCardPanelWrapper().addLine();
						for(int j=0;j<vos[i].getAttributeNames().length;j++){
						   if("crowno".equals(vos[i].getAttributeNames()[j])){
						      continue;
						    }
					       getBillCardPanel().setBodyValueAt(				    
					       vos[i].getAttributeValue(vos[i].getAttributeNames()[j]), i, vos[i].getAttributeNames()[j]);
						}
					     	
					} catch (Exception e) {
					//	getBillUI().showErrorMessage(e.getMessage());
						e.printStackTrace();
					}
            		
            	}
            }
            
   //     	getBillCardPanel().getBillModel().setBodyDataVO(vos);		
    		int count=getBillCardPanel().getRowCount();
    		int rownum=0;
    		for(int i=0;i<count;i++){
    			getBillCardPanel().getBillModel().execEditFormulas(i);	
    			getBillCardPanel().getBillModel().execLoadFormula();
    		}
		} catch (BusinessException e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(e.getMessage());
			return ;
		}		
	}
	/**
	 * 创建生产流程参数
	 * @return
	 * @throws BusinessException 
	 */
	public ProSetParaVO[] createParas() throws BusinessException {	       
	            String pk_corp=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_corp").getValueObject());	
	            String pk_factory=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_factory").getValueObject());		
		        String pk_beltline=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_beltline").getValueObject());		
		        String pk_classorder=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_classorder").getValueObject());	
		        String pk_minetype=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vreserve1").getValueObject());	
		        UFDate dbilldate=PuPubVO.getUFDate(getBillCardPanel().getHeadItem("dbilldate").getValueObject()); 
		        //关联原矿矿石
		   //     String pk_invmandoc=(String) getBillCardPanel().getHeadItem("vdef20").getValueObject();
		        
		        GenPrcOutHVO[] hvos= (GenPrcOutHVO[]) HYPubBO_Client.queryByCondition(GenPrcOutHVO.class, " isnull(dr,0)=0 and pk_corp='"+pk_corp+"'" +
		        " and pk_factory='"+pk_factory+"' and pk_beltline='"+pk_beltline+"'" +
		        " and pk_classorder='"+pk_classorder+"' and dbilldate='"+dbilldate.toString()+"'" +
		        " and vreserve1='"+pk_minetype+"'");
	            if(hvos==null || hvos.length==0){
	            	throw new BusinessException("没有原矿加工出库单");
	            }	            
	            HashMap<String,Map<String,String>>  mines=new HashMap<String, Map<String,String>>();
	            for(int i=0;i<hvos.length;i++){	            	
	            	if(mines.get(hvos[i].getPk_minarea())==null){
	            		mines.put(hvos[i].getPk_minarea(), new HashMap<String, String>());
	            	}
	            	
			        GenPrcOutBVO[] bvos= (GenPrcOutBVO[]) HYPubBO_Client.queryByCondition(GenPrcOutBVO.class, 
			        		" isnull(dr,0)=0 and pk_general_h='"+hvos[i].getPrimaryKey()+"'" );
			        if(bvos!=null && bvos.length>0){
			        	for(int j=0;j<bvos.length;j++){
			        		mines.get(hvos[i].getPk_minarea()).put(bvos[j].getPk_invmandoc(), bvos[j].getPk_invmandoc());
			        	}			        	
			        }
	            }
	            List<ProSetParaVO> list=new ArrayList<ProSetParaVO>();            
	            for(String key:mines.keySet()){
	            	 if(mines.get(key)!=null&&mines.get(key).size()>0){
	            		 for(String bkey:mines.get(key).keySet()){
	            			 ProSetParaVO para=createPara();	
	    	            	 para.setPk_minarea(key);
	    	            	 para.setPk_invmandoc(bkey);
	    	            	 list.add(para);
	            		 }
	            	 }
	            }	         
		return list.toArray(new ProSetParaVO[0]);
	}
	/**
	 * 创建生产流程参数
	 * @return
	 */
	public ProSetParaVO createPara() {
		    ProSetParaVO para=new ProSetParaVO();
	        String pk_factory=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_factory").getValueObject());		
	        String pk_beltline=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_beltline").getValueObject());		
	        String pk_classorder=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_classorder").getValueObject());	
	        UFDate dbilldate=PuPubVO.getUFDate(getBillCardPanel().getHeadItem("dbilldate").getValueObject());       
	        String pk_minetype=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vreserve1").getValueObject());
	        //关联原矿矿石
//	        String pk_invmandoc=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vdef20").getValueObject());
	        para.setPk_minetype(pk_minetype);
	        para.setPk_factory(pk_factory);
	        para.setPk_beltline(pk_beltline);
	        para.setPk_classorder(pk_classorder);
//	        para.setPk_invmandoc(pk_invmandoc);
	        para.setPk_corp(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());	        
	        para.setDbilldate(dbilldate);
		return para;
	}
	
	protected void onBoSave() throws Exception {
		AggFlouryieldVO billvo=(AggFlouryieldVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
			FlouryieldBVO []bvos=(FlouryieldBVO[])billvo.getChildrenVO();
		//	BsBeforeSaveValudate.beforeSaveBodyUnique(bvos,new String[]{"pk_invmandoc"},new String[]{"存货"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
}
