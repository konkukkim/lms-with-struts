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
import com.edutrack.courseforum.dto.CourseForumUserDTO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
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
public class CourseForumUserDAO  extends AbstractDAO {


	/**
	 * 등록된 포럼 수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
//	public int getCourseForumUserCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId , String Where ) throws DAOException{
//		 int  cnt = 0;
//
//		 StringBuffer sb = new StringBuffer();
//		  sb.append(" select ifnull(count(user_id),0) as cnt ");
//		  sb.append(" from course_forum_user ");
//		  sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
//		  if(!Where.equals(""))
//		  	sb.append(Where);
//
//		 QueryStatement sql = new QueryStatement();
//		 sql.setSql(sb.toString());
//
//		 sql.setString(SystemCode);
//		 sql.setString(CurriCode);
//		 sql.setInteger(CurriYear);
//		 sql.setInteger(CurriTerm);
//		 sql.setString(CourseId);
//
//		 log.debug("[getCourseForumUserCount]" + sql.toString());
//
//		 RowSet rs = null;
//		 try{
//			 rs = broker.executeQuery(sql);
//			 if(rs.next()){
//			 	cnt = rs.getInt("cnt");
//			 }
//			 rs.close();
//		 }catch(Exception e){
//		      e.printStackTrace();
//			  log.error(e.getMessage());
//			 throw new DAOException(e.getMessage());
//		 }finally{
//			 try{
//			  if(rs != null) rs.close();
//			 }catch(SQLException e){
//				throw new DAOException(e.getMessage());
//			 }
//		 }
//
//		 return cnt;
//	}




	/**
	 * 등록된 포럼 수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumUserDistinctCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId , String Where ) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count( distinct user_id),0) as cnt ");
		  sb.append(" from course_forum_user ");
		  sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
		  if(!Where.equals(""))
		  	sb.append(Where);

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);

		 log.debug("[getCourseForumUserCount]" + sql.toString());

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
	 * 학생이 등록되어 있는 포럼아이디를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int getSubForumId(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId , int ForumId , String UserId ) throws DAOException{
		 int  sub_team_no = 0;

		 StringBuffer sb = new StringBuffer();
		  //sb.append(" select sub_team_no ");
		 sb.append(" select forum_id ");
		  sb.append(" from course_forum_user ");
		  sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and user_id = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setString(UserId);

		 log.debug("[getCourseForumUserCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	sub_team_no = rs.getInt("forum_id");
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

		 return sub_team_no;
	}

	/**
	 * 해당토론방에 등록되어 있는 지 여부를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int getUserIdRegistCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId , int ForumId , String UserId) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(user_id),0) as cnt ");
		  sb.append(" from course_forum_user ");
		  sb.append(" where system_code = ?  and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and forum_id = ? and user_id = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setString(UserId);

		 log.debug("[getUserIdRegistCount]" + sql.toString());

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
	 * 접속 정보 수정한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int editConnetNo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId , int ForumId , String UserId) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_user set ");
		 sb.append(" connect_no = connect_no + 1  ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id= ? and forum_id= ? and user_id = ?");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setString(UserId);

		 log.debug("[editConnetNo]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}



	/**
	 * 유저의 정보를 등록한다.
	 * @param courseForumInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCourseForumUser(CourseForumUserDTO courseForumUser) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" insert into course_forum_user( ");
	 sb.append(" system_code,  curri_code, curri_year, curri_term, course_id, ");
	 sb.append(" forum_id, user_id, sub_team_no, score, connect_no, ");
	 sb.append(" reg_id, reg_date ) ");
	 sb.append(" values( ?, ? ,?, ?, ?, ");
	 sb.append(" ?, ?, ?, ?, ?, ");
	 sb.append(" ?, ? )");

	 sql.setSql(sb.toString());

	 sql.setString(courseForumUser.getSystemCode());
	 sql.setString(courseForumUser.getCurriCode());
	 sql.setInteger(courseForumUser.getCurriYear());
	 sql.setInteger(courseForumUser.getCurriTerm() );
	 sql.setString(courseForumUser.getCourseId() );
	 sql.setInteger(courseForumUser.getForumId() );
	 sql.setString(courseForumUser.getUserId() );
	 sql.setInteger(courseForumUser.getSubForumId() );
	 sql.setInteger(courseForumUser.getScore() );
	 sql.setInteger(courseForumUser.getConnectNo());
	 sql.setString(courseForumUser.getRegId() );
	 sql.setString(CommonUtil.getCurrentDate());

	 log.debug("[addCourseForumUser]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 토론자 정보를 수정한다.
	 * @param courseForumUser
	 * @return
	 * @throws DAOException
	 */
	public int editCourseForumUser(CourseForumUserDTO courseForumUser) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_user set ");
		 sb.append(" sub_team_no = ? ,  score = ? , connect_no = ? , mod_id = ? , mod_date = ?  ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id= ? and forum_id= ? and user_id = ?");
		 sql.setSql(sb.toString());

