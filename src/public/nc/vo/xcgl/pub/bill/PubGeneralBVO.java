package nc.vo.xcgl.pub.bill;
import nc.vo.pub.lang.UFDouble;
/**
 * 出入库表体VO
 * @author mlr
 */
public abstract class PubGeneralBVO extends XCHYChildSuperVO{

	private static final long serialVersionUID = -2565802062418704084L; 
	/**
	 * 计量单位
	 * */
	private String pk_measdoc;
	/**
	 * 关联精粉（一般是精粉管理主键，如果是精粉值为：pk_invmandoc）
	 */
	private String pk_father;
	/**
	 * 存货管理主键
	 */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 水份
	 */                                                                                                                                                                                                                                                                                                                        
	private UFDouble nwatercontent;
	/**
	 * 湿重  数量
	 */
	private UFDouble nwetweight;
	/**
	 * 湿重  辅数量
	 */
	private UFDouble nasswetweight;
	/**
	 * 干重  除去水份后的重量
	 */
	private UFDouble ndryweight;
	/**
	 * 辅干重 除去水份后的重量
	 */
	private UFDouble nassdryweight;
	/**
	 *关联行号
	 *
	 */
	private String vcrowno;
	   /**
     * 调出部门
     */
    private String pk_deptdoc;
    /**
     * 调入部门
     */
    private String pk_deptdoc1;
	/**
	 * 已出库数量
	 */
	private UFDouble noutnum;
	private UFDouble ngrossweight;
	public UFDouble getNgrossweight() {
		return ngrossweight;
	}

	public void setNgrossweight(UFDouble ngrossweight) {
		this.ngrossweight = ngrossweight;
	}

	public String getPk_deptdoc() {
		return pk_deptdoc;
	}

	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}

	public String getPk_deptdoc1() {
		return pk_deptdoc1;
	}

	public void setPk_deptdoc1(String pk_deptdoc1) {
		this.pk_deptdoc1 = pk_deptdoc1;
	}

	public UFDouble getNoutnum() {
		return noutnum;
	}

	public void setNoutnum(UFDouble noutnum) {
		this.noutnum = noutnum;
	}

	public String getVcrowno() {
		return vcrowno;
	}

	public void setVcrowno(String vcrowno) {
		this.vcrowno = vcrowno;
	}

	public String getPk_father() {
		return pk_father;
	}

	public void setPk_father(String pk_father) {
		this.pk_father = pk_father;
	}

	public UFDouble getNasswetweight() {
		return nasswetweight;
	}

	public void setNasswetweight(UFDouble nasswetweight) {
		this.nasswetweight = nasswetweight;
	}

	public UFDouble getNassdryweight() {
		return nassdryweight;
	}

	public void setNassdryweight(UFDouble nassdryweight) {
		this.nassdryweight = nassdryweight;
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
	public UFDouble getNwatercontent() {
		return nwatercontent;
	}

	public void setNwatercontent(UFDouble nwatercontent) {
		this.nwatercontent = nwatercontent;
	}

	public UFDouble getNwetweight() {
		return nwetweight;
	}

	public void setNwetweight(UFDouble nwetweight) {
		this.nwetweight = nwetweight;
	}

	public UFDouble getNdryweight() {
		return ndryweight;
	}

	public void setNdryweight(UFDouble ndryweight) {
		this.ndryweight = ndryweight;
	}

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}
}
