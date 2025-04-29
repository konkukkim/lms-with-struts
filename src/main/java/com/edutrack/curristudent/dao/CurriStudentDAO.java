package com.edutrack.curristudent.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.curristudent.dto.BestStudentDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.UsersDTO;


/*
 * @author pearlm
 *
 * 수강생 관리
 */
public class CurriStudentDAO extends AbstractDAO {

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
			addWhere += " and a.curri_code = ?";
		}
		if(CurriYear > 0 && CurriTerm > 0) {
			addWhere += " and a.curri_year = ? and a.curri_term=?";
		}

		if(!kb.equals("")){
		    addWhere += " and enroll_status in ('S', 'C','F')";
		}
		sb.append("select count(user_id) as cnt from student a where system_code= ?"+addWhere);// and curri_code=? and curri_year=? and curri_term=?");
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
	 * 수강생, 수료생 정보 가져오기.
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
	public ListDTO getConfirmPassStudentList(int curpage, String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String pSTarget, String pSWord, String Mode) throws DAOException {

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
			if(Mode.equals("enroll")) {
				modeWhere = " and a.enroll_status in ('E','N')";
			} else if(Mode.equals("confirm")) {
				modeWhere = " and a.enroll_status ='S'";
			} else if(Mode.equals("complete")){
				modeWhere = " and a.enroll_status ='C'";
			} else if(Mode.equals("confirmPass")){
			    modeWhere = " and a.enroll_status in ('S', 'C','F')";
			}
			sql.setTotalCol("a.user_id");
			sql.setCutCol("a.user_id");
			sql.setAlias("user_id, user_name, email, last_con, best_student_cnt ");
			String select =" a.user_id, b.user_name, b.email,"+
				//"  ifnull(( select round((sysdate-to_date(last_con,'yyyymmddhh24miss'))*24*60,0) " +
				" IFNULL(( select (HOUR(CAST(TIMEDIFF(now(), DATE_FORMAT(last_con,'%Y-%m-%d %H:%i:%s')) AS CHAR))*60 + MINUTE(CAST(TIMEDIFF(now(), DATE_FORMAT(last_con,'%Y-%m-%d %H:%i:%s')) AS CHAR)) ) " +
				" from user_connect  where system_code = a.system_code and user_id = a.user_id ),999) as last_con, "+
				//" (select count(student_id) from best_student where system_code = a.system_code AND curri_code = a.curri_code "+
				//" AND curri_year = a.curri_year AND curri_term = a.curri_term AND student_id = a.user_id ) as best_student_cnt";
				" 0 as best_student_cnt";
			sql.setSelect(select);
			sql.setFrom(" student a, users b ");
			sql.setWhere(" a.system_code=b.system_code "+
			" and a.user_id = b.user_id and a.system_code=? "+modeWhere+addWhere);
			sql.setString(SystemCode);
			if(!CurriCode.equals("")) sql.setString(CurriCode);
			if(CurriYear > 0 && CurriTerm > 0) {
				sql.setInteger(CurriYear);
				sql.setInteger(CurriTerm);
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) sql.setString("%"+pSWord+"%");
			sql.setOrderby(" b.user_name asc ");

			//---- 디버그 출력
			log.debug("[getConfirmPassStudentList]" + sql.toString());

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

	public ArrayList getConfirmPassStudentList2(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String pSTarget, String pSWord, String Mode) throws DAOException {

	    UsersDTO usersDto = null;
	    ArrayList	studentList = new ArrayList();
	    RowSet rs = null;
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
			if(Mode.equals("enroll")) {
				modeWhere = " and a.enroll_status in ('E','N')";
			} else if(Mode.equals("confirm")) {
				modeWhere = " and a.enroll_status ='S'";
			} else if(Mode.equals("complete")){
				modeWhere = " and a.enroll_status ='C'";
			} else if(Mode.equals("confirmPass")){
			    modeWhere = " and a.enroll_status in ('S', 'C','F')";
			}

			sql.setSelect("a.user_id, b.user_name, b.email ");
			sql.setFrom(" student a, users b");
			sql.setWhere(" a.system_code=b.system_code "+
			" and a.user_id = b.user_id and a.system_code=? "+modeWhere+addWhere);
			sql.setString(SystemCode);
			if(!CurriCode.equals("")) sql.setString(CurriCode);
			if(CurriYear > 0 && CurriTerm > 0) {
				sql.setInteger(CurriYear);
				sql.setInteger(CurriTerm);
			}
			if(!pSTarget.equals("") && !pSWord.equals("")) sql.setString("%"+pSWord+"%");

			//---- 디버그 출력
			log.debug("[getConfirmPassStudentList2]" + sql.toString());

			//---- 쿼리실행 및 반환값 설정

			rs = broker.executeQuery(sql);
			while(rs.next()){
			    usersDto = new UsersDTO();
			    usersDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
			    usersDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
			    usersDto.setEmail(StringUtil.nvl(rs.getString("email")));
			    studentList.add(usersDto);
			}

			return studentList;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}



	/**
	 * 우수학생 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public boolean addBestStudent(ArrayList bestStudentList) throws DAOException{

		 boolean retVal 		= 	false;
		 QueryStatement[] sqls 	= 	new QueryStatement[bestStudentList.size()];
		 QueryStatement sql = null;
		 StringBuffer sb = new StringBuffer();
		 sb.append("insert into best_student (system_code, curri_code, curri_year, curri_term, student_id, contents, reg_id, reg_date ) ");
		 sb.append("values(?,?,?,?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))");

		 BestStudentDTO bestStudentDto=null;

		 for(int i =0; i < bestStudentList.size(); i++){
		 	sql = new QueryStatement();
		 	bestStudentDto = (BestStudentDTO)bestStudentList.get(i);

	    	sql.setString(bestStudentDto.getSystemCode());
		    sql.setString(bestStudentDto.getCurriCode());
		    sql.setInteger(bestStudentDto.getCurriYear());
		    sql.setInteger(bestStudentDto.getCurriTerm());
		    sql.setString(bestStudentDto.getStudentId());
		    sql.setString(bestStudentDto.getContents());
		    sql.setString(bestStudentDto.getRegId());
		    sql.setSql(sb.toString());
			sqls[i] = sql;
		 }
		 log.debug("[addBestStudent]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 우수학생의 등록사유를 받아온다
	 * 2007.03.20 sangsang
	 * @param systemCode
	 * @param cateCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param studentId
	 * @return
	 * @throws DAOException
	 */
	public String getBestStudent(String systemCode, String curriCode, int curriYear, int curriTerm, String studentId) throws DAOException {
		String contents = "";
		StringBuffer sb = new StringBuffer();
		sb.append(" select contents from best_student ");
		sb.append(" where system_code= ? and curri_code=? and curri_year=? and curri_term=? and student_id=? ");

		QueryStatement sql = new QueryStatement();
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(studentId);
		sql.setSql(sb.toString());

		log.debug("[getBestStudent]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 contents = rs.getString("contents");
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
		 return contents;
	}

	/**
	 * 우수학생의 등록사유를 수정한다
	 * 2007.03.20 sangsang
	 * @param bestStudentDto
	 * @return
	 * @throws DAOException
	 */
	public int editBestStudent(BestStudentDTO bestStudentDto) throws DAOException {
		int retVal = 0;
		StringBuffer sb = new StringBuffer();
		sb.append(" update best_student set contents = ?, mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)  ");
		sb.append(" where system_code= ? and curri_code=? and curri_year=? and curri_term=? and student_id=? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(bestStudentDto.getContents());
		sql.setString(bestStudentDto.getModId());
		sql.setString(bestStudentDto.getSystemCode());
		sql.setString(bestStudentDto.getCurriCode());
		sql.setInteger(bestStudentDto.getCurriYear());
		sql.setInteger(bestStudentDto.getCurriTerm());
		sql.setString(bestStudentDto.getStudentId());

		log.debug("[editBestStudent]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 우수학생의 등록을 취소한다
	 * 2007.03.20 sangsang
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param studentId
	 * @return
	 * @throws DAOException
	 */
	public int deleteBestStudent(String systemCode, String curriCode, int curriYear, int curriTerm, String studentId) throws DAOException {
		int retVal = 0;
		StringBuffer sb = new StringBuffer();
		sb.append(" delete best_student where system_code= ? and curri_code=? and curri_year=? and curri_term=? and student_id=? ");

		QueryStatement sql = new QueryStatement();
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(studentId);
		sql.setSql(sb.toString());

		log.debug("[deleteBestStudent]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}