package nc.bs.pf.changedir;
/**
 * 现存量到药剂消耗
 * @author ddk
 *
 */
public class CHGXC19TOXC37 extends  nc.bs.pf.change.VOConversion{
	public String[] getField() {
		return new String[]{
			"B_pk_corp->H_pk_corp",
			"B_pk_factory->H_pk_factory",
			"B_pk_stordoc->H_pk_stordoc",
			"B_pk_invmandoc->B_pk_invmandoc",
			"B_pk_invbasdoc->B_pk_invbasdoc",
			"B_nnum->B_ndryweight",
			"B_pk_father->B_pk_father",
		};
	}
	public String[] getFormulas() {
		return null;
	}
}
