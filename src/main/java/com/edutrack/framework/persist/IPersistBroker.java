/*
 * Created on 2003-04-03
 *
 */
package com.edutrack.framework.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.RowSet;

/**
 * DBMS에 종속적인 브로커들에 대한 인터페이스 클래스
 *
 * @author $Author: cvs $<p><p>$Id: IPersistBroker.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public interface IPersistBroker
{
	/*
	 * Boroker의 이름 설정
	 */
	public String getName();

	/*
	 * 쿼리를 실행하고 RowSet을 반환한다.
	 */
    public RowSet executeQuery(ISqlStatement statement) throws SQLException;

	/*
	 * Default Connection Pool 이 아닌 지정된 커넥션풀을 이용한다. 
	 */
	public RowSet executeQuery(ISqlStatement statement, String dbName) throws SQLException;
    
    /*
     * 리스트 쿼리를 실행하고 ListDTO 를 반환한다.
     */
    public ListDTO executeListQuery(ListStatement statement, int curPage) throws SQLException;

	/*
	 * 리스트 쿼리를 실행하고 ListDTO 를 반환한다.
	 */
	public ListDTO executeListQuery(ListStatement statement, int curPage, int listScale) throws SQLException;

	/*
	 * 리스트 쿼리를 실행하고 ListDTO 를 반환한다.
	 */
	public ListDTO executeListQuery(ListStatement statement, int curPage, int listScale, int pageScale) throws SQLException;

	/*
	 * 리스트 쿼리를 실행하고 실제 총갯수를 가지는 ListDTO 를 반환한다.
	 */
	public ListDTO executeRealTotListQuery(ListStatement statement, int curPage) throws SQLException;

	/*
	 * Clob 데이터를 가져오기 위한 ListDTO를 돌려준다.
	 */	
	public ListDTO executeListQuery(ListStatement statement, int curPage,Connection conn) throws SQLException;
	/*
	 * Update 쿼리를 실행하고 적용 Row수를 반환 해준다.
	 */
	public int executeUpdate(ISqlStatement statement) throws SQLException;

	/*
	 * Default Connection Pool 이 아닌 지정된 커넥션풀을 이용한다. 
	 */
	public int executeUpdate(ISqlStatement statement, String dbName) throws SQLException;


	/*
	 * 배열로 된 여러 쿼리를 실행하고 결과를 반환한다.
	 */
	public boolean executeUpdate(ISqlStatement[] statementArray) throws SQLException;

	/*
	 * 쿼리에 대한 총 Row 수를 반환해준다.
	 */

	public int getTotalRow(SelectStatement statement) throws SQLException; 
	/*
	 * 쿼리를 실행하고 ResultSet을 반환한다.
	 */
	public ResultSet executeQuery(PreparedStatement stmt, List bindVars) throws SQLException;
	
	/*
	 * 쿼리를 배치모드로 실행하고 배열로 처리된 로우의 수를 반환한다.
	 */
	public int[] executeBatchUpdate(ISqlStatement statement) throws SQLException;
		
	
	public int executeUpdate(ISqlStatement statement, Connection conn)  throws SQLException;
	
	public ResultSet executeQuery(ISqlStatement statement, Connection conn) throws SQLException;

	public String executeXML(ISqlStatement statement) throws SQLException;
	
    /*
     * 리스트 쿼리를 실행하고 XML 결과셋을 반환한다.
     */
    public String executeXmlListQuery(String systemCode, ListStatement statement, int curPage) throws SQLException;

	/*
	 * 리스트 쿼리를 실행하고 XML 결과셋을  반환한다.
	 */
	public String executeXmlListQuery(String systemCode, ListStatement statement, int curPage, int listScale) throws SQLException;

	/*
	 * 리스트 쿼리를 실행하고 XML 결과셋을 반환한다.
	 */
	public String executeXmlListQuery(String systemCode, ListStatement statement, int curPage, int listScale, int pageScale) throws SQLException;

}
