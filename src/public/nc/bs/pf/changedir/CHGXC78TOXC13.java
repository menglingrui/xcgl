package nc.bs.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

/**
 * 精矿入库单到送样单的后台交换类
 * 后台交换类
 * @author Jay
 *
 */
public class CHGXC78TOXC13 extends  nc.bs.pf.change.VOConversion{
	public CHGXC78TOXC13(){
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
				"H_pk_classorder->H_pk_classorder",//班次
				"H_pk_minarea->H_pk_minarea",//部门--取样单位
				"H_pk_invmandoc->B_pk_invmandoc",
				"H_pk_invbasdoc->B_pk_invbasdoc",
				//"H_dweighdate->H_dbilldate",
				"H_dbilldate->H_dbilldate",//单据日期
				"H_pk_busitype->H_pk_busitype",//业务流程
				"H_pk_vehicle->B_pk_vehicle",
				"H_vmemo->H_vmemo",
				"B_vmemo->B_vmemo",
				"H_ireserve1->H_ireserve1",
				"H_ireserve10->H_ireserve10",
				"H_ireserve2->H_ireserve2",
				"H_ireserve3->H_ireserve3",
				"H_ireserve4->H_ireserve4",
				"H_ireserve5->H_ireserve5",
				"H_ireserve6->H_ireserve6",
				"H_ireserve7->H_ireserve7",
				"H_ireserve8->H_ireserve8",
				"H_ireserve9->H_ireserve9",
				"H_nreserve1->H_nreserve1",
				"H_nreserve10->H_nreserve10",
				"H_nreserve2->H_nreserve2",
				"H_nreserve3->H_nreserve3",
				"H_nreserve4->H_nreserve4",
				"H_nreserve5->H_nreserve5",
				"H_nreserve6->H_nreserve6",
				"H_nreserve7->H_nreserve7",
				"H_nreserve8->H_nreserve8",
				"H_nreserve9->H_nreserve9",
				"H_pk_defdoc1->H_pk_defdoc1",
				"H_pk_defdoc10->H_pk_defdoc10",
				"H_pk_defdoc11->H_pk_defdoc11",
				"H_pk_defdoc12->H_pk_defdoc12",
				"H_pk_defdoc13->H_pk_defdoc13",
				"H_pk_defdoc14->H_pk_defdoc14",
				"H_pk_defdoc15->H_pk_defdoc15",
				"H_pk_defdoc16->H_pk_defdoc16",
				"H_pk_defdoc17->H_pk_defdoc17",
				"H_pk_defdoc18->H_pk_defdoc18",
				"H_pk_defdoc19->H_pk_defdoc19",
				"H_pk_defdoc2->H_pk_defdoc2",
				"H_pk_defdoc20->H_pk_defdoc20",
				"H_pk_defdoc3->H_pk_defdoc3",
				"H_pk_defdoc4->H_pk_defdoc4",
				"H_pk_defdoc5->H_pk_defdoc5",
				"H_pk_defdoc6->H_pk_defdoc6",
				"H_pk_defdoc7->H_pk_defdoc7",
				"H_pk_defdoc8->H_pk_defdoc8",
				"H_pk_defdoc9->H_pk_defdoc9",
				"H_pk_deptdoc->H_pk_deptdoc",//部门档案
				"H_ureserve1->H_ureserve1",
				"H_ureserve10->H_ureserve10",
				"H_ureserve2->H_ureserve2",
				"H_ureserve3->H_ureserve3",
				"H_ureserve4->H_ureserve4",
				"H_ureserve5->H_ureserve5",
				"H_ureserve6->H_ureserve6",
				"H_ureserve7->H_ureserve7",
				"H_ureserve8->H_ureserve8",
				"H_ureserve9->H_ureserve9",
				"H_vdef1->H_vdef1",
				"H_vdef10->H_vdef10",
				"H_vdef11->H_vdef11",
				"H_vdef12->H_vdef12",
				"H_vdef13->H_vdef13",
				"H_vdef14->H_vdef14",
				"H_vdef15->H_vdef15",
				"H_vdef16->H_vdef16",
				"H_vdef17->H_vdef17",
				"H_vdef18->H_vdef18",
				"H_vdef19->H_vdef19",
				"H_vdef2->H_vdef2",
				"H_vdef20->H_vdef20",
				"H_vdef3->H_vdef3",
				"H_vdef4->H_vdef4",
				"H_vdef5->H_vdef5",
				"H_vdef6->H_vdef6",
				"H_vdef7->H_vdef7",
				"H_vdef8->H_vdef8",
				"H_vdef9->H_vdef9",
				"H_vreserve1->H_vreserve1",
				"H_vreserve10->H_vreserve10",
				"H_vreserve2->H_vreserve2",
				"H_vreserve3->H_vreserve3",
				"H_vreserve4->H_vreserve4",
				"H_vreserve5->H_vreserve5",
				"H_vreserve6->H_vreserve6",
				"H_vreserve7->H_vreserve7",
				"H_vreserve8->H_vreserve8",
				"H_vreserve9->H_vreserve9",
				
				"B_ireserve1->B_ireserve1",
				"B_ireserve10->B_ireserve10",
				"B_ireserve2->B_ireserve2",
				"B_ireserve3->B_ireserve3",
				"B_ireserve4->B_ireserve4",
				"B_ireserve5->B_ireserve5",
				"B_ireserve6->B_ireserve6",
				"B_ireserve7->B_ireserve7",
				"B_ireserve8->B_ireserve8",
				"B_ireserve9->B_ireserve9",
				"B_nreserve1->B_nreserve1",
				"B_nreserve10->B_nreserve10",
				"B_nreserve2->B_nreserve2",
				"B_nreserve3->B_nreserve3",
				"B_nreserve4->B_nreserve4",
				"B_nreserve5->B_nreserve5",
				"B_nreserve6->B_nreserve6",
				"B_nreserve7->B_nreserve7",
				"B_nreserve8->B_nreserve8",
				"B_nreserve9->B_nreserve9",
				"B_pk_defdoc1->B_pk_defdoc1",
				"B_pk_defdoc10->B_pk_defdoc10",
				"B_pk_defdoc11->B_pk_defdoc11",
				"B_pk_defdoc12->B_pk_defdoc12",
				"B_pk_defdoc13->B_pk_defdoc13",
				"B_pk_defdoc14->B_pk_defdoc14",
				"B_pk_defdoc15->B_pk_defdoc15",
				"B_pk_defdoc16->B_pk_defdoc16",
				"B_pk_defdoc17->B_pk_defdoc17",
				"B_pk_defdoc18->B_pk_defdoc18",
				"B_pk_defdoc19->B_pk_defdoc19",
				"B_pk_defdoc2->B_pk_defdoc2",
				"B_pk_defdoc20->B_pk_defdoc20",
				"B_pk_defdoc3->B_pk_defdoc3",
				"B_pk_defdoc4->B_pk_defdoc4",
				"B_pk_defdoc5->B_pk_defdoc5",
				"B_pk_defdoc6->B_pk_defdoc6",
				"B_pk_defdoc7->B_pk_defdoc7",
				"B_pk_defdoc8->B_pk_defdoc8",
				"B_pk_defdoc9->B_pk_defdoc9",
				"B_ureserve1->B_ureserve1",
				"B_ureserve10->B_ureserve10",
				"B_ureserve2->B_ureserve2",
				"B_ureserve3->B_ureserve3",
				"B_ureserve4->B_ureserve4",
				"B_ureserve5->B_ureserve5",
				"B_ureserve6->B_ureserve6",
				"B_ureserve7->B_ureserve7",
				"B_ureserve8->B_ureserve8",
				"B_ureserve9->B_ureserve9",
				"B_vdef1->B_vdef1",
				"B_vdef10->B_vdef10",
				"B_vdef11->B_vdef11",
				"B_vdef12->B_vdef12",
				"B_vdef13->B_vdef13",
				"B_vdef14->B_vdef14",
				"B_vdef15->B_vdef15",
				"B_vdef16->B_vdef16",
				"B_vdef17->B_vdef17",
				"B_vdef18->B_vdef18",
				"B_vdef19->B_vdef19",
				"B_vdef2->B_vdef2",
				"B_vdef20->B_vdef20",
				"B_vdef3->B_vdef3",
				"B_vdef4->B_vdef4",
				"B_vdef5->B_vdef5",
				"B_vdef6->B_vdef6",
				"B_vdef7->B_vdef7",
				"B_vdef8->B_vdef8",
				"B_vdef9->B_vdef9",
				"B_vreserve1->B_vreserve1",
				"B_vreserve10->B_vreserve10",
				"B_vreserve2->B_vreserve2",
				"B_vreserve3->B_vreserve3",
				"B_vreserve4->B_vreserve4",
				"B_vreserve5->B_vreserve5",
				"B_vreserve6->B_vreserve6",
				"B_vreserve7->B_vreserve7",
				"B_vreserve8->B_vreserve8",
				"B_vreserve9->B_vreserve9",
				
				"B_vlastbillcode->H_vbillno",
				"B_vlastbillid->B_pk_general_h",
				"B_vlastbillrowid->B_pk_general_b",
				"B_vlastbilltype->H_pk_billtype",
				"B_csourcebillcode->H_vbillno",
				"B_vsourcebillid->B_pk_general_h",
				"B_vsourcebillrowid->B_pk_general_b",
				"B_vsourcebilltype->H_pk_billtype",
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
