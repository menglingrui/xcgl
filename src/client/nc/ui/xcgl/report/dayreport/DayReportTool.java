package nc.ui.xcgl.report.dayreport;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import nc.bd.accperiod.AccountCalendar;
import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.xcgl.report.sumdayreport.RepSumClientUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.CombinVO;
import nc.vo.zmpub.pub.tool.ZmPubTool;

public class DayReportTool {
	/**
	 * 产量计算的最小维度
	 */
	public static String[] mapkey={"pk_corp","pk_factory","pk_beltline","pk_classorder",
        "pk_minarea","pk_oreinvmandoc","vreserve1","dbilldate"};
	
	/**
	 * 日累计
	 */
	public static String[] daymapkey={"pk_corp","pk_factory","pk_beltline",
        "pk_minarea","pk_oreinvmandoc","vreserve1","dbilldate"};
	
	/**
	 * 月累计
	 */
	public static String[] monthmapkey={"pk_corp","pk_factory","pk_beltline",
        "pk_minarea","pk_oreinvmandoc","vreserve1"};
	
	/**
	 * 月累计sum
	 */
	public static String[] monthsummapkey={"pk_corp","pk_factory","pk_beltline",
        "pk_minarea","vmonth","pk_oreinvmandoc","vreserve1"};
	
	/**
	 * 季累计
	 */
	public static String[] yuartermapkey={"pk_corp","pk_factory","pk_beltline",
        "pk_minarea","pk_oreinvmandoc","vreserve1"};
	
	/**
	 * 年累计
	 */
	public static String[] yearmapkey={"pk_corp","pk_factory","pk_beltline",
        "pk_minarea","pk_oreinvmandoc","vreserve1"};
	
	/**
	 * 月合并条件
	 */
	public static String[] combinConditions={"pk_corp","pk_factory","pk_beltline",
		"pk_minarea","pk_oreinvmandoc","vmonth"};
	/**
	 * 月合并字段
	 */
	public static String[] combinFields={"nwetnum","ndrynum","pb_noutnum",
		"zn_noutnum","ptm_pb","ptm_zn","pbtm_ag","zn_noutnum","pbtm_pb",
		"zntm_zn","pb_tmag"};
	/**
	 * 平均值条件
	 */
	public static String[] AverageConditions={"pk_corp","pk_factory","pk_beltline",
		"pk_minarea","pk_oreinvmandoc","vmonth"}; 
	/**
	 * 平均值字段
	 */
	public static String[] AveragecombinFields={"ore_pb","ore_zn","ore_ag",
		"pb_pb","pb_zn","pb_ag","zn_pb","zn_zn","pbt_pb",
		"pbt_ag","znt_zn"};	
	
    public static String[] formulas={
    	"pb_pb_recrate->(pb_pb*ore_pb-pbt_pb )/(ore_pb*(pb_pb-pbt_pb)",
    	"pb_ag_recrate->(pb_ag*ore_ag-pbt_ag )/(ore_ag*(pb_ag-pbt_ag)",
    	"pb_noutnum->ndrynum*ore_pb*pb_pb_recrate/pb_pb",
    	"ptm_pb->(ndrynum-pb_noutnum)*pbt_pb",
    	"ptm_ag->(ndrynum-pb_noutnum)*pbt_ag",
    	"ptm_pb->ndrynum*ore_pb-pbtm_pb",
    	"ptm_ag->ndrynum*ore_ag-pbtm_ag",
    	"zn_zn_recrate->((ore_zn-(pb_noutnum/ndrynum))*pb_zn-znt_zn)*zn_zn)/(ore_zn*(zn_zn-znt_zn))",
    	"zn_noutnum->ndrynum*ore_zn*zn_zn_recrate/zn_zn",
    	"zntm_zn->(ndrynum-pb_noutnum-zn_noutnum)*znt_zn",
    	"znm_zn->ndrynum*ore_zn-pb_noutnum*pb_zn-zntm_zn",   	
    };
	
