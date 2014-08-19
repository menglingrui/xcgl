package nc.bs.pub.pf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import nc.bd.accperiod.AccountCalendar;
import nc.bd.accperiod.InvalidAccperiodExcetion;
import nc.bs.framework.cluster.itf.BytesClusterMessage;
import nc.bs.framework.cluster.itf.ClusterMessageHeader;
import nc.bs.framework.cluster.itf.ClusterSender;
import nc.bs.framework.cluster.itf.ClusterServiceException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.framework.server.ServerConfiguration;
import nc.bs.framework.server.util.NewObjectService;
import nc.bs.logging.Logger;
import nc.bs.pf.change.VOConversion;
import nc.bs.pf.pub.PfDataCache;
import nc.bs.pub.mail.MailTool;
import nc.bs.pub.mobile.WirelessManager;
import nc.bs.uap.sf.excp.MailException;
import nc.itf.uap.pf.IPFConfig;
import nc.itf.uap.pf.IPFMetaModel;
import nc.itf.uap.pf.IPFWorkflowQry;
import nc.itf.uap.pf.IWorkflowMachine;
import nc.itf.uap.pf.IplatFormEntry;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.itf.uap.sf.IConfigFileService;
import nc.vo.bd.psndoc.PsnbasdocVO;
import nc.vo.framework.rsa.Encode;
import nc.vo.jcom.io.IOUtil;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pf.change.IchangeVO;
import nc.vo.pf.change.PfUtilBaseTools;
import nc.vo.pf.pub.IDapType;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.msg.DefaultSMTP;
import nc.vo.pub.msg.MessageVO;
import nc.vo.pub.msg.SysMessageParam;
import nc.vo.pub.pf.AssignableInfo;
import nc.vo.pub.pf.CurrencyInfo;
import nc.vo.pub.pf.IPFConfigInfo;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.pub.pf.workflow.IPFActionName;
import nc.vo.pub.pfflow.PfDataTypes;
import nc.vo.pub.pfflow.PfOperatorTypes;
import nc.vo.pub.workflownote.WorkflownoteVO;
import nc.vo.uap.pf.OrganizeUnit;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uap.rbac.RoleVO;
import nc.vo.wfengine.core.util.DateUtilities;
import nc.vo.wfengine.pub.WFTask;
import sun.misc.BASE64Encoder;

/**
 * ƽ̨��̨������
 * 
 * @author ���ھ� 2002-4-12 12:29:25
 * @modifier �׾� 2004-03-09 ���ӽ������ݶ���Լ�����������Ϣ��ʾ
 * @modifier �׾� 2005-8-9 ��־APIͳһʹ��UAP��־ϵͳ
 * @modifier leijun 2006-4-29 BOOLEAN����ҲҪ֧����ֵ�ж����������ֵ����ֱ�Ӽ�����ֵ��
 * @modifier leijun 2008-12 ����String���͵ĵ��ݺ���֧�ָ���Ƚ��������not like,in,not in
 */
public class PfUtilTools extends PfUtilBaseTools {

	public static final String NEEDDISPATCH = "NEEDDISPATCH";

