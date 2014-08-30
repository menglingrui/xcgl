package nc.ui.xcgl.report.sumdayreport;

import java.util.List;

import javax.swing.table.TableColumnModel;

import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.xcgl.report.dayreport.DayReportTool;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.CombinVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
/**
 * ѡ���ձ�(����)
 * @author mlr
 */
public class RepSumClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	//�����ܽڵ�� 2002AC021525
	
	private QueryClientDLG m_qryDlg;
	/**
	 * �����������Сά��
	 */
	public static String[] mapkey={"pk_corp","pk_factory","pk_beltline","pk_classorder",
        "pk_minarea","pk_oreinvmandoc","vreserve1","dbilldate"};
	
	public static String[] combinConditions={"pk_corp","pk_factory","pk_beltline",
		"pk_minarea","pk_oreinvmandoc","vmonth"};
	public static String[] combinFields={"nwetnum","ndrynum","pb_noutnum",
		"zn_noutnum","ptm_pb","ptm_zn","pbtm_ag","zn_noutnum","pbtm_pb",
		"zntm_zn","pb_tmag"};
	
	public static String[] AverageConditions={"pk_corp","pk_factory","pk_beltline",
		"pk_minarea","pk_oreinvmandoc","vmonth"}; 
	
	public static String[] AveragecombinFields={"ore_pb","ore_zn","ore_ag",
		"pb_pb","pb_zn","pb_ag","zn_pb","zn_zn","pbt_pb",
		"pbt_ag","znt_zn"};	
	//
	//�̶���Ԫ���ֶ��б� name
	//ѡ������Σ�����ʱ��,ԭ��,������ʪ��(�֣�,ԭ��ˮ��(%),����������(�֣�,Pb(%),Zn(%),Ag(g/t)
    //���������֣�,Pb(%),Zn(%),Ag(g/t)---->Ǧ����
	//���������֣�,Pb(%),Zn(%)---->п����
	//Pb(%),Ag(g/t)------->Ǧβ��Ʒλ
	//Zn(%)---->пβ
	//Ǧ����Pb(%),Ǧ����Ag(%),п����Zn(%)
	//Pb(t),Zn(t),Ag(kg)---->Ǧ���۽�����
	//Pb(t),Zn(t)----->п���۽�����
	//Pb(t),Zn(t),��(Kg)---->β�������
	//Ǧ����,п����(������)
	//
	//�̶���Ԫ���ֶ��б� code
	//     ѡ����             ��Σ�             ����ʱ��,      ԭ��,       ������ʪ��(�֣�,ԭ��ˮ��(%),����������(�֣� ,Pb(%) ,Zn(%) ,Ag(g/t)
	//pk_factory��pk_classorder��starhours,pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
	//                                 pk_oreinvbasdoc,
    //���������֣�  ,Pb(%),Zn(%),Ag(g/t)---->Ǧ����
	//pb_noutnum,pb_pb,pb_zn,pb_ag
	//
	//���������֣�   ,Pb(%), Zn(%)---->п����
	//zn_noutnum,zn_pb, zn_zn
	//
	//Pb(%),Ag(g/t)------->Ǧβ��Ʒλ
	//pbt_pb,pbt_ag
	//
	//Zn(%)---->пβ
	//znt_zn
	//
	//Ǧ����Pb(%),Ǧ����Ag(%),п����Zn(%)
	//pb_pb_recrate,pb_ag_recrate,zn_zn_recrate
	//
	//Pb(t) ,Zn(t) ,Ag(kg)---->Ǧ���۽�����
	//ptm_pb,ptm_zn,ptm_ag
	//
	//Pb(t) ,Zn(t)----->п���۽�����
	//znm_pb,znm_zn
	//
	//Pb(t),    Zn(t),    ��(Kg)---->β�������
	//pbtm_pb, zntm_zn,   pb_tmag
    //
	//Ǧ����,    п����,    ��(Kg)---->������
	//pbnrate, znnrate,
	
	//(  [��������.����Ʒλ��һ��]  *    (  [��������.ԭ��Ʒλ]  -  [��������.β��Ʒλ��һ��]  )    )    /  
	//(  [��������.ԭ��Ʒλ]  *    (  [��������.����Ʒλ��һ��]  -  [��������.β��Ʒλ��һ��]  )    )
	//pb_pb_recrate->(pb_pb*ore_pb-pbt_pb )/(ore_pb*(pb_pb-pbt_pb)
	//pb_ag_recrate->(pb_ag*ore_ag-pbt_ag )/(ore_ag*(pb_ag-pbt_ag)
	
	//ԭ������*ԭ��Ʒλ*������/����Ʒλ
	//pb_noutnum->ndrynum*ore_pb*pb_pb_recrate/pb_pb
	
	//(  [��������.ԭ������]  -  [��������.���������һ��]  )    *  [��������.β��Ʒλ��һ��]
	//ptm_pb->(ndrynum-pb_noutnum)*pbt_pb
	//ptm_ag->(ndrynum-pb_noutnum)*pbt_ag
	
	//[��������.ԭ������]  *  [��������.ԭ��Ʒλ]  - [��������.β���������һ��]
	//ptm_pb->ndrynum*ore_pb-pbtm_pb
	//ptm_ag->ndrynum*ore_ag-pbtm_ag
		
	//   ((  [��������.ԭ��Ʒλ]  -    (  [��������.���������һ��]  /  [��������.ԭ������]  )  
	//   * [��������.����Ʒλ��һ��]  -  [��������.β��Ʒλ������]  )  *  [��������.����Ʒλ������])  
	//   /(  [��������.ԭ��Ʒλ]  *   ([��������.����Ʒλ������]  -  [��������.β��Ʒλ������]))
	//zn_zn_recrate->((ore_zn-(pb_noutnum/ndrynum))*pb_zn-znt_zn)*zn_zn)/(ore_zn*(zn_zn-znt_zn))
	
	//ԭ������*ԭ��Ʒλ*������/����Ʒλ
	//zn_noutnum->ndrynum*ore_zn*zn_zn_recrate/zn_zn
	
	//([��������.ԭ������] - [��������.���������һ��] - [��������.�������������]  ) *  [��������.β��Ʒλ������]
	//zntm_zn->(ndrynum-pb_noutnum-zn_noutnum)*znt_zn
	
	// [��������.ԭ������]  *  [��������.ԭ��Ʒλ]  -  [��������.���������һ��]
	//   *  [��������.����Ʒλ��һ��]  -[��������.β�������������]
	//znm_zn->ndrynum*ore_zn-pb_noutnum*pb_zn-zntm_zn		
	public String[] getSqls() throws Exception {
		return new String[] { 
		   getBaseSql(),
		};
	}

	public String getBaseSql() {		
		return ReportSumSql.getBaseSql(" 1=1");
	}
    /**
     * �����кϲ�
     */
    public void setColumn() {
        //������Ŀ��������
        UITable cardTable = getReportBase().getBillTable();
        GroupableTableHeader cardHeader = (GroupableTableHeader) cardTable.getTableHeader();
        TableColumnModel cardTcm = cardTable.getColumnModel();   
//      cardTable.getColumnName(6);
//      getReportBase().getBillModel().getColumnName(6);
//      getReportBase().getBillModel().getItemByKey("nwetnum").getIDColName();
        ColumnGroup oregroup= new ColumnGroup("ԭ��");
      //pk_factory��pk_classorder��starhours,pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("nwetnum")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("nwater")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ndrynum")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_pb")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_zn")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_ag")));       
        cardHeader.addColumnGroup(oregroup);
        
        ColumnGroup oregroup1= new ColumnGroup("����");        
        ColumnGroup oregroup11= new ColumnGroup("Ǧ����");
        //���������֣�  ,Pb(%),Zn(%),Ag(g/t)---->Ǧ����
    	//pb_noutnum,pb_pb,pb_zn,pb_ag
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_noutnum")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_pb")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_zn")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_ag")));
        oregroup1.add(oregroup11);
  
        ColumnGroup oregroup12= new ColumnGroup("п����");
        //���������֣�   ,Pb(%), Zn(%)---->п����
      	//zn_noutnum,zn_pb, zn_zn
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_noutnum")));
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_pb")));
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_zn")));
        oregroup1.add(oregroup12);        
        cardHeader.addColumnGroup(oregroup1);
        
        ColumnGroup oregroup2= new ColumnGroup("Ǧβ��");
    	//Pb(%),Ag(g/t)------->Ǧβ��Ʒλ
    	//pbt_pb,pbt_ag
        oregroup2.add(cardTcm.getColumn(getColumnIndexByCode("pbt_pb")));
        oregroup2.add(cardTcm.getColumn(getColumnIndexByCode("pbt_ag")));
        cardHeader.addColumnGroup(oregroup2);
        
        ColumnGroup oregroup21= new ColumnGroup("пβ��");
    	//Zn(%)---->пβ
    	//znt_zn
        oregroup21.add(cardTcm.getColumn(getColumnIndexByCode("znt_zn")));
        cardHeader.addColumnGroup(oregroup21);
        
        ColumnGroup oregroup3= new ColumnGroup("������");
    	//Ǧ����Pb(%),Ǧ����Ag(%),п����Zn(%)
    	//pb_pb_recrate,pb_ag_recrate,zn_zn_recrate
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("pb_pb_recrate")));
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("pb_ag_recrate")));
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("zn_zn_recrate")));
        cardHeader.addColumnGroup(oregroup3);
            
        ColumnGroup oregroup4= new ColumnGroup("���۽�����");        
        ColumnGroup oregroup41= new ColumnGroup("Ǧ���۽�����");
    	//Pb(t) ,Zn(t) ,Ag(kg)---->Ǧ���۽�����
    	//ptm_pb,ptm_zn,ptm_ag
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_pb")));
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_zn")));
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_ag")));
        oregroup4.add(oregroup41);        
        ColumnGroup oregroup42= new ColumnGroup("п���۽�����");
    	//Pb(t) ,Zn(t)----->п���۽�����
    	//znm_pb,znm_zn
        oregroup42.add(cardTcm.getColumn(getColumnIndexByCode("znm_pb")));
        oregroup42.add(cardTcm.getColumn(getColumnIndexByCode("znm_zn")));
        oregroup4.add(oregroup42);       
        cardHeader.addColumnGroup(oregroup4);
                
        ColumnGroup oregroup5= new ColumnGroup("β�������");
        //Pb(t),    Zn(t),    ��(Kg)---->β�������
    	//pbtm_pb, zntm_zn,   pbtm_ag
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("pbtm_pb")));
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("zntm_zn")));
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("pbtm_ag")));
        cardHeader.addColumnGroup(oregroup5);
                
        ColumnGroup oregroup6= new ColumnGroup("������");
        //Ǧ����,    п����,    ��(Kg)---->������
    	//pbnrate, znnrate,  
        oregroup6.add(cardTcm.getColumn(getColumnIndexByCode("pbnrate")));
        oregroup6.add(cardTcm.getColumn(getColumnIndexByCode("znnrate")));
        cardHeader.addColumnGroup(oregroup6);
        getReportBase().getBillModel().updateValue();
    }
    
	public int getColumnIndexByCode(String code) {	  
	  return  getReportBase().getBillModel().getItemIndex(code);
	}

	/**
	 * ���캯��
	 */
	public RepSumClientUI() {
		super();
		setColumn();
	}

	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)throws Exception{
		if(list ==null || list.size()==0){
			return null;
		}
		if(list.get(0)==null || list.get(0).length==0){
			return null;
		} 
		ReportBaseVO[] vos=DayReportTool.getDealVO(list.get(0));		
		ReportBaseVO[] sumvos=(ReportBaseVO[]) CombinVO.combinData(
				(ReportBaseVO[])ObjectUtils.serializableClone(vos), combinConditions, combinFields);		
		ReportBaseVO[] avgvos=CombinVO.averageData(
				(ReportBaseVO[])ObjectUtils.serializableClone(vos), AverageConditions, AveragecombinFields);				
	    CombinVO.copyValueByContion(sumvos,avgvos, AverageConditions, AveragecombinFields);		
		return sumvos;
	}
	
	
	
	


	/**
	 * ��ѯ��� ���õ�ui����֮�� ��������
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:42:36
	 * @param list
	 * @return
	 */
	public void dealQueryAfter() throws Exception {
		super.dealQueryAfter();
		getReportBase().getBillModel().execFormulas(DayReportTool.formulas);
	}
	public QueryDLG getQueryDlg() {
		if (m_qryDlg == null) {
			m_qryDlg = new QueryClientDLG(this);
			m_qryDlg.setTempletID(_getCorpID(), _getModelCode(), _getUserID(),
					null, null, OrgnizeTypeVO.COMPANY_TYPE);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}
	 public String _getModelCode() {	       
	        return "2002AC021525";
	    }

}