package nc.ui.xcgl.report.daymetbanreport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumnModel;

import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.xcgl.report.dayreport.DayReportTool;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
/**
 * ѡ���ձ�
 * @author mlr
 */
public class RepClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	//�����ܽڵ�� 2002AC021525
	private QueryClientDLG m_qryDlg;
//ͶӰ����
//            num_month  num_quarter num_year
//ԭ��ʪ��                       y
//ԭ��ˮ��(%)      
//ԭ�����                       y
//Ǧ��                     
//п��                       
//β��                       
	
//ͶӰ����
//           g_pb_month  g_pb_quarter g_pb_year  g_zn_month  g_zn_quarter g_zn_year  g_ag_month  g_ag_quarter g_ag_year
//ԭ��ʪ��                  
//ԭ��ˮ��(%)
//ԭ�����        
//Ǧ��                       y
//п��                       y
//β��                       y

//           m_pb_month  m_pb_quarter m_pb_year  m_zn_month  m_zn_quarter m_zn_year  m_ag_month  m_ag_quarter m_ag_year
//ԭ��ʪ��        
//ԭ��ˮ��(%)
//ԭ�����
//Ǧ��                     y
//п��                     y
//β��                     y
	
//           r_pb_month  r_pb_quarter r_pb_year  r_zn_month  r_zn_quarter r_zn_year  r_ag_month  r_ag_quarter r_ag_year
//ԭ��ʪ��        
//ԭ��ˮ��(%)
//ԭ�����
//Ǧ��                   y
//п��                   y
//β��                 
	
	 
	//�����ǻ������ݵĴ�������Ҫת������������۽���ƽ���
	
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
	
	//���������֣�   ,Pb(%), Zn(%)---->п����
	//zn_noutnum,zn_pb, zn_zn
	
	//Pb(%),Ag(g/t)------->Ǧβ��Ʒλ
	//pbt_pb,pbt_ag
	
	//Zn(%)---->пβ
	//znt_zn
	
	//Ǧ����Pb(%),Ǧ����Ag(%),п����Zn(%)
	//pb_pb_recrate,pb_ag_recrate,zn_zn_recrate
	
	//Pb(t) ,Zn(t) ,Ag(kg)---->Ǧ���۽�����
	//ptm_pb,ptm_zn,pbtm_ag
	
	//Pb(t) ,Zn(t)----->п���۽�����
	//znm_pb,znm_zn
	
	//Pb(t),    Zn(t),    ��(Kg)---->β�������
	//pbtm_pb, zntm_zn,   pb_tmag

	//Ǧ����,    п����,    ��(Kg)---->������
	//pbnrate, znnrate,
	public String[] getSqls() throws Exception {
		return new String[] { 
		   getBaseSql(),
		};
	}

	public String getBaseSql() {		
		return ReportSql.getBaseSql(" 1=1");
	}
    /**
     * �����кϲ�
     */
    public void setColumn() {
        //������Ŀ��������
        UITable cardTable = getReportBase().getBillTable();
        GroupableTableHeader cardHeader = (GroupableTableHeader) cardTable.getTableHeader();
        TableColumnModel cardTcm = cardTable.getColumnModel();    
//        cardTable.getColumnName(6);
//        getReportBase().getBillModel().getColumnName(6);
//        getReportBase().getBillModel().getItemByKey("nwetnum").getIDColName();
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
	public RepClientUI() {
		super();
		setColumn();
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
	}
	 /**
	
    */
	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)throws Exception{
		if(list ==null || list.size()==0){
			return null;
		}
		if(list.get(0)==null || list.get(0).length==0){
			return null;
		}
    	
		return getDealVO(list.get(0));
	}

	private ReportBaseVO[] getDealVO(ReportBaseVO[] vos) throws Exception {
		if(vos==null || vos.length==0){
			return null;
		}
		//�̶���ѯ�������·�(��ѯ����)��ѡ��������
		vos=DayReportTool.getDealVO(vos); //�����ѯ�����ǣ��³�����ǰ����	
		//�������ۼƣ����ۼƣ����ۼƣ����ۼ�
		List<ReportBaseVO> rlist=new ArrayList<ReportBaseVO>();
		ReportBaseVO[][] rvoss=(ReportBaseVO[][]) SplitBillVOs.getSplitVOs(vos, MetalReportTool.metalmonthmapkey);
		for(int i=0;i<rvoss.length;i++){
			ReportBaseVO monthvo=DayReportTool.getMonthReportVO(rvoss[i]);
			ReportBaseVO quartervo=DayReportTool.getQuarterVO(rvoss[i]);
			ReportBaseVO yearvo=DayReportTool.getYearVO(rvoss[i]);      
			//ԭ��ʪ��        
			ReportBaseVO orevo=new ReportBaseVO();
			dealOreVO(monthvo,quartervo,yearvo,orevo);
			//ԭ��ˮ��(%)
			ReportBaseVO orewatervo=new ReportBaseVO();
			dealWaterVO(monthvo,quartervo,yearvo,orewatervo);
			//ԭ�����
			ReportBaseVO oredryvo=new ReportBaseVO();
			dealOreDryVO(monthvo,quartervo,yearvo,oredryvo);
			//Ǧ��                 
			ReportBaseVO pbvo=new ReportBaseVO();
			dealPbVO(monthvo,quartervo,yearvo,pbvo);
			//п��                  
			ReportBaseVO znvo=new ReportBaseVO();
			dealZnVO(monthvo,quartervo,yearvo,znvo);
			//β��          
			ReportBaseVO tvo=new ReportBaseVO();
			dealTVO(monthvo,quartervo,yearvo,tvo);
			rlist.add(orevo);
			rlist.add(orewatervo);
			rlist.add(oredryvo);
			rlist.add(pbvo);
			rlist.add(znvo);
			rlist.add(tvo);
		}
		return rlist.toArray(new ReportBaseVO[0]);
	}
	
	private void dealTVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO tvo) {

//      m_pb_month  m_pb_quarter m_pb_year  m_zn_month  m_zn_quarter m_zn_year  m_ag_month  m_ag_quarter m_ag_year
//      r_pb_month  r_pb_quarter r_pb_year  r_zn_month  r_zn_quarter r_zn_year  r_ag_month  r_ag_quarter r_ag_year
		
		//Pb(%),Ag(g/t)------->Ǧβ��Ʒλ
		//pbt_pb,pbt_ag		
		//Zn(%)---->пβ
		//znt_zn
		tvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("pbt_pb"));
		tvo.setAttributeValue("m_pb_quarter",quartervo.getAttributeValue("pbt_pb"));
		tvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("pbt_pb"));
		
		tvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("znt_zn"));
		tvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("znt_zn"));
		tvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("znt_zn"));
		
		tvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("pbt_ag"));
		tvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("pbt_ag"));
		tvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("pbt_ag"));
