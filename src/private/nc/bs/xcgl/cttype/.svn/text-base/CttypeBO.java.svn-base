package nc.bs.xcgl.cttype;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.xcgl.cttype.CtTypeVO;

/**
 *类说明：合同类型校验
 *@author jay
 *@version 1.0   
 *创建时间：2014-3-20下午04:07:09
 */
public class CttypeBO implements IBDBusiCheck{

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if (intBdAction == IBDACTION.SAVE) {
			if (vo == null)
				return;
			if (vo.getChildrenVO() != null && vo.getChildrenVO().length > 0) {
				CtTypeVO[] bvos = (CtTypeVO[]) vo.getChildrenVO();
				for (int i = 0; i < bvos.length; i++) {
					if (bvos[i].getStatus() != VOStatus.DELETED) {
						BsUniqueCheck.FieldUniqueChecks(bvos[i], new String[] {
								"pk_corp", "typecode" }, null,
								"类型编码重复！");
					}else{
						boolean isReference = ZMReferenceCheck.isReferenced(
								"xcgl_cttype", bvos[i].getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("数据已经被引用,编码为：" + "['"
									+ bvos[i].getTypecode() + "'] 名称为：['"
									+ bvos[i].getTypename() + "']");
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
