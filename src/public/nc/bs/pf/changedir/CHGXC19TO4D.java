package nc.bs.pf.changedir;

import nc.bs.pf.change.VOConversion;
import nc.vo.pf.change.UserDefineFunction;

public class CHGXC19TO4D  extends VOConversion {


	public CHGXC19TO4D() {
		super();
	}


	public String getAfterClassName() {
		return null;
	}

	public String getOtherClassName() {
	  return null;
	}

	public String[] getField() {
		return new String[] {
				"H_coperatoridnow->SYSOPERATOR",//操作员,单据加锁使用
				"H_dbilldate->SYSDATE",//单据日期
				"H_cwarehouseid->H_pk_stordoc",//仓库
//				"H_pk_calbody->H_ccalbodyid",//库存组织
				"H_cbiztype->H_pk_busitype",//业务类型
				"H_cdispatcherid->H_pk_resetype",//收发类别
				"H_cdptid->H_pk_deptdoc",//部门
//				"H_cbizid->H_clastbizid",//业务员
				"H_pk_corp->H_pk_corp",//公司
				"H_coperatorid->SYSOPERATOR",//制单人
				
				"B_cinvbasid->B_pk_invbasdoc",//存货基本档案ID   
				"B_cinventoryid->B_pk_invmandoc",//存货管理ID  	
				"B_pk_measdoc->B_pk_measdoc",//主单位
//				"B_castunitid->B_cassunitid",//辅单位
//				"B_hsl->B_hsl",//换算率 
//				"B_scrq->B_cshengchanriqi",//生产日期-----------------------------zpm
//				"B_dvalidate->B_cshixiaoriqi",//失效日期------------------------------zpm
//				"B_nneedinassistnum->B_nassnum",//应发辅数量
//				"B_nshouldinnum->B_ndryweight",//应发数量 
//				"B_ninassistnum->B_nassdryweight",//实发辅数量
//				"B_ninnum->B_ndryweight",//实发数量
				
//				"B_nshouldoutassistnum->B_nassnum",//应发辅数量
				"B_nshouldoutnum->B_ndryweight",//应发数量 
//				"B_noutassistnum->B_nassnum",//实发辅数量
				"B_noutnum->B_ndryweight",//实发数量
//				"B_nprice->B_nprice",//单价
//				"B_nmny->B_nmny",//金额	
//				"B_vbatchcode->B_vbatchcode",//批次号
//				"B_csourcebillhid->H_pk_general_h",
//				"B_cfirstbillbid->B_pk_general_b", 
//				"B_vfirstbillcode->H_vbillno",
//				"B_cfirsttype->H_pk_billtype",		
				
				"B_csourcebillhid->H_pk_general_h",
				"B_csourcebillbid->B_pk_general_b",
				"B_vsourcebillcode->H_vbillno",
				"B_csourcetype->H_pk_billtype",
				
				"B_cfirstbillhid->B_vsourcebillid",
				"B_cfirstbillbid->B_vsourcebillrowid", 
				"B_vfirstbillcode->B_csourcebillcode",
				"B_cfirsttype->B_vsourcebilltype",
				"B_dbizdate->SYSDATE"//出库日期->业务日期-------------------------zpm
				};
	}
	

	public String[] getFormulas() {
		
		return new String[] {
				"B_flargess->\"N\"",//是否赠品
				"H_cbilltypecode->\"4D\"",
				"H_fbillflag->2",
				"H_pk_calbody->getColValue(bd_stordoc,pk_calbody,pk_stordoc,H_pk_stordoc)",//库存组织
		};
	}
	/**
	* 返回用户自定义函数。
	*/
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}


}
