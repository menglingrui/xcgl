package nc.vo.xcgl.process;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 *  �����������VO
 * @author ddk
 */
@SuppressWarnings("serial")
public class ProcessVO extends XCNewBaseVO{
	/**
	 * ���
	 */
	private String vprocesscode;
	/**
	 * ����
	 */
	private String vprocessname;
	/**
	 * ����
	 */
	private String pk_process;
	/**
	 * �к�
	 */
	private String crowno;
	/**
	 * ����
	 */
	private Integer ilevels;
    
	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	public String getVprocesscode() {
		return vprocesscode;
	}

	public void setVprocesscode(String vprocesscode) {
		this.vprocesscode = vprocesscode;
	}

	public String getVprocessname() {
		return vprocessname;
	}

	public void setVprocessname(String vprocessname) {
		this.vprocessname = vprocessname;
	}

	public String getPk_process() {
		return pk_process;
	}

	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
	}


	public Integer getIlevels() {
		return ilevels;
	}

	public void setIlevels(Integer ilevels) {
		this.ilevels = ilevels;
	}

	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-12-06 10:25:16
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_process";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-12-06 10:25:16
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_process";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2013-12-06 10:25:16
	  */
     public ProcessVO() {
		super();	
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}    
} 
