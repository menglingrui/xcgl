package nc.vo.xcgl.oreprocount;
	
import nc.vo.pub.lang.*;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
	
/**
 * 矿石加工月汇总计算表头VO
 * 创建日期:2013-12-18 16:05:12
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class OreporcountHVO extends XCHYHeadSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_oreprocount;
	/**
	 * 矿区
	 */
	private String pk_minarea;
	/**
	 * 选厂档案主键
	 */
	private String pk_factory;
	/**
	 * Ag(g/t)
	 */
	private UFDouble nagpercent;
	/**
	 * Zn(%)
	 */
	private UFDouble nznpercent;
	/**
	 * Pb(%)
	 */
	private UFDouble npbpercent;
	/**
	 * 年度
	 */
	private String vyear;
	/**
	 * 月份
	 */
	private String vmonth;

	private String pk_deptdoc;
	
	public String getVyear() {
		return vyear;
	}

	public void setVyear(String vyear) {
		this.vyear = vyear;
	}

	public String getPk_oreprocount() {
		return pk_oreprocount;
	}

	public void setPk_oreprocount(String pk_oreprocount) {
		this.pk_oreprocount = pk_oreprocount;
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

	public UFDouble getNagpercent() {
		return nagpercent;
	}

	public void setNagpercent(UFDouble nagpercent) {
		this.nagpercent = nagpercent;
	}

	public UFDouble getNznpercent() {
		return nznpercent;
	}

	public void setNznpercent(UFDouble nznpercent) {
		this.nznpercent = nznpercent;
	}

	public UFDouble getNpbpercent() {
		return npbpercent;
	}

	public void setNpbpercent(UFDouble npbpercent) {
		this.npbpercent = npbpercent;
	}


	public String getVmonth() {
		return vmonth;
	}

	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}

	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-18 16:05:12
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-18 16:05:12
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_oreprocount";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-18 16:05:12
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_oreprocount";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2013-12-18 16:05:12
	  */
     public OreporcountHVO() {
		super();	
	}    
} 
