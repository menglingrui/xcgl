package nc.vo.xcgl.assay;

import java.util.List;

import nc.itf.zmpub.pub.ISonVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.xcgl.pub.bill.PubSampleBVO;

/**
 *类说明：化验单表体vo
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-20上午09:34:39
 */
public class AssayBVO extends PubSampleBVO implements ISonVO{
	private static final long serialVersionUID = 1L;
	/**
	 * 主计量单位
	 */
	 private String pk_measdoc;
		/**
		 *主表主键 
		 */
		private String pk_sample;
		/**
		 *子表主键 
		 */
		private String pk_sample_b;
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
	private List vindexentry1;
	
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
	public List getVindexentry1() {
		return vindexentry1;
	}
	public void setVindexentry1(List vindexentry) {
		this.vindexentry1 = vindexentry;
	}
	public String getPk_measdoc() {
		return pk_measdoc;
	}
	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}
	public String getRowNumName() {
		
		return "crowno";
	}
	public List getSonVOS() {
		
		return vindexentry1;
	}
	public void setSonVOS(List list) {
		vindexentry1=list;
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
