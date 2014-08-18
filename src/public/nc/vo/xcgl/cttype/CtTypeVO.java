
package nc.vo.xcgl.cttype;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCNewBaseVO;
	
/**
 * 合同类型vo
 * 创建日期:2014-02-13 15:37:21
 * @author jay
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class CtTypeVO extends XCNewBaseVO {
	/**
	 * 类型编码
	 */
	private String typecode;
	/**
	 * 数据控制方式
	 */
	private String ndatactlstyle;
	/**
	 * 订单控制方式
	 * 0--不控制
	 * 1--控制数量
	 * 2--控制金额
	 * 3--控制数量和金额
	 * 4--控制整单数量
	 * 5--控制整单金额
	 */
	private String controltype;
	/**
	 * 类型名称
	 */
	private String typename;
	/**
	 * 合同分类
	 * 0--采购合同
	 * 1--销售合同
	 * 2--其他合同
	 */
	private String nbusitype;
	/**
	 * 收付方向
	 * 0--收
	 * 1--付
	 */
	private String rec_or_pay;
	/**
	 * 是否严格控制订单
	 */
	private UFBoolean ismustcontrol;
	/**
	 * 订单容差控制比例
	 */
	private UFDouble urate;
	/**
	 * 存货控制方式
	 * 0--存货
	 * 1--存货分类
	 * 2--空
	 */
	private String ninvctlstyle;
	/**
	 * 合同类型主键
	 */
	private String pk_cttype;
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getNdatactlstyle() {
		return ndatactlstyle;
	}

	public void setNdatactlstyle(String ndatactlstyle) {
		this.ndatactlstyle = ndatactlstyle;
	}

	public String getControltype() {
		return controltype;
	}

	public void setControltype(String controltype) {
		this.controltype = controltype;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getNbusitype() {
		return nbusitype;
	}

	public void setNbusitype(String nbusitype) {
		this.nbusitype = nbusitype;
	}

	public String getRec_or_pay() {
		return rec_or_pay;
	}

	public void setRec_or_pay(String rec_or_pay) {
		this.rec_or_pay = rec_or_pay;
	}

	public UFBoolean getIsmustcontrol() {
		return ismustcontrol;
	}

	public void setIsmustcontrol(UFBoolean ismustcontrol) {
		this.ismustcontrol = ismustcontrol;
	}

	public UFDouble getUrate() {
		return urate;
	}

	public void setUrate(UFDouble urate) {
		this.urate = urate;
	}

	public String getNinvctlstyle() {
		return ninvctlstyle;
	}

	public void setNinvctlstyle(String ninvctlstyle) {
		this.ninvctlstyle = ninvctlstyle;
	}

	public String getPk_cttype() {
		return pk_cttype;
	}

	public void setPk_cttype(String pk_cttype) {
		this.pk_cttype = pk_cttype;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2014-02-13 15:37:21
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2014-02-13 15:37:21
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_cttype";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2014-02-13 15:37:21
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_cttype";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2014-02-13 15:37:21
	  */
     public CtTypeVO() {
		super();	
	}    
} 
