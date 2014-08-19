package nc.vo.xcgl.pub.helper;
import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.RuntimeEnv;
import nc.bs.xcgl.pub.IndexFineBO;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.ui.zmpub.pub.tool.MapCacheToolClient;
import nc.vo.pub.BusinessException;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
/**
 * @author lxh
 */
public class IndexFineHeler {
	
	public static String bo = "nc.bs.xcgl.pub.IndexFineBO";
	
	public static List<LabIndexSetBVO> getIndexFine(IndexParaVO para)throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc())==null
				||	PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea())==null){
			    throw new BusinessException("化验存货或部门不能为空");	
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp())==null){
		    throw new BusinessException("公司不能为空");	
		}
		if(RuntimeEnv.getInstance().isRunningInServer()){
			return IndexFineBO.getIndexFine(para);
		}else{
			if(MapCacheToolClient.getMapObject(PubOtherConst.labindex_cache, para.getPk_invmandoc()+para.getPk_minarea()+para.getPk_corp()+para.getIsstandingcrop()+para.getPk_invmandoc1())==null){
				//查询数据库 执行远程调用
				Class[] ParameterTypes = new Class[] {IndexParaVO.class};
				Object[] ParameterValues = new Object[] {para};
				Object o = null;
				try {
					o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
							"正在执行...", 1, bo, null, "getIndexFine",
							ParameterTypes, ParameterValues);
				} catch (Exception e) {
					throw new BusinessException(e);
				}
				if(o==null){
					MapCacheToolClient.pubMapObject(PubOtherConst.labindex_cache, para.getPk_invmandoc()+para.getPk_minarea()+para.getPk_corp()+para.getIsstandingcrop()+para.getPk_invmandoc1(), PubOtherConst.isquery_cache);
				}else{
					MapCacheToolClient.pubMapObject(PubOtherConst.labindex_cache, para.getPk_invmandoc()+para.getPk_minarea()+para.getPk_corp()+para.getIsstandingcrop()+para.getPk_invmandoc1(), o);
				}
			
				return (List<LabIndexSetBVO>) o;
			}else if(MapCacheToolClient.getMapObject(PubOtherConst.labindex_cache, para.getPk_invmandoc()+para.getPk_minarea()+para.getPk_corp()+para.getIsstandingcrop()+para.getPk_invmandoc1()).equals(PubOtherConst.isquery_cache)){
				return null;
			}else{           
				return (List<LabIndexSetBVO>) MapCacheToolClient.getMapObject(PubOtherConst.labindex_cache, para.getPk_invmandoc()+para.getPk_minarea()+para.getPk_corp()+para.getIsstandingcrop()+para.getPk_invmandoc1());
			}
		}  
	}
	/**
	 * 得到所有指标定义的部门
	 * @return
	 * @throws BusinessException
	 */
    public static List<String>  getAllMinarea(IndexParaVO para)throws BusinessException{
    	if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp())==null)
			    throw new BusinessException("公司不能为空");	
		if(RuntimeEnv.getInstance().isRunningInServer()){
			return IndexFineBO.getAllMinarea(para);
		}else{
			if(MapCacheToolClient.getMapObject(PubOtherConst.minarea_cache, para.getPk_corp())==null){
				//查询数据库 执行远程调用
				Class[] ParameterTypes = new Class[] {IndexParaVO.class};
				Object[] ParameterValues = new Object[] {para};
				Object o = null;
				try {
					o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
							"正在执行...", 1, bo, null, "getAllMinarea",
							ParameterTypes, ParameterValues);
				} catch (Exception e) {
					throw new BusinessException(e);
				}
				if(o==null){
					MapCacheToolClient.pubMapObject(PubOtherConst.minarea_cache, para.getPk_corp(), PubOtherConst.isquery_cache);
				}else{
					MapCacheToolClient.pubMapObject(PubOtherConst.minarea_cache, para.getPk_corp(), o);
				}
			
				return (List<String>) o;
			}else if(MapCacheToolClient.getMapObject(PubOtherConst.minarea_cache, para.getPk_corp()).equals(PubOtherConst.isquery_cache)){
				return null;
			}else{           
				return (List<String>) MapCacheToolClient.getMapObject(PubOtherConst.minarea_cache, para.getPk_corp());
			}
		} 
    }
	/**
	 * 获得主指标
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public static LabIndexSetBVO getMainIndexFine(IndexParaVO para)throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc())==null
				||	PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea())==null){
			    throw new BusinessException("化验存货或部门不能为空");	
		}
		List<LabIndexSetBVO>  list =getIndexFine(para);
		List<LabIndexSetBVO>  list1=new ArrayList<LabIndexSetBVO>();
		if(list==null||list.size()==0)
			return null;
		for(int i=0; i<list.size();i++){
			LabIndexSetBVO bvo=(LabIndexSetBVO)list.get(i);
			int a=bvo.getItype();
			if(a==0)
				list1.add(bvo);
		}
		if(list1==null|| list1.size()==0){
			return null;
		}
		return list1.get(0);
		
	}
	/**
	 * 获得副指标
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
    public static List<LabIndexSetBVO> getInvIndexFine(IndexParaVO para)throws BusinessException{
    	if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc())==null
				||	PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea())==null){
			    throw new BusinessException("化验存货或部门不能为空");	
		}
		List<LabIndexSetBVO>  list =getIndexFine(para);
		List<LabIndexSetBVO>  list1=new ArrayList<LabIndexSetBVO>();
		if(list==null||list.size()==0)
			return null;
		for(int i=0; i<list.size();i++){
			LabIndexSetBVO bvo=(LabIndexSetBVO)list.get(i);
			int a=bvo.getItype();
			if(a==1)
				list1.add(bvo);
		}
			
		return list1;
		
	}
	/**
	 * 获得其他指标
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
    public static List<LabIndexSetBVO> getOtherIndexFine(IndexParaVO para)throws BusinessException{
    	if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc())==null
				||	PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea())==null){
			    throw new BusinessException("化验存货或部门不能为空");	
		}
		List<LabIndexSetBVO>  list =getIndexFine(para);
		List<LabIndexSetBVO>  list1=new ArrayList<LabIndexSetBVO>();
		if(list==null||list.size()==0)
			return null;
		for(int i=0; i<list.size();i++){
			LabIndexSetBVO bvo=(LabIndexSetBVO)list.get(i);
			int a=bvo.getItype();
			if(a==2)
				list1.add(bvo);
		}
			
		return list1;
		
	}
}
