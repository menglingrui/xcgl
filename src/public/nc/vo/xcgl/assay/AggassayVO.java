package nc.vo.xcgl.assay;

import nc.itf.zmpub.pub.ISonVOH;
import nc.vo.xcgl.pub.bill.XCHYBillVO;

/**
 * ���鵥�ۺ�VO
 * @author yangtao
 * @date 2013-12-16����17:28:59
 */
public class AggassayVO extends XCHYBillVO implements ISonVOH{

	private static final long serialVersionUID = -880864146136858158L;

	public String getSonClass() {
		
		return "nc.vo.xcgl.assay.AssayBBVO";
	}

}
