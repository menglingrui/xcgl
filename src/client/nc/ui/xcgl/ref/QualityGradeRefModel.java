package nc.ui.xcgl.ref;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * �����żۡ�Ʒλ����
 * @author ddk
 *
 */
public class QualityGradeRefModel extends AbstractRefModel{
	
	private String m_sRefTitle = "�����żۡ�Ʒλ";

	private String tablename = "xcgl_qualitygrade_h";

	private String[] fieldcode = { "vqualitygradecode", "vqualitygradename" };

	private String[] fieldname = { "�����żۡ�Ʒλ����", "�����żۡ�Ʒλ����" };

	private String strWhere = " isnull(dr,0)=0 and pk_corp = '" + getPk_corp()
			+ "' and vbillstatus=1 " ;

	private String[] hidecode = { "pk_qualitygrade_h" };

	private int defaultFieldCount = 2;

	/**
	 * RouteRefModel ������ע�⡣
	 */
	public QualityGradeRefModel() {
		super();
	}

	/**
	 * getDefaultFieldCount ����ע�⡣
	 */
	public int getDefaultFieldCount() {
		return defaultFieldCount;
	}

	/**
	 * ��ʾ�ֶ��б� �������ڣ�(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldCode() {
		return fieldcode;
	}

	/**
	 * ��ʾ�ֶ������� �������ڣ�(01-4-4 0:57:23)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String[] getFieldName() {
		return fieldname;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-6 10:56:48)
	 */
	public String[] getHiddenFieldCode() {
		return hidecode;
	}

	/**
	 * �����ֶ���
	 * 
	 * @return java.lang.String
	 */
	public String getPkFieldCode() {
		return "pk_qualitygrade_h";
	}

	/**
	 * ���ձ��� �������ڣ�(01-4-4 0:57:23)
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
	 * �������ݿ�������ͼ�� �������ڣ�(01-4-4 0:57:23)
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
