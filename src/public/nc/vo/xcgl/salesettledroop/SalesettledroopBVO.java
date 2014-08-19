package nc.vo.xcgl.salesettledroop;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * ���۽���������VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class SalesettledroopBVO extends XCHYChildSuperVO{
	/**
	 * ��������
	 * */
	private String pk_salesettledroop_b;
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
	 * ��˰���
	 */
	private UFDouble notaxmargin;
	/**
	 * ��˰���
	 */
	private UFDouble ntaxmargin;
	
	
	/**
	 * �Ƿ� cal price
	 */
	private UFBoolean uimpurity;	
	/**
	 * ����
	 */
	private String vreserve1;
	
	
	private UFDouble ntaxrate;
	
	
	
	public UFDouble getNtaxrate() {
		return ntaxrate;
	}

	public void setNtaxrate(UFDouble ntaxrate) {
		this.ntaxrate = ntaxrate;
	}

	public UFBoolean getUimpurity() {
		return uimpurity;
	}

	public void setUimpurity(UFBoolean uimpurity) {
		this.uimpurity = uimpurity;
	}

	public String getVreserve1() {
		return vreserve1;
	}

	public void setVreserve1(String vreserve1) {
		this.vreserve1 = vreserve1;
	}

	public String getPk_salesettledroop_b() {
		return pk_salesettledroop_b;
	}

	public void setPk_salesettledroop_b(String pk_salesettledroop_b) {
		this.pk_salesettledroop_b = pk_salesettledroop_b;
	}

	public String getPk_salesettledroop_h() {
		return pk_salesettledroop_h;
	}

	public void setPk_salesettledroop_h(String pk_salesettledroop_h) {
		this.pk_salesettledroop_h = pk_salesettledroop_h;
	}

	public UFDouble getNotaxmargin() {
		return notaxmargin;
	}

	public void setNotaxmargin(UFDouble notaxmargin) {
		this.notaxmargin = notaxmargin;
	}

	public UFDouble getNtaxmargin() {
		return ntaxmargin;
	}

	public void setNtaxmargin(UFDouble ntaxmargin) {
		this.ntaxmargin = ntaxmargin;
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



	@Override
	public String getPKFieldName() {
		return "pk_salesettledroop_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_salesettledroop_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_salesettledroop_b";
	}

}
