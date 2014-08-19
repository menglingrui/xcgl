package nc.vo.xcgl.saleshareout;

import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.PubGeneralBVO;

public class ShareHVO extends PubGeneralBVO{
	private static final long serialVersionUID = 1444268994154354135L;
	private UFDouble ngrossweight;

	  /**
	   * source billcode
	   */
	 private String ccode;
	/**
	 * 主表主键
	 */
	private String pk_general_h;
	/**
	 * 日期
	 */
	private UFDate ddate;
	/**
	 * 单位
	 */
	private String pk_measdoc;
	
	private String pk_general_b;
	
	
	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public UFDouble getNgrossweight() {
		return ngrossweight;
	}

	public void setNgrossweight(UFDouble ngrossweight) {
		this.ngrossweight = ngrossweight;
	}
	public String getPk_general_b() {
		return pk_general_b;
	}

	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}
	
	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
	}

	public UFDate getDdate() {
		return ddate;
	}

	public void setDdate(UFDate ddate) {
		this.ddate = ddate;
	}

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	} 
	private String pk_share_h;

	public String getPk_share_h() {
		return pk_share_h;
	}

	public void setPk_share_h(String pk_share_h) {
		this.pk_share_h = pk_share_h;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		
		return "pk_share_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_share_h";
	}
	

}
