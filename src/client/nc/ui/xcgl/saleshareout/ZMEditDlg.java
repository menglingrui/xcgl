package nc.ui.xcgl.saleshareout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nc.bs.logging.Logger;
import nc.bs.zmpub.pub.check.BsBeforeSaveValudate;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.assayres.AssayResBVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.saleshareout.SaleShareoutBBVO;
import nc.vo.xcgl.saleshareout.SaleShareoutBVO;

/**
 * ���۳��������̯����
 * @author mlr
 */
public class ZMEditDlg extends nc.ui.pub.beans.UIDialog implements
		ActionListener, ListSelectionListener, BillEditListener,
		BillEditListener2 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel ivjUIDialogContentPane = null;
	
	private AbstractBillUI myClientUI=null;

	protected BillListPanel ivjbillListPanel = null;

	private String m_pkcorp = null;

	private String m_operator = null;

	private String m_billType = null;

	private UIPanel ivjPanlCmd = null;

	private UIButton ivjbtnOk = null;

	private UIButton ivjbtnCancel = null;

	private UIButton btn_addline = null;

	private UIButton btn_deline = null;
	
	private UILabel hitmessage=new UILabel();
	
//	zhf add �����������̵İ�ʵ�����̹��ܰ�ť   ��
	private UIButton ivjbtnLock = null;
    //��ű������ݵ�map�������Ǳ�ͷ���кţ�ֵ�Ǳ����Ӧ��vo��ɵ�list
	private Map<String, List<SuperVO>> map = null;
    //��ű�ͷ���ݵ�map�������Ǳ�ͷ���кţ�ֵ�Ǳ�ͷ��vo
	private Map<String, SuperVO> headmap = null;
	private boolean isEdit = true;
	
	private String pk_ware = null;//�ֿ�
	
	private boolean isSign = false;//�Ƿ�ǩ��ͨ��
	    
    private int oldrow=-1;

	public ZMEditDlg(String m_billType, String m_operator,
			String m_pkcorp, String m_nodeKey, AbstractBillUI myClientUI,
			boolean isEdit) {
		super(myClientUI);
		this.myClientUI = myClientUI;
		this.m_billType = m_billType;
		this.m_operator = m_operator;
		this.m_pkcorp = m_pkcorp;
		this.isEdit = isEdit;
		init();
	}

	private void init() {
		setName("BillSourceUI");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(950, 550);
		setTitle("���۳��������̯");
		setContentPane(getUIDialogContentPane());
		// ���ñ༩״̬
		setEdit();
		//
		getDeleteBtn().addActionListener(this);
		getAddBtn().addActionListener(this);
		getbtnOk().addActionListener(this);
		getbtnLock().addActionListener(this);
		getbtnCancel().addActionListener(this);
		getAddLine().addActionListener(this);
		getDeline().addActionListener(this);
		getbillListPanel().addEditListener(this);
		getbillListPanel().addBodyEditListener(this);
		getbillListPanel().getHeadTable().getSelectionModel()
				.addListSelectionListener(this);
		getbillListPanel().getBodyScrollPane(getbillListPanel().getChildListPanel().getTableCode())
				.addEditListener2(this);
		// ���ر�ͷ����
		loadHeadData();
	}

	public void setEdit() {
		getbillListPanel().setEnabled(isEdit);
		getbtnCancel().setEnabled(true);
		getbtnCancel().setEnabled(isEdit);
		getbtnOk().setEnabled(isEdit);
		getAddLine().setEnabled(isEdit);
		getDeline().setEnabled(isEdit);
		
		getbtnLock().setEnabled(isEdit||!isSign);
	}
	public void loadHeadData() {
		try {
			HYBillVO billvo = null;
			if(isEdit){
				billvo = (HYBillVO) myClientUI.getVOFromUI();
			}else{
				billvo = (HYBillVO)myClientUI.getBufferData().getCurrentVO();
			}
			CircularlyAccessibleValueObject hvo = billvo.getParentVO();
			if(hvo!=null){
			List<SuperVO>	templist=new ArrayList<SuperVO>();
			String	generalh=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_general_h"));
			Class[] ParameterTypes = new Class[] { String.class};
			Object[] ParameterValues = new Object[] {generalh};
			Map<Integer,List<SuperVO>[]> map=(Map<Integer, List<SuperVO>[]>) LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"����ִ��...", 1,"nc.bs.xcgl.saleshareout.QueryData" , null, "query",
						ParameterTypes, ParameterValues);
			if(map!=null&&map.size()!=0){
				//�ѽ���̯���ˣ��Ͱ����ݼ��ز�������ع���
				for(Integer key:map.keySet()){
					List[] list=map.get(key);
					List headlist = list[0];
					if(headlist!=null&&headlist.size()!=0){
						for(int i=0;i<headlist.size();i++){
							SuperVO vo = (SuperVO) headlist.get(i);
							SaleShareoutBVO salesharebvo = changeVoType(vo);
							templist.add(salesharebvo);
						}
						SuperVO tempvo=(SuperVO) headlist.get(0);
						getBufferData().put(PuPubVO.getString_TrimZeroLenAsNull(tempvo.getAttributeValue("crowno")), list[1]);
					}
					getbillListPanel().setHeaderValueVO((CircularlyAccessibleValueObject[]) templist.toArray(new SuperVO[0]));
					getbillListPanel().getHeadBillModel().execLoadFormula();
				}
				
			}
			else{
//			//û�з�̯��Ҫ������Դ��Ϣȡ���۵Ļ�����
//			Class[] ParameterTypes1 = new Class[] { HYBillVO.class};
//			Object[] ParameterValues1 = new Object[] {billvo};
//			Map<String, List<AssayResBVO>> assayresmap=(Map<String, List<AssayResBVO>>) 
//			LongTimeTask.calllongTimeService(PubOtherConst.module, null,
//					"����ִ��...", 1,"nc.bs.xcgl.saleshareout.FindGradeBySource" , null, "findGrade",
//					ParameterTypes1, ParameterValues1);
//			//Map<String, List<AssayResBVO>> assayresmap = findGrade(billvo);
//			HYBillVO   fixbillvo=fixdata(assayresmap,billvo);
//			if (fixbillvo != null) {
//				getbillListPanel().setHeaderValueVO(fixbillvo.getChildrenVO());
//				getbillListPanel().getHeadBillModel().execLoadFormula();
//				storeHeadData(fixbillvo.getChildrenVO());
//			}
			//���߻�������ֱ�Ӵӽ���ȥ���ݹ��������ص���ͷ
				//�����ѷ�̯������δ��̯����
				HYBillVO aggvo = setNum(billvo);
				getbillListPanel().setHeaderValueVO(aggvo.getChildrenVO());
				getbillListPanel().getHeadBillModel().execLoadFormula();
				storeHeadData(aggvo.getChildrenVO());
			}
			}
		} catch (Exception e) {
			Logger.error(e);
		}
	}
	
    private HYBillVO setNum(HYBillVO billvo) {
		if(billvo==null||billvo.getChildrenVO().length==0){
			return null;
		}
		CircularlyAccessibleValueObject[] bvos = billvo.getChildrenVO();
		for(int i=0;i<billvo.getChildrenVO().length;i++){
			bvos[i].setAttributeValue("nwetweight", bvos[i].getAttributeValue("ndryweight"));
			bvos[i].setAttributeValue("ngrossweight", UFDouble.ZERO_DBL);
			//bvos[i].setAttributeValue("pk_father", bvos[i].getAttributeValue("pk_invmandoc"));
			
		}
		return billvo;
	}

	protected void storeHeadData(CircularlyAccessibleValueObject[] childrenVO) {
    	if(childrenVO==null||childrenVO.length==0){
    		return;
    	}
		if(getHeadData()!=null||getHeadData().size()!=0){
			getHeadData().clear();
			for(int i=0;i<childrenVO.length;i++){
				getHeadData().put(PuPubVO.getString_TrimZeroLenAsNull(childrenVO[i].getAttributeValue("crowno")),
						(SuperVO) childrenVO[i]);
			}
		}
		
	}

	/**
     *�����ݽ����������
     *�����յ��ݵ�������Ϣȡ������������ָ���Ʒζ��Ϣ
     *Ȼ���ھ����������������Ϣ
	 * @throws Exception 
     */
	private HYBillVO fixdata(Map<String, List<AssayResBVO>> assayresmap,
			HYBillVO billvo) throws Exception {
		List<SaleShareoutBVO> list=new ArrayList<SaleShareoutBVO>();
		SaleShareoutBVO[] bodyvos = (SaleShareoutBVO[]) billvo.getChildrenVO();
		if(billvo==null||bodyvos==null||bodyvos.length==0){
			throw new Exception("��������Ϊ�գ��򵥾ݱ�������Ϊ�գ�");
		}
		if(assayresmap==null||assayresmap.size()==0){
			throw new Exception("���ۻ�����Ϊ�գ�");
		}
		HYBillVO fixbillvo=new HYBillVO();
		fixbillvo.setParentVO(billvo.getParentVO());
		for(int i=0;i<bodyvos.length;i++){
			bodyvos[i].setAttributeValue("nwetweight", bodyvos[i].getAttributeValue("ndryweight"));
			bodyvos[i].setAttributeValue("ngrossweight", UFDouble.ZERO_DBL);
			bodyvos[i].setAttributeValue("pk_father", bodyvos[i].getAttributeValue("pk_invmandoc"));
			list.add(bodyvos[i]);
			UFDouble dryweight=PuPubVO.getUFDouble_ZeroAsNull(bodyvos[i].getNdryweight());//ȡ������
			List<AssayResBVO> gradelist = assayresmap.get(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillid())+
					PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillrowid()));
			if(gradelist!=null&&gradelist.size()!=0){
				//SaleShareoutBVO[] bvos=new SaleShareoutBVO[gradelist.size()];
				for(int j=0;j<gradelist.size();j++){
					SaleShareoutBVO salevo=new SaleShareoutBVO();
					AssayResBVO bvo=gradelist.get(j);
					salevo.setPk_father(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getPk_invmandoc()));
					salevo.setCrowno(PuPubVO.getString_TrimZeroLenAsNull((i+1)*10+j+1));
					salevo.setVlastbillcode(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillcode()));
					salevo.setVlastbillid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillid()));
					salevo.setVlastbillrowid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbillrowid()));
					salevo.setVlastbilltype(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVlastbilltype()));
					salevo.setVsourcebillid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVsourcebillid()));
					salevo.setVsourcebillrowid(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVsourcebillrowid()));
					salevo.setVsourcebilltype(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getVsourcebilltype()));
					salevo.setCsourcebillcode(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getCsourcebillcode()));
					salevo.setPk_general_h(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getPk_general_h()));
					salevo.setPk_general_b(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getPk_general_b()));
					salevo.setCcode(PuPubVO.getString_TrimZeroLenAsNull(billvo.getParentVO().getAttributeValue("vbillno")));
					salevo.setPk_invmandoc(PuPubVO.getString_TrimZeroLenAsNull(bvo.getPk_invmandoc()));
					salevo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(bvo.getPk_invbasdoc()));
					salevo.setVcrowno(PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getCrowno()));
					salevo.setNdryweight(PuPubVO.getUFDouble_NullAsZero(dryweight.multiply(bvo.getNgrade().div(100))));
					salevo.setNwetweight(PuPubVO.getUFDouble_NullAsZero(dryweight.multiply(bvo.getNgrade().div(100))));
					salevo.setNgrossweight(UFDouble.ZERO_DBL);
					list.add(salevo);
				}
			}
		}
		fixbillvo.setChildrenVO(list.toArray(new SaleShareoutBVO[0]));
		return fixbillvo;
	}

	// ���У����Ĭ��ֵ
	public void setBodyDefaultValue(int row) {
		
		
	}
   public Map<String,SuperVO>  getHeadData(){
	if(headmap==null){
		headmap=cloneHeadData();
	}   
	return headmap;
   }
	public Map<String, SuperVO> cloneHeadData() {
	Map<String, SuperVO> map2 = new HashMap<String, SuperVO>();
	return map2;
}

	public Map<String, List<SuperVO>> getBufferData() {
		if (map == null) {
			map = cloneBufferData();
		}
		return map;
	}

	public Map<String, List<SuperVO>> cloneBufferData() {
	//	Map<String, List<SuperVO>> map1 = myClientUI.getTrayInfor();
		Map<String, List<SuperVO>> map2 = new HashMap<String, List<SuperVO>>();
//		if (map1.size() > 0) {
//			Iterator<String> it = map1.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next();
//				List<SuperVO> list = map1.get(key);
//				map2.put(key, cloneBBVO(list));
//			}
//		}
		return map2;
	}

	public List<SuperVO> cloneBBVO(List<SuperVO> list) {
		List<SuperVO> list1 = new ArrayList<SuperVO>();
		if (list != null && list.size() > 0) {
			for (SuperVO b : list) {
				list1.add((SuperVO) b.clone());
			}
		}
		return list1;
	}



	protected SuperVO getBodyVO(int row) {
		SuperVO vo = (SuperVO) getbillListPanel()
				.getBodyBillModel().getBodyValueRowVO(row,
						SuperVO.class.getName());
		return vo;
	}

	protected int getBodyCurrentRow() {
		int row = getbillListPanel().getBodyTable().getRowCount() - 1;
		return row;
	}

	protected int getHeadCurrentRow() {
		int row = getbillListPanel().getHeadTable().getSelectedRow();
		return row;
	}

	// ����
	protected void onLineAdd() {
		int rowcount=getbillListPanel().getBodyBillModel().getRowCount();
		getbillListPanel().getBodyBillModel().addLine();
		getbillListPanel().getBodyBillModel().setValueAt(
				PuPubVO.getString_TrimZeroLenAsNull((rowcount+1)*10), rowcount, "crowno");
		setBodyDefaultValue(getBodyCurrentRow());
	}


	// ɾ��
	protected void onLineDel() {
		int[] rows = getbillListPanel().getBodyTable().getSelectedRows();
		int headrow=getHeadCurrentRow();
		if(rows!=null&&rows.length!=0){
			for(int i=0;i<rows.length;i++){
			 UFDouble bodydryweight = PuPubVO.getUFDouble_NullAsZero(getbillListPanel().getBodyBillModel().getValueAt(rows[i], "ndryweight"));
	         UFDouble headnwetweight = PuPubVO.getUFDouble_NullAsZero(getbillListPanel().getHeadBillModel().getValueAt(headrow, "nwetweight"));
	         UFDouble ngrossweight = PuPubVO.getUFDouble_NullAsZero(getbillListPanel().getHeadBillModel().getValueAt(headrow, "ngrossweight"));
	         getbillListPanel().getHeadBillModel().setValueAt(headnwetweight.add(bodydryweight), headrow, "nwetweight");
	         getbillListPanel().getHeadBillModel().setValueAt(ngrossweight.sub(bodydryweight), headrow, "ngrossweight");
	         //getbillListPanel().getHeadBillModel().execEditFormulas(headrow);
			}
		}
		getbillListPanel().getBodyBillModel().delLine(rows);
	}

	protected BillListPanel getbillListPanel() {
		if (ivjbillListPanel == null) {
			try {
				ivjbillListPanel = new BillListPanel();
				ivjbillListPanel.setName("billListPanel");
				ivjbillListPanel.loadTemplet(m_billType, null, m_operator,
						m_pkcorp);
				ivjbillListPanel.getHeadTable().setSelectionMode(
						ListSelectionModel.SINGLE_INTERVAL_SELECTION);// ��ѡ
				ivjbillListPanel.getBodyTable().setSelectionMode(
						ListSelectionModel.SINGLE_INTERVAL_SELECTION);// ��ѡ
				ivjbillListPanel.getChildListPanel().setTotalRowShow(true);
				ivjbillListPanel.setEnabled(true);
			} catch (java.lang.Throwable e) {
				Logger.error(e.getMessage(), e);
			}
		}
		return ivjbillListPanel;
	}

	protected JPanel getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			ivjUIDialogContentPane = new JPanel();
			ivjUIDialogContentPane.setName("UIDialogContentPane");
			ivjUIDialogContentPane.setLayout(new BorderLayout());
			ivjUIDialogContentPane.add(getbillListPanel(), "Center");
			ivjUIDialogContentPane.add(getPanlCmd(), BorderLayout.SOUTH);
		}
		return ivjUIDialogContentPane;
	}

	private UIPanel getPanlCmd() {
		if (ivjPanlCmd == null) {
			ivjPanlCmd = new UIPanel();
			ivjPanlCmd.setName("PanlCmd");
			ivjPanlCmd.setPreferredSize(new Dimension(0, 50));
			ivjPanlCmd.setLayout(new BorderLayout());
			//ivjPanlCmd.add(getAddBtn(), getAddBtn().getName());
			//ivjPanlCmd.add(getDeleteBtn(), getDeleteBtn().getName());
//			
//			p.add(hitmessage, BorderLayout.WEST);
			UIPanel p=new UIPanel();
			p.setLayout(new FlowLayout());
		
			p.add(getAddLine(), getAddLine().getName());
			p.add(getDeline(), getDeline().getName());
			p.add(getbtnOk(), getbtnOk().getName());
			p.add(getbtnCancel(), getbtnCancel().getName());
			p.add(getbtnLock(),getbtnLock().getName());
			ivjPanlCmd.add( p,BorderLayout.CENTER);
			
			UIPanel pp=new UIPanel();
			pp.setLayout(new BorderLayout());
			hitmessage.setText("��ʼ�� ... ");
			pp.add(hitmessage,BorderLayout.WEST);
			
			pp.setBackground(new Color(216,230,246));
			pp.setBorder(BorderFactory.createLineBorder(new Color(143,175,215)));
					
			ivjPanlCmd.add(pp,BorderLayout.SOUTH);
			
			
		}
		return ivjPanlCmd;
	}
	private UIButton ivjbtnOk1 = null;
	private UIButton getAddBtn() {
		if (ivjbtnOk1 == null) {
			ivjbtnOk1 = new UIButton();
			ivjbtnOk1.setName("btnOk");
			ivjbtnOk1.setText("����");
		}
		return ivjbtnOk1;
	}
	private UIButton ivjbtnOk2 = null;

	
	private UIButton getDeleteBtn() {
		if (ivjbtnOk2 == null) {
			ivjbtnOk2 = new UIButton();
			ivjbtnOk2.setName("btnOk");
			ivjbtnOk2.setText("ɾ��");
		}
		return ivjbtnOk2;
	}
	private UIButton getbtnOk() {
		if (ivjbtnOk == null) {
			ivjbtnOk = new UIButton();
			ivjbtnOk.setName("btnOk");
			ivjbtnOk.setText("ȷ��");
		}
		return ivjbtnOk;
	}

	private UIButton getAddLine() {
		if (btn_addline == null) {
			btn_addline = new UIButton();
			btn_addline.setName("addline");
			btn_addline.setText("����");
		}
		return btn_addline;
	}

	private UIButton getDeline() {
		if (btn_deline == null) {
			btn_deline = new UIButton();
			btn_deline.setName("deline");
			btn_deline.setText("ɾ��");
		}
		return btn_deline;
	}

	private UIButton getbtnCancel() {
		if (ivjbtnCancel == null) {
			ivjbtnCancel = new UIButton();
			ivjbtnCancel.setName("btnCancel");
			ivjbtnCancel.setText("ȡ��");
		}
		return ivjbtnCancel;
	}
	
	// ��ӱ��水ť
	private UIButton getbtnLock() {
		if (ivjbtnLock == null) {
			ivjbtnLock = new UIButton();
			ivjbtnLock.setName("ivjbtnLock");
			ivjbtnLock.setText("����");
		}
		return ivjbtnLock;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(getbtnOk())) {
			try {
				saveCurrentData(getHeadCurrentRow());
				check();
				closeOK();
			} catch (Exception e1) {
				MessageDialog.showErrorDlg(this, "����", e1.getMessage());
			}
		} else if (e.getSource().equals(getbtnCancel())) {
			closeCancel();
		} else if (e.getSource().equals(getAddLine())) {
			onLineAdd();
		} else if (e.getSource().equals(getDeline())) {
			onLineDel();
		}else if(e.getSource().equals(getAddBtn())){
		    onAdd();
		}else if(e.getSource().equals(getDeleteBtn())){
			onDelete();
		}
		else if(e.getSource().equals(getbtnLock())){
			try {
				onSave();
			} catch (Exception e1) {
				e1.printStackTrace();
				this.myClientUI.showErrorMessage(e1.getMessage());
			}
		}
	}
    /**
     *���淽�� 
     * @throws Exception 
     * ��ȡ����ͷ��ǰѡ��ģ�Ȼ��ѱ�������ݣ��浽����map��ȥ
     * ȡ����ͷ�����ݣ��ñ�ͷ���ݺͱ������ݹ���һ���ۺ�vo
     * ������̨ȥ���ں�̨���д浽���ݿ�
     */
	public void onSave() throws Exception {
		Map<String, SuperVO> headmap= new HashMap<String, SuperVO>();
		int currentheadrow=getHeadCurrentRow();
		saveCurrentData(currentheadrow);
		int headrowcount=getbillListPanel().getHeadBillModel().getRowCount();
		int [] a=crateArray(headrowcount);
		if(a!=null&&a.length!=0){
		for(int i=0;i<a.length;i++){
			SuperVO vo = getHeadBVO(a[i]);
			headmap.put(PuPubVO.getString_TrimZeroLenAsNull(vo.getAttributeValue("crowno")), 
					vo);
		}
		//Map<String, SuperVO> headdata = getHeadData();
		Map<String, List<SuperVO>> bodydata = getBufferData();
		//����ǰУ��
		if(bodydata==null&&bodydata.size()<=0){
			this.myClientUI.showErrorMessage("��̯��������Ϊ��!");
			return;
		}
		for(String key:bodydata.keySet()){
			List<SuperVO> list=bodydata.get(key);
			try {
				BsBeforeSaveValudate.beforeSaveBodyUnique(list.toArray(new SuperVO[0]),new String []{"pk_stordoc"},new String []{"�ֿ�"});
			} catch (Exception e) {
				//throw new BusinessException("��ͷ��"+key+"�������"+e.getMessage());
				this.myClientUI.showErrorMessage("��ͷ��"+key+"�������"+e.getMessage());
				return;
			}
			
		}
//		Map<String, List<SuperVO>> bodymap=new HashMap<String, List<SuperVO>
		Class[] ParameterTypes = new Class[] { Map.class,Map.class};
		Object[] ParameterValues = new Object[] {headmap,bodydata};
		LongTimeTask.calllongTimeService(PubOtherConst.module, null,
				"����ִ��...", 1,"nc.bs.xcgl.saleshareout.SaveData" , null, "save",
				ParameterTypes, ParameterValues);
		//myClientUI.showHintMessage("�������!");
		hitmessage.setText("�������...");
	}
	}

	public int[] crateArray(int headrowcount) {
	 if(headrowcount<=0){
		return null;
		}
	 else{
		 int[] a=new int[headrowcount];
		 for(int i=0;i<headrowcount;i++){
			 a[i]=i;
		 }
		 return a;
	 }
	}

	protected void setTSFormBufferToVO(AggregatedValueObject billVO) {
		
		
	}

	public void onDelete() {
		
		
	}

	public void onAdd() {
		getbillListPanel().getHeadBillModel().addLine();
		int selrow=getbillListPanel().getHeadBillModel().getRowCount()-1;
		BillEditEvent event =new BillEditEvent(getbillListPanel().getParentListPanel().getTable(), oldrow, selrow);
		bodyRowChange(event);
	}

	public void saveCurrentData(int row) {
		if (row < 0) {
			return;
		}
		String key = (String) getbillListPanel().getHeadBillModel().getValueAt(row, "crowno");
		SuperVO[] bvos = (SuperVO[]) getbillListPanel()
				.getBodyBillModel().getBodyValueVOs(
						SaleShareoutBBVO.class.getName());
		if (bvos != null && bvos.length > 0) {
			getBufferData().put(key, arrayToList(bvos));
		} else {
			getBufferData().remove(key);
		}
	}

	public void check() throws BusinessException {

	}






	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == getbillListPanel().getHeadTable()
				.getSelectionModel()) {
			// ��������
			// ���¼��ر�������
		}
	}

	// ȡ��������ݻ�
	public void calcMaxTray() {
		// Ŀǰͨ����ʽ�Զ���ѯ����
	}

	public boolean beforeEdit(BillEditEvent e) {
		String key = e.getKey();
		int row = e.getRow();
		return true;
	}
    /**
     *��Ŀ����	ngrossweight   �ѷ�̯����
     *��Ŀ����	nwetweight     δ��̯����
     * 
     * 
     */
	public void afterEdit(BillEditEvent e) {
		String key = e.getKey();
		int row = e.getRow();
		int pos=e.getPos();
		if(pos==IBillItem.BODY&&key.equalsIgnoreCase("ndryweight")){
		UFDouble ndryweight = PuPubVO.getUFDouble_NullAsZero(
				getbillListPanel().getBillListData().getBodyBillModel().getValueAt(row, "ndryweight"));
		int headrow=getHeadCurrentRow();
		if(headrow>=0){
		UFDouble nwetweight=PuPubVO.getUFDouble_NullAsZero(getbillListPanel().getHeadBillModel().getValueAt(headrow, "nwetweight"));
		if(ndryweight.doubleValue()<=nwetweight.doubleValue()){	
        UFDouble ngrossweight=PuPubVO.getUFDouble_NullAsZero(getbillListPanel().getHeadBillModel().getValueAt(headrow, "ngrossweight"));
        getbillListPanel().getHeadBillModel().setValueAt(ndryweight.add(ngrossweight), headrow, "ngrossweight");
        getbillListPanel().getHeadBillModel().execEditFormulas(headrow);
		}
		else{
//			try {
//				throw new BusinessException("��̯��������δ��̯����");
//			} catch (BusinessException e1) {
				myClientUI.showErrorMessage("��̯��������δ��̯����");
//				e1.printStackTrace();
//			}
		}
        //getbillListPanel().getHeadBillModel().setValueAt(ndryweight.sub(ufd), headrow, "nwetweight");
		}
		}
		saveCurrentData(getHeadCurrentRow());
	}

	public ArrayList<SuperVO> arrayToList(SuperVO[] o) {
		if (o == null || o.length == 0)
			return null;
		ArrayList<SuperVO> list = new ArrayList<SuperVO>();
		for (SuperVO s : o) {
			if(s!=null){				
				list.add(s);
			}
		}
		return list;
	}

	public void bodyRowChange(BillEditEvent e) {
		if (e.getSource() == getbillListPanel().getParentListPanel().getTable()) {
			// ��������
			int oldrow = e.getOldRow();
			if (oldrow >= 0) {
				saveCurrentData(oldrow);
			}
			// ��ձ�������
			getbillListPanel().getBodyBillModel().clearBodyData();
			// ���¼��ر�������
			int row = e.getRow();
			this.oldrow=e.getRow();
			String key2 =(String) getbillListPanel().getHeadBillModel().getValueAt(row, "crowno");
            //��map��ȡ�����������
			ArrayList<SuperVO> list = (ArrayList<SuperVO>)getBufferData().get(key2);
			//��ȡ�������ݼ��ص�ģ����
			if(list !=null && list.size() > 0){
				getbillListPanel().getBodyBillModel().setBodyDataVO(list.toArray(new SuperVO[0]));			
				getbillListPanel().getBodyBillModel().execLoadFormula();	
		}
		}
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	
	private Object getBodyValue(int row,String fieldname){
		return getbillListPanel().getBodyBillModel().getValueAt(row, fieldname);
	}
	
	
	private Object getHeadValue(String fieldname){
		if(PuPubVO.getString_TrimZeroLenAsNull(fieldname)==null)
			return null;
		int row = getbillListPanel().getHeadTable().getSelectedRow();
		if(row < 0)
			return null;
		return getbillListPanel().getHeadBillModel().getValueAt(row, fieldname);
	}	

// private SuperVO[] getHeadValuevos(){
//	 int headrow=getbillListPanel().getHeadBillModel().getRowCount();
//	 
//	 if(headrow>0){
//		 Vector<SuperVO> head = getbillListPanel().getHeadBillModel().getBillModelData();
//		 Object rowattribute = getbillListPanel().getHeadBillModel().getBillModelData().get(0);
//		 return head.toArray(new SuperVO[0]);
//	 }
//	 return null;
// }
//	protected SuperVO getHeadVO(int row) {
//		SuperVO vo = (SuperVO) getbillListPanel()
//				.getBodyBillModel().getBodyValueRowVO(row,
//						SuperVO.class.getName());
//		 List head = getbillListPanel().getHeadBillModel().getBillModelData();
//		return vo;
//	}
	protected SuperVO getHeadBVO(int row) {
		SuperVO vo = (SuperVO) getbillListPanel().getHeadBillModel()
				.getBodyValueRowVO(row,SaleShareoutBVO.class.getName());
		return vo;
	}
	public int showModal() {
		// �¾��ʹ��Ļ��˸
		// if (getTopFrame() == null && getTopParent() != null)
		// getTopParent().setEnabled(false);
		//
		setModal(true);
        
		if (!isShowing()) {
			// setLocationRelativeTo(m_parent);
			setLocationRelativeTo(null);
			show();
		}
		return getResult();
	}
	private SaleShareoutBVO changeVoType(SuperVO superVO) {
		if(superVO!=null){
			SaleShareoutBVO hvo=new SaleShareoutBVO();
			for(int i=0;i<superVO.getAttributeNames().length;i++){
				hvo.setAttributeValue(superVO.getAttributeNames()[i],
						superVO.getAttributeValue(superVO.getAttributeNames()[i]));
			}
			return hvo;
		}
		return null;
	}
}
