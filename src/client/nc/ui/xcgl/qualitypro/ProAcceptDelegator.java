package nc.ui.xcgl.qualitypro;

import java.util.Hashtable;

import nc.ui.trade.business.HYPubBO_Client;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.xcgl.qualitypro.QualityPorB1VO;
import nc.vo.xcgl.qualitypro.QualityProB2VO;

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
			for (int i = 0; i < tableCodes.length; i++) {
				if (tableCodes[i].equals("xcgl_qualitypro_b2")) {
					QualityProB2VO[] b1vos = (QualityProB2VO[]) this.queryByCondition(
							QualityProB2VO.class, "pk_qualitypro_h='" + key
									+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b1vos);
				}
				if (tableCodes[i].equals("xcgl_qualitypro_b1")) {
					QualityPorB1VO[] b2vos = (QualityPorB1VO[]) this.queryByCondition(
							QualityPorB1VO.class, "pk_qualitypro_h='" + key
									+ "' and isnull(dr,0)=0");
					dataHT.put(tableCodes[i], b2vos);
				}
			}
			return dataHT;
		}
		return dataHT;
	}
		
}
