package nc.vo.xcgl.pub.bill;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

@SuppressWarnings("serial")
public abstract class PubHighQualityBVO extends XCHYChildSuperVO {
	/**
	 * index
	 */
	private String pk_invdoc;
	/**
	 * index
	 */
	private String pk_mandoc;
	/**
	 * ��������������� ����
	 */
	private String pk_invmandoc;
	/**
	 * ��������������� ����
	 */
	private String pk_invbasdoc;
	/**
	 * �Ƿ��������
	 */
	private UFBoolean uincludelow;
	/**
	 * ����ֵ
	 */
	private UFDouble nlowvalue;
	/**
	 * ����ֵ
	 */
	private UFDouble nupvalue;
	/**
	 * �Ƿ��������
	 */
	private UFBoolean uincludeup;
	/**
	 * ��������
	 * 0--�ϵ�
	 * 1--�µ�
	 * 2--���ն���
	 * 
	 */
	private Integer vadjtype;
	/**
	 * ��������
	 */
	private Integer iadjbase;
	/**
	 * ��������
	 */
	private UFDouble nadjrange;
	/**
	 * �����ż۹�ʽ
	 */
	private String  vformula;
	
	/**
	 * �����ż۹�ʽ����
	 * @return
	 * @author yangtao
	 * @date 2014-3-1 ����05:35:59
	 */
	private String vformulacode;
	
	
	
	
	
	public String getPk_invdoc() {
		return pk_invdoc;
	}

	public void setPk_invdoc(String pk_invdoc) {
		this.pk_invdoc = pk_invdoc;
	}

	public String getPk_mandoc() {
		return pk_mandoc;
	}

	public void setPk_mandoc(String pk_mandoc) {
		this.pk_mandoc = pk_mandoc;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}

	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}

	public UFBoolean getUincludelow() {
		return uincludelow;
	}

	public void setUincludelow(UFBoolean uincludelow) {
		this.uincludelow = uincludelow;
	}

	public UFDouble getNlowvalue() {
		return nlowvalue;
	}

	public void setNlowvalue(UFDouble nlowvalue) {
		this.nlowvalue = nlowvalue;
	}

	public UFDouble getNupvalue() {
		return nupvalue;
	}

	public void setNupvalue(UFDouble nupvalue) {
		this.nupvalue = nupvalue;
	}

	public UFBoolean getUincludeup() {
		return uincludeup;
	}

	public void setUincludeup(UFBoolean uincludeup) {
		this.uincludeup = uincludeup;
	}

	

	public Integer getVadjtype() {
		return vadjtype;
	}

	public void setVadjtype(Integer vadjtype) {
		this.vadjtype = vadjtype;
	}

	public Integer getIadjbase() {
		return iadjbase;
	}

	public void setIadjbase(Integer iadjbase) {
		this.iadjbase = iadjbase;
	}

	public UFDouble getNadjrange() {
		return nadjrange;
	}

	public void setNadjrange(UFDouble nadjrange) {
		this.nadjrange = nadjrange;
	}

	public String  getVformula() {
		return vformula;
	}

	public void setVformula(String  vformula) {
		this.vformula = vformula;
	}

	public String getVformulacode() {
		return vformulacode;
	}

	public void setVformulacode(String vformulacode) {
		this.vformulacode = vformulacode;
	}
	
	
} 

