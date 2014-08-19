package nc.ui.xcgl.metalbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.xcgl.journalaccount.QueryClientDLG;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 金属平衡表 理论金属平衡表
 * 
 * @author yangtao
 * @date 2014-4-21 下午03:33:03 2002AC021510
 */
public class RepClientUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = -553369634771906111L;
	private QueryClientDLG m_qryDlg;

	public RepClientUI() {
	}

	public String[] getSqls() throws Exception {
		return new String[] {
		// 原矿月累计信息
				getQuerySQL(),
				// 原矿季累计信息
				getQuerySQL01(),
				// 原矿年累计信息
				getQuerySQL02(),
				// 查询销售铅精粉的月累计
				getQuerySQL1(),
				// 查询销售铅精粉的季累计
				getQuerySQL11(),
				// 查询销售铅精粉的年累计
				getQuerySQL12(),
				// 查询销售锌精粉的月累计

				// 查询销售锌精粉的季累计

				// 查询销售锌精粉的年累计

				// 查询铅精粉的月累计

				// 查询精粉的本月库存盘点信息
				getQuerySQL21(),
				// 查询精粉的上月库存盘点信息
				getQuerySQL22(),

		};
	}
   /**
    * 查询精粉的上月库存盘点信息
    * @return
    * @throws Exception
    */
	private String getQuerySQL22() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql22(whereSql);
	}
    /**
     * 查询精粉的本月库存盘点信息
     * @return
     * @throws Exception
     */
	private String getQuerySQL21() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql21(whereSql);
	}
    /**
     * 
     * @return
     * @throws Exception
     */
	private String getQuerySQL12() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql2(whereSql);
	}
    /**
     * 
     * @return
     * @throws Exception
     */
	private String getQuerySQL11() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql11(whereSql);
	}
    /**
     * 
     * @return
     * @throws Exception
     */
	private String getQuerySQL1() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql1(whereSql);
	}
    /**
     * 
     * @return
     * @throws Exception
     */
	private String getQuerySQL02() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql02(whereSql);
	}
    /**
     * 
     * @return
     * @throws Exception
     */
	private String getQuerySQL01() throws Exception {
		
		String whereSql = getQueryConditon();
		
		return ReportSql.getBusinessSql01(whereSql);
	}
  
	@Override
	public void onQuery() {
		// super.onQuery();
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
				// sqls=getSqls();
				ReportBaseVO[] vos = null;	
				vos = dealBeforeSetUI(list);
				if (vos == null || vos.length == 0)
					return;
				if (vos != null) {
					super.updateBodyDigits();
					setBodyVO(vos);
					// updateVOFromModel();//重新加载初始化公式
					// 在数据库查询报表的缓存配置，现在暂不使用缓存，没有缓存表报错，所以暂时先注释了
					// dealQueryAfter();//查询后的后续处理 一般用于 默认数据交叉之类的操作
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
	 * 设置到ui界面之前 处理查询后的数据
	 * 
	 * @param list
	 * @return
	 */
	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		List<ReportBaseVO[]> destlist = new ArrayList<ReportBaseVO[]>();
		if (list == null || list.size() == 0) {
			return null;
		}
		List<ReportBaseVO> zlist = new ArrayList<ReportBaseVO>();
		List<ReportBaseVO> blist = new ArrayList<ReportBaseVO>();
		ReportBaseVO[] oremonth = list.get(0);
		ReportBaseVO[] orequarter = list.get(1);
		ReportBaseVO[] oreyear = list.get(2);
		List<ReportBaseVO> orevolist = setOre(oremonth, orequarter, oreyear);
		for(ReportBaseVO orevo:orevolist){
			zlist.add(orevo);
		}
		//精粉销售月汇总信息
		ReportBaseVO[] flourmonth = list.get(3);
		//精粉销售季汇总信息
		ReportBaseVO[] flourquarter = list.get(4);
		//精粉销售年汇总信息
		ReportBaseVO[] flouryear = list.get(5);
		//本月库存盘点
		ReportBaseVO[] currstor = list.get(6);
		//上月库存盘点
		ReportBaseVO[] laststor = list.get(7);
		List<ReportBaseVO> flourvolist = setFlour(flourmonth,flourquarter,flouryear,laststor,currstor);
		for(ReportBaseVO orevo:flourvolist){
			zlist.add(orevo);
		}
		//给插叙之后的数据按照选厂进行分组
		ReportBaseVO[][] voss = (ReportBaseVO[][]) SplitBillVOs.getSplitVOs(zlist.toArray( new ReportBaseVO[0]), new String[]{"pk_factory"});
		 for(int i=0;i<voss.length;i++){
				//获取相同维度下的所有的vo
			 ReportBaseVO[] vos = voss[i];
				//处理相同维度下的vo数组合并成一个vo
				ReportBaseVO[]    fixvo = dealVOS(vos);
				destlist.add(fixvo);
				 }
		
		for (int i = 0; i < destlist.size(); i++) {
			ReportBaseVO[] vos = destlist.get(i);
			if (vos == null || vos.length == 0)
				continue;
			for (int j = 0; j < vos.length; j++) {
				blist.add(vos[j]);
			}
		}
		return blist.toArray(new ReportBaseVO[0]);
	}
    /**
     * 设置尾矿的月信息，季累计，年累计信息
     * @param vos
     * @return
     */
	private ReportBaseVO[] dealVOS(ReportBaseVO[] vos) {
		List<ReportBaseVO> list = new ArrayList<ReportBaseVO>();
		if(vos==null||vos.length==0){
			return vos;
		}
		else{
			//先找到原矿的vo
			ReportBaseVO orevo = new ReportBaseVO();
			ReportBaseVO flourpbvo = new ReportBaseVO();
			ReportBaseVO flourznvo = new ReportBaseVO();
			for(ReportBaseVO vo:vos){
				if(PuPubVO.getString_TrimZeroLenAsNull(
						vo.getAttributeValue("pk_invbasdoc")).
						equalsIgnoreCase(PubOtherConst.pbznstonebaspk)){
					orevo = vo;
				}
				if(PuPubVO.getString_TrimZeroLenAsNull(
						vo.getAttributeValue("pk_invbasdoc")).
						equalsIgnoreCase(PubOtherConst.flourbaspkpb)){
					flourpbvo = vo;
				}
				if(PuPubVO.getString_TrimZeroLenAsNull(
						vo.getAttributeValue("pk_invbasdoc")).
						equalsIgnoreCase(PubOtherConst.flourbaspkzn)){
					flourznvo = vo;
				}
				
			}
		UFDouble monthnum = PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("ncrudes"));
		UFDouble quarternum = PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("ncrudesquarter"));
		UFDouble yearnum = PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("ncrudesyear"));
		UFDouble monthpbnum = PuPubVO.getUFDouble_NullAsZero(flourpbvo.getAttributeValue("ncrudes"));
		UFDouble quarterpbnum = PuPubVO.getUFDouble_NullAsZero(flourpbvo.getAttributeValue("ncrudesquarter"));
		UFDouble yearpbnum = PuPubVO.getUFDouble_NullAsZero(flourpbvo.getAttributeValue("ncrudesyear"));
		UFDouble monthznnum = PuPubVO.getUFDouble_NullAsZero(flourznvo.getAttributeValue("ncrudes"));
		UFDouble quarterznnum = PuPubVO.getUFDouble_NullAsZero(flourznvo.getAttributeValue("ncrudesquarter"));
		UFDouble yearznnum = PuPubVO.getUFDouble_NullAsZero(flourznvo.getAttributeValue("ncrudesyear"));
		ReportBaseVO tailvo = new ReportBaseVO();
		tailvo.setAttributeValue("ncrudes", monthnum.sub(monthpbnum).sub(monthznnum));
		tailvo.setAttributeValue("ncrudesquarter", quarternum.sub(quarterpbnum).sub(quarterznnum));
		tailvo.setAttributeValue("ncrudesyear", yearnum.sub(yearpbnum).sub(yearznnum));
		tailvo.setAttributeValue("pk_invbasdoc", PubOtherConst.tailbaspkzn);
		tailvo.setAttributeValue("pk_factory", orevo.getAttributeValue("pk_factory"));
		list.add(orevo);
		list.add(flourpbvo);
		list.add(flourznvo);
		list.add(tailvo);
		return list.toArray(new ReportBaseVO[0]);
		}
	}

	private List<ReportBaseVO> setFlour(ReportBaseVO[] month,
			ReportBaseVO[] quarter, ReportBaseVO[] year, 
			ReportBaseVO[] laststor, ReportBaseVO[] curstor) throws BusinessException {
		List<ReportBaseVO> list =  new ArrayList<ReportBaseVO>();
		if(month==null||quarter==null||year==null||laststor==null||curstor==null){
			
			return list;
		}
		else{
		 Map<String, ReportBaseVO> monthmap = createCalMap(month);
		 Map<String, ReportBaseVO> quartermap = createCalMap(quarter);
		 Map<String, ReportBaseVO> yearmap = createCalMap(year);
		 Map<String, ReportBaseVO> laststormap = createCalMap(laststor);
		 Map<String, ReportBaseVO> curstorcmap = createCalMap(curstor);
		 for(String key:monthmap.keySet()){
			 ReportBaseVO monthvo = monthmap.get(key);
			 ReportBaseVO quartervo = quartermap.get(key);
			 ReportBaseVO yearvo = yearmap.get(key);
			 ReportBaseVO laststorvo = laststormap.get(key);
			 ReportBaseVO curstorvo = curstorcmap.get(key);
			 ReportBaseVO[] fixvos =fixdata(monthvo,quartervo,yearvo,laststorvo,curstorvo);
			 for(int i = 0;i<fixvos.length;i++){
				 list.add(fixvos[i]);
			 }
		 }
			return list;
		}
	}

	private ReportBaseVO[] fixdata(ReportBaseVO monthvo,
			ReportBaseVO quartervo, ReportBaseVO yearvo,
			ReportBaseVO laststorvo, ReportBaseVO curstorvo) throws BusinessException {
		List<ReportBaseVO> list =  new ArrayList<ReportBaseVO>();
		if(monthvo==null||quartervo==null||yearvo==null
				||laststorvo==null||curstorvo==null){
			return list.toArray(new ReportBaseVO[0]);
		}
		else{
			//铅精粉中铅锌银的金属量
			if(ZmPubTool.getInvbaspkBymanPk(
					PuPubVO.getString_TrimZeroLenAsNull(
							monthvo.getAttributeValue("pk_father"))).
							equalsIgnoreCase(PubOtherConst.flourbaspkpb)){
			UFDouble pbflourmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("flourpb"));
			UFDouble pbmetalofpbmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("pbmetalmonth"));
			UFDouble znmetalofpbmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("znmetalmonth"));
			UFDouble agmetalofpbmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("agmetalmonth"));
			UFDouble pbflourquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("flourpb"));
			UFDouble pbmetalofquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("pbmetalquarter"));
			UFDouble znmetalofpbquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("znmetalquarter"));
			UFDouble agmetalofpbquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("agmetalquarter"));
			UFDouble pbflouryear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("flourpb"));
			UFDouble pbmetalofpbyear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("pbmetalyear"));
			UFDouble znmetalofpbyear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("znmetalyear"));
			UFDouble agmetalofpbyear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("agmetalyear"));
			UFDouble flourlast = PuPubVO.getUFDouble_NullAsZero(laststorvo.getAttributeValue("flourpb"));
			UFDouble flourcurr = PuPubVO.getUFDouble_NullAsZero(curstorvo.getAttributeValue("flourpb"));
			UFDouble mount = pbflourmonth.add(flourcurr).sub(flourlast);
		    ReportBaseVO pbflourvo = new ReportBaseVO();
		    //铅精粉的数量信息
		    pbflourvo.setAttributeValue("ncrudes", mount);
		    pbflourvo.setAttributeValue("ncrudesquarter", pbflourquarter);
		    pbflourvo.setAttributeValue("ncrudesyear", pbflouryear);
		    pbflourvo.setAttributeValue("pbmetalmonth", pbmetalofpbmonth);
		    pbflourvo.setAttributeValue("znmetalmonth", znmetalofpbmonth);
		    pbflourvo.setAttributeValue("agmetalmonth", agmetalofpbmonth);
		    pbflourvo.setAttributeValue("pbmetalquarter", pbmetalofquarter);
		    pbflourvo.setAttributeValue("znmetalquarter", znmetalofpbquarter);
		    pbflourvo.setAttributeValue("agmetalquarter", agmetalofpbquarter);
		    pbflourvo.setAttributeValue("pbmetalyear", pbmetalofpbyear);
		    pbflourvo.setAttributeValue("znmetalyear", znmetalofpbyear);
		    pbflourvo.setAttributeValue("agmetalyear", agmetalofpbyear);
		    pbflourvo.setAttributeValue("pk_factory", monthvo.getAttributeValue("pk_factory"));
		    pbflourvo.setAttributeValue("pk_invbasdoc", PubOtherConst.flourbaspkpb);
		    pbflourvo.setAttributeValue("pbtastemonth", pbmetalofpbmonth.div(mount));
		    pbflourvo.setAttributeValue("zntastemonth", znmetalofpbmonth.div(mount));
		    pbflourvo.setAttributeValue("agtastemonth", agmetalofpbmonth.div(mount));
		    pbflourvo.setAttributeValue("pbtastequarter", pbmetalofquarter.div(pbflourquarter));
		    pbflourvo.setAttributeValue("zntastequarter", znmetalofpbquarter.div(pbflourquarter));
		    pbflourvo.setAttributeValue("agtastequarter", agmetalofpbquarter.div(pbflourquarter));
		    pbflourvo.setAttributeValue("pbtasteyear", pbmetalofpbyear.div(pbflouryear));
		    pbflourvo.setAttributeValue("zntasteyear", znmetalofpbyear.div(pbflouryear));
		    pbflourvo.setAttributeValue("agtasteyear", agmetalofpbyear.div(pbflouryear));
		    list.add(pbflourvo);
			}
		    //锌精粉中的铅锌银金属的数量
			if(ZmPubTool.getInvbaspkBymanPk(
					PuPubVO.getString_TrimZeroLenAsNull(
							monthvo.getAttributeValue("pk_father"))).
							equalsIgnoreCase(PubOtherConst.flourbaspkzn)){
			UFDouble pbflourmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("flourzn"));
			UFDouble pbmetalofpbmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("pbmetalmonth"));
			UFDouble znmetalofpbmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("znmetalmonth"));
			UFDouble agmetalofpbmonth = PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("agmetalmonth"));
			UFDouble pbflourquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("flourzn"));
			UFDouble pbmetalofquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("pbmetalquarter"));
			UFDouble znmetalofpbquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("znmetalquarter"));
			UFDouble agmetalofpbquarter = PuPubVO.getUFDouble_NullAsZero(quartervo.getAttributeValue("agmetalquarter"));
			UFDouble pbflouryear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("flourzn"));
			UFDouble pbmetalofpbyear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("pbmetalyear"));
			UFDouble znmetalofpbyear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("znmetalyear"));
			UFDouble agmetalofpbyear = PuPubVO.getUFDouble_NullAsZero(yearvo.getAttributeValue("agmetalyear"));
			UFDouble flourlast = PuPubVO.getUFDouble_NullAsZero(laststorvo.getAttributeValue("flourzn"));
			UFDouble flourcurr = PuPubVO.getUFDouble_NullAsZero(curstorvo.getAttributeValue("flourzn"));
			UFDouble mount = pbflourmonth.add(flourcurr).sub(flourlast);
		    ReportBaseVO pbflourvo = new ReportBaseVO();
		    //精粉的数量信息
		    pbflourvo.setAttributeValue("ncrudes", pbflourmonth);
		    pbflourvo.setAttributeValue("ncrudesquarter", pbflourquarter);
		    pbflourvo.setAttributeValue("ncrudesyear", pbflouryear);
		    pbflourvo.setAttributeValue("pbmetalmonth", pbmetalofpbmonth);
		    pbflourvo.setAttributeValue("znmetalmonth", znmetalofpbmonth);
		    pbflourvo.setAttributeValue("agmetalmonth", agmetalofpbmonth);
		    pbflourvo.setAttributeValue("pbmetalquarter", pbmetalofquarter);
		    pbflourvo.setAttributeValue("znmetalquarter", znmetalofpbquarter);
		    pbflourvo.setAttributeValue("agmetalquarter", agmetalofpbquarter);
		    pbflourvo.setAttributeValue("pbmetalyear", pbmetalofpbyear);
		    pbflourvo.setAttributeValue("znmetalyear", znmetalofpbyear);
		    pbflourvo.setAttributeValue("agmetalyear", agmetalofpbyear);
		    pbflourvo.setAttributeValue("pk_factory", monthvo.getAttributeValue("pk_factory"));
		    pbflourvo.setAttributeValue("pk_invbasdoc", PubOtherConst.flourbaspkzn);
		    pbflourvo.setAttributeValue("pbtastemonth", pbmetalofpbmonth.div(mount));
		    pbflourvo.setAttributeValue("zntastemonth", znmetalofpbmonth.div(mount));
		    pbflourvo.setAttributeValue("agtastemonth", agmetalofpbmonth.div(mount));
		    pbflourvo.setAttributeValue("pbtastequarter", pbmetalofquarter.div(pbflourquarter));
		    pbflourvo.setAttributeValue("zntastequarter", znmetalofpbquarter.div(pbflourquarter));
		    pbflourvo.setAttributeValue("agtastequarter", agmetalofpbquarter.div(pbflourquarter));
		    pbflourvo.setAttributeValue("pbtasteyear", pbmetalofpbyear.div(pbflouryear));
		    pbflourvo.setAttributeValue("zntasteyear", znmetalofpbyear.div(pbflouryear));
		    pbflourvo.setAttributeValue("agtasteyear", agmetalofpbyear.div(pbflouryear));
		    list.add(pbflourvo);
			}
			return list.toArray(new ReportBaseVO[0]);
		}
	}

	/**
	 * 设置原矿信息
	 * 
	 * @param month
	 * @param quarter
	 * @param year
	 * @return
	 */
	private List<ReportBaseVO> setOre(ReportBaseVO[] month, ReportBaseVO[] quarter,
			ReportBaseVO[] year) {
		List<ReportBaseVO> list =new ArrayList<ReportBaseVO>();
		if (month == null || quarter == null || year == null) {
			return list;
		}
		else{
			Map<String, ReportBaseVO> monthmap = createMap(month);
			Map<String, ReportBaseVO> quartermap = createMap(quarter);
			Map<String, ReportBaseVO> yearmap = createMap(year);
			for(String key:monthmap.keySet()){
				ReportBaseVO monthvo = monthmap.get(key);
				ReportBaseVO quartervo = quartermap.get(key);
				String[] quarternames = quartervo.getAttributeNames();
				for(String name:quarternames){
					monthvo.setAttributeValue(name, quartervo.getAttributeValue(name));
				}
				ReportBaseVO yearvo = yearmap.get(key);
				String[] yearnames = yearvo.getAttributeNames();
				for(String name:yearnames){
					monthvo.setAttributeValue(name, yearvo.getAttributeValue(name));
				}
				list.add(monthvo);
			}
			return list;
		}
	}

	/**
	 * 把一个vo数组根据他的选厂主键，创建成map key=pk_factory value=key所在的vo
	 * 
	 * @param vos
	 * @return
	 */
	public Map<String, ReportBaseVO> createMap(ReportBaseVO[] vos) {
		Map<String, ReportBaseVO> map = new HashMap<String, ReportBaseVO>();
		for (ReportBaseVO vo : vos) {
			String factory = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("pk_factory"));
			map.put(factory, vo);
		}
		return map;
	}
	/**
	 * 把一个vo数组根据他的选厂主键，创建成map key=pk_factory+pk_father value=key所在的vo
	 * 
	 * @param vos
	 * @return
	 */
	public Map<String, ReportBaseVO> createCalMap(ReportBaseVO[] vos) {
		Map<String, ReportBaseVO> map = new HashMap<String, ReportBaseVO>();
		for (ReportBaseVO vo : vos) {
			String factory = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("pk_factory"));
			String father = PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("pk_father"));
			map.put(factory+father, vo);
		}
		return map;
	}

	@Override
	public String _getModelCode() {
		return "2002AC021510";
	}

	@Override
	public void setUIAfterLoadTemplate() {

	}

	public void setQueryAfter() throws Exception {

	}

	public String getQuerySQL() throws Exception {
		String whereSql = getQueryConditon();
		return ReportSql.getBusinessSql(whereSql);
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
