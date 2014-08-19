package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;
/**
 * ���ۺ�ͬ�����۶���
 *
 */
public class CHGXC70TO30 extends nc.ui.pf.change.VOConversionUI{

	/**
	 * CHGXC13TOXC31 Ĭ�Ϲ���
	 */
	public CHGXC70TO30() {
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
				"H_pk_corp->H_pk_corp", 	  //��˾
				"H_cdeptid->H_pk_deptdoc",    //����		
				"H_ccurrencytypeid->H_currid", //����
				"H_ccustomerid->H_custid",//���̹���
				"H_ccustbasid->H_custbasid",//���̻���
				"H_cbiztype->H_pk_busitype",//ҵ������
				
				"B_cinventoryid->B_invid",    //�����������
				"B_cinvbasdocid->B_invbasid", //�����������
				"B_cunitid->B_measid",        //��λ
				"B_ct_code->H_ct_code",
				"B_ct_name->H_ct_name",
				"H_dbilldate->H_dbilldate",   //��������			
				"B_cconsigncorpid->H_pk_corp",//������˾
				
				"B_vdef1->B_ureserve1",
				"B_vdef2->B_nreserve1",
				"B_vdef3->B_oritaxprice",
				
	//			"H_ccooppohid->H_vbillno",//�Է�������
	
				
//				"B_noriginalcurtaxprice->B_oritaxprice",// ��˰����
//				"B_noriginalcurprice->B_oriprice",           //��˰����
//				"B_ntaxrate->B_taxration",  //˰��
//				"B_noriginalcurmny->B_orisum", //��˰��� 
//				"B_noriginalcursummny->B_oritaxsummny",//��˰�ϼ�        

//				"B_vlastbillcode->H_vbillno",		 //
				"B_csourcebillid->H_pk_soct",        //��Դ����id
				"B_csourcebillbodyid->B_pk_soct_b",  //��Դ����id
		        "B_creceipttype->H_pk_billtype",     //��Դ��������
		        
//		        "B_csourcebillcode->H_vbillno",
//		    	"B_vsourcebillid->H_pk_soct",
//				"B_vsourcebillrowid->B_pk_soct_b",
//		        "B_vsourcebilltype->H_pk_billtype",
		
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
