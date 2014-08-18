package nc.bs.pub.action;
import java.util.Hashtable;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 水分化验单
 * @author Jay
 */
public class N_XC75_DELETE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;


	public N_XC75_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			CircularlyAccessibleValueObject[] bvos= vo.m_preValueVo.getChildrenVO();
			 String vlastbillid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillid"));
			 if(vlastbillid!=null&&vlastbillid.trim()!=""){
				 String sql="update xcgl_general_h set isref='N'"+ " where pk_general_h='"+vlastbillid+"'";
				 XCZmPubDAO.getDAO().executeUpdate(sql); 
				 }
			super.m_tmpVo = vo;
				// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			// 方法说明:行业公共删除
			retObj = runClass("nc.bs.trade.comdelete.BillDelete", "deleteBill",
					"nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,
					m_methodReturnHas);
			// ##################################################
			
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
	}

	/*
	 * 备注：平台编写原始脚本
	 */
	public String getCodeRemark() {
		return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\nObject retObj  =null;\n//方法说明:行业公共删除\nretObj  =runClassCom@ \"nc.bs.trade.comdelete.BillDelete\", \"deleteBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\n//##################################################\nreturn retObj;\n";
	}

	/*
	 * 备注：设置脚本变量的HAS
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
