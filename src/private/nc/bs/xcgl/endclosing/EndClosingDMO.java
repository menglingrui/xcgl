package nc.bs.xcgl.endclosing;

import java.util.Collection;
import java.util.Iterator;

import nc.bs.dao.BaseDAO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.latecheckout.MonthAccountVO;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;

public class EndClosingDMO {
	public EndClosingDMO(){
		super();
	}
	public EndClosingDMO(BaseDAO dao){
		super();
		this.dao = dao;
	}
	private BaseDAO dao = null;
	public BaseDAO getDao(){
		if(dao == null){
			dao = new BaseDAO();
		}
		return dao;
	}
	
	public void countNotApprove(UFDate sdate,UFDate edate,String corp) throws BusinessException{
		//按单据日期查询所有的业务单据是否存在未审批的，如果存在提示到具体单据，单据类型，单据号抛出异常
		//
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select count(0) count ");
//		sql.append(" from ca_bill ");
//		sql.append(" where isnull(dr,0)=0 ");
//		sql.append(" and pk_corp = "+ corp);
//		sql.append(" and (dbilldate between '"+sdate+"' and '"+edate+"') "); 
//		sql.append(" and vbillstatus <> "+IBillStatus.CHECKPASS+" ");
//		sql.append(" and pk_billtype in "+XcPubTool.getSubSql(CloseMonVO.con_billtypes)+"");
//		Object o = getDao().executeQuery(sql.toString(), ResultSetProcessorTool.COLUMNPROCESSOR);
//		int i = PuPubVO.getInteger_NullAs(o, 0);
//		if(i > 0)
//			throw new BusinessException("存在未审批通过的单据无法结账");
		return ;
	}
	
	/**
	 * 
	 * @author zhf
	 * @说明：（鹤岗矿业）从月结表获取指定月的月结数据
	 * 2011-10-22下午04:00:12
	 * @param cmonthid   appWhereSql条件范围
	 * @return
	 * @throws BusinessException
	 */
	public PubStockOnHandVO[] getAccountsForOneMonth(String cmonthid,String appWhereSql) throws BusinessException{
		if(PuPubVO.getString_TrimZeroLenAsNull(cmonthid)==null)
			throw new BusinessException("输入参数[月份]为空");
		String whereSql = null;
		if(PuPubVO.getString_TrimZeroLenAsNull(appWhereSql)!=null)
			whereSql = "isnull(dr,0) = 0 and cmonthid = '"+cmonthid+"'"+" and "+appWhereSql;
		else
			whereSql = "isnull(dr,0) = 0 and cmonthid = '"+cmonthid+"'";
		Collection<MonthAccountVO> c = (Collection<MonthAccountVO>)getDao().retrieveByClause(MonthAccountVO.class, whereSql);
		if(c == null || c.size() ==0)
			return null;
		MonthAccountVO[] months = new MonthAccountVO[c.size()];
		Iterator<MonthAccountVO> it = c.iterator();
		int index = 0;
		while(it.hasNext()){
			months[index] = it.next();
			index ++;
		}
		return months;
	}
	
	/**
	 * 
	 * @author zhf
	 * @说明：（鹤岗矿业）保存月结数据
	 * 2011-10-22下午04:00:39
	 * @param accounts
	 * @throws BusinessException
	 */
	public void saveMonAccounts(MonthAccountVO[] accounts) throws BusinessException{
		if(accounts == null || accounts.length == 0)
			return;
		getDao().insertVOArray(accounts);
	}
	
	/**
	 * 
	 * @author zhf
	 * @说明：（鹤岗矿业）删除月结表一个月的结存数据
	 * 2011-10-24上午09:14:28
	 * @param cmonthid
	 * @throws BusinessException
	 */
	public void delMonAccountsForOneMonth(String cmonthid,String pk_corp) throws BusinessException{
		if(PuPubVO.getString_TrimZeroLenAsNull(cmonthid)==null)
			throw new BusinessException("数据异常，月份为空");
		String whereSql = "isnull(dr,0) = 0 and cmonthid = '"+cmonthid+"' and pk_corp='"+pk_corp+"'";
		getDao().deleteByClause(MonthAccountVO.class, whereSql);
	}
}
