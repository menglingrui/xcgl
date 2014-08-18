package nc.ui.xcgl.plantdaily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ListSelectionModel;

import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.report.query.QueryDLG;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.xcgl.pub.consts.PubNodeCodeConst;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;

/**
 * 选厂日报
 * 
 * @author jay
 * 
 */
public class RepClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	// 报表功能节点号 2002AC021525
	private QueryClientDLG m_qryDlg;

	public String[] getSqls() throws Exception {
		return new String[] { 
		   getQuerySQL1(getQueryConditon()),
		   //小计，合计
		   //原矿信息
		   getQuerySQL21(getQueryConditon()),
		   //月累计数据
		    getQuerySQL2(getQueryConditon()),
		    //原矿信息
		    getQuerySQL31(getQueryConditon()),
		   //季累计数据
		   getQuerySQL3(getQueryConditon()),
		   //原矿信息
		   getQuerySQL41(getQueryConditon()),
		   //年累计数据
		    getQuerySQL4(getQueryConditon()),
		    //原矿信息日合计
			getQuerySQL51(getQueryConditon()),
			   //日合计数据
			getQuerySQL5(getQueryConditon()),
		};
	}

	private String getQuerySQL5(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql5(whereSql);
	}

	private String getQuerySQL51(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql51(whereSql);
	}

	private String getQuerySQL41(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql41(whereSql);
	}

	private String getQuerySQL4(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql4(whereSql);
	}

	private String getQuerySQL31(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql31(whereSql);
	}

	private String getQuerySQL3(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql3(whereSql);
	}

	private String getQuerySQL21(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql21(whereSql);
	}

	private String getQuerySQL2(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql2(whereSql);
	}

	/**
	 * 构造函数
	 */
	public RepClientUI() {
		super();
		// 查询动态列插入位置
		setLocation(2);

		// ReportItem
		// it=ReportPubTool.getItem("qrycondition","查询条件",IBillItem.TEXTAREA,7,
		// 300);

		// getReportBase().getHeadItem("qrycondition").setDataType(IBillItem.TEXTAREA);
		// getReportBase().getHeadItem("qrycondition").setWidth(500);
		// getReportBase().setMaxLenOfHeadItem("qrycondition", 500);
		// getReportBase().getHeadItem("qrycondition").setWidth(4);

		getReportBase().getBillTable().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// 去除字段自动排序的功能
		getReportBase().getBillTable().removeSortListener();
		setColumn();

	}
	  @Override
		public void onQuery() {
			getQueryDlg().showModal();
			if (getQueryDlg().getResult() == UIDialog.ID_OK) {
				try {
					 setQuery(true);
					// 清空表体数据
					clearBody();
					setDynamicColumn1();
					// 得到查询结果
				  	setColumn();

					List<ReportBaseVO[]> list = getReportVO(getSqls());
                    //sqls=getSqls();
					ReportBaseVO[] vos = null;
					vos=dealBeforeSetUI(list);
					//给vo排序
					VOUtil.sort(vos, new String[]{"pk_minarea","pk_beltline","pk_factory"}, new int[]{VOUtil.DESC,VOUtil.DESC,VOUtil.DESC});
					//处理重复的数据记录
					// ReportBaseVO[]  fixvos = fliterdata(vos);
					//if (fixvos == null || fixvos.length == 0)
					//	return;
					if (vos != null) {
						super.updateBodyDigits();
						setBodyVO(vos);
//						updateVOFromModel();//重新加载初始化公式  
						//dealQueryAfter();//查询后的后续处理 一般用于 默认数据交叉之类的操作
					}
				} catch (BusinessException e) {
					e.printStackTrace();
					this.showErrorMessage(e.getMessage());

				} catch (Exception e) {
					e.printStackTrace();
					this.showErrorMessage(e.getMessage());

				}
			}
		}
	  /**
		 * 设置到ui界面之前 处理分组查询后的数据
		 * 第一个SQL位置查询的是原矿信息
		 * 第二个SQL查询的是月合计信息
		 * 第三个SQL位置是季合计信息
		 * 第四个SQL位置是年合计信息
		 */
		public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)throws Exception{
			if(list==null || list.size()==0){
				return null;
			}
			List<ReportBaseVO> zlist=new ArrayList<ReportBaseVO>();
			//查询到的按照部门，选厂，成产线，单据日期，班次统计信息
			 ReportBaseVO[]  fixvos = fliterdata(list.get(0));
			 if(fixvos!=null&&fixvos.length!=0){
				 for(ReportBaseVO vo:fixvos){
					 zlist.add(vo);
				 }
			 }
			  //处理日合计信息
			 
			  Map<String, ReportBaseVO> dayoremap = createOreMap(list.get(7));
			  Map<String, ReportBaseVO[]> dayflourmap = createFlourMap(list.get(8));
			  ReportBaseVO[] daycountvos = dealCountMap(dayoremap,dayflourmap,"日合计");
			  setTastes(daycountvos);
			  for(ReportBaseVO vo :daycountvos){
					zlist.add(vo);
				 }
			 //处理月累计信息
			 Map<String,ReportBaseVO> monthoremap = createOreMap(list.get(1));
//			 ReportBaseVO[] monthore = list.get(1);
//			 ReportBaseVO monthorevo = monthore[0];
//			 ReportBaseVO[] monthflour = list.get(2);
			 Map<String,ReportBaseVO[]> monthflourmap = createFlourMap(list.get(2));
			 
			 ReportBaseVO[] monthcountvos = dealCountMap(monthoremap,monthflourmap,"月累计");
			 setTastes(monthcountvos);
			 for(ReportBaseVO vo :monthcountvos){
				 zlist.add(vo);
			 }
//			 ReportBaseVO monthcount = dealCount(monthorevo,monthflour,"月累计");
//			 zlist.add(monthcount);
			 //处理季累计信息
			 Map<String, ReportBaseVO> quarteroremap = createOreMap(list.get(3));
//			 ReportBaseVO[] quarterore = list.get(3);
//			 ReportBaseVO quarterorevo = quarterore[0];
			  Map<String, ReportBaseVO[]> quarterflourmap = createFlourMap(list.get(4));
//			 ReportBaseVO[] quarterflour = list.get(4);
//			 ReportBaseVO quartercount = dealCount(quarterorevo,quarterflour,"季累计");
//			 zlist.add(quartercount);
			 ReportBaseVO[] quartercountvos = dealCountMap(quarteroremap,quarterflourmap,"季累计");
			 setTastes(quartercountvos);
			 for(ReportBaseVO vo :quartercountvos){
				 zlist.add(vo);
			 }
			 //处理年累计信息
			 
			  Map<String, ReportBaseVO> yearoremap = createOreMap(list.get(5));
			  Map<String, ReportBaseVO[]> yearflourmap = createFlourMap(list.get(6));
			  ReportBaseVO[] yearcountvos = dealCountMap(yearoremap,yearflourmap,"年累计");
			  setTastes(yearcountvos);
			  for(ReportBaseVO vo :yearcountvos){
					zlist.add(vo);
				 }
//			 ReportBaseVO[] yearore = list.get(5);
//			 ReportBaseVO yearorevo = yearore[0];
//			 ReportBaseVO[] yearflour = list.get(6);
//			 ReportBaseVO yearcount = dealCount(yearorevo,yearflour,"年累计");
//			 zlist.add(yearcount);
//			for(int i=0;i<list.size();i++){
//				ReportBaseVO[] vos=list.get(i);
//				if(vos==null || vos.length==0)
//					continue;
//				for(int j=0;j<vos.length;j++){
//					zlist.add(vos[j]);
//				}
				
			//}
			return zlist.toArray(new ReportBaseVO[0]);
		}
		private void setTastes(ReportBaseVO[] vos) {
		if(vos==null&&vos.length==0){
				return;
			}
		for(ReportBaseVO vo:vos){
			UFDouble dryweight = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("ndryweight"));
			//UFDouble nwetweight = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("nwetweight"));
			//铅精粉产量
			UFDouble noutput = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("noutput"));
			//锌精粉产量
			UFDouble noutput1 = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("noutput1"));
			UFDouble pbpowdpb = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbpowdpb"));
			UFDouble pbpowdzn = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbpowdzn"));
			UFDouble pbpowdag = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbpowdag"));
			UFDouble znpowdpb = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znpowdpb"));
			UFDouble znpowdzn = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znpowdzn"));
			UFDouble tailpb = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailpb"));
			UFDouble tailzn = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailzn"));
			UFDouble tailag = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailag"));
		    vo.setAttributeValue("orepb", (pbpowdpb.add(tailpb)).div(dryweight));
		    vo.setAttributeValue("orezn", (pbpowdzn.add(znpowdzn).add(tailzn)).div(dryweight));
		    vo.setAttributeValue("oreag", (pbpowdag.add(tailag)).div(dryweight));
		    vo.setAttributeValue("pbpb",  (pbpowdpb.div(noutput)).multiply(100));
		    vo.setAttributeValue("pbzn",  (pbpowdzn.div(noutput)).multiply(100));
		    vo.setAttributeValue("pbag",  (pbpowdpb.div(noutput)).multiply(100));
		    vo.setAttributeValue("znpb", znpowdpb.div(noutput1));
		    vo.setAttributeValue("znzn", znpowdzn.div(noutput1));
		    vo.setAttributeValue("znpowdpb", znpowdpb.div(noutput1).multiply(100));
		    vo.setAttributeValue("znpowdzn", znpowdzn.div(noutput1).multiply(1000));
		    vo.setAttributeValue("pbtailpb", tailpb.div(dryweight.sub(noutput)));
		    vo.setAttributeValue("pbtailag", tailag.div(dryweight.sub(noutput)));
		    vo.setAttributeValue("zntailzn", tailzn.div(dryweight.sub(noutput)).sub(noutput1));
		    vo.setAttributeValue("powderpb", noutput.div(dryweight).multiply(100));
		    vo.setAttributeValue("powderzn", noutput1.div(dryweight).multiply(100));
		    vo.setAttributeValue("nrecoverpb", 
		    		(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbpb")).
		    				multiply(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("orepb")).
		    						sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailpb"))))).
		    						div(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("orepb")).
		    								multiply((PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbpb")).
		    										sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailpb")))))));
		    vo.setAttributeValue("nrecoverag", 
		    		(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbag")).
		    				multiply(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("oreag")).
		    						sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailag"))))).
		    						div(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("oreag")).
		    								multiply((PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbag")).
		    										sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailag")))))));

		    vo.setAttributeValue("nrecoverzn",(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znzn")).
		    		multiply(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("orezn")).
		    				sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("powderzn")).
		    						multiply(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbzn"))).
		    						div(100)).
		    						sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailzn"))))).
		    		div(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("orezn")).
		    				multiply(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znzn"))).
		    				sub(PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("tailzn")))));
//		    vo.setAttributeValue("", value);
		}
		
	}

		/**
		 * 处理累计数据
		 * @param monthoremap
		 * @param monthflourmap
		 * @param string
		 * @return
		 */
		public  ReportBaseVO[] dealCountMap(Map<String, ReportBaseVO> oremap,
			Map<String, ReportBaseVO[]> flourmap, String str) {
			if(oremap==null||oremap.size()==0||flourmap==null||oremap.size()==0){
				return null;
			}
			else{ 
				List<ReportBaseVO> list =new ArrayList<ReportBaseVO>();
				for(String key:oremap.keySet()){
					ReportBaseVO vo = dealCount(oremap.get(key),flourmap.get(key),str);
					list.add(vo);
				}
		   return list.toArray(new ReportBaseVO[0]);
			}
	}

		/**
		 * 根据精粉数量查询出来的结果集
		 * 对查询出来的精粉进行分组
		 * 分组条件是：
		 * key=pk_minarea+pk_beltline+pk_factory
		 * 把分组之后的数据放到map中
		 * map的key=pk_minarea+pk_beltline+pk_factory
		 * value是这个分组条件下的所有vo组成的数组
		 * @param reportBaseVOs
		 * @return
		 */
		public Map<String, ReportBaseVO[]> createFlourMap(ReportBaseVO[] vos){
			Map<String, ReportBaseVO[]> map = new HashMap<String, ReportBaseVO[]>();
			if(vos==null||vos.length==0){
				return map;
			}
			else{
				CircularlyAccessibleValueObject[][] voss = 
					SplitBillVOs.getSplitVOs(vos, new String[]{"pk_factory","pk_minarea","pk_beltline"});
				 for(int i=0;i<voss.length;i++){
				CircularlyAccessibleValueObject[] splitvos = voss[i];
				String minarea = PuPubVO.getString_TrimZeroLenAsNull(splitvos[0].getAttributeValue("pk_minarea"));
				String beltline = PuPubVO.getString_TrimZeroLenAsNull(splitvos[0].getAttributeValue("pk_beltline"));
				String factory = PuPubVO.getString_TrimZeroLenAsNull(splitvos[0].getAttributeValue("pk_factory"));
						map.put(minarea+beltline+factory, 
								(ReportBaseVO[]) splitvos);
						}
		     return map;
			}
	}

		/**
		 * 根据原矿数量查询出来的结果集，
		 * 按照key=pk_minarea+pk_beltline+pk_factory
		 * 进行分组
		 * value=key所对应的vo
		 * @param reportBaseVOs
		 * @return
		 */
		public Map<String, ReportBaseVO> createOreMap(ReportBaseVO[] vos) {
			/**
			 * 原矿数量map：key=pk_minarea+pk_beltline+pk_factory
			 * value：键组合查到的原矿数量map
			 */
			Map<String,ReportBaseVO> map = new HashMap<String, ReportBaseVO>();
			if(vos==null||vos.length==0){
				return map;
			}
			else{
				for(ReportBaseVO vo:vos){
				String minarea = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("pk_minarea"));
				String beltline = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("pk_beltline"));
				String factory = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("pk_factory"));
				map.put(minarea+beltline+factory, vo);
				}
		     return map;
			}
	    }
		/**
		 * 处理合计信息
		 * @author Jay
		 * @param str 
		 * @param monthorevo
		 * @param monthflour
		 * @return
		 */
	  public ReportBaseVO dealCount(ReportBaseVO orevo,ReportBaseVO[] flour, String str) {
		  if(orevo==null||flour==null||flour.length==0||str.trim().equalsIgnoreCase("")){
			  return orevo;
		  }
		  else{
			  for(ReportBaseVO flourvo:flour){
				  String[] names = flourvo.getAttributeNames();
				  if(names!=null&&names.length!=0){
					  for(String name:names){
                          if(flourvo.getAttributeValue(name)!=null){
                        	if(name.equalsIgnoreCase("pk_invbasdoc")){
                        		orevo.setAttributeValue("pk_minarea", flourvo.getAttributeValue("pk_minarea"));
                        		orevo.setAttributeValue("pk_factory", flourvo.getAttributeValue("pk_factory"));
                        		orevo.setAttributeValue("pk_beltline", flourvo.getAttributeValue("pk_beltline"));
                        		//orevo.setAttributeValue("dbilldate", flourvo.getAttributeValue("dbilldate"));
                        		orevo.setAttributeValue("count", str);
                        		//铅精粉
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.flourbaspkpb)){
                        			//指标铅
                        			if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
                        				orevo.setAttributeValue("pbpowdpb", flourvo.getAttributeValue("metalpb"));
                        				orevo.setAttributeValue("noutput", flourvo.getAttributeValue("noutputpb"));
                        			}
                        			//指标锌
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
                                    	orevo.setAttributeValue("pbpowdzn", flourvo.getAttributeValue("metalzn"));
                        			}
                        			//指标银
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkag)){
                                    	orevo.setAttributeValue("pbpowdag", flourvo.getAttributeValue("metalag"));
                        			}
                        		}
                        		//锌精粉
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.flourbaspkzn)){
                        			
                        			//指标铅
                        			if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
                        				orevo.setAttributeValue("znpowdpb", flourvo.getAttributeValue("metalpb"));
                        			}
                        			//指标锌
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
                                    	orevo.setAttributeValue("znpowdzn", flourvo.getAttributeValue("metalzn"));
                                    	orevo.setAttributeValue("noutput1", flourvo.getAttributeValue("noutputzn"));
                        			}
