package nc.vo.xcgl.pub.bill;


public abstract class PubWeighdocHVO extends XCHYHeadSuperVO{
	private static final long serialVersionUID = -3155221726649613134L;
	/**
	 * ������
	 */
	private String vemployeeid;
	/**
	 * ѡ��
	 */
	private String pk_factory;
	/**
	 * ��Դ����
	 */
	private String pk_minarea;
	/**
	 * ���ֿ�
	 */
	private String pk_stordoc;
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
	public String getVemployeeid() {
		return vemployeeid;
	}
	public void setVemployeeid(String vemployeeid) {
		this.vemployeeid = vemployeeid;
	}
	public String getPk_factory() {
		return pk_factory;
	}
	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}
	public String getPk_minarea() {
		return pk_minarea;
	}
	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}
	public String getPk_stordoc() {
		return pk_stordoc;
	}
	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}


	
	
}
