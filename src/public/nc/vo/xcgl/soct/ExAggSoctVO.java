package nc.vo.xcgl.soct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.IExAggVO;
import nc.vo.trade.pub.HYBillVO;


/**
 * 
 * ���������ڶ��ӱ�ľۺ�VO
 *
 * ��������:2014-04-01 09:27:46
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ExAggSoctVO extends HYBillVO implements IExAggVO{
    
	
	//����װ�ض��ӱ����ݵ�HashMap
	private HashMap hmChildVOs = new HashMap();
	/**
	 * �˴����뷽��˵���� �������ڣ�(01-3-20 17:36:56)
	 * 
	 * @return nc.vo.pub.ValueObject[]
	 */
	public void setChildrenVO(
			nc.vo.pub.CircularlyAccessibleValueObject[] children) {
          super.setChildrenVO(children);
         // setTableVO(getTableCodes()[0], children);
	}
	
	/**
	 * ���ض���ӱ�ı���
	 * �����뵥��ģ���ҳǩ�����Ӧ
	 * �������ڣ�2014-04-01 09:27:46
	 * @return String[]
	 */
	public String[] getTableCodes(){
		          
		return new String[]{
				"xcgl_soct_b",
				"xcgl_soct_b2",
				"xcgl_soct_b3",
				"xcgl_soct_b4",
				"xcgl_soct_b7",
//				"xcgl_soct_b5",
//				"xcgl_soct_b6",
		   		    };
	}
	
	
	/**
	 * ���ض���ӱ����������
	 * �������ڣ�2014-04-01 09:27:46
	 * @return String[]
	 */
	public String[] getTableNames(){
		
		return new String[]{
				"xcgl_soct_b",
				"xcgl_soct_b2",
				"xcgl_soct_b3",
				"xcgl_soct_b4",
				"xcgl_soct_b7",
//				"xcgl_soct_b5",
//				"xcgl_soct_b6",
                         };
	}
	
	
	/**
	 * ȡ�������ӱ������VO����
	 * �������ڣ�2014-04-01 09:27:46
	 * @return CircularlyAccessibleValueObject[]
	 */
	public CircularlyAccessibleValueObject[] getAllChildrenVO(){
		
		ArrayList al = new ArrayList();
		for(int i = 0; i < getTableCodes().length; i++){
			CircularlyAccessibleValueObject[] cvos
			        = getTableVO(getTableCodes()[i]);
			if(cvos != null)
				al.addAll(Arrays.asList(cvos));
		}
		
		return (SuperVO[]) al.toArray(new SuperVO[0]);
	}
	
	
	/**
	 * ����ÿ���ӱ��VO����
	 * �������ڣ�2014-04-01 09:27:46
	 * @return CircularlyAccessibleValueObject[]
	 */
	public CircularlyAccessibleValueObject[] getTableVO(String tableCode){
		
		return (CircularlyAccessibleValueObject[])
		            hmChildVOs.get(tableCode);
	}
	
	/**
	 * Ϊ�ض��ӱ�����VO����
	 */
	public void setTableVO(String tableCode, CircularlyAccessibleValueObject[] vos) {
		 if(tableCode!=null && tableCode.equals(getTableCodes()[0])){	
				setChildrenVO(vos);
			   }	
			hmChildVOs.put(tableCode, vos);
		  
	}
	
	/**
	 * ȱʡ��ҳǩ����
	 * �������ڣ�2014-04-01 09:27:46
	 * @return String 
	 */
	public String getDefaultTableCode(){
		
		return getTableCodes()[0];
	}


	public SuperVO[] getChildVOsByParentId(String tableCode, String parentid) {
		// TODO Auto-generated method stub
		return null;
	}


	public HashMap getHmEditingVOs() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public String getParentId(SuperVO item) {
		// TODO Auto-generated method stub
		return null;
	}


	public void setParentId(SuperVO item, String id) {
		// TODO Auto-generated method stub
		
	}

}
