package nc.ui.xcgl.weighcount;

import nc.ui.pub.ClientEnvironment;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 * 过磅统计
 * 
 * @author Jay
 * 
 */
public class ReportSql {
	/**
	 * 查询过磅登记和销售过磅登记
	 * 
	 * @param whereSql
	 * @return
	 */
	public static String getBusinessSql1(String whereSql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  xcgl_weighdoc.vbillno,");
		sb.append("xcgl_weighdoc.dbilldate,");
		sb.append("xcgl_weighdoc.pk_billtype,");
		sb.append("xcgl_weighdoc.vbillstatus,");
		// sb.append(" xcgl_weighdoc.pk_resetype,");
		sb.append(" xcgl_weighdoc.pk_stordoc,");
		sb.append("xcgl_weighdoc.pk_factory,");
		sb.append("xcgl_weighdoc.pk_deptdoc,");
		// sb.append("h.voperatorid,");
		// sb.append("h.vapproveid,");
		// sb.append("h.vmemo,");
		sb.append("xcgl_weighdoc_b.pk_invmandoc,");
		sb.append("xcgl_weighdoc_b.pk_invbasdoc,");
		sb.append("xcgl_weighdoc_b.pk_invbasdoc,");
		sb.append("xcgl_weighdoc_b.ngrossweight,");
		sb.append("xcgl_weighdoc_b.ntare,");
		sb.append("xcgl_weighdoc_b.nnetweight,");
		sb.append("xcgl_weighdoc_b.nwatercontent,");
		sb.append("xcgl_weighdoc_b.ndryamount,");
		sb.append("xcgl_weighdoc_b.pk_vehicle, ");
		sb.append("xcgl_weighdoc_b.* ");
	    // sb.append("xcgl_weighdoc.* ");
		// sb.append("b.ndryweight,");
		// sb.append("b.nlock,");
		// sb.append("b.crowno,");
		// sb.append("b.pk_father,");
		// sb.append("b.vcrowno,");
		// sb.append("b.vlastbilltype, ");
		// sb.append("b.vlastbillcode, ");
		// sb.append("b.vsourcebilltype, ");
		// sb.append("b.csourcebillcode ");
		sb.append("from xcgl_weighdoc  ,xcgl_weighdoc_b ");
		sb.append("where xcgl_weighdoc.pk_weighdoc=xcgl_weighdoc_b.pk_weighdoc ");
		// sb.append("and nvl(xcgl_weighdoc.dr,0)=0 ");
		// sb.append("and nvl(xcgl_weighdoc_b.dr,0)=0 ");
		if (whereSql != null && whereSql.length() > 0) {
			sb.append(" and ");
			sb.append(whereSql);
		}
		sb.append(" and xcgl_weighdoc.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "' ");
		sb.append(" and isnull(xcgl_weighdoc.dr,0)=0 and  isnull(xcgl_weighdoc_b.dr,0)=0");
		sb.append(" and xcgl_weighdoc.pk_billtype='"+PubBillTypeConst.billtype_weighdoc+"'");
		return sb.toString();
	}
}
