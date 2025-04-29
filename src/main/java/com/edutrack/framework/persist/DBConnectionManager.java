package com.edutrack.framework.persist;

import java.sql.Connection;
import java.sql.SQLException;

import com.edutrack.framework.GenericManager;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.FileUtil;


/**
 * DBConnectionManager는 DB Connection Pool을 추상화한 팩토리 클래스로
 * 실제 구현 팩토리 클래스는 Configuration 파일의 
 * "framework.persist.dbpoolManager" 의 값으로 명시된 클래스를 
 * 동적로딩해서 사용한다.
 * 
 * <pre>
 * DBConnectionManager dbpool = DBConnectionManager.getInstance();
 * Connection conn = null;
 * try
 * {
 * 	conn = dbpool.getConnection();
 * 	...............
 * } catch(SQLException se)
 * {
 * } finally
 * { 
 * 	dbpool.freeConnection(conn);
 * }
 * 
 * </pre>
 * 
 * @author $Author: cvs $<p><p>$Id: DBConnectionManager.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public abstract class DBConnectionManager extends GenericManager
{
	
	private static DBConnectionManager instance = null;
	
	/**
	 * 생성자
	 */
	protected DBConnectionManager() 
	{
		
	}
	
	/**
	 * DBConnectionManager 의 하위클래스를 동적 인스턴싱
	 * 사용할 클래스는 Configuration 에서 검색 
	 * 
	 * @return
	 */
	public static DBConnectionManager getInstance()
	{
		if (instance == null)
		{
			Configuration config = ConfigurationFactory.getInstance().getConfiguration();
			
			String dbpoolClass = config.getString(KEY_DBPOOL);
			
			instance = (DBConnectionManager)FileUtil.newInstance(dbpoolClass);
			
		}

		return instance;
	}
	
	/**
	 * Connection객체를 얻는다.
	 */
	public abstract Connection getConnection() throws SQLException;

	/**
	 * Connection객체를 얻는다.
	 */
	public abstract Connection getConnection(String alias) throws SQLException;
	
	/*
	 * Connection객체를 반환해 준다.
	 */
	public abstract void freeConnection(Connection conn) throws SQLException;
	

}
