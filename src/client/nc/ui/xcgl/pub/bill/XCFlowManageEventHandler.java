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
			getBillUI().showErrorMessage("当前日期已经关账");
		}
		super.onBoAdd(bo);
	}

	public IBillBusiListener m_bbl = null;

	/**
	 * 按钮m_boAdd点击时执行的动作,如有必要，请覆盖.
	 * 
	 * @param bo
	 *            来源单据的平台生成按钮
	 * @param sourceBillId
	 *            参考来源单据Id
	 */
	public void onBoBusiTypeAdd(ButtonObject bo, String sourceBillId)
			throws Exception {
		getBusiDelegator().childButtonClicked(bo, _getCorp().getPrimaryKey(),
				getBillUI()._getModuleCode(), _getOperator(),
				getUIController().getBillType(), getBillUI(),
				getBillUI().getUserObject(), sourceBillId);
		if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
			// 设置单据状态
			getBillUI().setCardUIState();
			// 新增
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
			getBillUI().showErrorMessage("当前单据日期已经关账");
			return;
		}
		super.onBoSave();
	}

	/**
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("当前单据日期已经关账");
			return;
		}
		super.onBoEdit();
	}

	/**
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit1() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("当前单据日期已经关账");
			return;
		}
		super.onBoEdit1();
	}

	@Override
	protected void onBoDelete() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("当前单据日期已经关账");
			return;
		}
		super.onBoDelete();
	}

	@Override
	protected void onBoDel() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("当前单据日期已经关账");
			return;
		}
		super.onBoDel();
	}

	protected void onBoCancelAudit() throws Exception {
		UFDate dbilldate = PuPubVO.getUFDate(getBufferData().getCurrentVO()
				.getParentVO().getAttributeValue("dbilldate"));
		if (MonthCloseHelper.isMonthClose(dbilldate, pk_corp).booleanValue()) {
			getBillUI().showErrorMessage("当前单据日期已经关账");
			return;
		}
		super.onBoCancelAudit();
	}

	/**
	 * Button的事件响应处理。 创建日期：(2004-1-6 17:20:57)
	 * 
	 * @param bo
	 *            nc.ui.pub.ButtonObject 异常说明。
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
					System.out.println("新增按钮必须设置TAG,TAG>100的整数.....");
				int intBtn = Integer.parseInt(bo.getTag());
				if (intBtn > 100)// 编号大于100认为是自定义按钮
					onBoElse(intBtn);
				else
					// 否则认为是预置按钮
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
	 * 按钮m_boBusiType点击时执行的动作,如有必要，请覆盖.
	 */
	private final void onBoBusiType(ButtonObject bo) throws Exception {
		// 执行前处理
		busiTypeBefore(getBillUI(), bo);
		bo.setSelected(true);
		// 设置业务类型
		BusitypeVO vo = (BusitypeVO) bo.getData();
		// 处理增加按钮
		getBusiDelegator()
				.retAddBtn(getButtonManager().getButton(IBillButton.Add),
						_getCorp().getPrimaryKey(),
						getUIController().getBillType(), bo);
		// //处理执行按钮(与单据状态无关）
		getBusiDelegator().retElseBtn(
				getButtonManager().getButton(IBillButton.Action),
				getUIController().getBillType(), staticACTION);

		getButtonManager().setActionButtonVO(
				getBillUI().isSaveAndCommitTogether());

		String oldtype = getBillUI().getBusinessType();
		String newtype = vo.getPrimaryKey();
		String oldcode = getBillUI().getBusicode();
		String newcode = vo.getBusicode();

		// 业务类型主键
		getBillUI().setBusinessType(newtype);
		// 业务类型代码
		getBillUI().setBusicode(newcode);

		// 重新刷新UI
		getBillUI().initUI();
		// 清空UI缓存
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
	 * 按钮m_boAction点击时执行的动作,如有必要，请覆盖. 单据执行动作处理
	 */
	private final void onBoAction(ButtonObject bo) throws Exception {
		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000179")/*
		// * @res "开始执行操作,请等待..."
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
		// * @res "执行完毕."
		// */
		// );
	}

	/**
	 * 按钮m_boAction点击时执行的动作,如有必要，请覆盖. 单据执行动作处理
	 */
	private final void onBoAss(ButtonObject bo) throws Exception {
		beforeOnBoAss(bo);
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		Object ret = getBusinessAction().processAction(bo.getTag(), modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (ret != null && ret instanceof AggregatedValueObject) {
			AggregatedValueObject vo = (AggregatedValueObject) ret;
			// 更新状态
			modelVo.getParentVO().setAttributeValue(
					getBillField().getField_BillStatus(),
					vo.getParentVO().getAttributeValue(
							getBillField().getField_BillStatus()));
			// 更新时间戳
			modelVo.getParentVO().setAttributeValue("ts",
					vo.getParentVO().getAttributeValue("ts"));

			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		afterOnBoAss(bo);
	}

	/**
	 * 行操作处理。 创建日期：(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	private void onBoLine(ButtonObject bo) throws java.lang.Exception {
		int intBtn = -1;// Integer.parseInt(bo.getTag());

		if (bo.getData() != null && bo.getData() instanceof ButtonVO) {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			intBtn = btnVo.getBtnNo();
		} else {
			intBtn = Integer.parseInt(bo.getTag());
		}

		// 动作执行前处理
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

		// 动作执行后处理
		buttonActionAfter(getBillUI(), intBtn);
	}

	/**
	 * 浏览操作处理。 创建日期：(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	private void onBoBrow(ButtonObject bo) throws java.lang.Exception {
		int intBtn = Integer.parseInt(bo.getTag());
		// 动作执行前处理
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
		// 动作执行后处理
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
																		 * "转换第:"
																		 * +
																		 * getBufferData
																		 * ().
																		 * getCurrentRow
																		 * () +
																		 * "页完成)"
																		 */
		);

	}

	/**
	 * 按钮m_boNodekey点击时执行的动作,如有必要，请覆盖.
	 */
	private final void onBoNodekey(ButtonObject bo) throws Exception {
		bo.setSelected(true);
		// 设置NodeKey
		getBillUI().setNodeKey(bo.getTag());

		// 重新刷新UI
		getBillUI().initUI();
		// 清空查询模版
		setQueryUI(null);
		// 清空UI缓存
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
												 * @res "开始选择业务类型，请等待......"
												 */);
			onBoBusiType(bo);
			break;
		}
		case IBillButton.Add: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000061")/*
												 * @res "开始进行增加单据，请等待......"
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
												 * @res "开始对单据的辅助进行操作，请等待......"
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
																	 * "开始进行增加单据，请等待......"
																	 */);
				onBoAdd(bo);
				// 动作执行后处理
				buttonActionAfter(getBillUI(), intBtn);
			}
			break;
		}
		case IBillButton.Edit: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000067")/*
												 * @res "开始进行编辑单据，请等待......"
												 */);
			onBoEdit();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Del: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000068")/*
												 * @res "开始进行作废单据，请等待......"
												 */);
			onBoDel();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000069")/* @res "单据作废操作结束" */
			);
			break;
		}
		case IBillButton.Delete: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000070")/*
												 * @res "开始进行档案删除，请等待......"
												 */);
			onBoDelete();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000071")/* @res "档案删除完成" */
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
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);

			break;
		}
		case IBillButton.Save: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000072")/*
												 * @res "开始进行单据保存，请等待......"
												 */);
			onBoSave();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000073")/* @res "单据保存操作结束" */
			);
			break;
		}
		case IBillButton.Cancel: {
			onBoCancel();
			// 清除提示状态栏提示信息
			getBillUI().showHintMessage("");
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Print: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
												 * @res "开始进行打印单据，请等待......"
												 */);
			onBoPrint();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "单据打印操作结束" */
			);
			break;
		}
		case IBillButton.DirectPrint: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
												 * @res "开始进行打印单据，请等待......"
												 */);
			onBoDirectPrint();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "单据打印操作结束" */
			);
			break;
		}

		case IBillButton.Return: {
			onBoReturn();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Card: {
			onBoCard();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Refresh: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000076")/*
												 * @res "开始进行刷新单据，请等待......"
												 */);
			onBoRefresh();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000077")/* @res "单据刷新操作结束" */
			);

			break;
		}
		case IBillButton.Refbill: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000078")/*
												 * @res "开始进行参照单据，请等待......"
												 */);
			onBillRef();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000079")/* @res "单据参照操作结束" */
			);
			break;
		}
		case IBillButton.Copy: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000080")/*
												 * @res "开始进行数据复制，请等待......"
												 */);
			onBoCopy();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000081")/* @res "数据复制操作结束" */
			);
			break;
		}
		case IBillButton.Audit: {
			// 因为下面的提示不符合易用性规范，现在删掉它们
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res "开始执行操作,请等待..."
			// */);
			onBoAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res "执行完毕. "
			// */
			// );
			break;
		}
		case IBillButton.CancelAudit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoCancelAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.Commit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoCommit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.SelAll: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );

			onBoSelAll();

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.SelNone: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoSelNone();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.ImportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoImport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.ExportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoExport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
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
			// * @res 开始执行操作, 请等待...
			// */
			// );

			onBoActionElse(bo);

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		}

	}
	
	/**
	 * 取化验结果
	 * 
	 * @author yangtao
	 * @date 2014-3-26 下午03:47:08
	 * 
	 */
	@SuppressWarnings("unchecked")
	public  void ReadAssayres(boolean isPresettle) throws Exception {
		// 得到表体vo
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
					"正在查询...", 1, "nc.bs.xcgl.salesettle.Price", null,
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
			// 赋完值以后，手动执行一遍显示公式；
			getBillCardPanel().getBillModel().execLoadFormulaByRow(i);
		}
	}
	
	 /**
     * 计算价格
     * 
     * @author yangtao
     * @date 2014-3-27 下午05:32:31
     */
	public void CountPrice(boolean isPresettle)throws Exception{
		int row = getBillCardPanel().getBodyPanel().getTable().getRowCount();
		if (row >= 1) {
			// 得到表体vo
			AggSalepresettleVO aggvo = (AggSalepresettleVO) getBillCardPanel().getBillValueVO(
					        AggSalepresettleVO.class.getName(),
							SalepresettleHVO.class.getName(),
							SalepresettleBVO.class.getName());
			SalepresettleBVO bvos[] = null;
			try {
				Class[] ParameterTypes = new Class[] { AggSalepresettleVO.class ,UFBoolean.class};
				Object[] ParameterValues = new Object[] { aggvo,new UFBoolean(isPresettle) };
				Object o = LongTimeTask.calllongTimeService("xcgl", null,
						"正在计算...", 1, "nc.bs.xcgl.salesettle.Price", null,
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
				// 赋完值以后，手动执行一遍显示公式；
				getBillCardPanel().getBillModel().execLoadFormulaByRow(i);
			}
			//去除精粉含税金额和无税金额
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
			//设置精粉价税合计和无税金额	
			//由于精度问题，通过除法得到的含税单价，可能存在无限小数
			//所以，含税单价*数量得到金额，有可能不等于指标价税合计之和
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
					// 获取关联行号
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
