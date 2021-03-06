package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * 销售过磅登记到销售预结算
 * @author ddk
 *
 */
public class CHGXC25TOXC65 extends  nc.bs.pf.change.VOConversion{
	/**
	 * 构造函数
	 */
	public CHGXC25TOXC65(){
		super();
	}
	/**
	 * 获得交换后处理类 全名称
	 * @return java.lang.String
	 */
	public String getAfterClassName() {
		return null;
	}
	/**
	 * 获得交换后处理类 全名称
	 * @return java.lang.String
	 */
	public String getOtherClassName() {
		return null;
	}
	/**
	 * 返回交换类型枚举ConversionPolicyEnum，默认为单据项目-单据项目
	 * @return ConversionPolicyEnum
	 * @since 5.5
	 */
	public ConversionPolicyEnum getConversionPolicy() {
		return ConversionPolicyEnum.BILLITEM_BILLITEM;
	}
	
	/**
	 * 获得映射类型的交换规则
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
				"H_ccustbasid->H_custbasid",//客商基本
				"B_vreserve1->B_carno",
				"B_nreserve2->B_nreserve2",//
				"B_ureserve1->B_ureserve1",
				
				"B_vlastbillcode->H_vbillno",
				
				"B_vlastbillid->B_pk_weighdoc",
				
				"B_vlastbillrowid->B_pk_weighdoc_b",
				"B_vlastbilltype->H_pk_billtype",
    			"B_csourcebillcode->B_vlastbillcode",
				"B_vsourcebillid->B_vsourcebillid",
				"B_vsourcebillrowid->B_vsourcebillrowid",
				"B_vsourcebilltype->B_vsourcebilltype",
		};
	}
	
	/**
	 * 获得赋值类型的交换规则
	 * @return java.lang.String[]
	 */
	public String[] getAssign() {
		return null;
	}
	/**
	 * 获得公式类型的交换规则
	 * @return java.lang.String[]
	 */
	public String[] getFormulas() {
		return new String[]{
			"pk_measdoc->getColValue(bd_invbasdoc,pk_measdoc,pk_invbasdoc,pk_invbasdoc);",
		};
	}
	/**
	 * 返回用户自定义函数
	 */
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}