//                        			//指标银
//                                    if(flourvo.getAttributeValue("pk_invinx").equals("0001AP1000000000NTNE")){
//                                    	orevo.setAttributeValue("pbpowdag", flourvo.getAttributeValue("metalag"));
//                        			}
                        		}
                        		//锌尾矿
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.tailbaspkzn)){
                        			//指标铅
//                        			if(flourvo.getAttributeValue("pk_invindex").equals("0001AP1000000000NTNI")){
//                        				orevo.setAttributeValue("pbpowdpb", flourvo.getAttributeValue("metalpb"));
//                        				orevo.setAttributeValue("noutput", flourvo.getAttributeValue("noutput"));
//                        			}
                        			//指标锌
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
                                    	orevo.setAttributeValue("tailzn", flourvo.getAttributeValue("metalzn"));
                        			}
                        			//指标银
//                                    if(flourvo.getAttributeValue("pk_invinx").equals("0001AP1000000000NTNE")){
//                                    	orevo.setAttributeValue("pbpowdag", flourvo.getAttributeValue("metalag"));
//                        			}
                        		}
                        		//铅尾矿
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.tailbaspkpb)){
                        			//指标铅
                        			if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
                        				orevo.setAttributeValue("tailpb", flourvo.getAttributeValue("metalpb"));
                        				//orevo.setAttributeValue("noutput", flourvo.getAttributeValue("noutput"));
                        			}
                        			//指标锌
