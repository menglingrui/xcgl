package nc.bs.xcgl.invent;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;

public class AdjustBO {

	public void InventoryAdj(AggregatedValueObject billvo, PfParameterVO vo) throws Exception {	
		CircularlyAccessibleValueObject hvo=billvo.getParentVO();		
		UFDate dbilldate=(UFDate) hvo.getAttributeValue("dbilldate");	   
		int year=dbilldate.getYear();
		String month = dbilldate.toString().substring(5, 7);
		
		String sql="select pk_endclosing_h from xcgl_endclosing_h " +
				" where cyear='"+year+"' and pk_corp='"+hvo.getAttributeValue("pk_corp")+"'" +
				" and isnull(dr,0)=0";
		String pk=PuPubVO.getString_TrimZeroLenAsNull(
				ZmPubDAO.getDAO().executeQuery(sql, new ColumnProcessor()));
		
		
		String sql1=" select isclosing from xcgl_endclosing_b where pk_endclosing_h ='" +
		pk+"' and vmonth='"+month+"' and isnull(dr,0)=0";
		String isclosing=PuPubVO.getString_TrimZeroLenAsNull(
				ZmPubDAO.getDAO().executeQuery(sql1, new ColumnProcessor()));
		
		
		String sql11="select pk_closemon from xcgl_closemon " +
		" where cyear='"+year+"' and pk_corp='"+hvo.getAttributeValue("pk_corp")+"'" +
		" and isnull(dr,0)=0";	
		String pk1=PuPubVO.getString_TrimZeroLenAsNull(
				ZmPubDAO.getDAO().executeQuery(sql11, new ColumnProcessor()));
		
		
		String sql12=" select isaccount from xcgl_closemon_b where pk_closemon ='" +
		pk1+"' and vmonth='"+month+"' and isnull(dr,0)=0";
		String isclosing1=PuPubVO.getString_TrimZeroLenAsNull(
				ZmPubDAO.getDAO().executeQuery(sql12, new ColumnProcessor()));
		
		if(isclosing ==null || isclosing.trim().equals("N")){
			throw new Exception("当前月份没有关账");
		}
		
		if(isclosing1==null || isclosing1.trim().equals("N")){
			throw new Exception("当前月份没有关账");
		}
		
		CircularlyAccessibleValueObject[] bvos1 =billvo.getChildrenVO();			
		String stordoc = (String) hvo.getAttributeValue("pk_stordoc");// 仓库
		String factory = (String) hvo.getAttributeValue("pk_factory");// 选场		
		CircularlyAccessibleValueObject[][] bbvos=SplitBillVOs.getSplitVOs(bvos1, new String[]{"pk_father"});			
	    for(int j=0;j<bbvos.length;j++){
			HYBillVO newVO=new HYBillVO();
			newVO.setParentVO(hvo);
			CircularlyAccessibleValueObject[] bvos =bbvos[j];				
			for(int i=0;i<bvos.length;i++){			
				String pk_invmandoc = (String) bvos[i].getAttributeValue("pk_invmandoc");// 表体存货
				String pk_father = (String) bvos[i].getAttributeValue("pk_father");// 关联精粉
				UFDouble nnum = (UFDouble) bvos[i].getAttributeValue("ndryweight");// 数量；
				if(nnum==null){nnum=new UFDouble(0);}
				// 查询结存数量
				String sql2 = "select nnum from xcgl_monthaccount where pk_stordoc='"
							+ stordoc
							+ "' and pk_factory='"
							+ factory
							+ "' and pk_invmandoc='"
							+ pk_invmandoc
							+ "' and pk_father='"
							+ pk_father
							+ "' and pk_corp='"
							+ SQLHelper.getCorpPk() + "' and isnull(dr,0)=0"    
						    + " and dyearmonth='"+dbilldate.toString().substring(0,7)+"'";
				UFDouble jnum = PuPubVO.getUFDouble_NullAsZero(ZmPubDAO.getDAO().executeQuery(sql2, new ColumnProcessor()));
				UFDouble balance = nnum.sub(jnum);
				bvos[i].setAttributeValue("ndryweight", balance);					
				}
				newVO.setChildrenVO(bvos);
				sendMessage(newVO, vo);
			}
	}

	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		
		if(valueVo==null||vo==null){
			return;
		}else{
			UFDouble balance=new UFDouble(0.0);
			for(int i=0;i<valueVo.getChildrenVO().length;i++){
				if(valueVo.getChildrenVO()[i].getAttributeValue("pk_invmandoc").equals(valueVo.getChildrenVO()[i].getAttributeValue("pk_father"))){
					balance=PuPubVO.getUFDouble_NullAsZero(valueVo.getChildrenVO()[i].getAttributeValue("ndryweight"));
				}
			}		
			HYBillVO tarBill=null;
			//走交换类，交换单据信息(主要是重选的表头信息和表体的存货信息)
			if(balance.toDouble()>0){
				tarBill= (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_monstocount, PubBillTypeConst.billtype_genotherin, valueVo,vo);
			}
			if(balance.toDouble()<0){
				if(valueVo!=null && valueVo.getChildrenVO()!=null && valueVo.getChildrenVO().length>0){
					 for(int i=0;i<valueVo.getChildrenVO().length;i++){
				    	UFDouble ndryweight=PuPubVO.getUFDouble_NullAsZero(valueVo.getChildrenVO()[i].getAttributeValue("ndryweight"));				    	
				    	valueVo.getChildrenVO()[i].setAttributeValue("ndryweight", new UFDouble(0).sub(ndryweight));
				     }
			    }				
			    tarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_monstocount, PubBillTypeConst.billtype_genotherout, valueVo,vo);				    
			}			
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期			
			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(tarBill.getParentVO().getAttributeValue("dbilldate"));		
			PfUtilWorkFlowVO vo1=new PfUtilWorkFlowVO();
			if(balance.toDouble()>0){
				tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_genotherin);//设置单据类型
				tarBill.getParentVO().setAttributeValue("vbillno", 
						new HYPubBO().getBillNo(PubBillTypeConst.billtype_genotherin, SQLHelper.getCorpPk(), null, null));//设置单据号；
				new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherin, dbilldate, vo1, tarBill, null);
			}
			if(balance.toDouble()<0){
				tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_genotherout);//设置单据类型
				tarBill.getParentVO().setAttributeValue("vbillno", 
						new HYPubBO().getBillNo(PubBillTypeConst.billtype_genotherout, SQLHelper.getCorpPk(), null, null));//设置单据号；
				new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_genotherout, dbilldate, vo1, tarBill, null);
			}
		}			
	}
}
