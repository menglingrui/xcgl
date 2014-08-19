package nc.vo.xcgl.monstocount;

import nc.vo.xcgl.pub.bill.PubGeneralBVO;




/**
 *类说明：月末库存盘点表体vo
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-18上午10:20:11
 */
public class MonStoCountBVO extends PubGeneralBVO{

	private static final long serialVersionUID = 1L;
	private String pk_general_h;
	
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
