/*
 * Created on 2003-04-08
 *
 */
package com.edutrack.framework.persist;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import oracle.jdbc.pool.OracleConnectionCacheImpl;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;


/**
 * 오라클 풀매니저를 이용한 DBConnectionManager 구현
 * 
 * @author $Author: cvs $<p><p>$Id: OraclePooledConnectionManager.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public class OraclePooledConnectionManager extends DBConnectionManager
{
	Log log = LogFactory.getLog(OraclePooledConnectionManager.class);
	Hashtable ds =  null;

	public OraclePooledConnectionManager()
	{
		super();
		try
		{
			Configuration c = ConfigurationFactory.getInstance().getConfiguration();
			ds = new Hashtable();
			
			String[] url = c.getStringArray("framework.persist.dbpoolManager.oracle.url");
			String[] user = c.getStringArray("framework.persist.dbpoolManager.oracle.user");
			String[] password = c.getStringArray("framework.persist.dbpoolManager.oracle.password");
			String[] maxnum = c.getStringArray("framework.persist.dbpoolManager.oracle.maxnum");
			String[] minnum = c.getStringArray("framework.persist.dbpoolManager.oracle.minnum");
			String[] alias = c.getStringArray("framework.persist.dbpoolManager.oracle.alias");
			
			if(url != null && url.length == 1){
				OracleConnectionPoolDataSource ods = new OracleConnectionPoolDataSource();
				ods.setURL(url[0]);
				ods.setUser(user[0]);
				ods.setPassword(password[0]);

				OracleConnectionCacheImpl connectionCache = new OracleConnectionCacheImpl(ods);
				
				connectionCache.setMaxLimit(Integer.parseInt(maxnum[0]));
				connectionCache.setMinLimit(Integer.parseInt(minnum[0]));
				
				ds.put("DEFAULT_DS", ods);
			}else if(alias != null && alias.length > 1){
				int size = alias.length;
				for(int i=0; i<size; i++){
					OracleConnectionPoolDataSource ods = new OracleConnectionPoolDataSource();
				
					ods.setURL(url[i]);
					ods.setUser(user[i]);
					ods.setPassword(password[i]);
					
					ds.put(alias[i], ods);
					if(i == 0){
						ds.put("DEFAULT_DS", ods);
					}
				}
			}

		} catch (SQLException e)
		{
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
		Object obj = ds.get("DEFAULT_DS");
		if(obj == null) throw new SQLException("CANNOT FIND DEFAULT DATASOURCE!!!!");
		
		OracleConnectionPoolDataSource ods = (OracleConnectionPoolDataSource)obj;
		Connection conn = ods.getConnection();
		
		if(conn == null) throw new SQLException("CANNOT GET DB CONNECTION!!!!");
		return conn;

	}
	
	public Connection getConnection(String alias)
		throws SQLException
	{
		Object obj = ds.get(alias);
		if(obj == null) throw new SQLException("CANNOT FIND '"+alias+"' DATASOURCE!!!!");
		
		OracleConnectionPoolDataSource ods = (OracleConnectionPoolDataSource)obj;
		Connection conn = ods.getConnection();
		
		if(conn == null) throw new SQLException("CANNOT GET '"+alias+"' CONNECTION!!!!");
		return conn;		
	}
	
	public void freeConnection(Connection conn)
		throws SQLException
	{
		if(conn != null) conn.close();
	}

}
