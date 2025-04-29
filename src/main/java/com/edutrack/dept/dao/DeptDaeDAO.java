/*
 * Created on 2004. 10. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.dept.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.dept.dto.DeptDaeCodeDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author sej
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeptDaeDAO extends AbstractDAO {

	/**
	 *
	 */
	public DeptDaeDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

   /**
   * 소속 코드의 갯수를 가져온다.
   * @param systemcode
   * @return count
   * @throws DAOException
   */
	public int getTotCount(String systemcode) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(dept_daecode) as cnt ");
		 sb.append(" from dept_daecode ");
		 sb.append(" where system_code = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);

		 log.debug("[getTotCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				retVal = rs.getInt("cnt");
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

		 return retVal;
	}


	/**
	 * 동일한 코드가 있는가 체크한다.
	 * @param SystemCode
	 * @param deptDaecode
	 * @return count
	 * @thrwos DAOException
	 */
	public int chkDaecode(String SystemCode, String DeptDaecode) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();

		StringBuffer sb = new StringBuffer();
		sb.append(" select count(dept_daecode) as cnt ");
		sb.append(" from dept_daecode ");
		sb.append(" where system_code = ? and dept_daecode= ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(DeptDaecode);

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if(rs.next()){
				retVal = rs.getInt("cnt");
			}
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			 try {
			 	if(rs != null) rs.close();
			 } catch (SQLException e){
			 	throw new DAOException(e.getMessage());
			 }
		}
		return retVal;
	}


	/**
	 * 소속코드 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getDeptDaeList(int curpage, String systemCode, String column, String orderBy) throws DAOException {
		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("dept_daecode");
			sql.setCutCol("dept_daecode");
			sql.setAlias("dept_daecode, dept_daename, use_yn,  use_name, reg_id, reg_date");
			sql.setSelect("dept_daecode, dept_daename, use_yn, case use_yn when 'Y' then '사용' else '사용안함' end as use_name, reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date");
			sql.setFrom(" dept_daecode");

			StringBuffer sbWhere = new StringBuffer();
			 sbWhere.append(" system_code = ?  ");
			 sql.setString(systemCode);

			sql.setWhere(sbWhere.toString());
			sql.setOrderby(column+" "+orderBy);

			//---- 디버그 출력
			log.debug("[getDeptDaeList]" + sql.toString());

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
	 * 소속코드 리스트를 가져온다.
	 * @param SystemCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getDeptDaeList(String SystemCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, dept_daecode, dept_daename from dept_daecode where system_code=? and use_yn='Y' ");
		sb.append(" order by dept_daename desc");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		log.debug("[getDeptDaeList]" + sql.toString());
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
	 * 소속코드 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addDeptDaeInfo(DeptDaeCodeDTO deptDaeInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into dept_daecode(system_code, dept_daecode, dept_daename,"+
				" use_yn, reg_id, reg_date, mod_id, mod_date)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(deptDaeInfo.getSystemCode());
		sql.setString(deptDaeInfo.getDeptDaecode());
		sql.setString(deptDaeInfo.getDeptDaename());
		sql.setString(deptDaeInfo.getUseYn());
		sql.setString(deptDaeInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(deptDaeInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		//---- 디버그 출력
		log.debug("[addDeptDaeInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 소속코드 정보를 수정한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int editDeptDaeInfo(DeptDaeCodeDTO deptDaeInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update dept_daecode set dept_daename=?, use_yn=?, mod_id=?, mod_date=?");
		sb.append(" where system_code=? and dept_daecode=?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(deptDaeInfo.getDeptDaename());
		sql.setString(deptDaeInfo.getUseYn());
		sql.setString(deptDaeInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(deptDaeInfo.getSystemCode());
		sql.setString(deptDaeInfo.getDeptDaecode());

		//---- 디버그 출력
		log.debug("[editDeptDaeInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 소속코드 정보를 가져온다.
	 * @param SystemCode
	 * @param DeptDae
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getDeptDaeInfo(String SystemCode, String DeptDae) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, dept_daecode, dept_daename, use_yn, reg_id, reg_date ");
		sb.append(" ,(select ifnull(count(dept_daecode),0) as cnt from dept_socode where system_code = a.system_code and dept_daecode = a.dept_daecode) as dept_so_cnt ");
		sb.append(" from dept_daecode a ");
		sb.append(" where system_code = ? and dept_daecode = ? ") ;

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(DeptDae);
		log.debug("[getDeptDaeInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


 /**
  * 소속코드 정보를 삭제한다.
  * @param SystemCode
  * @param DeptDaecode
  * @return
  * @throws DAOException
  */
	public int delDeptDaeInfo(String SystemCode, String DeptDaecode) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from dept_daecode where system_code = ? and dept_daecode = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(DeptDaecode);

		log.debug("[delDeptDaeInfo]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 소속코드 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
/*	public ArrayList getMemberList(String SystemCode, String DeptDaeCode, String DeptSoCode ) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.SYSTEM_CODE, B.USER_ID, B.USER_NAME, A.CURRI_CODE, A.CURRI_YEAR, A.CURRI_TERM ");
		sb.append(",( SELECT CURRI_PROPERTY2 FROM CURRI_TOP WHERE SYSTEM_CODE=A.SYSTEM_CODE AND CURRI_CODE=A.CURRI_CODE ) AS CURRI_TYPE");
		sb.append(",( SELECT CURRI_NAME      FROM CURRI_SUB WHERE SYSTEM_CODE=A.SYSTEM_CODE AND CURRI_CODE=A.CURRI_CODE AND CURRI_YEAR=A.CURRI_YEAR AND CURRI_TERM=A.CURRI_TERM  ) AS CURRI_NAME");
		sb.append(",A.ENROLL_NO     ");
		sb.append(",A.ENROLL_STATUS ");
		sb.append(",A.SERVICESTART_DATE");
		sb.append(",A.SERVICEEND_DATE ");
		sb.append(",A.CHUNGEND_DATE ");
		//sb.append(",A.TEAM_NO       ");
		sb.append(",A.ENROLL_DATE   ");
		sb.append(",A.CANCEL_DATE   ");
		sb.append(",A.COMPLETE_DATE ");
		sb.append(",A.COMPLETE_NO   ");
		sb.append(",A.GET_CREDIT    ");
		sb.append(",A.FIRST_CON     ");
		sb.append(",A.LAST_CON      ");
		sb.append("FROM ( SELECT SYSTEM_CODE, USER_ID , USER_NAME  ");
		sb.append("         FROM USERS                ");
		sb.append("        WHERE SYSTEM_CODE  = ? ");
		sb.append("          AND DEPT_DAECODE = ? ");
		sb.append("          AND DEPT_SOCODE  = ? ");
		sb.append("          AND USER_TYPE  = 'S'");
		sb.append("      ) B LEFT OUTER JOIN ");
		sb.append("   STUDENT A ");
		sb.append("ON A.SYSTEM_CODE = B.SYSTEM_CODE ");
		sb.append("  AND A.USER_ID  = B.USER_ID ");
		sb.append("  AND A.ENROLL_STATUS <> 'N'");

		sql.setSql(sb.toString());

		sql.setString(SystemCode);
		sql.setString(DeptDaeCode);
		sql.setString(DeptSoCode);

		RowSet rs = null;
		StudentDTO studentList = null;
	    ArrayList memberList	= 	new ArrayList();
        String preUserId  ="";
        String curUserId  ="";
        String curUserName  ="";
        log.debug("[getMemberList=======================>]" + sql.toString());

	    try {

	        rs 		= 	broker.executeQuery(sql);

			while(rs.next()){
			    studentList	= 	new StudentDTO();

			    curUserId    = StringUtil.nvl(rs.getString("USER_ID"));
			    curUserName  = StringUtil.nvl(rs.getString("USER_NAME"));

			    if( preUserId.equals( curUserId )) curUserName = "";

			    studentList.setUserId           (curUserId   );
			    studentList.setUserName         (curUserName );
			    studentList.setCurriCode        (StringUtil.nvl(rs.getString("CURRI_CODE")));
			    studentList.setCurriYear        (rs.getInt   ("CURRI_YEAR"));
			    studentList.setCurriTerm        (rs.getInt   ("CURRI_TERM"));
			    studentList.setCurriName        (StringUtil.nvl(rs.getString("CURRI_NAME")));
			    studentList.setCurriType        (StringUtil.nvl(rs.getString("CURRI_TYPE")));
			    studentList.setEnrollNo         (rs.getInt("ENROLL_NO"));
			    studentList.setEnrollStats      (StringUtil.nvl(rs.getString("ENROLL_STATUS")));
			    studentList.setServicestartDate (StringUtil.nvl(rs.getString("SERVICESTART_DATE")));
			    studentList.setServiceendDate   (StringUtil.nvl(rs.getString("SERVICEEND_DATE")));
			    studentList.setChungendDate     (StringUtil.nvl(rs.getString("CHUNGEND_DATE")));
			    //studentList.setTeamNo           (StringUtil.nvl(rs.getString("TEAM_NO")));
			    studentList.setEnrollDate       (StringUtil.nvl(rs.getString("ENROLL_DATE")));
			    studentList.setCancelDate       (StringUtil.nvl(rs.getString("CANCEL_DATE")));
			    studentList.setCompleteDate     (StringUtil.nvl(rs.getString("COMPLETE_DATE")));
			    studentList.setCompleteNo       (StringUtil.nvl(rs.getString("COMPLETE_NO")));
			    studentList.setGetCredit        (StringUtil.nvl(rs.getString("GET_CREDIT")));
			    studentList.setFirstCon         (StringUtil.nvl(rs.getString("FIRST_CON")));
			    studentList.setLastCon          (StringUtil.nvl(rs.getString("LAST_CON")));
			    //studentList.setPaymentIdx       (rs.getInt("PAYMENT_IDX"));

			    preUserId = curUserId ;

			    memberList.add(studentList);

			}

		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return memberList;
	}
	*/

	/**
	 * dept_daecode 중복체크
	 * 2007.03.27 sangsang
	 * @param systemCode
	 * @param deptDae
	 * @return
	 * @throws DAOException
	 */
	public int getDeptDaeCount(String systemCode, String deptDae) throws DAOException {
		int codeDaeCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(dept_daecode),0) as cnt from dept_daecode ");
		sb.append(" where system_code = ? and dept_daecode = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(deptDae);

		log.debug("[getDeptDaeCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 codeDaeCount = rs.getInt("cnt");
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
		 return codeDaeCount;
	}
}
