/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.xcgl.factory;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * 选厂基本档案VO
 * @author ddk
 */
@SuppressWarnings("serial")
public class FactoryVO extends XCNewBaseVO {
	/**
	 * 编号
	 */
	private String vfactorycode;
	/**
	 * 主键
	 */
	private String pk_factory;
	/**
	 * 备注 
	 */
	private String vmemo;
	/**
	 * 名称
	 */
	private String vfactoryname;
	/**
	 * 行号
	 */
	private String crowno;
	/**
	 * 部门档案主键
	 */
	private String pk_deptdoc;
	
	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}


	public String getVfactorycode() {
		return vfactorycode;
	}

	public void setVfactorycode(String vfactorycode) {
		this.vfactorycode = vfactorycode;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	public String getVfactoryname() {
		return vfactoryname;
	}

	public void setVfactoryname(String vfactoryname) {
		this.vfactoryname = vfactoryname;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-06 10:24:54
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-06 10:24:54
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_factory";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-06 10:24:54
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_factory";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2013-12-06 10:24:54
	  */
     public FactoryVO() {
		super();	
	}    
} 
