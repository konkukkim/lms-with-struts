package com.edutrack.curritop.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.curritop.dto.QuizDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/*
 * @author Bschoi
 *
 * 목차 관리
 */
public class QuizDAO extends AbstractDAO {

	/**
	 * 퀴즈 목차 번호 최대값을 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxQuizOrder(String SystemCode, String CourseId,String ContentsId) throws DAOException{
		 int  maxOrder = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(quiz_order),0)+1 as max_order ");
		 sb.append(" from quiz ");
		 sb.append(" where system_code = ? and course_id = ? and contents_id = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CourseId);
		 sql.setString(ContentsId);
		 log.debug("[getMaxQuizOrder]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	maxOrder = rs.getInt("max_order");
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

		 return maxOrder;
	}

	/**
	 * 목차의 전체 퀴즈 수를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getTotalQuizCnt(String SystemCode, String CourseId, String ContentsId) throws DAOException{
		 int  totalCnt = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(quiz_order),0) as totalCnt ");
		 sb.append(" from quiz ");
		 sb.append(" where system_code = ? and course_id = ? and contents_id = ?");
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CourseId);
		 sql.setString(ContentsId);
		 log.debug("[getTotalQuizCnt]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	totalCnt = rs.getInt("totalCnt");
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

		 return totalCnt;
	}



	/**
	 * 목차의 퀴즈 리스트전체를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @param ContentsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getQuizList(String SystemCode, String CourseId, String ContentsId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, course_id, contents_id, quiz_order, quiz_type, contents_text, example1, example2");
		sb.append(",example3, example4, example5, answer, quiz_comment, rfile_name, sfile_name, file_path, file_size");
		sb.append(" from quiz ");
		sb.append(" where system_code=? and course_id=? and contents_id = ? order by quiz_order");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		log.debug("[getQuizList]" + sql.toString());
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	/**
	 * 개별 Quiz 정보를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizOrder
	 * @return RowSet
	 * @throws DAOException
	 */
	public QuizDTO getQuiz(String SystemCode, String CourseId, String ContentsId, int QuizOrder) throws DAOException {
		QuizDTO quizDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select " +
					"system_code, course_id, contents_id, quiz_order" +
					", quiz_type, contents, contents_text, example1");
		sb.append(	", example2 ,example3, example4, example5, answer, quiz_comment");
		sb.append(	", rfile_name, sfile_name, file_path, file_size " +
					"from quiz ");
		sb.append(	" where system_code=? and course_id=? and contents_id = ? and quiz_order = ? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		sql.setInteger(QuizOrder);

		log.debug("[getQuiz]" + sql.toString());
		 ResultSet rs = null;
//		 Connection conn = null;
//	     DBConnectionManager pool = null;
	     StringBuffer output = new StringBuffer();
		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if(rs.next()){
				quizDto = new QuizDTO();
				quizDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				quizDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				quizDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
				quizDto.setQuizOrder(rs.getInt("quiz_order"));
				quizDto.setQuizType(StringUtil.nvl(rs.getString("quiz_type")));
				quizDto.setContents(StringUtil.nvl(rs.getString("contents")));
				quizDto.setContentsText(StringUtil.nvl(rs.getString("contents_text")));
				quizDto.setExample1(StringUtil.nvl(rs.getString("example1")));
				quizDto.setExample2(StringUtil.nvl(rs.getString("example2")));
				quizDto.setExample3(StringUtil.nvl(rs.getString("example3")));
				quizDto.setExample4(StringUtil.nvl(rs.getString("example4")));
				quizDto.setExample5(StringUtil.nvl(rs.getString("example5")));
				quizDto.setAnswer(StringUtil.nvl(rs.getString("answer")));
				quizDto.setComment(StringUtil.nvl(rs.getString("quiz_comment")));
				quizDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				quizDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				quizDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				quizDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        quizDto.setContents(StringUtil.nvl(output.toString()));
//		        output.delete(0,output.length());
			}
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
//			    if(conn != null){
//			        conn.setAutoCommit( true );
//			        pool.freeConnection(conn); // 컨넥션 해제
//			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }
		return quizDto;
	}


