
package nc.vo.xcgl.produceset;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * Ͷ������
 * ��������:2013-12-11 16:31:44
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ProductSetBB1VO extends XCHYChildSuperVO {
	/**
	 * ����	
	 */
	private String pk_produceset_bb1;
	/**
	 * ��������
	 */
	private String pk_produceset_b;	
	/**
	 * �к�
	 */
	private String crowno;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * �Ƿ�Ʒλ���
	 */
	private UFBoolean uistest;
	/**
	 * �Ƿ�ԭ��
	 */
	private UFBoolean uisore;
	/**
	 * ��������
	 */
	private String vouttype;
	/**
	 * ��ʽ����1
	 */
	private String vformulacode1;
	/**
	 * ��ʽ����1
	 */
	private String vformulaname1;
	/**
	 * ��ʽ����2
	 */
	private String vformulacode2;
	/**
	 * ��ʽ����2
	 */
	private String vformulaname2;
	/**
	 * ��ʽ����3
	 */
	private String vformulacode3;
	/**
	 * ��ʽ����3
	 */
	private String vformulaname3;
	/**
	 * ��ʽ����4
	 */
	private String vformulacode4;
	/**
	 * ��ʽ����4
	 */
	private String vformulaname4;
	
	/**
	 * ��ʽ����5
	 */
	private String vformulacode5;
	/**
	 * ��ʽ����5
	 */
	private String vformulaname5;
	/**
	 * ��ע
	 */
	private String vmemo;
	
	public String getVouttype() {
		return vouttype;
	}
	public void setVouttype(String vouttype) {
		this.vouttype = vouttype;
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
	public UFBoolean getUisore() {
		return uisore;
	}
	public void setUisore(UFBoolean uisore) {
		this.uisore = uisore;
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
	public String getPk_produceset_bb1() {
		return pk_produceset_bb1;
	}
	public void setPk_produceset_bb1(String pk_produceset_bb1) {
		this.pk_produceset_bb1 = pk_produceset_bb1;
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
		return "pk_produceset_bb1";
	}
	@Override
	public String getParentPKFieldName() {
		return "pk_produceset_b";
	}
	@Override
	public String getTableName() {
		return "xcgl_produceset_bb1";
	}	
} 
