package nc.vo.xcgl.classorder;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 *  ��λ�������VO 
 * @author ddk
 */
@SuppressWarnings("serial")
public class ClassorderVO extends XCNewBaseVO {
	/**
	 * ����
	 */
	private String vclassordername;
	/**
	 * ����
	 */
	private String vclassordercode;
	/**
	 * ����
	 */
	private String pk_classorder;
	/**
	 * ��ע
	 */
	private String vmemo;	
	/**
	 * �к�
	 */
	private String crowno;
	
	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}


	public String getVclassordername() {
		return vclassordername;
	}

	public void setVclassordername(String vclassordername) {
		this.vclassordername = vclassordername;
	}

	public String getVclassordercode() {
		return vclassordercode;
	}

	public void setVclassordercode(String vclassordercode) {
		this.vclassordercode = vclassordercode;
	}

	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-12-06 10:24:25
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-12-06 10:24:25
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_classorder";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-12-06 10:24:25
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_classorder";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2013-12-06 10:24:25
	  */
     public ClassorderVO() {
		super();	
	}    
} 
