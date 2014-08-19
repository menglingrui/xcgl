
package nc.vo.xcgl.produceset;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * 产出设置
 * 创建日期:2013-12-11 16:31:44
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ProductSetBB2VO extends XCHYChildSuperVO {
	/**
	 * 主键	
	 */
	private String pk_produceset_bb2;
	/**
	 * 主表主键
	 */
	private String pk_produceset_b;	
	/**
	 * 行号
	 */
	private String crowno;
	/**
	 * 存货管理主键
	 */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 是否品位检测
	 */
	private UFBoolean uistest;
	/**
	 * 存货类型 0精粉；1尾矿
	 */
	private Integer ioretype;
	/**
	 * 主指标主键
	 */
	private String pk_manindex;
	/**
	 * 主指标基本主键
	 */
	private String pk_invindex;	
	/**
	 * 公式编码1
	 */
	private String vformulacode1;
	/**
	 * 公式名称1
	 */
	private String vformulaname1;
	/**
	 * 公式编码2
	 */
	private String vformulacode2;
	/**
	 * 公式名称2
	 */
	private String vformulaname2;
	/**
	 * 公式编码3
	 */
	private String vformulacode3;
	/**
	 * 公式名称3
	 */
	private String vformulaname3;
	/**
	 * 公式编码4
	 */
	private String vformulacode4;
	/**
	 * 公式名称4
	 */
	private String vformulaname4;	
	/**
	 * 公式编码5
	 */
	private String vformulacode5;
	/**
	 * 公式名称5
	 */
	private String vformulaname5;
	/**
	 * 备注
	 */	
	private String vmemo;
	/**
	 * 是否二次重选
	 */
	private UFBoolean uisreselect;
	/**
	 * 是否二次选精粉
	 */
	private UFBoolean uispowder;

	public UFBoolean getUispowder() {
		return uispowder;
	}
	public void setUispowder(UFBoolean uispowder) {
		this.uispowder = uispowder;
	}
	public UFBoolean getUisreselect() {
		return uisreselect;
	}
	public void setUisreselect(UFBoolean uisreselect) {
		this.uisreselect = uisreselect;
	}
	public String getVformulacode4() {
		return vformulacode4;
	}
	public void setVformulacode4(String vformulacode4) {
		this.vformulacode4 = vformulacode4;
	}
	public String getVformulaname4() {
		return vformulaname4;
	}
	public void setVformulaname4(String vformulaname4) {
		this.vformulaname4 = vformulaname4;
	}
	public String getVformulacode5() {
		return vformulacode5;
	}
	public void setVformulacode5(String vformulacode5) {
		this.vformulacode5 = vformulacode5;
	}
	public String getVformulaname5() {
		return vformulaname5;
	}
	public void setVformulaname5(String vformulaname5) {
		this.vformulaname5 = vformulaname5;
	}
	public Integer getIoretype() {
		return ioretype;
	}
	public void setIoretype(Integer ioretype) {
		this.ioretype = ioretype;
	}
	public String getPk_manindex() {
		return pk_manindex;
	}
	public void setPk_manindex(String pk_manindex) {
		this.pk_manindex = pk_manindex;
	}
	public String getPk_invindex() {
		return pk_invindex;
	}
	public void setPk_invindex(String pk_invindex) {
		this.pk_invindex = pk_invindex;
	}
	public String getPk_invmandoc() {
		return pk_invmandoc;
	}
	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}
	public String getVformulacode3() {
		return vformulacode3;
	}
	public void setVformulacode3(String vformulacode3) {
		this.vformulacode3 = vformulacode3;
	}
	public String getVformulacode1() {
		return vformulacode1;
	}
	public void setVformulacode1(String vformulacode1) {
		this.vformulacode1 = vformulacode1;
	}
	public String getVformulacode2() {
		return vformulacode2;
	}
	public void setVformulacode2(String vformulacode2) {
		this.vformulacode2 = vformulacode2;
	}
	public String getPk_produceset_b() {
		return pk_produceset_b;
	}
	public void setPk_produceset_b(String pk_produceset_b) {
		this.pk_produceset_b = pk_produceset_b;
	}
	public String getVmemo() {
		return vmemo;
	}
	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}
	public String getCrowno() {
		return crowno;
	}
	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}
	public String getPk_produceset_bb2() {
		return pk_produceset_bb2;
	}
	public void setPk_produceset_bb2(String pk_produceset_bb2) {
		this.pk_produceset_bb2 = pk_produceset_bb2;
	}
	public UFBoolean getUistest() {
		return uistest;
	}
	public void setUistest(UFBoolean uistest) {
		this.uistest = uistest;
	}
	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}
	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}
	public String getVformulaname2() {
		return vformulaname2;
	}
	public void setVformulaname2(String vformulaname2) {
		this.vformulaname2 = vformulaname2;
	}
	public String getVformulaname1() {
		return vformulaname1;
	}
	public void setVformulaname1(String vformulaname1) {
		this.vformulaname1 = vformulaname1;
	}
	public String getVformulaname3() {
		return vformulaname3;
	}
	public void setVformulaname3(String vformulaname3) {
		this.vformulaname3 = vformulaname3;
	}
	@Override
	public String getPKFieldName() {
		return "pk_produceset_bb2";
	}
	@Override
	public String getParentPKFieldName() {
		return "pk_produceset_b";
	}
	@Override
	public String getTableName() {
		return "xcgl_produceset_bb2";
	}

	
} 
