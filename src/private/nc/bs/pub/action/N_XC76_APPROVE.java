package nc.bs.pub.action;
import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.pub.compiler.IWorkFlowRet;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.saleweighdoc.SaleWeighdocBVO;
/**
 * 销售水分化验单
 * @author jay
 */
public class N_XC76_APPROVE extends AbstractCompiler2 {
	private java.util.Hashtable m_methodReturnHas = new java.util.Hashtable();
	private Hashtable m_keyHas = null;
	private BaseDAO dao=null;
	public BaseDAO getDao() {
	if (dao == null) {
		dao = new BaseDAO();
	}
	return dao;
	}
	public N_XC76_APPROVE() {
		super();
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			//回写水分到销售过磅登记
			CircularlyAccessibleValueObject[] bvos= vo.m_preValueVo.getChildrenVO();
			 String vlastbillrowid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillrowid"));
			 UFDouble ngrade=PuPubVO.getUFDouble_ValueAsValue(bvos[0].getAttributeValue("ngrade"));
			 if(ngrade!=null){
			 if(vlastbillrowid!=null&&vlastbillrowid.trim()!=""){
			List<SaleWeighdocBVO> list= (List<SaleWeighdocBVO>) getDao().retrieveByClause(SaleWeighdocBVO.class, "pk_weighdoc_b='"+vlastbillrowid+"' and isnull(dr,0)=0");
			if(list!=null&&list.size()!=0){
				SaleWeighdocBVO  bvo=list.get(0);
				UFDouble nwetweight=PuPubVO.getUFDouble_ValueAsValue(bvo.getAttributeValue("nnetweight"));
				UFDouble ndryweight=nwetweight.sub(nwetweight.multiply(ngrade.div(100)));
				String sql=" update xcgl_weighdoc_b set nwatercontent ='"+ngrade+"'"+", ndryamount='"+ndryweight+"'"+ " where pk_weighdoc_b='"+vlastbillrowid+"'";
				getDao().executeUpdate(sql);
				
			}
			 }
			 }
			
			Object m_sysflowObj = procActionFlow(vo);
			if (m_sysflowObj != null) {
				nc.bs.pub.compiler.IWorkFlowRet work=(IWorkFlowRet) m_sysflowObj;
				AggregatedValueObject billvo=  (AggregatedValueObject) work.m_inVo;
				SuperVO hvo=(SuperVO) billvo.getParentVO();
				//处理驳回到制单人 单据状态出错
				if(PuPubVO.getInteger_NullAs(hvo.getAttributeValue("vbillstatus"), -2)==-1){
					hvo.setAttributeValue("vbillstatus", IBillStatus.FREE);
					getDao().updateVO(hvo);
					return m_sysflowObj;
				}
				return m_sysflowObj;
			}
			// ####该组件为单动作工作流处理结束...不能进行修改####
			Object retObj = null;
			setParameter("currentVo", vo.m_preValueVo);
			retObj = runClass("nc.bs.xcgl.pub.HYBillApprove", "approveHYBill",
					"nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,
					m_methodReturnHas);
			
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
		return "	//####该组件为单动作工作流处理开始...不能进行修改####\nprocActionFlow@@;\n//####该组件为单动作工作流处理结束...不能进行修改####\nObject  retObj  =null;\n setParameter(\"currentVo\",vo.m_preValueVo);           \nretObj =runClassCom@ \"nc.bs.pp.pp0201.ApproveAction\", \"approveHYBill\", \"nc.vo.pub.AggregatedValueObject:01\"@;\n            ArrayList ls = (ArrayList)getUserObj();\n       \n        setParameter(\"userOpt\",ls.get(1));               \n            runClassCom@ \"nc.bs.pp.pp0201.ApproveAction\", \"afterApprove\", \"&userOpt:java.lang.Integer,nc.vo.pub.AggregatedValueObject:01\"@;               \nreturn retObj;\n";
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