	public static ReportBaseVO[] getDealVO(ReportBaseVO[] vos) throws BusinessException {
		if(vos==null || vos.length==0){
			return null;
		}
		List<ReportBaseVO> list=new ArrayList<ReportBaseVO>();
		ReportBaseVO[][] voss=(ReportBaseVO[][]) SplitBillVOs.getSplitVOs(vos, mapkey);
		for(int i=0;i<voss.length;i++){
			ReportBaseVO[] bvos=voss[i];
			ReportBaseVO vo=getDealOreVO(bvos);
			dealPowerAndTail(vo,bvos);		
			list.add(vo);
		}
		return list.toArray(new ReportBaseVO[0]);
	}

	public static void dealPowerAndTail(ReportBaseVO vo, ReportBaseVO[] bvos) {
		dealPbPower(vo,bvos);
		dealZnPower(vo,bvos);
		dealPbTail(vo,bvos);
		dealZnTail(vo,bvos);	    
	}

	private static void dealZnTail(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Pb_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Zn_index);
		UFDouble Aggrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Ag_index);
		UFDouble Augrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Au_index);


	//	UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,);
		UFDouble Znnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Zn_index);
		UFDouble Pbnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Pb_index);
		UFDouble Agnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Ag_index);
		UFDouble Aunoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Au_index);

		//zn_noutnum,zn_pb, zn_zn
        vo.setAttributeValue("znt_pb", Pbgrade);
	    vo.setAttributeValue("znt_zn", Zngrade);
	    vo.setAttributeValue("znt_ag", Aggrade);
	    vo.setAttributeValue("znt_au", Augrade);
	 // vo.setAttributeValue("znt_noutnum", noutnum);
	    vo.setAttributeValue("zntm_zn", Znnoutmetalnum);
	    vo.setAttributeValue("zntm_pb", Pbnoutmetalnum);
	    vo.setAttributeValue("zntm_ag", Agnoutmetalnum);
	    vo.setAttributeValue("zntm_au", Aunoutmetalnum);
	}

	private static void dealPbTail(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Pb_index);
	    UFDouble Aggrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Ag_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Zn_index);
		UFDouble Augrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Au_index);

	//	UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Pb_index);
		UFDouble Pbnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Pb_index);
		UFDouble Agnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Ag_index);
		UFDouble Znnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Zn_index);
		UFDouble Aunoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Au_index);

		vo.setAttributeValue("pbt_pb", Pbgrade);
	    vo.setAttributeValue("pbt_ag", Aggrade);
	    vo.setAttributeValue("pbt_zn", Zngrade);
	    vo.setAttributeValue("pbt_au", Augrade);
  //    vo.setAttributeValue("pbt_noutnum", noutnum);
	    vo.setAttributeValue("pbtm_pb", Pbnoutmetalnum);
	    vo.setAttributeValue("pbtm_ag", Agnoutmetalnum);
	    vo.setAttributeValue("pbtm_zn", Znnoutmetalnum);
	    vo.setAttributeValue("pbtm_au", Aunoutmetalnum);

	}

	private static void dealZnPower(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Pb_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
		UFDouble Aggrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Ag_index);
		UFDouble Augrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Au_index);

		
		UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
		UFDouble Znnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
		UFDouble Pbnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Pb_index);
		UFDouble Agnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Ag_index);
		UFDouble Aunoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Au_index);

		UFDouble Znrecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
    	//zn_noutnum,zn_pb, zn_zn
        vo.setAttributeValue("zn_pb", Pbgrade);
	    vo.setAttributeValue("zn_zn", Zngrade);
	    vo.setAttributeValue("zn_ag", Aggrade);
	    vo.setAttributeValue("zn_au", Augrade);
	    vo.setAttributeValue("zn_noutnum", noutnum);
	    vo.setAttributeValue("znm_zn", Znnoutmetalnum);
	    vo.setAttributeValue("znm_pb", Pbnoutmetalnum);
	    vo.setAttributeValue("znm_ag", Agnoutmetalnum);
	    vo.setAttributeValue("znm_au", Aunoutmetalnum);
	    vo.setAttributeValue("zn_zn_recrate", Znrecrate);
	}

	private static void dealPbPower(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
	    UFDouble Aggrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Ag_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Zn_index);
		UFDouble Augrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Au_index);

		UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
		UFDouble Pbnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
		UFDouble Agnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Ag_index);
		UFDouble Znnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Zn_index);
		UFDouble Aunoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Au_index);

		UFDouble Pbrecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
        UFDouble Agrecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Ag_index);
        UFDouble Aurecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Au_index);

    	//pb_noutnum,pb_pb,pb_zn,pb_ag
        vo.setAttributeValue("pb_pb", Pbgrade);
	    vo.setAttributeValue("pb_ag", Aggrade);
	    vo.setAttributeValue("pb_zn", Zngrade);
	    vo.setAttributeValue("pb_au", Augrade);
	    
	    vo.setAttributeValue("pb_noutnum", noutnum);
	    vo.setAttributeValue("ptm_pb", Pbnoutmetalnum);
	    vo.setAttributeValue("ptm_ag", Agnoutmetalnum);
	    vo.setAttributeValue("ptm_zn", Znnoutmetalnum);
	    vo.setAttributeValue("ptm_au", Aunoutmetalnum);
	    vo.setAttributeValue("pb_pb_recrate", Pbrecrate);
	    vo.setAttributeValue("pb_ag_recrate", Agrecrate);
	    vo.setAttributeValue("pb_au_recrate", Aurecrate);
	}

	public static UFDouble getNRecRate(ReportBaseVO[] bvos, String type_power,
			String pb_power, String pb_index) {
		for(int i=0;i<bvos.length;i++){
			if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
				if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
				   return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("nrecover"));
				}   
			}
		}
		return new UFDouble(0);
	}

	public static UFDouble getNoutMetalNum(ReportBaseVO[] bvos, String type_power,
			String pb_power, String pb_index) {
		for(int i=0;i<bvos.length;i++){
			if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
				if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
				    return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("nmetalamount"));
				}
			}
		}
		return new UFDouble(0);
	}

	private static UFDouble getNoutNum(ReportBaseVO[] bvos, String type_power,
			String pb_power,String pk_invdex) {
		for(int i=0;i<bvos.length;i++){
			if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
				if(pk_invdex.equals(bvos[i].getAttributeValue("pk_invindex"))){
				   return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("noutput"));
				}
			}
		}
		return new UFDouble(0);
	}

	private static UFDouble getGrade(ReportBaseVO[] bvos, String type_power,
			String pb_power, String pb_index) {
		if(type_power.equals(DayReportConst.type_ore)){
			for(int i=0;i<bvos.length;i++){
				if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
					return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("ncrudescontent"));
				}
			}
		}else{
			for(int i=0;i<bvos.length;i++){
			  
				if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
					if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
						return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("ncontent"));
					}
					
				}
			}
		}		
		return new UFDouble(0);
	}

	public static ReportBaseVO getDealOreVO(ReportBaseVO[] bvos) throws BusinessException {
		try {
			 ReportBaseVO vo=(ReportBaseVO) ObjectUtils.serializableClone(bvos[0]);
			 UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Pb_index);
		     UFDouble Aggrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Ag_index);
			 UFDouble Zngrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Zn_index);
			 UFDouble Augrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Au_index);
			 vo.setAttributeValue("ore_pb", Pbgrade);
			 vo.setAttributeValue("ore_ag", Aggrade);
			 vo.setAttributeValue("ore_zn", Zngrade);
			 vo.setAttributeValue("ore_au", Augrade);
			 return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	public static ReportBaseVO getYearVO(ReportBaseVO[] dvos) throws Exception {
        List<ReportBaseVO> list=new ArrayList<ReportBaseVO>();		
        //取得当月
        UFDate date=PuPubVO.getUFDate(dvos[0].getAttributeValue("dbilldate"));
	    UFDate sdate=getMonthStartDate(date);
	    String sql=getMonthSql(dvos);   
	    sql=sql+" and dbilldate >= '"+sdate.toString()+"' and dbilldate<='"+date.toString()+"' ";
	    ReportBaseVO[] mvos=getReportVO(sql);	     
	    mvos=getDealVO(mvos);	
	    setMonth(mvos);	    
	    addVOtoList(list,mvos);	    
	    //取当月所在年度
	    List<String> mlist=getMonthsByYear(date);	   
	    if(mlist!=null && mlist.size()>0){
	    	for(int i=0;i<mlist.size();i++){
	    		String month=mlist.get(i);
	    		String msql=getSumMonthSql(dvos,month);
	    	    ReportBaseVO[] rvos=getReportVO(msql);
	    	    ReportBaseVO[] movos=getDealVO(rvos);	
	    	    addVOtoList(list,movos);
	    	}
	    }	    
	    ReportBaseVO[] qvos=list.toArray(new ReportBaseVO[0]);	    
		ReportBaseVO[] sumvos=(ReportBaseVO[]) CombinVO.combinData(
				(ReportBaseVO[])ObjectUtils.serializableClone(qvos), yearmapkey, RepSumClientUI.combinFields);		
		ReportBaseVO[] avgvos=CombinVO.averageData(
				(ReportBaseVO[])ObjectUtils.serializableClone(qvos), yearmapkey, RepSumClientUI.AveragecombinFields);				
	    CombinVO.copyValueByContion(sumvos,avgvos, yearmapkey, RepSumClientUI.AveragecombinFields);		    
		return sumvos[0];	
	}

	public static ReportBaseVO getQuarterVO(ReportBaseVO[] dvos) throws Exception {
        List<ReportBaseVO> list=new ArrayList<ReportBaseVO>();		
	    //取得当月
        UFDate date=PuPubVO.getUFDate(dvos[0].getAttributeValue("dbilldate"));
	    UFDate sdate=getMonthStartDate(date);
	    String sql=getMonthSql(dvos);   
	    sql=sql+" and dbilldate >= '"+sdate.toString()+"' and dbilldate<='"+date.toString()+"' ";
	    ReportBaseVO[] mvos=getReportVO(sql);	    
	    mvos=getDealVO(mvos);	
	    setMonth(mvos);	    
	    addVOtoList(list,mvos);	    
	    //取当月所在季度
	    List<String> mlist=getMonthsByQuarter(date);	   
	    if(mlist!=null && mlist.size()>0){
	    	for(int i=0;i<mlist.size();i++){
	    		String month=mlist.get(i);
	    		String msql=getSumMonthSql(dvos,month);
	    	    ReportBaseVO[] rvos=getReportVO(msql);
	    	    ReportBaseVO[] movos=getDealVO(rvos);	
	    	    addVOtoList(list,movos);
	    	}
	    }	    
	    ReportBaseVO[] qvos=list.toArray(new ReportBaseVO[0]);	    
		ReportBaseVO[] sumvos=(ReportBaseVO[]) CombinVO.combinData(
				(ReportBaseVO[])ObjectUtils.serializableClone(qvos), yuartermapkey, RepSumClientUI.combinFields);		
		ReportBaseVO[] avgvos=CombinVO.averageData(
				(ReportBaseVO[])ObjectUtils.serializableClone(qvos), yuartermapkey, RepSumClientUI.AveragecombinFields);				
	    CombinVO.copyValueByContion(sumvos,avgvos, yuartermapkey, RepSumClientUI.AveragecombinFields);		    
		return sumvos[0];	
	}

	public static ReportBaseVO getMonthReportVO(ReportBaseVO[] dvos) throws Exception {	    
	    UFDate date=PuPubVO.getUFDate(dvos[0].getAttributeValue("dbilldate"));
	    UFDate sdate=getMonthStartDate(date);
	    String sql=getMonthSql(dvos);   
	    sql=sql+" and dbilldate >= '"+sdate.toString()+"' and dbilldate<='"+date.toString()+"' ";
	    ReportBaseVO[] mvos=getReportVO(sql);	    
	    mvos=getDealVO(mvos);	
	    setMonth(mvos);
		ReportBaseVO[] smvos=(ReportBaseVO[]) CombinVO.combinData(
				(ReportBaseVO[])ObjectUtils.serializableClone(mvos), combinConditions, combinFields);		
		ReportBaseVO[] dmvos=CombinVO.averageData(
				(ReportBaseVO[])ObjectUtils.serializableClone(mvos), AverageConditions, AveragecombinFields);				
	    CombinVO.copyValueByContion(smvos,dmvos, AverageConditions, AveragecombinFields);		    
		return smvos[0];	
	}
	
	public static List<String> getMonthsByYear(UFDate monthenddate) {
		List<String> list=new ArrayList<String>();		
		String  month=ZmPubTool.getMonth(monthenddate.toString());		
		int counts=Integer.valueOf(month);		
		for(int i=1;i<counts;i++){
			if(i<10){
				list.add("0"+i);	
			}else{
				list.add(""+i);
			}
		}		
		return list;
	}
	
	public static void setMonth(ReportBaseVO[] mvos) {
		if(mvos==null || mvos.length==0){
			return;
		}
		UFDate date=PuPubVO.getUFDate(mvos[0].getAttributeValue("dbilldate"));
		
		String month=ZmPubTool.getMonth(date.toString());
	   	for(int i=0;i<mvos.length;i++){
	   		mvos[i].setAttributeValue("vmonth", month);
	   	}
		
	}

	public static ReportBaseVO[] getReportVO(String sql) {		
		return getReportVO(sql);
	}

	public static String getMonthSql(ReportBaseVO[] dvos) {
		
		ReportBaseVO vo=dvos[0];
		String wsql=getwhereSql(vo,monthmapkey);		
		return ReportSql.getBaseSql(wsql);
	}
	
	private static String getwhereSql(ReportBaseVO vo, String[] monthmapkey) {
		String wql=" ";		
		for(int i=0;i<monthmapkey.length;i++){
			wql=wql+" and  "+monthmapkey[i]+"='"+vo.getAttributeValue(monthmapkey[i])+"' ";
		}		
		return wql;
	}

	public static String getSumMonthSql(ReportBaseVO[] dvos, String month) {
		String wql=" ";		
		for(int i=0;i<monthsummapkey.length;i++){
			wql=wql+" and  "+monthsummapkey[i]+"='"+dvos[0].getAttributeValue(monthsummapkey[i])+"' ";
		}
		wql=wql+" and vmonth='"+dvos[0].getAttributeValue("vmonth")+"'";
		String sql=" select *  from xcgl_reportdaysum where isnull(dr,0)=0 "+wql;		
		return sql;
	}
	
	public static void addVOtoList(List<ReportBaseVO> list, ReportBaseVO[] mvos) {
		if(list==null || list.size()==0){
			return ;
		}
		if(mvos==null || mvos.length==0){
			return;
		}
		for(int i=0;i<mvos.length;i++){
		    list.add(mvos[i]);	
		}
	}

	private static List<String> getMonthsByQuarter(UFDate monthenddate) throws ParseException {
		String firstday=ZmPubTool.getFirstDayOfQuarter(monthenddate.toString());		
		List<String> list=new ArrayList<String>();		
		String  endmonth=ZmPubTool.getMonth(monthenddate.toString());		
		int end=Integer.valueOf(endmonth);				
		String  startmonth=ZmPubTool.getMonth(firstday);	
		int start=Integer.valueOf(startmonth);		
		
		for(int i=start;i<end+1;i++){
			if(i<10){
				list.add("0"+i);	
			}else{
				list.add(""+i);
			}
		}	
		return list;
	}

	public static UFDate getMonthEndDate(UFDate date) throws InvalidAccperiodExcetion {
	    AccountCalendar ac=AccountCalendar.getInstance();
	    ac.setDate(date);
	    UFDate enddate=ac.getMonthVO().getEnddate();
		return enddate;
	}
	
	
	public static UFDate getMonthStartDate(UFDate date) throws InvalidAccperiodExcetion {
	    AccountCalendar ac=AccountCalendar.getInstance();
	    ac.setDate(date);
	    UFDate enddate=ac.getMonthVO().getBegindate();
		return enddate;
	}
	
	
	public static ReportBaseVO getDayReportVO(ReportBaseVO[] dvos) throws Exception {
		ReportBaseVO[] sumvos=(ReportBaseVO[]) CombinVO.combinData(
				(ReportBaseVO[])ObjectUtils.serializableClone(dvos), daymapkey, RepSumClientUI.combinFields);		
		ReportBaseVO[] avgvos=CombinVO.averageData(
				(ReportBaseVO[])ObjectUtils.serializableClone(dvos), daymapkey, RepSumClientUI.AveragecombinFields);				
	    CombinVO.copyValueByContion(sumvos,avgvos, daymapkey, RepSumClientUI.AveragecombinFields);			
		return sumvos[0];
	}
}
