package nc.bs.xcgl.pub;
import java.util.List;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.produceset.AggProduceSetVO;
import nc.vo.xcgl.produceset.ProductSetVO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.logger.XCLogger;
import nc.vo.xcgl.pub.tool.XcPubTool;
/**
 * 生产流程设置公共业务处理类
 * @author mlr
 */
public class PubProduceSetBO {
	public static AggProduceSetVO getProduceSetBillVO(ProSetParaVO para)throws BusinessException{
		XCLogger.printInfor("取生产流程设置方法开始：");
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("生产线不能为空");	
		}	
        String pk_beltline=para.getPk_beltline();
        List list= (List) XCZmPubDAO.getDAO().retrieveByClause(ProductSetVO.class, "isnull(dr,0)=0 and pk_beltline='"+pk_beltline+"'");
		if(list==null || list.size()==0)
			return null;
		ProductSetVO hvo=(ProductSetVO) list.get(0);
		String pk=hvo.getPrimaryKey();		
		try {
			AggregatedValueObject[]  billvos=XcPubTool.getExtBillVOByTypeAndPk(PubBillTypeConst.billtype_prduceset, pk);
		    if(billvos==null || billvos.length==0){
		    	XCLogger.printInfor("取生产流程设置方法结束：");
		    	return null;
		    }	
		    XCLogger.printInfor("取生产流程设置方法结束：");
		    return (AggProduceSetVO) billvos[0];	    
		} catch (Exception e) {
			XCLogger.printError("取生产流程设置方法报错："+e.getMessage());
		    throw new BusinessException(e);
		}		
	}
}
