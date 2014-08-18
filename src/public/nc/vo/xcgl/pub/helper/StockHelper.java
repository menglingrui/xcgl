package nc.vo.xcgl.pub.helper;

import nc.bs.framework.common.RuntimeEnv;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
/**
 * 现存量帮助类
 * @author mlr
 */
public class StockHelper {
	public static String stockbo="nc.vo.xcgl.pub.stock.BillStockTool";
	/**
	 * 根据传入的现存量vo 取出维度 查询现存量 SuperVO[] 存放每个查询维度查询出来的现存量(按查询维度合并后)
	 * @param vos
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
     public static SuperVO[] queryStockCombin(SuperVO[] vos,String whereSql) throws Exception {
		if(vos==null || vos.length==0){
			return null;
		}
		if(RuntimeEnv.getInstance().isRunningInServer()){
			return BillStockTool.getInstrance().queryStockCombin1(vos, whereSql);
		}else{
			//查询数据库 执行远程调用
			Class[] ParameterTypes = new Class[] {SuperVO[].class,String.class};
			Object[] ParameterValues = new Object[] {vos,whereSql};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, stockbo, null, "queryStockCombin1",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}		
		}
		return vos;
	}
	/**
	 * 根据传入的现存量vo 取出维度 查询现存量 SuperVO[] 存放每个查询维度查询出来的现存量(按查询维度合并后)
	 * @param vos
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public static SuperVO[] queryStockCombin(SuperVO[] vos) throws Exception {
		if(vos==null || vos.length==0){
			return null;
		}
		if(RuntimeEnv.getInstance().isRunningInServer()){
			return BillStockTool.getInstrance().queryStockCombin(vos);
		}else{
			//查询数据库 执行远程调用
			Class[] ParameterTypes = new Class[] {SuperVO[].class};
			Object[] ParameterValues = new Object[] {vos};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, stockbo, null, "queryStockCombin",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}		
		}
		return vos;
	}	
}
