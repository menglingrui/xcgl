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
 * 该类是用于多子表的聚合VO
 *
 * 创建日期:2014-04-01 09:27:46
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ExAggSoctVO extends HYBillVO implements IExAggVO{
    
	
	//用于装载多子表数据的HashMap
	private HashMap hmChildVOs = new HashMap();
	/**
	 * 此处插入方法说明。 创建日期：(01-3-20 17:36:56)
	 * 
	 * @return nc.vo.pub.ValueObject[]
	 */
	public void setChildrenVO(
			nc.vo.pub.CircularlyAccessibleValueObject[] children) {
          super.setChildrenVO(children);
         // setTableVO(getTableCodes()[0], children);
	}
	
	/**
	 * 返回多个子表的编码
	 * 必须与单据模版的页签编码对应
	 * 创建日期：2014-04-01 09:27:46
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
	 * 返回多个子表的中文名称
	 * 创建日期：2014-04-01 09:27:46
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
	 * 取得所有子表的所有VO对象
	 * 创建日期：2014-04-01 09:27:46
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
	 * 返回每个子表的VO数组
	 * 创建日期：2014-04-01 09:27:46
	 * @return CircularlyAccessibleValueObject[]
	 */
	public CircularlyAccessibleValueObject[] getTableVO(String tableCode){
		
		return (CircularlyAccessibleValueObject[])
		            hmChildVOs.get(tableCode);
	}
	
	/**
	 * 为特定子表设置VO数据
	 */
	public void setTableVO(String tableCode, CircularlyAccessibleValueObject[] vos) {
		 if(tableCode!=null && tableCode.equals(getTableCodes()[0])){	
				setChildrenVO(vos);
			   }	
			hmChildVOs.put(tableCode, vos);
		  
	}
	
	/**
	 * 缺省的页签编码
	 * 创建日期：2014-04-01 09:27:46
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
