package nc.vo.xcgl.saleminenanteil;

import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
/**
 * 销售按矿区比例分摊计算表头VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class SaleminenanteilHVO extends XCHYHeadSuperVO{
	
	/**
	 * 主表主键
	 * */
	private String pk_saleminenanteil_h;
	/**
	 * 选厂
	 */
	private String pk_factory;
	/**
	 * 操作员
	 */
	private String pk_operator;
	/**
	 * 月份
	 */
	private String vmonth;
	
	

	public String getPk_saleminenanteil_h() {
		return pk_saleminenanteil_h;
	}

	public void setPk_saleminenanteil_h(String pk_saleminenanteil_h) {
		this.pk_saleminenanteil_h = pk_saleminenanteil_h;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_operator() {
		return pk_operator;
	}

	public void setPk_operator(String pk_operator) {
		this.pk_operator = pk_operator;
	}

	public String getVmonth() {
		return vmonth;
	}

	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}

	@Override
	public String getPKFieldName() {
		return "pk_saleminenanteil_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_saleminenanteil_h";
	}

}
