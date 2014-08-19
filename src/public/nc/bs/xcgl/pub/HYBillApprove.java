package nc.bs.xcgl.pub;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYSuperDMO;
import nc.bs.trade.comstatus.BillApprove;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.xew.pub.BillDateGetter;
public class HYBillApprove extends BillApprove {
	
	public AggregatedValueObject approveHYBill(AggregatedValueObject billVo) throws BusinessException {
		
		AggregatedValueObject retVO = null;
		try {
			retVO = super.approveBill(billVo);
	        valuteDate(retVO);			
		//	retVO=modifyApproveDate(retVO);
			
		} catch (BusinessException re) {
			throw re;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException("������������δ֪�쳣::", e);
		}
		return retVO;
	}
     /**
      * ����У��
      * @author zhf
      * @˵�������׸ڿ�ҵ��
      * 2012-2-17����12:28:01
      * @param retVO
      * @throws Exception
      */
	private void valuteDate(AggregatedValueObject retVO) throws BusinessException{
		//�������� ����С�� �Ƶ�����
		UFDate dapro=(UFDate) retVO.getParentVO().getAttributeValue("dapprovedate");		
		UFDate dmke=(UFDate) retVO.getParentVO().getAttributeValue("dbilldate");
        if(dapro.compareTo(dmke)<0)
    	  throw new BusinessException("�������� ����С�� �������� ");	
//        /*
//         * ���ϵ�(�Լ����������ĵ���)����ʱ 
//			
//	                ��ʽ �������ε��������ⵥ��ʱ  
//			
//	                �Ƶ�����  qu��ǰ����Ա��¼ϵͳ��ҵ������
//           
//	                ��������Ҳ�� 
//			
//	               ���� ҪУ��  ��ǰ��¼�˵ĵ�¼�����Ƿ�   �� ���ϵ����Ƶ����ڵĻ���ڼ���
//         */
//        UFDate dbilldate=dmke;
//		//��ȡ��׼����ڼ䷽��	
//		AccountCalendar canl = AccountCalendar.getInstance();//��ȡ��׼��Ʒ���
//		//��������
//		canl.setDate(dbilldate);
//	    //�����Ƶ�����     ��ȡ��Ӧ�����
//		AccperiodmonthVO ac= canl.getMonthVO();
//		UFDate startdate=ac.getBegindate();
//		UFDate enddate=ac.getEnddate();
//		//��õ�¼����
//	    UFDate logdate=dapro;
//        boolean isAudt=true;
//   		if(logdate.compareTo(startdate)>=0 && logdate.compareTo(enddate)<=0){
//   			isAudt=true;
//   		}else{
//   			isAudt=false;
//   		}	
//        if(isAudt==false){
//        	throw new BusinessException(" �Ƶ����� �� �������ڲ���ͬһ ����ڼ��� ");
//        }		
	}

	/**
	 * �޸���������
	 * 
	 * created by chenliang
	 * at 2007-8-21 ����11:14:32
	 */
	private AggregatedValueObject modifyApproveDate(AggregatedValueObject billVo) throws BusinessException{
		if(billVo==null||billVo.getParentVO()==null)
			return billVo;
		String primaryKey = billVo.getParentVO().getPrimaryKey();
		if(primaryKey==null||primaryKey.equals(""))
			return billVo;
		HYSuperDMO dmo=new HYSuperDMO();
		SuperVO headvo=dmo.queryByPrimaryKey(billVo.getParentVO().getClass(), primaryKey);
		UFDate approveDate=BillDateGetter.getApproveDate();
		headvo.setAttributeValue("dapprovedate", approveDate);
		billVo.setParentVO(headvo);
		dmo.update(headvo,new String[]{"dapprovedate"});
		billVo.setParentVO(dmo.queryByPrimaryKey(billVo.getParentVO().getClass(), primaryKey));
		return billVo;
	}
	@Override
	protected void specialApprove(AggregatedValueObject vo, HYSuperDMO dmo) throws Exception {
		super.specialApprove(vo, dmo);
	}

}
