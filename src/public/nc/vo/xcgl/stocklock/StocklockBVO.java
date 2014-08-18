package nc.vo.xcgl.stocklock;

import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.PubGeneralBVO;
/**
 * ���������������VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class StocklockBVO extends PubGeneralBVO{
	
	/**
	 * ��������
	 * */
	private String pk_general_b;
	/**
	 * ��������
	 * */
	private String pk_general_h;
	/**
	 * �ִ���
	 */
	private UFDouble nstandingcrop;
	/**
	 * ��������
	 */
	private UFDouble nlocked;
	/**
	 * ������  ������
	 */
	private UFDouble nunlock;
	/**
	 * ������
	 */
	private UFDouble nlock;
	/**
	 * ������λ
	 * */
	private String pk_measdoc;
	

	
	
	

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

	public UFDouble getNstandingcrop() {
		return nstandingcrop;
	}

	public void setNstandingcrop(UFDouble nstandingcrop) {
		this.nstandingcrop = nstandingcrop;
	}

	public UFDouble getNlocked() {
		return nlocked;
	}

	public void setNlocked(UFDouble nlocked) {
		this.nlocked = nlocked;
	}

	public UFDouble getNunlock() {
		return nunlock;
	}

	public void setNunlock(UFDouble nunlock) {
		this.nunlock = nunlock;
	}

	public UFDouble getNlock() {
		return nlock;
	}

	public void setNlock(UFDouble nlock) {
		this.nlock = nlock;
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
