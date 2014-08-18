package nc.ui.xcgl.report.genstordetails;

import nc.ui.pub.ClientEnvironment;

public class ReportSql {
	/**
	 * 查询矿区入库单和精矿入库单
	 * @param whereSql
	 * @return
	 */
	public static String getBusinessSql1(String whereSql) {
		StringBuffer sb=new StringBuffer();
		sb.append("select ");
		sb.append("h.vbillno,");
		sb.append("h.pk_billtype,");
		sb.append("h.dbilldate,");
		sb.append("h.pk_minarea,");
		sb.append("h.pk_factory,");
		sb.append("h.pk_stordoc,");
		sb.append("h.pk_vehicle,");
		sb.append("b.pk_invbasdoc,");
		sb.append("b.pk_invmandoc,");
		sb.append("b.nwetweight");
		sb.append(" from ");
		sb.append("xcgl_general_h h, xcgl_general_b b");
		sb.append(" where ");
		sb.append("(h.pk_general_h=b.pk_general_h)");
		sb.append(" and ");
		sb.append("(h.pk_billtype='XC18'  or h.pk_billtype='XC21' )" ); // or h.pk_billtype='XC21'
		sb.append(" and isnull(h.dr,0)=0");
		sb.append(" and isnull(b.dr,0)=0 ");
		if(whereSql!=null&&whereSql.length()==0){
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append("and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		return sb.toString();
	}
	
	
}
