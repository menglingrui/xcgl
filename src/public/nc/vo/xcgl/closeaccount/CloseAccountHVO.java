package nc.vo.xcgl.closeaccount;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * ��ĩ����
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class CloseAccountHVO extends XCHYHeadSuperVO{
	/**
	 * ��������
	 */
	private String pk_closeaccount;
	/**
	 * ���
	 */
	private String vyear;
	
	
	public String getPk_closeaccount() {
		return pk_closeaccount;
	}
	public void setPk_closeaccount(String pk_closeaccount) {
		this.pk_closeaccount = pk_closeaccount;
	}

	public String getVyear() {
		return vyear;
	}
	public void setVyear(String vyear) {
		this.vyear = vyear;
	}
	@Override
	public String getPKFieldName() {
		return "pk_closeaccount";
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	@Override
	public String getTableName() {
		return "xcgl_closeaccount";
	}
	
}
