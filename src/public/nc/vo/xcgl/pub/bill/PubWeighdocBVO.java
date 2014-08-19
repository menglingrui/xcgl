package nc.vo.xcgl.pub.bill;

import nc.vo.pub.lang.UFDouble;

public abstract class PubWeighdocBVO extends XCHYChildSuperVO {
	private static final long serialVersionUID = -8353010709211155761L;

	/**
	 * ������������
	 */
	private String pk_vehicle;
	/**
	 * ���������������
	 */
	private String pk_invbasdoc;
	/**
	 * �������������
	 */
	private String pk_invmandoc;
	/**
	 * ë��
	 */
	private UFDouble ngrossweight;
	/**
	 * Ƥ��
	 */
	private UFDouble ntare;
	/**
	 * ����
	 */
	private UFDouble nnetweight;
	/**
	 * ��Ա��������
	 */
	private String pk_psndoc;
	/**
	 * ��Ա������������
	 */
	private String pk_psnbasdoc;
	
	public String getPk_vehicle() {
		return pk_vehicle;
	}
	public void setPk_vehicle(String pk_vehicle) {
		this.pk_vehicle = pk_vehicle;
	}
	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}
	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}
	public String getPk_invmandoc() {
		return pk_invmandoc;
	}
	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}
	public UFDouble getNgrossweight() {
		return ngrossweight;
	}
	public void setNgrossweight(UFDouble ngrossweight) {
		this.ngrossweight = ngrossweight;
	}
	public UFDouble getNtare() {
		return ntare;
	}
	public void setNtare(UFDouble ntare) {
		this.ntare = ntare;
	}
	public UFDouble getNnetweight() {
		return nnetweight;
	}
	public void setNnetweight(UFDouble nnetweight) {
		this.nnetweight = nnetweight;
	}
	public String getPk_psndoc() {
		return pk_psndoc;
	}
	public void setPk_psndoc(String pk_psndoc) {
		this.pk_psndoc = pk_psndoc;
	}
	public String getPk_psnbasdoc() {
		return pk_psnbasdoc;
	}
	public void setPk_psnbasdoc(String pk_psnbasdoc) {
		this.pk_psnbasdoc = pk_psnbasdoc;
	}

	
}
