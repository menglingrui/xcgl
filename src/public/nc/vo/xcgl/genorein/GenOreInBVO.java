package nc.vo.xcgl.genorein;

import nc.vo.xcgl.pub.bill.PubGeneralBVO;
/**
 * 矿区入库表体VO
 * @author ddk
 *
 */
public class GenOreInBVO extends PubGeneralBVO{
	private static final long serialVersionUID = 1L;
	/**
	 *主表主键 
	 */
	private String pk_general_h;
	/**
	 *子表主键 
	 */
	private String pk_general_b;
	
	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
	}

	public String getPk_general_b() {
		return pk_general_b;
	}

	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}

	@Override
	public String getPKFieldName() {
		return "pk_general_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_general_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_general_b";
	}

}
