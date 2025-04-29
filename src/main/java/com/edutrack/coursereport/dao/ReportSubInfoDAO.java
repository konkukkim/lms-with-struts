/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.coursereport.dto.ReportSubInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportSubInfoDAO extends AbstractDAO {

	/**
	 * 상위 과제 정보별 하위 과제 리스트
	 * @param systemcode
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public RowSet getReportSubInfoList(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select report_id, sub_report_id, sub_report_subject, ");
			sb.append(" rfile_name, sfile_name, file_path, file_size, reg_id, reg_date ");
			sb.append(" from report_sub_info ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
			sb.append(" and curri_term = ? and course_id = ? and report_id = ? ");
			sql.setString(systemCode);
			sql.setString(curriInfo.curriCode);
			sql.setInteger(curriInfo.curriYear);
			sql.setInteger(curriInfo.curriTerm);
			sql.setString(courseId);
			sql.setInteger(reportId);
			sql.setSql(sb.toString());

			log.debug("[getReportSubInfoList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return rs;
	}


	/**
	 * 서브 과제 정보 등록
	 * @param systemcode
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int addReportSubInfo(String systemcode, CurriMemDTO curriInfo, ReportSubInfoDTO reportSubInfo) throws DAOException{
		 int retVal 			= 	0;

		 int sub_report_id = getReportSubInfoMaxReportSubId(systemcode, curriInfo.curriCode, curriInfo.curriYear, curriInfo.curriTerm, reportSubInfo.getCourseId(), reportSubInfo.getReportId());

		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into report_sub_info(system_code, curri_code, curri_year, curri_term, course_id, report_id, sub_report_id, ");
		 sb.append(" sub_report_subject, sub_report_contents, rfile_name, sfile_name, file_path, file_size, reg_id, reg_date) ");
		 sb.append(" values( ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(reportSubInfo.getCourseId());
		 sql.setInteger(reportSubInfo.getReportId());
		 sql.setInteger(sub_report_id);
		 sql.setString(reportSubInfo.getSubReportSubject());
		 sql.setString(reportSubInfo.getSubReportContents());
		 sql.setString(reportSubInfo.getRfileName());
		 sql.setString(reportSubInfo.getSfileName());
		 sql.setString(reportSubInfo.getFilePath());
		 sql.setString(reportSubInfo.getFileSize());
		 sql.setString(reportSubInfo.getRegId());

		 log.debug("[addReportSubInfo1]" + sql.toString());

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
     * 서브 과제 정보 수정
     * @param systemcode
     * @param curriinfo
     * @param courseid
     * @return
     * @throws DAOException
     */

	public int editReportSubInfo(String systemcode, CurriMemDTO curriInfo, ReportSubInfoDTO reportSubInfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update report_sub_info set ");
		 sb.append(" sub_report_subject = ?, sub_report_contents = ?, ");
		 sb.append(" rfile_name = ?, sfile_name = ?, file_path = ?, file_size = ?, mod_id = ?, ");
		 sb.append(" mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and report_id = ? and sub_report_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(reportSubInfo.getSubReportSubject());
		 sql.setString(reportSubInfo.getSubReportContents());
		 sql.setString(reportSubInfo.getRfileName());
		 sql.setString(reportSubInfo.getSfileName());
		 sql.setString(reportSubInfo.getFilePath());
		 sql.setString(reportSubInfo.getFileSize());
		 sql.setString(reportSubInfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(reportSubInfo.getCourseId());
		 sql.setInteger(reportSubInfo.getReportId());
		 sql.setInteger(reportSubInfo.getSubReportId());

		 log.debug("[editReportSubInfo]" + sql.toString());

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
	 * report_sub_info의 max 번호를 가져온다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int getReportSubInfoMaxReportSubId(String systemCode, String curriCode, String curriYear, String curriTerm, String courseId, int reportId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		StringBuffer sb = new StringBuffer();
		sb.append(" select IFNULL(MAX(sub_report_id), 0)+1 as max_no from report_sub_info ");
		sb.append(" where system_code = ? and curri_code = ? ");
		sb.append(" and curri_year = ? and curri_term = ? ");
		sb.append(" and course_id = ? and report_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);

		 log.debug("[getReportSubInfoMaxReportSubId]" + sql.toString());
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
	 * 문제 은행 문항의 max 번호를 가져온다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int getReportBankContentsMaxReportId(String systemCode, String courseId, int bankId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select IFNULL(max(report_id),0)+1 as max_no from report_bank_contents ");
		 sb.append(" where system_code = ? and course_id = ? and report_bank_id =? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(courseId);
		 sql.setInteger(bankId);
		 log.debug("[getReportBankContentsMaxReportId]" + sql.toString());
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
	 * 리포트 서브 개별 정보 가져오기
	 * @param systemcode
	 * @param courseid
	 * @param bankcode
	 * @param reportno
	 * @return
	 * @throws DAOException
	 */
	public ReportSubInfoDTO getReportSubInfo(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, int subReportId) throws DAOException{
		ReportSubInfoDTO reportSubInfo = null;
		ResultSet rs = null;

	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select report_id, sub_report_id, sub_report_subject, sub_report_contents, ");
		sb.append(" rfile_name, sfile_name, file_path, file_size, reg_id, reg_date ");
		sb.append(" from report_sub_info ");
		sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and course_id = ? and report_id = ? and sub_report_id= ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);
		sql.setInteger(subReportId);

		log.debug("[getReportSubInfo]" + sql.toString());

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
	 * 과제 첨부 파일을 삭제한다.
	 * @param systemCode
	 * @param courseId
	 * @param bankCode
	 * @param reportNo
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public int delReportSubInfoFile(String systemCode, CurriMemDTO curriInfo, String courseId, int reportId, int subReportId, String userId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update report_sub_info set rfile_name='', sfile_name='', file_path='', file_size='', mod_id=?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and course_id = ? and report_id = ? and sub_report_id= ? ");
		sql.setSql(sb.toString());

		sql.setString(userId);
		sql.setString(systemCode);
		sql.setString(curriInfo.curriCode);
		sql.setInteger(curriInfo.curriYear);
		sql.setInteger(curriInfo.curriTerm);
		sql.setString(courseId);
		sql.setInteger(reportId);
		sql.setInteger(subReportId);

		log.debug("[delReportBankContentsFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 하위 과제 정보를 삭제한다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int delReportSubInfo(String systemcode, CurriMemDTO curriInfo, String courseId, int reportId, int subReportId)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from report_sub_info ");
		 sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and report_id = ? and sub_report_id= ? ");

		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseId);
		 sql.setInteger(reportId);
		 sql.setInteger(subReportId);


		 log.debug("[delReportSubInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
}
