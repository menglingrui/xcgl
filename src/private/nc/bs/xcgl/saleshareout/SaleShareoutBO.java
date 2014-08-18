package nc.bs.xcgl.saleshareout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.util.SQLHelper;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.genotherout.GenotheroutBVO;
import nc.vo.xcgl.gensaleout.AggSaleOutVO;
import nc.vo.xcgl.gensaleout.GenSaleOutBVO;
import nc.vo.xcgl.gensaleout.GenSaleOutHVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBBVO;
import nc.vo.xcgl.saleshareout.SaleShareoutHVO;
import nc.vo.xcgl.saleshareout.ShareHVO;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 *类说明：销售出库矿区分摊
 *@author jay
 *@version 1.0   
 *创建时间：2014-1-13上午10:15:13
 */
public class SaleShareoutBO {
    /**
     * 审批时生成销售库单 
     * 添加判断是否结账
     * @throws Exception 
     */
	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		//规划求解模型分摊
//		if(valueVo==null||vo==null){
//			return;
//		}
//		else{
//		/**
//		 * 销售出库矿区分摊计算主子孙结构
//		 * 销售出库是主子表结构
//		 * 销售出库的矿区是在是在表头
//		 * 销售出库矿区分摊计算的矿区是在孙表中
//		 * 生成的销售出库单是按照销售出库矿区分摊计算孙表中的矿区来生成的
//		 * 即：销售出库矿区分摊表体一列下面的孙表当中有多少个矿区，那么这一条数据就要生成多少个销售出库单
//		 */	
//			SaleShareoutHVO hvo = (SaleShareoutHVO) valueVo.getParentVO();
//			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
//			UFDate curdate=UFDate.getDate(vo.m_currentDate);
//			UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,vo.m_coId),UFBoolean.FALSE);
//			if(!isclose.booleanValue()){
//			String pk=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_general_h"));
//			QueryData query=new QueryData();
//			Map<Integer, List<SuperVO>[]> map=query.query(pk); 
//		    if(map==null||map.size()==0){
//		    	throw new BusinessException("该单据还没有进行分摊!");
//		    }
//		    List<HYBillVO> agglist=new ArrayList<HYBillVO>();
//		    for(Integer key:map.keySet()){
//		    	List<SuperVO>[] array=map.get(key);
//		    	List<SuperVO> headlist=array[0];
//		    	if(headlist!=null&&headlist.size()!=0){
//		    		for(int i=0;i<headlist.size();i++){
//                   ShareHVO headvo = (ShareHVO) headlist.get(i);		    		
//		    		List<SuperVO> bodylist=array[1];
//		    		HYBillVO billvo=new HYBillVO();
//		    		billvo.setParentVO(headvo);
//		    		String pk_father=headvo.getPk_father();
//		    		String pk_invmandoc=headvo.getPk_invmandoc();
//		    		String pk_invbasdoc=headvo.getPk_invbasdoc();
//		    		for(int k=0;k<bodylist.size();k++){
//		    			SaleShareoutBBVO bbvo=(SaleShareoutBBVO) bodylist.get(k);
//		    			bbvo.setPk_father(pk_father);
//		    			bbvo.setPk_invbasdoc(pk_invbasdoc);
//		    			bbvo.setPk_invmandoc(pk_invmandoc);
//		    			bbvo.setVlastbillcode(hvo.getVbillno());
//		    			bbvo.setVlastbillid(headvo.getPk_general_h());
//		    			bbvo.setVlastbillrowid(headvo.getPk_general_b());
//		    			bbvo.setVlastbilltype(PubNodeModelConst.NodeModel_saleshareout);
//		    			bbvo.setCsourcebillcode(headvo.getCsourcebillcode());
//		    			bbvo.setVsourcebillid(headvo.getVsourcebillid());
//		    			bbvo.setVsourcebillrowid(headvo.getVsourcebillrowid());
//		    			bbvo.setVsourcebilltype(headvo.getVsourcebilltype());
//		    		}		    		    		
//		    		billvo.setChildrenVO(bodylist.toArray(new CircularlyAccessibleValueObject[0]));		    			    		
//		    		agglist.add(billvo);
//		    		}
//		    	}
//		    }
//		    if(agglist!=null&&agglist.size()!=0){
//		    	AggregatedValueObject[] bills = SplitBillVOs.getSplitVOs(HYBillVO.class.getName(), ShareHVO.class.getName(), SaleShareoutBBVO.class.getName(), 
//		    			agglist.toArray(new HYBillVO[0]), new String[]{"pk_father"}, new String[]{"pk_minarea","pk_stordoc"});
//	    		if(bills!=null&&bills.length!=0){
//    			for(int i=0;i<bills.length;i++){
//    				ShareHVO headvo = (ShareHVO) bills[i].getParentVO();
//    				
//    				SaleShareoutBBVO[] bodys= (SaleShareoutBBVO[]) bills[i].getChildrenVO();
//    				
//    				GenSaleOutBVO[] sbodys=new GenSaleOutBVO[bodys.length];    				
//    				//get stock and mine ,get body num
//    				for(int n=0;n<sbodys.length;n++){
//    					sbodys[n]=new GenSaleOutBVO();
//    					//set body num
//    					sbodys[n].setNdryweight(bodys[n].getNdryweight());
//    					sbodys[n].setPk_father(bodys[n].getPk_father());
//    					sbodys[n].setPk_invbasdoc(bodys[n].getPk_invbasdoc());
//    					sbodys[n].setPk_invmandoc(bodys[n].getPk_invmandoc());  
//    					sbodys[n].setVlastbillcode(bodys[n].getVlastbillcode());
//    					sbodys[n].setVlastbillid(bodys[n].getVlastbillid());
//    					sbodys[n].setVlastbillrowid(bodys[n].getVlastbillrowid());
//    					sbodys[n].setVlastbilltype(bodys[n].getVlastbilltype());
//    					sbodys[n].setCsourcebillcode(bodys[n].getCsourcebillcode());
//    					sbodys[n].setVsourcebillid(bodys[n].getVsourcebillid());
//    					sbodys[n].setVsourcebillrowid(bodys[n].getVsourcebillrowid());
//    					sbodys[n].setVsourcebilltype(bodys[n].getVsourcebilltype());
//    					sbodys[n].setStatus(VOStatus.NEW);
//    				}
//    				setLinkRowNo(sbodys);
//    				//get stock an mine
//    				String pk_stordoc=bodys[0].getPk_stordoc();
//    				String pk_mine=bodys[0].getPk_minarea();   				
//    				//create saleout headyvo
//    				GenSaleOutHVO shvo=new GenSaleOutHVO();
//    				//set mine and stock
//    				shvo.setPk_stordoc(pk_stordoc);
//    				shvo.setPk_minarea(pk_mine); 
//    				//客商档案
//    				shvo.setVreserve1(hvo.getVreserve1());
//    				shvo.setVreserve2(hvo.getVreserve2());
//                    AggSaleOutVO  aggsaleout=new AggSaleOutVO();
//    				aggsaleout.setParentVO(shvo);
//    				aggsaleout.setChildrenVO(sbodys);
//    				aggsaleout.getParentVO().setAttributeValue("pk_factory",PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_factory")));//设置选场
//    				aggsaleout.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_saleout);//设置单据类型
//    				aggsaleout.getParentVO().setAttributeValue("pk_corp", vo.m_coId);
//    				aggsaleout.getParentVO().setAttributeValue("vbillno", new HYPubBO().getBillNo(
//							PubBillTypeConst.billtype_saleout, SQLHelper.getCorpPk(), null, null));   //设置单据号；
//    				aggsaleout.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
//    				aggsaleout.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
//    				aggsaleout.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期
//    				aggsaleout.getParentVO().setAttributeValue("dbilldate", new UFDate(vo.m_currentDate));//单据日期
//    				PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
//    				new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_saleout, 
//							dbilldate, vo2, aggsaleout, null);
//    				
//    				
//    			}
//    		}
//		    	
//		    }
//			}
//			else{
//				throw new BusinessException("当前日期已关账，不能进行审批！");
//			}
//		}
		
