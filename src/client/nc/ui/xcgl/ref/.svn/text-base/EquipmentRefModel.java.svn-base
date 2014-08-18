package nc.ui.xcgl.ref;

import nc.ui.bd.ref.AbstractRefModel;
/**
 * 设备档案参照
 * @author lxh
 */
public class EquipmentRefModel extends AbstractRefModel{
	private String m_sRefTitle = "设备档案";

	private String tablename = "xcgl_equipment";

	private String[] fieldcode = { "vequip_code", "vequip_name" };

	private String[] fieldname = { "设备编码", "设备名称" };

	private String strWhere = " isnull(dr,0)=0 and pk_corp = '" + getPk_corp()
			+ "'";

	private String[] hidecode = { "pk_equipment" };

	private int defaultFieldCount = 2;

	/**
	 * RouteRefModel 构造子注解。
	 */
	public EquipmentRefModel() {
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
		return "pk_equipment";
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
