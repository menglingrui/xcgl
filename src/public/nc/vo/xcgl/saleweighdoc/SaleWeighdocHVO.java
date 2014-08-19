package nc.vo.xcgl.saleweighdoc;
	
import nc.vo.xcgl.pub.bill.PubWeighdocHVO;
	
/**
 * ���۹����ǼǱ�ͷVO
 * ��������:2013-12-16 11:15:56
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
	 * ����
	 */
	private String pk_weighdoc;

	public String getPk_weighdoc() {
		return pk_weighdoc;
	}

	public void setPk_weighdoc(String pk_weighdoc) {
		this.pk_weighdoc = pk_weighdoc;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-12-09 13:43:00
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-12-09 13:43:00
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_weighdoc";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-12-09 13:43:00
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_weighdoc";
	}    
    
} 
