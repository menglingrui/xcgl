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
	 * 处理vo
	 * 
	 * @author mlr
	 * @说明：（鹤岗矿业） 2011-12-21上午09:05:26
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
		// 查询动态列插入位置
		setLocation(2);
		getReportBase().getBillTable().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// 去除字段自动排序的功能
		getReportBase().getBillTable().removeSortListener();
		setColumn();
		// 设置查询动态列
	}

	@Override
	public void setUIAfterLoadTemplate() {

	}

	public void setQueryAfter() throws Exception {

	}

	/**
	 * @作者：mlr
	 * @说明：完达山物流项目 继承类JxReportBaseUI的类的 此方法书写规范 要想支持 按某个字段展开 必须 在查询模板 配置展开字段
	 *             已is开头 之后是表对应需要展开字段的名字 类型为boolean类型 中文名 为 是否按 ‘xx’ 展开 如果查询是 选中
	 *             如 是否按货位展开 则 取'货位'字段作为构造的查询动态列的 中文名字
	 *             getGroupByOrSelectConditon 此方法获得 查询框确定后 返回已经确定的展开的字段
	 * @时间：2011-5-10上午09:41:31
	 * 
	 */
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
					null,null);
			m_qryDlg.setNormalShow(false);
		}
		return m_qryDlg;
	}
	
}