package nc.bs.xcgl.weigh;

import java.util.ArrayList;
import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.VOStatus;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.genorein.GenOreInBVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.weighdoc.WeighdocBVO;
import nc.vo.xcgl.weighdoc.WeighdocHVO;

public class WeighBO {
	/**
	 * 传到矿区入库
	 * @param billvo
	 * @param pfvo
	 * @throws Exception
	 */
	public void sendMessage(AggregatedValueObject billvo,PfParameterVO pfvo) throws Exception{
		if(billvo==null || billvo.getParentVO()==null) {
			
		}else{
			WeighdocBVO []bvos=(WeighdocBVO[]) ObjectUtils.serializableClone(billvo.getChildrenVO());
			WeighdocHVO hvo=(WeighdocHVO) ObjectUtils.serializableClone(billvo.getParentVO());
			
			HYBillVO tarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_weighdoc, 
					PubBillTypeConst.billtype_Generalin, billvo,pfvo);
			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_Generalin);//设置单据类型
			tarBill.getParentVO().setAttributeValue("vbillno", new HYPubBO().getBillNo(
					PubBillTypeConst.billtype_Generalin, SQLHelper.getCorpPk(), null, null));   //设置单据号；
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			tarBill.getParentVO().setAttributeValue("voperatorid",pfvo.m_operator);//制单人
			tarBill.getParentVO().setAttributeValue("dmakedate",new UFDate(pfvo.m_currentDate));//制单日期
			//查找收发类别中 矿区入库 
			List list= new ArrayList();
			String sql="select b.pk_resetype from xcgl_resetype b where isnull(dr,0)=0 and b.vtypename='矿区入库'  and b.isclose='N';";
			Object []o=null;  
			list=(List) QueryHelper.executeQuery(sql, new ArrayListProcessor());
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					o=(Object[]) list.get(i);
					tarBill.getParentVO().setAttributeValue("pk_resetype", o[0].toString().trim());
				}
			}
			CircularlyAccessibleValueObject []bvos1=tarBill.getChildrenVO();
			if(bvos1.length>0&&bvos1!=null){
				for(int i=0;i<bvos1.length;i++){
					GenOreInBVO vo=(GenOreInBVO) bvos1[i];
					String pk_invmandoc=vo.getPk_invmandoc();
						bvos1[i].setAttributeValue("pk_father", pk_invmandoc);
						String father=vo.getPk_father();
						bvos1[i].setStatus(VOStatus.NEW);
				}
			}
			boolean flag=MonthCloseHelper.isMonthClose(new UFDate(pfvo.m_currentDate), hvo.getPk_corp()).booleanValue();
			if(flag==true){
				throw new BusinessException("当前日期已关账！不能完成该操作");
			}
//			MonthCloseHelper.isMonthClose(pfvo.m_currentDate, hvo.getPk_corp());
//			String m=pfvo.m_currentDate;
//			String s=pfvo.m_operator;
			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
			
			PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
			new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_General_in, 
					dbilldate, vo2, tarBill, null);
		}
	}
}
