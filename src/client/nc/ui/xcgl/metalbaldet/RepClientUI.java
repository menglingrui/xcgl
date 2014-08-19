package nc.ui.xcgl.metalbaldet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ListSelectionModel;

import nc.ui.pub.beans.UIDialog;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.report.query.QueryDLG;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.xcgl.pub.consts.PubNodeCodeConst;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;

/**
 * ʵ�ʽ���ƽ���
 * 
 * @author jay
 * 
 */
public class RepClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	// �����ܽڵ�� 2002AC021508
	private QueryClientDLG m_qryDlg;

	public String[] getSqls() throws Exception {
		return new String[] {
		//��ѯԭ����Ϣ
	    getQuerySQL(getQueryConditon()),
	  //��ѯԭ���ۼ�
	    getQuerySQLQuarter(getQueryConditon()),
	  //��ѯԭ�����ۼ���Ϣ
	    getQuerySQLYear(getQueryConditon()),
	    //��ѯǦ������Ϣ
		getQuerySQL1(getQueryConditon()),
		//��ѯǦ���ۼ��ۼ���Ϣ
		getQuerySQL11(getQueryConditon()),
		//��ѯǦ�������ۼ���Ϣ
		getQuerySQL12(getQueryConditon()),
		//��ѯп������Ϣ
		getQuerySQL2(getQueryConditon()),
		//��ѯп���ۼ��ۼ���Ϣ
		getQuerySQL21(getQueryConditon()),
		//��ѯп�������ۼ���Ϣ
		getQuerySQL22(getQueryConditon()),
		//��ѯβ����Ϣ
		getQuerySQL3(getQueryConditon()),
		//��ѯβ���ۼ���Ϣ
		getQuerySQL31(getQueryConditon()),
		//��ѯβ�����ۼ���Ϣ
		getQuerySQL32(getQueryConditon()),
		};
	}

	private String getQuerySQLYear(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSqlY(whereSql);
	}

	private String getQuerySQLQuarter(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSqlQ(whereSql);
	}

	private String getQuerySQL32(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql32(whereSql);
	}

	private String getQuerySQL31(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql31(whereSql);
		
	}

	private String getQuerySQL22(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql22(whereSql);
	}

	private String getQuerySQL21(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql21(whereSql);
	}

	private String getQuerySQL12(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql12(whereSql);
	}

	private String getQuerySQL11(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql11(whereSql);
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
                   // sqls=getSqls();
					ReportBaseVO[] vos = null;
					vos=dealBeforeSetUI(list);
					UFDouble orenum = findore(vos);
					//���û����ʺ�Ʒζ
					 ReportBaseVO[] recovervos = setRecover(vos);
					 //����Ʒζ
					 ReportBaseVO[] fixvos =setTaste(recovervos,orenum);
					if (fixvos == null || fixvos.length == 0)
						return;
					if (fixvos != null) {
						super.updateBodyDigits();
						setBodyVO(fixvos);
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
		 * @param list
		 * @return
		 */
		public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)throws Exception{
			if(list==null || list.size()==0){
				return null;
			}
			List<ReportBaseVO> zlist=new ArrayList<ReportBaseVO>();
			Map<String,ReportBaseVO> oremouthmap = createMap(list.get(0));
			Map<String,ReportBaseVO> orequartermap = createMap(list.get(1));
			Map<String,ReportBaseVO> oreyearmap = createMap(list.get(2));
			ReportBaseVO[] ore = setTaghter(oremouthmap,orequartermap,oreyearmap);
			for(ReportBaseVO vo:ore){
				zlist.add(vo);
			}
			Map<String,ReportBaseVO> pbmouthmap = createMap(list.get(3));
			Map<String,ReportBaseVO> pbquartermap = createMap(list.get(4));
			Map<String,ReportBaseVO> pbyearmap = createMap(list.get(5));
			ReportBaseVO[] pb = setTaghter(pbmouthmap,pbquartermap,pbyearmap);
			for(ReportBaseVO vo:pb){
				zlist.add(vo);
			}
			Map<String,ReportBaseVO> znmouthmap = createMap(list.get(6));
			Map<String,ReportBaseVO> znquartermap = createMap(list.get(7));
			Map<String,ReportBaseVO> znyearmap = createMap(list.get(8));
			ReportBaseVO[] zn = setTaghter(znmouthmap,znquartermap,znyearmap);
			for(ReportBaseVO vo:zn){
				zlist.add(vo);
			}
			Map<String,ReportBaseVO> tailmouthmap = createMap(list.get(9));
			Map<String,ReportBaseVO> tailquartermap = createMap(list.get(10));
			Map<String,ReportBaseVO> tailyearmap = createMap(list.get(11));
			ReportBaseVO[] tail = setTaghter(tailmouthmap,tailquartermap,tailyearmap);
			for(ReportBaseVO vo:tail){
				zlist.add(vo);
			}	                         
			return zlist.toArray(new ReportBaseVO[0]);
		}
		private ReportBaseVO[] setTaghter(Map<String, ReportBaseVO> mouth,
			Map<String, ReportBaseVO> quarter,
			Map<String, ReportBaseVO> year) {
			List<ReportBaseVO>  list =new ArrayList<ReportBaseVO>();
			if(mouth==null||mouth.size()==0||quarter==null
					||quarter.size()==0||year==null||year.size()==0){
				return list.toArray(new ReportBaseVO[0]);
			}
			else{
				for(String key:mouth.keySet()){
					ReportBaseVO mouthvo = mouth.get(key);
					ReportBaseVO quartervo = quarter.get(key);  
					    ReportBaseVO yearvo = year.get(key);
					    String[] quarters = quartervo.getAttributeNames();
					    if(quarters!=null&&quarters.length!=0){
					    	for(String name:quarters){
					    		if(!name.equalsIgnoreCase("pk_factory")&&
					    				!name.equalsIgnoreCase("pk_invbasdoc")){
					    			mouthvo.setAttributeValue(name, quartervo.getAttributeValue(name));
					    		}
					    	}
					    }
					    String[] years = yearvo.getAttributeNames();
					    if(years!=null&&years.length!=0){
					    	for(String name:years){
					    		if(!name.equalsIgnoreCase("pk_factory")&&
					    				!name.equalsIgnoreCase("pk_invbasdoc")){
					    			mouthvo.setAttributeValue(name, yearvo.getAttributeValue(name));
					    		}
					    		
					    	}
					    }
					    list.add(mouthvo);
				}
				return list.toArray(new ReportBaseVO[0]);
			}
	}

		/**
		 * ��һ��vo�����������ѡ��������������map
		 * key=pk_factory
		 * value=key���ڵ�vo
		 * @param vos
		 * @return
		 */
	  public  Map<String, ReportBaseVO> createMap(ReportBaseVO[] vos) {
		  Map<String, ReportBaseVO> map = new HashMap<String, ReportBaseVO>();
		for(ReportBaseVO vo:vos){
			String factory = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("pk_factory"));
			map.put(factory, vo);
		}
		return map;
	}

	public ReportBaseVO[] setRecover(ReportBaseVO[] vos)  {
		if(vos==null||vos.length==0){
			return vos;
		}
		try {
			UFDouble metalpb = UFDouble.ZERO_DBL;
			UFDouble metalzn = UFDouble.ZERO_DBL;
			UFDouble metalag = UFDouble.ZERO_DBL;
			UFDouble metalpbquarter = UFDouble.ZERO_DBL;
			UFDouble metalznquarter = UFDouble.ZERO_DBL;
			UFDouble metalagquarter = UFDouble.ZERO_DBL;
			UFDouble metalpbyear = UFDouble.ZERO_DBL;
			UFDouble metalznyear = UFDouble.ZERO_DBL;
			UFDouble metalagyear = UFDouble.ZERO_DBL;
			//�ϼ�Ǧ��п�����Ľ�����
			ReportBaseVO[] metalvos = (ReportBaseVO[]) ObjectUtils.serializableClone(vos);
			for(ReportBaseVO vo:metalvos){
			 UFDouble pb = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbmetalmonth"));
			 metalpb = metalpb.add(pb);
			 UFDouble zn = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znmetalmonth"));
			 metalzn = metalzn.add(zn);
			 UFDouble ag = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("agmetalmonth"));
			 metalag = metalag.add(ag);
			 UFDouble pbquarter = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbmetalquarter"));
			 metalpbquarter = metalpb.add(pbquarter);
			 UFDouble znquarter = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znmetalquarter"));
			 metalznquarter = metalzn.add(znquarter);
			 UFDouble agquarter = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("agmetalquarter"));
			 metalagquarter = metalag.add(agquarter);
			 UFDouble pbyear = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbmetalyear"));
			 metalpbyear = metalpb.add(pbyear);
			 UFDouble znyear = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znmetalyear"));
			 metalznyear = metalzn.add(znyear);
			 UFDouble agyear = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("agmetalyear"));
			 metalagyear = metalag.add(agyear);
			}
			ReportBaseVO[] recovervos = (ReportBaseVO[]) ObjectUtils.serializableClone(metalvos);
			for(ReportBaseVO vo:recovervos){
				String[] names = vo.getAttributeNames();
				if(names!=null&&names.length!=0){
					for(String name:names){
						if(name.equalsIgnoreCase("pk_invbasdoc")){
							//ԭ��
							if(vo.getAttributeValue(name).equals(PubOtherConst.pbznstonebaspk)){
								vo.setAttributeValue("pbmetalmonth", metalpb);
								vo.setAttributeValue("znmetalmonth", metalzn);
								vo.setAttributeValue("agmetalmonth", metalag);
								vo.setAttributeValue("pbmetalquarter", metalpbquarter);
								vo.setAttributeValue("znmetalquarter", metalznquarter);
								vo.setAttributeValue("agmetalquarter", metalagquarter);
								vo.setAttributeValue("pbmetalyear", metalpbyear);
								vo.setAttributeValue("znmetalyear", metalznyear);
								vo.setAttributeValue("agmetalyear", metalagyear);
								vo.setAttributeValue("pbrecovermonth", 100);
								vo.setAttributeValue("znrecovermonth", 100);
								vo.setAttributeValue("agrecovermonth", 100);
								vo.setAttributeValue("pbrecoverquarter", 100);
								vo.setAttributeValue("znrecoverquarter", 100);
								vo.setAttributeValue("agrecoverquarter", 100);
								vo.setAttributeValue("pbrecoveryear", 100);
								vo.setAttributeValue("znrecoveryear", 100);
								vo.setAttributeValue("agrecoveryear", 100);
								
							}
							else{
								//�����������۵Ļ�����
								 UFDouble pb = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbmetalmonth"));
								 UFDouble zn = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znmetalmonth"));
								 UFDouble ag = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("agmetalmonth"));
								 UFDouble pbquarter = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbmetalquarter"));
								 UFDouble znquarter = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znmetalquarter"));
								 UFDouble agquarter = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("agmetalquarter"));
								 UFDouble pbyear = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("pbmetalyear"));
								 UFDouble znyear = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("znmetalyear"));
								 UFDouble agyear = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("agmetalyear"));
								 vo.setAttributeValue("pbrecovermonth", pb.div(metalpb).multiply(100));
								 vo.setAttributeValue("znrecovermonth", zn.div(metalzn).multiply(100));
								 vo.setAttributeValue("agrecovermonth", ag.div(metalag).multiply(100));
								 vo.setAttributeValue("pbrecoverquarter", pbquarter.div(metalpbquarter).multiply(100));
								 vo.setAttributeValue("znrecoverquarter", znquarter.div(metalznquarter).multiply(100));
								 vo.setAttributeValue("agrecoverquarter", agquarter.div(metalagquarter).multiply(100));
								 vo.setAttributeValue("pbrecoveryear", pbyear.div(metalpbyear).multiply(100));
								 vo.setAttributeValue("znrecoveryear", znyear.div(metalznyear).multiply(100));
								 vo.setAttributeValue("agrecoveryear", agyear.div(metalagyear).multiply(100));
							}
						}
					}
				}
				
			}
			return recovervos;
		} catch (Exception e) {
			this.showErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return vos;
	}

	/**
	   * ��ȡԭ����Ϣ
	   * @param vos
	   * @return
	   */
     public UFDouble findore(ReportBaseVO[] vos) {
    	 UFDouble orenum=UFDouble.ZERO_DBL;
    	 if(vos==null||vos.length==0){
    		 return orenum;
    	 }
    		 for(ReportBaseVO vo:vos){
    			 String[] names = vo.getAttributeNames();
    			 if(names!=null&&names.length!=0){
    				 for(String name:names){
    					 if(name.equals("pk_invbasdoc")){
    						 if(vo.getAttributeValue(name).equals(PubOtherConst.pbznstonebaspk)){
    							UFDouble ore = PuPubVO.getUFDouble_NullAsZero(vo.getAttributeValue("ncrudes"));
    							orenum = ore;
    							return orenum;
    						 }
    					 }
    				 }
    			 }
    		 }
    		 return orenum;
    	
	}

	/**
      * ��ѯԭ����Ϣ
      * @param queryConditon
      * @return
      * @throws Exception
      */
	public String getQuerySQL(String queryConditon) throws Exception {
//		if (whereSql == null || whereSql.length() == 0) {
//			whereSql = " (isnull(xcgl_flouryield_h.dr,0)=0) and (isnull(xcgl_flouryield_b.dr,0)=0)";
//		} else {
//			whereSql = whereSql
//					+ " and (isnull(xcgl_flouryield_h.dr,0)=0) and (isnull(xcgl_flouryield_b.dr,0)=0) ";
//		}
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql(whereSql);
	}
	/**
	 * ��ѯǦ������Ϣ
	 * @param queryConditon
	 * @return
	 * @throws Exception
	 */
	public String getQuerySQL1(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql1(whereSql);
	}
	/**
	 * ��ѯп������Ϣ
	 * @param queryConditon
	 * @return
	 * @throws Exception
	 */
	public String getQuerySQL2(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql2(whereSql);
	}
	/**
	 * ��ѯβ����Ϣ
	 * @param queryConditon
	 * @return
	 * @throws Exception
	 */
	public String getQuerySQL3(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql3(whereSql);
	}
	/**
	 * ����Ʒζ��Ϣ
	 * @param vos
	 * @param orenum
	 * @return
	 */
   public ReportBaseVO[] setTaste(ReportBaseVO[]vos, UFDouble orenum ){
	   if(vos==null||vos.length==0||orenum.doubleValue()==0){
		   return vos;
	   }
	   else{
		   for(int i = 0;i<vos.length;i++){
               String[] names = vos[i].getAttributeNames();
			   for(String name:names){
				   if(vos[i].getAttributeValue(name)!=null){
					   if(name.equalsIgnoreCase("pk_invbasdoc")){
						   //ԭ��
						  // if(!vos[i].getAttributeValue(name).equals("0001AP1000000000NTN3")){
							   
						   //}
						   //Ǧ����
						   //if(vos[i].getAttributeValue(name).equals("0001A11000000000WAX9")){
							 UFDouble flour = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
							 UFDouble pb = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("pbmetalmonth"));
							 UFDouble zn = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("znmetalmonth"));
							 UFDouble ag = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("agmetalmonth"));
							 UFDouble flourquarter = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudesquarter"));
							 UFDouble pbquarter = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("pbmetalquarter"));
							 UFDouble znquarter = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("znmetalquarter"));
							 UFDouble agquarter = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("agmetalquarter"));
							 UFDouble flouryear = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudesyear"));
							 UFDouble pbyear = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("pbmetalyear"));
							 UFDouble znyear = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("znmetalyear"));
							 UFDouble agyear = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("agmetalyear"));
							 vos[i].setAttributeValue("pbtastemonth",pb.div(flour).multiply(100));
							 vos[i].setAttributeValue("zntastemonth",zn.div(flour).multiply(100));
							 vos[i].setAttributeValue("agtastemonth",ag.div(flour).multiply(100) );
							 vos[i].setAttributeValue("pbtastequarter",pbquarter.div(flourquarter).multiply(100));
							 vos[i].setAttributeValue("zntastequarter",znquarter.div(flourquarter).multiply(100));
							 vos[i].setAttributeValue("agtastequarter",agquarter.div(flourquarter).multiply(100) );
							 vos[i].setAttributeValue("pbtasteyear",pbyear.div(flouryear).multiply(100));
							 vos[i].setAttributeValue("zntasteyear",znyear.div(flouryear).multiply(100));
							 vos[i].setAttributeValue("agtasteyear",agyear.div(flouryear).multiply(100) );
							   
						   //}
//						   //п����
//						   if(vos[i].getAttributeValue(name).equals("0001A11000000000WAXF")){
//							   UFDouble flour = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//							   UFDouble pb = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//							   UFDouble zn = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//							   UFDouble ag = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//						   }
//						   //пβ��
//						   if(vos[i].getAttributeValue(name).equals("0001A11000000000WAXI")){
//							   UFDouble flour = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//							   UFDouble pb = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//							   UFDouble zn = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//							   UFDouble ag = PuPubVO.getUFDouble_NullAsZero(vos[i].getAttributeValue("ncrudes"));
//						   }
						   
					   }
				   }
			   }
		   }
		   return vos;
	   }
	 
   }

	private String getQueryCondition() {
		return null;
	}

	@Override
	public String _getModelCode() {
		return PubNodeCodeConst.NOdeModel_metalbaldet;
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