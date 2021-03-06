package nc.bs.xcgl.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.logger.XCLogger;

/**
 * 取化验指标定义
 * @author lxh
 * add by jay
 * 过滤了封存标志
 */
public class IndexFineBO {
    /**
     * 根据参数查询化验指标
     * @param para
     * @return
     * @throws BusinessException
     */
	public static List<LabIndexSetBVO> getIndexFine(IndexParaVO para)throws BusinessException{
		XCLogger.printInfor("查询化验指标开始：");
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
//		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc1())==null){
//		    throw new BusinessException("关联矿石 ");	
//		}
		
		String pk_invmandoc=para.getPk_invmandoc();
		String pk_minarea=para.getPk_minarea();
		String pk_corp=para.getPk_corp();
		String pk_invmandoc1=para.getPk_invmandoc1();
		UFBoolean isstandingcrop=para.getIsstandingcrop();

		String sql=" select h.pk_labindexset_h  from  xcgl_labindexset_h h   "+
		           " where h.pk_invmandoc = '"+pk_invmandoc+"'"+
		           " and h.pk_minarea= '"+pk_minarea+"' " +
		           " and coalesce(h.isclose,'N')='N' "+   //顾虑封存标志
		           " and h.pk_corp = '"+pk_corp+"' and isnull(h.dr,0)=0 " ;
		           if(pk_invmandoc1!=null && pk_invmandoc1.length()!=0 ){
		           //添加关联原矿矿石
		        	   sql=sql+ " and h.vdef20 ='"+pk_invmandoc1+"'";
		           }
		Object []o=(Object [])XCZmPubDAO.getDAO().executeQuery(sql,new ArrayProcessor());
		if(o==null || o.length==0)
			return null;
		String  pk=o[0].toString();
		if(isstandingcrop==null){
			List<LabIndexSetBVO> list= (List<LabIndexSetBVO>) XCZmPubDAO.getDAO().retrieveByClause(LabIndexSetBVO.class, "isnull(dr,0)=0 and pk_labindexset_h ='"+pk+"'");
		    XCLogger.printInfor("取化验指标方法结束：");
		    return list;
		}else{
			List<LabIndexSetBVO> list= (List<LabIndexSetBVO>) XCZmPubDAO.getDAO().retrieveByClause(LabIndexSetBVO.class, "isnull(dr,0)=0 and pk_labindexset_h ='"+pk+"'"+
			"and isstandingcrop ='"+isstandingcrop+"'");
			XCLogger.printInfor("取化验指标方法结束：");
			return list;
		}
	} 
	
	public static List<String> getAllMinarea(IndexParaVO para)throws BusinessException{
		Map<String,List<String>> map=new HashMap<String,List<String>>();
		XCLogger.printInfor("查询部门开始：");
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp())==null){
		    throw new BusinessException("公司不能为空");	
		}
		String pk_corp=para.getPk_corp();

		String sql="  select pk_minarea  from  xcgl_labindexset_h h  "+
		           "  where h.pk_corp = '"+pk_corp+"' and isnull(dr,0)=0 " +
		           "  and coalesce(h.isclose,'N')='N' ";//封存标志
		List list=(List)XCZmPubDAO.getDAO().executeQuery(sql,new ArrayListProcessor());
		if(list==null||list.size()==0)
			return null;
		for(int i=0;i<list.size();i++){
			Object []str=(Object [])list.get(i);
			String key=(String) str[0];
			if(!map.containsKey(key)){
				List<String> list1=new ArrayList<String>();
				list1.add((String) str[0]);
				map.put(key, list1);
			}
		}
		List<String> list2=new ArrayList<String>();
		Set<Entry<String, List<String>>>  set=map.entrySet();
		Iterator<Entry<String, List<String>>> iterator = set.iterator();
		    while (iterator.hasNext()) { 
		           Entry<String, List<String>>  entry =iterator.next();
		           List<String> plist = (List<String>) entry.getValue();
		           list2.add(plist.get(0));		           
		    }
		
	    XCLogger.printInfor("查部门方法结束：");
		return list2;
	}
}
