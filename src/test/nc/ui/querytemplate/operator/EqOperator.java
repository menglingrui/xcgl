package nc.ui.querytemplate.operator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.meta.IFilterMeta;
import nc.ui.querytemplate.value.IFieldValue;
import nc.ui.querytemplate.value.IFieldValueElement;
import nc.vo.xcgl.pub.tool.XcPubTool;

public class EqOperator extends AbstractOperator {

	private static final long serialVersionUID = 79122814953707611L;

	private static final EqOperator INSTANCE = new EqOperator();

	public static EqOperator getInstance() {
		return INSTANCE;
	}

	@Override
	public int getParameterNumber() {
		return -1;
	}

	@Override
	public String getOperatorCode() {
		return getSingleValueOperatorCode();
	}

	@Override
	public String getSQLString(FilterMeta meta, IFieldValue value) {
		if (value == null || value.getFieldValues() == null
				|| value.getFieldValues().size() == 0)
			return null;
		if (value.getFieldValues().size() == 1) {
			String valueString = value.getFieldValues().get(0).getSqlString();
			if(meta.getSQLFieldCode().equals("xcgl_sample_b.vsamplenumber")){
				String sql = null;
				try {
					sql = "("+meta.getSQLFieldCode() + " "
					+ getSingleValueOperatorCode() + " '" + valueString
					+ "' or "+meta.getSQLFieldCode() + " "
					+ getSingleValueOperatorCode() + " '" + XcPubTool.encrypt(valueString) 
					+ "' )";
				} catch (Exception e) {
					
				}
				return sql;
			}
		
			
			if (isNumberType(meta)) {
				return meta.getSQLFieldCode() + " "
						+ getSingleValueOperatorCode() + " " + valueString;
			} else if (isMultiSelectionSql(valueString)) {
				return meta.getSQLFieldCode() + " " + getMultiValueOperator()
						+ " (" + valueString + ")";
			} else {
				return meta.getSQLFieldCode() + " "
						+ getSingleValueOperatorCode() + " '" + valueString
						+ "'";
			}

		} else {
			StringBuffer buf = new StringBuffer();
			List<IFieldValueElement> l = value.getFieldValues();
			for (IFieldValueElement element : l) {
				if(element==null)
					continue;
				if (isNumberType(meta)) {
					buf.append(element.getSqlString()).append(",");
				} else {
					buf.append("'").append(element.getSqlString()).append("',");
				}
			}
			if(buf.length()==0)
				return null;
			return meta.getSQLFieldCode() + " " + getMultiValueOperator()
					+ " (" + buf.substring(0, buf.length() - 1) + ")";
		}
	}

	public String getMultiValueOperator() {
		return IOperatorConstants.IN;
	}

	protected String getSingleValueOperatorCode() {
		return IOperatorConstants.EQ;
	}

	public boolean isMultiSelectionSql(String valueString) {

		if(valueString == null) return false;
		Pattern p = Pattern.compile("select\\s+.*from");
		Matcher m = p.matcher(valueString.toLowerCase());
		return m.find();
	}

	@Override
	public String toString() {
		return IOperatori18n.getEQ_Desc();
	}

	public String getDescription(IFilterMeta meta, IFieldValue value) {
		if (value == null || value.getFieldValues() == null
				|| value.getFieldValues().size() == 0) {
			return meta.toString();
		}

		if (value.getFieldValues().size() == 1) {
			String sqlString = value.getFieldValues().get(0).getSqlString();
			String showString = value.getFieldValues().get(0).getShowString();
			if (isNumberType(meta)) {
				return meta.toString() + " " + toString() + " "	+ showString;
			} else if (isMultiSelectionSql(sqlString)) {
				return meta.toString() + " " + toString() + " "	+ showString;
			} else {
				return meta.toString() + " " + toString() + " '" + showString + "'";
			}

		} else {
			StringBuffer buf = new StringBuffer();
			List<IFieldValueElement> l = value.getFieldValues();
			for (IFieldValueElement element : l) {
				if(element==null)
					continue;
				if (isNumberType(meta)) {
					buf.append(element.getShowString()).append(",");
				} else {
					buf.append("'").append(element.getShowString()).append("',");
				}
			}
			if(buf.length()==0)
				return meta.toString();
			return meta.toString() + " " + toString() + " (" + buf.substring(0, buf.length() - 1) + ")";
		}
	}
	
	public boolean isFuzzy()
	{
		return true;
	}
}
