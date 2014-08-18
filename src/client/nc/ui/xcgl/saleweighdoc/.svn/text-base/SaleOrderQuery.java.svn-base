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
 * 销售参照查询类。
 * 
 * 创建日期：(2002-11-20 19:41:34)
 * 
 * @author：马万钧
 * 
 * @see		SaleOrderDLG	转单界面
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
	 * 获得节点nodeKey。
	 * 
	 * 创建日期：(2001-11-27 13:51:07)
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
			if(m_businesstype.equals("直运")){ /*-=notranslate=-*/
//				setTempletID("SO30TO21000000000001");
				setTempletID(pkCorp, "40060301", operator, null,"4006030102");
			}else if (m_businesstype.equals("协同")){ /*-=notranslate=-*/
//				setTempletID("SO30TO21000000000000");
				setTempletID(pkCorp, "40060301", operator, null,"4006030108");
				UIRefPane ref4 = new UIRefPane();
				ref4.setRefNodeName("公司目录"); /*-=notranslate=-*/
				ref4.getRefModel().addWherePart(" and pk_corp in (select ccustomerid from so_coopwith where rtrim(cmaincustomerid) = '"+getCorp()+"' and csourcereceipttype = '30')");
				setValueRef("so_sale.pk_corp", ref4);
			}
		}else if (currentBillType != null && currentBillType.equals(SaleBillType.SaleReceive)
				&& sourceBilltype!=null && sourceBilltype.equals(SaleBillType.SaleOrder)){ 
			setTempletID(pkCorp, "40060301", operator, null,"4006030101");
		}else
			setTempletID(pkCorp, "40060302", operator, null, getNodeCode(
					currentBillType, sourceBilltype));

		//收货地址
		UIRefPane carfp = new UIRefPane(this);
		try{
		carfp.setRefModel(new CustAddrRefModel() {
			
			String sRefName = "客户档案"; /*-=notranslate=-*/

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
		
//		// 项目
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
				
				//收货地址
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
        
        /** 直运销售采购加默认制单日期  **/
        if ((vos[i].getFieldCode() != null &&  
            vos[i].getFieldCode().indexOf("dmakedate") >= 0)
            && ("直运".equals(m_businesstype)) /*-=notranslate=-*/
            && (SaleBillType.SaleOrder.equals(sourceBilltype))) {
          vos[i].setValue(nc.ui.pub.ClientEnvironment.getInstance()
              .getDate().toString());
        }

				/** 退货申请单参照销售订单，可以选择业务类型* */
				if ((vos[i].getFieldCode() != null && vos[i].getFieldCode()
						.indexOf("so_sale.cbiztype") >= 0)
						&& ("3U".equals(currentBillType))
						&& (SaleBillType.SaleOrder.equals(sourceBilltype))) {
					vos[i].setIfDefault(UFBoolean.TRUE);
				}
				
			}// end for
		}

		// 业务类型
		SOBusiTypeRefPane biztypeRef = new SOBusiTypeRefPane(pkCorp);
		if("直运".equals(m_businesstype))//采购订单-参照直运销售订单，业务类型只显示‘直运’  tangmc liuzwc 2009-07-23 /*-=notranslate=-*/
			biztypeRef.getRefModel().setWherePart(biztypeRef.getRefModel().getWherePart()+" and verifyrule = 'Z' ");
		setValueRef("so_sale.cbiztype", biztypeRef);
		
		//设置公司相关档案权限参照
		setCorpRalationRefs();
		
		hideNormal();

	}
	
	
	private void setCorpRalationRefs(){
		// 虚拟公司
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

		// 数据权限
		setRefsDataPowerConVOs(ClientEnvironment.getInstance().getUser()
				.getPrimaryKey(), new String[] { pk_corp }, new String[] {
				"客户档案", "地区分类", "销售组织", "部门档案", "人员档案", "存货档案", "地区分类" ,"客户档案"}, new String[] { /*-=notranslate=-*/
				"so_sale.ccustomerid", "ccustomerid", "so_sale.csalecorpid",
				"so_sale.cdeptid", "so_sale.cemployeeid",
				"so_saleorder_b.cinventoryid","so_saleorder_b.creceiptareaid" ,"so_sale.creceiptcustomerid"}, new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });

		// 缓存单公司数据权限
		ConditionVO[] power_cond_tmp = getCtrTmpDataPowerVOs();

		// 根据虚拟公司控制库存组织和仓库
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
				.getPrimaryKey(), corps, new String[] { "库存组织", "库存组织", "仓库档案", /*-=notranslate=-*/
				"仓库档案", }, new String[] { "so_saleorder_b.cadvisecalbodyid", /*-=notranslate=-*/
				"so_saleorder_b.creccalbodyid", "so_saleorder_b.crecwareid",
				"so_saleorder_b.cbodywarehouseid"

		}, new int[] { 2, 2, 2, 2 });

		// 整合条件
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
	
	//协同公司
	String m_oppcorp = null;
	public void setPk_corp(String m_oppcorp){
		this.m_oppcorp = m_oppcorp;
	}
	
	//缓存查询条件vo，避免多次调用
	private ConditionVO[] newvo = null;

	public void clearConditionVO(){
		newvo = null;
	}
	
	public String getWhereSQL() {
		clearConditionVO();
		return super.getWhereSQL();
	}
	
	/**
	 * 取得用户设定的查询条件VO数组 
	 * 
	 * 创建日期:(2001-4-25 16:50:17)
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
				// 判断数据权限条件
				if (!(cvos[i].getOperaCode().trim().equalsIgnoreCase("IS")
						&& cvos[i].getValue().trim().equalsIgnoreCase("NULL") 
						|| cvos[i].getOperaCode().trim().equalsIgnoreCase("is NULL") && cvos[i].getValue() == null
						|| ((cvos[i].getValue() != null) && cvos[i].getValue().trim()
						.indexOf(
						// "(select distinct power.resource_data_id"
								"(select ") >= 0))) {
					if ("客户地区分类".equals(cvos[i].getFieldName())) { /*-=notranslate=-*/
						// 查询模板中TableCode为areaclname，value设置参照的name值
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

			// 穿透查询
			normals = ConvertQueryCondition.getConvertedVO(normals, m_oppcorp==null?pk_corp:m_oppcorp);

			// 重新组合条件
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
				// 规格
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
				// 型号
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

		//地区分类权限条件转换
		DataPowerUtils.trnsDPCndFromAreaToCust(cvos, "ccustomerid");
		
		// 销售出库单参照销售订单时，增加业务类型条件
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
				
				model = new DeptdocDefaultRefModel("部门档案");  /*-=notranslate=-*/
				model.setPk_corp(m_sCorp);
				pane1 = getRefPaneByCode("so_sale.cdeptid");
				pane1.setRefModel(model);
				changeValueRef("so_sale.cdeptid", pane1);
				
				model =new  PsndocDefaulRefModel("人员档案"); /*-=notranslate=-*/
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
		// 以下为对参照的初始化

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
		// 清值
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
					// 查到对应的结果VO，清掉PK，Code, Name
					if (rrvo[j] != null) {
						rrvo[j].setRefCode("");
						rrvo[j].setRefName("");
						rrvo[j].setRefPK("");
					}
					// 清界面显示值
					getUITabInput().setValueAt(null, j, col);
					break;
				}
			}
		}
	
}
	
	
	
}
