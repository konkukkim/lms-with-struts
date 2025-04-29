/*
 * Created on 2004. 10. 12.
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

import com.edutrack.common.CommonUtil;
import com.edutrack.curribbs.dto.CurriBbsContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriBbsContentsDAO extends AbstractDAO {

	/**
	 * 게시판 seq_no 최고값+1 을 가져온다
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String BbsType) throws DAOException{
	 int  maxSeqNo = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(seq_no),0)+1 as max_seq_no ");
	  sb.append(" from curri_bbs_contents ");
	  sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(BbsType);

	 log.debug("[getMaxSeqNo]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	maxSeqNo = rs.getInt("max_seq_no");
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

	 return maxSeqNo;
	}

	/**
	 * 등록된 게시글 총 수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getCurriBbsContentsCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, String Where) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(seq_no),0) as cnt ");
		  sb.append(" from curri_bbs_contents ");
		  sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? ");
		  if(!Where.equals("")){
		 	sb.append(" and "+Where);
		 }

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);


		 log.debug("[getCurriBbsContentsCount]" + sql.toString());

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
	 * 게시글 총 수를 가져온다 (리스트에서 사용)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param searchKey
	 * @param keyWord
	 * @return
	 * @throws DAOException
	 */
	public int getCurriBbsContentsCount(String SystemCode,  String BbsType, String searchKey, String keyWord) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(seq_no),0) as cnt ");
		  sb.append(" from curri_bbs_contents ");
		  sb.append(" where system_code = ? and bbs_type = ?  ");
		  if(!keyWord.equals(""))
		  	sb.append("  and "+searchKey+" like ? ");

		  QueryStatement sql = new QueryStatement();
		  sql.setSql(sb.toString());
		  sql.setString(SystemCode);
		  sql.setString(BbsType);
		  if(!keyWord.equals(""))
			sql.setString("%"+keyWord+"%");

		 log.debug("[getCurriBbsContentsListCount]" + sql.toString());

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
	 * 게시판 정보를 등록한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int addCurriBbsContents(CurriBbsContentsDTO curriBbsContents) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" insert into curri_bbs_contents("+
	 			" system_code, curri_code, curri_year, curri_term, bbs_type, "+
	 			" seq_no, course_id, bbs_no, depth_no, order_no, " +
				" par_no, editor_type, contents_type, subject, keyword, "+
				" contents, rfile_name1, sfile_name1, file_path1, file_size1,"+
				" rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, "+
				" sfile_name3, file_path3, file_size3, hit_no, reg_id, "+
				" reg_name, reg_date) ");
	 sb.append(" values("+
	 			" ?, ?, ?, ?, ?, "+
	 			" ?, ?, ?, ?, ?, "+
				" ?, ?, ?, ?, ?, "+
				" ?, ?, ?, ?, ?, "+
				" ?, ?, ?, ?, ?, "+
				" ?, ?, ?, ?, ?, "+
				" ?, ? )");
	 sql.setSql(sb.toString());

	 sql.setString(curriBbsContents.getSystemCode());
	 sql.setString(curriBbsContents.getCurriCode());
	 sql.setInteger(curriBbsContents.getCurriYear());
	 sql.setInteger(curriBbsContents.getCurriTerm());
	 sql.setString(curriBbsContents.getBbsType());
	 sql.setInteger(curriBbsContents.getSeqNo());
	 sql.setString(curriBbsContents.getCourseId());
	 sql.setInteger(curriBbsContents.getBbsNo());
	 sql.setInteger(curriBbsContents.getDepthNo());
	 sql.setInteger(curriBbsContents.getOrderNo());
	 sql.setInteger(curriBbsContents.getParNo());
	 sql.setString(curriBbsContents.getEditorType());
	 sql.setString(curriBbsContents.getContentsType());
	 sql.setString(curriBbsContents.getSubject());
	 sql.setString(curriBbsContents.getKeyword());
	 sql.setString(curriBbsContents.getContents());
	 sql.setString(curriBbsContents.getRfileName1());
	 sql.setString(curriBbsContents.getSfileName1());
	 sql.setString(curriBbsContents.getFilePath1());
	 sql.setString(curriBbsContents.getFileSize1());
	 sql.setString(curriBbsContents.getRfileName2());
	 sql.setString(curriBbsContents.getSfileName2());
	 sql.setString(curriBbsContents.getFilePath2());
	 sql.setString(curriBbsContents.getFileSize2());
	 sql.setString(curriBbsContents.getRfileName3());
	 sql.setString(curriBbsContents.getSfileName3());
	 sql.setString(curriBbsContents.getFilePath3());
	 sql.setString(curriBbsContents.getFileSize3());
	 sql.setInteger(curriBbsContents.getHitNo());
	 sql.setString(curriBbsContents.getRegId());
	 sql.setString(curriBbsContents.getRegName());
	 sql.setString(CommonUtil.getCurrentDate());

	 log.debug("[addBbsContents]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		 log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

//	 Connection conn = null;
//	 DBConnectionManager pool = null;
//	 ResultSet rs = null;
//	 QueryStatement sql2 = new QueryStatement();
//	 StringBuffer sb2 = new StringBuffer();
//	 try{
//	 	pool = DBConnectionManager.getInstance();
//		conn = pool.getConnection();
//		conn.setAutoCommit( false );
//
//	    retVal = broker.executeUpdate(sql,conn);
//
//		 sb2.append(" select contents from curri_bbs_contents ");
//		 sb2.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ? FOR UPDATE");
//		 sql2.setSql(sb2.toString());
//		 sql2.setString(curriBbsContents.getSystemCode());
//		 sql2.setString(curriBbsContents.getCurriCode());
//		 sql2.setInteger(curriBbsContents.getCurriYear());
//		 sql2.setInteger(curriBbsContents.getCurriTerm());
//		 sql2.setString(curriBbsContents.getBbsType());
//		 sql2.setInteger(curriBbsContents.getSeqNo());
//		 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//		 rs = broker.executeQuery(sql2,conn);
//     	if(rs.next()){
////     		log.debug("______ rs "+rs.getClob(1));
//     		CLOB clob = (CLOB)rs.getClob("CONTENTS");
//     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//     		Writer writer = clob.getCharacterOutputStream();
//     		Reader src = new CharArrayReader(curriBbsContents.getContents().toCharArray());
//     		char[] buffer = new char[1024];
//     		int read = 0;
//     		while ( (read = src.read(buffer,0,1024)) != -1) {
//     			writer.write(buffer, 0, read);
//     		}
//     		//clob.close();
//     		src.close();
//     		writer.close();
//     	}
//		conn.commit();
//
//	 }catch(Exception e){
//	 	e.printStackTrace();
//		try {
//			if(conn != null) conn.rollback();
//		} catch(SQLException ignore) {
//			log.error(ignore.getMessage(), ignore);
//		}
//		throw new DAOException(e.getMessage());
//	 } finally {
//		try {
//			if(conn != null){
//				conn.setAutoCommit( true );
//				pool.freeConnection(conn); // 컨넥션 해제
//			}
//		} catch(Exception ignore) {
//			log.error(ignore.getMessage(),ignore);
//		}
//	}

	 return retVal;
	}
	/**
	 * 답글 등록시 순서 정렬을 위한 Update
	 * @param bbsContents
	 * @param actionMode	Ins, Del
	 * @return
	 * @throws DAOException
	 */
	public boolean replyUpdateCurriBbsContents(CurriBbsContentsDTO curriBbsContents, String actionMode) throws DAOException{
		boolean retVal = false;
		int iVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 if(actionMode.equals("Ins"))
		 	sb.append(" update curri_bbs_contents set order_no = order_no+1  ");
		 else
		 	sb.append(" update curri_bbs_contents set order_no = order_no-1  ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and bbs_no = ? and order_no >= ? ");
		 sql.setSql(sb.toString());

		 sql.setString(curriBbsContents.getSystemCode());
		 sql.setString(curriBbsContents.getCurriCode());
		 sql.setInteger(curriBbsContents.getCurriYear());
		 sql.setInteger(curriBbsContents.getCurriTerm());
		 sql.setString(curriBbsContents.getBbsType());
		 sql.setInteger(curriBbsContents.getBbsNo());
		 sql.setInteger(curriBbsContents.getOrderNo());

		 log.debug("[replyUpdateCurriBbsContents]" + sql.toString());
		 try{
			 iVal = broker.executeUpdate(sql);
			 retVal  = true;
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	/**
	 * 게시판 내용을 수정한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int editCurriBbsContents(CurriBbsContentsDTO curriBbsContents) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update curri_bbs_contents set course_id= ?, editor_type = ? , contents_type = ? , subject = ? , keyword = ? ,"+
		 	"contents = ? , rfile_name1 = ? , sfile_name1 = ? , file_path1 = ? , file_size1 = ? ,"+
	 		"rfile_name2 = ? , sfile_name2 = ? , file_path2 = ? , file_size2 = ? ,"+
			"rfile_name3 = ? , sfile_name3 = ? , file_path3 = ? , file_size3 = ? ,"+
			"reg_name = ? ,  mod_id = ? , mod_date = ?  ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ?");
		 sql.setSql(sb.toString());
		 sql.setString(curriBbsContents.getCourseId());
		 sql.setString(curriBbsContents.getEditorType());
		 sql.setString(curriBbsContents.getContentsType());
		 sql.setString(curriBbsContents.getSubject());
		 sql.setString(curriBbsContents.getKeyword());
		 sql.setString(curriBbsContents.getContents());
		 sql.setString(curriBbsContents.getRfileName1());
		 sql.setString(curriBbsContents.getSfileName1());
		 sql.setString(curriBbsContents.getFilePath1());
		 sql.setString(curriBbsContents.getFileSize1());
		 sql.setString(curriBbsContents.getRfileName2());
		 sql.setString(curriBbsContents.getSfileName2());
		 sql.setString(curriBbsContents.getFilePath2());
		 sql.setString(curriBbsContents.getFileSize2());
		 sql.setString(curriBbsContents.getRfileName3());
		 sql.setString(curriBbsContents.getSfileName3());
		 sql.setString(curriBbsContents.getFilePath3());
		 sql.setString(curriBbsContents.getFileSize3());
		 sql.setString(curriBbsContents.getRegName());
		 sql.setString(curriBbsContents.getModId());
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(curriBbsContents.getSystemCode());
		 sql.setString(curriBbsContents.getCurriCode());
		 sql.setInteger(curriBbsContents.getCurriYear());
		 sql.setInteger(curriBbsContents.getCurriTerm());
		 sql.setString(curriBbsContents.getBbsType());
		 sql.setInteger(curriBbsContents.getSeqNo());

		 log.debug("[editCurriBbsContents]" + sql.toString());
		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

//		 Connection conn = null;
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
//			 sb2.append(" select contents from curri_bbs_contents ");
//			 sb2.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(curriBbsContents.getSystemCode());
//			 sql2.setString(curriBbsContents.getCurriCode());
//			 sql2.setInteger(curriBbsContents.getCurriYear());
//			 sql2.setInteger(curriBbsContents.getCurriTerm());
//			 sql2.setString(curriBbsContents.getBbsType());
//			 sql2.setInteger(curriBbsContents.getSeqNo());
//			 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("CONTENTS");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(curriBbsContents.getContents().toCharArray());
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

		 return retVal;
		}
	/**
	 * 게시물 조회수 증가하기
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int hitUpCurriBbsContents(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update curri_bbs_contents set hit_no=hit_no+1");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ?");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);

		 log.debug("[hitUpCurriBbsContents]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	/**
	 * 게시물 정보 가져오기
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param SeqNo
	 * @return RowSet
	 * @throws DAOException
	 */
	public CurriBbsContentsDTO getCurriBbsContents(String Systemcode,  String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo) throws DAOException{
		 CurriBbsContentsDTO contentsDto = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select " +
		 					"system_code, curri_code, curri_year, curri_term" +
		 					", bbs_type, seq_no, course_id, bbs_no" +
		 					", depth_no, order_no, par_no, editor_type"+
		 					", contents_type, subject, keyword, contents" +
		 					", rfile_name1, sfile_name1, file_path1, file_size1"+
		 					", rfile_name2, sfile_name2, file_path2, file_size2" +
		 					", rfile_name3, sfile_name3, file_path3, file_size3"+
							", hit_no,  reg_id, reg_name,  reg_date ");
		 sb.append(" from curri_bbs_contents ");
		 sb.append(" where system_code = ? and bbs_type = ? and seq_no = ? ");
		 if(!CurriCode.equals(""))
		 	sb.append("  and curri_code = ? ");
		 if(CurriYear> 0)
		 	sb.append("  and curri_year = ? ");
		 if(CurriTerm > 0)
		 	sb.append("  and curri_term = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(Systemcode);
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);
		 if(!CurriCode.equals(""))
		 	sql.setString(CurriCode);
		 if(CurriYear> 0)
		 	 sql.setInteger(CurriYear);
		 if(CurriTerm > 0)
		 	sql.setInteger(CurriTerm);

		 log.debug("[getCurriBbsContents]" + sql.toString());
		 ResultSet rs = null;
//		 Connection conn = null;
//	     DBConnectionManager pool = null;
	     StringBuffer output = new StringBuffer();
		 try{

//		 	pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if(rs.next()){

				contentsDto = new CurriBbsContentsDTO();
				contentsDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				contentsDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				contentsDto.setCurriYear(rs.getInt("curri_year"));
				contentsDto.setCurriTerm(rs.getInt("curri_term"));
				contentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				contentsDto.setBbsType(StringUtil.nvl(rs.getString("bbs_type")));
				contentsDto.setSeqNo(rs.getInt("seq_no"));
				contentsDto.setBbsNo(rs.getInt("bbs_no"));
				contentsDto.setDepthNo(rs.getInt("depth_no"));
				contentsDto.setOrderNo(rs.getInt("order_no"));
				contentsDto.setParNo(rs.getInt("par_no"));
				contentsDto.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				contentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				contentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				contentsDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				contentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				contentsDto.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
				contentsDto.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
				contentsDto.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
				contentsDto.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
				contentsDto.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
				contentsDto.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
				contentsDto.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
				contentsDto.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
				contentsDto.setRfileName3(StringUtil.nvl(rs.getString("rfile_name3")));
				contentsDto.setSfileName3(StringUtil.nvl(rs.getString("sfile_name3")));
				contentsDto.setFilePath3(StringUtil.nvl(rs.getString("file_path3")));
				contentsDto.setFileSize3(StringUtil.nvl(rs.getString("file_size3")));
				contentsDto.setHitNo(rs.getInt("hit_no"));
				contentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				contentsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				contentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        contentsDto.setContents(StringUtil.nvl(output.toString()));
//		        output.delete(0,output.length());
				rs.close();
			}
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
		 return contentsDto;
	}
	/**
	 * 이전글 SeqNo 불러오기
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param BbsNo
	 * @return
	 * @throws DAOException
	 */
	public int getCurriBbsContentsPrevSeqNo(String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, int BbsNo) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(min(seq_no),0) as seq_no");
		 sb.append(" from curri_bbs_contents ");
		 sb.append(" where system_code = ? and bbs_type = ? and bbs_no > ? and depth_no = 0");
		 if(!CurriCode.equals(""))
		 	sb.append("  and curri_code = ? ");
		 if(CurriYear> 0)
		 	sb.append("  and curri_year = ? ");
		 if(CurriTerm > 0)
		 	sb.append("  and curri_term = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(Systemcode);
		 sql.setString(BbsType);
		 sql.setInteger(BbsNo);
		 if(!CurriCode.equals(""))
		 	sql.setString(CurriCode);
		 if(CurriYear> 0)
		 	 sql.setInteger(CurriYear);
		 if(CurriTerm > 0)
		 	sql.setInteger(CurriTerm);
		 log.debug("[getCurriBbsContentsPrevSeqNo]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	if(rs.next()){
		 		retVal = rs.getInt("seq_no");
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
	/**
	 * 다음글 SeqNo 불러오기
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param BbsNo
	 * @return
	 * @throws DAOException
	 */
	public int getCurriBbsContentsNextSeqNo(String Systemcode,  String CurriCode, int CurriYear, int CurriTerm, String BbsType, int BbsNo) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(seq_no),0) as seq_no");
		 sb.append(" from curri_bbs_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and bbs_no < ? and depth_no = 0");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);
		 sql.setInteger(BbsNo);
		 log.debug("[getCurriBbsContentsNextSeqNo]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	if(rs.next()){
		 		retVal = rs.getInt("seq_no");
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
	/**
	 * 이전글, 다음글 글제목 가져오기
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public String getCurriBbsContentsSubject(String Systemcode,  String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo) throws DAOException{
		 String retVal = "";
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select subject ");
		 sb.append(" from curri_bbs_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ? ");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);
		 log.debug("[getCurriBbsContentsSubject]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	if(rs.next()){
		 		retVal = StringUtil.nvl(rs.getString("subject"));
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 과정게시판 정보를 삭제시 해당게시물을 삭제한다.
	 * @param SystemCode
	 * @param BbsType
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delCurriBbsContents( String SystemCode,  String BbsType, int SeqNo ) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from curri_bbs_contents ");
	 sb.append(" where system_code = ? and bbs_type = ?");
	 if(SeqNo>0)
	 	sb.append(" and seq_no = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(BbsType);
	 if(SeqNo>0)
	 sql.setInteger(SeqNo);

	 log.debug("[delCurriBbsContents]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}
	/**
	 * 게시물을 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delCurriBbsContents(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from curri_bbs_contents ");
	 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ?");
	 if(SeqNo>0)
	 	sb.append(" and seq_no = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(BbsType);
	 sql.setInteger(SeqNo);

	 log.debug("[delCurriBbsContents]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}
	/**
	 * 첨부 파일 삭제시 정보에서 파일 이름 Null 로 update
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @param SeqNo
	 * @param FileNo
	 * @return
	 * @throws DAOException
	 */
	public int delCurriBbsContentsFile(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo, String FileNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update curri_bbs_contents set rfile_name"+FileNo+" = '',sfile_name"+FileNo+" = '',file_path"+FileNo+" = '',file_size"+FileNo+" = '' ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);

		 log.debug("[delCurriBbsContentsFile]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 게시글 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriBbsContentsList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, String addWhere, String order, int Top) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" SELECT ");

	 sb.append(" a.system_code, a.curri_code, a.curri_year, a.curri_term, a.bbs_type, a.seq_no, a.course_id");
	 sb.append(", a.bbs_no, a.depth_no, a.order_no, a.par_no, a.editor_type, a.contents_type, a.subject");
	 sb.append(", a.rfile_name1, a.sfile_name1, a.file_path1, a.file_size1,a.rfile_name2, a.sfile_name2, a.file_path2, a.file_size2");
	 sb.append(", a.rfile_name3, a.sfile_name3, a.file_path3, a.file_size3,a.hit_no, a.reg_id, a.reg_name, a.reg_date ");
	 sb.append(", IFNULL(count(b.comm_no),0) as comm_cnt ");
	 sb.append(" FROM curri_bbs_contents a LEFT OUTER JOIN curri_bbs_comment b ON ");
	 sb.append(" ( a.system_code=b.system_code and a.curri_code=b.curri_code and a.curri_year=b.curri_year " +
	 			" and a.curri_term = b.curri_term and a.bbs_type=b.bbs_type and a.seq_no=b.seq_no ) ");
	 sb.append(" WHERE a.system_code = ?  and a.bbs_type = ?");

	 if(!CurriCode.equals(""))
	 	sb.append("  and a.curri_code = ? ");
	 if(CurriYear> 0)
	 	sb.append("  and a.curri_year = ? ");
	 if(CurriTerm > 0)
	 	sb.append("  and a.curri_term = ? ");
	 if(!addWhere.equals(""))
	 	sb.append(" and "+addWhere);

	 sb.append(" group by a.system_code, a.curri_code, a.curri_year, a.curri_term, a.bbs_type, a.seq_no, a.course_id");
	 sb.append(", a.bbs_no, a.depth_no, a.order_no, a.par_no, a.editor_type, a.contents_type, a.subject");
	 sb.append(", a.rfile_name1, a.sfile_name1, a.file_path1, a.file_size1,a.rfile_name2, a.sfile_name2, a.file_path2, a.file_size2");
	 sb.append(", a.rfile_name3, a.sfile_name3, a.file_path3, a.file_size3,a.hit_no, a.reg_id, a.reg_name, a.reg_date ");

	 if(order.equals(""))
		sb.append(" order by a.bbs_no desc, a.order_no asc ");
	 else
		sb.append(" "+order);

	 if(Top >0)
	 	sb.append(" limit "+ Top);

	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 sql.setString(BbsType);

	 if(!CurriCode.equals(""))
	 	sql.setString(CurriCode);
	 if(CurriYear> 0)
	 	 sql.setInteger(CurriYear);
	 if(CurriTerm > 0)
	 	sql.setInteger(CurriTerm);

	 log.debug("[getCurriBbsContentsList]" + sql.toString());
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
	 * 게시판 정보 리스트를 페이징 처리하여 가져온다.
	 * @param curpage
	 * @param DispLine
	 * @param DispPage
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param BbsType
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCurriBbsContentsPagingList(int curpage,int DispLine, int DispPage, String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, String userType, String searchKey, String keyWord , String order) throws DAOException{
		ListDTO retVal = null;
		log.debug("[getCurriBbsContentsPagingList] start");
		try{
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append(	" system_code, curri_code, curri_year, curri_term" +
							", bbs_type, seq_no, bbs_no, depth_no" +
							", order_no, par_no, editor_type, contents_type" +
							", subject, rfile_name1,sfile_name1, file_path1" +
							", file_size1, rfile_name2, sfile_name2, file_path2" +
							", file_size2, rfile_name3, sfile_name3, file_path3" +
							", file_size3, hit_no, reg_id, reg_name" +
							", reg_date ");

			StringBuffer sb = new StringBuffer();
			sb.append(	" a.system_code, a.curri_code, a.curri_year, a.curri_term" +
						", a.bbs_type, a.seq_no, a.bbs_no, a.depth_no" +
						", a.order_no, a.par_no, a.editor_type, a.contents_type" +
						", a.subject, a.rfile_name1, a.sfile_name1, a.file_path1" +
						", a.file_size1, a.rfile_name2, a.sfile_name2, a.file_path2" +
						", a.file_size2, a.rfile_name3, a.sfile_name3, a.file_path3" +
						", a.file_size3, a.hit_no, a.reg_id, a.reg_name" +
						", a.reg_date ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol(" a.seq_no ");
			sql.setCutCol(" concat(a.bbs_type, cast(a.seq_no AS CHAR))");
			sql.setAlias(sbAlias.toString()+", comm_cnt ");
			sql.setSelect(sb.toString()+", IFNULL(COUNT(b.comm_no), 0) as comm_cnt ");

			sql.setFrom(" curri_bbs_contents a LEFT OUTER JOIN curri_bbs_comment b ON " +
			                " ( a.system_code=b.system_code and a.curri_code=b.curri_code " +
							" and a.curri_year=b.curri_year and a.curri_term = b.curri_term " +
							" and a.bbs_type=b.bbs_type and a.seq_no=b.seq_no ) ");

			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(	" a.system_code = ? AND a.curri_code = ? " +
							" AND a.curri_year = ? AND a.curri_term = ? " +
							" AND a.bbs_type = ? AND a.contents_type = 'S' ");

			sql.setString(Systemcode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(BbsType);
			String strWhere = ""; //공지타입은 위에서 따로 가져옴.

			if(!keyWord.equals("")){
				sbWhere.append(" and a."+searchKey+" like ? ");
				sql.setString("%"+keyWord+"%");
			}
			sql.setWhere(sbWhere.toString());

			if(order.equals(""))
				sql.setOrderby(" a.bbs_no desc, a.order_no asc");
			else
				sql.setOrderby(" "+order);
			sql.setGroupby(sb.toString());

            // 파라미터 셋팅
			log.debug("[getCurriBbsContentsPagingList]" + sql.toString());

			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}


	public static void main(String args[]) throws DAOException{
		CurriBbsContentsDAO ld = new CurriBbsContentsDAO();
	//    ld.getUserInfo("jang","jang");
	//    ld.checkUser("jang10");
	//    ld.userPagingList(3);

	//      UserInfo user = new UserInfo();
	//	  for(int i=0; i < 100; i++){
	//        user.setNumber(i+1);
	//	  	user.setUserId("jang"+i);
	//	    user.setUserPw("jang"+i);
	//
	//	    ld.addUser(user);
	//	  }
	//    ld.delUser("jang2");

	//    ArrayList a = ld.userList();
	//    ld.getCurriBbsContentsPagingList(1,10, 10, "1", 1, " subject like '%ss%' " , "");
	    int i = 0;
	}

	/**
	 * [2007.07.03] 게시물 정보 가져오기
	 *
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriBbsContentsInfo(String Systemcode,  String CurriCode
										, int CurriYear, int CurriTerm
										, String CourseId, String BbsType, int SeqNo) throws DAOException {
		QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT " +
		 					"system_code, curri_code, curri_year, curri_term" +
		 					", bbs_type, seq_no, course_id, bbs_no" +
		 					", depth_no, order_no, par_no, editor_type"+
		 					", contents_type, subject, keyword, contents" +
		 					", rfile_name1, sfile_name1, file_path1, file_size1"+
		 					", rfile_name2, sfile_name2, file_path2, file_size2" +
		 					", rfile_name3, sfile_name3, file_path3, file_size3"+
							", hit_no,  reg_id, reg_name,  reg_date ");
		 sb.append(" FROM curri_bbs_contents ");
		 sb.append(" WHERE system_code = ? ");
		 sql.setString(Systemcode);

		 if(!CurriCode.equals("")) {
			 sb.append("  AND curri_code = ? ");
			 sql.setString(CurriCode);
		 }
		 if(CurriYear> 0) {
			 sb.append("  AND curri_year = ? ");
			 sql.setInteger(CurriYear);
		 }
		 if(CurriTerm > 0) {
			 sb.append("  AND curri_term = ? ");
			 sql.setInteger(CurriTerm);
		 }
		 if(!CourseId.equals("")) {
			 sb.append("  AND course_id = ? ");
			 sql.setString(CourseId);
		 }
		 sb.append(" AND bbs_type = ? AND seq_no = ? ");
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);
		 sql.setSql(sb.toString());

		 log.debug("[getCurriBbsContentsInfo] : "+sql.toString());
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
	 * [2007.07.03] 특정컬럼의 데이타를 받아온다.
	 *
	 * @return
	 * @throws DAOException
	 */
	public String getCurriBbsContentsColumnData(String ColumnName, String Systemcode,  String CurriCode
												, int CurriYear, int CurriTerm, String CourseId
												, String BbsType, int SeqNo) throws DAOException {

		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();

		sb.append(" SELECT "+ColumnName+" FROM curri_bbs_contents ");
		sb.append(" WHERE system_code = ? ");
		sql.setString(Systemcode);

		if(!CurriCode.equals("")) {
			 sb.append("  AND curri_code = ? ");
			 sql.setString(CurriCode);
		 }
		 if(CurriYear> 0) {
			 sb.append("  AND curri_year = ? ");
			 sql.setInteger(CurriYear);
		 }
		 if(CurriTerm > 0) {
			 sb.append("  AND curri_term = ? ");
			 sql.setInteger(CurriTerm);
		 }
		 if(!CourseId.equals("")) {
			 sb.append("  AND course_id = ? ");
			 sql.setString(CourseId);
		 }
		 sb.append(" AND bbs_type = ? AND seq_no = ? ");
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);
		 sql.setSql(sb.toString());

		 log.debug("[getCurriBbsContentsColumnData]" + sql.toString());
		 String result = "";
		 RowSet rs = null;
		 try{
			rs = broker.executeQuery(sql);
			if(rs.next())
				result = rs.getString(ColumnName);
			rs.close();
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return result;

	}






}
