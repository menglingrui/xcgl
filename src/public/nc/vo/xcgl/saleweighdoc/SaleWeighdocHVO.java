package nc.vo.xcgl.saleweighdoc;
	
import nc.vo.xcgl.pub.bill.PubWeighdocHVO;
	
/**
 * 销售过磅登记表头VO
 * 创建日期:2013-12-16 11:15:56
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SaleWeighdocHVO extends PubWeighdocHVO{
	
	
	private String custbasid;
	private String custid;
	
	
	
	
	public String getCustbasid() {
		return custbasid;
	}

	public void setCustbasid(String custbasid) {
		this.custbasid = custbasid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	/**
	 * 主键
	 */
	private String pk_weighdoc;

	public String getPk_weighdoc() {
		return pk_weighdoc;
	}

	public void setPk_weighdoc(String pk_weighdoc) {
		this.pk_weighdoc = pk_weighdoc;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-09 13:43:00
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-09 13:43:00
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_weighdoc";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-09 13:43:00
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_weighdoc";
	}    
    
} 
