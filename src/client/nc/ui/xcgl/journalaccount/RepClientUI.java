package nc.ui.xcgl.journalaccount;
import java.util.List;
import java.util.Map;

import javax.swing.ListSelectionModel;

import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.report.query.QueryDLG;
import nc.vo.pub.BusinessException;
import nc.vo.pub.query.ConditionVO;
import nc.vo.sm.nodepower.OrgnizeTypeVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.HYPubQueryDlg;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
/**
 * �������ˮ��
 * @author ddk	
 *
 */
public class RepClientUI extends ZmReportBaseUI3{
	private static final long serialVersionUID = 1L;
	//�����ܽڵ��   2002AC022531
	private QueryClientDLG m_qryDlg;
	

	public String[] getSqls() throws Exception {
		return new String[] { 
				getQuerySQL1(getQueryConditon()),
				getQuerySQL2(getQueryConditon())
		};
	}

	/**
	 * ���캯��
	 */
	public RepClientUI() {
		super();
		//��ѯ��̬�в���λ��
		setLocation(2);
		
		//ReportItem it=ReportPubTool.getItem("qrycondition","��ѯ����",IBillItem.TEXTAREA,7, 300);
		
	//	getReportBase().getHeadItem("qrycondition").setDataType(IBillItem.TEXTAREA);
//		getReportBase().getHeadItem("qrycondition").setWidth(500);
//		getReportBase().setMaxLenOfHeadItem("qrycondition", 500);
		getReportBase().getHeadItem("qrycondition").setWidth(4);
		
		getReportBase().getBillTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//ȥ���ֶ��Զ�����Ĺ���
		getReportBase().getBillTable().removeSortListener();
		setColumn();
		
		
	}
	
	  @Override
		public void onQuery() {
			getQueryDlg().showModal();
			if (getQueryDlg().getResult() == UIDialog.ID_OK) {
				try {
					 setQuery(true);
					// ��ձ�������
					clearBody();
					setDynamicColumn1();
					// �õ���ѯ���
				  	setColumn();
				  	
				  	ConditionVO[] svos=((HYPubQueryDlg)getQueryDlg()).getConditionVOsByFieldName("��ʼ����");
				  	if(svos!=null&&svos.length>0){
				  		getReportBase().setHeadItem("dbilldatefrom", svos[0].getValue());
				  	}
				  	
				  	
				 	ConditionVO[] dvos=((HYPubQueryDlg)getQueryDlg()).getConditionVOsByFieldName("��������");
				  	if(dvos!=null&&dvos.length>0){
				  		getReportBase().setHeadItem("dbilldateto", dvos[0].getValue());
				  	}
				
				  	// (��ʼ���� ���� '2014-03-01') ���� (�������� ���� '2014-03-19')
				  	getReportBase().setHeadItem("qrycondition",getQueryDlg().getChText().trim());
				  	
				//	getReportBase().setHeadItem("qrycondition","(��ʼ���� ���� ) ");
				  

					List<ReportBaseVO[]> list = getReportVO(getSqls());
                    sqls=getSqls();
					ReportBaseVO[] vos = null;
					vos=dealBeforeSetUI(list);
					if (vos == null || vos.length == 0)
						return;
					if (vos != null) {
						super.updateBodyDigits();
						setBodyVO(vos);
//						updateVOFromModel();//���¼��س�ʼ����ʽ  
						dealQueryAfter();//��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
					}
				} catch (BusinessException e) {
					e.printStackTrace();
					this.showErrorMessage(e.getMessage());

				} catch (Exception e) {
					e.printStackTrace();
					this.showErrorMessage(e.getMessage());

				}
			}
		}
	
	public String getQuerySQL1(String queryConditon) throws Exception {
		String whereSql=getQueryConditon();
		if(whereSql==null||whereSql.length()==0){
			whereSql=" (pk_billtype='" +PubBillTypeConst.billtype_Generalin +"' "+
					" or pk_billtype='"+PubBillTypeConst.billtype_concentratein+"' " +
					" or pk_billtype='"+PubBillTypeConst.billtype_genotherin+"')" ;
		}else{
			whereSql=whereSql+" and (pk_billtype='" + PubBillTypeConst.billtype_Generalin +"' "+
					" or pk_billtype='"+PubBillTypeConst.billtype_concentratein+"' " +
					" or pk_billtype='"+PubBillTypeConst.billtype_genotherin+"') "; 
		}
		return ReportSql.getBusinessSql1(whereSql);
	}
	public String getQuerySQL2(String queryConditon) throws Exception {
		String whereSql=getQueryConditon();
		if(whereSql==null||whereSql.length()==0){
			whereSql=" (pk_billtype='" +PubBillTypeConst.billtype_Generalout+"' " +
					" or pk_billtype='"+PubBillTypeConst.billtype_Drugconsume+"' "+
					" or pk_billtype='"+PubBillTypeConst.billtype_saleout+"' " +
					" or pk_billtype='"+PubBillTypeConst.billtype_genotherout+"')";
		}else{
			whereSql=whereSql+" and (pk_billtype='" + PubBillTypeConst.billtype_Generalout+"' " +
					" or pk_billtype='"+PubBillTypeConst.billtype_Drugconsume+"' "+
					" or pk_billtype='"+PubBillTypeConst.billtype_saleout+"' " +
					" or pk_billtype='"+PubBillTypeConst.billtype_genotherout+"')";
		}
		return ReportSql.getBusinessSql2(whereSql);
	}
	
	private String getQueryCondition() {
		return null;
	}
	@Override
	public String _getModelCode() {
		return "2002AC022531";
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