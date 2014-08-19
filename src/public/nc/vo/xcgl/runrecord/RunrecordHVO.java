package nc.vo.xcgl.runrecord;

import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * 设备运行记录表头VO
 * @author lxh
 */
public class RunrecordHVO extends XCHYHeadSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 工序
	 */
	private String pk_process;
	/**
	 * 仓库
	 */
	private String pk_stordoc;
	/**
	 * 选厂
	 */
	private String pk_factory;
	/**
	 * 班次
	 */
	private String pk_classorder;
	/**
	 * 生产线
	 */
	private String pk_beltline;
	
	private String pk_runrecord_h;
	
	
	
	public String getPk_beltline() {
		return pk_beltline;
	}

	public void setPk_beltline(String pk_beltline) {
		this.pk_beltline = pk_beltline;
	}

	public String getPk_process() {
		return pk_process;
	}

	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPk_stordoc() {
		return pk_stordoc;
	}

	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}

	public String getPk_runrecord_h() {
		return pk_runrecord_h;
	}

	public void setPk_runrecord_h(String pk_runrecord_h) {
		this.pk_runrecord_h = pk_runrecord_h;
	}

	@Override
	public String getPKFieldName() {
		return "pk_runrecord_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_runrecord_h";
	}

}
