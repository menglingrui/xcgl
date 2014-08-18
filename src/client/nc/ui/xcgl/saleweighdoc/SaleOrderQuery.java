package nc.ui.xcgl.saleweighdoc;

import java.util.ArrayList;

import javax.swing.table.TableCellEditor;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.ui.bd.datapower.DataPowerServ;
import nc.ui.bd.manage.UIRefCellEditor;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.bd.ref.busi.DeptdocDefaultRefModel;
import nc.ui.bd.ref.busi.PsndocDefaulRefModel;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.pf.IinitQueryData;
import nc.ui.pub.query.EditComponentFacotry;
import nc.ui.scm.pub.ScmPubHelper;
import nc.ui.scm.pub.query.ConvertQueryCondition;
import nc.ui.scm.pub.query.SCMQueryConditionDlg;
import nc.ui.scm.ref.prm.CustAddrRefModel;
import nc.ui.scm.so.SaleBillType;
import nc.ui.so.pub.DataPowerUtils;
import nc.ui.so.so001.panel.list.SOBusiTypeRefPane;
import nc.vo.bd.CorpVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.ConditionVO;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.RefResultVO;
import nc.vo.scm.pub.SCMEnv;

/**
 * ���۲��ղ�ѯ�ࡣ
 * 
 * �������ڣ�(2002-11-20 19:41:34)
 * 
 * @author�������
 * 
 * @see		SaleOrderDLG	ת������
 * 
 */
