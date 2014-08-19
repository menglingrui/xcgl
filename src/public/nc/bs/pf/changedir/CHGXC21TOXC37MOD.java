package nc.bs.pf.changedir;

import nc.bs.pf.change.VOConversion;
/**
 *  精矿入库->现存量修复
 * @author lxh
 */
public class CHGXC21TOXC37MOD  extends VOConversion{
	public String[] getField() {
		return new String[]{
				 "H_pk_corp->H_pk_corp",
				 "H_pk_invmandoc->H_pk_invmandoc",
				 "H_pk_invbasdoc->H_pk_invbasdoc",
				 "H_pk_factory->H_pk_factory",
				 "H_pk_stordoc->H_pk_stordoc",
				 "H_nnum->H_ndryweight",
				 "H_pk_father->H_pk_father"
		};
	}
	public String[] getFormulas() {
		return null;
	}
}