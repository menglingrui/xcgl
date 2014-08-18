package nc.vo.xcgl.latecheckout;

import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
/**
 * 结账vo
 * @author mlr
 *
 */
public class  MonthAccountVO extends PubStockOnHandVO {
	
	private static final long serialVersionUID = -5548940439998270540L;
	private   String pk_monthaccount=null;//主键
	protected String cmonthid = null;//结账会计月id   期初主键
	protected String dyearmonth = null;//CHAR(7) 年月份  应该和结账月id 一致
	protected UFDate dstartdate = null;
	protected UFDate denddate = null;
		
	public UFDate getDstartdate() {
		return dstartdate;
	}

	public void setDstartdate(UFDate dstartdate) {
		this.dstartdate = dstartdate;
	}

	public UFDate getDenddate() {
		return denddate;
	}

	public void setDenddate(UFDate denddate) {
		this.denddate = denddate;
	}

	public String getCmonthid() {
		return cmonthid;
	}

	public void setCmonthid(String cmonthid) {
		this.cmonthid = cmonthid;
	}

	public String getDyearmonth() {
		return dyearmonth;
	}

	public void setDyearmonth(String dyearmonth) {
		this.dyearmonth = dyearmonth;
	}

	public String getPk_monthaccount() {
		return pk_monthaccount;
	}

	public void setPk_monthaccount(String pk_monthaccount) {
		this.pk_monthaccount = pk_monthaccount;
	}

	@Override
	public String getPKFieldName() {
		return "pk_monthaccount";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_monthaccount";
	}
}
