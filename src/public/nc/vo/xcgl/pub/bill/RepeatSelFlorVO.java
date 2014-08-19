package nc.vo.xcgl.pub.bill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
/**
 * 二次选择精粉VO
 * @author mlr
 *
 */
public class RepeatSelFlorVO extends SuperVO{
	/**
	 * raw infor
	 */
	private ProSetFormulaVO  rawinfor;

	public ProSetFormulaVO getRawinfor() {
		return rawinfor;
	}

	public void setRawinfor(ProSetFormulaVO rawinfor) {
		this.rawinfor = rawinfor;
	}

	private static final long serialVersionUID = 1752998766928209464L;
	/**
	 *指标类型
	 *0主指标 1副指标 2其他指标 
	 */
	private Integer itype;
	/**
	 * 二次后精粉
	 */
	private String pk_reinvmandoc;
	/**
	 * 二次选后精粉
	 */
	private String pk_reinvbasdoc;	
	/**
	 * 二次后尾矿
	 */
	private String pk_reinvmandoc1;
	/**
	 * 二次选后尾矿
	 */
	private String pk_reinvbasdoc1;
	/**
	 * 二次选后品位
	 */
	private UFDouble nregrade;
	/**
	 * 二次选后尾矿品位
	 */
	private UFDouble nregrade1;	
	/**
	 * 二次选后精粉回收率
	 */
	private UFDouble nrerate;
	/**
	 * 二次选后weikuang回收率
	 */
	private UFDouble nrerate1;
	/**
	 * 二次选后精粉金属量
	 */
	private UFDouble nremetal;
	/**
	 * 二次选后weikuang金属量
	 */
	private UFDouble nremetal1;
	/**
	 * 二次选后精粉精粉量
	 */
	private UFDouble nreflour;
	
	/**
	 * 二次选后weikaung精粉量
	 */
	private UFDouble nreflour1;
	public UFDouble getNreflour1() {
		return nreflour1;
	}

	public void setNreflour1(UFDouble nreflour1) {
		this.nreflour1 = nreflour1;
	}

	/**
	 * 公式编码1
	 */
	private String vreformulacode1;
	/**
	 * 公式名称1
	 */
	private String vreformulaname1;
	/**
	 * 公式编码2
	 */
	private String vreformulacode2;
	/**
	 * 公式名称2
	 */
	private String vreformulaname2;
	/**
	 * 公式编码3
	 */
	private String vreformulacode3;
	/**
	 * 公式名称3
	 */
	private String vreformulaname3;
	/**
	 * 公式编码4
	 */
	private String vreformulacode4;
	/**
	 * 公式名称4
	 */
	private String vreformulaname4;	
	/**
	 * 公式编码5
	 */
	private String vreformulacode5;
	/**
	 * 公式名称5
	 */
	private String vreformulaname5;
	
	/**
	 * 公式编码1
	 */
	private String vreformulacode11;
	/**
	 * 公式名称1
	 */
	private String vreformulaname11;
	/**
	 * 公式编码2
	 */
	private String vreformulacode22;
	/**
	 * 公式名称2
	 */
	private String vreformulaname22;
	/**
	 * 公式编码3
	 */
	private String vreformulacode33;
	/**
	 * 公式名称3
	 */
	private String vreformulaname33;
	/**
	 * 公式编码4
	 */
	private String vreformulacode44;
	/**
	 * 公式名称4
	 */
	private String vreformulaname44;	
	/**
	 * 公式编码5
	 */
	private String vreformulacode55;
	/**
	 * 公式名称5
	 */
	private String vreformulaname55;

	/**
	 * 自定义项
	 */	
	public UFDouble nrereserve1;
	public UFDouble nrereserve2;
	public UFDouble nrereserve3;
	public UFDouble nrereserve4;
	public UFDouble nrereserve5;
	public UFDouble nrereserve6;
	public UFDouble nrereserve7;
	public UFDouble nrereserve8;
	public UFDouble nrereserve9;
	public UFDouble nrereserve10;

	




