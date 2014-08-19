package nc.ui.xcgl.pub.stock.mod;
import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UITextArea;
import nc.ui.pub.query.QueryConditionClient;
import nc.ui.trade.query.HYQueryDLG;
import nc.ui.trade.query.INormalQuery;
import nc.vo.xcgl.pub.consts.PubNodeCodeConst;
import nc.vo.xcgl.pub.helper.StockModHelper;
/**
 * 现存量修复入口类
 * @author mlr
 */
public class StockModUI extends ToftPanel{
	private static final long serialVersionUID = -7089976732186502392L;
	private ButtonObject m_modify = new ButtonObject("现存量修复","现存量修复",2,"现存量修复");
    private HYQueryDLG dig=null;
	private ClientEnvironment cl=null;
	public StockModUI(){
		super();
		init();
	}
	private void init() {
		setButton();	
		cl=ClientEnvironment.getInstance();
		initialize();
	}
	private void initialize()
	{
		setLayout(null);
		add(getTextAreaHint(), getTextAreaHint().getName());

	}
	/**
	 * 返回 m_taTextArea1 特性值。
	 * @return UITextArea
	 * 
	 * @author shaobing 邵兵
	 * on Jun 30, 2005
	 * 解决英文的显示问题（使之完整显示）。
	 */
	private UITextArea m_taTextArea1 = null;
	private UITextArea getTextAreaHint() {
		if (m_taTextArea1 == null) {
			try {
			    StringBuffer sText =new StringBuffer("现存量修复将根据出入库单和结存,重新计算本公司的现存量");
			    sText.append( "\n\n");
			    sText.append("现存量修复之前,需要指定修复的选厂(必输)、仓库(必输)、存货。");
			    sText.append( "\n\n");
			    sText.append(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi","UPP4008busi-000232")/*@res "该过程可能耗时较长，请在服务器空闲的时候进行..."*/);		   
			    m_taTextArea1 = new UITextArea();
			    m_taTextArea1.setName("UITextArea2");			    
			    m_taTextArea1.setRows(5);
			    m_taTextArea1.setLineWrap(true);	//自动换行
			    m_taTextArea1.setEditable(false);	//不可编辑	
			    m_taTextArea1.setEnabled(false);	//不可激活			    
			    m_taTextArea1.setBackground(this.getBackground());			//设置背景色
			    m_taTextArea1.setDisabledTextColor(java.awt.Color.BLACK);	//设置字体颜色		    
			    m_taTextArea1.setText(sText.toString());
			    m_taTextArea1.setBounds(74, 62, 500,150);
			} catch (java.lang.Throwable ivjExc) {
	            this.showErrorMessage(ivjExc.getMessage());
			}
		}
		return m_taTextArea1;
	}
	@Override
	public String getTitle() {	
		return "现存量修复";
	}
   
	@Override
	public void onButtonClicked(ButtonObject bo) {
		    UIDialog querydialog = getQueryUI();
        
		    if (querydialog.showModal() != UIDialog.ID_OK){			
			  return ;
	        }
		    QueryConditionClient qu=(QueryConditionClient) querydialog;
			INormalQuery query = (INormalQuery) querydialog;
			String strWhere = query.getWhereSql();
//			if(qu.getConditionVOsByFieldCode("pk_stordoc")==null|| qu.getConditionVOsByFieldCode("pk_stordoc").length==0||
//			   qu.getConditionVOsByFieldCode("pk_factory")==null|| qu.getConditionVOsByFieldCode("pk_factory").length==0){
//				this.showErrorMessage("选厂、仓库不能为空");	
//				return;
//			}
			try {
				strWhere=setDefaultContion(strWhere);
			} catch (Exception e) {
				e.printStackTrace();
			    return;
			}
			onModify(strWhere);	
	}
	String pk_corp=null;
	private String setDefaultContion(String strWhere) throws Exception {
		pk_corp=cl.getCorporation().getPrimaryKey();
		if(strWhere!=null&&strWhere.length()>0){
		   strWhere=strWhere+" and pk_corp='"+pk_corp+"'";
		}else{
			strWhere="  pk_corp='"+pk_corp+"' ";
		}
		return strWhere;
	}
	private HYQueryDLG getQueryUI() {
		if(dig==null){
			dig=new HYQueryDLG(this,null,cl.getCorporation().getPrimaryKey(),PubNodeCodeConst.NodeCode_stockmod,cl.getUser().getPrimaryKey(),null,null);
		}	     
		return dig;
	}
	protected void setButton() {
		this.setButtons(new ButtonObject[]{m_modify});
	}
	private void onModify(String strWhere){
		 try{
	     //创建查询业务单据的sql 需要找到最后的结账月
		 StockModHelper.modifyAccounts(strWhere, strWhere, strWhere, strWhere, cl.getCorporation().getPrimaryKey());		 
			 this.showHintMessage("修复成功");
	        }catch(Exception e){
	            Logger.error(e);
	            MessageDialog.showErrorDlg(this, "警告", e.getMessage());
	        }		
	}
	
}
