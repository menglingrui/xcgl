package nc.vo.xcgl.pub.bill;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

/**
 * 化验样品表头VO
 * @author mlr
 */
public abstract class PubSampleHVO extends XCHYHeadSuperVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -17145612477071504L; 
   /**
     * 存货管理主键
     */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 样品编码
	 */
	private String vsamplenumber;
	/**
	 * 样品编码(显示)
	 * 在打印条码时使用
	 */
	private String vsampleno;
	/**
	 * 选厂主键
	 */
	private String pk_factory;
	/**
	 * 生产线主键
	 */
	private String pk_beltline;
	/**
	 * 班次主键
	 */
	private String pk_classorder;
	/**
	 * 取样单位
	 */
	private String pk_minarea;
	/**
	 * 仓库主键
	 */
	private String pk_stordoc;
	/**
	 * 送样人
	 */
	private String  pk_sendpeople;
    /**
     * 化验员
     */
	private String  pk_analystple;
	/**
	 * 0 初检样
	 * 1 客户样
	 * 2 仲裁样
	 * 3 复检样
	 * 4 其他
	 * 5外检样
	 * 6预结算样
	 */
	private Integer isampletype;
	/**
	 * 业务类别:
	 * 
	 * 0 生产加工
	 * 
	 * 1 精粉销售
	 * 
	 */
	private Integer ibusinesstype;
	/**
	 *过磅日期 
	 */
	private UFDate  dweighdate;
	/**
	 * 车辆主键
	 */
	private String pk_vehicle;
	/**
	 * 是否二次选
	 * ISRESEL
	 */
	private UFBoolean isresel;
	
	public UFBoolean getIsresel() {
		return isresel;
	}
	public void setIsresel(UFBoolean isresel) {
		this.isresel = isresel;
	}
	public String getPk_vehicle() {
		return pk_vehicle;
	}
	public void setPk_vehicle(String pk_vehicle) {
		this.pk_vehicle = pk_vehicle;
	}
	public UFDate getDweighdate() {
		return dweighdate;
	}
	public void setDweighdate(UFDate dweighdate) {
		this.dweighdate = dweighdate;
	}
	public Integer getIbusinesstype() {
		return ibusinesstype;
	}
	public void setIbusinesstype(Integer ibusinesstype) {
		this.ibusinesstype = ibusinesstype;
	}
	public Integer getIsampletype() {
		return isampletype;
	}
	public void setIsampletype(Integer isampletype) {
		this.isampletype = isampletype;
	}
	public String getPk_analystple() {
		return pk_analystple;
	}
	public void setPk_analystple(String pk_analystple) {
		this.pk_analystple = pk_analystple;
	}
	public String getPk_sendpeople() {
		return pk_sendpeople;
	}
	public void setPk_sendpeople(String pk_sendpeople) {
		this.pk_sendpeople = pk_sendpeople;
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
	public String getPk_classorder() {
		return pk_classorder;
	}
	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
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
	public String getVsamplenumber() {
		return vsamplenumber;
	}
	public void setVsamplenumber(String vsamplenumber) {
		this.vsamplenumber = vsamplenumber;
	}
	public String getVsampleno() {
		return vsampleno;
	}
	public void setVsampleno(String vsampleno) {
		this.vsampleno = vsampleno;
	}
}
