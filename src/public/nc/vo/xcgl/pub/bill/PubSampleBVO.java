package nc.vo.xcgl.pub.bill;

import nc.vo.pub.lang.UFDouble;

/**
 * 化验样品表体VO
 * 
 * @author mlr
 * 
 */
public abstract class PubSampleBVO extends XCHYChildSuperVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8169398622501567439L;
	/**
	 * 存货管理主键
	 */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 样品编码
	 */
	private String vsamplenumber;
	/**
	 * 品位
	 */
	private UFDouble ngrade;
	/**
	 * 
	 */
	private String pk_measdoc;

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}

	public UFDouble getNgrade() {
		return ngrade;
	}

	public void setNgrade(UFDouble ngrade) {
		this.ngrade = ngrade;
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

	public String getVsamplenumber() {
		return vsamplenumber;
	}

	public void setVsamplenumber(String vsamplenumber) {
		this.vsamplenumber = vsamplenumber;
	}

}
