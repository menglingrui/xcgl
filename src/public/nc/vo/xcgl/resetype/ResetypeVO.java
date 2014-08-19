package nc.vo.xcgl.resetype;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * <b> 收发类别VO </b>
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ResetypeVO extends XCNewBaseVO {
	/**
	 * 主键
	 */
	private String pk_resetype;
	/**
	 * 类型编码
	 */
	private String vtypecode;
	/**
	 * 类型名称
	 */
	private String vtypename;
	/**
	 * 出入库类型
	 */
	private String vinouttype;
	
	public String getPk_resetype() {
		return pk_resetype;
	}

	public void setPk_resetype(String pk_resetype) {
		this.pk_resetype = pk_resetype;
	}

	public String getVtypecode() {
		return vtypecode;
	}

	public void setVtypecode(String vtypecode) {
		this.vtypecode = vtypecode;
	}

	public String getVtypename() {
		return vtypename;
	}

	public void setVtypename(String vtypename) {
		this.vtypename = vtypename;
	}

	public String getVinouttype() {
		return vinouttype;
	}

	public void setVinouttype(String vinouttype) {
		this.vinouttype = vinouttype;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-02-13 11:15:09
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-02-13 11:15:09
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_resetype";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-02-13 11:15:09
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_resetype";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-02-13 11:15:09
	  */
     public ResetypeVO() {
		super();	
	}    
} 
