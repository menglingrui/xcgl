package nc.bs.pf.changedir;

public class CHGXC15TOXC37MOD extends  nc.bs.pf.change.VOConversion{

	public String[] getField() {
		return new String[]{
			"H_pk_stordoc->H_pk_stordoc",
			"H_pk_factory->H_pk_factory",
			"H_pk_corp->H_pk_corp",
			"H_pk_invmandoc->H_pk_invmandoc",
			"H_pk_invbasdoc->H_pk_invbasdoc",
			"H_nnum->H_nwetweight",
			"H_pk_father->H_pk_father",
			"H_vdef1->H_pk_minarea",//����
		};
	}
	public String[] getFormulas() {
		return null;
	}

}
