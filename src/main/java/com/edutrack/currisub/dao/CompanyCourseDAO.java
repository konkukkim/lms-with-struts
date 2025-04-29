/*
 * Created on 2006. 7. 11.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.currisub.dao;

import javax.sql.RowSet;
import java.sql.SQLException;

import com.edutrack.currisub.dto.CompanyCourseDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


/**
 * @author narcisus
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompanyCourseDAO extends AbstractDAO {

	/**
	 * * 기업과정 추가부분  ID 최대값을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCompanyCourseId(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException{
		 int  maxOrder = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(company_course_id),0)+1 as max_order ");
		 sb.append(" from company_course ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 log.debug("[getMaxCompanyCourseId]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	maxOrder = rs.getInt("max_order");
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

		 return maxOrder;
	}

	/**
	 * 전체 기업과정 추가부분 를 가져한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriCompanyCourseList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		CompanyCourseDTO companyCourseDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select system_code, curri_code, curri_year, curri_term, company_course_id " +
				  ", dept_daecode, dept_socode, company_img, reg_id, reg_date");
		sb.append(" from company_course ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		log.debug("[getCompanyCourseList]" + sql.toString());

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
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
		return rs;
	}

	/**
	 * 기업과정 추가부분 를 가져한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public CompanyCourseDTO getCurriCompanyCourseList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String companyCourseId) throws DAOException {
		CompanyCourseDTO companyCourseDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select system_code, curri_code, curri_year, curri_term, company_course_id " +
				  ", dept_daecode, dept_socode, company_img, reg_id, reg_date");
		sb.append(" from company_course ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?");

		if (!companyCourseId.equals("")) {
			sb.append(" and company_course_id = ? ");
		}
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		if (!companyCourseId.equals("")) {
			sql.setInteger(companyCourseId);
		}
		log.debug("[getCompanyCourseList]" + sql.toString());

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				companyCourseDto = new CompanyCourseDTO();
				companyCourseDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				companyCourseDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				companyCourseDto.setCurriYear(rs.getInt("curri_year"));
				companyCourseDto.setCurriTerm(rs.getInt("curri_term"));
				companyCourseDto.setCompanyCourseId(rs.getInt("company_course_id"));
				companyCourseDto.setDeptDaeCode(StringUtil.nvl(rs.getString("dept_daecode")));
				companyCourseDto.setDeptSoCode(StringUtil.nvl(rs.getString("dept_socode")));
				companyCourseDto.setCompanyImg(StringUtil.nvl(rs.getString("company_img")));
				companyCourseDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				companyCourseDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
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
		return companyCourseDto;
	}


	/**
	 * 기업과정 추가부분 를 저장한다.
	 * @param curriCompanyCourseDto
	 * @return int
	 * @throws DAOException
	 */
	public int addCompanyCourse(CompanyCourseDTO companyCourseDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into company_course(");
		sb.append(" system_code, curri_code, curri_year, curri_term, company_course_id " +
				  ", dept_daecode, dept_socode, company_img, reg_id, reg_date) ");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))");
		sql.setSql(sb.toString());

		CompanyCourseDAO companyCourseDao = new CompanyCourseDAO();

		int	maxCompanyCourseId	=	0;
		maxCompanyCourseId	=	companyCourseDao.getMaxCompanyCourseId(companyCourseDto.getSystemCode(), companyCourseDto.getCurriCode(), companyCourseDto.getCurriYear(), companyCourseDto.getCurriTerm());

		//---- 입력된 값을 가져 온다.
		sql.setString(companyCourseDto.getSystemCode());
		sql.setString(companyCourseDto.getCurriCode());
		sql.setInteger(companyCourseDto.getCurriYear());
		sql.setInteger(companyCourseDto.getCurriTerm());
		sql.setInteger(maxCompanyCourseId);
		sql.setString(companyCourseDto.getDeptDaeCode());
		sql.setString(companyCourseDto.getDeptSoCode());
		sql.setString(companyCourseDto.getCompanyImg());
		sql.setString(companyCourseDto.getRegId());

		log.debug("[addCompanyCourse]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 기업과정 추가부분 를 수정한다.
	 * @param curriQuizDto
	 * @return	int
	 * @throws DAOException
	 */
	public int editCompanyCourse(CompanyCourseDTO companyCourseDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update company_course set dept_daecode = ?, dept_socode = ?, company_img = ? ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and company_course_id = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(companyCourseDto.getDeptDaeCode());
		sql.setString(companyCourseDto.getDeptSoCode());
		sql.setString(companyCourseDto.getCompanyImg());
		sql.setString(companyCourseDto.getSystemCode());
		sql.setString(companyCourseDto.getCurriCode());
		sql.setInteger(companyCourseDto.getCurriYear());
		sql.setInteger(companyCourseDto.getCurriTerm());
		sql.setInteger(companyCourseDto.getCompanyCourseId());

		log.debug("[editCompanyCourse]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 기업과정 추가부분 를 삭제한다.
	 * @param curriQuizDto
	 * @return	int
	 * @throws DAOException
	 */
	public int delCompanyCourse(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CompanyCourseId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from company_course ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");
		if (!CompanyCourseId.equals("")) {
			sb.append(" and company_course_id = ? ");
		}
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		if (!CompanyCourseId.equals("")) {
			sql.setInteger(CompanyCourseId);
		}

		log.debug("[deleteCompanyCourse]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}
