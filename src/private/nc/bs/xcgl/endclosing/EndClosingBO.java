package nc.bs.xcgl.endclosing;

import java.util.List;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.xcgl.latecheckout.CloseMonHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBDACTION;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.xcgl.endclosing.EndClosingBVO;
import nc.vo.xcgl.endclosing.EndClosingHVO;
import nc.vo.xcgl.endclosing.EndclosingVO;
import nc.vo.xcgl.genconcentratein.GenConcentrateinHVO;
import nc.vo.xcgl.gendrugconsume.GenDrugConsumeHVO;
import nc.vo.xcgl.genorein.GenOreInHVO;
import nc.vo.xcgl.genotherin.GenotherinHVO;
import nc.vo.xcgl.genotherout.GenotheroutHVO;
import nc.vo.xcgl.genprcessout.GenPrcOutHVO;
import nc.vo.xcgl.gensaleout.GenSaleOutHVO;
import nc.vo.xcgl.openingentry.OpeningEntryHVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.stocklock.StocklockHVO;

public class EndClosingBO implements IBDBusiCheck{
	private EndClosingDMO dmo = null;
	private BillStockTool stockBO=null;
	public BillStockTool getStockBO(){
		if(stockBO == null){
			stockBO = new BillStockTool();
		}
		return stockBO;
	}
	
	public EndClosingDMO getDMO(){
		if(dmo == null){
			dmo = new EndClosingDMO();
		}
		return dmo;
	}
//	
	public void countNotApprove(UFDate sdate,UFDate edate,String corp) throws BusinessException{
		getDMO().countNotApprove(sdate, edate, corp);
	}
	
	private ClosingFilter af = null;
	private ClosingFilter getAccountFilter(){
		if(af == null){
			af = new ClosingFilter();
		}
		return af;
	}
	private CancelClosingFilter caf = null;
	private CancelClosingFilter getCancelAccountFilter(){
		if(caf == null){
			caf = new CancelClosingFilter();
		}
		return caf;
	}
	
