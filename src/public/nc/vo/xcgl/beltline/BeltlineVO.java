package nc.vo.xcgl.beltline;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 *  生产线VO 
 * @author ddk
 */
@SuppressWarnings("serial")
public class BeltlineVO extends XCNewBaseVO {
	/**
	 * 工序
	 */
	private String pk_process;
	/**
	 * 所属工厂
	 */
	private String pk_factory;
	/**
	 * 主键
	 */
	private String pk_beltline;
	/**
	 * 备注
	 */
	private String vmemo;
	/**
	 * 名称
	 */
	private String vbeltlinename;
	/**
	 * 编码
	 */
	private String vbeltlinecode;
	/**
	 * 行号
	 */
	private String crowno;
	/**
	 * 工艺档案
	 */
	private String pk_technology;

	public String getPk_technology() {
		return pk_technology;
	}

	public void setPk_technology(String pk_technology) {
		this.pk_technology = pk_technology;
	}

	public String getPk_process() {
		return pk_process;
	}

	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
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

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	public String getVbeltlinename() {
		return vbeltlinename;
	}

	public void setVbeltlinename(String vbeltlinename) {
		this.vbeltlinename = vbeltlinename;
	}


	public String getVbeltlinecode() {
		return vbeltlinecode;
	}

	public void setVbeltlinecode(String vbeltlinecode) {
		this.vbeltlinecode = vbeltlinecode;
	}

	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-06 10:22:25
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-06 10:22:25
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_beltline";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-06 10:22:25
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_beltline";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2013-12-06 10:22:25
	  */
     public BeltlineVO() {
		super();	
	}    
} 
