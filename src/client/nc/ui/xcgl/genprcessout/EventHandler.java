package nc.ui.xcgl.genprcessout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.xcgl.pub.bill.XCFlowManageEventHandler;
import nc.ui.zmpub.pub.check.BeforeSaveValudate;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.genprcessout.AggGeneralVO;
import nc.vo.xcgl.produceset.ProductSetBB1VO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.helper.ProduceSetHelper;
/**
 *类说明：原矿加工出库单
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-11下午03:29:52
 */
public class EventHandler extends XCFlowManageEventHandler{
	public EventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}
	@Override
	protected void onBoSave() throws Exception {
		AggGeneralVO billvo = (AggGeneralVO)getBillUI().getVOFromUI();
		if(billvo==null)//not null check
			return;
		if(billvo.getChildrenVO()!=null&&billvo.getChildrenVO().length>0){	
			//必输项校验
			BeforeSaveValudate.dataNotNullValidate(getBillCardPanelWrapper().getBillCardPanel());
//			AssayHVO []hvos = (AssayHVO [])billvo.getParentVO();
//			AssayBVO []bvos=(AssayBVO[])billvo.getChildrenVO();
//			//工程项目和作业重复性校验
//			BsBeforeSaveValudate.beforeSaveBodyUnique(bvos, new String[]{"pk_jobmngfil","pk_invmandoc",""}, new String[]{"工程项目","作业"});
		}
		else
			throw new Exception("表体不能为空");
		super.onBoSave();
		super.onBoRefresh();
	}
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch(intBtn){
		case PuBtnConst.flowset:
			getFlowSet();
			break;
		}
	}
	private void getFlowSet() {
		ProSetParaVO para=new ProSetParaVO();
		String beltline=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_beltline").getValueObject());
		String process=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_process").getValueObject());
		String classorder=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_classorder").getValueObject());
		String sourcetype=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_billtype").getValueObject());
        para.setPk_beltline(beltline);
        para.setPk_process(process);
        para.setPk_classorder(classorder);
        para.setPk_sourcetype(sourcetype);
        para.setProInSet(true);
        para.setProOutSet(false);
        try {
			List list=ProduceSetHelper.getProInSetInfor(para);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
		    	    getBillCardPanelWrapper().addLine();
		    		ProductSetBB1VO vo=(ProductSetBB1VO) list.get(i);
		    		getBillCardPanel().getBillModel().setValueAt(vo.getPk_invmandoc(), i, "pk_invmandoc");
		    		getBillCardPanel().getBillModel().setValueAt(vo.getPk_invbasdoc(), i, "pk_invbasdoc");
		    		getBillCardPanel().getBillModel().setValueAt(vo.getPk_invmandoc(), i, "pk_father");
		    		getBillCardPanel().getBillModel().execEditFormulas(i);
		    	}
			}else{
				getBillUI().showHintMessage("该工序下没有投入设置"); 
			}
		} catch (Exception e) {
			getBillUI().showErrorMessage(e.getMessage());
		}
	}
	@Override
	protected void onBoLineDel() throws Exception {	
		List list=new ArrayList();
		Map<String,String> map=new HashMap<String,String>();
		/**
		 * 表体选择的行的数据
		 */
		CircularlyAccessibleValueObject[] bvos = getBillCardPanelWrapper().getSelectedBodyVOs();
		/**
		 * 表体所有的行的数据
		 */
		CircularlyAccessibleValueObject[] bodyvos = 
    		getBillCardPanelWrapper().getBillCardPanel().getBillModel().getBodyValueVOs(
    				getUIController().getBillVoName()[2]);
		if(bvos!=null&&bvos.length!=0){
			  for(int i=0;i<bvos.length;i++){
				  map.put(PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("crowno")),
						  PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("crowno")));
				  //list.add(PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("crowno")));
			  }
		}
		if(bodyvos!=null&&bodyvos.length!=0){
			for(int j=0;j<bodyvos.length;j++){
				if(map.containsKey(PuPubVO.getString_TrimZeroLenAsNull(
						bodyvos[j].getAttributeValue("vcrowno")))){
					list.add(j);
				}
			}
		}
		int[] a=listtoInt(list);
		if(a!=null&&a.length!=0){
			//删除多行
			getBillCardPanel().getBillModel().delLine(a);
		}
		super.onBoLineDel();
		
	}
	private int[] listtoInt(List list) {
		int[] a;
		if(list==null||list.size()==0){
			return null;
		}
		else{
			a=new int[list.size()];
			for(int i=0;i<list.size();i++){
				a[i]=PuPubVO.getInteger_NullAs(list.get(i), -1);
			}
			return a;
		}
	}
}
