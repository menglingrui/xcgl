
package nc.vo.xcgl.produceset;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
/**
 * 创建日期:2013-12-11 16:31:44
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
@nc.vo.annotation.AggVoInfo(parentVO = "nc.vo.xcgl.produceset.AggProduceSetVO")
public class AggProduceSetVO extends XCHYBillVO implements ISonExtendVOH{
	   /**
     * 产出设置
     */
	public String getSonClass1() {
		return "nc.vo.xcgl.produceset.ProductSetBB2VO";
	}
    /**
     * 投入设置
     */
	public String getSonClass() {
		return "nc.vo.xcgl.produceset.ProductSetBB1VO";
	}  
}
