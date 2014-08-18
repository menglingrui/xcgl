package nc.ui.xcgl.endclosing;

import nc.bd.accperiod.AccountCalendar;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.endclosing.EndClosingBVO;
import nc.vo.xcgl.endclosing.EndClosingHVO;
import nc.vo.xcgl.endclosing.EndclosingVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;

public class EndClosingHelper {
	private static String bo = "nc.bs.xcgl.endclosing.EndClosingBO";	
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
public static UFBoolean hasNotApprove(EndClosingBVO body,String corp) throws BusinessException{
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
 * 
 * @author mlr
 * @说明：（鹤岗矿业）
 * 月末结账,通过日期和公司，初始化关账信息
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
	EndClosingHVO hvo = new EndClosingHVO();
	hvo.setPk_corp(corp);
	hvo.setPk_accperiod(acc);
	hvo.setPk_accperiodscheme(accScheme);
	hvo.setCyear(periodYear);
	EndClosingBVO[] bvo = null;			
	if(months == null || months.length == 0)
		throw new ValidationException("没有会计月");		
	bvo = new EndClosingBVO[months.length];
	for (int i = 0; i < months.length; i++) {
		bvo[i] = new EndClosingBVO();
		bvo[i].setVmonth(months[i].getMonth());
		bvo[i].setDstartdate(months[i].getBegindate());
		bvo[i].setDenddate(months[i].getEnddate());
		bvo[i].setPk_accperiodmonth(months[i].getPk_accperiodmonth());
		bvo[i].setPk_accperiodscheme(months[i].getPk_accperiodscheme());
	}
	EndclosingVO accvo = new EndclosingVO(); 
	accvo.setParentVO(hvo);
	accvo.setChildrenVO(bvo);
	return accvo;
}



}
