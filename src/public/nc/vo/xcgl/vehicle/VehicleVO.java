package nc.vo.xcgl.vehicle;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;
/**
 *  ��������VO 
 * @author lxh
 */
public class VehicleVO extends XCNewBaseVO{
	private static final long serialVersionUID = 1L;
	//add by whl 
	/**
	 * Ϊ��˾���ֶ����ֶ�¼�룬���ӵ�˾���ֶ�
	 */
	private String driver;
	/**
	 * ˾��
	 */
	private String pk_driver;
	/**
	 * ����
	 */
	private String vehiclename;
	/**
	 * ����
	 */
	private String vehiclecode;
	/**
	 * ��������
	 */
	private String pk_vehicle;
	/**
	 * ��Ӧ�̹�������
	 */
	private String custid;
	/**
	 * ��Ӧ�̻�������
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
