package nc.vo.xcgl.pricemanage;
	
import nc.vo.pub.lang.*;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
	
/**
 * <b> �۸�ά����ͷVO </b>
 * ��������:2014-02-13 17:59:57
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class PriceManageHVO extends XCHYHeadSuperVO{
	
	/**
	 * ����
	 */
	private UFDate ddate;
	/**
	 * �Ƿ�ر�
	 */
	private UFBoolean isclose;
	/**
	 * ��������
	 */
	private String pk_pricemanage_h;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * ������Դ
	 */
	private String vsourcemanage;
	/**
	 * ���
	 */
	private String vyear;
	/**
	 * �·�
	 */
	private String vmonth;
	
	public String getVyear() {
		return vyear;
	}

	public void setVyear(String vyear) {
		this.vyear = vyear;
	}

	public String getVmonth() {
		return vmonth;
	}

	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}

	public String getVsourcemanage() {
		return vsourcemanage;
	}

	public void setVsourcemanage(String vsourcemanage) {
		this.vsourcemanage = vsourcemanage;
	}

	public UFDate getDdate() {
		return ddate;
	}

	public void setDdate(UFDate ddate) {
		this.ddate = ddate;
	}

	
	public UFBoolean getIsclose() {
		return isclose;
	}

	public void setIsclose(UFBoolean isclose) {
		this.isclose = isclose;
	}

	public String getPk_pricemanage_h() {
		return pk_pricemanage_h;
	}

	public void setPk_pricemanage_h(String pk_pricemanage_h) {
		this.pk_pricemanage_h = pk_pricemanage_h;
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

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-02-13 17:59:57
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-02-13 17:59:57
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_pricemanage_h";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-13 17:59:57
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_pricemanage_h";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-13 17:59:57
	  */
     public PriceManageHVO() {
		super();	
	}

	
} 
