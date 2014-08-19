package nc.vo.xcgl.oreprocount;
	
import nc.vo.pub.lang.*;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * 矿石加工月汇总计算表体VO
 * 创建日期:2013-12-18 16:05:13
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class OreprocountBVO extends XCHYChildSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_oreprocount;
	/**
	 * 子表主键
	 */
	private String pk_oreprocount_b;
	/**
	 * 存货基本档案主键
	 */
	private String pk_invbasdoc;
	/**
	 * 存货管理档案主键
	 */
	private String pk_invmandoc;
	/**
	 * 湿重
	 */
	private UFDouble ngreenweigh;
	/**
	 * 干重
	 */
	private UFDouble ndryamount;
	/**
	 * Pb(t)
	 */
	private UFDouble npbweigh;
	/**
	 * Ag(kg)
	 */
	private UFDouble nagweigh;
	/**
	 * Zn(t)
	 */
	private UFDouble nznweigh;
	
	public String getPk_oreprocount() {
		return pk_oreprocount;
	}

	public void setPk_oreprocount(String pk_oreprocount) {
		this.pk_oreprocount = pk_oreprocount;
	}

	public String getPk_oreprocount_b() {
		return pk_oreprocount_b;
	}

	public void setPk_oreprocount_b(String pk_oreprocount_b) {
		this.pk_oreprocount_b = pk_oreprocount_b;
	}

	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}

	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	public UFDouble getNgreenweigh() {
		return ngreenweigh;
	}

	public void setNgreenweigh(UFDouble ngreenweigh) {
		this.ngreenweigh = ngreenweigh;
	}

	public UFDouble getNdryamount() {
		return ndryamount;
	}

	public void setNdryamount(UFDouble ndryamount) {
		this.ndryamount = ndryamount;
	}

	public UFDouble getNpbweigh() {
		return npbweigh;
	}

	public void setNpbweigh(UFDouble npbweigh) {
		this.npbweigh = npbweigh;
	}

	public UFDouble getNagweigh() {
		return nagweigh;
	}

	public void setNagweigh(UFDouble nagweigh) {
		this.nagweigh = nagweigh;
	}

	public UFDouble getNznweigh() {
		return nznweigh;
	}

	public void setNznweigh(UFDouble nznweigh) {
		this.nznweigh = nznweigh;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-18 16:05:13
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_oreprocount";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-18 16:05:13
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_oreprocount_b";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-18 16:05:13
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_oreprocount_b";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2013-12-18 16:05:13
	  */
     public OreprocountBVO() {
		super();	
	}    
} 
