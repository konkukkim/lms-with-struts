/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author bschoi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamInfoDAO extends AbstractDAO {

	/**
	 * 
	 */
	public ExamInfoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 등록된 시험의 수를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param courseid
	 * @return
	 * @throws DAOException
	 */
	public int getExamCnt(String systemcode, CurriMemDTO curriinfo,String courseid,String Where) throws DAOException{
		 int examCnt = 0;
		 QueryStatement sql 	= 	new QueryStatement();
			
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(exam_id),0) as exam_cnt ");
		 sb.append(" from exam_info ");
		 sb.append(" where system_code= ? and curri_code= ? and curri_year= ? and curri_term= ? ");
		 if(!courseid.equals("")) sb.append(" and course_id = ?");
		 if(!Where.equals("")) 
		 	sb.append(Where);
		 sql.setSql(sb.toString());		 
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 if(!courseid.equals(""))  sql.setString(courseid);
		 		
		 log.debug("[getExamCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	examCnt = rs.getInt("exam_cnt");
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
		 
		 return examCnt;   
	}
	

	/**
	 * 시험 응시정보(과목메인)
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @param NowDate
	 * @return RowSet
	 * @throws DAOException
	 */                                                   
	public ArrayList getMainExamList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String UserId, String NowDate) throws DAOException{
	 ArrayList examList =  new ArrayList();

 	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 
	 sb.append(" select ");
	 sb.append(" i.system_code, i.curri_code, i.curri_year, i.curri_term, i.course_id,  i.exam_id, i.start_date, i.end_date, ");
	 sb.append(" i.subject, ifnull(a.user_id,'n') as user_id ");
	 sb.append(" from exam_info i  left outer join  exam_answer a ");
	 sb.append(" on i.system_code = a.system_code and i.curri_code = a.curri_code and i.curri_year=a.curri_year and i.curri_term=a.curri_term ");
	 sb.append(" and i.course_id=a.course_id and i.exam_id = a.exam_id  and a.user_id= ? ");
	 sb.append(" where  i.system_code = ?  and i.curri_code = ?  and i.curri_year = ?  and i.curri_term = ? ");
	 sb.append(" and i.flag_use='Y'  ");
//	 sb.append(" and i.flag_use='y' and  i.end_date >=  ?  ");
	 sql.setSql(sb.toString());
	 sql.setString(UserId);
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 //sql.setString(NowDate);
	 
	 log.debug("[getMainExamList]" + sql.toString());
	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);		 
		 ExamInfoDTO examinfo = null;
	
		 while(rs.next()){
		 	examinfo = new ExamInfoDTO();
		 	examinfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
		 	examinfo.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
		 	examinfo.setCurriYear(rs.getInt("curri_year"));
		 	examinfo.setCurriTerm(rs.getInt("curri_term"));
		 	examinfo.setCourseId(StringUtil.nvl(rs.getString("course_id")));
		 	examinfo.setExamId(rs.getInt("exam_id"));
		 	examinfo.setSubject(StringUtil.nvl(rs.getString("subject")));
		 	examinfo.setRegId(StringUtil.nvl(rs.getString("user_id")));
		 	examinfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
		 	examinfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
		 	examList.add(examinfo);
		 }		 
		 rs.close();
		 
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }
	 return examList;   
	}

}
