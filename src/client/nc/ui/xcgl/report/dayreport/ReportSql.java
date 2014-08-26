package nc.ui.xcgl.report.dayreport;

import nc.ui.pub.ClientEnvironment;

/**
 * 选厂日报
 * 
 * @author Jay
 * 
 */
public class ReportSql {	
	/**
	 * 构建报表基本数据
	 */
	public static String getBaseSql(String wheresql){
		StringBuffer sql=new StringBuffer();
		//	选场，                        班次，                     开机时间,           原矿,  处理量湿量(吨）,原矿水分(%),处理量干量(吨）
		//pk_factory，pk_classorder，starhours,  pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
		sql.append(" select ");
		sql.append(" h.pk_factory pk_factory, ");//--选厂
		sql.append(" h.pk_beltline pk_beltline,");//生产线
		sql.append(" h.dbilldate dbilldate,");//日期
		sql.append(" h.pk_classorder pk_classorder,");// --班次
		sql.append(" b.pk_minarea pk_minarea,");//部门
		sql.append(" b.starhours starhours,");//开机时间	
		sql.append(" h.vreserve1,");//矿石类型
		sql.append(" b.pk_oreinvmandoc pk_oreinvmandoc,");//--存货管理主键(原矿)
		sql.append(" b.pk_oreinvbasdoc pk_oreinvbasdoc,");//--存货基本主键(原矿)	
		sql.append(" h.pk_invmandoc ,");//--存货管理主键(精粉,尾矿)
		sql.append(" h.pk_invbasdoc ,");//--存货基本主键(精粉,尾矿)	
		sql.append(" b.nwetnum nwetnum,");//--湿重
		sql.append(" b.nwater nwater,");//--水分
		sql.append(" b.ndrynum ndrynum, ");//--干重
		//产出量（吨）  ,Pb(%),Zn(%),Ag(g/t)---->铅精粉
		sql.append(" h.pk_manindex,");//--指标
		sql.append(" h.pk_invindex,");//--指标
		sql.append(" h.ncrudescontent,");//原矿品位
		sql.append(" h.ncontent,");//品位
		sql.append(" h.noutput,");//--产量
		sql.append(" h.nmetalamount,");//--金属量
		sql.append(" h.nrecover ,");//--回收率
		sql.append(" b.pk_factory pk_factory, ");//--选厂
		sql.append(" b.pk_beltline pk_beltline,");//生产线
		sql.append(" b.pk_classorder pk_classorder,");// --班次
		sql.append(" b.vreserve1");//矿石类型	
		sql.append(" from "+getFlouryieldBaseSql(wheresql)+" ");
		sql.append(" join "+getBaseOreProcessingSql(wheresql)+"  ");
//		public static String[] mapkey={"pk_corp","pk_factory","pk_beltline","pk_classorder",
//	        "pk_minarea","pk_oreinvmandoc","vreserve1","dbilldate"};
		sql.append(" on  h.pk_corp=b.pk_corp");
		sql.append(" and h.pk_factory=b.pk_factory ");
		sql.append(" and h.pk_beltline=b.pk_beltline ");
		sql.append(" and h.pk_classorder=b.pk_classorder  ");
		sql.append(" and h.pk_minarea=h.pk_minarea ");
		sql.append(" and h.pk_oreinvmandoc=b.pk_oreinvmandoc ");
		sql.append(" and h.vreserve1=b.vreserve1 ");
		sql.append(" and h.dbilldate=b.dbilldate ");
		sql.append(" ");
		sql.append(" ");
		return sql.toString();
	}
	
