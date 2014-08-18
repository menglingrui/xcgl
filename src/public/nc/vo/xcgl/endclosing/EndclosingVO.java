package nc.vo.xcgl.endclosing;

import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;

public class EndclosingVO extends HYBillVO{
	private static final long serialVersionUID = 1L;
	public static String[] con_billtypes = new String[]{
	
	};	
	
	private UFBoolean isclosing= UFBoolean.FALSE;//	是否关账

	public UFBoolean getIsclosing() {
		return isclosing;
	}

	public void setIsclosing(UFBoolean isclosing) {
		this.isclosing = isclosing;
	}
	
	public EndClosingHVO getHeader(){
		return (EndClosingHVO)getParentVO();
	}
	public EndClosingBVO[] getItems(){
		return (EndClosingBVO[])getChildrenVO();
	}
	
	public void validation() throws ValidationException{
		EndClosingHVO head = getHeader();
		if(head == null)
			throw new ValidationException("数据为空");
		head.validataOnSave();
		EndClosingBVO[] bodys = getItems();
		if(bodys == null||bodys.length == 0)
			throw new ValidationException("数据为空");
		for(EndClosingBVO body:bodys){
			body.validataOnSave();
		}
	}
	
}
