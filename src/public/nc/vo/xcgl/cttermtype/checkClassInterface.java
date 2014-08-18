package nc.vo.xcgl.cttermtype;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass2;

/**
 *类说明：合同条款类型校验
 *@author jay
 *@version 1.0   
 *创建时间：2014-3-20下午03:31:37
 */
public class checkClassInterface implements IBDGetCheckClass2,Serializable{
	private static final long serialVersionUID = 1L;

	public String getUICheckClass() {
		
		return null;
	}

	public String getCheckClass() {
		
		return "nc.bs.xcgl.cttermtype.CttermtypeBO";
	}

}
