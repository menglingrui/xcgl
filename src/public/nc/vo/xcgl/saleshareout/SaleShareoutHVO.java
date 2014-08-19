package nc.vo.xcgl.saleshareout;

import nc.itf.zmpub.pub.ISonVOH;
import nc.vo.xcgl.pub.bill.PubGeneralHVO;

/**
 * 销售出库矿区分摊计算表头VO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class SaleShareoutHVO extends PubGeneralHVO implements ISonVOH{
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

	public String getSonClass() {
		
		return "nc.vo.xcgl.saleshareout.SaleShareoutBBVO";
	}

}
