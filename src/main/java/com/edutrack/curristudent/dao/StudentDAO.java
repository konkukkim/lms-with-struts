package com.edutrack.curristudent.dao;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;



/*
 * @author JamFam
 *
 * 수강생 관리
 */
public class StudentDAO extends AbstractDAO {
	
	/**
	 * 수강신청 화면에서 수강신청여부의 카운트
	 * @param systemCode
	 * @param paramMap
	 * @return
	 */
	public int getStuCurriCount(String systemCode, Map paramMap) {
		int		retVal		=	0;
		String	pUserId		=	StringUtil.nvl(paramMap.get("pUserId"));
		String	pSchoolYear	=	StringUtil.nvl(paramMap.get("pSchoolYear"));
		String	curriDate	=	"CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		
		QueryStatement 	sql	=	new QueryStatement();
		StringBuffer	sb	=	new StringBuffer();
		sb.append(	" SELECT COUNT(*) as curri_cnt ");
		sb.append(	" FROM curri_top ct, curri_sub cs, student s ");
		sb.append(	" WHERE cs.system_code = ct.system_code AND cs.curri_code = ct.curri_code " +
					" AND s.system_code = cs.system_code AND s.curri_code = cs.curri_code " +
					" AND s.curri_year = cs.curri_year AND s.curri_term = cs.curri_term " +
					" AND cs.enroll_start <= "+curriDate+" AND cs.enroll_end >= "+curriDate+
					" AND ct.system_code = ? AND s.curri_year = CAST(DATE_FORMAT(NOW(),'%Y') AS CHAR) " +
					" AND ct.pare_code2 = ? AND s.user_id = ?   ");
		
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(pSchoolYear);
		sql.setString(pUserId);
		
		log.debug(" [getStuCurriCount] "+sql.toString());
		
		RowSet	rs	=	null;
		try {
			rs	=	broker.executeQuery(sql);
			while(rs.next()) {
				retVal	=	rs.getInt("curri_cnt");
			}			
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return retVal;
	}
	
	/**
	 * 수강생의 enroll_no 를 구하기 위해 최대 enroll_no + 1 값을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param kb
	 * @return
	 * @throws DAOException
	 */
	public int getEnrollNo(String systemCode, String userId, String curriCode, int curriYear, int curriTerm) throws  DAOException  {
		log.debug("-------------------- getEnrollNo Start ------------------");
		int no = 1;
		QueryStatement sql = new QueryStatement();
		String qry = "select case when max(enroll_no) is null then 0 else  max(enroll_no) end + 1  as enroll_no from student "+
			" where system_code=? and user_id=? and curri_code=? and curri_year=? and curri_term=? ";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(userId);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		log.debug("qyr : "+qry);
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			rs.next();
			no = rs.getInt("enroll_no");
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		log.debug("-------------------- getEnrollNo End   ------------------");
		return no;
	}

	/**
	 * 수강생의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String kb) throws DAOException {
		int totCount = 0;
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		if(!CurriCode.equals("")) {
			addWhere += " and curri_code = ?";
		}
		if(CurriYear > 0 && CurriTerm > 0) {
			addWhere += " and curri_year = ? and curri_term=?";
		}

		if(!kb.equals("")){
		    addWhere += " and enroll_status in ('S', 'C','F')";
		}
		sb.append("select count(user_id) as cnt from student  where system_code= ?  "+addWhere);// and curri_code=? and curri_year=? and curri_term=?");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!CurriCode.equals("")) sql.setString(CurriCode);
		if(CurriYear > 0 && CurriTerm > 0) {
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
		}

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
	 * 수강생 리스트를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @return RowSet
	 * @throws DAOException
	 */
	public ListDTO getStudentList(int curpage, String systemCode, String curriCode, int curriYear, int curriTerm, String pSTarget, String pSWord, String Mode) throws DAOException {

		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			String addWhere = "";
			String modeWhere = "";
			if(!curriCode.equals("")) {
				addWhere += " and a.curri_code = ?";
			}
			if(curriYear > 0 && curriTerm > 0) {
				addWhere += " and a.curri_year = ? and a.curri_term=?";
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) {
				addWhere += " and "+pSTarget+" like ?";
			}
			if(Mode.equals("enroll")) {
				//modeWhere = " and a.enroll_status in ('E','N')"; //-- N 은 수강 취소자
				modeWhere = " and a.enroll_status in ('E')";
			} else if(Mode.equals("confirm")) {
				modeWhere = " and a.enroll_status ='S'";
			} else if(Mode.equals("complete")){
				modeWhere = " and a.enroll_status ='C'";
			} else if(Mode.equals("confirmPass")){//-- 수료작업 진행용  S = 수강중, C = 수료처리 , F = 미수료 처리
			    modeWhere = " and a.enroll_status in ('S', 'C', 'F')";
			}
			sql.setTotalCol("a.user_id");
			sql.setCutCol(" concat(a.curri_code,cast(a.curri_year AS CHAR),cast(a.curri_term AS CHAR),a.user_id) ");
			sql.setAlias("curri_code, curri_year, curri_term, user_id, user_name, curri_name, stu_curri_name,  reg_date, enroll_status, enroll_status_name, enroll_no ");
			sql.setSelect("a.curri_code, a.curri_year, a.curri_term, a.user_id, b.user_name, c.curri_name, concat( '[',cast(a.curri_year AS CHAR),'년도 ',cast(a.curri_term AS CHAR),'기 ] ',c.curri_name ) as stu_curri_name,  "+CommonUtil.getDbDateFormat("a.reg_date")+" as reg_date"+
			", enroll_status, case enroll_status when 'E' then '대기' when 'S' then '수강' when 'N' then '취소' when 'C' then '수료' when 'F' then '미수료' end as enroll_status_name, a.enroll_no ");

			sql.setFrom(" student a, users b, curri_top c");
			sql.setWhere(" a.system_code=b.system_code and a.system_code=c.system_code"+
			" and a.user_id = b.user_id and a.curri_code = c.curri_code and a.system_code=? "+modeWhere+addWhere);
			sql.setString(systemCode);
			if(!curriCode.equals("")) sql.setString(curriCode);
			if(curriYear > 0 && curriTerm > 0) {
				sql.setInteger(curriYear);
				sql.setInteger(curriTerm);
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) sql.setString("%"+pSWord+"%");
			sql.setOrderby(" a.reg_date desc");
//			sql.setOrderby(" b.user_name asc");

			//---- 디버그 출력
			log.debug("[getStudentList]" + sql.toString());

			//---- 쿼리실행 및 반환값 설정
//			int DispLine=3;
//			int DispPage = 10;
//			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);
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
	 * 수강생 목록 엑셀 다운...
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param pSTarget
	 * @param pSWord
	 * @param Mode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getStudentListAll(String systemCode, String curriCode, int curriYear, int curriTerm, String pSTarget, String pSWord, String Mode) throws DAOException {

		RowSet rs = null;
		ArrayList arrList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		StudentDTO studentDto = null;
		
		QueryStatement sql = new QueryStatement();
		String addWhere = "";
		String modeWhere = "";

		try{
			
			if(!curriCode.equals("")) {
				addWhere += " and a.curri_code = ?";
			}
			if(curriYear > 0 && curriTerm > 0) {
				addWhere += " and a.curri_year = ? and a.curri_term=?";
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) {
				addWhere += " and "+pSTarget+" like '%"+pSWord+"%' ";
			}
			if(Mode.equals("enroll")) {
				modeWhere = " and a.enroll_status in ('E')";
			} else if(Mode.equals("confirm")) {
				modeWhere = " and a.enroll_status ='S'";
			} else if(Mode.equals("complete")){
				modeWhere = " and a.enroll_status ='C'";
			} else if(Mode.equals("confirmPass")){//-- 수료작업 진행용  S = 수강중, C = 수료처리 , F = 미수료 처리
			    modeWhere = " and a.enroll_status in ('S', 'C', 'F')";
			}
			sb.append("select a.curri_code, a.curri_year, a.curri_term, a.user_id, b.user_name, c.curri_name, concat( '[',cast(a.curri_year AS CHAR),'년도 ',cast(a.curri_term AS CHAR),'기 ] ',c.curri_name ) as stu_curri_name,  "+CommonUtil.getDbDateFormat("a.reg_date")+" as reg_date"+
			", enroll_status, case enroll_status when 'E' then '대기' when 'S' then '수강' when 'N' then '취소' when 'C' then '수료' when 'F' then '미수료' end as enroll_status_name, a.enroll_no ");

			sb.append(" from student a, users b, curri_top c");
			sb.append(" where a.system_code=b.system_code and a.system_code=c.system_code"+
			" and a.user_id = b.user_id and a.curri_code = c.curri_code and a.system_code=? "+modeWhere+addWhere);
			//if(!pSTarget.equals("") && !pSWord.equals("")) sb.append("%"+pSWord+"% ");
			sb.append(" Order by a.reg_date ");
			
			sql.setSql(sb.toString());
			
			sql.setString(systemCode);
			if(!curriCode.equals("")) sql.setString(curriCode);
			if(curriYear > 0 && curriTerm > 0) {
				sql.setInteger(curriYear);
				sql.setInteger(curriTerm);
			}
			
			//---- 디버그 출력
log.debug("[getStudentListAll]" + sql.toString());
			rs = broker.executeQuery(sql);

			while(rs.next()){
				studentDto = new StudentDTO();
				studentDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				studentDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				studentDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				studentDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				studentDto.setCurriYear(rs.getInt("curri_year"));
				studentDto.setCurriTerm(rs.getInt("curri_term"));	
				studentDto.setEnrollNo(rs.getInt("enroll_no"));
				studentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			
				arrList.add(studentDto);
			}
			
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally{
			try{ if(rs!=null) rs.close(); } 
			catch(Exception ae){throw new DAOException(ae.getMessage());};
		}
		return arrList;
	}
	
	
	/**
	 * 수료처리를 위한 강의 종료된 정규과정의 수강생 점수와 가져오기.
	 * @param curpage
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param pSTarget
	 * @param pSWord
	 * @param Mode
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCompleteStudentList(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String pSTarget, String pSWord, String Mode) throws DAOException {

		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			String addWhere = "";
			String modeWhere = "";
			if(!CurriCode.equals("")) {
				addWhere += " and a.curri_code = ?";
			}
			if(CurriYear > 0 && CurriTerm > 0) {
				addWhere += " and a.curri_year = ? and a.curri_term=?";
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) {
				addWhere += " and "+pSTarget+" like ?";
			}
			//-- 수료작업 진행용  S = 수강중, C = 수료처리 , F = 미수료 처리
			modeWhere = " and a.enroll_status in ('S', 'C', 'F')";
			sql.setTotalCol("a.user_id");
			sql.setCutCol("a.user_id");
			sql.setAlias(	" user_id, user_name, curri_code, curri_year" +
							", curri_term, curri_name, enroll_status, enroll_no " +
							", max_score, min_score, avg_score, curri_type" +
							", enroll_no, score_gubun ");

			sql.setSelect(	" a.user_id, b.user_name, a.curri_code, a.curri_year" +
							", a.curri_term, c.curri_name, a.enroll_status, a.enroll_no " +
							", ifnull(MAX(r.score_total),0) as max_score, ifnull(MIN(r.score_total),0) as min_score" +
							", ifnull(AVG(r.score_total),0) as avg_score, c.curri_property2 AS curri_type" +
							", a.enroll_no" +
							", (SELECT score_gubun FROM curri_sub " +
								" WHERE system_code = a.system_code AND curri_code = A.curri_code" +
								" AND curri_year = a.curri_year AND curri_term = a.curri_term) AS score_gubun ");

			sql.setFrom(	" users b, curri_top c, student a LEFT OUTER JOIN result_course r " +
							" ON a.system_code = r.system_code and a.curri_code=r.curri_code " +
							" and a.curri_year=r.curri_year and a.curri_term=r.curri_term " +
							" and a.user_id=r.user_id ");

			sql.setWhere(	" a.system_code=b.system_code and a.system_code=c.system_code " +
							" and a.user_id = b.user_id and a.curri_code = c.curri_code " +
							" and a.system_code=? "+modeWhere+addWhere);
			sql.setString(SystemCode);
			if(!CurriCode.equals("")) sql.setString(CurriCode);
			if(CurriYear > 0 && CurriTerm > 0) {
				sql.setInteger(CurriYear);
				sql.setInteger(CurriTerm);
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) sql.setString("%"+pSWord+"%");
			sql.setOrderby(" b.user_name desc");
			sql.setGroupby(" a.user_id, b.user_name, a.curri_code, a.curri_year, a.curri_term, c.curri_name, a.enroll_status, a.enroll_no");
			//---- 디버그 출력
			log.debug("[getCompleteStudentList]" + sql.toString());

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
	 * 수료처리 조정을 위한 상시 과정의 강의 종료된 수강생 진도율 포함 가져오기.
	 * @param curpage
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param pSTarget
	 * @param pSWord
	 * @param Mode
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCompleteStudentList2(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String pSTarget, String pSWord, String Mode) throws DAOException {

		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			String addWhere = "";
			String modeWhere = "";
			String addWhere2 = "";

			if(!CurriCode.equals("")) {
				addWhere += " and a.curri_code = ?";
				addWhere2 += " and curri_code = ?";
			}
			if(CurriYear > 0 && CurriTerm > 0) {
				addWhere += " and a.curri_year = ? and a.curri_term=?";
				addWhere2 += " and curri_year = ? and curri_term=?";
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) {
				addWhere += " and "+pSTarget+" like ?";
			}
			//-- 수료작업 진행용  S = 수강중, C = 수료처리 , F = 미수료 처리
			modeWhere = " and a.enroll_status in ('S', 'C', 'F')";
			sql.setTotalCol("a.user_id");
			sql.setCutCol("a.user_id");
			sql.setAlias("user_id, user_name, curri_code, curri_year, curri_term, enroll_status, enroll_no , servicestart_date, serviceend_date, mark_cnt");
			sql.setSelect("a.user_id, b.user_name, a.curri_code, a.curri_year, a.curri_term, a.enroll_status, a.enroll_no , a.servicestart_date, a.serviceend_date,ifnull(r.mark_cnt,0) as mark_cnt");
			sql.setFrom(" users b, student a LEFT OUTER JOIN (select system_code, curri_code, curri_year, curri_term ,user_id, count(*) as mark_cnt from bookmark where system_code=? "+addWhere2+" group by system_code, curri_code, curri_year, curri_term ,user_id) r  on a.system_code = r.system_code and a.curri_code = r.curri_code and a.curri_year = r.curri_year and a.curri_term = r.curri_term and a.user_id = r.user_id ");
			sql.setWhere(" a.system_code=b.system_code  and a.user_id = b.user_id  and  a.system_code=? "+addWhere+modeWhere);
			sql.setString(SystemCode);
			if(!CurriCode.equals("")) sql.setString(CurriCode);
			if(CurriYear > 0 && CurriTerm > 0) {
				sql.setInteger(CurriYear);
				sql.setInteger(CurriTerm);
			}
			sql.setString(SystemCode);
			if(!CurriCode.equals("")) sql.setString(CurriCode);
			if(CurriYear > 0 && CurriTerm > 0) {
				sql.setInteger(CurriYear);
				sql.setInteger(CurriTerm);
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) sql.setString("%"+pSWord+"%");
			sql.setOrderby(" b.user_name desc");
			//sql.setGroupby(" a.user_id, b.user_name, a.curri_code, a.curri_year, a.curri_term, a.enroll_status, a.enroll_no");
			//---- 디버그 출력
			log.debug("[getCompleteStudentList2]" + sql.toString());

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
	 * 개별 수강생 정보를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getStudentInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String UserId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, user_id, curri_code, curri_year, curri_term, enroll_no, enroll_status, servicestart_date"+
				", serviceend_date, chungend_date, team_no, enroll_date, cancel_date, complete_date, complete_no"+
				", get_credit, first_con, last_con, reg_id, reg_date");
		sb.append(" from student");
		sb.append(" where system_code=? and curri_code=? and curri_year=? and curri_term=? and user_id=?");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(UserId);

		//---- 디버그 출력
		log.debug("[getStudentInfo]" + sql.toString());
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
	 * 수강생 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addStudentInfo(StudentDTO studentInfo, String curriType) throws DAOException{
	    Connection conn = null;
	    DBConnectionManager pool = null;
	    int retVal = 0;
	    String	pRegMode	=	StringUtil.nvl(studentInfo.getRegMode());
	    int		No			=	studentInfo.getTeamNo();	//기존 학생데이터 삭제하기 위해 임시로 받아옴.

	    try{
	        pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

			if(pRegMode.equals("EDIT")) {
				if(No == 0) {
					StudentDTO	stuDto	=	new StudentDTO();
					stuDto.setSystemCode(StringUtil.nvl(studentInfo.getSystemCode()));
					stuDto.setCurriYear(studentInfo.getCurriYear());
					stuDto.setUserId(StringUtil.nvl(studentInfo.getUserId()));

					retVal = delStudentInfo(stuDto, conn);
					
					if(retVal > 0){
						retVal = addStudentInfo(studentInfo, curriType, pRegMode, conn);
						conn.commit();
					} else {
						conn.rollback();
					}	
				}
				else {
					retVal = addStudentInfo(studentInfo, curriType, pRegMode, conn);
					conn.commit();
				}				
				
			}
			else {
				retVal = addStudentInfo(studentInfo, curriType, pRegMode, conn);
				conn.commit();
			}	

	    }catch(Exception e){
            e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch(SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
	    }finally {
            try {
                if(conn != null) conn.setAutoCommit( true );
                if(pool != null) pool.freeConnection(conn); // 컨넥션 해제

            } catch(Exception ignore) {
			    log.error(ignore.getMessage(),ignore);
            }
        }

	    return retVal;
	}

	/**
	 * 수강생 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addStudentInfo(StudentDTO studentInfo, String curriType, String pRegMode, Connection conn) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		int pEnrollNo = getEnrollNo(studentInfo.getSystemCode(),studentInfo.getUserId(),studentInfo.getCurriCode(),studentInfo.getCurriYear(),studentInfo.getCurriTerm());

		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		String addPaymentIdx = "";
		String addPaymentIdx2 = "";
		if(studentInfo.getPaymentIdx() > 0){
			addPaymentIdx = ",payment_idx";
			addPaymentIdx2 = ",? ";
		}
		StringBuffer sb = new StringBuffer();

		if(!curriType.equals("R")){//-- 상시 과정은 ServiceDay 를 넘겨 받음
			sb.append("insert into student " +
							"(system_code, user_id, curri_code, curri_year" +
							", curri_term, enroll_no, enroll_status, servicestart_date");
			sb.append(		", serviceend_date, chungend_date, enroll_date, reg_id ");
			sb.append(		", reg_date, mod_id, mod_date"+addPaymentIdx+")");
			sb.append("values(?,?,?,?" +
							",?,?,?,"+currentDate+"" +
							", CONCAT(CAST(DATE_FORMAT(NOW(),'%Y%m%d') AS CHAR),'235959'),CONCAT(CAST(DATE_FORMAT(NOW(),'%Y%m%d') AS CHAR),'235959'),"+currentDate+",? ");
			sb.append(		", "+currentDate+", ?,"+currentDate+" "+addPaymentIdx2+")");


		    sql.setSql(sb.toString());

		    sql.setString(studentInfo.getSystemCode());
		    sql.setString(studentInfo.getUserId());
		    sql.setString(studentInfo.getCurriCode());
		    sql.setInteger(studentInfo.getCurriYear());
		    sql.setInteger(studentInfo.getCurriTerm());
		    sql.setInteger(pEnrollNo);

		    sql.setString(studentInfo.getEnrollStats());

		    sql.setString(studentInfo.getRegId());
		    sql.setString(studentInfo.getRegId());
		    if(studentInfo.getPaymentIdx() > 0)
		    	sql.setInteger(studentInfo.getPaymentIdx());


		}else{
			//-- 정규 과정의 경우
			sb.append("INSERT INTO student (system_code, user_id, curri_code, curri_year, curri_term, enroll_no");
			sb.append(", enroll_status, servicestart_date, serviceend_date, chungend_date, enroll_date ");
			sb.append(", reg_id, reg_date, mod_id, mod_date"+addPaymentIdx+")");
			sb.append("SELECT system_code, ?, curri_code, curri_year, curri_term, ?");
			sb.append(", ?, service_start, service_end, chung_end, "+currentDate);
			sb.append(" ,?,"+currentDate+",?,"+currentDate+" "+addPaymentIdx2);
			sb.append(" FROM curri_sub ");
			sb.append(" WHERE system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");

			sql.setSql(sb.toString());

			sql.setString(studentInfo.getUserId());
			sql.setInteger(pEnrollNo);

			sql.setString(studentInfo.getEnrollStats());

			sql.setString(studentInfo.getRegId());
		    sql.setString(studentInfo.getRegId());
		    if(studentInfo.getPaymentIdx() > 0)
		    	sql.setInteger(studentInfo.getPaymentIdx());

		    sql.setString(studentInfo.getSystemCode());
		    sql.setString(studentInfo.getCurriCode());
		    sql.setInteger(studentInfo.getCurriYear());
		    sql.setInteger(studentInfo.getCurriTerm());
		}

		//---- 디버그 출력
		log.debug("[addStudentInfo]" + sb.toString());

		try{
			retVal = broker.executeUpdate(sql, conn);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * dept_enroll_idx 를 가져온다.
	 * @param systemCode
	 * @param userId
	 * @param deptDaeCode
	 * @param deptSoCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws Exception
	 */
	public String getEnrollIdx(StudentDTO studentDto, String deptDaeCode, String deptSoCode) throws Exception {
		//---- 수강신청 카운트를 가져온다.
		log.debug("-------------------- getEnrollCnt Start ------------------");
		String pDeptEnrollIdx = "";
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append("select ifnull(max(dept_enroll_idx),0)+1 as dept_enroll_idx");
		sb.append("  from dept_enroll ");
		sb.append(" where system_code = ? ");
		sb.append("   and user_id=?       ");
		sb.append("   and dept_daecode=?  ");
		sb.append("   and dept_socode=?   ");
		sb.append("   and curri_code=? ");
		sb.append("   and curri_year=? ");
		sb.append("   and curri_term=? ");

		sql.setSql(sb.toString());

		sql.setString (studentDto.getSystemCode() );
		sql.setString (studentDto.getUserId() );
		sql.setString (deptDaeCode);
		sql.setString (deptSoCode);
		sql.setString (studentDto.getCurriCode());
		sql.setInteger(studentDto.getCurriYear());
		sql.setInteger(studentDto.getCurriTerm());

		log.debug("getEnrollIdx : "+sql.toString());

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);

			if(rs.next()) pDeptEnrollIdx = rs.getString("dept_enroll_idx");

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally{
		    if(rs!=null ) rs.close();
		}

		log.debug("-------------------- getEnrollCnt End   ------------------");
		return pDeptEnrollIdx;
	}

	/**
	 * 수강생 정보를 수정한다.
	 * @param studentInfo
	 * @return
	 * @throws DAOException
	 */
	public int editStudentInfo(StudentDTO studentInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("update student set enroll_status=?, servicestart_date=?, serviceend_date=?, chungend_date=?"+
				", team_no=?, enroll_date=?, cancel_date=?, complete_date=?, complete_no=?, get_credit=?, first_con=?, last_con=?"+
				", mod_id=?, mod_date=? where system_code=? and user_id=? and curri_code=? and curri_year=? and curri_term=? and enroll_no=?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
//		---- 입력된 값을 가져 온다.
		sql.setString(studentInfo.getEnrollStats());
		sql.setString(studentInfo.getServicestartDate());
		sql.setString(studentInfo.getServiceendDate());
		sql.setString(studentInfo.getChungendDate());
		sql.setInteger(studentInfo.getTeamNo());
		sql.setString(studentInfo.getEnrollDate());
		sql.setString(studentInfo.getCancelDate());
		sql.setString(studentInfo.getCompleteDate());
		sql.setString(studentInfo.getCompleteNo());
		sql.setInteger(studentInfo.getGetCredit());
		sql.setString(studentInfo.getFirstCon());
		sql.setString(studentInfo.getLastCon());
		sql.setString(studentInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(studentInfo.getSystemCode());
		sql.setString(studentInfo.getUserId());
		sql.setString(studentInfo.getCurriCode());
		sql.setInteger(studentInfo.getCurriYear());
		sql.setInteger(studentInfo.getCurriTerm());
		sql.setInteger(studentInfo.getEnrollNo());
		//---- 디버그 출력
		log.debug("[editStudentInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개설 과정 정보 변경에 따른 수강일자, 청강일자 변경이 있는경우 변경 하여 준다.
	 * @param studentInfo
	 * @return
	 * @throws DAOException
	 */
	public int editStudentDateInfo(StudentDTO studentInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("update student set servicestart_date=?, serviceend_date=?, chungend_date=?"+
				", mod_id=?, mod_date=? where system_code=? and curri_code=? and curri_year=? and curri_term=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(studentInfo.getServicestartDate());
		sql.setString(studentInfo.getServiceendDate());
		sql.setString(studentInfo.getChungendDate());
		sql.setString(studentInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(studentInfo.getSystemCode());
		sql.setString(studentInfo.getCurriCode());
		sql.setInteger(studentInfo.getCurriYear());
		sql.setInteger(studentInfo.getCurriTerm());
		//---- 디버그 출력
		log.debug("[editStudentInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 * 학습자의 수료 상태를 변경한다.
	 * @param studentInfo
	 * @return
	 * @throws DAOException
	 */
	public int completetudentStatus(StudentDTO studentInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("update student set enroll_status=?, complete_date=?,  mod_id=?, mod_date=? where system_code=? and user_id=? and curri_code=? and curri_year=? and curri_term=?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
//		---- 입력된 값을 가져 온다.
		sql.setString(studentInfo.getEnrollStats());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(studentInfo.getModId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(studentInfo.getSystemCode());
		sql.setString(studentInfo.getUserId());
		sql.setString(studentInfo.getCurriCode());
		sql.setInteger(studentInfo.getCurriYear());
		sql.setInteger(studentInfo.getCurriTerm());
		//---- 디버그 출력
		log.debug("[completetudentStatus]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	
	/**
	 * 커넥션이 있는 학생정보 삭제
	 * @param studentInfo
	 * @param conn
	 * @return
	 * @throws DAOException
	 */
	public int delStudentInfo(StudentDTO studentInfo, Connection conn) throws DAOException{
		String	RegId 		= 	StringUtil.nvl(studentInfo.getRegId());
		String	UserId 		= 	StringUtil.nvl(studentInfo.getUserId());
		String	SystemCode 	= 	StringUtil.nvl(studentInfo.getSystemCode());
		String	CurriCode 	= 	StringUtil.nvl(studentInfo.getCurriCode());
		int 	CurriYear 	= 	StringUtil.nvl(studentInfo.getCurriYear(), 0);
		int 	CurriTerm 	= 	StringUtil.nvl(studentInfo.getCurriTerm(), 0);
		int 	EnrollNo 	= 	StringUtil.nvl(studentInfo.getEnrollNo(), 0);
		
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM student " +
				" WHERE system_code=?  ");
		sql.setString(SystemCode);		
		
		if(!UserId.equals("")) {
			sb.append(" AND user_id=? ");
			sql.setString(UserId);
		}
		if(!CurriCode.equals("")) {
			sb.append(" AND curri_code=? ");
			sql.setString(CurriCode);
		}
		if(CurriYear != 0) {
			sb.append(" AND curri_year=? ");
			sql.setInteger(CurriYear);
		}
		if(CurriTerm != 0) {
			sb.append(" AND curri_term=? ");
			sql.setInteger(CurriTerm);
		}
		if(EnrollNo != 0) {
			sb.append("  and enroll_no=? ");
			sql.setInteger(EnrollNo);
		}
		sql.setSql(sb.toString());		

		//---- 디버그 출력
		log.debug("[delStudentInfo]" + sql.toString());
		
		try{
			retVal = broker.executeUpdate(sql, conn);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 수강생 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int delStudentInfo(StudentDTO studentInfo) throws DAOException{
		String	RegId 		= 	studentInfo.getRegId();
		String	UserId 		= 	studentInfo.getUserId();
		String	SystemCode 	= 	studentInfo.getSystemCode();
		String	CurriCode 	= 	studentInfo.getCurriCode();
		int 	CurriYear 	= 	studentInfo.getCurriYear();
		int 	CurriTerm 	= 	studentInfo.getCurriTerm();
		int 	EnrollNo 	= 	studentInfo.getEnrollNo();
		
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM student " +
				" WHERE system_code=? AND user_id=? " +
				" AND curri_code=? AND curri_year=? " +
				" AND curri_term=? ");
		sql.setString(SystemCode);
		sql.setString(UserId);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		
		if(EnrollNo != 0) {
			sb.append("  and enroll_no=? ");
			sql.setInteger(EnrollNo);
		}		
		sql.setSql(sb.toString());		

		//---- 디버그 출력
		log.debug("[delStudentInfo]" + sql.toString());
		
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * student 의 상태값을 변경한다.....커넥션이 넘어오지 않을경우 커넥션을 만들어서 넘겨준다.
	 *                          .....결제환불 취소시 트랜잭션 관리를 위해..
	 * @param studentInfo
	 * @return
	 * @throws DAOException
	 */
	public int changeEnrollStats(StudentDTO studentInfo) throws DAOException{
		int retVal = 0;
		
		StringBuffer sb = null;
		QueryStatement sql = null;
		Connection conn = null;
	    DBConnectionManager pool = null;
		try{
			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );
			
			retVal = changeEnrollStats(studentInfo, conn );
			
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally{
			try{
			    if(conn != null){
			        conn.setAutoCommit( true );
			        pool.freeConnection(conn); // 컨넥션 해제
			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }
		return retVal;
	}


	/**
	 * 수강신청 상태를 변경한다.
	 * @param studentInfo
	 * @return
	 * @throws DAOException
	 */
	public int changeEnrollStats(StudentDTO studentInfo , Connection conn) throws DAOException {
		log.debug("------------------ changeEnrollStats Start ------------------");
		int				retVal		=	0;
		QueryStatement	sql 		= 	new QueryStatement();		
		String			currentDate	=	"CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		String			RegId 		= 	StringUtil.nvl(studentInfo.getRegId());
		String 			UserId 		= 	StringUtil.nvl(studentInfo.getUserId());
		String 			SystemCode 	= 	StringUtil.nvl(studentInfo.getSystemCode());
		String 			CurriCode 	= 	StringUtil.nvl(studentInfo.getCurriCode());
		int 			CurriYear 	= 	StringUtil.nvl(studentInfo.getCurriYear(), 0);
		int 			CurriTerm 	= 	StringUtil.nvl(studentInfo.getCurriTerm(), 0);
		int 			EnrollNo	= 	StringUtil.nvl(studentInfo.getEnrollNo(), 0);
		String 			EnrollStats = 	StringUtil.nvl(studentInfo.getEnrollStats());
		StudentDAO 		studentDao 	= 	new StudentDAO();
		int 			maxEnrollNo = 	studentDao.getMaxEnrollNo(SystemCode, UserId, CurriCode, CurriYear, CurriTerm);		
		String 			addSet 		= 	"";
		if(EnrollStats.equals("S")) {
			addSet 	= 	", enroll_date = "+currentDate+"";
		} else if(EnrollStats.equals("N")) {
			addSet 	= 	", cancel_date = "+currentDate+"";
		} else if(EnrollStats.equals("C")) {
			addSet 	= 	", complete_date = "+currentDate+"";
		}
		
		String 			qry 		= 	"update student set enroll_status='"+EnrollStats+"' "+addSet+", mod_id=?, "+
										"mod_date="+currentDate+" where system_code='"+SystemCode+"' and user_id=? and curri_code=? "+
										" and curri_year="+CurriYear+" and curri_term="+CurriTerm;
		if(EnrollNo > 0 ){
			qry +=" and enroll_no="+EnrollNo;
		} else {
			qry +=" and enroll_no = ? ";
		}
		sql.setSql(qry);
		sql.setString(RegId);
		sql.setString(UserId);
		sql.setString(CurriCode);
		if(EnrollNo <= 0){
			sql.setInteger(maxEnrollNo);
		}

		log.debug("changeEnrollStats : Query = "+qry);
		
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		log.debug("------------------ changeEnrollStats End --------------------");
		return retVal;
	}


	/**
	 * 수강신청 카운트를 가져온다
	 * @param systemCode
	 * @param userId
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws DAOException
	 */
	public int getEnrollCnt(String systemCode, String userId, String curriCode, int curriYear, int curriTerm) throws Exception {
		//---- 수강신청 카운트를 가져온다.
		log.debug("-------------------- getEnrollCnt Start ------------------");
		int cnt = 0;
		QueryStatement sql = new QueryStatement();
		String qry = "select count(*) as cnt from student where system_code=? and user_id=? and curri_code=? and curri_year=? and curri_term=? and enroll_status in ('E')";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(userId);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		log.debug("qyr : "+qry);
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		rs.next();
		cnt = rs.getInt("cnt");
		rs.close();
		log.debug("-------------------- getEnrollCnt End   ------------------");
		return cnt;
	}

	public int getStudentEnrollCnt(String systemCode, String userId, String curriCode, int curriYear, int curriTerm) throws Exception {
		//---- 수강신청 카운트를 가져온다.
		log.debug("-------------------- getEnrollCnt Start ------------------");
		int cnt = 0;
		QueryStatement sql = new QueryStatement();
		String qry = "select count(*) as cnt from student where system_code=? and user_id=? and curri_code=? and curri_year=? and curri_term=? and enroll_status in ('E','S')";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(userId);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		log.debug("qyr : "+qry);
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		rs.next();
		cnt = rs.getInt("cnt");
		rs.close();
		log.debug("-------------------- getEnrollCnt End   ------------------");
		return cnt;
	}

	/**
	 * 수강생 리스트를 가져온다.
	 * @param systemcode
	 * @param curricode
	 * @param curriyear
	 * @param curriterm
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getStudentList(String systemcode, String curricode, int curriyear, int curriterm) throws DAOException{
		ArrayList studentList			=	null;
		StudentDTO studentDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select distinct s.user_id, u.user_name ");
		sb.append(" from student s, users u   ");
		sb.append(" where s.system_code = u.system_code and s.user_id = u.user_id and ");
		sb.append(" s.system_code = ? and s.curri_code=? and s.curri_year=? and s.curri_term=? ");
		sb.append(" and  s.enroll_status in ('S', 'C','F')  ");
		sb.append(" order by u.user_name");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);

		log.debug("[getStudentList]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs 					= 	broker.executeQuery(sql);

			studentList		= 	new ArrayList();
			while(rs.next()){
				//studentDto	= 	new StudentDTO();
				//studentDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				//-- user_id 컬럼 하나만 받으므로 DTO 담을 필요없이 그냥 String 으로 받아서 처리
				studentList.add(StringUtil.nvl(rs.getString("user_id")));
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return studentList;
	}

	/**
	 * 수강생인지 아닌지 검색  수강생인 경우 1을 리턴 아닌경우 0을 리턴
	 * @param systemCode
	 * @param userId
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return int
	 * @throws DAOException
	 */
	public int isStudent(String systemCode, String userId, String curriCode, int curriYear, int curriTerm) throws Exception {
		log.debug("--------------- isStudent Start -------------------");
		int retVal = 0;
		QueryStatement sql = new QueryStatement();

		String qry = "SELECT count(a.user_id) as cnt FROM student a, curri_sub b  " +
		" WHERE a.system_code = b.system_code AND a.curri_code = b.curri_code " +
		" AND a.curri_year = b.curri_year AND a.curri_term = b.curri_term " +
		" AND a.system_code=? and a.user_id=? "+
		" and a.enroll_status='S' ";
		
		sql.setString(systemCode);
		sql.setString(userId);
		if(!curriCode.equals("")) {
			qry += " AND a.curri_code=? ";
			sql.setString(curriCode);	
		}
		if(curriYear != 0) {
			qry += " AND a.curri_year=? ";
			sql.setInteger(curriYear);
		}
		if(curriTerm != 0) {
			qry += " AND a.curri_term=? ";
			sql.setInteger(curriTerm);
		}		
		
		sql.setSql(qry);
		
		log.debug("qry : "+qry+"::: "+systemCode+","+userId+","+curriCode+","+Integer.toString(curriYear)+","+Integer.toString(curriTerm));
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		rs.next();
		retVal = rs.getInt("cnt");
		rs.close();
		log.debug("--------------- isStudent End   -------------------");
		return retVal;
	}
	
	/**
	 * 수강생인지 확인(청강기간까지 포함)
	 * @param systemCode
	 * @param userId
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws Exception
	 */
	public int isStudentChung(String systemCode, String userId, String curriCode, int curriYear, int curriTerm, String curriType) throws Exception {
		log.debug("--------------- isStudent Start -------------------");
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		String qry = "select count(s.user_id) as cnt from student s , curri_sub c "+
						"where s.system_code=c.system_code and s.curri_code=c.curri_code and s.curri_year=c.curri_year "+
						" and s.curri_term=c.curri_term and s.system_code=? and s.user_id=? and s.curri_code=? and s.curri_year=?"+
						" and s.curri_term=? and s.enroll_status in ('S','C','F') "+
						" and s.servicestart_date <= "+CommonUtil.getDbCurrentDate();
		if (!curriType.equals("O"))
			qry	+=			" and s.chungend_date >= "+CommonUtil.getDbCurrentDate();

		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(userId);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		
		
		log.debug("qry : "+qry+"::: "+systemCode+","+userId+","+curriCode+","+Integer.toString(curriYear)+","+Integer.toString(curriTerm));
		

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		rs.next();
		retVal = rs.getInt("cnt");
		rs.close();
		log.debug("--------------- isStudent End   -------------------");
		return retVal;
	}
	/**
	 * 수료번호 미 발급시 수료번호를 발급시켜준다.
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param userId
	 * @param enrollNo
	 * @return
	 * @throws Exception
	 */
	public int getStudentCertNo(String systemCode, String curriCode, int curriYear, int curriTerm, String userId, int enrollNo) throws Exception {
		log.debug("--------------- getStudentCertNo Start -------------------");
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		String qry = "SELECT IFNULL(complete_no, 0) as cert_no FROM student "+
						" WHERE system_code=? and curri_code=? and curri_year=?"+
						" and curri_term=? and user_id=? and enroll_no=? ";

		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(userId);
		sql.setInteger(enrollNo);

		RowSet	rs				=	null;
		RowSet rs1				=	null;
		String	MaxCompleteNo	=	"";
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				retVal = rs.getInt("cert_no");
			}


			if(retVal <=0){//-- 수료번호를 받지 못한경우

				QueryStatement sql3 = new QueryStatement();
				StringBuffer sb = new StringBuffer();

				sb.append(" SELECT MAX(IFNULL(complete_no, 0))+1 AS max_no FROM student ");
				sb.append(" WHERE system_code= ? and  curri_year =  ? ");
				sql3.setSql(sb.toString());
				sql3.setString(systemCode);
				sql3.setInteger(curriYear);

				rs1	=	broker.executeQuery(sql3);
				if(rs1.next()) {
					MaxCompleteNo	=	rs1.getString("max_no");
				}

				rs1.close();
				sb.setLength(0);

				QueryStatement sql2 = new QueryStatement();
				sb.append(" UPDATE student SET complete_no = ? ");
				sb.append(" WHERE system_code=? and curri_code=? and curri_year=? ");
				sb.append(" and curri_term=? and user_id=? and enroll_no=? ");
				sql2.setSql(sb.toString());

				sql2.setString(MaxCompleteNo);
				sql2.setString(systemCode);
				sql2.setString(curriCode);
				sql2.setInteger(curriYear);
				sql2.setInteger(curriTerm);
				sql2.setString(userId);
				sql2.setInteger(enrollNo);

				retVal = broker.executeUpdate(sql2);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally
		{
		    if(rs != null ) try{ rs.close()   ; }catch(SQLException ex){  }
		    if(rs1 != null ) try{ rs1.close()   ; }catch(SQLException ex){  }
		}
		log.debug("--------------- isStudent End   -------------------");
		return retVal;
	}

	/**
	 * 수료증 정보를 가져온다
	 * @param pUserId
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCerificationPrn(String pUserId, String systemCode, String curriCode, int curriYear, int curriTerm ) throws Exception {
		log.debug("--------------- getCerificationPrn Start -------------------");
		QueryStatement sql = new QueryStatement();
        StringBuffer sb = new StringBuffer();
		ArrayList list		= 	new ArrayList();
	    ResultSetMetaData rsmd  = null;
	    HashMap hm      = new HashMap();

	    sb.append(" SELECT a.curri_name, t.curri_property2 as curri_type      ");
	    sb.append("      , b.complete_no    ");
		sb.append("      , ifnull(B.SERVICESTART_DATE,'') AS  servicestart_date ");
		sb.append("      , ifnull(B.SERVICEEND_DATE,'') AS   serviceend_date  ");
		//sb.append("      , ifnull(A.COMPLETE_END,'') AS  complete_end   ");
		sb.append("      , c.user_name     as prof_name  ");
		sb.append("      , d.user_name     as user_name  ");
		sb.append("      , d.jumin_no      as jumin_no  ");
		sb.append("  FROM curri_top t, curri_sub a, student b, users c   , users d ");
		sb.append(" WHERE t.system_code =a.system_code   ");
		sb.append("   AND t.curri_code = a.curri_code     ");
		sb.append("   AND a.system_code =b.system_code   ");
		sb.append("   AND a.curri_code = b.curri_code     ");
		sb.append("   AND a.curri_year = b.curri_year     ");
		sb.append("   AND a.curri_term = b.curri_term     ");
		sb.append("   AND b.enroll_status ='c'            ");
		sb.append("   AND a.prof_id = c.user_id           ");
		sb.append("   AND b.user_id = d.user_id           ");
		sb.append("   AND a.system_code = ?   ");
		sb.append("   AND a.curri_code  = ?   ");
		sb.append("   AND a.curri_year  = ?   ");
		sb.append("   AND a.curri_term  = ?   ");
		sb.append("   AND b.user_id     = ?   ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(pUserId);

		log.debug("[getCerificationPrn]=============> "+  sql.toString()  );

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			rsmd = rs.getMetaData();

			while(rs.next())
	        {
	            hm = new HashMap();

	            for(int i=1; i<=rsmd.getColumnCount() ; i++)
	            {
	                hm.put(rsmd.getColumnName(i), rs.getString(i) );
	            }

	            list.add( hm );
	        }

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally
		{
		    if(rs != null ) try{ rs.close()   ; }catch(SQLException ex){  }
		}

		return list;
	}

	/**
	 * 수강 확인 정보를 가져 온다.
	 * @param pUserId
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws Exception
	 */
	public ArrayList getenrollConfirmPrn(String pUserId, String systemCode, String curriCode, int curriYear, int curriTerm , String pEnrollType, int pEnrollNo) throws Exception {
		log.debug("--------------- getenrollConfirmPrn Start -------------------");
		QueryStatement sql = new QueryStatement();
        StringBuffer sb = new StringBuffer();
		ArrayList list		= 	new ArrayList();
	    ResultSetMetaData rsmd  = null;
	    HashMap hm      = new HashMap();

	    sb.append(" SELECT a.curri_name, t.curri_property2 as curri_type      ");
	    sb.append("      , b.complete_no    ");
		sb.append("      , ifnull(b.servicestart_date,'') as  servicestart_date ");
		sb.append("      , ifnull(b.serviceend_date,'') as   serviceend_date  ");
		sb.append("      , c.user_name     as prof_name  ");
		sb.append("      , d.user_name     as user_name  ");
		sb.append("      , d.jumin_no      as jumin_no  ");
		sb.append("      , (select price from curri_buy_log ");
        sb.append(" where system_code=a.system_code and curri_code = a.curri_code and curri_year = b.curri_year and curri_term = b.curri_term and user_id = d.user_id" +
        		"   and payment_idx = ? ) as price  ");
		sb.append("  FROM curri_top t, curri_sub a, student b, users c   , users d ");
		sb.append(" WHERE t.system_code =a.system_code   ");
		sb.append("   AND t.curri_code = a.curri_code     ");
		sb.append("   AND a.system_code =b.system_code   ");
		sb.append("   AND a.curri_code = b.curri_code     ");
		sb.append("   AND a.curri_year = b.curri_year     ");
		sb.append("   AND a.curri_term = b.curri_term     ");
		sb.append("   AND a.prof_id = c.user_id           ");
		sb.append("   AND b.user_id = d.user_id           ");
		sb.append("   AND a.system_code = ?   ");
		sb.append("   AND a.curri_code  = ?   ");
		sb.append("   AND a.curri_year  = ?   ");
		sb.append("   AND a.curri_term  = ?   ");
		sb.append("   AND b.user_id     = ?   ");
		if(pEnrollType.equals("enroll"))
			sb.append("   AND b.enroll_status != 'N' ");

		sql.setSql(sb.toString());
		sql.setInteger(pEnrollNo);
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(pUserId);


		log.debug("[getenrollConfirmPrn]=============> "+  sql.toString()  );
		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			rsmd = rs.getMetaData();

			while(rs.next())
	        {
	            hm = new HashMap();

	            for(int i=1; i<=rsmd.getColumnCount() ; i++)
	            {
	                hm.put(rsmd.getColumnName(i), rs.getString(i) );
	            }

	            list.add( hm );
	        }

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally
		{
		    if(rs != null ) try{ rs.close()   ; }catch(SQLException ex){  }
		}

		return list;
	}



	/**
	 * 수강취소 페이지에 결제 타입을 보여주기 위함.
	 * @param pSystemCode
	 * @param pPaymentIdx
	 * @return
	 * @throws DAOException
	 */
	public RowSet getEnrollCancelInfo(StudentDTO studentDto , String pPaymentIdx ) throws DAOException{
		 String feeType = "";

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 RowSet rs = null;

		 sb.append("select a.curri_name  ");
		 sb.append(", b.price ");
		 sb.append(", (select curri_property2 from curri_top where system_code = b.system_code and curri_code  = a.curri_code ) as curri_type ");
		 sb.append(", (select fee_type from payment   where system_code = b.system_code and payment_idx = b.payment_idx ) as fee_type ");
		 sb.append(", (select status from vbank     where system_code = b.system_code and payment_idx = b.payment_idx ) as status ");
		 sb.append(", service_start ");
		 sb.append(", service_end ");
		 sb.append(", servicestart_date ");
		 sb.append(", CAST(DATE_FORMAT( CAST(DATE_FORMAT(servicestart_date,'%Y%m%d%H%i%s') AS CHAR) +  cancel_config_day , '%Y%m%d%H%i%s') AS CHAR ) cancel_end_date ");
		 sb.append(", cancel_config_day ");
		 sb.append(", cancel_config_study ");
		 sb.append(", c.enroll_status ");
		 sb.append(", b.payment_idx ");
		 sb.append(", b.status as status1 "); // 추가 했따..BlackMan
		 sb.append("FROM curri_sub a, curri_buy_log b,  student c ");
		 sb.append("WHERE a.system_code = b.system_code ");
		 sb.append("AND a.curri_code  = b.curri_code ");
		 sb.append("AND a.curri_year  = b.curri_year ");
		 sb.append("AND a.curri_term  = b.curri_term ");
		 sb.append("and a.system_code = c.system_code ");
		 sb.append("AND a.curri_code  = c.curri_code ");
		 sb.append("AND a.curri_year  = c.curri_year ");
		 sb.append("AND a.curri_term  = c.curri_term ");
		 sb.append("and b.user_id     = c.user_id ");
		 sb.append("and b.system_code = ? ");
		 sb.append("and b.curri_code  = ? ");
		 sb.append("and b.curri_year  = ? ");
		 sb.append("and b.curri_term  = ? ");
		 sb.append("and b.user_id     = ? ");
		 if( ("").equals(pPaymentIdx))
		     sb.append(" and b.payment_idx = ? ");
		 sb.append(" and b.status in ( '1','2') ");  // 1:결제, 2:미결제, 3: 결제취소

		 sql.setSql(sb.toString());
		 sql.setString ( studentDto.getSystemCode() );
		 sql.setString ( studentDto.getCurriCode() );
		 sql.setInteger( studentDto.getCurriYear() );
		 sql.setInteger( studentDto.getCurriTerm() );
		 sql.setString ( studentDto.getUserId() );

		 if(("").equals(pPaymentIdx))
		     sql.setString ( pPaymentIdx );


		 log.debug("[getEnrollCancelInfo]" + sql.toString());

		 try{
		     rs = 	broker.executeQuery(sql);

		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return rs;
		}

	/**
	 *
	 * @param studentDto
	 * @return
	 * @throws DAOException
	 */
	public String getBookMarkCnt(StudentDTO studentDto ) throws DAOException{
		 String feeType = "";
		 String curriCnt = "0";
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 RowSet rs = null;

		 sb.append(" SELECT count(* )  cnt               ");
		 sb.append("  FROM bookmark a              ");
		 sb.append(" WHERE a.open_chk    = 'Y'           ");
		 sb.append("   AND a.system_code = ?             ");
		 sb.append("   AND a.curri_code  = ?             ");
		 sb.append("   AND a.curri_year  = ?             ");
		 sb.append("   AND a.curri_term  = ?             ");
		 sb.append("   AND a.user_id     = ?             ");

		 sql.setSql(sb.toString());
		 sql.setString ( studentDto.getSystemCode() );
		 sql.setString ( studentDto.getCurriCode() );
		 sql.setInteger( studentDto.getCurriYear() );
		 sql.setInteger( studentDto.getCurriTerm() );
		 sql.setString ( studentDto.getUserId()    );


		 log.debug("[getBookMarkCnt]" + sql.toString());


		 try{
		     rs = broker.executeQuery(sql);

		     if(rs.next()){
		         curriCnt = rs.getString("cnt");
		     }
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return curriCnt;
		}

	/**
	 *
	 * @param studentDto
	 * @return
	 * @throws DAOException
	 */
	public String getTotContentsCnt(StudentDTO studentDto ) throws DAOException{
		 String feeType = "";
		 String curriCnt = "0";
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 RowSet rs = null;

		 sb.append(" SELECT count(* ) CNT        ");
		 sb.append("  FROM curri_contents a      ");
		 sb.append(" WHERE a.contents_type = 'C' ");
		 sb.append("   AND a.system_code = ?     ");
		 sb.append("   AND a.curri_code  = ?     ");
		 sb.append("   AND a.curri_year  = ?     ");
		 sb.append("   AND a.curri_term  = ?     ");

		 sql.setSql(sb.toString());
		 sql.setString ( studentDto.getSystemCode() );
		 sql.setString ( studentDto.getCurriCode() );
		 sql.setInteger( studentDto.getCurriYear() );
		 sql.setInteger( studentDto.getCurriTerm() );

		 log.debug("[getTotContentsCnt]" + sql.toString());


		 try{
		     rs = broker.executeQuery(sql);

		     if(rs.next()){
		         curriCnt = rs.getString("cnt");
		     }
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return curriCnt;
		}


	/**
	 * 수강생의 최대 enroll_no 값을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param kb
	 * @return
	 * @throws DAOException
	 */
	public int getMaxEnrollNo(String systemCode, String userId, String curriCode, int curriYear, int curriTerm) throws  DAOException  {
		log.debug("-------------------- getEnrollNo Start ------------------");
		int no = 1;
		QueryStatement sql = new QueryStatement();
		String qry = "select IFNULL(max(enroll_no),0) as enroll_no from student "+
			" where system_code=? and user_id=? and curri_code=? and curri_year=? and curri_term=? ";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(userId);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
				
		log.debug("[getMaxEnrollNo] : "+sql.toString());
		
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			rs.next();
			no = rs.getInt("enroll_no");
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		log.debug("-------------------- getEnrollNo End   ------------------");
		return no;
	}
}