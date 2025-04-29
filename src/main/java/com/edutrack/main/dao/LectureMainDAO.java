/*
 * Created on 2004. 10. 26.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.main.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class LectureMainDAO extends AbstractDAO {

	/**
	 *
	 */
	public LectureMainDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 과목 접속 정보(통계용)를 입력 및 수정한다.
     * @param users
     * @return
     * @throws DAOException
     */
	public int getCourseConnectCnt(String systemcode, String pCurriCode, String pCurriYear, String pCurriTerm, String pCourseId, String userId, String remoteIp) throws DAOException{
		int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();

	     sb.append(" insert into course_connect (system_code, curri_code, curri_year, curri_term, course_id, conn_time, user_id, remote_ip )");
	     sb.append(" values(?,?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),?,?)");
  		 sql.setSql(sb.toString());
  		 sql.setString(systemcode);
  		 sql.setString(pCurriCode);
  		 sql.setString(pCurriYear);
		 sql.setString(pCurriTerm);
		 sql.setString(pCourseId);
		 sql.setString(userId);
		 sql.setString(remoteIp);

		log.debug("[getCourseConnectCnt]" + sql.toString());
		try{
		    retVal 		= 	broker.executeUpdate(sql);
	 	}catch(Exception e){
	 	    log.error(e.getMessage());
	 	    throw new DAOException(e.getMessage());
	 	}
		return retVal;
	}

	/**
     * 과정 접속 정보(통계용)를 입력 한다.
     * @param users
     * @return
     * @throws DAOException
     */
	public int getCurriConnectCnt(String systemcode, String pCurriCode, String pCurriYear, String pCurriTerm, String userId, String remoteIp) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();

	     sb.append(" insert into curri_connect (system_code, curri_code, curri_year, curri_term, conn_time, user_id, remote_ip )");
	     sb.append(" values(?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),?,?)");
   		 sql.setSql(sb.toString());
   		 sql.setString(systemcode);
   		 sql.setString(pCurriCode);
   		 sql.setString(pCurriYear);
		 sql.setString(pCurriTerm);
		 sql.setString(userId);
		 sql.setString(remoteIp);

		log.debug("[getCurriConnectCnt]" + sql.toString());
		try{
		    retVal 		= 	broker.executeUpdate(sql);
	 	}catch(Exception e){
	 	    log.error(e.getMessage());
	 	    throw new DAOException(e.getMessage());
	 	}
		return retVal;
	}

	public static void main(String[] args) throws Exception{
	    LectureMainDAO LectureMainDao = new LectureMainDAO();
	}
}