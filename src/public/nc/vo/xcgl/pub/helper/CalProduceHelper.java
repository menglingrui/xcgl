package nc.vo.xcgl.pub.helper;
import java.util.List;
import java.util.Map;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.xcgl.pub.CalProduceBO;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.pub.bill.CalYieldVO;
import nc.vo.xcgl.pub.bill.ProSetEnum;
import nc.vo.xcgl.pub.bill.ProSetFormulaVO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
/**
 * 生产加工产量计算帮助类，不区分前后台
 * @author mlr
 */
public class CalProduceHelper {
	public static String bo="nc.bs.xcgl.pub.CalProduceBO";
    /**
     * 生产加工计算精粉产量
     * @return 
     */
	public static Map<String, Map<ProSetEnum, List<CalYieldVO>>> calPowderYield(ProSetParaVO[] paras)throws BusinessException{
		if(RuntimeEnv.getInstance().isRunningInServer()){
			 return new CalProduceBO().calPowderYield(paras);
		}else{
			Class[] ParameterTypes = new Class[] { ProSetParaVO[].class };
			Object[] ParameterValues = new Object[] { paras };
			Object o2 = null;
			try {
				o2 = LongTimeTask.calllongTimeService(PubOtherConst.module,
						null, "正在执行......", 1, bo, null, "calPowderYield",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			return (Map<String, Map<ProSetEnum, List<CalYieldVO>>>) o2 ;	
		}				
	}
	/**
     * 生产加工计算精粉产量
     * @return 
     */
	public static Map<String,List<ProSetFormulaVO>> calPowderYield1(ProSetParaVO[] paras)throws BusinessException{
		if(RuntimeEnv.getInstance().isRunningInServer()){
			 return new CalProduceBO().calPowderYield1(paras);
		}else{
			Class[] ParameterTypes = new Class[] { ProSetParaVO[].class };
			Object[] ParameterValues = new Object[] { paras };
			Object o2 = null;
			try {
				o2 = LongTimeTask.calllongTimeService(PubOtherConst.module,
						null, "正在执行......", 1, bo, null, "calPowderYield1",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			return (Map<String,List<ProSetFormulaVO>>) o2 ;	
		}		
	}
}
