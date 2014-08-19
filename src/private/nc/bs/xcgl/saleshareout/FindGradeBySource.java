package nc.bs.xcgl.saleshareout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.assay.AssayBVO;
import nc.vo.xcgl.assayres.AssayResBVO;
import nc.vo.xcgl.assayres.AssayResHVO;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.sample.SampleBVO;

/**
 *@author jay
 * 根据销售销售过磅登记的主键，按照来源关系，
 * 找到根据销售过磅登记的某条记录生成的销售
 * 化验结果
 * 
 */
public class FindGradeBySource {
	  /**
	   *查找销售化验结果  
	   */
	   public Map<String,List<AssayResBVO>> findGrade(HYBillVO billvo){
	    	Map<String,List<AssayResBVO>> map=new HashMap<String,List<AssayResBVO>>();
	    	if(billvo==null){
	    		return null;
	    	}
	    	CircularlyAccessibleValueObject[] bodyvos = billvo.getChildrenVO();
	    	if(bodyvos==null||bodyvos.length==0){
	    		return null;
	    	}
	    	for(int i=0;i<bodyvos.length;i++){
	    		String vlastbillid=PuPubVO.getString_TrimZeroLenAsNull
	    		(bodyvos[i].getAttributeValue("vlastbillid"));//pk_head
	    		String vlastbillrowid=PuPubVO.getString_TrimZeroLenAsNull(
	    				bodyvos[i].getAttributeValue("vlastbillrowid"));//pk_body
	    		try {
	    			//根据来源单据id和来源单据行id查询送样单
					List<SampleBVO> list=(List<SampleBVO>) QueryHelper.retrieveByClause(SampleBVO.class,
							" vlastbillid='"+vlastbillid+"'"+" and vlastbillrowid='"+vlastbillrowid+"'"+
							" and isnull(dr,0)=0 ");
					if(list==null||list.size()==0){
						return null;
					}
					else{
						//根据来源单据id和来源单据行id查询化验单 表体主键不唯一
						String assayvlastbillid=PuPubVO.getString_TrimZeroLenAsNull
			    		(list.get(0).getAttributeValue("pk_sample"));//pk_head
			    		List<AssayBVO> assaylist=(List<AssayBVO>) QueryHelper.retrieveByClause(AssayBVO.class,
								" vlastbillid='"+assayvlastbillid+"'"+" and isnull(dr,0)=0 ");
			    		if(assaylist==null||assaylist.size()==0){
			    			return null;
			    		}
			    		else{
			    			String assayresvlastbillid=PuPubVO.getString_TrimZeroLenAsNull
				    		(assaylist.get(0).getAttributeValue("pk_sample"));
			    			//根据来源单据id查询化验结果
			    			List<AssayResBVO> assayreslist=(List<AssayResBVO>) QueryHelper.retrieveByClause(AssayResBVO.class,
									" vlastbillid='"+assayresvlastbillid+"'"+" and isnull(dr,0)=0 ");
			    			//过滤审批通过的，初检样，并且不是杂质的
			    			List<AssayResBVO> filterlist = filter(assayreslist);
			    			if(filterlist==null||filterlist.size()==0){
			    				throw new BusinessException("取到的化验结果为空!");
			    			}
			    			map.put(vlastbillid+vlastbillrowid,
			    					filterlist);
			    		}
					}
					
				} catch (BusinessException e) {
					e.printStackTrace();
				}
	    	}


	    	return map;
	    }
	   /**
	    * 过滤表头审批通过的和样品类型是初检样的
	    * 表体不是杂质的
	    * @param list
	    * @return
	    * @throws BusinessException 
	    */
	public	List<AssayResBVO> filter(List<AssayResBVO> list) throws BusinessException {
		List<AssayResBVO> reslist=new ArrayList<AssayResBVO>();
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					AssayResBVO bvo=list.get(i);
					String sample=bvo.getPk_sample();
					UFBoolean impurity=PuPubVO.getUFBoolean_NullAs(bvo.getUimpurity(), UFBoolean.FALSE);
					//先过滤不是杂质的
					if(!impurity.booleanValue()){
					List<AssayResHVO> hvolist= (List<AssayResHVO>) QueryHelper.retrieveByClause(AssayResHVO.class,"pk_sample='"+sample+"'"+"and isnull(dr,0)=0");
					if(hvolist!=null&&hvolist.size()!=0){
					AssayResHVO hvo = hvolist.get(0);
					Integer sampletype=PuPubVO.getInteger_NullAs(hvo.getIsampletype(), -5);
					Integer billstaus=PuPubVO.getInteger_NullAs(hvo.getVbillstatus(), -8);
					//过滤初检样和审批通过的
					if(sampletype==0&&billstaus==1){
						reslist.add(bvo);
					}
					}
					}
			
			}
				return reslist;
			}
			return reslist;
		}
	

}
