package com.edutrack.framework.persist;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.RowSet;

import sun.jdbc.rowset.CachedRowSet;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.StringUtil;


/**
 * 이 클래스는 IPersistBroker 를 구현한 클래스로
 *
 * SQL을 실행하고 결과셋(ResultSet) 을 받아오는 역할을 수행한다.
 *
 * @author $Author: cvs $<p><p>$Id: SqlBroker.java,v 1.1.1.1 2007/10/11 05:33:57 cvs Exp $
 *
 */
public class SqlBroker extends PersistBroker
{	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	private static String name = "framework.persist.SqlBroker";

	/**
	 * @roseuid 3E8AC51C0273
	 */
	public SqlBroker()
	{
	}

	public String getName()
	{
		return name;
	}

	/**
	 * SQL을 실행시키고, 오라클의 CachedRowSet 을 반환한다.
	 * 이 CachedRowSet 은 결과셋을 메모리로 올리고 Connection 을
	 * 반납한 상태임. 대용량의 결과셋을 얻을 경우, 적당하지 않음.
	 *
	 * @param statement  실행시킬 ISqlStatement
	 * @return rowset 결과셋
	 * @exception SQLException
	 */
	public RowSet executeQuery(ISqlStatement statement)
	throws SQLException
	{
		return executeQuery(statement, "");
	}
	
	public RowSet executeQuery(ISqlStatement statement, String dbName)
	throws SQLException
	{
		return executeQuery(statement.getKey(), statement.getSql(), statement.getParam(), dbName);
	}
	/**
	 * 페이지 처리가 필요한 List SQL 을 실행시키고, 페이지정보와
	 * CachedRowSet 을 담고 있는 ListDTO 를 리턴
	 * executeQuery() 와 동일하게, 대용량의 결과셋을 얻는 경우 정당하지 않음
	 */
	public ListDTO executeListQuery(ListStatement statement, int curPage) throws SQLException{		
		int iListScale = config.getInt("framework.list.default_list_scale", 10);
		int iPageScale = config.getInt("framework.list.default_page_scale", 10);

		return executeListQuery(statement, curPage, iListScale, iPageScale);

	}

	public ListDTO executeListQuery(ListStatement statement, int curPage, int listScale) throws SQLException{
		Configuration config = ConfigurationFactory.getInstance().getConfiguration();
		
		int iPageScale = config.getInt("framework.list.default_page_scale", 10);

		return executeListQuery(statement, curPage, listScale, iPageScale);

	}

	/**
	 * 페이지 처리가 필요한 List SQL 을 실행시키고, 페이지정보와
	 * CachedRowSet 을 담고 있는 ListDTO 를 리턴
	 * executeQuery() 와 동일하게, 대용량의 결과셋을 얻는 경우 정당하지 않음
	 */
	public ListDTO executeListQuery(ListStatement statement, int curPage, int listScale, int pageScale) throws SQLException{
		int totalRow = getTotalRow((ListStatement)statement, curPage, listScale, pageScale);

		ListDTO result = new ListDTO(curPage, totalRow, listScale, pageScale);
		
		RowSet rs = null;
         
		if(PersistBrokerFactory.defaultBroker.equals("oracle")){
			statement.setInteger(result.getRownumAll());
			statement.setInteger(result.getRownumStart());
			statement.setInteger(result.getRownumEnd());

			rs = executeQuery(statement.getKey(), statement.getListSql(), statement.getParam());
		}
		else if (PersistBrokerFactory.defaultBroker.equals("mysql")){
			rs = executeQuery(statement.getKey(), statement.getListSql2(listScale, result.getRownumStart()-1), statement.getParam()); 
		}
		else {
		    List param = statement.getParam();
		    int listSize = param.size();
		    for(int i =0; i < listSize; i++){
		    	param.add(param.get(i));
		    }
		    
		    log.debug("List Statement : "+statement.getListSql(listScale, result.getRownumStart()-1));
		    
			rs = executeQuery(statement.getKey(), statement.getListSql(listScale, result.getRownumStart()-1), statement.getParam());
		}
		result.setItemList(rs);

		return result;
	}

