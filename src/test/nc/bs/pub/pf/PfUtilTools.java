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
 * 平台后台工具类
 * 
 * @author 樊冠军 2002-4-12 12:29:25
 * @modifier 雷军 2004-03-09 增加解析单据动作约束不满足的信息提示
 * @modifier 雷军 2005-8-9 日志API统一使用UAP日志系统
 * @modifier leijun 2006-4-29 BOOLEAN类型也要支持右值判定，如果无右值，则直接计算左值。
 * @modifier leijun 2008-12 返回String类型的单据函数支持更多比较运算符：not like,in,not in
 */
public class PfUtilTools extends PfUtilBaseTools {

	public static final String NEEDDISPATCH = "NEEDDISPATCH";

	/**
	 * 实例化与某单据类型相关联的类
	 * <li>前提：从单据类型可获取类所在的模块名
	 * 
	 * @param pkBilltype
	 *            单据类型PK
	 * @param checkClsName
	 *            类名
	 * @return 类实例
	 * @throws BusinessException
	 */
	public static Object instantizeObject(String pkBilltype, String checkClsName)
			throws BusinessException {
		// WARN::该接口的实现使用了后台缓存
		IPFMetaModel pfmeta = (IPFMetaModel) NCLocator.getInstance().lookup(
				IPFMetaModel.class.getName());
		String strModule = pfmeta.queryModuleOfBilltype(pkBilltype);
		if (StringUtil.isEmptyWithTrim(strModule))
			throw new PFBusinessException("该单据类型" + pkBilltype + "没有定义所属的模块");

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
	 * 从人员档案中获取操作员的手机信息，用于短信发送
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
	 * 获取注册在bd_billtype.checkclassname中类的实例
	 * <li>只可后台调用
	 * 
	 * @param billType
	 *            单据类型（或交易类型）PK
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
	 * 获得单据类型中注册的业务实现类的实例
	 * 
	 * @param billType
	 *            单据类型PK
	 * @param clzName
	 *            业务类名称
	 * @return
	 * @throws BusinessException
	 */
	public static Object findBizImplOfBilltype(String billType, String clzName)
			throws BusinessException {
		if (StringUtil.isEmptyWithTrim(clzName))
			throw new PFBusinessException("流程平台：单据类型" + billType + "注册的业务类名称为空");
		return instantizeObject(billType, clzName.trim());
	}

	/**
	 * 实例化两个单据的后台VO交换类
	 * 
	 * @param srcBillType
	 *            源单据类型（或交易类型）PK
	 * @param destBillType
	 *            目的单据类型（或交易类型）PK
	 * @return 后台交换类的实例
	 * @throws BusinessException
	 */
	private static Object findBSChangeScriptClass(String srcBillType,
			String destBillType) throws BusinessException {
		Logger.debug("查询BS交换类开始......");

		Object changeImpl = null;
		// XXX:leijun 2009-10 只找两级：当前级、单据类型级
		String strClassNameNoPackage = "CHG" + srcBillType + "TO"
				+ destBillType;
		String fullyQualifiedClassName = m_strBackChangeClassPack + "."
				+ strClassNameNoPackage;
		try {
			Logger.debug("尝试实例化类=" + fullyQualifiedClassName);
			changeImpl = newVoMappingObject(srcBillType, destBillType,
					fullyQualifiedClassName);
			Logger.debug("查询BS交换类结束...." + changeImpl);
			return changeImpl;
		} catch (Exception e) {
			if (isTranstype(srcBillType) || isTranstype(destBillType)) {
				// XXX:出现异常，则继续查找单据类型之间的交换类实例
				Logger.warn("警告：无法实例化类=" + fullyQualifiedClassName + "，但继续查找",
						e);
				strClassNameNoPackage = "CHG" + getRealBilltype(srcBillType)
						+ "TO" + getRealBilltype(destBillType);
				fullyQualifiedClassName = m_strBackChangeClassPack + "."
						+ strClassNameNoPackage;
				try {
					Logger.debug("尝试实例化类2=" + fullyQualifiedClassName);
					changeImpl = newVoMappingObject(srcBillType, destBillType,
							fullyQualifiedClassName);
					Logger.debug("查询BS交换类2结束...." + changeImpl);
					return changeImpl;
				} catch (Exception ex) {
					Logger.error(e.getMessage(), e);
					throw new PFBusinessException("无法获得" + srcBillType + "到"
							+ destBillType + "的后台VO交换类实例，异常" + ex.getMessage());
				}
			} else {
				Logger.error(e.getMessage(), e);
				throw new PFBusinessException("无法获得" + srcBillType + "到"
						+ destBillType + "的后台VO交换类实例，异常" + e.getMessage());
			}
		}
	}

	/**
	 * 获得指定类明的数组类定义
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
	 * 获得指定类明的类定义
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
	 * 返回当前使用数据源名称
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
	 * 计算宏表达式的值 <BR>
	 * 注意：目前 仅支持六种表达式。
	 * 
	 * @param macro
	 *            String 宏表达式，带有宏标记
	 * @param type
	 *            String 表达式类型
	 * @param leftValue
	 *            Object 左值
	 * @param rightValue
	 *            Object 右值
	 * @return String
	 * @author 雷军
	 */
	private static String getExpressValue(String macro, String type,
			Object leftValue, Object rightValue) {
		// 目前所有支持的宏表达式
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
	 * 功能：判断关系式是否成功。 类型Type;字符型"STRING"、整型“INTEGER“、浮点型"DOUBLE"、布尔型"BOOLEAN"
	 * 字符型运算符为:等于"=",包含于"like" 整型运算符为: 等于"=",大于等于">=",小于等于" <=",不等于"!="
	 * 大于">",小于" <" 浮点型运算符为:等于"=",大于等于">=",小于等于" <=",不等于"!=" 大于">",小于" <"
	 * 布尔型BOOLEAN运算符为:等于"=".
	 * 
	 * @param InObject
	 *            左值
	 * @param objType
	 *            左值类型，仅支持"BOOLEAN","STRING","INTEGER","DOUBLE"
	 * @param opType
	 *            比较符
	 * @param value
	 *            右值
	 * @return
	 * @throws BusinessException
	 * @modifier leijun 2007-9-1 左值类型支持"VOID"
	 */
	public static boolean isTrueOrNot(Object InObject, String objType,
			String opType, String value) throws BusinessException {
		Logger.debug("****比较类型:" + objType + "比较类型运算符:" + opType + "****");

		if (PfDataTypes.VOID.getTag().equals(objType))
			// XXX:如果左值函数返回值为"VOID"，则永真
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
	 * 判断Double型的真假
	 * 
	 * @param InObject
	 *            左值
	 * @param opType
	 *            比较运算符
	 * @param value
	 *            右值
	 * @return
	 */
	private static boolean compareDouble(UFDouble InObject, String opType,
			String value) {
		// 判断Double型的真假
		if (InObject == null) {
			InObject = new UFDouble(0);
		}
		if (value == null) {
			value = "0";
		}
		// WARN::V5前的版本中函数表达式运算符为"="，V5修改为"=="
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
	 * 判断Integer型的真假
	 * 
	 * @param InObject
	 *            左值
	 * @param opType
	 *            比较运算符
	 * @param value
	 *            右值
	 * @return
	 */
	private static boolean compareInteger(Integer InObject, String opType,
			String value) {
		Integer typeInteger;
		// 判断Integer型的真假
		if (InObject == null) {
			InObject = new Integer(0);
		}
		if (value == null) {
			value = "0";
		}
		typeInteger = (Integer) InObject;
		// WARN::V5前的版本中函数表达式运算符为"="，V5修改为"=="
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
	 * 判断String型的真假
	 * 
	 * @param strLeftValue
	 *            左值
	 * @param opType
	 *            比较运算符
	 * @param strRightValue
	 *            右值
	 * @return
	 */
	private static boolean compareString(String strLeftValue, String opType,
			String strRightValue) {
		// WARN::V5前的版本中函数表达式运算符为"="，V5修改为"=="
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
	 * 判断boolean型的真假
	 * 
	 * @param InObject
	 *            左值
	 * @param opType
	 *            比较运算符
	 * @param value
	 *            右值
	 * @return
	 */
	private static boolean compareBoolean(UFBoolean InObject, String opType,
			String value) {
		// //lj@2006-4-29 BOOLEAN类型也要支持右值判定

		// WARN::V5前的版本中函数表达式运算符为"="，V5修改为"=="
		// if (PfOperatorTypes.EQ.toString().equals(opType)) {
		if (PfOperatorTypes.EQ.toString().equals(opType) || "=".equals(opType)) {
			if (InObject.equals(UFBoolean.valueOf(value)))
				return true;
			else
				return false;
		} else
			// 如果没有比较符，则直接计算左值
			return InObject.booleanValue();
	}

	/**
	 * 执行函数并返回结果
	 * 
	 * @param functionNote
	 *            函数方法说明
	 * @param className
	 *            类名
	 * @param method
	 *            方法名
	 * @param parameter
	 *            参数
	 * @param paraVo
	 *            工作流上下文参数
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

		Logger.debug("parseFunction解析函数类名:" + className + "方法名:" + method
				+ "参数:" + parameter + "开始");
		if (className == null) {
			errString = "parseFunction无类名，无法运行函数";
			throw new PFBusinessException(errString);
		} else if (method == null) {
			errString = "parseFunction无方法名，无法运行函数";
			throw new PFBusinessException(errString);
		}
		// 执行类的方法
		checkObject = runClass(className, method, parameter, paraVo, null,
				methodReturnHas);
		if (functionNote != null) {
			Logger.debug("函数#" + functionNote + "#运行返回值为："
					+ String.valueOf(checkObject));
		} else {
			Logger.debug("函数运行返回值为：" + String.valueOf(checkObject));
		}
		Logger.debug("parseFunction解析函数类名:" + className + "方法名:" + method
				+ "参数:" + parameter + "****结束");
		return checkObject;
	}

	/**
	 * 解析属性参数
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
			throw new PFBusinessException("注册参数不正确");
		} else {
			try {
				// 通过参数名称前的第一字符标号区分组件返回属性返回COM..组件返回
				fieldName = dealStr.substring(0, retIndex);
				if (fieldName.substring(0, 3).equals("COM")) {
					// FIXME::不知NC50还有何作用？leijun
					paraObjects[arrIndex] = methodReturnHas.get(fieldName
							.substring(3));
				} else if (fieldName.startsWith("OBJ")) {
					// 表示为 用户自定义参数
					if (fieldName.endsWith("ARY")) {
						paraObjects[arrIndex] = paraVo.m_userObjs;
					} else {
						paraObjects[arrIndex] = paraVo.m_userObj;
					}
				} else {
					// 表示为 属性参数（主要是主表的一些数据）
					paraObjects[arrIndex] = paraVo.m_standHeadVo
							.getAttributeValue(fieldName);
				}
				datatype = dealStr.substring(retIndex + 1);
				paraClasses[arrIndex] = parseTypeClass(datatype);
			} catch (Exception ex) {
				Logger.error(ex.getMessage(), ex);
				throw new PFBusinessException("未找到注册的类文件", ex);
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
	 * 功能：用于解析语法判断条件
	 * 
	 * @author 雷军 2004-03-09 单据动作约束不满足提示信息通过参数errorMessageBuffer传出
	 * 
	 * @param className
	 *            类名（左）
	 * @param method
	 *            方法名（左）
	 * @param parameter
	 *            参数
	 * @param funNote
	 *            方法说明
	 * @param returnType
	 *            左函数返回值类型
	 * @param ysf
	 *            运算符
	 * @param value
	 *            值
	 * @param className2
	 *            类名（右）
	 * @param method2
	 *            方法名（右）
	 * @param parameter2
	 *            参数
	 * @param funNote2
	 *            方法说明
	 * @param paraVo
	 *            工作流上下文参数
	 * @param methodReturnHas
	 * @param errorMessageBuffer
	 *            返回的消息串
	 * @param originalHintBuffer
	 *            宏消息串
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

		strTmp = "parseSyntax解析语法开始";
		Logger.debug(strTmp);
		if (className == null) {
			// 发送消息
			Logger.debug("parseSyntax解析语法结束");
			return false;
		}

		// 返回变量
		boolean retBool = true;
		// 运行左值函数类
		leftObject = parseFunction(funNote, className, method, parameter,
				paraVo, methodReturnHas);
		if (className2 == null) {
			// 返回的值直接与用户输入比较
			Logger.debug("用户输入的右值为：" + value);
		} else {
			// 运行右值函数类
			rightObject = parseFunction(funNote2, className2, method2,
					parameter2, paraVo, methodReturnHas);
			value = String.valueOf(rightObject);
			Logger.debug("右函数的运行值为:" + value);
		}

		// 判断表达式
		retBool = isTrueOrNot(leftObject, returnType.toUpperCase(), ysf, value);
		if (retBool) {
			Logger.debug("函数运行结果判断成功!");
		} else {
			Logger.debug("函数运行结果判断不成功!");
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

			// added by 雷军 2004-03-09 解析单据动作约束不满足时的信息提示宏表达式
			String MACRO_TAG = "%%";
			String originalHint = "";
			if (originalHintBuffer != null) {
				originalHint = originalHintBuffer.toString();
				String hintAfterParse = translateMacro(originalHint, MACRO_TAG,
						returnType, leftObject, value);
				originalHintBuffer.append(hintAfterParse);
			}
		}

		strTmp = "parseSyntax解析语法结束";
		Logger.debug(strTmp);
		return retBool;
	}

	/**
	 * 运行数据交换类
	 * 
	 * @param sourceBillType
	 *            源单据类型PK
	 * @param destBillType
	 *            目的单据类型PK
	 * @param sourceBillVO
	 *            源单据聚合VO
	 * @return 目的单据聚合VO
	 * @throws BusinessException
	 */
	public static AggregatedValueObject runChangeData(String sourceBillType,
			String destBillType, AggregatedValueObject sourceBillVO)
			throws BusinessException {
		return runChangeData(sourceBillType, destBillType, sourceBillVO, null);
	}

	/**
	 * 运行数据交换类
	 * 
	 * @param sourceBillType
	 *            源单据类型PK
	 * @param destBillType
	 *            目的单据类型PK
	 * @param sourceBillVO
	 *            源单据聚合VO
	 * @param paraVo
	 *            工作流参数VO
	 * @return 目的单据聚合VO
	 * @throws BusinessException
	 */
	public static AggregatedValueObject runChangeData(String sourceBillType,
			String destBillType, AggregatedValueObject sourceBillVO,
			PfParameterVO paraVo) throws BusinessException {

		Logger.debug(">>开始单据VO交换=" + sourceBillType + "到" + destBillType);
		// 返回VO定义
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
				throw new PFBusinessException("后台VO交换类" + changeImpl
						+ "没有继承VOConversion");

			adjustBeforeVoMapping(sourceBillType, destBillType,
					new AggregatedValueObject[] { sourceBillVO });

			// 获取目的VO的定义
			int intChildLen = 0;
			if (sourceBillVO.getChildrenVO() != null) {
				intChildLen = sourceBillVO.getChildrenVO().length;
			}
			AggregatedValueObject destVo = pfInitVoClass(destBillType,
					intChildLen);
			// 接口运行返回后的VO
			destBillVO = ((IchangeVO) changeImpl).retChangeBusiVO(sourceBillVO,
					destVo);

			adjustAfterVoMapping(sourceBillType, destBillType,
					new AggregatedValueObject[] { sourceBillVO },
					new AggregatedValueObject[] { destBillVO });

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException(e.getMessage(), e);
		}

		Logger.debug(">>结束单据VO交换=" + sourceBillType + "到" + destBillType
				+ ",耗时=" + (System.currentTimeMillis() - start) + "ms");
		return destBillVO;
	}

	/**
	 * 环境变量
	 * 
	 * @param tmpVar
	 */
	private static void initConversionEnv(PfParameterVO paraVo,
			VOConversion tmpVar) {
		// 当前登陆日期
		String sysDate = null;
		if (paraVo != null)
			sysDate = paraVo.m_currentDate.substring(0, 10);
		else
			sysDate = new UFDateTime(new Date()).toString().substring(0, 10);
		tmpVar.setSysDate(sysDate);

		// 当前登陆操作员PK
		String sysOperator = null;
		if (paraVo == null || paraVo.m_operator == null) {
			sysOperator = InvocationInfoProxy.getInstance().getUserCode();
		} else
			sysOperator = paraVo.m_operator;
		tmpVar.setSysOperator(sysOperator);

		// 当前登陆的会计年度 WARN::必须保证环境参数VO中包含了当前登陆日期
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
		// 当前登陆公司PK
		tmpVar.setSysCorp(InvocationInfoProxy.getInstance().getCorpCode());
		// 当前系统时间
		tmpVar.setSysTime(new UFDateTime(new Date()).toString());
		// 目的业务流程,如果没有定义,则取源单据的业务流程->直接取空;
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
	 * 运行数据交换类(VO数组)
	 * 
	 * @param sourceBillType
	 *            源单据类型PK
	 * @param destBillType
	 *            目的单据类型PK
	 * @param sourceBillVOs
	 *            源单据聚合VO数组
	 * @return 目的单据聚合VO数组
	 * @throws BusinessException
	 */
	public static AggregatedValueObject[] runChangeDataAry(
			String sourceBillType, String destBillType,
			AggregatedValueObject[] sourceBillVOs) throws BusinessException {

		return runChangeDataAry(sourceBillType, destBillType, sourceBillVOs,
				null);
	}

	/**
	 * 运行数据交换类(VO数组)
	 * 
	 * @param sourceBillType
	 *            源单据类型PK
	 * @param destBillType
	 *            目的单据类型PK
	 * @param sourceBillVOs
	 *            源单据聚合VO数组
	 * @param paraVo
	 *            工作流参数VO，仅用于获取一些系统变量
	 * @return 目的单据聚合VO数组
	 * @throws BusinessException
	 */
	public static AggregatedValueObject[] runChangeDataAry(
			String sourceBillType, String destBillType,
			AggregatedValueObject[] sourceBillVOs, PfParameterVO paraVo)
			throws BusinessException {
		Logger.debug(">>开始单据VO批量交换=" + sourceBillType + "到" + destBillType);
		// 返回VO定义
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

			// 获取目的VO的定义
			AggregatedValueObject[] destVo = pfInitVosClass(destBillType,
					sourceBillVOs);
			// //接口运行返回后的VO
			destBillVOs = ((IchangeVO) changeImpl).retChangeBusiVOs(
					sourceBillVOs, destVo);

			adjustAfterVoMapping(sourceBillType, destBillType, sourceBillVOs,
					destBillVOs);

		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException(ex.getMessage(), ex);
		}
		Logger.debug(">>结束单据VO批量交换=" + sourceBillType + "到" + destBillType);
		return destBillVOs;
	}

	/**
	 * 实例化VO交换类
	 * <li>优先在目的单据类型所属模块 查找类
	 * 
	 * @param sourceBillType
	 *            源单据类型PK
	 * @param destBillType
	 *            目的单据类型PK
	 * @param fullyQualifiedClassName
	 *            VO交换类名
	 * @return VO交换类实例
	 * @throws BusinessException
	 */
	public static Object newVoMappingObject(String sourceBillType,
			String destBillType, String fullyQualifiedClassName)
			throws BusinessException {
		Object changeObj = null;
		IPFMetaModel pfmeta = (IPFMetaModel) NCLocator.getInstance().lookup(
				IPFMetaModel.class.getName());

		// 目的单据类型 或其父 所属模块
		String moduleOfDest = pfmeta.queryModuleOfBilltype(destBillType);
		// 源单据类型所属模块
		String moduleOfSrc = pfmeta.queryModuleOfBilltype(sourceBillType);

		if (moduleOfDest == null || moduleOfDest.trim().length() == 0) {
			if (moduleOfSrc == null || moduleOfSrc.trim().length() == 0)
				throw new PFBusinessException("错误：源或目的单据类型都没有注册模块名");
			else {
				changeObj = NewObjectService.newInstance(moduleOfSrc,
						fullyQualifiedClassName);
				Logger.debug("OK-目的单据没有所属模块，但在来源单据所属模块" + moduleOfSrc
						+ "中找到交换类=" + changeObj);
			}
		} else {
			// 优先在目的单据类型所属模块 查找类
			try {
				changeObj = NewObjectService.newInstance(moduleOfDest,
						fullyQualifiedClassName);
				Logger.debug("OK-在目的单据所属模块" + moduleOfDest + "中找到交换类="
						+ changeObj);
			} catch (Exception e) {
				if (moduleOfSrc == null || moduleOfSrc.trim().length() == 0)
					throw new PFBusinessException(
							"错误：VO交换类不在目的单据类型所属模块中，且来源单据没有注册所属模块");
				else {
					changeObj = NewObjectService.newInstance(moduleOfSrc,
							fullyQualifiedClassName);
					Logger.debug("OK-在目的单据所属模块中找不到交换类，但在来源单据所属模块" + moduleOfSrc
							+ "中找到交换类=" + changeObj);
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
	 * 运行类
	 * 
	 * @param className
	 *            运行的类名称
	 * @param method
	 *            方法名称
	 * @param parameter
	 *            参数
	 * @param paraVo
	 *            工作流参数VO
	 * @param keyHas
	 *            参数值Hash
	 * @param methodReturnHas
	 *            返回值Hash
	 * 
	 * @modifier leijun 2005-6-20 单据类型PK最大限制为4字符,非固定的2个字符
	 */
	public static Object runClass(String className, String method,
			String parameter, PfParameterVO paraVo, Hashtable keyHas,
			Hashtable methodReturnHas) throws BusinessException {
		Logger.debug("**********执行类PfUtilTools.runClass()开始************");

		Logger.debug("执行类:" + className + ";方法:" + method + ";参数:" + parameter);

		long begin = System.currentTimeMillis();

		// /1.解析参数，因为参数是以","分割的
		// String[] paraStrs = nc.vo.pf.pub.PfComm.dealString(parameter, ",");
		StringTokenizer tmpStrToken = new StringTokenizer(parameter, ",");
		String[] paraStrs = new String[tmpStrToken.countTokens()];
		int index = 0;
		while (tmpStrToken.hasMoreTokens())
			paraStrs[index++] = tmpStrToken.nextElement().toString();

		Object[] paraObjects = new Object[paraStrs.length];
		Class[] paraClasses = new Class[paraStrs.length];

		for (int i = 0; i < paraStrs.length; i++) {
			// 遍历参数串
			// 如果含有"."，则表示VO参数
			boolean isVo = paraStrs[i].indexOf(".") > 0 ? true : false;
			int colonPos = paraStrs[i].indexOf(":");

			if (paraStrs[i].startsWith("&")) {
				// 1.如果以"&"开头，则表示运行参数
				String paramKey = paraStrs[i].substring(1, colonPos);
				String strDataType = paraStrs[i].substring(colonPos + 1);
				Object valueObj = keyHas == null ? null : keyHas.get(paramKey);

				if (isVo) {
					// FIXME::不可能走这个分支？leijun
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
				// WARN::直接再次遍历
				continue;
			}

			if (isVo) {
				// 2.参数中包含VO类名
				if (colonPos < 0)
					throw new PFBusinessException("参数注册处VO必须重新注册");
				// 获取单据类型和VO类名 lj@2005-6-17
				String tmpbillType = paraStrs[i].substring(colonPos + 1);
				String voClassName = paraStrs[i].substring(0, colonPos);
				/***************************************************************
				 * 从单据Vo与单据类型对照表中获取单据类型,判断单据类型是否与当前类型相同: 如果相同则不用交换单据VO,否则交换单据VO
				 **************************************************************/
				if (tmpbillType.equals("00")) {
					throw new PFBusinessException("参数注册无标准VO");
				} else if (tmpbillType.equals("01")) {
					// 表示 通用的VO类型
					if (voClassName.endsWith("[]")) {
						paraObjects[i] = paraVo.m_preValueVos; // 参数值
						paraClasses[i] = AggregatedValueObject[].class; // 参数类
					} else {
						paraObjects[i] = paraVo.m_preValueVo; // 参数值
						paraClasses[i] = AggregatedValueObject.class;
					}
				} else {
					// 处理单据类型相同的或同类的单据的不需要进行转换
					// Integer inBillType =
					// PfDataCache.getBillTypeInfo(paraVo.m_billType).getBillstyle();
					// Integer currBillType =
					// PfDataCache.getBillTypeInfo(tmpbillType).getBillstyle();
					// if (tmpbillType.equals(paraVo.m_billType)
					// || (inBillType != null && currBillType != null &&
					// inBillType.equals(currBillType))) {
					if (isSimilarBilltype(paraVo.m_billType, tmpbillType)) {
						if (voClassName.endsWith("[]")) {
							paraObjects[i] = paraVo.m_preValueVos; // 参数值
							paraClasses[i] = getClassNameClass(voClassName
									.substring(0, voClassName.length() - 2),
									paraVo.m_preValueVos.length);
						} else {
							paraObjects[i] = paraVo.m_preValueVo; // 参数值
							paraClasses[i] = getClassByName(voClassName);
						}
					} else {
						// 异种单据类型之间需要进行转换
						if (voClassName.endsWith("[]")) {
							paraObjects[i] = runChangeDataAry(
									paraVo.m_billType, tmpbillType,
									paraVo.m_preValueVos, paraVo); // 参数值
							paraClasses[i] = getClassNameClass(voClassName
									.substring(0, voClassName.length() - 2),
									paraVo.m_preValueVos.length);
						} else {
							paraObjects[i] = runChangeData(paraVo.m_billType,
									tmpbillType, paraVo.m_preValueVo, paraVo); // 参数值
							paraClasses[i] = getClassByName(voClassName);
						}
					}
				}
			} else {
				// 3.参数中不包含VO类名
				parseParmeter(paraStrs[i], paraObjects, paraClasses, i, paraVo,
						methodReturnHas);
			}
		} // /{end for}

		// /2.实例化类，产生对象
		Object tmpObj = null;
		try {
			tmpObj = instantizeObject(paraVo.m_billType, className);
		} catch (Exception e) {
			Logger.warn("在模块中找不到类，则假设为PUBLIC类：" + className, e);
			try {
				// WARN::如果在模块中找不到类，则假设为PUBLIC类
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
			throw new PFBusinessException("无法实例化类：" + className);

		// /3.执行方法
		Object retObj = null;
		try {
			Class c = tmpObj.getClass();
			// 获取方法
			Method cm = c.getMethod(method, paraClasses);

			// fangj 2001-11-08返回为VOID的判断
			// 进行线程同步，保证事务在同一线程内运行
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
			// WARN::需要把反射调用中的异常抛出来!
			Throwable expt = e.getTargetException();
			Logger.error(e.getMessage(), e);
			if (expt instanceof BusinessException)
				// 业务异常直接抛出！
				throw (BusinessException) expt;
			else if (expt instanceof RemoteException
					&& expt.getCause() instanceof BusinessException) {
				throw (BusinessException) expt.getCause();
			} else
				throw new PFBusinessException(expt.getMessage(), expt);
		}

		long end = System.currentTimeMillis();
		Logger.debug("************执行类PfUtilTools.runClass()结束,耗时="
				+ (end - begin) + "ms************");
		return retObj;
	}

	/**
	 * 判定两个单据类型（或交易类型）是否为同源关系
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
			// a为交易类型
			BilltypeVO aParentTypeVO = PfDataCache.getBillTypeInfo(aTypeVO
					.getParentbilltype());
			Integer aParentStyle = aParentTypeVO.getBillstyle();
			if (bTypeVO.getIstransaction() != null
					&& bTypeVO.getIstransaction().booleanValue()) {
				// b为交易类型
				BilltypeVO bParentTypeVO = PfDataCache.getBillTypeInfo(bTypeVO
						.getParentbilltype());
				Integer bParentStyle = bParentTypeVO.getBillstyle();
				isSimilar = aTypeCode.equals(bTypeCode)
						|| aParentTypeVO.getPk_billtypecode().equals(
								bParentTypeVO.getPk_billtypecode())
						|| (aParentStyle != null && bParentStyle != null && aParentStyle
								.equals(bParentStyle));
			} else {
				// b为单据类型
				Integer bStyle = bTypeVO.getBillstyle();
				isSimilar = aParentTypeVO.getPk_billtypecode()
						.equals(bTypeCode)
						|| (aParentStyle != null && bStyle != null && aParentStyle
								.equals(bStyle));
			}
		} else {
			// a为单据类型
			Integer aStyle = aTypeVO.getBillstyle();
			if (bTypeVO.getIstransaction() != null
					&& bTypeVO.getIstransaction().booleanValue()) {
				// b为交易类型
				BilltypeVO bParentTypeVO = PfDataCache.getBillTypeInfo(bTypeVO
						.getParentbilltype());
				Integer bParentStyle = bParentTypeVO.getBillstyle();
				isSimilar = bParentTypeVO.getPk_billtypecode()
						.equals(aTypeCode)
						|| (bParentStyle != null && aStyle != null && bParentStyle
								.equals(aStyle));
			} else {
				// b为单据类型
				Integer bStyle = bTypeVO.getBillstyle();
				isSimilar = (bTypeCode.equals(bTypeCode) || (aStyle != null
						&& bStyle != null && aStyle.equals(bStyle)));
			}
		}

		return isSimilar;
	}

	/**
	 * 宏替换，比如：“你好，审批%%e.getName%%通过了” WARN::宏替换前最好先检查一下宏的起点和终点是否匹配，
	 * 也就是说是否含有偶数个宏标记
	 * 
	 * @author 雷军
	 */
	protected static String translateMacro(String content, String macro_tag,
			String type, Object leftValue, Object rightValue) {
		int pos_b = 0; // 起点
		int pos_e = 0; // 终点
		int offset = 0;
		boolean bFound = false;
		boolean isMatch = false;
		StringBuffer buffer = new StringBuffer();

		do {
			pos_e = content.indexOf(macro_tag, pos_b);
			bFound = pos_e == -1 ? false : true;
			offset = bFound ? macro_tag.length() : 0;
			if (bFound) {
				// 还没有匹配两个"%% %%"号
				if (isMatch) {
					// 替换宏"%%macro%%"中的内容
					String macro = content.substring(pos_b, pos_e);
					// System.out.println("macro = " + express);
					// ExpressParser parser = new ExpressParser(context,
					// express);
					buffer.append(getExpressValue(macro, type, leftValue,
							rightValue));
				} else {
					// 加入在奇数位“%%"号之前的字符串
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
	 * 判断是否增加VO。返回为true 表示该当前数据与该操作人或组相关，返回false，则不相关 算法： 1.操作人或角色无关，则返回该记录。
	 * 2.操作人有关，则返回跟该操作人有关的记录。 3.角色有关，则返回该角色相关的记录
	 * 
	 * @param pkCorp
	 *            公司PK
	 * @param currUserPK
	 *            当前操作的用户
	 * @param configflag
	 *            用户或角色相关
	 * @param configedOperator
	 *            配置的用户或角色PK
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isContinue(String pkCorp, String currUserPK,
			int configflag, String configedOperator) throws BusinessException {
		switch (configflag) {
		case IPFConfigInfo.UserRelation:
			// 判断当前事件是否与操作人有关
			if (configedOperator.equals(currUserPK))
				return true;
			return false;
		case IPFConfigInfo.RoleRelation: {
			// 查找当前用户所属的角色
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
	 * 将本次编译的动作脚本Java文件和Class文件集群化
	 * 
	 * @param relativePath
	 *            相对于NCHOME的路径
	 * @param fileName
	 *            文件名，可能带有目录
	 */
	public static void clusterFile(String relativePath, String fileName,
			String module) {
		Logger.debug(">>流程平台集群消息发送开始");
		// Logger.debug(">>relativePath=" + relativePath + ";fileName=" +
		// fileName);
		String strFile = RuntimeEnv.getInstance().getNCHome() + relativePath
				+ fileName;
		File file = new File(strFile);
		if (!file.exists()) {
			Logger.warn(">>文件=" + strFile + "并不存在，无法集群化文件。");
			return;
		}

		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes);
			BytesClusterMessage message = new BytesClusterMessage();
			// 进行广播的文件类型的信息
			ClusterMessageHeader header = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_FILETYPE, "PF");
			message.addHeader(header);
			// 进行文件名称Head信息
			ClusterMessageHeader header2 = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_FILENAME, fileName);
			message.addHeader(header2);
			// 进行文件时间戳的head信息
			ClusterMessageHeader header3 = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_TS, String.valueOf(file.lastModified()));
			message.addHeader(header3);
			// 文件相对于NCHOME的路径
			ClusterMessageHeader header4 = new ClusterMessageHeader(
					CLUSTER_MSG_HEADER_PATH, relativePath);
			message.addHeader(header4);

			// lj+2007-1-12 增加模块名
			if (module != null) {
				// NOTE::只有Class文件才有这个属性需要传递
				ClusterMessageHeader header5 = new ClusterMessageHeader(
						CLUSTER_MSG_HEADER_MODULE, module);
				message.addHeader(header5);
			}

			// add Message
			message.setBytes(bytes);
			ClusterSender sender = (ClusterSender) NCLocator.getInstance()
					.lookup(ClusterSender.class.getName());
			sender.sendMessage(message);

			Logger.debug(">>流程平台集群消息发送结束=" + strFile);

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
	 * 每个Servlet都需要注册数据源信息才可调用DMO
	 * 
	 * @param dsName
	 */
	public static void regDataSourceForServlet(String dsName)
			throws BusinessException {
		// 1.校验
		try {
			IConfigFileService iAccount = (IConfigFileService) NCLocator
					.getInstance().lookup(IConfigFileService.class.getName());

			String[] dataSources = iAccount.findDatasource();
			if (dataSources == null)
				throw new PFBusinessException("系统异常，请找管理员");
			boolean bValid = Arrays.asList(dataSources).contains(dsName);
			if (!bValid)
				throw new PFBusinessException("错误：无效数据源");
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("系统异常，请找管理员");
		}

		// 2.注册
		InvocationInfoProxy.getInstance().setUserDataSource(dsName);
	}

	/**
	 * 后台审批某张单据
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

		// 1.获得单据聚合VO
		IPFConfig bsConfig = (IPFConfig) NCLocator.getInstance().lookup(
				IPFConfig.class.getName());
		AggregatedValueObject billVo = bsConfig.queryBillDataVO(billType,
				billId);
		if (billVo == null)
			throw new PFBusinessException("错误：根据单据类型和单据ID获取不到单据聚合VO");

		// 2.获得工作项并设置审批意见
		java.util.Date date = new java.util.Date();
		String currentDate = DateUtilities.getInstance().formatJustDay(date);
		IWorkflowMachine bsWorkflow = (IWorkflowMachine) NCLocator
				.getInstance().lookup(IWorkflowMachine.class.getName());
		PfUtilWorkFlowVO wfVO = bsWorkflow.checkWorkFlow(IPFActionName.APPROVE
				+ checkman, billType, currentDate, billVo, null);
		if (wfVO != null) {
			/* 从单据聚合VO中获得币种、金额数据 */
			CurrencyInfo cinfo = new CurrencyInfo();
			PfUtilBaseTools.fetchMoneyInfo(billVo, cinfo, billType);
			wfVO.putMoney(billId, cinfo);

			wfVO.setCheckNote(checkNote);
			// 获取审批结果-通过/不通过/驳回
			if ("Y".equalsIgnoreCase(checkResult)) {
				wfVO.setIsCheckPass(true);
			} else if ("N".equalsIgnoreCase(checkResult)) {
				wfVO.setIsCheckPass(false);
			} else if ("R".equalsIgnoreCase(checkResult)) {
				wfVO.getTaskInfo().getTask().setTaskType(WFTask.TYPE_BACKWARD);
				wfVO.getTaskInfo().getTask().setBackToFirstActivity(true);
			} else
				return "错误：消息格式不对";

			// 获取处理时间
			wfVO.setDealDate(new UFDateTime(date));

			// 指派信息
			if (dispatched_ids != null && dispatched_ids.length > 0) {
				// 分离活动与其指派的参与者
				HashMap hm = new HashMap();
				for (int i = 0; i < dispatched_ids.length; i++) {
					int index = dispatched_ids[i].indexOf("#");
					String userid = dispatched_ids[i].substring(0, index);
					String actDefid = dispatched_ids[i].substring(index + 1);
					if (hm.get(actDefid) == null)
						hm.put(actDefid, new HashSet());
					((HashSet) hm.get(actDefid)).add(userid);
				}
				// 填写到活动的指派信息中
				Vector vecDispatch = wfVO.getTaskInfo().getAssignableInfos();
				for (int i = 0; i < vecDispatch.size(); i++) {
					AssignableInfo ai = (AssignableInfo) vecDispatch.get(i);
					HashSet hs = (HashSet) hm.get(ai.getActivityDefId());
					if (hs != null) {
						// XXX:要避免添加重复的指派用户PK
						for (Iterator iter = hs.iterator(); iter.hasNext();) {
							String userId = (String) iter.next();
							if (!ai.getAssignedOperatorPKs().contains(userId))
								ai.getAssignedOperatorPKs().add(userId);
						}
					}
				}
			}
		}

		// 3.执行审批动作
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
	 * 发送具有审批功能的HTML邮件，单据信息可有可无
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

		// 发送具有审批功能的HTML邮件，单据信息可有可无
		String subject = MessageVO.getMessageNoteAfterI18N(topic);
		// subject += "(可打开附件进行审批)";
		subject = getEncodedString(subject);

		String htmlContentNoApprove = mailTemplateHasBillWithoutApprove(
				billHtml, billId);
		String htmlContentWithApprove = mailTemplateHasBillWithApprove(
				billHtml, checkman, billtype, billId, assignInfos);

		// 产生临时附件
		String fileName = RuntimeEnv.getInstance().getNCHome()
				+ "/webapps/nc_web/pf/" + billId + ".html";
		try {
			writeTempFile(fileName, htmlContentWithApprove);
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
		// 发送无审批功能的邮件，并带上审批附件
		sendHtmlEmail(subject, new String[] { email }, htmlContentNoApprove,
				fileName);

		// 删除临时邮件附件文件
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
	 * 发送无审批功能的邮件，单据信息可有可无
	 * <li>如果有单据信息，则为HTML邮件；
	 * <li>如果无单据信息，则为文本邮件
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
			// 发送简单格式的邮件，无审批功能
			sendEmail(null, subject, "请及时登录处理!", strEmails, null, null);
		} else {
			// 发送HTML单据内容格式的邮件，无审批功能
			sendHtmlEmail(subject, strEmails,
					mailTemplateHasBillWithoutApprove(billHtml, billId), null);
		}
	}

	/**
	 * 解释模板HTML文件，替换其中的单据信息
	 * <li>无审批功能
	 * 
	 * @param billHtml
	 * @param billId
	 * @return
	 * @throws BusinessException
	 */
	private static String mailTemplateHasBillWithoutApprove(String billHtml,
			String billId) throws BusinessException {
		Logger.debug("mailTemplateHasBillWithoutApprove() called");
		// 从后台文件读取邮件模板
		String templateURL = RuntimeEnv.getInstance().getNCHome()
				+ "/webapps/nc_web/pf";
		File f = new File(templateURL + "/mail_template.htm");
		String strFile = null;
		try {
			strFile = IOUtil.toString(new BufferedReader(new FileReader(f)));
		} catch (FileNotFoundException e) {
			throw new PFBusinessException(">>错误：找不到webapps/nc_web/下的邮件模板文件");
		} catch (IOException e) {
			throw new PFBusinessException(e.getMessage(), e);
		}

		if (StringUtil.isEmptyWithTrim(strFile))
			return ">>错误：读取webapps/nc_web/下的邮件模板文件为空";

		// XXX::获得Web服务器地址
		String webUrl = ServerConfiguration.getServerConfiguration()
				.getDefWebServerURL();
		String pf_web_url = webUrl + "/pf";

		// 替换所有图片的URL
		strFile = StringUtil.replaceAllString(strFile, "#PF_WEB_URL#",
				pf_web_url);

		// 替换单据信息
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

		// 替换历史审批信息
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
	 * 解释模板HTML文件，替换其中的单据信息以及审批信息
	 * <li>有审批功能
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
		// 从后台文件读取邮件模板
		String templateURL = RuntimeEnv.getInstance().getNCHome()
				+ "/webapps/nc_web/pf";
		String fileName = null;
		// XXX:判断邮件审批需要数字签名
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
			throw new PFBusinessException(">>错误：找不到webapps/nc_web/下的邮件模板文件");
		} catch (IOException e) {
			throw new PFBusinessException(e.getMessage(), e);
		}

		if (StringUtil.isEmptyWithTrim(strFile))
			return ">>错误：读取webapps/nc_web/下的邮件模板文件为空";

		// XXX::获得Web服务器地址
		String webUrl = ServerConfiguration.getServerConfiguration()
				.getDefWebServerURL();
		String pf_web_url = webUrl + "/pf";
		// 替换所有图片的URL
		strFile = StringUtil.replaceAllString(strFile, "#PF_WEB_URL#",
				pf_web_url);

		// 替换单据信息
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

		// 替换表单的action
		String strActionServlet = webUrl + "/service/mailapproveserv";
		strFile = StringUtil.replaceAllString(strFile, "#ACTIONSERVLET_URL#",
				strActionServlet);

		String needDispatch = NEEDDISPATCH;
		if (assignInfos == null || assignInfos.size() == 0) {
			needDispatch = "NONEEDdISPATCH";
		}

		// 替换隐藏域
		// 将uid/pwd/billid/billtype/ds作为5个隐藏字段
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

		// 替换历史审批信息
		String tagHistoryBegin = "<!--APPROVE_HISTORY_BEGIN-->";
		String tagHistoryEnd = "<!--APPROVE_HISTORY_END-->";
		index = strFile.indexOf(tagHistoryBegin);
		int indexEnd = strFile.indexOf(tagHistoryEnd);
		String strHistory = strFile.substring(index + tagHistoryBegin.length(),
				indexEnd);
		strFile = strFile.substring(0, index + tagHistoryBegin.length())
				+ getNoteOfBill(strHistory, billId)
				+ strFile.substring(indexEnd);

		// 替换指派信息
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
	 * 查询该单据的历史审批信息，并拼凑到HTML中
	 * 
	 * @param strHistory
	 * @param billId
	 * @return
	 * @throws BusinessException
	 */
	private static String getNoteOfBill(String strHistory, String billId)
			throws BusinessException {
		// 查询该单据的历史审批信息
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
			// XXX:第一列数据为行号

			// 检查该工作项是否为修单
			String strActiontype = String.valueOf(rowData.get(8));
			boolean isMakebill = false;
			if (WorkflownoteVO.WORKITEM_TYPE_MAKEBILL
					.equalsIgnoreCase(strActiontype))
				isMakebill = true;

			// 解析审批状况
			int status = ((Integer) rowData.get(5)).intValue();
			String strStatus = WFTask.resolveApproveStatus(status, isMakebill);

			// 解析审核意见
			String strResultI18N = WFTask
					.resolveApproveResult(isMakebill ? null : rowData.get(6));

			// 为本币金额设置精度 lj@2005-6-2
			UFDouble localMoney = (UFDouble) rowData.get(4);
			UFDouble newLM = null;
			if (localMoney != null)
				newLM = new UFDouble(localMoney.getDouble(), 2);

			// 根据审批日期计算历时
			String strSendTime = String.valueOf(rowData.get(7));
			String strApproveTime = String.valueOf(rowData.get(1));
			// UFDateTime serverTime = ClientEnvironment.getServerTime();
			// String ellapsed = DurationUnit.getElapsedTime(new
			// UFDateTime(strSendTime), new UFDateTime(strApproveTime));

			// 分别为：审批日期、审批人、审批状况、审批意见、批语
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
	 * 发送HTML邮件内容，并可带附件
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
		Logger.info(">> 发送HTML邮件PfUtilTools.sendHtmlEmail() called");
		Logger.info("==================");

		SysMessageParam smp = WirelessManager.fetchSysMsgParam();
		if (smp == null)
			throw new MailException("错误1：没有正确配置邮件服务器文件/ierp/bin/message4pf.xml");
		DefaultSMTP defaultSmtp = smp.getSmtp();
		if (defaultSmtp == null)
			throw new MailException("错误2：没有正确配置邮件服务器文件/ierp/bin/message4pf.xml");

		String smtpHost = defaultSmtp.getSmtp();
		String fromAddr = defaultSmtp.getSender();
		String userName = defaultSmtp.getUser();
		String password = defaultSmtp.getPassword();
		String senderName = defaultSmtp.getSenderName();

		if (password != null)
			password = new Encode().decode(password);

		// XXX::接收地址必须以","分隔
		String receivers = "";
		for (int i = 0; i < recEmails.length; i++)
			receivers += recEmails[i] + ",";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append(htmlContent);
			Logger.info("邮件接收者receivers=" + receivers);
			MailTool.sendHtmlEmail(smtpHost, fromAddr, senderName, userName,
					password, receivers, subject, sb, fileName);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new PFBusinessException("错误：发送Email失败：" + e.getMessage());
		}

	}

	/**
	 * 发送邮件的工具方法，非EJB调用
	 * <li>只可后台调用
	 * <li>邮件服务器信息配置在message4pf.xml文件中
	 * 
	 * @param sender
	 *            发送人邮件地址，如果为空，则取message4pf.xml中的配置信息
	 * @param title
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param recEmails
	 *            接收人邮件地址
	 * @param ccEmails
	 *            抄送人邮件地址
	 * @param attachFiles
	 *            附件文件路径
	 * @throws MailException
	 */
	public static void sendEmail(String sender, String title, String content,
			String[] recEmails, String[] ccEmails, String[] attachFiles)
			throws MailException {
		Logger.info("==================");
		Logger.info(">> 发送邮件PfUtilTools.sendEmail() called");
		Logger.info("==================");
		if (recEmails == null || recEmails.length == 0)
			return;

		SysMessageParam smp = WirelessManager.fetchSysMsgParam();
		if (smp == null)
			throw new MailException("错误1：没有正确配置邮件服务器文件/ierp/bin/message4pf.xml");
		DefaultSMTP defaultSmtp = smp.getSmtp();
		if (defaultSmtp == null)
			throw new MailException("错误2：没有正确配置邮件服务器文件/ierp/bin/message4pf.xml");

		String smtpHost = defaultSmtp.getSmtp();
		String fromAddr = sender;
		String userName = defaultSmtp.getUser();
		String password = defaultSmtp.getPassword();
		String senderName = defaultSmtp.getSenderName();

//		if (password != null)
//			password = new Encode().decode(password);
		if (sender == null || sender.length() == 0)
			fromAddr = defaultSmtp.getSender();

		// XXX:接收地址必须以","分隔
		String receivers = "";
		for (int i = 0; i < recEmails.length; i++)
			receivers += recEmails[i] + ",";
		String ccs = "";
		for (int i = 0; i < (ccEmails == null ? 0 : ccEmails.length); i++)
			ccs += ccEmails[i] + ",";

		try {
			Logger.info("邮件接收者receivers=" + receivers);
			MailTool.sendMailWithAttach(smtpHost, fromAddr, senderName,
					userName, password, receivers, ccs, title, content,
					attachFiles);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new MailException("错误：发送Email失败：" + e.getMessage());
		}
	}
}