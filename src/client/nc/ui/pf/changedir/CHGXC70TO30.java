package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;
/**
 * 销售合同到销售订单
 *
 */
public class CHGXC70TO30 extends nc.ui.pf.change.VOConversionUI{

	/**
	 * CHGXC13TOXC31 默认构造
	 */
	public CHGXC70TO30() {
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
				"H_pk_corp->H_pk_corp", 	  //公司
				"H_cdeptid->H_pk_deptdoc",    //部门		
				"H_ccurrencytypeid->H_currid", //币种
				"H_ccustomerid->H_custid",//客商管理
				"H_ccustbasid->H_custbasid",//客商基本
				"H_cbiztype->H_pk_busitype",//业务流程
				
				"B_cinventoryid->B_invid",    //存货管理主键
				"B_cinvbasdocid->B_invbasid", //存货基本主键
				"B_cunitid->B_measid",        //单位
				"B_ct_code->H_ct_code",
				"B_ct_name->H_ct_name",
				"H_dbilldate->H_dbilldate",   //单据日期			
				"B_cconsigncorpid->H_pk_corp",//发货公司
				
				"B_vdef1->B_ureserve1",
				"B_vdef2->B_nreserve1",
				"B_vdef3->B_oritaxprice",
				
	//			"H_ccooppohid->H_vbillno",//对方订单号
	
				
//				"B_noriginalcurtaxprice->B_oritaxprice",// 含税单价
//				"B_noriginalcurprice->B_oriprice",           //无税单价
//				"B_ntaxrate->B_taxration",  //税率
//				"B_noriginalcurmny->B_orisum", //无税金额 
//				"B_noriginalcursummny->B_oritaxsummny",//价税合计        

//				"B_vlastbillcode->H_vbillno",		 //
				"B_csourcebillid->H_pk_soct",        //来源主表id
				"B_csourcebillbodyid->B_pk_soct_b",  //来源附表id
		        "B_creceipttype->H_pk_billtype",     //来源单据类型
		        
//		        "B_csourcebillcode->H_vbillno",
//		    	"B_vsourcebillid->H_pk_soct",
//				"B_vsourcebillrowid->B_pk_soct_b",
//		        "B_vsourcebilltype->H_pk_billtype",
		
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
