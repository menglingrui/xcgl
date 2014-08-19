package nc.vo.xcgl.defendrecord;

import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFTime;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 *  �豸ά����¼��ͷVO 
 * @author lxh
 */
@SuppressWarnings("serial")
public class DefendrecordHVO extends XCHYHeadSuperVO{
	
	
	/**
	 * ��������
	 * */
	private String pk_defendrecord_h;
	/**
	 * �ֿ�
	 * */
	private String pk_stordoc;
	/**
	 * ѡ��
	 * */
	private String pk_factory;
	/**
	 * ���
	 * */
	private String pk_classorder;
	/**
	 * ����ʱ��
	 * */
	private UFTime shifttime;
	
	

	public String getPk_defendrecord_h() {
		return pk_defendrecord_h;
	}

	public void setPk_defendrecord_h(String pk_defendrecord_h) {
		this.pk_defendrecord_h = pk_defendrecord_h;
	}

	public String getPk_stordoc() {
		return pk_stordoc;
	}

	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}

	

	public UFTime getShifttime() {
		return shifttime;
	}

	public void setShifttime(UFTime shifttime) {
		this.shifttime = shifttime;
	}

	@Override
	public String getPKFieldName() {
		return "pk_defendrecord_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_defendrecord_h";
	}

}
