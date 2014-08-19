package nc.vo.xcgl.labindexset;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
/**
 * 
 * ����ָ�궨�����VO
 * 
 * @author lxh
 * 
 */
public class LabIndexSetBVO extends XCNewBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �����������
	 */
	private String vchemetalname;      
	/**
	 * �����������
	 */
	private String vchemetalcode;    
	/**
	 * ���������������
	 */
	private String pk_invmandoc;
	/**
	 * �Ƿ��ִ���
	 */
	private UFBoolean isstandingcrop;
	/**
	 * ���������������
	 */
	private String pk_invbasdoc;
	/**
	 * ����  
	 * 0 ��ָ��  1 ��ָ�� 2 ����ָ�� 
	 */
	private Integer itype; 
	
	private String pk_labindexset_b;       
	private String pk_labindexset_h; 
	

	
	
	public UFBoolean getIsstandingcrop() {
		return isstandingcrop;
	}

	public void setIsstandingcrop(UFBoolean isstandingcrop) {
		this.isstandingcrop = isstandingcrop;
	}

	public Integer getItype() {
		return itype;
	}

	public void setItype(Integer itype) {
		this.itype = itype;
	}

	public String getVchemetalname() {
		return vchemetalname;
	}

	public void setVchemetalname(String vchemetalname) {
		this.vchemetalname = vchemetalname;
	}

	public String getVchemetalcode() {
		return vchemetalcode;
	}

	public void setVchemetalcode(String vchemetalcode) {
		this.vchemetalcode = vchemetalcode;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}

	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}

	public String getPk_labindexset_b() {
		return pk_labindexset_b;
	}

	public void setPk_labindexset_b(String pk_labindexset_b) {
		this.pk_labindexset_b = pk_labindexset_b;
	}

	public String getPk_labindexset_h() {
		return pk_labindexset_h;
	}

	public void setPk_labindexset_h(String pk_labindexset_h) {
		this.pk_labindexset_h = pk_labindexset_h;
	}

	@Override
	public String getPKFieldName() {
		return "pk_labindexset_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_labindexset_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_labindexset_b";
	}

}
