package nc.vo.xcgl.gendrugconsume;

import nc.vo.xcgl.pub.bill.PubGeneralHVO;
/**
 * 药剂消耗岗位记录表头VO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class GenDrugConsumeHVO extends PubGeneralHVO{
	/**
	 * 主表主键
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
