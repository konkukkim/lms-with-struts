/*
 * Created on 2006. 7. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.currisub.dao;

import javax.sql.RowSet;
import java.sql.SQLException;

import com.edutrack.currisub.dto.SinglyFinishDTO;
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
public class SinglyFinishDAO extends AbstractDAO {


	/**
	 * * 해당 개별수료과정정보 카운트를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getTotalSinglyFinishCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException{
		 int  sfcnt = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(sf_id) as sfcnt ");
		 sb.append(" from singly_finish ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 log.debug("[getTotalSinglyFinishCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	sfcnt = rs.getInt("sfcnt");
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

		 return sfcnt;
	}

	/**
	 * * 전체 개별수료과정정보 ID 최대값을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSinglyFinishId(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException{
		 int  maxOrder = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(sf_id),0)+1 as max_order ");
		 sb.append(" from singly_finish ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 log.debug("[getMaxSinglyFinishId]" + sql.toString());

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
	 * 전체 개별수료과정정보를 가져한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriSinglyFinishList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		SinglyFinishDTO SinglyFinishDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select system_code, curri_code, curri_year, curri_term, sf_id " +
				  ", course_name, study_start, study_end, study_where, suc_yn " +
				  ", reg_id, reg_date, mod_id, mod_date");
		sb.append(" from singly_finish ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" order by sf_id asc ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		log.debug("[getSinglyFinishList]" + sql.toString());

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
	 * 개별수료과정정보를 가져한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public SinglyFinishDTO getCurriSinglyFinishList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, int SinglyFinishId) throws DAOException {
		SinglyFinishDTO SinglyFinishDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select system_code, curri_code, curri_year, curri_term, sf_id " +
				  ", course_name, study_start, study_end, study_where, suc_yn, " +
				  ", reg_id, reg_date, mod_id, mod_date");
		sb.append(" from singly_finish ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and sf_id = ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setInteger(SinglyFinishId);
		log.debug("[getSinglyFinishList]" + sql.toString());

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				SinglyFinishDto = new SinglyFinishDTO();
				SinglyFinishDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				SinglyFinishDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				SinglyFinishDto.setCurriYear(rs.getInt("curri_year"));
				SinglyFinishDto.setCurriTerm(rs.getInt("curri_term"));
				SinglyFinishDto.setSfId(rs.getInt("sf_id"));
				SinglyFinishDto.setCourseName(StringUtil.nvl(rs.getString("course_name")));
				SinglyFinishDto.setStudyStart(StringUtil.nvl(rs.getString("study_start")));
				SinglyFinishDto.setStudyEnd(StringUtil.nvl(rs.getString("study_end")));
				SinglyFinishDto.setStudyWhere(StringUtil.nvl(rs.getString("study_where")));
				SinglyFinishDto.setSucYn(StringUtil.nvl(rs.getString("suc_yn")));
				SinglyFinishDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				SinglyFinishDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				SinglyFinishDto.setModId(StringUtil.nvl(rs.getString("mod_id")));
				SinglyFinishDto.setModDate(StringUtil.nvl(rs.getString("mod_date")));
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
		return SinglyFinishDto;
	}


	/**
	 * 개별수료과정정보를 저장한다.
	 * @param curriSinglyFinishDto
	 * @return int
	 * @throws DAOException
	 */
	public int addSinglyFinish(SinglyFinishDTO SinglyFinishDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into singly_finish(");
		sb.append(" system_code, curri_code, curri_year, curri_term, sf_id " +
				  ", course_name, study_start, study_end, study_where, suc_yn " +
				  ", reg_id, reg_date, mod_id)");
		sb.append(" values (?, ?, ?, ?, ?" +
				  ", ?, ?, ?, ?, ? " +
				  ", ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?) ");
		sql.setSql(sb.toString());

		SinglyFinishDAO SinglyFinishDao = new SinglyFinishDAO();

		int	maxSinglyFinishId	=	0;
		maxSinglyFinishId	=	SinglyFinishDao.getMaxSinglyFinishId(SinglyFinishDto.getSystemCode(), SinglyFinishDto.getCurriCode(), SinglyFinishDto.getCurriYear(), SinglyFinishDto.getCurriTerm());

		//---- 입력된 값을 가져 온다.
		sql.setString(SinglyFinishDto.getSystemCode());
		sql.setString(SinglyFinishDto.getCurriCode());
		sql.setInteger(SinglyFinishDto.getCurriYear());
		sql.setInteger(SinglyFinishDto.getCurriTerm());
		sql.setInteger(maxSinglyFinishId);
		sql.setString(SinglyFinishDto.getCourseName());
		sql.setString(SinglyFinishDto.getStudyStart());
		sql.setString(SinglyFinishDto.getStudyEnd());
		sql.setString(SinglyFinishDto.getStudyWhere());
		sql.setString(SinglyFinishDto.getSucYn());
		sql.setString(SinglyFinishDto.getRegId());
		sql.setString(SinglyFinishDto.getRegId());

		log.debug("[addSinglyFinish]" + sql.toString());
		
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 개별수료과정정보를 수정한다.
	 * @param curriQuizDto
	 * @return	int
	 * @throws DAOException
	 */
	public int editSinglyFinish(SinglyFinishDTO SinglyFinishDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update singly_finish set course_name = ?, study_start = ?, study_end = ?, study_where = ?, suc_yn = ?" +
				  ", mod_id = ?, mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and sf_id = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(SinglyFinishDto.getCourseName());
		sql.setString(SinglyFinishDto.getStudyStart());
		sql.setString(SinglyFinishDto.getStudyEnd());
		sql.setString(SinglyFinishDto.getStudyWhere());
		sql.setString(SinglyFinishDto.getSucYn());
		sql.setString(SinglyFinishDto.getModId());

		sql.setString(SinglyFinishDto.getSystemCode());
		sql.setString(SinglyFinishDto.getCurriCode());
		sql.setInteger(SinglyFinishDto.getCurriYear());
		sql.setInteger(SinglyFinishDto.getCurriTerm());
		sql.setInteger(SinglyFinishDto.getSfId());

		log.debug("[editSinglyFinish]" + sql.toString());
		
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 개별수료과정정보를 삭제한다.
	 * @param curriQuizDto
	 * @return	int
	 * @throws DAOException
	 */
	public int delSinglyFinish(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String SinglyFinishId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from singly_finish ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?");
		if (!SinglyFinishId.equals("")) {
			sb.append(" and sf_id = ? ");
		}
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		if (!SinglyFinishId.equals("")) {
			sql.setInteger(SinglyFinishId);
		}

		log.debug("[deleteSinglyFinish]" + sql.toString());
		
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


}
