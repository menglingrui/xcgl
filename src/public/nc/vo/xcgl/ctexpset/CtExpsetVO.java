package nc.vo.xcgl.ctexpset;
	
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * <b>合同费用VO </b>
 * 创建日期:2014-04-01 17:12:01
 * @author ddk
 * @version NCPrj 1.0
 */
public class CtExpsetVO extends XCNewBaseVO {
	private static final long serialVersionUID = -3369318295140740864L;
	/**
	 * 主键
	 */
	private String pk_ctexpset;
	/**
	 * 费用编码
	 */
	private String vexpitemcode;
	/**
	 * 费用名称
	 */
	private String vexpitemname;
	/**
	 * 金额
	 */
	private UFDouble nsum;
	/**
	 * 部门
	 */
	private String pk_deptdoc;
	
	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public UFDouble getNsum() {
		return nsum;
	}

	public void setNsum(UFDouble nsum) {
		this.nsum = nsum;
	}

	public String getPk_ctexpset() {
		return pk_ctexpset;
	}

	public void setPk_ctexpset(String pk_ctexpset) {
		this.pk_ctexpset = pk_ctexpset;
	}

	public String getVexpitemcode() {
		return vexpitemcode;
	}

	public void setVexpitemcode(String vexpitemcode) {
		this.vexpitemcode = vexpitemcode;
	}

	public String getVexpitemname() {
		return vexpitemname;
	}

	public void setVexpitemname(String vexpitemname) {
		this.vexpitemname = vexpitemname;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-04-01 17:12:01
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-04-01 17:12:01
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_ctexpset";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-04-01 17:12:01
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_ctexpset";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-04-01 17:12:01
	  */
     public CtExpsetVO() {
		super();	
	}    
} 

