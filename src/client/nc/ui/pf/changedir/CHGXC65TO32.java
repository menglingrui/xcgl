package nc.ui.pf.changedir;

import nc.ui.pf.change.VOConversionUI;
import nc.vo.pf.change.UserDefineFunction;

public class CHGXC65TO32 extends VOConversionUI{

	public CHGXC65TO32() {
	}

	public String getAfterClassName() {
	  //	        "H_csalecorpid->getColValue(bd_cubasdoc,custname,pk_cubasdoc, B_custbasid  )",
     //   "H_ccalbodyid->getColValue(bd_cubasdoc,custname,pk_cubasdoc, B_custbasid  )",
	  //"nc.vo.so.so002.OutToInvoiceChangeVO"
		return "nc.vo.xcgl.so.deal.OutToInvoiceChangeVO";
	}

	public String getOtherClassName() {
		//"nc.vo.so.so002.OutToInvoiceChangeVO"
		return null;
	}

	public String[] getField() {
		return (new String[] {
		    	"B_ccustomerid->H_custid",	    	
		    	"H_pk_corp->H_pk_corp",
		        "H_cbiztype->H_pk_busitype", 
		    //    "H_ccurrencyid->B_ccurrencytypeid", 
		        "H_cdeptid->H_pk_deptdoc",
		  //      "H_cemployeeid->H_cemployeeid", 
		        "H_creceiptcorpid->H_custid",
		        "H_creceiptcustomerid->H_custid",
		        "H_csalecorpid->H_pk_corp", 
		//        "H_ccalbodyid->H_ccalbodyid",
		//        "H_ctermprotocolid->H_ctermprotocolid",
		//        "H_ctransmodeid->H_ctransmodeid", 
		//        "H_cwarehouseid->H_cwarehouseid",
		//        "H_ndiscountrate->H_ndiscountrate", 
		 //       "H_bfreecustflag->H_bfreecustflag", 
		 //       "H_binitflag->H_binitflag",
		        "H_vnote->H_vmemo", 
		        "H_ts->H_ts", 
		 //       "H_cfreecustid->H_cfreecustid",
		//        "H_vreceiveaddress->H_vreceiveaddress",
		        
		        "H_dbilldate->SYSDATE", 
		        "H_dmakedate->SYSDATE",
		        "H_coperatorid->SYSOPERATOR", 
		        
		        
		        "B_pk_corp->H_pk_corp", 
		        
		        "B_cinventoryid->B_pk_invmandoc", 
		        "B_cinvbasdocid->B_pk_invbasdoc",
		        
		//      "B_cprolineid->B_cprolineid",
		//        "B_cbatchid->B_cbatchid",
		 //       "B_discountflag->B_discountflag",
		 //       
		 //       "B_cpackunitid->B_cpackunitid",
		//        "B_cunitid->B_cunitid", 
		//        "B_scalefactor->B_scalefactor",
		//        "B_fixedflag->B_fixedflag",
		//        "B_bqtfixedflag->B_bqtfixedflag",
		//        "B_nqtscalefactor->B_nqtscalefactor",
		        "B_nnumber->B_namount",
		//        "B_npacknumber->B_npacknumber",
		  //      "B_nquotenumber->B_nquoteunitnum",
		        
		//        "B_ccurrencytypeid->B_ccurrencytypeid",
		//        "B_ndiscountrate->B_ndiscountrate",
		//        "B_nexchangeotobrate->B_nexchangeotobrate",
		    //    "B_nitemdiscountrate->B_nitemdiscountrate", 
		    //    ��Ŀ����	noriginalcurtaxprice
		        
		   //     "B_noriginalcurdiscountmny->B_noriginalcurdiscountmny",
		  //      "B_noriginalcurmny->B_notaxsum",
		  //      "B_noriginalcurnetprice->B_noriginalcurnetprice",
		  //      "B_noriginalcurprice->B_notaxprice",
		  //      "B_noriginalcursummny->B_npricetaxsum",
		 //       "B_noriginalcurtaxmny->B_noriginalcurtaxmny",
		    //    "B_noriginalcurtaxnetprice->B_noriginalcurtaxnetprice",
		        "B_noriginalcurtaxprice->B_ntaxprice", 
		        "B_ntaxrate->B_ntaxrate",
		  //      "B_nprice->B_notaxprice",
		   //  //   "B_nnetprice->B_nnetprice",
		  //      "B_nsummny->B_npricetaxsum", 
		  //      "B_nmny->B_notaxsum",
		  //      "B_ntaxnetprice->B_ntaxnetprice",
		 //       "B_ntaxprice->B_ntaxprice", 
		 //       "B_ntaxmny->B_ntaxmny",
		  //      "B_ndiscountmny->B_ndiscountmny",
		        
		   //     "B_nquotenetprice->B_nqtnetprc",
		    //    "B_nquoteprice->B_nqtprc", 
		    //    "B_nquotetaxnetprice->B_nqttaxnetprc",
		   //     "B_nquotetaxprice->B_nqttaxprc",
		   //    "B_nquoteoriginalcurnetprice->B_norgqtnetprc",
		   //     "B_nquoteoriginalcurprice->B_norgqtprc",
		  //      "B_nquoteoriginalcurtaxnetprice->B_norgqttaxnetprc",
		  //      "B_nquoteoriginalcurtaxprice->B_norgqttaxprc",
		 
		  //      "B_nsubquotenetprice->B_nqtnetprc",
		  //      "B_nsubquoteprice->B_nqtprc", 
		  //      "B_nsubquotetaxnetprice->B_nqttaxnetprc",
		   //     "B_nsubquotetaxprice->B_nqttaxprc",

		 //       "B_cquoteunitid->B_cquoteunitid", 
		  //      "B_nquoteunitrate->B_nqtscalefactor", 
		 //      "B_vfree2->B_vfree2", "B_vfree3->B_vfree3", "B_vfree4->B_vfree4",
		   //     "B_vfree5->B_vfree5", "B_vfree1->B_vfree1",
		        
		 //       "B_cprojectid->B_cprojectid", 
		  //      "B_cprojectphaseid->B_cprojectphaseid",
		        
		        "B_creceipttype->B_vsourcebilltype", 
		        "B_coriginalbillcode->B_csourcebillcode",
		        "B_csourcebillid->B_vsourcebillid",
		        "B_csourcebillbodyid->B_vsourcebillrowid", 
		        "B_cupreceipttype->H_pk_billtype",
		        "B_cupsourcebillcode->H_vbillno",
		        "B_vupsourcerowno->B_crowno",
		        "B_cupsourcebillid->H_pk_salepresettle_h",
		        "B_cupsourcebillbodyid->B_pk_salepresettle_b",
		        
		        "B_creceiptcorpid->H_pk_corp", 
		//        "B_fbatchstatus->B_fbatchstatus",
		  //      "B_ct_manageid->B_ct_manageid", 
		  //      "B_cfreezeid->B_cfreezeid", 
		//        "B_blargessflag->B_blargessflag", 
		        "B_frownote->B_vmemo",
		//        "B_ddeliverdate->B_ddeliverdate",
		        "B_ts->H_ts", 
		        
//		        "H_vdef1->H_vdef1",
//		        "H_vdef2->H_vdef2", "H_vdef3->H_vdef3", "H_vdef4->H_vdef4",
//		        "H_vdef5->H_vdef5", "H_vdef6->H_vdef6", "H_vdef7->H_vdef7",
//		        "H_vdef8->H_vdef8", "H_vdef9->H_vdef9", "H_vdef10->H_vdef10",
//		        "H_vdef11->H_vdef11", "H_vdef12->H_vdef12", "H_vdef13->H_vdef13",
//		        "H_vdef14->H_vdef14", "H_vdef15->H_vdef15", "H_vdef16->H_vdef16",
//		        "H_vdef17->H_vdef17", "H_vdef18->H_vdef18", "H_vdef19->H_vdef19",
//		        "H_vdef20->H_vdef20",
//
//		        "B_vdef1->B_vdef1", "B_vdef2->B_vdef2", "B_vdef3->B_vdef3", "B_vdef4->B_vdef4",
//		        "B_vdef5->B_vdef5", "B_vdef6->B_vdef6", "B_vdef7->B_vdef7", "B_vdef8->B_vdef8", 
//		        "B_vdef9->B_vdef9", "B_vdef10->B_vdef10", "B_vdef11->B_vdef11", "B_vdef12->B_vdef12",
//		        "B_vdef13->B_vdef13", "B_vdef14->B_vdef14", "B_vdef15->B_vdef15", "B_vdef16->B_vdef16", 
//		        "B_vdef17->B_vdef17", "B_vdef18->B_vdef18", "B_vdef19->B_vdef19", "B_vdef20->B_vdef20",
//
//		        "H_pk_defdoc1->H_pk_defdoc1", "H_pk_defdoc2->H_pk_defdoc2",
//		        "H_pk_defdoc3->H_pk_defdoc3", "H_pk_defdoc4->H_pk_defdoc4",
//		        "H_pk_defdoc5->H_pk_defdoc5", "H_pk_defdoc6->H_pk_defdoc6",
//		        "H_pk_defdoc7->H_pk_defdoc7", "H_pk_defdoc8->H_pk_defdoc8",
//		        "H_pk_defdoc9->H_pk_defdoc9", "H_pk_defdoc10->H_pk_defdoc10",
//		        "H_pk_defdoc11->H_pk_defdoc11", "H_pk_defdoc12->H_pk_defdoc12",
//		        "H_pk_defdoc13->H_pk_defdoc13", "H_pk_defdoc14->H_pk_defdoc14",
//		        "H_pk_defdoc15->H_pk_defdoc15", "H_pk_defdoc16->H_pk_defdoc16",
//		        "H_pk_defdoc17->H_pk_defdoc17", "H_pk_defdoc18->H_pk_defdoc18",
//		        "H_pk_defdoc19->H_pk_defdoc19", "H_pk_defdoc20->H_pk_defdoc20",
//
//		        "B_pk_defdoc1->B_pk_defdoc1", "B_pk_defdoc2->B_pk_defdoc2",
//		        "B_pk_defdoc3->B_pk_defdoc3", "B_pk_defdoc4->B_pk_defdoc4",
//		        "B_pk_defdoc5->B_pk_defdoc5", "B_pk_defdoc6->B_pk_defdoc6",
//		        "B_pk_defdoc7->B_pk_defdoc7", "B_pk_defdoc8->B_pk_defdoc8",
//		        "B_pk_defdoc9->B_pk_defdoc9", "B_pk_defdoc10->B_pk_defdoc10",
//		        "B_pk_defdoc11->B_pk_defdoc11", "B_pk_defdoc12->B_pk_defdoc12",
//		        "B_pk_defdoc13->B_pk_defdoc13", "B_pk_defdoc14->B_pk_defdoc14",
//		        "B_pk_defdoc15->B_pk_defdoc15", "B_pk_defdoc16->B_pk_defdoc16",
//		        "B_pk_defdoc17->B_pk_defdoc17", "B_pk_defdoc18->B_pk_defdoc18",
//		        "B_pk_defdoc19->B_pk_defdoc19", "B_pk_defdoc20->B_pk_defdoc20",
		        
		    });
	}

	public String[] getFormulas() {
		return new String[] {
		    	"H_creceipttype->\"32\"",
		    	"H_fstatus->1",
		        "H_fcounteractflag->0",
		        "H_finvoicetype->0",
		        "H_ccustbaseid->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc, H_custid  )",
		//        "H_ccustbankid->getColValue2(bd_custbank,pk_accbank,pk_cubasdoc, H_ccustbaseid,defflag,\"Y\")",
		        "H_vprintcustname->getColValue(bd_cubasdoc,custname,pk_cubasdoc, H_custbasid  )",
		        "B_frowstatus->1",
		        "B_ninvoicediscountrate->100.00",
		        "B_cinvclassid->getColValue(bd_invbasdoc,pk_invcl,pk_invbasdoc,B_pk_invbasdoc)",
		        "B_nnumber->iif(B_nnumber=null,null,B_namount-B_nreserve1)",
		   //     "B_cadvisecalbodyid->iif(B_creccalbodyid=null,B_cadvisecalbodyid,B_creccalbodyid)",
		   //     "B_cbodywarehouseid->iif(B_creccalbodyid=null,B_cbodywarehouseid,B_crecwareid)"
		    };
	}

	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
	
}
