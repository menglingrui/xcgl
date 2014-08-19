package nc.ui.xcgl.salesettle;

import java.awt.Container;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG2;

public class BillSourceDlg extends XCMBillSourceDLG{
	private static final long serialVersionUID = -4908986743054244885L;
	String businessType =null;
	public BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
		this.businessType=businessType;
	}

	@Override
	public String getHeadCondition() {
		String whersql=" xcgl_weighdoc.issaleref1='N' and  isnull(xcgl_weighdoc.dr,0)=0  and xcgl_weighdoc.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'";
		if(businessType!=null&& businessType.length()>0){
			whersql=whersql+" and xcgl_weighdoc.pk_busitype='"+businessType+"'";
		}
		return whersql;
	}
	@Override
	public String getPk_invbasdocName() {
		return "pk_invbasdoc";
	}

	@Override
	public String getPk_invmandocName() {
		return "pk_invmandoc";
	}
	
	public String getBodyCondition() {
		return " XCGL_WEIGHDOC_B.ureserve2='Y' ";
	}

	@Override
	public IControllerBase getUIController() {
		return new nc.ui.xcgl.saleweighdoc.Controller();
	}

	@Override
	protected boolean isHeadCanMultiSelect() {
		
		return true;
	}
}
