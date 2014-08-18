package nc.vo.xcgl.saleshareout;

import java.util.List;

import nc.itf.zmpub.pub.ISonVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.PubGeneralBVO;

/**
 * 销售出库矿区分摊计算表体VO
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class SaleShareoutBVO extends PubGeneralBVO implements ISonVO{
  /**
   * source billcode
   */
   private String ccode;
  
	/**
	 * 表体主键
	 * */
	private String pk_general_b;
	/**
	 * 主表主键
	 * */
	private String pk_general_h;
	/**
	 * 日期
	 */
	private UFDate ddate;
	/**
	 * 单位
	 */
	private String pk_measdoc;
	private List share1;
	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public List getShare1() {
		return share1;
	}

	public void setShare1(List share) {
		this.share1 = share;
	}

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
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

	@Override
	public String getPKFieldName() {
		return "pk_general_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_general_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_general_b";
	}

	public String getRowNumName() {
		
		return "crowno";
	}

	public List getSonVOS() {
		
		return share1;
	}

	public void setSonVOS(List list) {
		share1=list;
	}

}
