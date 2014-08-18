package nc.bs.xcgl.soct;

import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.xcgl.soct.ExAggSoctVO;
/**
 * 销售合同远程调用
 * @author ddk
 *
 */
public class Update {
	/**
	 * 点击打开按钮时
	 * @param billvo
	 * @throws Exception
	 */
	public void setIsclose(ExAggSoctVO billvo) throws Exception{
		if(billvo!=null){
			CircularlyAccessibleValueObject hvo=billvo.getParentVO();
			CircularlyAccessibleValueObject[] bvos = billvo.getChildrenVO();
			String sql="update xcgl_soct set isclose='N' where vbillno='"+hvo.getAttributeValue("vbillno")+"'";
			ZmPubDAO.getDAO().executeUpdate(sql);
		}
	}
	/**
	 * 点击关闭按钮时
	 * @param billvo
	 * @throws Exception
	 */
	public void setIsclose1(ExAggSoctVO billvo) throws Exception{
		if(billvo!=null){
			CircularlyAccessibleValueObject hvo=billvo.getParentVO();
			CircularlyAccessibleValueObject[] bvos = billvo.getChildrenVO();
			String sql="update xcgl_soct set isclose='Y' where vbillno='"+hvo.getAttributeValue("vbillno")+"'";
			ZmPubDAO.getDAO().executeUpdate(sql);
		}
	}
}