		if(valueVo==null||vo==null){
		return;
	}
	else{
	    // get data from database
		SaleShareoutHVO hvo = (SaleShareoutHVO) valueVo.getParentVO();
		String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
		UFDate curdate=UFDate.getDate(vo.m_currentDate);
		UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,vo.m_coId),UFBoolean.FALSE);
		if(!isclose.booleanValue()){
		String pk=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_general_h"));
		QueryData query=new QueryData();
		Map<Integer, List<SuperVO>[]> map=query.query(pk); 
	    if(map==null||map.size()==0){
	    	throw new BusinessException("该单据还没有进行分摊!");
	    }
	    List<HYBillVO> agglist=new ArrayList<HYBillVO>();
	    for(Integer key:map.keySet()){
	    	List<SuperVO>[] array=map.get(key);
	    	List<SuperVO> headlist=array[0];
	    	if(headlist!=null&&headlist.size()!=0){
	    		for(int i=0;i<headlist.size();i++){
               ShareHVO headvo = (ShareHVO) headlist.get(i);		    		
	    		List<SuperVO> bodylist=array[1];
	    		HYBillVO billvo=new HYBillVO();
	    		billvo.setParentVO(headvo);
	    		String pk_father=headvo.getPk_father();
	    		String pk_invmandoc=headvo.getPk_invmandoc();
	    		String pk_invbasdoc=headvo.getPk_invbasdoc();
	    		for(int k=0;k<bodylist.size();k++){
	    			SaleShareoutBBVO bbvo=(SaleShareoutBBVO) bodylist.get(k);
	    			bbvo.setPk_father(pk_father);
	    			bbvo.setPk_invbasdoc(pk_invbasdoc);
	    			bbvo.setPk_invmandoc(pk_invmandoc);
	    			bbvo.setVlastbillcode(hvo.getVbillno());
	    			bbvo.setVlastbillid(headvo.getPk_general_h());
	    			bbvo.setVlastbillrowid(headvo.getPk_general_b());
	    			bbvo.setVlastbilltype(PubNodeModelConst.NodeModel_saleshareout);
	    			bbvo.setCsourcebillcode(headvo.getCsourcebillcode());
	    			bbvo.setVsourcebillid(headvo.getVsourcebillid());
	    			bbvo.setVsourcebillrowid(headvo.getVsourcebillrowid());
	    			bbvo.setVsourcebilltype(headvo.getVsourcebilltype());
	    		}		    		    		
	    		billvo.setChildrenVO(bodylist.toArray(new CircularlyAccessibleValueObject[0]));		    			    		
	    		agglist.add(billvo);
	    		}
	    	}
	    }
	    if(agglist!=null&&agglist.size()!=0){
	    	//用交换类来获得单据的来源信息和源头信息
	    	AggregatedValueObject billvo = PfUtilTools.runChangeData(PubBillTypeConst.billtype_saleshareout,
					PubBillTypeConst.billtype_saleout, valueVo, vo);
	    	//按照存货管理主键分组
	    	AggregatedValueObject[] bills = SplitBillVOs.getSplitVOs(HYBillVO.class.getName(), ShareHVO.class.getName(), SaleShareoutBBVO.class.getName(), 
	    			agglist.toArray(new HYBillVO[0]), new String[]{"pk_invmandoc"}, new String[]{"pk_minarea","pk_stordoc"});
    		if(bills!=null&&bills.length!=0){
			for(int i=0;i<bills.length;i++){
				ShareHVO sharevo = (ShareHVO) bills[i].getParentVO();
				//复制一个包含来源信息的聚合vo
				HYBillVO tarbill = (HYBillVO) ObjectUtils.serializableClone(billvo);
				SaleShareoutBBVO[] bodys= (SaleShareoutBBVO[]) bills[i].getChildrenVO();
				for(int j=0;j<bodys.length;j++){
					SaleShareoutBBVO bbvo=bodys[j];
					UFDouble num=bodys[j].getNdryweight();
					//查询现存量，取得该选厂，仓库，精粉的现存量
					  PubStockOnHandVO onhandvo=new PubStockOnHandVO();
				        onhandvo.setPk_corp(hvo.getPk_corp());
				        onhandvo.setPk_factory(hvo.getPk_factory());
				        onhandvo.setPk_father(sharevo.getPk_invmandoc());
				        onhandvo.setPk_stordoc(bbvo.getPk_stordoc());
				        List<SuperVO[]>  onlist=BillStockTool.getInstrance().queryStockDetail(new PubStockOnHandVO[]{onhandvo});
				        if(onlist!=null&&onlist.size()!=0){
				       	 SuperVO[] onvos=onlist.get(0);
			        	 if(onvos!=null&&onvos.length!=0){
			        		 //找到精粉vo和精粉数量
			        		 List<SuperVO> flourlist = getFlourVOByInvId(onvos, sharevo.getPk_invmandoc());
			        		 if(flourlist!=null&&flourlist.size()!=0){
				        			SuperVO flourvo=flourlist.get(0);
				        			UFDouble flourmout= PuPubVO.getUFDouble_NullAsZero( flourvo.getAttributeValue("nnum"));
				        			List<GenSaleOutBVO> blist=new ArrayList<GenSaleOutBVO>();
				        			for(int m=0;m<onvos.length;m++){
				        				//复制一个包含来源信息的销售出库vo
				        				GenSaleOutBVO bvo=(GenSaleOutBVO) ObjectUtils.serializableClone(tarbill.getChildrenVO()[0]);
				        				bvo.setCrowno(PuPubVO.getString_TrimZeroLenAsNull((m+1)*10));
					        			bvo.setPk_invmandoc(PuPubVO.getString_TrimZeroLenAsNull(onvos[m].getAttributeValue("pk_invmandoc")));
					        			bvo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(onvos[m].getAttributeValue("pk_invbasdoc")));
					        			bvo.setPk_father(sharevo.getPk_invmandoc());
					        			bvo.setPk_measdoc(getmeaspkBybas(PuPubVO.getString_TrimZeroLenAsNull(onvos[m].getAttributeValue("pk_invbasdoc"))));
					        			UFDouble nnum=PuPubVO.getUFDouble_NullAsZero(onvos[m].getAttributeValue("nnum"));
					        			bvo.setNdryweight(nnum.multiply(num.div(flourmout)));
					        			blist.add(bvo);
				        			}
				        			//tlist=setVcrowno(blist.toArray(new GenSaleOutBVO[0]));
				        			setLinkRowNo(blist.toArray(new GenSaleOutBVO[0]));
				        			tarbill.setChildrenVO(blist.toArray(new GenSaleOutBVO[0]));
				        			tarbill.getParentVO().setAttributeValue("pk_minarea",bbvo.getPk_minarea());//设置部门
				        			tarbill.getParentVO().setAttributeValue("pk_stordoc",bbvo.getPk_stordoc());//设置仓库
//				        			tarbill.getParentVO().setAttributeValue("vreserve1",hvo.getVreserve1());
//				        			tarbill.getParentVO().setAttributeValue("vreserve2",hvo.getVreserve2());
				        			tarbill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_saleout);//设置单据类型
				        			tarbill.getParentVO().setAttributeValue("pk_corp", vo.m_coId);
				        			tarbill.getParentVO().setAttributeValue("vbillno", new HYPubBO().getBillNo(
				    						PubBillTypeConst.billtype_saleout, SQLHelper.getCorpPk(), null, null));   //设置单据号；
				        			tarbill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
				        			tarbill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//制单人
				        			tarbill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//制单日期
				        			tarbill.getParentVO().setAttributeValue("dbilldate", new UFDate(dbilldate));//单据日期
				    				PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
				    				new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_saleout, 
				    						dbilldate, vo2, tarbill, null);
				        				}
				        			}
				        }
				}
				   				
			
				
			}
		}
	    	
	    }
		}
		else{
			throw new BusinessException("当前日期已关账，不能进行审批！");
		}
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
	 * set rowno and linkno
	 * @param sbodys
	 */
	public void setLinkRowNo(GenSaleOutBVO[] sbodys) {
      if(sbodys==null || sbodys.length==0){
    	  return ;
      }
      GenSaleOutBVO mainvo=null;      
      List<GenSaleOutBVO> indexvos=new ArrayList<GenSaleOutBVO>();     
      //get mainvo and indexvos
      for(int i=0;i<sbodys.length;i++){
    	  if(sbodys[i].getPk_invmandoc().equals(sbodys[i].getPk_father())){
    		  mainvo=sbodys[i];
    	  }else{
    		  indexvos.add(sbodys[i]);
    	  }
      }
      //set rowno
      String mrowno="10";
      mainvo.setCrowno(mrowno);
      //set indexvos rowno
      for(int i=0;i<indexvos.size();i++){
    	  indexvos.get(i).setCrowno(((i+2)*10)+"");
    	  indexvos.get(i).setVcrowno(mrowno);
      }   
	}
	private GenSaleOutBVO changeVoType(SuperVO superVO) {
		if(superVO!=null){
			GenSaleOutBVO hvo=new GenSaleOutBVO();
			for(int i=0;i<superVO.getAttributeNames().length;i++){
				hvo.setAttributeValue(superVO.getAttributeNames()[i],
						superVO.getAttributeValue(superVO.getAttributeNames()[i]));
			}
			return hvo;
		}
		return null;
	}
}


