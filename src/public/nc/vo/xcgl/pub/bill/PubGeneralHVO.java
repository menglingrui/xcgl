package nc.vo.xcgl.pub.bill;
import nc.vo.pub.lang.UFDouble;
/**
 * 出入库单表头VO
 * @author mlr
 */
public abstract class PubGeneralHVO extends XCHYHeadSuperVO{
	
	private static final long serialVersionUID = 2635386573909335659L;
	/**
	 * 矿区主键
	 */
	private String pk_minarea;
	/**
	 * 选厂主键
	 */
	private String pk_factory;
	/**
	 * 生产线主键
	 */
	private String pk_beltline;
	/**
	 * 工序主键
	 */
	private String pk_process;
	/**
	 * 班次主键
	 */
	private String pk_classorder;
	/**
	 * 仓库主键
	 */
	private String pk_stordoc;
	/**
	 * 车辆主键
	 */
	private String pk_car;
	/**
	 * 运行时间
	 */
	private UFDouble starthours;
	/**
	 * 部门主键
	 */
	private String pk_deptdoc;
	/**
	 * 收发类别主键
	 */
	private String pk_resetype;
	/**
	 * add by Jay
	 * 类型
	 * 0---调整
	 * 1---调拨
	 * 2---其他
	 * xc68其他出库单上的出库类型
	 * xc69其他入库单上的入库类型
	 */
	private Integer itype;
	/**
	 * 调入选厂
	 */
    private String pk_factory1;
    /**
     * 调入仓库
     */
    private String pk_stordoc1;
	public String getPk_factory1() {
		return pk_factory1;
	}

	public void setPk_factory1(String pk_factory1) {
		this.pk_factory1 = pk_factory1;
	}

	public String getPk_stordoc1() {
		return pk_stordoc1;
	}

	public void setPk_stordoc1(String pk_stordoc1) {
		this.pk_stordoc1 = pk_stordoc1;
	}

	public Integer getItype() {
		return itype;
	}

	public void setItype(Integer itype) {
		this.itype = itype;
	}

	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}


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


	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}

	public String getPk_stordoc() {
		return pk_stordoc;
	}

	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}



	public String getPk_car() {
		return pk_car;
	}

	public void setPk_car(String pk_car) {
		this.pk_car = pk_car;
	}

	public UFDouble getStarthours() {
		return starthours;
	}

	public void setStarthours(UFDouble starthours) {
		this.starthours = starthours;
	}

	public String getPk_resetype() {
		return pk_resetype;
	}

	public void setPk_resetype(String pk_resetype) {
		this.pk_resetype = pk_resetype;
	}

	
}
