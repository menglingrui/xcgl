package nc.vo.xcgl.pub.bill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
/**
 * 存放计算值的VO
 * @author mlr
 */
public class CalYieldNumVO extends SuperVO{
	private static final long serialVersionUID = -7681868515354780693L;
	/**
	 * repeat select  powder
	 */
	private UFBoolean isresel;
	
	/**
	 * 金属元素主键
	 */
	private String pk_invmandoc;
	/**
	 * 金属元素基本主键
	 */
	private String pk_invbasdoc;	
	/**
	 *指标类型
	 *0主指标 1副指标 2其他指标 
	 */
	private Integer itype;
	/**
	 * 品位
	 */
	private UFDouble ngrade;
	/**
	 * 原矿量
	 * 
	 */
	private UFDouble  nrawore;
	/**
	 * 精粉产量
	 */
	private UFDouble  nflouryield;
	/**
	 * 回收率
	 * 
	 */
	private UFDouble  nrecoveryrate;
	/**
	 * 金属量
	 */
	private UFDouble  nmetal;
	
	/**
	 * 精粉产量(尾矿)
	 */
	private UFDouble  nflouryield1;
	/**
	 * 回收率(尾矿)
	 * 
	 */
	private UFDouble  nrecoveryrate1;
	/**
	 * 金属量(尾矿)
	 */
	private UFDouble  nmetal1;
	
	
	public UFBoolean getIsresel() {
		return isresel;
	}
	public void setIsresel(UFBoolean isresel) {
		this.isresel = isresel;
	}
	public UFDouble getNflouryield1() {
		return nflouryield1;
	}
	public void setNflouryield1(UFDouble nflouryield1) {
		this.nflouryield1 = nflouryield1;
	}
	public UFDouble getNrecoveryrate1() {
		return nrecoveryrate1;
	}
	public void setNrecoveryrate1(UFDouble nrecoveryrate1) {
		this.nrecoveryrate1 = nrecoveryrate1;
	}
	public UFDouble getNmetal1() {
		return nmetal1;
	}
	public void setNmetal1(UFDouble nmetal1) {
		this.nmetal1 = nmetal1;
	}
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
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
	public UFDouble getNrawore() {
		return nrawore;
	}
	public void setNrawore(UFDouble nrawore) {
		this.nrawore = nrawore;
	}
	public UFDouble getNflouryield() {
		return nflouryield;
	}
	public void setNflouryield(UFDouble nflouryield) {
		this.nflouryield = nflouryield;
	}
	public UFDouble getNrecoveryrate() {
		return nrecoveryrate;
	}
	public void setNrecoveryrate(UFDouble nrecoveryrate) {
		this.nrecoveryrate = nrecoveryrate;
	}
	public UFDouble getNmetal() {
		return nmetal;
	}
	public void setNmetal(UFDouble nmetal) {
		this.nmetal = nmetal;
	}
	@Override
	public String getPKFieldName() {
		return null;
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	@Override
	public String getTableName() {
		return null;
	}
}
