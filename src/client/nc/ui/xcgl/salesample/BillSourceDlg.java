package nc.ui.xcgl.salesample;

import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG2;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;


@SuppressWarnings("serial")
public class BillSourceDlg extends XCMBillSourceDLG2{
	String businessType =null;
	public BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
		this.businessType=businessType;;
	}
	public String getHeadCondition() {
		String whersql=" xcgl_weighdoc.vbillstatus='1'  and xcgl_weighdoc.pk_billtype='XC25' ";
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

	@Override
	public IControllerBase getUIController() {
		return new nc.ui.xcgl.saleweighdoc.Controller();
	}
	@Override
	public String getHeadSpitChar() {
		
		return "x";
	}
	@Override
	public String getRefBillType() {
		
		return PubBillTypeConst.billtype_saleweighdoc_ref;
	}
//	@Override
//	public String getBodyCondition() {
//		String sql=null;
//		sql="  xcgl_weighdoc_b.isref='N' ";
//		return sql;
//	}
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
}
