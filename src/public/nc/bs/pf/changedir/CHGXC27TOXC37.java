package nc.bs.pf.changedir;

import nc.bs.pf.change.VOConversion;

public class CHGXC27TOXC37 extends VOConversion{
	public String[] getField() {
		return new String[]{
				 "B_pk_corp->H_pk_corp",
				 "B_pk_invmandoc->B_pk_invmandoc",
				 "B_pk_invbasdoc->B_pk_invbasdoc",
				 "B_pk_factory->H_pk_factory",
				 "B_pk_stordoc->H_pk_stordoc",
				 "B_nnum->B_nlock",
				 "B_nlock->B_nlock",
				 "B_pk_father->B_pk_father"
		};
	}
	public String[] getFormulas() {
		return null;
	}
}