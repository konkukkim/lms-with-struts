package com.edutrack.offlineattend.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.coursebookmark.dao.BookmarkDAO;
import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;

/*
 * @author Bschoi
 *
 * 목차 관리
 */
public class OfflineAttendDAO extends AbstractDAO {


	/**
	 * 
	 * @param curpage
	 * @param listScale
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param LectureGubun
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCurriContentsPagingList(int curpage, int listScale, String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String LectureGubun) throws DAOException {
		ListDTO retVal = null;
		StringBuffer sbWhere = new StringBuffer(); 
		try {
			ListStatement sql = new ListStatement(); 
			sql.setTotalCol("lecture_gubun");
			sql.setCutCol("lecture_gubun");
			sql.setAlias("lecture_gubun, start_date, contents_id ");
			sql.setSelect("lecture_gubun, getDateText(start_date, null) as start_date, contents_id ");
			sql.setFrom(" curri_contents");
			
			sbWhere.append(" system_code = ? ");  
			sbWhere.append("and curri_code = ? ");
			sbWhere.append("and curri_year = ? ");
			sbWhere.append("and curri_term = ? ");
			sbWhere.append("and course_id=? ");
			if(LectureGubun !=null && !("").equals(LectureGubun)){
				sbWhere.append("and lecture_gubun ='"+LectureGubun+"' ");
			}
			sql.setWhere(sbWhere.toString());
			sql.setOrderby(" contents_order");

			sql.setString(SystemCode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(CourseId);
			
			//--- 디버그 출력
			log.debug("[getCurriContentsPagingList]" + sql.toString());
			
			//--- 쿼리실행 및 반환값 설정
			retVal = broker.executeListQuery(sql, curpage, listScale); 
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
	 * 학생들의 오프라인 출석을 카운트 한다.
	 * @param curpage
	 * @param listScale
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param LectureGubun
	 * @return
	 * @throws DAOException
	 */
	public RowSet getOfflineAttendanceCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String pUserId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer(); 
		RowSet rs = null;
		
