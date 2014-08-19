package nc.vo.xcgl.pub.stock.mod;

import java.util.Collection;
import java.util.Iterator;

import nc.bs.zmpub.pub.tool.mod.AccountBalanceData;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.pub.helper.QueryHelper;
/**
 * ½á´æ×¢²áÀà
 * @author mlr
 */
public class XCAccountBalanceData extends AccountBalanceData{

	@Override
	public String getAcMonID() throws Exception {
		return "cmonthid";
	}

	@Override
	public String getBegindateName() throws Exception {
		return "dstartdate";
	}

	@Override
	public String getChangeClass() throws Exception {
		return "nc.bs.pf.changedir.CHGXC37TOXC32";
	}

	@Override
	public String getDyearmonthName() throws Exception {
		return "dyearmonth";
	}

	@Override
	public String getEnddateName() throws Exception {
		return "denddate";
	}

	@Override
	public String getLastAccountMonthQuerySql(String pk_corp) throws Exception {
		String sql = " select cmonthid from (select cmonthid from xcgl_monthaccount where isnull(dr,0) = 0 and pk_corp='"+pk_corp+"' order by dyearmonth desc)" +
		" where rownum < 2";
		return sql;
	}

	@Override
	public String getMonthAccountClass() throws Exception {
		return "nc.vo.xcgl.latecheckout.MonthAccountVO";
	}

	@Override
	public SuperVO[] loadPeridData(String whereSql4) throws Exception {	
		String whereSql = " isnull(dr,0) = 0 and dyearmonth = '00-00' ";	
		if(whereSql4!=null){
			whereSql=whereSql+" and "+whereSql4;
		}
		Collection<SuperVO> c = (Collection<SuperVO>)QueryHelper.retrieveByClause(Class.forName(getMonthAccountClass()), whereSql);
		SuperVO[] months=null;
	    if( c!=null && c.size()!=0){
		 months = new SuperVO[c.size()];
		 Iterator<SuperVO> it = c.iterator();
		 int index = 0;
		 while(it.hasNext()){
			months[index] = it.next();
			index ++;
		 }				
	  }
	  SuperVO[] mos=XCAccountModBOTool.getInstrance().combinAccounts(months);
	  return mos;
	}

//	@Override
//	public boolean isloadPeridData(String pk_corp) throws BusinessException {
//		String sql=" select count(*) from xcgl_closemon h join xcgl_closemon_b b on h.pk_closemon=b.pk_closemon" +
//				" where isnull(h.dr,0)=0 and isnull(b.dr,0)=0 and h.pk_corp='"+pk_corp+"'" +
//				"  and b.isaccount='Y'";
//		Integer count=PuPubVO.getInteger_NullAs(QueryHelper.executeQuery(sql, new ColumnProcessor()), -1);
//		if(count>0){
//			return false;
//		}
//		return true;
//	}

}
