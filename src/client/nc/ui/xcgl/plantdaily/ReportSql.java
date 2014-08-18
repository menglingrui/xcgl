package nc.ui.xcgl.plantdaily;

import java.text.ParseException;

import nc.ui.pub.ClientEnvironment;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 选厂日报
 * 
 * @author Jay
 * 
 */
public class ReportSql {
	/**
	 * SELECT xcgl_flouryield_h.pk_beltline, --生产线
              xcgl_general_h.dbilldate, --单据日期
              xcgl_general_h.pk_factory, --选厂
              xcgl_general_h.pk_classorder, --班次
	          xcgl_general_b.nwetweight,--湿重
			  xcgl_general_b.nwatercontent,--水分
			  xcgl_general_b.ndryweight,--干重
			  xcgl_flouryield_b.noutput,--产量
			  xcgl_flouryield_b.nrecover,--回收率
			  xcgl_flouryield_b.pk_invmandoc,--存货管理主键
			  xcgl_flouryield_b.pk_invbasdoc,--存货基本主键
			  xcgl_flouryield_b.pk_manindex,--主指标主键
			  xcgl_flouryield_b.nmetalamount--金属量
              FROM xcgl_general_h  
       JOIN xcgl_general_b ON xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h 
       JOIN xcgl_flouryield_h ON xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate 
       JOIN xcgl_flouryield_b ON  xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc
       WHERE  xcgl_general_h.pk_billtype='XC15'AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory
       AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder
       AND (NVL(xcgl_flouryield_h.DR, 0) = 0)
	   AND (NVL(xcgl_flouryield_b.DR, 0) = 0)
	   AND (NVL(xcgl_general_h.DR, 0) = 0)
	   AND (NVL(xcgl_general_b.DR, 0) = 0)
	   AND xcgl_general_h.dbilldate='2014-05-24';
	   SELECT  XCGL_GENERAL_H.PK_MINAREA,
       XCGL_GENERAL_H.PK_BELTLINE,
       XCGL_GENERAL_H.PK_FACTORY,
       XCGL_GENERAL_H.PK_CLASSORDER,
       XCGL_GENERAL_B.PK_INVMANDOC,
       XCGL_GENERAL_B.PK_INVBASDOC,
       XCGL_GENERAL_B.NWETWEIGHT,
       XCGL_GENERAL_B.NWATERCONTENT,
       XCGL_GENERAL_B.NDRYWEIGHT,
       XCGL_FLOURYIELD_B.PK_INVMANDOC PK_INVMANDOC1,
       XCGL_FLOURYIELD_B.PK_INVBASDOC PK_INVBASDOC1,
       XCGL_FLOURYIELD_B.PK_MANINDEX,
       XCGL_FLOURYIELD_B.PK_INVINDEX,
       XCGL_FLOURYIELD_B.NOUTPUT,
       XCGL_FLOURYIELD_B.NRECOVER,
       XCGL_FLOURYIELD_B.NMETALAMOUNT
  FROM XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B
 WHERE XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H
   AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H = XCGL_FLOURYIELD_B.Pk_Flouryield_h
	 AND xcgl_general_h.pk_corp='1020'
	 AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory
	 AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder
	 AND xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline
	 AND xcgl_general_h.pk_billtype='XC15'
	 AND (nvl(XCGL_GENERAL_H.DR, 0) = 0)
   AND (nvl(XCGL_GENERAL_B.DR, 0) = 0)
	 AND (nvl(Xcgl_Flouryield_h.DR, 0) = 0)
   AND (nvl(Xcgl_Flouryield_b.DR, 0) = 0)
	  AND XCGL_FLOURYIELD_B.NOUTPUT > 0
	   xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc
	 * 
	 * @param whereSql
	 * @return
	 */
//	public static String getBusinessSql1(String whereSql) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("select  xcgl_general_h.pk_minarea,");//部门
//		sb.append(" xcgl_general_h.pk_beltline,");//生产线
//		sb.append(" xcgl_general_h.pk_factory,");//选厂
//		sb.append(" xcgl_general_h.pk_classorder,");//班次
//		sb.append(" xcgl_general_b.pk_invmandoc,");
//		sb.append(" xcgl_general_b.pk_invbasdoc,");
//		sb.append(" xcgl_general_b.nwetweight,");//湿重
//		sb.append(" xcgl_general_b.nwatercontent,");//水分
//		sb.append(" xcgl_general_b.ndryweight, ");//干重
//		sb.append(" xcgl_flouryield_b.pk_invmandoc pk_invmandoc1,");
//		sb.append(" xcgl_flouryield_b.pk_invbasdoc pk_invbasdoc1,");
//		sb.append(" xcgl_flouryield_b.pk_manindex,");
//		sb.append(" xcgl_flouryield_b.pk_invindex,");
//		sb.append(" xcgl_flouryield_b.noutput,");
//		sb.append(" xcgl_flouryield_b.nrecover,");
//		sb.append(" xcgl_flouryield_b.nmetalamount ");
//		sb.append(" from xcgl_general_h ");
//		sb.append(" JOIN xcgl_general_b ON xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h ");
//		sb.append(" JOIN xcgl_flouryield_h ON xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate ");
//		sb.append(" JOIN xcgl_flouryield_b ON  xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
//		sb.append(" WHERE  xcgl_general_h.pk_billtype='XC15'");
//		sb.append(" AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
//		sb.append(" AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
//		sb.append(" AND (NVL(xcgl_flouryield_h.DR, 0) = 0)");
//		sb.append(" AND (NVL(xcgl_flouryield_b.DR, 0) = 0)");
//		sb.append(" and xcgl_flouryield_b.noutput>0");
//		if (whereSql != null && whereSql.length() > 0) {
//			sb.append(" and ");
//			sb.append(whereSql);
//		}
//		sb.append(
//				" and xcgl_general_h.pk_corp='"
//				+ ClientEnvironment.getInstance().getCorporation()
//						.getPrimaryKey() + "'");
//		return sb.toString();
//	}
	/**
	 * SELECT  XCGL_GENERAL_H.PK_MINAREA,
       XCGL_GENERAL_H.PK_BELTLINE,
       XCGL_GENERAL_H.PK_FACTORY,
       XCGL_GENERAL_H.PK_CLASSORDER,
       XCGL_GENERAL_B.PK_INVMANDOC,
       XCGL_GENERAL_B.PK_INVBASDOC,
       XCGL_GENERAL_B.NWETWEIGHT,
       XCGL_GENERAL_B.NWATERCONTENT,
       XCGL_GENERAL_B.NDRYWEIGHT,
       XCGL_FLOURYIELD_B.PK_INVMANDOC PK_INVMANDOC1,
       XCGL_FLOURYIELD_B.PK_INVBASDOC PK_INVBASDOC1,
       XCGL_FLOURYIELD_B.PK_MANINDEX,
       XCGL_FLOURYIELD_B.PK_INVINDEX,
       XCGL_FLOURYIELD_B.NOUTPUT,
       XCGL_FLOURYIELD_B.NRECOVER,
       XCGL_FLOURYIELD_B.NMETALAMOUNT
  FROM XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B
 WHERE XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H
   AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H = XCGL_FLOURYIELD_B.Pk_Flouryield_h
	 AND xcgl_general_h.pk_corp='1020'
	 AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory
	 AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder
	 AND xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline
	 AND xcgl_general_h.pk_billtype='XC15'
	 AND (nvl(XCGL_GENERAL_H.DR, 0) = 0)
   AND (nvl(XCGL_GENERAL_B.DR, 0) = 0)
	 AND (nvl(Xcgl_Flouryield_h.DR, 0) = 0)
   AND (nvl(Xcgl_Flouryield_b.DR, 0) = 0)
	  AND XCGL_FLOURYIELD_B.NOUTPUT > 0
		AND  xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc
	 * @param whereSql
	 * @return
	 */
	public static String getBusinessSql1(String whereSql) {
		//铅存货基本主键 pk_invbasdoc 0001AP1000000000NTNI
		StringBuffer sb = new StringBuffer();
		sb.append("select  xcgl_general_h.pk_minarea,");//部门
		sb.append(" xcgl_general_h.pk_beltline,");//生产线
		sb.append("xcgl_general_h.dbilldate,");//日期
		sb.append(" xcgl_general_h.pk_factory,");//选厂
		sb.append(" xcgl_general_h.pk_classorder,");//班次
		sb.append(" xcgl_general_b.pk_invmandoc,");
		sb.append(" xcgl_general_b.pk_invbasdoc,");
		sb.append(" xcgl_general_b.nwetweight,");//湿重
		sb.append(" xcgl_general_b.nwatercontent,");//水分
		sb.append(" xcgl_general_b.ndryweight, ");//干重
		sb.append(" xcgl_flouryield_b.pk_invmandoc pk_invmandoc1,");
		sb.append(" xcgl_flouryield_b.pk_invbasdoc pk_invbasdoc1,");
		sb.append(" xcgl_flouryield_b.pk_manindex,");
		sb.append(" xcgl_flouryield_b.pk_invindex,");
		sb.append(" xcgl_flouryield_b.noutput,");
		sb.append(" xcgl_flouryield_b.nrecover,");
		sb.append(" xcgl_flouryield_b.nmetalamount, ");
		sb.append(" xcgl_flouryield_b.ncrudescontent,");
		sb.append(" xcgl_flouryield_b.ncontent,");
		sb.append(" xcgl_flouryield_b.ncontent1,");
		sb.append(" xcgl_flouryield_b.ncontent2,");
		sb.append(" xcgl_flouryield_b.ncontent3");
		sb.append(" from XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B");
		sb.append(" WHERE  xcgl_general_h.pk_billtype='XC15'");
		sb.append(" and  xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h ");
		sb.append(" AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H = XCGL_FLOURYIELD_B.Pk_Flouryield_h");
		sb.append(" and  xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate ");
		sb.append(" and  xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		sb.append(" AND  xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		sb.append(" AND  xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		sb.append(" AND  xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		sb.append(" AND (NVL(xcgl_flouryield_h.DR, 0) = 0)");
		sb.append(" AND (NVL(xcgl_flouryield_b.DR, 0) = 0)");
		//sb.append(" and  xcgl_flouryield_b.noutput>0");
		//回收率大于0的控制
		//sb.append(" and xcgl_flouryield_b.nrecover>0");
		if (whereSql != null && whereSql.length() > 0) {
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		return sb.toString();
	}

	/**
	 * 求月合计数据(只有精粉和金属量没有原矿的合计信息)
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql2(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		//sb.append(" xcgl_general_h.dbilldate,");
    	sb.append(" XCGL_FLOURYIELD_B.PK_INVBASDOC,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVINDEX,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkpb+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkzn+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalzn,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkag+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0)/1000 metalag,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkpb+"',XCGL_FLOURYIELD_B.Noutput)),0)  noutputpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkzn+"',XCGL_FLOURYIELD_B.Noutput)),0)  noutputzn");
		sb.append(" FROM XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B");
		sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
		sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
		sb.append(" AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H =XCGL_FLOURYIELD_B.PK_FLOURYIELD_H");
		sb.append(" AND XCGL_GENERAL_H.DBILLDATE = XCGL_FLOURYIELD_H.DBILLDATE");
		sb.append(" AND XCGL_GENERAL_H.PK_MINAREA = XCGL_FLOURYIELD_B.PK_DEPTDOC");
		sb.append(" AND XCGL_GENERAL_H.PK_FACTORY = XCGL_FLOURYIELD_H.PK_FACTORY");
		sb.append(" AND XCGL_GENERAL_H.PK_BELTLINE = XCGL_FLOURYIELD_H.PK_BELTLINE");
		sb.append(" AND XCGL_GENERAL_H.PK_CLASSORDER = XCGL_FLOURYIELD_H.PK_CLASSORDER");
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
//		sb.append("");
		if (whereSql != null && whereSql.length() > 0) {
//			sb.append(" and ");
//			sb.append(whereSql);
			//切割字符串
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//月开始日期
				String beginofMonth = ZmPubTool.getFirstDayOfMonth(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+beginofMonth+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+str+"'");
			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_minarea,xcgl_general_h.pk_beltline,xcgl_general_h.pk_factory," +
				  "	XCGL_FLOURYIELD_B.PK_INVbasdoc,xcgl_flouryield_b.pk_invindex ");
		return sb.toString();
	}
	public static String getBusinessSql21(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT   XCGL_GENERAL_H.PK_MINAREA,");
		 sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		 sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		 sb.append(" nvl(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) NWETWEIGHT,");
		 sb.append(" nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0) NDRYWEIGHT,");
		 sb.append(" (NVL(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) - nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0)) /SUM(XCGL_GENERAL_B.NDRYWEIGHT) NWATERCONTENT");
         sb.append(" FROM XCGL_GENERAL_H,XCGL_GENERAL_B");
         sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
         sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
         sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
         sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
         if (whereSql != null && whereSql.length() > 0) {
// 			sb.append(" and ");
// 			sb.append(whereSql);
 			//切割字符串
 			String[] strs = whereSql.split("=");
 			if(strs!=null&&strs.length!=0){
 				String str = strs[1].substring(2, strs[1].length()-2);
 				//月开始日期
 				String beginofMonth = ZmPubTool.getFirstDayOfMonth(str);
 				sb.append(" and xcgl_general_h.dbilldate>='"+beginofMonth+"'");
 				sb.append(" and xcgl_general_h.dbilldate<='"+str+"'");
 			}
 		}
 		sb.append(
 				" and xcgl_general_h.pk_corp='"
 				+ ClientEnvironment.getInstance().getCorporation()
 						.getPrimaryKey() + "'");
 		sb.append(" GROUP BY XCGL_GENERAL_H.PK_MINAREA,XCGL_GENERAL_H.PK_BELTLINE,XCGL_GENERAL_H.PK_FACTORY");
		return sb.toString();
	}
    /**
     * 年合计原矿信息
     * @param whereSql
     * @return
     * @throws ParseException 
     */
	public static String getBusinessSql41(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT   XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		sb.append(" nvl(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) NWETWEIGHT,");
		sb.append(" nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0) NDRYWEIGHT,");
		sb.append(" (NVL(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) - nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0)) /SUM(XCGL_GENERAL_B.NDRYWEIGHT) NWATERCONTENT");
        sb.append(" FROM XCGL_GENERAL_H,XCGL_GENERAL_B");
        sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
        sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
        sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
        sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
        if (whereSql != null && whereSql.length() > 0) {
//			sb.append(" and ");
//			sb.append(whereSql);
			//切割字符串
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//年开始日期
				String beginofMonth = ZmPubTool.getFirstDayOfYear(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+beginofMonth+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+str+"'");
			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY XCGL_GENERAL_H.PK_MINAREA,XCGL_GENERAL_H.PK_BELTLINE,XCGL_GENERAL_H.PK_FACTORY");
		return sb.toString();
	}
    /**
     * 年合计信息
     * @param whereSql
     * @return
     * @throws ParseException
     */
	public static String getBusinessSql4(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		//sb.append(" xcgl_general_h.dbilldate,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVBASDOC,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVINDEX,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkpb+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkzn+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalzn,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkag+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0)/1000 metalag,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkpb+"',XCGL_FLOURYIELD_B.Noutput)),0)  noutputpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkzn+"',XCGL_FLOURYIELD_B.Noutput)),0)  noutputzn");
		sb.append(" FROM XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B");
		sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
		sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
		sb.append(" AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H =XCGL_FLOURYIELD_B.PK_FLOURYIELD_H");
		sb.append(" AND XCGL_GENERAL_H.DBILLDATE = XCGL_FLOURYIELD_H.DBILLDATE");
		sb.append(" AND XCGL_GENERAL_H.PK_MINAREA = XCGL_FLOURYIELD_B.PK_DEPTDOC");
		sb.append(" AND XCGL_GENERAL_H.PK_FACTORY = XCGL_FLOURYIELD_H.PK_FACTORY");
		sb.append(" AND XCGL_GENERAL_H.PK_BELTLINE = XCGL_FLOURYIELD_H.PK_BELTLINE");
		sb.append(" AND XCGL_GENERAL_H.PK_CLASSORDER = XCGL_FLOURYIELD_H.PK_CLASSORDER");
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
//		sb.append("");
		if (whereSql != null && whereSql.length() > 0) {
//			sb.append(" and ");
//			sb.append(whereSql);
			//切割字符串
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//年开始日期
				String beginofMonth = ZmPubTool.getFirstDayOfYear(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+beginofMonth+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+str+"'");
			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_minarea,xcgl_general_h.pk_beltline,xcgl_general_h.pk_factory," +
				  "	XCGL_FLOURYIELD_B.PK_INVbasdoc,xcgl_flouryield_b.pk_invindex");
		return sb.toString();
	}
    /**
     * 季合计原矿信息
     * @param whereSql
     * @return
     * @throws ParseException 
     */
	public static String getBusinessSql31(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT   XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		sb.append(" nvl(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) NWETWEIGHT,");
		sb.append(" nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0) NDRYWEIGHT,");
		sb.append(" (NVL(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) - nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0)) /SUM(XCGL_GENERAL_B.NDRYWEIGHT) NWATERCONTENT");
        sb.append(" FROM XCGL_GENERAL_H,XCGL_GENERAL_B");
        sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
        sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
        sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
        sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
        if (whereSql != null && whereSql.length() > 0) {
//			sb.append(" and ");
//			sb.append(whereSql);
			//切割字符串
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//月开始日期
				String beginofMonth = ZmPubTool.getFirstDayOfQuarter(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+beginofMonth+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+str+"'");
			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY XCGL_GENERAL_H.PK_MINAREA,XCGL_GENERAL_H.PK_BELTLINE,XCGL_GENERAL_H.PK_FACTORY");
		return sb.toString();
	}
     /**
      * 季合计信息
      * @param whereSql
      * @return
      * @throws ParseException
      */
	public static String getBusinessSql3(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		//sb.append(" xcgl_general_h.dbilldate,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVBASDOC,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVINDEX,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkpb+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkzn+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalzn,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkag+"',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0)/1000 metalag,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkpb+"',XCGL_FLOURYIELD_B.Noutput)),0)  noutputpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'"+PubOtherConst.metalbaspkzn+"',XCGL_FLOURYIELD_B.Noutput)),0)  noutputzn");
		sb.append(" FROM XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B");
		sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
		sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
		sb.append(" AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H =XCGL_FLOURYIELD_B.PK_FLOURYIELD_H");
		sb.append(" AND XCGL_GENERAL_H.DBILLDATE = XCGL_FLOURYIELD_H.DBILLDATE");
		sb.append(" AND XCGL_GENERAL_H.PK_MINAREA = XCGL_FLOURYIELD_B.PK_DEPTDOC");
		sb.append(" AND XCGL_GENERAL_H.PK_FACTORY = XCGL_FLOURYIELD_H.PK_FACTORY");
		sb.append(" AND XCGL_GENERAL_H.PK_BELTLINE = XCGL_FLOURYIELD_H.PK_BELTLINE");
		sb.append(" AND XCGL_GENERAL_H.PK_CLASSORDER = XCGL_FLOURYIELD_H.PK_CLASSORDER");
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
//		sb.append("");
		if (whereSql != null && whereSql.length() > 0) {
//			sb.append(" and ");
//			sb.append(whereSql);
			//切割字符串
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//季开始日期
				String beginofMonth = ZmPubTool.getFirstDayOfQuarter(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+beginofMonth+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+str+"'");
			}
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_minarea,xcgl_general_h.pk_beltline,xcgl_general_h.pk_factory," +
				  "	XCGL_FLOURYIELD_B.PK_INVbasdoc,xcgl_flouryield_b.pk_invindex");
		return sb.toString();
	}
    /**
     * 日合计信息
     * @param whereSql
     * @return
     */
	public static String getBusinessSql5(String whereSql) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		//sb.append(" xcgl_general_h.dbilldate,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVBASDOC,");
		sb.append(" XCGL_FLOURYIELD_B.PK_INVINDEX,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'0001AP1000000000YP2U',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'0001AP1000000000YP2W',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0) metalzn,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'0001AP1000000000YP2S',XCGL_FLOURYIELD_B.NMETALAMOUNT)),0)/1000 metalag,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'0001AP1000000000YP2U',XCGL_FLOURYIELD_B.Noutput)),0)  noutputpb,");
		sb.append(" NVL(SUM(DECODE(XCGL_FLOURYIELD_B.PK_INVINDEX,'0001AP1000000000YP2W',XCGL_FLOURYIELD_B.Noutput)),0)  noutputzn");
		sb.append(" FROM XCGL_GENERAL_H, XCGL_GENERAL_B, XCGL_FLOURYIELD_H, XCGL_FLOURYIELD_B");
		sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
		sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
		sb.append(" AND XCGL_FLOURYIELD_H.PK_FLOURYIELD_H =XCGL_FLOURYIELD_B.PK_FLOURYIELD_H");
		sb.append(" AND XCGL_GENERAL_H.DBILLDATE = XCGL_FLOURYIELD_H.DBILLDATE");
		sb.append(" AND XCGL_GENERAL_H.PK_MINAREA = XCGL_FLOURYIELD_B.PK_DEPTDOC");
		sb.append(" AND XCGL_GENERAL_H.PK_FACTORY = XCGL_FLOURYIELD_H.PK_FACTORY");
		sb.append(" AND XCGL_GENERAL_H.PK_BELTLINE = XCGL_FLOURYIELD_H.PK_BELTLINE");
		sb.append(" AND XCGL_GENERAL_H.PK_CLASSORDER = XCGL_FLOURYIELD_H.PK_CLASSORDER");
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
		sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");

		if (whereSql != null && whereSql.length() > 0) {
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_h.pk_minarea,xcgl_general_h.pk_beltline,xcgl_general_h.pk_factory," +
				  "	XCGL_FLOURYIELD_B.PK_INVbasdoc,xcgl_flouryield_b.pk_invindex");
		return sb.toString();
	}
    /**
     * 日合计原矿信息
     * @param whereSql
     * @return
     */
	public static String getBusinessSql51(String whereSql) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT   XCGL_GENERAL_H.PK_MINAREA,");
		sb.append(" XCGL_GENERAL_H.PK_BELTLINE,");
		sb.append(" XCGL_GENERAL_H.PK_FACTORY,");
		sb.append(" nvl(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) NWETWEIGHT,");
		sb.append(" nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0) NDRYWEIGHT,");
		sb.append(" (NVL(SUM(XCGL_GENERAL_B.NWETWEIGHT),0) - nvl(SUM(XCGL_GENERAL_B.NDRYWEIGHT),0)) /SUM(XCGL_GENERAL_B.NDRYWEIGHT) NWATERCONTENT");
        sb.append(" FROM XCGL_GENERAL_H,XCGL_GENERAL_B");
        sb.append(" WHERE XCGL_GENERAL_H.PK_BILLTYPE = 'XC15'");
        sb.append(" AND XCGL_GENERAL_H.PK_GENERAL_H = XCGL_GENERAL_B.PK_GENERAL_H");
        sb.append(" AND (NVL(XCGL_GENERAL_H.DR, 0) = 0)");
        sb.append(" AND (NVL(XCGL_GENERAL_B.DR, 0) = 0)");
        if (whereSql != null && whereSql.length() > 0) {
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append(
				" and xcgl_general_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY XCGL_GENERAL_H.PK_MINAREA,XCGL_GENERAL_H.PK_BELTLINE,XCGL_GENERAL_H.PK_FACTORY");
		return sb.toString();
	}
}
