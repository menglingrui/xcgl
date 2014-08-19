package nc.vo.xcgl.ctexpset;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass2;
/**
 * 合同费用数据校验接口
 * @author ddk
 *
 */
public class checkClassInterface implements IBDGetCheckClass2,Serializable{

	private static final long serialVersionUID = -4071131691047496949L;

	public String getUICheckClass() {
		return null;
	}

	public String getCheckClass() {
		return "nc.bs.xcgl.ctexpset.CtExpsetBO";
	}

}
