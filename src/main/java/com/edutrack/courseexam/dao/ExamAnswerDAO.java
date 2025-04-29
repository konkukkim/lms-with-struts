/*
 * Created on 2004. 10. 19.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamAnswerDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamAnswerDAO extends AbstractDAO {

	/**
	 *
	 */
	public ExamAnswerDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamAnswerDTO getExamAnswerInfo(String systemcode, CurriMemDTO curriinfo,String courseid,int examid,String userid) throws DAOException{
		 ExamAnswerDTO answerInfo = null;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select user_id, exam_order,exam_no,exam_score,exam_answer,answer ,answer_score,total_score,answer_time, ");
		 sb.append(" start_date,end_date,feed_back,flag_retest,reg_id,reg_date ");
		 sb.append(" from exam_answer ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? and user_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);
		 sql.setString(userid);

		 log.debug("[getExamAnswerInfo]" + sql.toString());

		 ResultSet rs = null;
	     StringBuffer output = new StringBuffer();
		 try{
			rs = broker.executeQuery(sql);

			 answerInfo = new ExamAnswerDTO();

			 if(rs.next()){
			 	answerInfo.setUserId(StringUtil.nvl(rs.getString("user_id")));
			 	answerInfo.setExamOrder(StringUtil.nvl(rs.getString("exam_order")));
			 	answerInfo.setExamScore(StringUtil.nvl(rs.getString("exam_score")));
			 	answerInfo.setExamAnswer(StringUtil.nvl(rs.getString("exam_answer")));
			 	answerInfo.setAnswer(StringUtil.nvl(rs.getString("answer")));
			 	answerInfo.setAnswerScore(StringUtil.nvl(rs.getString("answer_score")));
			 	answerInfo.setTotalScore(rs.getInt("total_score"));
			 	answerInfo.setAnswerTime(rs.getInt("answer_time"));
			 	answerInfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	answerInfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	answerInfo.setFeedBack(StringUtil.nvl(rs.getString("feed_back")));
			 	answerInfo.setFlagRetest(StringUtil.nvl(rs.getString("flag_retest")));
			 	answerInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 	answerInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 }
			 rs.close();

		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
		 	try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }

		 return answerInfo;
	}



	public int editExamAnswerTime(String systemcode,CurriMemDTO curriInfo,String courseid, int examid, String userid) throws DAOException{
		return editExamAnswerTime(systemcode,curriInfo,courseid, examid,userid,"");
	}
	public int editExamAnswerTime(String systemcode,CurriMemDTO curriInfo,String courseid, int examid, String userid,String mode) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_answer ");
		 sb.append(" set ");
		 if(mode.equals("")) sb.append(" answer_time = answer_time + 1  ");
		 else sb.append(" start_date = CAST(DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') AS CHAR)");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? and user_id = ? ");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);
	   	 sql.setString(userid);

		 log.debug("[editExamAnswerTime]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public int addExamAnswerInfo(String systemcode,CurriMemDTO curriInfo, String courseid, int examid, String userid) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_answer(system_code,curri_code,curri_year,curri_term,course_id ,exam_id,user_id,answer_time,start_date,flag_retest,reg_id,reg_date) ");
		 sb.append(" values(?,?,?,?,?,?,?,0,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" ,'N',?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);
		 sql.setString(userid);
		 sql.setString(userid);

		 log.debug("[addExamAnswerInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

/*
	public int editExamAnswerInfo(String systemcode,CurriMemDTO curriInfo,ExamAnswerDTO answerinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_answer ");
		 sb.append(" set exam_order=?,exam_answer=?,answer=empty_clob(),exam_score=?,answer_score=?,total_score=?,result_check=?,answer_time=999,end_date=date_format(now(), '%Y%m%d%H%i%s'), ");
		 sb.append(" mod_id=?,mod_date=date_format(now(), '%Y%m%d%H%i%s') ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ?	and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? and user_id = ? ");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(answerinfo.getExamOrder(),"R");
	   	 sql.setString(answerinfo.getExamAnswer(),"R");
	   	 //sql.setString(answerinfo.getAnswer(),"R");
	   	 sql.setString(answerinfo.getExamScore(),"R");
	   	 sql.setString(answerinfo.getAnswerScore(),"R");
	   	 sql.setInteger(answerinfo.getTotalScore());
	   	 sql.setString(answerinfo.getResultCheck());
	   	 sql.setString(answerinfo.getUserId());
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(answerinfo.getCourseId());
		 sql.setInteger(answerinfo.getExamId());
	   	 sql.setString(answerinfo.getUserId());

		 log.debug("[editExamAnswerInfo]" + sql.toString());

		 Connection conn = null;
		 DBConnectionManager pool = null;
		 ResultSet rs = null;
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 try{
		 	pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql,conn);

			 sb2.append(" select answer from exam_answer ");
			 sb2.append(" where system_code = ? and curri_code = ? and curri_year = ?	and curri_term = ? ");
			 sb2.append(" and course_id = ? and exam_id = ? and user_id = ?  FOR UPDATE");
			 sql2.setSql(sb2.toString());
			 sql2.setString(systemcode);
			 sql2.setString(curriInfo.curriCode);
			 sql2.setInteger(curriInfo.curriYear);
			 sql2.setInteger(curriInfo.curriTerm);
			 sql2.setString(answerinfo.getCourseId());
			 sql2.setInteger(answerinfo.getExamId());
		   	 sql2.setString(answerinfo.getUserId());
			 log.debug("[UseAdd_editExamAnswerInfo]" + sql2.toString());

			 rs = broker.executeQuery(sql2,conn);
	     	if(rs.next()){
//	     		log.debug("______ rs "+rs.getClob(1));
	     		CLOB clob = (CLOB)rs.getClob("ANSWER");
	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("answer");
	     		Writer writer = clob.getCharacterOutputStream();
	     		Reader src = new CharArrayReader(answerinfo.getAnswer().toCharArray());
	     		char[] buffer = new char[1024];
	     		int read = 0;
	     		while ( (read = src.read(buffer,0,1024)) != -1) {
	     			writer.write(buffer, 0, read);
	     		}
	     		//clob.close();
	     		src.close();
	     		writer.close();
	     	}
			conn.commit();

		 }catch(Exception e){
		 	e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch(SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			throw new DAOException(e.getMessage());
		 } finally {
			try {
				if(conn != null){
					conn.setAutoCommit( true );
					pool.freeConnection(conn); // 컨넥션 해제
				}
			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
		 return retVal;
	}
*/

	public int editExamAnswerInfo(String systemcode,CurriMemDTO curriInfo,ExamAnswerDTO answerinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_answer ");
		 sb.append(" set exam_order=?,exam_answer=?,answer=?,exam_score=?,answer_score=?,total_score=?,result_check=?,answer_time=999,end_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 sb.append(" mod_id=?,mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ?	and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? and user_id = ? ");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(answerinfo.getExamOrder(),"R");
	   	 sql.setString(answerinfo.getExamAnswer(),"R");
	   	 sql.setString(answerinfo.getAnswer(),"R");
	   	 sql.setString(answerinfo.getExamScore(),"R");
	   	 sql.setString(answerinfo.getAnswerScore(),"R");
	   	 sql.setInteger(answerinfo.getTotalScore());
	   	 sql.setString(answerinfo.getResultCheck());
	   	 sql.setString(answerinfo.getUserId());
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(answerinfo.getCourseId());
		 sql.setInteger(answerinfo.getExamId());
	   	 sql.setString(answerinfo.getUserId());

		 log.debug("[editExamAnswerInfo]" + sql.toString());


		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}



	public String getExamRemainTime(String systemcode, CurriMemDTO curriinfo,String courseid,int examid,String userid) throws DAOException{
		 int remainTime = 0;
		 String timeCheckYn = "N";
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select a.flag_time,IFNULL(a.run_time - b.answer_time,0) as remain_time ");
		 sb.append(" from exam_info a, exam_answer b ");
		 sb.append(" where a.system_code = b.system_code and a.curri_code = b.curri_code and a.curri_year = b.curri_year ");
		 sb.append(" and a.curri_term = b.curri_term and a.course_id = b.course_id and a.exam_id = b.exam_id ");
		 sb.append(" and b.system_code = ? and b.curri_code = ? and b.curri_year = ? ");
		 sb.append(" and b.curri_term = ? and b.course_id = ? and b.exam_id = ? and b.user_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);
		 sql.setString(userid);

		 log.debug("[getExamRemainTime]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	remainTime = rs.getInt("remain_time");
			 	timeCheckYn = StringUtil.nvl(rs.getString("flag_time"));
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		 }

		 return timeCheckYn+"/"+remainTime;
	}

	public int getAnswerCnt(String systemcode, CurriMemDTO curriinfo,String courseid,int examid) throws DAOException{
		 int userCnt = 0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(distinct(a.user_id)) as usr_cnt ");
		 sb.append(" from exam_info c,exam_answer a ");
		 sb.append(" where c.system_code = a.system_code ");
		 sb.append(" and c.curri_code = a.curri_code ");
		 sb.append(" and c.curri_year = a.curri_year ");
		 sb.append(" and c.curri_term = a.curri_term ");
		 sb.append(" and c.course_id = a.course_id ");
		 sb.append(" and c.exam_id = a.exam_id ");
		 sb.append(" and c.system_code = ? and c.curri_code = ? and c.curri_year = ? and c.curri_term = ? ");
		 sb.append(" and c.course_id = ? and c.exam_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);

		 log.debug("[getAnswerCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	userCnt = rs.getInt("usr_cnt");
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		 }

		 return userCnt;
	}

	public boolean editExamRetest(String systemcode,CurriMemDTO curriInfo,String courseid,int examid,String userids[],String modid) throws DAOException{
		 boolean retVal 		= 	false;
		 QueryStatement[] sqls 	= 	null;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" UPDATE exam_answer ");
		 sb.append(" SET exam_order='', exam_answer='' " +
		 			",answer='', exam_score='' " +
		 			",answer_score='', total_score=0 " +
		 			",result_check='N', answer_time=0 " +
		 			",start_date='', end_date='' ");
		 sb.append(	",feed_back='', flag_retest='Y' " +
		 			", mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" WHERE system_code = ? AND curri_code = ? AND curri_year = ? AND curri_term = ? ");
		 sb.append(" AND course_id = ? AND exam_id = ? AND user_id = ? ");
	   	 sqls = new QueryStatement[userids.length];

	   	 for(int i=0; i < userids.length; i++){
	   	 	 sqls[i] = new QueryStatement();
	   	 	 sqls[i].setSql(sb.toString());
		   	 sqls[i].setString(modid);
		   	 sqls[i].setString(systemcode);
			 sqls[i].setString(curriInfo.curriCode);
			 sqls[i].setInteger(curriInfo.curriYear);
			 sqls[i].setInteger(curriInfo.curriTerm);
			 sqls[i].setString(courseid);
			 sqls[i].setInteger(examid);
		   	 sqls[i].setString(userids[i]);

		     log.debug("[editExamRetest]" + sqls[i].toString());
	   	 }

		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
}
