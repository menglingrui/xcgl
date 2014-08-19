package nc.ui.xcgl.runrecord;

import java.util.List;

import javax.swing.ListSelectionModel;
import java.util.Map;

import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeCodeConst;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.CombinVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
import nc.ui.trade.report.query.QueryDLG;

public class ReportUI extends ZmReportBaseUI3{

	private static final long serialVersionUID = 1L;
	private QueryClientDLG m_qryDlg = null;
	//2002AC020502
	private String[] combinConds = { "pk_corp", "pk_equipment", "pk_factory",
			 "pk_classorder", "pk_beltline"};
//	private String[] combinFields1 = null;
	private String[] combinFields1 = { "nduration" };

	public String[] getSqls() throws Exception {
		return new String[] { getQuerySQL1(getQueryConditon()) };
	}

	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		return deal(list);
	}

	/**
	 * ����vo
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-21����09:05:26
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private ReportBaseVO[] deal(List<ReportBaseVO[]> list) throws Exception {
		if (list == null || list.size() == 0)
			return null;
		ReportBaseVO[] rvos = list.get(0);
		ReportBaseVO[] nrovs = (ReportBaseVO[]) CombinVO.combinData(rvos,
				combinConds, combinFields1, ReportBaseVO.class);
//		ReportBaseVO[] rvos1 = list.get(1);
//		ReportBaseVO[] nrvos1 = (ReportBaseVO[]) CombinVO.combinData(rvos1,
//				combinConds, combinFields1, ReportBaseVO.class);
//		ReportBaseVO[] zvos = CombinVO.addByContion1(nrvos1, nrovs,
//				combinConds, null);
		return nrovs;
	}

	private String getQuerySQL1(String queryConditon) throws Exception {
		String whereSql=getQueryConditon();
		if(whereSql==null ||whereSql.length()==0)
			whereSql=" ( pk_billtype='"+PubBillTypeConst.billtype_runrecord+"')" ;
//					" or pk_billtype='"+CaPubConst.bill_type_idespose+"' )";
		else{
			whereSql=whereSql+" and "+" ( pk_billtype='"+PubBillTypeConst.billtype_runrecord+"')" ;
//			" or pk_billtype='"+CaPubConst.bill_type_idespose+"' )";
		}      
		return Reportsql.getSql(whereSql);
	}

//	private String getQuerySQL2(String queryConditon) throws Exception {
//		return Reportsql.getBusinessSql(queryConditon);
//	}

	@Override
	public String _getModelCode() {
		return PubNodeCodeConst.NodeCode_runrecord1;
	}

	public ReportUI() {
		super();
		// ��ѯ��̬�в���λ��
		setLocation(2);
		getReportBase().getBillTable().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// ȥ���ֶ��Զ�����Ĺ���
		getReportBase().getBillTable().removeSortListener();
		setColumn();
		// ���ò�ѯ��̬��
	}

	@Override
	public void setUIAfterLoadTemplate() {

	}

	public void setQueryAfter() throws Exception {

	}

	/**
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ �̳���JxReportBaseUI����� �˷�����д�淶 Ҫ��֧�� ��ĳ���ֶ�չ�� ���� �ڲ�ѯģ�� ����չ���ֶ�
	 *             ��is��ͷ ֮���Ǳ��Ӧ��Ҫչ���ֶε����� ����Ϊboolean���� ������ Ϊ �Ƿ� ��xx�� չ�� �����ѯ�� ѡ��
	 *             �� �Ƿ񰴻�λչ�� �� ȡ'��λ'�ֶ���Ϊ����Ĳ�ѯ��̬�е� ��������
	 *             getGroupByOrSelectConditon �˷������ ��ѯ��ȷ���� �����Ѿ�ȷ����չ�����ֶ�
	 * @ʱ�䣺2011-5-10����09:41:31
	 * 
	 */
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
					null,null);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}
	
}