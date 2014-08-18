package nc.ui.xcgl.soct;

import javax.swing.JComponent;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.ui.xcgl.pub.bill.XCMutiDefBillManageUI;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PuBtnConst;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

/**
 * 销售合同
 * @author yangtao
 * @date 2014-2-19 上午09:59:29
 */
public class ClientUI  extends XCMutiDefBillManageUI implements BillCardBeforeEditListener{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isLinkQueryEnable() {
		
		return true;
	}

	@Override
	protected AbstractManageController createController() {
		
		return new Controller();
	}

	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
		
	}

	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
		
		
	}
	/**
	 * 实例化界面编辑前后事件处理, 如果进行事件处理需要重载该方法 创建日期：(2004-1-3 18:13:36)
	 */
	protected ManageEventHandler createEventHandler() {
		return new EventHandler(this, getUIControl());
	}

	@Override
	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
	}

	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		getBillCardPanel().getHeadItem("dbilldate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getTailItem("voperatorid").setValue(
				ClientEnvironment.getInstance().getUser().getPrimaryKey());
		getBillCardPanel().getHeadItem("vbillno").setValue(getBillNo());		
		getBillCardPanel().getTailItem("dmakedate").setValue(
				ClientEnvironment.getInstance().getDate());
		getBillCardPanel().getHeadItem("vbillstatus")
				.setValue(IBillStatus.FREE);
		getBillCardPanel().getHeadItem("pk_billtype").setValue(PubBillTypeConst.billtype_soct);
	}
	@Override
	protected String getBillNo() throws Exception {
		return HYPubBO_Client.getBillNo(PubBillTypeConst.billtype_soct, 
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), null, null);
	}

	protected void initPrivateButton() {
		ButtonVO btnvo1 = new ButtonVO();
		btnvo1.setBtnNo(PuBtnConst.open);
		btnvo1.setBtnName("打开");
		btnvo1.setBtnChinaName("打开");
		btnvo1.setBtnCode(null);//code最好设置为空
		btnvo1.setOperateStatus(new int[]{
				IBillOperate.OP_NOTEDIT,
		});
		addPrivateButton(btnvo1);
		
		ButtonVO btnvo2 = new ButtonVO();
		btnvo2.setBtnNo(PuBtnConst.close);
		btnvo2.setBtnName("关闭");
		btnvo2.setBtnChinaName("关闭");
		btnvo2.setBtnCode(null);//code最好设置为空
		btnvo2.setOperateStatus(new int[]{
				IBillOperate.OP_NOTEDIT,
				});
		addPrivateButton(btnvo2);
		
		ButtonVO btnvo3 = new ButtonVO();
		btnvo3.setBtnNo(PuBtnConst.revise);
		btnvo3.setBtnName("修订");
		btnvo3.setBtnChinaName("修订");
		btnvo3.setBtnCode(null);//code最好设置为空
		btnvo3.setOperateStatus(new int[]{
				IBillOperate.OP_NOTEDIT,
				});
		btnvo3.setBusinessStatus(new int[]{
				IBillStatus.CHECKPASS,
		});
		addPrivateButton(btnvo3);
		
		super.initPrivateButton();
	}
	
	public boolean beforeEdit(BillItemEvent e) {
		
		return false;
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		String key=e.getKey();
		String cttermtype=PuPubVO.getString_TrimZeroLenAsNull(
				getBillCardPanel().getHeadItem("pk_cttermtype").getValueObject());
		/**
		 *合同条款也签编码xcgl_soct_b2 
		 */
		String tablecode=e.getTableCode();
		if(tablecode.equalsIgnoreCase("xcgl_soct_b2")&&key.equalsIgnoreCase("termname")){
			if(cttermtype==null||cttermtype.trim()==""){
				this.showWarningMessage("表头合同条款类型为空，请先选择表头合同条款类型!");
			}
			else{
				JComponent jf=getBillCardPanel().getBodyItem("xcgl_soct_b2", "termname").getComponent();
				if(jf instanceof UIRefPane){
				UIRefPane ref=(UIRefPane) jf;
				ref.setNotLeafSelectedEnabled(false);
				ref.getRefModel().addWherePart(" and xcgl_cttermset.pk_cttermtype='"+cttermtype+"'");
				}
			}
		}
		return true;
		//return super.beforeEdit(e);
		
	}
 @Override
    public void afterEdit(BillEditEvent e) {
	super.afterEdit(e);
//	int pos=e.getPos();
//	if(pos==IBillItem.HEAD&&e.getKey().equalsIgnoreCase("pk_cttermtyp")){
//		String cttermtype=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_cttermtype").getValueObject());
//		if(cttermtype!=null&&cttermtype.trim()!=""){
//			try {
//				List<CtTermsetVO> list = (List<CtTermsetVO>) QueryHelper.retrieveByClause(CtTermsetVO.class, " pk_cttermtype='"+cttermtype+"' and isnull(dr,0)=0");
//				if(list!=null&&list.size()>0){
//					for(int i=0;i<list.size();i++){
//						
//					}
//				}
//			} catch (BusinessException e1) {
//				this.showErrorMessage(e1.getMessage());
//				e1.printStackTrace();
//			}
//		}
//	}
	
	
}
	protected BusinessDelegator createBusinessDelegator() {
	   return new ProAcceptDelegator();
	}

}
