/*
 * Created on 2004. 10. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.dao;

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

import com.edutrack.common.CommonUtil;
import com.edutrack.courseforum.dto.CourseForumContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseForumContentsDAO  extends AbstractDAO {

	/**
	 * 토론방 seq_no 최고값+1 을 가져온다
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId,  int ForumId) throws DAOException{
	 int  maxSeqNo = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(seq_no),0)+1 as max_seq_no ");
	  sb.append(" from course_forum_contents ");
	  sb.append(" where system_code = ? and curri_code = ? " +
	  			"and curri_year = ? and curri_term = ? " +
	  			"and course_id = ? and forum_id = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(CourseId);
	 sql.setInteger(ForumId);

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
	 * @param CourseId
	 * @param ForumId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumContentsCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId,  int ForumId,  String Where ) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(seq_no),0) as cnt ");
		  sb.append(" from course_forum_contents ");
		  sb.append(" where system_code = ? and curri_code = ? " +
		  			"and curri_year = ? and curri_term = ? " +
		  			"and course_id = ? and forum_id = ? ");
		  if(!Where.equals("")){
		 	sb.append(Where);
		 }

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);

		 log.debug("[getCourseForumContentsCount]" + sql.toString());

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
	 * 공개강좌 토론방의 게시글 비밀번호를 확인한다.
	 *
	 * @return
	 * @throws DAOException
	 */
	public int publishForumContentsPassChk (String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo, String RegPasswd) throws DAOException {
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" SELECT IFNULL(COUNT(seq_no),0) as cnt ");
		  sb.append(" FROM course_forum_contents ");
		  sb.append(" WHERE system_code = ? AND curri_code = ? " +
		  			" AND curri_year = ? AND curri_term = ? " +
		  			" AND course_id = ? AND forum_id = ? " +
		  			" AND seq_no = ? AND reg_passwd = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);
		 sql.setString(RegPasswd);

		 log.debug("[publishForumContentsPassChk]" + sql.toString());

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
	 * 토론방 정보를 등록한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int addCourseForumContents(CourseForumContentsDTO courseForumContents) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into course_forum_contents("+
		 			" system_code, curri_code, curri_year, curri_term, course_id, "+
		 			" forum_id, seq_no, bbs_no, depth_no, level_no, " +
					" parent_no, editor_type, contents_type, subject, keyword, "+
					" contents, rfile_name, sfile_name, file_path, file_size,"+
					"  hit_no, reg_id, reg_name, reg_passwd" +
					", reg_date) ");
		 sb.append(" values("+
		 			" ?, ?, ?, ?, ?, "+
		 			" ?, ?, ?, ?, ?, "+
					" ?, ?, ?, ?, ?, "+
					" ?, ?, ?, ?, ?, "+
					" ?, ?, ?, ?, " +
					" ?)");
		 sql.setSql(sb.toString());

		 sql.setString(courseForumContents.getSystemCode());
		 sql.setString(courseForumContents.getCurriCode());
		 sql.setInteger(courseForumContents.getCurriYear());
		 sql.setInteger(courseForumContents.getCurriTerm());
		 sql.setString(courseForumContents.getCourseId());
		 sql.setInteger(courseForumContents.getForumId());
		 sql.setInteger(courseForumContents.getSeqNo());
		 sql.setInteger(courseForumContents.getBbsNo());
		 sql.setInteger(courseForumContents.getDepthNo());
		 sql.setInteger(courseForumContents.getLevelNo());
		 sql.setInteger(courseForumContents.getParentNo());
		 sql.setString(courseForumContents.getEditorType());
		 sql.setString(courseForumContents.getContentsType());
		 sql.setString(courseForumContents.getSubject());
		 sql.setString(courseForumContents.getKeyword());
		 sql.setString(courseForumContents.getContents());
		 sql.setString(courseForumContents.getRfileName());
		 sql.setString(courseForumContents.getSfileName());
		 sql.setString(courseForumContents.getFilePath());
		 sql.setString(courseForumContents.getFileSize());
		 sql.setInteger(courseForumContents.getHitNo());
		 sql.setString(courseForumContents.getRegId());
		 sql.setString(courseForumContents.getRegName());
		 sql.setString(courseForumContents.getRegPasswd());
		 sql.setString(CommonUtil.getCurrentDate());

		 log.debug("[addCourseForumContents]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 답글 등록시 순서 정렬을 위한 Update
	 * @param bbsContents
	 * @param actionMode	Ins, Del
	 * @return
	 * @throws DAOException
	 */
	public boolean replyUpdateCourseForumContents(CourseForumContentsDTO courseForumContents, String actionMode) throws DAOException{
		boolean retVal = false;
		int iVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 if(actionMode.equals("Ins"))
		 	sb.append(" update course_forum_contents set level_no = level_no+1  ");
		 else
		 	sb.append(" update course_forum_contents set level_no = level_no-1  ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and bbs_no = ? and level_no >= ? ");
		 sql.setSql(sb.toString());

		 sql.setString(courseForumContents.getSystemCode());
		 sql.setString(courseForumContents.getCurriCode());
		 sql.setInteger(courseForumContents.getCurriYear());
		 sql.setInteger(courseForumContents.getCurriTerm());
		 sql.setString(courseForumContents.getCourseId());
		 sql.setInteger(courseForumContents.getForumId());
		 sql.setInteger(courseForumContents.getBbsNo());
		 sql.setInteger(courseForumContents.getLevelNo());

		 log.debug("[replyUpdateCourseForumContents]" + sql.toString());
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
	 * 토론방 내용을 수정한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int editCourseForumContents(CourseForumContentsDTO courseForumContents) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_contents set ");
		 sb.append(" editor_type = ? , contents_type = ? " +
		 			", subject = ? , keyword = ? " +
		 			", contents = ?, rfile_name = ? ");
		 sb.append(" , sfile_name = ? , file_path = ? " +
		 			", file_size = ? ,  mod_id = ? , ");
		 sb.append(" mod_date = ?  ");
		 sb.append(" where system_code = ? and curri_code = ? " +
		 					"and curri_year = ? and curri_term = ?  " +
		 					"and course_id = ? and forum_id = ? " +
		 					"and seq_no = ?");
		 sql.setSql(sb.toString());

		 sql.setString(courseForumContents.getEditorType());
		 sql.setString(courseForumContents.getContentsType());
		 sql.setString(courseForumContents.getSubject());
		 sql.setString(courseForumContents.getKeyword());
		 sql.setString(courseForumContents.getContents());
		 sql.setString(courseForumContents.getRfileName());
		 sql.setString(courseForumContents.getSfileName());
		 sql.setString(courseForumContents.getFilePath());
		 sql.setString(courseForumContents.getFileSize());
		 sql.setString(courseForumContents.getModId());
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(courseForumContents.getSystemCode());
		 sql.setString(courseForumContents.getCurriCode());
		 sql.setInteger(courseForumContents.getCurriYear());
		 sql.setInteger(courseForumContents.getCurriTerm());
		 sql.setString(courseForumContents.getCourseId());
		 sql.setInteger(courseForumContents.getForumId());
		 sql.setInteger(courseForumContents.getSeqNo());

		 log.debug("[editCourseForumContents]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}
	/**
	 * 게시물 조회수 증가하기
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param ForumId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int hitUpCourseForumContents(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_contents set hit_no=hit_no+1");
		 sb.append(" where system_code = ? and curri_code = ? " +
		 				"and curri_year = ? and curri_term = ? " +
		 				"and course_id = ? and forum_id = ? " +
		 				"and seq_no = ?");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);

		 log.debug("[hitUpCourseForumContents]" + sql.toString());
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
	 * @param ForumId
	 * @param SeqNo
	 * @return CourseForumContentsDTO
	 * @throws DAOException
	 */
	public CourseForumContentsDTO getCourseForumContents(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(	" SELECT system_code, curri_code, curri_year, curri_term, course_id, ");
		 sb.append(	" forum_id, seq_no,  bbs_no, depth_no, level_no,");
		 sb.append(	" parent_no, editor_type, contents_type, subject, keyword, ");
		 sb.append(	" contents, rfile_name, sfile_name, file_path, file_size, ");
		 sb.append(	" hit_no,  reg_id, reg_name, reg_passwd" +
		 			", reg_date, mod_id, mod_date ");
		 sb.append(" FROM course_forum_contents ");
		 sb.append(" WHERE system_code = ? and curri_code = ? " +
		 				"and curri_year = ? and curri_term = ? " +
		 				"and course_id = ? and  forum_id = ? " +
		 				"and seq_no = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
	 	 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);

		 log.debug("[getCourseForumContents]" + sql.toString());

		 ResultSet rs = null;
		 CourseForumContentsDTO courseForumContents = null;
		 try{

			rs = broker.executeQuery(sql);
			 if(rs.next()){
				 courseForumContents = new CourseForumContentsDTO();

				 courseForumContents.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				 courseForumContents.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				 courseForumContents.setCurriYear(rs.getInt("curri_year"));
				 courseForumContents.setCurriTerm(rs.getInt("curri_term"));
				 courseForumContents.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				 courseForumContents.setForumId(rs.getInt("forum_id"));
				 courseForumContents.setSeqNo(rs.getInt("seq_no"));
				 courseForumContents.setBbsNo(rs.getInt("bbs_no"));
				 courseForumContents.setDepthNo(rs.getInt("depth_no"));
				 courseForumContents.setLevelNo(rs.getInt("level_no"));
				 courseForumContents.setParentNo(rs.getInt("parent_no"));
				 courseForumContents.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				 courseForumContents.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				 courseForumContents.setSubject(StringUtil.nvl(rs.getString("subject")));
				 courseForumContents.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				 courseForumContents.setContents(StringUtil.nvl(rs.getString("contents")));
				 courseForumContents.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				 courseForumContents.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				 courseForumContents.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				 courseForumContents.setFileSize(StringUtil.nvl(rs.getString("file_size")));
				 courseForumContents.setHitNo(rs.getInt("hit_no"));
				 courseForumContents.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				 courseForumContents.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				 
				 courseForumContents.setRegName(StringUtil.nvl(rs.getString("reg_name")));		//공개강좌의 토론방 등록자이름
				 courseForumContents.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));	//공개강좌의 토론방 등록 비밀번호
				 
				 courseForumContents.setModId(StringUtil.nvl(rs.getString("mod_id")));
				 courseForumContents.setModDate(StringUtil.nvl(rs.getString("mod_date")));
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
		 return courseForumContents;
	}
	/**
	 * 이전글 SeqNo 불러오기
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param ForumId
	 * @param BbsNo
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumContentsPrevSeqNo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId,  int ForumId, int BbsNo) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(min(seq_no),0) as seq_no");
		 sb.append(" from course_forum_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and bbs_no > ? and depth_no = 0");
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(BbsNo);

		 log.debug("[getCourseForumContentsPrevSeqNo]" + sql.toString());
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
	 * @param ForumId
	 * @param BbsNo
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumContentsNextSeqNo(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int BbsNo) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(seq_no),0) as seq_no");
		 sb.append(" from course_forum_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and bbs_no < ? and depth_no = 0");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(BbsNo);

		 log.debug("[getCourseForumContentsNextSeqNo]" + sql.toString());
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
	 * @param ForumId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public String getCourseForumContentsSubject(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo) throws DAOException{
		 String retVal = "";
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select subject ");
		 sb.append(" from course_forum_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and seq_no = ? ");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);

		 log.debug("[getCourseForumContentsSubject]" + sql.toString());
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
	 * 과정토론방 정보를 삭제시 해당게시물을 삭제한다.
	 * @param SystemCode
	 * @param ForumId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delCourseForumContents( String SystemCode, String CurriCode , int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo ) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 CourseForumCommentDAO	courseForumCommentDao	=	new CourseForumCommentDAO();
		 sb.append(" delete from course_forum_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ?");
		 if(SeqNo>0)
		 	sb.append(" and seq_no = ?");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 if(SeqNo>0)
			 sql.setInteger(SeqNo);

		 log.debug("[delCourseForumContents]" + sql.toString());
		 try{
		 	 courseForumCommentDao.delCourseForumComment(SystemCode, CurriCode, CurriYear, CurriTerm, CourseId, ForumId, SeqNo, 0);
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
	 * @param ForumId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delCourseForumContentsFile(String SystemCode,  String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_contents set rfile_name = '', sfile_name = '', file_path = '', file_size = '' ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and seq_no = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);

		 log.debug("[delCourseForumContentsFile]" + sql.toString());
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
	 * @param ForumId
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getCourseForumContentsList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, String addWhere, String order) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" SELECT " +
	 					"system_code, curri_code, curri_year, curri_term" +
	 					", course_id, forum_id, seq_no, bbs_no" +
	 					", depth_no, level_no, parent_no, editor_type"+
	 					", contents_type, subject, contents, keyword, rfile_name" +
	 					", sfile_name, file_path, file_size, hit_no"+
						", reg_id,  getDateText(reg_date ,null) as reg_date, mod_id, mod_date ");
	 sb.append(" FROM course_forum_contents ");
	 sb.append(" WHERE system_code = ? and curri_code = ? " +
	 					"and curri_year = ? and curri_term = ? " +
	 					"and course_id = ? and forum_id = ?");

	 if(!addWhere.equals(""))
	 	sb.append( addWhere );
	 if(order.equals(""))
		sb.append(" order by bbs_no desc, level_no asc ");
	 else
		sb.append(" "+order);

	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(CourseId);
	 sql.setInteger(ForumId);



	 log.debug("[getCourseForumContentsList]" + sql.toString());
	 RowSet rs = null;
	 ArrayList courseForumContentsList =  new ArrayList();
	 try{
		 rs = broker.executeQuery(sql);
		 CourseForumContentsDTO courseForumContents = null;

		 while(rs.next()){
		 	 courseForumContents = new CourseForumContentsDTO();

			 courseForumContents.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			 courseForumContents.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			 courseForumContents.setCurriYear(rs.getInt("curri_year"));
			 courseForumContents.setCurriTerm(rs.getInt("curri_term"));
			 courseForumContents.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 courseForumContents.setForumId(rs.getInt("forum_id"));
			 courseForumContents.setSeqNo(rs.getInt("seq_no"));
			 courseForumContents.setBbsNo(rs.getInt("bbs_no"));
			 courseForumContents.setDepthNo(rs.getInt("depth_no"));
			 courseForumContents.setLevelNo(rs.getInt("level_no"));
			 courseForumContents.setParentNo(rs.getInt("parent_no"));
			 courseForumContents.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			 courseForumContents.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
			 courseForumContents.setSubject(StringUtil.nvl(rs.getString("subject")));
			 courseForumContents.setKeyword(StringUtil.nvl(rs.getString("keyword")));
			 courseForumContents.setContents(StringUtil.nvl(rs.getString("contents")));
			 courseForumContents.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
			 courseForumContents.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
			 courseForumContents.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			 courseForumContents.setFileSize(StringUtil.nvl(rs.getString("file_size")));
			 courseForumContents.setHitNo(rs.getInt("hit_no"));
			 courseForumContents.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 courseForumContents.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 courseForumContents.setModId(StringUtil.nvl(rs.getString("mod_id")));
			 courseForumContents.setModDate(StringUtil.nvl(rs.getString("mod_date")));
		 	courseForumContentsList.add(courseForumContents);
		 }
		 rs.close();

	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }
	 log.debug(courseForumContentsList);
	 return courseForumContentsList;
	}

	/**
	 * 토론방 정보 리스트를 페이징 처리하여 가져온다.
	 * @param curpage
	 * @param Systemcode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCourseForumContentsPagingList(int curpage,int DispLine, int DispPage, String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, String UserType, String searchKey, String keyWord , String Where , String order) throws DAOException{
		ListDTO retVal = null;
		try{
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("seq_no");
			sql.setCutCol(	" CONCAT(CAST(forum_id AS CHAR), CAST(seq_no AS CHAR))");
			
			sql.setAlias(	" system_code, curri_code, curri_year, curri_term" +
							", course_id, forum_id, seq_no, bbs_no" +
							", depth_no, level_no, parent_no, editor_type,"+
							" contents_type, subject, contents, rfile_name" +
							", sfile_name, file_path, file_size, hit_no"+
							",  reg_id, reg_name, reg_passwd, reg_date" +
							", mod_id, mod_date, comm_cnt ");
			
			sql.setSelect(	" cfc.system_code, cfc.curri_code, cfc.curri_year, cfc.curri_term" +
							", cfc.course_id, cfc.forum_id, cfc.seq_no, cfc.bbs_no" +
							", cfc.depth_no, cfc.level_no, cfc.parent_no, cfc.editor_type,"+
							" cfc.contents_type, cfc.subject, cfc.contents, cfc.rfile_name" +
							", cfc.sfile_name, cfc.file_path, cfc.file_size, cfc.hit_no"+
							", cfc.reg_id, cfc.reg_name, cfc.reg_passwd, getDateText(cfc.reg_date,null) reg_date" +
							", cfc.mod_id, cfc.mod_date" +
							", (SELECT IFNULL(COUNT(comm_no), 0) FROM course_forum_comment " +
								" WHERE system_code = cfc.system_code AND curri_code = cfc.curri_code " +
								" AND curri_year = cfc.curri_year AND curri_term = cfc.curri_term " +
								" AND course_id = cfc.course_id AND seq_no = cfc.seq_no) AS comm_cnt ");
			
			sql.setFrom(	" course_forum_contents cfc ");
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(	" cfc.system_code = ? and cfc.curri_code = ? " +
							" and cfc.curri_year = ? and cfc.curri_term = ? " +
							" and cfc.course_id = ? and cfc.forum_id = ? ");
			sql.setString(Systemcode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(CourseId);
			sql.setInteger(ForumId);
			if(!keyWord.equals("")){
				sbWhere.append(" and "+searchKey+" like ? ");
				sql.setString("%"+keyWord+"%");
			}
			String strWhere = ""; //공지타입은 위에서 따로 가져옴.
			if(!Where.equals("")){
				sbWhere.append(Where);
			}

			sql.setWhere(sbWhere.toString());

			if(order.equals(""))
				sql.setOrderby(" bbs_no desc, level_no asc");
			else
				sql.setOrderby(" "+order);
//            sql.setGroupby(" u_id, u_pw ");

            // 파라미터 셋팅
			log.debug("[getCourseForumContentsPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);
			//retVal = broker.executeListQuery(sql, curpage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}

	/**
	 * 포럼 참여건수 (과목메인)
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getMainForumList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String UserId) throws DAOException{
	 ArrayList forumList =  new ArrayList();

 	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 
	 sb.append(	" SELECT ");
	 sb.append(	" cu.system_code, cu.curri_code, cu.curri_year, cu.curri_term" +
	 			", cu.course_id, cu.forum_id ,ci.subject, IFNULL(COUNT(cc.seq_no),0) AS forum_cnt" +
	 			", ci.start_date, ci.end_date  ");
	 sb.append(	" FROM course_forum_info  ci, course_forum_user cu ");
	 sb.append(	" LEFT OUTER JOIN course_forum_contents cc ");
	 sb.append(	" ON cu.curri_year=cc.curri_year AND cu.curri_term=cc.curri_term " +
	 			" AND cu.course_id=cc.course_id AND ");
	 sb.append(	" cu.forum_id = cc.forum_id AND cu.user_id = cc.reg_id  ");
	 sb.append(	" WHERE " +
	 			" ci.system_code=cu.system_code AND ci.curri_code=cu.curri_code " +
	 			" AND ci.curri_year=cu.curri_year AND ci.curri_term=cu.curri_term" +
	 			" AND ci.course_id=cu.course_id AND ci.forum_id = cu.forum_id" +
	 			" AND ci.system_code = ? AND ci.curri_code = ? " +
	 			" AND ci.curri_year = ? AND ci.curri_term = ? " +
	 			" AND cu.user_id=? AND ci.regist_yn='Y' " +
	 			" AND parent_forum_id = '0' ");
	 sb.append(	" GROUP BY cu.system_code, cu.curri_code, cu.curri_year, cu.curri_term" +
	 			", cu.course_id, cu.forum_id,ci.subject ");
	 
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(UserId);

	 log.debug("[getMainForumList]" + sql.toString());
	 
	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 CourseForumContentsDTO ForumContents = null;

		 while(rs.next()){
		 	ForumContents = new CourseForumContentsDTO();

		 	ForumContents.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
		 	ForumContents.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
		 	ForumContents.setCurriYear(rs.getInt("curri_year"));
		 	ForumContents.setCurriTerm(rs.getInt("curri_term"));
		 	ForumContents.setCourseId(StringUtil.nvl(rs.getString("course_id")));
		 	ForumContents.setForumId(rs.getInt("forum_id"));
		 	ForumContents.setSubject(rs.getString("subject"));
		 	ForumContents.setForumCnt(rs.getInt("forum_cnt"));
		 	ForumContents.setStartDate(StringUtil.nvl(rs.getString("start_date")));
		 	ForumContents.setEndDate(StringUtil.nvl(rs.getString("end_date")));
		 	
		 	forumList.add(ForumContents);
		 }
		 rs.close();

	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }
	 log.debug(forumList);
	 return forumList;
	}


	public static void main(String args[]) throws DAOException{
		CourseForumContentsDAO ld = new CourseForumContentsDAO();

	    int i = 0;
	}

}
