package nc.vo.xcgl.pub.itf;

import java.util.List;

import nc.itf.zmpub.pub.ISonVO;
/**
 * ������ӿ���չ
 * @author mlr
 *
 */
public interface ISonExtendVO extends ISonVO{
	  /**
	   * ���������Ϣ
	   * @return
	   */
	  public abstract List getSonVOS1();
	  /**
	   * �����к�����
	   * @return
	   */
	  public abstract String getRowNumName1(); 
	  /**
	   * ���������Ϣ
	   * @param list
	   */
	  public abstract void  setSonVOS1(List list);
}
