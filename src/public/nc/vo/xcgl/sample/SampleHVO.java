package nc.vo.xcgl.sample;

import nc.vo.xcgl.pub.bill.PubSampleHVO;

/**
 * 送样单表头VO
 * @author yangtao
 * @date 2013-12-10 下午01:28:38
 */
public class SampleHVO extends PubSampleHVO{

	/**
	 * @date 2013-12-10下午02:43:10
	 */
	private static final long serialVersionUID = -7387607547324460284L;
	
	
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
	public String getTableName() {
	
		return "xcgl_sample";
	}

	@Override
	public String getParentPKFieldName() {

		return null;
	}

}