		try {
			
			sb.append(" select count(contents_id) all_cnt, a.t_cnt, a.m_cnt, a.f_cnt  ");
			sb.append(" from curri_contents x");
//			 inline view .... 전체 offline 컨텐츠 수
			sb.append(" ,( select sum(case a.attendance when 'O' then 1 else 0 end) t_cnt  ");	// true count
			sb.append(" ,sum(case a.attendance when '△' then 1 else 0 end) m_cnt ");	// middle count
			sb.append(" ,sum(case a.attendance when 'X' then 1 else 0 end) f_cnt ");	// false count
			sb.append(" , a.system_code, a.curri_code, a.curri_year, a.curri_term, a.course_id, a.user_id  ");
			sb.append(" from bookmark a");
			sb.append(" where a.system_code=? ");
			sb.append(" and a.curri_code=? ");
			sb.append(" and a.curri_year=? ");
			sb.append(" and a.curri_term=?");
			sb.append(" and a.course_id=? ");
			sb.append(" and a.user_id=?   ");
			sb.append(" group by a.system_code, a.curri_code, a.curri_year, a.curri_term, a.course_id, a.user_id  ) a");
			sb.append(" where a.system_code = x.system_code ");  
			sb.append(" and a.curri_code = x.curri_code ");
			sb.append(" and a.curri_year = x.curri_year ");
			sb.append(" and a.curri_term = x.curri_term ");
			sb.append(" and a.course_id = x.course_id ");
			sb.append(" and x.lecture_gubun ='2' ");	
			sb.append(" group by a.t_cnt, a.m_cnt, a.f_cnt");
			
			sql.setSql(sb.toString());
			
			sql.setString(SystemCode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(CourseId);
			sql.setString(pUserId);
			
			//--- 디버그 출력
			//log.debug("[getOfflineAttendanceCnt]" + sql.toString());
			
			//--- 쿼리실행 및 반환값 설정
			rs = broker.executeQuery(sql); 

		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage()); 
		}catch(Exception e){ 
			log.error(e.getMessage()); 
			throw new DAOException(e.getMessage()); 
		}
		return rs; 			 
		
	}
	
	
	/**
	 * 
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getOffStudentAttendList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm , String CourseId) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select a.user_id ");
		sb.append("  , c.user_name   ");
		sb.append("  , a.attendance  "); 
		sb.append("  , getDateText(b.start_date,null) start_date  ");
		sb.append(" from bookmark a  ");
		sb.append("  , curri_contents b  ");
		sb.append("  ,(select s.system_code ");
		sb.append("    , s.curri_code ");
		sb.append("    , s.curri_year ");
		sb.append("    , s.curri_term ");
		sb.append("    , s.user_id ");
		sb.append("    , u.user_name ");
		sb.append("     from student s ");
		sb.append("        , users u ");
		sb.append("    where s.system_code=u.system_code  ");   
		sb.append("      and s.user_id=u.user_id ");
		sb.append("      and s.enroll_status in ('S','C','F') ) c ");	// 
		sb.append(" where a.system_code=b.system_code ");
		sb.append(" and a.curri_code=b.curri_code ");
		sb.append(" and a.curri_year=b.curri_year ");
		sb.append(" and a.curri_term=b.curri_term ");
		sb.append(" and a.course_id =b.course_id  ");
		sb.append(" and a.contents_id=b.contents_id  ");
		sb.append(" and a.system_code=c.system_code ");
		sb.append(" and a.curri_code=c.curri_code ");
		sb.append(" and a.curri_year=c.curri_year ");
		sb.append(" and a.curri_term=c.curri_term ");
		sb.append(" and a.user_id=c.user_id ");
		sb.append(" and b.contents_type='F' ");
		sb.append(" and b.lecture_gubun='2' ");
		sb.append(" and a.system_code=? ");
		sb.append(" and a.curri_code=? ");
		sb.append(" and a.curri_year=? ");
		sb.append(" and a.curri_term=? ");
		sb.append(" and a.course_id=? ");
		sb.append(" order by c.user_name, a.user_id, b.start_date ");
		
		sql.setSql(sb.toString());

		sql.setString(Systemcode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		
		log.debug("[getOffStudentAttendPagingList]" + sql.toString());
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}
	
	
	/**
	 * bookmark...출석정보를 저장한다.. 
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param stuId
	 * @param attenSt
	 * @param contId
	 * @param regId
	 * @return
	 * @throws DAOException
	 */
	public boolean addOfflineAttendAll(String systemCode, String curriCode,  int curriYear, int curriTerm, String courseId, String[] stuId, String[] attenSt, String contId, String regId) throws DAOException{
		boolean retVal = false;
		QueryStatement sql = new QueryStatement();
		StringBuffer sbUP = new StringBuffer();
		StringBuffer sbIN = new StringBuffer();
		
		ISqlStatement[] stArray = new ISqlStatement[stuId.length];
		BookmarkDAO bookmarkDao = new BookmarkDAO();
		int cnt = 0;
		
		sbIN.append("insert into bookmark(");
		sbIN.append(" system_code, curri_code, curri_year, curri_term, user_id");
		sbIN.append(", course_id, contents_id, contents_order, open_chk, quiz_pass");
		sbIN.append(", score_raw, score_max, score_min, total_time, session_time");
		sbIN.append(", join_count, attendance, reg_id, reg_date, cmi_idref) ");
		sbIN.append("select system_code, curri_code, curri_year, curri_term, ?");
		sbIN.append(", course_id, contents_id, contents_order, 'Y' , case when quiz_count > 0 then 'N' else 'Y' end");
		sbIN.append(", null, null, null, 0, 0");
		sbIN.append(", 1, ?, ?,  CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),null ");
		sbIN.append(" from curri_contents ");
		sbIN.append(" where system_code=? ");
		sbIN.append(" and curri_code=? ");
		sbIN.append(" and curri_year=? ");
		sbIN.append(" and curri_term=? ");
		sbIN.append(" and course_id=? ");
		sbIN.append(" and contents_id=? ");
		
		sbUP.append(" update bookmark ");
		sbUP.append(" set attendance=?");
		sbUP.append(" , mod_id=?");
		sbUP.append(" , mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sbUP.append(" where system_code=? ");
		sbUP.append(" and curri_code = ? ");
		sbUP.append(" and curri_year = ? ");
		sbUP.append(" and curri_term = ? ");
		sbUP.append(" and course_id=? ");
		sbUP.append(" and contents_id=? ");
		sbUP.append(" and user_id=? ");
		
		try{
			//---- 입력된 값을 가져 온다.
			for(int i=0; i<stuId.length;i++){
				sql = new QueryStatement();
				
				cnt = bookmarkDao.isBookmark(systemCode, stuId[i], curriCode, curriYear, curriTerm, courseId, contId);
				
				if(cnt==0){
					
					// insert
					sql.setSql(sbIN.toString());
					
					sql.setString(stuId[i]);
					sql.setString(attenSt[i]);
					sql.setString(regId);
					//where
					sql.setString(systemCode);
					sql.setString(curriCode);
					sql.setInteger(curriYear);
					sql.setInteger(curriTerm);
					sql.setString(courseId);
					sql.setString(contId);
					
					stArray[i] = sql;
				
				}else if(cnt>0){
					
					// update
					sql.setSql(sbUP.toString());
					
					sql.setString(attenSt[i]);
					sql.setString(regId);
					//where
					sql.setString(systemCode);
					sql.setString(curriCode);
					sql.setInteger(curriYear);
					sql.setInteger(curriTerm);
					sql.setString(courseId);
					sql.setString(contId);
					sql.setString(stuId[i]);
					
					stArray[i] = sql;				
				}
				log.debug("[addOfflineAttendAll]" + sql.toString());
			}
			
			
			retVal = broker.executeUpdate(stArray);
			
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	
	
}