
package nc.ui.xcgl.cttermset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIScrollPane;
import nc.ui.pub.beans.UITree;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.trade.pub.IVOTreeData;
import nc.ui.trade.pub.TableTreeNode;
import nc.ui.trade.pub.TreeCreateTool;
import nc.ui.trade.pub.VOTreeNode;
import nc.ui.trade.treecard.BillTreeCardUI;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.cttermset.CtTermsetVO;
import nc.vo.xcgl.cttermtype.CtTermtypeVO;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.pub.helper.QueryHelper;
/**
 *类说明：合同条款ClientUI
 *@author jay
 *@version 1.0   
 *创建时间：2014-2-17下午04:02:32
 */
public class ClientUI extends BillTreeCardUI{

	private static final long serialVersionUID = 1L;
	private TreeCreateTool treetool = new TreeCreateTool();
	private UIScrollPane leftUIScrollPane = null;
	private UITree leftUITree = null;
	//树节点Map
	private HashMap treeNodeMap=new HashMap();
	//树节点IDMap
	private HashMap treeIDNodeMap=new HashMap();
	//树Model
	private DefaultTreeModel treemodel;
	@Override
	protected IVOTreeData createTableTreeData() {
		
		return null;
	}
	public ClientUI() {
		super();
		initialize();
//		init();
	}

