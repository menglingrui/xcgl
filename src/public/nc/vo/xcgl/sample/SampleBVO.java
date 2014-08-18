package nc.vo.xcgl.sample;

import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.PubSampleBVO;

/**
 * 送样单表体VO
 * @author yangtao
 * @date 2013-12-10 下午01:28:13
 */
public class SampleBVO extends PubSampleBVO{

	/**
	 * @date 2013-12-10下午01:27:12
	 */
	private static final long serialVersionUID = 5221158892783642170L;
	
	private String pk_sample_b;
	private String pk_sample;
	private String pk_invbasdoc;
	private String pk_invmandoc;
	/**
	 * 是否杂质
	 */
	private UFBoolean uimpurity;
	

	public UFBoolean getUimpurity() {
		return uimpurity;
	}

	public void setUimpurity(UFBoolean uimpurity) {
		this.uimpurity = uimpurity;
	}

	public String getPk_sample_b() {
		return pk_sample_b;
	}

	public void setPk_sample_b(String pk_sample_b) {
		this.pk_sample_b = pk_sample_b;
	}

	public String getPk_sample() {
		return pk_sample;
	}

	public void setPk_sample(String pk_sample) {
		this.pk_sample = pk_sample;
	}

    
	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}

	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
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
