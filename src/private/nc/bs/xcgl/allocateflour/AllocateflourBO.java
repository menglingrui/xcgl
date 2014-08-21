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
 * 精粉调拨单后台bo
 * @author Jay
 * 审批时先按照调出信息生成其它出库单
 * 在按照调入信息生成其它入库单
 */
public class AllocateflourBO {
	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		if(valueVo==null||vo==null){
			return;
		}else{
			//其它出库单 取调出信息
			HYBillVO outtarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_allocateofflour, PubBillTypeConst.billtype_genotherout, valueVo,vo);
			GenotheroutHVO hvo=(GenotheroutHVO) outtarBill.getParentVO();
			outtarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_genotherout);//设置单据类型
			outtarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			outtarBill.getParentVO().setAttributeValue("itype",1);
			outtarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
			outtarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期
			outtarBill.getParentVO().setAttributeValue("vbillno", 
					new HYPubBO().getBillNo(PubBillTypeConst.billtype_genotherout, SQLHelper.getCorpPk(), null, null));//设置单据号；
			String factory=hvo.getPk_factory();//选厂
			String stordoc=hvo.getPk_stordoc();//仓库
			String invman=PuPubVO.getString_TrimZeroLenAsNull(outtarBill.getChildrenVO()[0].getAttributeValue("pk_invmandoc"));//存货管理主键
			UFDouble num=PuPubVO.getUFDouble_NullAsZero(outtarBill.getChildrenVO()[0].getAttributeValue("nwetweight"));//数量
			//查询现存量，取得该选厂，仓库，精粉的现存量
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
		        		 //找到精粉vo和精粉数量
		        		 List<SuperVO> flourlist = getFlourVOByInvId(onvos, invman);
		        		 if(flourlist!=null&&flourlist.size()!=0){
		        			SuperVO flourvo=flourlist.get(0);
		        			UFDouble flourmout= PuPubVO.getUFDouble_NullAsZero( flourvo.getAttributeValue("nnum"));
		        			for(int i=0;i<onvos.length;i++){
		        				//复制一个包含来源信息的其它出库单vo
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
		        			//给关联行号赋值
		        			List<GenotheroutBVO> destlist = setVcrowno(tarlist,invman);
		        			outtarBill.setChildrenVO(destlist.toArray(new GenotheroutBVO[0]));
		        			 new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherout, 
			       	   					dbilldate, vo1, outtarBill, null);
		        		 }
		        		 else{
		        			 throw new BusinessException("改调出选厂,调出仓库下没有精粉!");
		        		 }
		        	 }
		        	 else{
		       			 new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherout, 
		       	   					dbilldate, vo1, outtarBill, null);
		        	 }
		        }else{
		        	//现存量该维度（选厂，仓库，存货）为空
		           throw new BusinessException("该调出选厂,调出仓库下没有该精粉!");
		        }
//			//其它入库单 取调入信息
//			HYBillVO intarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_allocateofflour, PubBillTypeConst.billtype_genotherin, valueVo,vo);
//			intarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_genotherin);//设置单据类型
//			intarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
//			intarBill.getParentVO().setAttributeValue("itype",1);
//			intarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
//			intarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期
//			intarBill.getParentVO().setAttributeValue("vbillno", 
//					new HYPubBO().getBillNo(PubBillTypeConst.billtype_genotherin, SQLHelper.getCorpPk(), null, null));//设置单据号；
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
	 * 根据存货基本主键获得计量单位主键
	 * @param protype
	 * @return
	 * @throws BusinessException 
	 */
	public  String getmeaspkBybas(String invbads) throws BusinessException {
		return PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("pk_measdoc->getColValue(bd_invbasdoc,pk_measdoc,pk_invbasdoc ,pk_invbasdoc );", 
				new String[]{"pk_invbasdoc"}, new String[]{invbads}));
	}

}
