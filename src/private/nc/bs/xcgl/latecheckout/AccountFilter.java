package nc.bs.xcgl.latecheckout;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.voutils.IFilter;
import nc.vo.xcgl.latecheckout.CloseMonBodyVO;

//月结时数据过滤器 已结账的月份过滤掉

public class AccountFilter implements IFilter {

	
	public boolean accept(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return false;
		if(!(o instanceof CloseMonBodyVO))
			return false;
		CloseMonBodyVO body = (CloseMonBodyVO)o;
		if(PuPubVO.getUFBoolean_NullAs(body.getFlag(), UFBoolean.FALSE).booleanValue()){
			return true;
		}
		return false;
	}

}
