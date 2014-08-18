package nc.vo.xcgl.pub.bill;
import java.util.List;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
/**
 * 流程设置公式定义VO
 * @author mlr
 */
public class ProSetFormulaVO extends SuperVO{
	private static final long serialVersionUID = 4087581814512533647L;
	
	private boolean isSharePowerFormula;
	
	private boolean isShareTailFormula;
	
	/**
	 * 存货类型 
	 */
	private ProSetEnum invtype;	
	/**
	 *指标类型
	 *0主指标 1副指标 2其他指标 
	 */
	private Integer itype;
    
	private String pk_raworemanid;

	private String pk_raworeinvid;

	private String pk_apowdermanid;

	private String pk_apowderinvid;

	private String pk_bpowdermanid;

	private String pk_bpowderinvid;
	
	private String pk_cpowdermanid;

	private String pk_cpowderinvid;
	
	private String pk_atailsmanid;

	private String pk_atailsinvid;

	private String pk_btailsmanid;

	private String pk_btailsinvid;
	
	private String pk_ctailsmanid;

	private String pk_ctailsinvid;	
	/**
	 * 是否二次重选
	 */
	private UFBoolean uisreselect;
	/**
	 * 二次选择精粉信息
	 */
	private List<RepeatSelFlorVO> replist;
	/**
	 * 指标
	 */
	private String pk_invindex;
	/**
	 * 指标
	 */
	private String pk_manindex;
	/**
	 * 原矿数量
	 */
	private UFDouble raworenum;
	/**
	 * 原矿品味
	 */
	private UFDouble raworegrade;
	
	/**
	 * 精矿（一）
	 */
	private UFDouble noutput1;
	/**
	 * 精矿品味（一）
	 */
	private UFDouble concentrate1;
	/**
	 * 尾矿品味（一）
	 */
	private UFDouble tailingsgrade1;
	
	/**
	 * 精矿（二）
	 */
	private UFDouble noutput2;
	/**
	 * 精矿品味（二）
	 */
	private UFDouble concentrate2;
	/**
	 * 尾矿品味（二）
	 */
	private UFDouble tailingsgrade2;
	/**
	 * 精矿（一）
	 */
	private UFDouble noutput3;
	/**
	 * 精矿品味（三）
	 */
	private UFDouble concentrate3;
	/**
	 * 尾矿品味（三）
	 */
	private UFDouble tailingsgrade3;	
	/**
	 * 回收率
	 */
	private UFDouble nrate;
	/**
	 * 回收率(1)
	 */
	private UFDouble nrate11;
	/**
	 * 回收率(2)
	 */
	private UFDouble nrate12;
	/**
	 * 回收率(3)
	 */
	private UFDouble nrate13;
	/**
	 * 金属量
	 */
	private UFDouble nmetal;
	/**
	 * 金属量(1)
	 */
	private UFDouble nmetal11;
	/**
	 * 金属量(2)
	 */
	private UFDouble nmetal12;
	/**
	 * 金属量(3)
	 */
	private UFDouble nmetal13;
	/**
	 * 精粉量
	 */
	private UFDouble nflour;
	/**
	 * 尾矿回收率
	 */
	private UFDouble nrate1;
	/**
	 * 尾矿金属量
	 */
	private UFDouble nmetal1;
	/**
	 * 尾矿金属量(1)
	 */
	private UFDouble nmetal121;
	/**
	 * 尾矿金属量(2)
	 */
	private UFDouble nmetal122;
	/**
	 * 尾矿金属量(3)
	 */
	private UFDouble nmetal123;
	/**
	 * 尾矿精粉量
	 */
	private UFDouble nflour1;
	
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
	 * 公式编码1
	 */
	private String vformulacode11;
	/**
	 * 公式名称1
	 */
	private String vformulaname11;
	/**
	 * 公式编码2
	 */
	private String vformulacode22;
	/**
	 * 公式名称2
	 */
	private String vformulaname22;
	/**
	 * 公式编码3
	 */
	private String vformulacode33;
	/**
	 * 公式名称3
	 */
	private String vformulaname33;
	/**
	 * 公式编码4
	 */
	private String vformulacode44;
	/**
	 * 公式名称4
	 */
	private String vformulaname44;	
	/**
	 * 公式编码5
	 */
	private String vformulacode55;
	/**
	 * 公式名称5
	 */
	private String vformulaname55;

