package nc.ui.xcgl.report.daymetbanreport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableColumnModel;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.xcgl.report.dayreport.DayReportTool;
import nc.ui.xcgl.report.dayreport.ReportSql;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
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
	//�����ܽڵ�� 2002AC021535
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
		String wheresql=" 1=1 ";
		if(getQueryDlg().getWhereSQL()!=null){
			wheresql=wheresql+" and "+getQueryDlg().getWhereSQL();
		}
		return ReportSql.getBaseSql(wheresql);
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
        ColumnGroup oregroup= new ColumnGroup("��");
//num_month  num_quarter num_year
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("num_month")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("num_quarter")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("num_year")));  
        cardHeader.addColumnGroup(oregroup);
        /////////////////  
        /////////////////  
        ColumnGroup oregroup1= new ColumnGroup("Ʒλ");        
        ColumnGroup oregroup11= new ColumnGroup("Pb(%)");
        //g_pb_month  g_pb_quarter g_pb_year
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("g_pb_month")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("g_pb_quarter")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("g_pb_year")));
        oregroup1.add(oregroup11);
    
        ColumnGroup oregroup13= new ColumnGroup("Zn(%)");
        //g_zn_month  g_zn_quarter g_zn_year
        oregroup13.add(cardTcm.getColumn(getColumnIndexByCode("g_zn_month")));
        oregroup13.add(cardTcm.getColumn(getColumnIndexByCode("g_zn_quarter")));
        oregroup13.add(cardTcm.getColumn(getColumnIndexByCode("g_zn_year")));
        oregroup1.add(oregroup13);   
        
        ColumnGroup oregroup12= new ColumnGroup("Ag(g/t)");
        //g_ag_month  g_ag_quarter g_ag_year
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("g_ag_month")));
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("g_ag_quarter")));
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("g_ag_year")));
        oregroup1.add(oregroup12);  
        
        cardHeader.addColumnGroup(oregroup1);
        /////////////////  
        //////////////
        ColumnGroup oregroup2= new ColumnGroup("������");        
        ColumnGroup oregroup111= new ColumnGroup("Pb(t)");
        // m_pb_month  m_pb_quarter m_pb_year  
        oregroup111.add(cardTcm.getColumn(getColumnIndexByCode("m_pb_month")));
        oregroup111.add(cardTcm.getColumn(getColumnIndexByCode("m_pb_quarter")));
        oregroup111.add(cardTcm.getColumn(getColumnIndexByCode("m_pb_year")));
        oregroup2.add(oregroup111);
    
        ColumnGroup oregroup131= new ColumnGroup("Zn(t)");
        //m_zn_month  m_zn_quarter m_zn_year  
        oregroup131.add(cardTcm.getColumn(getColumnIndexByCode("m_zn_month")));
        oregroup131.add(cardTcm.getColumn(getColumnIndexByCode("m_zn_quarter")));
        oregroup131.add(cardTcm.getColumn(getColumnIndexByCode("m_zn_year")));
        oregroup2.add(oregroup131);   
        
        ColumnGroup oregroup121= new ColumnGroup("Ag(Kg)");
        //m_ag_month  m_ag_quarter m_ag_year
        oregroup121.add(cardTcm.getColumn(getColumnIndexByCode("m_ag_month")));
        oregroup121.add(cardTcm.getColumn(getColumnIndexByCode("m_ag_quarter")));
        oregroup121.add(cardTcm.getColumn(getColumnIndexByCode("m_ag_year")));
        oregroup2.add(oregroup121);  
       
        cardHeader.addColumnGroup(oregroup2);
        /////////////////  
        ///////////////////////  
        ColumnGroup oregroup3= new ColumnGroup("������ (%)");        
        ColumnGroup oregroup1111= new ColumnGroup("Pb");
        //r_pb_month  r_pb_quarter r_pb_year 
        oregroup1111.add(cardTcm.getColumn(getColumnIndexByCode("r_pb_month")));
        oregroup1111.add(cardTcm.getColumn(getColumnIndexByCode("r_pb_quarter")));
        oregroup1111.add(cardTcm.getColumn(getColumnIndexByCode("r_pb_year")));
        oregroup3.add(oregroup1111);
    
        ColumnGroup oregroup1311= new ColumnGroup("Zn");
        // r_zn_month  r_zn_quarter r_zn_year  
        oregroup1311.add(cardTcm.getColumn(getColumnIndexByCode("r_zn_month")));
        oregroup1311.add(cardTcm.getColumn(getColumnIndexByCode("r_zn_quarter")));
        oregroup1311.add(cardTcm.getColumn(getColumnIndexByCode("r_zn_year")));
        oregroup3.add(oregroup1311);   
        
        ColumnGroup oregroup1211= new ColumnGroup("Ag");
        //r_ag_month  r_ag_quarter r_ag_year
        oregroup1211.add(cardTcm.getColumn(getColumnIndexByCode("r_ag_month")));
        oregroup1211.add(cardTcm.getColumn(getColumnIndexByCode("r_ag_quarter")));
        oregroup1211.add(cardTcm.getColumn(getColumnIndexByCode("r_ag_year")));
        oregroup3.add(oregroup1211);  
        
        cardHeader.addColumnGroup(oregroup3);
        /////////////////     
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
		
		getReportBase().getBillModel().execFormulas(DayReportTool.formulas1);
		
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
			ReportBaseVO monthvo=DayReportTool.getMonthMetalReportVO(rvoss[i]);
			ReportBaseVO quartervo=DayReportTool.getQuarterVO(rvoss[i]);
			ReportBaseVO yearvo=DayReportTool.getYearVO(rvoss[i]);      
			//ԭ��ʪ��        
			ReportBaseVO orevo=(ReportBaseVO) ObjectUtils.serializableClone(monthvo);
			dealOreVO(monthvo,quartervo,yearvo,orevo);
			orevo.setAttributeValue("datatype", "ԭ��ʪ��");
			//ԭ��ˮ��(%)
			ReportBaseVO orewatervo=(ReportBaseVO) ObjectUtils.serializableClone(monthvo);
			dealWaterVO(monthvo,quartervo,yearvo,orewatervo);
			orewatervo.setAttributeValue("datatype", "ԭ��ˮ��(%)");
			//ԭ�����
			ReportBaseVO oredryvo=(ReportBaseVO) ObjectUtils.serializableClone(monthvo);
			dealOreDryVO(monthvo,quartervo,yearvo,oredryvo);
			oredryvo.setAttributeValue("datatype", "ԭ�����");
			//Ǧ��                 
			ReportBaseVO pbvo=(ReportBaseVO) ObjectUtils.serializableClone(monthvo);
			dealPbVO(monthvo,quartervo,yearvo,pbvo);
			pbvo.setAttributeValue("datatype", "Ǧ��");
			//п��                  
			ReportBaseVO znvo=(ReportBaseVO) ObjectUtils.serializableClone(monthvo);
			dealZnVO(monthvo,quartervo,yearvo,znvo);
			znvo.setAttributeValue("datatype", "п��");
			//β��          
			ReportBaseVO tvo=(ReportBaseVO) ObjectUtils.serializableClone(monthvo);
			dealTVO(monthvo,quartervo,yearvo,tvo);
			tvo.setAttributeValue("datatype", "β��");
			//����β������
			dealTailNum(orevo,orewatervo,oredryvo,pbvo,znvo,tvo);
			//����ˮ��
			dealWaterNum(orevo,orewatervo,oredryvo,pbvo,znvo,tvo);
			//���㾫������
			dealPowerNum(orevo,orewatervo,oredryvo,pbvo,znvo,tvo);
			//���������
			dealRecNum(orevo,orewatervo,oredryvo,pbvo,znvo,tvo);
			
			rlist.add(orevo);
			rlist.add(orewatervo);
			rlist.add(oredryvo);
			rlist.add(pbvo);
			rlist.add(znvo);
			rlist.add(tvo);
			rlist.add(new ReportBaseVO());
			rlist.add(new ReportBaseVO());
		}
		return rlist.toArray(new ReportBaseVO[0]);
	}
	
	public void dealRecNum(ReportBaseVO orevo, ReportBaseVO orewatervo,
			ReportBaseVO oredryvo, ReportBaseVO pbvo, ReportBaseVO znvo,
			ReportBaseVO tvo) {
	// r_pb_month  r_pb_quarter r_pb_year  
	// r_zn_month  r_zn_quarter r_zn_year
	// r_ag_month  r_ag_quarter r_ag_year
		oredryvo.setAttributeValue("r_pb_month", new UFDouble(100));
		oredryvo.setAttributeValue("r_pb_quarter", new UFDouble(100));
		oredryvo.setAttributeValue("r_pb_year", new UFDouble(100));
		
		oredryvo.setAttributeValue("r_zn_month", new UFDouble(100));
		oredryvo.setAttributeValue("r_zn_quarter", new UFDouble(100));
		oredryvo.setAttributeValue("r_zn_year", new UFDouble(100));
		
		oredryvo.setAttributeValue("r_ag_month", new UFDouble(100));
		oredryvo.setAttributeValue("r_ag_quarter", new UFDouble(100));
		oredryvo.setAttributeValue("r_ag_year", new UFDouble(100));
		//Ǧ����
		//����Ǧ������		
		pbvo.setAttributeValue("r_pb_month", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_month"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_month")))
			    .multiply(100));
		pbvo.setAttributeValue("r_pb_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_quarter"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_quarter")))
			    .multiply(100));
		pbvo.setAttributeValue("r_pb_year", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_year"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_year")))
			    .multiply(100));
		
		//����п������
		pbvo.setAttributeValue("r_zn_month", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_zn_month"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_zn_month")))
			    .multiply(100));
		pbvo.setAttributeValue("r_zn_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_zn_quarter"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_zn_quarter")))
			    .multiply(100));
		pbvo.setAttributeValue("r_zn_year", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_year"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_year")))
			    .multiply(100));
		
		//������������
		pbvo.setAttributeValue("r_ag_month", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_ag_month"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_ag_month")))
			    .multiply(100));
		pbvo.setAttributeValue("r_ag_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_ag_quarter"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_ag_quarter")))
			    .multiply(100));
		pbvo.setAttributeValue("r_ag_year", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_ag_year"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_ag_year")))
			    .multiply(100));
		//п����
		//����Ǧ������		
		znvo.setAttributeValue("r_pb_month", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_month"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_month")))
			    .multiply(100));
		znvo.setAttributeValue("r_pb_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_quarter"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_quarter")))
			    .multiply(100));
		znvo.setAttributeValue("r_pb_year", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_year"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_year")))
			    .multiply(100));
		
		//����п������
		znvo.setAttributeValue("r_zn_month", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_zn_month"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_zn_month")))
			    .multiply(100));
		znvo.setAttributeValue("r_zn_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_zn_quarter"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_zn_quarter")))
			    .multiply(100));
		znvo.setAttributeValue("r_zn_year", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_year"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_pb_year")))
			    .multiply(100));
		
		//������������
		znvo.setAttributeValue("r_ag_month", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_ag_month"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_ag_month")))
			    .multiply(100));
		znvo.setAttributeValue("r_ag_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_ag_quarter"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_ag_quarter")))
			    .multiply(100));
		znvo.setAttributeValue("r_ag_year", 
			    PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_ag_year"))
			    .div(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("m_ag_year")))
			    .multiply(100));
		
		//β��
				//����Ǧ������		
				tvo.setAttributeValue("r_pb_month", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_pb_month"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_pb_month")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_pb_month"))));
				tvo.setAttributeValue("r_pb_quarter", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_pb_quarter"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_pb_quarter")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_pb_quarter"))));
				tvo.setAttributeValue("r_pb_year", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_pb_year"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_pb_year")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_pb_year"))));
			
				
				//����п������
				tvo.setAttributeValue("r_zn_month", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_zn_month"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_zn_month")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_zn_month"))));
				tvo.setAttributeValue("r_zn_quarter", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_zn_quarter"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_zn_quarter")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_zn_quarter"))));
				tvo.setAttributeValue("r_zn_year", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_zn_year"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_zn_year")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_zn_year"))));
				
				//������������
				tvo.setAttributeValue("r_ag_month", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_ag_month"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_ag_month")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_ag_month"))));
				tvo.setAttributeValue("r_ag_quarter", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_ag_quarter"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_ag_quarter")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_ag_quarter"))));
				tvo.setAttributeValue("r_ag_year", 
					    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("r_ag_year"))
					    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("r_ag_year")))
					    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("r_ag_year"))));
				
			
		
	}

	public void dealPowerNum(ReportBaseVO orevo, ReportBaseVO orewatervo,
			ReportBaseVO oredryvo, ReportBaseVO pbvo, ReportBaseVO znvo,
			ReportBaseVO tvo) {
		
		//num_month  num_quarter num_year ԭ��ʪ��		
		oredryvo.setAttributeValue("m_pb_month", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_month"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_month")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_pb_month"))));
		
		oredryvo.setAttributeValue("m_pb_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_quarter"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_quarter")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_pb_quarter"))));
		
		oredryvo.setAttributeValue("m_pb_year", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_pb_year"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_pb_year")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_pb_year"))));
		///////////////////////
		
		
		oredryvo.setAttributeValue("m_zn_month", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_zn_month"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_zn_month")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_zn_month"))));
		
		oredryvo.setAttributeValue("m_zn_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_zn_quarter"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_zn_quarter")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_zn_quarter"))));
		
		oredryvo.setAttributeValue("m_zn_year", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_zn_year"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_zn_year")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_zn_year"))));
		/////////////////////
		
		
		oredryvo.setAttributeValue("m_ag_month", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_ag_month"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_ag_month")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_ag_month"))));
		
		oredryvo.setAttributeValue("m_ag_quarter", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_ag_quarter"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_ag_quarter")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_ag_quarter"))));
		
		oredryvo.setAttributeValue("m_ag_year", 
			    PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("m_ag_year"))
			    .add(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("m_ag_year")))
			    .add(PuPubVO.getUFDouble_NullAsZero(tvo.getAttributeValue("m_ag_year"))));
		
		
     }

	void dealWaterNum(ReportBaseVO orevo, ReportBaseVO orewatervo,
			ReportBaseVO oredryvo, ReportBaseVO pbvo, ReportBaseVO znvo,
			ReportBaseVO tvo) {

		//num_month  num_quarter num_year ԭ��ʪ��		
		 orewatervo.setAttributeValue("num_month", 
	    (PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("num_month"))
	    .sub(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("num_month"))))
	    .div(PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("num_month"))).multiply(100)
	    );
		
		 orewatervo.setAttributeValue("num_quarter", 
		 (PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("num_quarter"))
		 .sub(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("num_quarter"))))
		 .div(PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("num_quarter"))).multiply(100)
		 );
		
		 orewatervo.setAttributeValue("num_year", 
		 (PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("num_year"))
		 .sub(PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("num_year"))))
		 .div(PuPubVO.getUFDouble_NullAsZero(orevo.getAttributeValue("num_year"))).multiply(100)
		 );
	
		
	}

	public void dealTailNum(ReportBaseVO orevo, ReportBaseVO orewatervo,
			ReportBaseVO oredryvo, ReportBaseVO pbvo, ReportBaseVO znvo,
			ReportBaseVO tvo) {
		//num_month  num_quarter num_year ԭ��ʪ��		
		tvo.setAttributeValue("num_month", 
	    PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("num_month"))
	    .sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("num_month")))
	    .sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("num_month"))));
		
		tvo.setAttributeValue("num_quarter", 
		PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("num_quarter"))
		.sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("num_quarter")))
		.sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("num_quarter"))));
		
		tvo.setAttributeValue("num_year", 
		PuPubVO.getUFDouble_NullAsZero(oredryvo.getAttributeValue("num_year"))
		.sub(PuPubVO.getUFDouble_NullAsZero(pbvo.getAttributeValue("num_year")))
		.sub(PuPubVO.getUFDouble_NullAsZero(znvo.getAttributeValue("num_year"))));
	}

	private void dealTVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO tvo) {
//      m_pb_month  m_pb_quarter m_pb_year  m_zn_month  m_zn_quarter m_zn_year  m_ag_month  m_ag_quarter m_ag_year
//      r_pb_month  r_pb_quarter r_pb_year  r_zn_month  r_zn_quarter r_zn_year  r_ag_month  r_ag_quarter r_ag_year		
//      Pb(%),Ag(g/t)------->Ǧβ��Ʒλ
//      pbt_pb,pbt_ag		
//      Zn(%)---->пβ
//      znt_zn
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_pb")).doubleValue()>0){
		   tvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("zntm_pb"));
		}else{
		   tvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("pbtm_pb"));
		}
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_pb")).doubleValue()>0){
		   tvo.setAttributeValue("m_pb_quarter", monthvo.getAttributeValue("zntm_pb"));
		}else{
		   tvo.setAttributeValue("m_pb_quarter", monthvo.getAttributeValue("pbtm_pb"));
		}
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_pb")).doubleValue()>0){
		   tvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("zntm_pb"));
		}else{
		   tvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("pbtm_pb"));
		}

		
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_zn")).doubleValue()>0){
		   tvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("zntm_zn"));
		}else{
		   tvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("pbtm_zn"));
		}
		
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_zn")).doubleValue()>0){
			   tvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("zntm_zn"));
		}else{
			   tvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("pbtm_zn"));
		}
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_zn")).doubleValue()>0){
			   tvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("zntm_zn"));
		}else{
			   tvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("pbtm_zn"));
		}
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_zn")).doubleValue()>0){
			   tvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("zntm_zn"));
		}else{
			   tvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("pbtm_zn"));
		}
		
		
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_ag")).doubleValue()>0){
			   tvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("zntm_ag"));
		}else{
			   tvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("pbtm_ag"));
		}
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_ag")).doubleValue()>0){
			   tvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("zntm_ag"));
		}else{
			   tvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("pbtm_ag"));
		}
		if(PuPubVO.getUFDouble_NullAsZero(monthvo.getAttributeValue("zntm_ag")).doubleValue()>0){
			   tvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("zntm_ag"));
		}else{
			   tvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("pbtm_ag"));
		}
		
		
