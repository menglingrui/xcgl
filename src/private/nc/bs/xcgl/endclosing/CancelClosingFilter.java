package nc.bs.xcgl.endclosing;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.voutils.IFilter;
import nc.vo.xcgl.endclosing.EndClosingBVO;

public class CancelClosingFilter implements IFilter {

	public boolean accept(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof EndClosingBVO))
			return false;
		EndClosingBVO body = (EndClosingBVO)o;
		if(!PuPubVO.getUFBoolean_NullAs(body.getFlag(), UFBoolean.FALSE).booleanValue()){
			return false;
		}
		return true;
	}
}