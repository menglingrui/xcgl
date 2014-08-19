package nc.ui.xcgl.soct;

import java.util.Hashtable;

import nc.ui.trade.business.HYPubBO_Client;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.soct.SoctB2VO;
import nc.vo.xcgl.soct.SoctB3VO;
import nc.vo.xcgl.soct.SoctB4VO;
import nc.vo.xcgl.soct.SoctB7VO;
import nc.vo.xcgl.soct.SoctBVO;

public class ProAcceptDelegator extends nc.ui.trade.bsdelegate.BusinessDelegator{
	public nc.vo.pub.CircularlyAccessibleValueObject[] queryBodyAllData(
			Class voClass, String billType, String key, String strWhere)
			throws Exception {
		if (billType == null || billType.trim().length() == 0)
			return null;
		else {
			// 根据主表主键，取得子表的数据
			CircularlyAccessibleValueObject[] b1vos = (CircularlyAccessibleValueObject[]) HYPubBO_Client
					.queryAllBodyData(billType, voClass, key, strWhere);
			return b1vos; 
		}
	}
	
	@Override
	public Hashtable loadChildDataAry(String[] tableCodes, String key)
			throws Exception {
		Hashtable<String, SuperVO[]> dataHT = new Hashtable<String, SuperVO[]>();
		if (tableCodes != null && tableCodes.length > 0) {
			// 查询数据放Hashtable并返回
		
			for (int i = 0; i < tableCodes.length; i++) {
				if (tableCodes[i].equals("xcgl_soct_b")) {
					SoctBVO[] b1vos = (SoctBVO[]) this.queryByCondition(
							SoctBVO.class, "pk_soct='" + key
									+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b1vos);
				}
				if (tableCodes[i].equals("xcgl_soct_b2")) {
					SoctB2VO[] b2vos = (SoctB2VO[]) this.queryByCondition(
							SoctB2VO.class, "pk_soct='" + key
									+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b2vos);
				}
				if (tableCodes[i].equals("xcgl_soct_b3")) {
					SoctB3VO[] b3vos = (SoctB3VO[]) this.queryByCondition(SoctB3VO.class, "pk_soct='"+key
							+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b3vos);
				}
				if (tableCodes[i].equals("xcgl_soct_b4")) {
					SoctB4VO[] b4vos = (SoctB4VO[]) this.queryByCondition(SoctB4VO.class, "pk_soct='"+key
							+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b4vos);
				}
				if (tableCodes[i].equals("xcgl_soct_b7")) {
					SoctB7VO[] b7vos = (SoctB7VO[]) this.queryByCondition(SoctB7VO.class, "pk_soct='"+key
							+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b7vos);
				}
//				if (tableCodes[i].equals("xcgl_soct_b5")) {
//					SoctB5VO[] b5vos = (SoctB5VO[]) this.queryByCondition(SoctB5VO.class, "pk_soct='"+key
//							+ "' and isnull(dr,0)=0");
//					dataHT.put(tableCodes[i], b5vos);
//				}
//				if (tableCodes[i].equals("xcgl_soct_b6")) {
//					SoctB6VO[] b6vos = (SoctB6VO[]) this.queryByCondition(SoctB6VO.class, "pk_soct='"+key
//							+ "' and isnull(dr,0)=0");
//					dataHT.put(tableCodes[i], b6vos);
//				}
			}
			return dataHT;
		}
		return dataHT;
	}
}
