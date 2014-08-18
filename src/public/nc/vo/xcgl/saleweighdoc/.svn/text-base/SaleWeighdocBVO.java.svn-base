package nc.vo.xcgl.saleweighdoc;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.PubWeighdocBVO;
/**
 * 销售过磅登记表体VO
 * 创建日期:2013-12-16 11:15:56
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SaleWeighdocBVO extends PubWeighdocBVO {
	/**
	 * 主表主键
	 */
	private String pk_weighdoc;
	/**
	 * 字表主键
	 */
	private String pk_weighdoc_b;
	/**
	 * 水分
	 */
	private UFDouble nwatercontent;
	/**
	 * 干量
	 */
	private UFDouble ndryamount;
	/**
	 * 日期
	 */
	private UFDate dsaledate;
	/**
	 * 是否已分摊出库
	 */
	private UFBoolean ushareout;
	/**
	 * add by Jay
	 * 车号手动录入
	 */
	private String carno;
//	/**
//	 *是否被参照过 
//	 */
//	private UFBoolean isref;
//	public UFBoolean getIsref() {
//		return isref;
//	}
//
//	public void setIsref(UFBoolean isref) {
//		this.isref = isref;
//	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public UFDouble getNwatercontent() {
		return nwatercontent;
	}

	public void setNwatercontent(UFDouble nwatercontent) {
		this.nwatercontent = nwatercontent;
	}

	public UFDouble getNdryamount() {
		return ndryamount;
	}

	public void setNdryamount(UFDouble ndryamount) {
		this.ndryamount = ndryamount;
	}

	public UFDate getDsaledate() {
		return dsaledate;
	}

	public void setDsaledate(UFDate dsaledate) {
		this.dsaledate = dsaledate;
	}

	public UFBoolean getUshareout() {
		return ushareout;
	}

	public void setUshareout(UFBoolean ushareout) {
		this.ushareout = ushareout;
	}

	public String getPk_weighdoc() {
		return pk_weighdoc;
	}

	public void setPk_weighdoc(String pk_weighdoc) {
		this.pk_weighdoc = pk_weighdoc;
	}

	public String getPk_weighdoc_b() {
		return pk_weighdoc_b;
	}

	public void setPk_weighdoc_b(String pk_weighdoc_b) {
		this.pk_weighdoc_b = pk_weighdoc_b;
	}

	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2013-12-09 13:43:01
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_weighdoc";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2013-12-09 13:43:01
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_weighdoc_b";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2013-12-09 13:43:01
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_WEIGHDOC_B";
	}    
      	
} 
