package nc.bs.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * ���۽��㵥�����۽������
 * @author ddk	
 *
 */
public class CHGXC66TOXC67 extends  nc.bs.pf.change.VOConversion{
	public CHGXC66TOXC67(){
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
				"H_pk_busitype->H_pk_busitype",
				"H_pk_corp->H_pk_corp",
				"H_vemployeeid->H_vemployeeid",//��Ŀ����	vemployeeid��Ŀ����	vemployeeid
				"H_pk_deptdoc->H_pk_deptdoc",
				"H_vmemo->H_vmemo",
				"H_dbilldate->H_dbilldate",
//				"H_vbillstatus->H_vbillstatus",
				"H_pk_vehicle->H_pk_vehicle",//��������
				"H_pk_invmandoc->H_pk_invmandoc",//�����������
				"H_pk_invbasdoc->H_pk_invbasdoc",//�����������
				"H_custid->H_custid",//���̹�������
				"H_custbasid->H_custbasid",//���̻�������
				"H_dshipdate->H_dshipdate",//��������
				
				"B_crowno->B_crowno",
				"B_vmemo->B_vmemo",
				"B_pk_invmandoc->B_pk_invmandoc",
				"B_pk_invbasdoc->B_pk_invbasdoc",
				"B_namount->B_namount",		//����
				"B_nassamount->B_nassamount",//������
				"B_notaxmargin->B_notaxsum",
				"B_ntaxmargin->B_npricetaxsum",
				
				"B_uimpurity->B_uimpurity",
				"B_vreserve1->B_vreserve1",
				"B_vreserve2->B_vreserve2",
				
				"B_ureserve2->B_ureserve2",
				
				
				"B_ureserve1->B_ureserve1",
				
				"B_nreserve2->B_nreserve1",
				
				"B_nreserve8->B_nreserve8",
				
				"B_nreserve9->B_nreserve9",
				
				"B_nreserve10->B_nreserve10",
				
				 "B_ntaxrate->B_ntaxrate",
				
				"B_vlastbillcode->H_vbillno",
				"B_vlastbillid->B_pk_salepresettle_h",
				"B_vlastbillrowid->B_pk_salepresettle_b",
				"B_vlastbilltype->H_pk_billtype",
				
			  	"B_vsourcebillid->B_vsourcebillid",
				"B_vsourcebillrowid->B_vsourcebillrowid",
		        "B_vsourcebilltype->B_vsourcebilltype",
		        "B_csourcebillcode->B_csourcebillcode",
				
		        

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
