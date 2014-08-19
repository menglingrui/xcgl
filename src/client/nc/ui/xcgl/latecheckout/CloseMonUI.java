package nc.ui.xcgl.latecheckout;
import javax.swing.JComponent;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PuBtnConst;
/**
 * @author mlr
 * 结算
 */
public class CloseMonUI extends BillCardUI implements BillCardBeforeEditListener{
	private static final long serialVersionUID = 1L;	
	private String accountyear = null;
	//重写构造函数
	public CloseMonUI(){
		super();
		//初始化界面 特性
		init();
		//初始化 数据
		try {
			initData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void initEventListener() {
		super.initEventListener();
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
	}
	public void initData() throws Exception{
		//获取当前日期   当前会计月   
		UFDate date=_getDate();
		//日期转型 为字符串格式，PuPubVO公共类型处理工具包
		if(PuPubVO.getString_TrimZeroLenAsNull(accountyear)==null)
			accountyear = String.valueOf(date.getYear());
		String corp = _getCorp().getPrimaryKey();
		AggregatedValueObject bill = CloseMonHelper.getCloseMonByYear(accountyear, corp);
		if(bill == null){
			bill = CloseMonHelper.initCloseMonByDate(accountyear, corp);
		}
		getBillCardPanel().setBillValueVO(bill);
	}	
	private void init(){
		getBillCardPanel().setEnabled(true);
		getBillCardPanel().getBillTable().removeSortListener();
	}
	//注册控制器
	@Override
	protected ICardController createController() {
		return new CloseMonUICtrl();
	}
	
	@Override
	public String getRefBillType() {
		return null;
	}

	@Override
	protected void initSelfData() {
		
	}

	@Override
	public void setDefaultData() throws Exception {
		
	}

	//注册自定义按钮
	@Override
	protected void initPrivateButton() {		
		//注册自定按钮
		//结算
		ButtonVO btnVo1 = new ButtonVO();
		btnVo1.setBtnNo(PuBtnConst.btn_closemon);
		btnVo1.setBtnCode(null);
		btnVo1.setBtnName("结账");
		btnVo1.setBtnChinaName("结账");
		btnVo1.setBtnName("结账");
		btnVo1.setHintStr("结账");
		btnVo1.setOperateStatus(new 
				int[]{IBillOperate.OP_NOTEDIT,
						IBillOperate.OP_INIT});
		btnVo1.setBusinessStatus(new 
				int[]{IBillStatus.FREE});
		addPrivateButton(btnVo1);
		//取消结算
		ButtonVO btnVo2 = new ButtonVO();
		btnVo2.setBtnNo(PuBtnConst.btn_disclosemon);
		btnVo2.setBtnCode(null);
		btnVo2.setBtnName("取消结账");
		btnVo2.setBtnChinaName("取消结账");
		btnVo2.setBtnName("取消结账");
		btnVo2.setHintStr("会计月取消结账");
		addPrivateButton(btnVo2);
		//刷新结算
		ButtonVO btnVo3 = new ButtonVO();
		btnVo3.setBtnNo(PuBtnConst.btn_refresh);
		btnVo3.setBtnCode(null);
		btnVo3.setBtnName("刷新");
		btnVo3.setBtnChinaName("刷新");
		btnVo3.setBtnName("刷新");
		btnVo3.setHintStr("刷新");
		addPrivateButton(btnVo3);
	}
	
	// 编辑后 事件处理
	@Override
	public void afterEdit(BillEditEvent e) {
		String key = e.getKey();		

		if("pk_accperiod".equalsIgnoreCase(key)){
			Object keyValue = e.getValue();
			if(null == keyValue || "".equals(keyValue.toString()) ){
				getBillCardPanel().getBillModel().clearBodyData();
				return;
			}
			try {
				accountyear = keyValue.toString();
				initData();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				showErrorMessage(e1.getMessage()==null?"":e1.getMessage());
			}
		}
		super.afterEdit(e);
	}

	
	//注册事件处理类
	@Override
	protected CardEventHandler createEventHandler() {
		return new CloseMonUIEH(this,getUIControl());
	}
	//更新 表单数据
//	private void updateBodyData(String year) {
//		AggregatedValueObject[] headvos = null;
//		try {
//			headvos = HYPubBO_Client.queryBillVOByCondition(CloseMonHeaderVO.class, "accountyear ="+year);
//		} catch (UifException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		if(null != headvos && headvos.length > 0){
//			AggregatedValueObject headvo = headvos[0];
//			getBillCardPanel().setBillValueVO(headvo);
////			CloseMonBodyVO[] bodys = (CloseMonBodyVO[]) headvo.getChildrenVO();
//		}
//		
//		AccountCalendar canl = AccountCalendar.getInstance();//获取基准会计方案
////		if())
//		try {
//			//设置 当前会计时间
//			canl.set(year);
//			String acc = canl.getYearVO().getPk_accperiod();
////			getBillCardPanel().setHeadItem("pk_accperiod", pk);
//			String accScheme = canl.getYearVO().getPk_accperiodscheme();
//			String periodYear = canl.getYearVO().getPeriodyear();
//			
//			AccperiodmonthVO[] months = canl.getMonthVOsOfCurrentYear();
//			CloseMonHeaderVO hvo = new CloseMonHeaderVO();
//
//			hvo.setPk_accperiod(acc);
//			hvo.setPk_accperiodscheme(accScheme);
//			hvo.setCyear(periodYear);
//			CloseMonBodyVO[] bvo = null;			
//			
//			if(null != months && months.length > 0){
//				bvo = new CloseMonBodyVO[months.length];
//				for (int i = 0; i < months.length; i++) {
//					bvo[i] = new CloseMonBodyVO();
//					bvo[i].setVmonth(months[i].getMonth());
//					
//					bvo[i].setDstartdate(months[i].getBegindate());
//					bvo[i].setDenddate(months[i].getEnddate());
//					bvo[i].setPk_accperiodmonth(months[i].getPk_accperiodmonth());
//					bvo[i].setPk_accperiodscheme(months[i].getPk_accperiodscheme());
//				}
//			}
//			CloseMonVO accvo = new CloseMonVO(); 
//			accvo.setParentVO(hvo);
//			accvo.setChildrenVO(bvo);
//			
//			getBillCardPanel().setBillValueVO(accvo);
//		} catch (InvalidAccperiodExcetion e) {
//			e.printStackTrace();
//		}
//	}
	public boolean beforeEdit(BillItemEvent e) {
		if(e.getItem().getKey().equals("pk_accperiod")){
			String key = e.getItem().getKey();
				//	String kuangqu = getBillCardPanel().getHeadItem("pk_accperiod").getValue();
	          
					JComponent jf = getBillCardPanel().getHeadItem(key).getComponent();
					if (jf instanceof UIRefPane) {
					UIRefPane panel = (UIRefPane) jf;
					panel.setNotLeafSelectedEnabled(false);
					panel
							.getRefModel()
							.addWherePart(
									" and bd_accperiod.pk_accperiodscheme ='0001AA00000000000001' "	);		
									}
		   
	       return true;
		}
		return true;
	}	
}
