package nc.vo.xcgl.genotherout;

import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.PubGeneralBVO;
/**
 * �����������VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class GenotheroutBVO extends PubGeneralBVO{
	/**
	 * ��������
	 * */
	private String pk_general_b;
	/**
	 * ��������
	 * */
	private String pk_general_h;
	/**
	 * �ѳ�������
	 */
	private UFDouble noutnum;
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

	public UFDouble getNoutnum() {
		return noutnum;
	}

	public void setNoutnum(UFDouble noutnum) {
		this.noutnum = noutnum;
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
