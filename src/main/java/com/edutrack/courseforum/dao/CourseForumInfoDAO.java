/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.courseforum.dto.CourseForumTeamDTO;
import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
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
public class CourseForumInfoDAO  extends AbstractDAO {
	/**
	 * 토론방 forum_id 최고값+1 을 가져온다
	 * 
	 * @return
	 * @throws DAOException
	 */
	public int getMaxForumId(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException{
		 int  maxForumId = 0;
	
		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(max(forum_id),0)+1 as max_forum_id ");
		  sb.append(" from course_forum_info ");
		  sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
	
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
	
		 log.debug("[getMaxForumId]" + sql.toString());
	
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	maxForumId = rs.getInt("max_forum_id");
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

	 	return maxForumId;
	}

	
	/**
	 * 토론방의 아이디를 받아온다.
	 *
	 * @return
	 * @throws DAOException
	 */
	public int getForumId(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException{
		 int  forumId = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select IFNULL(MAX(forum_id),0) as forum_id ");
		  sb.append(" from course_forum_info ");
		  sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getMaxForumId]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 forumId = rs.getInt("forum_id");
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

		 return forumId;
	}


	/**
	 * 등록된 포럼 수를 가져온다.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumInfoCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId , String Where) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(forum_id),0) as cnt ");
		 sb.append(" from course_forum_info ");
		 sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ?");
		 if(!CourseId.equals(""))
		 	sb.append(" and course_id = ? ");
		 if(!Where.equals(""))
		 	sb.append(Where);
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 if(!CourseId.equals(""))
		 	sql.setString(CourseId);

		 log.debug("[getCourseForumInfoCount]" + sql.toString());

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
	 * 등록된 포럼 수를 가져온다.(학생용)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumInfoJoinCFUserCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String UserId , String Where) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(ci.forum_id),0) as cnt ");
		 sb.append(" from course_forum_info ci , course_forum_user cu ");
		 sb.append(" where  ci.system_code=cu.system_code and ci.curri_code=cu.curri_code and ci.curri_year=cu.curri_year ");
		 sb.append(" and ci.curri_term=cu.curri_term and ci.course_id=cu.course_id and ci.forum_id = cu.forum_id ");
		 sb.append(" and ci.system_code = ?  and ci.curri_code = ? and ci.curri_year = ? and ci.curri_term = ? and ci.regist_yn='Y' and cu.user_id = ?");
		 if(!Where.equals(""))
		 	sb.append(Where);
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(UserId);
		 log.debug("[getCourseForumInfoCount]" + sql.toString());

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
	 * @param courseForumInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCourseForumInfo(CourseForumInfoDTO courseForumInfo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" INSERT INTO course_forum_info( ");
	 sb.append(" system_code,  curri_code, curri_year, curri_term, course_id, ");
	 sb.append(" forum_id, forum_type, parent_forum_id, sub_team_no, sub_team_name, subject, ");
	 sb.append(" contents, rfile_name, sfile_name, file_path, file_size, ");
	 sb.append(" forum_score, start_date, end_date, open_yn, regist_yn, ");
	 sb.append(" editor_yn, voice_yn, comment_use_yn, emoticon_use_yn, view_thread_yn, ");
	 sb.append(" view_prev_next_yn, reg_id, reg_date ) ");
	 
	 sb.append(" values( ?, ? ,?, ?, ?, ");
	 sb.append(" ?, ?, ?, ?, ?, ?, ");
	 sb.append(" ?, ?, ?, ?, ?, ");
	 sb.append(" ?, ?, ?, ?, ?, ");
	 sb.append(" ?, ?, ?, ?, ?, ");
	 sb.append(" ?, ?, ? )");
	 
	 
	 sql.setSql(sb.toString());

	 sql.setString(courseForumInfo.getSystemCode());
	 sql.setString(courseForumInfo.getCurriCode());
	 sql.setInteger(courseForumInfo.getCurriYear());
	 sql.setInteger(courseForumInfo.getCurriTerm() );
	 sql.setString(courseForumInfo.getCourseId() );
	 sql.setInteger(courseForumInfo.getForumId() );
	 sql.setString(courseForumInfo.getForumType() );
	 sql.setInteger(courseForumInfo.getParentForumId() );
	 sql.setInteger(courseForumInfo.getSubTeamNo() );
	 sql.setString(courseForumInfo.getSubTeamName() );
	 sql.setString(courseForumInfo.getSubject() );
	 sql.setString(courseForumInfo.getContents() );
	 sql.setString(courseForumInfo.getRfileName() );
	 sql.setString(courseForumInfo.getSfileName());
	 sql.setString(courseForumInfo.getFilePath() );
	 sql.setString(courseForumInfo.getFileSize() );
	 sql.setInteger(courseForumInfo.getForumScore() );
	 sql.setString(courseForumInfo.getStartDate());
	 sql.setString(courseForumInfo.getEndDate() );
	 sql.setString(courseForumInfo.getOpenYn() );
	 sql.setString(courseForumInfo.getRegistYn() );
	 sql.setString(courseForumInfo.getEditorYn() );
	 sql.setString(courseForumInfo.getVoiceYn() );
	 sql.setString(courseForumInfo.getCommentUseYn() );
	 sql.setString(courseForumInfo.getEmoticonUseYn() );
	 sql.setString(courseForumInfo.getViewThreadYn() );
	 sql.setString(courseForumInfo.getViewPrevNextYn() );
	 sql.setString(courseForumInfo.getRegId() );
	 sql.setString(CommonUtil.getCurrentDate());

	 log.debug("[addCourseForumInfo]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	

	/**
	 * 토론 정보를 수정한다.
	 * @param courseForumInfo
	 * @param joGubun 이 널일 경우는 조만 수정하는 경우임, 그 외는 "1" 로 보냄
	 * @return
	 * @throws DAOException
	 */
	public int editCourseForumInfo(CourseForumInfoDTO courseForumInfo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_info set ");
		 sb.append(" forum_type = ? ,  parent_forum_id = ? , sub_team_no = ? , subject = ? , contents = ? , ");
		 sb.append(" rfile_name = ? , sfile_name = ? , file_path = ? , file_size = ? , forum_score = ? , ");
		 sb.append(" start_date = ? , end_date = ? , open_yn = ? , regist_yn = ? , editor_yn = ?, ");
		 sb.append(" voice_yn = ? , comment_use_yn = ? , emoticon_use_yn = ? , view_thread_yn = ? ,  view_prev_next_yn = ?, ");
		 sb.append(" mod_id = ? , mod_date = ? ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ?");
		 sql.setSql(sb.toString());

		 sql.setString(courseForumInfo.getForumType());
		 sql.setInteger(courseForumInfo.getParentForumId() );
		 sql.setInteger(courseForumInfo.getSubTeamNo() );
		 sql.setString(courseForumInfo.getSubject() );
		 sql.setString(courseForumInfo.getContents() );
		 sql.setString(courseForumInfo.getRfileName() );
		 sql.setString(courseForumInfo.getSfileName() );
		 sql.setString(courseForumInfo.getFilePath() );
		 sql.setString(courseForumInfo.getFileSize() );
		 sql.setInteger(courseForumInfo.getForumScore() );
		 sql.setString(courseForumInfo.getStartDate() );
		 sql.setString(courseForumInfo.getEndDate() );
		 sql.setString(courseForumInfo.getOpenYn() );
		 sql.setString(courseForumInfo.getRegistYn() );
		 sql.setString(courseForumInfo.getEditorYn() );
		 sql.setString(courseForumInfo.getVoiceYn() );
		 sql.setString(courseForumInfo.getCommentUseYn() );
		 sql.setString(courseForumInfo.getEmoticonUseYn() );
		 sql.setString(courseForumInfo.getViewThreadYn() );
		 sql.setString(courseForumInfo.getViewPrevNextYn() );
		 sql.setString(courseForumInfo.getModId() );
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(courseForumInfo.getSystemCode());
		 sql.setString(courseForumInfo.getCurriCode());
		 sql.setInteger(courseForumInfo.getCurriYear());
		 sql.setInteger(courseForumInfo.getCurriTerm());
		 sql.setString(courseForumInfo.getCourseId());
		 sql.setInteger(courseForumInfo.getForumId());


		 log.debug("[editCourseForumInfo]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
			 
			 //관련 하위토론방 정보도 수정한다.
			 editCourseForumInfoWithParentForumId(courseForumInfo,  courseForumInfo.getForumId());
			 
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}


	/**
	 * 상위토론정보 변경시 하위토론정보도 수정한다.
	 * @param courseForumInfo
	 * @param ParentForumId
	 * @return
	 * @throws DAOException
	 */
	public int editCourseForumInfoWithParentForumId(CourseForumInfoDTO courseForumInfo, int ParentForumId ) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update course_forum_info set ");
		 sb.append(" forum_type = ? ,  forum_score = ? , regist_yn = ? , open_yn = ? , start_date = ? , ");
		 sb.append(" end_date = ?  , editor_yn = ?,  voice_yn = ? , comment_use_yn = ? , emoticon_use_yn = ? ,");
		 sb.append(" view_thread_yn = ? ,  view_prev_next_yn = ?, mod_id = ? , mod_date = ?  ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and parent_forum_id = ?");
		 sql.setSql(sb.toString());

		 sql.setString(courseForumInfo.getForumType());
		 sql.setInteger(courseForumInfo.getForumScore() );
		 sql.setString(courseForumInfo.getRegistYn() );
		 sql.setString(courseForumInfo.getOpenYn() );
		 sql.setString(courseForumInfo.getStartDate() );
		 sql.setString(courseForumInfo.getEndDate() );
		 sql.setString(courseForumInfo.getEditorYn());
		 sql.setString(courseForumInfo.getVoiceYn() );
		 sql.setString(courseForumInfo.getCommentUseYn() );
		 sql.setString(courseForumInfo.getEmoticonUseYn() );
		 sql.setString(courseForumInfo.getViewThreadYn() );
		 sql.setString(courseForumInfo.getViewPrevNextYn() );
		 sql.setString(courseForumInfo.getModId() );
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(courseForumInfo.getSystemCode());
		 sql.setString(courseForumInfo.getCurriCode());
		 sql.setInteger(courseForumInfo.getCurriYear());
		 sql.setInteger(courseForumInfo.getCurriTerm());
		 sql.setString(courseForumInfo.getCourseId());
		 sql.setInteger(ParentForumId);


		 log.debug("[editCourseForumInfoWithParentForumId]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 토론 정보 가져오기
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @return CourseForumInfoDTO
	 * @throws DAOException
	 */
	public CourseForumInfoDTO getCourseForumInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId ) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT a.system_code,  a.curri_code, a.curri_year, a.curri_term, a.course_id, ");
		 sb.append(" a.forum_id, a.forum_type, a.parent_forum_id, a.sub_team_no, a.sub_team_name, a.subject, ");
		 sb.append(" a.contents, a.rfile_name, a.sfile_name, a.file_path, a.file_size, ");
		 sb.append(" a.forum_score, a.start_date, a.end_date, a.open_yn, a.regist_yn, ");
		 sb.append(" a.editor_yn, a.voice_yn, a.comment_use_yn, a.emoticon_use_yn, a.view_thread_yn, ");
		 sb.append(" a.view_prev_next_yn, a.reg_id, a.reg_date, a.mod_id, a.mod_date ");
		 sb.append(", (SELECT curri_name FROM curri_sub " +
		 				" WHERE system_code = a.system_code AND curri_code = a.curri_code" +
		 				" AND curri_year = a.curri_year AND curri_term = a.curri_term) AS curri_name ");
		 sb.append(" FROM course_forum_info a");
		 sb.append(" WHERE a.system_code = ? and a.curri_code = ? " +
		 				" and a.curri_year = ? and a.curri_term =? " +
		 				" and a.course_id = ? and a.forum_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 
		 log.debug("[getCourseForumInfo]" + sql.toString());
		 
		 RowSet rs = null;
		 CourseForumInfoDTO courseForumInfo = null;
		 try{
			 rs = broker.executeQuery(sql);

			 if(rs.next()){
				courseForumInfo 		= 	new CourseForumInfoDTO();

				 courseForumInfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				 courseForumInfo.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				 courseForumInfo.setCurriYear(rs.getInt("curri_year"));
				 courseForumInfo.setCurriTerm(rs.getInt("curri_term"));
				 courseForumInfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				 courseForumInfo.setForumId(rs.getInt("forum_id"));
				 courseForumInfo.setForumType(StringUtil.nvl(rs.getString("forum_type")));
				 courseForumInfo.setParentForumId(rs.getInt("parent_forum_id"));
				 courseForumInfo.setSubTeamNo(rs.getInt("sub_team_no"));
				 courseForumInfo.setSubTeamName(StringUtil.nvl(rs.getString("sub_team_name")));
				 courseForumInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
				 courseForumInfo.setContents(StringUtil.nvl(rs.getString("contents")));
				 courseForumInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				 courseForumInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				 courseForumInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				 courseForumInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
				 courseForumInfo.setForumScore(rs.getInt("forum_score"));
				 courseForumInfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				 courseForumInfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
				 courseForumInfo.setOpenYn(StringUtil.nvl(rs.getString("open_yn")));
				 courseForumInfo.setRegistYn(StringUtil.nvl(rs.getString("regist_yn")));
				 courseForumInfo.setEditorYn(StringUtil.nvl(rs.getString("editor_yn")));
				 courseForumInfo.setVoiceYn(StringUtil.nvl(rs.getString("voice_yn")));
				 courseForumInfo.setCommentUseYn(StringUtil.nvl(rs.getString("comment_use_yn")));
				 courseForumInfo.setEmoticonUseYn(StringUtil.nvl(rs.getString("emoticon_use_yn")));
				 courseForumInfo.setViewThreadYn(StringUtil.nvl(rs.getString("view_thread_yn")));
				 courseForumInfo.setViewPrevNextYn(StringUtil.nvl(rs.getString("view_prev_next_yn")));
				 courseForumInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				 courseForumInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				 courseForumInfo.setModId(StringUtil.nvl(rs.getString("mod_id")));
				 courseForumInfo.setModDate(StringUtil.nvl(rs.getString("mod_date")));
				 
				 courseForumInfo.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
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
		 return courseForumInfo;
	}





	/**
	 * 토론정보 정보 리스트 가져오기(페이징 없이)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param Where
	 * @param Orderby
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getCourseForumInfoList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm , String CourseId , String Where, String Orderby ) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.system_code,  a.curri_code, a.curri_year, a.curri_term, a.course_id, ");
		sb.append(" a.forum_id, a.forum_type, a.parent_forum_id, a.sub_team_no, a.sub_team_name, a.subject, ");
		sb.append(" a.contents, a.rfile_name, a.sfile_name, a.file_path, a.file_size, ");
		sb.append(" a.forum_score, a.start_date, a.end_date, a.open_yn, a.regist_yn, ");
		sb.append(" a.editor_yn, a.voice_yn, a.comment_use_yn, a.emoticon_use_yn, a.view_thread_yn, ");
		sb.append(" a.view_prev_next_yn, a.reg_id, a.reg_date, a.mod_id, a.mod_date ");
		sb.append(" ,(select count(user_id) from course_forum_user where system_code=a.system_code and curri_code=a.curri_code and curri_year=a.curri_year and curri_term=a.curri_term and forum_id=a.forum_id) as teamUserCnt ");
		sb.append(" from course_forum_info a");
		sb.append(" where a.system_code = ? and a.curri_code = ? and a.curri_year = ? and a.curri_term =? and a.course_id = ?  ");
		if(!Where.equals(""))
			sb.append( Where );
		if(Orderby.equals(""))
			sb.append(" order by a.forum_id desc ");
		else
			sb.append(" order by " + Orderby);
		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		log.debug("[getCourseForumInfoList]" + sql.toString());

		RowSet rs = null;
		ArrayList courseForumInfoList = null;
	 try{
		 rs = broker.executeQuery(sql);
		 CourseForumInfoDTO courseForumInfo =	null;
		 courseForumInfoList = new ArrayList();

		 while(rs.next()){
		 	 courseForumInfo = new CourseForumInfoDTO();

			 courseForumInfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			 courseForumInfo.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			 courseForumInfo.setCurriYear(rs.getInt("curri_year"));
			 courseForumInfo.setCurriTerm(rs.getInt("curri_term"));
			 courseForumInfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 courseForumInfo.setForumId(rs.getInt("forum_id"));
			 courseForumInfo.setForumType(StringUtil.nvl(rs.getString("forum_type")));
			 courseForumInfo.setParentForumId(rs.getInt("parent_forum_id"));
			 courseForumInfo.setSubTeamNo(rs.getInt("sub_team_no"));
			 courseForumInfo.setSubTeamName(rs.getString("sub_team_name"));
			 courseForumInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
			 courseForumInfo.setContents(StringUtil.nvl(rs.getString("contents")));
			 courseForumInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
			 courseForumInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
			 courseForumInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			 courseForumInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
			 courseForumInfo.setForumScore(rs.getInt("forum_score"));
			 courseForumInfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 courseForumInfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 courseForumInfo.setOpenYn(StringUtil.nvl(rs.getString("open_yn")));
			 courseForumInfo.setRegistYn(StringUtil.nvl(rs.getString("regist_yn")));
			 courseForumInfo.setEditorYn(StringUtil.nvl(rs.getString("editor_yn")));
			 courseForumInfo.setVoiceYn(StringUtil.nvl(rs.getString("voice_yn")));
			 courseForumInfo.setCommentUseYn(StringUtil.nvl(rs.getString("comment_use_yn")));
			 courseForumInfo.setEmoticonUseYn(StringUtil.nvl(rs.getString("emoticon_use_yn")));
			 courseForumInfo.setViewThreadYn(StringUtil.nvl(rs.getString("view_thread_yn")));
			 courseForumInfo.setViewPrevNextYn(StringUtil.nvl(rs.getString("view_prev_next_yn")));
			 courseForumInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 courseForumInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 courseForumInfo.setModId(StringUtil.nvl(rs.getString("mod_id")));
			 courseForumInfo.setModDate(StringUtil.nvl(rs.getString("mod_date")));
			 courseForumInfo.setTeamUserCnt(rs.getInt("teamUserCnt"));  // team member count
			 

			 courseForumInfoList.add(courseForumInfo);
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

	 	return courseForumInfoList;
	}

	/**
	 * 토론정보 정보를 삭제한다.(Where =>forum_id 거나 parent_id 거나)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int delCourseForumInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String Where) throws DAOException{
		log.debug("=====================>>>>>>  " + Where);
	int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from course_forum_info ");
	 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
	 if(!Where.equals(""))
		sb.append( Where );
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(CourseId);


	 log.debug("[delCourseForumInfo]" + sql.toString());
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
	 * @param CourseId
	 * @param ForumId
	 * @return
	 * @throws DAOException
	 */
	public int delCourseForumInfoFile(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_info set rfile_name ='' , sfile_name = '' , file_size = '' , file_path = '' ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ?");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);

		 log.debug("[delCourseForumInfoFile]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	/**
	 * 토론정보  리스트를 페이징 처리하여 가져온다.(교수, 관리자용)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCourseForumInfoPagingList(int curpage, String SystemCode , String CurriCode, int CurriYear, int CurriTerm, String CourseId , String Where ) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" system_code,  curri_code, curri_year, curri_term, course_id, ");
			sb.append(" forum_id, forum_type, parent_forum_id, sub_team_no, subject, ");
			sb.append(" contents, rfile_name, sfile_name, file_path, file_size, ");
			sb.append(" forum_score, start_date, end_date, open_yn, regist_yn, ");
			sb.append(" editor_yn, voice_yn, comment_use_yn, emoticon_use_yn, view_thread_yn, ");
			sb.append(" view_prev_next_yn, reg_id, reg_date ");
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("forum_id");
			sql.setCutCol("forum_id");
			sql.setAlias(sb.toString());
			sql.setSelect(sb.toString());
			sql.setFrom(" course_forum_info ");
			if(CourseId.equals("")){
				sql.setWhere(" system_code = '"+SystemCode+"' and curri_code = '"+CurriCode+"' and curri_year ='"+CurriYear+"' and curri_term = '"+CurriTerm+"' " + Where );
			}else{
				sql.setWhere(" system_code = '"+SystemCode+"' and curri_code = '"+CurriCode+"' and curri_year ='"+CurriYear+"' and curri_term = '"+CurriTerm+"' and course_id = '"+CourseId+"' " + Where);
			}
			sql.setOrderby(" start_date desc ");

            // 파라미터 셋팅
			log.debug("[getCourseForumInfoPagingList]" + sql.toString());
			//retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
			retVal = broker.executeListQuery(sql, curpage);

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
	 * 토론정보 정보 리스트 가져오기(페이징 없이) => 결과용
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getCourseForumInfoJoinContentsList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm , String CourseId ) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select fi.system_code, fi.curri_code, fi.curri_year, fi.curri_term, fi.course_id, fi.forum_id, ");
		sb.append(" fi.start_date, fi.end_date, fi.subject, ifnull(count(fc.seq_no),0) as cnt , ifnull( sum(fc.hit_no),0) as hit_no  ");
		sb.append(" from course_forum_info fi left outer join course_forum_contents fc  ");
		sb.append(" on fi.system_code=fc.system_code and fi.curri_code = fc.curri_code and  ");
		sb.append(" fi.curri_year=fc.curri_year and fi.curri_term=fc.curri_term and fi.course_id=fc.course_id and ");
		sb.append(" fc.forum_id in ");
		sb.append(" (select a.forum_id from course_forum_info a where a.system_code=fi.system_code and fi.curri_code = a.curri_code and a.curri_year=a.curri_year  ");
		sb.append(" and fi.curri_term=a.curri_term and fi.course_id=a.course_id and  (fi.forum_id=a.forum_id or fi.forum_id=a.parent_forum_id)) ");
		sb.append(" where fi.parent_forum_id=0 ");
		sb.append(" and fi.system_code = ? and fi.curri_code = ? and fi.curri_year = ? and fi.curri_term =? and fi.course_id = ?  ");
		sb.append(" group by fi.system_code, fi.curri_code, fi.curri_year, fi.curri_term, fi.course_id,fi.forum_id, fi.subject , fi.start_date, fi.end_date");
		sb.append(" order by fi.start_date desc ");

		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		log.debug("[getCourseForumInfoJoinContentsList]" + sql.toString());

		RowSet rs = null;
		ArrayList courseForumInfoList = null;
	 try{
		 rs = broker.executeQuery(sql);

		 CourseForumInfoDTO courseForumInfo =	null;

		 courseForumInfoList = new ArrayList();

		 while(rs.next()){
		 	 courseForumInfo = new CourseForumInfoDTO();

			 courseForumInfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			 courseForumInfo.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			 courseForumInfo.setCurriYear(rs.getInt("curri_year"));
			 courseForumInfo.setCurriTerm(rs.getInt("curri_term"));
			 courseForumInfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 courseForumInfo.setForumId(rs.getInt("forum_id"));
			 courseForumInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
			 courseForumInfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 courseForumInfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 courseForumInfo.setContentsCnt(rs.getInt("cnt"));
			 courseForumInfo.setHitCnt(rs.getInt("hit_no"));

			 courseForumInfoList.add(courseForumInfo);
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

	 	return courseForumInfoList;
	}


	/**
	 * 토론정보  리스트를 페이징 처리하여 가져온다.(결과용)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCourseForumInfoJoinContentsPagingList(int curpage, String SystemCode , String CurriCode, int CurriYear, int CurriTerm, String CourseId , String Where ) throws DAOException{
		ListDTO retVal = null;
		try{
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			String groupby = " group by fi.system_code, fi.curri_code, fi.curri_year, fi.curri_term, fi.course_id,fi.forum_id, fi.subject , fi.start_date, fi.end_date ";
			sql.setTotalCol("forum_id");
			sql.setCutCol("forum_id");
			sql.setAlias(" system_code, curri_code,curri_year, curri_term, course_id, "+
						  " forum_id, subject, start_date, end_date, cnt ,  hit_no ");
			sql.setSelect(" fi.system_code, fi.curri_code, fi.curri_year, fi.curri_term, fi.course_id, "+
						  " fi.forum_id, fi.subject, fi.start_date, fi.end_date, ifnull(count(fc.seq_no),0) as cnt , ifnull( sum(fc.hit_no),0) as hit_no ");
			sql.setFrom(" course_forum_info fi left outer join course_forum_contents fc "+
						" on fi.system_code=fc.system_code and fi.curri_code = fc.curri_code "+
						" and fi.curri_year=fc.curri_year and fi.curri_term=fc.curri_term "+
						" and fi.course_id=fc.course_id and fc.forum_id in "+
						" (select a.forum_id from course_forum_info a where a.system_code=fi.system_code "+
						" and fi.curri_code = a.curri_code and a.curri_year=a.curri_year and fi.curri_term=a.curri_term "+
						" and fi.course_id=a.course_id and  (fi.forum_id=a.forum_id or fi.forum_id=a.parent_forum_id)) ");
			if(CourseId.equals("")){
				sql.setWhere(" system_code = '"+SystemCode+"' and curri_code = '"+CurriCode+"' and curri_year ='"+CurriYear+"' and curri_term = '"+CurriTerm+"' " + Where + groupby);
			}else{
				sql.setWhere(" system_code = '"+SystemCode+"' and curri_code = '"+CurriCode+"' and curri_year ='"+CurriYear+"' and curri_term = '"+CurriTerm+"' and course_id = '"+CourseId+"' " + Where + groupby);
			}
			sql.setOrderby(" start_date desc ");

            // 파라미터 셋팅
			log.debug("[getCourseForumInfoJoinContentsPagingList]" + sql.toString());
			//retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
			retVal = broker.executeListQuery(sql, curpage);

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
	 * 토론정보  리스트를 페이징 처리하여 가져온다.(학생용)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCourseForumInfoJoinCFUserPagingList(int curpage, String SystemCode , String CurriCode, int CurriYear, int CurriTerm, String UserId, String Where ) throws DAOException{
		ListDTO retVal = null;
		try{
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("ci.forum_id");
			sql.setCutCol("ci.forum_id");
			sql.setAlias(" system_code, curri_code, curri_year, curri_term, course_id, "+
							" forum_id, forum_type, parent_forum_id, sub_team_no, subject, "+
							" contents, rfile_name, sfile_name, file_path, file_size, "+
							" forum_score,start_date, end_date,open_yn, regist_yn, "+
							" editor_yn, voice_yn, comment_use_yn, emoticon_use_yn, view_thread_yn, "+
							" view_prev_next_yn, reg_id, reg_date , sub_team_no ");
			sql.setSelect(" ci.system_code,  ci.curri_code, ci.curri_year, ci.curri_term, ci.course_id, "+
							" ci.forum_id, ci.forum_type, ci.parent_forum_id, ci.sub_team_no, ci.subject, "+
							" ci.contents, ci.rfile_name, ci.sfile_name, ci.file_path, ci.file_size, "+
							" ci.forum_score, ci.start_date, ci.end_date, ci.open_yn, ci.regist_yn, "+
							" ci.editor_yn, ci.voice_yn, ci.comment_use_yn, ci.emoticon_use_yn, ci.view_thread_yn, "+
							" ci.view_prev_next_yn, ci.reg_id, ci.reg_date , cu.sub_team_no ");
			sql.setFrom(" course_forum_info  ci , course_forum_user  cu ");
			sql.setWhere("  ci.system_code=cu.system_code and ci.curri_code=cu.curri_code and ci.curri_year=cu.curri_year "+
						" and ci.curri_term=cu.curri_term and ci.course_id=cu.course_id and ci.forum_id = cu.forum_id"+
						" and ci.system_code = '"+SystemCode+"' and ci.curri_code = '"+CurriCode+"' and ci.curri_year ='"+CurriYear+"' " +
						" and ci.curri_term = '"+CurriTerm+"' and cu.user_id='"+UserId+"' and ci.regist_yn='Y' " + Where );
			sql.setOrderby(" ci.start_date desc ");

            // 파라미터 셋팅
			log.debug("[getCourseForumInfoPagingList]" + sql.toString());
			//retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
			retVal = broker.executeListQuery(sql, curpage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}

   public ArrayList getCourseForumMainUse(String systemcode) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select subject, reg_date ");
		sb.append(" from course_forum_info  ");
		sb.append(" where system_code = ? and parent_forum_id = 0 ");	//and limit 2
		sb.append(" order by reg_date desc ");
		sb.append(" limit 2 ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);

		log.debug("[getCourseForumMainUse]" + sql.toString());

		RowSet rs = null;
		ArrayList courseForumInfoList = null;
		 try{
			 rs = broker.executeQuery(sql);

			 CourseForumInfoDTO courseForumInfo =	null;

			 courseForumInfoList = new ArrayList();

			 while(rs.next()){
			 	 courseForumInfo = new CourseForumInfoDTO();

				 courseForumInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
				 courseForumInfo.setRegDate(rs.getString("reg_date"));

				 courseForumInfoList.add(courseForumInfo);
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

 	return courseForumInfoList;
   }
}
