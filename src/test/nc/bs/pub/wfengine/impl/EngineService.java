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
 * �������������ʵ����
 * 
 * @author wzhy 2004-2-21 
 * @modifier leijun 2006-4-7 ʹ�ö�̬�����Ʋ����ͷ�����
 * @modifier leijun 2008-8 ���ӹ�������ش���
 * @modifier leijun 2008-12 ������չ���������Ƿ�����װ��VO
 * �޸ģ�yangtao   �޸ķ���  forwardCheckFlow
 */
public class EngineService implements IWorkflowMachine {

	public EngineService() {
	}

	public PfUtilWorkFlowVO checkWorkFlow(String actionName, String billType, String currentDate,
			AggregatedValueObject billVO, HashMap hmPfExParams) throws BusinessException {
		IPfBusinessLock pfLock = new PfBusinessLock();
		String strBillId = null;
		try {
			//���ݼ�����һ����У��
			//�ж��Ƿ���Ҫ���� leijun+2008-12
			Object paramNoLock = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_NO_LOCK);
			if (paramNoLock == null)
				pfLock.lock(new VOLockData(billVO, billType), new VOConsistenceCheck(billVO, billType));

			//�ж��Ƿ���Ҫ���¼���VO leijun+2008-12
			Object paramReloadVO = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_RELOAD_VO);
			if (paramReloadVO != null) {
				//				String billId = billVO.getParentVO().getPrimaryKey();
				//ע����VO�����е�billid��һ���ǵ��ݵ������ֶ�
				PublicHeadVO headInfo = PfUtilBaseTools.fetchHeadVO(billVO, billType);
				String billId = headInfo.pkBillId;
				billVO = new PFConfigImpl().queryBillDataVO(billType, billId);
				if (billVO == null)
					throw new PFBusinessException("���󣺸��ݵ�������" + billType + "�͵���" + billId + "��ȡ�������ݾۺ�VO");
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
				/* �ӵ��ݾۺ�VO�л�ñ��֡�������� */
				CurrencyInfo cinfo = new CurrencyInfo();
				PfUtilBaseTools.fetchMoneyInfo(billVO, cinfo, billType);
				workFlow.putMoney(strBillId, cinfo);
			}
			return workFlow;
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow",
					"UPPpfworkflow-000004")/*��鹤����ʱ�������ݿ��쳣��*/
					+ ex.getMessage());
		} finally {
			//XXX::�����Ƴ�,�����ڴ�й©
			ActionEnvironment.getInstance().putParaVo(strBillId, null);
			//�ͷ��� 
			if (pfLock != null)
				pfLock.unLock();
		}
	}

	/**
	 * ��������ƽ̨����Ҫ�ĵ���id���ֶ��� ����һ����VO������
	 * @param billVO 
	 * @param billType
	 * @return
	 * @throws BusinessException
	 */
	private String getBillIDField(AggregatedValueObject billVO, String billType)
			throws BusinessException {
		String billIDFIeld = "";
		// �Ȳ�ѯ�������͹�����Ԫ����ģ�ͣ�ֱ��ʹ�������ֶΣ�����Ԫ���ݾ�ִ��billVO.getParentVO().getPrimaryKey()����
		boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(billType);
		if (hasMeta) {
			IFlowBizItf fbi = PfMetadataTools.getBizItfImpl(billVO, IFlowBizItf.class);
			if (fbi == null)
				throw new PFRuntimeException("����ʵ��û���ṩҵ��ӿ�IFlowBizItf��ʵ����");
			billIDFIeld = fbi.getColumnName(IFlowBizItf.ATTRIBUTE_BILLID);
		} else {
			// ʹ��VO������Ϣ
			VotableVO votable = (VotableVO) PfDataCache.getBillTypeToVO(billType, true);
			billIDFIeld = votable.getBillid();
		}
		return billIDFIeld;
	}

	/**
	 * �ڶ���SAVE ����������ʱ �����ã���ѯ������
	 * @param paraVO ����ִ�в���VO
	 * @return ������������VO
	 * @throws BusinessException
	 */
	private PfUtilWorkFlowVO checkApproveflowWhenSave(PfParameterVO paraVO) throws BusinessException {
		int status;
		WorkflowQuery queryDMO = new WorkflowQuery();
		try {
			//��ѯ���ݵ�����������״̬������̬���ύ̬�������У�ͨ����δͨ��
			status = queryDMO.queryApproveflowStatus(paraVO.m_billId, paraVO.m_billType);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("��ѯ������״̬�������ݿ��쳣��" + e.getMessage());
		}

		switch (status) {
			case IPfRetCheckInfo.COMMIT:
			case IPfRetBackCheckInfo.NOSTATE:
			case IPfRetCheckInfo.NOPASS:
				try {
					return queryDMO.queryApproveflowOnSave(paraVO, status);
				} catch (DbException e) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("����ʱ��ѯ�������������ݿ��쳣��" + e.getMessage());
				}
			case IPfRetCheckInfo.GOINGON:
				//����������,ȴ�ٴ��ύ,���ؿ�,����Ӱ������
				return null;
			default:
				return null;
		}
	}

	/**
	 * �ڶ���START ����������ʱ �����ã���ѯ������
	 * @param paraVO
	 * @return
	 * @throws BusinessException
	 * @since 5.5
	 */
	private PfUtilWorkFlowVO checkWorkflowWhenStart(PfParameterVO paraVO) throws BusinessException {
		int status;
		WorkflowQuery queryDMO = new WorkflowQuery();
		try {
			//��ѯ���ݵĹ�����״̬������̬���ύ̬�������У�������ͨ����
			status = queryDMO.queryWorkflowStatus(paraVO.m_billId, paraVO.m_billType);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("��ѯ������״̬�������ݿ��쳣��" + e.getMessage());
		}

		switch (status) {
			case IPfRetCheckInfo.COMMIT:
			case IPfRetBackCheckInfo.NOSTATE:
				try {
					return queryDMO.queryWorkflowOnSave(paraVO, status);
				} catch (DbException e) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("����ʱ��ѯ�������������ݿ��쳣��" + e.getMessage());
				}
			case IPfRetCheckInfo.GOINGON:
				//������������,ȴ�ٴ�����,���ؿ�,����Ӱ������
				return null;
			case IPfRetCheckInfo.PASSING:
				//�������Ѿ�������ͨ����,ȴ�ٴ�����,���ؿ�,����Ӱ������
				return null;
			default:
				return null;
		}
	}

	public RetBackWfVo backwardCheckFlow(PfParameterVO paraVo) throws BusinessException {
		//UNAPPROVEʱ������Ĵ���
		RetBackWfVo retBackVO = new RetBackWfVo();
		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			//��ѯ���� ����ɵ�����������
			if (paraVo.m_workFlow == null) {
				paraVo.m_workFlow = queryDMO.queryFinishedApproveItem(paraVo);
			}
			if (paraVo.m_workFlow == null) {
				//���û������ɵ������������ֱ�Ӱѵ�������Ϊ����̬
				retBackVO.setIsFinish(UFBoolean.TRUE);
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				return retBackVO;
			}

			//������ʵ���жϵ�ǰ���ݵ����Ƿ���������
			boolean isFinished = queryDMO.isApproveflowFinished(paraVo.m_billId, paraVo.m_billType);
			if (isFinished) {
				//��ǰ������������ͨ���������
				retBackVO.setIsFinish(UFBoolean.TRUE);
			}

			//ִ����������
			WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
			//����Ҫ�� ������,�޸�ʱ��
			currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
			// currentTask.setNote(paraVo.m_workFlow.getCheckNote());
			currentTask.setOperator(paraVo.m_operator);
			currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			currentTask.setTaskType(WFTask.TYPE_WITHDRAW);

			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);

			//��������ִ����ɺ�Ĵ���
			boolean isGoing = queryDMO.isExistFinishedApproveItem(paraVo.m_billId, paraVo.m_billType);
			if (isGoing) {
				//��������״̬Ϊ����������
				retBackVO.setBackState(IPfRetCheckInfo.GOINGON);
				if (isFinished) {
					//�������ڶ���1��ʱ�����һ��������Ҫ֪ͨ�Ƶ���
					WorknoteManager noteMgr = new WorknoteManager();
					noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000136}"/* @res "���󵥾ݡ�" */, paraVo);
				}
			} else {
				//��������״̬Ϊ����̬
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				//queryDMO.updateAllActivityInstanceStatusToInefficient(currentTask.getBillID(), currentTask.getBillType());
				WorknoteManager noteMgr = new WorknoteManager();
				noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000137}"/* @res "������,���޸ĵ��ݡ�" */, paraVo);
			}
			if (!paraVo.m_makeBillOperator.equals(paraVo.m_workFlow.getSenderMan())) {
				retBackVO.setPreCheckMan(paraVo.m_workFlow.getSenderMan());
			}
			return retBackVO;

		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("������ʱ�������ݿ��쳣��" + ex.getMessage());
		} finally {
			//XXX::�����Ƴ�,�����ڴ�й©
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
		}

	}

	public RetBackWfVo backCheckFlow(PfParameterVO paraVo) throws BusinessException {
		//UNAPPROVEʱ�Ĵ���֧���������һ������
		RetBackWfVo retBackVO = new RetBackWfVo();
		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			//��ѯ���� ����ɵ�����������
			paraVo.m_workFlow = queryDMO.queryFinishedApproveItem(paraVo);
			if (paraVo.m_workFlow == null) {
				//���û������ɵ������������ֱ�Ӱѵ�������Ϊ����̬
				retBackVO.setIsFinish(UFBoolean.TRUE);
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				return retBackVO;
			}

			//����ʱ���ӵ���VO��ȡ�����Ϣ
			CurrencyInfo cinfo = new CurrencyInfo();
			PfUtilBaseTools.fetchMoneyInfo(paraVo.m_preValueVo, cinfo, paraVo.m_billType);
			paraVo.m_workFlow.putMoney(paraVo.m_billId, cinfo);

			//��ѯ�����������е�����ģʽ
			//String processDefPK = paraVo.m_workFlow.getTaskInfo().getTask().getWfProcessDefPK();
			//BasicWorkflowProcess bwp = (BasicWorkflowProcess) queryDMO.findParsedWfProcessByDefPK(procInstPK);
			String procInstPK = paraVo.m_workFlow.getTaskInfo().getTask().getWfProcessInstancePK();
			BasicWorkflowProcess bwp = (BasicWorkflowProcess) queryDMO
					.findParsedMainWfProcessByInstancePK(procInstPK);
			boolean isStepbyStep = bwp.getBackModal() == BackModal.STEP;

			//������ʵ���жϵ�ǰ���ݵ����Ƿ���������
			boolean isFinished = queryDMO.isApproveflowFinished(paraVo.m_billId, paraVo.m_billType);
			if (isFinished) {
				//��ǰ������������ͨ���������
				retBackVO.setIsFinish(UFBoolean.TRUE);
			} else if (!isStepbyStep) {
				//���������������е�һ������
				throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow",
						"UPPpfworkflow-000135")/* @res [������]һ������ʱ��ֻ���������̽�����ſ�����*/);
			}

			//ִ����������
			WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
			//����Ҫ�� ������,�޸�ʱ��
			currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
			// currentTask.setNote(paraVo.m_workFlow.getCheckNote());
			currentTask.setOperator(paraVo.m_operator);
			currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			currentTask.setTaskType(WFTask.TYPE_WITHDRAW);
			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);

			//��������ִ����ɺ�Ĵ���
			if (isStepbyStep) {
				boolean isGoing = queryDMO.isExistFinishedApproveItem(paraVo.m_billId, paraVo.m_billType);
				if (isGoing) {
					//��������״̬Ϊ����������
					retBackVO.setBackState(IPfRetCheckInfo.GOINGON);
					retBackVO.setPreCheckMan(paraVo.m_workFlow.getSenderMan());
					if (isFinished) {
						//�������ڶ���1��ʱ�����һ��������Ҫ֪ͨ�Ƶ���
						WorknoteManager noteMgr = new WorknoteManager();
						noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000136}"/* @res "���󵥾ݡ�" */, paraVo);
					}
				} else {
					//��������״̬Ϊ����̬
					retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
					//queryDMO.updateAllActivityInstanceStatusToInefficient(currentTask.getBillID(), currentTask.getBillType());
					WorknoteManager noteMgr = new WorknoteManager();
					noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000137}"/* @res "������,���޸ĵ��ݡ�" */, paraVo);
				}
			} else {
				//һ�����ף�������Ϊ����̬�������Ƶ��˷���ҵ����Ϣ
				retBackVO.setBackState(IPfRetBackCheckInfo.NOSTATE);
				WorknoteManager noteMgr = new WorknoteManager();
				noteMgr.sendMessageToBillMaker("{UPPpfworkflow-000137}"/* @res "������,���޸ĵ��ݡ�" */, paraVo);
			}
			return retBackVO;
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("����������ʱ�������ݿ��쳣��" + e.getMessage());
		} finally {
			//XXX::�����Ƴ�,�����ڴ�й©
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
		}
	}

	public void cancelCheckFlow(String billType, String billId, String checkMan)
			throws BusinessException {
		//UNAPPROVEʱһ�����׵Ĵ���
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_billType = billType;
		paraVo.m_billId = billId;
		paraVo.m_operator = checkMan;
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			//��ѯ���� ����ɵ�����������
			PfUtilWorkFlowVO workFlow = queryDMO.queryFinishedApproveItem(paraVo);
			if (workFlow == null)
				return;

			//WARN::�������̽����󣬲�����һ������
			boolean isFinished = queryDMO.isApproveflowFinished(paraVo.m_billId, paraVo.m_billType);
			if (!isFinished)
				throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow",
						"UPPpfworkflow-000135")/* @res [������]һ������ʱ��ֻ���������̽�����ſ�����*/);

			//ִ����������
			WFTask currentTask = workFlow.getTaskInfo().getTask();
			//����Ҫ�� ������,�޸�ʱ��
			currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
			currentTask.setOperator(checkMan);
			currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
			currentTask.setTaskType(WFTask.TYPE_WITHDRAW);

			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("һ������ʱ�������ݿ��쳣��" + ex.getMessage());
		}

	}

	public void deleteCheckFlow(String billType, String billId, String checkMan,
			boolean isStepUnApprove) throws BusinessException {
		int status;
		try {
			//״̬��ѯ
			WorkflowQuery queryDMO = new WorkflowQuery();
			status = queryDMO.queryApproveflowStatus(billId, billType);
			//lj@2006-11-3 ����ͨ����Ҳ�ɰ�����ʵ��ɾ��
			switch (status) {
				case IPfRetBackCheckInfo.NOSTATE:
				case IPfRetCheckInfo.COMMIT:
				case IPfRetCheckInfo.NOPASS:
				case IPfRetCheckInfo.PASSING:
					queryDMO.deleteWorkflow(billId, billType, false, IApproveflowConst.WORKFLOW_TYPE_APPROVE);
					break;
				case IPfRetCheckInfo.GOINGON:
					//if (isStepUnApprove) {
					//	throw new PFBusinessException(NCLangResOnserver.getInstance().getStrByID("pfworkflow", "UPPpfworkflow-000271")/* @res "������ʱ,��������������ɾ������" */);
					//} else {
					//ɾ��������Ϣ
					queryDMO.deleteWorkflow(billId, billType, false, IApproveflowConst.WORKFLOW_TYPE_APPROVE);
					//�����������˺��Ƶ��˷�֪ͨ��Ϣ
					WorknoteManager noteMgr = new WorknoteManager();
					noteMgr.sendAllPersonMessage("{UPPpfworkflow-000272}"/* @res "��ɾ��" */, null, billId,
							billType, checkMan);
					//}
					break;
			}
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("ɾ���������������ݿ��쳣��" + ex.getMessage());
		}

	}

	public List forwardCheckFlow(PfParameterVO paraVo) throws BusinessException {
		int status;
		ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, paraVo);
		WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
		try {
			if (!currentTask.isBackToFirstActivity()) {
				//1-����
				//Ϊ���������� ��̻�Ĳ�����ָ����Ϣ 
				fillAssignableInfo(paraVo, currentTask);
				//Ϊ���������� ���ת�ƵĿ��ֹ�ѡ����Ϣ 
				fillTransitionSelectableInfo(paraVo, currentTask);

				//����Ҫ�� ���ҵ������,������,�������,�޸�ʱ��
				//�����������CurrencyInfo��,��AbstractComplier���
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
				//2-����
				//����Ҫ�� ������,�޸�ʱ��
				currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
				//currentTask.setNote(paraVo.m_workFlow.getCheckNote());
				currentTask.setOperator(paraVo.m_operator);
				currentTask.setNote(paraVo.m_workFlow.getCheckNote());
				currentTask.setPk_Checkflow(paraVo.m_workFlow.getWorkFlowOid());
				currentTask.setStatus(WfTaskStatus.Finished.getIntValue());
				currentTask.setTaskType(WFTask.TYPE_BACKWARD);

				//FIXME::���Ӳ��ؽ��?! lj@2005-5-16
				currentTask.setApproveResult("R");
			}

			//�����淢������
			WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
			
			PfParameterVO tmpparaVo = ActionEnvironment.getInstance().getParaVo(paraVo.m_billId + "@auto");
			if(tmpparaVo==null)
				tmpparaVo = ActionEnvironment.getInstance().getParaVo(paraVo.m_billId);
			
			paraVo = tmpparaVo;
			//���ݹ�����״̬��ѯ,�����д���
			WorkflowQuery queryDMO = new WorkflowQuery();
			boolean isWorkflow = IApproveflowConst.WORKFLOW_TYPE_WORKFLOW == currentTask
					.getWorkflowType();
			status = isWorkflow ? queryDMO.queryWorkflowStatus(paraVo.m_billId, paraVo.m_billType)
					: queryDMO.queryApproveflowStatus(paraVo.m_billId, paraVo.m_billType);
			if (status == IPfRetCheckInfo.PASSING || status == IPfRetCheckInfo.NOPASS) {
				//���̽���������δ��ɵĻʵ����Ϊ��Ч
				queryDMO.updateUnfinishedActivityInstanceStatusToInefficient(currentTask.getBillID(),
						currentTask.getBillType());
			} else if (status == IPfRetBackCheckInfo.NOSTATE) {
				//���ݻص�����̬�����лʵ����Ϊ��Ч
				queryDMO.updateAllActivityInstanceStatusToInefficient(currentTask.getBillID(), currentTask
						.getBillType());
			}

			WorknoteManager noteMgr = new WorknoteManager();
			if (status == IPfRetCheckInfo.PASSING) {
				//�������ͨ��,����Ƶ��˷��ͷ�ҵ����Ϣ
				Logger.debug("***����ʵ������֮����ͨ��,����Ƶ��˷��ͷ�ҵ����Ϣ***");
			
				noteMgr.sendMessageToBillMaker(isWorkflow ? "{UPPpfworkflow-000701}"/* "�������������" */
				: "{UPPpfworkflow-000273}"/* "����ͨ��" */, paraVo);
				
				//�����ʼ�������Ա������ҵ��Ա  yangtao
				
				//�����ʼ�
				EmaillHVO newvo=MaillTool.getmaillinfo("4");
				String emailladd=MaillTool.getEmaillAdd(MaillTool.getClerk(paraVo));
				if(emailladd!=null){
				MaillTool.sendApprovalEmail(newvo,paraVo,new String []{emailladd},null,null);
				}
			

			} else if (status == IPfRetCheckInfo.NOPASS) {
				//���������ͨ��,������뱾�����̵��˷��ͷ�ҵ����Ϣ
				Logger.debug("***����ʵ������֮������ͨ��,������뱾�����̵��˷��ͷ�ҵ����Ϣ***");
				noteMgr.sendMessageAfterNoPass("{UPPpfworkflow-000274}"/* "������ͨ��" */, paraVo);
				
			}
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("������ת�������ݿ��쳣��" + ex.getMessage());
		} finally {
			//XXX::�����Ƴ�,�����ڴ�й©
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
				//����������
				return startApproveflow(paraVo, hmPfExParams);
			else
				//����������
				return startWorkflow(paraVo, hmPfExParams);

		} finally {
			// XXX::�����Ƴ�,�����ڴ�й©
			ActionEnvironment.getInstance().putParaVo(paraVo.m_billId, null);
			ActionEnvironment.getInstance().putMethodReturn(paraVo.m_billId, null);
		}
	}

	/**
	 * ����������
	 * @param paraVo
	 * @param hmPfExParams
	 * @return
	 * @throws BusinessException
	 */
	private boolean startWorkflow(PfParameterVO paraVo, HashMap hmPfExParams)
			throws BusinessException {
		Logger.debug("��������Ϊ" + paraVo.m_actionName + "������������");

		//����ʱ��ɾ����������Ϣ������״̬Ϊ"ͨ����������"�ĳ���
		deleteWhenStartExceptPassOrGoing(paraVo, IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);

		if (paraVo.m_workFlow == null || paraVo.m_workFlow.getTaskInfo().getTask() == null) {
			//�����չ�����к������̶���PK����ֱ�Ӹ�ֵ
			Object paramDefPK = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_FLOWPK);
			paraVo.m_flowDefPK = paramDefPK == null ? null : String.valueOf(paramDefPK);

			Object noteChecked = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_NOTE_CHECKED);
			//leijun+2009-7 ���ǰ̨PfUtilClient.runAction�Ѿ���鲻�����������Ҳû�б�Ҫ�ٴμ��
			if (noteChecked == null)
				paraVo.m_workFlow = checkWorkflowWhenStart(paraVo);
			if (paraVo.m_workFlow == null)
				//���ݵĹ�����״̬���ڽ����У������
				return false;
		}

		/*
		 * ����������ʱ��ɾ���ɵĹ�������Ϣ�����������������ʷ��
		 *   1)����̬�����ػ���ת���Ƶ����ڣ��������ύ�������µĹ���������
		 *   2)�ύ̬����ͨ��̬�������ύ�������µĹ���������
		 * XXX:leijun@2008-11 
		 */
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			queryDMO.deleteWorkflow(paraVo.m_billId, paraVo.m_billType, true,
					IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("�����������������ݿ��쳣��" + ex.getMessage());
		}

		//�ύʱ���ӵ���VO��ȡ�����Ϣ
		CurrencyInfo cinfo = new CurrencyInfo();
		PfUtilBaseTools.fetchMoneyInfo(paraVo.m_preValueVo, cinfo, paraVo.m_billType);
		paraVo.m_workFlow.putMoney(paraVo.m_billId, cinfo);

		WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
		// �������ݺź����ɵ����.
		currentTask.setBillNO(paraVo.m_billNo);
		currentTask.setBillID(paraVo.m_billId);

		//Ϊ���������� ��̻�Ĳ�����ָ����Ϣ 
		fillAssignableInfo(paraVo, currentTask);
		//Ϊ���������� ���ת�ƵĿ��ֹ�ѡ����Ϣ 
		fillTransitionSelectableInfo(paraVo, currentTask);

		// ����Ҫ�� ���ҵ������,������,�޸�ʱ��
		currentTask.setOutObject(paraVo.m_preValueVo);
		//currentTask.setOperator(paraVo.m_operator);
		currentTask.setOperator(paraVo.m_makeBillOperator); //�Ƶ�����
		currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
		currentTask.setStatus(WfTaskStatus.Finished.getIntValue()); //�Ƶ����������

		//ִ������
		ExecuteResult result = WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
		if (result != null && result.isWorkflowNotExist()) {
			//������������
			return false;
		} else if (result != null && result.isApprovePass()) {
			//�������������漴������ͨ����
			//throw new ApproveAfterCommitException(IPfRetCheckInfo.PASSING, NCLangResOnserver
			//		.getInstance().getStrByID("pfworkflow", "UPPpfworkflow-000273")/* ����ͨ�� */);
			return true;
		} else
			return true;
	}

	/**
	 * ����ʱ��ɾ����������Ϣ������״̬Ϊ"ͨ����������"�ĳ���
	 * <li>ֻ������̬���ύ̬��δͨ��̬����ɾ��
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
			//��ѯ���ݵ�����������״̬������̬���ύ̬�������У�ͨ����δͨ��
			status = iWfType == IApproveflowConst.WORKFLOW_TYPE_WORKFLOW ? queryDMO.queryWorkflowStatus(
					paraVo.m_billId, paraVo.m_billType) : queryDMO.queryApproveflowStatus(paraVo.m_billId,
					paraVo.m_billType);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("��ѯ����״̬�������ݿ��쳣��" + e.getMessage());
		}

		/*
		 * ����������ʱ��ɾ���ɵ���������Ϣ��������������ʷ��
		 * 1)����̬�����ػ���ת���Ƶ����ڣ��������ύ�������µ�����������
		 * 2)�ύ̬����ͨ��̬�������ύ�������µ�����������
		 */
		switch (status) {
			case IPfRetCheckInfo.COMMIT:
			case IPfRetBackCheckInfo.NOSTATE:
			case IPfRetCheckInfo.NOPASS:
				try {
					queryDMO.deleteWorkflow(paraVo.m_billId, paraVo.m_billType, true, iWfType);
				} catch (DbException e) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("ɾ�����̳������ݿ��쳣��" + e.getMessage());
				}
				break;
			default:
				return;
		}
	}

	/**
	 * ����������
	 * @param paraVo
	 * @param hmPfExParams
	 * @return
	 * @throws BusinessException
	 */
	private boolean startApproveflow(PfParameterVO paraVo, HashMap hmPfExParams)
			throws BusinessException {
		Logger.debug("��������Ϊ" + paraVo.m_actionName + "������������");

		//����ʱ��ɾ����������Ϣ������״̬Ϊ"ͨ����������"�ĳ���
		deleteWhenStartExceptPassOrGoing(paraVo, IApproveflowConst.WORKFLOW_TYPE_APPROVE);

		if (paraVo.m_workFlow == null || paraVo.m_workFlow.getTaskInfo().getTask() == null) {
			//�����չ�����к������̶���PK����ֱ�Ӹ�ֵ
			Object paramDefPK = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_FLOWPK);
			paraVo.m_flowDefPK = paramDefPK == null ? null : String.valueOf(paramDefPK);

			Object noteChecked = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_NOTE_CHECKED);
			//leijun+2009-7 ���ǰ̨PfUtilClient.runAction�Ѿ���鲻�����������Ҳû�б�Ҫ�ٴμ��
			if (noteChecked == null)
				paraVo.m_workFlow = checkApproveflowWhenSave(paraVo);
			if (paraVo.m_workFlow == null)
				//����̬���޿����������̶��壻�����С�����ͨ��
				return false;
		}

		/*
		 * ����������ʱ��ɾ���ɵ���������Ϣ��������������ʷ��
		 *   1)����̬�����ػ���ת���Ƶ����ڣ��������ύ�������µ�����������
		 *   2)�ύ̬����ͨ��̬�������ύ�������µ�����������
		 * XXX:leijun@2008-11 
		 */
		try {
			WorkflowQuery queryDMO = new WorkflowQuery();
			queryDMO.deleteWorkflow(paraVo.m_billId, paraVo.m_billType, true,
					IApproveflowConst.WORKFLOW_TYPE_APPROVE);
		} catch (DbException ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException("�����������������ݿ��쳣��" + ex.getMessage());
		}

		//�ύʱ���ӵ���VO��ȡ�����Ϣ
		CurrencyInfo cinfo = new CurrencyInfo();
		PfUtilBaseTools.fetchMoneyInfo(paraVo.m_preValueVo, cinfo, paraVo.m_billType);
		paraVo.m_workFlow.putMoney(paraVo.m_billId, cinfo);

		WFTask currentTask = paraVo.m_workFlow.getTaskInfo().getTask();
		// �������ݺź����ɵ����.
		currentTask.setBillNO(paraVo.m_billNo);
		currentTask.setBillID(paraVo.m_billId);

		//Ϊ���������� ��̻�Ĳ�����ָ����Ϣ 
		fillAssignableInfo(paraVo, currentTask);

		// ����Ҫ�� ���ҵ������,������,�޸�ʱ��
		currentTask.setOutObject(paraVo.m_preValueVo);
		currentTask.setOperator(paraVo.m_operator);
		currentTask.setModifyTime(new UFDateTime(System.currentTimeMillis()));
		currentTask.setStatus(WfTaskStatus.Finished.getIntValue()); //�Ƶ����������

		//ִ������
		ExecuteResult result = WfTaskManager.getInstance().acceptTaskFromBusi(currentTask);
		if (result != null && result.isWorkflowNotExist()) {
			//������������
			return false;
		} else if (result != null && result.isApprovePass()) {
			//�ύ������ͨ��
			throw new ApproveAfterCommitException(IPfRetCheckInfo.PASSING, NCLangResOnserver
					.getInstance().getStrByID("pfworkflow", "UPPpfworkflow-000273")/* ����ͨ�� */);
		} else
			return true;
	}

	/**
	 * �Ѻ�̻�Ĳ�����ָ����Ϣ��ֵ���������
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
	 * �Ѻ��ת�ƵĿ��ֹ�ѡ����Ϣ��ֵ���������
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
					throw new PFBusinessException("�����˴���Ĳ���ֵiWorkflowtype");
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage());
		}
	}

	/**
	 * ��ֹ������ʵ��
	 * @param billid
	 * @param pkBilltype
	 * @param billNo
	 * @throws Exception
	 */
	private void stopWorkflow(String billid, String pkBilltype, String billNo) throws Exception {
		// 1.�ж��õ����Ƿ��ڹ����������У�������ǣ����׳��쳣����
		WorkflowQuery queryDMO = new WorkflowQuery();
		int status = queryDMO.queryWorkflowStatus(billid, pkBilltype);
		if (status == IPfRetCheckInfo.PASSING)
			throw new PFBusinessException("���������������ѽ������޷���ֹ��");

		// 2.�޸ĵ������ݿ�״̬Ϊ����̬
		boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(pkBilltype);
		AbstractBusiStateCallback absc = hasMeta ? new PFBusiStateOfMeta() : new PFBusiState();

		//��ѯ������VOʵ��
		IPFConfig pfcfg = (IPFConfig) NCLocator.getInstance().lookup(IPFConfig.class.getName());
		AggregatedValueObject billVO = pfcfg.queryBillDataVO(pkBilltype, billid);
		//���칤��������VO
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_workFlow = new PfUtilWorkFlowVO();
		paraVo.m_workFlow.setCheckNote("������ֹ");
		paraVo.m_billId = billid;
		paraVo.m_billType = pkBilltype;
		paraVo.m_preValueVo = billVO;
		absc.execUnApproveState(paraVo, null, IPfRetBackCheckInfo.NOSTATE);

		// 3.ɾ���õ�����ص�������Ϣ
		queryDMO.deleteWorkflow(billid, pkBilltype, false, IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);

		// 4.���Ƶ��˷�����Ϣ���������Ѵ����˷�����Ϣ
		String strMakerId = queryDMO.queryBillmakerOfInstance(billid, pkBilltype,
				IApproveflowConst.WORKFLOW_TYPE_WORKFLOW);
		if (!StringUtil.isEmptyWithTrim(strMakerId))
			new WorknoteManager().sendMessageToBillMaker("{UPPpfworkflow-000453}"/* "�����������ѱ���ֹ��" */,
					InvocationInfoProxy.getInstance().getUserCode(), strMakerId, billNo, pkBilltype, billid);

		// 5.ҵ��ع�
		Object objBizRule = PfUtilTools.getBizRuleImpl(pkBilltype);
		if (objBizRule instanceof IPfCompensateOnTerminated) {
			((IPfCompensateOnTerminated) objBizRule).rollbackOnWorkflowTerminated(billid, pkBilltype);
		}
	}

	/**
	 * ��ֹ������ʵ��
	 * @param billid
	 * @param pkBilltype
	 * @param billNo
	 * @throws Exception
	 */
	private void stopApproveflow(String billid, String pkBilltype, String billNo) throws Exception {
		// 1.�ж��õ����Ƿ������������У�������ǣ����׳��쳣����
		WorkflowQuery queryDMO = new WorkflowQuery();
		int status = queryDMO.queryApproveflowStatus(billid, pkBilltype);
		if (status == IPfRetCheckInfo.PASSING || status == IPfRetCheckInfo.NOPASS)
			throw new PFBusinessException("���������������ѽ������޷���ֹ��");

		// 2.�޸ĵ������ݿ�״̬Ϊ����̬
		boolean hasMeta = PfMetadataTools.isBilltypeRelatedMeta(pkBilltype);
		AbstractBusiStateCallback absc = hasMeta ? new PFBusiStateOfMeta() : new PFBusiState();

		//��ѯ������VOʵ��
		IPFConfig pfcfg = (IPFConfig) NCLocator.getInstance().lookup(IPFConfig.class.getName());
		AggregatedValueObject billVO = pfcfg.queryBillDataVO(pkBilltype, billid);
		//���칤��������VO
		PfParameterVO paraVo = new PfParameterVO();
		paraVo.m_workFlow = new PfUtilWorkFlowVO();
		paraVo.m_workFlow.setCheckNote("������ֹ");
		paraVo.m_billId = billid;
		paraVo.m_billType = pkBilltype;
		paraVo.m_preValueVo = billVO;
		absc.execUnApproveState(paraVo, null, IPfRetBackCheckInfo.NOSTATE);

		// 3.ɾ���õ�����ص�������Ϣ
		queryDMO.deleteWorkflow(billid, pkBilltype, false, IApproveflowConst.WORKFLOW_TYPE_APPROVE);

		// 4.���Ƶ��˷�����Ϣ���������������˷�����Ϣ
		String strMakerId = queryDMO.queryBillmakerOfInstance(billid, pkBilltype,
				IApproveflowConst.WORKFLOW_TYPE_APPROVE);
		if (!StringUtil.isEmptyWithTrim(strMakerId))
			new WorknoteManager().sendMessageToBillMaker("{UPPpfworkflow-000453}"/* "�����������ѱ���ֹ��" */,
					InvocationInfoProxy.getInstance().getUserCode(), strMakerId, billNo, pkBilltype, billid);

	}

	public PfUtilWorkFlowVO checkWorkitemOnSave(String actionName, String billType,
			String currentDate, AggregatedValueObject billVO, HashMap hmPfExParams)
			throws BusinessException {
		IPfBusinessLock pfLock = new PfBusinessLock();
		String strBillId = null;
		try {
			//���ݼ�����һ����У��
			pfLock.lock(new VOLockData(billVO, billType), new VOConsistenceCheck(billVO, billType));

			//�ж��Ƿ���Ҫ���¼���VO leijun+2008-12
			Object paramReloadVO = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_RELOAD_VO);
			if (paramReloadVO != null) {
				//				String billId = billVO.getParentVO().getPrimaryKey();
				//ע����VO�����е�billid��һ���ǵ��ݵ������ֶ�
				String billId = "";
				String billIdField = getBillIDField(billVO, billType);
				if (!StringUtil.isEmptyWithTrim(billIdField)) {
					billId = (String) billVO.getParentVO().getAttributeValue(billIdField);
				} else {
					billId = billVO.getParentVO().getPrimaryKey();
				}
				billVO = new PFConfigImpl().queryBillDataVO(billType, billId);
				if (billVO == null)
					throw new PFBusinessException("���󣺸��ݵ�������" + billType + "�͵���" + billId + "��ȡ�������ݾۺ�VO");
			}

			Hashtable<String, PfParameterVO> hashBilltypeToParavo = new Hashtable<String, PfParameterVO>();
			PfUtilBaseTools.getVariableValue(billType, actionName, currentDate, billVO, null, null, null,
					null, hashBilltypeToParavo);
			PfParameterVO paraVO = hashBilltypeToParavo.get(billType);
			strBillId = paraVO.m_billId;
			ActionEnvironment.getInstance().putParaVo(strBillId, paraVO);

			//Ϊ���̶��帳ֵ
			Object paramDefPK = hmPfExParams == null ? null : hmPfExParams
					.get(PfUtilBaseTools.PARAM_FLOWPK);
			paraVO.m_flowDefPK = paramDefPK == null ? null : String.valueOf(paramDefPK);

			boolean isWorkflow = IPFActionName.START.equalsIgnoreCase(paraVO.m_actionName);
			return isWorkflow ? checkWorkflowWhenStart(paraVO) : checkApproveflowWhenSave(paraVO);

		} finally {
			//XXX::�����Ƴ�,�����ڴ�й©
			ActionEnvironment.getInstance().putParaVo(strBillId, null);
			//�ͷ���
			if (pfLock != null)
				pfLock.unLock();
		}
	}
	
	public Object processSingleBillFlow_RequiresNew(String actionName, String billOrTranstype, String currentDate, PfUtilWorkFlowVO workflowVo, AggregatedValueObject billvo, Object userObj, HashMap eParam)
			throws BusinessException {
		return NCLocator.getInstance().lookup(IPFBusiAction.class).processAction(actionName, billOrTranstype, currentDate, workflowVo, billvo, userObj, eParam);
	}
}