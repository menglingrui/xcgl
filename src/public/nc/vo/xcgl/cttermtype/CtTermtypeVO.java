
package nc.vo.xcgl.cttermtype;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * ��ͬ��������vo 
 * ��������:2014-02-12 11:24:10
 * @author jay
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class CtTermtypeVO extends XCNewBaseVO {
	/**
	 * ��ͬ������������
	 */
	private String pk_cttermtype;
	/**
	 * ��ͬ��������
	 */
	private String termtypename;
	/**
	 * ��ͬ�������
	 */
	private String termtypecode;
	
	/**
	 * ������:��������ĸ��ڵ�ֵΪroot
	 */
	private String pk_parent;
 
	public String getPk_parent() {
		return pk_parent;
	}

	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}

	public String getPk_cttermtype() {
		return pk_cttermtype;
	}

	public void setPk_cttermtype(String pk_cttermtype) {
		this.pk_cttermtype = pk_cttermtype;
	}

	public String getTermtypename() {
		return termtypename;
	}

	public void setTermtypename(String termtypename) {
		this.termtypename = termtypename;
	}

	public String getTermtypecode() {
		return termtypecode;
	}

	public void setTermtypecode(String termtypecode) {
		this.termtypecode = termtypecode;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-02-12 11:24:10
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-02-12 11:24:10
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_cttermtype";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-12 11:24:10
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_cttermtype";
	}    
   
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-12 11:24:10
	  */
     public CtTermtypeVO() {
		super();	
	}    
} 
