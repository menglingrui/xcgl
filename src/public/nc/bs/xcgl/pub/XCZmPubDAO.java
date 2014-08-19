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
	 * ����SQL ִ�����ݿ��ѯ,������ResultSetProcessor�����Ķ��� ���� Javadoc��
	 * 
	 * @param sql
	 *            ��ѯ��SQL
	 * @param processor
	 *            �����������
	 */
	public Object executeQuery(String sql, ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			XCLogger.printInfor("sqlΪ��"+sql+"��ʼ");
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, processor);
			XCLogger.printInfor("sqlΪ��"+sql+"����");
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
	 * ����VO������where��������vo����
	 * 
	 * @param className
	 *            Vo������
	 * @param condition
	 *            ��ѯ����
	 * @return ����Vo����
	 * @throws DAOException
	 *             ���������׳�DAOException
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
	 * ����ָ��SQL ִ���޲��������ݿ���²���
	 * 
	 * @param sql
	 *            ���µ�sql
	 * @return
	 * @throws DAOException
	 *             ���·��������׳�DAOException
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
