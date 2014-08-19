package nc.ui.xcgl.watertest;

import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG;
/**
 * 参照对话框
 * @author Jay
 * @date 2014-1-2 下午02:56:47
 */
public class BillSourceDlg extends XCMBillSourceDLG {
	/**
	 * @date 2014-1-2下午02:57:01
	 */
	private static final long serialVersionUID = 3886184900807493717L;
	public BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
				businessType, templateId, currentBillType, parent);

	}

	/**
	 * 修改过滤查询条件；
	 * jay
	 */
	public String getHeadCondition() {
		String sql=null;
		//修改 过滤条件.	
		return sql=" xcgl_general_h.vbillstatus='1' and xcgl_general_h.isref='N'";
	}          
	
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
	@Override
	public IControllerBase getUIController() {
	
		return new nc.ui.xcgl.genprcessout.Controller();
	}

	@Override
	public String getPk_invbasdocName() {
	
		return null;
	}

	@Override
	public String getPk_invmandocName() {
	
		return null;
	}


}
