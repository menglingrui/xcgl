package nc.bs.wds.self.changedir;

import nc.bs.pf.change.VOConversion;

public class CHGXC21TOXC37 extends VOConversion{
	
	
	public String[] getField() {
		return new String[]{
				 "B_pk_corp->H_pk_corp",
				 "B_pk_invmandoc->B_pk_invmandoc",
				 "B_pk_invbasdoc->B_pk_invbasdoc",
				 "B_pk_factory->H_pk_factory",
				 "B_pk_stordoc->H_pk_stordoc",
				 "B_nnum->B_nwetweight",
		};
	}
	public String[] getFormulas() {
		return null;
	}
}
