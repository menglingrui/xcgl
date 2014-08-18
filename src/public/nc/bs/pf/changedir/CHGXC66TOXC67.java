package nc.bs.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * 销售结算单到销售结算调差
 * @author ddk	
 *
 */
public class CHGXC66TOXC67 extends  nc.bs.pf.change.VOConversion{
	public CHGXC66TOXC67(){
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
				"H_pk_busitype->H_pk_busitype",
				"H_pk_corp->H_pk_corp",
				"H_vemployeeid->H_vemployeeid",//项目主键	vemployeeid项目主键	vemployeeid
				"H_pk_deptdoc->H_pk_deptdoc",
				"H_vmemo->H_vmemo",
				"H_dbilldate->H_dbilldate",
//				"H_vbillstatus->H_vbillstatus",
				"H_pk_vehicle->H_pk_vehicle",//车辆主键
				"H_pk_invmandoc->H_pk_invmandoc",//存货管理主键
				"H_pk_invbasdoc->H_pk_invbasdoc",//存货基本主键
				"H_custid->H_custid",//客商管理主键
				"H_custbasid->H_custbasid",//客商基本主键
				"H_dshipdate->H_dshipdate",//发货日期
				
				"B_crowno->B_crowno",
				"B_vmemo->B_vmemo",
				"B_pk_invmandoc->B_pk_invmandoc",
				"B_pk_invbasdoc->B_pk_invbasdoc",
				"B_namount->B_namount",		//数量
				"B_nassamount->B_nassamount",//辅数量
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
		return null;
	}
	/**
	 * 返回用户自定义函数
	 */
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}

}
