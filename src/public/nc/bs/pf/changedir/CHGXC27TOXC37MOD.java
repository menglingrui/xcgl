package nc.bs.pf.changedir;

import nc.bs.pf.change.VOConversion;

public class CHGXC27TOXC37MOD extends VOConversion{
	public String[] getField() {
		return new String[]{
				 "H_pk_corp->H_pk_corp",
				 "H_pk_invmandoc->H_pk_invmandoc",
				 "H_pk_invbasdoc->H_pk_invbasdoc",
				 "H_pk_factory->H_pk_factory",
				 "H_pk_stordoc->H_pk_stordoc",
				 "H_nnum->H_nlock",
				 "H_nlock->H_nlock",
				 "H_pk_father->H_pk_father",
				 "H_vdef1->H_pk_minarea",//����
		};
	}
	public String[] getFormulas() {
		return null;
	}
}