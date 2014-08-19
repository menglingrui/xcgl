package nc.vo.xcgl.pub.stock;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * �ִ���VO
 * @author mlr
 *
 */
public class PubStockOnHandVO extends SuperVO{
	private static final long serialVersionUID = -8918426423509207720L;
    /**
     * ά��
     */
	/**
	 * �������ۣ�һ���Ǿ��۹�������������Ǿ���ֵΪ��pk_invmandoc��
	 */
	private String pk_father;
	/**
	 * ��˾
	 */
	private String pk_corp;
	/**
	 * �ֿ�
	 */
	private String pk_stordoc;
	/**
	 * ��λ
	 */
	private String pk_cargdoc;
	/**
	 * ѡ��
	 */
	private String pk_factory;
	/**
	 * ���
	 */
	private String pk_invmandoc;
	/**
	 * �������
	 */
	private String pk_invbasdoc;
	/**
	 * ���κ�
	 */
	private String vbatchcode;
	/**
	 * ����
	 */
	private String pk_stockonhand;
	
  	private String vdef1;
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	private String vdef6;
	private String vdef7;
	private String vdef8;
	private String vdef9;
	private String vdef10;
	
	
	/**
	 * ����
	 */
	private UFDouble nnum;
	/**
	 * 
	 */
	private UFDouble nassistnum;
	/**
	 * ������
	 */
	private UFDouble nlock;
	private UFDouble nreserve1;
	private UFDouble nreserve2;
	private UFDouble nreserve3;
	private UFDouble nreserve4;
	private UFDouble nreserve5;
	
	private UFDateTime ts;		
	
	private Integer dr;
	
	
	public String getPk_father() {
		return pk_father;
	}

	public void setPk_father(String pk_father) {
		this.pk_father = pk_father;
	}

	public UFDouble getNlock() {
		return nlock;
	}

	public void setNlock(UFDouble nlock) {
		this.nlock = nlock;
	}

	public String getPk_stockonhand() {
		return pk_stockonhand;
	}

	public void setPk_stockonhand(String pk_stockonhand) {
		this.pk_stockonhand = pk_stockonhand;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_stordoc() {
		return pk_stordoc;
	}

	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}

	public String getPk_cargdoc() {
		return pk_cargdoc;
	}

	public void setPk_cargdoc(String pk_cargdoc) {
		this.pk_cargdoc = pk_cargdoc;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}

	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}

	public String getVbatchcode() {
		return vbatchcode;
	}

	public void setVbatchcode(String vbatchcode) {
		this.vbatchcode = vbatchcode;
	}

	public String getVdef1() {
		return vdef1;
	}

	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}

	public String getVdef2() {
		return vdef2;
	}

	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}

	public String getVdef3() {
		return vdef3;
	}

	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}

	public String getVdef4() {
		return vdef4;
	}

	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}

	public String getVdef5() {
		return vdef5;
	}

	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}

	public String getVdef6() {
		return vdef6;
	}

	public void setVdef6(String vdef6) {
		this.vdef6 = vdef6;
	}

	public String getVdef7() {
		return vdef7;
	}

	public void setVdef7(String vdef7) {
		this.vdef7 = vdef7;
	}

	public String getVdef8() {
		return vdef8;
	}

	public void setVdef8(String vdef8) {
		this.vdef8 = vdef8;
	}

	public String getVdef9() {
		return vdef9;
	}

	public void setVdef9(String vdef9) {
		this.vdef9 = vdef9;
	}

	public String getVdef10() {
		return vdef10;
	}

	public void setVdef10(String vdef10) {
		this.vdef10 = vdef10;
	}

	public UFDouble getNnum() {
		return nnum;
	}

	public void setNnum(UFDouble nnum) {
		this.nnum = nnum;
	}

	public UFDouble getNassistnum() {
		return nassistnum;
	}

	public void setNassistnum(UFDouble nassistnum) {
		this.nassistnum = nassistnum;
	}

	public UFDouble getNreserve1() {
		return nreserve1;
	}

	public void setNreserve1(UFDouble nreserve1) {
		this.nreserve1 = nreserve1;
	}

	public UFDouble getNreserve2() {
		return nreserve2;
	}

	public void setNreserve2(UFDouble nreserve2) {
		this.nreserve2 = nreserve2;
	}

	public UFDouble getNreserve3() {
		return nreserve3;
	}

	public void setNreserve3(UFDouble nreserve3) {
		this.nreserve3 = nreserve3;
	}

	public UFDouble getNreserve4() {
		return nreserve4;
	}

	public void setNreserve4(UFDouble nreserve4) {
		this.nreserve4 = nreserve4;
	}

	public UFDouble getNreserve5() {
		return nreserve5;
	}

	public void setNreserve5(UFDouble nreserve5) {
		this.nreserve5 = nreserve5;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	@Override
	public String getPKFieldName() {
		return "pk_stockonhand";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "XCGL_STOCKONHAND";
	}
    /**
     * ����ִ�����Сά����������
     * @return
     */
	public String getKey() {
		StringBuffer str = new StringBuffer();
		for(String name:BillStockTool.getInstrance().getDef_Fields()){
			str.append(ZmPubTool.getString_NullAsTrimZeroLen(getAttributeValue(name)));
		}
		return str.toString();
		}
}
