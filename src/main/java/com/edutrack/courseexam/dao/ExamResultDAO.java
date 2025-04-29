/*
 * Created on 2004. 10. 20.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.dao;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamAnswerDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamResultDAO extends AbstractDAO {

	/**
	 *
	 */
	public ExamResultDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RowSet getExamUserList(String systemcode,CurriMemDTO curriinfo ,String courseid,int examid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.user_id,(select user_name from users where system_code = s.system_code and user_id = s.user_id) user_name,s.enroll_no, ");
			sb.append(" s.enroll_status,ifnull(a.user_id,'') examuser_id ,ifnull(a.start_date,'') start_date,ifnull(a.end_date,'') end_date, ifnull(a.total_score,0) total_score,ifnull(a.result_check,'N') result_check ");
			sb.append(" from student s left outer join exam_answer a on s.system_code = a.system_code and s.curri_code = a.curri_code ");
			sb.append(" and s.curri_year = a.curri_year and s.curri_term = a.curri_term and s.user_id = a.user_id ");
			sb.append(" and a.course_id = ? and a.exam_id = ? ");
			sb.append(" where s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term = ? and s.enroll_status = 'S' ");
			sql.setString(courseid);
			sql.setInteger(examid);
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setSql(sb.toString());

			log.debug("[getExamResultList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	public int editExamResult(String systemcode,CurriMemDTO curriInfo,ExamAnswerDTO answerinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_answer ");
		 sb.append(" set answer_score=?,total_score=?,result_check=?, feed_back=?, ");
		 sb.append(" mod_id=?,mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ?	and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? and user_id = ? ");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(answerinfo.getAnswerScore(),"R");
	   	 sql.setInteger(answerinfo.getTotalScore());
	   	 sql.setString(answerinfo.getResultCheck());
	   	 sql.setString(answerinfo.getFeedBack());
	   	 sql.setString(answerinfo.getModId());
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(answerinfo.getCourseId());
		 sql.setInteger(answerinfo.getExamId());
	   	 sql.setString(answerinfo.getUserId());


		 log.debug("[editExamAnswerInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

}