//		tvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("zntm_ag"));
//		tvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("zntm_ag"));
//		tvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("zntm_ag"));		
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
//      znm_pb,znm_zn
		znvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("znm_pb"));
		znvo.setAttributeValue("m_pb_quarter",quartervo.getAttributeValue("znm_pb"));
		znvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("znm_pb"));
		
		znvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("znm_zn"));
		znvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("znm_zn"));
		znvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("znm_zn"));
		
		znvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("znm_ag"));
		znvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("znm_ag"));
		znvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("znm_ag"));
		
		//����Ǧ��������
		//num_month  num_quarter num_year ԭ��ʪ��
		znvo.setAttributeValue("num_month", monthvo.getAttributeValue("zn_noutnum"));
		znvo.setAttributeValue("num_quarter", quartervo.getAttributeValue("zn_noutnum"));
		znvo.setAttributeValue("num_year", yearvo.getAttributeValue("zn_noutnum"));
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
//      ptm_pb,ptm_zn,pbtm_ag
		pbvo.setAttributeValue("m_pb_month", monthvo.getAttributeValue("ptm_pb"));
		pbvo.setAttributeValue("m_pb_quarter",quartervo.getAttributeValue("ptm_pb"));
		pbvo.setAttributeValue("m_pb_year", yearvo.getAttributeValue("ptm_pb"));
		
		pbvo.setAttributeValue("m_zn_month", monthvo.getAttributeValue("ptm_zn"));
		pbvo.setAttributeValue("m_zn_quarter", quartervo.getAttributeValue("ptm_zn"));
		pbvo.setAttributeValue("m_zn_year", yearvo.getAttributeValue("ptm_zn"));
		
		pbvo.setAttributeValue("m_ag_month", monthvo.getAttributeValue("ptm_ag"));
		pbvo.setAttributeValue("m_ag_quarter", quartervo.getAttributeValue("ptm_ag"));
		pbvo.setAttributeValue("m_ag_year", yearvo.getAttributeValue("ptm_ag"));
		
		//����Ǧ��������
		//num_month  num_quarter num_year ԭ��ʪ��
		pbvo.setAttributeValue("num_month", monthvo.getAttributeValue("pb_noutnum"));
		pbvo.setAttributeValue("num_quarter", quartervo.getAttributeValue("pb_noutnum"));
		pbvo.setAttributeValue("num_year", yearvo.getAttributeValue("pb_noutnum"));
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
//      num_month  num_quarter num_year ԭ��ʪ��
		oredryvo.setAttributeValue("num_month", monthvo.getAttributeValue("ndrynum"));
		oredryvo.setAttributeValue("num_quarter",quartervo.getAttributeValue("ndrynum"));
		oredryvo.setAttributeValue("num_year", yearvo.getAttributeValue("ndrynum"));		
	}

	private void dealWaterVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO orewatervo) {
		
	}

	private void dealOreVO(ReportBaseVO monthvo, ReportBaseVO quartervo,
			ReportBaseVO yearvo, ReportBaseVO orevo) {
//      num_month  num_quarter num_year ԭ��ʪ��
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
	        return "2002AC021535";
	    }

}