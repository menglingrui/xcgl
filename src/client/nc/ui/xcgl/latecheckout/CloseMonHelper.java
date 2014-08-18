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
 * ��ĩ���˹�����
 * @author mlr
 *
 */
public class CloseMonHelper {
	private static String bo = "nc.bs.xcgl.latecheckout.CloseMonBO";	
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
	public static UFBoolean hasNotApprove(CloseMonBodyVO body,String corp) throws BusinessException{
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
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * ��ĩ���ˣ�ͨ����ݺ͹�˾ ���ؽ�����Ϣ
	 * 2011-10-20����07:02:37
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
					"����ִ��...", 1, "nc.impl.uif.pub.UifServiceImp", null,
					"queryBillVOByCondition", ParameterTypes, ParameterValues);
			bills = (AggregatedValueObject[]) o;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		
		//���ر�����Ϊ�գ����ȡϵͳ�����ݣ�=1Ϊ��������Ψһ�ۺ�vo��>1��������ظ�����
		if(null != bills && bills.length > 0){
			if(bills.length>1)
				throw new ValidationException("�����쳣");
			//��õ�ǰ�����µľۺ�vo��0���Լ���ͷvo
			AggregatedValueObject bill = bills[0];
			CloseMonHeaderVO headvo = (CloseMonHeaderVO) bill.getParentVO();
			//ͨ����ͷvo������ֵ����ȡ����vo[]
			CloseMonBodyVO[] bodyvos = null;
			bodyvos = (CloseMonBodyVO[]) HYPubBO_Client.queryByCondition(CloseMonBodyVO.class, " pk_closemon = '" + headvo.getPrimaryKey()+"'");
			//У����岻Ϊ��
			if(bodyvos == null || bodyvos.length == 0)
				throw new ValidationException("�����쳣");
			//����vo[]���� �ۺ�vo
			bill.setChildrenVO(bodyvos);		
			return bill;
		}		
		return null;
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
		CloseMonHeaderVO hvo = new CloseMonHeaderVO();
		hvo.setPk_corp(corp);
		hvo.setPk_accperiod(acc);
		hvo.setPk_accperiodscheme(accScheme);
		hvo.setCyear(periodYear);
		CloseMonBodyVO[] bvo = null;			
		if(months == null || months.length == 0)
			throw new ValidationException("û�л����");		
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
	 * @˵�������׸ڿ�ҵ��
	 * ���˹������ݣ�У�鵥�����ڵĽ���״̬
	 * 2011-10-20����07:01:01
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
