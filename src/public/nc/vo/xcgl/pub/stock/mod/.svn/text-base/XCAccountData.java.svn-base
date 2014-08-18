package nc.vo.xcgl.pub.stock.mod;
import nc.bs.zmpub.pub.tool.mod.AccountData;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.zmpub.pub.report.IUFTypes;
/**
 * ÏÖ´æÁ¿
 * @author mlr
 */
public class XCAccountData extends AccountData{

	@Override
	public String getClearSql(String whereSql) throws Exception {
		 String csql=" update xcgl_stockonhand  set dr=1 where";
		 if(whereSql!=null&&whereSql.length()>0){
			 csql=csql+whereSql;
		 }
		return csql;
	}

	@Override
	public String getNumClass() throws Exception {
		return BillStockTool.getInstrance().getClassName();
	}

	@Override
	public String[] getSetNumFields() throws Exception {
		return BillStockTool.getInstrance().getChangeNums();
	}

	@Override
	public int[] getSetNumFieldsType() throws Exception {
		if(BillStockTool.getInstrance().getChangeNums()!=null&&BillStockTool.getInstrance().getChangeNums().length>0){
			int[] types=new int[BillStockTool.getInstrance().getChangeNums().length];
			for(int i=0;i<types.length;i++){
				types[i]=IUFTypes.UFD;
			}
			return types;
		}
		return null;
	}

	@Override
	public String[] getUnpk() throws Exception {
		return BillStockTool.getInstrance().getDef_Fields();
	}

	@Override
	public String[] getUnpkName() throws Exception {
		return BillStockTool.getInstrance().getDef_FieldsName();
	}

}
