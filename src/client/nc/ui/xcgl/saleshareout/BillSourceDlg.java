package nc.ui.xcgl.saleshareout;
import java.awt.Container;

import nc.ui.trade.controller.IControllerBase;
import nc.ui.xcgl.pub.bill.XCMBillSourceDLG;
/**
 * 参照对话框
 * @author jay
 * @date 2014-1-8 下午02:56:47
 */
public class BillSourceDlg extends XCMBillSourceDLG {
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
//	public void setSpiltBillVos(AggregatedValueObject[] spiltBillVos) {
//		this.spiltBillVos = spiltBillVos;
//	}
//	public String[] getSpiltFields() {
//		return spiltFields;
//	}
//	public void setSpiltFields(String[] spiltFields) {
//		this.spiltFields = spiltFields;
//	}
//	public String[] getSpiltFields1() {
//		return spiltFields1;
//	}
//	public void setSpiltFields1(String[] spiltFields1) {
//		this.spiltFields1 = spiltFields1;
//	}
	/**
	 * 修改过滤查询条件；
	 * 
	 */
	public String getHeadCondition() {
		String sql=null;
		//修改 过滤条件.		
		return sql=" xcgl_weighdoc.vbillstatus='1' and xcgl_weighdoc.isref='N' ";
	}          
	protected boolean isHeadCanMultiSelect() {
		return false;
	}
	/**
	 * 子表条件语句
	 * @return
	 */
	public String getBodyCondition() {
		return " xcgl_weighdoc_b.ureserve2='Y' ";
	}
//	@Override
//	public String getHeadSpitChar() {
//		return "x";
//	}
//	@Override
//	public String getRefBillType() {
//		return PubBillTypeConst.billtype_saleweighdoc_ref;
//	}
	@Override
	public IControllerBase getUIController() {
		return new nc.ui.xcgl.saleweighdoc.Controller();
	}
	@Override
	public String getPk_invbasdocName() {
		return null;
	}
	@Override
	public String getPk_invmandocName() {
		return null;
	}
//	@Override
//	public ReportBaseVO[] queryHeadAndBodyVOs(String strWhere,
//		String pk_invbasdocName, String pk_invmandocName) throws Exception {
////		ReportBaseVO[] vos=super.queryHeadAndBodyVOs(strWhere, pk_invbasdocName, pk_invmandocName);
////		for(int i =0;i<vos.length;i++){
////		  String sampleno=(String)vos[i].getAttributeValue("vsamplenumber");
////		  if(sampleno.length()==32){
////			 sampleno=XcPubTool.decrypt(sampleno);
////		    }
////		  vos[i].setAttributeValue("vsamplenumber", sampleno);
////		}
////		return vos;
//			return null;
//	}
}
