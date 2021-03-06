package nc.ui.xcgl.sample;

import java.util.List;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.pub.bill.IBillItem;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.helper.IndexFineHeler;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 送样单UI类。
 * @author yangtao
 * @date 2013-12-10 上午09:16:09
 */
public class ClientUI extends XCDefBillManageUI implements BillCardBeforeEditListener {

	private static final long serialVersionUID = 407579273837493957L;
	
    public ClientUI() {
    	 super();
    	 initEventListener();
	}

    protected void initEventListener() {
    	super.initEventListener();
    	getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
    	}


	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String key=e.getKey();
		int    pos=e.getPos();
		if(pos==IBillItem.HEAD){
			if("pk_invmandoc".equalsIgnoreCase(key)){
				String minarea=PuPubVO.getString_TrimZeroLenAsNull(
						getBillCardPanel().getHeadItem("pk_minarea").getValueObject());//部门
				if(minarea==null){
					this.showWarningMessage("取样单位为空，请先选择取样单位!");
				}
			}
			
			
		}
		return super.beforeEdit(e);
	}

	public boolean beforeEdit(BillItemEvent e) {
//		String key=e.getItem();
//		int    pos=e.getPos();
//		if(pos==IBillItem.HEAD){
//			if("pk_invmandoc".equals(e.getItem().getKey())){
//				String minarea=PuPubVO.getString_TrimZeroLenAsNull(
//						getBillCardPanel().getHeadItem("pk_minarea").getValueObject());//部门
//				if(minarea==null){
//					this.showWarningMessage("取样单位为空，请先选择取样单位!");
//					getBillCardPanel().stopEditing();
//				}
//			}
			return true;
//		}
	}

	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}

	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}
	/**
	 * 实例化界面编辑前后事件处理, 如果进行事件处理需要重载该方法 创建日期：(2004-1-3 18:13:36)
	 */
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}

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
		
//		if(this.isListPanelSelected()){
//			int RowCount =this.getBillListPanel().getHeadTable().getRowCount();
//			if(RowCount>0){
//				for(int i=0;i<RowCount;i++){
//					String sampleno=(String)this.getBillListPanel().getHeadBillModel().getValueAt(i, "vsamplenumber");
//					if(sampleno.length()==32){
//						sampleno=XcPubTool.decrypt(sampleno);
//					}
//					this.getBillListPanel().getHeadBillModel().setValueAt(sampleno, i, "vsamplenumber");
//				}
//			}
			}
		
//	}

	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
//		String sampleno=PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("vsamplenumber"));
//		if(sampleno.length()==32){
//			sampleno=XcPubTool.decrypt(sampleno);
//		}
//		vo.setAttributeValue("vsamplenumber", sampleno);
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
//		if(this.isListPanelSelected()){
//			int RowCount =this.getBillListPanel().getHeadTable().getRowCount();
//			if(RowCount>0){
//				for(int i=0;i<RowCount;i++){
//					String sampleno=(String)this.getBillListPanel().getHeadBillModel().getValueAt(i, "vsamplenumber");
//					if(sampleno.length()==32){
//						sampleno=XcPubTool.decrypt(sampleno);
//					}
//					this.getBillListPanel().getHeadBillModel().setValueAt(sampleno, i, "vsamplenumber");
//				}
//			}
//			if(vos!=null&&vos.length!=0){
//				for(int i=0;i<vos.length;i++){
//					String sampleno=PuPubVO.getString_TrimZeroLenAsNull(vos[i].getAttributeValue("vsamplenumber"));
//					if(sampleno.length()==32){
//						sampleno=XcPubTool.decrypt(sampleno);
//					}
//					vos[i].setAttributeValue("vsamplenumber", sampleno);
//					
//				}
//			}
//			
//		}
//		else{
//			int RowCount =this.getBillCardPanel().getHeadPanel().getTable().getRowCount();
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
//	
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
				
		getBillCardPanel().getHeadItem("dbilldate").setValue(ClientEnvironment.getInstance().getDate());
				
		getBillCardPanel().getTailItem("voperatorid").setValue(ClientEnvironment.getInstance().getUser().getPrimaryKey());
				
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(ClientEnvironment.getInstance().getDate());
				
		getBillCardPanel().getHeadItem("dbilldate").setValue(ClientEnvironment.getInstance().getDate());
				
		getBillCardPanel().getHeadItem("vbillstatus").setValue(IBillStatus.FREE);
				
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_sample);
	}

	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_sample, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}
	
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		int pos  = e.getPos();
		if(BillItem.HEAD == pos){
			 String key = e.getKey();
			 if("pk_invmandoc".equalsIgnoreCase(key)||"pk_minarea".equalsIgnoreCase(key) || "vdef20".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
				 
		String invmandoc=PuPubVO.getString_TrimZeroLenAsNull(
				getBillCardPanel().getHeadItem("pk_invmandoc").getValueObject());
		
		String minarea=PuPubVO.getString_TrimZeroLenAsNull(
				getBillCardPanel().getHeadItem("pk_minarea").getValueObject());
		
		String invmandoc1=PuPubVO.getString_TrimZeroLenAsNull(
				getBillCardPanel().getHeadItem("vdef20").getValueObject());
		
		if(invmandoc==null||minarea==null || invmandoc1==null){
			return;
		}
		IndexParaVO para=new IndexParaVO();
		para.setAttributeValue("pk_invmandoc",invmandoc );
		para.setAttributeValue("pk_minarea", minarea);
		para.setPk_invmandoc1(invmandoc1);
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
	public boolean isStockBill() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
