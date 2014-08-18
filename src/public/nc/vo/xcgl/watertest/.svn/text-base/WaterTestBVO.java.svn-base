package nc.vo.xcgl.watertest;

import nc.vo.xcgl.pub.bill.PubSampleBVO;

/**
 * 生产加工水分化验单表头vo
 * @author Jay
 *
 */
public class WaterTestBVO extends PubSampleBVO{
	private static final long serialVersionUID = 1L;
	/**
	 * 主表主键
	 */
    private String pk_sample;
    /**
     * 字表主键
     */
    private String pk_sample_b;
	public String getPk_sample() {
		return pk_sample;
	}

	public void setPk_sample(String pk_sample) {
		this.pk_sample = pk_sample;
	}

	public String getPk_sample_b() {
		return pk_sample_b;
	}

	public void setPk_sample_b(String pk_sample_b) {
		this.pk_sample_b = pk_sample_b;
	}

	@Override
	public String getPKFieldName() {

		return "pk_sample_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_sample";
	}

	@Override
	public String getTableName() {
		return "xcgl_sample_b";
	}

}
