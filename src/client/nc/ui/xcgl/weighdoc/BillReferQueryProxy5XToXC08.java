package nc.ui.xcgl.weighdoc;

import java.awt.Container;
import nc.ui.pub.query.QueryConditionClient;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.scm.pub.query.SCMQueryConditionDlg;
import nc.ui.scm.pub.sourceref.IBillReferQueryProxy;
import nc.ui.to.icredun.TOBillQueryClient;
import nc.vo.querytemplate.TemplateInfo;
/**
 * 调拨订单查询框
 * @author mlr
 *
 */
public class BillReferQueryProxy5XToXC08 extends IBillReferQueryProxy{
	  private TOBillQueryClient dlg = null;

	  public BillReferQueryProxy5XToXC08() {
	    super();
	    // TODO 自动生成构造函数存根
	  }

	  public BillReferQueryProxy5XToXC08(
	      Container parent, TemplateInfo ti) {
	    super(parent, ti);
	    // TODO 自动生成构造函数存根
	  }

	  public BillReferQueryProxy5XToXC08(
	      Container parent) {
	    super(parent);
	    // TODO 自动生成构造函数存根
	  }

	  @Override
	  public QueryConditionDLG createNewQryDlg() {
	    // TODO 自动生成方法存根
	    return null;
	  }

	  @Override
	  public QueryConditionClient createOldQryDlg() {
	    if(dlg==null){
	      dlg = new TOBillQueryClient(getContainer(), getPkCorp(),
	          getOperator(), getFunNode(), getBusinessType(),
	          getCurrentBillType(), getSourceBilltype(), getUserObj());
	      dlg.setBillRefModeSelPanel(true);
	    }
	    return dlg;
	  }

	  @Override
	  public boolean isNewQryDlg() {
	    return false;
	  }

	  @Override
	  public boolean isShowDoubleTableRef() {
	    return ((SCMQueryConditionDlg) createOldQryDlg())
	    .isShowDoubleTableRef();
	  }

	  @Override
	  public void setUserRefShowMode(boolean isShowDoubleTableRef) {
	    ((SCMQueryConditionDlg) createOldQryDlg())
	    .setBillRefShowMode(isShowDoubleTableRef);
	  }

}