//                                    if(flourvo.getAttributeValue("pk_invindex").equals("0001AP1000000000NTNL")){
//                                    	orevo.setAttributeValue("pbpowdzn", flourvo.getAttributeValue("metalzn"));
//                        			}
                        			//指标银
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkag)){
                                    	orevo.setAttributeValue("tailag", flourvo.getAttributeValue("metalag"));
                        			}
                        		}
                        	}  
                          }
                          }
				  }
			  }
			  return orevo;
		  }
	}

	/**
	   * 处理从数据库中查出来的数据，过滤掉重复的，同时对没有值的数据进行计算赋值
	   * @param vos
	   * @return
	   */
	public ReportBaseVO[] fliterdata(ReportBaseVO[] vos) {
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		if(vos==null||vos.length==0){
			return vos;
		}
		else{
			//先对查询出来的数据进行分组（部门，选厂，单据日期，班次，生产线）
			 CircularlyAccessibleValueObject[][] voss = SplitBillVOs.getSplitVOs(vos, new String[]{"dbilldate","pk_factory","pk_minarea","pk_classorder","pk_beltline"});
			 //处理分组之后的数据，过滤掉重复的数据
			 for(int i=0;i<voss.length;i++){
			//获取相同维度下的所有的vo
			CircularlyAccessibleValueObject[] vo = voss[i];
			//处理相同维度下的vo数组合并成一个vo
			ReportBaseVO    fixvo = dealVOS(vo);
			list.add(fixvo);
			 }
			return list.toArray(new ReportBaseVO[0]);
		}
	}
     /**
      * 处理一个维度下的vo，处理完日报表需要的所有字段
      * @param vos
      * @return
      */
	private ReportBaseVO dealVOS(CircularlyAccessibleValueObject[] vos) {
		if(vos==null||vos.length==0){
			return null;
		}
		else{
			ReportBaseVO basevo = new ReportBaseVO();
			basevo.setAttributeValue("dbilldate", vos[0].getAttributeValue("dbilldate"));
			basevo.setAttributeValue("pk_factory", vos[0].getAttributeValue("pk_factory"));
			basevo.setAttributeValue("pk_beltline", vos[0].getAttributeValue("pk_beltline"));
			basevo.setAttributeValue("pk_minarea", vos[0].getAttributeValue("pk_minarea"));
			basevo.setAttributeValue("pk_classorder", vos[0].getAttributeValue("pk_classorder"));
			//分配原矿信息
			basevo.setAttributeValue("pk_invmandoc", vos[0].getAttributeValue("pk_invmandoc"));
			basevo.setAttributeValue("pk_invbasdoc", vos[0].getAttributeValue("pk_invbasdoc"));
			basevo.setAttributeValue("nwetweight", vos[0].getAttributeValue("nwetweight"));
			basevo.setAttributeValue("nwatercontent", vos[0].getAttributeValue("nwatercontent"));
			basevo.setAttributeValue("ndryweight", vos[0].getAttributeValue("ndryweight"));
			UFDouble ore = PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("ndryweight"));
           for(int i=0;i<vos.length;i++)	{
        	   String[] names = vos[i].getAttributeNames();
        	   if(names!=null&&names.length!=0){
        		   for(int j=0;j<names.length;j++){
        			   if(vos[i].getAttributeValue(names[j])!=null){
        				   //分配精粉信息
        			   if(names[j].equalsIgnoreCase("pk_invbasdoc1")){
        				   //铅精粉
        				   if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.flourbaspkpb)){
        					   //判断指标
        					   //pb
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
        						   basevo.setAttributeValue("orepb", vos[i].getAttributeValue("ncrudescontent"));
        						   basevo.setAttributeValue("noutput", vos[i].getAttributeValue("noutput"));
        						   UFDouble pbflour =PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("noutput"));
        						   basevo.setAttributeValue("powderpb", (ore.getDouble()==0)?0:pbflour.div(ore));
        						   basevo.setAttributeValue("pbpowdpb", vos[i].getAttributeValue("nmetalamount"));
        						   //basevo.setAttributeValue("pbpb", vos[i].getAttributeValue("ncontent1"));
        						   basevo.setAttributeValue("nrecoverpb", vos[i].getAttributeValue("nrecover"));
        						   basevo.setAttributeValue("pbpb", vos[i].getAttributeValue("ncontent"));
        					   }
        					   //zn
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
        						   basevo.setAttributeValue("orezn", vos[i].getAttributeValue("ncrudescontent"));
        						   basevo.setAttributeValue("pbpowdzn", vos[i].getAttributeValue("nmetalamount"));
        						  // basevo.setAttributeValue("nrecoverzn", vos[i].getAttributeValue("nrecover"));
        						   basevo.setAttributeValue("pbzn", vos[i].getAttributeValue("ncontent"));
        						   
        					   }
        					   //ag
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkag)){
        						   basevo.setAttributeValue("oreag", vos[i].getAttributeValue("ncrudescontent"));
        						   basevo.setAttributeValue("pbpowdag", PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("nmetalamount")).div(1000));
        						   basevo.setAttributeValue("nrecoverag", vos[i].getAttributeValue("nrecover"));
        						   basevo.setAttributeValue("pbag", vos[i].getAttributeValue("ncontent"));
        					   }
        				   }
        				   //锌精粉
        				   if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.flourbaspkzn)){
        					   //判断主指标
        					   //pb
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
        						   basevo.setAttributeValue("znpowdpb", vos[i].getAttributeValue("nmetalamount"));
        						   basevo.setAttributeValue("znpb", vos[i].getAttributeValue("ncontent"));
        					   }
        					   //zn
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
        						   basevo.setAttributeValue("orezn", vos[i].getAttributeValue("ncrudescontent"));
        						   basevo.setAttributeValue("znpowdzn", vos[i].getAttributeValue("nmetalamount"));
        						   basevo.setAttributeValue("znzn", vos[i].getAttributeValue("ncontent"));
        						   basevo.setAttributeValue("pbzn", vos[i].getAttributeValue("ncontent1"));
        						   basevo.setAttributeValue("nrecoverzn", vos[i].getAttributeValue("nrecover"));
        						   basevo.setAttributeValue("noutput1", vos[i].getAttributeValue("noutput"));
        						   UFDouble znflour = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("noutput"));
        						   basevo.setAttributeValue("powderzn", (ore.getDouble()==0)?0:znflour.div(ore));
        					   }
        				   }
        				   //铅尾矿
        				   if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.tailbaspkpb)){
        					   //判断指标
        					   //pb
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
        						   basevo.setAttributeValue("tailpb", vos[i].getAttributeValue("nmetalamount"));
        						   basevo.setAttributeValue("pbtailpb", vos[i].getAttributeValue("ncontent"));
        						   basevo.setAttributeValue("pbtailpb", vos[i].getAttributeValue("ncontent1"));
        					   }
        					   //zn
