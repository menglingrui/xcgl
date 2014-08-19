package nc.vo.xcgl.soct;

import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 *  变更历史
 * 创建日期:2014-03-05 17:43:50
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SoctB5VO extends XCHYChildSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_soct;
	/**
	 * 子表主键
	 */
	private String pk_soct_b5;
	/**
	 * 版本号
	 */
	private String vchangecode;
	/**
	 * 变更人
	 */
	private String vchgpsn;
	/**
	 * 变更原因
	 */
	private String vchgreason;
	/**
	 * 变更日期
	 */
	private UFDate dchgdate;
 
	public String getPk_soct() {
		return pk_soct;
	}

	public void setPk_soct(String pk_soct) {
		this.pk_soct = pk_soct;
	}

	public String getPk_soct_b5() {
		return pk_soct_b5;
	}

	public void setPk_soct_b5(String pk_soct_b5) {
		this.pk_soct_b5 = pk_soct_b5;
	}

	public String getVchangecode() {
		return vchangecode;
	}

	public void setVchangecode(String vchangecode) {
		this.vchangecode = vchangecode;
	}

	public String getVchgpsn() {
		return vchgpsn;
	}

	public void setVchgpsn(String vchgpsn) {
		this.vchgpsn = vchgpsn;
	}

	public String getVchgreason() {
		return vchgreason;
	}

	public void setVchgreason(String vchgreason) {
		this.vchgreason = vchgreason;
	}

	public UFDate getDchgdate() {
		return dchgdate;
	}

	public void setDchgdate(UFDate dchgdate) {
		this.dchgdate = dchgdate;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-03-05 17:43:50
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_soct";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-03-05 17:43:50
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_soct_b5";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-03-05 17:43:50
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_SOCT_B5";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-03-05 17:43:50
	  */
     public SoctB5VO() {
		super();	
	}    
} 
