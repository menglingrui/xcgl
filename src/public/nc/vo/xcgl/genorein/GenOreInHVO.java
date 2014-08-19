package nc.vo.xcgl.genorein;

import nc.vo.xcgl.pub.bill.PubGeneralHVO;
/**
 * 矿区入库表头VO
 * @author ddk
 *
 */
public class GenOreInHVO extends PubGeneralHVO{
	private static final long serialVersionUID = 1L;
	/**
	 *主键 
	 */
	private String pk_general_h;

	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
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