	/**
	 * 自定义项
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
	
	
	
	public boolean isSharePowerFormula() {
		return isSharePowerFormula;
	}

	public void setSharePowerFormula(boolean isSharePowerFormula) {
		this.isSharePowerFormula = isSharePowerFormula;
	}

	public boolean isShareTailFormula() {
		return isShareTailFormula;
	}

	public void setShareTailFormula(boolean isShareTailFormula) {
		this.isShareTailFormula = isShareTailFormula;
	}

	public UFDouble getNoutput3() {
		return noutput3;
	}

	public void setNoutput3(UFDouble noutput3) {
		this.noutput3 = noutput3;
	}

	public UFDouble getNrate11() {
		return nrate11;
	}

	public void setNrate11(UFDouble nrate11) {
		this.nrate11 = nrate11;
	}

	public UFDouble getNrate12() {
		return nrate12;
	}

	public void setNrate12(UFDouble nrate12) {
		this.nrate12 = nrate12;
	}

	public UFDouble getNrate13() {
		return nrate13;
	}

	public void setNrate13(UFDouble nrate13) {
		this.nrate13 = nrate13;
	}

	public UFDouble getNmetal11() {
		return nmetal11;
	}

	public void setNmetal11(UFDouble nmetal11) {
		this.nmetal11 = nmetal11;
	}

	public UFDouble getNmetal12() {
		return nmetal12;
	}

	public void setNmetal12(UFDouble nmetal12) {
		this.nmetal12 = nmetal12;
	}

	public UFDouble getNmetal13() {
		return nmetal13;
	}

	public void setNmetal13(UFDouble nmetal13) {
		this.nmetal13 = nmetal13;
	}

	public UFDouble getNmetal121() {
		return nmetal121;
	}

	public void setNmetal121(UFDouble nmetal121) {
		this.nmetal121 = nmetal121;
	}

	public UFDouble getNmetal122() {
		return nmetal122;
	}

	public void setNmetal122(UFDouble nmetal122) {
		this.nmetal122 = nmetal122;
	}

	public UFDouble getNmetal123() {
		return nmetal123;
	}

	public void setNmetal123(UFDouble nmetal123) {
		this.nmetal123 = nmetal123;
	}

	public UFDouble getNoutput1() {
		return noutput1;
	}

	public void setNoutput1(UFDouble noutput1) {
		this.noutput1 = noutput1;
	}

	public UFDouble getNoutput2() {
		return noutput2;
	}

	public void setNoutput2(UFDouble noutput2) {
		this.noutput2 = noutput2;
	}

	public List<RepeatSelFlorVO> getReplist() {
		return replist;
	}

	public void setReplist(List<RepeatSelFlorVO> replist) {
		this.replist = replist;
	}

	public UFBoolean getUisreselect() {
		return uisreselect;
	}

	public void setUisreselect(UFBoolean uisreselect) {
		this.uisreselect = uisreselect;
	}

	public UFDouble getNrate1() {
		return nrate1;
	}

	public void setNrate1(UFDouble nrate1) {
		this.nrate1 = nrate1;
	}

	public UFDouble getNmetal1() {
		return nmetal1;
	}

	public void setNmetal1(UFDouble nmetal1) {
		this.nmetal1 = nmetal1;
	}

	public UFDouble getNflour1() {
		return nflour1;
	}

	public void setNflour1(UFDouble nflour1) {
		this.nflour1 = nflour1;
	}

	public String getVformulacode11() {
		return vformulacode11;
	}

	public void setVformulacode11(String vformulacode11) {
		this.vformulacode11 = vformulacode11;
	}

	public String getVformulaname11() {
		return vformulaname11;
	}

	public void setVformulaname11(String vformulaname11) {
		this.vformulaname11 = vformulaname11;
	}

	public String getVformulacode22() {
		return vformulacode22;
	}

	public void setVformulacode22(String vformulacode22) {
		this.vformulacode22 = vformulacode22;
	}

	public String getVformulaname22() {
		return vformulaname22;
	}

	public void setVformulaname22(String vformulaname22) {
		this.vformulaname22 = vformulaname22;
	}

	public String getVformulacode33() {
		return vformulacode33;
	}

	public void setVformulacode33(String vformulacode33) {
		this.vformulacode33 = vformulacode33;
	}

	public String getVformulaname33() {
		return vformulaname33;
	}

	public void setVformulaname33(String vformulaname33) {
		this.vformulaname33 = vformulaname33;
	}

	public String getVformulacode44() {
		return vformulacode44;
	}

	public void setVformulacode44(String vformulacode44) {
		this.vformulacode44 = vformulacode44;
	}

	public String getVformulaname44() {
		return vformulaname44;
	}

	public void setVformulaname44(String vformulaname44) {
		this.vformulaname44 = vformulaname44;
	}

	public String getVformulacode55() {
		return vformulacode55;
	}

	public void setVformulacode55(String vformulacode55) {
		this.vformulacode55 = vformulacode55;
	}

	public String getVformulaname55() {
		return vformulaname55;
	}

	public void setVformulaname55(String vformulaname55) {
		this.vformulaname55 = vformulaname55;
	}


	public UFDouble getNrate() {
		return nrate;
	}

	public void setNrate(UFDouble nrate) {
		this.nrate = nrate;
	}

	public UFDouble getNmetal() {
		return nmetal;
	}

	public void setNmetal(UFDouble nmetal) {
		this.nmetal = nmetal;
	}

	public UFDouble getNflour() {
		return nflour;
	}

	public void setNflour(UFDouble nflour) {
		this.nflour = nflour;
	}

	public String getPk_raworemanid() {
		return pk_raworemanid;
	}

	public void setPk_raworemanid(String pk_raworemanid) {
		this.pk_raworemanid = pk_raworemanid;
	}

	public String getPk_raworeinvid() {
		return pk_raworeinvid;
	}

	public void setPk_raworeinvid(String pk_raworeinvid) {
		this.pk_raworeinvid = pk_raworeinvid;
	}

	public String getPk_apowdermanid() {
		return pk_apowdermanid;
	}

	public void setPk_apowdermanid(String pk_apowdermanid) {
		this.pk_apowdermanid = pk_apowdermanid;
	}

	public String getPk_apowderinvid() {
		return pk_apowderinvid;
	}

	public void setPk_apowderinvid(String pk_apowderinvid) {
		this.pk_apowderinvid = pk_apowderinvid;
	}

	public String getPk_bpowdermanid() {
		return pk_bpowdermanid;
	}

	public void setPk_bpowdermanid(String pk_bpowdermanid) {
		this.pk_bpowdermanid = pk_bpowdermanid;
	}

	public String getPk_bpowderinvid() {
		return pk_bpowderinvid;
	}

	public void setPk_bpowderinvid(String pk_bpowderinvid) {
		this.pk_bpowderinvid = pk_bpowderinvid;
	}

	public String getPk_cpowdermanid() {
		return pk_cpowdermanid;
	}

	public void setPk_cpowdermanid(String pk_cpowdermanid) {
		this.pk_cpowdermanid = pk_cpowdermanid;
	}

	public String getPk_cpowderinvid() {
		return pk_cpowderinvid;
	}

	public void setPk_cpowderinvid(String pk_cpowderinvid) {
		this.pk_cpowderinvid = pk_cpowderinvid;
	}

	public String getPk_atailsmanid() {
		return pk_atailsmanid;
	}

	public void setPk_atailsmanid(String pk_atailsmanid) {
		this.pk_atailsmanid = pk_atailsmanid;
	}

	public String getPk_atailsinvid() {
		return pk_atailsinvid;
	}

	public void setPk_atailsinvid(String pk_atailsinvid) {
		this.pk_atailsinvid = pk_atailsinvid;
	}

	public String getPk_btailsmanid() {
		return pk_btailsmanid;
	}

	public void setPk_btailsmanid(String pk_btailsmanid) {
		this.pk_btailsmanid = pk_btailsmanid;
	}

	public String getPk_btailsinvid() {
		return pk_btailsinvid;
	}

	public void setPk_btailsinvid(String pk_btailsinvid) {
		this.pk_btailsinvid = pk_btailsinvid;
	}

	public String getPk_ctailsmanid() {
		return pk_ctailsmanid;
	}

	public void setPk_ctailsmanid(String pk_ctailsmanid) {
		this.pk_ctailsmanid = pk_ctailsmanid;
	}

	public String getPk_ctailsinvid() {
		return pk_ctailsinvid;
	}

	public void setPk_ctailsinvid(String pk_ctailsinvid) {
		this.pk_ctailsinvid = pk_ctailsinvid;
	}

	public String getVformulacode1() {
		return vformulacode1;
	}

	public void setVformulacode1(String vformulacode1) {
		this.vformulacode1 = vformulacode1;
	}

	public String getVformulaname1() {
		return vformulaname1;
	}

	public void setVformulaname1(String vformulaname1) {
		this.vformulaname1 = vformulaname1;
	}

	public String getVformulacode2() {
		return vformulacode2;
	}

	public void setVformulacode2(String vformulacode2) {
		this.vformulacode2 = vformulacode2;
	}

	public String getVformulaname2() {
		return vformulaname2;
	}

	public void setVformulaname2(String vformulaname2) {
		this.vformulaname2 = vformulaname2;
	}

	public String getVformulacode3() {
		return vformulacode3;
	}

	public void setVformulacode3(String vformulacode3) {
		this.vformulacode3 = vformulacode3;
	}

	public String getVformulaname3() {
		return vformulaname3;
	}

	public void setVformulaname3(String vformulaname3) {
		this.vformulaname3 = vformulaname3;
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

	public Integer getItype() {
		return itype;
	}

	public void setItype(Integer itype) {
		this.itype = itype;
	}

	public ProSetEnum getInvtype() {
		return invtype;
	}

	public void setInvtype(ProSetEnum invtype) {
		this.invtype = invtype;
	}
	
	public String getPk_invindex() {
		return pk_invindex;
	}

	public void setPk_invindex(String pk_invindex) {
		this.pk_invindex = pk_invindex;
	}

	public String getPk_manindex() {
		return pk_manindex;
	}

	public void setPk_manindex(String pk_manindex) {
		this.pk_manindex = pk_manindex;
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

	public UFDouble getRaworenum() {
		return raworenum;
	}

	public void setRaworenum(UFDouble raworenum) {
		this.raworenum = raworenum;
	}

	public UFDouble getRaworegrade() {
		return raworegrade;
	}

	public void setRaworegrade(UFDouble raworegrade) {
		this.raworegrade = raworegrade;
	}

	public UFDouble getConcentrate1() {
		return concentrate1;
	}

	public void setConcentrate1(UFDouble concentrate1) {
		this.concentrate1 = concentrate1;
	}

	public UFDouble getTailingsgrade1() {
		return tailingsgrade1;
	}

	public void setTailingsgrade1(UFDouble tailingsgrade1) {
		this.tailingsgrade1 = tailingsgrade1;
	}

	public UFDouble getConcentrate2() {
		return concentrate2;
	}

	public void setConcentrate2(UFDouble concentrate2) {
		this.concentrate2 = concentrate2;
	}

	public UFDouble getTailingsgrade2() {
		return tailingsgrade2;
	}

	public void setTailingsgrade2(UFDouble tailingsgrade2) {
		this.tailingsgrade2 = tailingsgrade2;
	}

	public UFDouble getConcentrate3() {
		return concentrate3;
	}

	public void setConcentrate3(UFDouble concentrate3) {
		this.concentrate3 = concentrate3;
	}

	public UFDouble getTailingsgrade3() {
		return tailingsgrade3;
	}

	public void setTailingsgrade3(UFDouble tailingsgrade3) {
		this.tailingsgrade3 = tailingsgrade3;
	}
}
