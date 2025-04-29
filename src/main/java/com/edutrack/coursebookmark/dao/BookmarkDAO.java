package com.edutrack.coursebookmark.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.coursebookmark.dto.BookmarkDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;

/*
 * @author JamFam
 *
 * 북마크 관련
 */
public class BookmarkDAO extends AbstractDAO {
	/**
	 *
	 */
	public BookmarkDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 북마크 정보를 저장 합니다.
	 * @param systemCode
	 * @param userId
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param contentsId
	 * @param studentYn
	 * @return int
	 * @throws DAOException
	 */
	public int setBookmark(String systemCode, String userId, String curriCode, int curriYear, int curriTerm, String courseId, String contentsId, int studentYn) throws DAOException {
		int retVal =0;
		//---- 수강생이 아닌 경우 바로 리턴
		if(studentYn > 0) {
			QueryStatement sql = new QueryStatement();
			String qry = "";
			String curDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
			if(isBookmark(systemCode, userId, curriCode, curriYear, curriTerm, courseId, contentsId) == 0) {
				//---- 수강생인 경우 북마킹 정보 남김
				qry = "INSERT INTO bookmark" +
						"(system_code, curri_code, curri_year, curri_term" +
						", user_id, course_id, contents_id, contents_order"+
						", open_chk, open_date, quiz_pass, score_raw" +
						", score_max, score_min, total_time, session_time"+
						", join_count, cmi_idref, reg_id, reg_date" +
						", mod_id, mod_date) "+
						"SELECT " +
						"system_code, curri_code, curri_year, curri_term" +
						", ?, course_id, contents_id, contents_order" +
						", 'Y', "+curDate+", case when quiz_count > 0 then 'N' else 'Y' end, null" +
						", null, null, 0, 0" +
						", 1, null, ?, " +curDate+
						", ?,"+curDate+
						"FROM curri_contents " +
						"WHERE system_code=? AND curri_code=? " +
						"AND curri_year=? AND curri_term=? " +
						"AND course_id=? AND contents_id=?";
				sql.setSql(qry);
				sql.setString(userId);
				sql.setString(userId);
				sql.setString(userId);
				sql.setString(systemCode);
				sql.setString(curriCode);
				sql.setInteger(curriYear);
				sql.setInteger(curriTerm);
				sql.setString(courseId);
				sql.setString(contentsId);

				log.debug("수강 정보 생성 : "+ qry);

			}
			else {
				qry = 	" UPDATE bookmark SET " +
						" open_date="+curDate+", join_count = join_count + 1" +
						", mod_id=? , mod_date="+curDate+
						" WHERE " +
						" system_code = ? AND curri_code = ? " +
						" AND curri_year = ? AND curri_term = ? " +
						" AND user_id = ? AND  course_id = ? " +
						" AND contents_id = ? ";
				sql.setSql(qry);
				sql.setString(userId);
				sql.setString(systemCode);
				sql.setString(curriCode);
				sql.setInteger(curriYear);
				sql.setInteger(curriTerm);
				sql.setString(userId);
				sql.setString(courseId);
				sql.setString(contentsId);

				log.debug("수강 정보 수정 : "+ qry);

			}

			try{
				retVal = broker.executeUpdate(sql);
			}catch(Exception e){
				log.error(e.getMessage());
				throw new DAOException(e.getMessage());
			}
		} else {
			retVal = 0;
		}
		return retVal;
	}

	public int isBookmark(String systemCode, String userId, String curriCode, int curriYear, int curriTerm, String courseId, String contentsId) throws DAOException {
		int retVal =0;
		QueryStatement sql = new QueryStatement();
		String qry = "select count(contents_id) as cnt from bookmark where system_code=? and curri_code=? and curri_year=? and curri_term=? and "+
				"user_id=? and  course_id=? and contents_id=?";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(userId);
		sql.setString(courseId);
		sql.setString(contentsId);

		RowSet rs = null;
		try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	retVal = rs.getInt("cnt");
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
		return retVal;
	}

