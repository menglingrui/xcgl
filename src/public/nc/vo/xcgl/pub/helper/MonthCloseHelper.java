package nc.vo.xcgl.pub.helper;
import nc.bs.trade.business.HYPubBO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.endclosing.EndClosingBVO;
import nc.vo.xcgl.endclosing.EndClosingHVO;
import nc.vo.xcgl.endclosing.EndclosingVO;
/**
 * 月末关账帮助类
 * @author lxh
 */
public class MonthCloseHelper {
    public static HYPubBO hypubbo=new HYPubBO();
	/**
	 * 判断当前日期是否已经关账
	 * @param curDate
	 * @return
	 * @throws BusinessException
	 */
	public static UFBoolean isMonthClose(UFDate curDate,String pk_corp)throws BusinessException{		
		String year = PuPubVO.getString_TrimZeroLenAsNull(curDate.getYear());
		EndclosingVO vo = (EndclosingVO)getCloseMonByYear(year,pk_corp);
		if(vo == null){
			return UFBoolean.FALSE;
		}
		EndClosingBVO[] bodys = vo.getItems();
		for (EndClosingBVO body : bodys) {
			UFDate sdate = body.getDstartdate();
			UFDate edate = body.getDenddate();
			if(curDate.compareTo(edate)<=0 && curDate.compareTo(sdate)>=0 ){
				if(PuPubVO.getUFBoolean_NullAs( body.getIsclosing(),UFBoolean.FALSE).booleanValue()==true){
					return UFBoolean.TRUE;
				}
			}
		}
		return UFBoolean.FALSE;		
	
	}	
	/**
	 * @author lxh
	 * @说明：（鹤岗矿业）
	 * 月末结账，通过年份和公司 返回关账信息
	 * 2011-10-20下午07:02:37
	 * @param accountyear
	 * @param corp
	 * @return
	 * @throws BusinessException
	 */
	public static AggregatedValueObject getCloseMonByYear(String accountyear,String corp) throws BusinessException{
		AggregatedValueObject[] bills = null;
		bills = hypubbo.queryBillVOByCondition(new String[]{"nc.vo.xcgl.endclosing.EndclosingVO","nc.vo.xcgl.endclosing.EndClosingHVO"},
				" cyear = '"+accountyear+"' and pk_corp = '"+corp+"'");
		//返回表数据为空，则读取系统表数据，=1为符合条件唯一聚合vo，>1出现年份重复错误
		if(null != bills && bills.length > 0){
			if(bills.length>1)
				throw new ValidationException("数据异常");
			//获得当前日期下的聚合vo【0】以及表头vo
			AggregatedValueObject bill = bills[0];
			EndClosingHVO headvo = (EndClosingHVO) bill.getParentVO();
			//通过表头vo的主键值，获取表体vo[]
			EndClosingBVO[] bodyvos = null;
			bodyvos = (EndClosingBVO[]) hypubbo.queryByCondition(EndClosingBVO.class, " pk_endclosing_h = '" + headvo.getPrimaryKey()+"'");
			//校验表体不为空
			if(bodyvos == null || bodyvos.length == 0)
				throw new ValidationException("数据异常");
			//表体vo[]存入 聚合vo
			bill.setChildrenVO(bodyvos);		
			return bill;
		}		
		return null;
	}
}
