package nc.vo.xcgl.pub.stock.mod;

import nc.bs.zmpub.pub.tool.mod.BillData;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.stock.BillStockTool;

/**
 * 原矿加工出库
 * @author ddk
 *
 */
public class XC15BillData extends BillData{

	@Override
	public boolean[] getBillStatus() throws Exception {
		return new boolean[]{true,true};
	}

	@Override
	public String getChangeClass() throws Exception {
		return "nc.bs.pf.changedir.CHGXC15TOXC37MOD";
	}

	@Override
	public UFBoolean[] getIsChangeNum() throws Exception {
		if(BillStockTool.getInstrance().getTypetosetnum()==null){
			return null;
		}else{
			return BillStockTool.getInstrance().getTypetosetnum().get(PubBillTypeConst.billtype_Generalout);
		}
	}

}
