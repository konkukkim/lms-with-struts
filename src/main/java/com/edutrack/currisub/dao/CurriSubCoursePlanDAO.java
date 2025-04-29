package com.edutrack.currisub.dao;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.currisub.dto.CurriSubCoursePlanDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;



/*
 * @author JamFam
 *
 * 과정 과목  강의 계획서  관리
 */
public class CurriSubCoursePlanDAO extends AbstractDAO {

	/**
	 *
	 */
	public CurriSubCoursePlanDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과목  강의 계획서  정보를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriSubCoursePlan(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, curri_code,curri_year, curri_term , course_id,");
		sb.append(" info_title_1, info_text_1, info_title_2, info_text_2, info_title_3, info_text_3,");
		sb.append(" info_title_4, info_text_4, info_title_5, info_text_5,");
		sb.append(" reg_id, reg_date, rfile_name, sfile_name, file_path, file_size ");
		sb.append(" from curri_sub_course_plan" );
		sb.append(" where system_code=? and curri_code=? and curri_year=? and curri_term=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		//---- 디버그 출력
		log.debug("[getCurriSubCoursePlan]" + sql.toString());

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
	 * 과목  강의 계획서  정보를 저장한다.
	 * @param curriSubCourseInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCurriSubCoursePlan(CurriSubCoursePlanDTO curriSubCoursePlan) throws DAOException{
		log.debug("--------------   addCurriSubCourseInfo Start  -----------------");
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into curri_sub_course_plan(system_code, curri_code, curri_year, curri_term, course_id,");
		sb.append(" info_title_1, info_text_1, info_title_2, info_text_2, info_title_3, info_text_3,");
		sb.append(" info_title_4, info_text_4, info_title_5, info_text_5,");
		sb.append(" reg_id, reg_date, rfile_name, sfile_name, file_path, file_size)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());

		log.debug("--------------   set Parameter Start  -----------------");
		//---- 입력된 값을 가져 온다.
		sql.setString(curriSubCoursePlan.getSystemCode());
		sql.setString(curriSubCoursePlan.getCurriCode());
		sql.setInteger(curriSubCoursePlan.getCurriYear());
		sql.setInteger(curriSubCoursePlan.getCurriTerm());
		sql.setString(curriSubCoursePlan.getCourseId());
		sql.setString(curriSubCoursePlan.getInfoTitle1());
		sql.setString(curriSubCoursePlan.getInfoText1());
		sql.setString(curriSubCoursePlan.getInfoTitle2());
		sql.setString(curriSubCoursePlan.getInfoText2());
		sql.setString(curriSubCoursePlan.getInfoTitle3());
		sql.setString(curriSubCoursePlan.getInfoText3());
		sql.setString(curriSubCoursePlan.getInfoTitle4());
		sql.setString(curriSubCoursePlan.getInfoText4());
		sql.setString(curriSubCoursePlan.getInfoTitle5());
		sql.setString(curriSubCoursePlan.getInfoText5());
		sql.setString(curriSubCoursePlan.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(curriSubCoursePlan.getRFileName());
		sql.setString(curriSubCoursePlan.getSFileName());
		sql.setString(curriSubCoursePlan.getFilePath());
		sql.setString(curriSubCoursePlan.getFilesize());

		log.debug("---------------- set Parameter end ----------------------");
		//---- 디버그 출력
		log.debug("[addCurriSubCoursePlan]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int addCurriSubCoursePlanFile(CurriSubCoursePlanDTO curriSubCoursePlan) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	" INSERT INTO curri_sub_course_plan" +
							"(system_code, curri_code, curri_year, curri_term" +
							", course_id, reg_id, reg_date, rfile_name " +
							", sfile_name, file_path, file_size) ");
		sb.append(	" VALUES (?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?)");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriSubCoursePlan.getSystemCode());
		sql.setString(curriSubCoursePlan.getCurriCode());
		sql.setInteger(curriSubCoursePlan.getCurriYear());
		sql.setInteger(curriSubCoursePlan.getCurriTerm());
		sql.setString(curriSubCoursePlan.getCourseId());
		sql.setString(curriSubCoursePlan.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(curriSubCoursePlan.getRFileName());
		sql.setString(curriSubCoursePlan.getSFileName());
		sql.setString(curriSubCoursePlan.getFilePath());
		sql.setString(curriSubCoursePlan.getFilesize());

		//---- 디버그 출력
		log.debug("[addCurriSubCoursePlanFile]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과목 강의 계획서 정보를 수정한다.
	 * @param curriSubCourseInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCurriSubCoursePlan(CurriSubCoursePlanDTO curriSubCoursePlan) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	"UPDATE curri_sub_course_plan SET ");
		sb.append(	" info_title_1=?, info_text_1=? " +
					", info_title_2=?, info_text_2=? " +
					", info_title_3=?, info_text_3=? ");
		sb.append(	", info_title_4=?, info_text_4=? " +
					", info_title_5=?, info_text_5=? " +
					", mod_id=?, mod_date=? ");
		sb.append(	", rfile_name = ?, sfile_name = ? " +
					", file_path = ?, file_size = ?   ");
		sb.append(	" WHERE system_code=? AND curri_code=? " +
					" AND curri_year=? AND curri_term=? " +
					" AND course_id=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriSubCoursePlan.getInfoTitle1());
		sql.setString(curriSubCoursePlan.getInfoText1());
		sql.setString(curriSubCoursePlan.getInfoTitle2());
		sql.setString(curriSubCoursePlan.getInfoText2());
		sql.setString(curriSubCoursePlan.getInfoTitle3());
		sql.setString(curriSubCoursePlan.getInfoText3());
		sql.setString(curriSubCoursePlan.getInfoTitle4());
		sql.setString(curriSubCoursePlan.getInfoText4());
		sql.setString(curriSubCoursePlan.getInfoTitle5());
		sql.setString(curriSubCoursePlan.getInfoText5());
		sql.setString(curriSubCoursePlan.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriSubCoursePlan.getRFileName());
		sql.setString(curriSubCoursePlan.getSFileName());
		sql.setString(curriSubCoursePlan.getFilePath());
		sql.setString(curriSubCoursePlan.getFilesize());

		sql.setString(curriSubCoursePlan.getSystemCode());
		sql.setString(curriSubCoursePlan.getCurriCode());
		sql.setInteger(curriSubCoursePlan.getCurriYear());
		sql.setInteger(curriSubCoursePlan.getCurriTerm());
		sql.setString(curriSubCoursePlan.getCourseId());

		//---- 디버그 출력
		log.debug("[editCurriSubCoursePlan]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int editCurriSubCoursePlanFile(CurriSubCoursePlanDTO curriSubCoursePlan) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	"UPDATE curri_sub_course_plan SET ");
		sb.append(	" mod_id=?, mod_date=? ");
		sb.append(	", rfile_name = ?, sfile_name = ? " +
					", file_path = ?, file_size = ?   ");
		sb.append(	" WHERE system_code=? AND curri_code=? " +
					" AND curri_year=? AND curri_term=? " +
					" AND course_id=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriSubCoursePlan.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriSubCoursePlan.getRFileName());
		sql.setString(curriSubCoursePlan.getSFileName());
		sql.setString(curriSubCoursePlan.getFilePath());
		sql.setString(curriSubCoursePlan.getFilesize());

		sql.setString(curriSubCoursePlan.getSystemCode());
		sql.setString(curriSubCoursePlan.getCurriCode());
		sql.setInteger(curriSubCoursePlan.getCurriYear());
		sql.setInteger(curriSubCoursePlan.getCurriTerm());
		sql.setString(curriSubCoursePlan.getCourseId());

		//---- 디버그 출력
		log.debug("[editCurriSubCoursePlanFile]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과목 강의 계획서 수를 가져온다.
	 * 2007.06.12 sangsang
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @return
	 * @throws DAOException
	 */
	public int getCurriSubCoursePlanCount(String systemCode, String curriCode, int curriYear, int curriTerm, String courseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(course_id) as cnt ");
		sb.append(" from curri_sub_course_plan" );
		sb.append(" where system_code=? and curri_code=? and curri_year=? and curri_term=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(courseId);

		//---- 디버그 출력
		log.debug("[getCurriSubCoursePlan]" + sql.toString());

		int retVal = 0;
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next())
				retVal = rs.getInt("cnt");
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 첨부파일을 삭제한다.
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws DAOException
	 */
	public int planFileDelete(String systemCode, String curriCode, int curriYear, int curriTerm) throws DAOException {
		int		retVal	=	0;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" UPDATE curri_sub_course_plan SET ");
		sb.append(" rfile_name = '', sfile_name = '', file_path = '', file_size = '' ");
		sb.append(" WHERE system_code = ? AND curri_code = ? AND curri_year = ? AND curri_term = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);

		try {
			retVal	=	broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

}