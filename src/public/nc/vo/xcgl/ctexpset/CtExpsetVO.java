package nc.vo.xcgl.ctexpset;
	
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * <b>��ͬ����VO </b>
 * ��������:2014-04-01 17:12:01
 * @author ddk
 * @version NCPrj 1.0
 */
public class CtExpsetVO extends XCNewBaseVO {
	private static final long serialVersionUID = -3369318295140740864L;
	/**
	 * ����
	 */
	private String pk_ctexpset;
	/**
	 * ���ñ���
	 */
	private String vexpitemcode;
	/**
	 * ��������
	 */
	private String vexpitemname;
	/**
	 * ���
	 */
	private UFDouble nsum;
	/**
	 * ����
	 */
	private String pk_deptdoc;
	
	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public UFDouble getNsum() {
		return nsum;
	}

	public void setNsum(UFDouble nsum) {
		this.nsum = nsum;
	}

	public String getPk_ctexpset() {
		return pk_ctexpset;
	}

	public void setPk_ctexpset(String pk_ctexpset) {
		this.pk_ctexpset = pk_ctexpset;
	}

	public String getVexpitemcode() {
		return vexpitemcode;
	}

	public void setVexpitemcode(String vexpitemcode) {
		this.vexpitemcode = vexpitemcode;
	}

	public String getVexpitemname() {
		return vexpitemname;
	}

	public void setVexpitemname(String vexpitemname) {
		this.vexpitemname = vexpitemname;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-04-01 17:12:01
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-04-01 17:12:01
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_ctexpset";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-04-01 17:12:01
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_ctexpset";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-04-01 17:12:01
	  */
     public CtExpsetVO() {
		super();	
	}    
} 

