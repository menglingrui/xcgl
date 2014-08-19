package nc.vo.xcgl.soct;
	
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * 
 * 创建日期:2014-03-05 17:43:50
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SoctB7VO extends XCHYChildSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_soct;
	/**
	 * 子表主键
	 */
	private String pk_soct_b7;
	
	
	private String pk_invdex;
	
	private String pk_mandex;
	
	private String pk_measdoc;
	
	private UFBoolean isprice;
	
	private String pk_sourceprice;
	/**
	 * 旬均价
       发货日后n日均价
       当日价
       发货日前n日均价
       月均价
       季度均价
	 */
	private Integer pricerule;
	
	private Integer days;
	/**
	 * 发货日
       结算日
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
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-03-05 17:43:50
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_soct";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-03-05 17:43:50
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_soct_b7";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-03-05 17:43:50
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_SOCT_B7";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-03-05 17:43:50
	  */
     public SoctB7VO() {
		super();	
	}    
} 
