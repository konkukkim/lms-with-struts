/*
 * Created on 2004. 10. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.dept.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.dept.dto.DeptSoCodeDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author sej
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeptSoDAO extends AbstractDAO {

	/**
	 * 
	 */
	public DeptSoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

 /**
 * 소속 소코드의 갯수를 가져온다. 
 * @param SystemCode
 * @param DeptDaecode
 * @return count
 * @throws DAOException
 */
 
	public int getTotCount(String SystemCode, String DeptDaecode) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
			
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(dept_socode) as cnt ");
		 sb.append(" from dept_socode ");
		 sb.append(" where system_code = ? ");
		 sb.append("  and dept_daecode = ? "); 
		 sql.setSql(sb.toString());		 
		 sql.setString(SystemCode);	
		 sql.setString(DeptDaecode); 
		 
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
   * @param systemCode
   * @param deptDaecode
   * @param deptSocode 
   * @return count
   * @throws DAOException 
   */
	public int chkSocode(String SystemCode, String deptDaecode, String deptSocode) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(dept_socode) as cnt ");
		sb.append(" from dept_socode ");
		sb.append(" where system_code= ? and dept_daecode = ? and dept_socode = ? "); 
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(deptDaecode);
		sql.setString(deptSocode); 
		
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
			throw new DAOException (e.getMessage()); 
		} finally {
			try {
				if (rs != null) rs.close(); 
			}catch(SQLException e) {
				throw new DAOException(e.getMessage());
			}
			
		}
		return retVal; 
	}
	/**
	 * 해당 소속의 단체 관리자 인지 확인 한다.
	 * @param SystemCode
	 * @param deptDaecode
	 * @param deptSocode
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public boolean chkDeptManager(String SystemCode, String deptDaecode, String deptSocode, String userId) throws DAOException {
		int chkVal = 0;
		boolean retVal = false;
		QueryStatement sql = new QueryStatement();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(dept_socode) as cnt ");
		sb.append(" from dept_socode ");
		sb.append(" where system_code= ? and dept_daecode = ? and dept_socode = ? and user_id = ?"); 
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(deptDaecode);
		sql.setString(deptSocode);
		sql.setString(userId); 
		
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if(rs.next()){
				chkVal = rs.getInt("cnt");
			}
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage()); 
			throw new DAOException (e.getMessage()); 
		} finally {
			try {
				if (rs != null) rs.close(); 
			}catch(SQLException e) {
				throw new DAOException(e.getMessage());
			}
			
		}
		if(chkVal > 0)
			retVal = true;
			
		return retVal; 
	}
	
	
	/**
	 * 소속 소코드의 리스트를 가져온다. 
	 * @param curPage
	 * @param SystemCode
	 * @param DeptDaecode
	 * @return ListDTO
	 * @throws DAOException 
	 */
	public ListDTO getDeptSoList(int curpage, String SystemCode, String DeptDaecode, String column, String orderBy) throws DAOException {
		ListDTO retVal = null;
		try {
			ListStatement sql = new ListStatement(); 
			sql.setTotalCol("dept_socode");
			sql.setCutCol("dept_socode");
			sql.setAlias("dept_socode, dept_daecode, dept_soname, use_yn,  use_name, reg_id, reg_date");
			sql.setSelect("dept_socode, dept_daecode, dept_soname, use_yn, case use_yn when 'Y' then '사용' else '사용안함' end as use_name, reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date");
			sql.setFrom(" dept_socode");
			StringBuffer sbWhere = new StringBuffer(); 
			 sbWhere.append(" system_code = ? and dept_daecode = ? and dept_soname is not null ");  
			 sql.setString(SystemCode); 
			 sql.setString(DeptDaecode);
			sql.setWhere(sbWhere.toString());
			sql.setOrderby(column+" "+orderBy);
			
			//--- 디버그 출력
			log.debug("[getDeptSoList]" + sql.toString());
			
			//--- 쿼리실행 및 반환값 설정
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
	 * 소속소코드 리스트를 가져온다.
	 * @param SystemCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getDeptSoList(String systemCode, String deptDaeCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, dept_daecode, dept_socode, dept_soname from dept_socode where system_code=? and dept_daecode=? and use_yn='Y' ");
		sb.append(" order by reg_date desc");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(deptDaeCode);
		log.debug("[getDeptSoList]" + sql.toString());
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
   * 동일한 코드가 있는가 체크한다.
   * @param systemCode
   * @param deptDaecode
   * @return max dept_socode
   * @throws DAOException 
   */
	public String  maxSocode(String SystemCode, String deptDaecode) throws DAOException {
		String deptSocode = "";
		
		QueryStatement sql = new QueryStatement();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select ifnull(max(dept_socode), concat(? ,'00'))+1 as dept_socode ");
		sb.append(" from dept_socode ");
		sb.append(" where system_code= ? and dept_daecode = ? "); 
		sql.setSql(sb.toString());
		
		sql.setString(deptDaecode); 
		sql.setString(SystemCode);
		sql.setString(deptDaecode);

		 log.debug("[maxSocode]" + sql.toString());
		
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if(rs.next()){
			    deptSocode = rs.getString("dept_socode");

			}
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage()); 
			throw new DAOException (e.getMessage()); 
		} finally {
			try {
				if (rs != null) rs.close(); 
			}catch(SQLException e) {
				throw new DAOException(e.getMessage());
			}
			
		}
		return deptSocode; 
	}
	

	/**
	 * 소속 소코드 정보를 저장한다.
	 * @param cateInfo 
	 * @return int 
	 * @throws DAOException 
	 */
	public int addDeptSoInfo(DeptSoCodeDTO deptSoInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement(); 
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into dept_socode (system_code, dept_socode, dept_daecode, dept_soname, use_yn, \n");
		sb.append("             user_id, post_code, address, phone, position, \n");	// Add date : 2005.07.30 ..
		sb.append("             reg_id, reg_date, comp_no         )\n");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
		sql.setSql(sb.toString());
		
		sql.setString(deptSoInfo.getSystemCode());
		sql.setString(deptSoInfo.getDeptSocode()); 
		sql.setString(deptSoInfo.getDeptDaecode());
		sql.setString(deptSoInfo.getDeptSoname());
		sql.setString(deptSoInfo.getUseYn());
		// Add date : 2005.07.30 .. as below 5 column
		sql.setString(deptSoInfo.getUserId    ());    
		sql.setString(deptSoInfo.getPostCode  ());    
		sql.setString(deptSoInfo.getAddress   ());    
		sql.setString(deptSoInfo.getPhone     ());    
		sql.setString(deptSoInfo.getPosition  ());    		
		sql.setString(deptSoInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(deptSoInfo.getCompNo());

 log.debug("[addDeptSoInfo]" + sql.toString());

		try {
			retVal = broker.executeUpdate(sql);
			          
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage()); 
		}
		return retVal; 
	}


	/**
	 * 소속 소코드 정보를 수정한다. 
	 * @param cateInfo 
	 * @return int
	 * @throws DAOException
	 */
	public int editDeptSoInfo(DeptSoCodeDTO deptSoInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update dept_socode set dept_soname=? , use_yn=? , user_id=? , post_code = ?, address = ?, phone = ?, position = ?, mod_id = ?, mod_date = ? ");
		sb.append(" where system_code = ? and dept_daecode = ? and dept_socode = ? ");
		sql.setSql(sb.toString());
		
		
		sql.setString(deptSoInfo.getDeptSoname()); 
		sql.setString(deptSoInfo.getUseYn     ()); 

		// Add date : 2005.07.30 .. as below 4 column
		sql.setString(deptSoInfo.getUserId    ());  
		sql.setString(deptSoInfo.getPostCode  ());    
		sql.setString(deptSoInfo.getAddress   ());    
		sql.setString(deptSoInfo.getPhone     ());    
		sql.setString(deptSoInfo.getPosition  ());    		

		sql.setString(deptSoInfo.getModId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(deptSoInfo.getSystemCode());
		sql.setString(deptSoInfo.getDeptDaecode());
		sql.setString(deptSoInfo.getDeptSocode());
		log.debug("[editDeptSoInfo]" + sql.toString());
		
		try {
			retVal = broker.executeUpdate(sql);
				
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal; 
	}


	/**
	 * 소속 소코드 정보중 단체 관리자 아이디를 널로 수정한다.(단체관리자 회원탈퇴시) 
	 * @param cateInfo 
	 * @return int
	 * @throws DAOException
	 */
	public int dropDeptSoInfo(DeptSoCodeDTO deptSoInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update dept_socode set user_id=? , mod_id = ?, mod_date = ? ");
		sb.append(" where system_code = ? and dept_daecode = ? and dept_socode = ? ");
		sql.setSql(sb.toString());
		
		
		sql.setString(""); 	

		sql.setString(deptSoInfo.getModId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(deptSoInfo.getSystemCode());
		sql.setString(deptSoInfo.getDeptDaecode());
		sql.setString(deptSoInfo.getDeptSocode());
log.debug("[dropDeptSoInfo]" + sql.toString());
		
		try {
			retVal = broker.executeUpdate(sql);
				
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal; 
	}

	/**
	 * 소속 소코드 정보를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException 
	 */
	public RowSet getDeptSoInfo(String SystemCode, String DeptDaecode, String DeptSocode) throws DAOException {
		QueryStatement sql = new QueryStatement(); 
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, dept_socode, dept_daecode, dept_soname, use_yn, reg_id, reg_date");
		sb.append("     , user_id, post_code, address, phone, position, comp_no ");
		sb.append(" from dept_socode");
		sb.append(" where system_code = ? and dept_daecode = ? and dept_socode = ? "); 
		
		sql.setSql(sb.toString()); 
		sql.setString(SystemCode);
		sql.setString(DeptDaecode);
		sql.setString(DeptSocode);
		log.debug("[getDeptSoInfo]" + sql.toString() );
		
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			
		} catch(Exception e){ 
			log.error(e.getMessage());
			throw new DAOException(e.getMessage()); 
		}
		return rs; 
	}
	
	
  /**
   * 소속 소코드 정보를 삭제한다. 
   * @param SystemCode
   * @param DeptDaecode
   * @param DeptSocode 
   * @return
   * @throws DAOException 
   */
    public int delDeptSoInfo(String SystemCode, String DeptDaecode, String DeptSocode) throws DAOException {
    	int retVal = 0;
    	QueryStatement sql = new QueryStatement();
    	StringBuffer sb = new StringBuffer();
    	sb.append("delete from dept_socode where system_code = ? and dept_daecode = ? and dept_socode = ? "); 
    	sql.setSql(sb.toString()); 
    	sql.setString(SystemCode);
    	sql.setString(DeptDaecode);
    	sql.setString(DeptSocode);

		log.debug("[delDeptSoInfo]" + sql.toString() );
    	try {
    		retVal = broker.executeUpdate(sql);  
    	} catch(Exception e) {
    		log.error(e.getMessage());
    		throw new DAOException(e.getMessage()); 
    	}
    	return retVal; 
    }

	/**
	 * dept_socode 중복체크
	 * 2007.03.27 sangsang
	 * @param systemCode
	 * @param deptDaecode
	 * @param deptSocode
	 * @return
	 * @throws DAOException
	 */
	public int getDeptSoCount(String systemCode, String deptDaecode, String deptSocode) throws DAOException {
		int codeSoCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(dept_socode),0) as cnt from dept_socode ");
		sb.append(" where system_code = ? and dept_daecode = ? and dept_socode = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(deptDaecode);
		sql.setString(deptSocode);

		log.debug("[getDeptSoCount]" + sql.toString());
		 
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