	/**
	 * ʵ������ĳ�����������������
	 * <li>ǰ�᣺�ӵ������Ϳɻ�ȡ�����ڵ�ģ����
	 * 
	 * @param pkBilltype
	 *            ��������PK
	 * @param checkClsName
	 *            ����
	 * @return ��ʵ��
	 * @throws BusinessException
	 */
	public static Object instantizeObject(String pkBilltype, String checkClsName)
			throws BusinessException {
		// WARN::�ýӿڵ�ʵ��ʹ���˺�̨����
		IPFMetaModel pfmeta = (IPFMetaModel) NCLocator.getInstance().lookup(
				IPFMetaModel.class.getName());
		String strModule = pfmeta.queryModuleOfBilltype(pkBilltype);
		if (StringUtil.isEmptyWithTrim(strModule))
			throw new PFBusinessException("�õ�������" + pkBilltype + "û�ж���������ģ��");

		Object ret = null;
		if (RuntimeEnv.getInstance().isRunningInServer())
			ret = NewObjectService.newInstance(strModule, checkClsName);
		else
			try {
				ret = Class.forName(checkClsName).newInstance();
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		return ret;
	}

	/**
	 * ����Ա�����л�ȡ����Ա���ֻ���Ϣ�����ڶ��ŷ���
	 * 
	 * @param userIds
	 * @throws BusinessException
	 */
	public static String[] fetchPhonesByUserId(String[] userIds)
			throws BusinessException {
		if (userIds == null || userIds.length == 0)
			return null;
		ArrayList<String> alPhones = new ArrayList<String>();
		try {
			// AccpsndocDMO accpsndocdmo = new AccpsndocDMO();
			IUserManageQuery umq = (IUserManageQuery) NCLocator.getInstance()
					.lookup(IUserManageQuery.class.getName());
			for (int i = 0; i < userIds.length; i++) {
				PsnbasdocVO psnVO = umq.getPsnbasdocByUserid(userIds[i]);
				if (psnVO != null) {
					String phone = psnVO.getMobile();
					if (phone != null && phone.length() > 0)
						alPhones.add(phone);
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage());
		}
		return (String[]) alPhones.toArray(new String[] {});
	}

	/**
	 * ��ȡע����bd_billtype.checkclassname�����ʵ��
	 * <li>ֻ�ɺ�̨����
	 * 
	 * @param billType
	 *            �������ͣ��������ͣ�PK
	 * @return
	 * @throws BusinessException
	 */
	public static Object getBizRuleImpl(String billType)
			throws BusinessException {
		BilltypeVO btVO = PfDataCache.getBillTypeInfo(billType);
		if (!StringUtil.isEmptyWithTrim(btVO.getCheckclassname()))
			return findBizImplOfBilltype(billType, btVO.getCheckclassname()
					.trim());

		String strTypeCode = getRealBilltype(billType);
		btVO = PfDataCache.getBillTypeInfo(strTypeCode);
		return findBizImplOfBilltype(billType, btVO.getCheckclassname());
	}

	/**
	 * ��õ���������ע���ҵ��ʵ�����ʵ��
	 * 
	 * @param billType
	 *            ��������PK
	 * @param clzName
	 *            ҵ��������
	 * @return
	 * @throws BusinessException
	 */
	public static Object findBizImplOfBilltype(String billType, String clzName)
			throws BusinessException {
		if (StringUtil.isEmptyWithTrim(clzName))
			throw new PFBusinessException("����ƽ̨����������" + billType + "ע���ҵ��������Ϊ��");
		return instantizeObject(billType, clzName.trim());
	}

	/**
	 * ʵ�����������ݵĺ�̨VO������
	 * 
	 * @param srcBillType
	 *            Դ�������ͣ��������ͣ�PK
	 * @param destBillType
	 *            Ŀ�ĵ������ͣ��������ͣ�PK
	 * @return ��̨�������ʵ��
	 * @throws BusinessException
	 */
	private static Object findBSChangeScriptClass(String srcBillType,
			String destBillType) throws BusinessException {
		Logger.debug("��ѯBS�����࿪ʼ......");

		Object changeImpl = null;
		// XXX:leijun 2009-10 ֻ����������ǰ�����������ͼ�
		String strClassNameNoPackage = "CHG" + srcBillType + "TO"
				+ destBillType;
		String fullyQualifiedClassName = m_strBackChangeClassPack + "."
				+ strClassNameNoPackage;
		try {
			Logger.debug("����ʵ������=" + fullyQualifiedClassName);
			changeImpl = newVoMappingObject(srcBillType, destBillType,
					fullyQualifiedClassName);
			Logger.debug("��ѯBS���������...." + changeImpl);
			return changeImpl;
		} catch (Exception e) {
			if (isTranstype(srcBillType) || isTranstype(destBillType)) {
				// XXX:�����쳣����������ҵ�������֮��Ľ�����ʵ��
				Logger.warn("���棺�޷�ʵ������=" + fullyQualifiedClassName + "������������",
						e);
				strClassNameNoPackage = "CHG" + getRealBilltype(srcBillType)
						+ "TO" + getRealBilltype(destBillType);
				fullyQualifiedClassName = m_strBackChangeClassPack + "."
						+ strClassNameNoPackage;
				try {
					Logger.debug("����ʵ������2=" + fullyQualifiedClassName);
					changeImpl = newVoMappingObject(srcBillType, destBillType,
							fullyQualifiedClassName);
					Logger.debug("��ѯBS������2����...." + changeImpl);
					return changeImpl;
				} catch (Exception ex) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("�޷����" + srcBillType + "��"
							+ destBillType + "�ĺ�̨VO������ʵ�����쳣" + ex.getMessage());
				}
			} else {
				Logger.error(e.getMessage(), e);
				throw new PFBusinessException("�޷����" + srcBillType + "��"
						+ destBillType + "�ĺ�̨VO������ʵ�����쳣" + e.getMessage());
			}
		}
	}

	/**
	 * ���ָ�������������ඨ��
	 * 
	 * @param className
	 * @param intLen
	 * @return
	 * @throws BusinessException
	 */
	public static Class getClassNameClass(String className, int intLen)
			throws BusinessException {
		AggregatedValueObject[] retVos = pfInitVos(className, intLen);
		return retVos.getClass();
	}

	/**
	 * ���ָ���������ඨ��
	 * 
	 * @param className
	 * @return
	 * @throws BusinessException
	 */
	public static Class getClassByName(String className)
			throws BusinessException {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new PFBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * ���ص�ǰʹ������Դ����
	 * 
	 * @return
	 */
	public static String getCurrentDatabase() {
		String userDataSource = InvocationInfoProxy.getInstance()
				.getUserDataSource();
		if (userDataSource == null)
			userDataSource = "design";
		return userDataSource;
	}

	/**
	 * �������ʽ��ֵ <BR>
	 * ע�⣺Ŀǰ ��֧�����ֱ��ʽ��
	 * 
	 * @param macro
	 *            String ����ʽ�����к���
	 * @param type
	 *            String ���ʽ����
	 * @param leftValue
	 *            Object ��ֵ
	 * @param rightValue
	 *            Object ��ֵ
	 * @return String
	 * @author �׾�
	 */
	private static String getExpressValue(String macro, String type,
			Object leftValue, Object rightValue) {
		// Ŀǰ����֧�ֵĺ���ʽ
		// String[] macros = new String[] { "%%L%%", "%%R%%", "%%L-R%%",
		// "%%R-L%%", "%%L+R%%", "%%R+L%%" };

		if (macro.equalsIgnoreCase("L")) {
			return leftValue.toString();
		} else if (macro.equalsIgnoreCase("R")) {
			return rightValue.toString();
		} else if (macro.equalsIgnoreCase("L-R")) {
			if (type.equalsIgnoreCase("INTEGER")) {
				return new Integer(((Integer) leftValue).intValue()
						- ((Integer) rightValue).intValue()).toString();
			} else if (type.equalsIgnoreCase("Double")) {
				double value = ((UFDouble) leftValue).doubleValue()
						- Double.parseDouble((String) rightValue);
				return String.valueOf(value);
			}
		} else if (macro.equalsIgnoreCase("R-L")) {
			if (type.equalsIgnoreCase("INTEGER")) {
				return new Integer(((Integer) rightValue).intValue()
						- ((Integer) leftValue).intValue()).toString();
			} else if (type.equalsIgnoreCase("Double")) {
				double value = ((UFDouble) rightValue).doubleValue()
						- Double.parseDouble((String) leftValue);
				return String.valueOf(value);
			}
		} else if (macro.equalsIgnoreCase("L+R")
				|| macro.equalsIgnoreCase("R+L")) {
			if (type.equalsIgnoreCase("INTEGER")) {
				return new Integer(((Integer) leftValue).intValue()
						+ ((Integer) rightValue).intValue()).toString();
			} else if (type.equalsIgnoreCase("Double")) {
				double value = ((UFDouble) leftValue).doubleValue()
						+ Double.parseDouble((String) rightValue);
				return String.valueOf(value);
			}
		}
		return macro;
	}

	/**
	 * ���ܣ��жϹ�ϵʽ�Ƿ�ɹ��� ����Type;�ַ���"STRING"�����͡�INTEGER����������"DOUBLE"��������"BOOLEAN"
	 * �ַ��������Ϊ:����"=",������"like" ���������Ϊ: ����"=",���ڵ���">=",С�ڵ���" <=",������"!="
	 * ����">",С��" <" �����������Ϊ:����"=",���ڵ���">=",С�ڵ���" <=",������"!=" ����">",С��" <"
	 * ������BOOLEAN�����Ϊ:����"=".
	 * 
	 * @param InObject
	 *            ��ֵ
	 * @param objType
	 *            ��ֵ���ͣ���֧��"BOOLEAN","STRING","INTEGER","DOUBLE"
	 * @param opType
	 *            �ȽϷ�
	 * @param value
	 *            ��ֵ
	 * @return
	 * @throws BusinessException
	 * @modifier leijun 2007-9-1 ��ֵ����֧��"VOID"
	 */
	public static boolean isTrueOrNot(Object InObject, String objType,
			String opType, String value) throws BusinessException {
		Logger.debug("****�Ƚ�����:" + objType + "�Ƚ����������:" + opType + "****");

		if (PfDataTypes.VOID.getTag().equals(objType))
			// XXX:�����ֵ��������ֵΪ"VOID"��������
			return true;
		else if (PfDataTypes.UFBOOLEAN.getTag().equals(objType))
			return compareBoolean((UFBoolean) InObject, opType, value);
		else if (PfDataTypes.STRING.getTag().equals(objType))
			return compareString((String) InObject, opType, value);
		else if (PfDataTypes.INTEGER.getTag().equals(objType))
			return compareInteger((Integer) InObject, opType, value);
		else if (PfDataTypes.UFDOUBLE.getTag().equals(objType))
			return compareDouble((UFDouble) InObject, opType, value);

		return false;
	}

	/**
	 * �ж�Double�͵����
	 * 
	 * @param InObject
	 *            ��ֵ
	 * @param opType
	 *            �Ƚ������
	 * @param value
	 *            ��ֵ
	 * @return
	 */
	private static boolean compareDouble(UFDouble InObject, String opType,
			String value) {
		// �ж�Double�͵����
		if (InObject == null) {
			InObject = new UFDouble(0);
		}
		if (value == null) {
			value = "0";
		}
		// WARN::V5ǰ�İ汾�к������ʽ�����Ϊ"="��V5�޸�Ϊ"=="
		// if (PfOperatorTypes.EQ.toString().equals(opType)) {
		if (PfOperatorTypes.EQ.toString().equals(opType) || "=".equals(opType)) {
			if (InObject.doubleValue() == Double.parseDouble(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.GE.toString().equals(opType)) {
			if (InObject.doubleValue() >= Double.parseDouble(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.LE.toString().equals(opType)) {
			if (InObject.doubleValue() <= Double.parseDouble(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.NE.toString().equals(opType)) {
			if (InObject.doubleValue() != Double.parseDouble(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.LT.toString().equals(opType)) {
			if (InObject.doubleValue() < Double.parseDouble(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.GT.toString().equals(opType)) {
			if (InObject.doubleValue() > Double.parseDouble(value)) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	/**
	 * �ж�Integer�͵����
	 * 
	 * @param InObject
	 *            ��ֵ
	 * @param opType
	 *            �Ƚ������
	 * @param value
	 *            ��ֵ
	 * @return
	 */
	private static boolean compareInteger(Integer InObject, String opType,
			String value) {
		Integer typeInteger;
		// �ж�Integer�͵����
		if (InObject == null) {
			InObject = new Integer(0);
		}
		if (value == null) {
			value = "0";
		}
		typeInteger = (Integer) InObject;
		// WARN::V5ǰ�İ汾�к������ʽ�����Ϊ"="��V5�޸�Ϊ"=="
		// if (PfOperatorTypes.EQ.toString().equals(opType)) {
		if (PfOperatorTypes.EQ.toString().equals(opType) || "=".equals(opType)) {
			if (typeInteger.intValue() == Integer.parseInt(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.GE.toString().equals(opType)) {
			if (typeInteger.intValue() >= Integer.parseInt(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.LE.toString().equals(opType)) {
			if (typeInteger.intValue() <= Integer.parseInt(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.NE.toString().equals(opType)) {
			if (typeInteger.intValue() != Integer.parseInt(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.LT.toString().equals(opType)) {
			if (typeInteger.intValue() < Integer.parseInt(value)) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.GT.toString().equals(opType)) {
			if (typeInteger.intValue() > Integer.parseInt(value)) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	/**
	 * �ж�String�͵����
	 * 
	 * @param strLeftValue
	 *            ��ֵ
	 * @param opType
	 *            �Ƚ������
	 * @param strRightValue
	 *            ��ֵ
	 * @return
	 */
	private static boolean compareString(String strLeftValue, String opType,
			String strRightValue) {
		// WARN::V5ǰ�İ汾�к������ʽ�����Ϊ"="��V5�޸�Ϊ"=="
		if (PfOperatorTypes.EQ.toString().equals(opType) || "=".equals(opType)) {
			if(strRightValue.equals(strLeftValue)){
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.LIKE.toString().equals(opType)) {
			if (strLeftValue!= null && strLeftValue.indexOf(strRightValue) > -1) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.NOTLIKE.toString().equals(opType)) {
			if (strLeftValue== null || strLeftValue.indexOf(strRightValue) == -1) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.IN.toString().equals(opType)) {
			String[] tokens = StringUtil.split(strRightValue, ",");
			HashSet<String> hs = new HashSet<String>();
			for (int i = 0; i < (tokens == null ? 0 : tokens.length); i++) {
				hs.add(tokens[i]);
			}
			return hs.contains(strLeftValue);
		} else if (PfOperatorTypes.IN.toString().equals(opType)) {
			String[] tokens = StringUtil.split(strRightValue, ",");
			HashSet<String> hs = new HashSet<String>();
			for (int i = 0; i < (tokens == null ? 0 : tokens.length); i++) {
				hs.add(tokens[i]);
			}
			return !hs.contains(strLeftValue);
		} else if (PfOperatorTypes.GE.toString().equals(opType)) {
			if (strLeftValue != null && strLeftValue.compareTo(strRightValue) >= 0) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.LE.toString().equals(opType)) {
			if (strLeftValue==null || strLeftValue.compareTo(strRightValue) <= 0) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.NE.toString().equals(opType)) {
			if (strRightValue.equals(strLeftValue)) {
				return false;
			} else {
				return true;
			}
		} else if (PfOperatorTypes.LT.toString().equals(opType)) {
			if (strLeftValue==null || strLeftValue.compareTo(strRightValue) < 0) {
				return true;
			} else {
				return false;
			}
		} else if (PfOperatorTypes.GT.toString().equals(opType)) {
			if (strLeftValue != null && strLeftValue.compareTo(strRightValue) > 0) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	/**
	 * �ж�boolean�͵����
	 * 
	 * @param InObject
	 *            ��ֵ
	 * @param opType
	 *            �Ƚ������
	 * @param value
	 *            ��ֵ
	 * @return
	 */
	private static boolean compareBoolean(UFBoolean InObject, String opType,
			String value) {
		// //lj@2006-4-29 BOOLEAN����ҲҪ֧����ֵ�ж�

		// WARN::V5ǰ�İ汾�к������ʽ�����Ϊ"="��V5�޸�Ϊ"=="
		// if (PfOperatorTypes.EQ.toString().equals(opType)) {
		if (PfOperatorTypes.EQ.toString().equals(opType) || "=".equals(opType)) {
			if (InObject.equals(UFBoolean.valueOf(value)))
				return true;
			else
				return false;
		} else
			// ���û�бȽϷ�����ֱ�Ӽ�����ֵ
			return InObject.booleanValue();
	}

	/**
	 * ִ�к��������ؽ��
	 * 
	 * @param functionNote
	 *            ��������˵��
	 * @param className
	 *            ����
	 * @param method
	 *            ������
	 * @param parameter
	 *            ����
	 * @param paraVo
	 *            �����������Ĳ���
	 * @param methodReturnHas
	 * @return
	 * @throws BusinessException
	 */
	public static Object parseFunction(String functionNote, String className,
			String method, String parameter,
			nc.vo.pub.compiler.PfParameterVO paraVo, Hashtable methodReturnHas)
			throws BusinessException {

		String errString;
		Object checkObject = null;

		Logger.debug("parseFunction������������:" + className + "������:" + method
				+ "����:" + parameter + "��ʼ");
		if (className == null) {
			errString = "parseFunction���������޷����к���";
			throw new PFBusinessException(errString);
		} else if (method == null) {
			errString = "parseFunction�޷��������޷����к���";
			throw new PFBusinessException(errString);
		}
		// ִ����ķ���
		checkObject = runClass(className, method, parameter, paraVo, null,
				methodReturnHas);
		if (functionNote != null) {
			Logger.debug("����#" + functionNote + "#���з���ֵΪ��"
					+ String.valueOf(checkObject));
		} else {
			Logger.debug("�������з���ֵΪ��" + String.valueOf(checkObject));
		}
		Logger.debug("parseFunction������������:" + className + "������:" + method
				+ "����:" + parameter + "****����");
		return checkObject;
	}

	/**
	 * �������Բ���
	 * 
	 * @param dealStr
	 * @param paraObjects
	 * @param paraClasses
	 * @param arrIndex
	 * @param paraVo
	 * @param methodReturnHas
	 * @throws BusinessException
	 */
	private static void parseParmeter(String dealStr, Object[] paraObjects,
			Class[] paraClasses, int arrIndex,
			nc.vo.pub.compiler.PfParameterVO paraVo, Hashtable methodReturnHas)
			throws BusinessException {
		String fieldName = null;
		String datatype = null;
		int retIndex = dealStr.indexOf(":");
		if (retIndex < 0) {
			throw new PFBusinessException("ע���������ȷ");
		} else {
			try {
				// ͨ����������ǰ�ĵ�һ�ַ������������������Է���COM..�������
				fieldName = dealStr.substring(0, retIndex);
				if (fieldName.substring(0, 3).equals("COM")) {
					// FIXME::��֪NC50���к����ã�leijun
					paraObjects[arrIndex] = methodReturnHas.get(fieldName
							.substring(3));
				} else if (fieldName.startsWith("OBJ")) {
					// ��ʾΪ �û��Զ������
					if (fieldName.endsWith("ARY")) {
						paraObjects[arrIndex] = paraVo.m_userObjs;
					} else {
						paraObjects[arrIndex] = paraVo.m_userObj;
					}
				} else {
					// ��ʾΪ ���Բ�������Ҫ�������һЩ���ݣ�
					paraObjects[arrIndex] = paraVo.m_standHeadVo
							.getAttributeValue(fieldName);
				}
				datatype = dealStr.substring(retIndex + 1);
				paraClasses[arrIndex] = parseTypeClass(datatype);
			} catch (Exception ex) {
				Logger.error(ex.getMessage(), ex);
				throw new PFBusinessException("δ�ҵ�ע������ļ�", ex);
			}
		}
	}

	public static boolean parseSyntax(String className, String method,
			String parameter, String funNote, String returnType, String ysf,
			String value, String className2, String method2, String parameter2,
			String funNote2, nc.vo.pub.compiler.PfParameterVO paraVo,
			Hashtable methodReturnHas) throws BusinessException {
		return parseSyntax(className, method, parameter, funNote, returnType,
				ysf, value, className2, method2, parameter2, funNote2, paraVo,
				methodReturnHas, null, null);
	}

	public static boolean parseSyntax(String className, String method,
			String parameter, String funNote, String returnType, String ysf,
			String value, String className2, String method2, String parameter2,
			String funNote2, nc.vo.pub.compiler.PfParameterVO paraVo,
			Hashtable methodReturnHas, StringBuffer errorMessageBuffer)
			throws BusinessException {
		return parseSyntax(className, method, parameter, funNote, returnType,
				ysf, value, className2, method2, parameter2, funNote2, paraVo,
				methodReturnHas, errorMessageBuffer, null);
	}

	/**
	 * ���ܣ����ڽ����﷨�ж�����
	 * 
	 * @author �׾� 2004-03-09 ���ݶ���Լ����������ʾ��Ϣͨ������errorMessageBuffer����
	 * 
	 * @param className
	 *            ��������
	 * @param method
	 *            ����������
	 * @param parameter
	 *            ����
	 * @param funNote
	 *            ����˵��
	 * @param returnType
	 *            ��������ֵ����
	 * @param ysf
	 *            �����
	 * @param value
	 *            ֵ
	 * @param className2
	 *            �������ң�
	 * @param method2
	 *            ���������ң�
	 * @param parameter2
	 *            ����
	 * @param funNote2
	 *            ����˵��
	 * @param paraVo
	 *            �����������Ĳ���
	 * @param methodReturnHas
	 * @param errorMessageBuffer
	 *            ���ص���Ϣ��
	 * @param originalHintBuffer
	 *            ����Ϣ��
	 * @return
	 * @throws BusinessException
	 */
	public static boolean parseSyntax(String className, String method,
			String parameter, String funNote, String returnType, String ysf,
			String value, String className2, String method2, String parameter2,
			String funNote2, nc.vo.pub.compiler.PfParameterVO paraVo,
			Hashtable methodReturnHas, StringBuffer errorMessageBuffer,
			StringBuffer originalHintBuffer) throws BusinessException {

		String strTmp = "";
		Object leftObject = null, rightObject = null;

		strTmp = "parseSyntax�����﷨��ʼ";
		Logger.debug(strTmp);
		if (className == null) {
			// ������Ϣ
			Logger.debug("parseSyntax�����﷨����");
			return false;
		}

		// ���ر���
		boolean retBool = true;
		// ������ֵ������
		leftObject = parseFunction(funNote, className, method, parameter,
				paraVo, methodReturnHas);
		if (className2 == null) {
			// ���ص�ֱֵ�����û�����Ƚ�
			Logger.debug("�û��������ֵΪ��" + value);
		} else {
			// ������ֵ������
			rightObject = parseFunction(funNote2, className2, method2,
					parameter2, paraVo, methodReturnHas);
			value = String.valueOf(rightObject);
			Logger.debug("�Һ���������ֵΪ:" + value);
		}

		// �жϱ��ʽ
		retBool = isTrueOrNot(leftObject, returnType.toUpperCase(), ysf, value);
		if (retBool) {
			Logger.debug("�������н���жϳɹ�!");
		} else {
			Logger.debug("�������н���жϲ��ɹ�!");
			if (errorMessageBuffer != null) {
				errorMessageBuffer.append(funNote);
				errorMessageBuffer.append("\n");
				errorMessageBuffer.append(leftObject);
				errorMessageBuffer.append("\n");
				errorMessageBuffer.append(ysf);
				errorMessageBuffer.append("\n");
				errorMessageBuffer.append(funNote2);
				errorMessageBuffer.append("\n");
				errorMessageBuffer.append(rightObject);
			}

			// added by �׾� 2004-03-09 �������ݶ���Լ��������ʱ����Ϣ��ʾ����ʽ
			String MACRO_TAG = "%%";
			String originalHint = "";
			if (originalHintBuffer != null) {
				originalHint = originalHintBuffer.toString();
				String hintAfterParse = translateMacro(originalHint, MACRO_TAG,
						returnType, leftObject, value);
				originalHintBuffer.append(hintAfterParse);
			}
		}

		strTmp = "parseSyntax�����﷨����";
		Logger.debug(strTmp);
		return retBool;
	}

	/**
	 * �������ݽ�����
	 * 
	 * @param sourceBillType
	 *            Դ��������PK
	 * @param destBillType
	 *            Ŀ�ĵ�������PK
	 * @param sourceBillVO
	 *            Դ���ݾۺ�VO
	 * @return Ŀ�ĵ��ݾۺ�VO
	 * @throws BusinessException
	 */
	public static AggregatedValueObject runChangeData(String sourceBillType,
			String destBillType, AggregatedValueObject sourceBillVO)
			throws BusinessException {
		return runChangeData(sourceBillType, destBillType, sourceBillVO, null);
	}

	/**
	 * �������ݽ�����
	 * 
	 * @param sourceBillType
	 *            Դ��������PK
	 * @param destBillType
	 *            Ŀ�ĵ�������PK
	 * @param sourceBillVO
	 *            Դ���ݾۺ�VO
	 * @param paraVo
	 *            ����������VO
	 * @return Ŀ�ĵ��ݾۺ�VO
	 * @throws BusinessException
	 */
	public static AggregatedValueObject runChangeData(String sourceBillType,
			String destBillType, AggregatedValueObject sourceBillVO,
			PfParameterVO paraVo) throws BusinessException {

		Logger.debug(">>��ʼ����VO����=" + sourceBillType + "��" + destBillType);
		// ����VO����
		AggregatedValueObject destBillVO = null;
		long start = System.currentTimeMillis();

		Object changeImpl = findBSChangeScriptClass(sourceBillType,
				destBillType);

		try {
			if (changeImpl instanceof VOConversion) {
				VOConversion tmpVar = (VOConversion) changeImpl;
				tmpVar.setSourceBilltype(sourceBillType);
				tmpVar.setDestBilltype(destBillType);
				initConversionEnv(paraVo, tmpVar);
			} else
				throw new PFBusinessException("��̨VO������" + changeImpl
						+ "û�м̳�VOConversion");

			adjustBeforeVoMapping(sourceBillType, destBillType,
					new AggregatedValueObject[] { sourceBillVO });

			// ��ȡĿ��VO�Ķ���
			int intChildLen = 0;
			if (sourceBillVO.getChildrenVO() != null) {
				intChildLen = sourceBillVO.getChildrenVO().length;
			}
			AggregatedValueObject destVo = pfInitVoClass(destBillType,
					intChildLen);
			// �ӿ����з��غ��VO
			destBillVO = ((IchangeVO) changeImpl).retChangeBusiVO(sourceBillVO,
					destVo);

			adjustAfterVoMapping(sourceBillType, destBillType,
					new AggregatedValueObject[] { sourceBillVO },
					new AggregatedValueObject[] { destBillVO });

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage(), e);
		}

		Logger.debug(">>��������VO����=" + sourceBillType + "��" + destBillType
				+ ",��ʱ=" + (System.currentTimeMillis() - start) + "ms");
		return destBillVO;
	}

	/**
	 * ��������
	 * 
	 * @param tmpVar
	 */
	private static void initConversionEnv(PfParameterVO paraVo,
			VOConversion tmpVar) {
		// ��ǰ��½����
		String sysDate = null;
		if (paraVo != null)
			sysDate = paraVo.m_currentDate.substring(0, 10);
		else
			sysDate = new UFDateTime(new Date()).toString().substring(0, 10);
		tmpVar.setSysDate(sysDate);

		// ��ǰ��½����ԱPK
		String sysOperator = null;
		if (paraVo == null || paraVo.m_operator == null) {
			sysOperator = InvocationInfoProxy.getInstance().getUserCode();
		} else
			sysOperator = paraVo.m_operator;
		tmpVar.setSysOperator(sysOperator);

		// ��ǰ��½�Ļ����� WARN::���뱣֤��������VO�а����˵�ǰ��½����
		String accountYear = "";
		try {
			AccountCalendar calendar = AccountCalendar.getInstance();
			calendar.setDate(DateUtilities.getInstance().parse(sysDate));
			accountYear = calendar.getYearVO().getPeriodyear();
		} catch (InvalidAccperiodExcetion e) {
			Logger.warn(e.getMessage(), e);
		} catch (ParseException e) {
			Logger.warn(e.getMessage(), e);
		}
		tmpVar.setSysAccountYear(accountYear);
		// ��ǰ��½��˾PK
		tmpVar.setSysCorp(InvocationInfoProxy.getInstance().getCorpCode());
		// ��ǰϵͳʱ��
		tmpVar.setSysTime(new UFDateTime(new Date()).toString());
		// Ŀ��ҵ������,���û�ж���,��ȡԴ���ݵ�ҵ������->ֱ��ȡ��;
		String destBusiType = null;
		try {
			if (paraVo != null && paraVo.m_businessType != null)
				destBusiType = NCLocator.getInstance().lookup(IPFConfig.class)
						.findDestBusitype(paraVo.m_businessType,
								tmpVar.getDestBilltype());
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		tmpVar.setDestBusitype(destBusiType);
	}

	/**
	 * �������ݽ�����(VO����)
	 * 
	 * @param sourceBillType
	 *            Դ��������PK
	 * @param destBillType
	 *            Ŀ�ĵ�������PK
	 * @param sourceBillVOs
	 *            Դ���ݾۺ�VO����
	 * @return Ŀ�ĵ��ݾۺ�VO����
	 * @throws BusinessException
	 */
	public static AggregatedValueObject[] runChangeDataAry(
			String sourceBillType, String destBillType,
			AggregatedValueObject[] sourceBillVOs) throws BusinessException {

		return runChangeDataAry(sourceBillType, destBillType, sourceBillVOs,
				null);
	}

	/**
	 * �������ݽ�����(VO����)
	 * 
	 * @param sourceBillType
	 *            Դ��������PK
	 * @param destBillType
	 *            Ŀ�ĵ�������PK
	 * @param sourceBillVOs
	 *            Դ���ݾۺ�VO����
	 * @param paraVo
	 *            ����������VO�������ڻ�ȡһЩϵͳ����
	 * @return Ŀ�ĵ��ݾۺ�VO����
	 * @throws BusinessException
	 */
	public static AggregatedValueObject[] runChangeDataAry(
			String sourceBillType, String destBillType,
			AggregatedValueObject[] sourceBillVOs, PfParameterVO paraVo)
			throws BusinessException {
		Logger.debug(">>��ʼ����VO��������=" + sourceBillType + "��" + destBillType);
		// ����VO����
		AggregatedValueObject[] destBillVOs = null;

		Object changeImpl = findBSChangeScriptClass(sourceBillType,
				destBillType);

		try {
			if (changeImpl instanceof VOConversion) {
				VOConversion tmpVar = (VOConversion) changeImpl;
				tmpVar.setSourceBilltype(sourceBillType);
				tmpVar.setDestBilltype(destBillType);
				initConversionEnv(paraVo, tmpVar);
			}

			adjustBeforeVoMapping(sourceBillType, destBillType, sourceBillVOs);

			// ��ȡĿ��VO�Ķ���
			AggregatedValueObject[] destVo = pfInitVosClass(destBillType,
					sourceBillVOs);
			// //�ӿ����з��غ��VO
			destBillVOs = ((IchangeVO) changeImpl).retChangeBusiVOs(
					sourceBillVOs, destVo);

			adjustAfterVoMapping(sourceBillType, destBillType, sourceBillVOs,
					destBillVOs);

		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException(ex.getMessage(), ex);
		}
		Logger.debug(">>��������VO��������=" + sourceBillType + "��" + destBillType);
		return destBillVOs;
	}

	/**
	 * ʵ����VO������
	 * <li>������Ŀ�ĵ�����������ģ�� ������
	 * 
	 * @param sourceBillType
	 *            Դ��������PK
	 * @param destBillType
	 *            Ŀ�ĵ�������PK
	 * @param fullyQualifiedClassName
	 *            VO��������
	 * @return VO������ʵ��
	 * @throws BusinessException
	 */
	public static Object newVoMappingObject(String sourceBillType,
			String destBillType, String fullyQualifiedClassName)
			throws BusinessException {
		Object changeObj = null;
		IPFMetaModel pfmeta = (IPFMetaModel) NCLocator.getInstance().lookup(
				IPFMetaModel.class.getName());

		// Ŀ�ĵ������� ���丸 ����ģ��
		String moduleOfDest = pfmeta.queryModuleOfBilltype(destBillType);
		// Դ������������ģ��
		String moduleOfSrc = pfmeta.queryModuleOfBilltype(sourceBillType);

		if (moduleOfDest == null || moduleOfDest.trim().length() == 0) {
			if (moduleOfSrc == null || moduleOfSrc.trim().length() == 0)
				throw new PFBusinessException("����Դ��Ŀ�ĵ������Ͷ�û��ע��ģ����");
			else {
				changeObj = NewObjectService.newInstance(moduleOfSrc,
						fullyQualifiedClassName);
				Logger.debug("OK-Ŀ�ĵ���û������ģ�飬������Դ��������ģ��" + moduleOfSrc
						+ "���ҵ�������=" + changeObj);
			}
		} else {
			// ������Ŀ�ĵ�����������ģ�� ������
			try {
				changeObj = NewObjectService.newInstance(moduleOfDest,
						fullyQualifiedClassName);
				Logger.debug("OK-��Ŀ�ĵ�������ģ��" + moduleOfDest + "���ҵ�������="
						+ changeObj);
			} catch (Exception e) {
				if (moduleOfSrc == null || moduleOfSrc.trim().length() == 0)
					throw new PFBusinessException(
							"����VO�����಻��Ŀ�ĵ�����������ģ���У�����Դ����û��ע������ģ��");
				else {
					changeObj = NewObjectService.newInstance(moduleOfSrc,
							fullyQualifiedClassName);
					Logger.debug("OK-��Ŀ�ĵ�������ģ�����Ҳ��������࣬������Դ��������ģ��" + moduleOfSrc
							+ "���ҵ�������=" + changeObj);
				}
			}
		}

		Class c = changeObj.getClass();
		Logger.debug(">>>OBJ=" + changeObj + ";CL="
				+ c.getProtectionDomain().getClassLoader());
		Logger.debug(">>>LOC="
				+ c.getProtectionDomain().getCodeSource().getLocation());
		return changeObj;
	}

	/**
	 * ������
	 * 
	 * @param className
	 *            ���е�������
	 * @param method
	 *            ��������
	 * @param parameter
	 *            ����
	 * @param paraVo
	 *            ����������VO
	 * @param keyHas
	 *            ����ֵHash
	 * @param methodReturnHas
	 *            ����ֵHash
	 * 
	 * @modifier leijun 2005-6-20 ��������PK�������Ϊ4�ַ�,�ǹ̶���2���ַ�
	 */
	public static Object runClass(String className, String method,
			String parameter, PfParameterVO paraVo, Hashtable keyHas,
			Hashtable methodReturnHas) throws BusinessException {
		Logger.debug("**********ִ����PfUtilTools.runClass()��ʼ************");

		Logger.debug("ִ����:" + className + ";����:" + method + ";����:" + parameter);

		long begin = System.currentTimeMillis();

		// /1.������������Ϊ��������","�ָ��
		// String[] paraStrs = nc.vo.pf.pub.PfComm.dealString(parameter, ",");
		StringTokenizer tmpStrToken = new StringTokenizer(parameter, ",");
		String[] paraStrs = new String[tmpStrToken.countTokens()];
		int index = 0;
		while (tmpStrToken.hasMoreTokens())
			paraStrs[index++] = tmpStrToken.nextElement().toString();

		Object[] paraObjects = new Object[paraStrs.length];
		Class[] paraClasses = new Class[paraStrs.length];

		for (int i = 0; i < paraStrs.length; i++) {
			// ����������
			// �������"."�����ʾVO����
			boolean isVo = paraStrs[i].indexOf(".") > 0 ? true : false;
			int colonPos = paraStrs[i].indexOf(":");

			if (paraStrs[i].startsWith("&")) {
				// 1.�����"&"��ͷ�����ʾ���в���
				String paramKey = paraStrs[i].substring(1, colonPos);
				String strDataType = paraStrs[i].substring(colonPos + 1);
				Object valueObj = keyHas == null ? null : keyHas.get(paramKey);

				if (isVo) {
					// FIXME::�������������֧��leijun
					if (strDataType.endsWith("[]")) {
						strDataType = strDataType.substring(0, strDataType
								.length() - 2);
						int intAryLen = 0;
						if (valueObj instanceof AggregatedValueObject[]) {
							intAryLen = ((AggregatedValueObject[]) valueObj).length;
						} else if (valueObj instanceof nc.vo.pub.ValueObject[]) {
							intAryLen = ((nc.vo.pub.ValueObject[]) valueObj).length;
						}
						paraClasses[i] = getClassNameClass(strDataType,
								intAryLen);
					} else {
						paraClasses[i] = getClassByName(strDataType);
					}
				} else {
					paraClasses[i] = parseTypeClass(strDataType);
				}
				paraObjects[i] = valueObj;
				// WARN::ֱ���ٴα���
				continue;
			}

			if (isVo) {
				// 2.�����а���VO����
				if (colonPos < 0)
					throw new PFBusinessException("����ע�ᴦVO��������ע��");
				// ��ȡ�������ͺ�VO���� lj@2005-6-17
				String tmpbillType = paraStrs[i].substring(colonPos + 1);
				String voClassName = paraStrs[i].substring(0, colonPos);
				/***************************************************************
				 * �ӵ���Vo�뵥�����Ͷ��ձ��л�ȡ��������,�жϵ��������Ƿ��뵱ǰ������ͬ: �����ͬ���ý�������VO,���򽻻�����VO
				 **************************************************************/
				if (tmpbillType.equals("00")) {
					throw new PFBusinessException("����ע���ޱ�׼VO");
				} else if (tmpbillType.equals("01")) {
					// ��ʾ ͨ�õ�VO����
					if (voClassName.endsWith("[]")) {
						paraObjects[i] = paraVo.m_preValueVos; // ����ֵ
						paraClasses[i] = AggregatedValueObject[].class; // ������
					} else {
						paraObjects[i] = paraVo.m_preValueVo; // ����ֵ
						paraClasses[i] = AggregatedValueObject.class;
					}
				} else {
					// ������������ͬ�Ļ�ͬ��ĵ��ݵĲ���Ҫ����ת��
					// Integer inBillType =
					// PfDataCache.getBillTypeInfo(paraVo.m_billType).getBillstyle();
					// Integer currBillType =
					// PfDataCache.getBillTypeInfo(tmpbillType).getBillstyle();
					// if (tmpbillType.equals(paraVo.m_billType)
					// || (inBillType != null && currBillType != null &&
					// inBillType.equals(currBillType))) {
					if (isSimilarBilltype(paraVo.m_billType, tmpbillType)) {
						if (voClassName.endsWith("[]")) {
							paraObjects[i] = paraVo.m_preValueVos; // ����ֵ
							paraClasses[i] = getClassNameClass(voClassName
									.substring(0, voClassName.length() - 2),
									paraVo.m_preValueVos.length);
						} else {
							paraObjects[i] = paraVo.m_preValueVo; // ����ֵ
							paraClasses[i] = getClassByName(voClassName);
						}
					} else {
						// ���ֵ�������֮����Ҫ����ת��
						if (voClassName.endsWith("[]")) {
							paraObjects[i] = runChangeDataAry(
									paraVo.m_billType, tmpbillType,
									paraVo.m_preValueVos, paraVo); // ����ֵ
							paraClasses[i] = getClassNameClass(voClassName
									.substring(0, voClassName.length() - 2),
									paraVo.m_preValueVos.length);
						} else {
							paraObjects[i] = runChangeData(paraVo.m_billType,
									tmpbillType, paraVo.m_preValueVo, paraVo); // ����ֵ
							paraClasses[i] = getClassByName(voClassName);
						}
					}
				}
			} else {
				// 3.�����в�����VO����
				parseParmeter(paraStrs[i], paraObjects, paraClasses, i, paraVo,
						methodReturnHas);
			}
		} // /{end for}

		// /2.ʵ�����࣬��������
		Object tmpObj = null;
		try {
			tmpObj = instantizeObject(paraVo.m_billType, className);
		} catch (Exception e) {
			Logger.warn("��ģ�����Ҳ����࣬�����ΪPUBLIC�ࣺ" + className, e);
			try {
				// WARN::�����ģ�����Ҳ����࣬�����ΪPUBLIC��
				Class cls = Class.forName(className);
				tmpObj = cls.newInstance();
			} catch (ClassNotFoundException ex) {
				Logger.error(ex.getMessage(), ex);
			} catch (InstantiationException ex) {
				Logger.error(ex.getMessage(), ex);
			} catch (IllegalAccessException ex) {
				Logger.error(ex.getMessage(), ex);
			}
		}

		if (tmpObj == null)
			throw new PFBusinessException("�޷�ʵ�����ࣺ" + className);

		// /3.ִ�з���
		Object retObj = null;
		try {
			Class c = tmpObj.getClass();
			// ��ȡ����
			Method cm = c.getMethod(method, paraClasses);

			// fangj 2001-11-08����ΪVOID���ж�
			// �����߳�ͬ������֤������ͬһ�߳�������
			synchronized (Thread.currentThread()) {
				if (cm.getReturnType().toString().equals("void")) {
					cm.invoke(tmpObj, paraObjects);
				} else {
					retObj = cm.invoke(tmpObj, paraObjects);
				}
			}
		} catch (SecurityException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			// WARN::��Ҫ�ѷ�������е��쳣�׳���!
			Throwable expt = e.getTargetException();
			Logger.error(e.getMessage(), e);
			if (expt instanceof BusinessException)
				// ҵ���쳣ֱ���׳���
				throw (BusinessException) expt;
			else if (expt instanceof RemoteException
					&& expt.getCause() instanceof BusinessException) {
				throw (BusinessException) expt.getCause();
			} else
				throw new PFBusinessException(expt.getMessage(), expt);
		}

		long end = System.currentTimeMillis();
		Logger.debug("************ִ����PfUtilTools.runClass()����,��ʱ="
				+ (end - begin) + "ms************");
		return retObj;
	}

	/**
	 * �ж������������ͣ��������ͣ��Ƿ�ΪͬԴ��ϵ
	 * 
	 * @param aTypeCode
	 * @param bTypeCode
	 * @return
	 * @since 5.5
	 */
	private static boolean isSimilarBilltype(String aTypeCode, String bTypeCode) {
		boolean isSimilar = false;
		BilltypeVO aTypeVO = PfDataCache.getBillTypeInfo(aTypeCode);
		BilltypeVO bTypeVO = PfDataCache.getBillTypeInfo(bTypeCode);
		if (aTypeVO.getIstransaction() != null
				&& aTypeVO.getIstransaction().booleanValue()) {
			// aΪ��������
			BilltypeVO aParentTypeVO = PfDataCache.getBillTypeInfo(aTypeVO
					.getParentbilltype());
			Integer aParentStyle = aParentTypeVO.getBillstyle();
			if (bTypeVO.getIstransaction() != null
					&& bTypeVO.getIstransaction().booleanValue()) {
				// bΪ��������
				BilltypeVO bParentTypeVO = PfDataCache.getBillTypeInfo(bTypeVO
						.getParentbilltype());
				Integer bParentStyle = bParentTypeVO.getBillstyle();
				isSimilar = aTypeCode.equals(bTypeCode)
						|| aParentTypeVO.getPk_billtypecode().equals(
								bParentTypeVO.getPk_billtypecode())
						|| (aParentStyle != null && bParentStyle != null && aParentStyle
								.equals(bParentStyle));
			} else {
				// bΪ��������
				Integer bStyle = bTypeVO.getBillstyle();
				isSimilar = aParentTypeVO.getPk_billtypecode()
						.equals(bTypeCode)
						|| (aParentStyle != null && bStyle != null && aParentStyle
								.equals(bStyle));
			}
		} else {
			// aΪ��������
			Integer aStyle = aTypeVO.getBillstyle();
			if (bTypeVO.getIstransaction() != null
					&& bTypeVO.getIstransaction().booleanValue()) {
				// bΪ��������
				BilltypeVO bParentTypeVO = PfDataCache.getBillTypeInfo(bTypeVO
						.getParentbilltype());
				Integer bParentStyle = bParentTypeVO.getBillstyle();
				isSimilar = bParentTypeVO.getPk_billtypecode()
						.equals(aTypeCode)
						|| (bParentStyle != null && aStyle != null && bParentStyle
								.equals(aStyle));
			} else {
				// bΪ��������
				Integer bStyle = bTypeVO.getBillstyle();
				isSimilar = (bTypeCode.equals(bTypeCode) || (aStyle != null
						&& bStyle != null && aStyle.equals(bStyle)));
			}
		}

		return isSimilar;
	}

	/**
	 * ���滻�����磺����ã�����%%e.getName%%ͨ���ˡ� WARN::���滻ǰ����ȼ��һ�º�������յ��Ƿ�ƥ�䣬
	 * Ҳ����˵�Ƿ���ż��������
	 * 
	 * @author �׾�
	 */
	protected static String translateMacro(String content, String macro_tag,
			String type, Object leftValue, Object rightValue) {
		int pos_b = 0; // ���
		int pos_e = 0; // �յ�
		int offset = 0;
		boolean bFound = false;
		boolean isMatch = false;
		StringBuffer buffer = new StringBuffer();

		do {
			pos_e = content.indexOf(macro_tag, pos_b);
			bFound = pos_e == -1 ? false : true;
			offset = bFound ? macro_tag.length() : 0;
			if (bFound) {
				// ��û��ƥ������"%% %%"��
				if (isMatch) {
					// �滻��"%%macro%%"�е�����
					String macro = content.substring(pos_b, pos_e);
					// System.out.println("macro = " + express);
					// ExpressParser parser = new ExpressParser(context,
					// express);
					buffer.append(getExpressValue(macro, type, leftValue,
							rightValue));
				} else {
					// ����������λ��%%"��֮ǰ���ַ���
					buffer.append(content.substring(pos_b, pos_e));
				}
				isMatch = !isMatch;
			} else {
				buffer.append(content.substring(pos_b));
			}
			pos_b = pos_e + offset;
		} while (pos_b != -1);

		return buffer.toString();
	}

	public PfUtilTools() {
		super();
	}

	/**
	 * �ж��Ƿ�����VO������Ϊtrue ��ʾ�õ�ǰ������ò����˻�����أ�����false������� �㷨�� 1.�����˻��ɫ�޹أ��򷵻ظü�¼��
	 * 2.�������йأ��򷵻ظ��ò������йصļ�¼�� 3.��ɫ�йأ��򷵻ظý�ɫ��صļ�¼
	 * 
	 * @param pkCorp
	 *            ��˾PK
	 * @param currUserPK
	 *            ��ǰ�������û�
	 * @param configflag
	 *            �û����ɫ���
	 * @param configedOperator
	 *            ���õ��û����ɫPK
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isContinue(String pkCorp, String currUserPK,
			int configflag, String configedOperator) throws BusinessException {
		switch (configflag) {
		case IPFConfigInfo.UserRelation:
			// �жϵ�ǰ�¼��Ƿ���������й�
			if (configedOperator.equals(currUserPK))
				return true;
			return false;
		case IPFConfigInfo.RoleRelation: {
			// ���ҵ�ǰ�û������Ľ�ɫ
			IUserManageQuery roleBS = (IUserManageQuery) NCLocator
					.getInstance().lookup(IUserManageQuery.class.getName());

			RoleVO[] roles = roleBS.getUserRole(currUserPK, pkCorp);

			if (roles == null || roles.length == 0)
				return false;

			for (int i = 0; i < roles.length; i++) {
				if (configedOperator.equals(roles[i].getPrimaryKey()))
					return true;
			}
			return false;
		}
		default:
			return true;
		}

	}

	/**
	 * �����α���Ķ����ű�Java�ļ���Class�ļ���Ⱥ��
	 * 
	 * @param relativePath
	 *            �����NCHOME��·��
	 * @param fileName
	 *            �ļ��������ܴ���Ŀ¼
	 */
	public static void clusterFile(String relativePath, String fileName,
			String module) {
		Logger.debug(">>����ƽ̨��Ⱥ��Ϣ���Ϳ�ʼ");
		// Logger.debug(">>relativePath=" + relativePath + ";fileName=" +
		// fileName);
		String strFile = RuntimeEnv.getInstance().getNCHome() + relativePath
				+ fileName;
		File file = new File(strFile);
		if (!file.exists()) {
			Logger.warn(">>�ļ�=" + strFile + "�������ڣ��޷���Ⱥ���ļ���");
			return;
		}

		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes);
			BytesClusterMessage message = new BytesClusterMessage();
			// ���й㲥���ļ����͵���Ϣ
			ClusterMessageHeader header = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_FILETYPE, "PF");
			message.addHeader(header);
			// �����ļ�����Head��Ϣ
			ClusterMessageHeader header2 = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_FILENAME, fileName);
			message.addHeader(header2);
			// �����ļ�ʱ�����head��Ϣ
			ClusterMessageHeader header3 = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_TS, String.valueOf(file.lastModified()));
			message.addHeader(header3);
			// �ļ������NCHOME��·��
			ClusterMessageHeader header4 = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_PATH, relativePath);
			message.addHeader(header4);

			// lj+2007-1-12 ����ģ����
			if (module != null) {
				// NOTE::ֻ��Class�ļ��������������Ҫ����
				ClusterMessageHeader header5 = new ClusterMessageHeader(
						CLUSTER_MSG_HEADER_MODULE, module);
				message.addHeader(header5);
			}

			// add Message
			message.setBytes(bytes);
			ClusterSender sender = (ClusterSender) NCLocator.getInstance()
					.lookup(ClusterSender.class.getName());
			sender.sendMessage(message);

			Logger.debug(">>����ƽ̨��Ⱥ��Ϣ���ͽ���=" + strFile);

		} catch (FileNotFoundException e) {
			Logger.error(e.getMessage(), e);
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		} catch (ClusterServiceException e) {
			Logger.error(e.getMessage(), e);
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e2) {
					Logger.error(e2.getMessage(), e2);
				}
				bis = null;
			}
		}

	}

	/**
	 * ÿ��Servlet����Ҫע������Դ��Ϣ�ſɵ���DMO
	 * 
	 * @param dsName
	 */
	public static void regDataSourceForServlet(String dsName)
			throws BusinessException {
		// 1.У��
		try {
			IConfigFileService iAccount = (IConfigFileService) NCLocator
					.getInstance().lookup(IConfigFileService.class.getName());

			String[] dataSources = iAccount.findDatasource();
			if (dataSources == null)
				throw new PFBusinessException("ϵͳ�쳣�����ҹ���Ա");
			boolean bValid = Arrays.asList(dataSources).contains(dsName);
			if (!bValid)
				throw new PFBusinessException("������Ч����Դ");
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("ϵͳ�쳣�����ҹ���Ա");
		}

		// 2.ע��
		InvocationInfoProxy.getInstance().setUserDataSource(dsName);
	}

	/**
	 * ��̨����ĳ�ŵ���
	 * 
	 * @param billType
	 * @param billId
	 * @param checkResult
	 * @param checkNote
	 * @param checkman
	 * @param dispatched_ids
	 * @return
	 * @throws Exception
	 */
	public static String approveSilently(String billType, String billId,
			String checkResult, String checkNote, String checkman,
			String[] dispatched_ids) throws Exception {

		// 1.��õ��ݾۺ�VO
		IPFConfig bsConfig = (IPFConfig) NCLocator.getInstance().lookup(
				IPFConfig.class.getName());
		AggregatedValueObject billVo = bsConfig.queryBillDataVO(billType,
				billId);
		if (billVo == null)
			throw new PFBusinessException("���󣺸��ݵ������ͺ͵���ID��ȡ�������ݾۺ�VO");

		// 2.��ù���������������
		java.util.Date date = new java.util.Date();
		String currentDate = DateUtilities.getInstance().formatJustDay(date);
		IWorkflowMachine bsWorkflow = (IWorkflowMachine) NCLocator
				.getInstance().lookup(IWorkflowMachine.class.getName());
		PfUtilWorkFlowVO wfVO = bsWorkflow.checkWorkFlow(IPFActionName.APPROVE
				+ checkman, billType, currentDate, billVo, null);
		if (wfVO != null) {
			/* �ӵ��ݾۺ�VO�л�ñ��֡�������� */
			CurrencyInfo cinfo = new CurrencyInfo();
			PfUtilBaseTools.fetchMoneyInfo(billVo, cinfo, billType);
			wfVO.putMoney(billId, cinfo);

			wfVO.setCheckNote(checkNote);
			// ��ȡ�������-ͨ��/��ͨ��/����
			if ("Y".equalsIgnoreCase(checkResult)) {
				wfVO.setIsCheckPass(true);
			} else if ("N".equalsIgnoreCase(checkResult)) {
				wfVO.setIsCheckPass(false);
			} else if ("R".equalsIgnoreCase(checkResult)) {
				wfVO.getTaskInfo().getTask().setTaskType(WFTask.TYPE_BACKWARD);
				wfVO.getTaskInfo().getTask().setBackToFirstActivity(true);
			} else
				return "������Ϣ��ʽ����";

			// ��ȡ����ʱ��
			wfVO.setDealDate(new UFDateTime(date));

			// ָ����Ϣ
			if (dispatched_ids != null && dispatched_ids.length > 0) {
				// ��������ָ�ɵĲ�����
				HashMap hm = new HashMap();
				for (int i = 0; i < dispatched_ids.length; i++) {
					int index = dispatched_ids[i].indexOf("#");
					String userid = dispatched_ids[i].substring(0, index);
					String actDefid = dispatched_ids[i].substring(index + 1);
					if (hm.get(actDefid) == null)
						hm.put(actDefid, new HashSet());
					((HashSet) hm.get(actDefid)).add(userid);
				}
				// ��д�����ָ����Ϣ��
				Vector vecDispatch = wfVO.getTaskInfo().getAssignableInfos();
				for (int i = 0; i < vecDispatch.size(); i++) {
					AssignableInfo ai = (AssignableInfo) vecDispatch.get(i);
					HashSet hs = (HashSet) hm.get(ai.getActivityDefId());
					if (hs != null) {
						// XXX:Ҫ��������ظ���ָ���û�PK
						for (Iterator iter = hs.iterator(); iter.hasNext();) {
							String userId = (String) iter.next();
							if (!ai.getAssignedOperatorPKs().contains(userId))
								ai.getAssignedOperatorPKs().add(userId);
						}
					}
				}
			}
		}

		// 3.ִ����������
		IplatFormEntry pff = (IplatFormEntry) NCLocator.getInstance().lookup(
				IplatFormEntry.class.getName());
		pff.processAction(IPFActionName.APPROVE + checkman, billType,
				currentDate, wfVO, billVo, null, null);

		return null;
	}

	public static String joinHtml(String billHtml, String approveHtml) {
		billHtml = "<head><body>" + billHtml + "<hr>" + approveHtml
				+ "</body></html>";
		return billHtml;
	}

	/**
	 * ���;����������ܵ�HTML�ʼ���������Ϣ���п���
	 * 
	 * @param billHtml
	 * @param email
	 * @param billId
	 * @param billtype
	 * @param topic
	 * @param checkman
	 * @param assignInfos
	 * @throws BusinessException
	 */
	public static void sendEmailWithApprove(String billHtml, String email,
			String billId, String billtype, String topic, String checkman,
			Vector assignInfos) throws BusinessException {

		// ���;����������ܵ�HTML�ʼ���������Ϣ���п���
		String subject = MessageVO.getMessageNoteAfterI18N(topic);
		// subject += "(�ɴ򿪸�����������)";
		subject = getEncodedString(subject);

		String htmlContentNoApprove = mailTemplateHasBillWithoutApprove(
				billHtml, billId);
		String htmlContentWithApprove = mailTemplateHasBillWithApprove(
				billHtml, checkman, billtype, billId, assignInfos);

		// ������ʱ����
		String fileName = RuntimeEnv.getInstance().getNCHome()
				+ "/webapps/nc_web/pf/" + billId + ".html";
		try {
			writeTempFile(fileName, htmlContentWithApprove);
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
		// �������������ܵ��ʼ�����������������
		sendHtmlEmail(subject, new String[] { email }, htmlContentNoApprove,
				fileName);

		// ɾ����ʱ�ʼ������ļ�
		File f = new File(fileName);
		f.delete();
	}

	private static String getEncodedString(String name) {
		if (name.getBytes().length == name.length())
			return name;
		BASE64Encoder encoder = new BASE64Encoder();
		String prefix = "=?gb2312?B?";
		String suffix = "?=";
		StringBuilder sb = new StringBuilder();
		int maxLen = 20;
		while (name.length() > maxLen) {
			String s = name.substring(0, maxLen);
			sb.append(prefix).append(encoder.encode(s.getBytes())).append(
					suffix);
			name = name.substring(maxLen);
		}
		if (name.length() > 0) {
			sb.append(prefix).append(encoder.encode(name.getBytes())).append(
					suffix);
		}
		return sb.toString();
	}

	private static void writeTempFile(String fileName, String strContent)
			throws IOException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			fw.write(strContent);
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (fw != null)
				fw.close();
		}
	}

	/**
	 * �������������ܵ��ʼ���������Ϣ���п���
	 * <li>����е�����Ϣ����ΪHTML�ʼ���
	 * <li>����޵�����Ϣ����Ϊ�ı��ʼ�
	 * 
	 * @param billHtml
	 * @param strEmails
	 * @param topic
	 * @param billId
	 * @throws BusinessException
	 */
	public static void sendEmailsWithoutApprove(String billHtml,
			String[] strEmails, String topic, String billId)
			throws BusinessException {
		String subject = MessageVO.getMessageNoteAfterI18N(topic);
		subject = getEncodedString(subject);

		if (StringUtil.isEmptyWithTrim(billHtml)) {
			// ���ͼ򵥸�ʽ���ʼ�������������
			sendEmail(null, subject, "�뼰ʱ��¼����!", strEmails, null, null);
		} else {
			// ����HTML�������ݸ�ʽ���ʼ�������������
			sendHtmlEmail(subject, strEmails,
					mailTemplateHasBillWithoutApprove(billHtml, billId), null);
		}
	}

	/**
	 * ����ģ��HTML�ļ����滻���еĵ�����Ϣ
	 * <li>����������
	 * 
	 * @param billHtml
	 * @param billId
	 * @return
	 * @throws BusinessException
	 */
	private static String mailTemplateHasBillWithoutApprove(String billHtml,
			String billId) throws BusinessException {
		Logger.debug("mailTemplateHasBillWithoutApprove() called");
		// �Ӻ�̨�ļ���ȡ�ʼ�ģ��
		String templateURL = RuntimeEnv.getInstance().getNCHome()
				+ "/webapps/nc_web/pf";
		File f = new File(templateURL + "/mail_template.htm");
		String strFile = null;
		try {
			strFile = IOUtil.toString(new BufferedReader(new FileReader(f)));
		} catch (FileNotFoundException e) {
			throw new PFBusinessException(">>�����Ҳ���webapps/nc_web/�µ��ʼ�ģ���ļ�");
		} catch (IOException e) {
			throw new PFBusinessException(e.getMessage(), e);
		}

		if (StringUtil.isEmptyWithTrim(strFile))
			return ">>���󣺶�ȡwebapps/nc_web/�µ��ʼ�ģ���ļ�Ϊ��";

		// XXX::���Web��������ַ
		String webUrl = ServerConfiguration.getServerConfiguration()
				.getDefWebServerURL();
		String pf_web_url = webUrl + "/pf";

		// �滻����ͼƬ��URL
		strFile = StringUtil.replaceAllString(strFile, "#PF_WEB_URL#",
				pf_web_url);

		// �滻������Ϣ
		if (StringUtil.isEmptyWithTrim(billHtml)) {
			String tagBillBegin = "<!--BILL_INFO_BEGIN-->";
			String tagBillEnd = "<!--BILL_INFO_END-->";
			int index = strFile.indexOf(tagBillBegin);
			int indexEnd = strFile.indexOf(tagBillEnd);
			strFile = strFile.substring(0, index + tagBillBegin.length())
					+ strFile.substring(indexEnd);
		} else {
			String tag2BillBegin = "<!--BILL_INFO2_BEGIN-->";
			String tag2BillEnd = "<!--BILL_INFO2_END-->";
			int index = strFile.indexOf(tag2BillBegin);
			int indexEnd = strFile.indexOf(tag2BillEnd);
			strFile = strFile.substring(0, index + tag2BillBegin.length())
					+ billHtml + strFile.substring(indexEnd);
		}

		// �滻��ʷ������Ϣ
		String tagHistoryBegin = "<!--APPROVE_HISTORY_BEGIN-->";
		String tagHistoryEnd = "<!--APPROVE_HISTORY_END-->";
		int index = strFile.indexOf(tagHistoryBegin);
		int indexEnd = strFile.indexOf(tagHistoryEnd);
		String strHistory = strFile.substring(index + tagHistoryBegin.length(),
				indexEnd);
		strFile = strFile.substring(0, index + tagHistoryBegin.length())
				+ getNoteOfBill(strHistory, billId)
				+ strFile.substring(indexEnd);

		return strFile;

	}

	/**
	 * ����ģ��HTML�ļ����滻���еĵ�����Ϣ�Լ�������Ϣ
	 * <li>����������
	 * 
	 * @param billHtml
	 * @param checkman
	 * @param billType
	 * @param billId
	 * @param assignInfos
	 * @return
	 * @throws BusinessException
	 */
	private static String mailTemplateHasBillWithApprove(String billHtml,
			String checkman, String billType, String billId, Vector assignInfos)
			throws BusinessException {
		Logger.debug("mailTemplateHasBillWithApprove() called");
		// �Ӻ�̨�ļ���ȡ�ʼ�ģ��
		String templateURL = RuntimeEnv.getInstance().getNCHome()
				+ "/webapps/nc_web/pf";
		String fileName = null;
		// XXX:�ж��ʼ�������Ҫ����ǩ��
		boolean isSignature = WirelessManager.getMobileConfig().isSignature();
		if (isSignature)
			fileName = "/approvemail_template2.htm";
		else
			fileName = "/approvemail_template.htm";
		File f = new File(templateURL + fileName);
		String strFile = null;
		try {
			strFile = IOUtil.toString(new BufferedReader(new FileReader(f)));
		} catch (FileNotFoundException e) {
			throw new PFBusinessException(">>�����Ҳ���webapps/nc_web/�µ��ʼ�ģ���ļ�");
		} catch (IOException e) {
			throw new PFBusinessException(e.getMessage(), e);
		}

		if (StringUtil.isEmptyWithTrim(strFile))
			return ">>���󣺶�ȡwebapps/nc_web/�µ��ʼ�ģ���ļ�Ϊ��";

		// XXX::���Web��������ַ
		String webUrl = ServerConfiguration.getServerConfiguration()
				.getDefWebServerURL();
		String pf_web_url = webUrl + "/pf";
		// �滻����ͼƬ��URL
		strFile = StringUtil.replaceAllString(strFile, "#PF_WEB_URL#",
				pf_web_url);

		// �滻������Ϣ
		if (StringUtil.isEmptyWithTrim(billHtml)) {
			String tagBillBegin = "<!--BILL_INFO_BEGIN-->";
			String tagBillEnd = "<!--BILL_INFO_END-->";
			int index = strFile.indexOf(tagBillBegin);
			int indexEnd = strFile.indexOf(tagBillEnd);
			strFile = strFile.substring(0, index + tagBillBegin.length())
					+ strFile.substring(indexEnd);
		} else {
			String tag2BillBegin = "<!--BILL_INFO2_BEGIN-->";
			String tag2BillEnd = "<!--BILL_INFO2_END-->";
			int index = strFile.indexOf(tag2BillBegin);
			int indexEnd = strFile.indexOf(tag2BillEnd);
			strFile = strFile.substring(0, index + tag2BillBegin.length())
					+ billHtml + strFile.substring(indexEnd);
		}

		// �滻����action
		String strActionServlet = webUrl + "/service/mailapproveserv";
		strFile = StringUtil.replaceAllString(strFile, "#ACTIONSERVLET_URL#",
				strActionServlet);

		String needDispatch = NEEDDISPATCH;
		if (assignInfos == null || assignInfos.size() == 0) {
			needDispatch = "NONEEDdISPATCH";
		}

		// �滻������
		// ��uid/pwd/billid/billtype/ds��Ϊ5�������ֶ�
		String strHidden = "<p><input name=\""
				+ PfUtilBaseTools.MAIL_APPROVE_UID
				+ "\" type=\"hidden\" id=\""
				+ PfUtilBaseTools.MAIL_APPROVE_UID
				+ "\" value=\""
				+ checkman
				+ "\">\n"
				+ "<input name=\""
				+ PfUtilBaseTools.MAIL_APPROVE_DS
				+ "\" type=\"hidden\" id=\""
				+ PfUtilBaseTools.MAIL_APPROVE_DS
				+ "\" value=\""
				+ InvocationInfoProxy.getInstance().getUserDataSource()
				+ "\">\n"
				+ "<input name=\""
				+ NEEDDISPATCH
				+ "\" type=\"hidden\" id=\""
				+ NEEDDISPATCH
				+ "\" value=\""
				+ needDispatch
				+ "\">\n"
				+ "<input name=\""
				+ PfUtilBaseTools.MAIL_APPROVE_BILLTYPE
				+ "\" type=\"hidden\" id=\""
				+ PfUtilBaseTools.MAIL_APPROVE_BILLTYPE
				+ "\" value=\""
				+ billType
				+ "\">\n"
				+ "<input name=\""
				+ PfUtilBaseTools.MAIL_APPROVE_BILLID
				+ "\" type=\"hidden\" id=\""
				+ PfUtilBaseTools.MAIL_APPROVE_BILLID
				+ "\" value=\""
				+ billId
				+ "\">\n"
				+ (isSignature ? "<input name=\""
						+ PfUtilBaseTools.MAIL_SIGNED_APPROVE_UID
						+ "\" type=\"hidden\" id=\""
						+ PfUtilBaseTools.MAIL_SIGNED_APPROVE_UID + "\">" : "")
				+ "</p>\n";

		String tagHidden = "<!--HIDDEN_FIELD-->";
		int index = strFile.indexOf(tagHidden);
		strFile = strFile.substring(0, index + tagHidden.length()) + strHidden
				+ strFile.substring(index);

		// �滻��ʷ������Ϣ
		String tagHistoryBegin = "<!--APPROVE_HISTORY_BEGIN-->";
		String tagHistoryEnd = "<!--APPROVE_HISTORY_END-->";
		index = strFile.indexOf(tagHistoryBegin);
		int indexEnd = strFile.indexOf(tagHistoryEnd);
		String strHistory = strFile.substring(index + tagHistoryBegin.length(),
				indexEnd);
		strFile = strFile.substring(0, index + tagHistoryBegin.length())
				+ getNoteOfBill(strHistory, billId)
				+ strFile.substring(indexEnd);

		// �滻ָ����Ϣ
		String tagDispatchBegin = "<!--DISPATCH_BEGIN-->";
		String tagDispatchEnd = "<!--DISPATCH_END-->";
		index = strFile.indexOf(tagDispatchBegin);
		indexEnd = strFile.indexOf(tagDispatchEnd);
		if (assignInfos == null || assignInfos.size() == 0) {
			strFile = strFile.substring(0, index + tagDispatchBegin.length())
					+ strFile.substring(indexEnd);
		} else {
			String tagDispatchActivityBegin = "<!--DISPATCH_ACTIVITY_BEGIN-->";
			String tagDispatchActivityEnd = "<!--DISPATCH_ACTIVITY_END-->";
			int index_a = strFile.indexOf(tagDispatchActivityBegin);
			int indexEnd_a = strFile.indexOf(tagDispatchActivityEnd);
			String strDispatchAct = strFile.substring(index_a
					+ tagDispatchActivityBegin.length(), indexEnd_a);

			String strTemp1 = "";
			for (Iterator iter = assignInfos.iterator(); iter.hasNext();) {
				AssignableInfo ainfo = (AssignableInfo) iter.next();
				strDispatchAct = StringUtil.replaceIgnoreCase(strDispatchAct,
						"#ACTIVITY_NAME#", ainfo.getDesc());

				String tagSelectUserBegin = "<!--SELECT_USERS_BEGIN-->";
				String tagSelectUserEnd = "<!--SELECT_USERS_END-->";
				int index_b = strDispatchAct.indexOf(tagSelectUserBegin);
				int indexEnd_b = strDispatchAct.indexOf(tagSelectUserEnd);
				String strUser = strDispatchAct.substring(index_b
						+ tagSelectUserBegin.length(), indexEnd_b);
				String strTemp2 = "";
				for (int i = 0; i < ainfo.getOuUsers().size(); i++) {
					OrganizeUnit ou = (OrganizeUnit) ainfo.getOuUsers().get(i);
					String ssstrUser = StringUtil.replaceIgnoreCase(strUser,
							"#USER_ID#", ou.getPk() + "#"
									+ ainfo.getActivityDefId());
					ssstrUser = StringUtil.replaceIgnoreCase(ssstrUser,
							"#USER_NAME#", ou.getName());
					strTemp2 += ssstrUser;
				}
				strTemp1 += strDispatchAct.substring(0, index_b
						+ tagSelectUserBegin.length())
						+ strTemp2 + strDispatchAct.substring(indexEnd_b);
			}

			strFile = strFile.substring(0, index_a
					+ tagDispatchActivityBegin.length())
					+ strTemp1 + strFile.substring(indexEnd_a);

		}

		return strFile;
	}

	/**
	 * ��ѯ�õ��ݵ���ʷ������Ϣ����ƴ�յ�HTML��
	 * 
	 * @param strHistory
	 * @param billId
	 * @return
	 * @throws BusinessException
	 */
	private static String getNoteOfBill(String strHistory, String billId)
			throws BusinessException {
		// ��ѯ�õ��ݵ���ʷ������Ϣ
		String sql = "select dealdate,checknote,b.user_name,localmoney,approvestatus,approveresult,senddate,actiontype "
				+ "from pub_workflownote a, sm_user b "
				+ "where a.checkman=b.cuserid and approveresult is not null and billid='"
				+ billId + "' order by a.dealdate";

		Vector checkedData = null;
		IPFWorkflowQry wfQry = (IPFWorkflowQry) NCLocator.getInstance().lookup(
				IPFWorkflowQry.class.getName());
		checkedData = wfQry.queryDataBySQL(sql, 8, new int[] { IDapType.UFDATE,
				IDapType.STRING, IDapType.STRING, IDapType.UFDOUBLE,
				IDapType.INTEGER, IDapType.STRING, IDapType.UFDATE,
				IDapType.STRING });

		String strRet = "";
		for (int i = 0; i < (checkedData == null ? 0 : checkedData.size()); i++) {
			Vector rowData = (Vector) checkedData.get(i);
			// XXX:��һ������Ϊ�к�

			// ���ù������Ƿ�Ϊ�޵�
			String strActiontype = String.valueOf(rowData.get(8));
			boolean isMakebill = false;
			if (WorkflownoteVO.WORKITEM_TYPE_MAKEBILL
					.equalsIgnoreCase(strActiontype))
				isMakebill = true;

			// ��������״��
			int status = ((Integer) rowData.get(5)).intValue();
			String strStatus = WFTask.resolveApproveStatus(status, isMakebill);

			// ����������
			String strResultI18N = WFTask
					.resolveApproveResult(isMakebill ? null : rowData.get(6));

			// Ϊ���ҽ�����þ��� lj@2005-6-2
			UFDouble localMoney = (UFDouble) rowData.get(4);
			UFDouble newLM = null;
			if (localMoney != null)
				newLM = new UFDouble(localMoney.getDouble(), 2);

			// �����������ڼ�����ʱ
			String strSendTime = String.valueOf(rowData.get(7));
			String strApproveTime = String.valueOf(rowData.get(1));
			// UFDateTime serverTime = ClientEnvironment.getServerTime();
			// String ellapsed = DurationUnit.getElapsedTime(new
			// UFDateTime(strSendTime), new UFDateTime(strApproveTime));

			// �ֱ�Ϊ���������ڡ������ˡ�����״�����������������
			String strTemp = StringUtil.replaceAllString(strHistory, "#DATE#",
					String.valueOf(rowData.get(1)));
			strTemp = StringUtil.replaceAllString(strTemp, "#CHECKMAN#", String
					.valueOf(rowData.get(3)));
			strTemp = StringUtil.replaceAllString(strTemp, "#STATUS#",
					strStatus);
			strTemp = StringUtil.replaceAllString(strTemp, "#RESULT#",
					strResultI18N);
			Object noteObj = rowData.get(2);
			strTemp = StringUtil.replaceAllString(strTemp, "#CHECKNOTE#",
					noteObj == null ? "" : String.valueOf(noteObj));
			strRet += strTemp + "\n";
		}
		return strRet;
	}

	/**
	 * ����HTML�ʼ����ݣ����ɴ�����
	 * 
	 * @param subject
	 * @param recEmails
	 * @param htmlContent
	 * @param fileName
	 * @throws BusinessException
	 */
	public static void sendHtmlEmail(String subject, String[] recEmails,
			String htmlContent, String fileName) throws BusinessException {
		Logger.info("==================");
		Logger.info(">> ����HTML�ʼ�PfUtilTools.sendHtmlEmail() called");
		Logger.info("==================");

		SysMessageParam smp = WirelessManager.fetchSysMsgParam();
		if (smp == null)
			throw new MailException("����1��û����ȷ�����ʼ��������ļ�/ierp/bin/message4pf.xml");
		DefaultSMTP defaultSmtp = smp.getSmtp();
		if (defaultSmtp == null)
			throw new MailException("����2��û����ȷ�����ʼ��������ļ�/ierp/bin/message4pf.xml");

		String smtpHost = defaultSmtp.getSmtp();
		String fromAddr = defaultSmtp.getSender();
		String userName = defaultSmtp.getUser();
		String password = defaultSmtp.getPassword();
		String senderName = defaultSmtp.getSenderName();

		if (password != null)
			password = new Encode().decode(password);

		// XXX::���յ�ַ������","�ָ�
		String receivers = "";
		for (int i = 0; i < recEmails.length; i++)
			receivers += recEmails[i] + ",";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append(htmlContent);
			Logger.info("�ʼ�������receivers=" + receivers);
			MailTool.sendHtmlEmail(smtpHost, fromAddr, senderName, userName,
					password, receivers, subject, sb, fileName);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("���󣺷���Emailʧ�ܣ�" + e.getMessage());
		}

	}

	/**
	 * �����ʼ��Ĺ��߷�������EJB����
	 * <li>ֻ�ɺ�̨����
	 * <li>�ʼ���������Ϣ������message4pf.xml�ļ���
	 * 
	 * @param sender
	 *            �������ʼ���ַ�����Ϊ�գ���ȡmessage4pf.xml�е�������Ϣ
	 * @param title
	 *            �ʼ�����
	 * @param content
	 *            �ʼ�����
	 * @param recEmails
	 *            �������ʼ���ַ
	 * @param ccEmails
	 *            �������ʼ���ַ
	 * @param attachFiles
	 *            �����ļ�·��
	 * @throws MailException
	 */
	public static void sendEmail(String sender, String title, String content,
			String[] recEmails, String[] ccEmails, String[] attachFiles)
			throws MailException {
		Logger.info("==================");
		Logger.info(">> �����ʼ�PfUtilTools.sendEmail() called");
		Logger.info("==================");
		if (recEmails == null || recEmails.length == 0)
			return;

		SysMessageParam smp = WirelessManager.fetchSysMsgParam();
		if (smp == null)
			throw new MailException("����1��û����ȷ�����ʼ��������ļ�/ierp/bin/message4pf.xml");
		DefaultSMTP defaultSmtp = smp.getSmtp();
		if (defaultSmtp == null)
			throw new MailException("����2��û����ȷ�����ʼ��������ļ�/ierp/bin/message4pf.xml");

		String smtpHost = defaultSmtp.getSmtp();
		String fromAddr = sender;
		String userName = defaultSmtp.getUser();
		String password = defaultSmtp.getPassword();
		String senderName = defaultSmtp.getSenderName();

//		if (password != null)
//			password = new Encode().decode(password);
		if (sender == null || sender.length() == 0)
			fromAddr = defaultSmtp.getSender();

		// XXX:���յ�ַ������","�ָ�
		String receivers = "";
		for (int i = 0; i < recEmails.length; i++)
			receivers += recEmails[i] + ",";
		String ccs = "";
		for (int i = 0; i < (ccEmails == null ? 0 : ccEmails.length); i++)
			ccs += ccEmails[i] + ",";

		try {
			Logger.info("�ʼ�������receivers=" + receivers);
			MailTool.sendMailWithAttach(smtpHost, fromAddr, senderName,
					userName, password, receivers, ccs, title, content,
					attachFiles);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new MailException("���󣺷���Emailʧ�ܣ�" + e.getMessage());
		}
	}
}