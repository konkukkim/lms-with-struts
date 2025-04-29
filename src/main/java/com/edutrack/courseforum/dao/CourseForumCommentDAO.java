/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.dao;

import java.sql.SQLException;
import javax.sql.RowSet;
import java.util.ArrayList;

import com.edutrack.common.CommonUtil;
import com.edutrack.courseforum.dao.CourseForumCommentDAO;
import com.edutrack.courseforum.dto.CourseForumCommentDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
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
public class CourseForumCommentDAO   extends AbstractDAO {
	/**
	 * 코멘트 comm_no 최고값+1 을 가져온다
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SeqNo
	 * @param CommNo
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCommNo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo) throws DAOException{
		 int  maxCommNo = 0;
		
		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(max(comm_no),0)+1 as max_seq_no "); 
		  sb.append(" from course_forum_comment ");
		  sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ?  and course_id = ? and forum_id = ?  and seq_no = ?");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);
		
		 log.debug("[getMaxCommNo]" + sql.toString());
		 
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	maxCommNo = rs.getInt("max_seq_no");
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
		
		 return maxCommNo;   
	}
	
	/**
	 * 공개강좌의 토론방 댓글 사용자 패스워드 확인
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SeqNo
	 * @param commNo
	 * @param regPass
	 * @return
	 * @throws DAOException
	 */
	public int publishForumCommentPassChk(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId, int SeqNo, int commNo, String regPass) throws DAOException{
		 int  maxCommNo = 0;
		
		 StringBuffer sb = new StringBuffer();
		  sb.append(" SELECT IFNULL(COUNT(comm_no),0) as cnt "); 
		  sb.append(" FROM course_forum_comment ");
		  sb.append(" WHERE system_code = ? AND curri_code = ? " +
		  			" AND curri_year = ? AND curri_term = ?  " +
		  			" AND course_id = ? AND forum_id = ? " +
		  			" AND seq_no = ? AND comm_no = ? ");
		  if(!regPass.equals(""))
			  sb.append(" AND reg_passwd = ? ");
		  			
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);
		 sql.setInteger(commNo);
		 if(!regPass.equals(""))
			 sql.setString(regPass);
		
		 log.debug("[getMaxCommNo]" + sql.toString());
		 
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	maxCommNo = rs.getInt("cnt");
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
		
