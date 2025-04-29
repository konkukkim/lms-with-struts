/*
 * Created on 2004. 10. 8.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamAdminDAO  extends AbstractDAO {

	/**
	 *
	 */
	public ExamAdminDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

    /**
     * 시험 리스트를 가져온다.
     * @param systemcode
     * @param curricode
     * @param curriyear
     * @param curriterm
     * @param courseid
     * @return
     * @throws DAOException
     */
	public RowSet getExamList(String systemcode,CurriMemDTO curriinfo ,String courseid,int examid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select course_id,exam_id,subject,exam_comment,exam_type,start_date,end_date,extend_end,result_date, ");
			sb.append(" run_time,view_style,flag_use,flag_time,reg_id,reg_date,mod_id,mod_date ");
			sb.append(" from exam_info ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year =? and curri_term =? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);

			if(!courseid.equals("")){
				sb.append(" and course_id = ? ");
				sql.setString(courseid);
			}

			if(examid > 0){
				sb.append(" and exam_id = ? ");
				sql.setInteger(examid);
			}

			sb.append(" order by exam_id desc ");

			sql.setSql(sb.toString());

			log.debug("[getExamList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

    /**
     * 사용자 정보를 가져온다.
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */
	public ExamInfoDTO getExamInfo(String systemcode,CurriMemDTO curriinfo ,String courseid, int examid) throws DAOException{
		ExamInfoDTO examInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
            rs = getExamList(systemcode, curriinfo, courseid,examid);
			examInfo = new ExamInfoDTO();
			if(rs.next()){
				examInfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				examInfo.setExamId(rs.getInt("exam_id"));
				examInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
				examInfo.setComment(StringUtil.nvl(rs.getString("exam_comment")));
				examInfo.setExamType(StringUtil.nvl(rs.getString("exam_type")));
				examInfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				examInfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
				examInfo.setExtendEnd(StringUtil.nvl(rs.getString("extend_end")));
				examInfo.setResultDate(StringUtil.nvl(rs.getString("result_date")));
				examInfo.setRunTime(rs.getInt("run_time"));
				examInfo.setViewStyle(StringUtil.nvl(rs.getString("view_style")));
				examInfo.setFlagUse(StringUtil.nvl(rs.getString("flag_use")));
				examInfo.setFlagTime(StringUtil.nvl(rs.getString("flag_time")));
				examInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		        examInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
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

		return examInfo;
	}

	public int addExamInfo(String systemcode,CurriMemDTO curriInfo, ExamInfoDTO examinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_info(system_code,curri_code,curri_year,curri_term,course_id,exam_id,subject,exam_comment,exam_type,start_date,end_date,extend_end,result_date, ");
		 sb.append(" run_time,view_style,flag_use,flag_time,reg_id,reg_date) ");
		 sb.append(" select ?,?,?,?,?,ifnull(max(exam_id),0)+1 as exam_id,?,?,?,?,?,?,?,?,?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from exam_info ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(examinfo.getCourseId());
		 sql.setString(examinfo.getSubject());
		 sql.setString(examinfo.getComment());
		 sql.setString(examinfo.getExamType());
		 sql.setString(examinfo.getStartDate());
		 sql.setString(examinfo.getEndDate());
		 sql.setString(examinfo.getExtendEnd());
		 sql.setString(examinfo.getResultDate());
		 sql.setInteger(examinfo.getRunTime());
		 sql.setString(examinfo.getViewStyle());
		 sql.setString(examinfo.getFlagUse());
		 sql.setString(examinfo.getFlagTime());
		 sql.setString(examinfo.getRegId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setString(curriInfo.curriYear);
		 sql.setString(curriInfo.curriTerm);
		 sql.setString(examinfo.getCourseId());

		 log.debug("[addExamInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public int editExamInfo(String systemcode,CurriMemDTO curriInfo, ExamInfoDTO examinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_info ");
		 sb.append(" set  subject=?,exam_comment=?,exam_type=?,start_date=?,end_date=?,extend_end=?,result_date=?,run_time=?,view_style=?,flag_time=?,mod_id=?, ");
		 sb.append(" mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and exam_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(examinfo.getSubject());
		 sql.setString(examinfo.getComment());
		 sql.setString(examinfo.getExamType());
		 sql.setString(examinfo.getStartDate());
		 sql.setString(examinfo.getEndDate());
		 sql.setString(examinfo.getExtendEnd());
		 sql.setString(examinfo.getResultDate());
		 sql.setInteger(examinfo.getRunTime());
		 sql.setString(examinfo.getViewStyle());
		 sql.setString(examinfo.getFlagTime());
		 sql.setString(examinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(examinfo.getCourseId());
		 sql.setInteger(examinfo.getExamId());

		 log.debug("[addExamInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 시험의 상태를 사용으로 변경한다.
	 * @param systemcode
	 * @param curriInfo
	 * @param courseid
	 * @param examid
	 * @return
	 * @throws DAOException
	 */
	public int editExamUseStatus(String systemcode,CurriMemDTO curriInfo,String courseid, int examid, String status) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_info ");
		 sb.append(" set flag_use = ? ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? ");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(status);
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);

		 log.debug("[editExamUseStatus]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public RowSet getStExamList(String systemcode,CurriMemDTO curriinfo,String userid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select e.course_id,(select course_name from course where system_code = e.system_code and course_id = e.course_id) as course_name, e.exam_id, e.subject, e.exam_comment, e.exam_type, e.start_date, e.end_date, e.extend_end, e.result_date, ");
			sb.append(" e.run_time, e.view_style, e.flag_use, e.flag_time, ifnull(a.start_date,'') as astart_date, ifnull(a.end_date,'') as aend_date,ifnull(a.result_check,'N') result_check ");
			sb.append(" from exam_info e left outer join exam_answer a on e.system_code = a.system_code and e.curri_code = a.curri_code and e.curri_year =a.curri_year and e.curri_term =a.curri_term and e.course_id = a.course_id ");
			sb.append("  and e.exam_id = a.exam_id and a.user_id = ? ");
			sb.append(" where e.system_code = ? and e.curri_code = ? and e.curri_year = ? and e.curri_term = ? and e.flag_use ='Y' ");
			sql.setString(userid);
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setSql(sb.toString());

			log.debug("[getStExamList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}


	/**
	 * exam_answer 테이블에서 시험 답안 제출 정보 삭제
	 * @param SystemCode
	 * @param CourseId
	 * @param ExamId
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return  int
	 * @throws DAOException
	 */
	public int delExamAnswer(String SystemCode, String CourseId, String ExamId, String CurriCode, int CurriYear, int CurriTerm ) throws DAOException{
		int retVal = 0;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from exam_answer where system_code=? and curri_code = ? and curri_year =? " +
				" and curri_term =? and course_id = ? and exam_id = ? ") ;

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ExamId);

		log.debug("[delExamAnswer]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * exam_contents 테이블에서 시험 문제 정보 삭제
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ExamId
	 * @return int
	 * @throws DAOException
	 */
	public int delExamContents(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,
			String CourseId, String ExamId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from exam_contents where system_code = ? and curri_code = ? " +
				" and curri_year = ? and curri_term = ? and course_id = ? and exam_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ExamId);

		log.debug("[delExamContents]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * exam_info 테이블에서 시험 정보 삭제
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ExamId
	 * @return int
	 * @throws DAOException
	 */
	public int delExamInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,
			String CourseId, String ExamId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from exam_info where system_code= ? and curri_code = ? " +
				" and curri_year = ? and curri_term = ? and course_id = ? and exam_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ExamId);

		log.debug("[delExamInfo]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal ;
	}
}
