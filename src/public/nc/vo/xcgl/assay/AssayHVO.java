package nc.vo.xcgl.assay;

import nc.itf.zmpub.pub.ISonVOH;
import nc.vo.xcgl.pub.bill.PubSampleHVO;

/**
 *��˵���� ���鵥��ͷvo
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-20����09:34:16
 */
public class AssayHVO extends PubSampleHVO implements ISonVOH{
	private static final long serialVersionUID = 1L;
	/**
	 *���� 
	 */
	private String pk_sample;
	/**
	 *ȡ����λ 
	 */
    private String pk_minarea;
	  /**
     * ����Ա2
     */
	private String  pk_analystple1;
	  /**
     * ����Ա3
     */
	private String  pk_analystple2;
	public String getPk_analystple1() {
		return pk_analystple1;
	}

	public void setPk_analystple1(String pk_analystple1) {
		this.pk_analystple1 = pk_analystple1;
	}

	public String getPk_analystple2() {
		return pk_analystple2;
	}

	public void setPk_analystple2(String pk_analystple2) {
		this.pk_analystple2 = pk_analystple2;
	}

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	public String getSonClass() {
		
		return "nc.vo.xcgl.assay.AssayBBVO";
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

	public String getPk_sample() {
		return pk_sample;
	}

	public void setPk_sample(String pk_sample) {
		this.pk_sample = pk_sample;
	}

}
