package nc.bs.xcgl.latecheckout;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.voutils.IFilter;
import nc.vo.xcgl.latecheckout.CloseMonBodyVO;
// 取消结账时数据过滤器 未结账的数据过滤掉
public class CancelAccountFilter implements IFilter {

	public boolean accept(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof CloseMonBodyVO))
			return false;
		CloseMonBodyVO body = (CloseMonBodyVO)o;
		if(!PuPubVO.getUFBoolean_NullAs(body.getFlag(), UFBoolean.FALSE).booleanValue()){
			return false;
		}
		return true;
	}
}
