package nc.vo.xcgl.latecheckout;

import nc.ui.pub.ClientEnvironment;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
import nc.vo.zmpub.pub.bill.HYChildSuperVO;

/**
 * 月末结账
 * @author mlr
 *
 */
public class CloseMonBodyVO extends XCHYChildSuperVO {
	private static final long serialVersionUID = 1L;	
	public static String[] sort_fields = new String[]{"vmonth"}; 
	private UFBoolean flag;//是否选中
	private String vmonth;//会计月 	
	private String pk_closemon_b;// ID
	private String pk_closemon;//	主表id	
	private UFDate dstartdate;//	开始日期
	private UFDate denddate;//	截止日期
	private UFBoolean isaccount;//	是否结账	
	private String pk_accperiodscheme;//会计方案id
	private String pk_accperiodmonth;//会计月id	
	private String pk_corp;//	
    public void validataOnSave() throws ValidationException{
		if(PuPubVO.getString_TrimZeroLenAsNull(getPk_accperiodmonth())==null){
			throw new ValidationException("会计月为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(getPk_accperiodscheme())==null){
			throw new ValidationException("期间方案为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(getPk_corp())==null){
			setPk_corp(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		}
    }

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

	public UFBoolean getIsaccount() {
		return isaccount;
	}

	public void setIsaccount(UFBoolean isaccount) {
		this.isaccount = isaccount;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	@Override
	public String getPKFieldName() {
		return "pk_closemon_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_closemon";
	}

	@Override
	public String getTableName() {
		return "xcgl_closemon_b";
	}

	public UFBoolean getFlag() {
		return flag;
	}

	public void setFlag(UFBoolean flag) {
		this.flag = flag;
	}

	public String getVmonth() {
		return vmonth;
	}

	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}

	public String getPk_accperiodscheme() {
		return pk_accperiodscheme;
	}

	public void setPk_accperiodscheme(String pk_accperiodscheme) {
		this.pk_accperiodscheme = pk_accperiodscheme;
	}

	public String getPk_accperiodmonth() {
		return pk_accperiodmonth;
	}

	public void setPk_accperiodmonth(String pk_accperiodmonth) {
		this.pk_accperiodmonth = pk_accperiodmonth;
	}


	public String getPk_closemon() {
		return pk_closemon;
	}

	public void setPk_closemon(String pk_closemon) {
		this.pk_closemon = pk_closemon;
	}

	public static String[] getSort_fields() {
		return sort_fields;
	}

	public static void setSort_fields(String[] sort_fields) {
		CloseMonBodyVO.sort_fields = sort_fields;
	}

	public String getPk_closemon_b() {
		return pk_closemon_b;
	}

	public void setPk_closemon_b(String pk_closemon_b) {
		this.pk_closemon_b = pk_closemon_b;
	}


}
