package com.edutrack.currisub.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamContentsDTO;
import com.edutrack.currisub.dto.CurriQuizDTO;
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
public class CurriQuizDAO extends AbstractDAO {

	/**
	 * 퀴즈 목차 번호 최대값을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCurriQuizOrder(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId,String ContentsId) throws DAOException{
		 int  maxOrder = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(quiz_order),0)+1 as max_order ");
		 sb.append(" from curri_quiz ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and contents_id = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setString(ContentsId);
		 log.debug("[getMaxCurriQuizOrder]" + sql.toString());

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
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getTotalCurriQuizCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId) throws DAOException{
		 int  totalCnt = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(quiz_order),0) as totalCnt ");
		 sb.append(" from curri_quiz ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		 if(!ContentsId.equals(""))
		 	sb.append(" and contents_id = ?");
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 if(!ContentsId.equals(""))
		 	sql.setString(ContentsId);
		 log.debug("[getTotalCurriQuizCnt]" + sql.toString());
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
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriQuizList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, course_id, contents_id, quiz_order, quiz_type, contents_text, example1, example2");
		sb.append(",example3, example4, example5, answer, quiz_comment, rfile_name, sfile_name, file_path, file_size");
		sb.append(" from curri_quiz ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and course_id=? and contents_id = ? order by quiz_order");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		log.debug("[getCurriQuizList]" + sql.toString());
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
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizOrder
	 * @return
	 * @throws DAOException
	 */
	public CurriQuizDTO getCurriQuiz(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId, int QuizOrder) throws DAOException {
		CurriQuizDTO quizDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, course_id, contents_id, quiz_order, quiz_type, contents, contents_text, example1, example2");
		sb.append(",example3, example4, example5, answer, quiz_comment, rfile_name, sfile_name, file_path, file_size");
		sb.append(" from curri_quiz ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and course_id=? and contents_id = ? and quiz_order = ? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		sql.setInteger(QuizOrder);

		log.debug("[getCurriQuiz]" + sql.toString());
		ResultSet rs = null;
//		 Connection conn = null;
//	     DBConnectionManager pool = null;
	     StringBuffer output = new StringBuffer();
		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
//			rs = broker.executeQuery(sql,conn);
			rs = broker.executeQuery(sql);
			if(rs.next()){
				quizDto = new CurriQuizDTO();
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
	 * @param curriQuizDto
	 * @return int
	 * @throws DAOException
	 */
	public int addCurriQuiz(CurriQuizDTO quizDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	" INSERT INTO curri_quiz ");
		sb.append(	" (system_code, curri_code, curri_year, curri_term" +
					", course_id, contents_id, quiz_order, quiz_type" +
					", contents, contents_text, example1, example2" +
					", example3, example4, example5, answer" +
					", quiz_comment, rfile_name, sfile_name, file_path" +
					", file_size, reg_id, reg_date) ");
		sb.append(	" VALUES (?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.

		sql.setString(quizDto.getSystemCode());
		sql.setString(quizDto.getCurriCode());
		sql.setInteger(quizDto.getCurriYear());
		sql.setInteger(quizDto.getCurriTerm());
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

		log.debug("[addCurriQuiz]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}


		return retVal;
	}


	/**
	 * 개별 CurriQuiz 정보를 수정한다.
	 * @param curriQuizDto
	 * @return	int
	 * @throws DAOException
	 */
	public int editCurriQuiz(CurriQuizDTO quizDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE curri_quiz SET quiz_type=?, contents=?" +
										", contents_text=?, example1=?" +
										", example2=?,example3=?" +
										", example4=?, example5=?");
		sb.append(						", answer=?, quiz_comment=?" +
										", rfile_name=?, sfile_name=?" +
										", file_path=?, file_size=?" +
										", mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(	" WHERE system_code=? and curri_code = ? " +
					" and curri_year = ? and curri_term = ? " +
					" and course_id=? and contents_id=? " +
					" and quiz_order = ? ");
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
		sql.setString(quizDto.getCurriCode());
		sql.setInteger(quizDto.getCurriYear());
		sql.setInteger(quizDto.getCurriTerm());
		sql.setString(quizDto.getCourseId());
		sql.setString(quizDto.getContentsId());
		sql.setInteger(quizDto.getQuizOrder());

		log.debug("[editCurriQuiz]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}


	/**
	 * 개별 Quiz 정보를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizOrder
	 * @return
	 * @throws DAOException
	 */

	public int delCurriQuiz(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId, int QuizOrder) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	"DELETE FROM curri_quiz " +
					"WHERE system_code=? and curri_code = ? " +
					"and curri_year = ? and curri_term = ? " +
					"and course_id=? and contents_id = ? " +
					"and quiz_order = ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		sql.setInteger(QuizOrder);

		log.debug("[delCurriQuiz]" + sql.toString());
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
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizOrder
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int delCurriQuizFile(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId, int QuizOrder, String UserId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE curri_quiz SET rfile_name='', sfile_name=''" +
					", file_path='', file_size=''" +
					", mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" WHERE system_code=? and curri_code = ? " +
					"and curri_year = ? and curri_term = ? " +
					"and course_id=? and contents_id=? " +
					"and quiz_order = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(UserId);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsId);
		sql.setInteger(QuizOrder);

		log.debug("[delCurriQuizFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 * 단원평가(퀴즈) 이월
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int addCurriQuizAuto(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String UserId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO curri_quiz(");
		sb.append(	" system_code, curri_code, curri_year, curri_term" +
					", course_id, contents_id, quiz_order, quiz_type" +
					", contents, contents_text, example1, example2" +
					", example3, example4, example5, answer");
		sb.append(	", quiz_comment, rfile_name, sfile_name, file_path" +
					", file_size, reg_id, reg_date) ");
		sb.append(" SELECT " +
						" system_code, ?, ?, ?" +
						", course_id, contents_id, quiz_order, quiz_type" +
						", contents, contents_text, example1, example2" +
						",example3, example4, example5, answer");
		sb.append(		", quiz_comment, rfile_name, sfile_name, file_path" +
						", file_size, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" FROM quiz");
		sb.append(" WHERE system_code = ? AND course_id = ?");
		sql.setSql(sb.toString());

		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(UserId);
		sql.setString(SystemCode);
		sql.setString(CourseId);

		log.debug("[addCurriQuizAuto]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
			retVal = 1;
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * Quiz 만큼 단원평가 문제를 불러온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param courseid
	 * @param contentsId
	 * @param QuizCnt
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getRandQuizList(String systemcode, CurriMemDTO curriinfo,String courseid,String contentsId,int QuizCnt) throws DAOException{
		ArrayList quizList = new ArrayList();
		CurriQuizDTO quizDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, course_id, contents_id, quiz_order, quiz_type, contents, contents_text, example1, example2");
		sb.append(",example3, example4, example5, answer, quiz_comment, rfile_name, sfile_name, file_path, file_size");
		sb.append(" from ( ");
			sb.append("select system_code, course_id, contents_id, quiz_order, quiz_type, contents, contents_text, example1, example2");
			sb.append(",example3, example4, example5, answer, quiz_comment, rfile_name, sfile_name, file_path, file_size");
			sb.append(" from curri_quiz ");
			sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and course_id=? and contents_id = ?");
			sb.append(" order by  dbms_random.value ");
		//sb.append(" ) where limit "+QuizCnt+"");
		sb.append(" ) limit "+QuizCnt+"");


		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curriinfo.curriCode);
		sql.setInteger(curriinfo.curriYear);
		sql.setInteger(curriinfo.curriTerm);
		sql.setString(courseid);
		sql.setString(contentsId);

		log.debug("[getRandQuizList]" + sql.toString());
		ResultSet rs = null;
	    StringBuffer output = new StringBuffer();
		try{
			rs = broker.executeQuery(sql);
			while(rs.next()){
				quizDto = new CurriQuizDTO();
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

				quizList.add(quizDto);
			}
			rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
		 	try{
				if(rs != null) rs.close();

			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }

		 return quizList;
	}


}