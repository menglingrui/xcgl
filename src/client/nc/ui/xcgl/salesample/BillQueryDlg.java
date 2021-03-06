package nc.ui.xcgl.salesample;

import java.awt.Container;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.pf.IinitQueryData2;
import nc.ui.trade.query.HYQueryDLG;
import nc.vo.xcgl.pub.consts.PubNodeCodeConst;

@SuppressWarnings("serial")
public class BillQueryDlg extends HYQueryDLG implements IinitQueryData2{
	
	static Container parent = null;
	static UIPanel normalPnl = null;
	static String pk_corp =  ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
	static String moduleCode =  null;
	static String operator = ClientEnvironment.getInstance().getUser().getPrimaryKey();
	static String busiType =  null;
	static String nodeKey =  null;

	public  BillQueryDlg(Container parent) {
		super(parent,null,pk_corp,PubNodeCodeConst.NodeCode_saleweighdoc,operator,null,null);
	}

	public BillQueryDlg(Container parent, UIPanel normalPnl, String pk_corp,
			String moduleCode, String operator, String busiType) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType);
	}
	
	public BillQueryDlg(Container parent, UIPanel normalPnl, String pk_corp,
			String moduleCode, String operator, String busiType, String nodeKey) {
		super(parent, normalPnl, pk_corp, moduleCode, operator, busiType, nodeKey);
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
