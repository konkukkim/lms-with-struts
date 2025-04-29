/*
 * Created on 2004. 10. 26.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curribbs.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.curribbs.dto.CurriWorkReqResDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author epitaph
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriWorkReqResDAO extends AbstractDAO{

    /**
     *
     */
    public CurriWorkReqResDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param systemCode
     * @param chkId
     * @param chkType
     * @return
     * @throws DAOException
     */

    public int getCount (String systemCode, String chkId, String chkType, String curriCode, int curriYear, int curriTerm, String sTarget, String sWord) throws DAOException {
        StringBuffer sb = new StringBuffer();
        String	whereSql	=	"";
        int		totCount	=	0;

        QueryStatement sql = new QueryStatement();

        sb.append("select count(seq_no) as cnt from work_request_response where system_code = ?");
        sql.setString(systemCode);
        if(chkType.equals("S")){
            sb.append(" and student_id = ?");
            sql.setString(chkId);
        }else if(chkType.equals("P")){
            sb.append(" and prof_id = ?");
            sql.setString(chkId);
        }

        if(!sTarget.equals("") && !sWord.equals("")) {
            sb.append(" and "+sTarget+" like ?");
            sql.setString("%"+sWord+"%");
        }

        if(!curriCode.equals("")){
            sb.append(" and curri_code = ?");
            sql.setString(curriCode);
        }
        if(curriYear != 0) {
            sb.append(" and curri_year = ?");
            sql.setInteger(curriYear);
        }
        if(curriTerm != 0) {
            sb.append(" and curri_term = ?");
            sql.setInteger(curriTerm);
        }
        sql.setSql(sb.toString());

        log.debug("[CurriWorkReqResCount]"+ sql.toString());

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
     *
     * @param curPage
     * @param systemCode
     * @param chkId
     * @param chkType
     * @return
     * @throws DAOException
     */

    public ListDTO getWorkReqResList(int curPage, String systemCode, String chkId, String chkType, String curriCode, int curriYear, int curriTerm ,String sTarget, String sWord) throws DAOException {

        ListDTO	retVal	=	null;
        StringBuffer sb = new StringBuffer();
        try{
        	ListStatement sql = new ListStatement();
	        sql.setTotalCol("seq_no");
	        sql.setCutCol("seq_no");
			sql.setAlias(	" seq_no, req_subject	, student_id, prof_id" +
							", rfile_name1, sfile_name1, file_path1, file_size1" +
							", html_use1, req_reg_date, req_mod_date, rfile_name2" +
							", sfile_name2,file_path2, file_size2, html_use2" +
							", res_reg_id, res_reg_date, res_mod_id, res_mod_date" +
							", work_status, point, curri_code, curri_year" +
							", curri_term ");

	        sql.setSelect(	" seq_no, req_subject	, student_id, prof_id" +
	        				", rfile_name1, sfile_name1, file_path1, file_size1" +
	        				", html_use1, req_reg_date, req_mod_date, rfile_name2" +
	        				", sfile_name2,file_path2, file_size2, html_use2" +
	        				", res_reg_id, res_reg_date, res_mod_id, res_mod_date" +
	        				", ifnull(work_status,'0') as work_status, point, curri_code, curri_year" +
	        				", curri_term ");

	        sql.setFrom("work_request_response");

	        sb.append("system_code = ?");
	        sql.setString(systemCode);

	        if(chkType.equals("S")){
	            sb.append(" and student_id = ?");
	            sql.setString(chkId);
	        }else if(chkType.equals("P")){
	            sb.append(" and prof_id = ?");
	            sql.setString(chkId);
	        }

	        if(!sTarget.equals("") && !sWord.equals("")) {
	            sb.append(" and "+sTarget+" like ?");
	            sql.setString("%"+sWord+"%");
	        }

	        if(!curriCode.equals("")){
	            sb.append(" and curri_code = ?");
	            sql.setString(curriCode);
	        }
	        if(curriYear != 0) {
	            sb.append(" and curri_year = ?");
	            sql.setInteger(curriYear);
	        }
	        if(curriTerm != 0) {
	            sb.append(" and curri_term = ?");
	            sql.setInteger(curriTerm);
	        }

	        sql.setWhere(sb.toString());
	        sql.setOrderby(" seq_no desc ");

	        log.debug("[getWorkReqResList] = " + sql.toString());
	        retVal = broker.executeListQuery(sql, curPage);

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
     *
     * @param systemCode
     * @param seqNo
     * @return
     * @throws DAOException
     */

    public CurriWorkReqResDTO getWorkReqResInfo (String systemCode, int seqNo) throws DAOException {

        log.debug("^^^^^^^^^^^ getWorkReqResInfo start ^^^^^^^^^^^^^^^^");

         CurriWorkReqResDTO curriworkreqresDto = null;
         ResultSet rs = null;

         StringBuffer output1 = new StringBuffer();
	     StringBuffer output2 = new StringBuffer();
	     StringBuffer sb = new StringBuffer();
         QueryStatement sql = new QueryStatement();
         sb.append("select req_subject ,req_contents, student_id, prof_id, rfile_name1, sfile_name1, file_path1,");
         sb.append("file_size1, html_use1, req_reg_date, req_mod_date,res_contents, rfile_name2, sfile_name2,");
			sb.append("file_path2, file_size2, html_use2, res_reg_id, res_reg_date, res_mod_id, res_mod_date, ");
			sb.append("work_status, point, curri_code, course_id, curri_year, curri_term ");
			sb.append("from work_request_response where system_code = ? ");
         sql.setString(systemCode);
         if(seqNo != 0){
             sb.append(" and seq_no = ?");
             sql.setInteger(seqNo);
         }
         sql.setSql(sb.toString());
         log.debug("[getWorkReqResInfo] = " + sql.toString());
        try{
			rs = broker.executeQuery(sql);
	        curriworkreqresDto = new CurriWorkReqResDTO();
	        if(rs.next()){
	            curriworkreqresDto.setReqSubject(StringUtil.nvl(rs.getString("req_subject")));
	            curriworkreqresDto.setReqContents(StringUtil.nvl(rs.getString("req_contents")));
	            curriworkreqresDto.setStudentId(StringUtil.nvl(rs.getString("student_id")));
	            curriworkreqresDto.setProfId(StringUtil.nvl(rs.getString("prof_id")));
	            curriworkreqresDto.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
	            curriworkreqresDto.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
	            curriworkreqresDto.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
	            curriworkreqresDto.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
	            curriworkreqresDto.setHtmlUse1(StringUtil.nvl(rs.getString("html_use1")));
	            curriworkreqresDto.setReqRegDate(StringUtil.nvl(rs.getString("req_reg_date")));
	            curriworkreqresDto.setReqModDate(StringUtil.nvl(rs.getString("req_mod_date")));
	            curriworkreqresDto.setResContents(StringUtil.nvl(rs.getString("res_contents")));
	            curriworkreqresDto.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
	            curriworkreqresDto.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
	            curriworkreqresDto.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
	            curriworkreqresDto.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
	            curriworkreqresDto.setHtmlUse2(StringUtil.nvl(rs.getString("html_use2")));
	            curriworkreqresDto.setResRegId(StringUtil.nvl(rs.getString("res_reg_id")));
	            curriworkreqresDto.setResRegDate(StringUtil.nvl(rs.getString("res_reg_date")));
	            curriworkreqresDto.setResModId(StringUtil.nvl(rs.getString("res_mod_id")));
	            curriworkreqresDto.setResModDate(StringUtil.nvl(rs.getString("res_mod_date")));
	            curriworkreqresDto.setWorkStatus(StringUtil.nvl(rs.getString("work_status")));
	            curriworkreqresDto.setPoint(rs.getInt("point"));
	            curriworkreqresDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
	            curriworkreqresDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
	            curriworkreqresDto.setCurriYear(rs.getInt("curri_year"));
	            curriworkreqresDto.setCurriTerm(rs.getInt("curri_term"));

		        rs.close();
	        }

        }catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
//			    if(conn != null){
//			        conn.setAutoCommit( true );
//			        pool.freeConnection(conn); // 컨넥션 해제
//			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}

		log.debug("^^^^^^^^^^^ getWorkReqResInfo end ^^^^^^^^^^^^^^^^");
		return curriworkreqresDto;
    }

    /**
     *
     * @param systemCode
     * @param seqNo
     * @return
     * @throws DAOException
     */
    public int workReqResDelete (String systemCode, int seqNo) throws DAOException{

        StringBuffer sb = new StringBuffer();
        int	retVal	=	0;
        try{
	        QueryStatement sql = new QueryStatement();
	        sb.append("delete from work_request_response where system_code = ? and seq_no = ?");
	        sql.setSql(sb.toString());
	        sql.setString(systemCode);
	        sql.setInteger(seqNo);
	        retVal = broker.executeUpdate(sql);
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
     *
     * @param systemCode
     * @return
     * @throws DAOException
     */
    public int	getMaxSeqNo(String systemCode) throws DAOException{
        StringBuffer sb  = new StringBuffer();
        RowSet	rs = null;
        int	retVal	=	1;
        try{
            QueryStatement sql = new QueryStatement();

            sb.append("select max(ifnull(seq_no, 0)) as maxSeqNo from work_request_response where system_code = ? ");
            sql.setSql(sb.toString());
            sql.setString(systemCode);

            log.debug("[getMaxSeqNo] = "+ sql.toString());
            rs = broker.executeQuery(sql);
            if(rs.next()){
                retVal = rs.getInt("maxSeqNo")+1;
            }
        }catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
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
		return retVal;
    }

    /**
     *
     * @param systemCode
     * @param curriworkreqresDto
     * @return
     * @throws DAOException
     */
    public int workReqResRegistInsert (String systemCode, CurriWorkReqResDTO curriworkreqresDto) throws DAOException{
        StringBuffer sb = new StringBuffer();
        int retVal = 0;
        int seqNo = getMaxSeqNo(systemCode);
       log.debug("workReqResRegistInsert  Db insert start................");

    	QueryStatement sql = new QueryStatement();
    	sb.append("insert into work_request_response " +
    						"(system_code, seq_no, req_subject, req_contents" +
    						", student_id, prof_id, rfile_name1, sfile_name1" +
    						", file_path1, file_size1, html_use1, req_reg_date" +
    						", curri_code, course_id, curri_year, curri_term" +
    						", res_contents,work_status )");
    	sb.append(" values ( ?, ?, ?, ?" +
    						", ?, ?, ?, ?" +
    						", ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)" +
    						", ?, ?, ?, ?" +
    						",' ','0' )");
    	sql.setSql(sb.toString());

    	sql.setString(systemCode);
    	sql.setInteger(seqNo);
    	sql.setString(curriworkreqresDto.getReqSubject());
    	sql.setString(curriworkreqresDto.getReqContents());
    	sql.setString(curriworkreqresDto.getStudentId());
    	sql.setString(curriworkreqresDto.getProfId());
    	sql.setString(curriworkreqresDto.getRfileName1());
    	sql.setString(curriworkreqresDto.getSfileName1());
    	sql.setString(curriworkreqresDto.getFilePath1());
    	sql.setString(curriworkreqresDto.getFileSize1());
    	sql.setString(curriworkreqresDto.getHtmlUse1());
    	sql.setString(curriworkreqresDto.getCurriCode());
    	sql.setString(curriworkreqresDto.getCourseId());
    	sql.setInteger(curriworkreqresDto.getCurriYear());
    	sql.setInteger(curriworkreqresDto.getCurriTerm());

    	log.debug("[workReqResRegistInsertSQL] = "+sql.toString());
    	try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

//    	Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select req_contents from work_request_response ");
//			 sb2.append(" where system_code = ? and seq_no = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(systemCode);
//		     sql2.setInteger(seqNo);
//			 log.debug("[UseAdd_workReqResRegistUpdate]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("req_contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("req_contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(curriworkreqresDto.getReqContents().toCharArray());
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		log.debug("workReqResRegistInsert  Db insert end................");
		return retVal;


    }

    /**
     *
     * @param systemCode
     * @param curriworkreqresDto
     * @param noRfile
     * @return
     * @throws DAOException
     */
    public int workReqResRegistUpdate (String systemCode, CurriWorkReqResDTO curriworkreqresDto, boolean noRfile) throws DAOException{

        log.debug("-------------workReqResRegistUpdate start---------");
        StringBuffer sb = new StringBuffer();
        int retVal = 0;
        QueryStatement sql = new QueryStatement();
        sb.append("update work_request_response set " +
        				"req_subject = ?, req_contents = ?" +
        				", html_use1 = ?, req_mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");

        sql.setString(curriworkreqresDto.getReqSubject());
        sql.setString(curriworkreqresDto.getReqContents());
        sql.setString(curriworkreqresDto.getHtmlUse1());
        if(!noRfile){
            sb.append(	", rfile_name1 = ?, sfile_name1 = ?" +
            			", file_path1 = ?, file_size1 = ? ");
            sql.setString(curriworkreqresDto.getRfileName1());
        	sql.setString(curriworkreqresDto.getSfileName1());
        	sql.setString(curriworkreqresDto.getFilePath1());
        	sql.setString(curriworkreqresDto.getFileSize1());
        }
        sb.append(" where system_code = ? and seq_no = ?");
        sql.setString(systemCode);
        sql.setInteger(curriworkreqresDto.getSeqNo());
        sql.setSql(sb.toString());


        log.debug("++workReqResRegistUpdate++"+sql.toString());
        try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }


//        Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select req_contents from work_request_response ");
//			 sb2.append(" where system_code = ? and seq_no = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(systemCode);
//		     sql2.setInteger(curriworkreqresDto.getSeqNo());
//			 log.debug("[UseAdd_workReqResRegistUpdate]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("req_contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("req_contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(curriworkreqresDto.getReqContents().toCharArray());
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		log.debug("-------------workReqResRegistUpdate end---------");
		return retVal;

    }

    /**
     *
     * @param systemCode
     * @param curriworkreqresDto
     * @param methodType
     * @param noRfile
     * @return
     * @throws DAOException
     */

    public int workReqResReplyUpdate (String systemCode, CurriWorkReqResDTO curriworkreqresDto, String methodType, boolean noRfile) throws DAOException{
        log.debug("-------------workReqResReplyUpdate start---------");
        StringBuffer sb = new StringBuffer();
        int retVal = 0;
        QueryStatement sql = new QueryStatement();

        if(methodType.equals("Insert"))
        	sb.append("update work_request_response set " +
        					"res_contents = ?, html_use2 = ?" +
        					", res_reg_id = ?, res_reg_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)" +
        					", work_status = ? ");
        else
            sb.append("update work_request_response set " +
            				"res_contents = ?, html_use2 = ?" +
            				", res_mod_id = ?, res_mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)" +
            				", work_status = ? ");

        sql.setString(curriworkreqresDto.getResContents());
        sql.setString(curriworkreqresDto.getHtmlUse2());
        if(methodType.equals("Insert"))
        	sql.setString(curriworkreqresDto.getResRegId());
        else
            sql.setString(curriworkreqresDto.getResModId());

        sql.setString("1");
        if(!(methodType.equals("Update") && noRfile)){
            sb.append(	", rfile_name2 = ?, sfile_name2 = ?" +
            			", file_path2 = ?, file_size2 = ? ");
            sql.setString(curriworkreqresDto.getRfileName2());
        	sql.setString(curriworkreqresDto.getSfileName2());
        	sql.setString(curriworkreqresDto.getFilePath2());
        	sql.setString(curriworkreqresDto.getFileSize2());
        }
        sb.append(" where system_code = ? and seq_no = ?");
        sql.setString(systemCode);
        sql.setInteger(curriworkreqresDto.getSeqNo());
        sql.setSql(sb.toString());


        log.debug("++workReqResReplyUpdate+"+sql.toString());
        try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

//        Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select res_contents from work_request_response ");
//			 sb2.append(" where system_code = ? and seq_no = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(systemCode);
//		     sql2.setInteger(curriworkreqresDto.getSeqNo());
//			 log.debug("[UseAdd_workReqResRegistUpdate]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("res_contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("res_contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(curriworkreqresDto.getResContents().toCharArray());
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		log.debug("-------------workReqResReplyUpdate end---------");
		return retVal;

    }

    /**
     *
     * @param systemCode
     * @param seqNo
     * @param point
     * @return
     * @throws DAOException
     */
    public int insertPoint(String systemCode, int seqNo, int point) throws DAOException{
        StringBuffer sb = new StringBuffer();
        int	retVal	=	0;
        try{
            sb.append(	" update work_request_response set point = ? " +
            			" where system_code = ? and seq_no = ?");
            QueryStatement sql = new QueryStatement();
            sql.setSql(sb.toString());
            sql.setInteger(point);
            sql.setString(systemCode);
            sql.setInteger(seqNo);

            retVal = broker.executeUpdate(sql);
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
     *
     * @param curPage
     * @param systemCode
     * @return
     * @throws DAOException
     */
    public ListDTO getWorkReqResStatList(int curPage, String systemCode, String sTarget, String sWord) throws DAOException{

        log.debug("********* getWorkReqResStatList  start ******");
        StringBuffer sb = new StringBuffer();
        ListDTO retVal = null;
        try{
            ListStatement sql = new ListStatement();
            sql.setTotalCol("a.prof_id");
            sql.setCutCol(	"a.prof_id");
			sql.setAlias(	"user_name, prof_id, noCnt , yesCnt, totCnt, pointSum");
            sql.setSelect(	"b.user_name, a.prof_id" +
            				", count(case when a.work_status = '0' then 1  end) as noCnt " +
            				", count(case when a.work_status = '1' then 1  end) as yesCnt" +
            				", count(a.seq_no) as totCnt" +
            				", sum(a.point) as pointSum");
            sql.setFrom(	"work_request_response a, users b");
            sb.append(		"a.system_code = ? and a.system_code = b.system_code " +
            				"and a.prof_id = b.user_id");
            sql.setString(systemCode);
            if(!sTarget.equals("") && !sWord.equals("")){
                sb.append(" and "+sTarget+" like ?");
                sql.setString("%"+sWord+"%");
            }
            sql.setWhere(sb.toString());
            sql.setGroupby("a.prof_id, b.user_name");
            sql.setOrderby("sum(a.point) desc");

            log.debug("[getWorkReqResStatList]= " + sql.toString());

            retVal = broker.executeListQuery(sql, curPage);

        }catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		log.debug("********* getWorkReqResStatList  end ******");

		return retVal;

    }

    /**
     *
     * @param systemCode
     * @return
     * @throws DAOException
     */
    public int getWorkReqResStatCount(String systemCode, String sTarget, String sWord) throws DAOException {

        log.debug("********* getWorkReqResStatCount  start ******");

        int	retVal = 0;
        RowSet rs = null;
        StringBuffer sb = new StringBuffer();
        try{
            QueryStatement sql = new QueryStatement();
            sb.append(	" select  count(distinct a.prof_id) as cnt " +
            			" from work_request_response a, users b " +
            			" where a.system_code = ? and a.system_code = b.system_code " +
            			"and a.prof_id = b.user_id ");
            //sb.append("select count(distinct prof_id) as cnt from work_request_response where system_code = ?");
            sql.setString(systemCode);
            if(!sTarget.equals("") && !sWord.equals("")){
                sb.append(" and "+sTarget+" like ?");
                sql.setString("%"+sWord+"%");
            }
            sql.setSql(sb.toString());

            log.debug("[getWorkReqResStatCount]= " + sql.toString());

            rs = broker.executeQuery(sql);
            if(rs.next()){
                retVal = rs.getInt("cnt");
            }

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
		 log.debug("********* getWorkReqResStatCount  end ******");
		 return retVal;
    }

}
