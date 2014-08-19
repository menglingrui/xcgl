package nc.vo.xcgl.pub.bill;

import java.util.List;

import nc.vo.pub.SuperVO;
import nc.vo.xcgl.produceset.ProductSetBB1VO;
import nc.vo.xcgl.produceset.ProductSetBB2VO;

/**
 * ���۲�������VO
 * ���ڹ���VO
 *  
 * �����ԭ��ֻ��Ͷ����Ϣ������ָ����Ϣ
 * 
 * ����Ǿ���ֻ�в�����Ϣ����ָ����Ϣ����ָ����Ϣ
 * 
 * �����β��ֻ�в�����Ϣ����ָ����Ϣ����ָ����Ϣ
 * 
 * @author mlr
 *
 */
public class CalYieldVO extends SuperVO{

	private static final long serialVersionUID = 4943482312338681384L;
    /**
     * Ͷ����Ϣ->ԭ�󡢲���
     */
	private ProductSetBB1VO insetvo;
	/**
	 * ������Ϣ->���ۡ�β��
	 */
	private ProductSetBB2VO outsetvo;
	/**
	 * ������ѡ���->����
	 */
	private List<CalYieldVO> outsetvos;
    /**
     * ��ָ����Ϣ->������Ԫ��
     */
	private CalYieldNumVO  mainnumvo;
	 /**
     * ��ָ����Ϣ->������Ԫ��
     */
	private List<CalYieldNumVO>  numList;	
	 /**
     * ����ָ����Ϣ->��������Ԫ��
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
