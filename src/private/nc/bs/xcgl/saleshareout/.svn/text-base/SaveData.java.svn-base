package nc.bs.xcgl.saleshareout;

import java.util.List;
import java.util.Map;

import nc.bs.dao.DAOException;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.vo.pub.SuperVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBBVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBVO;
import nc.vo.xcgl.saleshareout.ShareHVO;

public class SaveData {
	   /**
	    *数据的后台保存 
	    */
 public void save(Map<String, SuperVO> headdata,Map<String, List<SuperVO>> bodydata) throws Exception{
 	if(bodydata==null||bodydata.size()<=0){
 		throw new Exception("分摊表体数据为空!");
 	}
 	if(headdata==null||headdata.size()<=0){
 		throw new Exception("分摊表头数据为空");
 	}
 	//数据的保存，headdata 和bodydata 都是map类型，并且他们的主键都相同，都是表头的行号
 	//遍历表头map
 	//先删出已经存入数据库的关联数据
 	//根据pk_general_h的值删除
 	for(String key:headdata.keySet()){
 		SuperVO hvo = headdata.get(key);
 		if(hvo!=null){
 			
        String generalh=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_general_h"));
        if(generalh!=null&&generalh.trim()!=""){
        	String sql=" select pk_share_h from xcgl_share_h  h where h.pk_general_h='"+generalh+"'"
        	+" and isnull(dr,0)=0";
        	Object[]shareh=(Object[]) ZmPubDAO.getDAO().executeQuery(sql, new ArrayProcessor());
        	if(shareh!=null&&shareh.length!=0){
        		for(int i=0;i<shareh.length;i++){
        			ZmPubDAO.getDAO().deleteByClause(SaleShareoutBBVO.class, "pk_share_h='"
        					+PuPubVO.getString_TrimZeroLenAsNull(shareh[i])+"'");
        		}
        	}
        	ZmPubDAO.getDAO().deleteByClause(ShareHVO.class, "pk_general_h='"+generalh+"'");
        }
 		}
 	}
 	//存储数据
 	for(String key:headdata.keySet()){
 		SuperVO hvo = headdata.get(key);
// 		ZmPubDAO.getDAO().insertObjectWithPK(headdata.get(key), meta);
// 		ZmPubDAO.getDAO().deleteByPK(SaleShareoutBVO.class,
// 		String pk=PuPubVO.getString_TrimZeroLenAsNull( headdata.get(key).getAttributeValue("pk_general_b"));
// 		if(pk==null||pk.trim()==""){
 		ShareHVO vo=changeVoType(headdata.get(key));
 		if(vo!=null){
 			String headpk=XCZmPubDAO.getDAO().insertVO(vo);
            List<SuperVO> list = bodydata.get(key);
            if(list!=null&&list.size()!=0){
            	for(int i=0;i<list.size();i++){
            		if(list.get(i)!=null){
            			list.get(i).setAttributeValue("pk_share_h", headpk);
            		}
            		else{
            			list.remove(i);
            		}
            	}
            	XCZmPubDAO.getDAO().insertVOArray(list.toArray(new SuperVO[0]));
            }
 		}
 	}
// 					SingleVOChangeDataBsTool.runChangeVOAry(bvos, JobmngfilVO.class, 
 //	"nc.bs.xew.procreate.CHGXEW4TPROJMAN",PubConst.module,Xewpubconst.module);		
 }

