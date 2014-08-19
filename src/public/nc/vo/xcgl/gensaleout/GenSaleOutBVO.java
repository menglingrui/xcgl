package nc.vo.xcgl.gensaleout;
import nc.vo.xcgl.pub.bill.PubGeneralBVO;
/**
 *类说明：销售出库表体vo
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-16下午03:58:22
 */
public class GenSaleOutBVO extends PubGeneralBVO{
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
