/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.config.dao;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.config.dto.ConfigSubDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConfigSubDAO extends AbstractDAO {

   
	

	/**
	 * 설정을 수정한다.
	 * @param configSub
	 * @return
	 * @throws DAOException
	 */
	public int editConfigSub(ConfigSubDTO configSub) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" update config_sub set type_name = ?, config_value = ?, mod_id = ?, mod_date = ? ");
	 sb.append(" where system_code = ? and config_type = ? and config_code = ?"); 
	 sql.setSql(sb.toString());

	 sql.setString(configSub.getTypeName());
	 sql.setString(configSub.getConfigValue());
	 sql.setString(configSub.getModId());
	 sql.setString(CommonUtil.getCurrentDate());
	 sql.setString(configSub.getSystemCode());
	 sql.setString(configSub.getConfigType());
	 sql.setString(configSub.getConfigCode());
	 
	 log.debug("[editConfigSub]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	
	 
	 return retVal;   
	}
	
	/**
	 * 시스템 설정 정보 가져오기
	 * @param Systemcode
	 * @param ConfigType
	 * @param ConfigCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getConfigSub(String Systemcode,String ConfigType, String ConfigCode) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code, config_type, config_code, type_name, config_value, mod_id, mod_date"); 
		 sb.append(" from config_sub ");
		 sb.append(" where system_code = ? and config_type = ? and config_code = ? ");
		 
		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setString(ConfigType);
		 sql.setString(ConfigCode);
		 
		 
		 log.debug("[getConfigSub]" + sql.toString());
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
	
	
	/**
	 * 시스템 설정 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @param ConfigType
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getConfigSubList(String Systemcode, String ConfigType) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select system_code, config_type, config_code, type_name, config_value, mod_id, mod_date"); 
	 sb.append(" from config_sub ");
	 sb.append(" where system_code = ? and config_type = ? ");
	 sb.append(" order by config_code asc ");
	 
	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 sql.setString(ConfigType);
	 
	 log.debug("[getConfigSubList]" + sql.toString());
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
	
	public String getConfigSubValue(String Systemcode,String ConfigType, String ConfigCode) throws DAOException{
		 String retVal = "";
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select config_value "); 
		 sb.append(" from config_sub ");
		 sb.append(" where system_code = ? and config_type = ? and config_code = ? ");
		 
		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setString(ConfigType);
		 sql.setString(ConfigCode);
		 
		 
		 log.debug("[getConfigSub]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	retVal = rs.getString("config_value");
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;   
		}

}

