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
 * �۸�ά��
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
//			// ���ñ�������
//			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
//					PuPubVO.getString_TrimZeroLenAsNull(ClientEnvironment
//							.getInstance().getDate()), selectRow, "ddate");
//			String date = PuPubVO.getString_TrimZeroLenAsNull(
//					hvo.getAttributeValue("ddate")).substring(8, 10);
//			// ���������ж������¶�
//			int ndate = Integer.parseInt(date);
//			// �����¶�
//			setmonth(ndate, selectRow);
//			// �ҵ���ͷԪ���ڴ���������е�����
//			String pk = getBillCardPanelWrapper().getBillCardPanel()
//					.getHeadItem("pk_invmandoc").getValue();
//			// ͨ��������ѯ��λ
//			Object[] o = getMeasdoc(pk, selectRow);
//			// �����嵥λ��ֵ
//			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(o[0],
//					selectRow, "pk_measdoc");
//			// �����屸ע��ֵ
//			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(
//					"�����Ϻ��������۸�", selectRow, "vmemo");
//		}
		
//		throw new Exception("�����ֶ����У�");
			
		
	}
	/**
	 * �ж����������ĸ��¶�
	 */
	protected void setmonth(int date,int selectRow){
		if(date>0&&date<=10){
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("��Ѯ", selectRow, "vmanth");
		}else if(date>10&&date<=20){
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("��Ѯ", selectRow, "vmanth");
		}else if(date>20&&date<=31){
			getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt("��Ѯ", selectRow, "vmanth");
		}
	}
	/**
	 * �鵥λ���еĵ�λ����
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
//				//Ψһ��У��
//				BsBeforeSaveValudate.beforeSaveBodyUnique(hvos, new String[]{"pk_jobmngfil","pk_invmandoc"}, new String[]{"������Ŀ","��ҵ"});
//			}
//		}
	}
	@Override
	public void onButton(ButtonObject bo) {
		String corp=this._getCorp().getPk_corp();
		//bo.getName().equals("����")
		
		int intBtn = Integer.parseInt(bo.getTag());
		
		if (bo.getName().equals("����")
				|| bo.getName().equals("�޸�") || intBtn==IBillButton.Delete ){
		if(!corp.equalsIgnoreCase(PubOtherConst.orgcode)){
			getBillUI().showWarningMessage("��˾��ֻ�ܲ鿴!");
			return;
		}
		}
		super.onButton(bo);
	}
	

	/**
	 * �����Ӧ�Ĺ̶���ѯ������ �������ڣ�(2002-12-27 16:58:18)
	 * 
	 * @return java.lang.String
	 */
	protected String getHeadCondition() {
		// ��˾
		if (getBillCardPanelWrapper() != null)
			if (getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(
					getBillField().getField_Corp()) != null)
				return getBillField().getField_Corp() + "='"
						+ PubOtherConst.orgcode + "'";
		return null;
	}
}
