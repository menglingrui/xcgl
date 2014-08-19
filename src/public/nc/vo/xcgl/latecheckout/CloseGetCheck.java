package nc.vo.xcgl.latecheckout;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass;

public class CloseGetCheck implements IBDGetCheckClass ,Serializable{
	private static final long serialVersionUID = -6367719071038801488L;
	
	public String getCheckClass() {
		return "nc.bs.xcgl.latecheckout.CloseMonBO";
	}

}