		 sql.setInteger(courseForumUser.getSubForumId());
		 sql.setInteger(courseForumUser.getScore() );
		 sql.setInteger(courseForumUser.getConnectNo() );
		 sql.setString(courseForumUser.getModId() );
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(courseForumUser.getSystemCode());
		 sql.setString(courseForumUser.getCurriCode());
		 sql.setInteger(courseForumUser.getCurriYear());
		 sql.setInteger(courseForumUser.getCurriTerm());
		 sql.setString(courseForumUser.getCourseId());
		 sql.setInteger(courseForumUser.getForumId());
		 sql.setString(courseForumUser.getUserId());

		 log.debug("[editCourseForumUser]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}



	/**
	 * 토론자 점수를 넣는다.
	 * @param Score
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SubForumId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public boolean editCourseForumUserScore(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId , String[] Score,  String[] UserId) throws DAOException{
		 
		 boolean  retVal = false;
		 ISqlStatement[] sqlArray = new ISqlStatement[UserId.length];
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 
		 sb.append(" update course_forum_user ");
		 sb.append("  set score = ?  ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and curri_code = ? ");
		 sb.append(" and curri_year = ? ");
		 sb.append(" and curri_term = ? ");
		 sb.append(" and course_id= ? ");
		 sb.append(" and forum_id= ? ");
		 sb.append(" and user_id = ?");
		 
		 for(int i=0; i<UserId.length;i++){
		 	
		 	 sql = new QueryStatement();
		 	 sql.setSql(sb.toString());
		 	 
			 sql.setInteger(Integer.parseInt(Score[i]));
			 sql.setString(SystemCode);
			 sql.setString(CurriCode);
			 sql.setInteger(CurriYear);
			 sql.setInteger(CurriTerm);
			 sql.setString(CourseId);
			 sql.setInteger(ForumId);
			 //sql.setInteger(SubForumId);
			 sql.setString(UserId[i]);
			 sqlArray[i] = sql;
			 
			 log.debug("[editCourseForumUserScore]" + sql.toString());
		 }
		 
		 try{
			 retVal = broker.executeUpdate(sqlArray);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}


	/**
	 * 토론자 정보 가져오기
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param UserId
	 * @return CourseForumUserDTO
	 * @throws DAOException
	 */
	public CourseForumUserDTO getCourseForumUser(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId , String UserId) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" select system_code,  curri_code, curri_year, curri_term, course_id, ");
		 sb.append(" forum_id, user_id, sub_team_no, score, connect_no, ");
		 sb.append(" reg_id, reg_date, mod_id, mod_date ");
		 sb.append(" from course_forum_user ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term =? and course_id = ? and forum_id = ? and user_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setInteger(ForumId);
		 sql.setInteger(UserId);

		 log.debug("[getCourseForumUser]" + sql.toString());
		 RowSet rs = null;
		 CourseForumUserDTO courseForumUser = null;
		 try{
			 rs = broker.executeQuery(sql);

			 if(rs.next()){
				courseForumUser 		= 	new CourseForumUserDTO();

				 courseForumUser.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				 courseForumUser.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				 courseForumUser.setCurriYear(rs.getInt("curri_year"));
				 courseForumUser.setCurriTerm(rs.getInt("curri_term"));
				 courseForumUser.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				 courseForumUser.setForumId(rs.getInt("forum_id"));
				 courseForumUser.setUserId(rs.getString("user_id"));
				 courseForumUser.setSubForumId(rs.getInt("sub_team_no"));
				 courseForumUser.setScore(rs.getInt("score"));
				 courseForumUser.setConnectNo(rs.getInt("connect_no"));
				 courseForumUser.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				 courseForumUser.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				 courseForumUser.setModId(StringUtil.nvl(rs.getString("mod_id")));
				 courseForumUser.setModDate(StringUtil.nvl(rs.getString("mod_date")));
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
		 return courseForumUser;
	}

	/**
	 * 토론자정보 정보 리스트 가져오기(페이징 없이)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getCourseForumUserList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm , String CourseId , int ForumId ) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select system_code,  curri_code, curri_year, curri_term, course_id, ");
		sb.append(" forum_id, user_id, sub_team_no, score, connect_no, ");
		sb.append(" reg_id, reg_date, mod_id, mod_date ");
		sb.append(" from course_forum_user ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term =? and course_id = ? and forum_id = ?");
		sb.append(" order by curri_code desc, curri_year desc, curri_term desc, course_id asc, forum_id desc , user_id asc");
		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setInteger(ForumId);

		log.debug("[getCourseForumUserList]" + sql.toString());

		RowSet rs = null;
		ArrayList courseForumUserList = null;
	 try{
		 rs = broker.executeQuery(sql);

		 CourseForumUserDTO courseForumUser =	null;

		 courseForumUserList = new ArrayList();

		 while(rs.next()){
		 	 courseForumUser = new CourseForumUserDTO();

			 courseForumUser.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			 courseForumUser.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			 courseForumUser.setCurriYear(rs.getInt("curri_year"));
			 courseForumUser.setCurriTerm(rs.getInt("curri_term"));
			 courseForumUser.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 courseForumUser.setForumId(rs.getInt("forum_id"));
			 courseForumUser.setUserId(StringUtil.nvl(rs.getString("user_id")));
			 courseForumUser.setSubForumId(rs.getInt("sub_team_no"));
			 courseForumUser.setScore(rs.getInt("score"));
			 courseForumUser.setConnectNo(rs.getInt("connect_no"));
			 courseForumUser.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 courseForumUser.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 courseForumUser.setModId(StringUtil.nvl(rs.getString("mod_id")));
			 courseForumUser.setModDate(StringUtil.nvl(rs.getString("mod_date")));

			 courseForumUserList.add(courseForumUser);
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

	 	return courseForumUserList;
	}






	/**
	 * 토론자정보(학생테이블과 유저테이블 조인) 정보 리스트 가져오기(페이징 없이)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getCourseForumUserJoinStudentList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm , String CourseId , int ForumId ) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select u.user_name, s.user_id,  cu.forum_id, cu.sub_team_no, cf.subject ");
		sb.append(" from users u INNER JOIN student s ");
		sb.append(" ON u.system_code = s.system_code and  u.USER_ID = s.USER_ID  ");
		sb.append(" LEFT OUTER JOIN ( course_forum_user cu ");
		sb.append(" INNER JOIN  course_forum_info cf ");
		sb.append(" ON cf.system_code = cu.system_code and cf.curri_code = cu.curri_code and ");
		sb.append(" cf.curri_year=cu.curri_year and cf.curri_term=cu.curri_term and cf.course_id=cu.course_id and cf.forum_id=cu.sub_team_no )");
		sb.append(" ON s.system_code = cu.system_code and s.curri_code = cu.curri_code and ");
		sb.append(" s.curri_year=cu.curri_year and s.curri_term=cu.curri_term and u.user_id=cu.user_id ");
		sb.append(" and cu.forum_id= ? and cu.course_id = ? ");
		sb.append(" where s.enroll_status = 'S' and s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term =? ");
		sb.append(" order by u.user_name asc, s.user_id asc ");
		sql.setSql(sb.toString());
		sql.setInteger(ForumId);
		sql.setString(CourseId);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);


		log.debug("[getCourseForumUserJoinStudentList]" + sql.toString());

		RowSet rs = null;
		ArrayList courseForumUserList = null;
	 try{
		 rs = broker.executeQuery(sql);

		 CourseForumUserDTO courseForumUser =	null;

		 courseForumUserList = new ArrayList();

		 while(rs.next()){
		 	 courseForumUser = new CourseForumUserDTO();

		 	 courseForumUser.setUserName(StringUtil.nvl(rs.getString("user_name")));
		 	 courseForumUser.setUserId(StringUtil.nvl(rs.getString("user_id")));
			 courseForumUser.setForumId(rs.getInt("forum_id"));
			 courseForumUser.setSubForumId(rs.getInt("sub_team_no"));
			 courseForumUser.setSubject(rs.getString("subject"));

			 courseForumUserList.add(courseForumUser);
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

	 	return courseForumUserList;
	}

	/**
	 * 토론자정보(학생테이블과 유저테이블 조인) 정보 리스트 가져오기(페이징 없이)
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param SubForumId
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList getCourseForumUserJoinStudentSubList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm , String CourseId , int SubForumId ) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select u.user_name, s.user_id,  cu.forum_id, cu.sub_team_no ");
		sb.append(" from users u , student s , course_forum_user cu ");
		sb.append(" Where u.system_code = s.system_code and  u.USER_ID = s.USER_ID  ");
		sb.append(" and s.system_code = cu.system_code and s.curri_code = cu.curri_code and ");
		sb.append(" s.curri_year=cu.curri_year and s.curri_term=cu.curri_term and u.user_id=cu.user_id ");
		sb.append(" and cu.sub_team_no= ? and cu.course_id = ? ");
		sb.append(" and s.enroll_status = 'S' and s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term =? ");
		sb.append(" order by u.user_name asc, s.user_id asc ");
		sql.setSql(sb.toString());
		sql.setInteger(SubForumId);
		sql.setString(CourseId);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);


		log.debug("[getCourseForumUserJoinStudentList]" + sql.toString());

		RowSet rs = null;
		ArrayList courseForumUserList = null;
	 try{
		 rs = broker.executeQuery(sql);

		 CourseForumUserDTO courseForumUser =	null;

		 courseForumUserList = new ArrayList();

		 while(rs.next()){
		 	 courseForumUser = new CourseForumUserDTO();

		 	 courseForumUser.setUserName(StringUtil.nvl(rs.getString("user_name")));
		 	 courseForumUser.setUserId(StringUtil.nvl(rs.getString("user_id")));
			 courseForumUser.setForumId(rs.getInt("forum_id"));
			 courseForumUser.setSubForumId(rs.getInt("sub_team_no"));

			 courseForumUserList.add(courseForumUser);
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

	 	return courseForumUserList;
	}

	/**
	 * 토론자정보 정보를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param Where
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int delCourseForumUser(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String Where , String UserId) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from course_forum_user ");
	 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? ");
	 if(!Where.equals(""))
	 	sb.append(Where);
	 if(!UserId.equals(""))
	 	sb.append(" and user_id = ? ");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(CourseId);
	 if(!UserId.equals(""))
	 	sql.setInteger(UserId);

	 log.debug("[delCourseForumUser]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 토론자 정보  리스트를 페이징 처리하여 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCourseForumUserPagingList(int curpage, String SystemCode , String CurriCode, int CurriYear, int CurriTerm, String CourseId, int ForumId ) throws DAOException{
		ListDTO retVal = null;
		try{
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("course_id+forum_id");
			sql.setCutCol(" concat(course_id, cast(forum_id AS CHAR))");
			sql.setAlias(" system_code,  curri_code, curri_year, curri_term, course_id, "+
							" forum_id, user_id, sub_team_no, score, connect_no, "+
							" reg_id, reg_date, mod_id, mod_date ");
			sql.setSelect(" system_code,  curri_code, curri_year, curri_term, course_id, "+
							" forum_id, user_id, sub_team_no, score, connect_no, "+
							" reg_id, reg_date, mod_id, mod_date ");
			sql.setFrom(" course_forum_user ");
			sql.setWhere(" system_code = '"+SystemCode+"' and curri_code = '"+CurriCode+"' and curri_year ='"+CurriYear+"' and curri_term = '"+CurriTerm+"' and course_id = '"+CourseId+"' and forum_id ='"+ ForumId + "'");
			sql.setOrderby("curri_code , curri_year, curri_term , course_id  forum_id desc ");

            // 파라미터 셋팅
			log.debug("[getCourseForumUserPagingList]" + sql.toString());
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
	 * 토론자정보 정보 리스트 가져오기 => 채점용
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ForumId
	 * @param SubForumId
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ListDTO getCourseForumUserJoinContentsPagingList(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm , String CourseId , int ForumId) throws DAOException{
		ListDTO retVal = null;

		try{
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("cu.user_id");
			sql.setCutCol(" concat(cu.forum_id,cu.user_id)");
//			sql.setAlias(" cu.forum_id, cu.user_id, cu.sub_team_no, cu.score, cu.connect_no, "+
//							" (select count(seq_no) from course_forum_contents where system_code=cu.system_code and curri_code=cu.curri_code and curri_year=cu.curri_year and curri_term=cu.curri_term and course_id=cu.course_id and cu.sub_team_no=forum_id and cu.user_id=reg_id ) as contents_cnt , "+
//							" (select count(seq_no) from course_forum_comment where system_code=cu.system_code and curri_code=cu.curri_code and curri_year=cu.curri_year and curri_term=cu.curri_term and course_id=cu.course_id and cu.sub_team_no=forum_id and cu.user_id=reg_id ) as comment_cnt  , "+
//							" cu.reg_id, cu.reg_date");
			sql.setAlias(" forum_id, user_id, sub_team_no, score, connect_no, contents_cnt , comment_cnt  , reg_id, reg_date");
			sql.setSelect(" cu.forum_id, cu.user_id, cu.sub_team_no, cu.score, cu.connect_no, "+
							" (select count(seq_no) from course_forum_contents where system_code=cu.system_code and curri_code=cu.curri_code and curri_year=cu.curri_year and curri_term=cu.curri_term and course_id=cu.course_id and forum_id=cu.forum_id and cu.user_id=reg_id ) as contents_cnt , "+
							" (select count(seq_no) from course_forum_comment where system_code=cu.system_code and curri_code=cu.curri_code and curri_year=cu.curri_year and curri_term=cu.curri_term and course_id=cu.course_id and forum_id=cu.forum_id and cu.user_id=reg_id ) as comment_cnt  , "+
							" cu.reg_id, cu.reg_date");
			sql.setFrom(" course_forum_user cu ");
			//if(SubForumId > 0)
			//	sql.setWhere(" cu.system_code = '"+SystemCode+"' and cu.curri_code = '"+CurriCode+"' and cu.curri_year ='"+CurriYear+"' and cu.curri_term = '"+CurriTerm+"' and cu.course_id = '"+CourseId+"' and cu.forum_id ='"+ ForumId + "' and cu.sub_team_no='" + SubForumId+ " ' ");
			//else
				sql.setWhere(" cu.system_code = '"+SystemCode+"' and cu.curri_code = '"+CurriCode+"' and cu.curri_year ='"+CurriYear+"' and cu.curri_term = '"+CurriTerm+"' and cu.course_id = '"+CourseId+"' and cu.forum_id ='"+ ForumId + "'");
			sql.setOrderby(" cu.user_id asc ");

            // 파라미터 셋팅
			log.debug("[getCourseForumUserJoinContentsPagingList]" + sql.toString());
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
		 * 토론자정보 정보 리스트 가져오기 => 채점용
		 * @param SystemCode
		 * @param CurriCode
		 * @param CurriYear
		 * @param CurriTerm
		 * @param CourseId
		 * @param ForumId
		 * @param SubForumId
		 * @return ArrayList
		 * @throws DAOException
		 */
		public ListDTO getCourseForumResultUserList(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm , String CourseId , int ForumId , int SubForumId) throws DAOException{
			ListDTO retVal = null;

			try{
				// List Sql문 생성
				ListStatement sql = new ListStatement();
				sql.setTotalCol("cu.user_id");
				sql.setCutCol(" concat(cu.forum_id,cu.user_id)");
				sql.setAlias(" forum_id, user_id, sub_team_no, score, connect_no, "+
								" contents_cnt , comment_cnt , reg_id, reg_date");
				sql.setSelect(" cu.forum_id, cu.user_id, cu.sub_team_no, cu.score, cu.connect_no, "+
								" (select count(seq_no) from course_forum_contents where system_code=cu.system_code and curri_code=cu.curri_code and curri_year=cu.curri_year and curri_term=cu.curri_term and course_id=cu.course_id and cu.sub_team_no=forum_id and cu.user_id=reg_id ) as contents_cnt , "+
								" (select count(seq_no) from course_forum_comment where system_code=cu.system_code and curri_code=cu.curri_code and curri_year=cu.curri_year and curri_term=cu.curri_term and course_id=cu.course_id and cu.sub_team_no=forum_id and cu.user_id=reg_id ) as comment_cnt  , "+
								" cu.reg_id, cu.reg_date");
				sql.setFrom(" course_forum_user cu ");
				if(SubForumId > 0)
					sql.setWhere(" cu.system_code = '"+SystemCode+"' and cu.curri_code = '"+CurriCode+"' and cu.curri_year ='"+CurriYear+"' and cu.curri_term = '"+CurriTerm+"' and cu.course_id = '"+CourseId+"' and cu.forum_id ='"+ ForumId + "' and cu.sub_team_no='" + SubForumId+ " ' ");
				else
					sql.setWhere(" cu.system_code = '"+SystemCode+"' and cu.curri_code = '"+CurriCode+"' and cu.curri_year ='"+CurriYear+"' and cu.curri_term = '"+CurriTerm+"' and cu.course_id = '"+CourseId+"' and cu.forum_id ='"+ ForumId + "'");
				sql.setOrderby(" cu.user_id asc ");

	            // 파라미터 셋팅
				log.debug("[getCourseForumUserJoinContentsPagingList]" + sql.toString());
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
		 * 토론자정보 정보 리스트 가져오기(페이징없이 ) =>결과 현황 표시용
		 * @param SystemCode
		 * @param CurriCode
		 * @param CurriYear
		 * @param CurriTerm
		 * @param CourseId
		 * @param ForumId
		 * @return RowSet
		 * @throws DAOException
		 */
		public RowSet getCourseForumResultUserList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm , String CourseId ) throws DAOException{
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			String InfoSql = "";
			String SubForumIdSql = "";
			//포럼아이디를 가져온다.
			CourseForumInfoDAO	forumInfoDao	= 	new CourseForumInfoDAO();
			CourseForumInfoDTO	forumInfoDto	= 	new CourseForumInfoDTO();
			ArrayList ForumInfo = forumInfoDao.getCourseForumInfoList(Systemcode, CurriCode, CurriYear, CurriTerm , CourseId , " and parent_forum_id ='0' ", "");

			for(int i=0; i < ForumInfo.size(); i++){
				forumInfoDto = (CourseForumInfoDTO)ForumInfo.get(i);
				if(forumInfoDto.getForumType().equals("A")){
					InfoSql += " (select ifnull(connect_no,0) from course_forum_user where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and user_id=fu.user_id  and forum_id='"+ forumInfoDto.getForumId()+"') as connect"+i+" , ";
					InfoSql += " (select ifnull(count(seq_no),0) from course_forum_contents  where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and reg_id=fu.user_id and forum_id='"+ forumInfoDto.getForumId()+"') as contents"+i+" , ";
				}else{
					InfoSql += " (select ifnull(connect_no,0) from course_forum_user where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and user_id=fu.user_id  and forum_id = '"+ forumInfoDto.getForumId()+"') as connect"+i+" , ";
					InfoSql += " (select ifnull(count(seq_no),0) from course_forum_contents  where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and reg_id=fu.user_id and forum_id in ( select forum_id from course_forum_info where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and parent_forum_id ='"+forumInfoDto.getForumId()+"' ) ) as contents"+i+" , ";

				}
			}

			sb.append(" select  distinct user_id , ");
			sb.append( InfoSql );
			sb.append(" course_id ");
			sb.append(" from course_forum_user  fu ");
			sb.append(" where fu.system_code = ? and fu.curri_code = ? and fu.curri_year = ? and fu.curri_term =? and fu.course_id = ? ");
			sb.append(" order by fu.user_id asc");
			sql.setSql(sb.toString());
			sql.setString(Systemcode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(CourseId);

			log.debug("[getCourseForumResultUserList]" + sql.toString());

			RowSet rs = null;
			ArrayList courseForumUserList = null;
		 try{
			 rs = broker.executeQuery(sql);

		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{

		 }
		 	return rs;
	}

		/**
		 * 토론자정보 정보 리스트 가져오기 => 현황용
		 * @param SystemCode
		 * @param CurriCode
		 * @param CurriYear
		 * @param CurriTerm
		 * @param CourseId
		 * @param ForumId
		 * @param SubForumId
		 * @return ArrayList
		 * @throws DAOException
		 */
		public ListDTO getCourseForumResultUserPagingList(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm , String CourseId ) throws DAOException{
			ListDTO retVal = null;

			String InfoSql = "";
			String SubForumIdSql = "";
			String 	SelSql = "";
			String AliasSql = "";
			//포럼아이디를 가져온다.
			CourseForumInfoDAO	forumInfoDao	= 	new CourseForumInfoDAO();
			CourseForumInfoDTO	forumInfoDto	= 	new CourseForumInfoDTO();
			ArrayList ForumInfo = forumInfoDao.getCourseForumInfoList(SystemCode, CurriCode, CurriYear, CurriTerm , CourseId , " and parent_forum_id ='0' ", "");

			for(int i=0; i < ForumInfo.size(); i++){
				forumInfoDto = (CourseForumInfoDTO)ForumInfo.get(i);
				if(forumInfoDto.getForumType().equals("A")){
					InfoSql += " (select ifnull(connect_no,0) from course_forum_user where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and user_id=fu.user_id  and forum_id='"+ forumInfoDto.getForumId()+"') as connect"+i+" , ";
					InfoSql += " (select ifnull(count(seq_no),0) from course_forum_contents  where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and reg_id=fu.user_id and forum_id='"+ forumInfoDto.getForumId()+"') as contents"+i+" , ";
					SelSql	+= " a.connect"+i+" ,a.contents"+i+" , ";
					AliasSql +=" connect"+i+" ,contents"+i+" , ";
				}else{
					InfoSql += " (select ifnull(connect_no,0) from course_forum_user where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and user_id=fu.user_id  and forum_id = '"+ forumInfoDto.getForumId()+"' ) as connect"+i+" , ";
					InfoSql += " (select ifnull(count(seq_no),0) from course_forum_contents  where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and reg_id=fu.user_id and forum_id in ( select forum_id from course_forum_info where system_code=fu.system_code and curri_code=fu.curri_code and curri_year=fu.curri_year and curri_term=fu.curri_term and course_id=fu.course_id and parent_forum_id ='"+forumInfoDto.getForumId()+"' ) ) as contents"+i+" , ";
					SelSql	+= " a.connect"+i+" ,a.contents"+i+" , ";
					AliasSql +=" connect"+i+" ,contents"+i+" , ";
				}
			}

			try{
				// List Sql문 생성
				ListStatement sql = new ListStatement();
				sql.setTotalCol("a.user_id");
				sql.setCutCol("a.user_id");
				sql.setAlias(" user_id, " + AliasSql + " course_id ");
				sql.setSelect(" a.user_id, " + SelSql + " a.course_id ");
				sql.setFrom(" ( select distinct fu.user_id, "+ InfoSql + " fu.course_id from course_forum_user fu "+
						" where  fu.system_code = '"+SystemCode+"' and fu.curri_code = '"+CurriCode+"' and fu.curri_year ='"+CurriYear+"' and fu.curri_term = '"+CurriTerm+"' and fu.course_id = '"+CourseId+"' ) a ");
				//sql.setSelect(" distinct  fu.user_id , " + InfoSql + " fu.course_id ");
				//sql.setFrom(" course_forum_user fu ");
				sql.setWhere("");
				sql.setOrderby(" a.user_id asc ");

	            // 파라미터 셋팅
				log.debug("[getCourseForumResultUserPagingList]" + sql.toString());
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

	public static void main(String args[]) throws DAOException{
		CourseForumUserDAO ld = new CourseForumUserDAO();
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
