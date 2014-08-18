package nc.vo.xcgl.pub.bill;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

@SuppressWarnings("serial")
public abstract class PubHighQualityHVO extends XCHYHeadSuperVO {
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;

	/**
	 * �Ƿ�ر�
	 */
	private UFBoolean isclose;
	/**
	 * ��������
	 * 0--���۸�
	 * 1--��Ʒζ
	 */
	private Integer vtype;
	/**
	 * ��Ч����
	 */
	private UFDate dstartdate;
	/**
	 * ʧЧ����
	 */
	private UFDate denddate;

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

	public UFBoolean getIsclose() {
		return isclose;
	}

	public void setIsclose(UFBoolean isclose) {
		this.isclose = isclose;
	}



	public Integer getVtype() {
		return vtype;
	}

	public void setVtype(Integer vtype) {
		this.vtype = vtype;
	}

	public UFDate getDstartdate() {
		return dstartdate;
	}

	public void setDstartdate(UFDate dstartdate) {
		this.dstartdate = dstartdate;
	}

	public UFDate getDenddate() {
		return denddate;
	}

	public void setDenddate(UFDate denddate) {
		this.denddate = denddate;
	}
}
