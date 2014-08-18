package nc.vo.xcgl.runrecord;

import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
/**
 * �豸���м�¼����VO
 * @author lxh
 */
public class RunrecordBVO extends XCHYChildSuperVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ���
	 */
	private String vspec;
	/**
	 * �ͺ�
	 */
	private String vtype;
	/**
	 * �豸����
	 */
	private String pk_equipment;
	/**
	 * ʱ��
	 */
	private UFDouble nduration;
	/**
	 * ����ʱ��
	 */
	private UFDateTime boottime;
	/**
	 * �ػ�ʱ��
	 */
	private UFDateTime offtime;
	
	private String pk_runrecord_h;
	private String pk_runrecord_b;
	

	public String getVspec() {
		return vspec;
	}

	public void setVspec(String vspec) {
		this.vspec = vspec;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getPk_equipment() {
		return pk_equipment;
	}

	public void setPk_equipment(String pk_equipment) {
		this.pk_equipment = pk_equipment;
	}

	public UFDouble getNduration() {
		return nduration;
	}

	public void setNduration(UFDouble nduration) {
		this.nduration = nduration;
	}

	

	public UFDateTime getBoottime() {
		return boottime;
	}

	public void setBoottime(UFDateTime boottime) {
		this.boottime = boottime;
	}

	public UFDateTime getOfftime() {
		return offtime;
	}

	public void setOfftime(UFDateTime offtime) {
		this.offtime = offtime;
	}

	public String getPk_runrecord_h() {
		return pk_runrecord_h;
	}

	public void setPk_runrecord_h(String pk_runrecord_h) {
		this.pk_runrecord_h = pk_runrecord_h;
	}

	public String getPk_runrecord_b() {
		return pk_runrecord_b;
	}

	public void setPk_runrecord_b(String pk_runrecord_b) {
		this.pk_runrecord_b = pk_runrecord_b;
	}

	@Override
	public String getPKFieldName() {
		return "pk_runrecord_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_runrecord_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_runrecord_b";
	}
	
}
