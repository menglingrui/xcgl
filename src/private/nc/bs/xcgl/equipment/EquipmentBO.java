package nc.bs.xcgl.equipment;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.xcgl.equipment.EquipMentVO;

public class EquipmentBO implements IBDBusiCheck{

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if (intBdAction == IBDACTION.SAVE) {
			if (vo == null)
				return;
			if (vo.getChildrenVO() != null && vo.getChildrenVO().length > 0) {
				EquipMentVO[] bvos = (EquipMentVO[]) vo.getChildrenVO();
				for (int i = 0; i < bvos.length; i++) {
					if(bvos[i].getStatus()!=VOStatus.DELETED){
					   BsUniqueCheck.FieldUniqueChecks(bvos[i], new String[] {
							"pk_corp", "vequip_code" }, null, "��˾����������ظ���");
					}else{
						boolean isReference = ZMReferenceCheck.getReferenceCheck().isReferenced(
								"xcgl_equipment", bvos[i].getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("�����Ѿ�������,����Ϊ��" + "['"
									+ bvos[i].getVequip_code() + "'] ����Ϊ��['"
									+ bvos[i].getVequip_name() + "']");
						}
					}
				}
			}
		} 
		else if (intBdAction == IBDACTION.DELETE) {

		}
	}


	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
		
	}
}