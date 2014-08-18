package nc.vo.xcgl.soct;

import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 *  合同条款
 * 创建日期:2014-03-05 17:43:50
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SoctB2VO extends XCHYChildSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_soct;
	/**
	 * 字表主键
	 */
	private String pk_soct_b2;
	/**
	 * 其他信息
	 */
	private String otherinfo;
	/**
	 * 合同条款主键
	 */
	private String pk_cttermset;
	/**
	 * 条款类型主键
	 */
	private String pk_cttermtype;
 


	public String getPk_soct() {
		return pk_soct;
	}

	public void setPk_soct(String pk_soct) {
		this.pk_soct = pk_soct;
	}

	public String getPk_soct_b2() {
		return pk_soct_b2;
	}

	public void setPk_soct_b2(String pk_soct_b2) {
		this.pk_soct_b2 = pk_soct_b2;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public String getPk_cttermset() {
		return pk_cttermset;
	}

	public void setPk_cttermset(String pk_cttermset) {
		this.pk_cttermset = pk_cttermset;
	}

	public String getPk_cttermtype() {
		return pk_cttermtype;
	}

	public void setPk_cttermtype(String pk_cttermtype) {
		this.pk_cttermtype = pk_cttermtype;
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
	  return "pk_soct_b2";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-03-05 17:43:50
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_SOCT_B2";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-03-05 17:43:50
	  */
     public SoctB2VO() {
		super();	
	}    
} 

