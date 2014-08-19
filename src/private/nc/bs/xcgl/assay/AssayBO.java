package nc.bs.xcgl.assay;
import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.assay.AssayHVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
/**
 *类说明：化验单BO
 *化验单审批后生成审批态的化验结果
 *@author jay
 *@version 1.0   
 *创建时间：2014-1-13上午10:17:22
 */
public class AssayBO {
	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		//Map<String,List<PubSampleBBVO>> map=new HashMap<String, List<PubSampleBBVO>>();
		if(valueVo==null||vo==null){
			return;
		}else{
			HYBillVO tarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_assay, PubBillTypeConst.billtype_assayres, valueVo,vo);
			AssayHVO hvo=(AssayHVO) ObjectUtils.serializableClone(valueVo.getParentVO());
			//AssayBVO []bvos=(AssayBVO[]) ObjectUtils.serializableClone(valueVo.getChildrenVO());
			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_assayres);//设置单据类型
			String vbillno=new HYPubBO().getBillNo(PubBillTypeConst.billtype_assayres, SQLHelper.getCorpPk(), null, null);
			tarBill.getParentVO().setAttributeValue("vbillno", vbillno);//设置单据号；
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.COMMIT);
			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期
			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
			UFDate curdate=UFDate.getDate(vo.m_currentDate);
			UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,vo.m_coId),UFBoolean.FALSE);
			if(!isclose.booleanValue()){
			PfUtilWorkFlowVO vo1=new PfUtilWorkFlowVO();
		    List  os= (List) new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_assayres, 
					dbilldate, vo1, tarBill, null);
		    //生成审批通过的化验结果
//		    if(os!=null&&os.size()!=0){
//		    	HYBillVO resvo=(HYBillVO) os.get(1);
//		    	if(resvo!=null){
//		    		PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
//		    		//vo2.setTaskInfo(TaskInfo.setTask(new WFTask(WFTask.TYPE_CHECKBILL)));
//		    		new PfUtilBO().processAction("APPROVE", PubNodeModelConst.NodeModel_assayres, 
//							dbilldate, vo2, resvo, null);
//		    	}
//		    }
		}
			else{
				throw new BusinessException("当前日期已关账");
			}
		}
	}
}
