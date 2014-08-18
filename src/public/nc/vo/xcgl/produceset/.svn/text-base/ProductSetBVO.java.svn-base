
package nc.vo.xcgl.produceset;
import java.util.List;

import nc.vo.xcgl.pub.bill.XCHYChildSuperVO;
import nc.vo.xcgl.pub.itf.ISonExtendVO;
/**
 * 生产流程设置表体
 * 创建日期:2013-12-11 16:31:44
 * @author mlr
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class ProductSetBVO extends XCHYChildSuperVO implements ISonExtendVO{
	/**
	 * 主键
	 */
	private String pk_produceset_b;
	/**
	 * 主表主键
	 */
	private String pk_produceset;
	/**
	 * 行号
	 */
	private String crowno;
	/**
	 * 生产作业顺序
	 */
	private Integer iordernum;
	/**
	 * 工序主键
	 */
	private String pk_process;	
	/**
	 * 投入设置
	 */
	private List produceinset1;
	/**
	 * 产出设置
	 */
	private List produceoutset1;
	/**
	 * 工艺顺序 0 第一道工艺；1第二道工艺；2第三道工艺;3重选工艺；
	 */
	private Integer itech;
	
	private String vmemo;	

	public Integer getItech() {
		return itech;
	}
	public void setItech(Integer itech) {
		this.itech = itech;
	}
	public List getProduceinset1() {
		return produceinset1;
	}
	public void setProduceinset1(List produceinset1) {
		this.produceinset1 = produceinset1;
	}
	public List getProduceoutset1() {
		return produceoutset1;
	}
	public void setProduceoutset1(List produceoutset1) {
		this.produceoutset1 = produceoutset1;
	}
	public String getPk_produceset_b() {
		return pk_produceset_b;
	}
	public void setPk_produceset_b(String pk_produceset_b) {
		this.pk_produceset_b = pk_produceset_b;
	}
	public String getPk_process() {
		return pk_process;
	}
	public void setPk_process(String pk_process) {
		this.pk_process = pk_process;
	}
	public String getVmemo() {
		return vmemo;
	}
	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}
	public String getCrowno() {
		return crowno;
	}
	public void setCrowno(String crowno) {
		this.crowno = crowno;
	}
	public String getPk_produceset() {
		return pk_produceset;
	}
	public void setPk_produceset(String pk_produceset) {
		this.pk_produceset = pk_produceset;
	}

	public Integer getIordernum() {
		return iordernum;
	}
	public void setIordernum(Integer iordernum) {
		this.iordernum = iordernum;
	}
	@Override
	public String getPKFieldName() {
		return "pk_produceset_b";
	}
	@Override
	public String getParentPKFieldName() {
		return "pk_produceset";
	}
	@Override
	public String getTableName() {
		return "xcgl_produceset_b";
	}
	public String getRowNumName() {
		return "crowno";
	}
	public List getSonVOS() {
		return produceinset1;
	}
	public void setSonVOS(List list) {
		this.produceinset1=list;
	}
	public String getRowNumName1() {
		return "crowno";
	}
	public List getSonVOS1() {
		return produceoutset1;
	}
	public void setSonVOS1(List list) {
		this.produceoutset1=list;
	}
} 
