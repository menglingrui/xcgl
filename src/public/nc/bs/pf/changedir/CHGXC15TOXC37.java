package nc.bs.pf.changedir;
/**
 * �ִ�����ԭ��ӹ�����
 * @author ddk
 *
 */
public class CHGXC15TOXC37 extends  nc.bs.pf.change.VOConversion{
	public String[] getField() {
		return new String[]{
			"B_pk_stordoc->H_pk_stordoc",
			"B_pk_factory->H_pk_factory",
			"B_pk_corp->H_pk_corp",
			"B_pk_invmandoc->B_pk_invmandoc",
			"B_pk_invbasdoc->B_pk_invbasdoc",
			"B_nnum->B_nwetweight",
			"B_pk_father->B_pk_father",
			"B_vdef1->H_pk_minarea",//����
		};
	}
	public String[] getFormulas() {
		return null;
	}
}
