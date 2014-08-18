package nc.bs.pub.action;
import java.util.Hashtable;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.bs.zmpub.pub.tool.ZMReferenceCheck;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.qualityprice.QualityPriceHVO;
/**
 * 
 * @author mlr
 */
public class N_XC59_DELETE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;


	public N_XC59_DELETE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
				// ####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
			Object retObj = null;
			// 方法说明:行业公共删除
			   if(vo.m_preValueVo.getParentVO()!=null){
				   QualityPriceHVO hvo=(QualityPriceHVO) vo.m_preValueVo.getParentVO();
						BsUniqueCheck.FieldUniqueChecks(hvo, new String[]{"pk_corp","vqualitypricecode","vqualitypricename"},null,"信息重复");
						boolean isReference = ZMReferenceCheck.getReferenceCheck().isReferenced(
								"xcgl_qualityprice_h", hvo.getPrimaryKey());
						if (isReference == true) {
							throw new BusinessException("数据已经被引用,编码为：" + "['"
									+ hvo.getVqualitypricecode() + "'] 名称为：['"
									+ hvo.getVqualitypricename() + "']");
						}
			   }
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
