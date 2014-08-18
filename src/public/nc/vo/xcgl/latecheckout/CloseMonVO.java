package nc.vo.xcgl.latecheckout;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;
/**
 * 月末结账
 * @author mlr
 */
public class CloseMonVO extends HYBillVO{
	private static final long serialVersionUID = 1L;
	public static String[] con_billtypes = new String[]{
	
	};	

	private UFBoolean isaccount = UFBoolean.FALSE;//是否结账处理
		
	public UFBoolean getIsaccount() {
		return isaccount;
	}
	public void setIsaccount(UFBoolean isaccount) {
		this.isaccount = isaccount;
	}
	
	public CloseMonHeaderVO getHeader(){
		return (CloseMonHeaderVO)getParentVO();
	}
	public CloseMonBodyVO[] getItems(){
		return (CloseMonBodyVO[])getChildrenVO();
	}
	
	public void validation() throws ValidationException{
		CloseMonHeaderVO head = getHeader();
		if(head == null)
			throw new ValidationException("数据为空");
		head.validataOnSave();
		CloseMonBodyVO[] bodys = getItems();
		if(bodys == null||bodys.length == 0)
			throw new ValidationException("数据为空");
		for(CloseMonBodyVO body:bodys){
			body.validataOnSave();
		}
	}
}
