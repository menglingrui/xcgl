package nc.bs.xcgl.sample;

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
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.xcgl.sample.SampleHVO;
/**
 * 送样单审批之后推式生产自由态的化验单
 * @author fealty
 *
 */
public class SampleBO {
	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) {
		if(valueVo==null||valueVo.getParentVO()==null){
			return;
		} else {
			try {
				HYBillVO tarBill = (HYBillVO) PfUtilTools.runChangeData(PubBillTypeConst.billtype_sample,
						PubBillTypeConst.billtype_assay, valueVo, vo);
				SampleHVO hvo = (SampleHVO) ObjectUtils.serializableClone(valueVo
					.getParentVO());
//			AssayBVO[] bvos = (AssayBVO[]) ObjectUtils.serializableClone(valueVo
//					.getChildrenVO());
			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_assay);//设置单据类型
			tarBill.getParentVO().setAttributeValue("vbillno", new HYPubBO().getBillNo(
					PubBillTypeConst.billtype_assay, SQLHelper.getCorpPk(), null, null));   //设置单据号；
//			tarBill.getParentVO().setAttributeValue("pk_corp", SQLHelper.getCorpPk());   //设置公司
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期
				String dbilldate = PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
				UFDate curdate=UFDate.getDate(vo.m_currentDate);
				UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,vo.m_coId),UFBoolean.FALSE);
				if(!isclose.booleanValue()){
				PfUtilWorkFlowVO vo2 = new PfUtilWorkFlowVO();
				new PfUtilBO().processAction("WRITE",
						PubNodeModelConst.NodeModel_assay, dbilldate,
						vo2, tarBill, null);
				}
				else{
					throw new BusinessException("当前单据日期已关帐!");
				}
			}
		        catch (Exception e) {
			e.printStackTrace();
		}
				}
	}
}
			
		

