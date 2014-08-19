package nc.vo.xcgl.qualitypro;
	
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * <b> 优质优价方案品位B2VO </b>
 * 创建日期:2014-02-18 17:33:24
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class QualityProB2VO extends XCHYChildSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_qualitypro_h;
	/**
	 * 字表主键
	 */
	private String pk_qualitypro_b2;
	/**
	 * 优质优价-品位主键
	 */
	private String pk_qualitygrade_h;
	/**
	 * 计算优先级
	 */
	private Integer ipriority;
//	/**
//	 * 优质优价品味编码
//	 */      XCGL_QUALITYPRICE_H,VQUALITYPRICENAME ,PK_QUALITYPRICE_H 
//	private String vqualitygradecode;
//	/**
//	 * 优质优价品味名称
//	 */
//	private String vqualitygradename;

//	public String getVqualitygradecode() {
//		return vqualitygradecode;
//	}
//
//	public void setVqualitygradecode(String vqualitygradecode) {
//		this.vqualitygradecode = vqualitygradecode;
//	}
//
//	public String getVqualitygradename() {
//		return vqualitygradename;
//	}
//
//	public void setVqualitygradename(String vqualitygradename) {
//		this.vqualitygradename = vqualitygradename;
//	}

	public String getPk_qualitygrade_h() {
		return pk_qualitygrade_h;
	}

	public void setPk_qualitygrade_h(String pk_qualitygrade_h) {
		this.pk_qualitygrade_h = pk_qualitygrade_h;
	}

	public Integer getIpriority() {
		return ipriority;
	}

	public void setIpriority(Integer ipriority) {
		this.ipriority = ipriority;
	}

	public String getPk_qualitypro_h() {
		return pk_qualitypro_h;
	}

	public void setPk_qualitypro_h(String pk_qualitypro_h) {
		this.pk_qualitypro_h = pk_qualitypro_h;
	}

	public String getPk_qualitypro_b2() {
		return pk_qualitypro_b2;
	}

	public void setPk_qualitypro_b2(String pk_qualitypro_b2) {
		this.pk_qualitypro_b2 = pk_qualitypro_b2;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-02-18 17:33:24
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_qualitypro_h";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-02-18 17:33:24
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_qualitypro_b2";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-02-18 17:33:24
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_qualitypro_b2";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-02-18 17:33:24
	  */
     public QualityProB2VO() {
		super();	
	}    
} 
