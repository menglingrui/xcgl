package nc.vo.xcgl.pub.bill;

import nc.vo.pub.lang.UFDouble;

/**
 * ����ָ����Ϣ
 * @author mlr
 *
 */
public abstract class PubSampleBBVO extends XCHYChildSuperVO{

	private static final long serialVersionUID = 95088122171904519L;
	/**
	 * ����Ԫ������
	 */
	private String pk_invmandoc;
	/**
	 * ����Ԫ�ػ�������
	 */
	private String pk_invbasdoc;
	/**
	 * Ʒλ
	 */
	private UFDouble ngrade;	
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
	public UFDouble getNgrade() {
		return ngrade;
	}
	public void setNgrade(UFDouble ngrade) {
		this.ngrade = ngrade;
	}
}
