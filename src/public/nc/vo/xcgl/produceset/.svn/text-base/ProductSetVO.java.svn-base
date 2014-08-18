
package nc.vo.xcgl.produceset;	
import nc.vo.xcgl.pub.bill.XCHYHeadSuperVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
/**
 * 生产流程设置表头
 * 创建日期:2013-12-11 16:31:44
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ProductSetVO extends XCHYHeadSuperVO implements ISonExtendVOH{
    /**
     * 主键
     */
	private String pk_produceset;
	/**
	 * 生产线主键
	 */
	private String pk_beltline;
	/**
	 * 工艺主键
	 */
	private String pk_technology;
    /**
     * 备注
     */
	private String vmemo;

	public String getPk_produceset() {
		return pk_produceset;
	}

	public void setPk_produceset(String pk_produceset) {
		this.pk_produceset = pk_produceset;
	}

	public String getPk_beltline() {
		return pk_beltline;
	}

	public void setPk_beltline(String pk_beltline) {
		this.pk_beltline = pk_beltline;
	}

	public String getPk_technology() {
		return pk_technology;
	}

	public void setPk_technology(String pk_technology) {
		this.pk_technology = pk_technology;
	}

	public String getVmemo() {
		return vmemo;
	}

	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}

	@Override
	public String getPKFieldName() {
		return "pk_produceset";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_produceset";
	}
	
    /**
     * 产出设置
     */
	public String getSonClass1() {
		return "nc.vo.xcgl.produceset.ProductSetBB2VO";
	}
    /**
     * 投入设置
     */
	public String getSonClass() {
		return "nc.vo.xcgl.produceset.ProductSetBB1VO";
	}  

} 