	/**
	 * Clob를 사용하기 위한 ListStatement 실행
	 * @param statement
	 * @param curPage
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public ListDTO executeListQuery(ListStatement statement, int curPage,Connection conn) throws SQLException{
		int iListScale = config.getInt("framework.list.default_list_scale", 10);
		int iPageScale = config.getInt("framework.list.default_page_scale", 10);
		
	    int totalRow = getTotalRow((ListStatement)statement, curPage, iListScale, iPageScale);

		ListDTO result = new ListDTO(curPage, totalRow, iListScale, iPageScale);
		
		statement.setInteger(result.getRownumAll());
		statement.setInteger(result.getRownumStart());
		statement.setInteger(result.getRownumEnd());
		
		log.debug("List Statement : "+statement.getListSql());
		
		ResultSet rs = executeQuery(statement.getKey(), statement.getListSql(), statement.getParam(),conn);

		result.setArrayItemList(rs);

		return result;
	}
	
	/**
	 * 페이지 처리가 필요한 List SQL 을 실행시키고, 페이지정보와
	 * CachedRowSet 을 담고 있는 ListDTO 를 리턴
	 * executeQuery() 와 동일하게, 대용량의 결과셋을 얻는 경우 정당하지 않음
	 * 수정 : 장철웅 
	 * 위의 executeListQuery는 실제 총 갯수를 가져오지 않는다. 이 메소드는 실제 총 리스트 갯수를 가져온다. 
	 */
	public ListDTO executeRealTotListQuery(ListStatement statement, int curPage) throws SQLException{
		Configuration config = ConfigurationFactory.getInstance().getConfiguration();
 
 		int iListScale = config.getInt("framework.list.default_list_scale", 10);
		int iPageScale = config.getInt("framework.list.default_page_scale", 10);

		int totalRow = getTotalRow((SelectStatement)statement);

		ListDTO result = new ListDTO(curPage, totalRow, iListScale, iPageScale);

		statement.setInteger(result.getRownumAll());
		statement.setInteger(result.getRownumStart());
		statement.setInteger(result.getRownumEnd());
		RowSet rs = executeQuery(statement.getKey(), statement.getListSql(), statement.getParam());

		result.setItemList(rs);

		return result;
	}

	/**
	 * Insert, Update, Delete, Etc 에 해당하는 SQL문을 수행시키고
	 * 해당 처리된 로우 수를 리턴한다.
	 * @param statement 처리할 ISqlStatement
	 * @return 처리된 로우 수
	 *  @exception SQLException
	 *
	 */
	public int executeUpdate(ISqlStatement statement)
		throws SQLException
	{
		return executeUpdate(statement, "");
	}

	public int executeUpdate(ISqlStatement statement, String dbName)
		throws SQLException
	{
		return executeUpdate(statement, statement.getParam(), dbName);
	}
    /**   	
	 * 사용자 정의 트랜잭션을 사용자에게 맡긴다. 밖에서 Connection을 받아서 처리한다.
	 * 주의 ) Connection과 ResultSet은 꼭 free, close를 해줘야만 한다.
     **/
	public int executeUpdate(ISqlStatement statement, Connection conn)
	throws SQLException
	{
		return executeUpdate(statement, statement.getParam(), conn);
	}
	
	/**
	 * 해당 ISqlStatement를 배치모드로 실행시킨다.
	 * 
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchUpdate(ISqlStatement statement)
		throws SQLException
	{
		int result[] = null;
		if (!statement.isBatchMode()) throw new SQLException("This statement is not batch mode");

		result = executeBatchUpdate(statement, statement.getBatch()); 
		return result;
	}

	/**
	 * 이 메소드는 인자로 전달된 statement 배열을 하나의 트랙잰션으로
	 * 처리한다. 각 statement 내에서 예외가 발생될 경우, rollback 을 수행
	 * 한 후 SQLException을 던진다.
	 *
	 * @param ISqlStatement[] 실행시킬 ISqlStatement 배열
	 * @return result boolean 결과
	 * @exception SQLException
	 *
	 */
	public boolean executeUpdate(ISqlStatement[] statementArray)
		throws SQLException
	{
		boolean result = false;

		Connection conn = null;
		try
		{
			conn = pool.getConnection();
			boolean dftCommit = conn.getAutoCommit();
			
			conn.setAutoCommit( false );

			if (statementArray != null)
			{
				for (int i=0; i < statementArray.length; i++)
				{
					if (statementArray[i] != null)
						executeUpdate(statementArray[i], statementArray[i].getParam(), conn);
				}
			}
			conn.commit();
			conn.setAutoCommit(dftCommit);
			result = true;

		} catch(SQLException se)
		{
			try
			{
				if(conn != null) conn.rollback(); 
			} catch(SQLException ignore)
			{
				log.error(ignore.getMessage());
			}
			throw se;

		} catch(Exception e)
		{
			try
			{
				if(conn != null) conn.rollback(); 
			} catch(SQLException ignore)
			{
				log.error(ignore.getMessage(), ignore);
			}
			throw new SQLException(e.getMessage());
		}
		 finally
		{
			try
			{
				if (pool != null) { pool.freeConnection(conn); }
			} catch(SQLException ignore)	{
				log.error(ignore.getMessage(),ignore);
			}
		}
		return result;
	}


