package nc.ui.xcgl.journalaccount;

import nc.ui.pub.ClientEnvironment;

public class ReportSql {
	/**
	 * 入库单
	 */
	public static String getBusinessSql1(String whereSql){
		StringBuffer sb=new StringBuffer();
		sb.append("select  h.pk_corp,");
		sb.append("h.dbilldate,");
		sb.append("h.pk_billtype dbilltype,");
		sb.append("h.vbillstatus vbillstatus,");
		sb.append(" h.pk_resetype pk_resetype,");
		sb.append(" h.pk_stordoc,");
		sb.append("h.pk_factory,");
		sb.append("h.pk_minarea,");
		sb.append("h.voperatorid,");
		sb.append("h.vapproveid,");
		sb.append("h.vmemo,");
		sb.append("b.pk_invmandoc,");
		sb.append("b.pk_invbasdoc,");
		sb.append("b.ndryweight weighin,");
		sb.append("b.nlock,");
		sb.append("b.crowno,");
		sb.append("b.pk_father,");
		sb.append("b.vcrowno,");
		sb.append("b.vlastbilltype, ");
		sb.append("b.vlastbillcode, ");
		sb.append("b.vsourcebilltype, ");
		sb.append("b.csourcebillcode ");
		sb.append("from xcgl_general_h h ,xcgl_general_b b ");
		sb.append("where h.pk_general_h=b.pk_general_h ");
		sb.append("and nvl(h.dr,0)=0 ");
	    sb.append("and nvl(b.dr,0)=0 ");
	    if(whereSql!=null&&whereSql.length()>0){
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append("and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		return sb.toString();
	}
	/**
	 * 出库单
	 * @param whereSql
	 * @return
	 */
	public static String getBusinessSql2(String whereSql){
		StringBuffer sb=new StringBuffer();
		sb.append("select  h.pk_corp,");
		sb.append("h.dbilldate,");
		sb.append("h.pk_billtype dbilltype,");
		sb.append("h.vbillstatus vbillstatus,");
		sb.append(" h.pk_resetype pk_resetype,");
		sb.append(" h.pk_stordoc,");
		sb.append("h.pk_factory,");
		sb.append("h.pk_minarea,");
		sb.append("h.voperatorid,");
		sb.append("h.vapproveid,");
		sb.append("h.vmemo,");
		sb.append("b.pk_invmandoc,");
		sb.append("b.pk_invbasdoc,");
		sb.append("b.ndryweight weightout,");
		sb.append("b.nlock,");
		sb.append("b.crowno,");
		sb.append("b.pk_father,");
		sb.append("b.vcrowno,");
		sb.append("b.vlastbilltype, ");
		sb.append("b.vlastbillcode, ");
		sb.append("b.vsourcebilltype, ");
		sb.append("b.csourcebillcode ");
		sb.append("from xcgl_general_h h ,xcgl_general_b b ");
		sb.append("where h.pk_general_h=b.pk_general_h ");
		sb.append("and nvl(h.dr,0)=0 ");
	    sb.append("and nvl(b.dr,0)=0 ");
	    if(whereSql!=null&&whereSql.length()>0){
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append("and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		return sb.toString();
	}
}