	public UFDouble getNremetal1() {
		return nremetal1;
	}

	public void setNremetal1(UFDouble nremetal1) {
		this.nremetal1 = nremetal1;
	}

	public UFDouble getNrerate1() {
		return nrerate1;
	}

	public void setNrerate1(UFDouble nrerate1) {
		this.nrerate1 = nrerate1;
	}

	public String getPk_reinvmandoc1() {
		return pk_reinvmandoc1;
	}

	public void setPk_reinvmandoc1(String pk_reinvmandoc1) {
		this.pk_reinvmandoc1 = pk_reinvmandoc1;
	}

	public String getPk_reinvbasdoc1() {
		return pk_reinvbasdoc1;
	}

	public void setPk_reinvbasdoc1(String pk_reinvbasdoc1) {
		this.pk_reinvbasdoc1 = pk_reinvbasdoc1;
	}

	public Integer getItype() {
		return itype;
	}

	public void setItype(Integer itype) {
		this.itype = itype;
	}

	public UFDouble getNregrade() {
		return nregrade;
	}

	public void setNregrade(UFDouble nregrade) {
		this.nregrade = nregrade;
	}

	public UFDouble getNregrade1() {
		return nregrade1;
	}

	public void setNregrade1(UFDouble nregrade1) {
		this.nregrade1 = nregrade1;
	}

	public String getPk_reinvmandoc() {
		return pk_reinvmandoc;
	}

	public void setPk_reinvmandoc(String pk_reinvmandoc) {
		this.pk_reinvmandoc = pk_reinvmandoc;
	}

	public String getPk_reinvbasdoc() {
		return pk_reinvbasdoc;
	}

	public void setPk_reinvbasdoc(String pk_reinvbasdoc) {
		this.pk_reinvbasdoc = pk_reinvbasdoc;
	}


	public UFDouble getNrerate() {
		return nrerate;
	}

	public void setNrerate(UFDouble nrerate) {
		this.nrerate = nrerate;
	}

	public UFDouble getNremetal() {
		return nremetal;
	}

	public void setNremetal(UFDouble nremetal) {
		this.nremetal = nremetal;
	}

	public UFDouble getNreflour() {
		return nreflour;
	}

	public void setNreflour(UFDouble nreflour) {
		this.nreflour = nreflour;
	}

	public String getVreformulacode1() {
		return vreformulacode1;
	}

	public void setVreformulacode1(String vreformulacode1) {
		this.vreformulacode1 = vreformulacode1;
	}

	public String getVreformulaname1() {
		return vreformulaname1;
	}

	public void setVreformulaname1(String vreformulaname1) {
		this.vreformulaname1 = vreformulaname1;
	}

	public String getVreformulacode2() {
		return vreformulacode2;
	}

	public void setVreformulacode2(String vreformulacode2) {
		this.vreformulacode2 = vreformulacode2;
	}

	public String getVreformulaname2() {
		return vreformulaname2;
	}

	public void setVreformulaname2(String vreformulaname2) {
		this.vreformulaname2 = vreformulaname2;
	}

	public String getVreformulacode3() {
		return vreformulacode3;
	}

	public void setVreformulacode3(String vreformulacode3) {
		this.vreformulacode3 = vreformulacode3;
	}

	public String getVreformulaname3() {
		return vreformulaname3;
	}

	public void setVreformulaname3(String vreformulaname3) {
		this.vreformulaname3 = vreformulaname3;
	}

	public String getVreformulacode4() {
		return vreformulacode4;
	}

	public void setVreformulacode4(String vreformulacode4) {
		this.vreformulacode4 = vreformulacode4;
	}

	public String getVreformulaname4() {
		return vreformulaname4;
	}

	public void setVreformulaname4(String vreformulaname4) {
		this.vreformulaname4 = vreformulaname4;
	}

	public String getVreformulacode5() {
		return vreformulacode5;
	}

	public void setVreformulacode5(String vreformulacode5) {
		this.vreformulacode5 = vreformulacode5;
	}

