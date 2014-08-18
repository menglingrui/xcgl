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
 * ѡ���ձ�
 * 
 * @author jay
 * 
 */
public class RepClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	// �����ܽڵ�� 2002AC021525
	private QueryClientDLG m_qryDlg;

	public String[] getSqls() throws Exception {
		return new String[] { 
		   getQuerySQL1(getQueryConditon()),
		   //С�ƣ��ϼ�
		   //ԭ����Ϣ
		   getQuerySQL21(getQueryConditon()),
		   //���ۼ�����
		    getQuerySQL2(getQueryConditon()),
		    //ԭ����Ϣ
		    getQuerySQL31(getQueryConditon()),
		   //���ۼ�����
		   getQuerySQL3(getQueryConditon()),
		   //ԭ����Ϣ
		   getQuerySQL41(getQueryConditon()),
		   //���ۼ�����
		    getQuerySQL4(getQueryConditon()),
		    //ԭ����Ϣ�պϼ�
			getQuerySQL51(getQueryConditon()),
			   //�պϼ�����
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
	 * ���캯��
	 */
	public RepClientUI() {
		super();
		// ��ѯ��̬�в���λ��
		setLocation(2);

		// ReportItem
		// it=ReportPubTool.getItem("qrycondition","��ѯ����",IBillItem.TEXTAREA,7,
		// 300);

		// getReportBase().getHeadItem("qrycondition").setDataType(IBillItem.TEXTAREA);
		// getReportBase().getHeadItem("qrycondition").setWidth(500);
		// getReportBase().setMaxLenOfHeadItem("qrycondition", 500);
		// getReportBase().getHeadItem("qrycondition").setWidth(4);

		getReportBase().getBillTable().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// ȥ���ֶ��Զ�����Ĺ���
		getReportBase().getBillTable().removeSortListener();
		setColumn();

	}
	  @Override
		public void onQuery() {
			getQueryDlg().showModal();
			if (getQueryDlg().getResult() == UIDialog.ID_OK) {
				try {
					 setQuery(true);
					// ��ձ�������
					clearBody();
					setDynamicColumn1();
					// �õ���ѯ���
				  	setColumn();

					List<ReportBaseVO[]> list = getReportVO(getSqls());
                    //sqls=getSqls();
					ReportBaseVO[] vos = null;
					vos=dealBeforeSetUI(list);
					//��vo����
					VOUtil.sort(vos, new String[]{"pk_minarea","pk_beltline","pk_factory"}, new int[]{VOUtil.DESC,VOUtil.DESC,VOUtil.DESC});
					//�����ظ������ݼ�¼
					// ReportBaseVO[]  fixvos = fliterdata(vos);
					//if (fixvos == null || fixvos.length == 0)
					//	return;
					if (vos != null) {
						super.updateBodyDigits();
						setBodyVO(vos);
//						updateVOFromModel();//���¼��س�ʼ����ʽ  
						//dealQueryAfter();//��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
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
		 * ���õ�ui����֮ǰ ��������ѯ�������
		 * ��һ��SQLλ�ò�ѯ����ԭ����Ϣ
		 * �ڶ���SQL��ѯ�����ºϼ���Ϣ
		 * ������SQLλ���Ǽ��ϼ���Ϣ
		 * ���ĸ�SQLλ������ϼ���Ϣ
		 */
		public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)throws Exception{
			if(list==null || list.size()==0){
				return null;
			}
			List<ReportBaseVO> zlist=new ArrayList<ReportBaseVO>();
			//��ѯ���İ��ղ��ţ�ѡ�����ɲ��ߣ��������ڣ����ͳ����Ϣ
			 ReportBaseVO[]  fixvos = fliterdata(list.get(0));
			 if(fixvos!=null&&fixvos.length!=0){
				 for(ReportBaseVO vo:fixvos){
					 zlist.add(vo);
				 }
			 }
			  //�����պϼ���Ϣ
			 
			  Map<String, ReportBaseVO> dayoremap = createOreMap(list.get(7));
			  Map<String, ReportBaseVO[]> dayflourmap = createFlourMap(list.get(8));
			  ReportBaseVO[] daycountvos = dealCountMap(dayoremap,dayflourmap,"�պϼ�");
			  setTastes(daycountvos);
			  for(ReportBaseVO vo :daycountvos){
					zlist.add(vo);
				 }
			 //�������ۼ���Ϣ
			 Map<String,ReportBaseVO> monthoremap = createOreMap(list.get(1));
//			 ReportBaseVO[] monthore = list.get(1);
//			 ReportBaseVO monthorevo = monthore[0];
//			 ReportBaseVO[] monthflour = list.get(2);
			 Map<String,ReportBaseVO[]> monthflourmap = createFlourMap(list.get(2));
			 
			 ReportBaseVO[] monthcountvos = dealCountMap(monthoremap,monthflourmap,"���ۼ�");
			 setTastes(monthcountvos);
			 for(ReportBaseVO vo :monthcountvos){
				 zlist.add(vo);
			 }
//			 ReportBaseVO monthcount = dealCount(monthorevo,monthflour,"���ۼ�");
//			 zlist.add(monthcount);
			 //�����ۼ���Ϣ
			 Map<String, ReportBaseVO> quarteroremap = createOreMap(list.get(3));
//			 ReportBaseVO[] quarterore = list.get(3);
//			 ReportBaseVO quarterorevo = quarterore[0];
			  Map<String, ReportBaseVO[]> quarterflourmap = createFlourMap(list.get(4));
//			 ReportBaseVO[] quarterflour = list.get(4);
//			 ReportBaseVO quartercount = dealCount(quarterorevo,quarterflour,"���ۼ�");
//			 zlist.add(quartercount);
			 ReportBaseVO[] quartercountvos = dealCountMap(quarteroremap,quarterflourmap,"���ۼ�");
			 setTastes(quartercountvos);
			 for(ReportBaseVO vo :quartercountvos){
				 zlist.add(vo);
			 }
			 //�������ۼ���Ϣ
			 
			  Map<String, ReportBaseVO> yearoremap = createOreMap(list.get(5));
			  Map<String, ReportBaseVO[]> yearflourmap = createFlourMap(list.get(6));
			  ReportBaseVO[] yearcountvos = dealCountMap(yearoremap,yearflourmap,"���ۼ�");
			  setTastes(yearcountvos);
			  for(ReportBaseVO vo :yearcountvos){
					zlist.add(vo);
				 }
//			 ReportBaseVO[] yearore = list.get(5);
//			 ReportBaseVO yearorevo = yearore[0];
//			 ReportBaseVO[] yearflour = list.get(6);
//			 ReportBaseVO yearcount = dealCount(yearorevo,yearflour,"���ۼ�");
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
			//Ǧ���۲���
			UFDouble noutput = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("noutput"));
			//п���۲���
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
		 * �����ۼ�����
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
		 * ���ݾ���������ѯ�����Ľ����
		 * �Բ�ѯ�����ľ��۽��з���
		 * ���������ǣ�
		 * key=pk_minarea+pk_beltline+pk_factory
		 * �ѷ���֮������ݷŵ�map��
		 * map��key=pk_minarea+pk_beltline+pk_factory
		 * value��������������µ�����vo��ɵ�����
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
		 * ����ԭ��������ѯ�����Ľ������
		 * ����key=pk_minarea+pk_beltline+pk_factory
		 * ���з���
		 * value=key����Ӧ��vo
		 * @param reportBaseVOs
		 * @return
		 */
		public Map<String, ReportBaseVO> createOreMap(ReportBaseVO[] vos) {
			/**
			 * ԭ������map��key=pk_minarea+pk_beltline+pk_factory
			 * value������ϲ鵽��ԭ������map
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
		 * ����ϼ���Ϣ
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
                        		//Ǧ����
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.flourbaspkpb)){
                        			//ָ��Ǧ
                        			if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
                        				orevo.setAttributeValue("pbpowdpb", flourvo.getAttributeValue("metalpb"));
                        				orevo.setAttributeValue("noutput", flourvo.getAttributeValue("noutputpb"));
                        			}
                        			//ָ��п
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
                                    	orevo.setAttributeValue("pbpowdzn", flourvo.getAttributeValue("metalzn"));
                        			}
                        			//ָ����
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkag)){
                                    	orevo.setAttributeValue("pbpowdag", flourvo.getAttributeValue("metalag"));
                        			}
                        		}
                        		//п����
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.flourbaspkzn)){
                        			
                        			//ָ��Ǧ
                        			if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
                        				orevo.setAttributeValue("znpowdpb", flourvo.getAttributeValue("metalpb"));
                        			}
                        			//ָ��п
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
                                    	orevo.setAttributeValue("znpowdzn", flourvo.getAttributeValue("metalzn"));
                                    	orevo.setAttributeValue("noutput1", flourvo.getAttributeValue("noutputzn"));
                        			}
//                        			//ָ����
//                                    if(flourvo.getAttributeValue("pk_invinx").equals("0001AP1000000000NTNE")){
//                                    	orevo.setAttributeValue("pbpowdag", flourvo.getAttributeValue("metalag"));
//                        			}
                        		}
                        		//пβ��
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.tailbaspkzn)){
                        			//ָ��Ǧ
//                        			if(flourvo.getAttributeValue("pk_invindex").equals("0001AP1000000000NTNI")){
//                        				orevo.setAttributeValue("pbpowdpb", flourvo.getAttributeValue("metalpb"));
//                        				orevo.setAttributeValue("noutput", flourvo.getAttributeValue("noutput"));
//                        			}
                        			//ָ��п
                                    if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
                                    	orevo.setAttributeValue("tailzn", flourvo.getAttributeValue("metalzn"));
                        			}
                        			//ָ����
//                                    if(flourvo.getAttributeValue("pk_invinx").equals("0001AP1000000000NTNE")){
//                                    	orevo.setAttributeValue("pbpowdag", flourvo.getAttributeValue("metalag"));
//                        			}
                        		}
                        		//Ǧβ��
                        		if(flourvo.getAttributeValue(name).equals(PubOtherConst.tailbaspkpb)){
                        			//ָ��Ǧ
                        			if(flourvo.getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkpb)){
                        				orevo.setAttributeValue("tailpb", flourvo.getAttributeValue("metalpb"));
                        				//orevo.setAttributeValue("noutput", flourvo.getAttributeValue("noutput"));
                        			}
                        			//ָ��п
//                                    if(flourvo.getAttributeValue("pk_invindex").equals("0001AP1000000000NTNL")){
//                                    	orevo.setAttributeValue("pbpowdzn", flourvo.getAttributeValue("metalzn"));
//                        			}
                        			//ָ����
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
	   * ��������ݿ��в���������ݣ����˵��ظ��ģ�ͬʱ��û��ֵ�����ݽ��м��㸳ֵ
	   * @param vos
	   * @return
	   */
	public ReportBaseVO[] fliterdata(ReportBaseVO[] vos) {
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		if(vos==null||vos.length==0){
			return vos;
		}
		else{
			//�ȶԲ�ѯ���������ݽ��з��飨���ţ�ѡ�����������ڣ���Σ������ߣ�
			 CircularlyAccessibleValueObject[][] voss = SplitBillVOs.getSplitVOs(vos, new String[]{"dbilldate","pk_factory","pk_minarea","pk_classorder","pk_beltline"});
			 //�������֮������ݣ����˵��ظ�������
			 for(int i=0;i<voss.length;i++){
			//��ȡ��ͬά���µ����е�vo
			CircularlyAccessibleValueObject[] vo = voss[i];
			//������ͬά���µ�vo����ϲ���һ��vo
			ReportBaseVO    fixvo = dealVOS(vo);
			list.add(fixvo);
			 }
			return list.toArray(new ReportBaseVO[0]);
		}
	}
     /**
      * ����һ��ά���µ�vo���������ձ�����Ҫ�������ֶ�
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
			//����ԭ����Ϣ
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
        				   //���侫����Ϣ
        			   if(names[j].equalsIgnoreCase("pk_invbasdoc1")){
        				   //Ǧ����
        				   if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.flourbaspkpb)){
        					   //�ж�ָ��
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
        				   //п����
        				   if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.flourbaspkzn)){
        					   //�ж���ָ��
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
        				   //Ǧβ��
        				   if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.tailbaspkpb)){
        					   //�ж�ָ��
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
        				   //пβ��
                           if(vos[i].getAttributeValue("pk_invbasdoc1").equals(PubOtherConst.tailbaspkzn)){
        					   //zn
        					   if(vos[i].getAttributeValue("pk_invindex").equals(PubOtherConst.metalbaspkzn)){
        						   basevo.setAttributeValue("tailzn", vos[i].getAttributeValue("nmetalamount"));
        						   basevo.setAttributeValue("zntailzn", vos[i].getAttributeValue("ncontent"));
        						   
        					   }
        				   }
        				   
        			   }
        			   //���������
        			   //���������
        			   //�������
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
     * �����кϲ�
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
	 * �ϼ�
	 */
	public void setTolal() throws Exception {
	}

	/**
	 * Ҫ���ʼʱ���ض�̬�� ʵ�ִ˷���
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