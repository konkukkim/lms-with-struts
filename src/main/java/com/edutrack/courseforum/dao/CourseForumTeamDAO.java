/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.dao;

import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.courseforum.dto.CourseForumTeamDTO;
import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseForumTeamDAO  extends AbstractDAO {

	/**
	 * 팀 토론에 지정된 수강생 수를 가져온다..
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @param parentForumId
	 * @return
	 * @throws DAOException
	 */
	public int getCourseForumTeamMemberCnt(String systemcode, String curricode, int curriyear, int curriterm, String courseid, int parentForumId) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		int retVal = 0;
		
		sb.append(" select count(m.user_id) as cnt ");
		sb.append(" from course_forum_user m ");
		sb.append(" where m.system_code = ? ");
		sb.append(" and m.curri_code = ? ");
		sb.append(" and m.curri_year = ? ");
		sb.append(" and m.curri_term = ? ");
		sb.append(" and m.course_id = ? ");
		sb.append(" and m.forum_id  in  ");
		sb.append(" ( select forum_id ");
		sb.append(" from course_forum_info ");
		sb.append(" where system_code=? ");
		sb.append(" and curri_code=? ");
		sb.append(" and curri_year=? ");
		sb.append(" and curri_term =? ");
		sb.append(" and course_id=?  ");
		sb.append(" and parent_forum_id=? ) ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setInteger(parentForumId);		


		log.debug("[getCourseForumTeamMemberCnt]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs	= 	broker.executeQuery(sql);
			
			if(rs.next()){
				retVal = rs.getInt("cnt");
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	
	/**
	 * 토론팀을 자동생성한다.
	 * @param courseForumInfo
	 * @return
	 * @throws DAOException
	 */
	public boolean addCourseForumSubTeamInfoAuto(ArrayList arrList, CourseForumInfoDTO courseForumInfo, String gubun) throws DAOException{
		boolean  retVal = false;
		 
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 CourseTeamInfoDTO courseTeamInfoDto = new CourseTeamInfoDTO();
		 ISqlStatement[] sqlArray = new ISqlStatement[arrList.size()];
		 
		 sb.append(" insert into course_forum_info( ");
		 sb.append(" system_code,  curri_code, curri_year, curri_term, course_id, ");
		 sb.append(" forum_id, forum_type, parent_forum_id,");
		 sb.append(" sub_team_no, sub_team_name, ");
		 sb.append(" subject, contents, rfile_name, sfile_name, file_path, file_size, ");
		 sb.append(" forum_score, start_date, end_date, open_yn, regist_yn, ");
		 sb.append(" editor_yn, voice_yn, comment_use_yn, emoticon_use_yn, view_thread_yn, ");
		 sb.append(" view_prev_next_yn, reg_id, reg_date ) ");
		 // select insert
		 sb.append(" select  ?,  ?, ?, ?, ?, ");
		 sb.append(" (ifnull(max(forum_id),0)+1), ?, ?, ");
		 sb.append(" ?, ?,");  // sub_team_no
		 sb.append(" ?, ?, ?, ?, ?, ?, ");
		 sb.append(" ?, ?, ?, ?, ?, ");
		 sb.append(" ?, ?, ?, ?, ?, ");
		 sb.append(" ?, ?, ?   ");
		 sb.append(" from course_forum_info ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and curri_code = ? ");
		 sb.append(" and curri_year = ? ");
		 sb.append(" and curri_term = ? ");
		 sb.append(" and course_id = ? ");
		 //sb.append(" and forum_id = ? ");

		 try{
		 	
		 	 for(int i=0; i<arrList.size();i++){
		 	 	courseTeamInfoDto = (CourseTeamInfoDTO)arrList.get(i);
		 	 	
		 	 	sql = new QueryStatement();
		 	 	sql.setSql(sb.toString());
		 	 	
		 	 	// select
		 	 	sql.setString(courseForumInfo.getSystemCode());
		 	 	sql.setString(courseForumInfo.getCurriCode());
		 	 	sql.setInteger(courseForumInfo.getCurriYear());
		 	 	sql.setInteger(courseForumInfo.getCurriTerm());
		 	 	sql.setString(courseForumInfo.getCourseId());
		 	 	sql.setString(courseForumInfo.getForumType());
		 	 	sql.setInteger(courseForumInfo.getParentForumId());
			 	// sub_team_no
		 	 	sql.setInteger(courseTeamInfoDto.getTeamNo()); 
		 	 	sql.setString(courseTeamInfoDto.getTeamName());
		 	 	
				sql.setString(courseForumInfo.getSubject());
		 	 	sql.setString(courseForumInfo.getContents());
		 	 	sql.setString(courseForumInfo.getRfileName());
		 	 	
		 	 	if(("auto".equals(gubun))){
			 	 	sql.setString(courseForumInfo.getSfileName2()[i]);  // file 을 카피한 데이타를 입력..
		 	 	}else{
			 	 	sql.setString(courseForumInfo.getSfileName());
		 	 	}
		 	 	
		 	 	sql.setString(courseForumInfo.getFilePath());
		 	 	sql.setString(courseForumInfo.getFileSize());
		 	 	sql.setInteger(courseForumInfo.getForumScore());
		 	 	sql.setString(courseForumInfo.getStartDate());
		 	 	sql.setString(courseForumInfo.getEndDate());
		 	 	sql.setString(courseForumInfo.getOpenYn());
		 	 	sql.setString(courseForumInfo.getRegistYn());
		 	 	sql.setString(courseForumInfo.getEditorYn());
		 	 	sql.setString(courseForumInfo.getVoiceYn());
		 	 	sql.setString(courseForumInfo.getCommentUseYn());
		 	 	sql.setString(courseForumInfo.getEmoticonUseYn());
		 	 	sql.setString(courseForumInfo.getViewThreadYn());
		 	 	sql.setString(courseForumInfo.getViewPrevNextYn());
		 	 	// reg id
		 	 	sql.setString(courseForumInfo.getRegId());
				sql.setString(CommonUtil.getCurrentDate());
				// where
				sql.setString(courseForumInfo.getSystemCode());
				sql.setString(courseForumInfo.getCurriCode());
				sql.setInteger(courseForumInfo.getCurriYear());
				sql.setInteger(courseForumInfo.getCurriTerm() );
				sql.setString(courseForumInfo.getCourseId() );
				//sql.setInteger(courseForumInfo.getForumId() );
				
				log.debug("[addCourseForumSubTeamInfoAuto]" + sql.toString());
				sqlArray[i] = sql;
		 	 }

		 	retVal = broker.executeUpdate(sqlArray);
		 	 
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}
	

	/**
	 * 토론 정보를 개별 수정한다.
	 * @param courseForumInfo
	 * @param joGubun 이 널일 경우는 조만 수정하는 경우임, 그 외는 "1" 로 보냄
	 * @return
	 * @throws DAOException
	 */
	public int editCourseForumTeamInfo(CourseForumInfoDTO courseForumInfo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update course_forum_info set ");
		 sb.append(" forum_type = ? ,  parent_forum_id = ? , sub_team_no = ? ,  sub_team_name = ? , subject = ? , contents = ? , ");
		 sb.append(" rfile_name = ? , sfile_name = ? , file_path = ? , file_size = ? , ");
		 sb.append(" mod_id = ? , mod_date = ? ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and curri_code = ? ");
		 sb.append(" and curri_year = ? ");
		 sb.append(" and curri_term = ? ");
		 sb.append(" and course_id = ? ");
		 sb.append(" and forum_id = ?");
		 
		 sql.setSql(sb.toString());

		 sql.setString(courseForumInfo.getForumType());
		 sql.setInteger(courseForumInfo.getParentForumId() );
		 sql.setInteger(courseForumInfo.getSubTeamNo() );
		 sql.setString(courseForumInfo.getSubTeamName() );
		 sql.setString(courseForumInfo.getSubject() );
		 sql.setString(courseForumInfo.getContents() );
		 sql.setString(courseForumInfo.getRfileName() );
		 sql.setString(courseForumInfo.getSfileName() );
		 sql.setString(courseForumInfo.getFilePath() );
		 sql.setString(courseForumInfo.getFileSize() );
		 sql.setString(courseForumInfo.getModId() );
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(courseForumInfo.getSystemCode());
		 sql.setString(courseForumInfo.getCurriCode());
		 sql.setInteger(courseForumInfo.getCurriYear());
		 sql.setInteger(courseForumInfo.getCurriTerm());
		 sql.setString(courseForumInfo.getCourseId());
		 sql.setInteger(courseForumInfo.getForumId());


		 log.debug("[editCourseForumTeamInfo]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}
	
	/**
	 * 토론 팀 멤버를 자동 입력한다.
	 * @param courseForumInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCourseForumSubTeamMemberAuto(CourseForumInfoDTO courseForumInfoDto) throws DAOException{
		int  retVal = 0;
		 
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 
		 sb.append(" insert into course_forum_user( ");
		 sb.append(" system_code,  curri_code, curri_year, curri_term, course_id, ");
		 sb.append(" forum_id, user_id, sub_team_no,  "); //score, connect_no,
		 sb.append(" reg_id, reg_date ) ");
		 // select insert
		 sb.append(" select  a.system_code,  a.curri_code, a.curri_year, a.curri_term, a.course_id, ");
		 sb.append(" b.forum_id, a.user_id, a.team_no, ");
		 sb.append(" ?, ?  ");
		 sb.append(" from course_team_member a");
		 sb.append(" , course_forum_info b ");
		 sb.append(" where a.system_code = b.system_code ");
		 sb.append(" and a.curri_code = b.curri_code ");
		 sb.append(" and a.curri_year = b.curri_year ");
		 sb.append(" and a.curri_term = b.curri_term ");
		 sb.append(" and a.course_id  = b.course_id "); 
		 sb.append(" and a.team_no = b.sub_team_no ");
		 sb.append(" and a.system_code = ? ");
		 sb.append(" and a.curri_code = ? ");
		 sb.append(" and a.curri_year = ? ");
		 sb.append(" and a.curri_term = ? ");
		 sb.append(" and a.course_id = ? ");
		 sb.append(" and b.PARENT_FORUM_ID = ? ");
		  
		 try{
		 	
	 	 	sql.setSql(sb.toString());
	 	 	// reg id
	 	 	sql.setString(courseForumInfoDto.getRegId());
			sql.setString(CommonUtil.getCurrentDate());
			// where
			sql.setString(courseForumInfoDto.getSystemCode());
			sql.setString(courseForumInfoDto.getCurriCode());
			sql.setInteger(courseForumInfoDto.getCurriYear());
			sql.setInteger(courseForumInfoDto.getCurriTerm() );
			sql.setString(courseForumInfoDto.getCourseId() );
			sql.setInteger(courseForumInfoDto.getParentForumId() );
			
			log.debug("[addCourseForumSubTeamMemberAuto]" + sql.toString());

		 	 retVal = broker.executeUpdate(sql);
		 	 
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	

	

	/**
	 * 
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @param forumId
	 * @param column
	 * @param orderBy
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCourseForumStudentList(String systemcode, String curricode, int curriyear, int curriterm, String courseid, int parentForumId, String column, String orderBy) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select distinct s.user_id, u.user_name, d.dept_soname ");
		sb.append(" from student s, users u, dept_socode d ");
		sb.append(" where s.system_code=? ");
		sb.append(" and s.curri_code=? ");
		sb.append(" and s.curri_year=? ");
		sb.append(" and s.curri_term=? ");
		sb.append(" and s.enroll_status='S' " );
		sb.append(" and s.system_code=u.system_code ");
		sb.append(" and s.user_id=u.user_id ");
		sb.append(" and u.system_code=d.system_code ");
		sb.append(" and u.dept_daecode=d.dept_daecode ");
		sb.append(" and u.dept_socode=d.dept_socode ");
		sb.append(" and s.user_id not in " );
		sb.append(" ( select m.user_id ");
		sb.append(" from course_forum_user m ");
		sb.append(" where m.system_code = s.system_code ");
		sb.append(" and m.curri_code = s.curri_code ");
		sb.append(" and m.curri_year = s.curri_year ");
		sb.append(" and m.curri_term =s.curri_term ");
		sb.append(" and m.course_id = ? ");
		sb.append(" and m.forum_id  in ( select forum_id from course_forum_info where system_code=? and curri_code=? and curri_year=? and curri_term =? and course_id=?  and parent_forum_id=?) )");
//		sb.append(" ( select m.user_id ");
//		sb.append(" from course_team_member m ");
//		sb.append(" where s.system_code = m.system_code and s.curri_code = m.curri_code ");
//		sb.append(" and s.curri_year=m.curri_year and  s.curri_term=m.curri_term ");
//		sb.append(" and m.course_id=? ) ");
		sb.append(" order by "+column+" "+orderBy);

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setInteger(parentForumId);		


		log.debug("[getCourseForumStudentList]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs	= 	broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}
	
	
	/**
	 * 
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @param teamno
	 * @param column
	 * @param orderBy
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCourseForumTeamMemberList(String systemcode, String curricode, int curriyear, int curriterm, String courseid, int forumId, String column, String orderBy) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select m.user_id, u.user_name, d.dept_soname ");
		sb.append(" from course_forum_user m, users u, dept_socode d ");
		sb.append(" where m.system_code=? ");
		sb.append(" and m.curri_code=? ");
		sb.append(" and m.curri_year=? ");
		sb.append(" and m.curri_term=? ");
		sb.append(" and m.course_id =? ");
		sb.append(" and m.forum_id  =? ");
		//sb.append(" and m.sub_team_no = ? ");
		sb.append(" and m.user_id=u.user_id ");
		sb.append(" and m.system_code=u.system_code ");
		sb.append(" and u.system_code=d.system_code ");
		sb.append(" and u.dept_daecode=d.dept_daecode ");
		sb.append(" and u.dept_socode=d.dept_socode ");
		sb.append(" and m.user_id in " );
		sb.append(" ( select s.user_id ");
		sb.append(" from student s ");
		sb.append(" where s.system_code = m.system_code and s.curri_code = m.curri_code and s.curri_year=m.curri_year ");
		sb.append(" and  s.curri_term=m.curri_term and s.enroll_status='S') ");
		sb.append(" order by "+column+" "+orderBy);

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setInteger(forumId);

		log.debug("[getCourseForumTeamMemberList]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs	= 	broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}
	
	
	/**
	 * 팀 구성원을 삭제한다..
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param teamNo
	 * @return
	 * @throws DAOException
	 */
	public int deleteCourseForumTeamMember(String systemCode, String curriCode, int curriYear, int curriTerm, String courseId, int forumId) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" delete from course_forum_user ");
		sb.append(" where system_code = ? ");
		sb.append(" and curri_code=? ");
		sb.append(" and curri_year =? ");
		sb.append(" and curri_term=? ");
		sb.append(" and course_id = ? ");
		sb.append(" and forum_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(courseId);
		sql.setInteger(forumId);

		log.debug("[deleteCourseForumTeamMember]" + sql.toString());
		int ret = 0;
		try{
			ret	= 	broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return ret;
	}	
	
	
	/**
	 *  팀 구성원을 등록한다
	 * @param coteammemDto
	 * @return
	 * @throws DAOException
	 */
	public boolean addCourseForumTeamMember(CourseForumTeamDTO courseForumTeamDto) throws DAOException{
		boolean  retVal = false;

		ISqlStatement[] sqlArray = new ISqlStatement[courseForumTeamDto.getChkId().length];
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" insert into course_forum_user( ");
		sb.append(" system_code,  curri_code, curri_year, curri_term, course_id, ");
		sb.append(" forum_id, user_id, sub_team_no, score, connect_no, ");
		sb.append(" reg_id, reg_date ) ");
		sb.append(" values( ?, ? ,?, ?, ?, ");
		sb.append(" ?, ?, ?, ?, ?, ");
		sb.append(" ?, ? )");

		for(int i=0; i < courseForumTeamDto.getChkId().length; i++){
			
			sql = new QueryStatement();
			sql.setSql(sb.toString());

			 sql.setString(courseForumTeamDto.getSystemCode());
			 sql.setString(courseForumTeamDto.getCurriCode());
			 sql.setInteger(courseForumTeamDto.getCurriYear());
			 sql.setInteger(courseForumTeamDto.getCurriTerm() );
			 sql.setString(courseForumTeamDto.getCourseId() );
			 sql.setInteger(courseForumTeamDto.getForumId() );
			 sql.setString(courseForumTeamDto.getChkId()[i] );
			 sql.setInteger(courseForumTeamDto.getSubTeamNo() );
			 sql.setInteger(courseForumTeamDto.getScore() );
			 sql.setInteger(courseForumTeamDto.getConnectNo());
			 sql.setString(courseForumTeamDto.getRegId() );
			 sql.setString(CommonUtil.getCurrentDate());
			 
			log.debug("[addCourseForumTeamMember]" + sql.toString());
			sqlArray[i] = sql;
		}

		try{
			retVal	=	broker.executeUpdate(sqlArray);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}
}
