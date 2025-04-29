/*
 * Created on 2004. 10. 8.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.coursereport.dto.ReportInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportAdminDAO  extends AbstractDAO {

	/**
	 *
	 */
	public ReportAdminDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 과제 리스트를 가져온다.
     * @param systemcode
     * @param curricode
     * @param curriyear
     * @param curriterm
     * @param courseid
     * @return
     * @throws DAOException
     */
	public RowSet getReportList(String systemcode,CurriMemDTO curriinfo ,String courseid,int reportid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select course_id, report_id, report_subject, report_type2, report_type1, report_start_date, report_end_date, report_extend_date, ");
			sb.append(" report_score_yn, report_open_yn, report_regist_yn, reg_id, reg_date, mod_id, mod_date ");
			sb.append(" from report_info ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year =? and curri_term =? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			if(!courseid.equals("")){
				sb.append(" and course_id = ? ");
				sql.setString(courseid);
			}

			sb.append(" order by report_id desc ");
			sql.setSql(sb.toString());

			log.debug("[getReportList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}


    /**
     * 리포트 기본 상위정보를 가져온다.
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */
	public ReportInfoDTO getReportInfo(String systemcode,CurriMemDTO curriinfo ,String courseid, int reportid) throws DAOException{

		ReportInfoDTO reportInfo = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		StringBuffer output = new StringBuffer();

		sb.append(" select course_id, report_id, report_subject, report_contents, report_type1, report_type2, ");
		sb.append(" report_start_date, report_end_date, report_extend_date, report_score_yn, report_open_yn, ");
		sb.append(" report_regist_yn, reg_id, reg_date, mod_id, mod_date ");
		sb.append(" from report_info ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");
		sql.setString(systemcode);
		sql.setString(curriinfo.curriCode);
		sql.setInteger(curriinfo.curriYear);
		sql.setInteger(curriinfo.curriTerm);
		if(!courseid.equals("")){
			sb.append(" and course_id = ? ");
			sql.setString(courseid);
		}
		if(reportid > 0){
			sb.append(" and report_id = ? ");
			sql.setInteger(reportid);
		}
		sb.append(" order by report_id desc ");
		sql.setSql(sb.toString());

		log.debug("[reportadminDAO_getReportInfo]" + sql.toString());

		ResultSet rs = null;
		try{

			rs = broker.executeQuery(sql);

			reportInfo = new ReportInfoDTO();
			if(rs.next()){
				reportInfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				reportInfo.setReportId(rs.getInt("report_id"));
				reportInfo.setReportSubject(StringUtil.nvl(rs.getString("report_subject")));
				reportInfo.setReportType1(StringUtil.nvl(rs.getString("report_type1")));
				reportInfo.setReportType2(StringUtil.nvl(rs.getString("report_type2")));
				reportInfo.setReportStartDate(StringUtil.nvl(rs.getString("report_start_date")));
				reportInfo.setReportEndDate(StringUtil.nvl(rs.getString("report_end_date")));
				reportInfo.setReportExtendDate(StringUtil.nvl(rs.getString("report_extend_date")));
				reportInfo.setReportScoreYn(StringUtil.nvl(rs.getString("report_score_yn")));
				reportInfo.setReportOpenYn(StringUtil.nvl(rs.getString("report_open_yn")));
				reportInfo.setReportRegistYn(StringUtil.nvl(rs.getString("report_regist_yn")));
				reportInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		        reportInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
		        reportInfo.setModId(StringUtil.nvl(rs.getString("mod_id")));
		        reportInfo.setModDate(StringUtil.nvl(rs.getString("mod_date")));

		        reportInfo.setReportContents(StringUtil.nvl(rs.getString("report_contents")));

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
		return reportInfo;
	}

	/**
	 * 과제 정보의  max 번호를 가져온다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int getReportInfoMaxReportId(String systemCode, String curriCode, String curriYear, String curriTerm, String courseId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select IFNULL(max(report_id), 0)+1 as max_no from report_info ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb.append(" and curri_term = ? and course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(curriCode);
		 sql.setString(curriYear);
		 sql.setString(curriTerm);
		 sql.setString(courseId);
		 log.debug("[addReportContentsInfo]" + sql.toString());
		 try{
		 	rs = broker.executeQuery(sql);

			if(rs.next()){
				retVal = rs.getInt("max_no");
			}
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		return retVal;
	}

	/**
	 * REPORT_SUB_INFO에서 SUB_REPORT_ID의 MAX값을 가져온다.
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param reportId
	 * @return
	 * @throws DAOException
	 */
	public int getSubReportInfoMaxReportId(String systemCode, String curriCode, String curriYear, String curriTerm, String courseId, int reportId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select IFNULL(max(sub_report_id),0)+1 as max_no from report_sub_info ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb.append(" and curri_term = ? and course_id = ? and report_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(curriCode);
		 sql.setString(curriYear);
		 sql.setString(curriTerm);
		 sql.setString(courseId);
		 sql.setInteger(reportId);

		 log.debug("[getSubReportInfoMaxReportId]" + sql.toString());
		 try{
		 	rs = broker.executeQuery(sql);

			if(rs.next()){
				retVal = rs.getInt("max_no");
			}
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		return retVal;
	}

	/**
	 * 과제 정보의 하위 과제 갯수를 가져온다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int getReportSubInfoCnt(String systemCode, String curriCode, String curriYear, String curriTerm, String courseId, int reportId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(sub_report_id) as cnt from report_sub_info ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb.append(" and curri_term = ? and course_id = ? and report_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(curriCode);
		 sql.setString(curriYear);
		 sql.setString(curriTerm);
		 sql.setString(courseId);
		 sql.setInteger(reportId);
		 log.debug("[getReportSubInfoCnt]" + sql.toString());
		 try{
		 	rs = broker.executeQuery(sql);

			if(rs.next()){
				retVal = rs.getInt("cnt");
			}
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		return retVal;
	}

	/**
     * 과제 정보를 저장한다.
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */
	public int addReportInfo(String systemcode,CurriMemDTO curriInfo, ReportInfoDTO reportinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 int report_id = getReportInfoMaxReportId(systemcode, curriInfo.curriCode, curriInfo.curriYear, curriInfo.curriTerm, reportinfo.getCourseId());

		 StringBuffer sb = new StringBuffer();
		 sb.append(" INSERT INTO report_info (system_code, curri_code, curri_year, curri_term" +
		 									", course_id, report_id, report_subject, report_contents" +
		 									", report_type1, report_type2, report_start_date, report_end_date" +
		 									", report_extend_date, report_score_yn, report_open_yn, report_regist_yn" +
		 									", reg_id, reg_date) ");
		 sb.append(" values(?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(reportinfo.getCourseId());
		 sql.setInteger(report_id);
		 sql.setString(reportinfo.getReportSubject());
		 sql.setString(reportinfo.getReportContents());
		 sql.setString(reportinfo.getReportType1());
		 sql.setString(reportinfo.getReportType2());
		 sql.setString(reportinfo.getReportStartDate());
		 sql.setString(reportinfo.getReportEndDate());
		 sql.setString(reportinfo.getReportExtendDate());
		 sql.setString(reportinfo.getReportScoreYn());
		 sql.setString(reportinfo.getReportOpenYn());
		 sql.setString(reportinfo.getReportRegistYn());
		 sql.setString(reportinfo.getRegId());

		 log.debug("[addReportInfo]" + sql.toString());
		


		//과제 자동 생성의 경우
		QueryStatement sql3 = new QueryStatement();
		StringBuffer sb3 = new StringBuffer();
		String autoBankYn = reportinfo.getAutoBankYn();
		int reportBankId = reportinfo.getReportBankId();
		int sub_report_id = 0;
		int i	=	0;

		QueryStatement sql4 = new QueryStatement();
		StringBuffer sb4 = new StringBuffer();

		if(autoBankYn.equals("Y")) {
			sb4.append(	" SELECT report_subject, report_contents, rfile_name, sfile_name" +
						", file_path, file_size " +
						" FROM report_bank_contents " +
						" WHERE course_id = ? and report_bank_id = ? ");
			sql4.setSql(sb4.toString());
			sql4.setString(reportinfo.getCourseId());
			sql4.setInteger(reportBankId);

			sb3.append(" INSERT INTO report_sub_info (system_code, curri_code, curri_year, curri_term" +
													", course_id, report_id, sub_report_id, sub_report_subject" +
													", sub_report_contents, rfile_name, sfile_name, file_path" +
													", file_size, reg_id, reg_date ) ");
			sb3.append(" VALUES (?, ?, ?, ?" +
								", ?, ?, ?, ?" +
								", ?, ?, ?, ?" +
								", ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");
		 }

		 Connection conn = null;
		 ResultSet rs = null;
		 DBConnectionManager pool = null;
		 try{
			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql, conn);

	     	if(autoBankYn.equals("Y")) {
	     		
	     		rs	=	broker.executeQuery(sql4, conn);
	     		while(rs.next()) {
	     			i++;

	     			sql3.setSql(sb3.toString());
	     			sql3.setString(systemcode);
	     			sql3.setString(curriInfo.curriCode);
	     			sql3.setInteger(curriInfo.curriYear);
	     			sql3.setInteger(curriInfo.curriTerm);
	     			sql3.setString(reportinfo.getCourseId());
	     			sql3.setInteger(report_id);
	     			sql3.setInteger(i);
	     			sql3.setString(StringUtil.nvl(rs.getString("report_subject")));
	     			sql3.setString(StringUtil.nvl(rs.getString("report_contents")));
	     			sql3.setString(StringUtil.nvl(rs.getString("rfile_name")));
	     			sql3.setString(StringUtil.nvl(rs.getString("sfile_name")));
	     			sql3.setString(StringUtil.nvl(rs.getString("file_path")));
	     			sql3.setString(StringUtil.nvl(rs.getString("file_size")));
	     			sql3.setString(reportinfo.getRegId());

	     			log.debug("[addReportSubInfo]" + sql3.toString());

	     			retVal = broker.executeUpdate(sql3,conn);
	     			sql3.clearParam();
	     		}
	     		rs.close();
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

	/**
     * 과제 정보를 수정한다.
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */

	public int editReportInfo(String systemcode,CurriMemDTO curriInfo, ReportInfoDTO reportinfo) throws DAOException{
		 int 	retVal			=	0;

		 QueryStatement		sql		=	new QueryStatement();
		 StringBuffer 		sb 		= 	new StringBuffer();
		 sb.append(" UPDATE report_info SET report_subject = ?, report_contents = ?" +
		 								", report_type2 = ?, report_start_date = ?" +
		 								", report_end_date = ?, report_extend_date = ?" +
		 								", report_score_yn = ?, report_open_yn = ?" +
		 								", report_regist_yn = ?, mod_id = ?" +
		 								", mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" WHERE system_code = ? AND curri_code = ? " +
		 				"AND curri_year = ? AND curri_term = ? " +
		 				"AND course_id = ? AND report_id = ?  ");
		 sql.setSql(sb.toString());

		 sql.setString(reportinfo.getReportSubject());
		 sql.setString(reportinfo.getReportContents());
		 sql.setString(reportinfo.getReportType2());
		 sql.setString(reportinfo.getReportStartDate());
		 sql.setString(reportinfo.getReportEndDate());
		 sql.setString(reportinfo.getReportExtendDate());
		 sql.setString(reportinfo.getReportScoreYn());
		 sql.setString(reportinfo.getReportOpenYn());
		 sql.setString(reportinfo.getReportRegistYn());
		 sql.setString(reportinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(reportinfo.getCourseId());
		 sql.setInteger(reportinfo.getReportId());

		 log.debug("[editReportInfo]" + sql.toString());
		

	 	//과제 제출 정보 삭제의 경우
		QueryStatement sql3 = new QueryStatement();
		StringBuffer sb3 = new StringBuffer();
		String reportSendDelYn = reportinfo.getReportSendDelYn();

		if(reportSendDelYn.equals("Y")) {
			 sb3.append(" DELETE FROM report_send ");
			 sb3.append(" WHERE system_code = ? AND curri_code = ? " +
			 				"AND curri_year = ? AND curri_term = ? " +
			 				"AND course_id = ? AND report_id = ? ");
			 
			 sql3.setSql(sb3.toString());

			 sql3.setString(systemcode);
			 sql3.setString(curriInfo.curriCode);
			 sql3.setInteger(curriInfo.curriYear);
			 sql3.setInteger(curriInfo.curriTerm);
			 sql3.setString(reportinfo.getCourseId());
			 sql3.setInteger(reportinfo.getReportId());

			 log.debug("[editReportInfo2]" + sql3.toString());
		 }

		 Connection conn = null;
		 DBConnectionManager pool = null;
		 try{

			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal 		= 	broker.executeUpdate(sql, conn);

	     	if(reportSendDelYn.equals("Y")) {
	     		retVal = broker.executeUpdate(sql3,conn);
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

	/**
	 * 학습자 - 과제리스트
	 * @param systemcode
	 * @param curriInfo
	 * @param courseid
	 * @param reportid
	 * @return
	 * @throws DAOException
	 */
	public RowSet getStReportList(String systemcode,CurriMemDTO curriinfo, String courseId, String userId) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.course_id , a.report_id, a.report_subject, a.report_contents, ");
			sb.append(" a.report_type2, a.report_start_date, a.report_end_date, a.report_extend_date, ");
			sb.append(" a.report_score_yn, a.report_open_yn, a.report_regist_yn, ");
			sb.append(" IFNULL(b.stu_reg_id,'') sendCheckYn, IFNULL(b.prof_mark_date,'') markCheckYn, IFNULL(b.user_id,'') insertYn, IFNULL(b.stu_open_date,'') stu_open_date ");
			sb.append(" from report_info a LEFT OUTER JOIN report_send b" +
					" ON a.system_code = b.system_code and a.curri_code = b.curri_code " +
					" and a.curri_year = b.curri_year and a.curri_term = b.curri_term " +
					" and a.course_id = b.course_id and a.report_id = b.report_id " +
					" and ? = b.user_id ");
			sb.append(" where ");
			sb.append(" a.system_code = ? and a.curri_code = ? and a.curri_year = ? and a.curri_term = ? and a.course_id = ?");
			sb.append(" and a.report_regist_yn = 'Y' " +
						" and a.report_start_date <= CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");

			sql.setSql(sb.toString());
			sql.setString(userId);
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(courseId);

			log.debug("[getStReportList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	/**
	 * 과제 상위 정보 삭제(하위 과제 및 제출 정보 일괄 삭제)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ReportId
	 * @return int
	 * @throws DAOException
	 */
	public int delReportInfo(String systemcode, CurriMemDTO curriinfo, String courseId, int reportId) throws DAOException{
		boolean		retVal		=	false;
		int			retVal2		=	0;

		 //하위 과제 정보
		 QueryStatement sql1 	= new QueryStatement();
		 StringBuffer sb1 		= new StringBuffer();
		 sb1.append(" delete from report_sub_info ");
		 sb1.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb1.append(" and curri_term = ? and course_id=? and report_id = ? ");

		 sql1.setSql(sb1.toString());
		 sql1.setString(systemcode);
		 sql1.setString(curriinfo.curriCode);
		 sql1.setInteger(curriinfo.curriYear);
		 sql1.setInteger(curriinfo.curriTerm);
		 sql1.setString(courseId);
		 sql1.setInteger(reportId);


		 //과제 제출 정보
		 QueryStatement sql2	= new QueryStatement();
		 StringBuffer sb2 		= new StringBuffer();
		 sb2.append(" delete from report_send ");
		 sb2.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb2.append(" and curri_term = ? and course_id=? and report_id = ? ");

		 sql2.setSql(sb2.toString());
		 sql2.setString(systemcode);
		 sql2.setString(curriinfo.curriCode);
		 sql2.setInteger(curriinfo.curriYear);
		 sql2.setInteger(curriinfo.curriTerm);
		 sql2.setString(courseId);
		 sql2.setInteger(reportId);


		 //과제정보
		 QueryStatement	sql3	=	new QueryStatement();
		 StringBuffer	sb3		=	new StringBuffer();
		 sb3.append(" delete from report_info  ");
		 sb3.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb3.append(" and curri_term = ? and course_id=? and report_id = ? ");

		 sql3.setSql(sb3.toString());
		 sql3.setString(systemcode);
		 sql3.setString(curriinfo.curriCode);
		 sql3.setInteger(curriinfo.curriYear);
		 sql3.setInteger(curriinfo.curriTerm);
		 sql3.setString(courseId);
		 sql3.setInteger(reportId);

		 ISqlStatement[] sqlArray = new ISqlStatement[3];

		 sqlArray[0] = sql1;
		 sqlArray[1] = sql2;
		 sqlArray[2] = sql3;

		 log.debug("[delReportSubInfo]" + sql1.toString());
		 log.debug("[delReportSend]" + sql2.toString());
		 log.debug("[delReportInfo]" + sql3.toString());

		 try{
			retVal = broker.executeUpdate(sqlArray);
			/**
			 * 반환값을 1, 0으로 성공(1), 실패(0)를 판단한다.
			 * DAO에 있는 반환값을 int로 하기때문에 아래와 같이 변환한다.
			 */
			if(retVal)
				retVal2 = 1;
			else
				retVal2 = 0;

		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal2;
	}


	/**
     * 리포트 랜덤 출제 정보를 가져온다.
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */
	public ReportInfoDTO getReportRandomSubInfo(String systemcode,CurriMemDTO curriinfo ,String courseid, int reportid) throws DAOException{

		ReportInfoDTO reportInfo = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		StringBuffer output = new StringBuffer();

		sb.append(" select course_id, report_id, report_subject, report_contents, report_type1, report_type2, ");
		sb.append(" report_start_date, report_end_date, report_extend_date, report_score_yn, report_open_yn, ");
		sb.append(" report_regist_yn, reg_id, reg_date, mod_id, mod_date ");
		sb.append(" from report_info ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");
		sql.setString(systemcode);
		sql.setString(curriinfo.curriCode);
		sql.setInteger(curriinfo.curriYear);
		sql.setInteger(curriinfo.curriTerm);
		if(!courseid.equals("")){
			sb.append(" and course_id = ? ");
			sql.setString(courseid);
		}
		if(reportid > 0){
			sb.append(" and report_id = ? ");
			sql.setInteger(reportid);
		}
		sb.append(" order by report_id desc ");
		sql.setSql(sb.toString());

		log.debug("[reportadminDAO_getReportInfo]" + sql.toString());

		ResultSet rs = null;

		try{

			rs = broker.executeQuery(sql);

			reportInfo = new ReportInfoDTO();
			if(rs.next()){
				reportInfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				reportInfo.setReportId(rs.getInt("report_id"));
				reportInfo.setReportSubject(StringUtil.nvl(rs.getString("report_subject")));
				reportInfo.setReportType1(StringUtil.nvl(rs.getString("report_type1")));
				reportInfo.setReportType2(StringUtil.nvl(rs.getString("report_type2")));
				reportInfo.setReportStartDate(StringUtil.nvl(rs.getString("report_start_date")));
				reportInfo.setReportEndDate(StringUtil.nvl(rs.getString("report_end_date")));
				reportInfo.setReportExtendDate(StringUtil.nvl(rs.getString("report_extend_date")));
				reportInfo.setReportScoreYn(StringUtil.nvl(rs.getString("report_score_yn")));
				reportInfo.setReportOpenYn(StringUtil.nvl(rs.getString("report_open_yn")));
				reportInfo.setReportRegistYn(StringUtil.nvl(rs.getString("report_regist_yn")));
				reportInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		        reportInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
		        reportInfo.setModId(StringUtil.nvl(rs.getString("mod_id")));
		        reportInfo.setModDate(StringUtil.nvl(rs.getString("mod_date")));

		        reportInfo.setReportContents(StringUtil.nvl(rs.getString("report_contents")));

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
		return reportInfo;
	}

	/**
	 * 등록된 과제 수를 가져온다.(평가관리에서 필요)	-###############################
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getReportInfoCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select IFNULL(count(report_id),0) as cnt ");
		 sb.append(" from report_info ");
		 sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		 sb.append(" and report_regist_yn = 'Y' and report_score_yn = 'Y' ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getReportInfoCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	cnt = rs.getInt("cnt");
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

		 return cnt;
	}

	/**
	 * 과제 정보(과목메인에 필요)
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @param NowDate
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getMainReportList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String UserId, String NowDate) throws DAOException{
	 ArrayList reportList =  new ArrayList();

 	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select a.system_code, a.curri_code, a.curri_year, a.curri_term, a.course_id, ");
	 sb.append(" a.report_id, a.report_start_date, a.report_end_date, a.report_subject, IFNULL(b.user_id, 'N') as user_id ");
	 sb.append(	" from report_info a left outer join report_send b  " +
	 			" on a.system_code = b.system_code and a.curri_code = b.curri_code " +
	 			" and a.curri_year = b.curri_year and a.curri_term = b.curri_term " +
	 			" and a.course_id = b.course_id and a.report_id = b.report_id " +
	 			" and ? = b.user_id ");

	 sb.append(" where ");
	 sb.append(" a.system_code = ? and a.curri_code = ? and a.curri_year = ? and a.curri_term = ? ");
	 sb.append(" and a.report_regist_yn = 'Y'  ");
	 sb.append(" order by a.report_start_date  ");

	 sql.setSql(sb.toString());
	 sql.setString(UserId);
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);

	 log.debug("[getMainReportList]" + sql.toString());
	

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 ReportInfoDTO reportinfo = null;
		 while(rs.next()){
		 	reportinfo = new ReportInfoDTO();
		 	reportinfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
		 	reportinfo.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
		 	reportinfo.setCurriYear(rs.getInt("curri_year"));
		 	reportinfo.setCurriTerm(rs.getInt("curri_term"));
		 	reportinfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
		 	reportinfo.setReportId(rs.getInt("report_id"));
		 	reportinfo.setReportSubject(StringUtil.nvl(rs.getString("report_subject")));
		 	reportinfo.setRegId(StringUtil.nvl(rs.getString("user_id")));
		 	reportinfo.setReportStartDate(StringUtil.nvl(rs.getString("report_start_date")));
		 	reportinfo.setReportEndDate(StringUtil.nvl(rs.getString("report_end_date")));
		 	reportList.add(reportinfo);
		 }
		 rs.close();
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }
	 return reportList;
	}
}
