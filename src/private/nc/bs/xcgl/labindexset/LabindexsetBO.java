package nc.bs.xcgl.labindexset;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.xcgl.labindexset.LabIndexSetHVO;

public class LabindexsetBO implements IBDBusiCheck{

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if(intBdAction==IBDACTION.SAVE){
			   if(vo==null)
				   return ;
			   if(vo.getParentVO()!=null){
				   LabIndexSetHVO hvo=(LabIndexSetHVO) vo.getParentVO();
				   //CircularlyAccessibleValueObject[] bvos = vo.getChildrenVO();
//				   int count=0;
//				   if(bvos!=null&&bvos.length!=0){
//					   BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_invmandoc"},new String[]{"化验金属名称"});
//					   for(int i=0;i<bvos.length;i++){
//						   LabIndexSetBVO bvo = (LabIndexSetBVO) bvos[i];
//						   Integer itype =PuPubVO.getInteger_NullAs(bvo.getItype(),3);
//						   if(itype==0){
//							   count++;
//						   }
//					   }
//				   }
//				   if(count!=1){
//					   throw new BusinessException("表体有且只能有一个主指标!");
//				   }
					if(hvo.getStatus()!=VOStatus.DELETED){
				   BsUniqueCheck.FieldUniqueChecks(hvo, new String[]{"pk_corp","pk_invmandoc","pk_minarea","vdef20"},null,"公司，部门，关联矿石，化验存货名称组合重复!");
					}
					else{
						boolean isReference = ZMReferenceCheck.getReferenceCheck().isReferenced(
								"xcgl_labindexset_h", hvo.getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("数据已经被引用,编码为：" + "['"
									+ hvo.getVlabinvcode() + "'] 名称为：['"
									+ hvo.getVlabinvname() + "']");
						}
					}
			   }
		}else if(intBdAction==IBDACTION.DELETE){
			
		}
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
		
	}
}
