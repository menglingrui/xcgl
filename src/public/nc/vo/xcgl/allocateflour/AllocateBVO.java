package nc.vo.xcgl.allocateflour;

import nc.vo.xcgl.pub.bill.PubGeneralBVO;

/**
 * 精粉调拨单表体vo
 * @author Jay
 *
 */
public class AllocateBVO extends PubGeneralBVO {

	private static final long serialVersionUID = 1L;
	/**
	 * 主表主键
	 */
     private String pk_general_h;
	
	/**
	 * 字表主键
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
