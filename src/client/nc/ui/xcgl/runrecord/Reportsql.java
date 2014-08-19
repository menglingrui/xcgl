package nc.ui.xcgl.runrecord;

import nc.ui.pub.ClientEnvironment;

public class Reportsql {
	/**
	 * ��ѯ�豸���м�¼
     */	
	public static String getSql(String whereSql) throws Exception{	
		StringBuffer sql=new StringBuffer();
		sql.append(" select ");		
		sql.append(" xcgl_runrecord_h.pk_corp pk_corp,");//��˾
		sql.append(" xcgl_runrecord_h.vbillno,");//���ݱ��
		sql.append(" xcgl_runrecord_h.pk_billtype vbilltype,");//��������
		sql.append(" xcgl_runrecord_h.pk_beltline pk_beltline,");//������
		sql.append(" xcgl_runrecord_h.pk_factory pk_factory,");//ѡ��
		sql.append(" xcgl_runrecord_h.pk_process,");//����
		sql.append(" xcgl_runrecord_b.pk_equipment pk_equipment,");//�豸����
        sql.append(" xcgl_runrecord_b.boottime boottime,");//����ʱ��
        sql.append(" xcgl_runrecord_b.offtime offtime,");//�ػ�ʱ��
        sql.append(" xcgl_runrecord_b.nduration nduration");//ʱ��       
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
