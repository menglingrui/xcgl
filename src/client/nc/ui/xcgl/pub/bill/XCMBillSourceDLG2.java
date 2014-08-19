package nc.ui.xcgl.pub.bill;
import java.awt.Container;
import nc.ui.zmpub.pub.bill.MBillSourceDLG2;
public abstract class XCMBillSourceDLG2 extends MBillSourceDLG2{
	private static final long serialVersionUID = -8654522468241173052L;
	public XCMBillSourceDLG2(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
	}	
}
