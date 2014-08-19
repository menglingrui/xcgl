package nc.ui.xcgl.weighcount;

import java.util.Map;

import javax.swing.ListSelectionModel;

import nc.ui.trade.report.query.QueryDLG;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;

/**
 * 过磅统计
 * 
 * @author jay
 * 
 */
public class RepClientUI extends ZmReportBaseUI3 {
	private static final long serialVersionUID = 1L;
	// 报表功能节点号 2002AC021010
	private QueryClientDLG m_qryDlg;

	public String[] getSqls() throws Exception {
		return new String[] { getQuerySQL1(getQueryConditon()),
		// getQuerySQL2(getQueryConditon())
		};
	}

	/**
	 * 构造函数
	 */
	public RepClientUI() {
		super();
		// 查询动态列插入位置
		setLocation(2);

		// ReportItem
		// it=ReportPubTool.getItem("qrycondition","查询条件",IBillItem.TEXTAREA,7,
		// 300);

		// getReportBase().getHeadItem("qrycondition").setDataType(IBillItem.TEXTAREA);
		// getReportBase().getHeadItem("qrycondition").setWidth(500);
		// getReportBase().setMaxLenOfHeadItem("qrycondition", 500);
		// getReportBase().getHeadItem("qrycondition").setWidth(4);

		getReportBase().getBillTable().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// 去除字段自动排序的功能
		getReportBase().getBillTable().removeSortListener();
		setColumn();

	}

	public String getQuerySQL1(String queryConditon) throws Exception {
		String whereSql = getQueryConditon();
		if (whereSql == null || whereSql.length() == 0) {
			whereSql = " (isnull(xcgl_weighdoc.dr,0)=0) and (isnull(xcgl_weighdoc_b.dr,0)=0)";
		} else {
			whereSql = whereSql
					+ " and (isnull(xcgl_weighdoc.dr,0)=0) and (isnull(xcgl_weighdoc_b.dr,0)=0) ";
		}
		return ReportSql.getBusinessSql1(whereSql);
	}

	private String getQueryCondition() {
		return null;
	}

	@Override
	public String _getModelCode() {
		return "2002AC021010";
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
	 * 合计
	 */
	public void setTolal() throws Exception {
	}

	/**
	 * 要想初始时加载动态列 实现此方法
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
					null, null, OrgnizeTypeVO.COMPANY_TYPE);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}

}