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
 * �����������۹����Ǽǵ�������������Դ��ϵ��
 * �ҵ��������۹����Ǽǵ�ĳ����¼���ɵ�����
 * ������
 * 
 */
public class FindGradeBySource {
	  /**
	   *�������ۻ�����  
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
	    			//������Դ����id����Դ������id��ѯ������
					List<SampleBVO> list=(List<SampleBVO>) QueryHelper.retrieveByClause(SampleBVO.class,
							" vlastbillid='"+vlastbillid+"'"+" and vlastbillrowid='"+vlastbillrowid+"'"+
							" and isnull(dr,0)=0 ");
					if(list==null||list.size()==0){
						return null;
					}
					else{
						//������Դ����id����Դ������id��ѯ���鵥 ����������Ψһ
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
			    			//������Դ����id��ѯ������
			    			List<AssayResBVO> assayreslist=(List<AssayResBVO>) QueryHelper.retrieveByClause(AssayResBVO.class,
									" vlastbillid='"+assayresvlastbillid+"'"+" and isnull(dr,0)=0 ");
			    			//��������ͨ���ģ������������Ҳ������ʵ�
			    			List<AssayResBVO> filterlist = filter(assayreslist);
			    			if(filterlist==null||filterlist.size()==0){
			    				throw new BusinessException("ȡ���Ļ�����Ϊ��!");
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
	    * ���˱�ͷ����ͨ���ĺ���Ʒ�����ǳ�������
	    * ���岻�����ʵ�
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
					//�ȹ��˲������ʵ�
					if(!impurity.booleanValue()){
					List<AssayResHVO> hvolist= (List<AssayResHVO>) QueryHelper.retrieveByClause(AssayResHVO.class,"pk_sample='"+sample+"'"+"and isnull(dr,0)=0");
					if(hvolist!=null&&hvolist.size()!=0){
					AssayResHVO hvo = hvolist.get(0);
					Integer sampletype=PuPubVO.getInteger_NullAs(hvo.getIsampletype(), -5);
					Integer billstaus=PuPubVO.getInteger_NullAs(hvo.getVbillstatus(), -8);
					//���˳�����������ͨ����
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
