package nc.ui.xcgl.saleweighdoc;

import java.awt.Container;

import nc.ui.pub.query.QueryConditionClient;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.scm.so.SaleBillType;
import nc.ui.so.pub.query.SaleOrderBillReferQueryProxy;
import nc.vo.querytemplate.TemplateInfo;
/**
 * sale order query dig
 * @author mlr
 *
 */
@SuppressWarnings("restriction")
public class BillReferQuery30ToXC25 extends SaleOrderBillReferQueryProxy{

	public BillReferQuery30ToXC25() {
	}

	public BillReferQuery30ToXC25(Container parent, TemplateInfo ti) {
		super(parent, ti);
	}

	public BillReferQuery30ToXC25(Container parent) {
		super(parent);
	}

	@Override
	public QueryConditionDLG createNewQryDlg() {
		return null;
	}
	

	@Override
	public QueryConditionClient createOldQryDlg() {
		/*
		 * if(getSourceBilltype().equals(SaleBillType.SaleOrder)){
		 *  }
		 */
		SaleOrderQuery dlg = new SaleOrderQuery(getContainer());
		dlg.initData(getPkCorp(), getOperator(), getNodeKey(),
				getBusinessType(), getCurrentBillType(), getSourceBilltype(),
				getUserObj());
		dlg.setBillRefModeSelPanel(true);
		return dlg;
	}
	
    public nc.vo.pub.query.ConditionVO[] getConditionVO() {
    	return getOldQryDlg().getConditionVO();
    }

    public void clearConditionVO(){
    	((SaleOrderQuery)getOldQryDlg()).clearConditionVO();
    }
    
	@Override
	public boolean isNewQryDlg() {
		return false;
	}

	@Override
	public boolean isShowDoubleTableRef() {
    return ((SaleOrderQuery)getOldQryDlg()).isShowDoubleTableRef();
	}

	@Override
	public void setUserRefShowMode(boolean isShowDoubleTableRef) {
    ((SaleOrderQuery)getOldQryDlg()).setBillRefShowMode(isShowDoubleTableRef) ;
	}
	
	public String getNodeCode(String strCurrentBillType, String strBillType) {
		
		String nodecode = null;

		if (strCurrentBillType.equals(SaleBillType.SaleInvoice)
				&& strBillType.equals(SaleBillType.SaleOrder))
			nodecode = "40069901";
		if ("3U".equals(strCurrentBillType))
			nodecode = "40069901";
		if (strCurrentBillType.equals(SaleBillType.SaleOutStore)
				&& strBillType.equals(SaleBillType.SaleOrder))
			nodecode = "40069902";
		
		

		return "40069902";
	}
}
