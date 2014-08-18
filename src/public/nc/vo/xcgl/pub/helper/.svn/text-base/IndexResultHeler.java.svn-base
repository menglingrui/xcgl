package nc.vo.xcgl.pub.helper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.RuntimeEnv;
import nc.bs.xcgl.pub.IndexResultBO;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.ui.zmpub.pub.tool.MapCacheToolClient;
import nc.vo.pub.BusinessException;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.bill.IndexResultParaVO;
import nc.vo.xcgl.pub.bill.PubSampleBVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.tool.XcPubTool;
/**
 * @author whl
 */
public class IndexResultHeler {
	public static String bo = "nc.bs.xcgl.pub.IndexResultBO";
	/**
	 * key=选厂+生产线+班次+单据日期+矿区+存货(管理id) 根据参数查询化验结果 支持前台缓存 存货有值
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String,List<PubSampleBVO>> getIndexResult(IndexResultParaVO para)
			throws BusinessException {
		if (para !=null) {
			if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp())!=null){
			if (PuPubVO.getString_TrimZeroLenAsNull(para.getPk_factory()) != null) {
				if (PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline()) != null) {
					if (PuPubVO.getString_TrimZeroLenAsNull(para.getPk_classorder()) != null) {
						if (PuPubVO.getString_TrimZeroLenAsNull(para.getDbilldate()) != null) {
							if (PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea()) != null) {
								/**
								 *判断运行环境，服务端直接调用BO 客户端 创建零时缓存并远程调用数据库进行查询
								 */
								if (RuntimeEnv.getInstance().isRunningInServer()) {
									return IndexResultBO.getIndexFine(para);
								} else {
									if (MapCacheToolClient.getMapObject(
											PubOtherConst.indexresult_cache, para.getPk_minarea()
													+ para.getPk_invmandoc()) == null) {// 远程调用
										Class[] ParameterTypes = new Class[] { IndexParaVO.class };
										Object[] ParameterValues = new Object[] { para };
										Object o2 = null;
										try {
											o2 = LongTimeTask.calllongTimeService(PubOtherConst.module,
													null, "正在执行......", 1, bo, null, "getIndexResult",
													ParameterTypes, ParameterValues);
										} catch (Exception e) {
											throw new BusinessException(e.getMessage());
										}
										if (o2 == null) {
											MapCacheToolClient.pubMapObject(
													PubOtherConst.indexresult_cache, para
															.getPk_minarea()
															+ para.getPk_invmandoc(),
													PubOtherConst.isquery_cache);
										} else {
											MapCacheToolClient.pubMapObject(
													PubOtherConst.indexresult_cache, para
															.getPk_minarea()
															+ para.getPk_invmandoc(), o2);
										}
										return (Map<String,List<PubSampleBVO>>) o2;
									} else if (MapCacheToolClient.getMapObject(
											PubOtherConst.indexresult_cache,
											para.getPk_minarea() + para.getPk_invmandoc()).equals(
											PubOtherConst.isquery_cache)) {
										return null;
									} else {
										return (Map<String,List<PubSampleBVO>>) MapCacheToolClient.getMapObject(
												PubOtherConst.indexresult_cache, para.getPk_minarea()
														+ para.getPk_invmandoc());
									}
								}
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
			else{
				throw new BusinessException("公司为空");
			}
			} else {
                 throw new BusinessException("选厂为空");
			}
		}
		else{
			throw new BusinessException("参数为空");
		}
	}
//	/**
//	 * key=选厂+生产线+班次+单据日期+矿区+存货(管理id) 根据参数查询化验结果 支持前台缓存 存货没有值
//	 * 
//	 * @param para
//	 * @return
//	 * @throws BusinessException
//	 */
//	public static Map<String, List<PubSampleBBVO>> getIndexResults(
//			IndexResultParaVO para) throws BusinessException {
//		Map<String, List<PubSampleBBVO>> map = new HashMap<String, List<PubSampleBBVO>>();
//		
//		Map<String,List<PubSampleBBVO>> temp = getIndexResult(para);
//		if (temp != null && temp.size() != 0) {
//			String topkey = XcPubTool.getMapKey(para);
//			for (String key: temp.keySet()) {
//				List<PubSampleBBVO> list=temp.get(key);
//			    String calkey=topkey+key;
//		        map.put(calkey, list);
//			}
//			return map;
//		} else {
//			return null;
//		}
//	}
	/**
	 * key=选厂+生产线+班次+单据日期+矿区+存货(管理id) 根据参数查询化验结果 支持前台缓存 存货没有值
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, List<PubSampleBVO>> getIndexResults(
			IndexResultParaVO para) throws BusinessException {
		Map<String, List<PubSampleBVO>> map = new HashMap<String, List<PubSampleBVO>>();
		
		Map<String,List<PubSampleBVO>> temp = getIndexResult(para);
		if (temp != null && temp.size() != 0) {
			String topkey = XcPubTool.getMapKey(para);
			for (String key: temp.keySet()) {
				List<PubSampleBVO> list=temp.get(key);
			    String calkey=topkey+key;
		        map.put(calkey, list);
			}
			return map;
		} else {
			return null;
		}
	}
}
