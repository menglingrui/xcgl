package nc.vo.xcgl.pub.bill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
/**
 * ָ�궨�����
 * @author mlr
 *
 */
public class IndexParaVO extends SuperVO{
	private static final long serialVersionUID = -7875071037668219425L;
	/**
	 * ����ԭ���ʯ
	 */
	private String pk_invmandoc1;
	
	private String pk_invbasdoc1;
	/**
	 * ��˾
	 */
	private String pk_corp;
	/**
	 * Ҫ����ľ��ۡ�β��ԭ��Ĵ����������
	 */
	private String pk_invmandoc;
	/**
	 * �����޶�����(��ǰ�ǿ��������ڸ�Ϊ����)
	 */
    private String pk_minarea;
    /**
     * �Ƿ�����ִ���
     */
    private UFBoolean isstandingcrop;
    /**
     * add by jay
     * ���˷���־
     *  
     */
    private UFBoolean isclose;//�Ƿ���
   
    
    
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

	public UFBoolean getIsclose() {
		return isclose;
	}

	public void setIsclose(UFBoolean isclose) {
		this.isclose = isclose;
	}

	public UFBoolean getIsstandingcrop() {
		return isstandingcrop;
	}

	public void setIsstandingcrop(UFBoolean isstandingcrop) {
		this.isstandingcrop = isstandingcrop;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_invmandoc() {
		return pk_invmandoc;
	}

	public void setPk_invmandoc(String pk_invmandoc) {
		this.pk_invmandoc = pk_invmandoc;
	}

	public String getPk_minarea() {
		return pk_minarea;
	}

	public void setPk_minarea(String pk_minarea) {
		this.pk_minarea = pk_minarea;
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