//		
//		pbvo.setAttributeValue("r_pb_month", value);
//		pbvo.setAttributeValue("r_pb_quarter", value);
//		pbvo.setAttributeValue("r_pb_year", value);
//		pbvo.setAttributeValue("r_zn_month", value);
//		pbvo.setAttributeValue("r_zn_quarter", value);
//		pbvo.setAttributeValue("r_ag_month", value);
//		pbvo.setAttributeValue("r_ag_quarter", value);
//		pbvo.setAttributeValue("r_ag_year", value);
	
	}

	private void dealZnVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO znvo) {
//      m_pb_month  m_pb_quarter m_pb_year  m_zn_month  m_zn_quarter m_zn_year  m_ag_month  m_ag_quarter m_ag_year
//      r_pb_month  r_pb_quarter r_pb_year  r_zn_month  r_zn_quarter r_zn_year  r_ag_month  r_ag_quarter r_ag_year
		
		//znm_pb,znm_zn
		znvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("znm_pb"));
		znvo.setAttributeValue("m_pb_quarter",quartervo.getAttributeValue("znm_pb"));
		znvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("znm_pb"));
		
		znvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("znm_zn"));
		znvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("znm_zn"));
		znvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("znm_zn"));
		
//		znvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("pbtm_ag"));
//		znvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("pbtm_ag"));
//		znvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("pbtm_ag"));
//		
//		pbvo.setAttributeValue("r_pb_month", value);
//		pbvo.setAttributeValue("r_pb_quarter", value);
//		pbvo.setAttributeValue("r_pb_year", value);
//		pbvo.setAttributeValue("r_zn_month", value);
//		pbvo.setAttributeValue("r_zn_quarter", value);
//		pbvo.setAttributeValue("r_ag_month", value);
//		pbvo.setAttributeValue("r_ag_quarter", value);
//		pbvo.setAttributeValue("r_ag_year", value);
	}

	private void dealPbVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO pbvo) {
//      m_pb_month  m_pb_quarter m_pb_year  m_zn_month  m_zn_quarter m_zn_year  m_ag_month  m_ag_quarter m_ag_year
//      r_pb_month  r_pb_quarter r_pb_year  r_zn_month  r_zn_quarter r_zn_year  r_ag_month  r_ag_quarter r_ag_year
		
		//ptm_pb,ptm_zn,pbtm_ag
		pbvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("ptm_pb"));
		pbvo.setAttributeValue("m_pb_quarter",quartervo.getAttributeValue("ptm_pb"));
		pbvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("ptm_pb"));
		
		pbvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("ptm_zn"));
		pbvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("ptm_zn"));
		pbvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("ptm_zn"));
		
		pbvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("pbtm_ag"));
		pbvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("pbtm_ag"));
		pbvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("pbtm_ag"));
//		
//		pbvo.setAttributeValue("r_pb_month", value);
//		pbvo.setAttributeValue("r_pb_quarter", value);
//		pbvo.setAttributeValue("r_pb_year", value);
//		pbvo.setAttributeValue("r_zn_month", value);
//		pbvo.setAttributeValue("r_zn_quarter", value);
//		pbvo.setAttributeValue("r_ag_month", value);
//		pbvo.setAttributeValue("r_ag_quarter", value);
//		pbvo.setAttributeValue("r_ag_year", value);
	}

	private void dealOreDryVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO oredryvo) {
		//num_month  num_quarter num_year ԭ��ʪ��
		oredryvo.setAttributeValue("num_month", monthvo.getAttributeValue("ndrynum"));
		oredryvo.setAttributeValue("num_quarter",quartervo.getAttributeValue("ndrynum"));
		oredryvo.setAttributeValue("num_year", yearvo.getAttributeValue("ndrynum"));		
	}

	private void dealWaterVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO orewatervo) {
		
	}

	private void dealOreVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO orevo) {
		//num_month  num_quarter num_year ԭ��ʪ��
		orevo.setAttributeValue("num_month", monthvo.getAttributeValue("nwetnum"));
		orevo.setAttributeValue("num_quarter",quartervo.getAttributeValue("nwetnum"));
		orevo.setAttributeValue("num_year", yearvo.getAttributeValue("nwetnum"));		
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