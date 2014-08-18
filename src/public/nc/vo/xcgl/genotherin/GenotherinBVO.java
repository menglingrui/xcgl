package nc.vo.xcgl.genotherin;

import nc.vo.xcgl.pub.bill.PubGeneralBVO;
/**
 * ����������VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class GenotherinBVO extends PubGeneralBVO{
	/**
	 * ��������
	 * */
	private String pk_general_b;
	/**
	 * ��������
	 * */
	private String pk_general_h;
	/**
	 * ������λ
	 * */
	private String pk_measdoc;
	/**
	 * ��������������
	 * */
	private String pk_fabas;


	
	
	public String getPk_fabas() {
		return pk_fabas;
	}

	public void setPk_fabas(String pk_fabas) {
		this.pk_fabas = pk_fabas;
	}

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}

	public String getPk_general_b() {
		return pk_general_b;
	}

	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}

	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
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
