package nc.vo.xcgl.pub.helper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nc.bs.framework.common.RuntimeEnv;
import nc.bs.xcgl.pub.PubProduceSetBO;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.ui.zmpub.pub.tool.MapCacheToolClient;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.produceset.AggProduceSetVO;
import nc.vo.xcgl.produceset.ProductSetBB1VO;
import nc.vo.xcgl.produceset.ProductSetBB2VO;
import nc.vo.xcgl.produceset.ProductSetBVO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.tool.XcPubTool;
/**
 * 生产流程设置帮助类 不区分前后台
 * @author mlr
 */
public class ProduceSetHelper {
	public static String bo = "nc.bs.xcgl.pub.PubProduceSetBO";
	/**
	 * 得到流程设置聚合VO,支持前台客户端内存缓存
	 * @param para
	 * @return
	 */
	public static AggProduceSetVO getProduceSetBillVO(ProSetParaVO para)throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("生产线不能为空");	
		}
		//
		if(RuntimeEnv.getInstance().isRunningInServer()){
				return PubProduceSetBO.getProduceSetBillVO(para);		
		}else{
			if(MapCacheToolClient.getMapObject(PubOtherConst.proset_cache, para.getPk_beltline())==null){
				//查询数据库 执行远程调用
				Class[] ParameterTypes = new Class[] {ProSetParaVO.class};
				Object[] ParameterValues = new Object[] {para};
				Object o = null;
				try {
					o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
							"正在执行...", 1, bo, null, "getProduceSetBillVO",
							ParameterTypes, ParameterValues);
				} catch (Exception e) {
					throw new BusinessException(e);
				}
				if(o==null){
					MapCacheToolClient.pubMapObject(PubOtherConst.proset_cache, para.getPk_beltline(), PubOtherConst.isquery_cache);
				}else{
					MapCacheToolClient.pubMapObject(PubOtherConst.proset_cache, para.getPk_beltline(), o);
				}
				return (AggProduceSetVO) o;
			}else if(MapCacheToolClient.getMapObject(PubOtherConst.proset_cache, para.getPk_beltline()).equals(PubOtherConst.isquery_cache)){
				return null;
			}else{
				return (AggProduceSetVO) MapCacheToolClient.getMapObject(PubOtherConst.proset_cache, para.getPk_beltline());
			}
		}		
	}
	/**
	 * 按工序获得投入信息
	 * 如果来源单据类型不为空，则按来源单据类型过滤投入信息
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public static List<ProductSetBB1VO> getProInSetInfor(ProSetParaVO para) throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("生产线不能为空");	
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_process())==null){
		    throw new BusinessException("工序不能为空");	
		}
		AggProduceSetVO billvo=getProduceSetBillVO(para);
		if(billvo==null){
			throw new BusinessException("该生产线没有设置生产流程");
		}
		ProductSetBVO[] bvos=(ProductSetBVO[]) billvo.getChildrenVO();
		if(bvos==null || bvos.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bvos.length;i++){
		   if(para.getPk_process().equals(bvos[i].getPk_process()))	{
			       List inlist= bvos[i].getProduceinset1();
			       if(inlist==null||inlist.size()==0){
			    	   return null;
			       }
			       if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_sourcetype())!=null){
			            List  sinlist=new ArrayList();
			            for(int j=0;j<inlist.size();j++){
			            	 ProductSetBB1VO b1vo=(ProductSetBB1VO) inlist.get(j);
			    	         if(para.getPk_sourcetype().equals(b1vo.getVouttype())){
			    	        	 sinlist.add(b1vo);
			    	         }
			            }
			            return sinlist;
			       }else{
			    	    return inlist;
			       }
		   }
		}
		return null;
	}
	/**
	 * 获得产出信息
	 * @author jay
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public static List<ProductSetBB2VO> getAllProOutSetInfor(ProSetParaVO para) throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("生产线不能为空");	
		}
//		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_process())==null){
//		    throw new BusinessException("工序不能为空");	
//		}
		AggProduceSetVO billvo=getProduceSetBillVO(para);
		if(billvo==null){
			throw new BusinessException("该生产线没有设置生产流程");
		}
		List<ProductSetBB2VO> list=new ArrayList<ProductSetBB2VO>();
		ProductSetBVO[] bvos=(ProductSetBVO[]) billvo.getChildrenVO();
		if(bvos==null || bvos.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bvos.length;i++){
		List<ProductSetBB2VO> outlist=bvos[i].getProduceoutset1();
		if(outlist!=null&&outlist.size()!=0){
			for(int j=0;j<outlist.size();j++){
				list.add(outlist.get(i));
			}
		}
		}
//		List alllist=new ArrayList();
//		for(int i=0;i<bvos.length;i++){
//		   if(para.getPk_process().equals(bvos[i].getPk_process()))	{
//			       List inlist= bvos[i].getProduceinset1();
//			       if(inlist==null||inlist.size()==0){
//			    	   continue;
//			       }
//			       if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_sourcetype())!=null){
//			            List  sinlist=new ArrayList();
//			            for(int j=0;j<inlist.size();j++){
//			            	 ProductSetBB1VO b1vo=(ProductSetBB1VO) inlist.get(j);
//			    	         if(para.getPk_sourcetype().equals(b1vo.getVouttype())){
//			    	        	 sinlist.add(b1vo);
//			    	         }
//			            }
//			            alllist.addAll(sinlist);
//			       }else{
//			    	   alllist.addAll(inlist);
//			       }
//		   }
//		}
		return list;
	}
	/**
	 * 获得产出信息(精粉)
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllPowderProOutSetInfor(ProSetParaVO para) throws BusinessException{
        //取流程设置		AggProduceSetVO aggvo=getProduceSetBillVO(para);
		//产出孙表
		//过滤存货类型为精粉
		List<ProductSetBB2VO> list=new ArrayList<ProductSetBB2VO>();
		Map<String,List<ProductSetBB2VO>>  map=new HashMap<String, List<ProductSetBB2VO>>();
		if(para!=null){
			AggProduceSetVO aggvo=getProduceSetBillVO(para);
			if(aggvo!=null){
			ProductSetBVO []bodyvos=(ProductSetBVO[]) aggvo.getChildrenVO();
			if(bodyvos==null||bodyvos.length==0){
				throw new BusinessException("该生产线没有设置工序");
			}
			for(int i=0;i<bodyvos.length;i++){
              String key="bodyvo"+i;
              if(bodyvos[i].getProduceoutset1()!=null&&bodyvos[i].getProduceoutset1().size()!=0){
			  map.put(key, bodyvos[i].getProduceoutset1());
              }
			}
			//过滤存货类型为精粉
            Set<Entry<String,List<ProductSetBB2VO>>> set=map.entrySet();
            Iterator<Entry<String, List<ProductSetBB2VO>>> iterator = set.iterator();
            while (iterator.hasNext()){
            	Entry<String, List<ProductSetBB2VO>>  entry=iterator.next();
            	List<ProductSetBB2VO> list2=entry.getValue();
            	if(list2!=null&&list2.size()!=0){
                for(int i=0;i<list2.size();i++){
                	ProductSetBB2VO bb2vo=(ProductSetBB2VO) list2.get(i);
                	//获得存货类型  存货类型为0的是精粉 
                	Integer ioretype=PuPubVO.getInteger_NullAs(bb2vo.getIoretype(), -2);
                	if(ioretype!=null){
                	if(ioretype==0){
                		list.add(bb2vo);
                	}
                	}
                }
            	}
            }
            return list;
			}
			else{
				throw new BusinessException("该生产线没有设置生产流程");
			}
		}else{
			throw new BusinessException("参数为空");
		}
	}
	/**
	 * 获得产出信息(精粉) 一道工艺
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllAPowderProOutSetInfor(ProSetParaVO para) throws BusinessException{
        //取流程设置
		//产出孙表
		//过滤存货类型为精粉
		List<ProductSetBB2VO> outlist=new ArrayList<ProductSetBB2VO>();
		List<ProductSetBB2VO> returnlist=new ArrayList<ProductSetBB2VO>();
		List<String> pklist=new ArrayList<String>();
		AggProduceSetVO  aggvo=getProduceSetBillVO(para);//流程设置聚合vo
		if(aggvo==null){
			throw new BusinessException("流程设置为空");
		}
		ProductSetBVO []bodyvo=(ProductSetBVO[]) aggvo.getChildrenVO();
		if(bodyvo==null||bodyvo.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bodyvo.length;i++){
          Integer itech= PuPubVO.getInteger_NullAs(bodyvo[i].getItech(), -3);
          if(itech!=null){
          if(itech==0){//工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺
        	 String bodypk=bodyvo[i].getPk_produceset_b();
        	 pklist.add(bodypk);
          }
          }
		}
		outlist=getAllPowderProOutSetInfor(para);//所有的产出信息
		if(outlist==null||outlist.size()==0){
			throw new BusinessException("获得产出信息为空");
		}
		for(int i=0;i<outlist.size();i++){
			ProductSetBB2VO bb2vo=outlist.get(i);
			if(bb2vo!=null){
			String pk=bb2vo.getPk_produceset_b();	
			for(int j=0;j<pklist.size();j++){
				// 判断是否是一道工艺
				if(pk.equals(pklist.get(j))){
				  returnlist.add(bb2vo);
				  break;
				}
		
			}
			}
			
		}
		sortRepSel(returnlist);
		return returnlist;
	}
	/**
	 * 获得产出信息(精粉) 二道工艺
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllBPowderProOutSetInfor(ProSetParaVO para) throws BusinessException{
        //取流程设置
		//产出孙表
		//过滤存货类型为精粉
		List<ProductSetBB2VO> outlist=new ArrayList<ProductSetBB2VO>();
		List<ProductSetBB2VO> returnlist=new ArrayList<ProductSetBB2VO>();
		List<String> pklist=new ArrayList<String>();
		AggProduceSetVO  aggvo=getProduceSetBillVO(para);//流程设置聚合vo
		if(aggvo==null){
			throw new BusinessException("流程设置为空");
		}
		ProductSetBVO []bodyvo=(ProductSetBVO[]) aggvo.getChildrenVO();
		if(bodyvo==null||bodyvo.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bodyvo.length;i++){
          Integer itech= PuPubVO.getInteger_NullAs(bodyvo[i].getItech(), -3);
          if(itech!=null){
          if(itech==1){//工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺
        	 String bodypk=bodyvo[i].getPk_produceset_b();
        	 pklist.add(bodypk);
          }
          }
		}
		outlist=getAllPowderProOutSetInfor(para);//所有的产出信息
		if(outlist==null||outlist.size()==0){
			throw new BusinessException("获得产出信息为空");
		}
		for(int i=0;i<outlist.size();i++){
			ProductSetBB2VO bb2vo=outlist.get(i);
			if(bb2vo!=null){
			String pk=bb2vo.getPk_produceset_b();
			for(int j=0;j<pklist.size();j++){
				if(pk.equals(pklist.get(j))){
				 returnlist.add(bb2vo);
				 break;
				}
			}	
			}
		}
		sortRepSel(returnlist);
		return returnlist;
	}
	/**
	 * 获得产出信息(精粉) 三道工艺
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllCPowderProOutSetInfor(ProSetParaVO para) throws BusinessException{
        //取流程设置
		//产出孙表
		//过滤存货类型为精粉
		List<ProductSetBB2VO> outlist=new ArrayList<ProductSetBB2VO>();
		List<ProductSetBB2VO> returnlist=new ArrayList<ProductSetBB2VO>();
		List<String> pklist=new ArrayList<String>();
		AggProduceSetVO  aggvo=getProduceSetBillVO(para);//流程设置聚合vo
		if(aggvo==null){
			throw new BusinessException("流程设置为空");
		}
		ProductSetBVO []bodyvo=(ProductSetBVO[]) aggvo.getChildrenVO();
		if(bodyvo==null||bodyvo.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bodyvo.length;i++){
          Integer itech= PuPubVO.getInteger_NullAs(bodyvo[i].getItech(), -3);
          if(itech!=null){
          if(itech==2){//工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺
        	 String bodypk=bodyvo[i].getPk_produceset_b();
        	 pklist.add(bodypk);
          }
          }
		}
		outlist=getAllPowderProOutSetInfor(para);//所有的产出信息
		if(outlist==null||outlist.size()==0){
			throw new BusinessException("获得产出信息为空");
		}
		for(int i=0;i<outlist.size();i++){
			ProductSetBB2VO bb2vo=outlist.get(i);
			if(bb2vo!=null){
			String pk=bb2vo.getPk_produceset_b();
			for(int j=0;j<pklist.size();j++){
				if(pk.equals(pklist.get(j))){
				 returnlist.add(bb2vo);
				 break;
				}
			}
			}
		}
		sortRepSel(returnlist);
		return returnlist;
	}
	/**
	 * place repselpower set list first index
	 * @param returnlist
	 * @throws BusinessException 
	 */
	public static void sortRepSel(List<ProductSetBB2VO> returnlist) throws BusinessException {
        if(returnlist!=null){
        	for(int i=0;i<returnlist.size();i++){
        	   if(returnlist.get(i).getUisreselect()!=null&&returnlist.get(i).getUisreselect().booleanValue()==true){
        		try {
					ProductSetBB2VO bvso=(ProductSetBB2VO) ObjectUtils.serializableClone(returnlist.get(i));
					returnlist.remove(i);
					
					returnlist.add(0, bvso);
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(e);
				}
        		   
        	   }	
        	}
        }		
	}
	/**
	 * 获得产出信息(尾矿)
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllTailsProOutSetInfor(ProSetParaVO para) throws BusinessException{
		//取流程设置
		//产出孙表
		//过滤存货类型为精粉  
		List<ProductSetBB2VO> list=new ArrayList<ProductSetBB2VO>();
		Map<String,List<ProductSetBB2VO>>  map=new HashMap<String, List<ProductSetBB2VO>>();
		if(para!=null){
			AggProduceSetVO aggvo=getProduceSetBillVO(para);
			if(aggvo!=null){
			ProductSetBVO []bodyvos=(ProductSetBVO[]) aggvo.getChildrenVO();
			if(bodyvos==null||bodyvos.length==0){
				throw new BusinessException("该生产线没有设置工序");
			}
			for(int i=0;i<bodyvos.length;i++){
              String key="bodyvo"+i;
              if(bodyvos[i].getProduceoutset1()!=null&&bodyvos[i].getProduceoutset1().size()!=0){
			  map.put(key, bodyvos[i].getProduceoutset1());
              }
			}
			//过滤存货类型为精粉
            Set<Entry<String,List<ProductSetBB2VO>>> set=map.entrySet();
            Iterator<Entry<String, List<ProductSetBB2VO>>> iterator = set.iterator();
            while (iterator.hasNext()){
            	Entry<String, List<ProductSetBB2VO>>  entry=iterator.next();
            	List<ProductSetBB2VO> list2=entry.getValue();
            	if(list2!=null&&list2.size()!=0){
                for(int i=0;i<list2.size();i++){
                	ProductSetBB2VO bb2vo=(ProductSetBB2VO) list2.get(i);
                	//获得存货类型  存货类型为1的是尾矿
                	if(bb2vo!=null){
                	Integer ioretype=PuPubVO.getInteger_NullAs(bb2vo.getIoretype(), -2);
                	if(ioretype!=null){
                	if(ioretype==1){
                		list.add(bb2vo);
                	}
                	}
                }
                }
            }
            }
            return list;
			}
			else{
				throw new BusinessException("该生产线没有设置生产流程");
			}
		}else{
			throw new BusinessException("参数为空");
		}
	}
	/**
	 * 获得产出信息(尾矿)一道工艺
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllATailsProOutSetInfor(ProSetParaVO para) throws BusinessException{
		//取流程设置
		//产出孙表
		//过滤存货类型为精粉  
		List<ProductSetBB2VO> outlist=new ArrayList<ProductSetBB2VO>();
		List<ProductSetBB2VO> returnlist=new ArrayList<ProductSetBB2VO>();
		List<String> pklist=new ArrayList<String>();
		AggProduceSetVO  aggvo=getProduceSetBillVO(para);//流程设置聚合vo
		if(aggvo==null){
			throw new BusinessException("流程设置为空");
		}
		ProductSetBVO []bodyvo=(ProductSetBVO[]) aggvo.getChildrenVO();
		if(bodyvo==null||bodyvo.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bodyvo.length;i++){
          Integer itech= PuPubVO.getInteger_NullAs(bodyvo[i].getItech(), -3);
          if(itech!=null){
          if(itech==0){//工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺
        	 String bodypk=bodyvo[i].getPk_produceset_b();
        	 pklist.add(bodypk);
          }
          }
		}
		outlist=getAllTailsProOutSetInfor(para);//所有的产出信息(尾矿)
		if(outlist==null||outlist.size()==0){
			throw new BusinessException("获得产出信息为空     ");
		}
		for(int i=0;i<outlist.size();i++){
			ProductSetBB2VO bb2vo=outlist.get(i);
			if(bb2vo!=null){
			String pk=bb2vo.getPk_produceset_b();
			for(int j=0;j<pklist.size();j++){
				String pk1=pklist.get(j);
				 if(pk.equals(pk1)){
				  returnlist.add(bb2vo);		
				  break;
				 } 
			}
		}
		}
		return returnlist;
	}
	/**
	 * 获得产出信息(尾矿)二道工艺
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllBTailsProOutSetInfor(ProSetParaVO para) throws BusinessException{
		//取流程设置
		//产出孙表
		//过滤存货类型为精粉  
		List<ProductSetBB2VO> outlist=new ArrayList<ProductSetBB2VO>();
		List<ProductSetBB2VO> returnlist=new ArrayList<ProductSetBB2VO>();
		List<String> pklist=new ArrayList<String>();
		AggProduceSetVO  aggvo=getProduceSetBillVO(para);//流程设置聚合vo
		if(aggvo==null){
			throw new BusinessException("流程设置为空");
		}
		ProductSetBVO []bodyvo=(ProductSetBVO[]) aggvo.getChildrenVO();
		if(bodyvo==null||bodyvo.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bodyvo.length;i++){
          Integer itech= PuPubVO.getInteger_NullAs(bodyvo[i].getItech(), -3);
          if(itech!=null){
          if(itech==1){//工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺
        	 String bodypk=bodyvo[i].getPk_produceset_b();
        	 pklist.add(bodypk);
          }
          }
		}
		outlist=getAllTailsProOutSetInfor(para);//所有的产出信息(尾矿)
		if(outlist==null||outlist.size()==0){
			throw new BusinessException("获得产出信息为空");
		}
		for(int i=0;i<outlist.size();i++){
			ProductSetBB2VO bb2vo=outlist.get(i);
			if(bb2vo!=null){
			String pk=bb2vo.getPk_produceset_b();
			for(int j=0;j<pklist.size();j++){
				String pk1=pklist.get(j);
				if(pk.equals(pk1)){
				 returnlist.add(bb2vo);		
				 break;
				}	
			}
			}
		}
		return returnlist;
	}
	/**
	 * 获得产出信息(尾矿)三道工艺
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB2VO> getAllCTailsProOutSetInfor(ProSetParaVO para) throws BusinessException{
		//取流程设置
		//产出孙表
		//过滤存货类型为精粉  
		List<ProductSetBB2VO> outlist=new ArrayList<ProductSetBB2VO>();
		List<ProductSetBB2VO> returnlist=new ArrayList<ProductSetBB2VO>();
		List<String> pklist=new ArrayList<String>();
		AggProduceSetVO  aggvo=getProduceSetBillVO(para);//流程设置聚合vo
		if(aggvo==null){
			throw new BusinessException("流程设置为空");
		}
		ProductSetBVO []bodyvo=(ProductSetBVO[]) aggvo.getChildrenVO();
		if(bodyvo==null||bodyvo.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bodyvo.length;i++){
          Integer itech= PuPubVO.getInteger_NullAs(bodyvo[i].getItech(), -3);
          if(itech!=null){
          if(itech==2){//工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺
        	 String bodypk=bodyvo[i].getPk_produceset_b();
        	 pklist.add(bodypk);
          }
          }
		}
		outlist=getAllTailsProOutSetInfor(para);//所有的产出信息(尾矿)
		if(outlist==null||outlist.size()==0){
			throw new BusinessException("获得产出信息为空");
		}
		for(int i=0;i<outlist.size();i++){
			ProductSetBB2VO bb2vo=outlist.get(i);
			if(bb2vo!=null){
			String pk=bb2vo.getPk_produceset_b();
			for(int j=0;j<pklist.size();j++){
				String pk1=pklist.get(j);
				if(pk.equals(pk1)){
				 returnlist.add(bb2vo);		
				 break;
				}
			}
			}
		}
		return returnlist;
	}
	/**
	 * 获得投入信息
	 * 如果来源单据类型不为空，则按来源单据类型过滤投入信息
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public static List<ProductSetBB1VO> getAllProInSetInfor(ProSetParaVO para) throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("生产线不能为空");	
		}
//		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_process())==null){
//		    throw new BusinessException("工序不能为空");	
//		}
		AggProduceSetVO billvo=getProduceSetBillVO(para);
		if(billvo==null){
			throw new BusinessException("该生产线没有设置生产流程");
		}
		ProductSetBVO[] bvos=(ProductSetBVO[]) billvo.getChildrenVO();
		if(bvos==null || bvos.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		List alllist=new ArrayList();
		for(int i=0;i<bvos.length;i++){
//		   if(para.getPk_process().equals(bvos[i].getPk_process()))	{
			       List inlist= bvos[i].getProduceinset1();
			       if(inlist==null||inlist.size()==0){
			    	   continue;
			       }
			       if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_sourcetype())!=null){
			            List  sinlist=new ArrayList();
			            for(int j=0;j<inlist.size();j++){
			            	 ProductSetBB1VO b1vo=(ProductSetBB1VO) inlist.get(j);
			    	         if(para.getPk_sourcetype().equals(b1vo.getVouttype())){
			    	        	 sinlist.add(b1vo);
			    	         }
			            }
			            alllist.addAll(sinlist);
			       }else{
			    	   alllist.addAll(inlist);
			       }
		   }
//		}
		return alllist;
	}
	/**
	 * 按工序获得产出信息
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay
	 */
	public static List<ProductSetBB1VO> getProOutSetInfor(ProSetParaVO para) throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("生产线不能为空");	
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_process())==null){
		    throw new BusinessException("工序不能为空");	
		}
		AggProduceSetVO billvo=getProduceSetBillVO(para);
		if(billvo==null){
			throw new BusinessException("该生产线没有设置生产流程");
		}
		ProductSetBVO[] bvos=(ProductSetBVO[]) billvo.getChildrenVO();
		if(bvos==null || bvos.length==0){
			throw new BusinessException("该生产线没有设置工序");
		}
		for(int i=0;i<bvos.length;i++){
		   if(para.getPk_process().equals(bvos[i].getPk_process()))	{
			   return bvos[i].getProduceoutset1();
		   }
		}
		return null;
	}
	/**
	 * 取到属性为原矿的出库单据类型
	 * @param para
	 * @return
	 * @throws BusinessException 
	 * @author jay
	 */
	public static List<String> getProInType(ProSetParaVO para) throws BusinessException {
		List list=getAllProInSetInfor(para);
		if(list!=null&&list.size()>0){
			List<String> list1=new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				ProductSetBB1VO vo=(ProductSetBB1VO) list.get(i);
				UFBoolean ora=vo.getUisore();
				if(ora!=null){
				if(ora.booleanValue()){
					if(!list1.contains(PuPubVO.getString_TrimZeroLenAsNull(vo.getVouttype()))){
						list1.add(PuPubVO.getString_TrimZeroLenAsNull(vo.getVouttype()));
					}
				}
			}
		}
		return list1;
		}
		else{
			throw new BusinessException("没有属性为原矿的单据类型");
		}	
	}
	/**
	 * 得到投入设置信息（原矿）
	 * @param para
	 * @return
	 * @throws BusinessException 
	 * @author jay
	 */
	public static List<ProductSetBB1VO> getAllRawOreProInSetInfor(ProSetParaVO para) throws BusinessException {
		List list=getAllProInSetInfor(para);
		if(list!=null&&list.size()>0){
			List<ProductSetBB1VO> list1=new ArrayList<ProductSetBB1VO>();
			for(int i=0;i<list.size();i++){
				ProductSetBB1VO vo=(ProductSetBB1VO) list.get(i);
				UFBoolean ora=vo.getUisore();
				if(ora!=null){
				if(ora.booleanValue()){
					if(para.getPk_invmandoc().equals(vo.getPk_invmandoc()))
					list1.add(vo);
					}
				}
				}
			return list1;
			}
		else{
			throw new BusinessException("没有设置原矿的投入信息");
			}
		
	}
	/**
	 * 得到投入设置信息（材料）
	 * @param para
	 * @return
	 * @throws BusinessException
	 * @author jay 
	 */
	public static List<ProductSetBB1VO> getAllMaterialsProInSetInfor(ProSetParaVO para) throws BusinessException {
		List list=getAllProInSetInfor(para);
		if(list!=null&&list.size()>0){
			List<ProductSetBB1VO> list1=new ArrayList<ProductSetBB1VO>();
			for(int i=0;i<list.size();i++){
				ProductSetBB1VO vo=(ProductSetBB1VO) list.get(i);
				UFBoolean ora=vo.getUisore();
				if(!ora.booleanValue()){
                      list1.add(vo);					
					}
				}
			return list1;
			}
		else{
			throw new BusinessException("没有设置材料的投入信息");
		}	
	
	}
	/**
	 * 获得重选
	 * @param para
	 * @return
	 * @author jay
	 * @throws BusinessException 
	 */
	public static List<ProductSetBB2VO> getAllGravityProOutSetInfor(
			ProSetParaVO para) throws BusinessException {
		List<ProductSetBB2VO>  list=new ArrayList<ProductSetBB2VO>();
//		List<ProductSetBB2VO>  orelist=new ArrayList<ProductSetBB2VO>();
	//	Map<String,List<ProductSetBB2VO>>  map=new HashMap<String, List<ProductSetBB2VO>>();
		
		List<ProductSetBB2VO>   bblist=new ArrayList<ProductSetBB2VO>();
		
		AggProduceSetVO aggvo=getProduceSetBillVO(para);
		if(aggvo==null){
			throw new BusinessException("生产流程设置为空");
		}
		else{
			ProductSetBVO[] bvos=(ProductSetBVO[]) aggvo.getChildrenVO();
			if(bvos==null||bvos.length==0){
               throw new BusinessException("设置为空");   			
			}
			else{
				for(int i=0;i<bvos.length;i++){
				  Integer itech=PuPubVO.getInteger_NullAs(bvos[i].getItech(), -3);
				  if(itech!=null){
					  if(itech==3){
	//					  map.put(XcPubTool.getMapKey(para), bvos[i].getProduceoutset1());
						  if(bvos[i].getProduceoutset1()!=null &&bvos[i].getProduceoutset1().size()>0){
						   bblist.addAll(bvos[i].getProduceoutset1());
						  }
					  }
				  }
			  }
		//		orelist= map.get(XcPubTool.getMapKey(para));
				if(bblist==null||bblist.size()==0){
					return list;
				}
				else{
					for(int j=0;j<bblist.size();j++){
						ProductSetBB2VO bvo=bblist.get(j);
						Integer ioretype=PuPubVO.getInteger_NullAs(bvo.getIoretype(), -2);//过滤存货类型
						if(ioretype!=null){
							if(ioretype==0){
								list.add(bvo);
							}
						}
					}
					return list;
				}
		}
			} 
	}
}
