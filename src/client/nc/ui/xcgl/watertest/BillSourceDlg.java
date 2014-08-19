package nc.ui.xcgl.watertest;

import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG;
/**
 * ���նԻ���
 * @author Jay
 * @date 2014-1-2 ����02:56:47
 */
public class BillSourceDlg extends XCMBillSourceDLG {
	/**
	 * @date 2014-1-2����02:57:01
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
	 * �޸Ĺ��˲�ѯ������
	 * jay
	 */
	public String getHeadCondition() {
		String sql=null;
		//�޸� ��������.	
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
