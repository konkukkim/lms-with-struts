/*
 * Created on 2004. 9. 10.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.framework.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SqlConnectionManager extends DBConnectionManager{
	Log log = LogFactory.getLog(SqlConnectionManager.class);

	public SqlConnectionManager()
	{
		super();
		try
		{
			Configuration c = ConfigurationFactory.getInstance().getConfiguration();
			
			String driver = c.getString("framework.persist.dbpoolManager.driver");
			String url = c.getString("framework.persist.dbpoolManager.url");
			String user = c.getString("framework.persist.dbpoolManager.user");
			String password = c.getString("framework.persist.dbpoolManager.password");
			String maxnum = c.getString("framework.persist.dbpoolManager.maxnum");
			String minnum = c.getString("framework.persist.dbpoolManager.minnum");
			String alias = c.getString("framework.persist.dbpoolManager.alias");
			
//		    new pool.JDCConnectionDriver(driver,url,user,password); 
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ORACLE POOLED CONNECTION MANAGER, CANNOT INITIALIZE");
			log.error(e.getMessage());
		}
	}
	/**
	 * 오라클 OraclePooledDataSource 로부터 Connection을 얻어온다.
	 */
	public Connection getConnection()
		throws SQLException
	{

		Connection conn = DriverManager.getConnection("jdbc:jdc:jdcpool");
		
		if(conn == null) throw new SQLException("CANNOT GET DB CONNECTION!!!!");
		return conn;

	}
	
	public Connection getConnection(String alias)
		throws SQLException
	{
		Connection conn = DriverManager.getConnection(alias);
		
		if(conn == null) throw new SQLException("CANNOT GET DB CONNECTION!!!!");
		return conn;	
	}
	
	public void freeConnection(Connection conn)
		throws SQLException
	{
		
		if(conn != null) conn.close();
	}

}
