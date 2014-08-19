package nc.vo.xcgl.qualitygrade;

import nc.vo.xcgl.pub.bill.PubHighQualityHVO;
/**
 * 优质优价—品位HVO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class QualityGradeHVO extends PubHighQualityHVO{
	/**
	 * 表头主键
	 */
	private String pk_qualitygrade_h;
	/**
	 * 优质优价编码
	 */
	private String vqualitygradecode;
	/**
	 * 优质优价名称
	 */
	private String vqualitygradename;
	public String getPk_qualitygrade_h() {
		return pk_qualitygrade_h;
	}

	public void setPk_qualitygrade_h(String pk_qualitygrade_h) {
		this.pk_qualitygrade_h = pk_qualitygrade_h;
	}


	public String getVqualitygradecode() {
		return vqualitygradecode;
	}

	public void setVqualitygradecode(String vqualitygradecode) {
		this.vqualitygradecode = vqualitygradecode;
	}

	public String getVqualitygradename() {
		return vqualitygradename;
	}

	public void setVqualitygradename(String vqualitygradename) {
		this.vqualitygradename = vqualitygradename;
	}

	@Override
	public String getPKFieldName() {
		return "pk_qualitygrade_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_qualitygrade_h";
	}
	
	public QualityGradeHVO(){
		super();
	}
}
