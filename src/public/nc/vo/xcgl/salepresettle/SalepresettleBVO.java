package nc.vo.xcgl.salepresettle;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * 销售预结算表体vo
 * @author lxh
 */
@SuppressWarnings("serial")
public class SalepresettleBVO extends XCHYChildSuperVO{

	/**
	 * 表体主键
	 * */
	private String pk_salepresettle_b;
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
	 * 单位主键
	 */
	private String pk_measdoc;
	/**
	 * 数量
	 */
	private UFDouble namount;
	/**
	 * 辅数量
	 */
	private UFDouble nassamount;
	/**
	 * 无税单价
	 */
	private UFDouble notaxprice;
	/**
	 * 税率
	 */
	private UFDouble ntaxrate;
	/**
	 * 含税单价
	 */
	private UFDouble ntaxprice;
	/**
	 * 无税金额
	 */
	private UFDouble notaxsum;
	/**
	 * 价税合计
	 */
	private UFDouble npricetaxsum;
	
	/**
	 * 品位
	 */
	private UFDouble ngrade;
	/**
	 * 精粉价税合计实际金额
	 */
	public UFDouble nreserve7;
	
	/**
	 * 调整后品位
	 * @return
	 * @author yangtao
	 * @date 2014-4-2 下午02:01:17
	 */
	
	private UFDouble ngraded;
	/**
	 * 调整后数量
	 */
	private UFDouble namounted;	
	/**
	 * 是否 cal price
	 */
	private UFBoolean uimpurity;	
	/**
	 * 车号
	 */
	private String vreserve1;
	/**
	 * 是否精粉
	 */
	private UFBoolean ureserve2;
 
	



	public UFDouble getNreserve7() {
		return nreserve7;
	}

	public void setNreserve7(UFDouble nreserve7) {
		this.nreserve7 = nreserve7;
	}

	public UFBoolean getUreserve2() {
		return ureserve2;
	}

	public void setUreserve2(UFBoolean ureserve2) {
		this.ureserve2 = ureserve2;
	}

	public String getVreserve1() {
		return vreserve1;
	}

	public void setVreserve1(String vreserve1) {
		this.vreserve1 = vreserve1;
	}

	public UFBoolean getUimpurity() {
		return uimpurity;
	}

	public void setUimpurity(UFBoolean uimpurity) {
		this.uimpurity = uimpurity;
	}

	public String getPk_salepresettle_b() {
		return pk_salepresettle_b;
	}

	public void setPk_salepresettle_b(String pk_salepresettle_b) {
		this.pk_salepresettle_b = pk_salepresettle_b;
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

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}

	public UFDouble getNamount() {
		return namount;
	}

	public void setNamount(UFDouble namount) {
		this.namount = namount;
	}

	public UFDouble getNassamount() {
		return nassamount;
	}

	public void setNassamount(UFDouble nassamount) {
		this.nassamount = nassamount;
	}

	public UFDouble getNotaxprice() {
		return notaxprice;
	}

	public void setNotaxprice(UFDouble notaxprice) {
		this.notaxprice = notaxprice;
	}

	public UFDouble getNtaxrate() {
		return ntaxrate;
	}

	public void setNtaxrate(UFDouble ntaxrate) {
		this.ntaxrate = ntaxrate;
	}

	public UFDouble getNtaxprice() {
		return ntaxprice;
	}

	public void setNtaxprice(UFDouble ntaxprice) {
		this.ntaxprice = ntaxprice;
	}

	public UFDouble getNotaxsum() {
		return notaxsum;
	}

	public void setNotaxsum(UFDouble notaxsum) {
		this.notaxsum = notaxsum;
	}

	public UFDouble getNpricetaxsum() {
		return npricetaxsum;
	}

	public void setNpricetaxsum(UFDouble npricetaxsum) {
		this.npricetaxsum = npricetaxsum;
	}

	
	public UFDouble getNgrade() {
		return ngrade;
	}

	public void setNgrade(UFDouble ngrade) {
		this.ngrade = ngrade;
	}

	
	public UFDouble getNgraded() {
		return ngraded;
	}
	
	public void setNgraded(UFDouble ngraded) {
		this.ngraded = ngraded;
	}

	
	public UFDouble getNamounted() {
		return namounted;
	}

	public void setNamounted(UFDouble namounted) {
		this.namounted = namounted;
	}

	@Override
	public String getPKFieldName() {
		return "pk_salepresettle_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_salepresettle_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_salepresettle_b";
	}

}
