package nc.vo.xcgl.pub.bill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;

/**
 * �����ż�-Ʒλ����ʽִ��VO
 * @author yangtao
 * @date 2014-4-1 ����04:34:23
 */
public class QualitygradeFormulaVO extends SuperVO{

	/**
	 * @date 2014-4-1����04:36:43
	 */
	private static final long serialVersionUID = 2796317242851090037L;
	/**
	 * ʵ�ʼ��ֵ
	 */
	private  UFDouble aworenum;
	/**
	 * ����
	 */
	private UFDouble nupvalue;
	/**
	 * ����
	 */
	private UFDouble nlowvalue;
	/**
	 * ��������
	 */
	private UFDouble iadjbase;
	/**
	 * ��������
	 */
	private UFDouble nadjrange;
	/**
	 * ��ʽ����
	 */
	private String vformulacode;
	/**
	 * ��ʽ����
	 */
	private String vformula;
	
	private String	pk_invbasdoc;
	
	private String 	pk_invmandoc;
	
	
	public UFDouble getAworenum() {
		return aworenum;
	}

	public void setAworenum(UFDouble aworenum) {
		this.aworenum = aworenum;
	}

	public UFDouble getNupvalue() {
		return nupvalue;
	}

	public void setNupvalue(UFDouble nupvalue) {
		this.nupvalue = nupvalue;
	}

	public UFDouble getNlowvalue() {
		return nlowvalue;
	}

	public void setNlowvalue(UFDouble nlowvalue) {
		this.nlowvalue = nlowvalue;
	}

	public UFDouble getIadjbase() {
		return iadjbase;
	}

	public void setIadjbase(UFDouble iadjbase) {
		this.iadjbase = iadjbase;
	}

	public UFDouble getNadjrange() {
		return nadjrange;
	}

	public void setNadjrange(UFDouble nadjrange) {
		this.nadjrange = nadjrange;
	}

	public String getVformulacode() {
		return vformulacode;
	}

	public void setVformulacode(String vformulacode) {
		this.vformulacode = vformulacode;
	}

	public String getVformula() {
		return vformula;
	}

	public void setVformula(String vformula) {
		this.vformula = vformula;
	}

	public UFDouble getNreserve1() {
		return nreserve1;
	}

	public void setNreserve1(UFDouble nreserve1) {
		this.nreserve1 = nreserve1;
	}

	public UFDouble getNreserve2() {
		return nreserve2;
	}

	public void setNreserve2(UFDouble nreserve2) {
		this.nreserve2 = nreserve2;
	}

	public UFDouble getNreserve3() {
		return nreserve3;
	}

	public void setNreserve3(UFDouble nreserve3) {
		this.nreserve3 = nreserve3;
	}

	public UFDouble getNreserve4() {
		return nreserve4;
	}

	public void setNreserve4(UFDouble nreserve4) {
		this.nreserve4 = nreserve4;
	}

	public UFDouble getNreserve5() {
		return nreserve5;
	}

	public void setNreserve5(UFDouble nreserve5) {
		this.nreserve5 = nreserve5;
	}

	public UFDouble getNreserve6() {
		return nreserve6;
	}

	public void setNreserve6(UFDouble nreserve6) {
		this.nreserve6 = nreserve6;
	}

	public UFDouble getNreserve7() {
		return nreserve7;
	}

	public void setNreserve7(UFDouble nreserve7) {
		this.nreserve7 = nreserve7;
	}

	public UFDouble getNreserve8() {
		return nreserve8;
	}

	public void setNreserve8(UFDouble nreserve8) {
		this.nreserve8 = nreserve8;
	}

	public UFDouble getNreserve9() {
		return nreserve9;
	}

	public void setNreserve9(UFDouble nreserve9) {
		this.nreserve9 = nreserve9;
	}

	public UFDouble getNreserve10() {
		return nreserve10;
	}

	public void setNreserve10(UFDouble nreserve10) {
		this.nreserve10 = nreserve10;
	}

	/**
	 * �Զ�����
	 */	
	public UFDouble nreserve1;
	public UFDouble nreserve2;
	public UFDouble nreserve3;
	public UFDouble nreserve4;
	public UFDouble nreserve5;
	public UFDouble nreserve6;
	public UFDouble nreserve7;
	public UFDouble nreserve8;
	public UFDouble nreserve9;
	public UFDouble nreserve10;
	
	
	
	

	@Override
	public String getPKFieldName() {
		return null;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
