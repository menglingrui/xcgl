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
 * �޸�У������ָ����벻���ظ�
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
		//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_invmandoc"},new String[]{"�����������"});
		//�豸�����ֶβ�Ϊ��У��
		BeforeSaveValudate.FieldBodyUnique(getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTable(), 
				getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().getTableModel(), "vchemetalcode", "�����������");
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
				   throw new BusinessException("��������ֻ����һ����ָ��!");
			   }
			//BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_corp","vchemetalcode"}, new String[]{"��˾","�����������"});
		}
		else
			throw new Exception("���岻��Ϊ��");
		super.onBoSave();
	}
}
