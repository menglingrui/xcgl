package nc.vo.xcgl.flouryield;

import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * ���۲��������ͷVO
 * @author lxh
 */
@SuppressWarnings("serial")
public class FlouryieldHVO extends XCHYHeadSuperVO {
	/**
	 * ���۲��������ͷ����
	 */
	private String pk_flouryield_h;
	/**
	 * ѡ������
	 */
	private String pk_factory;
	/**
	 * ����������
	 */
	private String pk_beltline;
	/**
	 * �������
	 */
	private String pk_classorder;
	/**
	 * �ֿ�����
	 */
	private String pk_stordoc;
	/**
	 * �����������
	 */
	private String pk_invmandoc;
	/**
	 * �����������
	 */
	private String pk_invbasdoc;
	/**
	 * �ӹ�������
	 */
	private UFDouble ntoolingout;
	/**
	 * ������
	 */
	private String pk_countpeople;
	/**
	 * add by jay
	 * ���Ӱ����ֶ�
	 * @return
	 */
	private String pk_teamgroup_h;
	
	public String getPk_teamgroup_h() {
		return pk_teamgroup_h;
	}



	public void setPk_teamgroup_h(String pk_teamgroup_h) {
		this.pk_teamgroup_h = pk_teamgroup_h;
	}



	public String getPk_flouryield_h() {
		return pk_flouryield_h;
	}



	public void setPk_flouryield_h(String pk_flouryield_h) {
		this.pk_flouryield_h = pk_flouryield_h;
	}



	public String getPk_factory() {
		return pk_factory;
	}



	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}



	public String getPk_beltline() {
		return pk_beltline;
	}



	public void setPk_beltline(String pk_beltline) {
		this.pk_beltline = pk_beltline;
	}



	public String getPk_classorder() {
		return pk_classorder;
	}



	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}



	public String getPk_stordoc() {
		return pk_stordoc;
	}



	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
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



	public UFDouble getNtoolingout() {
		return ntoolingout;
	}



	public void setNtoolingout(UFDouble ntoolingout) {
		this.ntoolingout = ntoolingout;
	}



	public String getPk_countpeople() {
		return pk_countpeople;
	}



	public void setPk_countpeople(String pk_countpeople) {
		this.pk_countpeople = pk_countpeople;
	}

	@Override
	public String getPKFieldName() {
		return "pk_flouryield_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_flouryield_h";
	}

}
