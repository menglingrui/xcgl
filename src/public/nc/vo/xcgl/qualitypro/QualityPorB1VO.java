package nc.vo.xcgl.qualitypro;
	
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * <b> �����ż۷����۸����B1VO </b>
 * ��������:2014-02-18 17:33:24
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class QualityPorB1VO extends XCHYChildSuperVO {
	/**
	 * ��������
	 */
	private String pk_qualitypro_h;
	/**
	 * �ֱ�����
	 */
	private String pk_qualitypro_b1;
	/**
	 * �����ż�-�۸�����
	 */
	private String pk_qualityprice_h;
	/**
	 * �������ȼ�
	 */
	private Integer ipriority;
	/**
	 * �����żۼ۸����
	 */
	private String vqualitypricecode;
	/**
	 * �����żۼ۸�����
	 */
	private String vqualitypricename;
	
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

	public String getPk_qualityprice_h() {
		return pk_qualityprice_h;
	}

	public void setPk_qualityprice_h(String pk_qualityprice_h) {
		this.pk_qualityprice_h = pk_qualityprice_h;
	}

	public String getPk_qualitypro_h() {
		return pk_qualitypro_h;
	}

	public void setPk_qualitypro_h(String pk_qualitypro_h) {
		this.pk_qualitypro_h = pk_qualitypro_h;
	}

	public String getPk_qualitypro_b1() {
		return pk_qualitypro_b1;
	}

	public void setPk_qualitypro_b1(String pk_qualitypro_b1) {
		this.pk_qualitypro_b1 = pk_qualitypro_b1;
	}


	public Integer getIpriority() {
		return ipriority;
	}

	public void setIpriority(Integer ipriority) {
		this.ipriority = ipriority;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-02-18 17:33:24
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_qualitypro_h";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-02-18 17:33:24
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_qualitypro_b1";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-18 17:33:24
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_qualitypro_b1";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-18 17:33:24
	  */
     public QualityPorB1VO() {
		super();	
	}    
} 
