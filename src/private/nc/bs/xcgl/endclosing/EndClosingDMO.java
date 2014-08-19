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
		//���������ڲ�ѯ���е�ҵ�񵥾��Ƿ����δ�����ģ����������ʾ�����嵥�ݣ��������ͣ����ݺ��׳��쳣
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
//			throw new BusinessException("����δ����ͨ���ĵ����޷�����");
		return ;
	}
	
	/**
	 * 
	 * @author zhf
	 * @˵�������׸ڿ�ҵ�����½���ȡָ���µ��½�����
	 * 2011-10-22����04:00:12
	 * @param cmonthid   appWhereSql������Χ
	 * @return
	 * @throws BusinessException
	 */
	public PubStockOnHandVO[] getAccountsForOneMonth(String cmonthid,String appWhereSql) throws BusinessException{
		if(PuPubVO.getString_TrimZeroLenAsNull(cmonthid)==null)
			throw new BusinessException("�������[�·�]Ϊ��");
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
	 * @˵�������׸ڿ�ҵ�������½�����
	 * 2011-10-22����04:00:39
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
	 * @˵�������׸ڿ�ҵ��ɾ���½��һ���µĽ������
	 * 2011-10-24����09:14:28
	 * @param cmonthid
	 * @throws BusinessException
	 */
	public void delMonAccountsForOneMonth(String cmonthid,String pk_corp) throws BusinessException{
		if(PuPubVO.getString_TrimZeroLenAsNull(cmonthid)==null)
			throw new BusinessException("�����쳣���·�Ϊ��");
		String whereSql = "isnull(dr,0) = 0 and cmonthid = '"+cmonthid+"' and pk_corp='"+pk_corp+"'";
		getDao().deleteByClause(MonthAccountVO.class, whereSql);
	}
}
