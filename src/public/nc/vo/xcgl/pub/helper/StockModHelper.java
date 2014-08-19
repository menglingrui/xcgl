package nc.vo.xcgl.pub.helper;
import nc.bs.framework.common.RuntimeEnv;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.stock.mod.XCAccountModBOTool;
/**
 * �ִ����޸�������
 * @author mlr
 */
public class StockModHelper {
	public static String stockbo="nc.vo.xcgl.pub.stock.mod.XCAccountModBOTool";
     /**
		 * @author mlr
		 * @˵�������׸ڿ�ҵ���޸���̨��
		 * �ִ����޸������
		 * ֧�ִӰ��½�����ȡ����̨���޸�
		 * @param whereSql1 ���ݲ�ѯwhereSql    ��Ӧҵ�񵥾�  ҵ�񵥾ݱ���֧��һ��wheresql ���ҵ�񵥾ݶ�Ӧ��ͬ�ı�
		 *                                     ��֧��  �˷�����Ҫ�Ľ�
		 * @param whereSql2 ����ѯwhereSql    ��Ӧ����
		 * @param whereSql3 ���̨�����ݵ� whereSql  ��Ӧ�ִ�����
		 * @param whereSql4 �����ڳ�����whereSql   ��Ӧ����  һ��� whereSql2 һ��  ���� ������ڳ����ǲ�ͬ��
		 * @return
		 * @throws Exception 
	*/
	public static void modifyAccounts(String whereSql1,String whereSql2,String whereSql3,String whereSql4 ,String pk_corp) throws Exception{
		if(RuntimeEnv.getInstance().isRunningInServer()){
			 XCAccountModBOTool.getInstrance().modifyAccounts(whereSql1, whereSql2, whereSql3, whereSql4, pk_corp);
		}else{
			//��ѯ���ݿ� ִ��Զ�̵���
			Class[] ParameterTypes = new Class[] {String.class,String.class,String.class,String.class,String.class};
			Object[] ParameterValues = new Object[] {whereSql1,whereSql2,whereSql3,whereSql4,pk_corp};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"����ִ��...", 1, stockbo, null, "modifyAccounts",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}		
		}
	}
}
