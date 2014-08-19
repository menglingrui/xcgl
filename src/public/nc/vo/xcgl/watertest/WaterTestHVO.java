package nc.vo.xcgl.watertest;

import nc.vo.xcgl.pub.bill.PubSampleHVO;

/**
 * 生产加工水分化验单表头vo
 * @author Jay
 *
 */

public class WaterTestHVO extends PubSampleHVO{
	private static final long serialVersionUID = 1L;
	/**
	 * 主表主键
	 */
    private String pk_sample;

	public String getPk_sample() {
		return pk_sample;
	}

	public void setPk_sample(String pk_sample) {
		this.pk_sample = pk_sample;
	}

	@Override
	public String getPKFieldName() {

		return "pk_sample";
	}

	@Override
	public String getParentPKFieldName() {
		
		return null;
	}

	@Override
	public String getTableName() {

		return "xcgl_sample";
	}

}
