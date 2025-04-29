package com.edutrack.code.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.code.dto.CodeDaeDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;

/*
 * @author JamFam
 * 
 * 대코드 관리
 */
public class CodeDaeDAO extends AbstractDAO {
	/**
	 * 
	 */
	public CodeDaeDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 대코드의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode) throws DAOException {
		int totCount = 0;
		
		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(code_dae) as cnt from code_dae where system_code= ?");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		
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
	 * 대코드의 카운트를 가져온다.(중복 체크용)
	 * @param SystemCode
	 * @param CodeDae
	 * @return
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CodeDae) throws DAOException {
		int totCount = 0;
		
		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(code_dae) as cnt from code_dae where system_code= ? and code_dae = ?");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CodeDae);
		
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
	 * 대코드 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCodeDaeList(int curpage, String SystemCode, String column, String orderBy) throws DAOException {
		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("code_dae");
			sql.setCutCol("code_dae");
			sql.setAlias("code_dae, dae_name, code_comment, use_yn, use_name , reg_id, reg_date ");
			sql.setSelect("code_dae, dae_name, code_comment, use_yn, case use_yn when 'Y' then '사용' else '사용안함' end as use_name , reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date "); 
			sql.setFrom(" code_dae ");
			sql.setWhere(" system_code='"+SystemCode+"'");
			sql.setOrderby(column+" "+orderBy);
			
			//---- 디버그 출력
			log.debug("[getCodeDaeList]" + sql.toString());
			
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
	 * 개별 대코드 정보를 가져온다.
	 * @param SystemCode
	 * @param CodeDae
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCodeDaeInfo(String SystemCode, String CodeDae) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, code_dae, dae_name, code_comment, use_yn, reg_id, reg_date ");
		sb.append(" ,(select ifnull(count(code_dae),0) as cnt from code_so where system_code = a.system_code and code_dae = a.code_dae) as code_so_cnt ");
		sb.append(" from code_dae a");
		sb.append(" where system_code=? and code_dae=? ");
		
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CodeDae);
		
		//---- 디버그 출력
		log.debug("[getCodeDaeInfo]" + sql.toString());
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
	 * 대코드 정보를 저장한다.
	 * @param codeDaeInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCodeDaeInfo(CodeDaeDTO codeDaeInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into code_dae(system_code, code_dae, dae_name, code_comment,"+
				" use_yn, reg_id, reg_date, mod_id, mod_date)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());
		
		//---- 입력된 값을 가져 온다.
		sql.setString(codeDaeInfo.getSystemCode());
		sql.setString(codeDaeInfo.getCodeDae());
		sql.setString(codeDaeInfo.getDaeName());
		sql.setString(codeDaeInfo.getXcomment());
		sql.setString(codeDaeInfo.getUseYn());
		sql.setString(codeDaeInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(codeDaeInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		
		//---- 디버그 출력
		log.debug("[addCodeDaeInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}

	/**
	 * 대코드 정보를 수정한다.
	 * @param codeDaeInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCodeDaeInfo(CodeDaeDTO codeDaeInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update code_dae set dae_name=?, code_comment=?, use_yn=?, mod_id=?, mod_date=?");
		sb.append(" where system_code=? and code_dae=?");
		sql.setSql(sb.toString());
		
		//---- 입력된 값을 가져 온다.
		sql.setString(codeDaeInfo.getDaeName());
		sql.setString(codeDaeInfo.getXcomment());
		sql.setString(codeDaeInfo.getUseYn());
		sql.setString(codeDaeInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(codeDaeInfo.getSystemCode());
		sql.setString(codeDaeInfo.getCodeDae());
		
		//---- 디버그 출력
		log.debug("[editCodeDaeInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}

	/**
	 * 대코드 정보를 삭제한다.
	 * @param SystemCode
	 * @param CodeDae
	 * @return
	 * @throws DAOException
	 */
	public int delCodeDaeInfo(String SystemCode, String CodeDae) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from code_dae where system_code=? and code_dae=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CodeDae);	
			
		//---- 디버그 출력
		log.debug("[delCodeDaeInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;
	}		
	
	/**
	 * code_dae 중복체크
	 * 2007.03.27 sangsang
	 * @param systemCode
	 * @param codeDae
	 * @param codeSo
	 * @return
	 * @throws DAOException
	 */
	public int getCodeDaeCount(String systemCode, String codeDae) throws DAOException {
		int codeDaeCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(code_dae),0) as cnt from code_dae ");
		sb.append(" where system_code = ? and code_dae = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(codeDae);
		
		log.debug("[getCodeDaeCount]" + sql.toString());
		 
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