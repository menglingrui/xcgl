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
 * �����������ù���ҵ������
 * @author mlr
 */
public class PubProduceSetBO {
	public static AggProduceSetVO getProduceSetBillVO(ProSetParaVO para)throws BusinessException{
		XCLogger.printInfor("ȡ�����������÷�����ʼ��");
		if(para==null){
			throw new BusinessException("����Ϊ��");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
		    throw new BusinessException("�����߲���Ϊ��");	
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
		    	XCLogger.printInfor("ȡ�����������÷���������");
		    	return null;
		    }	
		    XCLogger.printInfor("ȡ�����������÷���������");
		    return (AggProduceSetVO) billvos[0];	    
		} catch (Exception e) {
			XCLogger.printError("ȡ�����������÷�������"+e.getMessage());
		    throw new BusinessException(e);
		}		
	}
}
