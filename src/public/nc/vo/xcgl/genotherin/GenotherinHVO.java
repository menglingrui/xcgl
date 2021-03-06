package nc.vo.xcgl.genotherin;

import nc.vo.xcgl.pub.bill.PubGeneralHVO;
/**
 * 其他入库表头VO
 * @author lxh
 */
@SuppressWarnings("serial")
public class GenotherinHVO extends PubGeneralHVO{
	/**
	 * 表头主键
	 * */
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
