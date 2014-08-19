package nc.bs.xcgl.pub.bill;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nc.bs.dao.DAOException;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.bs.zmpub.pub.bill.SonBillSave;
import nc.itf.zmpub.pub.ISonVO;
import nc.itf.zmpub.pub.ISonVOH;
import nc.jdbc.framework.SQLParameter;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.pub.itf.ISonExtendVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
import nc.vo.xcgl.pub.logger.XCLogger;
/**
 * 支持两张孙表保存的动作脚本保存类
 * @author mlr
 */
public class XCSonExtBillSave extends SonBillSave{
	
	public XCZmPubDAO getDao1(){
        return XCZmPubDAO.getDAO();
	}
	
	public java.util.ArrayList saveBill1(nc.vo.pub.AggregatedValueObject billVo) throws Exception{
		XCLogger.printInfor("调用XCSonBillSave保存方法开始：");
		XCLogger.printInfor("单据类型："+billVo.getParentVO().getAttributeValue("pk_billtype"));
		if(billVo==null){
			XCLogger.printError("传入数据为空");
			throw new Exception("传入数据为空");
		}	
		if(billVo.getParentVO()==null ){
			XCLogger.printError("表头不能为空");
			throw new Exception("表头不能为空");
		}
		if(billVo.getChildrenVO()==null || billVo.getChildrenVO().length==0){
			XCLogger.printError("表体不能为空");
			throw new Exception("表体不能为空");
		}	
		if(!(billVo instanceof ISonVOH)){
			XCLogger.printError("聚合vo没有实现ISonVOH接口");
			throw new Exception("聚合vo没有实现ISonVOH接口");
		}
		
		if(!(billVo.getChildrenVO()[0] instanceof ISonVO)){
			XCLogger.printError("子表vo没有实现ISonVO接口");
			throw new Exception("子表vo没有实现ISonVO接口");
		}
		ioh=(ISonVOH)billVo;
	    HYBillVO  oldbillvo=(HYBillVO) ObjectUtils.serializableClone(billVo);
	    HYBillVO billvov=(HYBillVO)billVo;
	    XCLogger.printInfor("基本单据信息保存开始：");
	    java.util.ArrayList retAry = super.saveBill(billvov);
	  
		if(retAry == null || retAry.size() == 0){
			throw new BusinessException("基本单据信息保存失败");
		}
		XCLogger.printInfor("基本单据信息保存结束：");
		HYBillVO newBillVo = (HYBillVO)retAry.get(1);
		if(oldbillvo.getParentVO().getPrimaryKey()==null){
			//新增保存手动查出表体信息 
			//因为新增保存的话  
			//保存后 返回的聚合vo 是不带表体信息的
			setChildData(newBillVo,getSuperDMO());			
		}
		XCLogger.printInfor("同步子表主键到孙表中开始：");
		insertBody_Pk(newBillVo,oldbillvo);		
		XCLogger.printInfor("同步子表主键到孙表中结束：");
		
		XCLogger.printInfor("孙表保存开始：");
		if(oldbillvo.getParentVO().getPrimaryKey()==null){
			//新增保存 
			//如果是新增保存的话：
			// 首先要  将billvo 深层克隆一份  作为 旧数据为  oldbillvo
			// 保存billvo  获得返回结果   然后取得 billvo的表体 主键  根据行号信息  
			// 将表体主键  同步到 oldbillvo的孙表中
			// 然后 保存孙表信息
		     //处理新增保存			
			saveXiHa(oldbillvo);			
		}else{
			//如果是 修改保存的话   
			// 首先要  将billvo 深层克隆一份  作为 旧数据为  oldbillvo
			// 保存billvo  获得返回结果   然后取得 billvo的表体 主键  根据行号信息  
			// 将表体主键  同步到 oldbillvo的孙表中
			
			//然后 从节的  oldbillvo 中根据vo状态 找出  新增 删除 修改的表体信息
			//新增的  保存孙表信息
			//修改的  先删除原来的孙表信息  然后保存最新的孙表信息
			//删除的  删除孙表信息		
		   //处理修改保存
		   Map<String,List<SuperVO>>  map=splitVO((SuperVO[])oldbillvo.getChildrenVO());
		   List<SuperVO> adds=map.get("add");
		   List<SuperVO> edits=map.get("edit");
		   List<SuperVO> detes=map.get("dete");
		   List<SuperVO> unchg=map.get("unchg");
		   if(adds!=null && adds.size()!=0){
			  for(int i=0;i<adds.size();i++){
				 saveXiHa(((ISonVO)(adds.get(i))).getSonVOS());
				 saveXiHa(((ISonExtendVO)(adds.get(i))).getSonVOS1());
			  } 
		   }
           if(edits!=null && edits.size()!=0){
        	   for(int i=0;i<edits.size();i++){
				 updateXiHa(((ISonVO)(edits.get(i))).getSonVOS());
				 updateXiHa(((ISonExtendVO)(edits.get(i))).getSonVOS1());
  			  } 
		   }
           if(detes!=null && detes.size()!=0){
        	   for(int i=0;i<detes.size();i++){
          		 deleteXiHa((detes.get(i)).getPrimaryKey());
          		 deleteXiHa1((detes.get(i)).getPrimaryKey());
    		  } 
		   }	
           if(unchg!=null && unchg.size()!=0){
        	   for(int i=0;i<unchg.size();i++){
        		   updateXiHa(((ISonVO)(unchg.get(i))).getSonVOS());
        		   updateXiHa(((ISonExtendVO)(unchg.get(i))).getSonVOS1());
      		    } 
		   }	
		}
		ioh=null;
		sql=null;
		XCLogger.printInfor("孙表保存结束：");		
		XCLogger.printInfor("单据类型："+billVo.getParentVO().getAttributeValue("pk_billtype")+"返回值为："+retAry);
		XCLogger.printInfor("调用XCSonBillSave保存方法结束：");		
        return retAry;
	}
	