	private void initialize() {
		try
		{
			this.add(getSplitPane());
			getSplitPane().setLeftComponent(getLeftUIScrollPane());
			getSplitPane().setRightComponent(getBillCardPanel());
			getBillCardPanel().setPreferredSize(new java.awt.Dimension(298, 469));
			setUITreeData(getTermTyepVO());
			afterInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory","UPPuifactory-000109")/*@res "发生异常，界面初始化错误"*/);
		}
	}
	/**
	 *通过查询数据库获取所有的
	 *合同条款类型vo 
	 */
	public CtTermtypeVO[] getTermTyepVO() {
		try {
			List<CtTermtypeVO> list=(List<CtTermtypeVO>) QueryHelper.retrieveByClause(CtTermtypeVO.class, " "+" isnull(dr,0)=0 and pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
			if(list!=null&&list.size()!=0){
				return list.toArray(new CtTermtypeVO[0]);
				
			}
			else{
				return null;
			}
		} catch (BusinessException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	protected UIScrollPane getLeftUIScrollPane() {
		if (leftUIScrollPane == null) {
			try {
				leftUIScrollPane = new UIScrollPane();
				leftUIScrollPane.setName("UIScrollPane2");
				leftUIScrollPane.setBounds(17, 22, 160, 134);
				leftUIScrollPane.setViewportView(getLeftUITree());
			} catch (Throwable e) {
				handleException(e);
			}
		}
		return leftUIScrollPane;
	}
	/**
	 *调用底层代码构建的树模型 
	 * 
	 */
	public UITree getLeftUITree() {
		if (leftUITree == null) {
			leftUITree = new UITree();
			leftUITree.setRootVisible(true);
			leftUITree.setShowsRootHandles(true);
			leftUITree.putClientProperty("JTree.lineStyle", "Angled");
			leftUITree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
			leftUITree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					try {
						onSelectTree();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return leftUITree;
	}
	/**
	 *根据选择的树的节点的getLevel()方法的返回值
	 *来判断节点在树的位置，并对数据做相应的处理
	 *getLevel（）方法返回值是1的为一级子节点
	 *getLevel（）方法返回值是2的为二级子节点 
	 */
	public void onSelectTree() throws Exception {
		TreePath treePath = getLeftUITree().getSelectionPath();
		if (treePath == null) {
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
		int level=node.getLevel();//此节点距离根节点的距离
		if (!(level==2)) { // || !node.isLeaf()
			clearRigthData();
			return;
		}
		else {
			CtTermsetVO rowvo = (CtTermsetVO) ((VOTreeNode) node).getData();
			setData(rowvo);
		}
	
		}
	/**
	 *设置合同条款表头数据
	 */
	private void setData(CtTermsetVO rowvo) {
		if(rowvo==null){
			return;
		}
		else{
			getBillCardPanel().setHeadItem("pk_corp", rowvo.getPk_corp());
			getBillCardPanel().setHeadItem("pk_cttermtype", rowvo.getPk_cttermtype());
			getBillCardPanel().setHeadItem("pk_cttermset", rowvo.getPk_cttermset());
			getBillCardPanel().setHeadItem("termcontent", rowvo.getTermcontent());
			getBillCardPanel().setHeadItem("termname", rowvo.getTermname());
			getBillCardPanel().setHeadItem("termsetcode", rowvo.getTermsetcode());
			getBillCardPanel().setHeadItem("vmemo", rowvo.getVmemo());
			
			if (rowvo != null )
			{
				XCHYBillVO aVo =new XCHYBillVO();
					
				aVo.setParentVO(rowvo);
				getBufferData().addVOToBuffer(aVo);

			    int num = getBufferData().getVOBufferSize();
			    if(num == -1)
				     num = 0;
			    else
				     num = num - 1;
			    this.getTreeToBuffer().put(rowvo.getPk_cttermtype(),num+"");

			    try {
					this.setBillOperate(IBillOperate.OP_NOTEDIT);
				} catch (Exception e) {
					this.showErrorMessage("..");
					e.printStackTrace();
				}
			}
			else
			{
				try {
					this.setBillOperate(IBillOperate.OP_INIT);
				} catch (Exception e) {
					this.showErrorMessage("..");
					e.printStackTrace();
				}
			}
			
		    return;
		}
	}
	public void clearRigthData() throws Exception {
		//清空缓冲数据
		getBufferData().clear();
	    this.setListHeadData(null);
		getBufferData().setCurrentRow(-1);
		this.setBillOperate(IBillOperate.OP_INIT);	
	}
	/**
	 * 每当部件抛出异常时被调用
	 */
	public void handleException(java.lang.Throwable exception) {
		
	}
	/**
	 * 根据合同欠款类型的主键，过滤公司，
	 * dr标志，和合同条款类型主键，查询
	 * 数据库，取得某个合同欠款类型下的
	 * 所有的合同条款vo
	 */
	private CtTermsetVO[] getAllTetmSetVO(String pk_cttermtype ){
		try {
			List<CtTermsetVO> list=(List<CtTermsetVO>) QueryHelper.retrieveByClause(CtTermsetVO.class, " "+" isnull(dr,0)=0 and " +
					"pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'"+
					" and pk_cttermtype='"+pk_cttermtype+"'");
			if(list!=null&&list.size()!=0){
				return list.toArray(new CtTermsetVO[0]);
			}
			else{
				return null;
			}
		} catch (BusinessException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 *树构建的实现 :
	 *树的根节点是root，
	 *树的一级子节点是合同条款类型主键
	 *输的二级子节点是相应的合同条款类
	 *型下面的合同条款
	 */
	protected void setUITreeData(CtTermtypeVO[] rowvos) {
		List<VOTreeNode> list=new ArrayList<VOTreeNode>();
		if (rowvos != null && rowvos.length != 0) {
			VOTreeNode[] nodes = null;
			int size = rowvos.length;
			nodes = new VOTreeNode[size];
			for (int i = 0; i < size; i++) {
				CtTermtypeVO vo = rowvos[i];
				String cobj=vo.getTermtypecode();
				String obj = vo.getTermtypename();
				nodes[i] = new VOTreeNode(cobj+" "+obj);
				nodes[i].setData(vo);
				String pk_cttermtype=vo.getPk_cttermtype();
				nodes[i].setNodeID(pk_cttermtype);
				nodes[i].setParentnodeID("root");
				list.add(nodes[i]);
				CtTermsetVO[] setVO=getAllTetmSetVO(pk_cttermtype);
				if(setVO!=null&&setVO.length!=0){
					VOTreeNode[] snodes=new VOTreeNode[setVO.length];
					for(int j=0;j<setVO.length;j++){
						CtTermsetVO setvo=setVO[j];
						String termcode = setvo.getTermsetcode();
						String termname = setvo.getTermname();					
						snodes[j] = new VOTreeNode(termcode+" "+termname);
						snodes[j].setData(setvo);
						//String pk_cttermtype=setvo.getPk_cttermtype();
						snodes[j].setNodeID(setvo.getPk_cttermset());
						snodes[j].setParentnodeID(setvo.getPk_cttermtype());
						list.add(snodes[j]);
					}
				}
			}
		}
		TableTreeNode rootnode = new TableTreeNode("合同条款类型");
		rootnode.setNodeID("root");
		
//			treetool=new TreeCreateTool();
	
//		DefaultTreeModel model = treetool.createTree(nodes, rootnode);
		DefaultTreeModel model = createTree(list.toArray(new VOTreeNode[0]), rootnode);
		getLeftUITree().setModel(model);
		TableTreeNode root = (TableTreeNode) getLeftUITree().getModel().getRoot();
		TreePath rootPath = new TreePath(root.getPath());
		getLeftUITree().setSelectionPath(rootPath);
		//expandAllPathtNode(root);
	}

//	private void init(){
//	 	getBillTreeData().modifyRootNodeShowName("合同条款");
//	}
	public TreeCreateTool getBillTreeData() {

		if(treetool == null)
			treetool = new TreeCreateTool();

		return treetool;
	}
	@Override
	protected ICardController createController() {
		
		return new Controller();
	}
   @Override
   protected CardEventHandler createEventHandler() {
	
	return new EventHandler(this,getUIControl());
}
	@Override
	public String getRefBillType() {
		
		return null;
	}

	@Override
	protected void initSelfData() {
		
	}
	
	public void addTreeNode(Object obj){
		
	}
	public DefaultTreeModel getTreeModel(){
		return null;
		
	}
	/**
	 * 按ID和parentID构造树的方法
	 * 创建日期：(2003-9-5 20:05:59)
	 * @return javax.swing.tree.DefaultTreeModel
	 * @param treenods nc.ui.pub.report.treetableex.TableTreeNode[]
	 * @param root java.lang.Object
	 */
	public DefaultTreeModel createTree(TableTreeNode[] treenods, Object root) {
		TableTreeNode rootnode = new TableTreeNode(root);
		rootnode.setNodeID(root);

		treeNodeMap.put("", rootnode);
		treeIDNodeMap.put("", rootnode);

		if(treenods != null){
			//将树节点放如哈希表
			for (int i = 0; i < treenods.length; i++){
				treeNodeMap.put(treenods[i].getNodeID(),treenods[i]);
				treeIDNodeMap.put(treenods[i].getNodeID(),treenods[i]);
			}
		
			for (int i = 0; i < treenods.length; i++){
				Object parentID = treenods[i].getParentnodeID();
				TableTreeNode parentNode = (parentID == null)?rootnode:(TableTreeNode)treeNodeMap.get(parentID);
				if(parentNode != null){
					parentNode.add(treenods[i]);
				}
				else{
					System.out.println("父节点未找到，数据有错@nc.ui.pub.report.treetableex.TreeCreateTool.createTree(TableTreeNode [], Object)");
					rootnode.add(treenods[i]);
				}
			}
		}
		treemodel = new DefaultTreeModel(rootnode);
		return treemodel;
	}
	@Override
	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(
				ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}
	@Override
	protected IVOTreeData createTreeData() {
		// TODO Auto-generated method stub
		return null;
	}
}
