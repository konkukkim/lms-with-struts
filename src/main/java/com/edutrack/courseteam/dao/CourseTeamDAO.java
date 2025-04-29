/*
 * Created on 2004. 10. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseteam.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.courseteam.dto.CourseTeamMemberDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseTeamDAO extends AbstractDAO {
	/**
	 * 팀관리 리스트를  가져온다.
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @param orderBy , desc ==> null, asc ==> order by ~~~
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCourseTeamList(String systemcode, String curricode, int curriyear, int curriterm, String courseid, String orderBy ) throws DAOException{
		ArrayList arrayList	=	new ArrayList();
		CourseTeamInfoDTO courseTeamInfoDto = new CourseTeamInfoDTO();
		
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		ResultSet rs = null;
		
		sb.append(" select team_no, team_name, reg_id, reg_date, ");
		sb.append(" ( select  count(*) from course_team_member b where ");
		sb.append(" a.system_code=b.system_code and a.curri_code=b.curri_code and " );
		sb.append(" a.curri_year=b.curri_year and a.curri_term=b.curri_term and ");
		sb.append(" a.course_id=b.course_id and a.team_no=b.team_no and b.user_id in ");
		sb.append(" ( select s.user_id from student s ");
		sb.append(" where s.system_code = b.system_code and s.curri_code = b.curri_code ");
		sb.append(" and b.curri_year=b.curri_year and  s.curri_term=b.curri_term ");
		sb.append(" and s.enroll_status='S')) as team_cnt");
		sb.append(" from course_team_info a ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term =? ");
		sb.append(" and course_id= ? ");
		
		if(orderBy!=null){
			sb.append( orderBy );
		}else{
			sb.append(" order by team_no desc ");
		}
		
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);

		log.debug("[getCourseTeamList]" + sql.toString());
		
		try{
			rs	= 	broker.executeQuery(sql);
			
			while(rs.next()) {
				courseTeamInfoDto	=	new CourseTeamInfoDTO();
				courseTeamInfoDto.setTeamNo(rs.getInt("team_no"));
				courseTeamInfoDto.setTeamName(StringUtil.nvl(rs.getString("team_name")));
				courseTeamInfoDto.setTeamCnt(StringUtil.nvl(rs.getString("team_cnt"),0));

				arrayList.add(courseTeamInfoDto);
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
		return arrayList;
	}

	/**
	 * 팀관리 정보를  가져온다.
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @param teamno
	 * @return
	 * @throws DAOException
	 */
	public CourseTeamInfoDTO getCourseTeam(String systemcode, String curricode, int curriyear, int curriterm, String courseid, int teamno) throws DAOException{
		CourseTeamInfoDTO courseteamDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select team_name ");
		sb.append(" from course_team_info  ");
		sb.append(" where system_code = ? and curri_code=? and curri_year=? and curri_term = ?");
		sb.append(" and course_id=? and team_no=? ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setInteger(teamno);

		log.debug("[getCourseTeam]" + sql.toString());
		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				courseteamDto 		= 	new CourseTeamInfoDTO();
				courseteamDto.setTeamName(StringUtil.nvl(rs.getString("team_name")));
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
		return courseteamDto;
	}

	/**
	 * 팀번호 최대값을 가져온다
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @return
	 * @throws DAOException
	 */
	public int getMaxTeamNo(String systemcode, String curricode, int curriyear, int curriterm, String courseid) throws DAOException{
	 int  maxNo = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select IFNULL(max(team_no),0)+1 as team_no ");
	 sb.append(" from course_team_info ");
	 sb.append(" where system_code = ? and curri_code=? and curri_year =? and curri_term=? ");
	 sb.append(" and course_id = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setString(curricode);
	 sql.setInteger(curriyear);
	 sql.setInteger(curriterm);
	 sql.setString(courseid);

	 log.debug("[getMaxTeamNo]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	maxNo = rs.getInt("team_no");
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

	 return maxNo;
	}

	/**
	 * 팀을 입력한다.
	 * @param courseteam
	 * @return
	 * @throws DAOException
	 */
	public int addCourseTeam(CourseTeamInfoDTO courseteam) throws DAOException{
		 int retVal = 0;

		 //팀번호 최대값
		 int team_no = getMaxTeamNo(courseteam.getSystemCode(), courseteam.getCurriCode(), courseteam.getCurriYear(), courseteam.getCurriTerm(), courseteam.getCourseId());

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into course_team_info ");
		 sb.append(" (system_code, curri_code, curri_year, curri_term, course_id, team_no, ");
		 sb.append(" team_name, reg_id, reg_date)");
		 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ) ");
		 sql.setSql(sb.toString());

		 sql.setString(courseteam.getSystemCode());
		 sql.setString(courseteam.getCurriCode());
		 sql.setInteger(courseteam.getCurriYear());
		 sql.setInteger(courseteam.getCurriTerm());
		 sql.setString(courseteam.getCourseId());
		 sql.setInteger(team_no);
		 sql.setString(courseteam.getTeamName());
		 sql.setString(courseteam.getRegId());

		 log.debug("[addCourseTeam]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 팀을 수정한다.
	 * @param courseteam
	 * @return
	 * @throws DAOException
	 */
	public int editCourseTeam(CourseTeamInfoDTO courseteam) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update course_team_info ");
		 sb.append(" set team_name= ?, mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code=? and curri_year =? and curri_term=? ");
		 sb.append(" and course_id = ? and team_no = ? ");

		 sql.setSql(sb.toString());
		 sql.setString(courseteam.getTeamName());
		 sql.setString(courseteam.getModId());
		 sql.setString(courseteam.getSystemCode());
		 sql.setString(courseteam.getCurriCode());
		 sql.setInteger(courseteam.getCurriYear());
		 sql.setInteger(courseteam.getCurriTerm());
		 sql.setString(courseteam.getCourseId());
		 sql.setInteger(courseteam.getTeamNo());

		 log.debug("[editCourseTeam]" + sql.toString());
		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 팀을 삭제한다.
	 * @param courseteam
	 * @return
	 * @throws DAOException
	 */
	public int delCourseTeam(CourseTeamInfoDTO courseteam) throws DAOException{
		 boolean retVal = false;
		 int retVal2 = 0;

		 //팀구성원 삭제
		 QueryStatement sql1 = new QueryStatement();
		 StringBuffer sb1 = new StringBuffer();
		 sb1.append(" delete from course_team_member ");
		 sb1.append(" where system_code = ? and curri_code=? and curri_year =? and curri_term=? ");
		 sb1.append(" and course_id = ? and team_no = ? ");
		 sql1.setSql(sb1.toString());
		 sql1.setString(courseteam.getSystemCode());
		 sql1.setString(courseteam.getCurriCode());
		 sql1.setInteger(courseteam.getCurriYear());
		 sql1.setInteger(courseteam.getCurriTerm());
		 sql1.setString(courseteam.getCourseId());
		 sql1.setInteger(courseteam.getTeamNo());

		 //팀구성정보 삭제
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 sb2.append(" delete from course_team_info ");
		 sb2.append(" where system_code = ? and curri_code=? and curri_year =? and curri_term=? ");
		 sb2.append(" and course_id = ? and team_no = ? ");
		 sql2.setSql(sb2.toString());
		 sql2.setString(courseteam.getSystemCode());
		 sql2.setString(courseteam.getCurriCode());
		 sql2.setInteger(courseteam.getCurriYear());
		 sql2.setInteger(courseteam.getCurriTerm());
		 sql2.setString(courseteam.getCourseId());
		 sql2.setInteger(courseteam.getTeamNo());

		 ISqlStatement[] sqlArray = new ISqlStatement[2];

		 sqlArray[0] = sql1;
		 sqlArray[1] = sql2;


		 log.debug("[member_delete]" + sql1.toString());
		 log.debug("[info_delete]" + sql2.toString());

		 try{
			retVal = broker.executeUpdate(sqlArray);

			/**
			 * 반환값을 1, 0으로 성공(1), 실패(0)를 판단한다.
			 * DAO에 있는 반환값을 int로 하기때문에 아래와 같이 변환한다.
			 */
			if(retVal)
				retVal2 = 1;
			else
				retVal2 = 0;
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal2;
	}


	/**
	 * 과정을 수강하고 있는 학생 리스트를  가져온다.(해당과목-팀에 속한 학생 제외)
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCurriStudentList(String systemcode, String curricode, int curriyear, int curriterm, String courseid) throws DAOException{
		ArrayList teamStudentList		=	null;
		CourseTeamMemberDTO coteammemDto	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select distinct s.user_id, u.user_name, d.dept_soname ");
		sb.append(" from student s, users u, dept_socode d ");
		sb.append(" where s.system_code=? and s.curri_code=? and s.curri_year=? ");
		sb.append(" and s.curri_term=? and s.enroll_status='S' " );
		sb.append(" and s.system_code=u.system_code and s.user_id=u.user_id ");
		sb.append(" and u.system_code=d.system_code and u.dept_daecode=d.dept_daecode ");
		sb.append(" and u.dept_socode=d.dept_socode and s.user_id not in " );
		sb.append(" ( select m.user_id ");
		sb.append(" from course_team_member m ");
		sb.append(" where s.system_code = m.system_code and s.curri_code = m.curri_code ");
		sb.append(" and s.curri_year=m.curri_year and  s.curri_term=m.curri_term ");
		sb.append(" and m.course_id=? ) ");
		sb.append(" order by s.user_id asc ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);

		log.debug("[getCurriStudentList]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs 					= 	broker.executeQuery(sql);

			teamStudentList		= 	new ArrayList();
			while(rs.next()){
				coteammemDto	= 	new CourseTeamMemberDTO();
				coteammemDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				coteammemDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				coteammemDto.setDeptSoName(StringUtil.nvl(rs.getString("dept_soname")));
				teamStudentList.add(coteammemDto);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return teamStudentList;
	}

	public RowSet getCurriStudentList(String systemcode, String curricode, int curriyear, int curriterm, String courseid, String column, String orderBy) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select distinct s.user_id, u.user_name, d.dept_soname ");
		sb.append(" from student s, users u, dept_socode d ");
		sb.append(" where s.system_code=? and s.curri_code=? and s.curri_year=? ");
		sb.append(" and s.curri_term=? and s.enroll_status='S' " );
		sb.append(" and s.system_code=u.system_code and s.user_id=u.user_id ");
		sb.append(" and u.system_code=d.system_code and u.dept_daecode=d.dept_daecode ");
		sb.append(" and u.dept_socode=d.dept_socode and s.user_id not in " );
		sb.append(" ( select m.user_id ");
		sb.append(" from course_team_member m ");
		sb.append(" where s.system_code = m.system_code and s.curri_code = m.curri_code ");
		sb.append(" and s.curri_year=m.curri_year and  s.curri_term=m.curri_term ");
		sb.append(" and m.course_id=? ) ");
		sb.append(" order by "+column+" "+orderBy);

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);

		log.debug("[getCurriStudentList]" + sql.toString());
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
	 * 과목에 등록된 팀구성 리스트를  가져온다.
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @param courseid
	 * @param teamno
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCourseTeamMemberList(String systemcode, String curricode, int curriyear, int curriterm, String courseid, int teamno) throws DAOException{
		ArrayList courseTeamMemList		=	null;
		CourseTeamMemberDTO coteammemDto	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select m.user_id, u.user_name, d.dept_soname ");
		sb.append(" from course_team_member m, users u, dept_socode d ");
		sb.append(" where m.system_code=? and m.curri_code=? and m.curri_year=? and m.curri_term=? ");
		sb.append(" and  m.course_id=? and  m.team_no = ?	 " );
		sb.append(" and m.user_id=u.user_id and m.system_code=u.system_code  ");
		sb.append(" and u.system_code=d.system_code and u.dept_daecode=d.dept_daecode  ");
		sb.append(" and u.dept_socode=d.dept_socode and m.user_id in " );
		sb.append(" ( select s.user_id ");
		sb.append(" from student s ");
		sb.append(" where s.system_code = m.system_code and s.curri_code = m.curri_code and s.curri_year=m.curri_year ");
		sb.append(" and  s.curri_term=m.curri_term and s.enroll_status='S') ");
		sb.append(" order by m.user_id asc ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		sql.setString(courseid);
		sql.setInteger(teamno);

		log.debug("[getCourseTeamMemberList]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs 					= 	broker.executeQuery(sql);

			courseTeamMemList		= 	new ArrayList();
			while(rs.next()){
				coteammemDto	= 	new CourseTeamMemberDTO();
				coteammemDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				coteammemDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				coteammemDto.setDeptSoName(StringUtil.nvl(rs.getString("dept_soname")));
				courseTeamMemList.add(coteammemDto);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return courseTeamMemList;
	}

	public RowSet getCourseTeamMemberList(String systemcode, String curricode, int curriyear, int curriterm, String courseid, int teamno, String column, String orderBy) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select m.user_id, u.user_name, d.dept_soname ");
		sb.append(" from course_team_member m, users u, dept_socode d ");
		sb.append(" where m.system_code=? and m.curri_code=? and m.curri_year=? and m.curri_term=? ");
		sb.append(" and  m.course_id=? and  m.team_no = ?	 " );
		sb.append(" and m.user_id=u.user_id and m.system_code=u.system_code  ");
		sb.append(" and u.system_code=d.system_code and u.dept_daecode=d.dept_daecode  ");
		sb.append(" and u.dept_socode=d.dept_socode and m.user_id in " );
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
		sql.setInteger(teamno);

		log.debug("[getCourseTeamMemberList]" + sql.toString());
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
	 *  팀 구성원을 등록한다
	 * @param coteammemDto
	 * @return
	 * @throws DAOException
	 */
	public boolean addCourseTeamMember(CourseTeamMemberDTO courseTeamMemberDto) throws DAOException{
		boolean  retVal = false;

		ISqlStatement[] sqlArray = new ISqlStatement[courseTeamMemberDto.getChkId().length];
		QueryStatement sql = null;
		StringBuffer sb = null;
		for(int i=0; i < courseTeamMemberDto.getChkId().length; i++){
			sql = new QueryStatement();
			sb = new StringBuffer();
			sb.append(" insert into course_team_member ");
			sb.append(" (system_code, curri_code, curri_year, curri_term, course_id, team_no, ");
			sb.append(" user_id, reg_id, reg_date)");
			sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ) ");
			sql.setSql(sb.toString());
			sql.setString(courseTeamMemberDto.getSystemCode());
			sql.setString(courseTeamMemberDto.getCurriCode());
			sql.setInteger(courseTeamMemberDto.getCurriYear());
			sql.setInteger(courseTeamMemberDto.getCurriTerm());
			sql.setString(courseTeamMemberDto.getCourseId());
			sql.setInteger(courseTeamMemberDto.getTeamNo());
			sql.setString(courseTeamMemberDto.getChkId()[i]);
			sql.setString(courseTeamMemberDto.getRegId());
			log.debug("[addCourseTeamMember]" + sql.toString());
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

	public int deleteCourseTeamMember(String systemCode, String curriCode, int curriYear, int curriTerm, String courseId, int teamNo) throws DAOException{

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" delete from course_team_member ");
		sb.append(" where system_code = ? and curri_code=? and curri_year =? and curri_term=? and course_id = ? and team_no = ?");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(courseId);
		sql.setInteger(teamNo);

		log.debug("[deleteCourseTeamMember]" + sql.toString());
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
	 *  팀 구성원을 삭제한다
	 * @param coteammemDto
	 * @return
	 * @throws DAOException
	 */
	public boolean delCourseTeamMember(CourseTeamMemberDTO coteammem) throws DAOException{
		boolean  retVal = false;
		 ISqlStatement[] sqlArray = new ISqlStatement[coteammem.getChkId().length];
		 QueryStatement sql = null;
		 StringBuffer sb = null;

		for(int i=0; i < coteammem.getChkId().length; i++){
			sql = new QueryStatement();
			sb = new StringBuffer();
			sb.append(" delete from course_team_member ");
			sb.append(" where system_code = ? and curri_code=? and curri_year =? and curri_term=? ");
			sb.append(" and course_id = ? and team_no = ? and user_id=?");
			sql.setSql(sb.toString());
			sql.setString(coteammem.getSystemCode());
			sql.setString(coteammem.getCurriCode());
			sql.setInteger(coteammem.getCurriYear());
			sql.setInteger(coteammem.getCurriTerm());
			sql.setString(coteammem.getCourseId());
			sql.setInteger(coteammem.getTeamNo());
			sql.setString(coteammem.getChkId()[i]);
			log.debug("[delCourseTeamMember]" + sql.toString());
			sqlArray[i] = sql;
		}

		try{
			retVal				=	broker.executeUpdate(sqlArray);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

}