	public String getVreformulaname5() {
		return vreformulaname5;
	}

	public void setVreformulaname5(String vreformulaname5) {
		this.vreformulaname5 = vreformulaname5;
	}

	public String getVreformulacode11() {
		return vreformulacode11;
	}

	public void setVreformulacode11(String vreformulacode11) {
		this.vreformulacode11 = vreformulacode11;
	}

	public String getVreformulaname11() {
		return vreformulaname11;
	}

	public void setVreformulaname11(String vreformulaname11) {
		this.vreformulaname11 = vreformulaname11;
	}

	public String getVreformulacode22() {
		return vreformulacode22;
	}

	public void setVreformulacode22(String vreformulacode22) {
		this.vreformulacode22 = vreformulacode22;
	}

	public String getVreformulaname22() {
		return vreformulaname22;
	}

	public void setVreformulaname22(String vreformulaname22) {
		this.vreformulaname22 = vreformulaname22;
	}

	public String getVreformulacode33() {
		return vreformulacode33;
	}

	public void setVreformulacode33(String vreformulacode33) {
		this.vreformulacode33 = vreformulacode33;
	}

	public String getVreformulaname33() {
		return vreformulaname33;
	}

	public void setVreformulaname33(String vreformulaname33) {
		this.vreformulaname33 = vreformulaname33;
	}

	public String getVreformulacode44() {
		return vreformulacode44;
	}

	public void setVreformulacode44(String vreformulacode44) {
		this.vreformulacode44 = vreformulacode44;
	}

	public String getVreformulaname44() {
		return vreformulaname44;
	}

	public void setVreformulaname44(String vreformulaname44) {
		this.vreformulaname44 = vreformulaname44;
	}

	public String getVreformulacode55() {
		return vreformulacode55;
	}

	public void setVreformulacode55(String vreformulacode55) {
		this.vreformulacode55 = vreformulacode55;
	}

	public String getVreformulaname55() {
		return vreformulaname55;
	}

	public void setVreformulaname55(String vreformulaname55) {
		this.vreformulaname55 = vreformulaname55;
	}

	public UFDouble getNrereserve1() {
		return nrereserve1;
	}

	public void setNrereserve1(UFDouble nrereserve1) {
		this.nrereserve1 = nrereserve1;
	}

	public UFDouble getNrereserve2() {
		return nrereserve2;
	}

	public void setNrereserve2(UFDouble nrereserve2) {
		this.nrereserve2 = nrereserve2;
	}

	public UFDouble getNrereserve3() {
		return nrereserve3;
	}

	public void setNrereserve3(UFDouble nrereserve3) {
		this.nrereserve3 = nrereserve3;
	}

	public UFDouble getNrereserve4() {
		return nrereserve4;
	}

	public void setNrereserve4(UFDouble nrereserve4) {
		this.nrereserve4 = nrereserve4;
	}

	public UFDouble getNrereserve5() {
		return nrereserve5;
	}

	public void setNrereserve5(UFDouble nrereserve5) {
		this.nrereserve5 = nrereserve5;
	}

	public UFDouble getNrereserve6() {
		return nrereserve6;
	}

	public void setNrereserve6(UFDouble nrereserve6) {
		this.nrereserve6 = nrereserve6;
	}

	public UFDouble getNrereserve7() {
		return nrereserve7;
	}

	public void setNrereserve7(UFDouble nrereserve7) {
		this.nrereserve7 = nrereserve7;
	}

	public UFDouble getNrereserve8() {
		return nrereserve8;
	}

	public void setNrereserve8(UFDouble nrereserve8) {
		this.nrereserve8 = nrereserve8;
	}

	public UFDouble getNrereserve9() {
		return nrereserve9;
	}

	public void setNrereserve9(UFDouble nrereserve9) {
		this.nrereserve9 = nrereserve9;
	}

	public UFDouble getNrereserve10() {
		return nrereserve10;
	}

	public void setNrereserve10(UFDouble nrereserve10) {
		this.nrereserve10 = nrereserve10;
	}

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
