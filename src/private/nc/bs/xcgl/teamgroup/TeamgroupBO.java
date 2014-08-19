package nc.bs.xcgl.teamgroup;

import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.fp.combase.pub01.IBDACTION;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.xcgl.teamgroup.TeamGruopHVO;

public class TeamgroupBO implements IBDBusiCheck{

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if(intBdAction==IBDACTION.SAVE){
			   if(vo==null)
				   return ;
			   if(vo.getParentVO()!=null){
				   TeamGruopHVO hvo=(TeamGruopHVO) vo.getParentVO();
					if(hvo.getStatus()!=VOStatus.DELETED){
				   BsUniqueCheck.FieldUniqueChecks(hvo, new String[]{"pk_corp","teamcode"},null,"信息重复");
					}
					else{
						boolean isReference = ZMReferenceCheck.getReferenceCheck().isReferenced(
								"xcgl_teamgroup_h", hvo.getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("数据已经被引用,编码为：" + "['"
									+ hvo.getTeamcode() + "'] 名称为：['"
									+ hvo.getTeamname() + "']");
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