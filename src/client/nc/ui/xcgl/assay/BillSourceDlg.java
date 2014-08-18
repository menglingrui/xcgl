package nc.ui.xcgl.assay;

import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG;
/**
 * 参照对话框
 * @author yangtao
 * @date 2014-1-2 下午02:56:47
 */
public class BillSourceDlg extends XCMBillSourceDLG {
	/**
	 * @date 2014-1-2下午02:57:01
	 */
	private static final long serialVersionUID = 3886184900807493717L;
	
//	protected AggregatedValueObject[] spiltBillVos = null;
//	//分担维度 表头
//	protected String[] spiltFields=null;
//	//分担维度 表体
//	protected String[] spiltFields1=null;

	public BillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
				businessType, templateId, currentBillType, parent);

	}
	
//	public AggregatedValueObject[] getSpiltBillVos() {
//		return spiltBillVos;
//	}
//
//	public void setSpiltBillVos(AggregatedValueObject[] spiltBillVos) {
//		this.spiltBillVos = spiltBillVos;
//	}
//
//	public String[] getSpiltFields() {
//		return spiltFields;
//	}
//
//	public void setSpiltFields(String[] spiltFields) {
//		this.spiltFields = spiltFields;
//	}
//
//	public String[] getSpiltFields1() {
//		return spiltFields1;
//	}
//
//	public void setSpiltFields1(String[] spiltFields1) {
//		this.spiltFields1 = spiltFields1;
//	}

	/**
	 * 修改过滤查询条件；
	 * yangtao
	 */
	public String getHeadCondition() {
		String sql=null;
		//修改 过滤条件.		
		return sql=" xcgl_sample.vbillstatus='1' and xcgl_sample.isref='N'";
	}          
	
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
	@Override
	public IControllerBase getUIController() {
	
		return new nc.ui.xcgl.sample.Controller();
	}

	@Override
	public String getPk_invbasdocName() {
	
		return null;
	}

	@Override
	public String getPk_invmandocName() {
	
		return null;
	}

	/**
	 * 在参照模板上解析加密过的样品编码。使之能正常显示；
	 * yangtao
	 */
//	@Override
//	public ReportBaseVO[] queryHeadAndBodyVOs(String strWhere,
//			String pk_invbasdocName, String pk_invmandocName) throws Exception {
//		ReportBaseVO[] vos=super.queryHeadAndBodyVOs(strWhere, pk_invbasdocName, pk_invmandocName);
////		for(int i =0;i<vos.length;i++){
////		  String sampleno=(String)vos[i].getAttributeValue("vsamplenumber");
////		  if(sampleno.length()==32){
////			 sampleno=XcPubTool.decrypt(sampleno);
////		    }
////		  vos[i].setAttributeValue("vsamplenumber", sampleno);
////		}
//		return vos;
//	}
}
