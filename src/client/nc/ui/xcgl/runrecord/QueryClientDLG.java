package nc.ui.xcgl.runrecord;
import javax.swing.table.TableCellEditor;

import nc.ui.trade.report.query.QueryDLG;
import nc.vo.scm.pu.PuPubVO;
/*
 * @author mlr
 */
public class QueryClientDLG extends QueryDLG{
	private static final long serialVersionUID = -2815238144653479676L;
	public QueryClientDLG(java.awt.Container parent)
	{
		super(parent);
	}	
	protected void afterEdit(TableCellEditor editor, int row, int col) {
		super.afterEdit(editor, row, col);
		String fieldcode = getFieldCodeByRow(row);
		if(PuPubVO.getString_TrimZeroLenAsNull(fieldcode)==null)
			return;
//		if(fieldcode.equalsIgnoreCase(dep_fieldname)){
//			setValue(null,null,3,stor_fieldname);
//		}		
	}
}
