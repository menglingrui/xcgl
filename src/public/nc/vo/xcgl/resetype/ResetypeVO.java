package nc.vo.xcgl.resetype;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * <b> �շ����VO </b>
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ResetypeVO extends XCNewBaseVO {
	/**
	 * ����
	 */
	private String pk_resetype;
	/**
	 * ���ͱ���
	 */
	private String vtypecode;
	/**
	 * ��������
	 */
	private String vtypename;
	/**
	 * ���������
	 */
	private String vinouttype;
	
	public String getPk_resetype() {
		return pk_resetype;
	}

	public void setPk_resetype(String pk_resetype) {
		this.pk_resetype = pk_resetype;
	}

	public String getVtypecode() {
		return vtypecode;
	}

	public void setVtypecode(String vtypecode) {
		this.vtypecode = vtypecode;
	}

	public String getVtypename() {
		return vtypename;
	}

	public void setVtypename(String vtypename) {
		this.vtypename = vtypename;
	}

	public String getVinouttype() {
		return vinouttype;
	}

	public void setVinouttype(String vinouttype) {
		this.vinouttype = vinouttype;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-02-13 11:15:09
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-02-13 11:15:09
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_resetype";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-02-13 11:15:09
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_resetype";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2014-02-13 11:15:09
	  */
     public ResetypeVO() {
		super();	
	}    
} 
