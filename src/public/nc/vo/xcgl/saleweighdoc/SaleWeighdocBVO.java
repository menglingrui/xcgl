package nc.vo.xcgl.saleweighdoc;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.xcgl.pub.bill.PubWeighdocBVO;
/**
 * ���۹����ǼǱ���VO
 * ��������:2013-12-16 11:15:56
 * @author ddk
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SaleWeighdocBVO extends PubWeighdocBVO {
	/**
	 * ��������
	 */
	private String pk_weighdoc;
	/**
	 * �ֱ�����
	 */
	private String pk_weighdoc_b;
	/**
	 * ˮ��
	 */
	private UFDouble nwatercontent;
	/**
	 * ����
	 */
	private UFDouble ndryamount;
	/**
	 * ����
	 */
	private UFDate dsaledate;
	/**
	 * �Ƿ��ѷ�̯����
	 */
	private UFBoolean ushareout;
	/**
	 * add by Jay
	 * �����ֶ�¼��
	 */
	private String carno;
//	/**
//	 *�Ƿ񱻲��չ� 
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
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-12-09 13:43:01
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_weighdoc";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-12-09 13:43:01
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_weighdoc_b";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-12-09 13:43:01
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "XCGL_WEIGHDOC_B";
	}    
      	
} 