	public int getTotalRow(SelectStatement statement)
		throws SQLException
	{
		  Connection conn = null;
		  PreparedStatement stmt = null;
		  ResultSet rset = null;
		  int totalCount = 0;
		  String sql = "SELECT count(*) FROM (" + statement.getSql() + ")";
		  List bindVars = statement.getParam();
		  try
		  {
				  conn = pool.getConnection();
				  stmt = conn.prepareStatement(sql);

				  if (bindVars != null) setBinding(stmt, bindVars);

				  rset = stmt.executeQuery();
				  if(rset.next()){
					totalCount = rset.getInt(1);
				  }
		  } catch (SQLException e)
		  {
				  StringBuffer s = new StringBuffer();
				  s.append("sql statement:").append(statement.getKey());
				  s.append("\nsql error :").append(e.getMessage());
				  s.append("\nsql error code:").append(e.getErrorCode());
				  s.append("\nsql :\n").append(sql );
				  if (bindVars != null) s.append("\nsql bind :" + bindVars);

				  log.error(s.toString());
				  throw e;
		  }
		  finally
		  {
			  try
			  {
				  if (rset != null) { rset.close(); rset = null;  }
			  } 
			  catch (Exception ignore)
			  {
			      log.error(ignore.getMessage());
			  }
			
			  try
			  {
			      if (stmt != null) { stmt.close(); stmt = null; }
			  } 
			  catch (Exception ignore)
			  {
					  log.error(ignore.getMessage());
			  }
			  
			  try
			  {
			      if (conn != null) pool.freeConnection(conn);
			  } 
			  catch (SQLException ignore)
			  {
						  log.error(ignore.getMessage());
			  }

		  }
		return totalCount;
	}

	public int getTotalRow(ListStatement statement, int curPage, int listScale, int pageScale)
		throws SQLException
	{
		  Connection conn = null;
		  PreparedStatement stmt = null;
		  ResultSet rset = null;
		  int totalCount = 0;
		  String sql = statement.getTotalSql(curPage, listScale, pageScale);
		  
		  log.debug("[ Total Sql ]"+statement.getKey() + ":" +sql.toString());
		  
		  List bindVars = statement.getParam();
		  try
		  {
long t1 = System.currentTimeMillis();		  	
				  conn = pool.getConnection();
				  stmt = conn.prepareStatement(sql);

				  if (bindVars != null) setBinding(stmt, bindVars);

				  rset = stmt.executeQuery();
				  if(rset.next()){
					totalCount = rset.getInt(1);
				  }
long t2 = System.currentTimeMillis();
log.debug("[" + statement.getKey()+ " TotalRow ]oracle broker execute time:[" + (t2-t1)/1000.0 + "] seconds");

		  } catch (SQLException e)
		  {
				  StringBuffer s = new StringBuffer();
				  s.append("sql statement:").append(statement.getKey());
				  s.append("\nsql error :").append(e.getMessage());
				  s.append("\nsql error code:").append(e.getErrorCode());
				  s.append("\nsql :\n").append(sql );
				  if (bindVars != null) s.append("\nsql bind :" + bindVars);

				  log.error(s.toString());
				  throw e;
		  }
		  finally
		  {
			  try
			  {
				  if (rset != null) { rset.close(); rset = null;  }
			  } 
			  catch (Exception ignore)
			  {
			      log.error(ignore.getMessage());
			  }
			
			  try
			  {
			      if (stmt != null) { stmt.close(); stmt = null; }
			  } 
			  catch (Exception ignore)
			  {
					  log.error(ignore.getMessage());
			  }
			  
			  try
			  {
			      if (conn != null) pool.freeConnection(conn);
			  } 
			  catch (SQLException ignore)
			  {
						  log.error(ignore.getMessage());
			  }
		  }
		return totalCount;
	}


	//protected RowSet executeQuery(String sql, List bindVars) throws SQLException{
	//	return executeQuery(sql,bindVars, "");
	//}

	protected RowSet executeQuery(String key, String sql, List bindVars) throws SQLException{
		return executeQuery(key,sql,bindVars, "");
	}
	
