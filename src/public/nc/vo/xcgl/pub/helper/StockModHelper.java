package nc.vo.xcgl.pub.helper;
import nc.bs.framework.common.RuntimeEnv;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.stock.mod.XCAccountModBOTool;
/**
 * 现存量修复帮助类
 * @author mlr
 */
public class StockModHelper {
	public static String stockbo="nc.vo.xcgl.pub.stock.mod.XCAccountModBOTool";
     /**
		 * @author mlr
		 * @说明：（鹤岗矿业）修复矿级台账
		 * 现存量修复入口类
		 * 支持从按月结存表中取数的台账修复
		 * @param whereSql1 单据查询whereSql    对应业务单据  业务单据必须支持一个wheresql 如果业务单据对应不同的表
		 *                                     则不支持  此方法需要改进
		 * @param whereSql2 结存查询whereSql    对应结存表
		 * @param whereSql3 清楚台账数据的 whereSql  对应现存量表
		 * @param whereSql4 加载期初库存的whereSql   对应结存表  一般跟 whereSql2 一样  除非 结存表和期初表是不同表
		 * @return
		 * @throws Exception 
	*/
	public static void modifyAccounts(String whereSql1,String whereSql2,String whereSql3,String whereSql4 ,String pk_corp) throws Exception{
		if(RuntimeEnv.getInstance().isRunningInServer()){
			 XCAccountModBOTool.getInstrance().modifyAccounts(whereSql1, whereSql2, whereSql3, whereSql4, pk_corp);
		}else{
			//查询数据库 执行远程调用
			Class[] ParameterTypes = new Class[] {String.class,String.class,String.class,String.class,String.class};
			Object[] ParameterValues = new Object[] {whereSql1,whereSql2,whereSql3,whereSql4,pk_corp};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, stockbo, null, "modifyAccounts",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}		
		}
	}
}
