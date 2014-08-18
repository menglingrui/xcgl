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
 * @˵�������׸ڿ�ҵ��
 * ��ĩ����У�飬�Ƿ����δ��������
 * 2011-10-20����07:01:39
 * @param body
 * @param corp
 * @return
 * @throws Exception
 */
public static UFBoolean hasNotApprove(EndClosingBVO body,String corp) throws BusinessException{
	//ȡ����� �� ��ʼ����
	UFDate sdate = body.getDstartdate();
	UFDate edate = body.getDenddate();	
	Class[] ParameterTypes = new Class[]{UFDate.class,UFDate.class,String.class};
	Object[] ParameterValues = new Object[]{sdate,edate,corp};
	try {
		LongTimeTask.callRemoteService(PubOtherConst.module,bo, "countNotApprove", ParameterTypes, ParameterValues, 2);
	} catch (Exception e) {
		throw new BusinessException("�����쳣");
	}
	return UFBoolean.TRUE;
}	

/**
 * 
 * @author mlr
 * @˵�������׸ڿ�ҵ��
 * ��ĩ����,ͨ�����ں͹�˾����ʼ��������Ϣ
 * 2011-10-20����07:03:32
 * @param date
 * @param corp
 * @return
 * @throws BusinessException
 */
public static AggregatedValueObject initCloseMonByDate(String year,String corp) throws BusinessException{
	AccountCalendar canl = AccountCalendar.getInstance();//��ȡ��׼��Ʒ���

	//���� ��ǰ���ʱ��
	canl.set(year);
	//����꣨����ڼ�������
	String acc = canl.getYearVO().getPk_accperiod();
	//��Ʒ�������
	String accScheme = canl.getYearVO().getPk_accperiodscheme();
	//��ǰ������
	String periodYear = canl.getYearVO().getPeriodyear();
	//��ǰ�����
	AccperiodmonthVO[] months = canl.getMonthVOsOfCurrentYear();		
	//��ϵͳ������ݽ������·�װ
	EndClosingHVO hvo = new EndClosingHVO();
	hvo.setPk_corp(corp);
	hvo.setPk_accperiod(acc);
	hvo.setPk_accperiodscheme(accScheme);
	hvo.setCyear(periodYear);
	EndClosingBVO[] bvo = null;			
	if(months == null || months.length == 0)
		throw new ValidationException("û�л����");		
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
