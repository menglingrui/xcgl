package nc.ui.xcgl.report.dayreport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableColumnModel;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.trade.report.query.QueryDLG;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
/**
 * 选厂日报
 * @author mlr
 */
public class RepClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	//报表功能节点号 2002AC021525
	private QueryClientDLG m_qryDlg;
    //
	private List<Integer> rowformulas=new ArrayList<Integer>();
	//
	//固定单元格字段列表 name
	//选场，班次，开机时间,原矿,处理量湿量(吨）,原矿水分(%),处理量干量(吨）,Pb(%),Zn(%),Ag(g/t)
    //产出量（吨）,Pb(%),Zn(%),Ag(g/t)---->铅精粉
	//产出量（吨）,Pb(%),Zn(%)---->锌精粉
	//Pb(%),Ag(g/t)------->铅尾矿品位
	//Zn(%)---->锌尾
	//铅精粉Pb(%),铅精粉Ag(%),锌精粉Zn(%)
	//Pb(t),Zn(t),Ag(kg)---->铅精粉金属量
	//Pb(t),Zn(t)----->锌精粉金属量	
	//Pb(t),Zn(t),银(Kg)---->尾矿金属量
	//铅精矿,锌精矿(回收率)
	
	//
	//固定单元格字段列表 code
	//     选场，             班次，             开机时间,      原矿,       处理量湿量(吨）,原矿水分(%),处理量干量(吨） ,Pb(%) ,Zn(%) ,Ag(g/t)
	//pk_factory，pk_classorder，starhours,pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
	//                                 pk_oreinvbasdoc,
    //产出量（吨）  ,Pb(%),Zn(%),Ag(g/t)---->铅精粉
	//pb_noutnum,pb_pb,pb_zn,pb_ag
	
	//产出量（吨）   ,Pb(%), Zn(%)---->锌精粉
	//zn_noutnum,zn_pb, zn_zn
	
	//Pb(%),Ag(g/t)------->铅尾矿品位
	//pbt_pb,pbt_ag
	
	//Zn(%)---->锌尾
	//znt_zn
	
	//铅精粉Pb(%),铅精粉Ag(%),锌精粉Zn(%)
	//pb_pb_recrate,pb_ag_recrate,zn_zn_recrate
	
	//Pb(t) ,Zn(t) ,Ag(kg)---->铅精粉金属量
	//ptm_pb,ptm_zn,pbtm_ag
	
	//Pb(t) ,Zn(t)----->锌精粉金属量
	//znm_pb,znm_zn
	
	//Pb(t),    Zn(t),    银(Kg)---->尾矿金属量
	//pbtm_pb, zntm_zn,   pb_tmag
	//
	//pbtm_zn,zntm_pb,zntm_ag

	//铅精矿,    锌精矿,    银(Kg)---->产出率
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
     * 基本列合并
     */
    public void setColumn() {
        //表体栏目分组设置
        UITable cardTable = getReportBase().getBillTable();
        GroupableTableHeader cardHeader = (GroupableTableHeader) cardTable.getTableHeader();
        TableColumnModel cardTcm = cardTable.getColumnModel();
    
//        cardTable.getColumnName(6);
//        getReportBase().getBillModel().getColumnName(6);
//        getReportBase().getBillModel().getItemByKey("nwetnum").getIDColName();
        ColumnGroup oregroup= new ColumnGroup("原矿");
//pk_factory，pk_classorder，starhours,pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("nwetnum")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("nwater")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ndrynum")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_pb")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_zn")));
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_ag")));  
        //add
        oregroup.add(cardTcm.getColumn(getColumnIndexByCode("ore_au")));     
        cardHeader.addColumnGroup(oregroup);        
        ColumnGroup oregroup1= new ColumnGroup("精粉");
        
        ColumnGroup oregroup11= new ColumnGroup("铅精粉");
        //产出量（吨）  ,Pb(%),Zn(%),Ag(g/t)---->铅精粉
    	//pb_noutnum,pb_pb,pb_zn,pb_ag
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_noutnum")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_pb")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_zn")));
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_ag")));
        //add
        oregroup11.add(cardTcm.getColumn(getColumnIndexByCode("pb_au")));
        oregroup1.add(oregroup11);
  
        ColumnGroup oregroup12= new ColumnGroup("锌精粉");
        //产出量（吨）   ,Pb(%), Zn(%)---->锌精粉
      	//zn_noutnum,zn_pb, zn_zn,zn_ag
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_noutnum")));
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_pb")));
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_zn")));
        //新增固定单元格
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_ag")));   
        //add
        oregroup12.add(cardTcm.getColumn(getColumnIndexByCode("zn_au")));  
        oregroup1.add(oregroup12);       
        cardHeader.addColumnGroup(oregroup1);
 
        
        ColumnGroup oregroup2= new ColumnGroup("铅尾矿");
    	//Pb(%),Ag(g/t)------->铅尾矿品位
    	//pbt_pb,pbt_ag,pbt_zn
        oregroup2.add(cardTcm.getColumn(getColumnIndexByCode("pbt_pb")));
        oregroup2.add(cardTcm.getColumn(getColumnIndexByCode("pbt_ag")));
        //新增固定单元格
        oregroup2.add(cardTcm.getColumn(getColumnIndexByCode("pbt_zn")));  
        //add
        oregroup2.add(cardTcm.getColumn(getColumnIndexByCode("pbt_au")));       
        cardHeader.addColumnGroup(oregroup2);
    
        
        ColumnGroup oregroup21= new ColumnGroup("锌尾矿");
    	//Zn(%)---->锌尾
    	//znt_zn
        oregroup21.add(cardTcm.getColumn(getColumnIndexByCode("znt_zn")));
        //新增固定单元格
        oregroup21.add(cardTcm.getColumn(getColumnIndexByCode("znt_pb")));
        oregroup21.add(cardTcm.getColumn(getColumnIndexByCode("znt_ag")));    
        //add
        oregroup21.add(cardTcm.getColumn(getColumnIndexByCode("znt_au")));  
        cardHeader.addColumnGroup(oregroup21);        
        ColumnGroup oregroup3= new ColumnGroup("回收率");
    	//铅精粉Pb(%),铅精粉Ag(%),锌精粉Zn(%)
    	//pb_pb_recrate,pb_ag_recrate,zn_zn_recrate
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("pb_pb_recrate")));
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("pb_ag_recrate")));
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("zn_zn_recrate")));
        //add
        oregroup3.add(cardTcm.getColumn(getColumnIndexByCode("pb_au_recrate")));
        cardHeader.addColumnGroup(oregroup3);
        
        
        ColumnGroup oregroup4= new ColumnGroup("精粉金属量");        
        ColumnGroup oregroup41= new ColumnGroup("铅精粉金属量");
    	//Pb(t) ,Zn(t) ,Ag(kg)---->铅精粉金属量
    	//ptm_pb,ptm_zn,ptm_ag
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_pb")));
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_zn")));
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_ag")));
        //add
        oregroup41.add(cardTcm.getColumn(getColumnIndexByCode("ptm_au")));
        oregroup4.add(oregroup41);
        
        ColumnGroup oregroup42= new ColumnGroup("锌精粉金属量");
    	//Pb(t) ,Zn(t)----->锌精粉金属量
    	//znm_pb,znm_zn
        oregroup42.add(cardTcm.getColumn(getColumnIndexByCode("znm_pb")));
        oregroup42.add(cardTcm.getColumn(getColumnIndexByCode("znm_zn")));
        //新增固定单元格
        oregroup42.add(cardTcm.getColumn(getColumnIndexByCode("znm_ag")));  
        //add
        oregroup42.add(cardTcm.getColumn(getColumnIndexByCode("znm_au"))); 
        oregroup4.add(oregroup42);       
        cardHeader.addColumnGroup(oregroup4);
               
        ColumnGroup oregroup5= new ColumnGroup("铅尾矿金属量");
        //Pb(t),    Zn(t),    银(Kg)---->尾矿金属量
    	//pbtm_pb, zntm_zn,   pbtm_ag
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("pbtm_pb")));
        //新增固定单元格
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("pbtm_zn")));
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("pbtm_ag")));
        //add
        oregroup5.add(cardTcm.getColumn(getColumnIndexByCode("pbtm_au")));
        cardHeader.addColumnGroup(oregroup5);
        
        ColumnGroup oregroup51= new ColumnGroup("锌尾矿金属量");
        //Pb(t),    Zn(t),    银(Kg)---->尾矿金属量
    	//pbtm_pb, zntm_zn,   pbtm_ag
        oregroup51.add(cardTcm.getColumn(getColumnIndexByCode("zntm_zn")));
        //新增固定单元格
        oregroup51.add(cardTcm.getColumn(getColumnIndexByCode("zntm_pb")));       
        oregroup51.add(cardTcm.getColumn(getColumnIndexByCode("zntm_ag")));
        //add
        oregroup51.add(cardTcm.getColumn(getColumnIndexByCode("zntm_au")));
        cardHeader.addColumnGroup(oregroup51);
        
        
        ColumnGroup oregroup6= new ColumnGroup("产出率");
        //铅精矿,    锌精矿,    银(Kg)---->产出率
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
	 * 构造函数
	 */
	public RepClientUI() {
		super();
		setColumn();
	}
	/**
	 * 查询完成 设置到ui界面之后 后续处理
	 * @author mlr
	 * @说明：（鹤岗矿业） 2011-12-22上午10:42:36
	 * @param list
	 * @return
	 */
	public void dealQueryAfter() throws Exception {
		super.dealQueryAfter();
		for(int i=0;i<rowformulas.size();i++){
			getReportBase().getBillModel().execFormulas(rowformulas.get(i), DayReportTool.formulas);
		}
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
		vos=DayReportTool.getDealVO(vos);	
		rowformulas.clear();
		//处理日累计，月累计，季累计，年累计
		List<ReportBaseVO> rlist=new ArrayList<ReportBaseVO>();
		ReportBaseVO[][] rvoss=(ReportBaseVO[][]) SplitBillVOs.getSplitVOs(vos, DayReportTool.daymapkey);
		for(int i=0;i<rvoss.length;i++){
			ReportBaseVO[] dvos=rvoss[i];	
			for(int j=0;j<dvos.length;j++){
				rlist.add(dvos[j]);
			}
			ReportBaseVO dayvo=DayReportTool.getDayReportVO(dvos);
			dayvo.setAttributeValue("dbilldate", "日合计");
			dayvo.setAttributeValue("classorder", "日合计");
			ReportBaseVO monthvo=DayReportTool.getMonthReportVO(dvos);
			monthvo.setAttributeValue("dbilldate", "月合计");
			monthvo.setAttributeValue("classorder", "月合计");
			ReportBaseVO quartervo=DayReportTool.getQuarterVO(dvos);
			quartervo.setAttributeValue("dbilldate", "季度合计");
			quartervo.setAttributeValue("classorder", "季度合计");
			ReportBaseVO yearvo=DayReportTool.getYearVO(dvos);
			yearvo.setAttributeValue("dbilldate", "年度合计");
			yearvo.setAttributeValue("classorder", "年度合计");
			rlist.add(dayvo);
			rowformulas.add(rlist.size()-1);
			rlist.add(monthvo);
			rowformulas.add(rlist.size()-1);
			rlist.add(quartervo);
			rowformulas.add(rlist.size()-1);
			rlist.add(yearvo);		
			rowformulas.add(rlist.size()-1);
		}
		return rlist.toArray(new ReportBaseVO[0]);
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