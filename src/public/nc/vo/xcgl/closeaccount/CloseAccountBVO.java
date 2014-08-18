package nc.vo.xcgl.closeaccount;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;

public class CloseAccountBVO extends XCHYChildSuperVO{
	private static final long serialVersionUID = 1L;
	/**
	 * 月份
	 */
	private String vmonth;
	/**
	 * 是否关账
	 */
	private UFBoolean uclose;
	
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
		return "pk_closeaccount_b";
	}
	@Override
	public String getParentPKFieldName() {
		return "pk_closeaccount";
	}
	@Override
	public String getTableName() {
		return "xcgl_closeaccount_b";
	}
}
