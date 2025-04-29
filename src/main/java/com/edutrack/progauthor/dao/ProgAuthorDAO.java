/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.progauthor.dao;

import java.sql.ResultSet;

import javax.sql.RowSet;

import com.edutrack.progauthor.dto.ProgAuthorDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgAuthorDAO  extends AbstractDAO {

	/**
	 * 
	 */
	public ProgAuthorDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListDTO progAuthorPagingList(int curpage,String systemcode,String socode, String pfield, String pvalue) throws DAOException{
		ListDTO retVal = null;
		try{
			// List Sql문 생성 
			ListStatement sql = new ListStatement();
			StringBuffer where = new StringBuffer(" system_code = ? ");
			sql.setString(systemcode);
			
			if(!socode.equals("")) {
				where.append(" and work_gubun = ? ");
				sql.setString(socode);
			}
			
			if(!pfield.equals("")){
				where.append(" and "+pfield+" like ? ");
				sql.setString("%"+pvalue+"%");
			}

			sql.setTotalCol("prog_id");
			sql.setCutCol("prog_id");
			sql.setSelect(" system_code, (select so_name from code_so where system_code = system_code and code_dae = '501' and code_so = work_gubun) as work_name,work_gubun,prog_seq,prog_id,prog_name,view_level,reg_id,reg_date "); 
			sql.setFrom(" progauthor ");
			sql.setOrderby(" work_gubun, prog_id ");
			sql.setWhere(where.toString());		
			
			retVal = broker.executeListQuery(sql, curpage);
            	
			return retVal;
		}catch(Exception e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}	
	}

	public ProgAuthorDTO getProgAuthorInfo(String systemcode,String proggubun, String progno) throws DAOException{
		 ProgAuthorDTO authorInfo = null;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code,work_gubun,prog_seq,prog_id,prog_name,prog_comment,view_level,reg_id,reg_date ");
		 sb.append(" from progauthor ");
		 sb.append(" where system_code = ? and work_gubun = ? and prog_seq = ? ");
		 	
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setString(proggubun);
         sql.setInteger(progno);
		 
		 sql.setSql(sb.toString());

		 log.debug("[getProgAuthorInfo]" + sql.toString());
		 
		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
	         authorInfo = new ProgAuthorDTO(); 
			 if(rs.next()){
			 	authorInfo.setProgSeq(rs.getInt("prog_seq"));
				authorInfo.setWorkGubun(StringUtil.nvl(rs.getString("work_gubun")));
				authorInfo.setProgId(StringUtil.nvl(rs.getString("prog_id")));
				authorInfo.setProgName(StringUtil.nvl(rs.getString("prog_name")));
				authorInfo.setProgComment(StringUtil.nvl(rs.getString("prog_comment")));
				authorInfo.setViewLevel(StringUtil.nvl(rs.getString("view_level")));
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{ 
			  if(rs != null) rs.close();
			 }catch(Exception e){
				throw new DAOException(e.getMessage());
			 }
		 }
		 
		 return authorInfo;
	}
	
	public int editProgAuthorInfo(ProgAuthorDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update progauthor set prog_id = ?,prog_name = ?, prog_comment = ? ,view_level = ?,mod_id = ?,mod_date =  getCurrentDate() ");
		 sb.append(" where system_code = ? and work_gubun = ? and prog_seq = ? ");
		 
		 sql.setSql(sb.toString());
		 sql.setString(prog.getProgId());
		 sql.setString(prog.getProgName());
		 sql.setString(prog.getProgComment());
		 sql.setString(prog.getViewLevel());
		 sql.setString(prog.getModId());
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getWorkGubun());
		 sql.setInteger(prog.getProgSeq());
			
		 log.debug("[editProgAuthorInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	} 
	
	/**
	 * 
	 * @param prog
	 * @return
	 * @throws DAOException
	 */
	public int addProgAuthorInfo(ProgAuthorDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into progauthor(system_code,work_gubun,prog_seq,prog_id,prog_name, prog_comment, view_level,reg_id,reg_date) ");
		 sb.append(" select ?, ?, IFNULL(max(prog_seq),0)+1 as prog_seq,?,?,?,?,?, getCurrentDate() ");
		 sb.append(" from progauthor ");
		 sb.append(" where system_code = ? and work_gubun = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getWorkGubun());
		 sql.setString(prog.getProgId());
		 sql.setString(prog.getProgName());
		 sql.setString(prog.getProgComment());
		 sql.setString(prog.getViewLevel());
		 sql.setString(prog.getRegId());
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getWorkGubun());	
			
		 log.debug("[addProgAuthorInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	} 

	
	/**
	 * 프로그램 권한을 삭제한다.
	 * @param prog
	 * @return
	 * @throws DAOException
	 */
	public int deleteProgAuthorInfo(ProgAuthorDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from progauthor ");
		 sb.append(" where system_code = ? and work_gubun = ? and prog_seq=? and prog_id=? ");
		 sql.setSql(sb.toString());
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getWorkGubun());
		 sql.setInteger(prog.getProgSeq());
		 sql.setString(prog.getProgId());
			
log.debug("[deleteProgAuthorInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	} 
	
	/**
	 * 프로그램의 권한을 체크한다.
	 * @param systemcode
	 * @param progid
	 * @return
	 * @throws DAOException
	 */
	public String checkProgAuthor(String systemcode, String progid ) throws DAOException{
		 String retVal = null;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select view_level  as author_yn ");
		 sb.append(" from progauthor ");
		 sb.append(" where system_code = ? and prog_id = ? ");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setString(progid);
         
		 sql.setSql(sb.toString());

		 //System.out.println(sql);
		 
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				retVal = rs.getString("author_yn");
			 }
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{ 
			  if(rs != null) rs.close();
			 }catch(Exception e){
				throw new DAOException(e.getMessage());
			 }
		 }
		 
		 return retVal;
	}
	

}