	public int setLearningTime(String systemCode, String userId, String curriCode, int curriYear, int curriTerm, String courseId, String contentsId, String startTime) throws DAOException {
		int retVal = 0;
		//---- 시간차를 구한다.
		QueryStatement sql1 = new QueryStatement();
		String Ymd = startTime.substring(1,8);
		String Hour = startTime.substring(8,10);
		String Min = startTime.substring(10,12);
		String Sec = startTime.substring(12,14);
		//String qry1 = "select round((sysdate-to_date('"+Ymd+Hour+Min+Sec+"','yyyymmddhh24miss'))*24*60,0)  as gap_time from dual ";

		String qry1 = "select (HOUR(CAST(TIMEDIFF(now(), DATE_FORMAT('"+startTime.substring(0,14)+"','%Y-%m-%d %H:%i:%s')) AS CHAR))*60 ";
		qry1 += " + MINUTE(CAST(TIMEDIFF(now(), DATE_FORMAT('"+startTime.substring(0,14)+"','%Y-%m-%d %H:%i:%s')) AS CHAR)) ";
		qry1 += " ) as gap_time from DUAL ";

		sql1.setSql(qry1);

		RowSet Rs1 = null;
		int gapTime = 0;
		try {
			Rs1 = broker.executeQuery(sql1);
			if(Rs1.next()) {
				gapTime = Rs1.getInt("gap_time");
			}
			Rs1.close();
		} catch (Exception e) {
			gapTime = 0;
		}
		//log.debug("gapTime => "+gapTime);
//System.out.println("gapTime => "+gapTime);
		if(gapTime > 0) {
			log.debug("실행 시간 적용");
			QueryStatement sql = new QueryStatement();
			String qry = "update bookmark set total_time = total_time + ? , session_time=? where system_code=? and curri_code=? and curri_year=? and curri_term=? and "+
				"user_id=? and  course_id=? and contents_id=?";
			sql.setSql(qry);
			sql.setInteger(gapTime);
			sql.setInteger(gapTime);
			sql.setString(systemCode);
			sql.setString(curriCode);
			sql.setInteger(curriYear);
			sql.setInteger(curriTerm);
			sql.setString(userId);
			sql.setString(courseId);
			sql.setString(contentsId);
			try{

//System.out.println("[setLearningTime] sql : "+sql.toString());

				retVal = broker.executeUpdate(sql);
			}catch(Exception e){
				log.error(e.getMessage());
				throw new DAOException(e.getMessage());
			}
		}
		return retVal;
	}

	public int isQuizPass(String systemCode, String userId, String curriCode, int curriYear, int curriTerm, String courseId, String contentsId) throws DAOException {
		log.debug("------------- isQuizPass Start ---------------");
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		String qry = "select sum(pass_cnt) as sum_cnt from ("+
					" select case quiz_pass when 'P' then 0 else 1 end as pass_cnt"+
					" from bookmark"+
					" where	contents_order < ("+
					" select contents_order "+
					" from bookmark"+
					" where	system_code=? and curri_code=? and curri_year=? and curri_term=?"+
					" and user_id=? and course_id=? and contents_id=?)) a";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(userId);
		sql.setString(courseId);
		sql.setString(contentsId);

		try {
			RowSet rs = broker.executeQuery(sql);
			if(rs.next()) {
				if(rs.getInt("sum_cnt") > 0) retVal = 0;
				else retVal = 1;
			}
			rs.close();
		} catch (Exception e) {
			retVal = 0;
		}
		log.debug("------------- isQuizPass End   ---------------");
		return retVal;
	}
	/**
	 * 퀴즈 통과 여부를 저장한다.
	 * @param bookmarkDto
	 * @return
	 * @throws DAOException
	 */
	public int editBookmarkQuiz(BookmarkDTO bookmarkDto) throws DAOException {
		int retVal =0;
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("update bookmark set quiz_pass = ? , score_max = ?, score_raw = ?, score_min = ?, mod_id=? ");
			sb.append(", mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
			sb.append(" where system_code=? and curri_code=? and curri_year=? and curri_term=? and  course_id=? ");
			sb.append("and user_id=?  and contents_id=? ");
			sql.setSql(sb.toString());
			sql.setString(bookmarkDto.getQuizPass());
			sql.setInteger(bookmarkDto.getScoreMax());
			sql.setInteger(bookmarkDto.getScoreRaw());
			sql.setInteger(bookmarkDto.getScoreMin());
			sql.setString(bookmarkDto.getModId());
			sql.setString(bookmarkDto.getSystemCode());
			sql.setString(bookmarkDto.getCurriCode());
			sql.setInteger(bookmarkDto.getCurriYear());
			sql.setInteger(bookmarkDto.getCurriTerm());
			sql.setString(bookmarkDto.getCourseId());
			sql.setString(bookmarkDto.getUserId());
			sql.setString(bookmarkDto.getContentsId());
			log.debug("editBookmarkQuiz : "+ sql.toString());

			try{
				retVal = broker.executeUpdate(sql);
			}catch(Exception e){
				log.error(e.getMessage());
				throw new DAOException(e.getMessage());
			}

		return retVal;
	}
	/**
	 * 북마크 정보를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getBookmark(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String UserId, String ContentsId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select  system_code, curri_code, curri_year, curri_term, user_id, course_id, contents_id");
		sb.append(", contents_order, open_chk, open_date, quiz_pass, score_raw, score_max, score_min, total_time");
		sb.append(", session_time, join_count, cmi_idref, reg_id, reg_date, mod_id, mod_date ");
		sb.append(" from bookmark");
		sb.append(" where system_code=? and curri_code=? and curri_year=? and curri_term=? and  course_id=? ");
		sb.append("and user_id=?  and contents_id=? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(UserId);
		sql.setString(ContentsId);
		log.debug("[getBookmark]" + sql.toString());
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
}