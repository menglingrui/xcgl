package nc.ui.xcgl.labindexset;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.BusinessException;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.labindexset.AggLabIndexSetVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
/**
 * add by Jay
 * 修改校验表体的指标编码不能重复
 * @author Jay
 *
 */
public class EventHandler extends ManageEventHandler{

	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected void onBoSave() throws Exception {
		 AggLabIndexSetVO billvo=( AggLabIndexSetVO) getBillUI().getVOFromUI();
		if(billvo==null)
			return;
		BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
		//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_invmandoc"},new String[]{"化验金属名称"});
		//设备编码字段不为空校验
		BeforeSaveValudate.FieldBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTable(), 
				getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTableModel(), "vchemetalcode", "化验金属编码");
		LabIndexSetBVO []bvos=(LabIndexSetBVO[])billvo.getChildrenVO();
		if(bvos!=null&&bvos.length>0){
			   int count=0;
			   if(bvos!=null&&bvos.length!=0){
				   for(int i=0;i<bvos.length;i++){
					   LabIndexSetBVO bvo = (LabIndexSetBVO) bvos[i];
					   Integer itype =PuPubVO.getInteger_NullAs(bvo.getItype(),3);
					   if(itype==0){
						   count++;
					   }
				   }
			   }
			   if(count!=1){
				   throw new BusinessException("表体有且只能有一个主指标!");
			   }
			//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vchemetalcode"}, new String[]{"公司","化验金属编码"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
	}
}
