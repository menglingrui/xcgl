package nc.vo.xcgl.endclosing;

import nc.ui.pub.ClientEnvironment;
import nc.vo.pub.ValidationException;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.bill.HYHeadSuperVO;

public class EndClosingHVO extends HYHeadSuperVO{

	private static final long serialVersionUID = 1L;
	public String pk_endclosing_h;//
	public String cyear;//年度  
	public String pk_accperiodscheme;//会计方案id
	public String pk_accperiod;// 会计年度主键
	
	

    
	public String getPk_endclosing_h() {
		return pk_endclosing_h;
	}

	public void setPk_endclosing_h(String pk_endclosing_h) {
		this.pk_endclosing_h = pk_endclosing_h;
	}

	public String getCyear() {
		return cyear;
	}

	public void setCyear(String cyear) {
		this.cyear = cyear;
	}

	public void validataOnSave() throws ValidationException{
		if(PuPubVO.getString_TrimZeroLenAsNull(getPk_accperiod())==null){
			throw new ValidationException("会计年度为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(getPk_accperiodscheme())==null){
			throw new ValidationException("期间方案为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(getPk_corp())==null){
			setPk_corp(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		}
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	@Override
	public String getPKFieldName() {
		return "pk_endclosing_h";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "xcgl_endclosing_h";
	}
   


	@Override
	public String getPrimaryKey() {		
		return pk_endclosing_h;
	}

	public String getPk_accperiodscheme() {
		return pk_accperiodscheme;
	}

	public void setPk_accperiodscheme(String pk_accperiodscheme) {
		this.pk_accperiodscheme = pk_accperiodscheme;
	}

	public String getPk_accperiod() {
		return pk_accperiod;
	}

	public void setPk_accperiod(String pk_accperiod) {
		this.pk_accperiod = pk_accperiod;
	}

	

}
