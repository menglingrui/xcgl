package nc.vo.xcgl.flouryield;

import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * 精粉产量计算表体VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class FlouryieldBVO extends XCHYChildSuperVO{
	/**
	 *类型
	 *0--是否二次选
	 *1--二次选精粉
	 *2--二次选尾矿
	 *3--精粉
	 *4--尾矿 
	 */
    private Integer itype;
	/**
	 * 精粉产量计算表头主键
	 */
	private String pk_flouryield_h;
	/**
	 * 精粉产量计算表体主键
	 */
	private String pk_flouryield_b;
	/**
	 * 存货管理主键
	 */
	private String pk_invmandoc;
	/**
	 * 存货基本主键
	 */
	private String pk_invbasdoc;
	/**
	 * 矿区
	 */
	private String pk_minarea;
	/**
	 * 仓库
	 */
	private String pk_stordoc;
	/**
	 * 品位
	 */
	private UFDouble ncontent;
	/**
	 * 回收率
	 */
	private UFDouble nrecover;
	/**
	 * 原矿品位
	 */
	private UFDouble ncrudescontent;
	/**
	 * 产量
	 */
	private UFDouble noutput;
	/**
	 * 金属量
	 */
	private UFDouble nmetalamount;
	/**
	 * 原矿量
	 */
	private UFDouble ncrudes;
	/**
	 * 主指标主键
	 */
	private String pk_manindex;
	/**
	 * 主指标基本主键
	 */
	private String pk_invindex;
	/**
	 * 精粉品味一
	 */
	private UFDouble  ncontent1;
	/**
	 * 精粉品味二
	 */
	private UFDouble  ncontent2;
	/**
	 * 精粉品味三
	 */
	private UFDouble  ncontent3;
	/**
	 * 计量单位
	 * */
	private String pk_measdoc;
	/**
	 * 部门
	 */
	private String pk_deptdoc;
	/**
	 * add by jay
	 * 关联精粉
	 */
	private String pk_father;
	/**
	 * 关联行号
	 */
	private String vcrowno;
	
	
	public String getPk_father() {
		return pk_father;
	}
	public void setPk_father(String pk_father) {
		this.pk_father = pk_father;
	}
	public String getVcrowno() {
		return vcrowno;
	}
	public void setVcrowno(String vcrowno) {
		this.vcrowno = vcrowno;
	}
	public String getPk_deptdoc() {
		return pk_deptdoc;
	}
	public void setPk_deptdoc(String pk_deptdoc) {
		this.pk_deptdoc = pk_deptdoc;
	}
	public String getPk_measdoc() {
		return pk_measdoc;
	}
	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}
	public UFDouble getNcontent1() {
		return ncontent1;
	}
	public void setNcontent1(UFDouble ncontent1) {
		this.ncontent1 = ncontent1;
	}
	public UFDouble getNcontent2() {
		return ncontent2;
	}
	public void setNcontent2(UFDouble ncontent2) {
		this.ncontent2 = ncontent2;
	}
	public UFDouble getNcontent3() {
		return ncontent3;
	}
	public void setNcontent3(UFDouble ncontent3) {
		this.ncontent3 = ncontent3;
	}
	public UFDouble getNcrudes() {
		return ncrudes;
	}
	public void setNcrudes(UFDouble ncrudes) {
		this.ncrudes = ncrudes;
	}

	public UFDouble getNmetalamount() {
		return nmetalamount;
	}

	public void setNmetalamount(UFDouble nmetalamount) {
		this.nmetalamount = nmetalamount;
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

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	public String getPk_stordoc() {
		return pk_stordoc;
	}

	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}

	public String getPk_flouryield_h() {
		return pk_flouryield_h;
	}

	public void setPk_flouryield_h(String pk_flouryield_h) {
		this.pk_flouryield_h = pk_flouryield_h;
	}

	public String getPk_flouryield_b() {
		return pk_flouryield_b;
	}

	public void setPk_flouryield_b(String pk_flouryield_b) {
		this.pk_flouryield_b = pk_flouryield_b;
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

	public UFDouble getNcontent() {
		return ncontent;
	}

	public void setNcontent(UFDouble ncontent) {
		this.ncontent = ncontent;
	}

	public UFDouble getNrecover() {
		return nrecover;
	}

	public void setNrecover(UFDouble nrecover) {
		this.nrecover = nrecover;
	}

	public UFDouble getNcrudescontent() {
		return ncrudescontent;
	}

	public void setNcrudescontent(UFDouble ncrudescontent) {
		this.ncrudescontent = ncrudescontent;
	}

	public UFDouble getNoutput() {
		return noutput;
	}

	public void setNoutput(UFDouble noutput) {
		this.noutput = noutput;
	}

	@Override
	public String getPKFieldName() {
		return "pk_flouryield_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_flouryield_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_flouryield_b";
	}
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
	}

}
