package nc.vo.xcgl.ctexpset;

import java.io.Serializable;

import nc.vo.trade.pub.IBDGetCheckClass2;
/**
 * ��ͬ��������У��ӿ�
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
