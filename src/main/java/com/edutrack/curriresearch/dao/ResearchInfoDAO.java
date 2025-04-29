/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.curriresearch.dto.CurriResInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchInfoDAO  extends AbstractDAO {

	/**
	 *
	 */
	public ResearchInfoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 설문정보를 등록한다.
	 * @param systemcode
	 * @param curriInfo
	 * @param resinfo
	 * @return
	 * @throws DAOException
	 */
	public int addResearchInfo(String systemcode,CurriMemDTO curriInfo, CurriResInfoDTO resinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into curri_res_info(system_code,curri_code,curri_year,curri_term,res_id,subject,contents,start_date,end_date,open_yn,reg_id,reg_date) ");
		 sb.append(" select ?,?,?,?,ifnull(max(res_id),0)+1 res_id,?,?,?,?,?,?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from curri_res_info ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year  = ? and curri_term = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(resinfo.getSubject());
		 sql.setString(resinfo.getContents());
		 sql.setString(resinfo.getStartDate());
		 sql.setString(resinfo.getEndDate());
		 sql.setString(resinfo.getOpenYn());
		 sql.setString(resinfo.getRegId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setString(curriInfo.curriYear);
		 sql.setString(curriInfo.curriTerm);

		 log.debug("[addResearchInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 설문리스트와 정보를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param courseid
	 * @param resid
	 * @return
	 * @throws DAOException
	 */
	public RowSet getResearchInfoList(String systemcode,CurriMemDTO curriinfo,int resid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select res_id,subject,contents,start_date,end_date,open_yn,reg_id,reg_date ");
			sb.append(" from curri_res_info ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year =? and curri_term =? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);

			if(resid > 0){
				sb.append(" and res_id = ? ");
				sql.setInteger(resid);
			}

			sb.append(" order by res_id desc ");

			sql.setSql(sb.toString());

			log.debug("[getResearchInofList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	/**
	 * 설문정보를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param resid
	 * @return
	 * @throws DAOException
	 */
	public CurriResInfoDTO getResearchInfo(String systemcode,CurriMemDTO curriinfo ,int resid) throws DAOException{
		CurriResInfoDTO resInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
            rs = getResearchInfoList(systemcode, curriinfo, resid);
			resInfo = new CurriResInfoDTO();
			if(rs.next()){
				resInfo.setResId(rs.getInt("res_id"));
				resInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
				resInfo.setContents(StringUtil.nvl(rs.getString("contents")));
				resInfo.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				resInfo.setEndDate(StringUtil.nvl(rs.getString("end_date")));
				resInfo.setOpenYn(StringUtil.nvl(rs.getString("open_yn")));
				resInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		        resInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
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

		return resInfo;
	}

	/**
	 * 설문정보를 수정한다.
	 * @param systemcode
	 * @param curriInfo
	 * @param resinfo
	 * @return
	 * @throws DAOException
	 */
	public int editResearchInfo(String systemcode,CurriMemDTO curriInfo, CurriResInfoDTO resinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update curri_res_info ");
		 sb.append(" set subject = ?,contents = ?,start_date = ?,end_date = ?,open_yn = ?, ");
		 sb.append(" mod_id = ?,mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year  = ? and curri_term = ? and res_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(resinfo.getSubject());
		 sql.setString(resinfo.getContents());
		 sql.setString(resinfo.getStartDate());
		 sql.setString(resinfo.getEndDate());
		 sql.setString(resinfo.getOpenYn());
		 sql.setString(resinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setInteger(resinfo.getResId());

		 log.debug("[editResearchInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public int editResearchOpenYn(String systemcode,CurriMemDTO curriInfo, CurriResInfoDTO resinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update curri_res_info ");
		 sb.append(" set open_yn = ?,mod_id = ?,mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year  = ? and curri_term = ? and res_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(resinfo.getOpenYn());
		 sql.setString(resinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setInteger(resinfo.getResId());

		 log.debug("[editResearchOpenYn]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public RowSet getResearchStInfoList(String systemcode,CurriMemDTO curriinfo,String userid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select res_id,subject,contents,start_date,end_date, ");
			sb.append(" (select count(distinct user_id) as user_cnt  from curri_res_answer where system_code = i.system_code  and curri_code = i.curri_code and curri_year = i.curri_year and curri_term = i.curri_term  and res_id = i.res_id and user_id = ? ) attend_yn ");
			sb.append(" from curri_res_info i ");
			sb.append(" where i.system_code = ? and i.curri_code = ? and i.curri_year =? and i.curri_term = ? and i.open_yn = 'Y' ");
			//sb.append(" and i.start_date <= CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
			//sb.append(" and i.end_date >= CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
			sql.setString(userid);
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setSql(sb.toString());

			log.debug("[getResearchStInfoList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}


	//
	/**
	 * 설문 답변 정보(curri_res_answer) 삭제
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param ResId
	 * @return int
	 * @throws DAOException
	 */
	public int delResAnswer(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,
			String ResId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_res_answer where system_code = ? and curri_code = ? and curri_year = ? " +
				" and curri_term = ? and res_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(ResId);

		log.debug("[delResAnswer]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 설문 문제 정보(curri_res_contents) 삭제
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param ResId
	 * @return int
	 * @throws DAOException
	 */
	public int delResContents(String SystemCode, String CurriCode, int CurriYear, int CurriTerm,
			String ResId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_res_contents where system_code = ? and curri_code = ? and curri_year = ? " +
				" and curri_term = ? and res_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(ResId);

		log.debug("[delResContents]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 설문정보(curri_res_info) 삭제
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param ResId
	 * @return int
	 * @throws DAOException
	 */
	public int delResInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String ResId)
	 throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_res_info where system_code = ? and curri_code = ? and curri_year = ? " +
				" and curri_term = ? and res_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(ResId);

		log.debug("[delResInfo]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}
