package nc.vo.xcgl.assayres;

import nc.vo.xcgl.pub.bill.PubSampleBBVO;

/**
 *��˵����
 *
 *@version 1.0   
 *����ʱ�䣺2013-12-26����04:57:25
 */
public class AssayResBBVO extends PubSampleBBVO{
	private static final long serialVersionUID = 1L;
	/**
	 * ��������
	 */
	private String pk_sample;
	/**
     * �ӱ�����
	 */
	private String pk_sample_b;
	/**
	 * �������
	 */
	private String pk_sample_bb;
	
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

	public String getPk_sample_bb() {
		return pk_sample_bb;
	}

	public void setPk_sample_bb(String pk_sample_bb) {
		this.pk_sample_bb = pk_sample_bb;
	}

	@Override
	public String getPKFieldName() {
		
		return "pk_sample_bb";
	}

	@Override
	public String getParentPKFieldName() {
		
		return "pk_sample_b";
	}

	@Override
	public String getTableName() {
		
		return "xcgl_sample_bb";
	}

}
