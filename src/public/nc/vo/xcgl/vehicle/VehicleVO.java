package nc.vo.xcgl.vehicle;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;
/**
 *  车辆档案VO 
 * @author lxh
 */
public class VehicleVO extends XCNewBaseVO{
	private static final long serialVersionUID = 1L;
	//add by whl 
	/**
	 * 为了司机字段能手动录入，增加的司机字段
	 */
	private String driver;
	/**
	 * 司机
	 */
	private String pk_driver;
	/**
	 * 名称
	 */
	private String vehiclename;
	/**
	 * 编码
	 */
	private String vehiclecode;
	/**
	 * 车辆主键
	 */
	private String pk_vehicle;
	/**
	 * 供应商管理主键
	 */
	private String custid;
	/**
	 * 供应商基本主键
	 */
	private String custbasid;
	
	
	
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
	public String getPk_driver() {
		return pk_driver;
	}
	public void setPk_driver(String pk_driver) {
		this.pk_driver = pk_driver;
	}
	public String getVehiclename() {
		return vehiclename;
	}
	public void setVehiclename(String vehiclename) {
		this.vehiclename = vehiclename;
	}
	public String getVehiclecode() {
		return vehiclecode;
	}
	public void setVehiclecode(String vehiclecode) {
		this.vehiclecode = vehiclecode;
	}
	public String getPk_vehicle() {
		return pk_vehicle;
	}
	public void setPk_vehicle(String pk_vehicle) {
		this.pk_vehicle = pk_vehicle;
	}
	@Override
	public String getPKFieldName() {
		return "pk_vehicle";
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	@Override
	public String getTableName() {
		return "xcgl_vehicle";
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
}