	protected RowSet executeQuery(String key, String sql, List bindVars, String dbName)
		throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		CachedRowSet ocrs = new CachedRowSet(); 

		try
		{
long t1 = System.currentTimeMillis();
		long t3 = System.currentTimeMillis();
            if(StringUtil.isNotNull(dbName))	conn = pool.getConnection(dbName);
			else	conn = pool.getConnection();
		
        long t4 = System.currentTimeMillis();
        log.debug("[ Pool ]oracle broker execute time:[" + (t4-t3)/1000.0 + "] seconds");

			stmt = conn.prepareStatement(sql);

			if (bindVars != null) setBinding(stmt, bindVars);
			rset = stmt.executeQuery();
			ocrs.populate(rset);			
long t2 = System.currentTimeMillis();
log.debug("[ Query ]oracle broker execute time:[" + (t2-t1)/1000.0 + "] seconds");

		} catch (SQLException e)
		{
			StringBuffer s = new StringBuffer();
			s.append("\nsql error :").append(e.getMessage());
			s.append("\nsql error code:").append(e.getErrorCode());
			s.append("\nsql :\n").append(sql );
			if (bindVars != null) s.append("\nsql bind :" + bindVars);

			log.error(s.toString());
			throw e;
		}
		finally
		{
			  try
			  {
				  if (rset != null) { rset.close(); rset = null;  }
			  } 
			  catch (Exception ignore)
			  {
			      log.error(ignore.getMessage());
			  }
			
			  try
			  {
			      if (stmt != null) { stmt.close(); stmt = null; }
			  } 
			  catch (Exception ignore)
			  {
					  log.error(ignore.getMessage());
			  }
			  
			  try
			  {
			      if (conn != null) pool.freeConnection(conn);
			  } 
			  catch (SQLException ignore)
			  {
						  log.error(ignore.getMessage());
			  }

		}
		return ocrs;
	}

	protected int executeUpdate(ISqlStatement statement , List bindVars)
		throws SQLException
	{
		return executeUpdate(statement, bindVars, "");
	}
	
	
	protected int executeUpdate(ISqlStatement statement , List bindVars, String dbName)
		throws SQLException
	{
		int result = -1;
		Connection conn = null;
		try
		{
long t1 = System.currentTimeMillis();
	long t3 = System.currentTimeMillis();
			if(StringUtil.isNotNull(dbName)) conn = pool.getConnection(dbName);
			else conn = pool.getConnection();
    long t4 = System.currentTimeMillis();
    log.debug("[" + statement.getKey() + " Pool ]oracle broker execute time:[" + (t4-t3)/1000.0 + "] seconds");
			
			result = executeUpdate(statement, bindVars, conn);
long t2 = System.currentTimeMillis();
log.debug("[" + statement.getKey() + " Update ]oracle broker execute time:[" + (t2-t1)/1000.0 + "] seconds");

		} catch(SQLException se)
		{
			throw se;

		} finally
		{
			try
			{
				if (pool != null) { pool.freeConnection(conn); }
			} catch(SQLException ignore)	{
				log.error(ignore.getMessage());
			}
		}
		return result;
	}
	


	/**
	 * 해당 SQL을 실행시키고,  실행된 row의 수를 반환한다.
	 *
	 * @param statement
	 * @param bindVars
	 * @exception SQLException
	 */
	protected int executeUpdate(ISqlStatement statement, List bindVars, Connection conn)
		throws SQLException
	{
		PreparedStatement stmt = null;
		String sql = statement.getSql();
		int result = -1;

		try
		{
			stmt = conn.prepareStatement(sql);

			if (bindVars != null) setBinding(stmt, bindVars);

			result = stmt.executeUpdate();

		} catch (SQLException e)
		{
			StringBuffer s = new StringBuffer();
			s.append("sql statement:").append(statement.getKey());
			s.append("\nsql error :").append(e.getMessage());
			s.append("\nsql error code:").append(e.getErrorCode());
			s.append("\nsql :\n").append(sql );

			if (bindVars != null) s.append("\nsql bind :" + bindVars);

			log.error(s.toString());
			throw e;
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); stmt = null; 	}
			} catch (SQLException ignore) { log.error(ignore.getMessage());}
		}
		return result;
	}

	/**
	 * 사용자 정의 트랜잭션을 사용자에게 맡긴다. 밖에서 Connection을 받아서 처리한다.
	 * 주의 ) Connection과 ResultSet은 꼭 free, close를 해줘야만 한다.
	 * @param statement
	 * @param conn
	 * @return
	 * @throws SQLException
	 */ 
	public ResultSet executeQuery(ISqlStatement statement, Connection conn)
	throws SQLException
	{
		return executeQuery(statement.getKey(), statement.getSql(), statement.getParam(), conn);
	}

	protected ResultSet executeQuery(String key, String sql, List bindVars, Connection conn)
	throws SQLException
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		CachedRowSet ocrs = new CachedRowSet(); 
	
		try
		{		
			stmt = conn.prepareStatement(sql);
	
			if (bindVars != null) setBinding(stmt, bindVars);
	long t1 = System.currentTimeMillis();
	rset = stmt.executeQuery();
	long t2 = System.currentTimeMillis();
	log.debug("[" + key + "]oracle broker execute time:[" + (t2-t1)/1000.0 + "] seconds");
	
	
		} catch (SQLException e)
		{
			StringBuffer s = new StringBuffer();
			s.append("sql statement:").append(key+"\n");
			s.append("\nsql error :").append(e.getMessage());
			s.append("\nsql error code:").append(e.getErrorCode());
			s.append("\nsql :\n").append(sql );
			if (bindVars != null) s.append("\nsql bind :" + bindVars);
	
			log.error(s.toString());
			throw e;
		}
		finally
		{
//			try 
//			{
//				if (rset != null) { rset.close(); rset = null; }
//				if (stmt != null) { stmt.close(); stmt = null; 	}
//			} catch (SQLException ignore)
//			{
//				log.error(ignore.getMessage());
//			}
		}
		return rset;
	}

	
	/**
	 * 해당 SQL을 Batch 로 실행시키고, 실행된 결과를 int 배열로 반환.
	 *
	 * @param statement
	 * @param batch
	 * 
	 * @exception SQLException
	 */
	protected int[] executeBatchUpdate(ISqlStatement statement, List batch)
		throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = statement.getSql();
		int result[];
		
		List bindVars = null;
		try
		{
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);

			for (int i=0; batch != null && i < batch.size();i++)
			{
				bindVars = (List)batch.get(i);
				setBinding(stmt, bindVars);
				stmt.addBatch();					
			}
			result = stmt.executeBatch();
			
			conn.commit();
			conn.setAutoCommit(true);
			
			stmt.close();
			stmt = null;
			

		} catch (SQLException se)
		{
			try
			{
				if(conn != null) conn.rollback(); 
			} catch(SQLException ignore)
			{
				log.error(ignore.getMessage());
			}
						
			StringBuffer s = new StringBuffer();
			s.append("sql statement:").append(statement.getKey());
			s.append("\nsql error :").append(se.getMessage());
			s.append("\nsql error code:").append(se.getErrorCode());
			s.append("\nsql :\n").append(sql );
			for (int i=0;batch != null &&i < batch.size(); i++)
			{
				bindVars = (List)batch.get(i);
				if (bindVars != null) s.append("\nsql bind[" + i + "] :" + bindVars);
			}

			log.error(s.toString());
			throw se;
		} catch(Exception e)
		{
			try
			{
				if(conn != null) conn.rollback(); 
			} catch(SQLException ignore)
			{
				log.error(ignore.getMessage(), ignore);
			}
			throw new SQLException(e.getMessage());
		}
		finally
		{
			
			  try
			  {
			      if (stmt != null) { stmt.close(); stmt = null; }
			  } 
			  catch (Exception ignore)
			  {
					  log.error(ignore.getMessage());
			  }
			  
			  try
			  {
			      if (conn != null) pool.freeConnection(conn);
			  } 
			  catch (SQLException ignore)
			  {
						  log.error(ignore.getMessage());
			  }

		}
		return result;
	}

	public String executeXML(ISqlStatement statement) throws SQLException{
		return executeXML(statement.getSql(), statement.getParam());
	}
	
	private String executeXML(String sql, List bindVars) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		HashMap   hm_set = null;
		ResultSetMetaData rsmd = null;
		LinkedList  llist = new LinkedList();
		StringBuffer sb = new StringBuffer();
