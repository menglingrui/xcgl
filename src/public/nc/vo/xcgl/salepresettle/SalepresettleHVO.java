package nc.vo.xcgl.salepresettle;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * 销售预结算表头vo
 * @author lxh
 */
@SuppressWarnings("serial")
public class SalepresettleHVO extends XCHYHeadSuperVO{

	/**
	 * 主表主键
	 * */
	private String pk_salepresettle_h;
	/**
	 * 存货管理主键
	 */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 客商管理主键
	 */
	private String custid;
	/**
	 * 客商基本主键
	 */
	private String custbasid;
	/**
	 * 发货日期
	 */
	private UFDate dshipdate;
	/**
	 * 车辆主键
	 */
	private String pk_vehicle;
	/**
	 * 化验类型
	 * 0初检样  1客户样  2仲裁样  3复检样  4其他
	 */
	private Integer vassaytype;
	/**
	 * 优质优价方案
	 */
	private String qualityplan;
	/**
	 * 是否封存
	 */
	private UFBoolean isseal;
	/**
	 * 部门
	 */
	private String pk_deptdoc;
	
	public String getQualityplan() {
		return qualityplan;
	}

	public void setQualityplan(String qualityplan) {
		this.qualityplan = qualityplan;
	}

	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getPk_salepresettle_h() {
		return pk_salepresettle_h;
	}

	public void setPk_salepresettle_h(String pk_salepresettle_h) {
		this.pk_salepresettle_h = pk_salepresettle_h;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}

	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}

	public UFDate getDshipdate() {
		return dshipdate;
	}

	public void setDshipdate(UFDate dshipdate) {
		this.dshipdate = dshipdate;
	}

	public String getPk_vehicle() {
		return pk_vehicle;
	}

	public void setPk_vehicle(String pk_vehicle) {
		this.pk_vehicle = pk_vehicle;
	}

	public Integer getVassaytype() {
		return vassaytype;
	}

	public void setVassaytype(Integer vassaytype) {
		this.vassaytype = vassaytype;
	}

	public UFBoolean getIsseal() {
		return isseal;
	}

	public void setIsseal(UFBoolean isseal) {
		this.isseal = isseal;
	}

	@Override
	public String getPKFieldName() {
		return "pk_salepresettle_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_salepresettle_h";
	}

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

}
