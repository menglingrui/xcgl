
package nc.vo.xcgl.cttermtype;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * 合同条款类型vo 
 * 创建日期:2014-02-12 11:24:10
 * @author jay
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class CtTermtypeVO extends XCNewBaseVO {
	/**
	 * 合同条款类型主键
	 */
	private String pk_cttermtype;
	/**
	 * 合同条款名称
	 */
	private String termtypename;
	/**
	 * 合同条款编码
	 */
	private String termtypecode;
	
	/**
	 * 父主键:存的是树的根节点值为root
	 */
	private String pk_parent;
 
	public String getPk_parent() {
		return pk_parent;
	}

	public void setPk_parent(String pk_parent) {
		this.pk_parent = pk_parent;
	}

	public String getPk_cttermtype() {
		return pk_cttermtype;
	}

	public void setPk_cttermtype(String pk_cttermtype) {
		this.pk_cttermtype = pk_cttermtype;
	}

	public String getTermtypename() {
		return termtypename;
	}

	public void setTermtypename(String termtypename) {
		this.termtypename = termtypename;
	}

	public String getTermtypecode() {
		return termtypecode;
	}

	public void setTermtypecode(String termtypecode) {
		this.termtypecode = termtypecode;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-02-12 11:24:10
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-02-12 11:24:10
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_cttermtype";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-02-12 11:24:10
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_cttermtype";
	}    
   
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-02-12 11:24:10
	  */
     public CtTermtypeVO() {
		super();	
	}    
} 
