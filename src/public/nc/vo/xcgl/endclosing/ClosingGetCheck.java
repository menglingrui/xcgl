package nc.vo.xcgl.endclosing;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass;

public class ClosingGetCheck implements IBDGetCheckClass ,Serializable{
	private static final long serialVersionUID = -6367719071038801488L;
	
	public String getCheckClass() {
		return "nc.bs.xcgl.endclosing.EndClosingBO";
	}

}
