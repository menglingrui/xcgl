package nc.vo.xcgl.closeaccount;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * 月末关账
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class CloseAccountVO extends XCHYHeadSuperVO{
	/**
	 * 主表主键
	 */
	private String pk_closeaccount;
	/**
	 * 年度
	 */
	private String vyear;
	/**
	 * 月份
	 */
	private String vmonth;
	/**
	 * 是否关账
	 */
	private UFBoolean uclose;
	
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
	public String getVmonth() {
		return vmonth;
	}
	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}
	public UFBoolean getUclose() {
		return uclose;
	}
	public void setUclose(UFBoolean uclose) {
		this.uclose = uclose;
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
