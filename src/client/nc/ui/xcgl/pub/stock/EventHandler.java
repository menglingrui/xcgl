package nc.ui.xcgl.pub.stock;

import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.query.INormalQuery;
import nc.ui.xcgl.pub.bill.XCSingleBodyEventHandler;

public class EventHandler extends XCSingleBodyEventHandler{

	public EventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}
	@Override
	protected void onBoSave() throws Exception {
		super.onBoSave();
	}

	@Override
	protected void onBoBodyQuery() throws Exception {
		StringBuffer strWhere = new StringBuffer();
		if (askForBodyQueryCondition(strWhere) == false)
			return;// 用户放弃了查询
		sWhere = strWhere.toString();
		if(getBillCardPanelWrapper()!=null)
			if(getBillCardPanelWrapper().getBillCardPanel().getBodyItem("pk_corp")!=null)
				sWhere += " and pk_corp='"+_getCorp().getPrimaryKey()+"'";	
		doBodyQuery(sWhere);
	}
	/**
	 * @param strWhere
	 * @param b
	 * @return
	 * 添加的存货分类查询条件为 xcgl_stockonhand.pk_invcl
	 */
	protected boolean askForBodyQueryCondition(StringBuffer sqlWhereBuf)
			throws Exception {
		if (sqlWhereBuf == null)
			throw new IllegalArgumentException(
					"askForQueryCondition().sqlWhereBuf cann't be null");
		UIDialog querydialog = getQueryUI();

		if (querydialog.showModal() != UIDialog.ID_OK)
			return false;
		INormalQuery query = (INormalQuery) querydialog;
		String strWhere = query.getWhereSql();
		//(xcgl_stockonhand.pk_factory = '1021AP1000000009ALIZ' AND xcgl_stockonhand.pk_invmandoc = '0001AP1000000001170B')		
		if (strWhere == null)
			strWhere = "1=1";		
		int index=strWhere.indexOf("xcgl_stockonhand.pk_invcl = ");		
		if(index >0){
			index=index+29;
		}		
		if(index>=29){
			String pk_invcl=strWhere.substring(index, index+20);			
			String reg="xcgl_stockonhand.pk_invcl = '"+pk_invcl+"'";
			strWhere.indexOf(reg);
			//xcgl_stockonhand.pk_invcl = '001AP1000000000X8L9'
			//xcgl_stockonhand.pk_invcl = '0001AP1000000000X8L9'
			strWhere=strWhere.replaceAll(reg,
					"xcgl_stockonhand.pk_invbasdoc in (select pk_invbasdoc from bd_invcl where pk_invcl='"+pk_invcl+"')"										
					);
		}					
		strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";
		if (getUIController().getBodyCondition() != null)
			strWhere = strWhere + " and "
					+ getUIController().getBodyCondition();
		// 现在我先直接把这个拼好的串放到StringBuffer中而不去优化拼串的过程
		sqlWhereBuf.append(strWhere);
		return true;
	}
}
