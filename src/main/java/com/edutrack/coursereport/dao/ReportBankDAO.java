/*
 * Created on 2004. 10. 21.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.coursereport.dto.ReportBankContentsDTO;
import com.edutrack.coursereport.dto.ReportBankInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportBankDAO  extends AbstractDAO {

	/**
	 *
	 */
	public ReportBankDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 문제은행 항목들을 가져온다
	 */
	public RowSet getReportBankInfoCodeList(String systemcode, String courseid) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		RowSet rs = null;

		sb.append(" select report_bank_id, report_bank_name ");
		sb.append(" from report_bank_info ");
		sb.append(" where  system_code = ? and course_id = ? ");
		sb.append(" order by report_bank_id");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(courseid);
		log.debug("[getReportBankInfoCodeList]" + sql.toString());
		try{
			rs = broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 문제은행 이름을 가져온다.
	 * @param systemCode
	 * @param courseId
	 * @return
	 */
	public ReportBankInfoDTO getReportBankInfo(String systemCode, String courseId, long bankCode)  throws DAOException{
		ReportBankInfoDTO bankInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select report_bk_code,report_bk_name ");
			sb.append(" from report_bk_info ");
			sb.append(" where  system_code = ? and course_id = ? and report_bk_code = ?");
			sql.setSql(sb.toString());
			sql.setString(systemCode);
			sql.setString(courseId);
			sql.setLong(bankCode);

			log.debug("[getReportBankInfo]" + sql.toString());

			rs = broker.executeQuery(sql);

			bankInfo = new ReportBankInfoDTO();
			if(rs.next()){
				bankInfo.setReportBankId(rs.getInt("report_bank_id"));
				bankInfo.setReportBankName(StringUtil.nvl(rs.getString("report_bank_name")));

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

		 return bankInfo;
	}

	/**
	 * 문제항목을 등록한다.
	 * @param systemCode
	 * @param courseId
	 * @param bankName
	 */
	public int addReportBankInfo(String systemcode,String userid, String courseid, String bankname)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into report_bank_info(system_code,course_id,report_bank_id, report_bank_name, reg_id, reg_date) ");
		 sb.append(" select ?,?,IFNULL(max(report_bank_id),0)+1 as report_bank_id,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from report_bank_info ");
		 sb.append(" where system_code = ? and course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(courseid);
		 sql.setString(bankname);
		 sql.setString(userid);
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 log.debug("[addReportBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
	/**
	 * 문제 은행 항목 등록 후 bankCode 반환
	 * @param systemcode
	 * @param userid
	 * @param courseid
	 * @param bankname
	 * @return
	 * @throws DAOException
	 */
	public long addReportBankInfoReturnBankId(String systemcode,String userid, String courseid, String bankname)  throws DAOException{
		 int retVal 			= 	0;
		 long retVal2			= 0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into report_bank_info(system_code,course_id,report_bank_id, report_bk_name, reg_id, reg_date) ");
		 sb.append(" select ?,?,IFNULL(max(report_bank_id),0)+1 as report_bank_id,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from report_bank_id ");
		 sb.append(" where system_code = ? and course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(courseid);
		 sql.setString(bankname);
		 sql.setString(userid);
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 log.debug("[addReportBankInfo]" + sql.toString());
		 Connection conn = null;
		 DBConnectionManager pool = null;
		 ResultSet rs = null;
		 try{
		 	pool = DBConnectionManager.getInstance();
            conn = pool.getConnection();
		     retVal 		= 	broker.executeUpdate(sql,conn);
		     if(retVal >0){
		     	QueryStatement sql2 	= 	new QueryStatement();
				 StringBuffer sb2 = new StringBuffer();
				 sb2.append(" select max(report_bank_id) as report_bank_id ");
				 sb2.append(" from report_bank_info ");
				 sb2.append(" where system_code = ? and course_id = ? ");
				 sql2.setSql(sb2.toString());
				 sql2.setString(systemcode);
				 sql2.setString(courseid);
				 rs 		= 	broker.executeQuery(sql2,conn);
				 rs.next();
				 retVal2 = rs.getLong("report_bank_id");
				 rs.close();
		     }
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
		 	try{
		 		if(rs != null) rs.close();
			    if(conn != null){
			        conn.setAutoCommit( true );
			        pool.freeConnection(conn); // 컨넥션 해제
			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }
		 return retVal2;
	}

	/**
	 * 문제항목을 수정한다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int editReportBankInfo(String systemcode, String userid, String courseid,long bankid, String bankname)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update report_bank_info ");
		 sb.append(" set report_bank_name = ? ");
		 sb.append(" where  system_code = ? and course_id = ? and report_bank_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(bankname);
		 sql.setString(systemcode);
		 sql.setString(courseid);
		 sql.setLong(bankid);

		 log.debug("[addReportBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 문제항목 및 해당 문제 문항을 전부 삭제한다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public boolean delReportBankInfo(String systemcode, String userid, String courseid,long bankid)  throws DAOException{
		 boolean retVal 			= 	false;
		 QueryStatement[] sqls 	= 	new QueryStatement[2];
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from report_bank_contents ");
		 sb.append(" where  system_code = ? and course_id = ? ");
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 if(bankid > 0) {
		 	sb.append(" and report_bank_id = ?");
			 sql.setLong(bankid);
		 }
		 sql.setSql(sb.toString());

		 sqls[0] = sql;

		 QueryStatement sql2 	= 	new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 sb2.append(" delete from report_bank_info ");
		 sb2.append(" where  system_code = ? and course_id = ? ");
		 sql2.setString(systemcode);
		 sql2.setString(courseid);

		 if(bankid > 0){
		 	sb2.append(" and report_bank_id = ? ");
		 	sql2.setLong(bankid);
		 }
		 sql2.setSql(sb2.toString());

		 sqls[1] = sql2;

		 log.debug("[delReportBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 문제 은행 문항을 개별 삭제한다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int delReportBankContents(String systemcode, String courseid,long bankid, int reportid)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from report_bank_contents ");
		 sb.append(" where  system_code = ? and course_id = ? ");
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 if(bankid > 0){
		 	sb.append(" and report_bank_id = ? ");
		 	sql.setLong(bankid);
		 }

		 if(reportid > 0) {
		 	sb.append(" and report_id = ?");
			sql.setInteger(reportid);
		 }
		 sql.setSql(sb.toString());

		 log.debug("[delReportBankContents]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
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
	public int getReportBankContentsMaxReportId(String systemCode, String courseId, long bankId) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select IFNULL(max(report_id),0)+1 as max_no from report_bank_contents ");
		 sb.append(" where system_code = ? and course_id = ? and report_bank_id =? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(courseId);
		 sql.setLong(bankId);
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
	 * 문제 은행 문항 등록
	 * @param systemcode
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int addReportBankContentsInfo(String systemcode,ReportBankContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;

		 int report_id = getReportBankContentsMaxReportId(systemcode,contentsinfo.getCourseId(),contentsinfo.getReportBankId());
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into report_bank_contents(system_code, course_id, report_bank_id, report_id" +
		 											", report_subject, report_contents, rfile_name, sfile_name" +
		 											", file_path, file_size, reg_id, reg_date) ");
		  sb.append(" values( ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setLong(contentsinfo.getReportBankId());
		 sql.setInteger(report_id);

		 sql.setString(contentsinfo.getReportSubject());
		 sql.setString(contentsinfo.getReportContents());
		 sql.setString(contentsinfo.getRfileName());
		 sql.setString(contentsinfo.getSfileName());
		 sql.setString(contentsinfo.getFilePath());
		 sql.setString(contentsinfo.getFileSize());
		 sql.setString(contentsinfo.getRegId());

		 log.debug("[addReportContentsInfo]" + sql.toString());

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
	 * 문제 은행 문항 수정
	 * @param systemcode
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int editReportBankContentsInfo(String systemcode, ReportBankContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update report_bank_contents ");
		 sb.append(" set report_subject = ?, report_contents = ?, ");
		 sb.append(" rfile_name = ?, sfile_name = ?, file_path = ?, file_size = ?, mod_id = ?, ");
		 sb.append(" mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and course_id = ? and report_bank_id =? and report_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(contentsinfo.getReportSubject());
		 sql.setString(contentsinfo.getReportContents());
		 sql.setString(contentsinfo.getRfileName());
		 sql.setString(contentsinfo.getSfileName());
		 sql.setString(contentsinfo.getFilePath());
		 sql.setString(contentsinfo.getFileSize());
		 sql.setString(contentsinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setLong(contentsinfo.getReportBankId());
		 sql.setInteger(contentsinfo.getReportId());

		 log.debug("[editReportBankContentsInfo]" + sql.toString());

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
	 * 문제 은행 항목별 문제 리스트
	 * @param systemcode
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public RowSet getReportBankContentsList(String systemCode, String courseId, long bankId) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select report_id, report_subject, ");
			sb.append(" rfile_name, sfile_name, file_path, file_size, reg_id, reg_date ");
			sb.append(" from report_bank_contents ");
			sb.append(" where system_code = ? and course_id = ? and report_bank_id = ? ");
			sb.append(" order by report_id desc");
			sql.setString(systemCode);
			sql.setString(courseId);
			sql.setLong(bankId);
			sql.setSql(sb.toString());

			log.debug("[getReportBankContentsList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return rs;
	}

	/**
	 * 문제은행 개별 문제 정보 가져오기
	 * @param systemcode
	 * @param courseid
	 * @param bankcode
	 * @param reportno
	 * @return
	 * @throws DAOException
	 */
	public ReportBankContentsDTO getReportBkContentsInfo(String systemCode, String courseId, long bankId, int reportId) throws DAOException{
		ReportBankContentsDTO contentsInfo = null;
		ResultSet rs = null;

	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select report_id, report_subject, report_contents, ");
		sb.append(" rfile_name, sfile_name, file_path, file_size, reg_id, reg_date ");
		sb.append(" from report_bank_contents ");
		sb.append(" where system_code= ?  and course_id= ? and report_bank_id = ? and report_id= ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(courseId);
		sql.setLong(bankId);
	    sql.setInteger(reportId);

		log.debug("[getReportBkContentsInfo]" + sql.toString());

		try{
			rs = broker.executeQuery(sql);

            contentsInfo = new ReportBankContentsDTO();
            if(rs.next()){
            	contentsInfo.setReportId(rs.getInt("report_id"));
            	contentsInfo.setReportSubject(StringUtil.nvl(rs.getString("report_subject")));
        		contentsInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
        		contentsInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
        		contentsInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
        		contentsInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
        		contentsInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
                contentsInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
                contentsInfo.setReportContents(StringUtil.nvl(rs.getString("report_contents")));
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
		return contentsInfo;
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
	public int delReportBankContentsFile(String systemCode, String courseId, long bankId, int reportId, String userId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update report_bank_contents set rfile_name='', sfile_name='', file_path='', file_size='', mod_id=?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where system_code=? and course_id=? and report_bank_id=? and report_id = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(userId);
		sql.setString(systemCode);
		sql.setString(courseId);
		sql.setLong(bankId);
		sql.setInteger(reportId);

		log.debug("[delReportBankContentsFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}
