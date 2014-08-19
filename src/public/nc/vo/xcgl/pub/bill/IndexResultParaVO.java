package nc.vo.xcgl.pub.bill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;

public class IndexResultParaVO extends SuperVO{
	private static final long serialVersionUID = 7144513041776091307L;
	private String pk_corp;
	
	/**
	 * 关联原矿矿石
	 */
	private String pk_invmandoc1;
	
	private String pk_invbasdoc1;
	
	/**
	 * add by whl
	 * 矿区主键
	 */
	private String pk_minarea;
	/**
     * 选厂主键
	*/
	private String pk_factory;
	/**
	 * 生产线主键
	 */
	private String pk_beltline;
	/**
	 * 班次主键
	 */
	private String pk_classorder;
	/**
	 * 单据日期
	 * @return
	 */
    private UFDate dbilldate;
	/**
	 * 存货id
	 */
	private String pk_invmandoc;
	/**
	 * 是否水分
	 * @return
	 */
	private UFBoolean iswater;
	/**
	 * repeat select powder
	 */
	private UFBoolean isrepeatsel;
	
	private String pk_minetype;
	
	
	
	public String getPk_invmandoc1() {
		return pk_invmandoc1;
	}

	public void setPk_invmandoc1(String pk_invmandoc1) {
		this.pk_invmandoc1 = pk_invmandoc1;
	}

	public String getPk_invbasdoc1() {
		return pk_invbasdoc1;
	}

	public void setPk_invbasdoc1(String pk_invbasdoc1) {
		this.pk_invbasdoc1 = pk_invbasdoc1;
	}

	public String getPk_minetype() {
		return pk_minetype;
	}

	public void setPk_minetype(String pk_minetype) {
		this.pk_minetype = pk_minetype;
	}

	public UFBoolean getIsrepeatsel() {
		return isrepeatsel;
	}

	public void setIsrepeatsel(UFBoolean isrepeatsel) {
		this.isrepeatsel = isrepeatsel;
	}

	public UFBoolean getIswater() {
		return iswater;
	}

	public void setIswater(UFBoolean iswater) {
		this.iswater = iswater;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_beltline() {
		return pk_beltline;
	}

	public void setPk_beltline(String pk_beltline) {
		this.pk_beltline = pk_beltline;
	}

	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}

	public UFDate getDbilldate() {
		return dbilldate;
	}

	public void setDbilldate(UFDate dbilldate) {
		this.dbilldate = dbilldate;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	@Override
	public String getPKFieldName() {
		return null;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

}
