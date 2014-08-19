package nc.vo.xcgl.teamgroup;

import nc.vo.xcgl.pub.bill.XCNewBaseVO;
/**
 * 
 * ���鵵����ͷVO
 * 
 * @author lxh
 * 
 */
public class TeamGruopHVO extends XCNewBaseVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ����
	 */
	private String teamcode;    
	/**
	 * ����
	 */
	private String teamname;     
	/**
	 * ��������
	 */
	private String pk_teamgroup_h;
	/**
	 * ����ѡ��
	 */
	private String pk_factory;
	
	
	
	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_teamgroup_h() {
		return pk_teamgroup_h;
	}

	public void setPk_teamgroup_h(String pk_teamgroup_h) {
		this.pk_teamgroup_h = pk_teamgroup_h;
	}

	public String getTeamcode() {
		return teamcode;
	}

	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	@Override
	public String getPKFieldName() {
		return "pk_teamgroup_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_teamgroup_h";
	}

}
