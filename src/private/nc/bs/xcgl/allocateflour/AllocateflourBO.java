package nc.bs.xcgl.allocateflour;

import java.util.ArrayList;
import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.genotherout.GenotheroutBVO;
import nc.vo.xcgl.genotherout.GenotheroutHVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * ���۵�������̨bo
 * @author Jay
 * ����ʱ�Ȱ��յ�����Ϣ�����������ⵥ
 * �ڰ��յ�����Ϣ����������ⵥ
 */
public class AllocateflourBO {
	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		if(valueVo==null||vo==null){
			return;
		}else{
			//�������ⵥ ȡ������Ϣ
			HYBillVO outtarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_allocateofflour, PubBillTypeConst.billtype_genotherout, valueVo,vo);
			GenotheroutHVO hvo=(GenotheroutHVO) outtarBill.getParentVO();
			outtarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_genotherout);//���õ�������
			outtarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			outtarBill.getParentVO().setAttributeValue("itype",1);
			outtarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//�Ƶ���
			outtarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//�Ƶ�����
			outtarBill.getParentVO().setAttributeValue("vbillno", 
					new HYPubBO().getBillNo(PubBillTypeConst.billtype_genotherout, SQLHelper.getCorpPk(), null, null));//���õ��ݺţ�
			String factory=hvo.getPk_factory();//ѡ��
			String stordoc=hvo.getPk_stordoc();//�ֿ�
			String invman=PuPubVO.getString_TrimZeroLenAsNull(outtarBill.getChildrenVO()[0].getAttributeValue("pk_invmandoc"));//�����������
			UFDouble num=PuPubVO.getUFDouble_NullAsZero(outtarBill.getChildrenVO()[0].getAttributeValue("nwetweight"));//����
			//��ѯ�ִ�����ȡ�ø�ѡ�����ֿ⣬���۵��ִ���
			  PubStockOnHandVO onhandvo=new PubStockOnHandVO();
		        onhandvo.setPk_corp(hvo.getPk_corp());
		        onhandvo.setPk_factory(factory);
		        onhandvo.setPk_father(invman);
		        onhandvo.setPk_stordoc(stordoc);
		        List<SuperVO[]>  onlist=BillStockTool.getInstrance().queryStockDetail(new PubStockOnHandVO[]{onhandvo});
		        String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
		        PfUtilWorkFlowVO vo1=new PfUtilWorkFlowVO();
		        if(onlist!=null&&onlist.size()!=0){
		        	List<GenotheroutBVO> tarlist=new ArrayList<GenotheroutBVO>();
		        	 SuperVO[] onvos=onlist.get(0);
		        	 if(onvos!=null&&onvos.length!=0){
		        		 //�ҵ�����vo�;�������
		        		 List<SuperVO> flourlist = getFlourVOByInvId(onvos, invman);
		        		 if(flourlist!=null&&flourlist.size()!=0){
		        			SuperVO flourvo=flourlist.get(0);
		        			UFDouble flourmout= PuPubVO.getUFDouble_NullAsZero( flourvo.getAttributeValue("nnum"));
		        			for(int i=0;i<onvos.length;i++){
		        				//����һ��������Դ��Ϣ���������ⵥvo
			        			GenotheroutBVO bvo=(GenotheroutBVO) ObjectUtils.serializableClone(outtarBill.getChildrenVO()[0]);
			        			bvo.setCrowno(PuPubVO.getString_TrimZeroLenAsNull((i+1)*10));
			        			bvo.setPk_invmandoc(PuPubVO.getString_TrimZeroLenAsNull(onvos[i].getAttributeValue("pk_invmandoc")));
			        			bvo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(onvos[i].getAttributeValue("pk_invbasdoc")));
			        			bvo.setPk_measdoc(getmeaspkBybas(PuPubVO.getString_TrimZeroLenAsNull(onvos[i].getAttributeValue("pk_invbasdoc"))));
			        			UFDouble nnum=PuPubVO.getUFDouble_NullAsZero(onvos[i].getAttributeValue("nnum"));
			        			bvo.setNwetweight(nnum.multiply(num.div(flourmout)));
			        			bvo.setNdryweight(nnum.multiply(num.div(flourmout)));
			        			tarlist.add(bvo);
		        			}
		        			//�������кŸ�ֵ
		        			List<GenotheroutBVO> destlist = setVcrowno(tarlist,invman);
		        			outtarBill.setChildrenVO(destlist.toArray(new GenotheroutBVO[0]));
		        			 new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherout, 
			       	   					dbilldate, vo1, outtarBill, null);
		        		 }
		        		 else{
		        			 throw new BusinessException("�ĵ���ѡ��,�����ֿ���û�о���!");
		        		 }
		        	 }
		        	 else{
		       			 new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherout, 
		       	   					dbilldate, vo1, outtarBill, null);
		        	 }
		        }else{
		        	//�ִ�����ά�ȣ�ѡ�����ֿ⣬�����Ϊ��
		           throw new BusinessException("�õ���ѡ��,�����ֿ���û�иþ���!");
		        }
//			//������ⵥ ȡ������Ϣ
//			HYBillVO intarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_allocateofflour, PubBillTypeConst.billtype_genotherin, valueVo,vo);
//			intarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_genotherin);//���õ�������
//			intarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
//			intarBill.getParentVO().setAttributeValue("itype",1);
//			intarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//�Ƶ���
//			intarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//�Ƶ�����
//			intarBill.getParentVO().setAttributeValue("vbillno", 
//					new HYPubBO().getBillNo(PubBillTypeConst.billtype_genotherin, SQLHelper.getCorpPk(), null, null));//���õ��ݺţ�
//			PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
//			 new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherin, 
//	   					dbilldate, vo2, intarBill, null);
			
		}
	}
	public List<GenotheroutBVO> setVcrowno(List<GenotheroutBVO> tarlist, String invman) {
		if(tarlist==null||tarlist.size()==0||invman==null){
			return null;
		}
		List<SuperVO> list = getFlourVOByInvId(tarlist.toArray(new GenotheroutBVO[0]), invman);
		if(list!=null&&list.size()!=0){
			GenotheroutBVO bvo=(GenotheroutBVO) list.get(0);
			String crowno=bvo.getCrowno();
			for(int i=0;i<tarlist.size();i++){
				GenotheroutBVO tarvo=tarlist.get(i);
				if(!crowno.equalsIgnoreCase(tarvo.getCrowno())){
					tarvo.setVcrowno(crowno);
				}
			}
		}
		return tarlist;
	}
	public List<SuperVO> getFlourVOByInvId(
			SuperVO[] dbvos, String pk_invmandoc) {

		if(dbvos==null || dbvos.length==0 || pk_invmandoc==null){
			return null;
		}else{
			List<SuperVO>  list=new ArrayList<SuperVO>();
			for(int i=0;i<dbvos.length;i++){
				if(pk_invmandoc.equals(PuPubVO.getString_TrimZeroLenAsNull(dbvos[i].getAttributeValue("pk_invmandoc")))){
					list.add(dbvos[i]);
				}
			}
			return list;
		}
	}
	/**
	 * ���ݴ������������ü�����λ����
	 * @param protype
	 * @return
	 * @throws BusinessException 
	 */
	public  String getmeaspkBybas(String invbads) throws BusinessException {
		return PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("pk_measdoc->getColValue(bd_invbasdoc,pk_measdoc,pk_invbasdoc ,pk_invbasdoc );", 
				new String[]{"pk_invbasdoc"}, new String[]{invbads}));
	}

}
