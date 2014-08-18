package nc.ui.xcgl.pricemanage;

import nc.jdbc.framework.processor.ArrayProcessor;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCDefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pricemanage.checkClassInterface;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * 价格维护ClientUI
 * @author ddk
 *
 */
@SuppressWarnings("serial")
public class ClientUI extends XCDefBillManageUI{
	EventHandler h=(EventHandler) createEventHandler();
	@Override
	public boolean isLinkQueryEnable() {
		return true;
	}

	@Override
	protected AbstractManageController createController() {
		return new Controller();
	}

	@Override
	protected ManageEventHandler createEventHandler() {
	
		return new EventHandler(this,getUIControl());
	}
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}
	
	public java.lang.Object getUserObject() {
		return new checkClassInterface();
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
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		getBillCardPanel().getHeadItem("vyear").setValue(
				ClientEnvironment.getInstance().getDate().toString().substring(0, 4));
		getBillCardPanel().getHeadItem("vmonth").setValue(
				ClientEnvironment.getInstance().getDate().toString().substring(5, 7));
	}
	
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		int pos  = e.getPos();
		if(BillItem.HEAD == pos){
			 String key = e.getKey();
			 if("pk_invmandoc".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
			 }
		}
		/**
		 * 表头日期编辑完后带出表体，并赋默认值
		 * @author lxh
		 */
		String sItemkey=e.getKey();
		
		if("vyear".equals(sItemkey)||"pk_invmandoc".equals(sItemkey)||"vmonth".equals(sItemkey)||"vsourcemanage".equals(sItemkey)){
		try {
			if("vyear".equals(e.getKey())){
				ZmPubTool.regexYear(PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vyear").getValueObject()));
				String m=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vmonth").getValueObject());
				if(m==null){
					throw new BusinessException("请输入月份");
				}else{
					ZmPubTool.regexMonth(m);
				}
			}
			if("vmonth".equals(e.getKey())){
				ZmPubTool.regexMonth(PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vmonth").getValueObject()));
				String y=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vyear").getValueObject());
				if(y==null){
					throw new BusinessException("请输入年份");
				}else{
					ZmPubTool.regexYear(y);
				}
			}
		} catch (Exception e1) {
			nc.ui.pub.beans.MessageDialog.showHintDlg(this, "提示",e1.toString());
		}
			int rowcount=getBillCardPanel().getBillModel().getRowCount();
			int [] rows=new int[rowcount];
			for(int i=0;i<rowcount;i++){
				rows[i]=i;
			}
			getBillCardPanel().getBillModel().delLine(rows);
//			getBillCardPanel().getBillModel().clearBodyData();
//			String date=PuPubVO.getString_TrimZeroLenAsNull(
//					getBillCardPanel().getHeadItem("ddate").getValueObject());
//			String date=ClientEnvironment.getInstance().getDate().toString();
			String pk_invmandoc=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_invmandoc").getValueObject());
			String sourcemanage=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vsourcemanage").getValueObject());
			Object[] o2=null;
			try {
				String sql1="select b.vsourcename from xcgl_sourcemanage b where b.pk_sourcemanage='"+sourcemanage+"'";
				o2=(Object[]) QueryHelper.executeQuery(sql1, new ArrayProcessor());
				
			} catch (BusinessException e4) {
				e4.printStackTrace();
			}
			String vmemo=null;
			if(o2==null){
				 vmemo="";
			}else{
				 vmemo=PuPubVO.getString_TrimZeroLenAsNull(o2[0]);
			}
		String sql=	" select a.measname " +
			" from bd_measdoc a " +
			" where a.pk_measdoc " +
			" in  (select b.pk_measdoc " +
			" from bd_invbasdoc b " +
			" join bd_measdoc a " +
			" on b.pk_measdoc = a.pk_measdoc " +
			" where b.pk_invbasdoc in " +
			" (select c.pk_invbasdoc " +
			" from bd_invmandoc c " +
			" join bd_invbasdoc b " +
			" on b.pk_invbasdoc = c.pk_invbasdoc " +
			" where c.pk_invbasdoc = '" +pk_invmandoc+ "'))";
			
			try {
				Object []o=(Object[]) QueryHelper.executeQuery(sql, new ArrayProcessor());
				
				if(o==null){
					nc.ui.pub.beans.MessageDialog.showHintDlg(this, "提示","请先选择金属名称" );
//					getBillCardPanel().getHeadItem("ddate").setValue("");
				}else{
				String measname=PuPubVO.getString_TrimZeroLenAsNull(o[0]);
				
				String year=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vyear").getValueObject());
				String month=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("vmonth").getValueObject());
//				String year=date.substring(0, 4);
				ZmPubTool.regexYear(year);
				ZmPubTool.regexMonth(month);
				int y=Integer.parseInt(year);
//				String month=date.substring(5, 7);
				int m=Integer.parseInt(month);
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
					for(int i=1;i<=31;i++){
						try {
							h.onBoLineAdd();
							String day=String.valueOf(i);
							String x=year+"-"+month+"-"+day;
//							Date x = format.parse(str);
							getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
							getBillCardPanel().getBillModel().setValueAt(measname, i-1,"pk_measdoc");
//							getBillCardPanel().getBillModel().setValueAt(sourcemanage, i-1,"vmemo");
							getBillCardPanel().setBodyValueAt(vmemo, i-1, "vmemo");
							getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), i-1, "pk_corp");
							if(i<10){
								x=year+"-"+month+"-0"+day;
								getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
								getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								
							}else if(i==10){
								getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
							
							}
							else if(i>=11&&i<=20){
								getBillCardPanel().getBillModel().setValueAt("中旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
							
							}else{
								getBillCardPanel().getBillModel().setValueAt("下旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}else if(m==4||m==6||m==9||m==11){
					for(int i=1;i<=30;i++){
						try {
							h.onBoLineAdd();
							String day=String.valueOf(i);
							String x=year+"-"+month+"-"+day;
//							Date x = format.parse(str);
							getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
							getBillCardPanel().getBillModel().setValueAt(measname, i-1,"pk_measdoc");
							getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
							getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), i-1, "pk_corp");
							if(i<10){
								x=year+"-"+month+"-0"+day;
								getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
								getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								
							}else if(i==10){
								getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								
							}
							else if(i>=11&&i<=20){
								getBillCardPanel().getBillModel().setValueAt("中旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								
							}else{
								getBillCardPanel().getBillModel().setValueAt("下旬", i-1,"vmanth");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}else{
					if ((y%4 == 0 && y%100 != 0) || y%400 == 0){
						for(int i=1;i<=29;i++){
							try {
								h.onBoLineAdd();
								String day=String.valueOf(i);
								String x=year+"-"+month+"-"+day;
//								Date x = format.parse(str);
								getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
								getBillCardPanel().getBillModel().setValueAt(measname, i-1,"pk_measdoc");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), i-1, "pk_corp");
								if(i<10){
									x=year+"-"+month+"-0"+day;
									getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
									getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
									
								}else if(i==10){
									getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
									
								}
								else if(i>=11&&i<=20){
									getBillCardPanel().getBillModel().setValueAt("中旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
									
								}else{
									getBillCardPanel().getBillModel().setValueAt("下旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
									
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}else{
						for(int i=1;i<=28;i++){
							try {
								h.onBoLineAdd();
								String day=String.valueOf(i);
								String x=year+"-"+month+"-"+day;
//								Date x = format.parse(str);
								getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
								getBillCardPanel().getBillModel().setValueAt(measname, i-1,"pk_measdoc");
								getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), i-1, "pk_corp");
								if(i<10){
									x=year+"-"+month+"-0"+day;
									getBillCardPanel().getBillModel().setValueAt(x, i-1,"ddate");
									getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								}else if(i==10){
									getBillCardPanel().getBillModel().setValueAt("上旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								}
								else if(i>=11&&i<=20){
									getBillCardPanel().getBillModel().setValueAt("中旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								}else{
									getBillCardPanel().getBillModel().setValueAt("下旬", i-1,"vmanth");
									getBillCardPanel().getBillModel().setValueAt(vmemo, i-1,"vmemo");
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
				}
			} catch (BusinessException e2) {
				e2.printStackTrace();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
 		}
	}
	
	@Override
	public boolean isStockBill() {
		return false;
	}
}
