package nc.bs.xcgl.pricemanage;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.xcgl.pricemanage.PriceManageHVO;
/**
 * 价格维护校验类
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
								"金属名称,年份,月份,来源信息字段信息重复!");
//					}
//				}
			}
		}
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {

	}


}
