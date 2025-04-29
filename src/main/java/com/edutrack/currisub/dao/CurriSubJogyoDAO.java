package com.edutrack.currisub.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.UsersDTO;


/*
 * @author JamFam
 *
 * 조교관리..
 */
public class CurriSubJogyoDAO extends AbstractDAO {


	/**
	 * 조교목록을 가져온다..
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCurriSubJogyoList(String systemCode, String curriCode, int curriYear, int curriTerm) throws DAOException {
		
		ArrayList aList = new ArrayList();
		UsersDTO userDto = new UsersDTO();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select jogyo_id, user_name  from jogyo a, users b");
		sb.append(" where a.system_code=b.system_code and a.jogyo_id=b.user_id ");
		sb.append(" and a.system_code= ? and a.curri_code=? and a.curri_year=? and a.curri_term=?");
		sb.append(" order by a.reg_date asc ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);

		log.debug("[getCurriSubJogyo]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 while(rs.next()){
				 userDto = new UsersDTO();
				 userDto.setUserId(StringUtil.nvl(rs.getString("jogyo_id")));
				 userDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				 
				 aList.add(userDto);
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
		 return aList;
	}
	
	
	/**
	 * 조교자를 등록한다
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param jogyoId
	 * @param regId
	 * @return
	 * @throws DAOException
	 */
	public boolean addCurriSubJogyo(String systemCode, String curriCode, int curriYear, int curriTerm, String jogyoId[], String regId) throws DAOException{
		boolean retVal = false;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		ISqlStatement[] stArray = new ISqlStatement[jogyoId.length];
		
		sb.append(" INSERT INTO jogyo(system_code, curri_code, curri_year, curri_term, jogyo_id, reg_id, reg_date) ");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");

		//---- 입력된 값을 가져 온다.
		for(int i=0; i<jogyoId.length; i++ ){
			sql = new QueryStatement();
			
			sql.setSql(sb.toString());
			sql.setString(systemCode);
			sql.setString(curriCode);
			sql.setInteger(curriYear);
			sql.setInteger(curriTerm);
			sql.setString(jogyoId[i]);
			sql.setString(regId);
			sql.setString(CommonUtil.getCurrentDate());
			
			stArray[i] = sql;
		}

		//---- 디버그 출력
		log.debug("[addCurriSubJogyo]" + sql.toString());

		try{
			retVal = broker.executeUpdate(stArray);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	
	
	/**
	 * 조교자를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int delCurriSubJogyo(String systemCode, String curriCode, int curriYear, int curriTerm, String jogyoId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from jogyo where system_code=? and curri_code=? and curri_year=? and curri_term=? ");
		if(jogyoId!=null){
			sb.append(" and jogyo_id=?");
		}
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		if(jogyoId!=null){
			sql.setString(jogyoId);
		}
		//---- 디버그 출력
		log.debug("[delCurriSubJogyo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;

	}
	
}