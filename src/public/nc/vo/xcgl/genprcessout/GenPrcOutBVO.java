
package nc.vo.xcgl.genprcessout;
	
import nc.vo.xcgl.pub.bill.PubGeneralBVO;
	
/**
 * ԭ��ӹ��������vo
 * ��������:2013-12-10 16:59:38
 * @author jay
 */
@SuppressWarnings("serial")
public class GenPrcOutBVO extends PubGeneralBVO {
	/**
	 * ���ݳɱ�Ҫ�� ���õĳɱ������� ����ֶ�����
	 */
    public static String costdrivervale="num";	
    /**
     * ���ݶ���������Ľ��  ����ֶ�����
     */
    public static String costallonmy="endmny";	
	/**
	 * ��������
	 * */
	private String pk_general_b;
	/**
	 * ��������
	 * */
	private String pk_general_h;
	/**
	 * ����pk_general_h��Getter����.
	 * ��������:2013-12-10 16:59:38
	 * @return String
	 */
	public String getPk_general_h () {
		return pk_general_h;
	}   
	/**
	 * ����pk_general_h��Setter����.
	 * ��������:2013-12-10 16:59:38
	 * @param newPk_general_h String
	 */
	public void setPk_general_h (String newPk_general_h ) {
	 	this.pk_general_h = newPk_general_h;
	} 	  
	public String getPk_general_b() {
		return pk_general_b;
	}
	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2013-12-10 16:59:38
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_general_h";
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2013-12-10 16:59:38
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_general_b";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2013-12-10 16:59:38
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "xcgl_general_b";
	}    
      
} 
