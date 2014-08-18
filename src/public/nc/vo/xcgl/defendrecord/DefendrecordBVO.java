package nc.vo.xcgl.defendrecord;

import nc.vo.pub.lang.UFDateTime;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 *  �豸ά����¼����VO 
 * @author lxh
 */
@SuppressWarnings("serial")
public class DefendrecordBVO extends XCHYChildSuperVO{

	/**
	 * ��������
	 * */
	private String pk_defendrecord_b;
	/**
	 * ��������
	 * */
	private String pk_defendrecord_h;
	/**
	 * �豸����
	 * */
	private String pk_equipment;
	/**
	 * ά����
	 * */
	private String pk_defendman;
	/**
	 * ά��ʱ��
	 * */
	private UFDateTime defendtime;
	/**
	 * �´�ά��ʱ��
	 * */
	private UFDateTime nextdefendtime;
	
	
	
	
	public String getPk_defendrecord_b() {
		return pk_defendrecord_b;
	}

	public void setPk_defendrecord_b(String pk_defendrecord_b) {
		this.pk_defendrecord_b = pk_defendrecord_b;
	}

	public String getPk_defendrecord_h() {
		return pk_defendrecord_h;
	}

	public void setPk_defendrecord_h(String pk_defendrecord_h) {
		this.pk_defendrecord_h = pk_defendrecord_h;
	}

	public String getPk_equipment() {
		return pk_equipment;
	}

	public void setPk_equipment(String pk_equipment) {
		this.pk_equipment = pk_equipment;
	}

	public String getPk_defendman() {
		return pk_defendman;
	}

	public void setPk_defendman(String pk_defendman) {
		this.pk_defendman = pk_defendman;
	}

	
	
	public UFDateTime getDefendtime() {
		return defendtime;
	}

	public void setDefendtime(UFDateTime defendtime) {
		this.defendtime = defendtime;
	}

	public UFDateTime getNextdefendtime() {
		return nextdefendtime;
	}

	public void setNextdefendtime(UFDateTime nextdefendtime) {
		this.nextdefendtime = nextdefendtime;
	}

	@Override
	public String getPKFieldName() {
		return "pk_defendrecord_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_defendrecord_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_defendrecord_b";
	}

}