	public void deleteXiHa1(String pk) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DAOException {
		SQLParameter para=new SQLParameter();
		para.addParam(pk.trim());
	    String sql=getSql1();	
		getDao1().executeUpdate(sql, para);		
	}
	public String sql1=null;
	private String getSql1() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		  if(sql1==null){
			String str=((ISonExtendVOH)ioh).getSonClass1();
			SuperVO ivo=(SuperVO) Class.forName(str).newInstance();
			sql1=" update "+ivo.getTableName()+" set dr=1 where "+ivo.getParentPKFieldName()+"=?";
		  }	
		  return sql1;
		}
	/**
	 * 将newBillVo保存时生成的表头表体主键信息，同步到oldbillvo中
	 * @author mlr
	 * @说明：（鹤岗矿业）
	 * 2011-10-12下午06:07:16
	 * @param newBillVo
	 * @param oldbillvo
	 * @throws Exception 
	 */
	public void insertBody_Pk(HYBillVO newBillVo,HYBillVO oldbillvo) throws Exception {
		 super.insertBody_Pk(newBillVo,oldbillvo);
		 insertBody_Pk1(newBillVo,oldbillvo);
	}

	public void insertBody_Pk1(HYBillVO newBillVo, HYBillVO oldbillvo) throws Exception{
		//根据 行号 同步孙表的主键    但是存在表体修改删除后     又进行了增行会存在行号重复的问题		
		//保存后的子表  已经去掉了删除行  但没有去掉孙表信息  newChilds
	    //保存前的子表  没有去掉删除行 （存在vo状态） 有孙表的信息oldChilds		
		SuperVO[]  newChilds=(SuperVO[]) newBillVo.getChildrenVO();
		if(newChilds==null || newChilds.length==0 ){
			return;
		}
		SuperVO[] oldChilds=(SuperVO[]) oldbillvo.getChildrenVO();
		for(int i=0;i<newChilds.length;i++){
			String pk=newChilds[i].getPrimaryKey();
	//		String pk_h=PuPubVO.getString_TrimZeroLenAsNull(newChilds[i].getAttributeValue(newChilds[i].getParentPKFieldName()));
			ISonExtendVO newso=(ISonExtendVO)newChilds[i];
			String crowno=PuPubVO.getString_TrimZeroLenAsNull(newChilds[i].getAttributeValue(newso.getRowNumName1()));   
			if(crowno==null || crowno.length()==0)
				throw new Exception("行号不能为空");
            for(int j=0;j<oldChilds.length;j++){
               if(oldChilds[j].getStatus()==VOStatus.DELETED){
            	   continue;
               }
               ISonExtendVO oldso=(ISonExtendVO) oldChilds[j];
               if(crowno.equalsIgnoreCase(PuPubVO.getString_TrimZeroLenAsNull(oldChilds[j].getAttributeValue(oldso.getRowNumName1())))){
            	   oldChilds[j].setPrimaryKey(newChilds[i].getPrimaryKey());
            	   List list= oldso.getSonVOS1();
            	   if(list!=null && list.size()!=0){
            	      for(int k=0;k<list.size();k++){
       				  // ((SuperVO)list.get(k)).setPrimaryKey(pk);
       				((SuperVO)list.get(k)).setAttributeValue(((SuperVO)list.get(k)).getParentPKFieldName(), pk);
       			      }
            	   }
               }
           }
		}}
	
	/**
	 * 直接将孙表数据存入数据库
	 * @param oldbillvo
	 * @throws Exception
	 */
	public void saveXiHa(HYBillVO oldbillvo) throws Exception {
	   super.saveXiHa(oldbillvo);
	   saveXiHa1(oldbillvo);
		
	}

	public void saveXiHa1(HYBillVO oldbillvo) throws Exception {
		SuperVO[] oldchilds=(SuperVO[]) oldbillvo.getChildrenVO();
		if(oldchilds!=null && oldchilds.length!=0){
			List<SuperVO> list=new ArrayList<SuperVO>();
				for(int i=0;i<oldchilds.length;i++){	
					if(((ISonExtendVO)oldchilds[i]).getSonVOS1()!=null&& ((ISonExtendVO)oldchilds[i]).getSonVOS1().size()>0){
					  list.addAll(((ISonExtendVO)oldchilds[i]).getSonVOS1());
					}
				}
				saveXiHa(list);
		}		
	}
}