//		sb.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>\n");
//		sb.append("<dataset>\n");
		int columnCnt=0;
		try
		{
			
			conn = pool.getConnection();			
			stmt = conn.prepareStatement(sql);
	
			if (bindVars != null) setBinding(stmt, bindVars);
			rset = stmt.executeQuery();			
			rsmd = rset.getMetaData();
			columnCnt = rsmd.getColumnCount();
			StringBuffer output = null;
			Reader input = null;
			char[] buffer= null;
			int byteRead = 0;
			int count =0;
			String[] columnName = new String[columnCnt];
			while(rset.next()) {
				// 처음에 들어와서 한번만 컬럼 배열 이름을 만든다.
				if(count == 0){
					for(int j=1; j<=columnCnt; j++){
						columnName[j-1] = getColumnName(rsmd.getColumnName(j));
					}
				}
	
				sb.append("<entry>\n");
				for(int j=1; j<=columnCnt; j++) {
					sb.append("<"+columnName[j-1]+">");
					if(rsmd.getColumnTypeName(j).equals("CLOB")){
						output = new StringBuffer();
						input = rset.getCharacterStream(rsmd.getColumnName(j).toUpperCase());

						if(input != null){
							buffer = new char[1024];
							byteRead = 0;
							while((byteRead=input.read(buffer,0,1024))!=-1){
								output.append(buffer,0,byteRead);
							}

							sb.append("<![CDATA["+output.toString()+"]]>");
							input.close();							
						}else{
							sb.append("<![CDATA[]]>");
						}

					}else{
						if(rset.getObject(j) == null){
							sb.append("<![CDATA["+StringUtil.nvl((String)rset.getObject(j))+"]]>");
						}else
							sb.append("<![CDATA["+rset.getObject(j)+"]]>");
					}
					
					sb.append("</"+columnName[j-1]+">\n");
				}
				sb.append("</entry>\n");
				
				count++;
			} 
		} catch (SQLException e)
		{
			throw e;
		}catch (Exception e) { 
			e.printStackTrace();
		}
