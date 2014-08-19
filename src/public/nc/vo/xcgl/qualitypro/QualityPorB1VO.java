package nc.vo.xcgl.qualitypro;
	
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
	
/**
 * <b> 优质优价方案价格表体B1VO </b>
 * 创建日期:2014-02-18 17:33:24
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class QualityPorB1VO extends XCHYChildSuperVO {
	/**
	 * 主表主键
	 */
	private String pk_qualitypro_h;
	/**
	 * 字表主键
	 */
	private String pk_qualitypro_b1;
	/**
	 * 优质优价-价格主键
	 */
	private String pk_qualityprice_h;
	/**
	 * 计算优先级
	 */
	private Integer ipriority;
	/**
	 * 优质优价价格编码
	 */
	private String vqualitypricecode;
	/**
	 * 优质优价价格名称
	 */
	private String vqualitypricename;
	
	public String getVqualitypricecode() {
		return vqualitypricecode;
	}

	public void setVqualitypricecode(String vqualitypricecode) {
		this.vqualitypricecode = vqualitypricecode;
	}

	public String getVqualitypricename() {
		return vqualitypricename;
	}

	public void setVqualitypricename(String vqualitypricename) {
		this.vqualitypricename = vqualitypricename;
	}

	public String getPk_qualityprice_h() {
		return pk_qualityprice_h;
	}

	public void setPk_qualityprice_h(String pk_qualityprice_h) {
		this.pk_qualityprice_h = pk_qualityprice_h;
	}

	public String getPk_qualitypro_h() {
		return pk_qualitypro_h;
	}

	public void setPk_qualitypro_h(String pk_qualitypro_h) {
		this.pk_qualitypro_h = pk_qualitypro_h;
	}

	public String getPk_qualitypro_b1() {
		return pk_qualitypro_b1;
	}

	public void setPk_qualitypro_b1(String pk_qualitypro_b1) {
		this.pk_qualitypro_b1 = pk_qualitypro_b1;
	}


	public Integer getIpriority() {
		return ipriority;
	}

	public void setIpriority(Integer ipriority) {
		this.ipriority = ipriority;
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
	  return "pk_qualitypro_b1";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-02-18 17:33:24
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_qualitypro_b1";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-02-18 17:33:24
	  */
     public QualityPorB1VO() {
		super();	
	}    
} 
