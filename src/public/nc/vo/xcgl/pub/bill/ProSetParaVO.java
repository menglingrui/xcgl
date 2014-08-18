package nc.vo.xcgl.pub.bill;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
/**
 * 生产流程设置参数VO
 * @author mlr
 */
public class ProSetParaVO extends SuperVO{
	private static final long serialVersionUID = 2709281222060707419L;	
	/**
	 * 关联原矿矿石
	 */
	private String pk_invmandoc;
	/**
	 * 关联原矿矿石
	 */
	private String pk_invbasdoc;
	
	private String pk_corp;
	/**
	 * 选厂主键
	 */
	private String pk_factory;
	/**
	 * 生产线主键
	 */
	private String pk_beltline;
	/**
	 * 工序主键
	 */
	private String pk_process;
	/**
	 * 班次主键
	 */
	private String pk_classorder;
	/**
	 * 单据类型
	 */
	private String pk_sourcetype;
	/**
	 * 是否投入设置
	 */
	private boolean isProInSet;
	/**
	 * 是否产出设置
	 */
	private boolean isProOutSet;
	/**
	 * 是否原矿
	 * @return
	 */
	private boolean isPowder;
	/**
	 * 单据日期
	 * @return
	 */
    private UFDate dbilldate;
    /**
     * 矿区
     * @return
     */
    private String pk_minarea;
    
    private String pk_minetype;
    
    
    
    
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

	public String getPk_minetype() {
		return pk_minetype;
	}

	public void setPk_minetype(String pk_minetype) {
		this.pk_minetype = pk_minetype;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
	}

	public UFDate getDbilldate() {
		return dbilldate;
	}

	public void setDbilldate(UFDate dbilldate) {
		this.dbilldate = dbilldate;
	}

	public boolean isPowder() {
		return isPowder;
	}

	public void setPowder(boolean isPowder) {
		this.isPowder = isPowder;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public boolean isProInSet() {
		return isProInSet;
	}

	public void setProInSet(boolean isProInSet) {
		this.isProInSet = isProInSet;
	}

	public boolean isProOutSet() {
		return isProOutSet;
	}

	public void setProOutSet(boolean isProOutSet) {
		this.isProOutSet = isProOutSet;
	}

	public String getPk_sourcetype() {
		return pk_sourcetype;
	}

	public void setPk_sourcetype(String pk_sourcetype) {
		this.pk_sourcetype = pk_sourcetype;
	}

	public String getPk_beltline() {
		return pk_beltline;
	}

	public void setPk_beltline(String pk_beltline) {
		this.pk_beltline = pk_beltline;
	}

	public String getPk_process() {
		return pk_process;
	}

	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
	}

	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
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
}
