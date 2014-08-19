package nc.vo.xcgl.pricemanage;
	
import nc.vo.pub.lang.*;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * <b> 价格维护表体VO </b>
 * 创建日期:2014-02-13 17:59:58
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class PriceManageBVO extends XCHYChildSuperVO {
	/**
	 * 公司
	 */
	private String pk_corp;
	/**
	 * 日期
	 */
	private String ddate;
	/**
	 * 表体主键
	 */
	private String pk_pricemanage_b;
	/**
	 * 是否关闭
	 */
	private UFBoolean isclose;
	/**
	 * 月段
	 */
	private String vmanth;
	/**
	 * 表头主键
	 */
	private String pk_pricemanage_h;
	/**
	 * 价格
	 */
	private UFDouble nprice;
	/**
	 * 单位
	 */
	private String pk_measdoc;
	
	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	public String getPk_pricemanage_b() {
		return pk_pricemanage_b;
	}

	public void setPk_pricemanage_b(String pk_pricemanage_b) {
		this.pk_pricemanage_b = pk_pricemanage_b;
	}

	public UFBoolean getIsclose() {
		return isclose;
	}

	public void setIsclose(UFBoolean isclose) {
		this.isclose = isclose;
	}

	public String getVmanth() {
		return vmanth;
	}

	public void setVmanth(String vmanth) {
		this.vmanth = vmanth;
	}

	public String getPk_pricemanage_h() {
		return pk_pricemanage_h;
	}

	public void setPk_pricemanage_h(String pk_pricemanage_h) {
		this.pk_pricemanage_h = pk_pricemanage_h;
	}

	public UFDouble getNprice() {
		return nprice;
	}

	public void setNprice(UFDouble nprice) {
		this.nprice = nprice;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-02-13 17:59:58
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_pricemanage_h";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-02-13 17:59:58
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_pricemanage_b";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-02-13 17:59:58
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_pricemanage_b";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-02-13 17:59:58
	  */
     public PriceManageBVO() {
		super();	
	}    
} 
