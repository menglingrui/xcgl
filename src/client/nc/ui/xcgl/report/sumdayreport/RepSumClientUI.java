package nc.ui.xcgl.report.sumdayreport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumnModel;

import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.xcgl.report.dayreport.DayReportConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
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
	public RepSumClientUI() {
		super();
		setColumn();
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
		ReportBaseVO[] vos=getDealVO(list.get(0));
		
		ReportBaseVO[] sumvos=(ReportBaseVO[]) CombinVO.combinData(
				(ReportBaseVO[])ObjectUtils.serializableClone(vos), combinConditions, combinFields);
		
		ReportBaseVO[] avgvos=CombinVO.averageData(
				(ReportBaseVO[])ObjectUtils.serializableClone(vos), AverageConditions, AveragecombinFields);
		
		
	    CombinVO.copyValueByContion(
	    		  sumvos,avgvos, AverageConditions, AveragecombinFields);
		
		return sumvos;
	}

	private ReportBaseVO[] getDealVO(ReportBaseVO[] vos) throws BusinessException {
		if(vos==null || vos.length==0){
			return null;
		}
		List<ReportBaseVO> list=new ArrayList<ReportBaseVO>();
		ReportBaseVO[][] voss=(ReportBaseVO[][]) SplitBillVOs.getSplitVOs(vos, mapkey);
		for(int i=0;i<voss.length;i++){
			ReportBaseVO[] bvos=voss[i];
			ReportBaseVO vo=getDealOreVO(bvos);
			dealPowerAndTail(vo,bvos);		
			list.add(vo);
		}
		return list.toArray(new ReportBaseVO[0]);
	}

	public void dealPowerAndTail(ReportBaseVO vo, ReportBaseVO[] bvos) {
		dealPbPower(vo,bvos);
		dealZnPower(vo,bvos);
		dealPbTail(vo,bvos);
		dealZnTail(vo,bvos);	    
	}

	private void dealZnTail(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Pb_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Zn_index);
	//	UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,);
		UFDouble Znnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Zn_tail,DayReportConst.Zn_index);
    	//zn_noutnum,zn_pb, zn_zn
        vo.setAttributeValue("znt_pb", Pbgrade);
	    vo.setAttributeValue("znt_zn", Zngrade);
	 //   vo.setAttributeValue("znt_noutnum", noutnum);
	    vo.setAttributeValue("zntm_zn", Znnoutmetalnum);
	}

	private void dealPbTail(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Pb_index);
	    UFDouble Aggrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Ag_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Zn_index);
	//	UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Pb_index);
		UFDouble Pbnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Pb_index);
		UFDouble Agnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_tail,DayReportConst.Pb_tail,DayReportConst.Ag_index);
        vo.setAttributeValue("pbt_pb", Pbgrade);
	    vo.setAttributeValue("pbt_ag", Aggrade);
	    vo.setAttributeValue("pbt_zn", Zngrade);
	//    vo.setAttributeValue("pbt_noutnum", noutnum);
	    vo.setAttributeValue("pbtm_pb", Pbnoutmetalnum);
	    vo.setAttributeValue("pbtm_ag", Agnoutmetalnum);
	}

	private void dealZnPower(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Pb_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
		UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
		UFDouble Znnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
        UFDouble Znrecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Zn_power,DayReportConst.Zn_index);
    	//zn_noutnum,zn_pb, zn_zn
        vo.setAttributeValue("zn_pb", Pbgrade);
	    vo.setAttributeValue("zn_zn", Zngrade);
	    vo.setAttributeValue("zn_noutnum", noutnum);
	    vo.setAttributeValue("znm_zn", Znnoutmetalnum);
	    vo.setAttributeValue("zn_zn_recrate", Znrecrate);
	}

	private void dealPbPower(ReportBaseVO vo, ReportBaseVO[] bvos) {
	    UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
	    UFDouble Aggrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Ag_index);
		UFDouble Zngrade=getGrade(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Zn_index);
		UFDouble noutnum=getNoutNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
		UFDouble Pbnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
		UFDouble Agnoutmetalnum=getNoutMetalNum(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Ag_index);
        UFDouble Pbrecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
        UFDouble Agrecrate=getNRecRate(bvos,DayReportConst.type_power,DayReportConst.Pb_power,DayReportConst.Pb_index);
    	//pb_noutnum,pb_pb,pb_zn,pb_ag
        vo.setAttributeValue("pb_pb", Pbgrade);
	    vo.setAttributeValue("pb_ag", Aggrade);
	    vo.setAttributeValue("pb_zn", Zngrade);
	    vo.setAttributeValue("pb_noutnum", noutnum);
	    vo.setAttributeValue("ptm_pb", Pbnoutmetalnum);
	    vo.setAttributeValue("ptm_ag", Agnoutmetalnum);
	    vo.setAttributeValue("pb_pb_recrate", Pbrecrate);
	    vo.setAttributeValue("pb_ag_recrate", Agrecrate);
	}

	public UFDouble getNRecRate(ReportBaseVO[] bvos, String type_power,
			String pb_power, String pb_index) {
		for(int i=0;i<bvos.length;i++){
			if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
				if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
				   return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("nrecover"));
				}   
			}
		}
		return new UFDouble(0);
	}

	public UFDouble getNoutMetalNum(ReportBaseVO[] bvos, String type_power,
			String pb_power, String pb_index) {
		for(int i=0;i<bvos.length;i++){
			if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
				if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
				    return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("nmetalamount"));
				}
			}
		}
		return new UFDouble(0);
	}

	private UFDouble getNoutNum(ReportBaseVO[] bvos, String type_power,
			String pb_power,String pk_invdex) {
		for(int i=0;i<bvos.length;i++){
			if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
				if(pk_invdex.equals(bvos[i].getAttributeValue("pk_invindex"))){
				   return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("noutput"));
				}
			}
		}
		return new UFDouble(0);
	}

	private UFDouble getGrade(ReportBaseVO[] bvos, String type_power,
			String pb_power, String pb_index) {
		if(type_power.equals(DayReportConst.type_ore)){
			for(int i=0;i<bvos.length;i++){
				if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
					return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("ncrudescontent"));
				}
			}
		}else{
			for(int i=0;i<bvos.length;i++){
			  
				if(pb_power.equals(bvos[i].getAttributeValue("pk_invbasdoc"))){
					if(pb_index.equals(bvos[i].getAttributeValue("pk_invindex"))){
						return PuPubVO.getUFDouble_NullAsZero(bvos[i].getAttributeValue("ncontent"));
					}
					
				}
			}
		}		
		return new UFDouble(0);
	}

	public ReportBaseVO getDealOreVO(ReportBaseVO[] bvos) throws BusinessException {
		try {
			 ReportBaseVO vo=(ReportBaseVO) ObjectUtils.serializableClone(bvos[0]);
			 UFDouble Pbgrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Pb_index);
		     UFDouble Aggrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Ag_index);
			 UFDouble Zngrade=getGrade(bvos,DayReportConst.type_ore,null,DayReportConst.Zn_index);
			 vo.setAttributeValue("ore_pb", Pbgrade);
			 vo.setAttributeValue("ore_ag", Aggrade);
			 vo.setAttributeValue("ore_zn", Zngrade);
			 return vo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

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