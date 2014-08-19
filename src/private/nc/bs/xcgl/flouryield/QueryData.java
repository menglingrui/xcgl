package nc.bs.xcgl.flouryield;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
/**
 *查询化验指标定义
 *某个部门加存货下的所有
 *产生现存量的指标 
 */
public class QueryData {
 public Map<String, LabIndexSetBVO> queryIndexSet(String dept,String invmandoc,String corp) throws DAOException{
	 Map<String,LabIndexSetBVO> map=new HashMap<String,LabIndexSetBVO>();
	 if(dept==null||dept.trim()==""){
		 return map;
	 }
	 if(invmandoc==null||invmandoc==""){
		 return map;
	 }
	 if(corp==null||corp==""){
		 return map;
	 }
	 String sql=" select pk_labindexset_h from  xcgl_labindexset_h h where h.pk_corp='"+corp+"'"
               +" and coalesce(dr,0)=0"
               +" and h.pk_invmandoc='"+invmandoc+"'"
               +" and h.pk_minarea='"+ dept+"'";

		List o=(List) XCZmPubDAO.getDAO().executeQuery(sql, new ArrayListProcessor());
		if(o!=null&&o.size()!=0){
			for(int i=0;i<o.size();i++){
				Object[] s = (Object[]) o.get(i);
				String pk=PuPubVO.getString_TrimZeroLenAsNull(s[0]);
				List<LabIndexSetBVO> list=(List<LabIndexSetBVO>) XCZmPubDAO.getDAO().retrieveByClause(LabIndexSetBVO.class, "pk_labindexset_h='"+
						pk+"'"+" and isnull(dr,0)=0");   
				if(list!=null&&list.size()!=0){
					for(int j=0;j<list.size();j++){
					LabIndexSetBVO vo = list.get(j);
					UFBoolean istand=PuPubVO.getUFBoolean_NullAs(vo.getIsstandingcrop(), UFBoolean.FALSE);
					if(istand.booleanValue()){
						map.put(PuPubVO.getString_TrimZeroLenAsNull(vo.getPk_invmandoc()), vo);
					}
					}
				}
				}
			}

	return map;
 }

}
