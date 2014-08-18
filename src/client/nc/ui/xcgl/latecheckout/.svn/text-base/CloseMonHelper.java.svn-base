package nc.ui.xcgl.latecheckout;

import nc.bd.accperiod.AccountCalendar;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.latecheckout.CloseMonBodyVO;
import nc.vo.xcgl.latecheckout.CloseMonHeaderVO;
import nc.vo.xcgl.latecheckout.CloseMonVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;
/**
 * 月末结账工具类
 * @author mlr
 *
 */
public class CloseMonHelper {
	private static String bo = "nc.bs.xcgl.latecheckout.CloseMonBO";	
	/**
	 * @author mlr
	 * @说明：（鹤岗矿业）
	 * 月末结账校验，是否存在未审批单据
	 * 2011-10-20下午07:01:39
	 * @param body
	 * @param corp
	 * @return
	 * @throws Exception
	 */
	public static UFBoolean hasNotApprove(CloseMonBodyVO body,String corp) throws BusinessException{
		//取会计月 的 起始日期
		UFDate sdate = body.getDstartdate();
		UFDate edate = body.getDenddate();	
		Class[] ParameterTypes = new Class[]{UFDate.class,UFDate.class,String.class};
		Object[] ParameterValues = new Object[]{sdate,edate,corp};
		try {
			LongTimeTask.callRemoteService(PubOtherConst.module,bo, "countNotApprove", ParameterTypes, ParameterValues, 2);
		} catch (Exception e) {
			throw new BusinessException("数据异常");
		}
		return UFBoolean.TRUE;
	}	
	/**
	 * @author mlr
	 * @说明：（鹤岗矿业）
	 * 月末结账，通过年份和公司 返回结账信息
	 * 2011-10-20下午07:02:37
	 * @param accountyear
	 * @param corp
	 * @return
	 * @throws BusinessException
	 */
	public static AggregatedValueObject getCloseMonByYear(String accountyear,String corp) throws BusinessException{
		AggregatedValueObject[] bills = null;
		bills = HYPubBO_Client.queryBillVOByCondition(new String[]{"nc.vo.xcgl.latecheckout.CloseMonVO","nc.vo.xcgl.latecheckout.CloseMonHeaderVO"},
				" cyear = '"+accountyear+"' and pk_corp = '"+corp+"'");
		
		
		try {
			Class[] ParameterTypes = new Class[] { Object.class,String.class };
			Object[] ParameterValues = new Object[] { 
					new String[]{"nc.vo.xcgl.latecheckout.CloseMonVO","nc.vo.xcgl.latecheckout.CloseMonHeaderVO"},
			
					" cyear = '"+accountyear+"' and pk_corp = '"+corp+"'"};
			Object o = LongTimeTask.calllongTimeService("xcgl", null,
					"正在执行...", 1, "nc.impl.uif.pub.UifServiceImp", null,
					"queryBillVOByCondition", ParameterTypes, ParameterValues);
			bills = (AggregatedValueObject[]) o;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		
		//返回表数据为空，则读取系统表数据，=1为符合条件唯一聚合vo，>1出现年份重复错误
		if(null != bills && bills.length > 0){
			if(bills.length>1)
				throw new ValidationException("数据异常");
			//获得当前日期下的聚合vo【0】以及表头vo
			AggregatedValueObject bill = bills[0];
			CloseMonHeaderVO headvo = (CloseMonHeaderVO) bill.getParentVO();
			//通过表头vo的主键值，获取表体vo[]
			CloseMonBodyVO[] bodyvos = null;
			bodyvos = (CloseMonBodyVO[]) HYPubBO_Client.queryByCondition(CloseMonBodyVO.class, " pk_closemon = '" + headvo.getPrimaryKey()+"'");
			//校验表体不为空
			if(bodyvos == null || bodyvos.length == 0)
				throw new ValidationException("数据异常");
			//表体vo[]存入 聚合vo
			bill.setChildrenVO(bodyvos);		
			return bill;
		}		
		return null;
	}
	
	/**
	 * 
	 * @author mlr
	 * @说明：（鹤岗矿业）
	 * 月末结账,通过日期和公司，初始化结账信息
	 * 2011-10-20下午07:03:32
	 * @param date
	 * @param corp
	 * @return
	 * @throws BusinessException
	 */
	public static AggregatedValueObject initCloseMonByDate(String year,String corp) throws BusinessException{
		AccountCalendar canl = AccountCalendar.getInstance();//获取基准会计方案

		//设置 当前会计时间
		canl.set(year);
		//会计年（会计期间主键）
		String acc = canl.getYearVO().getPk_accperiod();
		//会计方案主键
		String accScheme = canl.getYearVO().getPk_accperiodscheme();
		//当前会计年度
		String periodYear = canl.getYearVO().getPeriodyear();
		//当前会计月
		AccperiodmonthVO[] months = canl.getMonthVOsOfCurrentYear();		
		//对系统会计数据进行重新封装
		CloseMonHeaderVO hvo = new CloseMonHeaderVO();
		hvo.setPk_corp(corp);
		hvo.setPk_accperiod(acc);
		hvo.setPk_accperiodscheme(accScheme);
		hvo.setCyear(periodYear);
		CloseMonBodyVO[] bvo = null;			
		if(months == null || months.length == 0)
			throw new ValidationException("没有会计月");		
		bvo = new CloseMonBodyVO[months.length];
		for (int i = 0; i < months.length; i++) {
			bvo[i] = new CloseMonBodyVO();
			bvo[i].setVmonth(months[i].getMonth());
			bvo[i].setDstartdate(months[i].getBegindate());
			bvo[i].setDenddate(months[i].getEnddate());
			bvo[i].setPk_accperiodmonth(months[i].getPk_accperiodmonth());
			bvo[i].setPk_accperiodscheme(months[i].getPk_accperiodscheme());
		}
		CloseMonVO accvo = new CloseMonVO(); 
		accvo.setParentVO(hvo);
		accvo.setChildrenVO(bvo);
		return accvo;
	}
	
	/**
	 * @author mlr
	 * @说明：（鹤岗矿业）
	 * 结账关联单据，校验单据日期的结账状态
	 * 2011-10-20下午07:01:01
	 * @param date
	 * @param corp
	 * @return
	 * @throws BusinessException
	 */
	public static UFBoolean isMonClose(UFDate date,String corp) throws BusinessException{
		String year = PuPubVO.getString_TrimZeroLenAsNull(date.getYear());
		CloseMonVO vo = (CloseMonVO) getCloseMonByYear(year,corp);
		if(vo == null){
			return UFBoolean.FALSE;
		}
		CloseMonBodyVO[] bodys = vo.getItems();
		for (CloseMonBodyVO body : bodys) {
			UFDate sdate = body.getDstartdate();
			UFDate edate = body.getDenddate();
			if(date.compareTo(edate)<0 && date.compareTo(sdate)>0 ){
				if(PuPubVO.getUFBoolean_NullAs( body.getIsaccount(),UFBoolean.FALSE).booleanValue()==true){
					return UFBoolean.TRUE;
				}
			}
		}
		return UFBoolean.FALSE;		
	}
}
