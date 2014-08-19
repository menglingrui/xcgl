package nc.vo.xcgl.soct;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;

public class SoctHVO extends XCHYHeadSuperVO{

	private static final long serialVersionUID = 2381260519121266817L;
	/**
	 * 主表主键
	 */
	private String pk_soct;
	/**
	 * 合同编码
	 */
	private String ct_code;
	/**
	 * 合同名称
	 */
	private String ct_name;
	/**
	 * 版本
	 */
	private String	mversion;//版本
	/**
	 * 合同类型主键
	 */
	private String	pk_cttype;
	/**
	 * 合同签订日期
	 */
	private UFDate	subscribedate;
	/**
	 * 计划生效日期
	 */
	private UFDate	valdate;
	/**
	 * 计划失效日期
	 */
	private UFDate	invallidate;
	/**
	 * 实际生效日期
	 */
	private UFDate	actualvalidate;
	/**
	 * 实际终止日期
	 */
	private UFDate	actualinvalidate;
	/**
	 * 折辅汇率
	 */
	private UFDouble astcurrate;
	/**
	 * 审核人
	 */
	private String	audiid;
	/**
	 * 审核日期
	 */
	private UFDate	auditdate;
	
	private String	ct_templet_id;//合同模板
	/**
	 * 币种
	 */
	private String	currid;
	/**
	 * 客商基本
	 */
	private String	custbasid;
	/**
	 * 客商管理
	 */
	private String	custid;
	/**
	 * 对方单位说明
	 */
	private String	custunit;
	/**
	 * 部门
	 */
	private String pk_deptdoc;
	/**
	 * 人员
	 */
	private String	personnelid;
	/**
	 * 运输类型
	 */
	private String	transpmode;
	/**
	 * 交货地址
	 */
	private String deliaddr;
	/**
	 * 折本汇率
	 */
	private UFDouble currrate;
	/**
	 * 收付款协议
	 */
	private String	payterm;
	/**
	 * 合同状态
	 */
	private String	ctflag;
	/**
	 * 累计原币应收/付款总额
	 */
	private UFDouble norigpshamount;
	/**
	 * 财务原币累计收/付款总额
	 */
	private UFDouble naraptotalgpamount;
	/**
	 * 原币预付款
	 */
	private UFDouble noriprepaymny;
	/**
	 * 累计原币收付款总额
	 */
	private UFDouble noritotalgpamount;
	/**
	 * 原币预付款限额
	 */
	private UFDouble nprepaylimitmny;
	/**
	 * 本币预付款
	 */
	private UFDouble nprepaymny;
	/**
	 * 累计本币收付款总额
	 */
	private UFDouble ntotalgpamount;
	/**
	 * 累计本币应收/付款总额
	 */
	private UFDouble ntotalgpshamount;
	/**
	 * 付款计划报警
	 */
	private String	payalarmday;
	/**
	 * 项目基本档案主键
	 */
	private String	pk_jobbasfil;
	/**
	 * 原始合同pk
	 */
	private String	pk_orign;
	/**
	 * 项目号
	 */
	private String	projectid;
	/**
	 * 收获计划报警
	 */
	private UFDouble recalarmday;
	/**
	 * 合同条款类型
	 */
	private String pk_cttermtype;
	/**
	 * 是否关闭
	 */
	private UFBoolean isclose;
	
	public UFBoolean getIsclose() {
		return isclose;
	}

	public void setIsclose(UFBoolean isclose) {
		this.isclose = isclose;
	}

	public String getPk_cttermtype() {
		return pk_cttermtype;
	}

	public void setPk_cttermtype(String pk_cttermtype) {
		this.pk_cttermtype = pk_cttermtype;
	}

	public String getPk_soct() {
		return pk_soct;
	}

	public void setPk_soct(String pk_soct) {
		this.pk_soct = pk_soct;
	}

	public String getCt_code() {
		return ct_code;
	}

	public void setCt_code(String ct_code) {
		this.ct_code = ct_code;
	}

	public String getCt_name() {
		return ct_name;
	}

	public void setCt_name(String ct_name) {
		this.ct_name = ct_name;
	}

	public String getMversion() {
		return mversion;
	}

	public void setMversion(String mversion) {
		this.mversion = mversion;
	}

	public String getPk_cttype() {
		return pk_cttype;
	}

	public void setPk_cttype(String pk_cttype) {
		this.pk_cttype = pk_cttype;
	}

	public UFDate getSubscribedate() {
		return subscribedate;
	}

	public void setSubscribedate(UFDate subscribedate) {
		this.subscribedate = subscribedate;
	}

	public UFDate getValdate() {
		return valdate;
	}

	public void setValdate(UFDate valdate) {
		this.valdate = valdate;
	}

	public UFDate getInvallidate() {
		return invallidate;
	}

	public void setInvallidate(UFDate invallidate) {
		this.invallidate = invallidate;
	}

