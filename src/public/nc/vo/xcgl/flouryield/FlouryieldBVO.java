package nc.vo.xcgl.flouryield;

import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * ���۲����������VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class FlouryieldBVO extends XCHYChildSuperVO{
	/**
	 *����
	 *0--�Ƿ����ѡ
	 *1--����ѡ����
	 *2--����ѡβ��
	 *3--����
	 *4--β�� 
	 */
    private Integer itype;
	/**
	 * ���۲��������ͷ����
	 */
	private String pk_flouryield_h;
	/**
	 * ���۲��������������
	 */
	private String pk_flouryield_b;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * ����
	 */
	private String pk_minarea;
	/**
	 * �ֿ�
	 */
	private String pk_stordoc;
	/**
	 * Ʒλ
	 */
	private UFDouble ncontent;
	/**
	 * ������
	 */
	private UFDouble nrecover;
	/**
	 * ԭ��Ʒλ
	 */
	private UFDouble ncrudescontent;
	/**
	 * ����
	 */
	private UFDouble noutput;
	/**
	 * ������
	 */
	private UFDouble nmetalamount;
	/**
	 * ԭ����
	 */
	private UFDouble ncrudes;
	/**
	 * ��ָ������
	 */
	private String pk_manindex;
	/**
	 * ��ָ���������
	 */
	private String pk_invindex;
	/**
	 * ����Ʒζһ
	 */
	private UFDouble  ncontent1;
	/**
	 * ����Ʒζ��
	 */
	private UFDouble  ncontent2;
	/**
	 * ����Ʒζ��
	 */
	private UFDouble  ncontent3;
	/**
	 * ������λ
	 * */
	private String pk_measdoc;
	/**
	 * ����
	 */
	private String pk_deptdoc;
	/**
	 * add by jay
	 * ��������
	 */
	private String pk_father;
	/**
	 * �����к�
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
