package nc.bs.pub.wfengine.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.ml.NCLangResOnserver;
import nc.bs.pf.pub.PfDataCache;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.pub.pf.busistate.AbstractBusiStateCallback;
import nc.bs.pub.pf.busistate.PFBusiState;
import nc.bs.pub.pf.busistate.PFBusiStateOfMeta;
import nc.bs.pub.pf.exception.ApproveAfterCommitException;
import nc.bs.pub.pflock.IPfBusinessLock;
import nc.bs.pub.pflock.PfBusinessLock;
import nc.bs.pub.pflock.VOConsistenceCheck;
import nc.bs.pub.pflock.VOLockData;
import nc.bs.pub.taskmanager.WfTaskManager;
import nc.bs.pub.workflownote.WorknoteManager;
import nc.bs.wfengine.engine.query.WorkflowQuery;
import nc.bs.xcgl.mailltool.MaillTool;
import nc.impl.uap.pf.PFConfigImpl;
import nc.itf.uap.pf.IPFBusiAction;
import nc.itf.uap.pf.IPFConfig;
import nc.itf.uap.pf.IWorkflowMachine;
import nc.itf.uap.pf.metadata.IFlowBizItf;
import nc.jdbc.framework.exception.DbException;
import nc.uap.pf.metadata.PfMetadataTools;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pf.change.PfUtilBaseTools;
import nc.vo.pf.changeui02.VotableVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.change.PublicHeadVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.pf.AssignableInfo;
import nc.vo.pub.pf.CurrencyInfo;
import nc.vo.pub.pf.IPfCompensateOnTerminated;
import nc.vo.pub.pf.IPfRetBackCheckInfo;
import nc.vo.pub.pf.IPfRetCheckInfo;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.pub.pf.TransitionSelectableInfo;
import nc.vo.pub.pf.workflow.IPFActionName;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uap.pf.PFRuntimeException;
import nc.vo.uap.pf.RetBackWfVo;
import nc.vo.wfengine.core.workflow.BackModal;
import nc.vo.wfengine.core.workflow.BasicWorkflowProcess;
import nc.vo.wfengine.definition.IApproveflowConst;
import nc.vo.wfengine.engine.ExecuteResult;
import nc.vo.wfengine.pub.WFTask;
import nc.vo.wfengine.pub.WfTaskStatus;
import nc.vo.xcgl.emaill.EmaillHVO;

/**
 * 工作流引擎服务实现类
 * 
 * @author wzhy 2004-2-21 
 * @modifier leijun 2006-4-7 使用动态锁机制不需释放锁了
 * @modifier leijun 2008-8 增加工作流相关处理
 * @modifier leijun 2008-12 根据扩展参数决定是否重新装载VO
 * 修改：yangtao   修改方法  forwardCheckFlow
 */
public class EngineService implements IWorkflowMachine {

	public EngineService() {
	}

	public PfUtilWorkFlowVO checkWorkFlow(String actionName, String billType, String currentDate,
			AggregatedValueObject billVO, HashMap hmPfExParams) throws BusinessException {
		IPfBusinessLock pfLock = new PfBusinessLock();
		String strBillId = null;
		try {
			//单据加锁和一致性校验
			//判定是否需要加锁 leijun+2008-12
			Object paramNoLock = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_NO_LOCK);
			if (paramNoLock == null)
				pfLock.lock(new VOLockData(billVO, billType), new VOConsistenceCheck(billVO, billType));

			//判定是否需要重新加载VO leijun+2008-12
			Object paramReloadVO = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_RELOAD_VO);
			if (paramReloadVO != null) {
				//				String billId = billVO.getParentVO().getPrimaryKey();
				//注册在VO对照中的billid不一定是单据的主键字段
				PublicHeadVO headInfo = PfUtilBaseTools.fetchHeadVO(billVO, billType);
				String billId = headInfo.pkBillId;
				billVO = new PFConfigImpl().queryBillDataVO(billType, billId);
				if (billVO == null)
					throw new PFBusinessException("错误：根据单据类型" + billType + "和单据" + billId + "获取不到单据聚合VO");
			}

			Hashtable<String, PfParameterVO> hashBilltypeToParavo = new Hashtable<String, PfParameterVO>();
			PfUtilBaseTools.getVariableValue(billType, actionName, currentDate, billVO, null, null, null,
					null, hashBilltypeToParavo);
			PfParameterVO paraVO = hashBilltypeToParavo.get(billType);
			strBillId = paraVO.m_billId;
			ActionEnvironment.getInstance().putParaVo(strBillId, paraVO);

			WorkflowQuery wq = new WorkflowQuery();
			boolean isWorkflow = IPFActionName.SIGNAL.equalsIgnoreCase(paraVO.m_actionName);
			PfUtilWorkFlowVO workFlow = isWorkflow ? wq.queryUnfinishedWorkflowItem(paraVO) : wq
					.queryUnfinishedApproveItem(paraVO);

