package nc.ui.xcgl.runrecord;

import nc.ui.pub.ClientEnvironment;

public class Reportsql {
	/**
	 * 查询设备运行记录
     */	
	public static String getSql(String whereSql) throws Exception{	
		StringBuffer sql=new StringBuffer();
		sql.append(" select ");		
		sql.append(" xcgl_runrecord_h.pk_corp pk_corp,");//公司
		sql.append(" xcgl_runrecord_h.vbillno,");//单据编号
		sql.append(" xcgl_runrecord_h.pk_billtype vbilltype,");//单据类型
		sql.append(" xcgl_runrecord_h.pk_beltline pk_beltline,");//生产线
		sql.append(" xcgl_runrecord_h.pk_factory pk_factory,");//选厂
		sql.append(" xcgl_runrecord_h.pk_process,");//工序
		sql.append(" xcgl_runrecord_b.pk_equipment pk_equipment,");//设备主键
        sql.append(" xcgl_runrecord_b.boottime boottime,");//开机时间
        sql.append(" xcgl_runrecord_b.offtime offtime,");//关机时间
        sql.append(" xcgl_runrecord_b.nduration nduration");//时长       
      	sql.append(" from xcgl_runrecord_h ");
		sql.append(" join xcgl_runrecord_b on xcgl_runrecord_h.pk_runrecord_h=xcgl_runrecord_b.pk_runrecord_h");
		sql.append(" where  ");
		sql.append(" isnull(xcgl_runrecord_h.dr,0)=0 ");
		sql.append(" and isnull(xcgl_runrecord_b.dr,0)=0 ");
		if(whereSql!=null && whereSql.length()!=0){
		    sql.append(" and ");
			sql.append(whereSql);
		}
		sql.append(" and xcgl_runrecord_h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		return sql.toString();
	}
}
