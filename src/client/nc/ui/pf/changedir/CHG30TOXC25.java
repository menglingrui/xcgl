package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * sale order change to XC25
 * @author mlr
 *
 */
public class CHG30TOXC25 extends nc.ui.pf.change.VOConversionUI{
	
	/**
	 * CHGXC13TOXC31 默认构造
	 */
	public CHG30TOXC25() {
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
				"H_pk_deptdoc->H_cdeptid",
				"H_pk_corp->H_pk_corp",
				"H_pk_calbody->H_cadvisecalbodyid",			
				"H_custid->H_ccustomerid",//客商管理
				"H_ccustbasid->H_custbasid",//客商基本
//				"H_vuserdef9->H_vdef9",
//				"H_vuserdef8->H_vdef8",
//				"H_vuserdef7->H_vdef7",
//				"H_vuserdef6->H_vdef6",
//				"H_vuserdef5->H_vdef5",
//				"H_vuserdef4->H_vdef4",
//				"H_vuserdef3->H_vdef3",
//				"H_vuserdef2->H_vdef2",
				"H_pk_stordoc->B_cbodywarehouseid",
//				"B_cwarehouseid->B_ctakeoutwhid",//// added by lirr 2009-10-30上午10:01:32
//				"H_cotherwhid->B_cinwhid",
//				"H_clogdatenow->SYSDATE",
//				"H_vuserdef1->H_vdef1",
//				"H_vuserdef10->H_vdef10",
//				"H_vuserdef11->H_vdef11",
//				"H_vuserdef12->H_vdef12",
//				"H_vuserdef13->H_vdef13",
//				"H_vuserdef14->H_vdef14",
//				"H_vuserdef15->H_vdef15",
//				"H_vuserdef16->H_vdef16",
//				"H_vuserdef17->H_vdef17",
//				"H_vuserdef18->H_vdef18",
//				"H_vuserdef19->H_vdef19",
//				"H_vuserdef20->H_vdef20",
//				"H_coperatorid->SYSOPERATOR",
//				"H_dbilldate->SYSDATE",
//				"H_coperatoridnow->SYSOPERATOR",
//				"H_cbizid->B_ctakeoutpsnid",
//				"H_vnote->H_vnote",
//				"H_fallocflag->H_fallocflag",
//		    "H_bdrictflag->H_bdrictflag",
//				"H_cothercorpid->H_cincorpid",
//				"H_cothercalbodyid->H_cincbid",
//				"H_coutcorpid->H_coutcorpid",
//				"H_coutcalbodyid->H_coutcbid",
//				"H_freplenishflag->B_bretractflag",
//		    "B_freplenishflag->B_bretractflag",
//				"H_cdilivertypeid->B_pk_sendtype",
//				"H_vdiliveraddress->B_vreceiveaddress",
//		    "B_vdiliveraddress->B_vreceiveaddress",
//				"H_ccustomerid->B_ccustomerid",//由上游定单转换后传值
//				"B_vfree2->B_vfree2",
//				"B_vfree1->B_vfree1",
				"B_pk_invmandoc->B_cinventoryid",
				"B_pk_invbasdoc->B_cinvbasdocid",
//				"B_vuserdef6->B_vbdef6",
//				"B_vuserdef5->B_vbdef5",
//				"B_vuserdef4->B_vbdef4",
//				"B_vuserdef3->B_vbdef3",
//				"B_vuserdef2->B_vbdef2",
//				"B_vuserdef1->B_vbdef1",
//				"B_vuserdef7->B_vbdef7",
//				"B_vuserdef8->B_vbdef8",
//				"B_vuserdef9->B_vbdef9",
//				"B_vuserdef10->B_vbdef10",
//				"B_vuserdef11->B_vbdef11",
//				"B_vuserdef12->B_vbdef12",
//				"B_vuserdef13->B_vbdef13",
//				"B_vuserdef14->B_vbdef14",
//				"B_vuserdef15->B_vbdef15",
//				"B_vuserdef16->B_vbdef16",
//				"B_vuserdef17->B_vbdef17",
//				"B_vuserdef18->B_vbdef18",
//				"B_vuserdef19->B_vbdef19",
//				"B_vuserdef20->B_vbdef20",
			    
//				public String  vlastbilltype;//来源单据类型    记录流程 上游核心单据用的    
//		        public String  vlastbillid;//来源单据ID
//		        public String  vlastbillrowid;//来源单据RowID
//		        public String  vlastbillcode;//来源单据号
//		        
//		        public String  csourcebillcode;//源头单据号
//		        public String  vsourcebilltype;//源头单据类型      
//		        public String  vsourcebillid;//源头单据ID
//		        public String  vsourcebillrowid;//源头单据RowID	
//				"B_vfree5->B_vfree5",
//				"B_vfree4->B_vfree4",
//				"B_vfree3->B_vfree3",
//				"B_vlastbillid->B_csourcebillid",
//				"B_vlastbillrowid->B_csourcebillbodyid",
//		        "B_vlastbilltype->B_creceipttype",
				"B_vlastbillid->B_csaleid",
				"B_vlastbillrowid->B_corder_bid",
		        "B_vlastbilltype->H_creceipttype",
		        "B_vlastbillcode->H_vreceiptcode",
//				"B_vbatchcode->B_vbatch",
		    	"B_vsourcebillid->B_csourcebillid",
				"B_vsourcebillrowid->B_csourcebillbodyid",
		        "B_vsourcebilltype->B_creceipttype",
		  //      "B_csourcebillcode->H_vreceiptcode",
		        
		    	
				"B_ureserve1->B_vdef1",
				"B_nreserve1->B_vdef2",
				"B_nreserve2->B_vdef3",
		        
//		    	"B_vsourcebillid->B_csaleid",
//				"B_vsourcebillrowid->B_corder_bid",
//		        "B_vsourcebilltype->H_creceipttype",
//		        "B_csourcebillcode->H_vreceiptcode",
		
//				"B_castunitid->B_castunitid",
//				"B_creceieveid->B_creceieveid",
//				"B_hsl->B_nchangerate",
//				"B_cspaceid->B_ctakeoutspaceid",
//				"B_dvalidate->B_dvalidate",
//				"B_csourceheadts->H_ts",
//				"B_csourcebodyts->B_ts",
//				"B_cinvbasid->B_cinvbasid",
//				"B_cinvmanid->B_cinvbasid",
//				
//				"B_cquoteunitid->B_cquoteunitid",		
//				"B_nquoteunitrate->B_nquoteunitrate",
				
//				"H_pk_defdoc1->H_pk_defdoc1",
//				"H_pk_defdoc2->H_pk_defdoc2",
//				"H_pk_defdoc3->H_pk_defdoc3",
//				"H_pk_defdoc4->H_pk_defdoc4",
//				"H_pk_defdoc5->H_pk_defdoc5",
//				"H_pk_defdoc6->H_pk_defdoc6",
//				"H_pk_defdoc7->H_pk_defdoc7",
//				"H_pk_defdoc8->H_pk_defdoc8",
//				"H_pk_defdoc9->H_pk_defdoc9",
//				"H_pk_defdoc10->H_pk_defdoc10",
//				"H_pk_defdoc11->H_pk_defdoc11",
//				"H_pk_defdoc12->H_pk_defdoc12",
//				"H_pk_defdoc13->H_pk_defdoc13",
//				"H_pk_defdoc14->H_pk_defdoc14",
//				"H_pk_defdoc15->H_pk_defdoc15",
//				"H_pk_defdoc16->H_pk_defdoc16",
//				"H_pk_defdoc17->H_pk_defdoc17",
//				"H_pk_defdoc18->H_pk_defdoc18",
//				"H_pk_defdoc19->H_pk_defdoc19",
//				"H_pk_defdoc20->H_pk_defdoc20",
//				
//				"B_pk_defdoc1->B_pk_defdoc1",
//				"B_pk_defdoc2->B_pk_defdoc2",
//				"B_pk_defdoc3->B_pk_defdoc3",
//				"B_pk_defdoc4->B_pk_defdoc4",
//				"B_pk_defdoc5->B_pk_defdoc5",
//				"B_pk_defdoc6->B_pk_defdoc6",
//				"B_pk_defdoc7->B_pk_defdoc7",
//				"B_pk_defdoc8->B_pk_defdoc8",
//				"B_pk_defdoc9->B_pk_defdoc9",
//				"B_pk_defdoc10->B_pk_defdoc10",
//				"B_pk_defdoc11->B_pk_defdoc11",
//				"B_pk_defdoc12->B_pk_defdoc12",
//				"B_pk_defdoc13->B_pk_defdoc13",
//				"B_pk_defdoc14->B_pk_defdoc14",
//				"B_pk_defdoc15->B_pk_defdoc15",
//				"B_pk_defdoc16->B_pk_defdoc16",
//				"B_pk_defdoc17->B_pk_defdoc17",
//				"B_pk_defdoc18->B_pk_defdoc18",
//				"B_pk_defdoc19->B_pk_defdoc19",
//				"B_pk_defdoc20->B_pk_defdoc20",
//				"B_bsourcelargess->B_flargess",
//				"B_flargess->B_flargess",
//				"B_cvendorid->B_cvendorid",
//				"B_nsaleprice->B_nnotaxprice",
//				"B_ntaxprice->B_nprice",
//		    "H_cbiztype->H_cbiztypeid",
//		    "H_csettlepathid->H_csettlepathid",
		    
//		    "B_ddeliverdate->B_dplanarrivedate",
//		    "B_creceiveareaid->B_pk_arrivearea",
//		    "B_vreceiveaddress->B_vreceiveaddress",
//		    "B_creceivepointid->B_pk_areacl",
//		    "B_cprojectid->B_cprojectid",
//		    "B_cprojectphaseid->B_cprojectphase",
//		    "H_bdirecttranflag->H_bdrictflag",
//		    "B_vnotebody->B_vnote",
//		    "B_pk_corp->B_ctakeoutcorpid"
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
				"B_ngrossweight->B_nnumber-tonumber(B_vdef20)",
				"B_nnetweight->B_nnumber-tonumber(B_vdef20)",
				"B_ureserve2->'Y'",
		};
	}
	/**
	 * 返回用户自定义函数
	 */
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}
