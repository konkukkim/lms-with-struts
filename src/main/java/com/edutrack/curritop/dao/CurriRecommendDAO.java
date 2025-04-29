package com.edutrack.curritop.dao;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;


/*
 * @author JamFam
 *
 * 추천 과정 관리
 */
public class CurriRecommendDAO extends AbstractDAO {


	/**
	 * 추천이 안된 과정을 가져옵니다.
	 * @param systemCode
	 * @param property
	 * @return RowSet
	 * @throws DAOException
	 */

	public RowSet getDontRecomendCurriList (String systemCode, String property) throws DAOException {
		RowSet rs = null;
		//----
		QueryStatement sql = new QueryStatement();
		String qry = "select curri_code, curri_name from curri_top where system_code=? and curri_property1=?" +
				" and curri_code not in (select curri_code from curri_recommend where system_code=?)";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(property);
		sql.setString(systemCode);
		log.debug("[getDontRecomendCurriList]" + sql.toString());
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
	 * 추천이된 과정을 가져옵니다.
	 * @param systemCode
	 * @param property
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getRecomendCurriList (String systemCode, String property) throws DAOException {
		RowSet rs = null;
		//----
		QueryStatement sql = new QueryStatement();
		String qry = "select a.curri_code, b.curri_name from curri_recommend a , curri_top b "+
			"where a.system_code=b.system_code and a.curri_code = b.curri_code and a.system_code=? and "+
			"b.curri_property1=?";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(property);
		log.debug("[getRecomendCurriList]" + sql.toString());
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
	 * 추천이된 과정을 저장합니다.
	 * @param systemCode
	 * @param curri_codes[]
	 * @return RowSet
	 * @throws DAOException
	 */
	public int setRecommendCurri(String systemCode, String[] curri_codes, String property) throws DAOException {
		int retVal = 0;
		int retVal2 = 0;
		QueryStatement sql = new QueryStatement();

		//--- 넘어온 배열로 쿼리를 만듭니다.
		String curriCodes = "";

		//---- 모든 값을 삭제 합니다.
		String qry = "delete from curri_recommend where system_code=? and curri_code in "+
			"(select curri_code from curri_top where system_code=? and curri_property1=?)";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(systemCode);
		sql.setString(property);
		log.debug("[delete curri_recommend] : "+sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	log.debug("&&&&&&&&&&&&="+curri_codes.length);

		for(int i=0; i < curri_codes.length; i++) {
			QueryStatement sql1 = new QueryStatement();
			String qry1 = "insert into curri_recommend values(?, ?, ?)";
			sql1.setSql(qry1);
			sql1.setString(systemCode);
			sql1.setString(curri_codes[i]);
			sql1.setInteger(i+1);
			log.debug("[insert curri_recommend] : "+sql1.toString());
			try{
				retVal = broker.executeUpdate(sql1);
				retVal2 += retVal;
			}catch(Exception e){
				log.error(e.getMessage());
				throw new DAOException(e.getMessage());
			}
		}
		return retVal2;
	}

	/**
	 * 추천과정중 개설과정이 속한 추천과정을 가져옵니다.
	 * @param systemCode
	 * @param
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurrentRecommendCurri(String systemCode, String property) throws DAOException {
		RowSet rs = null;
		QueryStatement sql = new QueryStatement();
		String qry =	" SELECT " +
						" a.curri_code, b.curri_name	" +
						" FROM curri_recommend a, curri_top b"+
						" WHERE a.system_code=b.system_code and a.curri_code=b.curri_code "+
						" and a.system_code=? and b.curri_property1=? " +
						" and a.curri_code IN "+
											" ( " +
											" SELECT curri_code " +
											" FROM curri_sub " +
											" WHERE enroll_start <= "+CommonUtil.getDbCurrentDate()+" and service_end >= "+CommonUtil.getDbCurrentDate()+
											" and system_code=? " +
											") " +
						" ORDER BY a.view_order " +
						" limit 5 ";
		sql.setSql(qry);
		sql.setString(systemCode);
		sql.setString(property);
		sql.setString(systemCode);
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}
}