package nc.vo.xcgl.pub.bill;
import nc.vo.pub.lang.UFDouble;
/**
 * ��������VO
 * @author mlr
 */
public abstract class PubGeneralBVO extends XCHYChildSuperVO{

	private static final long serialVersionUID = -2565802062418704084L; 
	/**
	 * ������λ
	 * */
	private String pk_measdoc;
	/**
	 * �������ۣ�һ���Ǿ��۹�������������Ǿ���ֵΪ��pk_invmandoc��
	 */
	private String pk_father;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * ˮ��
	 */                                                                                                                                                                                                                                                                                                                        
	private UFDouble nwatercontent;
	/**
	 * ʪ��  ����
	 */
	private UFDouble nwetweight;
	/**
	 * ʪ��  ������
	 */
	private UFDouble nasswetweight;
	/**
	 * ����  ��ȥˮ�ݺ������
	 */
	private UFDouble ndryweight;
	/**
	 * ������ ��ȥˮ�ݺ������
	 */
	private UFDouble nassdryweight;
	/**
	 *�����к�
	 *
	 */
	private String vcrowno;
	   /**
     * ��������
     */
    private String pk_deptdoc;
    /**
     * ���벿��
     */
    private String pk_deptdoc1;
	/**
	 * �ѳ�������
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