			if (workFlow != null) {
				/* 从单据聚合VO中获得币种、金额数据 */
				CurrencyInfo cinfo = new CurrencyInfo();
				PfUtilBaseTools.fetchMoneyInfo(billVO, cinfo, billType);
				workFlow.putMoney(strBillId, cinfo);
			}
			return workFlow;
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow",
					"UPPpfworkflow-000004")/*检查工作项时出现数据库异常：*/
					+ ex.getMessage());
		} finally {
			//XXX::必须移除,否则内存泄漏
			ActionEnvironment.getInstance().putParaVo(strBillId, null);
			//释放锁 
			if (pfLock != null)
				pfLock.unLock();
		}
	}

	/**
	 * 返回流程平台所需要的单据id的字段名 （不一定是VO主键）
	 * @param billVO 
	 * @param billType
	 * @return
	 * @throws BusinessException
	 */
	private String getBillIDField(AggregatedValueObject billVO, String billType)
			throws BusinessException {
		String billIDFIeld = "";
		// 先查询单据类型关联的元数据模型，直接使用主键字段，或者元数据就执行billVO.getParentVO().getPrimaryKey()？？
		boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(billType);
		if (hasMeta) {
			IFlowBizItf fbi = PfMetadataTools.getBizItfImpl(billVO, IFlowBizItf.class);
			if (fbi == null)
				throw new PFRuntimeException("单据实体没有提供业务接口IFlowBizItf的实现类");
			billIDFIeld = fbi.getColumnName(IFlowBizItf.ATTRIBUTE_BILLID);
		} else {
			// 使用VO对照信息
			VotableVO votable = (VotableVO) PfDataCache.getBillTypeToVO(billType, true);
			billIDFIeld = votable.getBillid();
		}
		return billIDFIeld;
	}

	/**
	 * 在动作SAVE 启动审批流时 被调用，查询工作项
	 * @param paraVO 动作执行参数VO
	 * @return 工作流上下文VO
	 * @throws BusinessException
	 */
	private PfUtilWorkFlowVO checkApproveflowWhenSave(PfParameterVO paraVO) throws BusinessException {
		int status;
		WorkflowQuery queryDMO = new WorkflowQuery();
		try {
			//查询单据的五种审批流状态：自由态，提交态，进行中，通过，未通过
			status = queryDMO.queryApproveflowStatus(paraVO.m_billId, paraVO.m_billType);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("查询审批流状态出现数据库异常：" + e.getMessage());
		}

		switch (status) {
			case IPfRetCheckInfo.COMMIT:
			case IPfRetBackCheckInfo.NOSTATE:
			case IPfRetCheckInfo.NOPASS:
				try {
					return queryDMO.queryApproveflowOnSave(paraVO, status);
				} catch (DbException e) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("启动时查询审批流出现数据库异常：" + e.getMessage());
				}
			case IPfRetCheckInfo.GOINGON:
				//审批进行中,却再次提交,返回空,即不影响流程
				return null;
			default:
				return null;
		}
	}

	/**
	 * 在动作START 启动工作流时 被调用，查询工作项
	 * @param paraVO
	 * @return
	 * @throws BusinessException
	 * @since 5.5
	 */
	private PfUtilWorkFlowVO checkWorkflowWhenStart(PfParameterVO paraVO) throws BusinessException {
		int status;
		WorkflowQuery queryDMO = new WorkflowQuery();
		try {
			//查询单据的工作流状态：自由态，提交态，进行中，结束（通过）
			status = queryDMO.queryWorkflowStatus(paraVO.m_billId, paraVO.m_billType);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("查询工作流状态出现数据库异常：" + e.getMessage());
		}

		switch (status) {
			case IPfRetCheckInfo.COMMIT:
			case IPfRetBackCheckInfo.NOSTATE:
				try {
					return queryDMO.queryWorkflowOnSave(paraVO, status);
				} catch (DbException e) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("启动时查询工作流出现数据库异常：" + e.getMessage());
				}
			case IPfRetCheckInfo.GOINGON:
				//工作流进行中,却再次启动,返回空,即不影响流程
				return null;
			case IPfRetCheckInfo.PASSING:
				//工作流已经结束（通过）,却再次启动,返回空,即不影响流程
				return null;
			default:
				return null;
		}
	}

	public RetBackWfVo backwardCheckFlow(PfParameterVO paraVo) throws BusinessException {
		//UNAPPROVE时逐级弃审的处理
		RetBackWfVo retBackVO = new RetBackWfVo();
		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			//查询有无 已完成的审批工作项
			if (paraVo.m_workFlow == null) {
				paraVo.m_workFlow = queryDMO.queryFinishedApproveItem(paraVo);
			}
			if (paraVo.m_workFlow == null) {
				//如果没有已完成的审批工作项，则直接把单据弃审为自由态
				retBackVO.setIsFinish(UFBoolean.TRUE);
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				return retBackVO;
			}

			//查流程实例判断当前单据单据是否审批结束
			boolean isFinished = queryDMO.isApproveflowFinished(paraVo.m_billId, paraVo.m_billType);
			if (isFinished) {
				//当前单据是在审批通过后弃审的
				retBackVO.setIsFinish(UFBoolean.TRUE);
			}

			//执行弃审任务
			WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
			//任务要赋 操作人,修改时间
			currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
			// currentTask.setNote(paraVo.m_workFlow.getCheckNote());
			currentTask.setOperator(paraVo.m_operator);
			currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			currentTask.setTaskType(WFTask.TYPE_WITHDRAW);

			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);

			//弃审任务执行完成后的处理
			boolean isGoing = queryDMO.isExistFinishedApproveItem(paraVo.m_billId, paraVo.m_billType);
			if (isGoing) {
				//逐级弃审后的状态为审批进行中
				retBackVO.setBackState(IPfRetCheckInfo.GOINGON);
				if (isFinished) {
					//审批环节多于1个时，最后一个人弃审要通知制单人
					WorknoteManager noteMgr = new WorknoteManager();
					noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000136}"/* @res "弃审单据。" */, paraVo);
				}
			} else {
				//逐级弃审后的状态为自由态
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				//queryDMO.updateAllActivityInstanceStatusToInefficient(currentTask.getBillID(), currentTask.getBillType());
				WorknoteManager noteMgr = new WorknoteManager();
				noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000137}"/* @res "已弃审,请修改单据。" */, paraVo);
			}
			if (!paraVo.m_makeBillOperator.equals(paraVo.m_workFlow.getSenderMan())) {
				retBackVO.setPreCheckMan(paraVo.m_workFlow.getSenderMan());
			}
			return retBackVO;

		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("逐级弃审时发生数据库异常：" + ex.getMessage());
		} finally {
			//XXX::必须移除,否则内存泄漏
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
		}

	}

	public RetBackWfVo backCheckFlow(PfParameterVO paraVo) throws BusinessException {
		//UNAPPROVE时的处理，支持逐级弃审和一弃到底
		RetBackWfVo retBackVO = new RetBackWfVo();
		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			//查询有无 已完成的审批工作项
			paraVo.m_workFlow = queryDMO.queryFinishedApproveItem(paraVo);
			if (paraVo.m_workFlow == null) {
				//如果没有已完成的审批工作项，则直接把单据弃审为自由态
				retBackVO.setIsFinish(UFBoolean.TRUE);
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				return retBackVO;
			}

			//弃审时，从单据VO获取金额信息
			CurrencyInfo cinfo = new CurrencyInfo();
			PfUtilBaseTools.fetchMoneyInfo(paraVo.m_preValueVo, cinfo, paraVo.m_billType);
			paraVo.m_workFlow.putMoney(paraVo.m_billId, cinfo);

			//查询审批流定义中的弃审模式
			//String processDefPK = paraVo.m_workFlow.getTaskInfo().getTask().getWfProcessDefPK();
			//BasicWorkflowProcess bwp = (BasicWorkflowProcess) queryDMO.findParsedWfProcessByDefPK(procInstPK);
			String procInstPK = paraVo.m_workFlow.getTaskInfo().getTask().getWfProcessInstancePK();
			BasicWorkflowProcess bwp = (BasicWorkflowProcess) queryDMO
					.findParsedMainWfProcessByInstancePK(procInstPK);
			boolean isStepbyStep = bwp.getBackModal() == BackModal.STEP;

			//查流程实例判断当前单据单据是否审批结束
			boolean isFinished = queryDMO.isApproveflowFinished(paraVo.m_billId, paraVo.m_billType);
			if (isFinished) {
				//当前单据是在审批通过后弃审的
				retBackVO.setIsFinish(UFBoolean.TRUE);
			} else if (!isStepbyStep) {
				//不允许审批进行中的一弃到底
				throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow",
						"UPPpfworkflow-000135")/* @res [审批流]一弃到底时，只有审批流程结束后才可弃审！*/);
			}

			//执行弃审任务
			WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
			//任务要赋 操作人,修改时间
			currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
			// currentTask.setNote(paraVo.m_workFlow.getCheckNote());
			currentTask.setOperator(paraVo.m_operator);
			currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			currentTask.setTaskType(WFTask.TYPE_WITHDRAW);
			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);

			//弃审任务执行完成后的处理
			if (isStepbyStep) {
				boolean isGoing = queryDMO.isExistFinishedApproveItem(paraVo.m_billId, paraVo.m_billType);
				if (isGoing) {
					//逐级弃审后的状态为审批进行中
					retBackVO.setBackState(IPfRetCheckInfo.GOINGON);
					retBackVO.setPreCheckMan(paraVo.m_workFlow.getSenderMan());
					if (isFinished) {
						//审批环节多于1个时，最后一个人弃审要通知制单人
						WorknoteManager noteMgr = new WorknoteManager();
						noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000136}"/* @res "弃审单据。" */, paraVo);
					}
				} else {
					//逐级弃审后的状态为自由态
					retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
					//queryDMO.updateAllActivityInstanceStatusToInefficient(currentTask.getBillID(), currentTask.getBillType());
					WorknoteManager noteMgr = new WorknoteManager();
					noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000137}"/* @res "已弃审,请修改单据。" */, paraVo);
				}
			} else {
				//一弃到底，单据置为自由态，并给制单人发送业务消息
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				WorknoteManager noteMgr = new WorknoteManager();
				noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000137}"/* @res "已弃审,请修改单据。" */, paraVo);
			}
			return retBackVO;
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("审批流弃审时发生数据库异常：" + e.getMessage());
		} finally {
			//XXX::必须移除,否则内存泄漏
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
		}
	}

	public void cancelCheckFlow(String billType, String billId, String checkMan)
			throws BusinessException {
		//UNAPPROVE时一弃到底的处理
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_billType = billType;
		paraVo.m_billId = billId;
		paraVo.m_operator = checkMan;
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			//查询有无 已完成的审批工作项
			PfUtilWorkFlowVO workFlow = queryDMO.queryFinishedApproveItem(paraVo);
			if (workFlow == null)
				return;

			//WARN::审批流程结束后，才允许一弃到底
			boolean isFinished = queryDMO.isApproveflowFinished(paraVo.m_billId, paraVo.m_billType);
			if (!isFinished)
				throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow",
						"UPPpfworkflow-000135")/* @res [审批流]一弃到底时，只有审批流程结束后才可弃审！*/);

			//执行弃审任务
			WFTask currentTask = workFlow.getTaskInfo().getTask();
			//任务要赋 操作人,修改时间
			currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
			currentTask.setOperator(checkMan);
			currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			currentTask.setTaskType(WFTask.TYPE_WITHDRAW);

			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("一弃到底时发生数据库异常：" + ex.getMessage());
		}

	}

	public void deleteCheckFlow(String billType, String billId, String checkMan,
			boolean isStepUnApprove) throws BusinessException {
		int status;
		try {
			//状态查询
			WorkflowQuery queryDMO = new WorkflowQuery();
			status = queryDMO.queryApproveflowStatus(billId, billType);
			//lj@2006-11-3 审批通过后也可把流程实例删除
			switch (status) {
				case IPfRetBackCheckInfo.NOSTATE:
				case IPfRetCheckInfo.COMMIT:
				case IPfRetCheckInfo.NOPASS:
				case IPfRetCheckInfo.PASSING:
					queryDMO.deleteWorkflow(billId, billType, false, IApproveflowConst.WORKFLOW_TYPE_APPROVE);
					break;
				case IPfRetCheckInfo.GOINGON:
					//if (isStepUnApprove) {
					//	throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow", "UPPpfworkflow-000271")/* @res "逐级弃审时,所有人弃审后才能删除单据" */);
					//} else {
					//删除流程信息
					queryDMO.deleteWorkflow(billId, billType, false, IApproveflowConst.WORKFLOW_TYPE_APPROVE);
					//给所有审批人和制单人发通知消息
					WorknoteManager noteMgr = new WorknoteManager();
					noteMgr.sendAllPersonMessage("{UPPpfworkflow-000272}"/* @res "被删除" */, null, billId,
							billType, checkMan);
					//}
					break;
			}
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("删除审批流出现数据库异常：" + ex.getMessage());
		}

	}

	public List forwardCheckFlow(PfParameterVO paraVo) throws BusinessException {
		int status;
		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
		try {
			if (!currentTask.isBackToFirstActivity()) {
				//1-审批
				//为任务对象填充 后继活动的参与者指派信息 
				fillAssignableInfo(paraVo, currentTask);
				//为任务对象填充 后继转移的可手工选择信息 
				fillTransitionSelectableInfo(paraVo, currentTask);

				//任务要赋 输出业务数据,操作人,审批结果,修改时间
				//审批批语放在CurrencyInfo里,在AbstractComplier里存
				currentTask.setOutObject(paraVo.m_preValueVo);
				currentTask.setOperator(paraVo.m_operator);
				if (paraVo.m_workFlow.isIsCheckPass()) {
					currentTask.setApproveResult("Y");
				} else {
					currentTask.setApproveResult("N");
				}
				currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
				currentTask.setNote(paraVo.m_workFlow.getCheckNote());
				currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			} else {
				//2-驳回
				//任务要赋 操作人,修改时间
				currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
				//currentTask.setNote(paraVo.m_workFlow.getCheckNote());
				currentTask.setOperator(paraVo.m_operator);
				currentTask.setNote(paraVo.m_workFlow.getCheckNote());
				currentTask.setPk_Checkflow(paraVo.m_workFlow.getWorkFlowOid());
				currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
				currentTask.setTaskType(WFTask.TYPE_BACKWARD);

				//FIXME::增加驳回结果?! lj@2005-5-16
				currentTask.setApproveResult("R");
			}

			//向引擎发送任务
			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
			
			PfParameterVO tmpparaVo = ActionEnvironment.getInstance().getParaVo(paraVo.m_billId + "@auto");
			if(tmpparaVo==null)
				tmpparaVo = ActionEnvironment.getInstance().getParaVo(paraVo.m_billId);
			
			paraVo = tmpparaVo;
			//单据工作流状态查询,并进行处理
			WorkflowQuery queryDMO = new WorkflowQuery();
			boolean isWorkflow = IApproveflowConst.WORKFLOW_TYPE_WORKFLOW == currentTask
					.getWorkflowType();
			status = isWorkflow ? queryDMO.queryWorkflowStatus(paraVo.m_billId, paraVo.m_billType)
					: queryDMO.queryApproveflowStatus(paraVo.m_billId, paraVo.m_billType);
			if (status == IPfRetCheckInfo.PASSING || status == IPfRetCheckInfo.NOPASS) {
				//流程结束后将所有未完成的活动实例置为无效
				queryDMO.updateUnfinishedActivityInstanceStatusToInefficient(currentTask.getBillID(),
						currentTask.getBillType());
			} else if (status == IPfRetBackCheckInfo.NOSTATE) {
				//单据回到自由态后将所有活动实例置为无效
				queryDMO.updateAllActivityInstanceStatusToInefficient(currentTask.getBillID(), currentTask
						.getBillType());
			}

			WorknoteManager noteMgr = new WorknoteManager();
			if (status == IPfRetCheckInfo.PASSING) {
				//如果审批通过,则给制单人发送非业务消息
				Logger.debug("***流程实例结束之审批通过,则给制单人发送非业务消息***");
			
				noteMgr.sendMessageToBillMaker(isWorkflow ? "{UPPpfworkflow-000701}"/* "工作流处理完毕" */
				: "{UPPpfworkflow-000273}"/* "审批通过" */, paraVo);
				
				//发送邮件给操作员关联的业务员  yangtao
				
				//发送邮件
				EmaillHVO newvo=MaillTool.getmaillinfo("4");
				String emailladd=MaillTool.getEmaillAdd(MaillTool.getClerk(paraVo));
				if(emailladd!=null){
				MaillTool.sendApprovalEmail(newvo,paraVo,new String []{emailladd},null,null);
				}
			

			} else if (status == IPfRetCheckInfo.NOPASS) {
				//如果审批不通过,则给参与本次流程的人发送非业务消息
				Logger.debug("***流程实例结束之审批不通过,则给参与本次流程的人发送非业务消息***");
				noteMgr.sendMessageAfterNoPass("{UPPpfworkflow-000274}"/* "审批不通过" */, paraVo);
				
			}
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("流程流转出现数据库异常：" + ex.getMessage());
		} finally {
			//XXX::必须移除,否则内存泄漏
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId + "@auto", null);
		}

		ArrayList retList = new ArrayList();
		retList.add(status);
		retList.add(paraVo);
		return retList;
	}

	public boolean sendWorkFlowOnSave(PfParameterVO paraVo, Hashtable m_methodReturnHas,
			HashMap hmPfExParams) throws BusinessException {
		Logger.debug("BILLNO**********" + paraVo.m_billNo + "**********");
		Logger.debug("BILLID**********" + paraVo.m_billId + "**********");

		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		ActionEnvironment.getInstance().putMethodReturn(paraVo.m_billId, m_methodReturnHas);
		try {
			boolean isWorkflow = IPFActionName.START.equalsIgnoreCase(paraVo.m_actionName);
			if (!isWorkflow)
				//启动审批流
				return startApproveflow(paraVo, hmPfExParams);
			else
				//启动工作流
				return startWorkflow(paraVo, hmPfExParams);

		} finally {
			// XXX::必须移除,否则内存泄漏
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
			ActionEnvironment.getInstance().putMethodReturn(paraVo.m_billId, null);
		}
	}

	/**
	 * 启动工作流
	 * @param paraVo
	 * @param hmPfExParams
	 * @return
	 * @throws BusinessException
	 */
	private boolean startWorkflow(PfParameterVO paraVo, HashMap hmPfExParams)
			throws BusinessException {
		Logger.debug("动作编码为" + paraVo.m_actionName + "，启动工作流");

		//启动时先删除旧流程信息，流程状态为"通过、进行中"的除外
		deleteWhenStartExceptPassOrGoing(paraVo, IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);

		if (paraVo.m_workFlow == null || paraVo.m_workFlow.getTaskInfo().getTask() == null) {
			//如果扩展参数中含有流程定义PK，则直接赋值
			Object paramDefPK = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_FLOWPK);
			paraVo.m_flowDefPK = paramDefPK == null ? null : String.valueOf(paramDefPK);

			Object noteChecked = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_NOTE_CHECKED);
			//leijun+2009-7 如果前台PfUtilClient.runAction已经检查不出工作项，这里也没有必要再次检查
			if (noteChecked == null)
				paraVo.m_workFlow = checkWorkflowWhenStart(paraVo);
			if (paraVo.m_workFlow == null)
				//单据的工作流状态处于进行中，已完成
				return false;
		}

		/*
		 * 启动工作流时，删除旧的工作流信息（但保留工作项处理历史）
		 *   1)自由态（驳回或流转到制单环节），重新提交，会走新的工作流定义
		 *   2)提交态、不通过态，重新提交，会走新的工作流定义
		 * XXX:leijun@2008-11 
		 */
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			queryDMO.deleteWorkflow(paraVo.m_billId, paraVo.m_billType, true,
					IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("启动工作流出现数据库异常：" + ex.getMessage());
		}

		//提交时，从单据VO获取金额信息
		CurrencyInfo cinfo = new CurrencyInfo();
		PfUtilBaseTools.fetchMoneyInfo(paraVo.m_preValueVo, cinfo, paraVo.m_billType);
		paraVo.m_workFlow.putMoney(paraVo.m_billId, cinfo);

		WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
		// 修正单据号后生成的情况.
		currentTask.setBillNO(paraVo.m_billNo);
		currentTask.setBillID(paraVo.m_billId);

		//为任务对象填充 后继活动的参与者指派信息 
		fillAssignableInfo(paraVo, currentTask);
		//为任务对象填充 后继转移的可手工选择信息 
		fillTransitionSelectableInfo(paraVo, currentTask);

		// 任务要赋 输出业务数据,操作人,修改时间
		currentTask.setOutObject(paraVo.m_preValueVo);
		//currentTask.setOperator(paraVo.m_operator);
		currentTask.setOperator(paraVo.m_makeBillOperator); //制单环节
		currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
		currentTask.setStatus(WfTaskStatus.Finished.getIntValue()); //制单任务已完成

		//执行任务
		ExecuteResult result = WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
		if (result != null && result.isWorkflowNotExist()) {
			//工作流不启动
			return false;
		} else if (result != null && result.isApprovePass()) {
			//工作流启动后，随即结束（通过）
			//throw new ApproveAfterCommitException(IPfRetCheckInfo.PASSING, NCLangResOnserver
			//		.getInstance().getStrByID("pfworkflow", "UPPpfworkflow-000273")/* 审批通过 */);
			return true;
		} else
			return true;
	}

	/**
	 * 启动时先删除旧流程信息，流程状态为"通过、进行中"的除外
	 * <li>只有自由态，提交态，未通过态，才删除
	 * 
	 * @param paraVo
	 * @param iWfType
	 * @throws BusinessException
	 */
	private void deleteWhenStartExceptPassOrGoing(PfParameterVO paraVo, int iWfType)
			throws BusinessException {
		int status;
		WorkflowQuery queryDMO = new WorkflowQuery();
		try {
			//查询单据的五种审批流状态：自由态，提交态，进行中，通过，未通过
			status = iWfType == IApproveflowConst.WORKFLOW_TYPE_WORKFLOW ? queryDMO.queryWorkflowStatus(
					paraVo.m_billId, paraVo.m_billType) : queryDMO.queryApproveflowStatus(paraVo.m_billId,
					paraVo.m_billType);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("查询流程状态出现数据库异常：" + e.getMessage());
		}

		/*
		 * 启动审批流时，删除旧的审批流信息（但保留审批历史）
		 * 1)自由态（驳回或流转到制单环节），重新提交，会走新的审批流定义
		 * 2)提交态、不通过态，重新提交，会走新的审批流定义
		 */
		switch (status) {
			case IPfRetCheckInfo.COMMIT:
			case IPfRetBackCheckInfo.NOSTATE:
			case IPfRetCheckInfo.NOPASS:
				try {
					queryDMO.deleteWorkflow(paraVo.m_billId, paraVo.m_billType, true, iWfType);
				} catch (DbException e) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("删除流程出现数据库异常：" + e.getMessage());
				}
				break;
			default:
				return;
		}
	}

	/**
	 * 启动审批流
	 * @param paraVo
	 * @param hmPfExParams
	 * @return
	 * @throws BusinessException
	 */
	private boolean startApproveflow(PfParameterVO paraVo, HashMap hmPfExParams)
			throws BusinessException {
		Logger.debug("动作编码为" + paraVo.m_actionName + "，启动审批流");

		//启动时先删除旧流程信息，流程状态为"通过、进行中"的除外
		deleteWhenStartExceptPassOrGoing(paraVo, IApproveflowConst.WORKFLOW_TYPE_APPROVE);

		if (paraVo.m_workFlow == null || paraVo.m_workFlow.getTaskInfo().getTask() == null) {
			//如果扩展参数中含有流程定义PK，则直接赋值
			Object paramDefPK = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_FLOWPK);
			paraVo.m_flowDefPK = paramDefPK == null ? null : String.valueOf(paramDefPK);

			Object noteChecked = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_NOTE_CHECKED);
			//leijun+2009-7 如果前台PfUtilClient.runAction已经检查不出工作项，这里也没有必要再次检查
			if (noteChecked == null)
				paraVo.m_workFlow = checkApproveflowWhenSave(paraVo);
			if (paraVo.m_workFlow == null)
				//自由态且无可启动的流程定义；审批中、或已通过
				return false;
		}

		/*
		 * 启动审批流时，删除旧的审批流信息（但保留审批历史）
		 *   1)自由态（驳回或流转到制单环节），重新提交，会走新的审批流定义
		 *   2)提交态、不通过态，重新提交，会走新的审批流定义
		 * XXX:leijun@2008-11 
		 */
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			queryDMO.deleteWorkflow(paraVo.m_billId, paraVo.m_billType, true,
					IApproveflowConst.WORKFLOW_TYPE_APPROVE);
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("启动审批流出现数据库异常：" + ex.getMessage());
		}

		//提交时，从单据VO获取金额信息
		CurrencyInfo cinfo = new CurrencyInfo();
		PfUtilBaseTools.fetchMoneyInfo(paraVo.m_preValueVo, cinfo, paraVo.m_billType);
		paraVo.m_workFlow.putMoney(paraVo.m_billId, cinfo);

		WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
		// 修正单据号后生成的情况.
		currentTask.setBillNO(paraVo.m_billNo);
		currentTask.setBillID(paraVo.m_billId);

		//为任务对象填充 后继活动的参与者指派信息 
		fillAssignableInfo(paraVo, currentTask);

		// 任务要赋 输出业务数据,操作人,修改时间
		currentTask.setOutObject(paraVo.m_preValueVo);
		currentTask.setOperator(paraVo.m_operator);
		currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
		currentTask.setStatus(WfTaskStatus.Finished.getIntValue()); //制单任务已完成

		//执行任务
		ExecuteResult result = WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
		if (result != null && result.isWorkflowNotExist()) {
			//审批流不启动
			return false;
		} else if (result != null && result.isApprovePass()) {
			//提交即审批通过
			throw new ApproveAfterCommitException(IPfRetCheckInfo.PASSING, NCLangResOnserver
					.getInstance().getStrByID("pfworkflow", "UPPpfworkflow-000273")/* 审批通过 */);
		} else
			return true;
	}

	/**
	 * 把后继活动的参与者指派信息赋值给任务对象
	 * 
	 * @param paraVo
	 * @param currentTask
	 */
	private void fillAssignableInfo(PfParameterVO paraVo, WFTask currentTask) {
		Vector assInfos = paraVo.m_workFlow.getTaskInfo().getAssignableInfos();
		for (int i = 0; i < assInfos.size(); i++) {
			AssignableInfo assInfo = (AssignableInfo) assInfos.get(i);
			currentTask.setAssignNextOperators(WFTask.getActAssignID(assInfo.getProcessDefPK(), assInfo
					.getActivityDefId()), assInfo.getAssignedOperatorPKs());
		}
	}

	/**
	 * 把后继转移的可手工选择信息赋值给任务对象
	 * @param paraVo
	 * @param currentTask
	 */
	private void fillTransitionSelectableInfo(PfParameterVO paraVo, WFTask currentTask) {
		Vector tsInfos = paraVo.m_workFlow.getTaskInfo().getTransitionSelectableInfos();
		for (int i = 0; i < tsInfos.size(); i++) {
			TransitionSelectableInfo tsInfo = (TransitionSelectableInfo) tsInfos.get(i);
			if (tsInfo.isChoiced()) {
				Vector vec = new Vector();
				vec.add(tsInfo.getTransitionDefId());
				currentTask.setAssignNextTransition(WFTask.getTransAssignID(tsInfo.getProcessDefPK(),
						tsInfo.getTransitionDefId()), vec);
			}
		}
	}

	public void terminateWorkflow(String billid, String pkBilltype, String billNo, int iWorkflowtype)
			throws BusinessException {
		try {
			switch (iWorkflowtype) {
				case IApproveflowConst.WORKFLOW_TYPE_APPROVE:
					stopApproveflow(billid, pkBilltype, billNo);
					break;
				case IApproveflowConst.WORKFLOW_TYPE_WORKFLOW:
					stopWorkflow(billid, pkBilltype, billNo);
					break;
				default:
					throw new PFBusinessException("传入了错误的参数值iWorkflowtype");
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage());
		}
	}

	/**
	 * 终止工作流实例
	 * @param billid
	 * @param pkBilltype
	 * @param billNo
	 * @throws Exception
	 */
	private void stopWorkflow(String billid, String pkBilltype, String billNo) throws Exception {
		// 1.判定该单据是否处于工作流进行中，如果不是，则抛出异常返回
		WorkflowQuery queryDMO = new WorkflowQuery();
		int status = queryDMO.queryWorkflowStatus(billid, pkBilltype);
		if (status == IPfRetCheckInfo.PASSING)
			throw new PFBusinessException("单据所属的流程已结束，无法终止！");

		// 2.修改单据数据库状态为自由态
		boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(pkBilltype);
		AbstractBusiStateCallback absc = hasMeta ? new PFBusiStateOfMeta() : new PFBusiState();

		//查询出单据VO实体
		IPFConfig pfcfg = (IPFConfig) NCLocator.getInstance().lookup(IPFConfig.class.getName());
		AggregatedValueObject billVO = pfcfg.queryBillDataVO(pkBilltype, billid);
		//构造工作流参数VO
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_workFlow = new PfUtilWorkFlowVO();
		paraVo.m_workFlow.setCheckNote("流程终止");
		paraVo.m_billId = billid;
		paraVo.m_billType = pkBilltype;
		paraVo.m_preValueVo = billVO;
		absc.execUnApproveState(paraVo, null, IPfRetBackCheckInfo.NOSTATE);

		// 3.删除该单据相关的流程信息
		queryDMO.deleteWorkflow(billid, pkBilltype, false, IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);

		// 4.给制单人发送消息，并不给已处理人发送消息
		String strMakerId = queryDMO.queryBillmakerOfInstance(billid, pkBilltype,
				IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);
		if (!StringUtil.isEmptyWithTrim(strMakerId))
			new WorknoteManager().sendMessageToBillMaker("{UPPpfworkflow-000453}"/* "所属的流程已被终止！" */,
					InvocationInfoProxy.getInstance().getUserCode(), strMakerId, billNo, pkBilltype, billid);

		// 5.业务回滚
		Object objBizRule = PfUtilTools.getBizRuleImpl(pkBilltype);
		if (objBizRule instanceof IPfCompensateOnTerminated) {
			((IPfCompensateOnTerminated) objBizRule).rollbackOnWorkflowTerminated(billid, pkBilltype);
		}
	}

	/**
	 * 终止审批流实例
	 * @param billid
	 * @param pkBilltype
	 * @param billNo
	 * @throws Exception
	 */
	private void stopApproveflow(String billid, String pkBilltype, String billNo) throws Exception {
		// 1.判定该单据是否处于审批进行中，如果不是，则抛出异常返回
		WorkflowQuery queryDMO = new WorkflowQuery();
		int status = queryDMO.queryApproveflowStatus(billid, pkBilltype);
		if (status == IPfRetCheckInfo.PASSING || status == IPfRetCheckInfo.NOPASS)
			throw new PFBusinessException("单据所属的流程已结束，无法终止！");

		// 2.修改单据数据库状态为自由态
		boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(pkBilltype);
		AbstractBusiStateCallback absc = hasMeta ? new PFBusiStateOfMeta() : new PFBusiState();

		//查询出单据VO实体
		IPFConfig pfcfg = (IPFConfig) NCLocator.getInstance().lookup(IPFConfig.class.getName());
		AggregatedValueObject billVO = pfcfg.queryBillDataVO(pkBilltype, billid);
		//构造工作流参数VO
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_workFlow = new PfUtilWorkFlowVO();
		paraVo.m_workFlow.setCheckNote("流程终止");
		paraVo.m_billId = billid;
		paraVo.m_billType = pkBilltype;
		paraVo.m_preValueVo = billVO;
		absc.execUnApproveState(paraVo, null, IPfRetBackCheckInfo.NOSTATE);

		// 3.删除该单据相关的流程信息
		queryDMO.deleteWorkflow(billid, pkBilltype, false, IApproveflowConst.WORKFLOW_TYPE_APPROVE);

		// 4.给制单人发送消息，并不给已审批人发送消息
		String strMakerId = queryDMO.queryBillmakerOfInstance(billid, pkBilltype,
				IApproveflowConst.WORKFLOW_TYPE_APPROVE);
		if (!StringUtil.isEmptyWithTrim(strMakerId))
			new WorknoteManager().sendMessageToBillMaker("{UPPpfworkflow-000453}"/* "所属的流程已被终止！" */,
					InvocationInfoProxy.getInstance().getUserCode(), strMakerId, billNo, pkBilltype, billid);

	}

	public PfUtilWorkFlowVO checkWorkitemOnSave(String actionName, String billType,
			String currentDate, AggregatedValueObject billVO, HashMap hmPfExParams)
			throws BusinessException {
		IPfBusinessLock pfLock = new PfBusinessLock();
		String strBillId = null;
		try {
			//单据加锁和一致性校验
			pfLock.lock(new VOLockData(billVO, billType), new VOConsistenceCheck(billVO, billType));

			//判定是否需要重新加载VO leijun+2008-12
			Object paramReloadVO = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_RELOAD_VO);
			if (paramReloadVO != null) {
				//				String billId = billVO.getParentVO().getPrimaryKey();
				//注册在VO对照中的billid不一定是单据的主键字段
				String billId = "";
				String billIdField = getBillIDField(billVO, billType);
				if (!StringUtil.isEmptyWithTrim(billIdField)) {
					billId = (String) billVO.getParentVO().getAttributeValue(billIdField);
				} else {
					billId = billVO.getParentVO().getPrimaryKey();
				}
				billVO = new PFConfigImpl().queryBillDataVO(billType, billId);
				if (billVO == null)
					throw new PFBusinessException("错误：根据单据类型" + billType + "和单据" + billId + "获取不到单据聚合VO");
			}

			Hashtable<String, PfParameterVO> hashBilltypeToParavo = new Hashtable<String, PfParameterVO>();
			PfUtilBaseTools.getVariableValue(billType, actionName, currentDate, billVO, null, null, null,
					null, hashBilltypeToParavo);
			PfParameterVO paraVO = hashBilltypeToParavo.get(billType);
			strBillId = paraVO.m_billId;
			ActionEnvironment.getInstance().putParaVo(strBillId, paraVO);

			//为流程定义赋值
			Object paramDefPK = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_FLOWPK);
			paraVO.m_flowDefPK = paramDefPK == null ? null : String.valueOf(paramDefPK);

			boolean isWorkflow = IPFActionName.START.equalsIgnoreCase(paraVO.m_actionName);
			return isWorkflow ? checkWorkflowWhenStart(paraVO) : checkApproveflowWhenSave(paraVO);

		} finally {
			//XXX::必须移除,否则内存泄漏
			ActionEnvironment.getInstance().putParaVo(strBillId, null);
			//释放锁
			if (pfLock != null)
				pfLock.unLock();
		}
	}
	
	public Object processSingleBillFlow_RequiresNew(String actionName, String billOrTranstype, String currentDate, PfUtilWorkFlowVO workflowVo, AggregatedValueObject billvo, Object userObj, HashMap eParam)
			throws BusinessException {
		return NCLocator.getInstance().lookup(IPFBusiAction.class).processAction(actionName, billOrTranstype, currentDate, workflowVo, billvo, userObj, eParam);
	}
}