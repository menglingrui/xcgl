package nc.vo.xcgl.assayres;

import nc.itf.zmpub.pub.ISonVOH;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.PubSampleHVO;

/**
 *类说明：化验结果表头vo
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-26下午04:50:32
 */
public class AssayResHVO extends PubSampleHVO implements ISonVOH{

	private static final long serialVersionUID = 1L;
    private String pk_sample;
    /**
     *是否水分
     *默认不是 
     */
    private UFBoolean uiswater;
    
	public UFBoolean getUiswater() {
		return uiswater;
	}

	public void setUiswater(UFBoolean uiswater) {
		this.uiswater = uiswater;
	}

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

	public String getSonClass() {
		
		return "nc.vo.xcgl.assayres.AssayResBBVO";
	}

}
