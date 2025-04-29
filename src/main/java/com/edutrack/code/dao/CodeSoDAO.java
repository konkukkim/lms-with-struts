package com.edutrack.code.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.code.dto.CodeSoDTO;
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
public class CodeSoDAO extends AbstractDAO {
	/**
	 * 
	 */
	public CodeSoDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 소코드의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CodeDae) throws DAOException {
		int totCount = 0;
		
		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(code_so) as cnt from code_so where system_code= ? and code_dae= ?");
		 
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
	 * 소코드의 카운트를 가져온다.(중복체크용)
	 * @param SystemCode
	 * @param CodeDae
	 * @param CodeSo
	 * @return
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CodeDae, String CodeSo) throws DAOException {
		int totCount = 0;
		
		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(code_so) as cnt from code_so where system_code= ? and code_dae= ? and code_so = ?");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CodeDae);
		 sql.setString(CodeSo);
		
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
	 * 소코드 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCodeSoList(int curpage, String SystemCode, String CodeDae, String column, String orderBy) throws DAOException {
		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("code_so");
			sql.setCutCol("code_so");
			sql.setAlias("code_dae, code_so, so_name, code_comment, use_yn, use_name , reg_id, reg_date");
			sql.setSelect("code_dae, code_so, so_name, code_comment, use_yn, case use_yn when 'Y' then '사용' else '사용안함' end as use_name , reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date");
			sql.setFrom(" code_so");
			sql.setWhere(" system_code='"+SystemCode+"' and code_dae='"+CodeDae+"'");
			sql.setOrderby(column+" "+orderBy);
			
			//---- 디버그 출력
			log.debug("[getCodeSoList]" + sql.toString());
			
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
	 * 소코드전체 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public RowSet getCodeSoListAll(String SystemCode, String CodeDae) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, code_dae, code_so, so_name from code_so where system_code=? and code_dae=? and use_yn='Y' order by code_dae");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CodeDae);
		log.debug("[getCodeSoListAll]" + sql.toString());
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
	 * 개별 소코드 정보를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCodeSoInfo(String SystemCode, String CodeDae, String CodeSo) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, code_dae, code_so, so_name, code_comment, use_yn, reg_id, reg_date");
		sb.append(" from code_so");
		sb.append(" where system_code=? and code_dae=? and code_so=?");
		
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CodeDae);
		sql.setString(CodeSo);
		
		//---- 디버그 출력
		log.debug("[getCodeSoInfo]" + sql.toString());
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
	 * 소코드 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCodeSoInfo(CodeSoDTO codeSoInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into code_so (system_code, code_dae, code_so, so_name, code_comment,"+
				" use_yn, reg_id, reg_date, mod_id, mod_date)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());
		
		//---- 입력된 값을 가져 온다.
		sql.setString(codeSoInfo.getSystemCode());
		sql.setString(codeSoInfo.getCodeDae());
		sql.setString(codeSoInfo.getCodeSo());
		sql.setString(codeSoInfo.getSoName());
		sql.setString(codeSoInfo.getXcomment());
		sql.setString(codeSoInfo.getUseYn());
		sql.setString(codeSoInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(codeSoInfo.getRegId());
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
	 * 소코드 정보를 수정한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCodeSoInfo(CodeSoDTO codeSoInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update code_so set so_name=?, code_comment=?, use_yn=?, mod_id=?, mod_date=?");
		sb.append(" where system_code=? and code_dae=? and code_so=?");
		sql.setSql(sb.toString());
		
		//---- 입력된 값을 가져 온다.
		sql.setString(codeSoInfo.getSoName());
		sql.setString(codeSoInfo.getXcomment());
		sql.setString(codeSoInfo.getUseYn());
		sql.setString(codeSoInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(codeSoInfo.getSystemCode());
		sql.setString(codeSoInfo.getCodeDae());
		sql.setString(codeSoInfo.getCodeSo());
		
		//---- 디버그 출력
		log.debug("[editCodeSoInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;   
	}

	/**
	 * 소코드 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int delCodeSoInfo(String SystemCode, String CodeDae, String CodeSo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from code_so where system_code=? and code_dae=? and code_so=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CodeDae);	
		sql.setString(CodeSo);
			
		//---- 디버그 출력
		log.debug("[delCodeSoInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}	
		return retVal;
	}		
	
	/**
	 * code_so 중복체크
	 * 2007.03.27 sangsang
	 * @param systemCode
	 * @param codeDae
	 * @param codeSo
	 * @return
	 * @throws DAOException
	 */
	public int getCodeSoCount(String systemCode, String codeDae, String codeSo) throws DAOException {
		int codeSoCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(code_so),0) as cnt from code_so ");
		sb.append(" where system_code = ? and code_dae = ? and code_so = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(codeDae);
		sql.setString(codeSo);

		log.debug("[getCodeSoCount]" + sql.toString());
		 
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 codeSoCount = rs.getInt("cnt");
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
		 return codeSoCount;
	}
}