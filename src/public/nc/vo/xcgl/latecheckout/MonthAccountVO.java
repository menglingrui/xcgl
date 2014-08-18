package nc.vo.xcgl.latecheckout;

import nc.vo.pub.lang.UFDate;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
/**
 * ����vo
 * @author mlr
 *
 */
public class  MonthAccountVO extends PubStockOnHandVO {
	
	private static final long serialVersionUID = -5548940439998270540L;
	private   String pk_monthaccount=null;//����
	protected String cmonthid = null;//���˻����id   �ڳ�����
	protected String dyearmonth = null;//CHAR(7) ���·�  Ӧ�úͽ�����id һ��
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
