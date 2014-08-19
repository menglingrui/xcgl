package nc.ui.xcgl.pub.bill;
import java.awt.Container;
import nc.ui.zmpub.pub.bill.MBillSourceDLG;
public abstract class XCMBillSourceDLG extends MBillSourceDLG{
	private static final long serialVersionUID = -9164274332143479217L;
	public XCMBillSourceDLG(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
	}	
}
