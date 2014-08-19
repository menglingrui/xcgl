package nc.vo.xcgl.closeaccount;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * 月末关账
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class CloseAccountHVO extends XCHYHeadSuperVO{
	/**
	 * 主表主键
	 */
	private String pk_closeaccount;
	/**
	 * 年度
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
