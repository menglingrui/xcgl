package nc.vo.xcgl.gensaleout;
import nc.vo.xcgl.pub.bill.PubGeneralHVO;
/**
 *��˵�������۳����ͷvo
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-16����03:58:01
 */
public class GenSaleOutHVO extends PubGeneralHVO{
	private static final long serialVersionUID = 1L;
	/**
	 *���� 
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
