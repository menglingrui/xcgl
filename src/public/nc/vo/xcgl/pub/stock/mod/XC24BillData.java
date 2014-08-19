package nc.vo.xcgl.pub.stock.mod;
import nc.bs.zmpub.pub.tool.mod.BillData;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
/**
 * ÏúÊÛ³ö¿â
 * @author mlr
 */
public class XC24BillData extends BillData{
	@Override
	public boolean[] getBillStatus() throws Exception {
		return new boolean[]{true,true};
	}

	@Override
	public String getChangeClass() throws Exception {
		return "nc.bs.pf.changedir.CHGXC24TOXC37MOD";
	}

	@Override
	public UFBoolean[] getIsChangeNum() throws Exception {
		if(BillStockTool.getInstrance().getTypetosetnum()==null){
			return null;
		}else{
			return BillStockTool.getInstrance().getTypetosetnum().get(PubBillTypeConst.billtype_saleout);
		}
	}
}
