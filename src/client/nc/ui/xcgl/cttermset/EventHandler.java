package nc.ui.xcgl.cttermset;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.pub.VOTreeNode;
import nc.ui.trade.treecard.BillTreeCardUI;
import nc.ui.trade.treecard.TreeCardEventHandler;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.xcgl.cttermset.CtTermsetVO;
import nc.vo.xcgl.cttermtype.CtTermtypeVO;

/**
 *类说明：合同条款EventHandler
 *@author jay
 *@version 1.0   
 *创建时间：2014-2-17下午04:03:51
 */

public class EventHandler extends TreeCardEventHandler{

	public EventHandler(BillTreeCardUI billUI, ICardController control) {
		super(billUI, control);
	
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2004-1-9 8:37:34)
	 * @return nc.ui.trade.pub.BillCardUI
	 */
	protected void onBoSave() throws Exception {
		super.onBoSave();
		if(getUITreeCardController().isAutoManageTree()){
			ClientUI ui=((ClientUI) getBillUI());
             //again create tree data
			ui.setUITreeData(ui.getTermTyepVO());
		}
	}

@Override
public void onBoAdd(ButtonObject bo) throws Exception {
	ClientUI ui=((ClientUI) getBillUI());
	
	
	TreePath treePath =ui.getLeftUITree().getSelectionPath();
	if (treePath == null) {
		return;
	}
	DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
	int level=node.getLevel();//此节点距离根节点的距离
	String pk_type=null;
	if(level==1){
		CtTermtypeVO rowvo = (CtTermtypeVO) ((VOTreeNode) node).getData();
	    pk_type=rowvo.getPk_cttermtype();
	}else if(level==2){
		CtTermsetVO rowvo = (CtTermsetVO) ((VOTreeNode) node).getData();
	    pk_type=rowvo.getPk_cttermtype();
	}else{
		ui.showErrorMessage("请选择条款类型");
		return;
	}	
	super.onBoAdd(bo);
	getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cttermtype").setValue(pk_type);
	
}
/**
按钮m_boDel点击时执行的动作,如有必要，请覆盖.
档案的删除处理
*/
protected void onBoDelete() throws Exception {
if(isAllowDelNode(getBillTreeCardUI().getBillTreeSelectNode())){
	if (MessageDialog.showOkCancelDlg(getBillUI(), 
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory","UPPuifactory-000064")/*@res "档案删除"*/, 
			nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory","UPPuifactory-000065")/*@res "是否确认删除该基本档案?"*/
			,UIDialog.ID_CANCEL)
		!= UIDialog.ID_OK)
		return;

	AggregatedValueObject modelVo = getBufferData().getCurrentVO();
	//进行数据晴空
	Object o = null;
	ISingleController sCtrl = null;
	if (getUIController() instanceof ISingleController)
	{
		sCtrl = (ISingleController) getUIController();
		if (sCtrl.isSingleDetail())
		{
			o = modelVo.getParentVO();
			modelVo.setParentVO(null);
		}
		else
		{
			o = modelVo.getChildrenVO();
			modelVo.setChildrenVO(null);
		}
	}

	getBusinessAction().delete(
		modelVo,
		getUIController().getBillType(),
		getBillUI()._getDate().toString(),
		getBillUI().getUserObject());

	if (PfUtilClient.isSuccess())
	{
		//从树中删除节点
		if(getUITreeCardController().isAutoManageTree()){
		//	getBillTreeCardUI().deleteNodeFromTree(getBillCardPanelWrapper().getBillVOFromUI().getParentVO());
			getBillCardPanelWrapper().getBillCardPanel().getBillData().clearViewData();
		}
		//清除界面数据
		if (getUIController() instanceof ISingleController)
		{
			ISingleController sctl = (ISingleController) getUIController();
			if (sctl.isSingleDetail())
				getBufferData().refresh();
			else
				getBufferData().removeCurrentRow();
		}else{
			getBufferData().removeCurrentRow();
		}
		getBillTreeCardUI().resetTreeToBufferData();
	}

	if (getBufferData().getVOBufferSize() == 0)
		getBillUI().setBillOperate(IBillOperate.OP_INIT);
	else
		getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
	ClientUI ui=((ClientUI) getBillUI());
    //again create tree data
	ui.setUITreeData(ui.getTermTyepVO());
}
}
protected void onBoRefresh() throws Exception {

	getBillTreeCardUI().clearTreeSelect();

	getBillTreeCardUI().createBillTree(getBillTreeCardUI().getCreateTreeData());

	getBillTreeCardUI().afterInit();

	ClientUI ui=((ClientUI) getBillUI());
    //again create tree data
	ui.setUITreeData(ui.getTermTyepVO());
	getBillTreeCardUI().setBillOperate(nc.ui.trade.base.IBillOperate.OP_INIT);
}
}
