package nc.vo.xcgl.weighdoc;
	
import nc.vo.xcgl.pub.bill.PubWeighdocHVO;
	
/**
 * �����ǼǱ�ͷVO
 * ��������:2013-12-09 13:43:00
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class WeighdocHVO extends PubWeighdocHVO {
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
		return "XCGL_WEIGHDOC";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2013-12-09 13:43:00
	  */
     public WeighdocHVO() {
		super();	
	}    
} 
