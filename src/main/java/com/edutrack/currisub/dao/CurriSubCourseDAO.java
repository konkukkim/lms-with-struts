package com.edutrack.currisub.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.currisub.dto.CurriCourseListDTO;
import com.edutrack.currisub.dto.CurriSubCourseDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;



/*
 * @author JamFam
 *
 * 과정 과목 연결 관리
 */
public class CurriSubCourseDAO extends AbstractDAO {

	/**
	 *
	 */
	public CurriSubCourseDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과목연결 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(course_id) as cnt from curri_sub_course where system_code= ? and curri_code=? and curri_year=? and curri_term=?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);


		 log.debug("[getTotCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	totCount = rs.getInt("cnt");
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
		return totCount;
	}


	/**
	 * 과목연결 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param Target
	 * @param Word
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCurriSubCourseList(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String Target, String Word, int dispLine) throws DAOException {
		ListDTO retVal = null;
		try{
			String addWhere = "";
			if(!Target.equals("") && !Word.equals(""))	addWhere += " and "+Target+" like ?";
			if(dispLine == 0) dispLine = 10;
			
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("a.course_id");
			sql.setCutCol(	"a.course_id");
			sql.setAlias(	"course_id, course_name, prof_id, prof_name, reg_date");
			sql.setSelect(	"a.course_id, b.course_name, a.prof_id, c.user_name as prof_name, concat(substring(a.reg_date,1,4),'-',substring(a.reg_date,5,2),'-',substring(a.reg_date,7,2)) as reg_date");
			sql.setFrom(	" curri_sub_course a, course b, users c");
			sql.setWhere	(" a.system_code=b.system_code and a.system_code=b.system_code " +
							" and a.system_code=? and a.curri_code=? " +
							" and a.curri_year=? and a.curri_term=? " +
							" and a.course_id=b.course_id and a.prof_id=c.user_id"+addWhere);
			sql.setString(SystemCode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			 
			if(!Target.equals("") && !Word.equals("")) sql.setString("%"+Word+"%");
			sql.setOrderby(" a.reg_date desc ,a.course_id asc ");
						
			//---- 디버그 출력
			log.debug("[getCurriSubCourseList]" + sql.toString());

			//---- 쿼리실행 및 반환값 설정
			retVal = broker.executeListQuery(sql, curpage, dispLine);
			
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
	 * 과목연결 정보를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriSubCourseInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT " +
						" a.system_code, a.curri_code, a.course_type, b.curri_name" +
						", a.course_id, c.course_name, c.contents_type, a.prof_id" +
						", a.exam_base, a.report_base, a.attend_base, a.forum_base"+
						", a.etc1_base, a.etc2_base, a.reg_id, a.reg_date" +
						", c.contents_width, c.contents_height, c.flag_navi" +
						", (SELECT curri_property2 FROM curri_top " +
							" WHERE system_code = a.system_code AND curri_code = a.curri_code) AS curri_type ");
		sb.append(	" FROM curri_sub_course a, curri_sub b, course c" );
		sb.append(	" WHERE a.system_code=b.system_code AND a.system_code=c.system_code " +
					" AND a.curri_code=b.curri_code AND a.course_id=c.course_id " +
					" AND a.curri_year = b.curri_year AND a.curri_term = b.curri_term " +
					" AND a.system_code=? AND a.curri_code=? " +
					" AND a.curri_year=? AND a.curri_term=? " +
					" AND a.course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		//---- 디버그 출력
		log.debug("[getCurriSubCourseInfo]" + sql.toString());
		
		
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
	 * 과목의 교수아이디를 가져온다
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public String getCurriSubCourseProfId(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select prof_id from curri_sub_course " );
		sb.append(" where system_code=? and curri_code=? and curri_year=? and curri_term=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		//---- 디버그 출력
		log.debug("[getCurriSubCourseInfo]" + sql.toString());
		String profId=null;
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next())
				profId = StringUtil.nvl(rs.getString("prof_id"));
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return profId;
	}

	/**
	 * 과목 연결 정보를 저장한다.
	 * @param curriSubCourseInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCurriSubCourseInfo(CurriSubCourseDTO curriSubCourseInfo) throws DAOException{
		log.debug("--------------   addCurriSubCourseInfo Start  -----------------");
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO curri_sub_course(system_code, curri_code, curri_year, curri_term" +
												", course_id, course_type, online_yn, prof_id" +
												", exam_base, report_base, attend_base, forum_base"+
												", etc1_base, etc2_base, complete_score, reg_id" +
												", reg_date, mod_id, mod_date)");
		sb.append(" VALUES (?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?)");
		sql.setSql(sb.toString());

		log.debug("--------------   set Parameter Start  -----------------");
		//---- 입력된 값을 가져 온다.
		sql.setString(curriSubCourseInfo.getSystemCode());
		sql.setString(curriSubCourseInfo.getCurriCode());
		sql.setInteger(curriSubCourseInfo.getCurriYear());
		sql.setInteger(curriSubCourseInfo.getCurriTerm());
		sql.setString(curriSubCourseInfo.getCourseId());
		sql.setString(curriSubCourseInfo.getCourseType());
		sql.setString(curriSubCourseInfo.getOnlineYn());
		sql.setString(curriSubCourseInfo.getProfId());
		sql.setInteger(curriSubCourseInfo.getExamBase());
		sql.setInteger(curriSubCourseInfo.getReportBase());
		sql.setInteger(curriSubCourseInfo.getAttendBase());
		sql.setInteger(curriSubCourseInfo.getForumBase());
		sql.setInteger(curriSubCourseInfo.getEtc1Base());
		sql.setInteger(curriSubCourseInfo.getEtc2Base());
		sql.setString("N");
		sql.setString(curriSubCourseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriSubCourseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		log.debug("---------------- set Parameter end ----------------------");
		//---- 디버그 출력
		log.debug("[addCurriSubCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과목 연결 정보를 수정한다.
	 * @param curriSubCourseInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCurriSubCourseInfo(CurriSubCourseDTO curriSubCourseInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE curri_sub_course SET " +
									" course_type=?, online_yn=?" +
									", prof_id=?, exam_base=?" +
									", report_base=?, attend_base=?" +
									", forum_base=?, etc1_base=?" +
									", etc2_base=?, complete_score=?" +
									", mod_id=?, mod_date=?");
		sb.append(" WHERE system_code=? AND curri_code=? AND curri_year=? AND curri_term=? AND course_id=?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriSubCourseInfo.getCourseType());
		sql.setString(curriSubCourseInfo.getOnlineYn());
		sql.setString(curriSubCourseInfo.getProfId());
		sql.setInteger(curriSubCourseInfo.getExamBase());
		sql.setInteger(curriSubCourseInfo.getReportBase());
		sql.setInteger(curriSubCourseInfo.getAttendBase());
		sql.setInteger(curriSubCourseInfo.getForumBase());
		sql.setInteger(curriSubCourseInfo.getEtc1Base());
		sql.setInteger(curriSubCourseInfo.getEtc2Base());
		sql.setString(StringUtil.nvl(curriSubCourseInfo.getCompleteScore(),"N"));
		sql.setString(curriSubCourseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriSubCourseInfo.getSystemCode());
		sql.setString(curriSubCourseInfo.getCurriCode());
		sql.setInteger(curriSubCourseInfo.getCurriYear());
		sql.setInteger(curriSubCourseInfo.getCurriTerm());
		sql.setString(curriSubCourseInfo.getCourseId());
		//---- 디버그 출력
		log.debug("[editCurriTopCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 과목 연결 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public int delCurriSubCourseInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_sub_course where system_code=? and curri_code=? and curri_year=? and curri_term=? and course_id=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);


		//---- 디버그 출력
		log.debug("[delCurriSubCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 개설 과목의 강의를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public int delCurriContents(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_contents where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		try{
			retVal = broker.executeUpdate(sql);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 개설 과목의 퀴즈를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public int delCurriQuiz(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_quiz where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		try {
			retVal = broker.executeUpdate(sql);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 과정의 과목 정보를 가져 온다.
	 * @param SystemCode
	 * @param CateCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */
	public int topCourseToSubCourse (String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String regId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		String currentDate = CommonUtil.getCurrentDate();
		String qry =
			" INSERT INTO curri_sub_course " +
			" (system_code, curri_code, curri_year, curri_term" +
			", course_id, course_type, online_yn, prof_id, exam_base"+
			", report_base, attend_base, forum_base, etc1_base"+
			", etc2_base, complete_score, reg_id, reg_date" +
			", mod_id, mod_date) "+
			" SELECT " +
			" system_code, curri_code, ?, ?" +
			", course_id, course_type, online_yn, prof_id, exam_base"+
			", report_base, attend_base, forum_base, etc1_base" +
			", etc2_base, 'N', ?, ?"+
			", ?, ? " +
			" FROM curri_top_course " +
			" WHERE system_code = ? and curri_code = ? ";
		sql.setSql(qry.toString());
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(regId);
		sql.setString(currentDate);
		sql.setString(regId);
		sql.setString(currentDate);
		sql.setString(SystemCode);
		sql.setString(CurriCode);


		try {
			retVal = broker.executeUpdate(sql);
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개설과정의 과목정보 리스트를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */

	public ArrayList getCurriCourseList(String SystemCode, String CurriCode, int CurriYear,int CurriTerm, String ProfId) throws DAOException {
		ArrayList curriCourseList = null;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT csc.system_code, csc.curri_code, csc.curri_year");
		sb.append(", csc.curri_term, csc.course_id, cs.course_name");
		sb.append(", csc.prof_id, u.user_name, csc.reg_date, csc.mod_date");
		sb.append(", cs.contents_width, cs.contents_height, cs.flag_navi ");
		sb.append(", cs.contents_type ");
		sb.append(" FROM curri_sub_course csc, course cs, users u ");
		sb.append(" WHERE csc.system_code = cs.system_code and csc.course_id = cs.course_id ");
		sb.append(" and csc.system_code = u.system_code and csc.prof_id=u.user_id");
		sb.append(" and csc.system_code = ? and csc.curri_code = ? and csc.curri_year = ? and csc.curri_term = ? ");
		if(!ProfId.equals(""))
			sb.append("and csc.prof_id = ? ");
		sb.append(" ORDER BY csc.reg_date ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		if(!ProfId.equals(""))
			sql.setString(ProfId);

		log.debug("[getCurriCourseList]" + sql.toString());


		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);			 
			 CurriCourseListDTO curriCourseListDto = null;
			 curriCourseList = new ArrayList();

			 while(rs.next()){
			 	curriCourseListDto = new CurriCourseListDTO();
			 	curriCourseListDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			 	curriCourseListDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			 	curriCourseListDto.setCurriYear(rs.getInt("curri_year"));
			 	curriCourseListDto.setCurriTerm(rs.getInt("curri_term"));
			 	curriCourseListDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 	curriCourseListDto.setCourseName(StringUtil.nvl(rs.getString("course_name")));
			 	curriCourseListDto.setProfId(StringUtil.nvl(rs.getString("prof_id")));
			 	curriCourseListDto.setProfName(StringUtil.nvl(rs.getString("user_name")));
			 	curriCourseListDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 	curriCourseListDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			 	curriCourseListDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width")));
			 	curriCourseListDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height")));
			 	curriCourseListDto.setFlagNavi(StringUtil.nvl(rs.getString("flag_navi")));
			 	
			 	
			 	curriCourseList.add(curriCourseListDto);
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
		return curriCourseList;
	}
}