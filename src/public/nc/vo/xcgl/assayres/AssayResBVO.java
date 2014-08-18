package nc.vo.xcgl.assayres;

import java.util.List;

import nc.itf.zmpub.pub.ISonVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.PubSampleBVO;

/**
 *��˵��������������vo
 *@author jay
 *@version 1.0   
 *����ʱ�䣺2013-12-26����04:51:03
 */
public class AssayResBVO extends PubSampleBVO implements ISonVO{
	private static final long serialVersionUID = 1L;
   /**
    * �ӱ�����
    */
	private String pk_sample_b;
	/**
	 * ��������
	 */
    private String pk_sample;
    /**
	 * �Ƿ�����
	 */
	private UFBoolean uimpurity;
    public UFBoolean getUimpurity() {
		return uimpurity;
	}

	public void setUimpurity(UFBoolean uimpurity) {
		this.uimpurity = uimpurity;
	}

	private List assayquota1;
    
	public List getAssayquota1() {
		return assayquota1;
	}

	public void setAssayquota1(List assayquota1) {
		this.assayquota1 = assayquota1;
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

	public String getRowNumName() {
		
		return "crowno";
	}

	public List getSonVOS() {
		
		return assayquota1;
	}

	public void setSonVOS(List list) {
		assayquota1=list;
	}

}
