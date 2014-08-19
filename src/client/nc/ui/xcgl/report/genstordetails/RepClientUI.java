package nc.ui.xcgl.report.genstordetails;

import java.util.List;
import java.util.Map;

import javax.swing.ListSelectionModel;

import nc.ui.trade.report.query.QueryDLG;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
/**
 * ���������ˮ��ϸ
 * @author ddk
 *
 */
public class RepClientUI extends ZmReportBaseUI3{
	private static final long serialVersionUID = 1L;
	//2002AC022525  �����ܽڵ��
	private QueryClientDLG m_qryDlg;
	/**
	 * ��ⵥά��
	 */
	String [] combinConds={"pk_corp","pk_minaera","pk_factory","pk_stordoc",
			"pk_vehicle","pk_invmandoc","pk_invbasdoc","nwetweight"};
	private String[] combinFields1 = null;
	private String[] combinFields2 = {"nduration"};
	
	public String[] getSqls() throws Exception {
		return new String[] { getQuerySQL1(getQueryConditon()) };
	}

	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		return deal(list);
	}
	
	private ReportBaseVO[] deal(List<ReportBaseVO[]> list) throws Exception {
		if (list == null || list.size() == 0)
			return null;
		ReportBaseVO[] rvos = list.get(0);
//		ReportBaseVO[] nrovs = (ReportBaseVO[]) CombinVO.combinData(rvos,
//				combinConds, combinFields2, ReportBaseVO.class);
//		ReportBaseVO[] rvos1 = list.get(1);
//		ReportBaseVO[] nrvos1 = (ReportBaseVO[]) CombinVO.combinData(rvos1,
//				combinConds, combinFields1, ReportBaseVO.class);
//		ReportBaseVO[] zvos = CombinVO.addByContion1(nrvos1, nrovs,
//				combinConds, null);
		return rvos;
	}
	
	
	
	/**
	 * ���캯��
	 */
	public RepClientUI() {
		super();
		//��ѯ��̬�в���λ��
		setLocation(2);
		getReportBase().getBillTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//ȥ���ֶ��Զ�����Ĺ���
		getReportBase().getBillTable().removeSortListener();
		setColumn();
	}
	
	private String getQuerySQL1(String queryConditon) {
		String whereSql=getQueryCondition();
		if(whereSql==null||whereSql.length()==0){
			whereSql=" (pk_billtype='" + PubBillTypeConst.billtype_Generalin +"'"
			+" or pk_billtype='"+PubBillTypeConst.billtype_concentratein+"')";
		}else{
			whereSql=whereSql+"and (pk_billtype='"+PubBillTypeConst.billtype_Generalin+"'" +
			" or pk_billtype='"+PubBillTypeConst.billtype_concentratein+"')";
		}
		return ReportSql.getBusinessSql1(whereSql);
	}
	
	private String getQueryCondition() {
		return null;
	}

	@Override
	public String _getModelCode() {
		return "2002AC022525";
	}
	
	@Override
	public void setUIAfterLoadTemplate() {

	}

	public void setQueryAfter() throws Exception {

	}
	
	public String getQuerySQL() throws Exception {

		return null;
	}

	/**
	 * �ϼ�
	 */
	public void setTolal() throws Exception {
	}

	/**
	 * Ҫ���ʼʱ���ض�̬�� ʵ�ִ˷���
	 */
	@Override
	public Map getNewItems() throws Exception {
		return null;
	}

	@Override
	public void initReportUI() {

	}

	public QueryDLG getQueryDlg() {
		if (m_qryDlg == null) {
			m_qryDlg = new QueryClientDLG(this);
			m_qryDlg.setTempletID(_getCorpID(), _getModelCode(), _getUserID(),
					null, null,OrgnizeTypeVO.COMPANY_TYPE);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}
}
