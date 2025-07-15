/**
 *
 */
package com.edutrack.curridate.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.curridate.dto.CurriDateDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

public class CurriDateDAO extends AbstractDAO {

	/**
	 * 중복되는 학기가 있는지 체크
	 * @param systemCode
	 * @param hakgiTerm
	 * @return
	 * @throws DAOException
	 */
	public int checkHakgiTerm(String systemCode, int hakgiTerm) throws DAOException {
		int	retVal	=	0;

		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
		sb.append(" SELECT COUNT(*) as cnt FROM work_term ");
		sb.append(" WHERE system_code = ? AND hakgi_term = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setInteger(hakgiTerm);

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
	 * 학기일자 설정 리스트를 가져온다.
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList curriDateList(String systemCode, String pGubun, int pCurriYear, int pHakgiTerm) throws DAOException {

		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();

		sb.append(" SELECT system_code, curri_year, hakgi_term" +
				", enroll_start, enroll_end, cancel_start, cancel_end" +
				", service_start, service_end, chung_end, reg_id" +
				", reg_date, mod_id, mod_date, assessment_end" +
				", complete_end, service_yn ");
		sb.append(" FROM work_term ");
		sb.append(" WHERE system_code = ? ");
		sql.setString(systemCode);
		if(pGubun.equals("popup")) {
			sb.append(" AND service_yn = 'Y' ");
		}

		if(pCurriYear > 0) {
			sb.append(" AND curri_year = ? ");
			sql.setInteger(pCurriYear);
		}
		if(pHakgiTerm > 0) {
			sb.append(" AND (hakgi_term <= ? or hakgi_term in (21, 11)) ");
			sql.setInteger(pHakgiTerm);
		}

		sql.setSql(sb.toString());		

		ArrayList	dateList	=	null;
		RowSet		rs			=	null;
		CurriDateDTO	curriDateDto	=	null;
		try {
			rs	=	broker.executeQuery(sql);
			dateList	=	new ArrayList();
			while(rs.next()) {
				curriDateDto	=	new CurriDateDTO();
				curriDateDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				curriDateDto.setCurriYear(StringUtil.nvl(rs.getString("curri_year"), 0));
				curriDateDto.setHakgiTerm(StringUtil.nvl(rs.getString("hakgi_term"), 0));
				curriDateDto.setEnrollStart(StringUtil.nvl(rs.getString("enroll_start")));
				curriDateDto.setEnrollEnd(StringUtil.nvl(rs.getString("enroll_end")));
				curriDateDto.setCancelStart(StringUtil.nvl(rs.getString("cancel_start")));
				curriDateDto.setCancelEnd(StringUtil.nvl(rs.getString("cancel_end")));
				curriDateDto.setServiceStart(StringUtil.nvl(rs.getString("service_start")));
				curriDateDto.setServiceEnd(StringUtil.nvl(rs.getString("service_end")));
				curriDateDto.setChungEnd(StringUtil.nvl(rs.getString("chung_end")));

				curriDateDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				curriDateDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				curriDateDto.setModId(StringUtil.nvl(rs.getString("mod_id")));
				curriDateDto.setModDate(StringUtil.nvl(rs.getString("mod_date")));

				curriDateDto.setAssessmentEnd(StringUtil.nvl(rs.getString("assessment_end")));
				curriDateDto.setCompleteEnd(StringUtil.nvl(rs.getString("complete_end")));
				curriDateDto.setServiceYn(StringUtil.nvl(rs.getString("service_yn")));
				dateList.add(curriDateDto);
			}

		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return dateList;
	}

	/**
	 * 학기일자 정보를 받아온다.
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public CurriDateDTO getCurriDateInfo(String systemCode, int curriYear, int hakgiTerm, String addWhere) throws DAOException {
		CurriDateDTO	curriDateDto	=	null;
		RowSet			rs				=	null;

		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();

		sb.append(" SELECT system_code, curri_year, hakgi_term" +
				", enroll_start, enroll_end, cancel_start, cancel_end" +
				", service_start, service_end, chung_end, reg_id" +
				", reg_date, mod_id, mod_date, assessment_end" +
				", complete_end, service_yn ");
		sb.append(" FROM work_term ");
		sb.append(" WHERE system_code = ? AND curri_year = ? ");
		//sb.append(" AND service_yn = 'Y'  ");
		sql.setString(systemCode);
		sql.setInteger(curriYear);
		
		if(hakgiTerm > 0) {
			sb.append(" AND hakgi_term = ? ");
			sql.setInteger(hakgiTerm);
		}
		if(!addWhere.equals("")) {
			sb.append(addWhere);	
		}
		
		sql.setSql(sb.toString());
		
		log.debug("[getCurriDateInfo] : "+sql.toString());
		try {
			rs	=	broker.executeQuery(sql);

			if(rs.next()) {
				curriDateDto	=	new CurriDateDTO();
				curriDateDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				curriDateDto.setCurriYear(StringUtil.nvl(rs.getString("curri_year"), 0));
				curriDateDto.setHakgiTerm(StringUtil.nvl(rs.getString("hakgi_term"), 0));
				curriDateDto.setEnrollStart(StringUtil.nvl(rs.getString("enroll_start")));
				curriDateDto.setEnrollEnd(StringUtil.nvl(rs.getString("enroll_end")));
				curriDateDto.setCancelStart(StringUtil.nvl(rs.getString("cancel_start")));
				curriDateDto.setCancelEnd(StringUtil.nvl(rs.getString("cancel_end")));
				curriDateDto.setServiceStart(StringUtil.nvl(rs.getString("service_start")));
				curriDateDto.setServiceEnd(StringUtil.nvl(rs.getString("service_end")));
				curriDateDto.setChungEnd(StringUtil.nvl(rs.getString("chung_end")));

				curriDateDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				curriDateDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				curriDateDto.setModId(StringUtil.nvl(rs.getString("mod_id")));
				curriDateDto.setModDate(StringUtil.nvl(rs.getString("mod_date")));

				curriDateDto.setAssessmentEnd(StringUtil.nvl(rs.getString("assessment_end")));
				curriDateDto.setCompleteEnd(StringUtil.nvl(rs.getString("complete_end")));
				curriDateDto.setServiceYn(StringUtil.nvl(rs.getString("service_yn")));
			}

		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return curriDateDto;
	}

	/**
	 * CURRI_TERM 맥스값 가져오기
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCnt(String	systemCode) throws DAOException {
		int	retVal	=	0;
		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();
		sb.append(" SELECT IFNULL(MAX(curri_term), 0) + 1 AS max_cnt ");
		sb.append(" FROM work_term ");
		sb.append(" WHERE system_code = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);

		RowSet	rs	=	null;
		try {
			rs	=	broker.executeQuery(sql);
			if(rs.next()) {
				retVal	=	rs.getInt("max_cnt");
			}

		} catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		} finally {
			 try{
			  if (rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		}
		return retVal;
	}

	/**
	 * 학기일자 등록
	 * @param curriDateDto
	 * @return
	 * @throws DAOException
	 */
	public int insertCurriDate(CurriDateDTO curriDateDto) throws DAOException {
		int	retVal	=	0;
		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();

		sb.append(" INSERT INTO work_term (system_code, curri_year, hakgi_term" +
										", enroll_start, enroll_end, cancel_start, cancel_end" +
										", service_start, service_end, chung_end, reg_id" +
										", reg_date, assessment_end, complete_end, service_yn) ");
		sb.append(" VALUES (?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?, ?, ?) ");

		sql.setSql(sb.toString());

		sql.setString(StringUtil.nvl(curriDateDto.getSystemCode()));
		sql.setInteger(curriDateDto.getCurriYear());
		sql.setInteger(curriDateDto.getHakgiTerm());
		sql.setString(StringUtil.nvl(curriDateDto.getEnrollStart()));
		sql.setString(StringUtil.nvl(curriDateDto.getEnrollEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getCancelStart()));
		sql.setString(StringUtil.nvl(curriDateDto.getCancelEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getServiceStart()));
		sql.setString(StringUtil.nvl(curriDateDto.getServiceEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getChungEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getRegId()));

		sql.setString(StringUtil.nvl(curriDateDto.getAssessmentEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getCompleteEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getServiceYn()));

		log.debug("============[insertCurriDate] : "+sql.toString());

		try {

			retVal	=	broker.executeUpdate(sql);

		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 학기일자 수정
	 * @param curriDateDto
	 * @return
	 * @throws DAOException
	 */
	public int editCurriDate(CurriDateDTO curriDateDto) throws DAOException {
		int	retVal	=	0;
		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();

		sb.append(" UPDATE work_term SET enroll_start = ?, enroll_end = ? " +
									", cancel_start = ?, cancel_end = ? " +
									", service_start = ?, service_end = ? " +
									", chung_end = ?, mod_id = ? " +
									", mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), assessment_end = ? " +
									", complete_end = ?, service_yn = ? ");
		sb.append(" WHERE system_code = ? AND curri_year = ? AND hakgi_term = ? ");

		sql.setSql(sb.toString());

		sql.setString(StringUtil.nvl(curriDateDto.getEnrollStart()));
		sql.setString(StringUtil.nvl(curriDateDto.getEnrollEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getCancelStart()));
		sql.setString(StringUtil.nvl(curriDateDto.getCancelEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getServiceStart()));
		sql.setString(StringUtil.nvl(curriDateDto.getServiceEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getChungEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getModId()));
		sql.setString(StringUtil.nvl(curriDateDto.getAssessmentEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getCompleteEnd()));
		sql.setString(StringUtil.nvl(curriDateDto.getServiceYn()));

		sql.setString(StringUtil.nvl(curriDateDto.getSystemCode()));
		sql.setInteger(curriDateDto.getCurriYear());
		sql.setInteger(curriDateDto.getHakgiTerm());

		log.debug("============[editCurriDate] : "+sql.toString());

		try {
			retVal	=	broker.executeUpdate(sql);
		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public String getDateFlag(String systemCode, String colName1, String colName2, String inputDate) throws DAOException {
		String retVal = "";
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
		sb.append(" SELECT CASE WHEN ");
		if (!colName1.equals(""))
			sb.append(String.valueOf(colName1) + " <= '" + inputDate + "' ");
		if (!colName2.equals(""))
			sb.append(" AND " + colName2 + " >= '" + inputDate + "' ");
		sb.append(" THEN 'Y' ELSE 'N' END AS date_yn ");
		sb.append(" FROM work_term ");
		sb.append(" WHERE system_code = ? AND service_yn = 'Y' ");
		sql.setString(systemCode);
		sb.append(" ORDER BY " + colName1 + " desc ");
		sb.append(" limit 1 ");
		sql.setSql(sb.toString());
		this.log.debug(" [getDateFlag] : " + sql.toString());
		RowSet rs = null;
		try {
			rs = this.broker.executeQuery(/*(ISqlStatement)*/sql);
			if (rs.next())
				retVal = rs.getString("date_yn");
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
		return retVal;
	}

}
