package nc.bs.xcgl.saleshareout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.assayres.AssayResBVO;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
import nc.vo.xcgl.saleshareout.AggSaleShareoutVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBBVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBVO;
import nc.vo.xcgl.saleshareout.SaleShareoutHVO;
/**
 * plan solution
 * @author mlr
 *
 */
public class PlanSolutionBO {
	FindGradeBySource fg=new FindGradeBySource();
	SaveData billsave=new SaveData();
	public void planSolution(AggSaleShareoutVO shareBillVo) throws Exception{
		if(shareBillVo==null || shareBillVo.getChildrenVO()==null || shareBillVo.getChildrenVO().length<=0){
			return ;
		}
		//get flour information
		SaleShareoutBVO[] mbvos=(SaleShareoutBVO[]) shareBillVo.getChildrenVO();		
		for(int i=0;i<mbvos.length;i++){
			SaleShareoutBVO mbvo=mbvos[i];		
			AggSaleShareoutVO billvo=new AggSaleShareoutVO();
			billvo.setParentVO(shareBillVo.getParentVO());
			billvo.setChildrenVO(new SaleShareoutBVO[]{mbvo});			
			Map<String,List<AssayResBVO>> indexresmap=fg.findGrade(billvo);
			HYBillVO dsharebillvo= fixdata(indexresmap,billvo);			
			List<SaleShareoutBBVO> dbvos=calPlanSolution(dsharebillvo);
			
			HYBillVO[] savebillvos=createSaveBillVOS(dsharebillvo,dbvos);
			
				billsave.savefixvo(savebillvos);		
//			HYBillVO savebillvo=new HYBillVO();
//			savebillvo.setParentVO(mbvo);
//			savebillvo.setChildrenVO(dbvos.toArray(new SaleShareoutBBVO[0]));
			
		}			
	}
	/**
	 * create save billvo
	 * @param dsharebillvo
	 * @param dbvos
	 * @return
	 */
	public HYBillVO[] createSaveBillVOS(HYBillVO dsharebillvo,
			List<SaleShareoutBBVO> dbvos) {
	    		
		SaleShareoutBVO[] bvos=(SaleShareoutBVO[]) dsharebillvo.getChildrenVO();
		HYBillVO[] savebillvos=new HYBillVO[bvos.length];
		for(int i=0;i<bvos.length;i++){
			savebillvos[i]=new HYBillVO();
			savebillvos[i].setParentVO(bvos[i]);			
			List<SaleShareoutBBVO> slist=getSaleShareoutBBVOByInvId(dbvos,bvos[i].getPk_invmandoc());
			if(slist!=null && slist.size()>0){
				savebillvos[i].setChildrenVO(slist.toArray(new SaleShareoutBBVO[0]));
			}					
		}					
		return savebillvos;
	}

	public List<SaleShareoutBBVO> getSaleShareoutBBVOByInvId(
			List<SaleShareoutBBVO> dbvos, String pk_invmandoc) {

		if(dbvos==null || dbvos.size()==0 || pk_invmandoc==null){
			return null;
		}else{
			List<SaleShareoutBBVO>  list=new ArrayList<SaleShareoutBBVO>();
			for(int i=0;i<dbvos.size();i++){
				if(pk_invmandoc.equals(dbvos.get(i).getPk_invmandoc())){
					list.add(dbvos.get(i));
				}
			}
			return list;
		}
	}
	public List<SaleShareoutBBVO> calPlanSolution(HYBillVO dsharebillvo) throws Exception {
        if(dsharebillvo==null || dsharebillvo.getChildrenVO()==null || dsharebillvo.getChildrenVO().length==0){
        	return null;       	
        }    
        SaleShareoutBVO[] bvos=(SaleShareoutBVO[]) dsharebillvo.getChildrenVO();
        Map<String,UFDouble> shareRadioMap=calShareRadioMap(bvos);
               
        SaleShareoutHVO hvo=(SaleShareoutHVO) dsharebillvo.getParentVO();      
        PubStockOnHandVO onhandvo=new PubStockOnHandVO();
        onhandvo.setPk_corp(hvo.getPk_corp());
        onhandvo.setPk_factory(hvo.getPk_factory());
        onhandvo.setPk_father(bvos[0].getPk_invmandoc());
        ArrayList<SuperVO[]>  onlist=BillStockTool.getInstrance().queryStockDetail(new PubStockOnHandVO[]{onhandvo});
        
        SuperVO[] onvos=onlist.get(0);
        if(onvos==null || onvos.length==0){
        	return null;
        }
        //spilt stock  pk_father
        SuperVO[][] onvoss=(SuperVO[][]) SplitBillVOs.getSplitVOs(onvos, new String[]{"pk_stordoc"});       
        List<PubStockOnHandVO[]> list=new ArrayList<PubStockOnHandVO[]>();       
        List<Map<String,UFDouble>> shareRadioList=new ArrayList<Map<String,UFDouble>>();
         
        for(int i=0;i<onvoss.length;i++){
        	SuperVO[] xonvos=onvoss[i];
        	PubStockOnHandVO[] wvos=new PubStockOnHandVO[xonvos.length];
        	for(int j=0;j<xonvos.length;j++){
        		PubStockOnHandVO xonvo=(PubStockOnHandVO) xonvos[j];
        		wvos[j]=xonvo;
        	}
        	list.add(wvos);        	
        	shareRadioList.add(calShareRadioMap(wvos));        	
        }
        //cal    
        List<SaleShareoutBBVO> reslist=calPlanSolution(dsharebillvo,shareRadioMap,list,shareRadioList);       
		return reslist;
	}

