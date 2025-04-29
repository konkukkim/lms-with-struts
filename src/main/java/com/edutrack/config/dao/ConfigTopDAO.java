/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.config.dao;

import javax.sql.RowSet;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConfigTopDAO extends AbstractDAO {
	
	/**
	 * 설정 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getConfigTopList(String Systemcode) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select system_code, config_type, config_name"); 
	 sb.append(" from config_top ");
	 sb.append(" where system_code = ? ");
	 sb.append(" order by config_type asc ");
	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 
	 log.debug("[getConfigTopList]" + sql.toString());
	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 //-- rs.close() 는 반드시 JSP 에서 해 줄
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }
	 return rs;   
	}
}

