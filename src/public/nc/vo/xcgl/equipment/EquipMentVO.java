
package nc.vo.xcgl.equipment;

import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.bill.XCNewBaseVO;

/**
 * 
 * 设备档案VO
 * 
 * @author lxh
 * 
 */
@SuppressWarnings("serial")
public class EquipMentVO extends XCNewBaseVO {
	/**
	 * 型号
	 */
	private String vtype;            
	/**
	 * 编码
	 */
	private String vequip_code;      
	/**
	 * 规格
	 */
	private String vspec;			 
	/**
	 * 名称
	 */
	private String vequip_name; 	
	/**
	 * 设备主键
	 */
	private String pk_equipment; 
	/**
	 * 客商管理主键
	 */
	private String custid;
	/**
	 * 客商基本主键
	 */
	private String custbasid;
	/**
	 * 编号1
	 */
	private String vnumber1;      
	/**
	 * 编号2
	 */
	private String vnumber2;	
	/**
	 * 开始使用日期
	 */
	private UFDate dstartuse;	
	/**
	 * 使用部门 
	 */
	private String pk_usesector;
	/**
	 * 责任人 
	 */
	private String pk_responsible;
	/**
	 * 购置日期
	 */
	private UFDate dpurchase;
	/**
	 * 保修截止
	 */
	private UFDate dwarranty;
	/**
	 * 存放地点
	 */
	private String vstoragelocation;
	/**
	 * 设备状态
	 */
	private String vstatus;
	/**
	 * 购置合同编号
	 */
	private String vcontractnumber;
	
	
	
	
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustbasid() {
		return custbasid;
	}

	public void setCustbasid(String custbasid) {
		this.custbasid = custbasid;
	}

	public String getVnumber1() {
		return vnumber1;
	}

	public void setVnumber1(String vnumber1) {
		this.vnumber1 = vnumber1;
	}

	public String getVnumber2() {
		return vnumber2;
	}

	public void setVnumber2(String vnumber2) {
		this.vnumber2 = vnumber2;
	}

	public UFDate getDstartuse() {
		return dstartuse;
	}

	public void setDstartuse(UFDate dstartuse) {
		this.dstartuse = dstartuse;
	}

	public String getPk_usesector() {
		return pk_usesector;
	}

	public void setPk_usesector(String pk_usesector) {
		this.pk_usesector = pk_usesector;
	}

	public String getPk_responsible() {
		return pk_responsible;
	}

	public void setPk_responsible(String pk_responsible) {
		this.pk_responsible = pk_responsible;
	}

	public UFDate getDpurchase() {
		return dpurchase;
	}

	public void setDpurchase(UFDate dpurchase) {
		this.dpurchase = dpurchase;
	}

	public UFDate getDwarranty() {
		return dwarranty;
	}

	public void setDwarranty(UFDate dwarranty) {
		this.dwarranty = dwarranty;
	}

	public String getVstoragelocation() {
		return vstoragelocation;
	}

	public void setVstoragelocation(String vstoragelocation) {
		this.vstoragelocation = vstoragelocation;
	}

	public String getVstatus() {
		return vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}

	public String getVcontractnumber() {
		return vcontractnumber;
	}

	public void setVcontractnumber(String vcontractnumber) {
		this.vcontractnumber = vcontractnumber;
	}

	public String getPk_equipment() {
		return pk_equipment;
	}

	public void setPk_equipment(String pk_equipment) {
		this.pk_equipment = pk_equipment;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getVequip_code() {
		return vequip_code;
	}

	public void setVequip_code(String vequip_code) {
		this.vequip_code = vequip_code;
	}

	public String getVspec() {
		return vspec;
	}

	public void setVspec(String vspec) {
		this.vspec = vspec;
	}

	public String getVequip_name() {
		return vequip_name;
	}

	public void setVequip_name(String vequip_name) {
		this.vequip_name = vequip_name;
	}

	/**
	 * <p>
	 * 取得父VO主键字段.
	 * <p>
	 * 创建日期:2013-12-05 16:08:53
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParentPKFieldName() {
		return null;
	}

	/**
	 * <p>
	 * 取得表主键.
	 * <p>
	 * 创建日期:2013-12-05 16:08:53
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPKFieldName() {
		return "pk_equipment";
	}

	/**
	 * <p>
	 * 返回表名称.
	 * <p>
	 * 创建日期:2013-12-05 16:08:53
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_equipment";
	}

	/**
	 * 按照默认方式创建构造子.
	 * 
	 * 创建日期:2013-12-05 16:08:53
	 */
	public EquipMentVO() {
		super();
	}
}