	public UFDate getActualvalidate() {
		return actualvalidate;
	}

	public void setActualvalidate(UFDate actualvalidate) {
		this.actualvalidate = actualvalidate;
	}

	public UFDate getActualinvalidate() {
		return actualinvalidate;
	}

	public void setActualinvalidate(UFDate actualinvalidate) {
		this.actualinvalidate = actualinvalidate;
	}

	public UFDouble getAstcurrate() {
		return astcurrate;
	}

	public void setAstcurrate(UFDouble astcurrate) {
		this.astcurrate = astcurrate;
	}

	public String getAudiid() {
		return audiid;
	}

	public void setAudiid(String audiid) {
		this.audiid = audiid;
	}

	public UFDate getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(UFDate auditdate) {
		this.auditdate = auditdate;
	}

	public String getCt_templet_id() {
		return ct_templet_id;
	}

	public void setCt_templet_id(String ct_templet_id) {
		this.ct_templet_id = ct_templet_id;
	}

	public String getCurrid() {
		return currid;
	}

	public void setCurrid(String currid) {
		this.currid = currid;
	}

	public String getCustbasid() {
		return custbasid;
	}

	public void setCustbasid(String custbasid) {
		this.custbasid = custbasid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustunit() {
		return custunit;
	}

	public void setCustunit(String custunit) {
		this.custunit = custunit;
	}

	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getPersonnelid() {
		return personnelid;
	}

	public void setPersonnelid(String personnelid) {
		this.personnelid = personnelid;
	}

	public String getTranspmode() {
		return transpmode;
	}

	public void setTranspmode(String transpmode) {
		this.transpmode = transpmode;
	}

	public String getDeliaddr() {
		return deliaddr;
	}

	public void setDeliaddr(String deliaddr) {
		this.deliaddr = deliaddr;
	}

	public UFDouble getCurrrate() {
		return currrate;
	}

	public void setCurrrate(UFDouble currrate) {
		this.currrate = currrate;
	}

	public String getPayterm() {
		return payterm;
	}

	public void setPayterm(String payterm) {
		this.payterm = payterm;
	}

	public String getCtflag() {
		return ctflag;
	}

	public void setCtflag(String ctflag) {
		this.ctflag = ctflag;
	}

	public UFDouble getNorigpshamount() {
		return norigpshamount;
	}

	public void setNorigpshamount(UFDouble norigpshamount) {
		this.norigpshamount = norigpshamount;
	}

	public UFDouble getNaraptotalgpamount() {
		return naraptotalgpamount;
	}

	public void setNaraptotalgpamount(UFDouble naraptotalgpamount) {
		this.naraptotalgpamount = naraptotalgpamount;
	}

	public UFDouble getNoriprepaymny() {
		return noriprepaymny;
	}

	public void setNoriprepaymny(UFDouble noriprepaymny) {
		this.noriprepaymny = noriprepaymny;
	}

	public UFDouble getNoritotalgpamount() {
		return noritotalgpamount;
	}

	public void setNoritotalgpamount(UFDouble noritotalgpamount) {
		this.noritotalgpamount = noritotalgpamount;
	}

	public UFDouble getNprepaylimitmny() {
		return nprepaylimitmny;
	}

	public void setNprepaylimitmny(UFDouble nprepaylimitmny) {
		this.nprepaylimitmny = nprepaylimitmny;
	}

	public UFDouble getNprepaymny() {
		return nprepaymny;
	}

	public void setNprepaymny(UFDouble nprepaymny) {
		this.nprepaymny = nprepaymny;
	}

	public UFDouble getNtotalgpamount() {
		return ntotalgpamount;
	}

	public void setNtotalgpamount(UFDouble ntotalgpamount) {
		this.ntotalgpamount = ntotalgpamount;
	}

	public UFDouble getNtotalgpshamount() {
		return ntotalgpshamount;
	}

	public void setNtotalgpshamount(UFDouble ntotalgpshamount) {
		this.ntotalgpshamount = ntotalgpshamount;
	}

	public String getPayalarmday() {
		return payalarmday;
	}

	public void setPayalarmday(String payalarmday) {
		this.payalarmday = payalarmday;
	}

	public String getPk_jobbasfil() {
		return pk_jobbasfil;
	}

	public void setPk_jobbasfil(String pk_jobbasfil) {
		this.pk_jobbasfil = pk_jobbasfil;
	}

	public String getPk_orign() {
		return pk_orign;
	}

	public void setPk_orign(String pk_orign) {
		this.pk_orign = pk_orign;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public UFDouble getRecalarmday() {
		return recalarmday;
	}

	public void setRecalarmday(UFDouble recalarmday) {
		this.recalarmday = recalarmday;
	}

	@Override
	public String getPKFieldName() {
		
		return "pk_soct";
	}

	@Override
	public String getParentPKFieldName() {
		
		return null;
	}

	@Override
	public String getTableName() {
		
		return "xcgl_soct";
	}

}
