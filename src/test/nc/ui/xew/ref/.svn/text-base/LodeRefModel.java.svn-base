package nc.ui.xew.ref;

import nc.ui.bd.ref.AbstractRefModel;
/**
 * 矿脉参照
 * @author dp
 */
public class LodeRefModel extends  AbstractRefModel{
	// 参照标题
	private String m_sRefTitle = "矿脉档案";

	private String tablename = "xew_lode";

	private String[] fieldcode = { "vcode", "vname" };

	private String[] fieldname = { "矿脉编码", "矿脉名称" };

	private String strWhere = " isnull(dr,0)=0 and pk_corp = '" + getPk_corp()
			+ "'";

	private String[] hidecode = { "pk_lode","pk_minearea" };

	private int defaultFieldCount = 2;

	/**
	 * RouteRefModel 构造子注解。
	 */
	public LodeRefModel() {
		super();
	}

	/**
	 * getDefaultFieldCount 方法注解。
	 */
	public int getDefaultFieldCount() {
		return defaultFieldCount;
	}

	/**
	 * 显示字段列表 创建日期：(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return fieldcode;
	}

	/**
	 * 显示字段中文名 创建日期：(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return fieldname;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-6 10:56:48)
	 */
	public String[] getHiddenFieldCode() {
		return hidecode;
	}

	/**
	 * 主键字段名
	 * 
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_lode";
	}

	/**
	 * 参照标题 创建日期：(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public String getRefTitle() {
		return m_sRefTitle;
	}

	@Override
	public String getWherePart() {

		return strWhere;
	}

	/**
	 * 参照数据库表或者视图名 创建日期：(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public String getTableName() {

		return tablename;
	}

	@Override
	public boolean isCacheEnabled() {

		return false;
	}
}
