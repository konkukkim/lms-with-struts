/*
 * Created on 2005. 8. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.currisub.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.currisub.dto.CurriSubDTO;
import com.edutrack.currisub.dto.CurriSubInfoDTO;
import com.edutrack.curritop.dto.QuizDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author soon
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriSaleInfoDAO  extends AbstractDAO{
	/**
	 * 해당 개설과정의 단체 할인 정보 갯수를 리턴한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */
	public int getSaleCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		int infoCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(curri_code),0) as cnt from curri_sale_info");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		
		log.debug("[getInfoCount]" + sql.toString());
		 
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	infoCount = rs.getInt("cnt");
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
		return infoCount;
	}
	
	/**
	 * 개설과정의 단체 할인 정보 목록을 갸져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriSaleInfoList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();		
		
		sb.append(" select from_cnt , to_cnt , sale_fee from curri_sale_info  ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
			
		//---- 디버그 출력
		log.debug("[getCurriSaleInfoList]" + sql.toString());
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
	 * 개설과정 단체 할인 정보를 입력 한다.
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param fromCnt
	 * @param toCnt
	 * @param saleFee
	 * @param regId
	 * @return
	 * @throws DAOException
	 */
	public int addCurriSaleInfo(String systemCode, String curriCode, int curriYear, int curriTerm,  int fromCnt, int toCnt, int saleFee, String regId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into curri_sale_info(system_code, curri_code, curri_year, curri_term"+
				", from_cnt, to_cnt, sale_fee, reg_id, reg_date, mod_id, mod_date)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());
		
		
		//---- 입력된 값을 가져 온다.
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setInteger(fromCnt);
		sql.setInteger(toCnt);
		sql.setInteger(saleFee);
		sql.setString(regId);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(regId);
		sql.setString(CommonUtil.getCurrentDate());
		
		//---- 디버그 출력
		log.debug("[addCurriSaleInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}
	
	/**
	 * 개설과정 단체 할인정보를 삭제 한다.
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws DAOException
	 */
	public int delCurriSaleInfo(String systemCode, String curriCode, int curriYear, int curriTerm) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from curri_sale_info  ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		
		//---- 디버그 출력
		log.debug("[delCurriSaleInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}
}
