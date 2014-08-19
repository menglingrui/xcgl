package nc.vo.xcgl.soct;

import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 *  �����ʷ
 * ��������:2014-03-05 17:43:50
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SoctB5VO extends XCHYChildSuperVO {
	/**
	 * ��������
	 */
	private String pk_soct;
	/**
	 * �ӱ�����
	 */
	private String pk_soct_b5;
	/**
	 * �汾��
	 */
	private String vchangecode;
	/**
	 * �����
	 */
	private String vchgpsn;
	/**
	 * ���ԭ��
	 */
	private String vchgreason;
	/**
	 * �������
	 */
	private UFDate dchgdate;
 
	public String getPk_soct() {
		return pk_soct;
	}

	public void setPk_soct(String pk_soct) {
		this.pk_soct = pk_soct;
	}

	public String getPk_soct_b5() {
		return pk_soct_b5;
	}

	public void setPk_soct_b5(String pk_soct_b5) {
		this.pk_soct_b5 = pk_soct_b5;
	}

	public String getVchangecode() {
		return vchangecode;
	}

	public void setVchangecode(String vchangecode) {
		this.vchangecode = vchangecode;
	}

	public String getVchgpsn() {
		return vchgpsn;
	}

	public void setVchgpsn(String vchgpsn) {
		this.vchgpsn = vchgpsn;
	}

	public String getVchgreason() {
		return vchgreason;
	}

	public void setVchgreason(String vchgreason) {
		this.vchgreason = vchgreason;
	}

	public UFDate getDchgdate() {
		return dchgdate;
	}

	public void setDchgdate(UFDate dchgdate) {
		this.dchgdate = dchgdate;
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
	  return "pk_soct_b5";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-03-05 17:43:50
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_SOCT_B5";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-03-05 17:43:50
	  */
     public SoctB5VO() {
		super();	
	}    
} 