	public void check(int intBdAction,AggregatedValueObject billVo,Object userObj)
	throws Exception {
		if(intBdAction == IBDACTION.SAVE){//����ǰ����У��
			if(billVo == null)
				return;
			EndclosingVO bill = (EndclosingVO)ObjectUtils.serializableClone(billVo);
			UFBoolean isclosing = bill.getIsclosing();
			if(isclosing == null)
				throw new BusinessException("�����쳣�����˱�ʶδ��ȷ");			
			if(isclosing.booleanValue()){//��̨У��  �������ڵ�ҵ�񵥾ݱ��������ͨ��
				EndClosingBVO[] bodys = bill.getItems();			
				bodys = (EndClosingBVO[])VOUtil.filter(bodys, getAccountFilter());				
				bill.setChildrenVO(bodys);				
				if(bodys == null || bodys.length ==0){
					throw new BusinessException("�����쳣����������Ϊ��");
				}
				//���·�����
				VOUtil.ascSort(bodys, EndClosingBVO.sort_fields);
				UFDate dstartdate = bodys[0].getDstartdate();
				UFDate denddate = bodys[bodys.length-1].getDenddate();
				countNotApprove(dstartdate, denddate, bill.getHeader().getPk_corp());
			}
			if(isclosing.booleanValue())
				doClosing(bill);	
			else
			    disClosing(bill);
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
	private void disClosing(EndclosingVO bill) throws BusinessException{
		EndClosingBVO[] bodys = bill.getItems();
		
		String pk_corp=PuPubVO.getString_TrimZeroLenAsNull(bill.getParentVO().getAttributeValue("pk_corp"));
		bodys = (EndClosingBVO[])VOUtil.filter(bodys, getCancelAccountFilter());
		if(bodys == null || bodys.length == 0)
			return;
		for(EndClosingBVO body:bodys){
			if(CloseMonHelper.isMonClose(body.getDstartdate(),pk_corp).booleanValue()){
				throw new BusinessException("����ΪҪȡ��������ȡ������");
			}
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
	private void doClosing(EndclosingVO bill) throws BusinessException{
		EndClosingBVO[] bodys = bill.getItems();
		String pk_corp=PuPubVO.getString_TrimZeroLenAsNull(bill.getParentVO().getAttributeValue("pk_corp"));		
		if(bodys == null || bodys.length == 0)
			return;
		if(bodys.length>1){
			throw new BusinessException("���ܶ����һ�����");
		}
        //���·�����
		VOUtil.ascSort(bodys, EndClosingBVO.sort_fields);	
        //���½����¹��ʴ���		
		for(EndClosingBVO body:bodys){
		    UFDate a=body.getDstartdate();
			UFDate b=body.getDenddate();
			List<GenConcentrateinHVO> hvos=( List<GenConcentrateinHVO> )XCZmPubDAO.getDAO().retrieveByClause(GenConcentrateinHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" 
					+" and pk_billtype ='"+PubBillTypeConst.billtype_concentratein+"' and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'"
					+" and vbillstatus !=1");
			for(int i=0;i<hvos.size();i++){
						String billtype=hvos.get(i).getPk_billtype();
	            		String billno=hvos.get(i).getVbillno();
	            		throw new BusinessException("������ⵥ,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<OpeningEntryHVO> hvos0=( List<OpeningEntryHVO> )XCZmPubDAO.getDAO().retrieveByClause(OpeningEntryHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" 
					+" and pk_billtype ='"+PubBillTypeConst.billtype_openingentry+"' and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'"
					+" and vbillstatus !=1");
			for(int i=0;i<hvos0.size();i++){
						String billtype=hvos0.get(i).getPk_billtype();
	            		String billno=hvos0.get(i).getVbillno();
	            		throw new BusinessException("�ڳ�¼��,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<GenDrugConsumeHVO> hvos1=( List<GenDrugConsumeHVO> )XCZmPubDAO.getDAO().retrieveByClause(GenDrugConsumeHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_Drugconsume+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos1.size();i++){
						String billtype=hvos1.get(i).getPk_billtype();
	            		String billno=hvos1.get(i).getVbillno();
	            		throw new BusinessException("ҩ�����ĳ���,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<GenOreInHVO > hvos2=( List<GenOreInHVO > )XCZmPubDAO.getDAO().retrieveByClause(GenOreInHVO .class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_Generalin+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos2.size();i++){
						String billtype=hvos2.get(i).getPk_billtype();
	            		String billno=hvos2.get(i).getVbillno();
	            		throw new BusinessException("�������,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<GenPrcOutHVO> hvos3=( List<GenPrcOutHVO> )XCZmPubDAO.getDAO().retrieveByClause(GenPrcOutHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_Generalout+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos3.size();i++){
						String billtype=hvos3.get(i).getPk_billtype();
	            		String billno=hvos3.get(i).getVbillno();
	            		throw new BusinessException("ԭ��ӹ�����,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<GenSaleOutHVO> hvos4=( List<GenSaleOutHVO> )XCZmPubDAO.getDAO().retrieveByClause(GenSaleOutHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_saleout+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos4.size();i++){
						String billtype=hvos4.get(i).getPk_billtype();
	            		String billno=hvos4.get(i).getVbillno();
	            		throw new BusinessException("���۳���,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<GenotheroutHVO> hvos5=( List<GenotheroutHVO> )XCZmPubDAO.getDAO().retrieveByClause(GenotheroutHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_genotherout+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos5.size();i++){
						String billtype=hvos5.get(i).getPk_billtype();
	            		String billno=hvos4.get(i).getVbillno();
	            		throw new BusinessException("��������,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			List<GenotherinHVO> hvos6=( List<GenotherinHVO> )XCZmPubDAO.getDAO().retrieveByClause(GenotherinHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_genotherin+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos6.size();i++){
						String billtype=hvos6.get(i).getPk_billtype();
	            		String billno=hvos6.get(i).getVbillno();
	            		throw new BusinessException("�������,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }
			
			List<StocklockHVO> hvos7=( List<StocklockHVO> )XCZmPubDAO.getDAO().retrieveByClause(StocklockHVO.class, "isnull(dr,0)=0 and pk_corp ='"+pk_corp+"'" +
					" and pk_billtype ='"+PubBillTypeConst.billtype_stocklock+"'and dbilldate >= '"+a.toString()+"' and dbilldate <= '"+b.toString()+"'" 
					+" and vbillstatus !=1");
			for(int i=0;i<hvos7.size();i++){
						String billtype=hvos7.get(i).getPk_billtype();
	            		String billno=hvos7.get(i).getVbillno();
	            		throw new BusinessException("�������,��������Ϊ"+billtype+",���ݺ�Ϊ"+billno+"δ����ͨ��");
	        }		
		}		
	}	
	
	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {		
	}
	
	public static AggregatedValueObject getCloseMonByYear(String accountyear,String corp) throws BusinessException{
		AggregatedValueObject[] bills = null;
		bills = HYPubBO_Client.queryBillVOByCondition(new String[]{"nc.vo.xcgl.endclosing.EndclosingVO","nc.vo.xcgl.endclosing.EndClosingHVO"},
				" cyear = '"+accountyear+"' and pk_corp = '"+corp+"'");
		//���ر�����Ϊ�գ����ȡϵͳ�����ݣ�=1Ϊ��������Ψһ�ۺ�vo��>1��������ظ�����
		if(null != bills && bills.length > 0){
			if(bills.length>1)
				throw new ValidationException("�����쳣");
			//��õ�ǰ�����µľۺ�vo��0���Լ���ͷvo
			AggregatedValueObject bill = bills[0];
			EndClosingHVO headvo = (EndClosingHVO) bill.getParentVO();
			//ͨ����ͷvo������ֵ����ȡ����vo[]
			EndClosingBVO[] bodyvos = null;
			bodyvos = (EndClosingBVO[]) HYPubBO_Client.queryByCondition(EndClosingBVO.class, " pk_endclosing_h = '" + headvo.getPrimaryKey()+"'");
			//У����岻Ϊ��
			if(bodyvos == null || bodyvos.length == 0)
				throw new ValidationException("�����쳣");
			//����vo[]���� �ۺ�vo
			bill.setChildrenVO(bodyvos);		
			return bill;
		}		
		return null;
	}
}
