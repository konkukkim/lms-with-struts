/*
 * Created on 2003. 4. 14.
 *
 */
package com.edutrack.framework.persist;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;


/**
 * 이 클래스는 데이터베이스 시퀀스 객체를 이용해서 고유아이디를 
 * 생성하는 역할을 수행한다.
 * 
 * @author $Author: cvs $<p><p>$Id: OracleSequence.java,v 1.1.1.1 2007/10/11 05:33:57 cvs Exp $
 *
 */
public class OracleSequence implements ISequence
{
	Log log = LogFactory.getLog(OracleSequence.class);
	DBConnectionManager pool =  DBConnectionManager.getInstance();

    /*
     * 시퀀스의 값을 String형으로 반환해 준다.
     */	
	public String getIdAsString(String seqName)
		throws PersistentException,SQLException
	{
		return getId(seqName).toString();
	}
	
     /*
     * 시퀀스의 값을 Int형으로 반환해 준다.
     */	
	public int getIdAsInt(String seqName)
		throws PersistentException,SQLException
	{
		return getId(seqName).intValue(); 
	}
	
    /*
     * 시퀀스의 값을 Long형으로 반환해 준다.
     */
	public long getIdAsLong(String seqName)
		throws PersistentException,SQLException
	{
		return getId(seqName).longValue();
	}
	
    /*
     * 시퀀스의 값을 BigDecimal형으로 반환해 준다.
     */	
	public BigDecimal getIdAsBigDecimal(String seqName)
		throws PersistentException,SQLException
	{
		return getId(seqName);
	}

    /*
     * 해당 시퀀스에서 다음 값에 해당하는 값을 돌려준다.
     */	
	protected BigDecimal getId(String seqName)
		throws PersistentException,SQLException
	{
		BigDecimal nextVal = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try
		{
			conn = pool.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery("select " + seqName + ".NEXTVAL from dual");
			if (rset.next())
			{
				nextVal = rset.getBigDecimal(1);
			}
		} catch(SQLException se)
		{
			log.error(se.getMessage());
			throw se;
		} finally
		{
			try
			{
				if (rset != null)	{ rset.close(); rset = null; }
				if (stmt != null)	{ stmt.close(); stmt = null; } 
				
				pool.freeConnection(conn);
				
			} catch(SQLException ignore)
			{
				log.error( ignore.getMessage());
			}
		}
		if (nextVal == null) 
			throw new PersistentException(seqName + " sequence nextval null, from database");
			
		return nextVal;
	}

}
