package nc.bs.pf.changedir;

import nc.bs.pf.change.VOConversion;
/**
 * 物流台账 精矿入库->现存量
 * @author lxh
 */
public class CHGXC21TOXC37 extends VOConversion{
	public String[] getField() {
		return new String[]{
				 "B_pk_corp->H_pk_corp",
				 "B_pk_invmandoc->B_pk_invmandoc",
				 "B_pk_invbasdoc->B_pk_invbasdoc",
				 "B_pk_factory->H_pk_factory",
				 "B_pk_stordoc->H_pk_stordoc",
				 "B_nnum->B_ndryweight",
				 "B_pk_father->B_pk_father",
				 "B_vdef1->H_pk_minarea",//部门//部门
		};
	}
	public String[] getFormulas() {
		return null;
	}
}