	public List<SaleShareoutBBVO> calPlanSolution(HYBillVO dsharebillvo,
			Map<String, UFDouble> dshareRadioMap, List<PubStockOnHandVO[]> onlist,
			List<Map<String, UFDouble>> onshareRadioList) {
		
		SaleShareoutHVO dhvo=(SaleShareoutHVO) dsharebillvo.getParentVO();
			
	    SaleShareoutBVO[] dbvos=(SaleShareoutBVO[]) dsharebillvo.getChildrenVO();
	    
	    SaleShareoutBVO dmainvo=dbvos[0];
	    
	    UFDouble dnum=PuPubVO.getUFDouble_NullAsZero(dmainvo.getNdryweight());
	    	    
	    List<SaleShareoutBBVO> list=new ArrayList<SaleShareoutBBVO>();  
	      
	    while(dnum.doubleValue()>0 && onlist.size()>0){	    	
	    	int mindex=0;	    	
	    	UFDouble minndeviation=null;
	    	//get min index
	    	for(int i=0;i<onshareRadioList.size();i++){
	    		Map<String, UFDouble> onshareRadio=onshareRadioList.get(i);
	    		UFDouble ndeviation=new UFDouble(0);
	    		for(String key:dshareRadioMap.keySet()){
	    			UFDouble  dradio=PuPubVO.getUFDouble_NullAsZero(dshareRadioMap.get(key));	    			
	    		    UFDouble  onradio=PuPubVO.getUFDouble_NullAsZero(onshareRadio.get(key));	    		    
	    		    UFDouble  ndevi=onradio.sub(dradio);	    		    
	    		    ndeviation=ndeviation.add(ndevi.abs());	    		    
	    		}
	    		if(minndeviation==null){
	    			mindex=i;
	    			minndeviation=ndeviation;
	    		}else{
	    		   if((ndeviation.sub(minndeviation)).doubleValue()<0){
	    				mindex=i;
		    			minndeviation=ndeviation;
	    		   }	
	    		}	    		
	    		if(i==onshareRadioList.size()-1){
	    			//share num and create SaleShareoutBBVO
	    			PubStockOnHandVO[] onvos=onlist.get(mindex);	    			
	    			PubStockOnHandVO monvo=getFlourData(onvos);	    			
	    			if((dnum.sub(PuPubVO.getUFDouble_NullAsZero(monvo.getNnum()))).doubleValue()>=0){	    				
	    				for(int j=0;j<onvos.length;j++){
	    					SaleShareoutBBVO bbvo=new SaleShareoutBBVO();		    				
		    				bbvo.setPk_invmandoc(onvos[j].getPk_invmandoc());
		    				bbvo.setPk_invbasdoc(onvos[j].getPk_invbasdoc());	    				
		    				bbvo.setPk_father(onvos[j].getPk_father());
		    				bbvo.setPk_stordoc(onvos[j].getPk_stordoc()); 		
		    				bbvo.setNdryweight(onvos[j].getNnum());		    			 				
		    				list.add(bbvo);
	    				}    
	    				//rest  dnum 
	    				dnum=dnum.sub(PuPubVO.getUFDouble_NullAsZero(monvo.getNnum()));
	    			}else{
	    				UFDouble radio=dnum.div(monvo.getNnum());
	    				for(int j=0;j<onvos.length;j++){
	    					SaleShareoutBBVO bbvo=new SaleShareoutBBVO();		    				
		    				bbvo.setPk_invmandoc(onvos[j].getPk_invmandoc());
		    				bbvo.setPk_invbasdoc(onvos[j].getPk_invbasdoc());	    				
		    				bbvo.setPk_father(onvos[j].getPk_father());
		    				bbvo.setPk_stordoc(onvos[j].getPk_stordoc()); 				
		    				bbvo.setNdryweight((PuPubVO.getUFDouble_NullAsZero(onvos[j].getNnum())).multiply(radio));
		    				list.add(bbvo);
	    				} 
	    				//rest  dnum 
        				dnum=new UFDouble(0);
	    			}	    			
	    			//remove onlist by mindex
	    			onlist.remove(mindex);
	    			onshareRadioList.remove(mindex);	    				    			
	    		}
	    	}    	
	    } 
		return list;
	}

	public Map<String, UFDouble> calShareRadioMap(PubStockOnHandVO[] bvos) {
		PubStockOnHandVO mainbvo=getFlourData(bvos);
		UFDouble zuf=PuPubVO.getUFDouble_NullAsZero(mainbvo.getNnum());
		if(zuf.doubleValue()<=0){
			return null;
		}
		Map<String, UFDouble> ramap=new HashMap<String, UFDouble>();
		for(int i=1;i<bvos.length;i++){
			PubStockOnHandVO bvo=bvos[i];			
			UFDouble uf=PuPubVO.getUFDouble_NullAsZero(bvo.getNnum());
			UFDouble radio=uf.div(zuf);
			ramap.put(bvo.getPk_invmandoc(), radio);
		}	
		return ramap;
	}

