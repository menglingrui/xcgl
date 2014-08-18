package nc.ui.xcgl.metalbalance;

import java.text.ParseException;

import nc.ui.pub.ClientEnvironment;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 金属平衡表
 */
public class ReportSql {
    /**
     * 月原矿加工汇总
     * @param whereSql
     * @return
     * @throws ParseException 
     */
	public static String getBusinessSql(String whereSql) throws ParseException{
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT xcgl_general_h.pk_factory,");
		sb.append(" xcgl_general_b.pk_invbasdoc,");
		sb.append(" NVL(SUM(xcgl_general_b.nwetweight),0)  nwetweight,");
		sb.append(" NVL(SUM(xcgl_general_b.ndryweight),0)  ncrudes,");
		sb.append(" ( NVL(SUM(xcgl_general_b.nwetweight),0) -NVL(SUM(xcgl_general_b.ndryweight),0))/");
		sb.append(" NVL(SUM(xcgl_general_b.nwetweight),0)  nwater");
		sb.append(" FROM xcgl_general_h,xcgl_general_b");
		sb.append(" ,xcgl_flouryield_h,xcgl_flouryield_b");
		sb.append(" WHERE xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		sb.append(" AND  xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h");
		sb.append(" AND   xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		sb.append(" AND   xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		sb.append(" AND   xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		sb.append(" AND   xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		sb.append(" AND   xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate");
		sb.append(" AND   xcgl_general_h.pk_billtype='XC15'");
		sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		sb.append(" AND   NVL(xcgl_general_b.dr,0)=0");
		if(whereSql!=null&&whereSql.length()>0){
 			String[] strs = whereSql.split("=");
 			if(strs!=null&&strs.length!=0){
 				String str = strs[1].substring(2, strs[1].length()-2);
 				//月开始日期
 				String begin = ZmPubTool.getFirstDayOfMonth(str);
 				String end = ZmPubTool.getLastDayOfMonth(str);
 				sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
 				sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
 			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_invbasdoc");
		return sb.toString();
	}
   /**
    * 季原矿加工汇总
    * @param whereSql
    * @return
 * @throws ParseException 
    */
	public static String getBusinessSql01(String whereSql) throws ParseException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT xcgl_general_h.pk_factory,");
		sb.append(" xcgl_general_b.pk_invbasdoc,");
		sb.append(" NVL(SUM(xcgl_general_b.nwetweight),0)  nwetweightquarter,");
		sb.append(" NVL(SUM(xcgl_general_b.ndryweight),0)  ncrudesquarter,");
		sb.append(" ( NVL(SUM(xcgl_general_b.nwetweight),0) -NVL(SUM(xcgl_general_b.ndryweight),0))/");
		sb.append(" NVL(SUM(xcgl_general_b.nwetweight),0)  nwaterquarter");
		sb.append(" FROM xcgl_general_h,xcgl_general_b");
		sb.append(" ,xcgl_flouryield_h,xcgl_flouryield_b");
		sb.append(" WHERE xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		sb.append(" AND  xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h");
		sb.append(" AND   xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		sb.append(" AND   xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		sb.append(" AND   xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		sb.append(" AND   xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		sb.append(" AND   xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate");
		sb.append(" AND   xcgl_general_h.pk_billtype='XC15'");
		sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		sb.append(" AND   NVL(xcgl_general_b.dr,0)=0");
		if(whereSql!=null&&whereSql.length()>0){
 			String[] strs = whereSql.split("=");
 			if(strs!=null&&strs.length!=0){
 				String str = strs[1].substring(2, strs[1].length()-2);
 				//月开始日期
 				String begin = ZmPubTool.getFirstDayOfQuarter(str);
 				String end = ZmPubTool.getLastDayOfQuarter(str);
 				sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
 				sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
 			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_invbasdoc");
		return sb.toString();
	}
   /**
    * 年原矿加工汇总
    * @param whereSql
    * @return
 * @throws ParseException 
    */
	public static String getBusinessSql02(String whereSql) throws ParseException {
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT xcgl_general_h.pk_factory,");
		sb.append(" xcgl_general_b.pk_invbasdoc,");
		sb.append(" NVL(SUM(xcgl_general_b.nwetweight),0)  nwetweightyear,");
		sb.append(" NVL(SUM(xcgl_general_b.ndryweight),0)  ncrudesyear,");
		sb.append(" ( NVL(SUM(xcgl_general_b.nwetweight),0) -NVL(SUM(xcgl_general_b.ndryweight),0))/");
		sb.append(" NVL(SUM(xcgl_general_b.nwetweight),0)  nwateryear");
		sb.append(" FROM xcgl_general_h,xcgl_general_b");
		sb.append(" ,xcgl_flouryield_h,xcgl_flouryield_b");
		sb.append(" WHERE xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		sb.append(" AND  xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h");
		sb.append(" AND   xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		sb.append(" AND   xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		sb.append(" AND   xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		sb.append(" AND   xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		sb.append(" AND   xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate");
		sb.append(" AND   xcgl_general_h.pk_billtype='XC15'");
		sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		sb.append(" AND   NVL(xcgl_general_b.dr,0)=0");
		if(whereSql!=null&&whereSql.length()>0){
 			String[] strs = whereSql.split("=");
 			if(strs!=null&&strs.length!=0){
 				String str = strs[1].substring(2, strs[1].length()-2);
 				//月开始日期
 				String begin = ZmPubTool.getFirstDayOfYear(str);
 				String end = ZmPubTool.getLastDayOfYear(str);
 				sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
 				sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
 			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_invbasdoc");
		return sb.toString();
	}
	/***
	 * 查询精粉销售的月信息
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
      public static String getBusinessSql1(String whereSql) throws ParseException {
	   StringBuffer sb=new StringBuffer();
	   sb.append(" SELECT  xcgl_general_h.pk_factory,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.flourbaspkpb+"',xcgl_general_b.ndryweight )),0) flourpb, ");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.flourbaspkzn+"',xcgl_general_b.ndryweight )),0) flourzn,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.flourbaspks+"',xcgl_general_b.ndryweight )),0) flours,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkpb+"',xcgl_general_b.ndryweight )),0) pbmetalmonth,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkzn+"',xcgl_general_b.ndryweight )),0) znmetalmonth,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkag+"',xcgl_general_b.ndryweight )),0)/1000 agmetalmonth,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkau+"',xcgl_general_b.ndryweight )),0) aumetalmonth,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspks+"',xcgl_general_b.ndryweight )),0) smetalmonth,");
	   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspksn+"',xcgl_general_b.ndryweight )),0) snmetalmonth,");
	   sb.append(" xcgl_general_b.pk_father");
	   sb.append(" FROM xcgl_general_h,xcgl_general_b");
	   sb.append(" WHERE xcgl_general_h.pk_billtype='XC24'");
	   sb.append(" AND  xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
	   sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
	   sb.append("AND   NVL(xcgl_general_b.dr,0)=0");
	   if(whereSql!=null&&whereSql.length()>0){
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//月开始日期
				String begin = ZmPubTool.getFirstDayOfMonth(str);
				String end = ZmPubTool.getLastDayOfMonth(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
			}
		}
	   sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey() + "'");
	   sb.append("GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_father");
	   
	   return sb.toString();
       }
      /**
       * 精粉的季累计销售信息
       * @param whereSql
       * @return
       * @throws ParseException
       */
	public static String getBusinessSql11(String whereSql) throws ParseException {
		   StringBuffer sb=new StringBuffer();
		   sb.append(" SELECT  xcgl_general_h.pk_factory,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkpb+"',xcgl_general_b.ndryweight )),0) flourpb, ");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkzn+"',xcgl_general_b.ndryweight )),0) flourzn,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspks+"',xcgl_general_b.ndryweight )),0) flours,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkpb+"',xcgl_general_b.ndryweight )),0) pbmetalquarter,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkzn+"',xcgl_general_b.ndryweight )),0) znmetalquarter,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkag+"',xcgl_general_b.ndryweight )),0)/1000 agmetalquarter,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkau+"',xcgl_general_b.ndryweight )),0) aumetalquarter,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspks+"',xcgl_general_b.ndryweight )),0) smetalquarter,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspksn+"',xcgl_general_b.ndryweight )),0) snmetalquarter,");
		   sb.append(" xcgl_general_b.pk_father");
		   sb.append(" FROM xcgl_general_h,xcgl_general_b");
		   sb.append(" WHERE xcgl_general_h.pk_billtype='XC24'");
		   sb.append(" AND  xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		   sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		   sb.append("AND   NVL(xcgl_general_b.dr,0)=0");
		   if(whereSql!=null&&whereSql.length()>0){
				String[] strs = whereSql.split("=");
				if(strs!=null&&strs.length!=0){
					String str = strs[1].substring(2, strs[1].length()-2);
					//季开始日期
					String begin = ZmPubTool.getFirstDayOfQuarter(str);
					String end = ZmPubTool.getLastDayOfQuarter(str);
					sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
					sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
				}
			}
		   sb.append(
					" and xcgl_general_h.pk_corp='"
					+ ClientEnvironment.getInstance().getCorporation()
					.getPrimaryKey() + "'");
		   sb.append("GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_father");
		   
		   return sb.toString();
	}
	/**
	 * 精粉的年累计销售信息
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql2(String whereSql) throws ParseException {
		   StringBuffer sb=new StringBuffer();
		   sb.append(" SELECT  xcgl_general_h.pk_factory,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkpb+"',xcgl_general_b.ndryweight )),0) flourpb, ");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkzn+"',xcgl_general_b.ndryweight )),0) flourzn,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspks+"',xcgl_general_b.ndryweight )),0) flours,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkpb+"',xcgl_general_b.ndryweight )),0) pbmetalyear,	");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkzn+"',xcgl_general_b.ndryweight )),0) znmetalyear,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkag+"',xcgl_general_b.ndryweight )),0)/1000 agmetalyear,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkau+"',xcgl_general_b.ndryweight )),0) aumetalyear,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspks+"',xcgl_general_b.ndryweight )),0) smetalyear,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspksn+"',xcgl_general_b.ndryweight )),0) snmetalyear,");
		   sb.append(" xcgl_general_b.pk_father");
		   sb.append(" FROM xcgl_general_h,xcgl_general_b");
		   sb.append(" WHERE xcgl_general_h.pk_billtype='XC24'");
		   sb.append(" AND  xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		   sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		   sb.append("AND   NVL(xcgl_general_b.dr,0)=0");
		   if(whereSql!=null&&whereSql.length()>0){
				String[] strs = whereSql.split("=");
				if(strs!=null&&strs.length!=0){
					String str = strs[1].substring(2, strs[1].length()-2);
					//年开始日期
					String begin = ZmPubTool.getFirstDayOfYear(str);
					String end = ZmPubTool.getLastDayOfYear(str);
					sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
					sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
				}
			}
		   sb.append(
					" and xcgl_general_h.pk_corp='"
					+ ClientEnvironment.getInstance().getCorporation()
					.getPrimaryKey() + "'");
		   sb.append("GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_father");
		   
		   return sb.toString();
	}
	/**
	 * 查询库存上月盘点信息
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql22(String whereSql) throws ParseException {
		 StringBuffer sb=new StringBuffer();
		   sb.append(" SELECT  xcgl_general_h.pk_factory,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkpb+"',xcgl_general_b.ndryweight )),0) flourpb, ");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkzn+"',xcgl_general_b.ndryweight )),0) flourzn,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspks+"',xcgl_general_b.ndryweight )),0) flours,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkpb+"',xcgl_general_b.ndryweight )),0) pbmetalmonth,	");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkzn+"',xcgl_general_b.ndryweight )),0) znmetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkag+"',xcgl_general_b.ndryweight )),0)/1000 agmetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkau+"',xcgl_general_b.ndryweight )),0) aumetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspks+"',xcgl_general_b.ndryweight )),0) smetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspksn+"',xcgl_general_b.ndryweight )),0) snmetalmonth,");
		   sb.append(" xcgl_general_b.pk_father");
		   sb.append(" FROM xcgl_general_h,xcgl_general_b");
		   sb.append(" WHERE xcgl_general_h.pk_billtype='XC26'");
		   sb.append(" AND  xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		   sb.append(" AND xcgl_general_h.vbillstatus='1'");
		   sb.append(" AND xcgl_general_h.itype='3'");
		   sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		   sb.append(" AND   NVL(xcgl_general_b.dr,0)=0");
		   if(whereSql!=null&&whereSql.length()>0){
				String[] strs = whereSql.split("=");
				if(strs!=null&&strs.length!=0){
					String str = strs[1].substring(2, strs[1].length()-2);
					  String lastMonth = ZmPubTool.getDateOfMontth(str, -1);
					//月开始日期
					String begin = ZmPubTool.getFirstDayOfMonth(lastMonth);
					String end = ZmPubTool.getLastDayOfMonth(lastMonth);
					sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
					sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
				}
			}
		   sb.append(
					" and xcgl_general_h.pk_corp='"
					+ ClientEnvironment.getInstance().getCorporation()
					.getPrimaryKey() + "'");
		   sb.append("GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_father");
		   
		   return sb.toString();
	}
	/**
	 * 查询库存本月盘点信息
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql21(String whereSql) throws ParseException {
		
		 StringBuffer sb=new StringBuffer();
		   sb.append(" SELECT  xcgl_general_h.pk_factory,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkpb+"',xcgl_general_b.ndryweight )),0) flourpb, ");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspkzn+"',xcgl_general_b.ndryweight )),0) flourzn,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.flourbaspks+"',xcgl_general_b.ndryweight )),0) flours,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkpb+"',xcgl_general_b.ndryweight )),0) pbmetalmonth,	");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc, '"+PubOtherConst.metalbaspkzn+"',xcgl_general_b.ndryweight )),0) znmetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkag+"',xcgl_general_b.ndryweight )),0)/1000 agmetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspkau+"',xcgl_general_b.ndryweight )),0) aumetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspks+"',xcgl_general_b.ndryweight )),0) smetalmonth,");
		   sb.append(" nvl(sum(decode(xcgl_general_b.pk_invbasdoc,'"+PubOtherConst.metalbaspksn+"',xcgl_general_b.ndryweight )),0) snmetalmonth,");
		   sb.append(" xcgl_general_b.pk_father");
		   sb.append(" FROM xcgl_general_h,xcgl_general_b");
		   sb.append(" WHERE xcgl_general_h.pk_billtype='XC26'");
		   sb.append(" AND  xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h");
		   sb.append(" AND xcgl_general_h.vbillstatus='1'");
		   sb.append(" AND xcgl_general_h.itype='3'");
		   sb.append(" AND   NVL(xcgl_general_h.dr,0)=0");
		   sb.append(" AND   NVL(xcgl_general_b.dr,0)=0");
		   if(whereSql!=null&&whereSql.length()>0){
				String[] strs = whereSql.split("=");
				if(strs!=null&&strs.length!=0){
					String str = strs[1].substring(2, strs[1].length()-2);
					//月开始日期
					String begin = ZmPubTool.getFirstDayOfMonth(str);
					String end = ZmPubTool.getLastDayOfMonth(str);
					sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
					sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
				}
			}
		   sb.append(
					" and xcgl_general_h.pk_corp='"
					+ ClientEnvironment.getInstance().getCorporation()
					.getPrimaryKey() + "'");
		   sb.append("GROUP BY xcgl_general_h.pk_factory,xcgl_general_b.pk_father");
		   
		   return sb.toString();
	}

      
      
      
      
  }