	/**
	 * 取计算数据
	 */
	public static String getFlouryieldBaseSql(String wheresql){
		StringBuffer sql=new StringBuffer();
		//	选场，                        班次，                  开机时间,           原矿,  处理量湿量(吨）,原矿水分(%),处理量干量(吨）
		//pk_factory，pk_classorder，starhours,  pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
//		public static String[] mapkey={"pk_corp","pk_factory","pk_beltline","pk_classorder",
//        "pk_minarea","pk_oreinvmandoc","vreserve1","dbilldate"};
		sql.append(" (select  ");//--生产线
		sql.append(" xcgl_flouryield_h.pk_corp pk_corp, ");//--公司
		sql.append(" xcgl_flouryield_h.pk_factory pk_factory, ");//--选厂
		sql.append(" xcgl_flouryield_h.pk_beltline pk_beltline,");//生产线
		sql.append(" xcgl_flouryield_h.pk_classorder pk_classorder,");// --班次
		sql.append(" xcgl_flouryield_b.pk_deptdoc pk_minarea,");//部门
		sql.append(" xcgl_flouryield_b.vdef20 pk_oreinvmandoc,");//--存货管理主键(原矿)
		sql.append(" xcgl_flouryield_b.pk_defdoc20 pk_oreinvbasdoc,");//--存货基本主键(原矿)
		sql.append(" xcgl_flouryield_h.vreserve1,");//矿石类型
		sql.append(" xcgl_flouryield_h.dbilldate dbilldate,");//日期
		sql.append(" xcgl_flouryield_b.pk_invmandoc,");//--存货管理主键(精粉，尾矿)
		sql.append(" xcgl_flouryield_b.pk_invbasdoc,");//--存货基本主键(精粉，尾矿)
		//产出量（吨）  ,Pb(%),Zn(%),Ag(g/t)---->铅精粉
		sql.append(" xcgl_flouryield_b.pk_manindex,");//--指标
		sql.append(" xcgl_flouryield_b.pk_invindex,");//--指标
		sql.append(" xcgl_flouryield_b.ncrudescontent,");//原矿品位
		sql.append(" xcgl_flouryield_b.ncontent,");//品位
		sql.append(" xcgl_flouryield_b.noutput,");//--产量
		sql.append(" xcgl_flouryield_b.nmetalamount,");//--金属量
		sql.append(" xcgl_flouryield_b.nrecover, ");//--回收率
		sql.append(" xcgl_flouryield_h.pk_flouryield_h,");
		sql.append(" xcgl_flouryield_b.pk_flouryield_b");
        sql.append(" FROM xcgl_flouryield_h  ");
		sql.append(" join xcgl_flouryield_b on xcgl_flouryield_h.pk_flouryield_h =xcgl_flouryield_b.pk_flouryield_h ");
		sql.append(" where isnull(xcgl_flouryield_h.dr,0)=0");
		sql.append(" and isnull(xcgl_flouryield_b.dr,0)=0");
		sql.append(" and xcgl_flouryield_h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		sql.append(" and "+wheresql+") h");
		sql.append(" ");
		sql.append(" ");
		return sql.toString();
	}	
	/**
	 * 取原矿加工出库
	 */
	public static String getBaseOreProcessingSql(String wheresql){
		StringBuffer sql=new StringBuffer();
		//	选场，                       班次，                    开机时间,      原矿,       处理量湿量(吨）,原矿水分(%),处理量干量(吨）
		//pk_factory，pk_classorder，starhours,  pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
		sql.append(" (select  ");
		sql.append(" xcgl_general_h.pk_corp pk_corp, ");//--公司
		sql.append(" xcgl_general_h.pk_factory pk_factory, ");//--选厂
		sql.append(" xcgl_general_h.dbilldate ,");//日期
		sql.append(" xcgl_general_h.pk_beltline pk_beltline,");//生产线
		sql.append(" xcgl_general_h.pk_classorder pk_classorder,");// --班次
		sql.append(" xcgl_general_h.pk_minarea pk_minarea,");//部门
		sql.append(" xcgl_general_h.vreserve1,");//矿石类型
		sql.append(" xcgl_general_h.starthours starhours,");//开机时间	
		sql.append(" xcgl_general_b.pk_invmandoc pk_oreinvmandoc,");//--存货管理主键(原矿,精粉，尾矿)
		sql.append(" xcgl_general_b.pk_invbasdoc pk_oreinvbasdoc,");//--存货基本主键(原矿,精粉，尾矿)	
		sql.append(" xcgl_general_b.nwetweight nwetnum,");//--湿重
		sql.append(" xcgl_general_b.nwatercontent nwater,");//--水分
		sql.append(" xcgl_general_b.ndryweight ndrynum, ");//--干重
		sql.append(" xcgl_general_h.pk_general_h,");
		sql.append(" xcgl_general_b.pk_general_b");
        sql.append(" FROM xcgl_general_h  ");
		sql.append(" join xcgl_general_b on xcgl_general_h.pk_general_h =xcgl_general_b.pk_general_h ");
		sql.append(" where isnull(xcgl_general_h.dr,0)=0");
		sql.append(" and isnull(xcgl_general_b.dr,0)=0");
		sql.append(" and xcgl_general_h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		sql.append(" and "+wheresql+") b");
		sql.append(" ");
		sql.append(" ");
		return sql.toString();
	}

}
