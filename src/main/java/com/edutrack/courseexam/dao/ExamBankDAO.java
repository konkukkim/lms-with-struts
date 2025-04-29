/*
 * Created on 2004. 10. 21.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.dao;

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

import com.edutrack.common.dto.CodeDTO;
import com.edutrack.courseexam.dto.ExamBkContentsDTO;
import com.edutrack.courseexam.dto.ExamBkInfoDTO;
import com.edutrack.courseexam.dto.ExamContentsDTO;
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
public class ExamBankDAO  extends AbstractDAO {

	/**
	 *
	 */
	public ExamBankDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
/*
	public ArrayList getExamBankInfoCodeList(String systemcode, String courseid) throws DAOException{
		ArrayList codeList = new ArrayList();
		ExamContentsDTO contentsInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select exam_bk_code,exam_bk_name ");
			sb.append(" from exam_bk_info ");
			sb.append(" where  system_code = ? and course_id = ? ");
			sb.append(" order by exam_bk_code");
			sql.setSql(sb.toString());
			sql.setString(systemcode);
			sql.setString(courseid);

			log.debug("[getExamBankInfoCodeList]" + sql.toString());

			rs = broker.executeQuery(sql);

			CodeDTO codeDto = null;
            int count = 0;
			while(rs.next()){
				codeDto = new CodeDTO();
				codeDto.setCode(String.valueOf(rs.getInt("exam_bk_code")));
				codeDto.setName(StringUtil.nvl(rs.getString("exam_bk_name")));
				codeList.add(codeDto);
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

		 return codeList;
	}
*/

	/**
	 * 문제은행 항목들을 가져온다
	 */
	public RowSet getExamBankInfoCodeList(String systemcode, String courseid) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		RowSet rs = null;

		sb.append(" select exam_bk_code,exam_bk_name ");
		sb.append(" from exam_bk_info ");
		sb.append(" where  system_code = ? and course_id = ? ");
		sb.append(" order by exam_bk_code");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(courseid);
		log.debug("[getExamBankInfoCodeList]" + sql.toString());
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
	public ExamBkInfoDTO getExamBankInfo(String systemcode, String courseid, long bankcode)  throws DAOException{
		ExamBkInfoDTO bankInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select exam_bk_code,exam_bk_name ");
			sb.append(" from exam_bk_info ");
			sb.append(" where  system_code = ? and course_id = ? and exam_bk_code = ?");
			sql.setSql(sb.toString());
			sql.setString(systemcode);
			sql.setString(courseid);
			sql.setLong(bankcode);

			log.debug("[getExamBankInfo]" + sql.toString());

			rs = broker.executeQuery(sql);

			bankInfo = new ExamBkInfoDTO();
			if(rs.next()){
				bankInfo.setExamBkCode(rs.getInt("exam_bk_code"));
				bankInfo.setExamBkName(StringUtil.nvl(rs.getString("exam_bk_name")));

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
	public int addExamBankInfo(String systemcode,String userid, String courseid, String bankname)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_bk_info(system_code,course_id,exam_bk_code, exam_bk_name, reg_id, reg_date) ");
		 sb.append(" select ?,?, ifnull(max(exam_bk_code),0)+1 as exam_bk_code,?,?,getCurrentDate() ");
		 sb.append(" from exam_bk_info ");
		 sb.append(" where system_code = ? and course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(courseid);
		 sql.setString(bankname);
		 sql.setString(userid);
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 log.debug("[addExamBankInfo]" + sql.toString());

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
	public long addExamBankInfoReturnBankCode(String systemcode,String userid, String courseid, String bankname)  throws DAOException{
		 int retVal 			= 	0;
		 long retVal2			= 0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_bk_info(system_code,course_id,exam_bk_code, exam_bk_name, reg_id, reg_date) ");
		 sb.append(" select ?,?, ifnull(max(exam_bk_code),0)+1 as exam_bk_code,?,?,getCurrentDate() ");
		 sb.append(" from exam_bk_info ");
		 sb.append(" where system_code = ? and course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(courseid);
		 sql.setString(bankname);
		 sql.setString(userid);
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 log.debug("[addExamBankInfo]" + sql.toString());
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
				 sb2.append(" select max(exam_bk_code) as exam_bk_code ");
				 sb2.append(" from exam_bk_info ");
				 sb2.append(" where system_code = ? and course_id = ? ");
				 sql2.setSql(sb2.toString());
				 sql2.setString(systemcode);
				 sql2.setString(courseid);
				 rs 		= 	broker.executeQuery(sql2,conn);
				 rs.next();
				 retVal2 = rs.getLong("exam_bk_code");
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
	public int editExamBankInfo(String systemcode, String userid, String courseid,long bankcode, String bankname)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_bk_info ");
		 sb.append(" set exam_bk_name = ? ");
		 sb.append(" where  system_code = ? and course_id = ? and exam_bk_code = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(bankname);
		 sql.setString(systemcode);
		 sql.setString(courseid);
		 sql.setLong(bankcode);

		 log.debug("[addExamBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public boolean delExamBankInfo(String systemcode, String userid, String courseid,long bankcode)  throws DAOException{
		 boolean retVal 			= 	false;
		 QueryStatement[] sqls 	= 	new QueryStatement[2];
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from exam_bk_info ");
		 sb.append(" where  system_code = ? and course_id = ? ");
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 if(bankcode > 0) {
		 	sb.append(" and exam_bk_code = ?");
			 sql.setLong(bankcode);
		 }
		 sql.setSql(sb.toString());

		 sqls[0] = sql;

		 QueryStatement sql2 	= 	new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 sb2.append(" delete from exam_bk_contents ");
		 sb2.append(" where  system_code = ? and course_id = ? ");
		 sql2.setString(systemcode);
		 sql2.setString(courseid);

		 if(bankcode > 0){
		 	sb2.append(" and exam_bk_code = ? ");
		 	sql2.setLong(bankcode);
		 }
		 sql2.setSql(sb2.toString());

		 sqls[1] = sql2;

		 log.debug("[delExamBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public int delExamBankContents(String systemcode, String courseid,long bankcode, int examno)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from exam_bk_contents ");
		 sb.append(" where  system_code = ? and course_id = ? ");
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 if(bankcode > 0){
		 	sb.append(" and exam_bk_code = ? ");
		 	sql.setLong(bankcode);
		 }

		 if(examno > 0) {
		 	sb.append(" and exam_no = ?");
			sql.setInteger(examno);
		 }
		 sql.setSql(sb.toString());

		 log.debug("[delExamBankContents]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
	public int getExamBankContentsMaxExamNo(String systemCode, String courseId, long examBkCode) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select  ifnull(max(exam_no),0)+1 as max_no from exam_bk_contents ");
		 sb.append(" where system_code = ? and course_id = ? and exam_bk_code =? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(courseId);
		 sql.setLong(examBkCode);
		 log.debug("[addExamContentsInfo]" + sql.toString());
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
	public int addExamBankContentsInfo(String systemcode,ExamBkContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 int examOrder          =   0;
		 int exam_no = getExamBankContentsMaxExamNo(systemcode,contentsinfo.getCourseId(),contentsinfo.getExamBkCode());
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_bk_contents(system_code,course_id,exam_bk_code,exam_no,exam_type,contents,contents_text,example1,example2,example3,example4,example5, ");
		 sb.append(" answer,multianswer,exam_comment,rfile_name,sfile_name,file_path,file_size,reg_id,reg_date) ");
		 sb.append(" values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,getCurrentDate()) ");
//		 sb.append(" from exam_bk_contents ");
//		 sb.append(" where system_code = ? and course_id = ? and exam_bk_code =? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setLong(contentsinfo.getExamBkCode());
		 sql.setInteger(exam_no);
		 sql.setString(contentsinfo.getExamType());
		 sql.setString(contentsinfo.getContents());
		 sql.setString(contentsinfo.getContentsText());
		 sql.setString(contentsinfo.getExample1());
		 sql.setString(contentsinfo.getExample2());
		 sql.setString(contentsinfo.getExample3());
		 sql.setString(contentsinfo.getExample4());
		 sql.setString(contentsinfo.getExample5());
		 sql.setString(contentsinfo.getAnswer());
		 sql.setString(contentsinfo.getMultiAnswer());
		 sql.setString(contentsinfo.getComment());
		 sql.setString(contentsinfo.getRfileName());
		 sql.setString(contentsinfo.getSfileName());
		 sql.setString(contentsinfo.getFilePath());
		 sql.setString(contentsinfo.getFileSize());
		 sql.setString(contentsinfo.getRegId());

		 log.debug("[addExamContentsInfo]" + sql.toString());

		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	public int editExamBankContentsInfo(String systemcode, ExamBkContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_bk_contents ");
		 sb.append(" set  exam_type = ? ,contents = ?, contents_text = ?,example1 = ?,example2 = ?,example3 = ?,example4 = ?,example5 = ?, ");
		 sb.append(" answer = ?,multianswer = ?,exam_comment = ?,rfile_name = ?,sfile_name = ?,file_path = ?,file_size = ?,mod_id = ?, ");
		 sb.append(" mod_date= getCurrentDate() ");
		 sb.append(" where system_code = ? and course_id = ? and exam_bk_code =? and exam_no = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(contentsinfo.getExamType());
		 sql.setString(contentsinfo.getContents());
		 sql.setString(contentsinfo.getContentsText());
		 sql.setString(contentsinfo.getExample1());
		 sql.setString(contentsinfo.getExample2());
		 sql.setString(contentsinfo.getExample3());
		 sql.setString(contentsinfo.getExample4());
		 sql.setString(contentsinfo.getExample5());
		 sql.setString(contentsinfo.getAnswer());
		 sql.setString(contentsinfo.getMultiAnswer());
		 sql.setString(contentsinfo.getComment());
		 sql.setString(contentsinfo.getRfileName());
		 sql.setString(contentsinfo.getSfileName());
		 sql.setString(contentsinfo.getFilePath());
		 sql.setString(contentsinfo.getFileSize());
		 sql.setString(contentsinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setLong(contentsinfo.getExamBkCode());
		 sql.setInteger(contentsinfo.getExamNo());

		 log.debug("[editExamBankContentsInfo]" + sql.toString());

		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	public RowSet getExamBankContentsList(String systemcode, String courseid,long bankcode) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select exam_no,exam_type,contents_text,example1,example2,example3,example4,example5, ");
			sb.append(" answer,multianswer,exam_comment,rfile_name,sfile_name,file_path,file_size,reg_id,reg_date ");
			sb.append(" from exam_bk_contents ");
			sb.append(" where system_code = ? and course_id = ? and exam_bk_code = ? ");
			sb.append(" order by exam_no desc");
			sql.setString(systemcode);
			sql.setString(courseid);
			sql.setLong(bankcode);
			sql.setSql(sb.toString());

			log.debug("[getExamBankContentsList]" + sql.toString());

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
	 * @param examno
	 * @return
	 * @throws DAOException
	 */
	public ExamBkContentsDTO getExamBkContentsInfo(String systemcode, String courseid, long bankcode, int examno) throws DAOException{
		ExamBkContentsDTO contentsInfo = null;
		ResultSet rs = null;
		Connection conn = null;
	    DBConnectionManager pool = null;
	    StringBuffer output = new StringBuffer();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select exam_no,exam_type,contents,example1,example2,example3,example4,example5, ");
		sb.append(" answer,multianswer,exam_comment,rfile_name,sfile_name,file_path,file_size,reg_id,reg_date ");
		sb.append(" from exam_bk_contents ");
		sb.append(" where system_code= ?  and course_id= ? and exam_bk_code = ? and exam_no= ? ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(courseid);
		sql.setLong(bankcode);
	    sql.setInteger(examno);

		log.debug("[getExamBkContentsInfo]" + sql.toString());

		try{
			pool = DBConnectionManager.getInstance();
            conn = pool.getConnection();
			rs = broker.executeQuery(sql,conn);

            contentsInfo = new ExamBkContentsDTO();
            if(rs.next()){
            	contentsInfo.setExamNo(rs.getInt("exam_no"));
        		contentsInfo.setExamType(StringUtil.nvl(rs.getString("exam_type")));
        		//contentsInfo.setContents(StringUtil.nvl(rs.getString("contents")));
        		contentsInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
        		contentsInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
        		contentsInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
        		contentsInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
        		contentsInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
        		contentsInfo.setAnswer(StringUtil.nvl(rs.getString("answer")));
        		contentsInfo.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
        		contentsInfo.setComment(StringUtil.nvl(rs.getString("exam_comment")));
        		contentsInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
        		contentsInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
        		contentsInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
        		contentsInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
        		contentsInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
                contentsInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				Reader input = rs.getCharacterStream("contents");
		        char[] buffer = new char[1024];
		        int byteRead;
		        while((byteRead=input.read(buffer,0,1024))!=-1)
		        {
		            output.append(buffer,0,byteRead);
		        }
		        input.close();

		        contentsInfo.setContents(StringUtil.nvl(output.toString()));
		        output.delete(0,output.length());
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
		return contentsInfo;
	}

	/**
	 * 시험 첨부 파일을 삭제한다.
	 * @param systemCode
	 * @param courseId
	 * @param bankCode
	 * @param reportNo
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public int delExamBankContentsFile(String systemCode, String courseId, long bankCode, int reportNo, String userId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update exam_bk_contents set rfile_name='', sfile_name='', file_path='', file_size='', mod_id=?, mod_date=getCurrentDate()");
		sb.append(" where system_code=? and course_id=? and exam_bk_code=? and exam_no = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(userId);
		sql.setString(systemCode);
		sql.setString(courseId);
		sql.setLong(bankCode);
		sql.setInteger(reportNo);

		log.debug("[delExamBankContentsFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}
