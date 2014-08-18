package nc.bs.xcgl.cttermtype;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.xcgl.cttermtype.CtTermtypeVO;

/**
 *��˵������ͬ��������У��
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2014-3-20����03:35:18
 */
public class CttermtypeBO implements IBDBusiCheck{

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if (intBdAction == IBDACTION.SAVE) {
			if (vo == null)
				return;
			if (vo.getChildrenVO() != null && vo.getChildrenVO().length > 0) {
				CtTermtypeVO[] bvos = (CtTermtypeVO[]) vo.getChildrenVO();
				for (int i = 0; i < bvos.length; i++) {
					if (bvos[i].getStatus() != VOStatus.DELETED) {
						BsUniqueCheck.FieldUniqueChecks(bvos[i], new String[] {
								"pk_corp", "termtypecode" }, null,
								"�������ͱ����ظ���");
					} else{
						boolean isReference = ZMReferenceCheck.isReferenced(
								"xcgl_cttermtype", bvos[i].getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("�����Ѿ�������,����Ϊ��" + "['"
									+ bvos[i].getTermtypecode() + "'] ����Ϊ��['"
									+ bvos[i].getTermtypename() + "']");
						}
					}
				}
			}
		} 
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
	}

}
