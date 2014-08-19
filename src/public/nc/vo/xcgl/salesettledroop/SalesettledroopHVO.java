package nc.vo.xcgl.salesettledroop;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * ���۽���������VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class SalesettledroopHVO extends XCHYHeadSuperVO{
	/**
	 * ��������
	 * */
	private String pk_salesettledroop_h;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * ���̹�������
	 */
	private String custid;
	/**
	 * ���̻�������
	 */
	private String custbasid;
	/**
	 * ��������
	 */
	private UFDate dshipdate;
	/**
	 * ��������
	 */
	private String pk_vehicle;
	/**
	 * �Ƿ���
	 */
	private UFBoolean isseal;
	/**
	 * ����
	 */
	private String pk_deptdoc;
	
	
	
	
	
	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getPk_salesettledroop_h() {
		return pk_salesettledroop_h;
	}

	public void setPk_salesettledroop_h(String pk_salesettledroop_h) {
		this.pk_salesettledroop_h = pk_salesettledroop_h;
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


	public UFBoolean getIsseal() {
		return isseal;
	}

	public void setIsseal(UFBoolean isseal) {
		this.isseal = isseal;
	}

	@Override
	public String getPKFieldName() {
		return "pk_salesettledroop_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_salesettledroop_h";
	}

}
