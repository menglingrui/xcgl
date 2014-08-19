package nc.bs.xcgl.saleassay;

import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.assay.AssayBVO;
import nc.vo.xcgl.assay.AssayHVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
public class SaleAssayBO {
	/**
	 * 传化验结果
	 * @author ddk
	 * @param valueVo
	 * @param vo
	 * @throws Exception 
	 */
	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO pfvo) throws Exception {
		if(valueVo==null||valueVo.getParentVO()==null){
			return;
		} else {
			HYBillVO tarBill = (HYBillVO) PfUtilTools.runChangeData(PubBillTypeConst.billtype_saleassay,
					PubBillTypeConst.billtype_saleassayres, valueVo, pfvo);
			AssayHVO hvo = (AssayHVO) ObjectUtils.serializableClone(valueVo
					.getParentVO());
			AssayBVO[] bvos = (AssayBVO[]) ObjectUtils.serializableClone(valueVo
					.getChildrenVO());
			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_saleassayres);//设置单据类型
			tarBill.getParentVO().setAttributeValue("vbillno", new HYPubBO().getBillNo(
					PubBillTypeConst.billtype_saleassayres, SQLHelper.getCorpPk(), null, null));   //设置单据号；
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(pfvo.m_operator));//制单人
			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(pfvo.m_currentDate));//制单日期
				String dbilldate = PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
				UFDate curdate=UFDate.getDate(pfvo.m_currentDate);
				UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,pfvo.m_coId),UFBoolean.FALSE);
				if(!isclose.booleanValue()){
				PfUtilWorkFlowVO vo1 = new PfUtilWorkFlowVO();
				List os=(List) new PfUtilBO().processAction("WRITE",
						PubNodeModelConst.NodeModel_saleassayres, dbilldate,
						vo1, tarBill, null);
				//生成审批通过的化验结果
//			    if(os!=null&&os.size()!=0){
//		    	HYBillVO resvo=(HYBillVO) os.get(1);
//		    	if(resvo!=null){
//		    		PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
//		    		//vo2.setTaskInfo(TaskInfo.setTask(new WFTask(WFTask.TYPE_CHECKBILL)));
//		    		new PfUtilBO().processAction("APPROVE", PubNodeModelConst.NodeModel_saleassayres, 
//							dbilldate, vo2, resvo, null);
//		    	}
//		    }
				}
			}
		}
}
