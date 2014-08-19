package nc.vo.xcgl.openingentry;

import nc.vo.xcgl.pub.bill.PubGeneralBVO;

public class OpeningEntryBVO extends PubGeneralBVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9111301316292322942L;
	/**
	 * 表体主键
	 * */
	private String pk_general_b;
	/**
	 * 主表主键
	 * */
	private String pk_general_h;
	/**
	 * 计量单位
	 * */
	private String pk_measdoc;
	


	
	

	public String getPk_measdoc() {
		return pk_measdoc;
	}

	public void setPk_measdoc(String pk_measdoc) {
		this.pk_measdoc = pk_measdoc;
	}

	public String getPk_general_b() {
		return pk_general_b;
	}

	public void setPk_general_b(String pk_general_b) {
		this.pk_general_b = pk_general_b;
	}

	public String getPk_general_h() {
		return pk_general_h;
	}

	public void setPk_general_h(String pk_general_h) {
		this.pk_general_h = pk_general_h;
	}

	@Override
	public String getPKFieldName() {
		return "pk_general_b";
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_general_h";
	}

	@Override
	public String getTableName() {
		return "xcgl_general_b";
	}


}