	/**
	 * 개별 컨텐츠 정보를 저장한다.
	 * @param quizDto
	 * @return int
	 * @throws DAOException
	 */
	public int addQuiz(QuizDTO quizDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into quiz(");
		sb.append("system_code, course_id, contents_id, quiz_order, quiz_type, contents, contents_text, example1, example2,example3");
		sb.append(", example4, example5, answer, quiz_comment, rfile_name, sfile_name, file_path, file_size, reg_id, reg_date)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.

		sql.setString(quizDto.getSystemCode());
		sql.setString(quizDto.getCourseId());
		sql.setString(quizDto.getContentsId());
		sql.setInteger(quizDto.getQuizOrder());
		sql.setString(quizDto.getQuizType());
		sql.setString(quizDto.getContents());
		sql.setString(quizDto.getContentsText());
		sql.setString(quizDto.getExample1());
		sql.setString(quizDto.getExample2());
		sql.setString(quizDto.getExample3());
		sql.setString(quizDto.getExample4());
		sql.setString(quizDto.getExample5());
		sql.setString(quizDto.getAnswer());
		sql.setString(quizDto.getComment());
		sql.setString(quizDto.getRfileName());
		sql.setString(quizDto.getSfileName());
		sql.setString(quizDto.getFilePath());
		sql.setString(quizDto.getFileSize());
		sql.setString(quizDto.getRegId());

		log.debug("[addQuiz]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

//		Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select contents from quiz ");
//			 sb2.append(" where system_code=? and course_id=? and contents_id=? and quiz_order = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(quizDto.getSystemCode());
//			 sql2.setString(quizDto.getCourseId());
//			 sql2.setString(quizDto.getContentsId());
//			 sql2.setInteger(quizDto.getQuizOrder());
//			 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	            CLOB clob = (CLOB)rs.getClob("CONTENTS");
//           		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(quizDto.getContents().toCharArray());
//
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//	     	rs.close();
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		return retVal;
	}

	/**
	 * 개별 Quiz 정보를 수정한다.
	 * @param quizDto
	 * @return	int
	 * @throws DAOException
	 */
	public int editQuiz(QuizDTO quizDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE quiz SET " +
							"quiz_type=?, contents=?" +
							", contents_text=?, example1=?" +
							", example2=?,example3=?" +
							", example4=?, example5=?");
		sb.append(			", answer=?, quiz_comment=?" +
							", rfile_name=?, sfile_name=?" +
							", file_path=?, file_size=?" +
							", mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(			" WHERE system_code=? and course_id=? and contents_id=? and quiz_order = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.

		sql.setString(quizDto.getQuizType());
		sql.setString(quizDto.getContents());
		sql.setString(quizDto.getContentsText());
		sql.setString(quizDto.getExample1());
		sql.setString(quizDto.getExample2());
		sql.setString(quizDto.getExample3());
		sql.setString(quizDto.getExample4());
		sql.setString(quizDto.getExample5());
		sql.setString(quizDto.getAnswer());
		sql.setString(quizDto.getComment());
		sql.setString(quizDto.getRfileName());
		sql.setString(quizDto.getSfileName());
		sql.setString(quizDto.getFilePath());
		sql.setString(quizDto.getFileSize());
		sql.setString(quizDto.getModId());
		sql.setString(quizDto.getSystemCode());
		sql.setString(quizDto.getCourseId());
		sql.setString(quizDto.getContentsId());
		sql.setInteger(quizDto.getQuizOrder());

		log.debug("[editQuiz]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

//		Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select contents from quiz ");
//			 sb2.append(" where system_code=? and course_id=? and contents_id=? and quiz_order = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(quizDto.getSystemCode());
//			 sql2.setString(quizDto.getCourseId());
//			 sql2.setString(quizDto.getContentsId());
//			 sql2.setInteger(quizDto.getQuizOrder());
//			 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(quizDto.getContents().toCharArray());
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		return retVal;
	}

	/**
	 * 개별 Quiz 정보를 삭제한다.
	 * @param SystemCode
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizOrder
	 * @return int
	 * @throws DAOException
	 */
	public int delQuiz(String SystemCode, String CourseId, String ContentsId, int QuizOrder) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from quiz where system_code=? and course_id=? and contents_id = ? and quiz_order = ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		sql.setInteger(QuizOrder);

		log.debug("[delQuiz]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 * 단원평가 첨부 파일을 삭제한다.
	 * @param SystemCode
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizOrder
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int delQuizFile(String SystemCode, String CourseId, String ContentsId, int QuizOrder, String UserId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update quiz set rfile_name='', sfile_name='', file_path='', file_size='', mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where system_code=? and course_id=? and contents_id=? and quiz_order = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(UserId);
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		sql.setInteger(QuizOrder);

		log.debug("[delQuizFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}