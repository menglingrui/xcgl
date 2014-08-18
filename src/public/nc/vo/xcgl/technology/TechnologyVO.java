package nc.vo.xcgl.technology;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;
/**
 * ���յ���VO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class TechnologyVO extends XCNewBaseVO{
	/**
	 * ����
	 */
	private String pk_technology;
	/**
	 * �������
	 */
	private String itechnologyno;
	/**
	 * ��������
	 */
	private String vtechnologyname;
	/**
	 * ���ձ���
	 */
	private String vtechnologycode;
	/**
	 * ��ע
	 */
	private String vmemo;
	
	
	public String getPk_technology() {
		return pk_technology;
	}

	public void setPk_technology(String pk_technology) {
		this.pk_technology = pk_technology;
	}

	public String getItechnologyno() {
		return itechnologyno;
	}

	public void setItechnologyno(String itechnologyno) {
		this.itechnologyno = itechnologyno;
	}

	public String getVtechnologyname() {
		return vtechnologyname;
	}

	public void setVtechnologyname(String vtechnologyname) {
		this.vtechnologyname = vtechnologyname;
	}

	public String getVtechnologycode() {
		return vtechnologycode;
	}

	public void setVtechnologycode(String vtechnologycode) {
		this.vtechnologycode = vtechnologycode;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	@Override
	public String getPKFieldName() {
		return "pk_technology";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "XCGL_TECHNOLOGY";
	}

}
