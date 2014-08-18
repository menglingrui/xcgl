package nc.ui.xcgl.assayres;

import java.util.List;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.helper.IndexFineHeler;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 *类说明：化验结果
 *@author jay
 *@version 1.0   
 *创建时间：2013-12-26下午04:46:58
 */
public class ClientUI extends XCDefBillManageUI implements BillCardBeforeEditListener{
	private static final long serialVersionUID = 1L;
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}
	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}

	public ClientUI() {
		super();
		initEventListener();
	}
	  protected void initEventListener() {
	    	super.initEventListener();
	    	getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	    	}
	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
//	@Override
//	public String getQueryDetailBtnName() {
//		
//		return "化验指标查看";
//	}
//
//	@Override
//	public String getSonBillType() {
//		
//		return PubBillTypeConst.billtype_assayquota;
//	}
//
//	@Override
//	public String getSonDigName() {
//		
//		return "assayquota";
//	}
//
//	@Override
//	public String getSonDigTile() {
//		
//		return "指标定义";
//	}
//
//	@Override
//	public String getSonQueryBillType() {
//		
//		return PubNodeModelConst.NodeModel_assayquotaview;
//	}

	public boolean beforeEdit(BillItemEvent e) {
		if("pk_invmandoc".equals(e.getItem().getKey())){
			String minarea=PuPubVO.getString_TrimZeroLenAsNull(
					getBillCardPanel().getHeadItem("pk_minarea").getValueObject());//部门
			if(minarea==null){
				this.showWarningMessage("取样单位为空，请先选择取样单位!");
				getBillCardPanel().stopEditing();
			}
		}
		return true;
	}

	public void SonafterButtonClick(String btg) throws BusinessException {
	}

	public void SonbeforeButtonClick(String btg) throws BusinessException {
	}
	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		getBillCardPanel().getHeadItem("uiswater").setValue(UFBoolean.FALSE);//是否水分
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getTailItem("voperatorid").setValue(
				ClientEnvironment.getInstance().getUser().getPrimaryKey());
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_assayres);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_assayres, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
//	protected void initPrivateButton() {
//		ButtonVO btnvo1 = new ButtonVO();
//		btnvo1.setBtnNo(PuBtnConst.ckmx);
//		btnvo1.setBtnName(getQueryDetailBtnName());
//		btnvo1.setBtnChinaName(getQueryDetailBtnName());
//		btnvo1.setBtnCode(null);//code最好设置为空
//		addPrivateButton(btnvo1);
//		super.initPrivateButton();
//		
//	
//	}
	
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {

//		System.out.println("");
//		if(this.isListPanelSelected()){
//			int RowCount =this.getBillListPanel().getBodyTable().getRowCount();
//			if(RowCount>0){
//				for(int i=0;i<RowCount;i++){
//					String sampleno=(String)this.getBillListPanel().getBodyBillModel().getValueAt(i, "vsamplenumber");
//					if(sampleno.length()==32){
//						 sampleno=XcPubTool.decrypt(sampleno);
//					}
//					
//					this.getBillListPanel().getBodyBillModel().setValueAt(sampleno, i, "vsamplenumber");
//				}
//			
//			}
//		}else{
//			int RowCount =this.getBillCardPanel().getBodyPanel().getTable().getRowCount();
//			if(RowCount>0){
//				for(int i=0;i<RowCount;i++){
//					String sampleno=(String)this.getBillCardPanel().getBodyValueAt(i, "vsamplenumber");
//					if(sampleno.length()==32){
//					sampleno=XcPubTool.decrypt(sampleno);
//					}
//					this.getBillCardPanel().setBodyValueAt(sampleno, i, "vsamplenumber");
//				}
//			
//			}
//		}
		
	
	}
	@Override
	protected void initSelfData() {
		ButtonObject btnobj = getButtonManager().getButton(IBillButton.Line);
		if (btnobj != null) {
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.CopyLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.InsLine));
			btnobj.removeChildButton(getButtonManager().getButton(IBillButton.PasteLinetoTail));
		}
		super.initSelfData();
	}
	
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		int pos  = e.getPos();
		if(BillItem.HEAD == pos){
			 String key = e.getKey();
			 if("pk_invmandoc".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
					String invmandoc=PuPubVO.getString_TrimZeroLenAsNull(
							getBillCardPanel().getHeadItem("pk_invmandoc").getValueObject());
					String minarea=PuPubVO.getString_TrimZeroLenAsNull(
							getBillCardPanel().getHeadItem("pk_minarea").getValueObject());
					IndexParaVO para=new IndexParaVO();
					para.setAttributeValue("pk_invmandoc",invmandoc );
					para.setAttributeValue("pk_minarea", minarea);
					para.setPk_corp(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
					try {
						int[] s=delLine();
						if(s!=null&&s.length!=0){
							//删除多行
							getBillCardPanel().getBillModel().delLine(s);
						}
						List<LabIndexSetBVO> list = IndexFineHeler.getIndexFine(para);
						addBodyDatas(list);
					} catch (BusinessException e1) {
						this.showErrorMessage(e1.getMessage());
						e1.printStackTrace();
					}
			 }
		}
	}
	private int[] delLine(){
		int[] a;
	    	CircularlyAccessibleValueObject[] bodyvos = 
	    		getBillCardWrapper().getBillCardPanel().getBillModel().getBodyValueVOs(
	    				getUIControl().getBillVoName()[2]);
		if(bodyvos==null||bodyvos.length==0){
			return null;
		}
		else{
			a=new int[bodyvos.length];
		for( int i=0;i<bodyvos.length;i++){
		
			a[i]=i;	
			
		}
		return a;
		}
		}
	private void addBodyDatas(List<LabIndexSetBVO> list) {
		if(list==null||list.size()==0){
			return;
		}
		else{
			for(int i=0;i<list.size();i++){
				try {
					getBillCardWrapper().addLine();
					getBillCardPanel().setBodyValueAt(
                  		  list.get(i).getPk_invmandoc(), i, "pk_invmandoc");
                    getBillCardPanel().setBodyValueAt(
                  		  list.get(i).getPk_invbasdoc(), i, "pk_invbasdoc");
                    getBillCardPanel().execBodyFormula( i, "invname");
                    getBillCardPanel().setBodyValueAt(
                    		ZmPubTool.getInvNameByPk(list.get(i).getPk_invbasdoc()), i, "invname");
				} catch (Exception e) {
		            this.showErrorMessage(e.getMessage());
					e.printStackTrace();
				} 
			}
		}
	}
	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
	}
	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}
	@Override
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}
}
