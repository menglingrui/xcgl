package nc.ui.xcgl.pricemanage;

import java.math.BigDecimal;

import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.ClientEnvironment;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.helper.QueryHelper;

/**
 * ����ƽ���۸�
 * @author yangtao
 * @date 2014-3-28 ����10:06:22
 */
public class AvgPrice {
   
	/**
	 * ����Ѯ���۵ķ���
	 * ������Ҫ��������
	 * @param pk_invmandoc     �����Ϣ
	 * @param pk_sourcemanage  �۸���Դ��Ϣ
	 * @author yangtao
	 * @return 
	 * @throws BusinessException 
	 * @date 2014-3-28 ����10:29:06
	 */
	public static UFDouble AvgtoVmanth(String pk_invmandoc,String pk_sourcemanage) throws BusinessException{
		//���Ȼ�õ�ǰʱ��  
		String date=ClientEnvironment.getServerTime().toString().substring(0,10); 
		//�õ���ǰ��
		Integer month = Integer.parseInt(date.substring(5, 7));
		String wheresql=" and isnull(dr,0)=0 and pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'";
		//������3���������ǰ����ʲô�¶ξͲ�ѯʲô�¶εļ۸񡣡�������
		if(month>0&&month<=10){
			wheresql+=" and vmanth ='��Ѯ'";
		}else if(month>10&&month<=20){
			wheresql+=" and vmanth ='��Ѯ'";
		}else if(month>20&&month<=31){
			wheresql+=" and vmanth ='��Ѯ'";
		}
		
		wheresql+=" and ddate<='"+date+"'";
		
		String sql="select avg(nprice) from xcgl_pricemanage_b " +
				" where nprice is not null and pk_pricemanage_h in " +
				"(select pk_pricemanage_h from xcgl_pricemanage_h " +
				" where pk_invmandoc = '"+pk_invmandoc+"'and vyear = '"+date.substring(0, 4)+"' " +
				" and vmonth = '"+date.substring(5, 7)+"'and vsourcemanage = '"+pk_sourcemanage+"' " +
				" and isnull(dr,0)=0 and pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"')";
		
		sql+=wheresql;
		//��ѯ����ǰѰ�ľ���
		UFDouble d= new UFDouble((BigDecimal) QueryHelper.executeQuery(sql, new ColumnProcessor()));
		
		return d ;
		
	} 
}
