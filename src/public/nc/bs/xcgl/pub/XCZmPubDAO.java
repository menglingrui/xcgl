package nc.bs.xcgl.pub;

import java.util.Collection;

import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.bs.zmpub.pub.dao.ZmPubDAO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.xcgl.pub.logger.XCLogger;

public class XCZmPubDAO extends ZmPubDAO {
	private static XCZmPubDAO dao=null;
	
	public static XCZmPubDAO getDAO(){
		if(dao==null){
			dao=new XCZmPubDAO();
		}
		return dao;
	}
	/**
	 * 根据SQL 执行数据库查询,并返回ResultSetProcessor处理后的对象 （非 Javadoc）
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param processor
	 *            结果集处理器
	 */
	public Object executeQuery(String sql, ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			XCLogger.printInfor("sql为："+sql+"开始");
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, processor);
			XCLogger.printInfor("sql为："+sql+"结束");
		} catch (DbException e) {
			XCLogger.printDebug(e.getMessage());
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}
	/**
	 * 根据VO类名和where条件返回vo集合
	 * 
	 * @param className
	 *            Vo类名称
	 * @param condition
	 *            查询条件
	 * @return 返回Vo集合
	 * @throws DAOException
	 *             发生错误抛出DAOException
	 */
	public Collection retrieveByClause(Class className, String condition) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByClause(className, condition);
		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}
	/**
	 * 根据指定SQL 执行无参数的数据库更新操作
	 * 
	 * @param sql
	 *            更新的sql
	 * @return
	 * @throws DAOException
	 *             更新发生错误抛出DAOException
	 */
	public int executeUpdate(String sql) throws DAOException {
		PersistenceManager manager = null;
		int value;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeUpdate(sql);

		} catch (DbException e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}
	
}
