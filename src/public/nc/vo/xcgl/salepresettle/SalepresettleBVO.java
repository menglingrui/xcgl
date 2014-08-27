package nc.vo.xcgl.salepresettle;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * ����Ԥ�������vo
 * @author lxh
 */
@SuppressWarnings("serial")
public class SalepresettleBVO extends XCHYChildSuperVO{

	/**
	 * ��������
	 * */
	private String pk_salepresettle_b;
	/**
	 * ��������
	 * */
	private String pk_salepresettle_h;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * ��λ����
	 */
	private String pk_measdoc;
	/**
	 * ����
	 */
	private UFDouble namount;
	/**
	 * ������
	 */
	private UFDouble nassamount;
	/**
	 * ��˰����
	 */
	private UFDouble notaxprice;
	/**
	 * ˰��
	 */
	private UFDouble ntaxrate;
	/**
	 * ��˰����
	 */
	private UFDouble ntaxprice;
	/**
	 * ��˰���
	 */
	private UFDouble notaxsum;
	/**
	 * ��˰�ϼ�
	 */
	private UFDouble npricetaxsum;
	
	/**
	 * Ʒλ
	 */
	private UFDouble ngrade;
	/**
	 * ���ۼ�˰�ϼ�ʵ�ʽ��
	 */
	public UFDouble nreserve7;
	
	/**
	 * ������Ʒλ
	 * @return
	 * @author yangtao
	 * @date 2014-4-2 ����02:01:17
	 */
	
	private UFDouble ngraded;
	/**
	 * ����������
	 */
	private UFDouble namounted;	
	/**
	 * �Ƿ� cal price
	 */
	private UFBoolean uimpurity;	
	/**
	 * ����
	 */
	private String vreserve1;
	/**
	 * �Ƿ񾫷�
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
