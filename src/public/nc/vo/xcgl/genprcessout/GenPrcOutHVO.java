
package nc.vo.xcgl.genprcessout;
import nc.vo.xcgl.pub.bill.PubGeneralHVO;
/**
 * 原矿加工出库表头vo
 * 创建日期:2013-12-10 16:59:38
 * @author jay
 */
@SuppressWarnings("serial")
public class GenPrcOutHVO extends PubGeneralHVO {
	/**
	 * cost workcenters
	 */
	public static String[] workcenters={"h.pk_factory","h.vdef1","h.vdef2","h.vdef3","h.vdef4",
		 "h.vdef5","h.vdef6","h.vdef7","h.vdef8"};
    /**
     * 关联矿石
     */
	public String vdef20;
	/**
	 * 表头主键
	 * */
	private String pk_general_h;
	/**
	 * 球磨工1
	 * */
	private String pk_psndoc1;
	/**
	 * 球磨工2
	 * */
	private String pk_psndoc2;
	/**
	 * 球磨工3
	 * */
	private String pk_psndoc3;
	/**
	 * 浮选工1
	 * */
	private String pk_psndoc4;
	/**
	 * 浮选工2
	 * */
	private String pk_psndoc5;
	/**
	 * 浮选工3
	 * */
	private String pk_psndoc6;
	/**
	 * 人员基本档案主键1
	 */
	private String pk_psnbasdoc1;
	/**
	 * 人员基本档案主键2
	 */
	private String pk_psnbasdoc2;
	/**
	 * 人员基本档案主键3
	 */
	private String pk_psnbasdoc3;
	/**
	 * 人员基本档案主键4
	 */
	private String pk_psnbasdoc4;
	/**
	 * 人员基本档案主键5
	 */
	private String pk_psnbasdoc5;
	/**
	 * 人员基本档案主键6
	 */
	private String pk_psnbasdoc6;
	/**
	 *计算类型 
	 *0--矿石
	 *1--矿快
	 *2--其它
	 */
	private Integer icalctype;
	
	/**
	 *出入库类型 收发类别
	 */
	private String pk_restype;
	
	public String getVdef20() {
		return vdef20;
	}
	public void setVdef20(String vdef20) {
		this.vdef20 = vdef20;
	}
	public Integer getIcalctype() {
		return icalctype;
	}
	public void setIcalctype(Integer icalctype) {
		this.icalctype = icalctype;
	}

//	private String  istoragetype;
	public String getPk_psnbasdoc1() {
		return pk_psnbasdoc1;
	}
	public void setPk_psnbasdoc1(String pk_psnbasdoc1) {
		this.pk_psnbasdoc1 = pk_psnbasdoc1;
	}
	public String getPk_psnbasdoc2() {
		return pk_psnbasdoc2;
	}
	public void setPk_psnbasdoc2(String pk_psnbasdoc2) {
		this.pk_psnbasdoc2 = pk_psnbasdoc2;
	}
	public String getPk_psnbasdoc3() {
		return pk_psnbasdoc3;
	}
	public void setPk_psnbasdoc3(String pk_psnbasdoc3) {
		this.pk_psnbasdoc3 = pk_psnbasdoc3;
	}
	public String getPk_psnbasdoc4() {
		return pk_psnbasdoc4;
	}
	public void setPk_psnbasdoc4(String pk_psnbasdoc4) {
		this.pk_psnbasdoc4 = pk_psnbasdoc4;
	}
	public String getPk_psnbasdoc5() {
		return pk_psnbasdoc5;
	}
	public void setPk_psnbasdoc5(String pk_psnbasdoc5) {
		this.pk_psnbasdoc5 = pk_psnbasdoc5;
	}
	public String getPk_psnbasdoc6() {
		return pk_psnbasdoc6;
	}
	public void setPk_psnbasdoc6(String pk_psnbasdoc6) {
		this.pk_psnbasdoc6 = pk_psnbasdoc6;
	}
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
	/**
	 * 属性vdef11的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	/**
	 * 属性pk_psndoc2的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_psndoc2 () {
		return pk_psndoc2;
	}   
	/**
	 * 属性pk_psndoc2的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_psndoc2 String
	 */
	public void setPk_psndoc2 (String newPk_psndoc2 ) {
	 	this.pk_psndoc2 = newPk_psndoc2;
	} 	  
	/**
	 * 属性pk_psndoc1的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_psndoc1 () {
		return pk_psndoc1;
	}   
	/**
	 * 属性pk_psndoc1的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_psndoc1 String
	 */
	public void setPk_psndoc1 (String newPk_psndoc1 ) {
	 	this.pk_psndoc1 = newPk_psndoc1;
	} 	  	  
	/**
	 * 属性pk_psndoc5的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_psndoc5 () {
		return pk_psndoc5;
	}   
	/**
	 * 属性pk_psndoc5的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_psndoc5 String
	 */
	public void setPk_psndoc5 (String newPk_psndoc5 ) {
	 	this.pk_psndoc5 = newPk_psndoc5;
	} 	  
	/**
	 * 属性pk_psndoc3的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_psndoc3 () {
		return pk_psndoc3;
	}   
	/**
	 * 属性pk_psndoc3的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_psndoc3 String
	 */
	public void setPk_psndoc3 (String newPk_psndoc3 ) {
	 	this.pk_psndoc3 = newPk_psndoc3;
	} 	    
	/**
	 * 属性pk_psndoc4的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_psndoc4 () {
		return pk_psndoc4;
	}   
	/**
	 * 属性pk_psndoc4的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_psndoc4 String
	 */
	public void setPk_psndoc4 (String newPk_psndoc4 ) {
	 	this.pk_psndoc4 = newPk_psndoc4;
	} 	  
	/**
	 * 属性pk_psndoc6的Getter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_psndoc6 () {
		return pk_psndoc6;
	}   
	/**
	 * 属性pk_psndoc6的Setter方法.
	 * 创建日期:2013-12-10 16:59:38
	 * @param newPk_psndoc6 String
	 */
	public void setPk_psndoc6 (String newPk_psndoc6 ) {
	 	this.pk_psndoc6 = newPk_psndoc6;
	} 	  	  
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-10 16:59:38
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-10 16:59:38
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_general_h";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-10 16:59:38
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_general_h";
	}
	public String getPk_restype() {
		return pk_restype;
	}
	public void setPk_restype(String pk_restype) {
		this.pk_restype = pk_restype;
	}       
} 
