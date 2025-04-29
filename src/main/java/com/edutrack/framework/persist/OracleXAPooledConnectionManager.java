/*
 * Created on 2003-04-08
 *
 */
package com.edutrack.framework.persist;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import oracle.jdbc.xa.client.OracleXADataSource;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;

/**
 * 오라클 OracleXADataSource를 이용한 DBConnectionManager 구현
 * 
 * @author $Author: cvs $<p><p>$Id: OracleXAPooledConnectionManager.java,v 1.1.1.1 2007/10/11 05:33:57 cvs Exp $
 *
 */
public class OracleXAPooledConnectionManager extends DBConnectionManager
{
	Log log = LogFactory.getLog(OracleXAPooledConnectionManager.class);
	Hashtable ds = null;
    /*
     * 생성자
     */	
	public OracleXAPooledConnectionManager()
	{
		super();
		try
		{
			Configuration c = ConfigurationFactory.getInstance().getConfiguration();

			ds = new Hashtable();
			
			String[] url = c.getStringArray("framework.persist.dbpoolManager.oraclexa.url");
			String[] user = c.getStringArray("framework.persist.dbpoolManager.oraclexa.user");
			String[] password = c.getStringArray("framework.persist.dbpoolManager.oraclexa.password");
			String[] alias = c.getStringArray("framework.persist.dbpoolManager.oraclexa.alias");
			
			if(url != null && url.length == 1){
				OracleXADataSource oxads = new OracleXADataSource();
				oxads.setURL(url[0]);
				oxads.setUser(user[0]);
				oxads.setPassword(password[0]);
				
				ds.put("DEFAULT_DS", oxads);
			}else if(alias != null && alias.length > 1){
				int size = alias.length;
				for(int i=0; i<size; i++){
					
					OracleXADataSource oxads = new OracleXADataSource();
					oxads.setURL(url[i]);
					oxads.setUser(user[i]);
					oxads.setPassword(password[i]);
				
					ds.put(alias[i], oxads);
					if(i == 0){
						ds.put("DEFAULT_DS", oxads);
					}
				}
			}
			
		} catch (Exception e)
		{
			log.error("[ORACLE XA POOLED CONNECTION MANAGER INITIALIZE ERROR!!");
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
		
		OracleXADataSource ods = (OracleXADataSource)obj;
		Connection conn = ods.getConnection();
		
		if(conn == null) throw new SQLException("CANNOT GET DB CONNECTION!!!!");
		return conn;
	}

	public Connection getConnection(String alias)
		throws SQLException
	{
		Object obj = ds.get(alias);
		if(obj == null) throw new SQLException("CANNOT FIND '"+alias+"' DATASOURCE!!!!");
		
		OracleXADataSource ods = (OracleXADataSource)obj;
		Connection conn = ods.getConnection();
		
		if(conn == null) throw new SQLException("CANNOT GET '"+alias+"' CONNECTION!!!!");
		return conn;
	}
	
	/**
	 * 오라클 OraclePooledDataSource 로부터 Connection을 반환한다.
	 */
	public void freeConnection(Connection conn)
		throws SQLException 
	{
		if(conn != null) conn.close();
		
	}

}
