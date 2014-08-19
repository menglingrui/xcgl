package nc.vo.xcgl.pub.itf;
import nc.itf.zmpub.pub.ISonVOH;
/**
 * 主子孙接口扩展
 * @author mlr
 */
public interface ISonExtendVOH extends ISonVOH{
	/**
	 * 返回孙表实现类
	 * @author mlr
	 * @说明：（鹤岗矿业）
	 * 2011-11-16下午04:56:17
	 * @return
	 * @throws Exception
	 */
  public String getSonClass1();
}
