package nc.vo.xcgl.saleshareout;

import nc.itf.zmpub.pub.ISonVOH;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
/**
 * 销售出库矿区分摊计算聚合VO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
@nc.vo.annotation.AggVoInfo(parentVO = "nc.vo.xcgl.AggSaleShareoutVO")
public class AggSaleShareoutVO extends XCHYBillVO implements ISonVOH{

	public String getSonClass() {
		
		return "nc.vo.xcgl.saleshareout.SaleShareoutBBVO";
	}

}
