package nc.vo.xcgl.saleshareout;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * ���۳��������̯���vo
 * ��������:2014-01-08 10:58:15
 * @author jay
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SaleShareoutBBVO extends XCHYChildSuperVO {
	/**
	 *���۳��������̯��ͷ���� 
	 */
	private String pk_share_h;
	public String getPk_share_h() {
		return pk_share_h;
	}

	public void setPk_share_h(String pk_share_h) {
		this.pk_share_h = pk_share_h;
	}

	/**
	 *�������� 
	 */
	private String pk_general_h;
	
	public String getPk_general_h() {
		return pk_general_h;
	}

	
	
	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
	}

	/**
	 * �ӱ�����
	 */
	private String pk_general_b;
	/**
	 * �������
	 */
	private String pk_general_bb;
	/**
	 * ����
	 */
	private UFDouble ndryweight;
	/**
	 * ������̯
	 */
	private String pk_minarea;
	/**
	 * �ֿ� 
	 */
    private String pk_stordoc;
    /**
     * invmandoc
     * @return
     */
    private String pk_invmandoc;
    /**
     * invbasdoc
     * @return
     */
    private String pk_invbasdoc;
    /**
     * father
     * @return
     */
    private String pk_father;
    /**
     *�����к� 
     * 
     */
    private String vcrowno;
    
    
	public String getVcrowno() {
		return vcrowno;
	}

	public void setVcrowno(String vcrowno) {
		this.vcrowno = vcrowno;
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

	public String getPk_father() {
		return pk_father;
	}

	public void setPk_father(String pk_father) {
		this.pk_father = pk_father;
	}

	public String getPk_stordoc() {
		return pk_stordoc;
	}

	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}

	public String getPk_general_b() {
		return pk_general_b;
	}

	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}

	public String getPk_general_bb() {
		return pk_general_bb;
	}

	public void setPk_general_bb(String pk_general_bb) {
		this.pk_general_bb = pk_general_bb;
	}

	public UFDouble getNdryweight() {
		return ndryweight;
	}

	public void setNdryweight(UFDouble ndryweight) {
		this.ndryweight = ndryweight;
	}

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2014-01-08 10:58:15
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return "pk_general_b";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2014-01-08 10:58:15
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_general_bb";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2014-01-08 10:58:15
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_general_bb";
	}    
} 
