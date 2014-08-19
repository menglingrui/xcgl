package nc.vo.xcgl.pub.stock.mod;
import java.util.HashMap;
import java.util.Map;

import nc.bs.zmpub.pub.tool.mod.AccountBalanceData;
import nc.bs.zmpub.pub.tool.mod.AccountData;
import nc.bs.zmpub.pub.tool.mod.AccountModBOTool4;
import nc.bs.zmpub.pub.tool.mod.BillData;
import nc.bs.zmpub.pub.tool.mod.PubBillData;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 * 现存量修复入口类
 * 
 * @author mlr
 */
public class XCAccountModBOTool extends AccountModBOTool4 {

	private static XCAccountModBOTool bo = null;

	private XCAccountBalanceData xcbalancedate = new XCAccountBalanceData();

	private XCAccountData xcaccountdate = new XCAccountData();

	private XCPubBillData xcpubdate = new XCPubBillData();

	public static XCAccountModBOTool getInstrance() {
		if (bo == null) {
			bo = new XCAccountModBOTool();
		}
		return bo;
	}

	@Override
	public AccountBalanceData getAccountBalanceData() throws Exception {

		return xcbalancedate;
	}

	@Override
	public AccountData getAccountData() throws Exception {
		return xcaccountdate;
	}

	@Override
	public Map<String, BillData> getBillDataMap() throws Exception {
		Map<String, BillData> map = new HashMap<String, BillData>();
		map.put(PubBillTypeConst.billtype_Generalout, new XC15BillData());
		map.put(PubBillTypeConst.billtype_Generalin, new XC18BillData());
		map.put(PubBillTypeConst.billtype_openingentry, new XC51BillData());
	//	map.put(PubBillTypeConst.billtype_Drugconsume, new XC19BillData());
		map.put(PubBillTypeConst.billtype_concentratein, new XC21BillData());
		map.put(PubBillTypeConst.billtype_saleout, new XC24BillData());
		map.put(PubBillTypeConst.billtype_stocklock, new XC27BillData());
		map.put(PubBillTypeConst.billtype_genotherout, new XC68BillData());
		map.put(PubBillTypeConst.billtype_genotherin, new XC69BillData());
		map.put(PubBillTypeConst.billtype_gravity, new XC78BillData());
		return map;
	}

	@Override
	public PubBillData getPubBillData() throws Exception {
		return xcpubdate;
	}

	public void modifyAccounts(String whereSql1, String whereSql2,
			String whereSql3, String whereSql4, String pk_corp)
			throws Exception {
		super.modifyAccounts(whereSql1, whereSql2, whereSql3, whereSql4,
				pk_corp);
	}

}
