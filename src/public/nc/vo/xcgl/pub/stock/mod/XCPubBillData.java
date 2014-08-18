package nc.vo.xcgl.pub.stock.mod;
import nc.bs.zmpub.pub.tool.mod.PubBillData;
/**
 * 公共单据注册类
 * @author mlr
 */
public class XCPubBillData extends PubBillData{

	@Override
	public String getBillDateName() throws Exception {
		return "dbilldate";
	}

	@Override
	public String getBillTypeName() throws Exception {
		return "pk_billtype";
	}

	@Override
	public String getBillTypeStatusName() throws Exception {
		return "vbillstatus";
	}

	@Override
	public String getQuerySql(String whereSql) throws Exception {
		String sql=" select * from  xcgl_general_h h " +
		   " join xcgl_general_b b " +
		   " on h.pk_general_h=b.pk_general_h" +
		   " where isnull(h.dr,0)=0 and isnull(b.dr,0)=0 ";
		if(whereSql!=null){
			sql=sql+" and "+whereSql;
		}
		return sql;
	}

	@Override
	public String[] getQuerySql1(String whereSql) throws Exception {
		return null;
	}
}
