package nc.vo.xcgl.cttype;
import java.io.Serializable;
import nc.vo.trade.pub.IBDGetCheckClass2;

/**
 *��˵������ͬ����У��
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2014-3-20����04:00:47
 */
public class checkClassInterface implements IBDGetCheckClass2,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getUICheckClass() {
		
		return null;
	}

	public String getCheckClass() {
		
		return "nc.bs.xcgl.cttype.CttypeBO";
	}

}
