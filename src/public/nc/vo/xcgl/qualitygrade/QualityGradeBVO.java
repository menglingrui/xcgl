package nc.vo.xcgl.qualitygrade;

import nc.vo.xcgl.pub.bill.PubHighQualityBVO;
/**
 * 优质优价—品位BVO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class QualityGradeBVO extends PubHighQualityBVO{
	/**
	 * 主表主键
	 */
	private String pk_qualitygrade_h;
	/**
	 * 字表主键
	 */
	private String pk_qualitygrade_b;
	
	public String getPk_qualitygrade_h() {
		return pk_qualitygrade_h;
	}

	public void setPk_qualitygrade_h(String pk_qualitygrade_h) {
		this.pk_qualitygrade_h = pk_qualitygrade_h;
	}

	public String getPk_qualitygrade_b() {
		return pk_qualitygrade_b;
	}

	public void setPk_qualitygrade_b(String pk_qualitygrade_b) {
		this.pk_qualitygrade_b = pk_qualitygrade_b;
	}

	@Override
	public String getPKFieldName() {
		return "pk_qualitygrade_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_qualitygrade_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_qualitygrade_b";
	}
	
//	public QualityGradeBVO(){
//		super();
//	}

}
