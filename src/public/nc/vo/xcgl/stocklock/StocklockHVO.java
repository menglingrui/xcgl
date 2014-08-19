package nc.vo.xcgl.stocklock;

import nc.vo.xcgl.pub.bill.PubGeneralHVO;
/**
 * 库存锁定解锁表头VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class StocklockHVO extends PubGeneralHVO{
	
	/**
	 * 表头主键
	 * */
	private String pk_general_h;
	/**
	 * 操作员
	 */
	private String pk_operator;
	
	
	
	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
	}

	public String getPk_operator() {
		return pk_operator;
	}

	public void setPk_operator(String pk_operator) {
		this.pk_operator = pk_operator;
	}

	@Override
	public String getPKFieldName() {
		return "pk_general_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_general_h";
	}
}
