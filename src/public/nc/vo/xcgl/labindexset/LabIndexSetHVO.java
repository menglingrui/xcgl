package nc.vo.xcgl.labindexset;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;
/**
 * 
 * 化验指标定义表头VO
 * 
 * @author lxh
 * 
 */
public class LabIndexSetHVO extends XCNewBaseVO{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 化验存货名称
	 */
	private String vlabinvname;      
	/**
	 * 化验存货编码
	 */
	private String vlabinvcode; 
	/**
	 * 化验存货管理主键
	 */
	private String pk_invmandoc;      
	/**
	 * 化验存货基本主键
	 */
	private String pk_invbasdoc;  
	/**
	 * 限定矿区(以前是矿区，现在改为部门)
	 */
	private String pk_minarea; 
	/**
	 * 化验指标定义主键
	 */
	private String pk_labindexset_h; 
	/**
	 * 
	 */
	private String pk_deptdoc; 
	/**
	 * 关联矿石
	 */
	public String vdef20;
	
	
	public String getVdef20() {
		return vdef20;
	}

	public void setVdef20(String vdef20) {
		this.vdef20 = vdef20;
	}

	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getVlabinvname() {
		return vlabinvname;
	}

	public void setVlabinvname(String vlabinvname) {
		this.vlabinvname = vlabinvname;
	}

	public String getVlabinvcode() {
		return vlabinvcode;
	}

	public void setVlabinvcode(String vlabinvcode) {
		this.vlabinvcode = vlabinvcode;
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

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	public String getPk_labindexset_h() {
		return pk_labindexset_h;
	}

	public void setPk_labindexset_h(String pk_labindexset_h) {
		this.pk_labindexset_h = pk_labindexset_h;
	}

	@Override
	public String getPKFieldName() {
		return "pk_labindexset_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_labindexset_h";
	}
	
}
