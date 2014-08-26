package nc.ui.xcgl.report.dayreport;

import nc.ui.pub.ClientEnvironment;

/**
 * ѡ���ձ�
 * 
 * @author Jay
 * 
 */
public class ReportSql {	
	/**
	 * ���������������
	 */
	public static String getBaseSql(String wheresql){
		StringBuffer sql=new StringBuffer();
		//	ѡ����                        ��Σ�                     ����ʱ��,           ԭ��,  ������ʪ��(�֣�,ԭ��ˮ��(%),����������(�֣�
		//pk_factory��pk_classorder��starhours,  pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
		sql.append(" select ");
		sql.append(" h.pk_factory pk_factory, ");//--ѡ��
		sql.append(" h.pk_beltline pk_beltline,");//������
		sql.append(" h.dbilldate dbilldate,");//����
		sql.append(" h.pk_classorder pk_classorder,");// --���
		sql.append(" b.pk_minarea pk_minarea,");//����
		sql.append(" b.starhours starhours,");//����ʱ��	
		sql.append(" h.vreserve1,");//��ʯ����
		sql.append(" b.pk_oreinvmandoc pk_oreinvmandoc,");//--�����������(ԭ��)
		sql.append(" b.pk_oreinvbasdoc pk_oreinvbasdoc,");//--�����������(ԭ��)	
		sql.append(" h.pk_invmandoc ,");//--�����������(����,β��)
		sql.append(" h.pk_invbasdoc ,");//--�����������(����,β��)	
		sql.append(" b.nwetnum nwetnum,");//--ʪ��
		sql.append(" b.nwater nwater,");//--ˮ��
		sql.append(" b.ndrynum ndrynum, ");//--����
		//���������֣�  ,Pb(%),Zn(%),Ag(g/t)---->Ǧ����
		sql.append(" h.pk_manindex,");//--ָ��
		sql.append(" h.pk_invindex,");//--ָ��
		sql.append(" h.ncrudescontent,");//ԭ��Ʒλ
		sql.append(" h.ncontent,");//Ʒλ
		sql.append(" h.noutput,");//--����
		sql.append(" h.nmetalamount,");//--������
		sql.append(" h.nrecover ,");//--������
		sql.append(" b.pk_factory pk_factory, ");//--ѡ��
		sql.append(" b.pk_beltline pk_beltline,");//������
		sql.append(" b.pk_classorder pk_classorder,");// --���
		sql.append(" b.vreserve1");//��ʯ����	
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
	 * ȡ��������
	 */
	public static String getFlouryieldBaseSql(String wheresql){
		StringBuffer sql=new StringBuffer();
		//	ѡ����                        ��Σ�                  ����ʱ��,           ԭ��,  ������ʪ��(�֣�,ԭ��ˮ��(%),����������(�֣�
		//pk_factory��pk_classorder��starhours,  pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
//		public static String[] mapkey={"pk_corp","pk_factory","pk_beltline","pk_classorder",
//        "pk_minarea","pk_oreinvmandoc","vreserve1","dbilldate"};
		sql.append(" (select  ");//--������
		sql.append(" xcgl_flouryield_h.pk_corp pk_corp, ");//--��˾
		sql.append(" xcgl_flouryield_h.pk_factory pk_factory, ");//--ѡ��
		sql.append(" xcgl_flouryield_h.pk_beltline pk_beltline,");//������
		sql.append(" xcgl_flouryield_h.pk_classorder pk_classorder,");// --���
		sql.append(" xcgl_flouryield_b.pk_deptdoc pk_minarea,");//����
		sql.append(" xcgl_flouryield_b.vdef20 pk_oreinvmandoc,");//--�����������(ԭ��)
		sql.append(" xcgl_flouryield_b.pk_defdoc20 pk_oreinvbasdoc,");//--�����������(ԭ��)
		sql.append(" xcgl_flouryield_h.vreserve1,");//��ʯ����
		sql.append(" xcgl_flouryield_h.dbilldate dbilldate,");//����
		sql.append(" xcgl_flouryield_b.pk_invmandoc,");//--�����������(���ۣ�β��)
		sql.append(" xcgl_flouryield_b.pk_invbasdoc,");//--�����������(���ۣ�β��)
		//���������֣�  ,Pb(%),Zn(%),Ag(g/t)---->Ǧ����
		sql.append(" xcgl_flouryield_b.pk_manindex,");//--ָ��
		sql.append(" xcgl_flouryield_b.pk_invindex,");//--ָ��
		sql.append(" xcgl_flouryield_b.ncrudescontent,");//ԭ��Ʒλ
		sql.append(" xcgl_flouryield_b.ncontent,");//Ʒλ
		sql.append(" xcgl_flouryield_b.noutput,");//--����
		sql.append(" xcgl_flouryield_b.nmetalamount,");//--������
		sql.append(" xcgl_flouryield_b.nrecover, ");//--������
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
	 * ȡԭ��ӹ�����
	 */
	public static String getBaseOreProcessingSql(String wheresql){
		StringBuffer sql=new StringBuffer();
		//	ѡ����                       ��Σ�                    ����ʱ��,      ԭ��,       ������ʪ��(�֣�,ԭ��ˮ��(%),����������(�֣�
		//pk_factory��pk_classorder��starhours,  pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
		sql.append(" (select  ");
		sql.append(" xcgl_general_h.pk_corp pk_corp, ");//--��˾
		sql.append(" xcgl_general_h.pk_factory pk_factory, ");//--ѡ��
		sql.append(" xcgl_general_h.dbilldate ,");//����
		sql.append(" xcgl_general_h.pk_beltline pk_beltline,");//������
		sql.append(" xcgl_general_h.pk_classorder pk_classorder,");// --���
		sql.append(" xcgl_general_h.pk_minarea pk_minarea,");//����
		sql.append(" xcgl_general_h.vreserve1,");//��ʯ����
		sql.append(" xcgl_general_h.starthours starhours,");//����ʱ��	
		sql.append(" xcgl_general_b.pk_invmandoc pk_oreinvmandoc,");//--�����������(ԭ��,���ۣ�β��)
		sql.append(" xcgl_general_b.pk_invbasdoc pk_oreinvbasdoc,");//--�����������(ԭ��,���ۣ�β��)	
		sql.append(" xcgl_general_b.nwetweight nwetnum,");//--ʪ��
		sql.append(" xcgl_general_b.nwatercontent nwater,");//--ˮ��
		sql.append(" xcgl_general_b.ndryweight ndrynum, ");//--����
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
