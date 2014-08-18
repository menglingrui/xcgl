package nc.bs.xcgl.saleshareout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.vo.pub.SuperVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.saleshareout.SaleShareoutBBVO;
import nc.vo.xcgl.saleshareout.ShareHVO;
public class QueryData {
 public Map<Integer, List<SuperVO>[]> query(String pk){
	 if(pk==null||pk.trim()==""){
		 return null;
	 }
	 Map<Integer,List<SuperVO>[]> map=new HashMap<Integer,List<SuperVO>[]>();
	
	 try {  String sql="select pk_share_h from  xcgl_share_h h where h.pk_general_h='"+pk+"'"
	             +" and coalesce(dr,0)=0";
		List o = (List) XCZmPubDAO.getDAO().executeQuery(sql, new ArrayListProcessor());
		if(o!=null&&o.size()!=0){
			for(int i=0;i<o.size();i++){
				Object[] s = (Object[]) o.get(i);
				String sharepk=PuPubVO.getString_TrimZeroLenAsNull(s[0]);
				List [] a=new List[2];
				List<ShareHVO>  headlist=(List<ShareHVO>) XCZmPubDAO.getDAO().retrieveByClause(ShareHVO.class, "pk_share_h='"+
						sharepk+"'"+" and pk_general_h='"+pk+"'"+
				                		" and isnull(dr,0)=0");   
				a[0]=headlist;
				List<SaleShareoutBBVO>  bodylist=(List<SaleShareoutBBVO>) XCZmPubDAO.getDAO().retrieveByClause(SaleShareoutBBVO.class, " pk_share_h='"+
						sharepk+"'"+" and isnull(dr,0)=0");

				a[1]=bodylist;
				map.put(i, a);
			}
			return map;
		}
	} catch (DAOException e) {
		e.printStackTrace();
	}
	return map;
 }

}
