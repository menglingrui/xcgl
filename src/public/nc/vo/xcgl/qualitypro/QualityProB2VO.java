package nc.vo.xcgl.qualitypro;
	
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * <b> �����ż۷���ƷλB2VO </b>
 * ��������:2014-02-18 17:33:24
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class QualityProB2VO extends XCHYChildSuperVO {
	/**
	 * ��������
	 */
	private String pk_qualitypro_h;
	/**
	 * �ֱ�����
	 */
	private String pk_qualitypro_b2;
	/**
	 * �����ż�-Ʒλ����
	 */
	private String pk_qualitygrade_h;
	/**
	 * �������ȼ�
	 */
	private Integer ipriority;
//	/**
//	 * �����ż�Ʒζ����
//	 */      XCGL_QUALITYPRICE_H,VQUALITYPRICENAME ,PK_QUALITYPRICE_H 
//	private String vqualitygradecode;
//	/**
//	 * �����ż�Ʒζ����
//	 */
//	private String vqualitygradename;

//	public String getVqualitygradecode() {
//		return vqualitygradecode;
//	}
//
//	public void setVqualitygradecode(String vqualitygradecode) {
//		this.vqualitygradecode = vqualitygradecode;
//	}
//
//	public String getVqualitygradename() {
//		return vqualitygradename;
//	}
//
//	public void setVqualitygradename(String vqualitygradename) {
//		this.vqualitygradename = vqualitygradename;
//	}

	public String getPk_qualitygrade_h() {
		return pk_qualitygrade_h;
	}

	public void setPk_qualitygrade_h(String pk_qualitygrade_h) {
		this.pk_qualitygrade_h = pk_qualitygrade_h;
	}

	public Integer getIpriority() {
		return ipriority;
	}

	public void setIpriority(Integer ipriority) {
		this.ipriority = ipriority;
	}

	public String getPk_qualitypro_h() {
		return pk_qualitypro_h;
	}

	public void setPk_qualitypro_h(String pk_qualitypro_h) {
		this.pk_qualitypro_h = pk_qualitypro_h;
	}

	public String getPk_qualitypro_b2() {
		return pk_qualitypro_b2;
	}

	public void setPk_qualitypro_b2(String pk_qualitypro_b2) {
		this.pk_qualitypro_b2 = pk_qualitypro_b2;
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
	  return "pk_qualitypro_b2";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-18 17:33:24
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_qualitypro_b2";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-18 17:33:24
	  */
     public QualityProB2VO() {
		super();	
	}    
} 
