package com.edutrack.curritop.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.curritop.dto.CurriTopCourseDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;



/*
 * @author JamFam
 * 
 * 과정 과목 연결 관리
 */
public class CurriTopCourseDAO extends AbstractDAO {
	
	/**
	 * 
	 */
	public CurriTopCourseDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 과목연결 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @param CurriCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CurriCode) throws DAOException {
		int totCount = 0;
		
		 StringBuffer sb = new StringBuffer();
		 sb.append("select ifnull(count(course_id),0) as cnt from curri_top_course where system_code= ? and curri_code=?");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 
		
		 log.debug("[getTotCount]" + sql.toString());
		 
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
	 * 과목연결 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @param CurriCode
	 * @param Target
	 * @param Word
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCurriTopCourseList(int curpage, String SystemCode, String CurriCode, String Target, String Word) throws DAOException {
		ListDTO retVal = null;
		try{
			String addWhere = "";
			if(!Target.equals("") && !Word.equals("")) addWhere = " and "+Target+" like ?";
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("a.course_id");
			sql.setCutCol("a.course_id");
			sql.setAlias("course_id, course_name, prof_id, prof_name, reg_date");
			sql.setSelect("a.course_id, b.course_name, a.prof_id, c.user_name as prof_name, concat(substring(a.reg_date,1,4),'-',substring(a.reg_date,5,2),'-',substring(a.reg_date,7,2)) as reg_date");
			sql.setFrom(" curri_top_course a, course b, users c");
			sql.setWhere(" a.system_code=b.system_code and a.system_code=b.system_code and a.system_code=? and a.curri_code=? and a.course_id=b.course_id and a.prof_id=c.user_id"+addWhere);
			sql.setString(SystemCode);
			sql.setString(CurriCode);
			if(!Target.equals("") && !Word.equals("")) sql.setString("%"+Word+"%");
			sql.setOrderby(" a.course_id asc");
			//---- 디버그 출력
			log.debug("[getCurriTopCourseList]" + sql.toString());
			
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
	
	/**
	 * 과목연결 정보를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriTopCourseInfo(String SystemCode, String CurriCode, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT " +
					"a.system_code, a.curri_code, a.course_type, a.online_yn" +
					", b.curri_name, a.course_id, c.course_name, a.prof_id" +
					", a.exam_base, a.report_base, a.attend_base, a.forum_base"+
					", a.etc1_base, a.etc2_base, a.reg_id, a.reg_date" +
					", b.curri_property2 AS curri_type ");
		sb.append(" FROM curri_top_course a, curri_top b, course c" );
		sb.append(" WHERE a.system_code=b.system_code and a.system_code=c.system_code " +
					" and a.curri_code=b.curri_code and a.course_id=c.course_id " +
					" and a.system_code=? and a.curri_code=? " +
					" and a.course_id=? ");
		
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setString(CourseId);
		
		//---- 디버그 출력
		log.debug("[getCurriTopCourseInfo]" + sql.toString());
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
	 * 과목 연결 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCurriTopCourseInfo(CurriTopCourseDTO curriTopCourseInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO curri_top_course(system_code, curri_code, course_id, course_type, online_yn, prof_id, exam_base"+
				", report_base, attend_base, forum_base, etc1_base, etc2_base, reg_id, reg_date, mod_id, mod_date)");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());
		
		//---- 입력된 값을 가져 온다.
		sql.setString(curriTopCourseInfo.getSystemCode());
		sql.setString(curriTopCourseInfo.getCurriCode());
		sql.setString(curriTopCourseInfo.getCourseId());
		sql.setString(curriTopCourseInfo.getCourseType());
		sql.setString(curriTopCourseInfo.getOnlineYn());
		sql.setString(curriTopCourseInfo.getProfId());
		sql.setInteger(curriTopCourseInfo.getExamBase());
		sql.setInteger(curriTopCourseInfo.getReportBase());
		sql.setInteger(curriTopCourseInfo.getAttendBase());
		sql.setInteger(curriTopCourseInfo.getForumBase());
		sql.setInteger(curriTopCourseInfo.getEtc1Base());
		sql.setInteger(curriTopCourseInfo.getEtc2Base());
		sql.setString(curriTopCourseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriTopCourseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		
		//---- 디버그 출력
		log.debug("[addCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}

	/**
	 * 과목 연결 정보를 수정한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCurriTopCourseInfo(CurriTopCourseDTO curriTopCourseInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE curri_top_course SET " +
							" course_type=?, online_yn=?" +
							", prof_id=?, exam_base=?" +
							", report_base=?, attend_base=?"+
							", forum_base=?, etc1_base=?" +
							", etc2_base=?, mod_id=?" +
							", mod_date=?");
		sb.append(" WHERE system_code=? AND curri_code=? AND course_id=?");
		sql.setSql(sb.toString());
		
		//---- 입력된 값을 가져 온다.
		sql.setString(curriTopCourseInfo.getCourseType());
		sql.setString(curriTopCourseInfo.getOnlineYn());
		sql.setString(curriTopCourseInfo.getProfId());
		sql.setInteger(curriTopCourseInfo.getExamBase());
		sql.setInteger(curriTopCourseInfo.getReportBase());
		sql.setInteger(curriTopCourseInfo.getAttendBase());
		sql.setInteger(curriTopCourseInfo.getForumBase());
		sql.setInteger(curriTopCourseInfo.getEtc1Base());
		sql.setInteger(curriTopCourseInfo.getEtc2Base());
		sql.setString(curriTopCourseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriTopCourseInfo.getSystemCode());
		sql.setString(curriTopCourseInfo.getCurriCode());
		sql.setString(curriTopCourseInfo.getCourseId());		
	
		//---- 디버그 출력
		log.debug("[editCurriTopCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}

	/**
	 * 과목 연결 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int delCurriTopCourseInfo(String SystemCode, String CurriCode, String CourseId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_top_course where system_code=? and curri_code=? and course_id=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setString(CourseId);	
		
		//---- 디버그 출력
		log.debug("[delCurriTopCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}		
}