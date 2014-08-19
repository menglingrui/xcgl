package nc.ui.pf.changedir;
/**
 * 原矿加工出库到水分化验单
 * @author Jay
 *
 */
public class CHGXC15TOXC75 extends  nc.ui.pf.change.VOConversionUI{
	public String[] getField() {
		return new String[]{
			"H_pk_corp->H_pk_corp",
			"B_pk_stordoc->H_pk_stordoc",
			"B_pk_factory->H_pk_factory",
			"H_pk_minarea->H_pk_minarea",
			//"B_pk_corp->H_pk_corp",
			"H_pk_invmandoc->B_pk_invmandoc",
			"H_pk_invbasdoc->B_pk_invbasdoc",
			//"B_nnum->B_ndryweight",
			//"B_pk_father->B_pk_father"
			"H_vmemo->H_vmemo",
			"B_vlastbillcode->H_vbillno",
			"B_vlastbillid->B_pk_general_h",
			"B_vlastbillrowid->B_pk_general_b",
			"B_vlastbilltype->H_pk_billtype",
			
			"B_csourcebillcode->H_vbillno",
			"B_vsourcebillid->B_pk_general_h",
			"B_vsourcebillrowid->B_pk_general_b",
			"B_vsourcebilltype->H_pk_billtype",
//			"H_ireserve1->H_ireserve1",
//			"H_ireserve10->H_ireserve10",
//			"H_ireserve2->H_ireserve2",
//			"H_ireserve3->H_ireserve3",
//			"H_ireserve4->H_ireserve4",
//			"H_ireserve5->H_ireserve5",
//			"H_ireserve6->H_ireserve6",
//			"H_ireserve7->H_ireserve7",
//			"H_ireserve8->H_ireserve8",
//			"H_ireserve9->H_ireserve9",
//			"H_nreserve1->H_nreserve1",
//			"H_nreserve10->H_nreserve10",
//			"H_nreserve2->H_nreserve2",
//			"H_nreserve3->H_nreserve3",
//			"H_nreserve4->H_nreserve4",
//			"H_nreserve5->H_nreserve5",
//			"H_nreserve6->H_nreserve6",
//			"H_nreserve7->H_nreserve7",
//			"H_nreserve8->H_nreserve8",
//			"H_nreserve9->H_nreserve9",
//			"H_pk_defdoc1->H_pk_defdoc1",
//			"H_pk_defdoc10->H_pk_defdoc10",
//			"H_pk_defdoc11->H_pk_defdoc11",
//			"H_pk_defdoc12->H_pk_defdoc12",
//			"H_pk_defdoc13->H_pk_defdoc13",
//			"H_pk_defdoc14->H_pk_defdoc14",
//			"H_pk_defdoc15->H_pk_defdoc15",
//			"H_pk_defdoc16->H_pk_defdoc16",
//			"H_pk_defdoc17->H_pk_defdoc17",
//			"H_pk_defdoc18->H_pk_defdoc18",
//			"H_pk_defdoc19->H_pk_defdoc19",
//			"H_pk_defdoc2->H_pk_defdoc2",
//			"H_pk_defdoc20->H_pk_defdoc20",
//			"H_pk_defdoc3->H_pk_defdoc3",
//			"H_pk_defdoc4->H_pk_defdoc4",
//			"H_pk_defdoc5->H_pk_defdoc5",
//			"H_pk_defdoc6->H_pk_defdoc6",
//			"H_pk_defdoc7->H_pk_defdoc7",
//			"H_pk_defdoc8->H_pk_defdoc8",
//			"H_pk_defdoc9->H_pk_defdoc9",
//			//"H_pk_deptdoc->H_pk_deptdoc",
//			//"H_ureserve1->H_ureserve1",
//			"H_ureserve10->H_ureserve10",
//			//"H_ureserve2->H_ureserve2",
//			"H_ureserve3->H_ureserve3",
//			"H_ureserve4->H_ureserve4",
//			"H_ureserve5->H_ureserve5",
//			"H_ureserve6->H_ureserve6",
//			"H_ureserve7->H_ureserve7",
//			"H_ureserve8->H_ureserve8",
//			"H_ureserve9->H_ureserve9",
//			"H_vdef1->H_vdef1",
//			"H_vdef10->H_vdef10",
//			"H_vdef11->H_vdef11",
//			"H_vdef12->H_vdef12",
//			"H_vdef13->H_vdef13",
//			"H_vdef14->H_vdef14",
//			"H_vdef15->H_vdef15",
//			"H_vdef16->H_vdef16",
//			"H_vdef17->H_vdef17",
//			"H_vdef18->H_vdef18",
//			"H_vdef19->H_vdef19",
//			"H_vdef2->H_vdef2",
//			"H_vdef20->H_vdef20",
//			"H_vdef3->H_vdef3",
//			"H_vdef4->H_vdef4",
//			"H_vdef5->H_vdef5",
//			"H_vdef6->H_vdef6",
//			"H_vdef7->H_vdef7",
//			"H_vdef8->H_vdef8",
//			"H_vdef9->H_vdef9",
//			"H_vreserve1->H_vreserve1",
//			"H_vreserve10->H_vreserve10",
//			"H_vreserve2->H_vreserve2",
//			"H_vreserve3->H_vreserve3",
//			"H_vreserve4->H_vreserve4",
//			"H_vreserve5->H_vreserve5",
//			"H_vreserve6->H_vreserve6",
//			"H_vreserve7->H_vreserve7",
//			"H_vreserve8->H_vreserve8",
//			"H_vreserve9->H_vreserve9",

		};
	}
	public String[] getFormulas() {
		return null;
	}
}
