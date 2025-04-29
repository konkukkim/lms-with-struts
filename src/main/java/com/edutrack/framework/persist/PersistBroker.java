/*
 * Created on 2003-04-03
 *
 */
package com.edutrack.framework.persist;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;
import com.edutrack.framework.util.StringUtil;


/**
 * IPersistBroker 인터페이스에 대한 추상화된 클래스
 * 
 * @author $Author: cvs $<p><p>$Id: PersistBroker.java,v 1.1.1.1 2007/10/11 05:33:57 cvs Exp $
 *
 */
public abstract class PersistBroker implements IPersistBroker
{
	DBConnectionManager pool = DBConnectionManager.getInstance();
	Log log = LogFactory.getLog(getName());

	/**
	 * ResultSet 을 얻기위한 유틸리티 메소드, 이 메소드에서 사용한 ResultSet 은 반드시 
	 * 리소스를 해제시켜야함.
	 * 
	 * @param conn
	 * @param stmt
	 * @param bindVars
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(PreparedStatement stmt, List bindVars)
		throws SQLException
	{
		ResultSet rset = null;
		
		if (stmt == null) return null;
		try
		{
			if (bindVars != null) setBinding(stmt, bindVars);
			
			rset = stmt.executeQuery();
			
		} catch(SQLException se)
		{
			log.error(se.getMessage());
		}
		
		return rset;
	}

	/**
	 * PreparedStatement parameter binding.
	 * 
	 * @param stmt
	 * @param bindVars
	 */
	protected void setBinding(PreparedStatement stmt, List bindVars)
		throws SQLException
	{
		int len = 0;
		if(bindVars != null) len = bindVars.size();
		
		for (int i=0; i < len; i++)
		{
			Object bind = bindVars.get(i);
			if(bind == null){
				stmt.setString(i+1, null);
				continue;
			}
				
			if (bind instanceof String) 
				stmt.setString(i+1, (String)bind);
			else if (bind instanceof Integer) 
				stmt.setInt(i+1, ((Integer)bind).intValue());
			else if (bind instanceof Long)
				stmt.setLong(i+1,((Long)bind).longValue());
			else if (bind instanceof StringBuffer)
			{
				String value = ((StringBuffer)bind).toString();
				StringReader sr = new StringReader(value);
				stmt.setCharacterStream(i+1, sr, value.length());
			}
			else if (bind instanceof Timestamp)
				stmt.setTimestamp(i+1, (Timestamp)bind);
			else	if (bind instanceof Date)
			{
				java.sql.Date d = new java.sql.Date(((Date)bind).getTime());
				stmt.setDate(i+1, d);
			}
			else if(bind instanceof Float){
				stmt.setFloat(i+1, ((Float)bind).floatValue());
			}
			else if(bind instanceof Double){
				stmt.setDouble(i+1, ((Double)bind).doubleValue());
			}
			else
				stmt.setObject(i+1, bind);
		}

	}
}
