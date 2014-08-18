package nc.vo.xcgl.teamgroup;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass2;

public class checkClassInterface implements IBDGetCheckClass2,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getCheckClass() {
		return "nc.bs.xcgl.teamgroup.TeamgroupBO";
	}

	public String getUICheckClass() {
		return null;
	}


}
