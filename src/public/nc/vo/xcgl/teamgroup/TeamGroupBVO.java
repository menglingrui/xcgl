package nc.vo.xcgl.teamgroup;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;

/**
 * 
 * ���鵵������VO
 * 
 * @author lxh
 * 
 */

public class TeamGroupBVO extends XCNewBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��Ա����
	 */
	private String vpsncode;   
	/**
	 * ��Ա����
	 */
	private String vpsname;  
	
	/**
	 * ��Ա��������
	 */
	private String pk_psnbasdoc;   
	/**
	 * ��Ա��������
	 */
	private String pk_psndoc;      
	
	private String pk_teamgroup_h;
	private String pk_teamgroup_b;
	
	
	
	
	
	

	public String getVpsncode() {
		return vpsncode;
	}

	public void setVpsncode(String vpsncode) {
		this.vpsncode = vpsncode;
	}

	public String getVpsname() {
		return vpsname;
	}

	public void setVpsname(String vpsname) {
		this.vpsname = vpsname;
	}

	public String getPk_teamgroup_h() {
		return pk_teamgroup_h;
	}

	public void setPk_teamgroup_h(String pk_teamgroup_h) {
		this.pk_teamgroup_h = pk_teamgroup_h;
	}

	public String getPk_teamgroup_b() {
		return pk_teamgroup_b;
	}

	public void setPk_teamgroup_b(String pk_teamgroup_b) {
		this.pk_teamgroup_b = pk_teamgroup_b;
	}

	public String getPk_psnbasdoc() {
		return pk_psnbasdoc;
	}

	public void setPk_psnbasdoc(String pk_psnbasdoc) {
		this.pk_psnbasdoc = pk_psnbasdoc;
	}

	public String getPk_psndoc() {
		return pk_psndoc;
	}

	public void setPk_psndoc(String pk_psndoc) {
		this.pk_psndoc = pk_psndoc;
	}

	@Override
	public String getPKFieldName() {
		return "pk_teamgroup_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_teamgroup_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_teamgroup_b";
	}

}
