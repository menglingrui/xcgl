package nc.ui.xcgl.metalbalance;

import javax.swing.table.TableCellEditor;

import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report2.HYPubQueryDlg;
/**
 * 理论金属平衡表
 * @author 
 *
 */
public class QueryClientDLG extends HYPubQueryDlg{
	private static final long serialVersionUID = 1L;
	public QueryClientDLG(java.awt.Container parent)
	{
		super(parent);
	}	
	protected void afterEdit(TableCellEditor editor, int row, int col) {
		super.afterEdit(editor, row, col);
		String fieldcode = getFieldCodeByRow(row);
		if(PuPubVO.getString_TrimZeroLenAsNull(fieldcode)==null)
			return;
	}
}
