package nc.vo.xcgl.qualityprice;
	
import nc.vo.xcgl.pub.bill.PubHighQualityBVO;
	
/**
 * <b> �����żۡ��۸�BVO </b>
 * ��������:2014-02-14 14:56:01
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class QualityPriceBVO extends PubHighQualityBVO {
	/**
	 * ��ͷ����
	 */
	private String pk_qualityprice_h;
	/**
	 * ��������
	 */
	private String pk_qualityprice_b;
	
	public String getPk_qualityprice_h() {
		return pk_qualityprice_h;
	}

	public void setPk_qualityprice_h(String pk_qualityprice_h) {
		this.pk_qualityprice_h = pk_qualityprice_h;
	}

	public String getPk_qualityprice_b() {
		return pk_qualityprice_b;
	}

	public void setPk_qualityprice_b(String pk_qualityprice_b) {
		this.pk_qualityprice_b = pk_qualityprice_b;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-02-14 14:56:01
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_qualityprice_h";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-02-14 14:56:01
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_qualityprice_b";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-14 14:56:01
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_qualityprice_b";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-14 14:56:01
	  */
     public QualityPriceBVO() {
		super();	
	}    
} 
