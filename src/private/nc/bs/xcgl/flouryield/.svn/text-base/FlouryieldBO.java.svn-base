package nc.bs.xcgl.flouryield;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.util.SQLHelper;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.flouryield.AggFlouryieldVO;
import nc.vo.xcgl.flouryield.FlouryieldBVO;
import nc.vo.xcgl.flouryield.FlouryieldHVO;
import nc.vo.xcgl.genconcentratein.AggConcentrateinVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.zmpub.pub.tool.ZmPubTool;
public class FlouryieldBO {
	/**
	 * 传到精矿入库
	 * @param billvo
	 * @param pfvo
	 * @throws Exception
	 */
	public void sendMessage(AggregatedValueObject billvo,PfParameterVO pfvo) throws Exception{
		AggConcentrateinVO aggvo= new AggConcentrateinVO();
		if(billvo==null || billvo.getParentVO()==null) {
			return;
		}else{
		    if(billvo.getChildrenVO()==null || billvo.getChildrenVO().length==0){
		    	return ;
		    }
		    FlouryieldHVO conhvo=(FlouryieldHVO) billvo.getParentVO();
		    String corp=conhvo.getPk_corp();
		    AggregatedValueObject[] bills=SplitBillVOs.getSplitVO(AggFlouryieldVO.class.getName(), FlouryieldHVO.class.getName(),FlouryieldBVO.class.getName(),
    		(AggregatedValueObject)ObjectUtils.serializableClone(billvo), new String[]{"pk_factory","pk_beltline","pk_classorder"}, new String[]{"pk_deptdoc","pk_stordoc"});
		    if(bills!=null&&bills.length!=0){
		    for(int j=0;j<bills.length;j++){
		    FlouryieldBVO[] bvos=(FlouryieldBVO[]) bills[j].getChildrenVO();
		    //bodys splits by pk_invmandoc,itype
		    FlouryieldBVO[][] bbvos=(FlouryieldBVO[][]) SplitBillVOs.getSplitVOs(bvos, new String[]{"pk_invmandoc","itype"});
		    List<FlouryieldBVO> list=new ArrayList<FlouryieldBVO>();
		    int count=0;
		    if(bbvos!=null&&bbvos.length>0){
		    	for(int i=0;i<bbvos.length;i++){
		    		FlouryieldBVO[] bbvoss=bbvos[i];
		    		if(PuPubVO.getInteger_NullAs(bbvoss[0].getAttributeValue("itype"), -3)==1||
		    				PuPubVO.getInteger_NullAs(bbvoss[0].getAttributeValue("itype"), -3)==2||
		    				PuPubVO.getInteger_NullAs(bbvoss[0].getAttributeValue("itype"), -3)==3){
		    			    String dept=PuPubVO.getString_TrimZeroLenAsNull(bbvoss[0].getPk_deptdoc());
		    			    String invmandoc=PuPubVO.getString_TrimZeroLenAsNull(bbvoss[0].getPk_invmandoc());
		    			    QueryData queryindex=new QueryData();
		    			     Map<String, LabIndexSetBVO> map = queryindex.queryIndexSet(dept,invmandoc,corp);
		    		     	FlouryieldBVO bvo=new FlouryieldBVO();
		    		     	String crowno=PuPubVO.getString_TrimZeroLenAsNull((count+1)*10);;
		    			    bvo.setCrowno(crowno);		    			  
		    			    count++;
		    			    bvo.setPk_invmandoc(bbvoss[0].getPk_invmandoc());
		    			    bvo.setPk_invbasdoc(bbvoss[0].getPk_invbasdoc());
		    			    bvo.setPk_father(bbvoss[0].getPk_invmandoc());
		    			    bvo.setNoutput(bbvoss[0].getNoutput());
		    			    bvo.setPk_stordoc(bbvoss[0].getPk_stordoc());
		    			    bvo.setPk_deptdoc(bbvoss[0].getPk_deptdoc());
		    			    //set deptdoc
		    			    bvo.setPk_minarea(bbvoss[0].getPk_minarea());
		    			    bvo.setStatus(VOStatus.NEW);
		    			    list.add(bvo);
		    		for(int n=0;n<bbvoss.length;n++){
		    			if(map.containsKey(bbvoss[n].getPk_manindex())){
		    			    bbvoss[n].setCrowno(PuPubVO.getString_TrimZeroLenAsNull((count+1)*10));
		    			    count++;
		    			    bbvoss[n].setVcrowno(PuPubVO.getString_TrimZeroLenAsNull(crowno));		    			    
		    			    bbvoss[n].setPk_invmandoc(bbvoss[n].getPk_manindex());
		    			    bbvoss[n].setPk_invbasdoc(bbvoss[n].getPk_invindex());
		    			    bbvoss[n].setPk_father(bvo.getPk_invmandoc());
		    			    bbvoss[n].setNoutput(bbvoss[n].getNmetalamount());
		    			    bbvoss[n].setPk_deptdoc(bbvoss[n].getPk_deptdoc());
		    			    bbvoss[n].setStatus(VOStatus.NEW);
		    			    list.add(bbvoss[n]);
		    			}
		    			}
		    	}
		    }
		   if(list!=null&&list.size()>0){
		    AggFlouryieldVO   flvo=new AggFlouryieldVO();
		    FlouryieldHVO hvo=(FlouryieldHVO) bills[j].getParentVO();
		    flvo.setParentVO(hvo);
		    dealMeas(list);
		    flvo.setChildrenVO(list.toArray(new FlouryieldBVO[0]));
			HYBillVO tarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_flouryield, 
			PubBillTypeConst.billtype_concentratein,  flvo,pfvo);
			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_concentratein);//设置单据类型
			tarBill.getParentVO().setAttributeValue("vbillno", new HYPubBO().getBillNo(
					PubBillTypeConst.billtype_concentratein, SQLHelper.getCorpPk(), null, null));   //设置单据号；
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(pfvo.m_operator));//制单人
			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(pfvo.m_currentDate));//制单日期

			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
			UFDate curdate=UFDate.getDate(pfvo.m_currentDate);
			UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,pfvo.m_coId),UFBoolean.FALSE);
			if(!isclose.booleanValue()){
			PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
			new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_concentratein, 
					dbilldate, vo2, tarBill, null);
			}
		   }
		    }
		    }
		    }
		}
	}
    /**
     * repeat set meas
     * @param list
     * @throws BusinessException 
     */
	public void dealMeas(List<FlouryieldBVO> list) throws BusinessException {
         if(list==null || list.size()==0){
        	 return ;
         }
         for(int i=0;i<list.size();i++){
        	 FlouryieldBVO fvo=list.get(i);
        	 String pk_mes=PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("pk_measdoc->getColValue(bd_invbasdoc,pk_measdoc,pk_invbasdoc,pk_invbasdoc)", 
 					new String[]{"pk_invbasdoc "}, new String[]{fvo.getPk_invbasdoc()})) ;
        	fvo.setPk_measdoc(pk_mes);
        }		
	}
		
}
	
