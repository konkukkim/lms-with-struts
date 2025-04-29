/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.curriresearch.dto.CurriResAnswerDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchAnswerDAO extends AbstractDAO {

	/**
	 *
	 */
	public ResearchAnswerDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 해당 설문에 답변한 사람의 수를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param resid
	 * @return
	 * @throws DAOException
	 */
	public int getResearchAnswerCnt(String systemcode,CurriMemDTO curriinfo,int resid,String userid) throws DAOException{
		int  retVal = 0;
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct user_id) as user_cnt ");
			sb.append(" from curri_res_answer ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and res_id = ? ");

			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);

		    if(!userid.equals("")){
		    	sb.append(" and user_id = ? ");
		    	sql.setString(userid);
		    }

			sql.setSql(sb.toString());

			log.debug("[getResearchAnswerCnt]" + sql.toString());

			rs = broker.executeQuery(sql);
			if(rs.next()){
				retVal = rs.getInt("user_cnt");
			}

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

	    return retVal;
	}

	/**
	 * 설문 답변을 등록해준다.
	 * @param systemcode
	 * @param curriInfo
	 * @param answerinfo
	 * @return
	 * @throws DAOException
	 */
	public boolean addResearchAnswer(String systemcode,CurriMemDTO curriInfo,String userid, int resid, ArrayList answerlist) throws DAOException{
		 boolean retVal 		= 	false;
		 int examOrder          =   0;
		 QueryStatement[] sqls 	= 	new QueryStatement[answerlist.size()];
		 QueryStatement sql = null;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into curri_res_answer(system_code,curri_code,curri_year,curri_term,user_id,res_id,res_no,contents_type,example1,example2,example3,example4,example5,example6,example7,example8,example9,example10,answer,reg_id,reg_date) ");
		 sb.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

		 CurriResAnswerDTO answerInfo = null;
		 String[] example = null;
		 for(int i =0; i < answerlist.size(); i++){
		 	 sql = new QueryStatement();
		 	 answerInfo = (CurriResAnswerDTO)answerlist.get(i);
		 	 example = answerInfo.getExample();

		     sql.setSql(sb.toString());
			 sql.setString(systemcode);
			 sql.setString(curriInfo.curriCode);
			 sql.setInteger(curriInfo.curriYear);
			 sql.setInteger(curriInfo.curriTerm);
			 sql.setString(userid);
			 sql.setInteger(resid);
			 sql.setInteger(answerInfo.getResNo());
			 sql.setString(answerInfo.getContentsType());
			 sql.setString(example[1]);
			 sql.setString(example[2]);
			 sql.setString(example[3]);
			 sql.setString(example[4]);
			 sql.setString(example[5]);
			 sql.setString(example[6]);
			 sql.setString(example[7]);
			 sql.setString(example[8]);
			 sql.setString(example[9]);
			 sql.setString(example[10]);
			 sql.setString(answerInfo.getAnswer());
			 sql.setString(userid);

			 sqls[i] = sql;
		 }
		 log.debug("[addResearchAnswer]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public RowSet getResearchAnswerList(String systemcode,CurriMemDTO curriinfo,int resid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select user_id, res_no, answer ");
			sb.append(" from  curri_res_answer ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year = ?	and curri_term = ? and res_id = ? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);
			sql.setSql(sb.toString());

			log.debug("[getResearchAnswerList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

	     return rs;
	}

}
