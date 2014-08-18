package nc.ui.xcgl.pub.bill;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.zmpub.pub.bill.FlowManageEventHandler;
import nc.ui.zmpub.pub.bill.LogNumRefUFPanel;
import nc.ui.zmpub.pub.bill.SonDefBillManageUI;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pub.itf.ISonExtendVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
import nc.vo.zmpub.pub.bill.HYChildSuperVO;
/**
 * 支持两种孙表的UI类
 * @author mlr
 */
public abstract class XCSonExtDefBillManageUI extends SonDefBillManageUI{
	private static final long serialVersionUID = 6673917648452305655L;
	
	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		
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
		
	}

	@Override
	public void setDefaultData() throws Exception {
		super.setDefaultData();
	}
	public boolean beforeEdit(BillItemEvent e) {
		return true;
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		boolean b=super.beforeEdit(e);
		if(b==false){
			return b;
		}
		String key = e.getKey();
		int row = e.getRow();
		if (e.getPos() == BillItem.BODY) {
			return ProduceOutSetbeforeEdit(e);
		}  
		return true;
	}
	@Override
	public void afterEdit(BillEditEvent e) {
		try {
			String key = e.getKey();
			int row = e.getRow();
			Object value = e.getValue();
			if (e.getPos() == BillItem.HEAD) {
				getBillCardPanel().execHeadEditFormulas();
			} else {
			    ProductOutSetAfterEdit(e);
			    super.afterEdit(e);
			}
		} catch (Exception e1) {
            this.showErrorMessage(e1.getMessage());
		}
	}	
	public void ProductOutSetAfterEdit(BillEditEvent e) {
		String key = e.getKey();
		int row = e.getRow();
		if (getSonDigName1().equalsIgnoreCase(key)) {
			// 支持批次号 多选拆行 for add mlr
			List<SuperVO> vos = getLotNumbRefPane1()
					.getLotNumbDlg().getLis();
			String crowno = PuPubVO
					.getString_TrimZeroLenAsNull(getBillCardPanel()
							.getBillModel().getValueAt(row, "crowno"));
			String bclassname=getUIControl().getBillVoName()[2];
			SuperVO  vo=null;
			try {
				 vo=(SuperVO) Class.forName(bclassname).newInstance();
			} catch (Exception e1) {
		         this.showErrorMessage(e1.getMessage());
		         return ;
			}
			String bodypk = PuPubVO
					.getString_TrimZeroLenAsNull(getBillCardPanel()
							.getBillModel().getValueAt(row,
									vo.getPKFieldName()));
			String ckey = crowno + bodypk;
			if(vos!=null ||vos.size()>0){
				 Map<String,SuperVO> omap=hl12.get(ckey);
				 for(int i=0;i<vos.size();i++){
					 String pk=vos.get(i).getPrimaryKey();
					 String cnum=(String) vos.get(i).getAttributeValue("crowno");
					 if(omap==null|| omap.size()==0){
						 omap=new HashMap<String,SuperVO>();
						 hl12.put(ckey, omap);
					 }		 
					 omap.put(pk+cnum, vos.get(i));
				 }		
				}
			  setIsExistSonChar1(hl12.get(ckey),e);
			}				
			getLotNumbRefPane1().getLotNumbDlg().setLis(null);
  }

	/**
	 * 编辑后设置材料是否存在的标记
	 * @param map
	 * @param e 
	 */
	public void setIsExistSonChar1(Map<String, SuperVO> map, BillEditEvent e) {
	   boolean isexit=false;
	   if(map!=null&&map.size()>0){
		   for(String key:map.keySet()){
			   SuperVO vo=map.get(key);
			   if(vo!=null){
				   if(vo.getStatus()==VOStatus.DELETED){
					     
				   }else{
					   isexit=true;
					   break;
				   }
			   }
		   }
	   }
	   if(isexit==true){
		   getBillCardPanel().getBillModel().setValueAt("Y", e.getRow(), getSonDigName1()) ;
	   }else{
		   getBillCardPanel().getBillModel().setValueAt("N", e.getRow(), getSonDigName1()) ; 
	   }		
	}
	//以下是对第二种孙表的支持代码 start
	public abstract void SonafterButtonClick(String btg) throws BusinessException;
	
	public abstract void SonbeforeButtonClick(String btg) throws BusinessException;
	
	public abstract  String getQueryDetailBtnName1() ;

	public abstract  String getSonBillType1();

	public abstract String getSonDigName1();

	public abstract String getSonDigTile1();

	public abstract String getSonQueryBillType1();
		
	public abstract void SonafterButtonClick1(String btg) throws BusinessException;
		
	public abstract void SonbeforeButtonClick1(String btg) throws BusinessException;
	
	/**
	 * 设置缓存或者查询后的数据到Map中
	 */
	public void setList(SuperVO[] bvo) {
		super.setList(bvo);
		Map<String, Map<String,SuperVO>> hl1 = new HashMap<String, Map<String,SuperVO>>();
		if (bvo != null && bvo.length > 0) {
			for (SuperVO b : bvo) {
				HYChildSuperVO svo=(HYChildSuperVO) b;
				ISonExtendVO son=(ISonExtendVO) b;
				String crowno =svo.getCrowno();
				String key = b.getPrimaryKey();
				Map<String,SuperVO> map=new HashMap<String,SuperVO>();			
				hl1.put(crowno + key, map);
				List<SuperVO> list=son.getSonVOS1();
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
					   SuperVO vo =list.get(i);
						 String pk=vo.getPrimaryKey();
						 String cnum=(String) vo.getAttributeValue("crowno");
						 map.put(pk+cnum, vo);
					   
					}
				}
			}
		}		
		hl12=hl1;
	}
	// 存放孙表使用材料信息 行号+表体主键 作为key值
	private Map<String, List> hl2 = new HashMap<String, List>();
	//保存前存放孙表数据
	private Map<String, Map<String,SuperVO>> hl12 = new HashMap<String, Map<String,SuperVO>>();
	
	private LogNumRefUFPanel ivjLotNumbRefPane1 = null;
	public LogNumRefUFPanel getLotNumbRefPane1(){
		if (ivjLotNumbRefPane1 == null) {
			try {
				SuperVO vo=(SuperVO) Class.forName(getUIControl().getBillVoName()[1]).newInstance();
				String sonclssname1=null;
				if(vo instanceof ISonExtendVOH){
					ISonExtendVOH son=(ISonExtendVOH) vo;
					sonclssname1=son.getSonClass1();
				}else{
					this.showErrorMessage("主表没有实现 ISonExtendVOH接口");
				}
				ivjLotNumbRefPane1 = new LogNumRefUFPanel(sonclssname1,getSonBillType1(),getSonDigTile1());			
				ivjLotNumbRefPane1.setName("LotNumbRefPane");
				ivjLotNumbRefPane1.setLocation(38, 1);
				initSonListener1(ivjLotNumbRefPane1);
				
			} catch (java.lang.Throwable ivjExc) {
			}
		}
		return ivjLotNumbRefPane1;
	}
	/**
	 * 返回卡片模版。 创建日期：(2002-12-23 9:44:25)
	 */
	public  BillCardPanel getSonBillCardPanel1() {
		return  getLotNumbRefPane1().getLotNumbDlg().getBillCardPanel();
	}
	/**
	 * 初始化孙表监听器
	 * @param panel
	 */
	public void initSonListener1(LogNumRefUFPanel panel) {
			   
		panel.getLotNumbDlg().getBillCardPanel().addBodyEditListener2(new BillEditListener2(){
				 public boolean beforeEdit(BillEditEvent e) {
					 boolean bb= SonbeforeEdit1(e);
					 return bb;
				 }			   
	    });
		   
		panel.getLotNumbDlg().getBillCardPanel().addEditListener(new BillEditListener(){

			public void afterEdit(BillEditEvent e) {
				SonafterEdit1(e);				
			}
			
			public void bodyRowChange(BillEditEvent e) {
				SonbodyRowChange1(e);
			}
					
		});
		panel.getLotNumbDlg().setHandler(this);
		
	}
	
	public  boolean SonbeforeEdit1(BillEditEvent e) {
		return true;
	}	
	
	public void SonafterEdit1(BillEditEvent e) {
		
	}
	
	public void SonbodyRowChange1(BillEditEvent e) {
		
	}
	public AggregatedValueObject getChangedVOFromUI() throws java.lang.Exception {	
		//将Map的数据放到新的符合条件往后台传的Map中
		Map<String, List> nmap=new HashMap<String, List>();
		hl2=nmap;
		Map<String, Map<String,SuperVO>> map=hl12;
		for(String key:map.keySet()){
			Map<String,SuperVO> map1=map.get(key);
			List list=new ArrayList();
			for(String key2:map1.keySet()){
				list.add(map1.get(key2));
			}
			nmap.put(key, list);
		}	
		//取到数据全的vo
		AggregatedValueObject newbillvo=super.getChangedVOFromUI();
		//将map中的产出数据设置到vo中，传到后台进行处理
		SuperVO[] bodys = (SuperVO[])newbillvo.getChildrenVO();		
		if(bodys == null || bodys.length==0)
			return newbillvo;		
		if(getSonMap1() == null)
			return newbillvo;
		String key = null;
		for(SuperVO body:bodys){
			ISonExtendVO isvo=(ISonExtendVO)body;			
			key =PuPubVO.getString_TrimZeroLenAsNull(body.getAttributeValue(isvo.getRowNumName1()));
			String pk=body.getPrimaryKey();
			if(getSonMap1().containsKey(key+pk)){
				isvo.setSonVOS1((nmap.get(key+pk)));
			}
		}		
		return newbillvo;
	}
	
	public Map<String, List> getSonMap1() {
		return hl2;
	}

	
	/**
	 * 编辑前加载Map中产出设置数据
	 * @param e
	 * @return
	 */
	public boolean ProduceOutSetbeforeEdit(BillEditEvent e) {
		String key = e.getKey();
		int row = e.getRow();
		if (e.getPos() == BillItem.BODY) {
			if(getSonDigName1()==null || getSonDigName1().length()==0){
				this.showErrorMessage(" getSonDigName1() 方法没有实现 ,必须返回模版孙表存放字段名称 ");
				return false;
			}
			if (getSonDigName1().equalsIgnoreCase(key)) {
				getBillCardPanel().getBillModel().setValueAt("", row, getSonDigName1());
				nc.ui.pub.bill.BillItem biCol = getBillCardPanel().getBodyItem(key);
				getLotNumbRefPane().setMaxLength(biCol.getLength());
				getBillCardPanel().getBodyPanel().getTable().getColumn(
						biCol.getName()).setCellEditor(
						new nc.ui.pub.bill.BillCellEditor(getLotNumbRefPane1()));
				String crowno = PuPubVO
						.getString_TrimZeroLenAsNull(getBillCardPanel()
								.getBillModel().getValueAt(row, "crowno"));
				String bclassname=getUIControl().getBillVoName()[2];
				SuperVO  vo=null;
				try {
					 vo=(SuperVO) Class.forName(bclassname).newInstance();
				} catch (Exception e1) {
			         this.showErrorMessage(e1.getMessage());
			         return false;
				}
				String bodypk = PuPubVO
						.getString_TrimZeroLenAsNull(getBillCardPanel()
								.getBillModel().getValueAt(row,
										vo.getPKFieldName()));
				String ckey = crowno + bodypk;
				if (getHl12().get(ckey) != null && getHl12().get(ckey).size() > 0) {
					Map<String,SuperVO> map= getHl12().get(ckey);
					List<SuperVO> nlist=new ArrayList<SuperVO>();
					if(map!=null && map.size()>0){
					    for(String pk:map.keySet()){
					    	if(map.get(pk).getStatus()!=VOStatus.DELETED){
					    		nlist.add(map.get(pk));
					    }	
					}				
					getLotNumbRefPane1().setVos(
							(SuperVO[]) (nlist.toArray(new SuperVO[0])));
				    } 
				}else {
					getLotNumbRefPane1().setVos(null);
				}
			  
			}
			return true;	
	 }  
		return true;
	}
	
	public Map<String, Map<String,SuperVO>> getHl12() {
		return hl12;
	}
	public void setHl12(Map<String, Map<String,SuperVO>> hl12 ) {
	    this.hl12=hl12;
	}

	public LogNumRefUFPanel getIvjLotNumbRefPane1() {
		return ivjLotNumbRefPane1;
	}

	public void setIvjLotNumbRefPane1(LogNumRefUFPanel ivjLotNumbRefPane) {
		this.ivjLotNumbRefPane1 = ivjLotNumbRefPane;
	}
	protected BusinessDelegator createBusinessDelegator() {
		return new XCSonExtBusinessDelegator();
	}
	/**
     * ui界面更新后调用该方法 缓存更新 
     * 
     * 如按钮事件，行切换事件触发后，更新界面，最后调用该方法
     */
	public void afterUpdate() {
		super.afterUpdate();
		try {
			setIsExistSonChar(this);
			setExtIsExistSonChar(this);
		} catch (BusinessException e) {
			e.printStackTrace();
			this.showErrorMessage(e.getMessage());
		}
		   if(getManageEventHandler() instanceof FlowManageEventHandler){
	        	  ((FlowManageEventHandler)getManageEventHandler()).afterUpdate();
	         }
	}
	/**
	 * 设置孙表标志位 1
	 * @param ui
	 * @throws BusinessException
	 */
	public  void setExtIsExistSonChar(BillManageUI ui) throws BusinessException {
		try {
		if(ui.isListPanelSelected()){
			XCSonExtDefBillManageUI sonui=null;
			BillListPanel  panel=ui.getBillListPanel();
			if(ui instanceof SonDefBillManageUI ){
				sonui=(XCSonExtDefBillManageUI) ui;
			}else{
				return;
			}
			Map<String, Map<String,SuperVO>> map=sonui.getHl12();
			int count=panel.getBodyBillModel().getRowCount();
			Class bodyclass = Class.forName(ui.getUIControl().getBillVoName()[2]);		
			SuperVO bodyvo=(SuperVO) bodyclass.newInstance();			
			for(int i=0;i<count;i++){
				String pk=(String) panel.getBodyBillModel().getValueAt(i, bodyvo.getPKFieldName());
				String crowno=(String) panel.getBodyBillModel().getValueAt(i,"crowno");
				String key=crowno+pk;				
			    setExtIsExistSonChar1(map.get(key), i);		
			}
		}else{
			XCSonExtDefBillManageUI sonui=null;
			BillCardPanel  panel=ui.getBillCardPanel();
		    if(ui instanceof XCSonExtDefBillManageUI ){
				sonui=(XCSonExtDefBillManageUI) ui;
			}else{
				return;
			}
			Map<String, Map<String,SuperVO>> map=sonui.getHl12();
			int count=panel.getBillModel().getRowCount();
			Class bodyclass = Class.forName(ui.getUIControl().getBillVoName()[2]);		
			SuperVO bodyvo=(SuperVO) bodyclass.newInstance();			
			for(int i=0;i<count;i++){
				String pk=(String) panel.getBillModel().getValueAt(i, bodyvo.getPKFieldName());
				String crowno=(String) panel.getBillModel().getValueAt(i,"crowno");
				String key=crowno+pk;
				setExtIsExistSonChar(map.get(key), i);				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	public void setExtIsExistSonChar(Map<String, SuperVO> map, int row) {

		   boolean isexit=false;
		   if(map!=null&&map.size()>0){
			   for(String key:map.keySet()){
				   SuperVO vo=map.get(key);
				   if(vo!=null){
					   if(vo.getStatus()==VOStatus.DELETED){
						     
					   }else{
						   isexit=true;
						   break;
					   }
				   }
			   }
		   }
		   if(isexit==true){
			   getBillCardPanel().getBillModel().setValueAt("Y", row, getSonDigName1()) ;
		   }else{
			   getBillCardPanel().getBillModel().setValueAt("N", row, getSonDigName1()) ; 
		   }		
		
	}
	public void setExtIsExistSonChar1(Map<String, SuperVO> map, int row) {

		   boolean isexit=false;
		   if(map!=null&&map.size()>0){
			   for(String key:map.keySet()){
				   SuperVO vo=map.get(key);
				   if(vo!=null){
					   if(vo.getStatus()==VOStatus.DELETED){
						     
					   }else{
						   isexit=true;
						   break;
					   }
				   }
			   }
		   }
		   if(isexit==true){
			   getBillListPanel().getBodyBillModel().setValueAt("Y", row, getSonDigName1()) ;
		   }else{
			   getBillListPanel().getBodyBillModel().setValueAt("N", row, getSonDigName1()); 
		   }		
		
	}
}
