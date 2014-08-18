package nc.ui.xcgl.saleassay;

import java.awt.Container;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIPanel;
import nc.ui.xcgl.pub.bill.XCBillQueryDlg;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeCodeConst;

public class BillQueryDlg extends XCBillQueryDlg{
	

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

	
	public BillQueryDlg(Container parent) {
		super(parent,null,pk_corp,PubNodeCodeConst.NodeCode_salesample,operator,null,PubBillTypeConst.billtype_salesample);
	}

	public BillQueryDlg(Container parent, UIPanel normalPnl, String pk_corp,
			String moduleCode, String operator, String busiType, String nodeKey) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType, nodeKey);
		
	}


	public BillQueryDlg(Container parent, UIPanel normalPnl, String pk_corp,
			String moduleCode, String operator, String busiType) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType);
		
	}


	
	@Override
	public String getWhereSQL() {
		return super.getWhereSQL();
	}

	public void initData(String pkCorp, String operator, String funNode,
			String businessType, String currentBillType, String sourceBilltype,
			String nodeKey, Object userObj) throws Exception {
	}


}
