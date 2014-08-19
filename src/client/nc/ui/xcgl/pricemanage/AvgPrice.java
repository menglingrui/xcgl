package nc.ui.xcgl.pricemanage;

import java.math.BigDecimal;

import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.ClientEnvironment;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.helper.QueryHelper;

/**
 * 计算平均价格
 * @author yangtao
 * @date 2014-3-28 上午10:06:22
 */
public class AvgPrice {
   
	/**
	 * 计算旬均价的方法
	 * 这里需要两个参数
	 * @param pk_invmandoc     存货信息
	 * @param pk_sourcemanage  价格来源信息
	 * @author yangtao
	 * @return 
	 * @throws BusinessException 
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble AvgtoVmanth(String pk_invmandoc,String pk_sourcemanage) throws BusinessException{
		//首先获得当前时间  
		String date=ClientEnvironment.getServerTime().toString().substring(0,10); 
		//得到当前月
		Integer month = Integer.parseInt(date.substring(5, 7));
		String wheresql=" and isnull(dr,0)=0 and pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'";
		//这里有3种情况。当前处于什么月段就查询什么月段的价格。。。。。
		if(month>0&&month<=10){
			wheresql+=" and vmanth ='上旬'";
		}else if(month>10&&month<=20){
			wheresql+=" and vmanth ='中旬'";
		}else if(month>20&&month<=31){
			wheresql+=" and vmanth ='下旬'";
		}
		
		wheresql+=" and ddate<='"+date+"'";
		
		String sql="select avg(nprice) from xcgl_pricemanage_b " +
				" where nprice is not null and pk_pricemanage_h in " +
				"(select pk_pricemanage_h from xcgl_pricemanage_h " +
				" where pk_invmandoc = '"+pk_invmandoc+"'and vyear = '"+date.substring(0, 4)+"' " +
				" and vmonth = '"+date.substring(5, 7)+"'and vsourcemanage = '"+pk_sourcemanage+"' " +
				" and isnull(dr,0)=0 and pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"')";
		
		sql+=wheresql;
		//查询出当前寻的均价
		UFDouble d= new UFDouble((BigDecimal) QueryHelper.executeQuery(sql, new ColumnProcessor()));
		
		return d ;
		
	} 
}
