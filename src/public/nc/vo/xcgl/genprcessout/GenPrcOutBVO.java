
package nc.vo.xcgl.genprcessout;
	
import nc.vo.xcgl.pub.bill.PubGeneralBVO;
	
/**
 * 原矿加工出库表体vo
 * 创建日期:2013-12-10 16:59:38
 * @author jay
 */
@SuppressWarnings("serial")
public class GenPrcOutBVO extends PubGeneralBVO {
	/**
	 * 根据成本要素 设置的成本动因量 存放字段名字
	 */
    public static String costdrivervale="num";	
    /**
     * 根据动因量分配的金额  存放字段名字
     */
    public static String costallonmy="endmny";	
	/**
	 * 表体主键
	 * */
	private String pk_general_b;
	/**
	 * 主表主键
	 * */
	private String pk_general_h;
	/**
	 * 属性pk_general_h的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_general_h () {
		return pk_general_h;
	}   
	/**
	 * 属性pk_general_h的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_general_h String
	 */
	public void setPk_general_h (String newPk_general_h ) {
	 	this.pk_general_h = newPk_general_h;
	} 	  
	public String getPk_general_b() {
		return pk_general_b;
	}
	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-10 16:59:38
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_general_h";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-10 16:59:38
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_general_b";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-10 16:59:38
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_general_b";
	}    
      
} 