	private ShareHVO changeVoType(SuperVO superVO) {
		if(superVO!=null){
			ShareHVO hvo=new ShareHVO();
			for(int i=0;i<superVO.getAttributeNames().length;i++){
				hvo.setAttributeValue(superVO.getAttributeNames()[i],
						superVO.getAttributeValue(superVO.getAttributeNames()[i]));
			}
			return hvo;
		}
		return null;
	}
	/**
	 * 聚合vo的表头vo是已近取到化验结果，并对数据进行了处理之后的SaleShareoutBVO
	 * 表体是对应的表头vo的规划求解的结果
	 * @param dbvos
	 * @throws DAOException
	 */
	public void savefixvo(HYBillVO[] dbvoss) throws DAOException{
		for(int j=0;j<dbvoss.length;j++){
			HYBillVO  dbvos=dbvoss[j];
			if(dbvos==null||dbvos.getChildrenVO()==null||dbvos.getChildrenVO().length==0){
				return;
			}
			SaleShareoutBVO hvo=(SaleShareoutBVO) dbvos.getParentVO();
			String  generalh=hvo.getPk_general_h();
			String  generalb=hvo.getPk_general_b();
			// only delete one
			if(j==0){
	         if(generalb!=null&&generalb.trim()!=""){
	        	String sql=" select pk_share_h from xcgl_share_h  h where h.pk_general_h='"+generalh+"' and h.pk_general_b='"+generalb+"'"+" and isnull(dr,0)=0";
	        	List shareh=(List) ZmPubDAO.getDAO().executeQuery(sql, new ArrayListProcessor());
	        	if(shareh!=null&&shareh.size()!=0){
	        		for(int i=0;i<shareh.size();i++){
	        			Object[] objs=(Object[]) shareh.get(i);
	        			ZmPubDAO.getDAO().deleteByClause(SaleShareoutBBVO.class, "pk_share_h='"
	        					+PuPubVO.getString_TrimZeroLenAsNull(objs[0])+"'");
	        			
	        			ZmPubDAO.getDAO().deleteByClause(ShareHVO.class, "pk_share_h='"
	        					+PuPubVO.getString_TrimZeroLenAsNull(objs[0])+"'");
	        		}
	        	}    	
	         }
			}
	        ShareHVO vo = changeVoType(hvo);
	        String headpk=XCZmPubDAO.getDAO().insertVO(vo);
	        SaleShareoutBBVO[] bvos = (SaleShareoutBBVO[]) dbvos.getChildrenVO();
	        if(bvos!=null&&bvos.length!=0){
	        	for(int i=0;i<bvos.length;i++){
	        		bvos[i].setPk_share_h(headpk);
	        	}
	        	XCZmPubDAO.getDAO().insertVOArray(bvos);
	        }
	        
		}
		
	}
	/**
	 * 聚合vo的表头vo是已近取到化验结果，并对数据进行了处理之后的SaleShareoutBVO
	 * 表体是对应的表头vo的规划求解的结果
	 * @param dbvos
	 * @throws DAOException
	 */
	public void savefixvo(HYBillVO dbvos) throws DAOException{
		if(dbvos==null||dbvos.getChildrenVO()==null||dbvos.getChildrenVO().length==0){
			return;
		}
		SaleShareoutBVO hvo=(SaleShareoutBVO) dbvos.getParentVO();
		String  generalh=hvo.getPk_general_h();
		String  generalb=hvo.getPk_general_b();
        if(generalb!=null&&generalb.trim()!=""){
        	String sql=" select pk_share_h from xcgl_share_h  h where h.pk_general_h='"+generalh+"' and h.pk_general_b='"+generalb+"'"+" and isnull(dr,0)=0";
        	Object[]shareh=(Object[]) ZmPubDAO.getDAO().executeQuery(sql, new ArrayProcessor());
        	if(shareh!=null&&shareh.length!=0){
        		for(int i=0;i<shareh.length;i++){
        			ZmPubDAO.getDAO().deleteByClause(SaleShareoutBBVO.class, "pk_share_h='"
        					+PuPubVO.getString_TrimZeroLenAsNull(shareh[i])+"'");
        			
        			ZmPubDAO.getDAO().deleteByClause(ShareHVO.class, "pk_share_h='"
        					+PuPubVO.getString_TrimZeroLenAsNull(shareh[i])+"'");
        		}
        	}    	
        }
        ShareHVO vo = changeVoType(hvo);
        String headpk=XCZmPubDAO.getDAO().insertVO(vo);
        SaleShareoutBBVO[] bvos = (SaleShareoutBBVO[]) dbvos.getChildrenVO();
        if(bvos!=null&&bvos.length!=0){
        	for(int i=0;i<bvos.length;i++){
        		bvos[i].setPk_share_h(headpk);
        	}
        	XCZmPubDAO.getDAO().insertVOArray(bvos);
        }
        
	}

}
