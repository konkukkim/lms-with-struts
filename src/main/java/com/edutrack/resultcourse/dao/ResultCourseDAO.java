/*
 * Created on 2004. 10. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.resultcourse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dto.CurriCourseListDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.resultcourse.dto.ResultCourseDTO;
import com.edutrack.resultcourse.dto.ResultScoreDTO;

/**
 * @author bschoi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResultCourseDAO extends AbstractDAO {
	/**
	 * 성적 등록된 학생수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int getResultCourseCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String UserId) throws DAOException{
		 int  totalCnt = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(user_id),0) as totalCnt ");
		 sb.append(" from result_course ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		 if(!UserId.equals(""))
		 	sb.append(" and user_id = ?");
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 if(!UserId.equals(""))
		 	sql.setString(UserId);
		 log.debug("[getResultCourseCnt]" + sql.toString());
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
	 * 성적 테이블 데이터 자동 입력/수정
	 * 학생이 성적테이블에 있는지 확인 , 없으면 Add 있으면 Edit
	 * @param resultCourse
	 * @return
	 * @throws DAOException
	 */
	public int registResultCourse(ResultCourseDTO resultCourse) throws DAOException{
		int retVal = 0;
		int chkRegist = 0;
		String 	systemCode 	= 	resultCourse.getSystemCode();
		String 	curriCode 	= 	resultCourse.getCurriCode();
		int		curriYear	=	resultCourse.getCurriYear();
		int 	curriTerm	=	resultCourse.getCurriTerm();
		String	courseId	=	resultCourse.getCourseId();
		String	userId		=	resultCourse.getUserId();
		//-- 학생이 성적테이블에 있는지 확인 , 없으면 Add 있으면 Edit
		chkRegist = getResultCourseCnt(systemCode,curriCode,curriYear,curriTerm,courseId,userId);
		if(chkRegist > 0)
			retVal = editResultCourse(resultCourse);
		else
			retVal = addResultCourse(resultCourse);

		return retVal;
	}

	/**
	 * 성적 정보를 등록 한다.
	 * @param resultCourse
	 * @return
	 * @throws DAOException
	 */
	public int addResultCourse(ResultCourseDTO resultCourse) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into result_course(system_code, curri_code, curri_year, curri_term, course_id, user_id");
		sb.append(", score_exam, score_report, score_attend, score_forum, score_etc1, score_etc2, score_total");
		sb.append(", reg_id, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		sql.setSql(sb.toString());

		sql.setString(resultCourse.getSystemCode());
		sql.setString(resultCourse.getCurriCode());
		sql.setInteger(resultCourse.getCurriYear());
		sql.setInteger(resultCourse.getCurriTerm());
		sql.setString(resultCourse.getCourseId());
		sql.setString(resultCourse.getUserId());
		sql.setDouble(resultCourse.getScoreExam());
		sql.setDouble(resultCourse.getScoreReport());
		sql.setDouble(resultCourse.getScoreAttend());
		sql.setDouble(resultCourse.getScoreForum());
		sql.setDouble(resultCourse.getScoreEtc1());
		sql.setDouble(resultCourse.getScoreEtc2());
		sql.setDouble(resultCourse.getScoreTotal());
		sql.setString(resultCourse.getRegId());

		log.debug("[addResultCourse]" + sql.toString());
		try{
		     retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		}

		 return retVal;
		}
	/**
	 * 성적 정보를 수정한다.
	 * @param resultCourse
	 * @return
	 * @throws DAOException
	 */
	public int editResultCourse(ResultCourseDTO resultCourse) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update result_course set score_exam = ? , score_report = ? , score_attend = ? ");
		sb.append(", score_forum = ? , score_etc1 = ? , score_etc2 = ? , score_total = ?");
		sb.append(", mod_id = ?, reg_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and user_id = ? ");
		sql.setSql(sb.toString());

		sql.setDouble(resultCourse.getScoreExam());
		sql.setDouble(resultCourse.getScoreReport());
		sql.setDouble(resultCourse.getScoreAttend());
		sql.setDouble(resultCourse.getScoreForum());
		sql.setDouble(resultCourse.getScoreEtc1());
		sql.setDouble(resultCourse.getScoreEtc2());
		sql.setDouble(resultCourse.getScoreTotal());
		sql.setString(resultCourse.getModId());
		sql.setString(resultCourse.getSystemCode());
		sql.setString(resultCourse.getCurriCode());
		sql.setInteger(resultCourse.getCurriYear());
		sql.setInteger(resultCourse.getCurriTerm());
		sql.setString(resultCourse.getCourseId());
		sql.setString(resultCourse.getUserId());

		log.debug("[editResultCourse]" + sql.toString());
		try{
		     retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		}

		 return retVal;
		}


	/**
	 * 성적현황 리스트를 페이징객체로 가져온다.
	 * @param curpage
	 * @param systemcode
	 * @param op
	 * @param search
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getResultCoursePagingList(int curpage, String systemcode,String CurriCode, int CurriYear, int CurriTerm, String CourseId, String op, String search, String orderBy) throws DAOException{
		ListDTO retVal 		= 	null;
		try{
			ListStatement sql = new ListStatement();

			sql.setTotalCol("s.user_id");
			sql.setCutCol("s.user_id");
			sql.setAlias(	"user_id,user_name,score_exam, score_report" +
							", score_attend, score_forum,  score_etc1, score_etc2" +
							", score_total, get_credit, grade, enroll_status ");

			sql.setSelect(	"s.user_id, u.user_name, ifnull(r.score_exam,0) as score_exam, ifnull(r.score_report,0) as score_report" +
							", ifnull(r.score_attend,0) as score_attend, ifnull(r.score_forum,0) as score_forum, ifnull(r.score_etc1,0) as score_etc1, ifnull(r.score_etc2,0) as score_etc2" +
							", round(ifnull(r.score_total,0),1) as score_total, s.get_credit, s.grade, s.enroll_status ");

			sql.setFrom(	" users u, student s left outer join result_course r " +
							" on s.system_code = r.system_code and  s.curri_code = r.curri_code " +
							" and s.curri_year = r.curri_year and  s.curri_term = r.curri_term " +
							" and s.user_id = r.user_id and r.course_id = ? ");

			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(	" s.system_code=u.system_code and s.user_id=u.user_id " +
							" and (s.enroll_status='S' or s.enroll_status='C' or s.enroll_status='F') ");
			sbWhere.append(	" and s.system_code = ? and s.curri_code = ? " +
							" and s.curri_year = ? and s.curri_term = ? ");
			sql.setString(CourseId);
			sql.setString(systemcode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);

			if (!search.equals("")) {
				sbWhere.append(" and "+op+" like ? ");
				sql.setString("%"+search+"%");
			}
			sql.setWhere(sbWhere.toString());

			if(orderBy!=null && ("").equals(orderBy)){
				sql.setOrderby(" u.user_name asc ");
			}else{
				sql.setOrderby(orderBy);
			}

			log.debug("[ResultCoursePagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 * 성적현황 리스트
	 * @param systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getResultCourseList(String systemcode,String CurriCode, int CurriYear, int CurriTerm,String CourseId, String UserId, String orderBy) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(	" SELECT " +
		 				"s.user_id, u.user_name, IFNULL(r.score_exam,0) as score_exam, IFNULL(r.score_report,0) as score_report" +
		 				", IFNULL(r.score_attend,0) as score_attend, IFNULL(r.score_forum,0) as score_forum, IFNULL(r.score_etc1,0) as score_etc1, IFNULL(r.score_etc2,0) as score_etc2" +
		 				", ROUND(ifnull(r.score_total,0),1) as score_total, s.SERVICESTART_DATE, s.SERVICEEND_DATE, s.get_credit, s.grade, s.enroll_status ");
		 sb.append(	" FROM users u, student s LEFT OUTER JOIN result_course r " +
					" ON s.system_code = r.system_code AND  s.curri_code = r.curri_code " +
					" AND s.curri_year = r.curri_year AND  s.curri_term = r.curri_term " +
					" AND s.user_id = r.user_id  AND r.course_id = ? ");
		 sb.append(	" WHERE s.system_code=u.system_code AND s.user_id=u.user_id " +
		 			" AND (s.enroll_status='S' OR s.enroll_status='C' OR s.enroll_status='F')" +
					" AND s.system_code = ? AND s.curri_code = ? " +
					" AND s.curri_year = ? AND s.curri_term = ? ");
		 if(!UserId.equals(""))  sb.append(" AND s.user_id = ?");

		 if(orderBy!=null && ("").equals(orderBy)){
		 	sb.append(" ORDER BY u.user_name asc ");
		 }else{
		 	sb.append(" ORDER BY " + orderBy );
		 }

		 sql.setSql(sb.toString());
		 sql.setString(CourseId);
		 sql.setString(systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 if(!UserId.equals("")) sql.setString(UserId);
		 log.debug("[getResultCourseList]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 //-- rs.close() 는 반드시 JSP 에서 해 줄
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }


		 return rs;
	}
	/**
	 * 개별 학생의 토론 점수 현황 리스트를 가져온다.
	 * @param systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return	ArrayList
	 * @throws DAOException
	 */
	public ArrayList getStudentForumScoreList(String systemcode,String CurriCode, int CurriYear, int CurriTerm,String CourseId, String UserId) throws DAOException{
		ArrayList scoreList = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT  " +
		 				" fi.forum_id,fi.subject,fi.start_date,fi.end_date" +
		 				", fi.forum_score, ifnull(fu.score,0) as result_score ");
		 sb.append(" FROM course_forum_info fi LEFT OUTER JOIN course_forum_user fu ");
		 sb.append(								" ON fi.system_code = fu.system_code and fi.curri_code = fu.curri_code " +
		 										" and fi.curri_year = fu.curri_year and fi.curri_term = fu.curri_term ");
		 sb.append(								" and fi.course_id = fu.course_id and fi.forum_id=fu.forum_id " +
		 										" and fu.user_id=? ");
		 sb.append(	" WHERE fi.system_code=? AND fi.curri_code = ? " +
		 			" AND fi.curri_year=? AND fi.curri_term=? " +
		 			" AND fi.course_id=? ");
		 sb.append(	" AND fi.regist_yn='Y' AND fi.parent_forum_id=0 ");
		 sb.append(	" ORDER BY fi.start_date ");
		 sql.setSql(sb.toString());
		 sql.setString(UserId);
		 sql.setString(systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getStudentForumScoreList]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	ResultScoreDTO resultDto =	null;
			scoreList = new ArrayList();

			 while(rs.next()){
			 	resultDto = new ResultScoreDTO();
			 	resultDto.setScoreId(StringUtil.nvl(String.valueOf(rs.getInt("forum_id"))));
			 	resultDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	resultDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	resultDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	resultDto.setBaseScore(rs.getInt("forum_score"));
			 	resultDto.setResultScore(rs.getInt("result_score"));
			 	scoreList.add(resultDto);
			 }
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
		 return scoreList;
		}
	/**
	 * 개별 학생의 과제 점수 현황 리스트를 가져온다.
	 * @param systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return	ArrayList
	 * @throws DAOException
	 */
	/*
	public ArrayList getStudentReportScoreList(String systemcode,String CurriCode, int CurriYear, int CurriTerm,String CourseId, String UserId) throws DAOException{
		ArrayList scoreList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select  ri.report_id,ri.subject,ri.start_date,ri.end_date, ri.report_score, ifnull(rs.score,0) as result_score ");
		 sb.append(" from report_info ri left outer join report_send rs ");
		 sb.append(" on ri.system_code = rs.system_code and ri.curri_code = rs.curri_code and ri.curri_year = rs.curri_year ");
		 sb.append(" and ri.curri_term = rs.curri_term and ri.course_id = rs.course_id and ri.report_id=rs.report_id and rs.user_id=? ");
		 sb.append(" where ri.system_code=? and ri.curri_code = ? and ri.curri_year=? and ri.curri_term=? and ri.course_id=? ");
		 sb.append(" and ri.regist_yn='Y' and ri.parent_report_id=0 ");
		 sb.append(" order by ri.start_date ");
		 sql.setSql(sb.toString());
		 sql.setString(UserId);
		 sql.setString(systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getStudentReportScoreList]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	ResultScoreDTO resultDto =	null;
			scoreList = new ArrayList();

			 while(rs.next()){
			 	resultDto = new ResultScoreDTO();
			 	resultDto.setScoreId(StringUtil.nvl(String.valueOf(rs.getInt("report_id"))));
			 	resultDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	resultDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	resultDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	resultDto.setBaseScore(rs.getInt("report_score"));
			 	resultDto.setResultScore(rs.getInt("result_score"));
			 	scoreList.add(resultDto);
			 }
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
		 return scoreList;
		}
	*/

	/**
	 * 개별 학생의 과제 점수 현황 리스트를 가져온다.
	 * @param systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getStudentReportScoreList(String systemcode,String CurriCode, int CurriYear, int CurriTerm,String CourseId, String UserId) throws DAOException{
		ArrayList scoreList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT " +
		 				" ei.report_id,ei.report_subject,ei.report_start_date,ei.report_end_date" +
		 				", 100 as report_score, ifnull(ea.score,0) as result_score ");
		 sb.append(" FROM report_info ei LEFT OUTER JOIN report_send ea ");
		 sb.append(						" ON ei.system_code = ea.system_code and ei.curri_code = ea.curri_code " +
		 								" and ei.curri_year = ea.curri_year and ei.curri_term = ea.curri_term ");
		 sb.append(						" and ei.course_id = ea.course_id and ei.report_id=ea.report_id " +
		 								" and ea.user_id=? ");
		 sb.append(	" WHERE ei.system_code=? AND ei.curri_code = ? " +
		 			" AND ei.curri_year=? AND ei.curri_term=? " +
		 			" AND ei.course_id=? ");
		 sb.append(	" AND ei.report_regist_yn='Y' ");	//AND ei.report_type='Y'
		 sb.append(	" ORDER BY ei.report_start_date ");
		 sql.setSql(sb.toString());
		 sql.setString(UserId);
		 sql.setString(systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getStudentReportScoreList]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	ResultScoreDTO resultDto =	null;
			scoreList = new ArrayList();

			 while(rs.next()){
			 	resultDto = new ResultScoreDTO();
			 	resultDto.setScoreId(StringUtil.nvl(String.valueOf(rs.getInt("report_id"))));
			 	resultDto.setSubject(StringUtil.nvl(rs.getString("report_subject")));
			 	resultDto.setStartDate(StringUtil.nvl(rs.getString("report_start_date")));
			 	resultDto.setEndDate(StringUtil.nvl(rs.getString("report_end_date")));
			 	resultDto.setBaseScore(rs.getInt("report_score"));
			 	resultDto.setResultScore(rs.getInt("result_score"));
			 	scoreList.add(resultDto);
			 }
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
		 return scoreList;
	}

	/**
	 * 개별 학생의 시험 점수 현황 리스트를 가져온다.
	 * @param systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getStudentExamScoreList(String systemcode,String CurriCode, int CurriYear, int CurriTerm,String CourseId, String UserId) throws DAOException{
		ArrayList scoreList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select " +
		 				"ei.exam_id,ei.subject,ei.start_date,ei.end_date" +
		 				",100 as exam_score, ifnull(ea.total_score,0) as result_score ");
		 sb.append(" from exam_info ei left outer join exam_answer ea ");
		 sb.append(						" on ei.system_code = ea.system_code and ei.curri_code = ea.curri_code " +
		 								" and ei.curri_year = ea.curri_year and ei.curri_term = ea.curri_term ");
		 sb.append(						" and ei.course_id = ea.course_id and ei.exam_id=ea.exam_id " +
		 								" and ea.user_id=? ");
		 sb.append(	" where ei.system_code=? and ei.curri_code = ? " +
		 			" and ei.curri_year=? and ei.curri_term=? " +
		 			" and ei.course_id=? ");
		 sb.append(	" and ei.flag_use='Y' and ei.exam_type='Y' ");
		 sb.append(" order by ei.start_date ");
		 sql.setSql(sb.toString());
		 sql.setString(UserId);
		 sql.setString(systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getStudentExamScoreList]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	ResultScoreDTO resultDto =	null;
			scoreList = new ArrayList();

			 while(rs.next()){
			 	resultDto = new ResultScoreDTO();
			 	resultDto.setScoreId(StringUtil.nvl(String.valueOf(rs.getInt("exam_id"))));
			 	resultDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	resultDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	resultDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	resultDto.setBaseScore(rs.getInt("exam_score"));
			 	resultDto.setResultScore(rs.getInt("result_score"));
			 	scoreList.add(resultDto);
			 }
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
		 return scoreList;
	}

	/**
	 * 과목별 성적현황을 가져온다.
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public ResultCourseDTO getMainCourseResultView(String systemcode, String curricode, int curriyear, int curriterm, String userid) throws DAOException{
		ResultCourseDTO resultcourseDto	= 	new ResultCourseDTO();

	 	//과목 정보 가져오기
		CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();
		CurriCourseListDTO courseinfo = null;

		ArrayList courselist = curriSubCourseDao.getCurriCourseList(systemcode, curricode, curriyear, curriterm, "");
		int size = courselist.size();

		String[] courseId1 = new String[size];
		int[] baseExam = new int[size];
		int[] baseReport = new int[size];
		int[] baseAttend = new int[size];
		int[] baseForum = new int[size];
		int[] baseEtc1 = new int[size];
		int[] baseEtc2 = new int[size];
		double[] scoreExam1 = new double[size];
		double[] scoreReport1 = new double[size];
		double[] scoreAttend1 = new double[size];
		double[] scoreForum1 = new double[size];
		double[] scoreEtc11 = new double[size];
		double[] scoreEtc21 = new double[size];
		double[] scoreTotal1 = new double[size];
		int[] totCnt = new int[size];
	 	int k = 0;

	 	QueryStatement sql = null;
		StringBuffer sb = null;
		RowSet rs 	= 	null;
		try{

			 for(int i=0; i < size; i++){
				 sql = new QueryStatement();
				 sb = new StringBuffer();
				 k++;

				 courseinfo = (CurriCourseListDTO)courselist.get(i);

				 sb.append(" select a.course_id, ifnull(r.score_exam,0) as score_exam, ifnull(r.score_report,0) as score_report, ");
				 sb.append(" ifnull(r.score_attend,0) as score_attend, ifnull(r.score_forum,0) as score_forum, ifnull(r.score_etc1,0) as score_etc1,  ");
				 sb.append(" ifnull(r.score_etc2,0) as score_etc2, ifnull(r.score_total,0) as score_total, ");
				 sb.append(" a.exam_base, a.report_base,a.attend_base, a.forum_base, a.etc1_base, a.etc2_base ");
				 sb.append(" from users u, student s left outer join curri_sub_course a  ");
				 sb.append(" on a.system_code = s.system_code and  a.curri_code = s.curri_code and a.curri_year = s.curri_year ");
				 sb.append(" and  a.curri_term = s.curri_term and  a.course_id = ? ");
				 sb.append(" left outer join result_course r on s.system_code = r.system_code  ");
				 sb.append(" and  s.curri_code = r.curri_code and s.curri_year = r.curri_year and  s.curri_term = r.curri_term  ");
				 sb.append(" and s.user_id = r.user_id  and a.course_id = r.course_id  ");
				 sb.append(" where s.system_code=u.system_code and s.user_id=u.user_id and (s.enroll_status='S' or s.enroll_status='C') ");
				 sb.append(" and s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term = ? and s.user_id = ?  ");

				 sql.setSql(sb.toString());
				 sql.setString(courseinfo.getCourseId());
				 sql.setString(systemcode);
				 sql.setString(curricode);
				 sql.setInteger(curriyear);
				 sql.setInteger(curriterm);
				 sql.setString(userid);

				 log.debug("[getMainCourseResultView]" + sql.toString());
				 rs = broker.executeQuery(sql);
				 if(rs.next()){
				 	courseId1[i]=StringUtil.nvl(rs.getString("course_id"));
					scoreExam1[i] = rs.getDouble("score_exam");
					scoreReport1[i] = rs.getDouble("score_report");
					scoreAttend1[i] = rs.getDouble("score_attend");
					scoreForum1[i] = rs.getDouble("score_forum");
					scoreEtc11[i] = rs.getDouble("score_etc1");
					scoreEtc21[i] = rs.getDouble("score_etc2");
					scoreTotal1[i] = rs.getDouble("score_total");
					baseExam[i] = rs.getInt("exam_base");
					baseReport[i] = rs.getInt("report_base");
					baseAttend[i] = rs.getInt("attend_base");
					baseForum[i] = rs.getInt("forum_base");
					baseEtc1[i] = rs.getInt("etc1_base");
					baseEtc2[i] = rs.getInt("etc2_base");
				 }

			 }//end for
			 rs.close();

			 resultcourseDto.setCourseId1(courseId1);
			 resultcourseDto.setScoreExam1(scoreExam1);
			 resultcourseDto.setScoreReport1(scoreReport1);
			 resultcourseDto.setScoreAttend1(scoreAttend1);
			 resultcourseDto.setScoreForum1(scoreForum1);
			 resultcourseDto.setScoreEtc11(scoreEtc11);
			 resultcourseDto.setScoreEtc21(scoreEtc21);
			 resultcourseDto.setScoreTotal1(scoreTotal1);
			 resultcourseDto.setBaseExam(baseExam);
			 resultcourseDto.setBaseReport(baseReport);
			 resultcourseDto.setBaseAttend(baseAttend);
			 resultcourseDto.setBaseForum(baseForum);
			 resultcourseDto.setBaseEtc1(baseEtc1);
			 resultcourseDto.setBaseEtc2(baseEtc2);
			 resultcourseDto.setTotCnt(k);

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
		return resultcourseDto;
	}

	/**
	 * 메인화면 학습자 계층별 점수 평균을 가져온다. (serviceEndFlag =1 : 서비스 종료 된것만)
	 * @param systemcode
	 * @param nowdate
	 * @param serviceEndFlag
	 * @return
	 * @throws DAOException
	 */
	public int getMainDeptScoreAvg(String systemcode, String nowdate, int serviceEndFlag) throws DAOException{
		int score_avg = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select avg(score_total) as score_avg ");
		sb.append(" from users u , result_course c, curri_sub s, dept_socode d ");
		sb.append(" where c.system_code = ? ");
		sb.append(" and u.system_code = d.system_code and u.dept_daecode = d.dept_daecode  ");
		sb.append(" and u.dept_socode = d.dept_socode   ");
		sb.append(" and u.system_code = c.system_code and u.use_status ='Y' and u.user_type ='S' ");
		sb.append(" and d.dept_daecode = '100'");//-- 정상 가입자만 가져오기..
		sb.append(" and u.user_id = c.user_id  ");
		sb.append(" and c.system_code = s.system_code and c.curri_code=s.curri_code ");
		sb.append("  and c.curri_year=s.curri_year and c.curri_term=s.curri_term ");
		if(serviceEndFlag == 1){
			sb.append(" and substring(s.service_end,1,8) <= ?   ");
		}
		sql.setSql(sb.toString());
	    sql.setString(systemcode);
		if(serviceEndFlag == 1){
		sql.setString(nowdate);
		}

		log.debug("[getMainDeptScoreAvg]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	score_avg = rs.getInt("score_avg");
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
		 return score_avg;
		}


	/**
	 * 학습자 계층별 점수를 가져온다. (serviceEndFlag =1 : 서비스 종료 된것만)
	 * @param systemcode
	 * @param nowdate
	 * @param serviceEndFlag
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getMainDeptScoreList(String systemcode, String nowdate, int serviceEndFlag) throws DAOException{
		ArrayList scoreList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" select avg(score_total) as dept_score, dept_soname, count(distinct u.user_id) as user_cnt ");
		 sb.append(" from users u , result_course c, curri_sub s, dept_socode d ");
		 sb.append(" where c.system_code = ? ");
		 sb.append(" and u.system_code = d.system_code and u.dept_daecode = d.dept_daecode  ");
		 sb.append(" and u.dept_socode = d.dept_socode   ");
		 sb.append(" and u.system_code = c.system_code and u.use_status ='Y' and u.user_type ='S' ");
		 sb.append(" and d.dept_daecode = '100'");//-- 정상 가입자만 가져오기..
		 sb.append(" and u.user_id = c.user_id  ");
		 sb.append(" and c.system_code = s.system_code and c.curri_code=s.curri_code and c.curri_year=s.curri_year ");
		 sb.append(" and c.curri_term=s.curri_term ");
		 if(serviceEndFlag == 1){
		 sb.append(" and substring(s.service_end,1,8) <= ?   ");
		 }
		 sb.append(" group by dept_soname ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 if(serviceEndFlag == 1){
		 sql.setString(nowdate);
		 }
		 log.debug("[getMainDeptScoreList]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	ResultScoreDTO resultDto =	null;
			scoreList = new ArrayList();

			 while(rs.next()){
			 	resultDto = new ResultScoreDTO();
			 	resultDto.setDeptScore(rs.getInt("dept_score"));
			 	resultDto.setDeptName(StringUtil.nvl(rs.getString("dept_soname")));
			 	resultDto.setUserCnt(rs.getInt("user_cnt"));
			 	scoreList.add(resultDto);
			 }
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
		 return scoreList;
		}

	/**
	 * 수료처리를 위해 과목의 성적 입력완료 처리를 'Y'로 한다.
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseId
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int completeCurriSubCourse(String systemcode, String curricode, int curriyear, int curriterm,String courseId, String userid) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update curri_sub_course set complete_score = 'Y' , mod_id = ?");
		sb.append(", mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
		sql.setSql(sb.toString());

		sql.setString(userid);
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseId);

		log.debug("[completeCurriSubCourse]" + sql.toString());
		try{
		     retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		}

		 return retVal;
		}



	/**
	 * 상대평가일경우
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseId
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public boolean completeScoreProcess1(String systemcode, String curricode, int curriyear, int curriterm,String courseId, String userid) throws DAOException{
		boolean retVal = false;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_rt = new StringBuffer(); // rate
		StringBuffer sb_up = new StringBuffer();
		ArrayList arrList = new ArrayList();
		String rankComp[] = new String[3];

//		 학생별 점수 가져오기..
		sb.append("     select A.system_code ");
		sb.append("     , B.user_id     ");
		sb.append("     , round(sum(B.score_total)/count(A.course_id),1) as score_total_avg ");
		sb.append("     , ifnull(( select count(user_id) from student where system_code= B.system_code and curri_code=B.curri_code and curri_term=B.curri_term and enroll_status in ('S','C','F')), 0) cnt_total_stu ");
		sb.append("      from curri_sub_course A, result_course B ");
		sb.append("     where A.system_code=? ");
		sb.append("       and A.curri_code= ? ");
		sb.append("       and A.curri_year= ? ");
		sb.append("       and A.curri_term= ? ");
		sb.append("       and A.complete_score = 'Y' ");
		sb.append("       and A.system_code= B.system_code  ");
		sb.append("       and A.curri_code = B.curri_code   ");
		sb.append("       and A.curri_year = B.curri_year   ");
		sb.append("       and A.curri_term = B.curri_term   ");
		sb.append("       and A.course_id  = B.course_id    ");
		sb.append("       and B.user_id in ( select user_id  from student where enroll_status in ('S','C','F') and system_code=? and curri_code= ? and curri_year= ? and curri_term= ? )");
		sb.append("     group by A.system_code, A.curri_code, A.curri_year, A.curri_term, B.user_id ");
		sb.append("  order by score_total_avg desc  ");

//		 평가 등급 카운트 하기...
		sb_rt.append("     select ifnull(count(grade_code),0) cnt_grade_code ");
		sb_rt.append("      from score_grade A ");
		sb_rt.append("     where A.system_code=? ");
		sb_rt.append("       and A.curri_year= ? ");
		sb_rt.append("       and A.hakgi_term= ? ");

//		 student update
		sb_up.append("update student A");
		sb_up.append(" set A.grade     = ifnull((select grade_name  from score_grade where system_code=? and curri_year=? and hakgi_term=? and ? between fr_rate and to_rate ),'') ");
		sb_up.append("  , A.get_credit = ifnull((select grade_point from score_grade where system_code=? and curri_year=? and hakgi_term=? and ? between fr_rate and to_rate ),'0.0') ");
		sb_up.append("  , A.mod_id = ?");
		sb_up.append("  , A.mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		sb_up.append(" where A.system_code = ? ");
		sb_up.append("  and A.curri_code = ? ");
		sb_up.append("  and A.curri_year = ? ");
		sb_up.append("  and A.curri_term = ? ");
		sb_up.append("  and A.user_id = ? ");
		sb_up.append("  and A.enroll_status in ('S','C','F') ");

		ResultSet rs = null;

		try{

			sql.setSql(sb.toString());
			// where
			sql.setString(systemcode);
			sql.setString(curricode);
			sql.setInteger(curriyear);
			sql.setInteger(curriterm);
			// where in
			sql.setString(systemcode);
			sql.setString(curricode);
			sql.setInteger(curriyear);
			sql.setInteger(curriterm);

			log.debug("[completeScoreProcess1]" + sql.toString());
		    rs = broker.executeQuery(sql);

		    int iPx =1;	  // 등수를 나타낸다..
		    int iCnt =0;  // row count
		    double b_total = 0;
		    double stuRate = 0;

		    // 학생별 점수 가져오기..
		    while(rs.next()){
	    		iCnt++;
	    		rankComp = new String[3];

		    	if(iCnt >1 && (b_total != rs.getDouble("score_total_avg"))  ){
		    		iPx = iCnt;
		    	}

		    	stuRate =  Math.round(iPx/rs.getDouble("cnt_total_stu")*100) ;

		    	rankComp[0] = rs.getString("user_id");
		    	rankComp[1] = rs.getString("score_total_avg");
		    	rankComp[2] = String.valueOf(stuRate);

		    	arrList.add(rankComp);

		    	b_total = rs.getDouble("score_total_avg");

		    }


		    ////////////////////////////////////////
		    // 평가 등급 카운트 하기...
		    sql = new QueryStatement();
		    sql.setSql(sb_rt.toString());
			// where
			sql.setString(systemcode);
			sql.setInteger(curriyear);
			sql.setInteger(curriterm);

		    rs = broker.executeQuery(sql);
		    int iCntGradeCode = 0;
		    if(rs.next()){
		    	iCntGradeCode = rs.getInt("cnt_grade_code");
		    }

		    ///////////////////////////////////////////////
		    // student update
		    ISqlStatement[] sqlArray = new ISqlStatement[arrList.size()];
		    String rankComp2[] = new String[3];

		    //	 년도, 학기에 맞는 등급이 있으면 년도, 학기로 조회
		    // 없으면 년도=0(공통), 학기=0(공통) 인건을 조회함...
		    int imsiCurriYear = 0;
		    int imsiCurriTerm = 0;
		    if(iCntGradeCode>0){
				imsiCurriYear = curriyear;
				imsiCurriTerm = curriterm;
			}

		    for(int i=0;i<arrList.size();i++){
		    	rankComp2 = (String[])arrList.get(i);

		    	sql = new QueryStatement();
				sql.setSql(sb_up.toString());
				sql.setString(systemcode);
				sql.setInteger(imsiCurriYear);
				sql.setInteger(imsiCurriTerm);
				sql.setString(rankComp2[2]);	// GRADE

				sql.setString(systemcode);
				sql.setInteger(imsiCurriYear);
				sql.setInteger(imsiCurriTerm);
				sql.setString(rankComp2[2]);	// get credit

				sql.setString(userid);	// mod_id

				// where
				sql.setString(systemcode);
				sql.setString(curricode);
				sql.setInteger(curriyear);
				sql.setInteger(curriterm);
				sql.setString(rankComp2[0]);	// user_id

				sqlArray[i] = sql;
		    }
		    retVal = broker.executeUpdate(sqlArray);

		    rs.close();

		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		}finally{
			try{
				if(rs!=null)rs.close();
			}catch(Exception se){
				throw new DAOException(se.getMessage());
			}
		}

		 return retVal;
		}





	/**
	 * 절대평가일경우
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseId
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public boolean completeScoreProcess2(String systemcode, String curricode, int curriyear, int curriterm,String courseId, String userid) throws DAOException{
		boolean retVal = false;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_up = new StringBuffer();

		// 학생별로 등급과 학점을 가져온다.
		sb.append(" select y.user_id,  y.score_total_avg         ");
		sb.append("  , case  when cnt_grade_code=0               ");
		sb.append("          then ifnull((select grade_name      ");
		sb.append("              from score_grade                ");
		sb.append("             where system_code=Y.system_code  ");
		sb.append("              and curri_year=0                ");
		sb.append("              and hakgi_term=0                ");
		sb.append("              and Y.score_total_avg between fr_point and to_point ),'')  ");
		sb.append("          else ifnull((select grade_name       ");
		sb.append("              from score_grade                 ");
		sb.append("             where system_code=Y.system_code   ");
		sb.append("              and curri_year=Y.curri_year      ");
		sb.append("              and hakgi_term=Y.curri_term      ");
		sb.append("              and Y.score_total_avg between fr_point and to_point ),'')  ");
		sb.append("        end as grade_name                      ");
		sb.append("  , case  when cnt_grade_code=0                ");
		sb.append("          then ifnull((select grade_point      ");
		sb.append("              from score_grade                 ");
		sb.append("             where system_code=Y.system_code   ");
		sb.append("              and curri_year=0 ");
		sb.append("              and hakgi_term=0 ");
		sb.append("              and Y.score_total_avg between fr_point and to_point ),'0.0')  ");
		sb.append("          else ifnull((select grade_point      ");
		sb.append("              from score_grade                 ");
		sb.append("             where system_code=Y.system_code   ");
		sb.append("              and curri_year=Y.curri_year      ");
		sb.append("              and hakgi_term=Y.curri_term      ");
		sb.append("              and Y.score_total_avg between fr_point and to_point ),'0.0')   ");
		sb.append("          end as grade_point ");
		sb.append("   from (  ");
		sb.append("     select a.system_code ");
		sb.append("     , a.curri_code  ");
		sb.append("     , a.curri_year  ");
		sb.append("     , a.curri_term  ");
		sb.append("     , b.user_id     ");
		sb.append("     , round(sum(b.score_total)/count(a.course_id),1) as score_total_avg ");
		sb.append("     , (select count(grade_code)  ");
		sb.append("          from score_grade  ");
		sb.append("          where system_code=a.system_code  ");
		sb.append("          and curri_year=a.curri_year  ");
		sb.append("          and hakgi_term=a.curri_term ) as cnt_grade_code ");
		sb.append("      from curri_sub_course a, result_course b ");
		sb.append("     where a.system_code=? ");
		sb.append("       and a.curri_code= ? ");
		sb.append("       and a.curri_year= ? ");
		sb.append("       and a.curri_term= ? ");
		sb.append("       and a.complete_score = 'y' ");
		sb.append("       and a.system_code= b.system_code  ");
		sb.append("       and a.curri_code = b.curri_code   ");
		sb.append("       and a.curri_year = b.curri_year   ");
		sb.append("       and a.curri_term = b.curri_term   ");
		sb.append("       and a.course_id  = b.course_id    ");
		sb.append("     group by a.system_code, a.curri_code, a.curri_year, a.curri_term, b.user_id ");
		sb.append("     ) y ");

		sb_up.append("update student ");
		sb_up.append(" set grade = ? ");
		sb_up.append("  , get_credit = ?");
		sb_up.append("  , mod_id = ?");
		sb_up.append("  , mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		sb_up.append(" where system_code = ? ");
		sb_up.append("  and curri_code = ? ");
		sb_up.append("  and curri_year = ? ");
		sb_up.append("  and curri_term = ? ");
		sb_up.append("  and user_id = ? ");
		sb_up.append("  and enroll_status in ('S','C','F') ");

		ResultSet rs = null;

		try{

			sql.setSql(sb.toString());

			sql.setString(systemcode);
			sql.setString(curricode);
			sql.setInteger(curriyear);
			sql.setInteger(curriterm);

			log.debug("[completeScoreProcess2]" + sql.toString());
		    rs = broker.executeQuery(sql);
		    int rCnt=0;

		    while(rs.next()){
		    	rCnt++;
		    }

		    ISqlStatement[] sqlArray = new ISqlStatement[rCnt];

		    rs.beforeFirst();
		    for(int i=0;i<rCnt;i++){
		    	rs.next();
		    	sql = new QueryStatement();
				sql.setSql(sb_up.toString());
				sql.setString(rs.getString("grade_name"));
				sql.setString(rs.getString("grade_point"));
				sql.setString(userid);

				// where
				sql.setString(systemcode);
				sql.setString(curricode);
				sql.setInteger(curriyear);
				sql.setInteger(curriterm);
				sql.setString(rs.getString("user_id"));

				sqlArray[i] = sql;
		    }
		    rs.close();

		    retVal = broker.executeUpdate(sqlArray);
		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		}finally{
			try{
				if(rs!=null)rs.close();
			}catch(Exception se){
				throw new DAOException(se.getMessage());
			}
		}

		 return retVal;
		}

	
	
	
	/**
	 * 학기별로 전체 성적을 가져온다.
	 * @param systemcode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @param orderBy
	 * @return
	 * @throws DAOException
	 */
	public RowSet getResultCourseListTotal(String systemcode, int pCurriYear, int pCurriTerm, String pUserId) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append("SELECT x.curri_code ");
		 sb.append(" ,(SELECT curri_name from curri_sub where system_code=? and curri_code=x.curri_code and curri_year=? and curri_term=? ) curri_name   ");
		 sb.append(" ,ROUND(x.score_exam/x.cntCourse  ,2) AS score_exam   ");
		 sb.append(" ,ROUND(x.score_report/x.cntCourse,2) AS score_report ");
		 sb.append(" ,ROUND(x.score_attend/x.cntCourse,2) AS score_attend ");
		 sb.append(" ,ROUND(x.score_forum/x.cntCourse ,2) AS score_forum  ");
		 sb.append(" ,ROUND(x.score_etc1/x.cntCourse  ,2) AS score_etc1   ");
		 sb.append(" ,ROUND(x.score_etc2/x.cntCourse  ,2) AS score_etc2   ");
		 sb.append(" ,ROUND(x.score_total/x.cntCourse ,2) AS score_total  ");
		 sb.append(" ,x.grade  ");
		 sb.append(" FROM (    ");
		 sb.append(" SELECT b.curri_code  ");
		 sb.append(" , count(a.course_id) as cntCourse ");
		 sb.append(" , sum(ifnull(score_exam,0))   as score_exam   ");
		 sb.append(" , sum(ifnull(score_report,0)) as score_report ");
		 sb.append(" , sum(ifnull(score_attend,0)) as score_attend ");
		 sb.append(" , sum(ifnull(score_forum,0))  as score_forum  ");
		 sb.append(" , sum(ifnull(score_etc1,0))   as score_etc1   ");
		 sb.append(" , sum(ifnull(score_etc2,0))   as score_etc2   ");
		 sb.append(" , sum(ifnull(score_total,0))  as score_total  ");
		 sb.append(" , b.grade            ");
		 sb.append(" from student b left outer join result_course a ");
		 sb.append("  on a.system_code   = b.system_code	");
		 sb.append("    and a.curri_code = b.curri_code	");
		 sb.append("    and a.curri_year = b.curri_year	");
		 sb.append("    and a.curri_term = b.curri_term	");
		 sb.append("    and a.user_id = b.user_id	");
		 sb.append(" where b.system_code = ?	");
		 sb.append("    and b.curri_year = ?	");
		 sb.append("    and b.curri_term = ?	");
		 sb.append("    and b.user_id = ?	    ");
		 sb.append("  group by b.system_code");
		 sb.append("  , b.curri_code	");
		 sb.append("  , b.curri_year	");
		 sb.append("  , b.curri_term	");
		 sb.append("  , b.user_id		");
		 sb.append(" )  x  ");


		 sql.setSql(sb.toString());
		 // inline view
		 sql.setString(systemcode);
		 sql.setInteger(pCurriYear);
		 sql.setInteger(pCurriTerm);
		 //where
		 sql.setString(systemcode);
		 sql.setInteger(pCurriYear);
		 sql.setInteger(pCurriTerm);
		 sql.setString(pUserId);
		 log.debug("[getResultCourseListAllTerm]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 //-- rs.close() 는 반드시 JSP 에서 해 줄
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return rs;
	}
	
}
