package nc.bs.xcgl.ctexpset;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.xcgl.ctexpset.CtExpsetVO;
/**
 * 合同费用校验
 * @author ddk
 *
 */
public class CtExpsetBO implements IBDBusiCheck{

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if(intBdAction == IBDACTION.SAVE){
			if (vo == null)
				return;
			if (vo.getChildrenVO() != null && vo.getChildrenVO().length > 0){
				CtExpsetVO []bvos=(CtExpsetVO[]) vo.getChildrenVO();
				for (int i = 0; i < bvos.length; i++){
					if (bvos[i].getStatus() != VOStatus.DELETED) {
						BsUniqueCheck.FieldUniqueChecks(bvos[i], new String[] {
								"pk_corp", "vexpitemcode" }, null,
								"公司、编码组合重复！");
					} else {
						boolean isReference = ZMReferenceCheck.isReferenced(
								"xcgl_ctexpset", bvos[i].getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("数据已经被引用,编码为：" + "['"
									+ bvos[i].getVexpitemcode() + "'] 名称为：['"
									+ bvos[i].getVexpitemname() + "']");
						}
					}
				}
			}
		}else if (intBdAction == IBDACTION.DELETE) {
			
		}
		
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
		
	}

}
