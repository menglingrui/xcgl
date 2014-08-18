package nc.vo.xcgl.gendrugconsume;

import nc.vo.xcgl.pub.bill.PubGeneralBVO;
/**
 * ҩ�����ĸ�λ��¼����VO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class GenDrugConsumeBVO extends PubGeneralBVO{
	/**
	 * ��������
	 */
	private String pk_general_h;
	/**
	 * �ֱ�����
	 */
	private String pk_general_b;
	/**
	 * ��Ա����
	 */
	private String pk_psndoc;
	/**
	 * ��Ա��������
	 */
	private String pk_psnbasdoc;
	/**
	 * ��λ
	 */
	private String pk_measdoc;
	/**
	 * ������
	 */
	private String psnperson;
	
	public String getPsnperson() {
		return psnperson;
	}

	public void setPsnperson(String psnperson) {
		this.psnperson = psnperson;
	}

	public String getPk_psndoc() {
		return pk_psndoc;
	}

	public void setPk_psndoc(String pk_psndoc) {
		this.pk_psndoc = pk_psndoc;
	}

	public String getPk_psnbasdoc() {
		return pk_psnbasdoc;
	}

	public void setPk_psnbasdoc(String pk_psnbasdoc) {
		this.pk_psnbasdoc = pk_psnbasdoc;
	}

	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
	}

	public String getPk_general_b() {
		return pk_general_b;
	}

	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}
	@Override
	public String getPKFieldName() {
		return "pk_general_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_general_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_general_b";
	}

}
