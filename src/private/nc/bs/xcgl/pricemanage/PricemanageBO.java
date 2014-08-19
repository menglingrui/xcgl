package nc.bs.xcgl.pricemanage;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.xcgl.pricemanage.PriceManageHVO;
/**
 * �۸�ά��У����
 * @author ddk
 *
 */
public class PricemanageBO implements IBDBusiCheck {

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if (intBdAction == IBDACTION.SAVE) {
			if (vo == null)
				return;
			if (vo.getParentVO() != null ) {
				PriceManageHVO hvo = (PriceManageHVO) vo.getParentVO();
//					if (hvo.getStatus() != VOStatus.DELETED) {
						BsUniqueCheck.FieldUniqueChecks(hvo, new String[] {
								"pk_invmandoc", "vyear","vmonth","vsourcemanage" }, null,
								"��������,���,�·�,��Դ��Ϣ�ֶ���Ϣ�ظ�!");
//					}
//				}
			}
		}
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {

	}


}
