/*
 * Created on 2003. 4. 14.
 *
 */
package com.edutrack.framework.persist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;

/**
 *  Resin JNDI�� ���� ���ؼ��� �����ϴ� Ŭ����
 * @author $Author: cvs $<p><p>$Id: DbJndiDataSource.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 */
public class DbJndiDataSource extends DBConnectionManager
{
	Log log = LogFactory.getLog(DbJndiDataSource.class);
	Hashtable ds = null;
	/**
	 *  ������
	 */
	public DbJndiDataSource()
	{
		super();
		try
		{
			Configuration c = ConfigurationFactory.getInstance().getConfiguration();
			ds = new Hashtable();

			log.debug("DbJndiDataSource INIT");
			String[] jndiName = c.getStringArray("framework.persist.dbpoolManager.JndiDataSource.jndiName");
			String wasName = c.getString("framework.persist.dbpoolManager.wasName");

			if(jndiName == null || jndiName.length <= 0) log.error("Can't get property : framework.persist.dbpoolManager.JndiDataSource.jndiName");
			else log.debug("jndiName[0] : " + jndiName[0]);


System.out.println("jndiName[0] : " + jndiName[0]);


			Context ctx = null;

			if(wasName.equals("tomcat")) {
				ctx = (Context)new InitialContext().lookup("java:comp/env");
			} else if(wasName.equals("jeus")) {
				ctx = new InitialContext();
			}

			if(jndiName != null && jndiName.length == 1){
				DataSource ods = (DataSource)ctx.lookup(jndiName[0]);
				ds.put("DEFAULT_DS", ods);
			}else if(jndiName != null && jndiName.length > 0){
				int size = jndiName.length;
				for(int i=0; i<size; i++){
					DataSource ods = (DataSource)ctx.lookup(jndiName[i]);
					ds.put(jndiName[i], ods);
					if(i == 0)
						ds.put("DEFAULT_DS", ods);
				}
			}


		} catch (Exception e)
		{
			log.error("[JNDI DATA SOURCE INITIALIZE ERROR!!!");
			log.error(e.getMessage(),e);
		}
	}

	/* ���ؼ��� ��´�.
	 * @see framework.persist.DBConnectionManager#getConnection()
	 */
	public Connection getConnection() throws SQLException
	{
		Object obj = ds.get("DEFAULT_DS");
		if(obj == null) throw new SQLException("DbJndiDataSource.java CANNOT FIND DEFAULT DATASOURCE!!!!");

		DataSource ods = (DataSource)obj;
		Connection conn = ods.getConnection();

		if(conn == null) throw new SQLException("CANNOT GET DEFAULT CONNECTION!!!!");
		return conn;
	}


	public Connection getConnection(String alias) throws SQLException
	{
		Object obj = ds.get(alias);
		if(obj == null) throw new SQLException("CANNOT FIND '"+alias+"' DATASOURCE!!!!");

		DataSource ods = (DataSource)obj;
		Connection conn = ods.getConnection();

		if(conn == null) throw new SQLException("CANNOT GET '"+alias+"' CONNECTION!!!!");
		return conn;

	}

	/*
	 * ���ؼ��� ��ȯ���ش�.
	 */
	public void freeConnection(Connection conn) throws SQLException
	{
		if(conn != null && !conn.isClosed())
			conn.close();

	}

}
