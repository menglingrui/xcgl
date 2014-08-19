package nc.ui.xcgl.pub.bill;

import java.awt.Container;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.pf.IinitQueryData2;
import nc.ui.trade.query.HYQueryDLG;

public class XCBillQueryDlg extends HYQueryDLG implements IinitQueryData2{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4287320734726666065L;
	static Container parent = null;
	static UIPanel normalPnl = null;
	static String pk_corp =  ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
	static String moduleCode =  null;
	static String operator = ClientEnvironment.getInstance().getUser().getPrimaryKey();
	static String busiType =  null;
	static String nodeKey =  null;



	public XCBillQueryDlg(Container parent, UIPanel normalPnl, String pk_corp,
			String moduleCode, String operator, String busiType, String nodeKey) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType, nodeKey);
		
	}


	public XCBillQueryDlg(Container parent, UIPanel normalPnl, String pk_corp,
			String moduleCode, String operator, String busiType) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType);
		
	}




	public void initData(String pkCorp, String operator, String funNode,
			String businessType, String currentBillType, String sourceBilltype,
			String nodeKey, Object userObj) throws Exception {
			}


}
