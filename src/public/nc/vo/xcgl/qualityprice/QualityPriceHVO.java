package nc.vo.xcgl.qualityprice;
	
import nc.vo.xcgl.pub.bill.PubHighQualityHVO;
	
/**
 * <b> �����żۡ��۸�HVO </b>
 * ��������:2014-02-14 14:56:01
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class QualityPriceHVO extends PubHighQualityHVO {
	/**
	 * ��������
	 */
	private String pk_qualityprice_h;
	/**
	 * �����ż۱���
	 */
	private String vqualitypricecode;
	/**
	 * �����ż�����
	 */
	private String vqualitypricename;
	
	public String getPk_qualityprice_h() {
		return pk_qualityprice_h;
	}

	public void setPk_qualityprice_h(String pk_qualityprice_h) {
		this.pk_qualityprice_h = pk_qualityprice_h;
	}

	public String getVqualitypricecode() {
		return vqualitypricecode;
	}

	public void setVqualitypricecode(String vqualitypricecode) {
		this.vqualitypricecode = vqualitypricecode;
	}

	public String getVqualitypricename() {
		return vqualitypricename;
	}

	public void setVqualitypricename(String vqualitypricename) {
		this.vqualitypricename = vqualitypricename;
	}

	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-02-14 14:56:01
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_qualityprice_h";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-14 14:56:01
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_qualityprice_h";
	}    
    

	@Override
	public String getParentPKFieldName() {
		return null;
	}
	
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-14 14:56:01
	  */
     public QualityPriceHVO() {
		super();	
	}
} 
