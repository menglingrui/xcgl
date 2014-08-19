package nc.vo.xcgl.pub.bill;

import java.util.List;

import nc.vo.pub.SuperVO;
import nc.vo.xcgl.produceset.ProductSetBB1VO;
import nc.vo.xcgl.produceset.ProductSetBB2VO;

/**
 * 精粉产量计算VO
 * 属于公共VO
 *  
 * 如果是原矿只有投入信息和其他指标信息
 * 
 * 如果是精粉只有产出信息、主指标信息、副指标信息
 * 
 * 如果是尾矿只有产出信息、主指标信息、副指标信息
 * 
 * @author mlr
 *
 */
public class CalYieldVO extends SuperVO{

	private static final long serialVersionUID = 4943482312338681384L;
    /**
     * 投入信息->原矿、材料
     */
	private ProductSetBB1VO insetvo;
	/**
	 * 产出信息->精粉、尾矿
	 */
	private ProductSetBB2VO outsetvo;
	/**
	 * 二次重选后的->精粉
	 */
	private List<CalYieldVO> outsetvos;
    /**
     * 主指标信息->主金属元素
     */
	private CalYieldNumVO  mainnumvo;
	 /**
     * 副指标信息->副金属元素
     */
	private List<CalYieldNumVO>  numList;	
	 /**
     * 其他指标信息->其他金属元素
     */
	private List<CalYieldNumVO>  othernumList;
		

	public List<CalYieldVO> getOutsetvos() {
		return outsetvos;
	}
	public void setOutsetvos(List<CalYieldVO> outsetvos) {
		this.outsetvos = outsetvos;
	}
	public List<CalYieldNumVO> getOthernumList() {
		return othernumList;
	}
	public void setOthernumList(List<CalYieldNumVO> othernumList) {
		this.othernumList = othernumList;
	}
	public ProductSetBB1VO getInsetvo() {
		return insetvo;
	}
	public void setInsetvo(ProductSetBB1VO insetvo) {
		this.insetvo = insetvo;
	}
	public ProductSetBB2VO getOutsetvo() {
		return outsetvo;
	}
	public void setOutsetvo(ProductSetBB2VO outsetvo) {
		this.outsetvo = outsetvo;
	}
	public CalYieldNumVO getMainnumvo() {
		return mainnumvo;
	}
	public void setMainnumvo(CalYieldNumVO mainnumvo) {
		this.mainnumvo = mainnumvo;
	}
	public List<CalYieldNumVO> getNumList() {
		return numList;
	}
	public void setNumList(List<CalYieldNumVO> numList) {
		this.numList = numList;
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
