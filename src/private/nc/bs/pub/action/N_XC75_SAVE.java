package nc.bs.pub.action;

import java.util.Hashtable;

import nc.bs.pub.compiler.AbstractCompiler2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * ˮ�ֻ��鵥
 * @author Jay
 */
public class N_XC75_SAVE extends AbstractCompiler2 {
	
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();

	private Hashtable m_keyHas = null;

	public N_XC75_SAVE() {
		super();
	}


	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
		    setParameter ( "INCURVO",vo.m_preValueVo);
		    setParameter ( "BillType",vo.m_billType);
		    setParameter ( "BillDate",getUserDate ().toString ());
		    setParameter ( "ActionName", "SAVE");
		    setParameter ( "P3",null);
		    setParameter ( "P5",null); //getUserObj ());
		    Object obj1 = runClass("nc.bs.xcgl.pub.HYBillCommit", "commitHYBill","nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,	m_methodReturnHas);
		    return obj1;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/*
	 * ��ע��ƽ̨��дԭʼ�ű�
	 */
	public String getCodeRemark() {
		return "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\nrunClassCom@ \"nc.bs.pp.pp0201.BillCommit\", \"beforeCommitCheck\", \"nc.vo.pub.AggregatedValueObject:01\"@;\nObject retObj =runClassCom@ \"nc.bs.pp.pub.comstatus.HYBillCommit\", \"commitHYBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\nreturn retObj;\n";
	}

	/*
	 * ��ע�����ýű�������HAS
	 */
	private void setParameter(String key, Object val) {
		if (m_keyHas == null) {
			m_keyHas = new Hashtable();
		}
		if (val != null) {
			m_keyHas.put(key, val);
		}
	}
}