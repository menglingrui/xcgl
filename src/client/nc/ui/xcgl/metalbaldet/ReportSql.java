package nc.ui.xcgl.metalbaldet;

import java.text.ParseException;

import nc.ui.pub.ClientEnvironment;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * ����ƽ����ϸ
 * ʵ�ʽ���ƽ���
 * �����ѯ�������ѯԭ��Ǧ���ۣ�п���ۣ�β����Ϣ��
 * @author Jay
 */
public class ReportSql {
	/**
	 * ͳ�Ƶĵ��¼ӹ���ԭ��ʪ�غ͸���
	 * 
	 * @param whereSql
	 * @return
	 * ԭ����Ϣ
	 * @throws ParseException 
	 */
	public static String getBusinessSql(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT  SUM(xcgl_general_b.nwetweight) nwetweight,");
		
		sb.append(" SUM(xcgl_general_b.ndryweight) ncrudes,");
		
		sb.append("  xcgl_general_b.pk_invbasdoc,");
		
		sb.append("  xcgl_flouryield_h.pk_factory,");
		
		sb.append(" to_number(SUM(XCGL_GENERAL_B.NDRYWEIGHT))/to_number(SUM(XCGL_GENERAL_B.NWETWEIGHT)) nwater");

		sb.append(" FROM xcgl_general_h,xcgl_general_b,xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h ");
		
		sb.append(" AND xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h");
		
		sb.append(" AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		
		sb.append(" AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		
		sb.append(" AND xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		
		sb.append(" AND xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate");
		
		sb.append(" AND xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		
		sb.append(" AND xcgl_general_h.pk_billtype='XC15'");
		
		sb.append(" AND (nvl(XCGL_GENERAL_H.DR, 0) = 0)");
		
		sb.append(" AND (nvl(XCGL_GENERAL_B.DR, 0) = 0)");
		
		sb.append("  AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		
		sb.append("  AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfMonth(str);
				String end = ZmPubTool.getLastDayOfMonth(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
  
		sb.append(" GROUP BY xcgl_general_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * 
      SELECT xcgl_flouryield_b.pk_invbasdoc,
      nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.nmetalamount)),0) Ǧ,
	  nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.noutput )),0) Ǧ��,
      nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNL', xcgl_flouryield_b.nmetalamount)),0) п,
      nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNE', xcgl_flouryield_b.nmetalamount)),0) ��
      from xcgl_flouryield_h,xcgl_flouryield_b
	  WHERE xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h 
      AND xcgl_flouryield_b.pk_invbasdoc='0001A11000000000WAX9'
      group by  xcgl_flouryield_b.pk_invbasdoc;
	 * Ǧ����
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql1(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalmonth,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.noutput )),0) ncrudes, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalmonth,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalmonth");
		
		//sb.append(" to_number(nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.nmetalamount)),0))/to_number(nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.noutput )),0))) pbtastemonth"); //ǦƷζ

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.flourbaspkpb+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfMonth(str);
				String end = ZmPubTool.getLastDayOfMonth(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");          
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * п����
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql2(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
        sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalmonth,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.noutput )),0) ncrudes, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalmonth,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalmonth");
		

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.flourbaspkzn+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfMonth(str);
				String end = ZmPubTool.getLastDayOfMonth(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");         
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * β��
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql3(String whereSql) throws ParseException {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalmonth,");
			
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.noutput )),0) ncrudes, ");
			
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalmonth,");
			
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalmonth");

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.tailbaspkzn+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfMonth(str);
				String end = ZmPubTool.getLastDayOfMonth(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");       
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * β�����ۼ�
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql32(String whereSql) throws ParseException {
       StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalyear,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.noutput )),0) ncrudesyear, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalyear,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalyear");

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.tailbaspkzn+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfYear(str);
				String end = ZmPubTool.getLastDayOfYear(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");       
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * β���ۼ�
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql31(String whereSql) throws ParseException {
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalquarter,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.noutput )),0) ncrudesquarter, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalquarter,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalquarter");

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.tailbaspkzn+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfQuarter(str);
				String end = ZmPubTool.getLastDayOfQuarter(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");       
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 *п���۵����ۼ���Ϣ
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql22(String whereSql) throws ParseException {
		
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalyear,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.noutput )),0) ncrudesyear, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalyear,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalyear");

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.flourbaspkzn+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfYear(str);
				String end = ZmPubTool.getLastDayOfYear(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");         
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * п���۵ļ��ۼ���Ϣ
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql21(String whereSql) throws ParseException {
		
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalquarter,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.noutput )),0) ncrudesquarter, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalquarter,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalquarter");

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.flourbaspkzn+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfQuarter(str);
				String end = ZmPubTool.getLastDayOfQuarter(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");         
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * Ǧ���۵����ۼ���Ϣ
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql12(String whereSql) throws ParseException {
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalyear,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.noutput )),0) ncrudesyear, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalyear,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalyear");
		
		//sb.append(" to_number(nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.nmetalamount)),0))/to_number(nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.noutput )),0))) pbtastemonth"); //ǦƷζ

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.flourbaspkpb+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfYear(str);
				String end = ZmPubTool.getLastDayOfYear(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
         
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * Ǧ���۵ļ��ۼ���Ϣ
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSql11(String whereSql) throws ParseException {
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT    xcgl_flouryield_b.pk_invbasdoc,");
		
		sb.append(" xcgl_flouryield_h.pk_factory,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.nmetalamount)),0) pbmetalquarter,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkpb+"', xcgl_flouryield_b.noutput )),0) ncrudesquarter, ");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.flourbaspkzn+"', xcgl_flouryield_b.nmetalamount)),0) znmetalquarter,");
		
		sb.append(" nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '"+PubOtherConst.metalbaspkag+"', xcgl_flouryield_b.nmetalamount)),0)/1000 agmetalquarter");
		
		//sb.append(" to_number(nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.nmetalamount)),0))/to_number(nvl(sum(decode(xcgl_flouryield_b.pk_invindex, '0001AP1000000000NTNI', xcgl_flouryield_b.noutput )),0))) pbtastemonth"); //ǦƷζ

		sb.append(" FROM xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h ");
		
		sb.append(" and xcgl_flouryield_b.pk_invbasdoc='"+PubOtherConst.flourbaspkpb+"'");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");
		
		sb.append(" and (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0) ");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfQuarter(str);
				String end = ZmPubTool.getLastDayOfQuarter(str);
				sb.append(" and xcgl_flouryield_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_flouryield_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");        
		sb.append(" group by  xcgl_flouryield_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * ԭ��ļ��ۼ���Ϣ
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSqlQ(String whereSql) throws ParseException {
        StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT  SUM(xcgl_general_b.nwetweight) nwetweightquarter,");
		
		sb.append(" SUM(xcgl_general_b.ndryweight) ncrudesquarter,");
		
		sb.append("  xcgl_general_b.pk_invbasdoc,");
		
		sb.append("  xcgl_flouryield_h.pk_factory,");
		
		sb.append(" to_number(SUM(XCGL_GENERAL_B.NDRYWEIGHT))/to_number(SUM(XCGL_GENERAL_B.NWETWEIGHT)) nwaterquarter");

		sb.append(" FROM xcgl_general_h,xcgl_general_b,xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h ");
		
		sb.append(" AND xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h");
		
		sb.append(" AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		
		sb.append(" AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		
		sb.append(" AND xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		
		sb.append(" AND xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate");
		
		sb.append(" AND xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		
		sb.append(" AND xcgl_general_h.pk_billtype='XC15'");
		
		sb.append(" AND (nvl(XCGL_GENERAL_H.DR, 0) = 0)");
		
		sb.append(" AND (nvl(XCGL_GENERAL_B.DR, 0) = 0)");
		
		sb.append("  AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		
		sb.append("  AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//��ʼ����
				String begin = ZmPubTool.getFirstDayOfQuarter(str);
				String end = ZmPubTool.getLastDayOfQuarter(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+begin+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+end+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sb.append(" GROUP BY xcgl_general_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	/**
	 * ԭ������ۼ���Ϣ
	 * @param whereSql
	 * @return
	 * @throws ParseException 
	 */
	public static String getBusinessSqlY(String whereSql) throws ParseException {
        StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT  SUM(xcgl_general_b.nwetweight) nwetweightyear,");
		
		sb.append(" SUM(xcgl_general_b.ndryweight) ncrudesyear,");
		
		sb.append("  xcgl_general_b.pk_invbasdoc,");
		
		sb.append("  xcgl_flouryield_h.pk_factory,");
		
		sb.append(" to_number(SUM(XCGL_GENERAL_B.NDRYWEIGHT))/to_number(SUM(XCGL_GENERAL_B.NWETWEIGHT)) nwateryear");

		sb.append(" FROM xcgl_general_h,xcgl_general_b,xcgl_flouryield_h,xcgl_flouryield_b ");
		
		sb.append(" where xcgl_general_h.pk_general_h=xcgl_general_b.pk_general_h ");
		
		sb.append(" AND xcgl_flouryield_h.pk_flouryield_h=xcgl_flouryield_b.pk_flouryield_h");
		
		sb.append(" AND xcgl_general_h.pk_factory=xcgl_flouryield_h.pk_factory");
		
		sb.append(" AND xcgl_general_h.pk_classorder=xcgl_flouryield_h.pk_classorder");
		
		sb.append(" AND xcgl_general_h.pk_beltline=xcgl_flouryield_h.pk_beltline");
		
		sb.append(" AND xcgl_general_h.dbilldate=xcgl_flouryield_h.dbilldate");
		
		sb.append(" AND xcgl_general_h.pk_minarea=xcgl_flouryield_b.pk_deptdoc");
		
		sb.append(" AND xcgl_general_h.pk_corp=xcgl_flouryield_h.pk_corp");
		
		sb.append(" AND xcgl_general_h.pk_billtype='XC15'");
		
		sb.append(" AND (nvl(XCGL_GENERAL_H.DR, 0) = 0)");
		
		sb.append(" AND (nvl(XCGL_GENERAL_B.DR, 0) = 0)");
		
		sb.append("  AND (NVL(XCGL_FLOURYIELD_H.DR, 0) = 0)");
		
		sb.append("  AND (NVL(XCGL_FLOURYIELD_B.DR, 0) = 0)");

		if (whereSql != null && whereSql.length() > 0) {
			//�и��ַ���
			String[] strs = whereSql.split("=");
			if(strs!=null&&strs.length!=0){
				String str = strs[1].substring(2, strs[1].length()-2);
				//�꿪ʼ����
				String beginofYear = ZmPubTool.getFirstDayOfYear(str);
				String endofYear = ZmPubTool.getLastDayOfYear(str);
				sb.append(" and xcgl_general_h.dbilldate>='"+beginofYear+"'");
				sb.append(" and xcgl_general_h.dbilldate<='"+endofYear+"'");
			}
		}
		sb.append("and xcgl_flouryield_h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");  
		sb.append(" GROUP BY xcgl_general_b.pk_invbasdoc,xcgl_flouryield_h.pk_factory");
		return sb.toString();
	}
	
}
