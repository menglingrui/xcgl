package nc.ui.xcgl.saleweighdoc;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.RefBillTypeChangeEvent;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.saleweighdoc.AggSaleWeighdocVO;
import nc.vo.xcgl.saleweighdoc.SaleWeighdocBVO;

public class EventHandler extends XCFlowManageEventHandler {

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

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
			// set businesstype
			getBillCardPanel().getHeadItem("pk_busitype").setValue(
					getBillUI().getBusinessType());
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
				getButtonManager().getButton(IBillButton.Line).setEnabled(true);
				getBillManageUI().updateButtons();
				getBillCardPanel().setBodyMenuShow(false);
				if (getBillCardPanel().getRowCount() > 0) {
					if (getBillCardPanel().getRowCount() > 0) {
						for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
							getBillCardPanel().getBillModel().execEditFormulas(
									i);
							getBillCardPanel().getBillModel().setValueAt(
									(i + 1) * 10 + "", i, "crowno");
							getBillCardPanel().getBillModel().setValueAt(
									new UFBoolean(true), i, "ureserve2");
						}
					}
				}
				getBillCardPanel().getHeadItem("pk_busitype").setValue(
						getBillUI().getBusinessType());
			}
		}
	}

	@Override
	protected void onBoSave() throws Exception {
		AggSaleWeighdocVO billvo = (AggSaleWeighdocVO) getBillCardPanelWrapper()
				.getBillVOFromUI();
		if (billvo.getChildrenVO() != null) {
			if (billvo == null)
				return;
			if (billvo.getChildrenVO() != null
					&& billvo.getChildrenVO().length > 0) {
				SaleWeighdocBVO[] bvos = (SaleWeighdocBVO[]) billvo
						.getChildrenVO();
				// 必输项校验
				BeforeSaveValudate
						.dataNotNullValidate(getBillCardPanelWrapper()
								.getBillCardPanel());
				// 唯一性校验
				// BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new
				// String[]{"pk_jobmngfil","pk_invmandoc"}, new
				// String[]{"工程项目","作业"});
				// 获得选中行号
				int selerow = getBillCardPanelWrapper().getBillCardPanel()
						.getBodyPanel().getTable().getSelectedRow();
				UFDouble a = PuPubVO.getUFDouble_NullAsZero(
						getBillCardPanel().getBodyValueAt(selerow,
								"ngrossweight")).sub(
						PuPubVO.getUFDouble_NullAsZero(getBillCardPanel()
								.getBodyValueAt(selerow, "ntare")));
				if (a.doubleValue() < 0) {
					throw new BusinessException("毛重-净重的值不能小于0！");
				}
				calSum(bvos);
			} else
				throw new BusinessException("表体不能为空!");
			super.onBoSave();
		}
	}

	public void calSum(SaleWeighdocBVO[] bvos) {
		if (bvos == null || bvos.length == 0) {
			return;
		}
		UFDouble sz = new UFDouble(0);
		UFDouble pz = new UFDouble(0);
		UFDouble jz = new UFDouble(0);
		UFDouble gz = new UFDouble(0);
		for (int i = 0; i < bvos.length; i++) {
			sz = sz.add(PuPubVO.getUFDouble_NullAsZero(bvos[i].getNgrossweight()));
			pz = pz.add(PuPubVO.getUFDouble_NullAsZero(bvos[i].getNtare()));
			jz = jz.add(PuPubVO.getUFDouble_NullAsZero(bvos[i].getNnetweight()));
			gz = gz.add(PuPubVO.getUFDouble_NullAsZero(bvos[i].getNdryamount()));
		}
		getBillCardPanel().getHeadItem("nreserve1").setValue(sz);
		getBillCardPanel().getHeadItem("nreserve2").setValue(pz);
		getBillCardPanel().getHeadItem("nreserve3").setValue(jz);
		getBillCardPanel().getHeadItem("nreserve4").setValue(gz);

	}

	/**
	 * 按钮m_boLineCopy点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoLineCopy() throws Exception {
		getBillCardPanelWrapper().copySelectedLines();
		CircularlyAccessibleValueObject[] vos = getBillCardPanelWrapper()
				.getCopyedBodyVOs();
		if (vos != null) {
			for (int i = 0; i < vos.length; i++) {
				vos[i].setAttributeValue("ureserve2", null);
			}
		}
	}

	/**
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit() throws Exception {
		getButtonManager().getButton(IBillButton.Line).setEnabled(false);
		super.onBoEdit();
	}
}
