package nc.vo.xcgl.soct;
	
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * 
 * ��������:2014-03-05 17:43:50
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SoctB7VO extends XCHYChildSuperVO {
	/**
	 * ��������
	 */
	private String pk_soct;
	/**
	 * �ӱ�����
	 */
	private String pk_soct_b7;
	
	
	private String pk_invdex;
	
	private String pk_mandex;
	
	private String pk_measdoc;
	
	private UFBoolean isprice;
	
	private String pk_sourceprice;
	/**
	 * Ѯ����
       �����պ�n�վ���
       ���ռ�
       ������ǰn�վ���
       �¾���
       ���Ⱦ���
	 */
	private Integer pricerule;
	
	private Integer days;
	/**
	 * ������
       ������
	 */
	private Integer dayrule;
	
	private Integer para;
 
	/**
	 * 
	 */
	private UFDouble ndownnum;
	
	
	public UFDouble getNdownnum() {
		return ndownnum;
	}

	public void setNdownnum(UFDouble ndownnum) {
		this.ndownnum = ndownnum;
	}

	public String getPk_soct_b7() {
		return pk_soct_b7;
	}

	public void setPk_soct_b7(String pk_soct_b7) {
		this.pk_soct_b7 = pk_soct_b7;
	}

	public String getPk_invdex() {
		return pk_invdex;
	}

	public void setPk_invdex(String pk_invdex) {
		this.pk_invdex = pk_invdex;
	}

	public String getPk_mandex() {
		return pk_mandex;
	}

	public void setPk_mandex(String pk_mandex) {
		this.pk_mandex = pk_mandex;
	}

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}



	public UFBoolean getIsprice() {
		return isprice;
	}

	public void setIsprice(UFBoolean isprice) {
		this.isprice = isprice;
	}

	public String getPk_sourceprice() {
		return pk_sourceprice;
	}

	public void setPk_sourceprice(String pk_sourceprice) {
		this.pk_sourceprice = pk_sourceprice;
	}

	public Integer getPricerule() {
		return pricerule;
	}

	public void setPricerule(Integer pricerule) {
		this.pricerule = pricerule;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getDayrule() {
		return dayrule;
	}

	public void setDayrule(Integer dayrule) {
		this.dayrule = dayrule;
	}

	public Integer getPara() {
		return para;
	}

	public void setPara(Integer para) {
		this.para = para;
	}

	public String getPk_soct() {
		return pk_soct;
	}

	public void setPk_soct(String pk_soct) {
		this.pk_soct = pk_soct;
	}
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-03-05 17:43:50
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_soct";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-03-05 17:43:50
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_soct_b7";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-03-05 17:43:50
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_SOCT_B7";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-03-05 17:43:50
	  */
     public SoctB7VO() {
		super();	
	}    
} 
