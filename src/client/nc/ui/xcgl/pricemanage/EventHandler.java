package nc.ui.xcgl.pricemanage;

import nc.jdbc.framework.processor.ArrayProcessor;
import nc.ui.pub.ButtonObject;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.helper.QueryHelper;
/**
 * 价格维护
 * @author ddk
 *
 */
public class EventHandler extends ManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
//		int selectRow = getBillCardPanelWrapper().getBillCardPanel()
//				.getBillTable().getSelectedRow();
//		AggPriceManageVO billvo = (AggPriceManageVO) getBillCardPanelWrapper()
//				.getBillVOFromUI();
//		if (billvo.getChildrenVO() != null) {
//			if (billvo == null)
//				return;
//			PriceManageHVO hvo = (PriceManageHVO) billvo.getParentVO();
//			// 设置表体日期
//			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
//					PuPubVO.getString_TrimZeroLenAsNull(ClientEnvironment
//							.getInstance().getDate()), selectRow, "ddate");
//			String date = PuPubVO.getString_TrimZeroLenAsNull(
//					hvo.getAttributeValue("ddate")).substring(8, 10);
//			// 根据日期判断设置月段
//			int ndate = Integer.parseInt(date);
//			// 设置月段
//			setmonth(ndate, selectRow);
//			// 找到表头元素在存货管理档案中的主键
//			String pk = getBillCardPanelWrapper().getBillCardPanel()
//					.getHeadItem("pk_invmandoc").getValue();
//			// 通过主键查询单位
//			Object[] o = getMeasdoc(pk, selectRow);
//			// 给表体单位赋值
//			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(o[0],
//					selectRow, "pk_measdoc");
//			// 给表体备注赋值
//			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
//					"来自上海金属网价格", selectRow, "vmemo");
//		}
		
//		throw new Exception("请勿手动增行！");
			
		
	}
	/**
	 * 判断日期属于哪个月段
	 */
	protected void setmonth(int date,int selectRow){
		if(date>0&&date<=10){
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("上旬", selectRow, "vmanth");
		}else if(date>10&&date<=20){
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("中旬", selectRow, "vmanth");
		}else if(date>20&&date<=31){
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("下旬", selectRow, "vmanth");
		}
	}
	/**
	 * 查单位表中的单位名称
	 * @throws Exception 
	 */
	protected Object[] getMeasdoc(String pk,int selectRow) throws Exception{
		String sql = " select a.measname " +
		" from bd_measdoc a " +
		" where a.pk_measdoc " +
		" in  (select b.pk_measdoc " +
		" from bd_invbasdoc b " +
		" join bd_measdoc a " +
		" on b.pk_measdoc = a.pk_measdoc " +
		" where b.pk_invbasdoc in " +
		" (select c.pk_invbasdoc " +
		" from bd_invmandoc c " +
		" join bd_invbasdoc b " +
		" on b.pk_invbasdoc = c.pk_invbasdoc " +
		" where c.pk_invmandoc = '" +pk+ "'))";
		Object []o=(Object[]) QueryHelper.executeQuery(sql, new ArrayProcessor());
		return o;
	}
	
	@Override
	protected void onBoSave() throws Exception {
		super.onBoSave();
//		AggPriceManageVO billvo=(AggPriceManageVO) getBillCardPanelWrapper().getBillVOFromUI();
//		if(billvo.getChildrenVO()!=null){
//			if(billvo==null)
//				return;
//			if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
//				CircularlyAccessibleValueObject hvos=billvo.getParentVO();
//				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
//				//唯一性校验
//				BsBeforeSaveValudate.beforeSaveBodyUnique(hvos, new String[]{"pk_jobmngfil","pk_invmandoc"}, new String[]{"工程项目","作业"});
//			}
//		}
	}
	@Override
	public void onButton(ButtonObject bo) {
		String corp=this._getCorp().getPk_corp();
		//bo.getName().equals("增加")
		
		int intBtn = Integer.parseInt(bo.getTag());
		
		if (bo.getName().equals("增加")
				|| bo.getName().equals("修改") || intBtn==IBillButton.Delete ){
		if(!corp.equalsIgnoreCase(PubOtherConst.orgcode)){
			getBillUI().showWarningMessage("公司下只能查看!");
			return;
		}
		}
		super.onButton(bo);
	}
	

	/**
	 * 主表对应的固定查询条件。 创建日期：(2002-12-27 16:58:18)
	 * 
	 * @return java.lang.String
	 */
	protected String getHeadCondition() {
		// 公司
		if (getBillCardPanelWrapper() != null)
			if (getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(
					getBillField().getField_Corp()) != null)
				return getBillField().getField_Corp() + "='"
						+ PubOtherConst.orgcode + "'";
		return null;
	}
}
