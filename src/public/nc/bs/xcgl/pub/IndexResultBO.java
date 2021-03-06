package nc.bs.xcgl.pub;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.assayres.AssayResBVO;
import nc.vo.xcgl.pub.bill.IndexResultParaVO;
import nc.vo.xcgl.pub.bill.PubSampleBVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.logger.XCLogger;
import nc.vo.xcgl.pub.tool.XcPubTool;
/**
 * @author whl
 */
public class IndexResultBO {
    /**
     * key=选厂+生产线+班次+单据日期+矿区+存货(管理id)
     * 存货可以为空，其它都不允许为空
     * 根据参数查询化验结果
     * 支持前台缓存
     * @param para
     * @return
     * @throws BusinessException
     */
	public static Map<String,List<PubSampleBVO>> getIndexFine(IndexResultParaVO para)throws BusinessException{
		XCLogger.printInfor("查询化验结果开始:");
		Map<String,List<PubSampleBVO>> map=new HashMap<String, List<PubSampleBVO>>();
		if(para!=null){
			String factory=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_factory());
			String beltline=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline());
			String classorder=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_classorder());
			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(para.getDbilldate());
			String minera=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea());//矿区
//			String invmandoc=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc());//存货管理主键
			String corp=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp());
			String minetype=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minetype());
			//add connecting stock
			String pk_invmandoc1=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc1());
			if(corp!=null){
			if (factory != null) {
				if (beltline != null) {
					if (classorder != null) {
						if (dbilldate != null) {
							if (minera != null) {
								List<PubSampleBVO> list=new ArrayList<PubSampleBVO>();
							String sql = " select pk_sample,pk_invmandoc from xcgl_sample h where " +" h.pk_corp='"+corp+"'"
								        +" and h.vbillstatus='1' and h.pk_minarea='"+minera+"'";
						    sql=sql+" and h.Pk_factory='"+factory+"'";
		         		    sql=sql+" and h.Pk_beltline='"+beltline+"'";
					     	sql=sql+" and h.Pk_classorder='"+classorder+"'";
						    sql=sql+" and h.dbilldate='"+dbilldate+"'";
						    sql=sql+" and h.pk_billtype='"+PubBillTypeConst.billtype_assayres+"'";
						    sql=sql+" and h.vreserve1='"+minetype+"'";
						    sql=sql+" and h.vdef20='"+pk_invmandoc1+"'";
						    if(para.getIswater()!=null &&para.getIswater().booleanValue()==true){
						    	 sql=sql+" and h.uiswater='Y'";
						    }else if(para.getIswater()!=null &&para.getIswater().booleanValue()==false){
						    	 sql=sql+" and coalesce(h.uiswater,'N')='N'";
						    }
						    if(para.getIsrepeatsel()!=null &&para.getIsrepeatsel().booleanValue()==true){
						    	 sql=sql+" and h.isresel='Y'";
						    }else if(para.getIsrepeatsel()!=null &&para.getIsrepeatsel().booleanValue()==false){
						    	 sql=sql+" and coalesce(h.isresel,'N')='N'";
						    }
						   
						    
						List<Object []> o=(List<Object []>)XCZmPubDAO.getDAO().executeQuery(sql+" and isnull(dr,0)=0",new ArrayListProcessor());
						if(o!=null&&o.size()!=0){
							for(int i=0;i<o.size();i++){
								Object [] o1=o.get(i);
								if(o1!=null&&o1.length!=0){
								String headpk=PuPubVO.getString_TrimZeroLenAsNull(o1[0]);
								String pk_invmandoc=PuPubVO.getString_TrimZeroLenAsNull(o1[1]);
								List<PubSampleBVO> bvolist=(List<PubSampleBVO>) XCZmPubDAO.getDAO().retrieveByClause(AssayResBVO.class, " isnull(dr,0)=0 and pk_sample='"+headpk+"'");
								if(bvolist!=null&&bvolist.size()!=0){
									map.put(pk_invmandoc, bvolist);
									//deal mease
									XcPubTool.dealLabGrade(bvolist);
								}
								
								
//								String bodysql=" select pk_sample_b,pk_invmandoc from xcgl_sample_b h " +
//										" where h.pk_sample='"+headpk+"'";
//								List<Object []>  o2=(List<Object []> )XCZmPubDAO.getDAO().executeQuery(bodysql+" and isnull(dr,0)=0",new ArrayListProcessor());
//								if(o2!=null&&o2.size()!=0){
//									for(int j=0;j<o2.size();j++){
//										if(o2.get(j)!=null&&o2.get(j).length!=0){
//										String bodypk=PuPubVO.getString_TrimZeroLenAsNull(o2.get(j)[0]);
//										List<PubSampleBVO> temp=  (List<PubSampleBVO>) XCZmPubDAO.getDAO().retrieveByClause(AssayResBBVO.class, " isnull(dr,0)=0 and pk_sample_b ='"+bodypk+"'");
//									    if(temp!=null&&temp.size()!=0){
//										map.put(PuPubVO.getString_TrimZeroLenAsNull(o2.get(j)[1]), temp);
//										}
//										}
//									}
//								}
								}
							}
						}
						 XCLogger.printInfor("查询化验结果方法结束：");
									return map;
								
							} else {
                               throw new BusinessException("矿区为空");
							}
						} else {
                            throw new BusinessException("制单日期为空");
						}
					} else {
                         throw new BusinessException("班次为空");
					}
					
				} else {
                       throw new BusinessException("生产线为空");
				}
			}
			else {
                 throw new BusinessException("选厂为空");
			}
		}
		else{
			throw new BusinessException("公司为空");
		}
		}else{
			throw new BusinessException("参数为空");
		}
     }
	}



