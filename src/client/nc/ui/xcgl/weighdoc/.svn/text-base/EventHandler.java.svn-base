package nc.ui.xcgl.weighdoc;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.weighdoc.AggWeighdocVO;
import nc.vo.xcgl.weighdoc.WeighdocBVO;

public class EventHandler extends XCFlowManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	
	@Override
	protected void onBoSave() throws Exception {
		AggWeighdocVO billvo = (AggWeighdocVO) getBillCardPanelWrapper().getBillVOFromUI();
		if(billvo.getChildrenVO()!=null){
			if(billvo==null)
				return;
			if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
				WeighdocBVO []bvos=(WeighdocBVO[])billvo.getChildrenVO();
				// 必输项校验
				BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
				//唯一性校验
//				BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc"}, new String[]{"工程项目","作业"});
				//获得选中行号
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
			}else
				throw new BusinessException("表体不能为空!");
				super.onBoSave();
		}
	}
	@Override
	public void onBillRef() throws Exception {
		try {
		
			super.onBillRef();
			if (PfUtilClient.isCloseOK()) {
				getBillUI().setBillOperate(IBillOperate.OP_REFADD);
				getBillUI().setDefaultData();
				getButtonManager().getButton(IBillButton.Line).setEnabled(false);
				getBillManageUI().updateButtons();
				getBillCardPanel().setBodyMenuShow(false);
				if(getBillCardPanel().getRowCount()>0){
					for(int i=0;i<getBillCardPanel().getRowCount();i++){
						getBillCardPanel().getBillModel().execEditFormulas(i);
						getBillCardPanel().getBillModel().setValueAt((i+1)*10, i, "crowno");
					}
				}
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 参照完成之后还原参照按钮tag
			ButtonObject btn = getButtonManager()
					.getButton(IBillButton.Refbill);
			btn.setTag(String.valueOf(IBillButton.Refbill));
		}
	}
	
	/**
	 * 进行参照单据时是否需要数据交换。 创建日期：(2004-4-5 19:12:29)
	 * 
	 * @return boolean
	 */
	protected boolean isDataChange() {
		return true;
	}
}