	public PubStockOnHandVO getFlourData(PubStockOnHandVO[] bvos) {
	    for(int i=0;i<bvos.length;i++){
	       if(bvos[i].getPk_invmandoc().equals(bvos[i].getPk_father())){
	    	   return bvos[i];
	       }	
	    }
		return null;
	}

	public Map<String, UFDouble> calShareRadioMap(SaleShareoutBVO[] bvos) {
		SaleShareoutBVO mainbvo=bvos[0];
		UFDouble zuf=PuPubVO.getUFDouble_NullAsZero(mainbvo.getNdryweight());
		if(zuf.doubleValue()<=0){
			return null;
		}
		Map<String, UFDouble> ramap=new HashMap<String, UFDouble>();
		for(int i=1;i<bvos.length;i++){
			SaleShareoutBVO bvo=bvos[i];
			UFDouble uf=PuPubVO.getUFDouble_NullAsZero(bvo.getNdryweight());
			UFDouble radio=uf.div(zuf);
			ramap.put(bvo.getPk_invmandoc(), radio);
		}	
		return ramap;
	}

	/**
     *对数据进行重新组合
     *即按照单据的上游信息取到化验结果各个指标的品味信息
     *然后在精粉下面带出金属信息
	 * @throws Exception 
     */
	public HYBillVO fixdata(Map<String, List<AssayResBVO>> assayresmap,
			HYBillVO billvo) throws Exception {
		List<SaleShareoutBVO> list=new ArrayList<SaleShareoutBVO>();
		SaleShareoutBVO[] bodyvos = (SaleShareoutBVO[]) billvo.getChildrenVO();
		if(billvo==null||bodyvos==null||bodyvos.length==0){
			throw new Exception("单据数据为空，或单据表体数据为空！");
		}
		if(assayresmap==null||assayresmap.size()==0){
			throw new Exception("精粉化验结果为空！");
		}
		HYBillVO fixbillvo=new HYBillVO();
		fixbillvo.setParentVO(billvo.getParentVO());
		for(int i=0;i<bodyvos.length;i++){
			bodyvos[i].setAttributeValue("nwetweight", bodyvos[i].getAttributeValue("ndryweight"));
			bodyvos[i].setAttributeValue("ngrossweight", UFDouble.ZERO_DBL);
			bodyvos[i].setAttributeValue("pk_father", bodyvos[i].getAttributeValue("pk_invmandoc"));
			list.add(bodyvos[i]);
			UFDouble dryweight=PuPubVO.getUFDouble_ZeroAsNull(bodyvos[i].getNdryweight());//取到干重
			List<AssayResBVO> gradelist = assayresmap.get(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillid())+
					PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillrowid()));
			if(gradelist!=null&&gradelist.size()!=0){
				//SaleShareoutBVO[] bvos=new SaleShareoutBVO[gradelist.size()];
				for(int j=0;j<gradelist.size();j++){
					SaleShareoutBVO salevo=new SaleShareoutBVO();
					AssayResBVO bvo=gradelist.get(j);
					salevo.setPk_father(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getPk_invmandoc()));
					salevo.setCrowno(PuPubVO.getString_TrimZeroLenAsNull((i+1)*10+j+1));
					salevo.setVlastbillcode(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillcode()));
					salevo.setVlastbillid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillid()));
					salevo.setVlastbillrowid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillrowid()));
					salevo.setVlastbilltype(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbilltype()));
					salevo.setVsourcebillid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVsourcebillid()));
					salevo.setVsourcebillrowid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVsourcebillrowid()));
					salevo.setVsourcebilltype(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVsourcebilltype()));
					salevo.setCsourcebillcode(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getCsourcebillcode()));
					salevo.setPk_general_h(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getPk_general_h()));
					salevo.setPk_general_b(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getPk_general_b()));
					salevo.setCcode(PuPubVO.getString_TrimZeroLenAsNull(billvo.getParentVO().getAttributeValue("vbillno")));
					salevo.setPk_invmandoc(PuPubVO.getString_TrimZeroLenAsNull(bvo.getPk_invmandoc()));
					salevo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(bvo.getPk_invbasdoc()));
					salevo.setVcrowno(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getCrowno()));
					salevo.setNdryweight(PuPubVO.getUFDouble_NullAsZero(dryweight.multiply(bvo.getNgrade().div(100))));
					salevo.setNwetweight(PuPubVO.getUFDouble_NullAsZero(dryweight.multiply(bvo.getNgrade().div(100))));
					salevo.setNgrossweight(UFDouble.ZERO_DBL);
					list.add(salevo);
				}
			}
		}
		fixbillvo.setChildrenVO(list.toArray(new SaleShareoutBVO[0]));
		return fixbillvo;
	}
}