//        					   if(vos[i].getAttributeValue("pk_invindex").equals("0001AP1000000000NTNI")){
//        						   basevo.setAttributeValue("pbpowdzn", vos[i].getAttributeValue("nmetalamount"));
//        						   basevo.setAttributeValue("pbzn", vos[i].getAttributeValue("ncontent"));
//        						   
//        					   }
        					   //ag
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkag)){
        						   basevo.setAttributeValue("tailag", PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("nmetalamount")).div(1000));
        						   basevo.setAttributeValue("pbtailag", vos[i].getAttributeValue("ncontent"));
        						   basevo.setAttributeValue("pbtailag", vos[i].getAttributeValue("ncontent1"));
        					   }
        				   }
        				   //锌尾矿
                           if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.tailbaspkzn)){
        					   //zn
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
        						   basevo.setAttributeValue("tailzn", vos[i].getAttributeValue("nmetalamount"));
        						   basevo.setAttributeValue("zntailzn", vos[i].getAttributeValue("ncontent"));
        						   
        					   }
        				   }
        				   
        			   }
        			   //分配金属量
        			   //分配回收率
        			   //分配产率
        			   }
        		   }
        	   }
           }
           return basevo;
		}
	}

	public String getQuerySQL1(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		if (whereSql == null || whereSql.length() == 0) {
			whereSql = " (isnull(xcgl_general_h.dr,0)=0) and (isnull(xcgl_general_b.dr,0)=0)";
		} else {
			whereSql = whereSql
					+ " and (isnull(xcgl_general_h.dr,0)=0) and (isnull(xcgl_general_b.dr,0)=0) ";
		}
		return ReportSql.getBusinessSql1(whereSql);
	}

	private String getQueryCondition() {
		return null;
	}
	 /**
     * 基本列合并
     */
    protected void setColumn() {
    	
    }
	@Override
	public String _getModelCode() {
		return PubNodeCodeConst.NOdeModel_plantdaily;
	}

	@Override
	public void setUIAfterLoadTemplate() {

	}

	public void setQueryAfter() throws Exception {

	}

	public String getQuerySQL() throws Exception {

		return null;
	}

	/**
	 * 合计
	 */
	public void setTolal() throws Exception {
	}

	/**
	 * 要想初始时加载动态列 实现此方法
	 */
	@Override
	public Map getNewItems() throws Exception {
		return null;
	}

	@Override
	public void initReportUI() {

	}

	public QueryDLG getQueryDlg() {
		if (m_qryDlg == null) {
			m_qryDlg = new QueryClientDLG(this);
			m_qryDlg.setTempletID(_getCorpID(), _getModelCode(), _getUserID(),
					null, null, OrgnizeTypeVO.COMPANY_TYPE);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}

}