		 return maxCommNo;   
	}
	
	
	/**
	 * 등록된 코멘트 총 수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumCommentCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,  String CourseId, int ForumId, int SeqNo ) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" SELECT IFNULL(COUNT(comm_no),0) as cnt "); 
		  sb.append(" FROM course_forum_comment ");
		  sb.append(" WHERE system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id = ? and forum_id = ? ");
		  if(SeqNo>0)
		 	sb.append(" and seq_no = ? ");
		 
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 if(SeqNo>0)
		 	sql.setInteger(SeqNo);
		 
		 log.debug("[getCurriBbsCommentCount]" + sql.toString());
		 
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
	 * 코멘트를 등록한다.
	 * @param courseForumComment
	 * @return
	 * @throws DAOException
	 */
	public int addCourseForumComment(CourseForumCommentDTO courseForumComment ) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 
	 sb.append(" INSERT INTO course_forum_comment (system_code, curri_code, curri_year, curri_term" +
	 											", course_id, forum_id, seq_no, comm_no" +
	 											", contents, emoticon_num, reg_id, reg_name" +
	 											", reg_passwd, reg_date) ");
	 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	 sql.setSql(sb.toString());
	 
	 sql.setString(courseForumComment.getSystemCode());
	 sql.setString(courseForumComment.getCurriCode());
	 sql.setInteger(courseForumComment.getCurriYear());
	 sql.setInteger(courseForumComment.getCurriTerm());
	 sql.setString(courseForumComment.getCourseId());
	 sql.setInteger(courseForumComment.getForumId());
	 sql.setInteger(courseForumComment.getSeqNo());
	 sql.setInteger(courseForumComment.getCommNo());
	 sql.setString(courseForumComment.getContents());
	 sql.setInteger(courseForumComment.getEmoticonNum());
	 sql.setString(courseForumComment.getRegId());
	 sql.setString(courseForumComment.getRegName());
	 sql.setString(courseForumComment.getRegPasswd());
	 sql.setString(CommonUtil.getCurrentDate());
 
	 log.debug("[addcourseForumComment]2" + sql.toString());
	 
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	
	 
	 return retVal;   
	}
	
	/**
	 * 코멘트를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SeqNo
	 * @param CommNo
	 * @return
	 * @throws DAOException
	 */
	public int delCourseForumComment(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,  String CourseId, int ForumId, int SeqNo, int CommNo ) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from course_forum_comment "); 
	 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id = ? and forum_id = ? ");
	 if(SeqNo>0) sb.append(" and seq_no = ?");
	 if(CommNo>0) sb.append(" and comm_no = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(CourseId);
	 sql.setInteger(ForumId);
	 if(SeqNo>0) sql.setInteger(SeqNo);
	 if(CommNo>0) sql.setInteger(CommNo);
	 
	 log.debug("[delcourseForumComment]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	

	 return retVal;   
	}
	/**
	 * 코멘트 정보 가져오기
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SeqNo
	 * @param CommNo
	 * @return
	 * @throws DAOException
	 */
	public CourseForumCommentDTO getCourseForumComment(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,  String CourseId, int ForumId, int SeqNo, int CommNo ) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code, curri_code, curri_year, curri_term, course_id, forum_id,  seq_no,  comm_no, contents, emoticon_num, reg_id, reg_date, mod_id, mode_date "); 
		 sb.append(" from course_forum_comment ");
		 sb.append(" where system_code = ? and curri_code=? and curri_year = ? and curri_term = ?   and course_id = ? and forum_id = ?  and seq_no = ? and comm_no = ?  ");
		 
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(SeqNo);
		 sql.setInteger(CommNo);
		 
		 log.debug("[getcourseForumComment]" + sql.toString());
		 RowSet rs = null;
		 CourseForumCommentDTO courseForumComment = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	courseForumComment = new CourseForumCommentDTO();
	
			 	courseForumComment.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			 	courseForumComment.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			 	courseForumComment.setCurriYear(rs.getInt("curri_year"));
			 	courseForumComment.setCurriTerm(rs.getInt("curri_term"));
			 	courseForumComment.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 	courseForumComment.setForumId(rs.getInt("forum_id"));
			 	courseForumComment.setSeqNo(rs.getInt("seq_no"));
			 	courseForumComment.setCommNo(rs.getInt("comm_no"));
			 	courseForumComment.setContents(StringUtil.nvl(rs.getString("contents")));
			 	courseForumComment.setEmoticonNum(rs.getInt("emoticon_num"));
			 	courseForumComment.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 	courseForumComment.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 	courseForumComment.setModId(StringUtil.nvl(rs.getString("mod_id")));
			 	courseForumComment.setModDate(StringUtil.nvl(rs.getString("mod_date")));		 	
			 	
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return courseForumComment;   
		}
	
	
	/**
	 * comment 리스트 가져오기(페이징 없이)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SeqNo
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getCourseForumCommentList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,  String CourseId, int ForumId, int SeqNo) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select system_code, curri_code, curri_year, curri_term, course_id, forum_id, seq_no,  comm_no,  contents, emoticon_num, reg_id, reg_date, mod_id, mod_date "); 
	 sb.append(" from course_forum_comment ");
	 sb.append(" where system_code = ? and curri_code=? and curri_year = ? and curri_term = ?  and course_id = ? and forum_id = ?  and seq_no = ? ");
	 sb.append(" order by comm_no desc ");
	 
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(CourseId);
	 sql.setInteger(ForumId);
	 sql.setInteger(SeqNo);
	 
	 log.debug("[getcourseForumCommentList]" + sql.toString());
	 RowSet rs = null;
	 ArrayList courseForumCommentList = null;
	 
	 try{
		 rs = broker.executeQuery(sql);
		 
		 CourseForumCommentDTO courseForumComment =	null;	
		 
		 courseForumCommentList = new ArrayList();

		 while(rs.next()){	
		 	courseForumComment = new CourseForumCommentDTO();

		 	courseForumComment.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
		 	courseForumComment.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
		 	courseForumComment.setCurriYear(rs.getInt("curri_year"));
		 	courseForumComment.setCurriTerm(rs.getInt("curri_term"));
		 	courseForumComment.setCourseId(StringUtil.nvl(rs.getString("course_id")));
		 	courseForumComment.setForumId(rs.getInt("forum_id"));
		 	courseForumComment.setSeqNo(rs.getInt("seq_no"));
		 	courseForumComment.setCommNo(rs.getInt("comm_no"));
		 	courseForumComment.setContents(StringUtil.nvl(rs.getString("contents")));
		 	courseForumComment.setEmoticonNum(rs.getInt("emoticon_num"));
		 	courseForumComment.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		 	courseForumComment.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
		 	courseForumComment.setModId(StringUtil.nvl(rs.getString("mod_id")));
		 	courseForumComment.setModDate(StringUtil.nvl(rs.getString("mod_date")));
		 	
		 	courseForumCommentList.add(courseForumComment);
		 	
		 }
		 rs.close(); 
		 
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return courseForumCommentList;   
	}


	
	/**
	 * 	comment 리스트 가져오기(페이징)
	 *  
	 *
	 */
	public ListDTO getCourseForumCommentPagingList(int curpage, String systemCode, String curriCode, int curriYear, int curriTerm, String pCourseId, int pForumId, int seqNo) throws DAOException {
		ListDTO retVal = null;
		try{
	 		//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("comm_no");
			sql.setCutCol("comm_no");
			sql.setAlias(" system_code, curri_code, curri_year, curri_term, course_id, forum_id, seq_no,  comm_no,  contents, emoticon_num, reg_id, reg_name, reg_date, mod_id, mod_date ");
			sql.setSelect(	" b.system_code, b.curri_code, b.curri_year, b.curri_term" +
							", b.course_id, b.forum_id, b.seq_no,  b.comm_no" +
							",  b.contents, b.emoticon_num, b.reg_id" +
							", (select a.user_name from users a where a.system_code=b.system_code and a.user_id=b.reg_id) as reg_name" +
							", getDateText(b.reg_date,null) reg_date, b.mod_id, b.mod_date ");
			sql.setFrom(" course_forum_comment b");
			sql.setWhere(" b.system_code = ? and b.curri_code=? and b.curri_year = ? and b.curri_term = ?  and b.course_id = ? and b.forum_id = ?  and b.seq_no = ?  ");
			sql.setOrderby(" b.comm_no desc ");

			sql.setString(systemCode);
			sql.setString(curriCode);
			sql.setInteger(curriYear);
			sql.setInteger(curriTerm);
			sql.setString(pCourseId);
			sql.setInteger(pForumId);
			sql.setInteger(seqNo);
			 
			//---- 디버그 출력
			log.debug("[getCourseForumCommentPagingList]" + sql.toString());
			
			//---- 쿼리실행 및 반환값 설정
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
	
	
	public ListDTO getPublishForumCommentPagingList(int curpage, String systemCode, String curriCode, int curriYear, int curriTerm, String pCourseId, int pForumId, int seqNo) throws DAOException {
		ListDTO retVal = null;
		try{
	 		//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("comm_no");
			sql.setCutCol(	"comm_no");
			sql.setAlias(	" system_code, curri_code, curri_year, curri_term" +
							", course_id, forum_id, seq_no,  comm_no" +
							",  contents, emoticon_num, reg_id, reg_name" +
							", reg_passwd, reg_date, mod_id, mod_date ");
			sql.setSelect(	" b.system_code, b.curri_code, b.curri_year, b.curri_term" +
							", b.course_id, b.forum_id, b.seq_no,  b.comm_no" +
							",  b.contents, b.emoticon_num, b.reg_id, b.reg_name" +
							", b.reg_passwd, getDateText(b.reg_date,null) reg_date, b.mod_id, b.mod_date ");
			sql.setFrom(	" course_forum_comment b");
			sql.setWhere(	" b.system_code = ? and b.curri_code=? " +
							" and b.curri_year = ? and b.curri_term = ? " +
							" and b.course_id = ? and b.forum_id = ? " +
							" and b.seq_no = ?  ");
			sql.setOrderby(	" b.comm_no desc ");

			sql.setString(systemCode);
			sql.setString(curriCode);
			sql.setInteger(curriYear);
			sql.setInteger(curriTerm);
			sql.setString(pCourseId);
			sql.setInteger(pForumId);
			sql.setInteger(seqNo);
			 
			//---- 디버그 출력
			log.debug("[getPublishForumCommentPagingList]" + sql.toString());
			
			//---- 쿼리실행 및 반환값 설정
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
	
	
	public static void main(String args[]) throws DAOException{
		CourseForumCommentDAO ld = new CourseForumCommentDAO();
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
	//    ld.getBbsCommentList(1,10, 10, "1", 1, " subject like '%ss%' " , "");
	    int i = 0;
	}
}
