package nc.bs.xcgl.latecheckout;
import java.util.ArrayList;
import java.util.List;

import nc.bs.trade.business.IBDBusiCheck;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBDACTION;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.xcgl.latecheckout.CloseMonBodyVO;
import nc.vo.xcgl.latecheckout.CloseMonVO;
import nc.vo.xcgl.latecheckout.MonthAccountVO;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.pub.stock.mod.XCAccountModBOTool;
/**
 * ��洦����
 * @author zhf 
 */
public class CloseMonBO implements IBDBusiCheck{
	private CloseMonDMO dmo = null;
	private BillStockTool stockBO=null;
	public BillStockTool getStockBO(){
		if(stockBO == null){
			stockBO = new BillStockTool();
		}
		return stockBO;
	}
	
	public CloseMonDMO getDMO(){
		if(dmo == null){
			dmo = new CloseMonDMO();
		}
		return dmo;
	}
	
	public void countNotApprove(UFDate sdate,UFDate edate,String corp) throws BusinessException{
		getDMO().countNotApprove(sdate, edate, corp);
	}
	
	private AccountFilter af = null;
	private AccountFilter getAccountFilter(){
		if(af == null){
			af = new AccountFilter();
		}
		return af;
	}
	private CancelAccountFilter caf = null;
	private CancelAccountFilter getCancelAccountFilter(){
		if(caf == null){
			caf = new CancelAccountFilter();
		}
		return caf;
	}
	
	public void check(int intBdAction,AggregatedValueObject billVo,Object userObj)
	throws Exception {
		if(intBdAction == IBDACTION.SAVE){//����ǰ����У��
			if(billVo == null)
				return;
			CloseMonVO bill = (CloseMonVO)ObjectUtils.serializableClone(billVo);
			UFBoolean isaccount = bill.getIsaccount();
			if(isaccount == null)
				throw new BusinessException("�����쳣�����˱�ʶδ��ȷ");			
			if(isaccount.booleanValue()){//��̨У��  �������ڵ�ҵ�񵥾ݱ��������ͨ��
				CloseMonBodyVO[] bodys = bill.getItems();			
				bodys = (CloseMonBodyVO[])VOUtil.filter(bodys, getAccountFilter());				
				bill.setChildrenVO(bodys);				
				if(bodys == null || bodys.length ==0){
					throw new BusinessException("�����쳣����������Ϊ��");
				}
				//���·�����
				VOUtil.ascSort(bodys, CloseMonBodyVO.sort_fields);
				UFDate dstartdate = bodys[0].getDstartdate();
				UFDate denddate = bodys[bodys.length-1].getDenddate();
				countNotApprove(dstartdate, denddate, bill.getHeader().getPk_corp());
			}
			if(isaccount.booleanValue())
				doAccount(bill);	
			else
			    disAccount(bill);
		}
	}
	
	/**
	 * 
	 * @author zhf
	 * @˵�������׸ڿ�ҵ��ȡ������
	 * 2011-10-22����04:20:01
	 * @param bill
	 * @throws BusinessException
	 */
	private void disAccount(CloseMonVO bill) throws BusinessException{
		CloseMonBodyVO[] bodys = bill.getItems();
		
		String pk_corp=PuPubVO.getString_TrimZeroLenAsNull(bill.getParentVO().getAttributeValue("pk_corp"));
		bodys = (CloseMonBodyVO[])VOUtil.filter(bodys, getCancelAccountFilter());
		if(bodys == null || bodys.length == 0)
			return;
		for(CloseMonBodyVO body:bodys){
			getDMO().delMonAccountsForOneMonth(body.getPk_accperiodmonth(),pk_corp);
		}
	}
	
	/**
	 * 
	 * @author zhf
	 * @˵�������׸ڿ�ҵ������
	 * 2011-10-22����04:20:12
	 * @param bill
	 * @throws BusinessException
	 */
	private void doAccount(CloseMonVO bill) throws BusinessException{
		CloseMonBodyVO[] bodys = bill.getItems();
		String pk_corp=PuPubVO.getString_TrimZeroLenAsNull(bill.getParentVO().getAttributeValue("pk_corp"));		
		if(bodys == null || bodys.length == 0)
			return;
		if(bodys.length>1){
			throw new BusinessException("���ܶ����һ�����");
		}
        //���·�����
		VOUtil.ascSort(bodys, CloseMonBodyVO.sort_fields);	
        //���½����½ᴦ��		
		List<MonthAccountVO> lmon = new ArrayList<MonthAccountVO>();//���ת��󱾴ν��˵���������
		for(CloseMonBodyVO body:bodys){
			if(!MonthCloseHelper.isMonthClose(body.getDstartdate(),pk_corp).booleanValue()){
				throw new BusinessException("Ҫ��ת���·�û�й���");
			}
			calForOneMonth(body,lmon,pk_corp);
		}		
		if(lmon.size()>0){
            //���м�������
			BillStockTool.getInstrance().lock(lmon.toArray(new MonthAccountVO[0]));
			//�������
			getDMO().saveMonAccounts(lmon.toArray(new MonthAccountVO[0]));
		} 
	}	
	/**
	 * ���н��˴���
	 * @param body
	 * @param lmon
	 * @param pk_corp
	 * @throws BusinessException
	 */
	private void calForOneMonth(CloseMonBodyVO body,List<MonthAccountVO> lmon,String pk_corp) throws BusinessException{		
		try {
			
			SuperVO[] vos=XCAccountModBOTool.getInstrance().doAccount(body.getPk_accperiodmonth(), pk_corp);
			if(vos==null|| vos.length==0){
				return;
			}
			for(int i=0;i<vos.length;i++){
				lmon.add((MonthAccountVO) vos[i]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}	
	
	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {		
	}
}
