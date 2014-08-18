package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * ���۹����Ǽǵ����۽���
 * @author ddk
 *
 */
public class CHGXC25TOXC66 extends  nc.bs.pf.change.VOConversion{
	/**
	 * ���캯��
	 */
	public CHGXC25TOXC66(){
		super();
	}
	/**
	 * ��ý��������� ȫ����
	 * @return java.lang.String
	 */
	public String getAfterClassName() {
		return null;
	}
	/**
	 * ��ý��������� ȫ����
	 * @return java.lang.String
	 */
	public String getOtherClassName() {
		return null;
	}
	/**
	 * ���ؽ�������ö��ConversionPolicyEnum��Ĭ��Ϊ������Ŀ-������Ŀ
	 * @return ConversionPolicyEnum
	 * @since 5.5
	 */
	public ConversionPolicyEnum getConversionPolicy() {
		return ConversionPolicyEnum.BILLITEM_BILLITEM;
	}
	
	/**
	 * ���ӳ�����͵Ľ�������
	 * @return java.lang.String[]
	 */
	public String[] getField() {
		return new String[] {
				"H_pk_corp->H_pk_corp",
				"H_pk_invmandoc->B_pk_invmandoc",
				"H_pk_invbasdoc->B_pk_invbasdoc",
				"H_pk_vehicle->B_carno",
				"B_namount->H_nreserve4",
				"B_pk_invmandoc->B_pk_invmandoc",
				"B_pk_invbasdoc->B_pk_invbasdoc",
//				"B_pk_measdoc->B_pk_measdoc",
				"H_custid->H_custid",
				"H_ccustbasid->H_custbasid",//���̻���
				"B_vreserve1->B_carno",				
				"B_nreserve2->B_nreserve2",//
				"B_ureserve1->B_ureserve1",
				
				"B_vlastbillcode->H_vbillno",
				"B_vlastbillid->H_pk_weighdoc",
				
				"B_vlastbillrowid->B_pk_weighdoc_b",
				"B_vlastbilltype->H_pk_billtype",
    			"B_csourcebillcode->B_vlastbillcode",
				"B_vsourcebillid->B_vsourcebillid",
				"B_vsourcebillrowid->B_vsourcebillrowid",
				"B_vsourcebilltype->B_vsourcebilltype",
		};
	}
	
	/**
	 * ��ø�ֵ���͵Ľ�������
	 * @return java.lang.String[]
	 */
	public String[] getAssign() {
		return null;
	}
	/**
	 * ��ù�ʽ���͵Ľ�������
	 * @return java.lang.String[]
	 */
	public String[] getFormulas() {
		return null;
	}
	/**
	 * �����û��Զ��庯��
	 */
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}