package nc.ui.xcgl.pub.bill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.BusiTypeChangeEvent;
import nc.ui.trade.bill.IBillBusiListener;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.bill.RefBillTypeChangeEvent;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.zmpub.pub.bill.FlowManageEventHandler;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pf.pub.BusitypeVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;
import nc.vo.xcgl.salepresettle.SalepresettleHVO;

public class XCFlowManageEventHandler extends FlowManageEventHandler {
	String pk_corp = ClientEnvironment.getInstance().getCorporation()
			.getPrimaryKey();

	public XCFlowManageEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	public void onBoAdd(ButtonObject bo) throws Exception {
		if (MonthCloseHelper.isMonthClose(
				ClientEnvironment.getInstance().getDate(), pk_corp)
				.booleanValue()) {
			getBillUI().showErrorMessage("��ǰ�����Ѿ�����");
		}
		super.onBoAdd(bo);
	}

	public IBillBusiListener m_bbl = null;

	/**
	 * ��ťm_boAdd���ʱִ�еĶ���,���б�Ҫ���븲��.
	 * 
	 * @param bo
	 *            ��Դ���ݵ�ƽ̨���ɰ�ť
	 * @param sourceBillId
	 *            �ο���Դ����Id
	 */
	public void onBoBusiTypeAdd(ButtonObject bo, String sourceBillId)
			throws Exception {
		getBusiDelegator().childButtonClicked(bo, _getCorp().getPrimaryKey(),
				getBillUI()._getModuleCode(), _getOperator(),
				getUIController().getBillType(), getBillUI(),
				getBillUI().getUserObject(), sourceBillId);
		if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
			// ���õ���״̬
			getBillUI().setCardUIState();
			// ����
			getBillUI().setBillOperate(IBillOperate.OP_ADD);
			//set businesstype
			getBillCardPanel().getHeadItem("pk_busitype").setValue(getBillUI().getBusinessType());
			getBillCardPanel().execHeadEditFormulas();
		} else {
			if (PfUtilClient.isCloseOK()) {
				if (m_bbl != null) {
					String tmpString = bo.getTag();
					int findIndex = tmpString.indexOf(":");
					String newtype = tmpString.substring(0, findIndex);
					RefBillTypeChangeEvent e = new RefBillTypeChangeEvent(this,
							null, newtype);
					m_bbl.refBillTypeChange(e);
				}
				if (isDataChange()) {
					setRefData(PfUtilClient.getRetVos());
				} else {
					setRefData(PfUtilClient.getRetOldVos());
				}
				// set defalut data

				getBillUI().setBillOperate(IBillOperate.OP_REFADD);
				getBillUI().setDefaultData();
				getButtonManager().getButton(IBillButton.Line)
						.setEnabled(false);
				getBillManageUI().updateButtons();
				getBillCardPanel().setBodyMenuShow(false);
				if (getBillCardPanel().getRowCount() > 0) {
					if(getBillCardPanel().getRowCount()>0){
						for(int i=0;i<getBillCardPanel().getRowCount();i++){
							getBillCardPanel().getBillModel().execEditFormulas(i);
							getBillCardPanel().getBillModel().setValueAt((i+1)*10+"", i, "crowno");
						}
					}
				}
				getBillCardPanel().getHeadItem("pk_busitype").setValue(getBillUI().getBusinessType());
			}
		}
	}

	@Override
	protected void onBoSave() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBillCardPanel().getHeadItem(
				"dbilldate").getValueObject());
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("��ǰ���������Ѿ�����");
			return;
		}
		super.onBoSave();
	}

	/**
	 * ��ťm_boEdit���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	protected void onBoEdit() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("��ǰ���������Ѿ�����");
			return;
		}
		super.onBoEdit();
	}

	/**
	 * ��ťm_boEdit���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	protected void onBoEdit1() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("��ǰ���������Ѿ�����");
			return;
		}
		super.onBoEdit1();
	}

	@Override
	protected void onBoDelete() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("��ǰ���������Ѿ�����");
			return;
		}
		super.onBoDelete();
	}

	@Override
	protected void onBoDel() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("��ǰ���������Ѿ�����");
			return;
		}
		super.onBoDel();
	}

	protected void onBoCancelAudit() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("��ǰ���������Ѿ�����");
			return;
		}
		super.onBoCancelAudit();
	}

	/**
	 * Button���¼���Ӧ���� �������ڣ�(2004-1-6 17:20:57)
	 * 
	 * @param bo
	 *            nc.ui.pub.ButtonObject �쳣˵����
	 */
	public void onButton(ButtonObject bo) {
		if (getBillUI().getBillOperate() == IBillOperate.OP_ADD
				|| getBillUI().getBillOperate() == IBillOperate.OP_EDIT) {
			if (getBillCardPanelWrapper() != null)
				getBillCardPanelWrapper().getBillCardPanel().stopEditing();

		}
		try {
			ButtonObject parentBtn = bo.getParent();

			if (parentBtn != null && Integer.parseInt(parentBtn.getTag()) < 100) {
				int intParentBtn = Integer.parseInt(parentBtn.getTag());
				complexOnButton(intParentBtn, bo);
			} else {
				if (bo.getTag() == null)
					System.out.println("������ť��������TAG,TAG>100������.....");
				int intBtn = Integer.parseInt(bo.getTag());
				if (intBtn > 100)// ��Ŵ���100��Ϊ���Զ��尴ť
					onBoElse(intBtn);
				else
					// ������Ϊ��Ԥ�ð�ť
					simpleOnButton(intBtn, bo);
			}
		} catch (BusinessException ex) {
			onBusinessException(ex);
		} catch (SQLException ex) {
			getBillUI().showErrorMessage(ex.getMessage());
		} catch (Exception e) {
			getBillUI().showErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	private static final String staticACTION = "BOACTION";

	private static final String staticASS = "BOASS";

	/**
	 * ��ťm_boBusiType���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	private final void onBoBusiType(ButtonObject bo) throws Exception {
		// ִ��ǰ����
		busiTypeBefore(getBillUI(), bo);
		bo.setSelected(true);
		// ����ҵ������
		BusitypeVO vo = (BusitypeVO) bo.getData();
		// �������Ӱ�ť
		getBusiDelegator()
				.retAddBtn(getButtonManager().getButton(IBillButton.Add),
						_getCorp().getPrimaryKey(),
						getUIController().getBillType(), bo);
		// //����ִ�а�ť(�뵥��״̬�޹أ�
		getBusiDelegator().retElseBtn(
				getButtonManager().getButton(IBillButton.Action),
				getUIController().getBillType(), staticACTION);

		getButtonManager().setActionButtonVO(
				getBillUI().isSaveAndCommitTogether());

		String oldtype = getBillUI().getBusinessType();
		String newtype = vo.getPrimaryKey();
		String oldcode = getBillUI().getBusicode();
		String newcode = vo.getBusicode();

		// ҵ����������
		getBillUI().setBusinessType(newtype);
		// ҵ�����ʹ���
		getBillUI().setBusicode(newcode);

		// ����ˢ��UI
		getBillUI().initUI();
		// ���UI����
		getBillUI().getBufferData().clear();
		getBillUI().getBufferData().setCurrentRow(-1);

		getBillUI().updateButtonUI();

		if (m_bbl != null) {
			BusiTypeChangeEvent e = new BusiTypeChangeEvent(this, oldtype,
					newtype, oldcode, newcode);
			m_bbl.busiTypeChange(e);
		}
	}

	/**
	 * ��ťm_boAction���ʱִ�еĶ���,���б�Ҫ���븲��. ����ִ�ж�������
	 */
	private final void onBoAction(ButtonObject bo) throws Exception {
		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000179")/*
		// * @res "��ʼִ�в���,��ȴ�..."
		// */);
		ButtonVO btnVo = (ButtonVO) bo.getData();
		if (btnVo == null)
			return;
		switch (btnVo.getBtnNo()) {
		case IBillButton.Audit: {
			onBoAudit();
			break;
		}
		case IBillButton.CancelAudit: {
			onBoCancelAudit();
			break;
		}
		case IBillButton.Commit: {
			onBoCommit();
			break;
		}
		case IBillButton.Del: {
			onBoDel();
			break;
		}
		default: {
			onBoActionElse(bo);
			break;
		}
		}

		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000180")/*
		// * @res "ִ�����."
		// */
		// );
	}

	/**
	 * ��ťm_boAction���ʱִ�еĶ���,���б�Ҫ���븲��. ����ִ�ж�������
	 */
	private final void onBoAss(ButtonObject bo) throws Exception {
		beforeOnBoAss(bo);
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		Object ret = getBusinessAction().processAction(bo.getTag(), modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (ret != null && ret instanceof AggregatedValueObject) {
			AggregatedValueObject vo = (AggregatedValueObject) ret;
			// ����״̬
			modelVo.getParentVO().setAttributeValue(
					getBillField().getField_BillStatus(),
					vo.getParentVO().getAttributeValue(
							getBillField().getField_BillStatus()));
			// ����ʱ���
			modelVo.getParentVO().setAttributeValue("ts",
					vo.getParentVO().getAttributeValue("ts"));

			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		afterOnBoAss(bo);
	}

	/**
	 * �в������� �������ڣ�(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	private void onBoLine(ButtonObject bo) throws java.lang.Exception {
		int intBtn = -1;// Integer.parseInt(bo.getTag());

		if (bo.getData() != null && bo.getData() instanceof ButtonVO) {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			intBtn = btnVo.getBtnNo();
		} else {
			intBtn = Integer.parseInt(bo.getTag());
		}

		// ����ִ��ǰ����
		buttonActionBefore(getBillUI(), intBtn);

		getBillUI().showHintMessage(bo.getName());

		switch (intBtn) {
		case IBillButton.AddLine: {
			onBoLineAdd();
			break;
		}
		case IBillButton.DelLine: {
			onBoLineDel();
			break;
		}
		case IBillButton.CopyLine: {
			onBoLineCopy();
			break;
		}
		case IBillButton.InsLine: {
			onBoLineIns();
			break;
		}
		case IBillButton.PasteLine: {
			onBoLinePaste();
			break;
		}
		case IBillButton.PasteLinetoTail: {
			onBoLinePasteToTail();
			break;
		}
		default:
			onBoElse(intBtn);
			break;
		}

		// ����ִ�к���
		buttonActionAfter(getBillUI(), intBtn);
	}

	/**
	 * ����������� �������ڣ�(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	private void onBoBrow(ButtonObject bo) throws java.lang.Exception {
		int intBtn = Integer.parseInt(bo.getTag());
		// ����ִ��ǰ����
		buttonActionBefore(getBillUI(), intBtn);
		switch (intBtn) {
		case IBillButton.First: {
			getBufferData().first();
			break;
		}
		case IBillButton.Prev: {
			getBufferData().prev();
			break;
		}
		case IBillButton.Next: {
			getBufferData().next();
			break;
		}
		case IBillButton.Last: {
			getBufferData().last();
			break;
		}
		}
		// ����ִ�к���
		buttonActionAfter(getBillUI(), intBtn);
		getBillUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance()
						.getStrByID(
								"uifactory",
								"UPPuifactory-000503",
								null,
								new String[] { nc.vo.format.Format
										.indexFormat(getBufferData()
												.getCurrentRow() + 1) })/*
																		 * @res
																		 * "ת����:"
																		 * +
																		 * getBufferData
																		 * ().
																		 * getCurrentRow
																		 * () +
																		 * "ҳ���)"
																		 */
		);

	}

	/**
	 * ��ťm_boNodekey���ʱִ�еĶ���,���б�Ҫ���븲��.
	 */
	private final void onBoNodekey(ButtonObject bo) throws Exception {
		bo.setSelected(true);
		// ����NodeKey
		getBillUI().setNodeKey(bo.getTag());

		// ����ˢ��UI
		getBillUI().initUI();
		// ��ղ�ѯģ��
		setQueryUI(null);
		// ���UI����
		getBillUI().getBufferData().clear();
		getBillUI().getBufferData().setCurrentRow(-1);

		getBillUI().updateButtonUI();
	}

	public void complexOnButton(int intBtn, ButtonObject bo)
			throws java.lang.Exception {
		switch (intBtn) {
		case IBillButton.Busitype: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000060")/*
												 * @res "��ʼѡ��ҵ�����ͣ���ȴ�......"
												 */);
			onBoBusiType(bo);
			break;
		}
		case IBillButton.Add: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000061")/*
												 * @res "��ʼ�������ӵ��ݣ���ȴ�......"
												 */);
			onBoBusiTypeAdd(bo, null);
			break;
		}
		case IBillButton.Action: {
			onBoAction(bo);
			break;
		}
		case IBillButton.Ass: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000062")/*
												 * @res "��ʼ�Ե��ݵĸ������в�������ȴ�......"
												 */);
			onBoAss(bo);
			break;
		}
		case IBillButton.Line: {
			onBoLine(bo);
			break;
		}
		case IBillButton.File: {
			onBoFile(bo);
		}
		case IBillButton.Brow: {
			onBoBrow(bo);
			break;
		}
		case IBillButton.NodeKey: {
			onBoNodekey(bo);
			break;
		}
		case IBillButton.Refbill: {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			onBoElse(btnVo.getBtnNo());
			break;
		}
		default: {
			if (bo.getData() != null && bo.getData() instanceof ButtonVO) {
				ButtonVO btnVo = (ButtonVO) bo.getData();
				onBoElse(btnVo.getBtnNo());
			}
			break;
		}
		}
	}

	private void simpleOnButton(int intBtn, ButtonObject bo)
			throws java.lang.Exception {
		buttonActionBefore(getBillUI(), intBtn);
		switch (intBtn) {
		case IBillButton.Add: {
			if (!getBillUI().isBusinessType().booleanValue()) {
				getBillUI().showHintMessage(
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"uifactory", "UPPuifactory-000061")/*
																	 * @res
																	 * "��ʼ�������ӵ��ݣ���ȴ�......"
																	 */);
				onBoAdd(bo);
				// ����ִ�к���
				buttonActionAfter(getBillUI(), intBtn);
			}
			break;
		}
		case IBillButton.Edit: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000067")/*
												 * @res "��ʼ���б༭���ݣ���ȴ�......"
												 */);
			onBoEdit();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Del: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000068")/*
												 * @res "��ʼ�������ϵ��ݣ���ȴ�......"
												 */);
			onBoDel();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000069")/* @res "�������ϲ�������" */
			);
			break;
		}
		case IBillButton.Delete: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000070")/*
												 * @res "��ʼ���е���ɾ������ȴ�......"
												 */);
			onBoDelete();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000071")/* @res "����ɾ�����" */
			);
			break;
		}
		case IBillButton.Query: {
			getBillUI().showHintMessage(bo.getName());

			if (super.getUIController() instanceof ISingleController) {
				ISingleController strl = (ISingleController) super
						.getUIController();
				if (strl.isSingleDetail())
					onBoBodyQuery();
				else
					onBoQuery();
			} else
				onBoQuery();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);

			break;
		}
		case IBillButton.Save: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000072")/*
												 * @res "��ʼ���е��ݱ��棬��ȴ�......"
												 */);
			onBoSave();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000073")/* @res "���ݱ����������" */
			);
			break;
		}
		case IBillButton.Cancel: {
			onBoCancel();
			// �����ʾ״̬����ʾ��Ϣ
			getBillUI().showHintMessage("");
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Print: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
												 * @res "��ʼ���д�ӡ���ݣ���ȴ�......"
												 */);
			onBoPrint();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "���ݴ�ӡ��������" */
			);
			break;
		}
		case IBillButton.DirectPrint: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
												 * @res "��ʼ���д�ӡ���ݣ���ȴ�......"
												 */);
			onBoDirectPrint();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "���ݴ�ӡ��������" */
			);
			break;
		}

		case IBillButton.Return: {
			onBoReturn();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Card: {
			onBoCard();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Refresh: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000076")/*
												 * @res "��ʼ����ˢ�µ��ݣ���ȴ�......"
												 */);
			onBoRefresh();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000077")/* @res "����ˢ�²�������" */
			);

			break;
		}
		case IBillButton.Refbill: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000078")/*
												 * @res "��ʼ���в��յ��ݣ���ȴ�......"
												 */);
			onBillRef();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000079")/* @res "���ݲ��ղ�������" */
			);
			break;
		}
		case IBillButton.Copy: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000080")/*
												 * @res "��ʼ�������ݸ��ƣ���ȴ�......"
												 */);
			onBoCopy();
			// ����ִ�к���
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000081")/* @res "���ݸ��Ʋ�������" */
			);
			break;
		}
		case IBillButton.Audit: {
			// ��Ϊ�������ʾ�����������Թ淶������ɾ������
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res "��ʼִ�в���,��ȴ�..."
			// */);
			onBoAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res "ִ�����. "
			// */
			// );
			break;
		}
		case IBillButton.CancelAudit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoCancelAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.Commit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoCommit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.SelAll: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );

			onBoSelAll();

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.SelNone: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoSelNone();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.ImportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoImport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.ExportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );
			onBoExport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		case IBillButton.ApproveInfo: {
			onBoApproveInfo();
			break;
		}
		default: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res ��ʼִ�в���, ��ȴ�...
			// */
			// );

			onBoActionElse(bo);

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res ִ�����.
			// */
			// );
			break;
		}
		}

	}
	
	/**
	 * ȡ������
	 * 
	 * @author yangtao
	 * @date 2014-3-26 ����03:47:08
	 * 
	 */
	@SuppressWarnings("unchecked")
	public  void ReadAssayres(boolean isPresettle) throws Exception {
		// �õ�����vo
		AggSalepresettleVO aggvo = (AggSalepresettleVO) getBillCardPanel().getBillValueVO(
				        AggSalepresettleVO.class.getName(),
						SalepresettleHVO.class.getName(),
						SalepresettleBVO.class.getName());
		AggSalepresettleVO billvo=null;
		SalepresettleBVO bvos[] = null;
		try {
			Class[] ParameterTypes = new Class[] { AggSalepresettleVO.class ,UFBoolean.class};
			Object[] ParameterValues = new Object[] { aggvo, new UFBoolean(isPresettle)};
			Object o = LongTimeTask.calllongTimeService("xcgl", null,
					"���ڲ�ѯ...", 1, "nc.bs.xcgl.salesettle.Price", null,
					"ReadAssayres", ParameterTypes, ParameterValues);
			billvo = (AggSalepresettleVO) o;
			bvos=(SalepresettleBVO[]) billvo.getChildrenVO();
		} catch (Exception e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(e.getMessage());
		}
        if(bvos==null){
        	return ;
        }
    	getBillCardPanel().getBillModel().setBodyDataVO(bvos);
		for (int i = 0; i < bvos.length; i++) {
			getBillCardPanel().getBillModel().execEditFormulas(i);
			// ����ֵ�Ժ��ֶ�ִ��һ����ʾ��ʽ��
			getBillCardPanel().getBillModel().execLoadFormulaByRow(i);
		}
	}
	
	 /**
     * ����۸�
     * 
     * @author yangtao
     * @date 2014-3-27 ����05:32:31
     */
	public void CountPrice(boolean isPresettle)throws Exception{
		int row = getBillCardPanel().getBodyPanel().getTable().getRowCount();
		if (row >= 1) {
			// �õ�����vo
			AggSalepresettleVO aggvo = (AggSalepresettleVO) getBillCardPanel().getBillValueVO(
					        AggSalepresettleVO.class.getName(),
							SalepresettleHVO.class.getName(),
							SalepresettleBVO.class.getName());
			SalepresettleBVO bvos[] = null;
			try {
				Class[] ParameterTypes = new Class[] { AggSalepresettleVO.class ,UFBoolean.class};
				Object[] ParameterValues = new Object[] { aggvo,new UFBoolean(isPresettle) };
				Object o = LongTimeTask.calllongTimeService("xcgl", null,
						"���ڼ���...", 1, "nc.bs.xcgl.salesettle.Price", null,
						"CountPrice", ParameterTypes, ParameterValues);
				bvos = (SalepresettleBVO[]) o;
			} catch (Exception e) {
				e.printStackTrace();
				getBillUI().showErrorMessage(e.getMessage());
			}
            if(bvos==null){
            	return ;
            }
        	getBillCardPanel().getBillModel().setBodyDataVO(bvos);
			for (int i = 0; i < bvos.length; i++) {
				getBillCardPanel().getBillModel().execEditFormulas(i);
				// ����ֵ�Ժ��ֶ�ִ��һ����ʾ��ʽ��
				getBillCardPanel().getBillModel().execLoadFormulaByRow(i);
			}
			//ȥ�����ۺ�˰������˰���
			int count=getBillCardPanel().getRowCount();
			for(int i=0;i<count;i++){
				if(PuPubVO.getUFBoolean_NullAs(getBillCardPanel().getBodyValueAt(i, "ureserve2"), new UFBoolean(false)).booleanValue()==true
						&&PuPubVO.getUFBoolean_NullAs(getBillCardPanel().getBodyValueAt(i, "ureserve1"), new UFBoolean(false)).booleanValue()==false
						){					
					getBillCardPanel().getBillModel().setValueAt(null, i, "notaxsum");
					getBillCardPanel().getBillModel().setValueAt(null, i, "npricetaxsum");
					getBillCardPanel().getBillModel().setValueAt(null, i, "nreserve1");
					getBillCardPanel().getBillModel().setValueAt(null, i, "nreserve5");
				}
			}
			//���þ��ۼ�˰�ϼƺ���˰���	
			//���ھ������⣬ͨ�������õ��ĺ�˰���ۣ����ܴ�������С��
			//���ԣ���˰����*�����õ����п��ܲ�����ָ���˰�ϼ�֮��
			for(int i=0;i<count;i++){
				if(PuPubVO.getUFBoolean_NullAs(getBillCardPanel().getBodyValueAt(i, "ureserve2"), new UFBoolean(false)).booleanValue()==true
						&&PuPubVO.getUFBoolean_NullAs(getBillCardPanel().getBodyValueAt(i, "ureserve1"), new UFBoolean(false)).booleanValue()==false
						){	
				    String rowno=(String) getBillCardPanel().getBodyValueAt(i, "crowno");
		            
				    Integer[] rows=getJoinRowno(rowno);
				    UFDouble nmy=new UFDouble(0);
				    UFDouble wnmy=new UFDouble(0);
				    if(rows!=null && rows.length>0){
				    	for(int j=0;j<rows.length;j++){
				    		nmy=nmy.add(PuPubVO.getUFDouble_NullAsZero(
				    				getBillCardPanel().getBodyValueAt(rows[j], "nreserve10")));
				    		wnmy=wnmy.add(PuPubVO.getUFDouble_NullAsZero(
				    				getBillCardPanel().getBodyValueAt(rows[j], "nreserve9")));
				    	}
				    }			    
				    getBillCardPanel().getBillModel().setValueAt(nmy, i, "nreserve10");
				    getBillCardPanel().getBillModel().setValueAt(wnmy,i, "nreserve9");	
				}
			}
		}
	}

	private Integer[] getJoinRowno(String rowno) {
		int count = getBillCardPanel().getRowCount();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < count; i++) {
			if (!PuPubVO.getUFBoolean_NullAs(
					getBillCardPanel().getBodyValueAt(i, "ureserve2"),
					new UFBoolean(false)).booleanValue() == true) {

				if (PuPubVO.getUFBoolean_NullAs(
						getBillCardPanel().getBodyValueAt(i, "uimpurity"),
						new UFBoolean(false)).booleanValue() == true) {
					// ��ȡ�����к�
					String rowno1 = (String) getBillCardPanel().getBodyValueAt(
							i, "vreserve2");
					if (rowno.equals(rowno1)) {
						list.add(new Integer(i));
					}
				}

			}
		}
		return list.toArray(new Integer[0]);
	}

	
	
	
	
	
	
	
	
	
	
}
