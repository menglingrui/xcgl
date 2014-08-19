package nc.vo.xcgl.beltline;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 *  ������VO 
 * @author ddk
 */
@SuppressWarnings("serial")
public class BeltlineVO extends XCNewBaseVO {
	/**
	 * ����
	 */
	private String pk_process;
	/**
	 * ��������
	 */
	private String pk_factory;
	/**
	 * ����
	 */
	private String pk_beltline;
	/**
	 * ��ע
	 */
	private String vmemo;
	/**
	 * ����
	 */
	private String vbeltlinename;
	/**
	 * ����
	 */
	private String vbeltlinecode;
	/**
	 * �к�
	 */
	private String crowno;
	/**
	 * ���յ���
	 */
	private String pk_technology;

	public String getPk_technology() {
		return pk_technology;
	}

	public void setPk_technology(String pk_technology) {
		this.pk_technology = pk_technology;
	}

	public String getPk_process() {
		return pk_process;
	}

	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_beltline() {
		return pk_beltline;
	}

	public void setPk_beltline(String pk_beltline) {
		this.pk_beltline = pk_beltline;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	public String getVbeltlinename() {
		return vbeltlinename;
	}

	public void setVbeltlinename(String vbeltlinename) {
		this.vbeltlinename = vbeltlinename;
	}


	public String getVbeltlinecode() {
		return vbeltlinecode;
	}

	public void setVbeltlinecode(String vbeltlinecode) {
		this.vbeltlinecode = vbeltlinecode;
	}

	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-12-06 10:22:25
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-12-06 10:22:25
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_beltline";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-12-06 10:22:25
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_beltline";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2013-12-06 10:22:25
	  */
     public BeltlineVO() {
		super();	
	}    
} 
