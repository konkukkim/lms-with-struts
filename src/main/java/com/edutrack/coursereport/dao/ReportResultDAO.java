/*
 * Created on 2004. 10. 20.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.coursereport.dto.ReportSendDTO;
import com.edutrack.coursereport.dto.ReportSubInfoDTO;
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
public class ReportResultDAO extends AbstractDAO {

	/**
	 *
	 */
	public ReportResultDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과제 평가 제출 리스트(교수자) 	- ########################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public RowSet getReportUserList(String systemcode,CurriMemDTO curriinfo ,String courseid, int reportid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.user_id,(select f.user_name from users f where f.system_code = s.system_code and f.user_id = s.user_id) user_name,  ");
			sb.append(" s.enroll_no, s.enroll_status, IFNULL(a.stu_reg_id,'') checkYn, IFNULL(a.stu_reg_date, stu_mod_date) stu_mod_date, a.score, a.report_id, a.sub_report_id, ");
			sb.append(" IFNULL((select report_end_date  from report_info c where c.system_code = a.system_code and c.curri_code = a.curri_code and c.curri_year = a.curri_year ");
			sb.append(" and c.curri_term = a.curri_term and c.course_id = a.course_id and c.report_id = a.report_id),0) report_end_date, ");
			sb.append(" IFNULL((select report_extend_date  from report_info c where c.system_code = a.system_code and c.curri_code = a.curri_code and c.curri_year = a.curri_year ");
			sb.append(" and c.curri_term = a.curri_term and c.course_id = a.course_id and c.report_id = a.report_id),0) report_extend_date ");
			sb.append(" from student s LEFT OUTER JOIN report_send a " +
					" ON s.system_code = a.system_code and s.curri_code = a.curri_code " +
					" and s.curri_year = a.curri_year and s.curri_term = a.curri_term " +
					" and s.user_id = a.user_id and ? = a.course_id and ? = a.report_id ");
			sb.append(" where ");
			sb.append(" s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term = ? and s.enroll_status = 'S' ");
			sb.append("  ");

			sql.setString(courseid);
			sql.setInteger(reportid);

			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);

			sql.setSql(sb.toString());

			log.debug("[getReportUserList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}


	/**
	 * 리포트 서브 정보 랜덤으로 가져오기(랜덤 출제시 정보)	-############################
	 * @param systemcode
	 * @param courseid
	 * @param bankcode
	 * @param reportno
	 * @return
	 * @throws DAOException
	 */
	public ReportSubInfoDTO getReportSubInfoRandom(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId) throws DAOException{
		ReportSubInfoDTO reportSubInfo = null;
		ResultSet rs = null;

	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
/*
		sb.append(" select a.randomNum, ");
		sb.append(" a.report_id, a.sub_report_id, a.sub_report_subject, a.sub_report_contents, ");
		sb.append(" a.rfile_name, a.sfile_name, a.file_path, a.file_size, a.reg_id, a.reg_date ");
		sb.append(" from ( ");
		sb.append(" select ROW_NUMBER() OVER (partition by null order by dbms_random.value) randomNum, ");
		sb.append(" report_id, sub_report_id, sub_report_subject, sub_report_contents, ");
		sb.append(" rfile_name, sfile_name, file_path, file_size, reg_id, reg_date ");
		sb.append(" from report_sub_info ");
		sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and course_id = ? and report_id = ? ) a ");
		sb.append(" where a.randomNum = 1 ");
*/
		sb.append(" SELECT " +
						" report_id, sub_report_id, sub_report_subject, sub_report_contents" +
						", rfile_name, sfile_name, file_path, file_size" +
						", reg_id, reg_date  ");
		sb.append(" FROM report_sub_info   ");
		sb.append(" WHERE " +
						" system_code = ?  AND curri_code = ? " +
						" AND curri_year = ? AND curri_term = ?  " +
						" AND course_id = ? AND report_id = ?  ");
		sb.append(" ORDER BY RAND() ");
		sb.append(" LIMIT 1 ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);

		log.debug("[getReportSubInfoRandom]" + sql.toString());

		try{
			rs = broker.executeQuery(sql);

			reportSubInfo = new ReportSubInfoDTO();
            if(rs.next()){
            	reportSubInfo.setReportId(rs.getInt("report_id"));
            	reportSubInfo.setSubReportId(rs.getInt("sub_report_id"));
            	reportSubInfo.setSubReportSubject(StringUtil.nvl(rs.getString("sub_report_subject")));
            	reportSubInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
            	reportSubInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
            	reportSubInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
            	reportSubInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
            	reportSubInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
            	reportSubInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
            	reportSubInfo.setSubReportContents(StringUtil.nvl(rs.getString("sub_report_contents")));
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
		return reportSubInfo;
	}

	/**
	 * 리포트 서브 정보 랜덤 및 선택으로 등록된 정보 가져오기	-############################
	 * @param systemcode
	 * @param courseid
	 * @param bankcode
	 * @param reportno
	 * @return
	 * @throws DAOException
	 */
	public ReportSubInfoDTO getReportSubInfoRandom2(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, String userId) throws DAOException{
		ReportSubInfoDTO reportSubInfo = null;
		ResultSet rs = null;

	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.report_id, a.sub_report_id, a.sub_report_subject, a.sub_report_contents, ");
		sb.append(" a.rfile_name, a.sfile_name, a.file_path, a.file_size, a.reg_id, a.reg_date ");
		sb.append(" from report_sub_info a, report_send b ");
		sb.append(" where a.system_code = b.system_code and a.curri_code = b.curri_code and a.curri_year = b.curri_year ");
		sb.append(" and a.curri_term = b.curri_term and a.course_id = b.course_id and a.report_id = b.report_id ");
		sb.append(" and a.sub_report_id = b.sub_report_id ");
		sb.append(" and a.system_code = ?  and a.curri_code = ? and a.curri_year = ? and a.curri_term = ? ");
		sb.append(" and a.course_id = ? and a.report_id = ? and b.user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);
		sql.setString(userId);

		log.debug("[getReportSubInfoRandom2]" + sql.toString());

		try{

			rs = broker.executeQuery(sql);

			reportSubInfo = new ReportSubInfoDTO();
            if(rs.next()){
            	reportSubInfo.setReportId(rs.getInt("report_id"));
            	reportSubInfo.setSubReportId(rs.getInt("sub_report_id"));
            	reportSubInfo.setSubReportSubject(StringUtil.nvl(rs.getString("sub_report_subject")));
            	reportSubInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
            	reportSubInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
            	reportSubInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
            	reportSubInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
            	reportSubInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
            	reportSubInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
            	reportSubInfo.setSubReportContents(StringUtil.nvl(rs.getString("sub_report_contents")));
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
		return reportSubInfo;
	}

	/**
	 * 리포트 제출 정보 가져오기	-############################
	 * @param systemcode
	 * @param courseid
	 * @param bankcode
	 * @param reportno
	 * @return
	 * @throws DAOException
	 */
	public ReportSendDTO getReportSend(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, String userId) throws DAOException{
		ReportSendDTO reportSend = null;
		ResultSet rs = null;

	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.report_id, b.user_id, b.sub_report_id, b.report_send_subject, b.report_send_contents, editor_type, ");
		sb.append(" b.rfile_name1, b.sfile_name1, b.file_path1, b.file_size1, ");
		sb.append(" b.rfile_name2, b.sfile_name2, b.file_path2, b.file_size2, ");
		sb.append(" b.rfile_name3, b.sfile_name3, b.file_path3, b.file_size3, ");
		sb.append(" IFNULL(b.send_count,0) send_count, b.stu_open_date, b.stu_reg_id, b.stu_reg_date, b.stu_mod_id, b.stu_mod_date, ");
		sb.append(" b.prof_mark_date, IFNULL(b.score,0) score, b.prof_comment, b.reg_id, b.reg_date ");
		sb.append(" from report_sub_info a, report_send b  ");
		sb.append(" where a.system_code = b.system_code and a.curri_code = b.curri_code and a.curri_year = b.curri_year ");
		sb.append(" and a.curri_term = b.curri_term and a.course_id = b.course_id and a.report_id = b.report_id ");
		//sb.append(" and a.sub_report_id = b.sub_report_id ");
		sb.append(" and a.system_code = ?  and a.curri_code = ? and a.curri_year = ? and a.curri_term = ? ");
		sb.append(" and a.course_id = ? and a.report_id = ? and b.user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);
		sql.setString(userId);

		log.debug("[getReportSend]" + sql.toString());

		try{

			rs = broker.executeQuery(sql);

			reportSend = new ReportSendDTO();
            if(rs.next()){
            	reportSend.setReportId(rs.getInt("report_id"));
            	reportSend.setUserId(StringUtil.nvl(rs.getString("user_id")));
            	reportSend.setSubReportId(rs.getInt("sub_report_id"));
            	reportSend.setReportSendSubject(StringUtil.nvl(rs.getString("report_send_subject")));
            	reportSend.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
            	reportSend.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
            	reportSend.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
            	reportSend.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
            	reportSend.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
            	reportSend.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
            	reportSend.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
            	reportSend.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
            	reportSend.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
            	reportSend.setRfileName3(StringUtil.nvl(rs.getString("rfile_name3")));
            	reportSend.setSfileName3(StringUtil.nvl(rs.getString("sfile_name3")));
            	reportSend.setFilePath3(StringUtil.nvl(rs.getString("file_path3")));
            	reportSend.setFileSize3(StringUtil.nvl(rs.getString("file_size3")));
            	reportSend.setSendCount(rs.getInt("send_count"));
            	reportSend.setStuOpenDate(StringUtil.nvl(rs.getString("stu_open_date")));
            	reportSend.setStuRegId(StringUtil.nvl(rs.getString("stu_reg_id")));
            	reportSend.setStuRegDate(StringUtil.nvl(rs.getString("stu_reg_date")));
            	reportSend.setStuModId(StringUtil.nvl(rs.getString("stu_mod_id")));
            	reportSend.setStuModDate(StringUtil.nvl(rs.getString("stu_mod_date")));
            	reportSend.setProfMarkDate(StringUtil.nvl(rs.getString("prof_mark_date")));
            	reportSend.setScore(rs.getInt("score"));
            	reportSend.setRegId(StringUtil.nvl(rs.getString("reg_id")));
            	reportSend.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

            	reportSend.setReportSendContents(StringUtil.nvl(rs.getString("report_send_contents")));
            	reportSend.setProfComment(StringUtil.nvl(rs.getString("prof_comment")));
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
		return reportSend;
	}


	/**
	 * 랜덤 출제 타입 과제 정보 클릭시 기본정보 등록(학생) 	-######################
	 * @param systemCode
	 * @param courseId
	 * @param bankName
	 */
	public int addRandomReportSend(String systemcode, CurriMemDTO curriInfo, String courseId, int reportId, int subReportId, String userId)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into report_send(system_code, curri_code, curri_year, curri_term, course_id, report_id, user_id, sub_report_id, reg_id, reg_date) ");
		 sb.append(" values (?,?,?,?,?,?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseId);
		 sql.setInteger(reportId);
		 sql.setString(userId);
		 sql.setInteger(subReportId);
		 sql.setString(userId);

		 log.debug("[addRandomReportSend]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 리포트 제출 정보 등록(학생)	-############################
	 * @param systemcode
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int addReportSend(String systemcode, CurriMemDTO curriInfo, ReportSendDTO reportSendDto) throws DAOException{
		 int retVal 			= 	0;

		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into report_send(system_code, curri_code, curri_year, curri_term, course_id, report_id, user_id, sub_report_id, ");
		 sb.append(" report_send_subject, report_send_contents, editor_type, rfile_name1, sfile_name1, file_path1, file_size1, ");
		 sb.append(" rfile_name2, sfile_name2, file_path2, file_size2, send_count, stu_reg_id, stu_reg_date, reg_id, reg_date) ");
		 sb.append(" values( ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, 1" +
		 					", ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);

		 sql.setString(reportSendDto.getCourseId());
		 sql.setInteger(reportSendDto.getReportId());
		 sql.setString(reportSendDto.getUserId());
		 sql.setInteger(reportSendDto.getSubReportId());

		 sql.setString(reportSendDto.getReportSendSubject());
		 sql.setString(reportSendDto.getReportSendContents());
		 sql.setString(reportSendDto.getEditorType());
		 sql.setString(reportSendDto.getRfileName1());

		 sql.setString(reportSendDto.getSfileName1());
		 sql.setString(reportSendDto.getFilePath1());
		 sql.setString(reportSendDto.getFileSize1());
		 sql.setString(reportSendDto.getRfileName2());
		 sql.setString(reportSendDto.getSfileName2());
		 sql.setString(reportSendDto.getFilePath2());
		 sql.setString(reportSendDto.getFileSize2());
		 sql.setString(reportSendDto.getRegId());
		 sql.setString(reportSendDto.getRegId());

		 log.debug("[addReportSend]" + sql.toString());


		 try{
		     retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
		 	e.printStackTrace();
			throw new DAOException(e.getMessage());
		 } finally {
			try {

			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
		 return retVal;
	}

	/**
     * 리포트 제출 정보 수정(학생)	-############################
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */

	public int editReportSend(String systemcode, CurriMemDTO curriInfo, ReportSendDTO reportSendDto, String pMODE) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update report_send set ");
		 sb.append(" report_send_subject = ?, report_send_contents = ?, editor_type = ?, ");
		 sb.append(" rfile_name1 = ?, sfile_name1 = ?, file_path1 = ?, file_size1 = ?, ");
		 sb.append(" rfile_name2 = ?, sfile_name2 = ?, file_path2 = ?, file_size2 = ?, ");
		 if(pMODE.equals("ADD")) {
			 sb.append(" stu_reg_id = ?, stu_reg_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 } else {
			 sb.append(" stu_mod_id = ?, stu_mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 }
		 sb.append(" send_count = IFNULL(send_count,0)+1, mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and report_id = ? and user_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(reportSendDto.getReportSendSubject());
		 sql.setString(reportSendDto.getReportSendContents());
		 sql.setString(reportSendDto.getEditorType());
		 sql.setString(reportSendDto.getRfileName1());
		 sql.setString(reportSendDto.getSfileName1());
		 sql.setString(reportSendDto.getFilePath1());
		 sql.setString(reportSendDto.getFileSize1());
		 sql.setString(reportSendDto.getRfileName2());
		 sql.setString(reportSendDto.getSfileName2());
		 sql.setString(reportSendDto.getFilePath2());
		 sql.setString(reportSendDto.getFileSize2());
		 sql.setString(reportSendDto.getModId());
		 sql.setString(reportSendDto.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(reportSendDto.getCourseId());
		 sql.setInteger(reportSendDto.getReportId());
		 sql.setString(reportSendDto.getUserId());;

		 log.debug("[editReportSend]" + sql.toString());

		 try{

		    retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
		 	e.printStackTrace();
			throw new DAOException(e.getMessage());
		 } finally {
			try {

			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
		 return retVal;
	}

	/**
	 * 과제 첨부 파일을 삭제한다.  -#############################
	 * @param systemCode
	 * @param courseId
	 * @param bankCode
	 * @param reportNo
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public int delReportSendFile(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, String userId, String modId, String type) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update report_send set ");
		if(type.equals("1")) {
			sb.append(" rfile_name1 = '', sfile_name1 = '', file_path1 = '', file_size1 = '', send_count = IFNULL(send_count,0)+1, ");
		} else if(type.equals("2")) {
			sb.append(" rfile_name2 = '', sfile_name2 = '', file_path2 = '', file_size2 = '', send_count = IFNULL(send_count,0)+1, ");
		} else {
			sb.append(" rfile_name3 = '', sfile_name3 = '', file_path3 = '', file_size3 = '',  ");
		}
		sb.append(" mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and course_id = ? and report_id = ? and user_id = ? ");
		sql.setSql(sb.toString());

		sql.setString(modId);
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);
		sql.setString(userId);

		log.debug("[delReportSendFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		if(retVal > 0) {
			retVal = Integer.parseInt(type);
		}
		return retVal;
	}


	/**
     * 평가 정보 입력 & 수정(교수자)	-############################
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */

	public int editReportProfSend(String systemcode, CurriMemDTO curriInfo, ReportSendDTO reportSendDto) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update report_send set ");
		 sb.append(" score = ?, prof_comment = ?, ");
		 sb.append(" rfile_name3 = ?, sfile_name3 = ?, file_path3 = ?, file_size3 = ?, ");
		 sb.append(" prof_mark_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)" +
		 			", mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and report_id = ? and user_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setInteger(reportSendDto.getScore());
		 sql.setString(reportSendDto.getProfComment());
		 sql.setString(reportSendDto.getRfileName3());
		 sql.setString(reportSendDto.getSfileName3());
		 sql.setString(reportSendDto.getFilePath3());
		 sql.setString(reportSendDto.getFileSize3());
		 sql.setString(reportSendDto.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(reportSendDto.getCourseId());
		 sql.setInteger(reportSendDto.getReportId());
		 sql.setString(reportSendDto.getUserId());

		 log.debug("[editReportProfSend]" + sql.toString());

		 try{
		     retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
		 	e.printStackTrace();
			throw new DAOException(e.getMessage());
		 } finally {
			try {

			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
		 return retVal;
	}


	/**
	 *  insert & update 구분(과제 점수 업로드를 위해)
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int getReportSendWriteGubun(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, String userId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		StringBuffer sb = new StringBuffer();
		sb.append(" select decode(count(user_id),0,1,2) gubun from report_send ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and course_id = ? and report_id = ? and user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);
		sql.setString(userId);
		log.debug("[getReportSendWriteGubun]" + sql.toString());
		 try{
		 	rs = broker.executeQuery(sql);

			if(rs.next()){
				retVal = rs.getInt("gubun");
			}
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		return retVal;
	}

	/**
	 *  수강중인 학생 체크 여부(과제 점수 업로드를 위해)
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int getReportStudentGubun(String systemCode, CurriMemDTO curriInfo, String userId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		StringBuffer sb = new StringBuffer();
		sb.append(" select decode(count(user_id),0,1,2) gubun from student ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and user_id = ? and enroll_status = 'S' ");
		sql.setSql(sb.toString());
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(userId);
		 log.debug("[getReportStudentGubun]" + sql.toString());
		 try{
		 	rs = broker.executeQuery(sql);

			if(rs.next()){
				retVal = rs.getInt("gubun");
			}
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		return retVal;
	}


	/**
	 * 점수를 등록한다(파일 일괄 등록)	-######################################
	 * @param systemCode
	 * @param courseId
	 * @param bankName
	 */
	public int addReportSendScore(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, String userId, int score, String modId, int gubun)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 if(gubun == 1) {
			 sb.append(" insert into report_send(system_code, curri_code, curri_year, curri_term, course_id, report_id, user_id, sub_report_id, prof_mark_date, score, reg_id, reg_date) ");
			 sb.append(" values(?, ?, ?, ?, ?, ?, ?, 0, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

			 sql.setSql(sb.toString());
			 sql.setString(systemCode);
			 sql.setString(curriInfo.curriCode);
			 sql.setInteger(curriInfo.curriYear);
			 sql.setInteger(curriInfo.curriTerm);
			 sql.setString(courseId);
			 sql.setInteger(reportId);
			 sql.setString(userId);
			 sql.setInteger(score);
			 sql.setString(modId);
		 } else if(gubun == 2) {
			 sb.append(" update report_send set ");
			 sb.append(" score = ?, prof_mark_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
			 sb.append(" mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
			 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
			 sb.append(" and course_id = ? and report_id = ? and user_id = ? ");
			 sql.setSql(sb.toString());
			 sql.setInteger(score);
			 sql.setString(modId);
			 sql.setString(systemCode);
			 sql.setString(curriInfo.curriCode);
			 sql.setInteger(curriInfo.curriYear);
			 sql.setInteger(curriInfo.curriTerm);
			 sql.setString(courseId);
			 sql.setInteger(reportId);
			 sql.setString(userId);
		 }

		 log.debug("[addReportSendScore]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 수강생 성적 확인일을 입력한다.	-####################################
	 * @param systemCode
	 * @param courseId
	 * @param bankName
	 */
	public int addReportSendStuOpenDate(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, String userId, String modId)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();

		 sb.append(" update report_send set ");
		 sb.append(" stu_open_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 sb.append(" mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and report_id = ? and user_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(modId);
		 sql.setString(systemCode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseId);
		 sql.setInteger(reportId);
		 sql.setString(userId);

		 log.debug("[addReportSendStuOpenDate]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 과제 평가 제출 리스트 (다른 사람 제출 정보)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public RowSet getReportOtherUserList(String systemcode,CurriMemDTO curriinfo ,String courseid, int reportid, String userId) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.user_id,(select f.user_name from users f where f.system_code = s.system_code and f.user_id = s.user_id) user_name,  ");
			sb.append(" s.enroll_no, s.enroll_status, IFNULL(a.stu_reg_id,'') checkYn, IFNULL(a.stu_reg_date, stu_mod_date) stu_mod_date, a.score, a.report_id, a.sub_report_id, ");
			sb.append(" rfile_name1, sfile_name1, file_path1, file_size1, rfile_name2, sfile_name2, file_path2, file_size2, ");
			sb.append(" IFNULL((select report_end_date  from report_info c where c.system_code = a.system_code and c.curri_code = a.curri_code and c.curri_year = a.curri_year ");
			sb.append(" and c.curri_term = a.curri_term and c.course_id = a.course_id and c.report_id = a.report_id),0) report_end_date, ");
			sb.append(" IFNULL((select report_extend_date  from report_info c where c.system_code = a.system_code and c.curri_code = a.curri_code and c.curri_year = a.curri_year ");
			sb.append(" and c.curri_term = a.curri_term and c.course_id = a.course_id and c.report_id = a.report_id),0) report_extend_date ");
			sb.append(" from student s, report_send a  ");
			sb.append(" where s.system_code = a.system_code and s.curri_code = a.curri_code ");
			sb.append(" and s.curri_year = a.curri_year and s.curri_term = a.curri_term and s.user_id = a.user_id ");
			sb.append(" and s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term = ? and s.enroll_status = 'S' ");
			sb.append(" and a.course_id = ? and a.report_id = ? and a.stu_reg_id is not null and a.user_id <> ? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(courseid);
			sql.setInteger(reportid);
			sql.setString(userId);
			sql.setSql(sb.toString());

			log.debug("[getReportOtherUserList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	/**
	 * 과제물 응시정보(과목메인)
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @param NowDate
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getMainReportList(String systemcode,CurriMemDTO curriinfo, String userId, String nowDate) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.system_code, a.curri_code, a.curri_year, a.curri_term, a.course_id, a.report_id,  ");
			sb.append(" a.report_start_date, a.report_end_date, a.report_extend_date, a.report_subject, ");
			sb.append(" b.sub_report_id, IFNULL(b.send_count,0) send_count ");
			sb.append(" from report_info a LEFT OUTER JOIN report_send b " +
					" ON a.system_code = b.system_code and a.curri_code = b.curri_code " +
					" and a.curri_term = b.curri_term and	a.course_id = b.course_id " +
					" and a.report_id = b.report_id and ? = b.user_id ");
			sb.append(" where  ");
			sb.append(" a.report_regist_yn = 'Y'  ");
			sb.append(" and a.system_code = ? and a.curri_code = ? and a.curri_year = ? and a.curri_term = ? ");
			sb.append(" and a.report_start_date <= ? and a.report_extend_date >= ? ");
			sb.append(" order by a.report_extend_date ");

			sql.setString(userId);
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(nowDate);
			sql.setString(nowDate);
			sql.setSql(sb.toString());

			log.debug("[getMainReportList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}
}
