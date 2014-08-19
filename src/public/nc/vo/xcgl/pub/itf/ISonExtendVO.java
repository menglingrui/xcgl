package nc.vo.xcgl.pub.itf;

import java.util.List;

import nc.itf.zmpub.pub.ISonVO;
/**
 * 主子孙接口扩展
 * @author mlr
 *
 */
public interface ISonExtendVO extends ISonVO{
	  /**
	   * 返回孙表信息
	   * @return
	   */
	  public abstract List getSonVOS1();
	  /**
	   * 返回行号名字
	   * @return
	   */
	  public abstract String getRowNumName1(); 
	  /**
	   * 设置孙表信息
	   * @param list
	   */
	  public abstract void  setSonVOS1(List list);
}
