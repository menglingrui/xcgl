package nc.vo.xcgl.pub.helper;

import nc.bs.framework.common.RuntimeEnv;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
/**
 * �ִ���������
 * @author mlr
 */
public class StockHelper {
	public static String stockbo="nc.vo.xcgl.pub.stock.BillStockTool";
	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� SuperVO[] ���ÿ����ѯά�Ȳ�ѯ�������ִ���(����ѯά�Ⱥϲ���)
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
			//��ѯ���ݿ� ִ��Զ�̵���
			Class[] ParameterTypes = new Class[] {SuperVO[].class,String.class};
			Object[] ParameterValues = new Object[] {vos,whereSql};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"����ִ��...", 1, stockbo, null, "queryStockCombin1",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}		
		}
		return vos;
	}
	/**
	 * ���ݴ�����ִ���vo ȡ��ά�� ��ѯ�ִ��� SuperVO[] ���ÿ����ѯά�Ȳ�ѯ�������ִ���(����ѯά�Ⱥϲ���)
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
			//��ѯ���ݿ� ִ��Զ�̵���
			Class[] ParameterTypes = new Class[] {SuperVO[].class};
			Object[] ParameterValues = new Object[] {vos};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"����ִ��...", 1, stockbo, null, "queryStockCombin",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}		
		}
		return vos;
	}	
}