//		sb.append("</dataset>\n");

		return sb.toString();
    }
	
	/**
	 * 컬럼명을 프로그램 명명규칙에 맞춰 돌려준다.
	 * @param columnName
	 * @return
	 */
	private String getColumnName(String columnName){
		StringBuffer sb = new StringBuffer();
		
        StringTokenizer st = new StringTokenizer(columnName, "_");
    	
        if (st.hasMoreTokens()) {
            sb.append(st.nextToken().toString().toLowerCase());
        }

        while (st.hasMoreTokens()) {
            String tmp = st.nextToken().toString();
            sb.append(tmp.substring(0, 1).toUpperCase() + tmp.substring(1).toLowerCase());
        }

        return sb.toString();
	}

	public String executeXmlListQuery(String systemCode, ListStatement statement, int curPage) throws SQLException{		
		int iListScale = config.getInt("framework.list.default_list_scale", 10);
		int iPageScale = config.getInt("framework.list.default_page_scale", 10);

		return executeXmlListQuery(systemCode, statement, curPage, iListScale, iPageScale);

	}

	public String executeXmlListQuery(String systemCode, ListStatement statement, int curPage, int listScale) throws SQLException{
		Configuration config = ConfigurationFactory.getInstance().getConfiguration();
		
		int iPageScale = config.getInt("framework.list.default_page_scale", 10);

		return executeXmlListQuery(systemCode, statement, curPage, listScale, iPageScale);

	}

	public String executeXmlListQuery(String systemCode, ListStatement statement, int curPage, int listScale, int pageScale) throws SQLException{
		int totalRow = getTotalRow((ListStatement)statement, curPage, listScale, pageScale);

		ListDTO result = new ListDTO(curPage, totalRow, listScale, pageScale);
		
		StringBuffer rs = new StringBuffer();
         
		if(PersistBrokerFactory.defaultBroker.equals("oracle")){
			statement.setInteger(result.getRownumAll());
			statement.setInteger(result.getRownumStart());
			statement.setInteger(result.getRownumEnd());

			rs.append(executeXML(statement.getListSql(), statement.getParam()));
		}else{
		    List param = statement.getParam();
		    int listSize = param.size();
		    for(int i =0; i < listSize; i++){
		    	param.add(param.get(i));
		    }
		    
		    log.debug("List Statement : "+statement.getListSql(listScale, result.getRownumStart()-1));
		    
			rs.append(executeXML(statement.getListSql(listScale, result.getRownumStart()-1), statement.getParam()));
		}
		
		rs.append("<selectValue>");
		rs.append("<cntAll>"+result.getStartPageNum()+"</cntAll>");
		rs.append("<getPagging><![CDATA["+result.getPaggingAjax(systemCode,"goPage")+"]]></getPagging>");
		rs.append("</selectValue>");

		return rs.toString();
	}

}
