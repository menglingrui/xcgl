package nc.vo.xcgl.process;
	
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 *  工序基本档案VO
 * @author ddk
 */
@SuppressWarnings("serial")
public class ProcessVO extends XCNewBaseVO{
	/**
	 * 编号
	 */
	private String vprocesscode;
	/**
	 * 名称
	 */
	private String vprocessname;
	/**
	 * 主键
	 */
	private String pk_process;
	/**
	 * 行号
	 */
	private String crowno;
	/**
	 * 级次
	 */
	private Integer ilevels;
    
	public String getCrowno() {
		return crowno;
	}

	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}

	public String getVprocesscode() {
		return vprocesscode;
	}

	public void setVprocesscode(String vprocesscode) {
		this.vprocesscode = vprocesscode;
	}

	public String getVprocessname() {
		return vprocessname;
	}

	public void setVprocessname(String vprocessname) {
		this.vprocessname = vprocessname;
	}

	public String getPk_process() {
		return pk_process;
	}

	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
	}


	public Integer getIlevels() {
		return ilevels;
	}

	public void setIlevels(Integer ilevels) {
		this.ilevels = ilevels;
	}

	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-06 10:25:16
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_process";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-06 10:25:16
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_process";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2013-12-06 10:25:16
	  */
     public ProcessVO() {
		super();	
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}    
} 
