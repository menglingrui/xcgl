package nc.vo.xcgl.soct;
	
import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * ִ�й���
 * ��������:2014-03-05 17:43:50
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SoctB6VO extends XCHYChildSuperVO {
	/**
	 * ��������
	 */
	private String pk_soct;
	/**
	 * �ӱ�����
	 */
	private String pk_soct_b6;
	/**
	 * ִ������
	 */
	private String vexecflow;
	/**
	 * ִ������
	 */
	private UFDate dexecdate;
	/**
	 * ִ��ԭ��
	 */
	private String vexecreason;
	
 
	public String getPk_soct() {
		return pk_soct;
	}

	public void setPk_soct(String pk_soct) {
		this.pk_soct = pk_soct;
	}

	public String getPk_soct_b6() {
		return pk_soct_b6;
	}

	public void setPk_soct_b6(String pk_soct_b6) {
		this.pk_soct_b6 = pk_soct_b6;
	}

	public String getVexecflow() {
		return vexecflow;
	}

	public void setVexecflow(String vexecflow) {
		this.vexecflow = vexecflow;
	}

	public UFDate getDexecdate() {
		return dexecdate;
	}

	public void setDexecdate(UFDate dexecdate) {
		this.dexecdate = dexecdate;
	}

	public String getVexecreason() {
		return vexecreason;
	}

	public void setVexecreason(String vexecreason) {
		this.vexecreason = vexecreason;
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
	  return "pk_soct_b6";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-03-05 17:43:50
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_SOCT_B6";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-03-05 17:43:50
	  */
     public SoctB6VO() {
		super();	
	}    
} 
