package nc.vo.xcgl.saleminenanteil;

import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * 销售按矿区比例分摊计算表体VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class SaleminenanteilBVO extends XCHYChildSuperVO{
	/**
	 * 表体主键
	 * */
	private String pk_saleminenanteil_b;
	/**
	 * 主表主键
	 * */
	private String pk_saleminenanteil_h;
	/**
	 * 存货管理主键
	 */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 矿区
	 */
	private String pk_minarea;
	/**
	 * 比例
	 */
	private UFDouble nscale;
	/**
	 * 分摊重量
	 */
	private UFDouble nweightsharing;
	/**
	 * Pb
	 */
	private UFDouble nlead;
	/**
	 * Ag
	 */
	private UFDouble nsilver;
	/**
	 * Zn
	 */
	private UFDouble nzinc;
	
	

	public String getPk_saleminenanteil_b() {
		return pk_saleminenanteil_b;
	}

	public void setPk_saleminenanteil_b(String pk_saleminenanteil_b) {
		this.pk_saleminenanteil_b = pk_saleminenanteil_b;
	}

	public String getPk_saleminenanteil_h() {
		return pk_saleminenanteil_h;
	}

	public void setPk_saleminenanteil_h(String pk_saleminenanteil_h) {
		this.pk_saleminenanteil_h = pk_saleminenanteil_h;
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

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	public UFDouble getNscale() {
		return nscale;
	}

	public void setNscale(UFDouble nscale) {
		this.nscale = nscale;
	}

	public UFDouble getNweightsharing() {
		return nweightsharing;
	}

	public void setNweightsharing(UFDouble nweightsharing) {
		this.nweightsharing = nweightsharing;
	}

	public UFDouble getNlead() {
		return nlead;
	}

	public void setNlead(UFDouble nlead) {
		this.nlead = nlead;
	}

	public UFDouble getNsilver() {
		return nsilver;
	}

	public void setNsilver(UFDouble nsilver) {
		this.nsilver = nsilver;
	}

	public UFDouble getNzinc() {
		return nzinc;
	}

	public void setNzinc(UFDouble nzinc) {
		this.nzinc = nzinc;
	}

	@Override
	public String getPKFieldName() {
		return "pk_saleminenanteil_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_saleminenanteil_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_saleminenanteil_b";
	}

}