public class SaleOrderQuery extends SCMQueryConditionDlg implements
		IinitQueryData {

	public SaleOrderQuery() {
		super();
		setSealedDataShow(true);
	}

	public SaleOrderQuery(java.awt.Container parent) {
		super(parent);
		setSealedDataShow(true);
	}

	public SaleOrderQuery(java.awt.Container parent, String title) {
		super(parent, title);
		setSealedDataShow(true);
	}

	public SaleOrderQuery(java.awt.Frame parent) {
		super(parent);
		setSealedDataShow(true);
	}

	public SaleOrderQuery(java.awt.Frame parent, String title) {
		super(parent, title);
		setSealedDataShow(true);
	}
	public SaleOrderQuery(java.awt.Container parent,String pkCorp, String operator,
			String funNode, String businessType,
			String currentBillType, String sourceBilltype,
			Object userObj) {
		super(parent);
		initData(pkCorp,  operator,funNode,  businessType, currentBillType,  sourceBilltype, userObj);
		setSealedDataShow(true);
	}

	public SaleOrderQuery(boolean isFixedSet) {
		super(isFixedSet);
		setSealedDataShow(true);
	}

	/**
	 * ��ýڵ�nodeKey��
	 * 
	 * �������ڣ�(2001-11-27 13:51:07)
	 * 
	 * @return String
	 * 
	 */
	public String getNodeCode(String strCurrentBillType, String strBillType) {
		
		String nodecode = null;

		if (strCurrentBillType.equals(SaleBillType.SaleInvoice)
				&& strBillType.equals(SaleBillType.SaleOrder))
			nodecode = "40069901";
		if ("3U".equals(strCurrentBillType))
			nodecode = "40069901";
		if (strCurrentBillType.equals(SaleBillType.SaleOutStore)
				&& strBillType.equals(SaleBillType.SaleOrder))
			nodecode = "40069902";

		return nodecode;
	}
	public String getCorp() {
		String userId = nc.ui.pub.ClientEnvironment.getInstance()
				.getCorporation().getPk_corp();
		return userId;
	}

	private String m_billtypesource;
	
	private String m_billtypecurrent;
	
	private String m_businesstype;
	
	private String pk_corp;
	
	public void initData(String pkCorp, String operator,
			String funNode, String businessType,
			String currentBillType, String sourceBilltype,
			Object userObj){
		
		m_billtypesource = sourceBilltype;
		m_billtypecurrent = currentBillType;
		m_businesstype = businessType;
		pk_corp = pkCorp;
		
		//30-->32[3U]	SO30TO32000000000000
		//30-->4C		SO30TO4C000000000000
		//30-->21		SO30TO21000000000000
		if (currentBillType != null && currentBillType.equals("21")) {
			if(m_businesstype.equals("ֱ��")){ /*-=notranslate=-*/
//				setTempletID("SO30TO21000000000001");
				setTempletID(pkCorp, "40060301", operator, null,"4006030102");
			}else if (m_businesstype.equals("Эͬ")){ /*-=notranslate=-*/
//				setTempletID("SO30TO21000000000000");
				setTempletID(pkCorp, "40060301", operator, null,"4006030108");
				UIRefPane ref4 = new UIRefPane();
				ref4.setRefNodeName("��˾Ŀ¼"); /*-=notranslate=-*/
				ref4.getRefModel().addWherePart(" and pk_corp in (select ccustomerid from so_coopwith where rtrim(cmaincustomerid) = '"+getCorp()+"' and csourcereceipttype = '30')");
				setValueRef("so_sale.pk_corp", ref4);
			}
		}else if (currentBillType != null && currentBillType.equals(SaleBillType.SaleReceive)
				&& sourceBilltype!=null && sourceBilltype.equals(SaleBillType.SaleOrder)){ 
			setTempletID(pkCorp, "40060301", operator, null,"4006030101");
		}else
			setTempletID(pkCorp, "40060302", operator, null, getNodeCode(
					currentBillType, sourceBilltype));

		//�ջ���ַ
		UIRefPane carfp = new UIRefPane(this);
		try{
		carfp.setRefModel(new CustAddrRefModel() {
			
			String sRefName = "�ͻ�����"; /*-=notranslate=-*/

			String dpTableName = DataPowerServ.getRefTableName(sRefName);

			String[] pk_corps = new String[] { getPk_corp() };

			public String getWherePart() {
				String sql = " bd_cumandoc.pk_corp='"
						+ getClientEnvironment().getCorporation()
								.getPrimaryKey() + "'";
				return sql
						+ " and bd_cumandoc.pk_cumandoc in "
						+ getPowerSubSql(dpTableName, sRefName, getPk_user(),
								pk_corps);
			}
		});
		}catch(Exception e){
			return;
		}
		setValueRef("so_saleorder_b.vreceiveaddress", carfp);
		
//		// ��Ŀ
//		UIRefPane refpane = new UIRefPane();
//		refpane.setRefType(nc.ui.bd.ref.IBusiType.GRIDTREE);
//		JobRefTreeModel treemodel = new JobRefTreeModel("0001", pk_corp,
//				null);
//		treemodel.setUseDataPower(true);
//		refpane.setRefModel(treemodel);
//		setValueRef("so_saleexecute.cprojectid", refpane);
		
		super.addCurToCurDate("so_sale.dbilldate");
		
		QueryConditionVO[] vos = getConditionDatas();
		if (vos != null) {
			nc.ui.pub.beans.UIRefPane ref = null;			
			
			for (int i = 0; i < vos.length; i++) {
				
				//�ջ���ַ
				if (vos[i].getFieldCode() != null
						&& vos[i].getFieldCode().indexOf("vreceiveaddress") > 0) {
					vos[i].setReturnType(new Integer(RETURNCODE));
					vos[i].setDispType(new Integer(DISPCODE));
					if (vos[i].getDataType() != null
							&& vos[i].getDataType().intValue() == nc.vo.pub.query.IQueryConstants.UFREF) {
						EditComponentFacotry factry = new EditComponentFacotry(
								vos[i]);
						ref = (nc.ui.pub.beans.UIRefPane) factry
								.getEditComponent(null);
						ref.setReturnCode(true);
						ref.getRefModel().setRefCodeField(
								"bd_custaddr.addrname");
						ref.getRefModel().setRefNameField(
								"bd_custaddr.addrname");

					}

				}

				if (SaleBillType.SaleInvoice
						.equals(currentBillType)
						&& vos[i].getFieldCode() != null
						&& vos[i].getFieldCode().indexOf("cinventoryid") > 0) {
					if (vos[i].getDataType() != null
							&& vos[i].getDataType().intValue() == nc.vo.pub.query.IQueryConstants.UFREF) {
						EditComponentFacotry factry = new EditComponentFacotry(
								vos[i]);
						ref = (nc.ui.pub.beans.UIRefPane) factry
								.getEditComponent(null);
						ref
								.getRefModel()
								.setWherePart(
										ref.getRefModel().getWherePart()
												+ " and bd_invmandoc.iscansaleinvoice='Y' ");

					}

				}
				
				if (vos[i].getFieldCode() != null
						&& (vos[i].getFieldCode().indexOf("dbilldate") >= 0)) {
					vos[i].setValue(nc.ui.pub.ClientEnvironment.getInstance()
							.getDate().toString());
				}
        
        /** ֱ�����۲ɹ���Ĭ���Ƶ�����  **/
        if ((vos[i].getFieldCode() != null &&  
            vos[i].getFieldCode().indexOf("dmakedate") >= 0)
            && ("ֱ��".equals(m_businesstype)) /*-=notranslate=-*/
            && (SaleBillType.SaleOrder.equals(sourceBilltype))) {
          vos[i].setValue(nc.ui.pub.ClientEnvironment.getInstance()
              .getDate().toString());
        }

				/** �˻����뵥�������۶���������ѡ��ҵ������* */
				if ((vos[i].getFieldCode() != null && vos[i].getFieldCode()
						.indexOf("so_sale.cbiztype") >= 0)
						&& ("3U".equals(currentBillType))
						&& (SaleBillType.SaleOrder.equals(sourceBilltype))) {
					vos[i].setIfDefault(UFBoolean.TRUE);
				}
				
			}// end for
		}

		// ҵ������
		SOBusiTypeRefPane biztypeRef = new SOBusiTypeRefPane(pkCorp);
		if("ֱ��".equals(m_businesstype))//�ɹ�����-����ֱ�����۶�����ҵ������ֻ��ʾ��ֱ�ˡ�  tangmc liuzwc 2009-07-23 /*-=notranslate=-*/
			biztypeRef.getRefModel().setWherePart(biztypeRef.getRefModel().getWherePart()+" and verifyrule = 'Z' ");
		setValueRef("so_sale.cbiztype", biztypeRef);
		
		//���ù�˾��ص���Ȩ�޲���
		setCorpRalationRefs();
		
		hideNormal();

	}
	
	
	private void setCorpRalationRefs(){
		// ���⹫˾
		IUserManageQuery config = (IUserManageQuery) NCLocator.getInstance()
				.lookup(IUserManageQuery.class.getName());
		CorpVO[] corpVos = null;
		try {
			corpVos = config.queryAllCorpsByUserPK(ClientEnvironment
					.getInstance().getUser().getPrimaryKey());
		} catch (BusinessException e) {
			SCMEnv.out(e);
		}

		ArrayList<String> alcorp = new ArrayList<String>();
		if (corpVos != null && corpVos.length > 0) {
			for (int i = 0; i < corpVos.length; i++) {
				alcorp.add(corpVos[i].getPk_corp());
			}
		}

		// ����Ȩ��
		setRefsDataPowerConVOs(ClientEnvironment.getInstance().getUser()
				.getPrimaryKey(), new String[] { pk_corp }, new String[] {
				"�ͻ�����", "��������", "������֯", "���ŵ���", "��Ա����", "�������", "��������" ,"�ͻ�����"}, new String[] { /*-=notranslate=-*/
				"so_sale.ccustomerid", "ccustomerid", "so_sale.csalecorpid",
				"so_sale.cdeptid", "so_sale.cemployeeid",
				"so_saleorder_b.cinventoryid","so_saleorder_b.creceiptareaid" ,"so_sale.creceiptcustomerid"}, new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });

		// ���浥��˾����Ȩ��
		ConditionVO[] power_cond_tmp = getCtrTmpDataPowerVOs();

		// �������⹫˾���ƿ����֯�Ͳֿ�
		if (!alcorp.contains(pk_corp))
			alcorp.add(pk_corp);
		initCorpRef("so_saleorder_b.pk_corp", pk_corp, alcorp);

		setCorpRefs("so_saleorder_b.pk_corp", new String[] {
				"so_saleorder_b.cadvisecalbodyid",
				"so_saleorder_b.creccalbodyid", "so_saleorder_b.crecwareid",
				"so_saleorder_b.cbodywarehouseid" });

		String[] corps = new String[alcorp.size()];
		alcorp.toArray(corps);

		setRefsDataPowerConVOs(ClientEnvironment.getInstance().getUser()
				.getPrimaryKey(), corps, new String[] { "�����֯", "�����֯", "�ֿ⵵��", /*-=notranslate=-*/
				"�ֿ⵵��", }, new String[] { "so_saleorder_b.cadvisecalbodyid", /*-=notranslate=-*/
				"so_saleorder_b.creccalbodyid", "so_saleorder_b.crecwareid",
				"so_saleorder_b.cbodywarehouseid"

		}, new int[] { 2, 2, 2, 2 });

		// ��������
		ConditionVO[] power_cond_tmp2 = getCtrTmpDataPowerVOs();
		int len1 = power_cond_tmp == null ? 0 : power_cond_tmp.length;
		int len2 = power_cond_tmp2 == null ? 0 : power_cond_tmp2.length;
		if (len1 + len2 > 0) {
			ConditionVO[] all_conds = new ConditionVO[len1 + len2];
			if (len1 > 0)
				System.arraycopy(power_cond_tmp, 0, all_conds, 0, len1);
			if (len2 > 0)
				System.arraycopy(power_cond_tmp2, 0, all_conds, len1, len2);

			setCtrTmpDataPowerVOs(all_conds);
		}

	}
	
	//Эͬ��˾
	String m_oppcorp = null;
	public void setPk_corp(String m_oppcorp){
		this.m_oppcorp = m_oppcorp;
	}
	
	//�����ѯ����vo�������ε���
	private ConditionVO[] newvo = null;

	public void clearConditionVO(){
		newvo = null;
	}
	
	public String getWhereSQL() {
		clearConditionVO();
		return super.getWhereSQL();
	}
	
	/**
	 * ȡ���û��趨�Ĳ�ѯ����VO���� 
	 * 
	 * ��������:(2001-4-25 16:50:17)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 * 
	 */
	public nc.vo.pub.query.ConditionVO[] getConditionVO() {
		
		if (this.newvo==null){
		
		ConditionVO[] cvos = super.getConditionVO();
		
		
			try {
				cvos = ScmPubHelper.getTotalSubPkVOs(cvos, pk_corp);
			} catch (Exception e) {
				nc.vo.scm.pub.SCMEnv.out(e);
			}
			
			
			ArrayList al = new ArrayList();
			ArrayList<ConditionVO> qualified = new ArrayList();
			for (int i = 0, len = cvos.length; i < len; i++) {
				// �ж�����Ȩ������
				if (!(cvos[i].getOperaCode().trim().equalsIgnoreCase("IS")
						&& cvos[i].getValue().trim().equalsIgnoreCase("NULL") 
						|| cvos[i].getOperaCode().trim().equalsIgnoreCase("is NULL") && cvos[i].getValue() == null
						|| ((cvos[i].getValue() != null) && cvos[i].getValue().trim()
						.indexOf(
						// "(select distinct power.resource_data_id"
								"(select ") >= 0))) {
					if ("�ͻ���������".equals(cvos[i].getFieldName())) { /*-=notranslate=-*/
						// ��ѯģ����TableCodeΪareaclname��value���ò��յ�nameֵ
						if (null != cvos[i].getRefResult())
							cvos[i].setValue(cvos[i].getRefResult()
									.getRefName());
					}
					al.add(cvos[i]);
				} else {
					qualified.add(cvos[i]);
				}
			}// end for
			ConditionVO[] normals = new ConditionVO[al.size()];
			al.toArray(normals);

			// ��͸��ѯ
			normals = ConvertQueryCondition.getConvertedVO(normals, m_oppcorp==null?pk_corp:m_oppcorp);

			// �����������
			for (int i = 0, len = normals.length; i < len; i++) {
				cvos[i] = normals[i];
			}
			for (int i = 0, len = qualified.size(); i < len; i++) {
				cvos[i + normals.length] = qualified.get(i);
			}	


		if (cvos != null && cvos.length > 0) {
			for (int i = 0, iLen = cvos.length; i < iLen; i++) {
				if (cvos[i].getFieldCode() != null
						&& cvos[i].getFieldCode().equals(
								"so_saleorder_b.ct_manageid")
						&& cvos[i].getValue() != null
						&& !cvos[i].getValue().startsWith(
								" select pk_ct_manage")) {
					cvos[i]
							.setValue(" select pk_ct_manage from ct_manage where ct_code='"
									+ cvos[i].getValue() + "' ");
					cvos[i].setOperaCode(" in ");
					cvos[i].setDataType(2);
				}
				// ���
				else if (cvos[i].getFieldCode() != null
						&& cvos[i].getFieldCode()
								.equals("bd_invbasdoc.invspec")) {
					cvos[i].setFieldCode("so_saleorder_b.cinventoryid");
					cvos[i].setOperaCode(" IN ");
					cvos[i]
							.setValue("(select pk_invmandoc from bd_invmandoc where pk_invbasdoc in ( select pk_invbasdoc from bd_invbasdoc where invspec = '"
									+ cvos[i].getValue() + "'))");
					cvos[i].setDataType(2);
				}
				// �ͺ�
				else if (cvos[i].getFieldCode() != null
						&& cvos[i].getFieldCode()
								.equals("bd_invbasdoc.invtype")) {
					cvos[i].setFieldCode("so_saleorder_b.cinventoryid");
					cvos[i].setOperaCode(" IN ");
					cvos[i]
							.setValue("(select pk_invmandoc from bd_invmandoc where pk_invbasdoc in ( select pk_invbasdoc from bd_invbasdoc where invtype = '"
									+ cvos[i].getValue() + "'))");
					cvos[i].setDataType(2);
				}
			}
		}

		//��������Ȩ������ת��
		DataPowerUtils.trnsDPCndFromAreaToCust(cvos, "ccustomerid");
		
		// ���۳��ⵥ�������۶���ʱ������ҵ����������
		if (m_billtypesource.equals("30") && m_billtypecurrent.equals("4C")) {

			ConditionVO[] cvos_new = new ConditionVO[cvos.length + 1];
			System.arraycopy(cvos, 0, cvos_new, 0, cvos.length);

			ConditionVO con_biztype = new ConditionVO();
			con_biztype.setFieldCode("so_sale.cbiztype");
			con_biztype.setOperaCode("=");
			con_biztype.setValue(m_businesstype);

			cvos_new[cvos.length] = con_biztype;

			return cvos_new;
		}
		
		// 30-->21
		if (m_billtypesource.equals("30") && m_billtypecurrent.equals("21")) {

		}
		
		// 30-->21
		if (m_billtypesource.equals("30") && m_billtypecurrent.equals("3U")) {

		}
		
		// 30-->32
		if (m_billtypesource.equals("30") && (SaleBillType.SaleReceive.equals(m_billtypecurrent) 
				|| m_billtypecurrent.equals("32")) ) {
			
		}

		this.newvo = cvos;
		
		}
		
		return this.newvo;
		
	}
	public String getBusinesstype(){
		return m_businesstype;
	}
	private String m_sCorp=null;
	public void afterEdit(TableCellEditor editor, int row, int col) {
		super.afterEdit(editor, row, col);
		Object temp = ((UIRefCellEditor) editor).getComponent();
		AbstractRefModel model = null;
		if (temp instanceof UIRefPane) {
			UIRefPane pane = (UIRefPane) temp;
			String key = pane.getName();
			if("so_sale.pk_corp".equals(key)){
				String sCorp = pane.getRefPK();
				if(sCorp !=null && (m_sCorp == null || !sCorp.equals(m_sCorp))){
				m_sCorp = pane.getRefPK();
				UIRefPane pane1=null;
//				UIRefPane pane1 = getRefPaneByCode("so_sale.csalecorpid");
//				pane1.setPk_corp(m_sCorp);
//				pane1.getRefModel().addWherePart(" and bd_purorg.ownercorp = '"+m_sCorp+"' and bd_purorg.pk_purorg in (select csourceorgid from so_coopwith where ccustomerid = '"+m_sCorp+"')");
//				pane1.getRefModel().reloadData();
//				changeValueRef("po_order.cpurorganization", pane1);
				
				model = new DeptdocDefaultRefModel("���ŵ���");  /*-=notranslate=-*/
				model.setPk_corp(m_sCorp);
				pane1 = getRefPaneByCode("so_sale.cdeptid");
				pane1.setRefModel(model);
				changeValueRef("so_sale.cdeptid", pane1);
				
				model =new  PsndocDefaulRefModel("��Ա����"); /*-=notranslate=-*/
				model.setPk_corp(m_sCorp);
				pane1 = getRefPaneByCode("so_sale.cemployeeid");
				pane1.setRefModel(model);
				changeValueRef("so_sale.cemployeeid", pane1);
				
			
				}else if(sCorp == null){
					m_sCorp = null;
//					UIRefPane pane1 = getRefPaneByCode("po_order.cpurorganization");
//					pane1.setPK(null);
//					changeValueRef("po_order.cpurorganization", pane1);
//					setValueRef("po_order.cpurorganization", pane1);
//					pane1 = getRefPaneByCode("po_order.cemployeeid");
//					pane1.setPK(null);
//					changeValueRef("po_order.cemployeeid", pane1);
//					setValueRef("po_order.cemployeeid", pane1);
//					setDefaultValue("po_order.cdeptid", null,null);
					setValueNull();
//					setValueNull("po_order.cemployeeid");
//					setValueNull("po_order.cpurorganization");
//					setValue(null, null, "po_order.cdeptid");
//					setValue(null, null, "po_order.cemployeeid");
//					QueryConditionVO[] vos = getConditionDatas();
//					for(int i = 0 ; i < getConditionVO().length ; i ++){
//						if(!getConditionVO()[i].getFieldCode().equals("po_order.cpurorganization")){
//							for(int j = 0 ; j < vos.length ; j ++){
//								if(vos[j].getFieldCode().equals(getConditionVO()[i].getFieldCode())){
//									vos[j] = (QueryConditionVO)getConditionVO()[i];
//								}
//							}
//						}
//					}
//					LoadConditionVOs(getConditionVO());
//					refreshUI();
				}

			}
		}
	}
	
	private UIRefPane getRefPaneByCode(String sFieldCode) {

		nc.vo.pub.query.QueryConditionVO[] voaConData = getConditionDatas();
		if (voaConData == null || voaConData.length == 0)
			return null;
		// ����Ϊ�Բ��յĳ�ʼ��

		nc.ui.pub.beans.UIRefPane ref = null;
		for (int i = 0; i < voaConData.length; i++) {
			if (sFieldCode.equals(voaConData[i].getFieldCode())) {
				if (voaConData[i].getDataType().intValue() == nc.vo.pub.query.IQueryConstants.UFREF) {
					EditComponentFacotry factry = new EditComponentFacotry(
							voaConData[i]);
					ref = (nc.ui.pub.beans.UIRefPane) factry
							.getEditComponent(null);

				}

				break;
			}
		}
		return ref;
	}
	
	public void setValueNull() {
		 
//		  if (fieldcode == null )
//		         return;
//		 
//		     int rowcount = getTabModelInput().getRowCount();
//		     
//		     int initConditions = getConditionDatas().length;
//		     
//		     int immobilityRows = getImmobilityRows();
//		     
//		     Object obj = getValueRefObjectByFieldCode(fieldcode);
//		     if(obj==null)
//		      return;
//		     if(!(obj instanceof UIRefPane))
//		      return;
//		     
//		     UIRefPane uirp = (UIRefPane)obj;
//		     uirp.setPK(pkvalue);
//		     
//		     UIRefCellEditor tc =  new UIRefCellEditor(uirp);
//		 
//		        for (int row = immobilityRows; row < rowcount; row++) {
//		            Object fieldObj = getTabModelInput().getValueAt(row, COLFIELD);
//		            String fieldName = fieldObj == null ? "" : fieldObj.toString();
//		            int index_row = getIndexes(row);
//		            if (index_row >= 0 && index_row < initConditions) {
//		                if (getConditionDatas()[index_row].getFieldCode().equals(
//		                  fieldcode)) {
		                 
//		                  super.afterEdit(tc,row,COLVALUE);
		                	autoClear(new String[]{"so_sale.cdeptid","so_sale.cemployeeid"},  COLVALUE);
		                 
//		                    getTabModelInput().setValueAt(namevalue, row, COLVALUE);
//		                    if(getValueRefResults()[row - immobilityRows]!=null){
//		                     getValueRefResults()[row - immobilityRows].setRefPK( pkvalue);
//		                     getValueRefResults()[row - getImmobilityRows()].setRefName(namevalue);
//		                      getValueRefResults()[row - getImmobilityRows()].setRefObj(pkvalue);
//		                    }
//		                }
//		            }
//		        }
		        setConditionVO();
		    
		 }
	
	protected void autoClear(String[] sKey,  int col) {
		
		String[] sOtherFieldCodes = sKey;
		RefResultVO[] rrvo = getValueRefResults();
		QueryConditionVO[] qcvos = getConditionDatas();
		// ��ֵ
		for (int i = 0; i < sOtherFieldCodes.length; i++) {
			String sFieldCodeClear = sOtherFieldCodes[i].trim();
			for (int j = 0; j < getUITabInput().getRowCount(); j++) {
				int index = getIndexes(j - getImmobilityRows());
				// 2002-10-27.01 wnj add below check.
				if (index < 0 || index >= qcvos.length) {
					nc.vo.scm.pub.SCMEnv.out("qry cond err.");
					continue;
				}
				if (qcvos[index].getFieldCode().trim().equals(
						sFieldCodeClear)) {
					// �鵽��Ӧ�Ľ��VO�����PK��Code, Name
					if (rrvo[j] != null) {
						rrvo[j].setRefCode("");
						rrvo[j].setRefName("");
						rrvo[j].setRefPK("");
					}
					// �������ʾֵ
					getUITabInput().setValueAt(null, j, col);
					break;
				}
			}
		}
	
}
	
	
	
}
