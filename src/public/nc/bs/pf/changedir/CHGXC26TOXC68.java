package nc.bs.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * ������ⵥ���������ĺ�̨������
 * ��̨������
 * @author Jay
 *
 */
public class CHGXC26TOXC68 extends  nc.bs.pf.change.VOConversion{
	public CHGXC26TOXC68(){
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
				"H_pk_factory->H_pk_factory",//ѡ��
				"H_pk_beltline->H_pk_beltline",//������
				"H_pk_minarea->H_pk_minarea",//����--ȡ����λ
				"H_pk_deptdoc->H_pk_deptdoc",
				"H_pk_stordoc->H_pk_stordoc",//�ֿ�
				
				//"H_dweighdate->H_dbilldate",
				"H_dbilldate->H_dbilldate",//��������
				"H_pk_busitype->H_pk_busitype",//ҵ������
                "H_pk_resetype->H_pk_resetype",//�շ����
                "B_crowno->B_crowno",
				"B_pk_invmandoc->B_pk_invmandoc",
				"B_pk_invbasdoc->B_pk_invbasdoc",
				"B_pk_father->B_pk_father",
				"B_vlastbillcode->H_vbillno",
				"B_vlastbillid->B_pk_general_h",
				"B_vlastbillrowid->B_pk_general_b",
				"B_vlastbilltype->H_pk_billtype",
				"B_csourcebillcode->H_vbillno",
				"B_vsourcebillid->B_pk_general_h",
				"B_vsourcebillrowid->B_pk_general_b",
				"B_vsourcebilltype->H_pk_billtype",
				"B_ndryweight->B_ndryweight",//��������
				"B_nwetweight->B_ndryweight",//��������			
				"B_vcrowno->B_vcrowno",
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
