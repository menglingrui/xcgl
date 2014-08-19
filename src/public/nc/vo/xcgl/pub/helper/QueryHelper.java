package nc.vo.xcgl.pub.helper;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.vo.pub.BusinessException;
import nc.vo.xcgl.pub.consts.PubOtherConst;
/**
 * 数据库查询对象不区分前后台
 * @author mlr
 */
public class QueryHelper {
	private static String bo = "nc.bs.xcgl.pub.XCZmPubDAO";
	private static QueryHelper help = null;
	public static QueryHelper getInstrance() {
		if (help == null) {
			help = new QueryHelper();
		}
		return help;
	}

	public static Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor,boolean isDebug) throws BusinessException {
		if (RuntimeEnv.getInstance().isRunningInServer()) {
			Object o=XCZmPubDAO.getDAO().executeQuery(sql, parameter, processor,isDebug);
			return o;
		} else {
			Class[] ParameterTypes = new Class[] { String.class,SQLParameter.class,ResultSetProcessor.class,ResultSetProcessor.class,boolean.class };
			Object[] ParameterValues = new Object[] {sql, parameter, processor,isDebug};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, bo, null, "executeQuery",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return o;
		}
	}

	public static Object executeUpdate(String sql) throws BusinessException {
		if (RuntimeEnv.getInstance().isRunningInServer()) {
			Object o= XCZmPubDAO.getDAO().executeUpdate(sql);
			return o;
		} else {
			Class[] ParameterTypes = new Class[]{String.class};
			Object[] ParameterValues = new Object[]{sql};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService("uap", null,
						"正在执行...", 1, "nc.bs.dao.BaseDAO", null, "executeUpdate",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return o;
		}
	}
	
	public static Object executeQuery(String sql,ResultSetProcessor processor) throws BusinessException {
		if (RuntimeEnv.getInstance().isRunningInServer()) {
			Object o= XCZmPubDAO.getDAO().executeQuery(sql,processor);
			return o;
		} else {
			Class[] ParameterTypes = new Class[] { String.class,ResultSetProcessor.class };
			Object[] ParameterValues = new Object[] {sql,processor};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, bo, null, "executeQuery",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return o;
		}
	}
	
	public static Object retrieveByClause(Class className, String condition) throws BusinessException {
		if (RuntimeEnv.getInstance().isRunningInServer()) {
			Object o=XCZmPubDAO.getDAO().retrieveByClause(className,condition);
			return o;
		} else {
			Class[] ParameterTypes = new Class[] { Class.class,String.class };
			Object[] ParameterValues = new Object[] {className,condition};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, bo, null, "retrieveByClause",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return o;
		}
	}
	
	public static Object retrieveByClause(Class className, String condition,SQLParameter para) throws BusinessException {
		if (RuntimeEnv.getInstance().isRunningInServer()) {
			Object o= XCZmPubDAO.getDAO().retrieveByClause(className,condition,para);
			return o;
		} else {
			Class[] ParameterTypes = new Class[] { Class.class,String.class,SQLParameter.class};
			Object[] ParameterValues = new Object[] {className,condition,para};
			Object o = null;
			try {
				o = LongTimeTask.calllongTimeService(PubOtherConst.module, null,
						"正在执行...", 1, bo, null, "retrieveByClause",
						ParameterTypes, ParameterValues);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return o;
		}
	}		
}
