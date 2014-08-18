package nc.bs.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * 精矿入库单到送样单的后台交换类
 * 后台交换类
 * @author Jay
 *
 */
public class CHGXC26TOXC68 extends  nc.bs.pf.change.VOConversion{
	public CHGXC26TOXC68(){
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
				"H_pk_factory->H_pk_factory",//选厂
				"H_pk_beltline->H_pk_beltline",//生产线
				"H_pk_minarea->H_pk_minarea",//部门--取样单位
				"H_pk_deptdoc->H_pk_deptdoc",
				"H_pk_stordoc->H_pk_stordoc",//仓库
				
				//"H_dweighdate->H_dbilldate",
				"H_dbilldate->H_dbilldate",//单据日期
				"H_pk_busitype->H_pk_busitype",//业务流程
                "H_pk_resetype->H_pk_resetype",//收发类别
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
				"B_ndryweight->B_ndryweight",//数量交换
				"B_nwetweight->B_ndryweight",//数量交换			
				"B_vcrowno->B_vcrowno",
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
