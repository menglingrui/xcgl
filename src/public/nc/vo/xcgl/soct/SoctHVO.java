package nc.vo.xcgl.soct;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;

public class SoctHVO extends XCHYHeadSuperVO{

	private static final long serialVersionUID = 2381260519121266817L;
	/**
	 * ��������
	 */
	private String pk_soct;
	/**
	 * ��ͬ����
	 */
	private String ct_code;
	/**
	 * ��ͬ����
	 */
	private String ct_name;
	/**
	 * �汾
	 */
	private String	mversion;//�汾
	/**
	 * ��ͬ��������
	 */
	private String	pk_cttype;
	/**
	 * ��ͬǩ������
	 */
	private UFDate	subscribedate;
	/**
	 * �ƻ���Ч����
	 */
	private UFDate	valdate;
	/**
	 * �ƻ�ʧЧ����
	 */
	private UFDate	invallidate;
	/**
	 * ʵ����Ч����
	 */
	private UFDate	actualvalidate;
	/**
	 * ʵ����ֹ����
	 */
	private UFDate	actualinvalidate;
	/**
	 * �۸�����
	 */
	private UFDouble astcurrate;
	/**
	 * �����
	 */
	private String	audiid;
	/**
	 * �������
	 */
	private UFDate	auditdate;
	
	private String	ct_templet_id;//��ͬģ��
	/**
	 * ����
	 */
	private String	currid;
	/**
	 * ���̻���
	 */
	private String	custbasid;
	/**
	 * ���̹���
	 */
	private String	custid;
	/**
	 * �Է���λ˵��
	 */
	private String	custunit;
	/**
	 * ����
	 */
	private String pk_deptdoc;
	/**
	 * ��Ա
	 */
	private String	personnelid;
	/**
	 * ��������
	 */
	private String	transpmode;
	/**
	 * ������ַ
	 */
	private String deliaddr;
	/**
	 * �۱�����
	 */
	private UFDouble currrate;
	/**
	 * �ո���Э��
	 */
	private String	payterm;
	/**
	 * ��ͬ״̬
	 */
	private String	ctflag;
	/**
	 * �ۼ�ԭ��Ӧ��/�����ܶ�
	 */
	private UFDouble norigpshamount;
	/**
	 * ����ԭ���ۼ���/�����ܶ�
	 */
	private UFDouble naraptotalgpamount;
	/**
	 * ԭ��Ԥ����
	 */
	private UFDouble noriprepaymny;
	/**
	 * �ۼ�ԭ���ո����ܶ�
	 */
	private UFDouble noritotalgpamount;
	/**
	 * ԭ��Ԥ�����޶�
	 */
	private UFDouble nprepaylimitmny;
	/**
	 * ����Ԥ����
	 */
	private UFDouble nprepaymny;
	/**
	 * �ۼƱ����ո����ܶ�
	 */
	private UFDouble ntotalgpamount;
	/**
	 * �ۼƱ���Ӧ��/�����ܶ�
	 */
	private UFDouble ntotalgpshamount;
	/**
	 * ����ƻ�����
	 */
	private String	payalarmday;
	/**
	 * ��Ŀ������������
	 */
	private String	pk_jobbasfil;
	/**
	 * ԭʼ��ͬpk
	 */
	private String	pk_orign;
	/**
	 * ��Ŀ��
	 */
	private String	projectid;
	/**
	 * �ջ�ƻ�����
	 */
	private UFDouble recalarmday;
	/**
	 * ��ͬ��������
	 */
	private String pk_cttermtype;
	/**
	 * �Ƿ�ر�
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
