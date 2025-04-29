/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.curriresearch.dto.CurriResContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchContentsDAO extends AbstractDAO {

	/**
	 *
	 */
	public ResearchContentsDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 설문문형에 대한 리스트를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param resid
	 * @param resno
	 * @return
	 * @throws DAOException
	 */
	public RowSet getResearchContentsList(String systemcode,CurriMemDTO curriinfo,int resid,int resno) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			/*
			sb.append(" select res_no,contents,contents_type,example1,example2,example3,example4,example5, ");
			sb.append(" example6,example7,example8,example9,example10,example_num,reg_id,reg_date ");
			sb.append(" from curri_res_contents ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year =? and curri_term =? and res_id = ? ");
			*/
			sb.append(" SELECT *  ");
			sb.append(", X.exp1 + X.exp2 + X.exp3 + X.exp4 + X.exp5 + X.exp6 + X.exp7 + X.exp8 + X.exp9 + X.exp10 AS sum_exp_cnt ");
			sb.append(" FROM  ");
			sb.append(" ( ");
			sb.append(		" SELECT " +
							" res_no,contents,contents_type,example1 " +
							", example2,example3,example4 ,example5 " +
							", example6,example7,example8 ,example9 " +
							", example10 ,example_num,reg_id,reg_date " +
							", case when example1 = '' then 0 else 1 end exp1 " +
							", case when example2 = '' then 0 else 1 end exp2 " +
							", case when example3 = '' then 0 else 1 end exp3 " +
							", case when example4 = '' then 0 else 1 end exp4 " +
							", case when example5 = '' then 0 else 1 end exp5 " +
							", case when example6 = '' then 0 else 1 end exp6 " +
							", case when example7 = '' then 0 else 1 end exp7 " +
							", case when example8 = '' then 0 else 1 end exp8 " +
							", case when example9 = '' then 0 else 1 end exp9 " +
							", case when example10 = '' then 0 else 1 end exp10  ");
			sb.append(		" FROM curri_res_contents ");
			sb.append(		" WHERE system_code = ? AND curri_code = ? " +
							" AND curri_year = ? AND curri_term = ? " +
							" AND res_id = ? ");
			sb.append(" ) X ");

			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);

			if(resno > 0){
				sb.append(" and res_no = ? ");
				sql.setInteger(resno);
			}

			sql.setSql(sb.toString());

			log.debug("[getResearchContentsList]" + sql.toString());

			rs = broker.executeQuery(sql);

		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	/**
	 * 설문문형에 대한 정보를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param resid
	 * @param resno
	 * @return
	 * @throws DAOException
	 */
	public CurriResContentsDTO getResearchContentsInfo(String systemcode,CurriMemDTO curriinfo ,int resid,int resno) throws DAOException{
		CurriResContentsDTO resInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
            rs = getResearchContentsList(systemcode, curriinfo, resid, resno);
			resInfo = new CurriResContentsDTO();
			if(rs.next()){
				resInfo.setResNo(rs.getInt("res_no"));
				resInfo.setContents(StringUtil.nvl(rs.getString("contents")));
				resInfo.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				resInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
				resInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
				resInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
				resInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
				resInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
				resInfo.setExample6(StringUtil.nvl(rs.getString("example6")));
				resInfo.setExample7(StringUtil.nvl(rs.getString("example7")));
				resInfo.setExample8(StringUtil.nvl(rs.getString("example8")));
				resInfo.setExample9(StringUtil.nvl(rs.getString("example9")));
				resInfo.setExample10(StringUtil.nvl(rs.getString("example10")));
				resInfo.setExampleNum(StringUtil.nvl(rs.getString("example_num")));
				resInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		        resInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 }
			 rs.close();
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

		return resInfo;
	}

	/**
	 * 설문문항을 등록해 준다.
	 * @param systemcode
	 * @param curriInfo
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int addResearchContentsInfo(String systemcode,CurriMemDTO curriInfo, CurriResContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 int examOrder          =   0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into curri_res_contents(system_code,curri_code,curri_year,curri_term,res_id,res_no,contents_type,contents, ");
		 sb.append(" example1,example2,example3,example4,example5,example6,example7,example8,example9,example10,example_num,reg_id,reg_date) ");
		 sb.append(" select ?,?,?,?,?,ifnull(max(res_no),0)+1 as res_no,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from curri_res_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?  and res_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setInteger(contentsinfo.getResId());
		 sql.setString(contentsinfo.getContentsType());
		 sql.setString(contentsinfo.getContents());
		 sql.setString(contentsinfo.getExample1());
		 sql.setString(contentsinfo.getExample2());
		 sql.setString(contentsinfo.getExample3());
		 sql.setString(contentsinfo.getExample4());
		 sql.setString(contentsinfo.getExample5());
		 sql.setString(contentsinfo.getExample6());
		 sql.setString(contentsinfo.getExample7());
		 sql.setString(contentsinfo.getExample8());
		 sql.setString(contentsinfo.getExample9());
		 sql.setString(contentsinfo.getExample10());
		 sql.setString(contentsinfo.getExampleNum());
		 sql.setString(contentsinfo.getRegId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setInteger(contentsinfo.getResId());

		 log.debug("[addResearchContentsInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 설문문항을 수정해준다.
	 * @param systemcode
	 * @param curriInfo
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int editResearchContentsInfo(String systemcode,CurriMemDTO curriInfo, CurriResContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 int examOrder          =   0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update curri_res_contents ");
		 sb.append(" set contents_type = ?,contents = ?,example1 = ?,example2 = ?,example3 = ?,example4 = ?,example5 = ?,example6 = ?,example7 = ?,example8 = ?,example9 = ?,example10 = ?,example_num = ?, ");
		 sb.append(" mod_id = ?,mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from curri_res_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ?  and res_id = ? and res_no = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(contentsinfo.getContentsType());
		 sql.setString(contentsinfo.getContents());
		 sql.setString(contentsinfo.getExample1());
		 sql.setString(contentsinfo.getExample2());
		 sql.setString(contentsinfo.getExample3());
		 sql.setString(contentsinfo.getExample4());
		 sql.setString(contentsinfo.getExample5());
		 sql.setString(contentsinfo.getExample6());
		 sql.setString(contentsinfo.getExample7());
		 sql.setString(contentsinfo.getExample8());
		 sql.setString(contentsinfo.getExample9());
		 sql.setString(contentsinfo.getExample10());
		 sql.setString(contentsinfo.getExampleNum());
		 sql.setString(contentsinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setInteger(contentsinfo.getResId());
		 sql.setInteger(contentsinfo.getResNo());

		 log.debug("[editResearchContentsInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public boolean moveBankContents(String systemcode,CurriMemDTO curriinfo,int resid,int contentsresid,String userid,String[] resno) throws DAOException{
		 boolean retVal 		= 	false;
		 QueryStatement[] sqls 	= 	new QueryStatement[resno.length];
		 QueryStatement sql = null;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into curri_res_contents(system_code,curri_code,curri_year,curri_term,res_id,res_no,contents_type,contents, ");
		 sb.append(" example1,example2,example3,example4,example5,example6,example7,example8,example9,example10,example_num,reg_id,reg_date) ");
		 sb.append(" select ?,?,?,?,?,");
		 sb.append(" (select ifnull(max(res_no),0)+1 from curri_res_contents where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and res_id = ?) as res_no, ");
		 sb.append(" contents_type,contents, ");
		 sb.append(" example1,example2,example3,example4,example5,example6,example7,example8,example9,example10,example_num, ");
		 sb.append(" ?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from res_bk_contents ");
		 sb.append(" where system_code = ? and res_id = ? and res_no = ? ");

		 for(int i =0; i < resno.length; i++){
			 sql = new QueryStatement();
			 sql.setSql(sb.toString());
			 sql.setString(systemcode);
			 sql.setString(curriinfo.curriCode);
			 sql.setInteger(curriinfo.curriYear);
			 sql.setInteger(curriinfo.curriTerm);
			 sql.setInteger(contentsresid);
			 sql.setString(systemcode);
			 sql.setString(curriinfo.curriCode);
			 sql.setInteger(curriinfo.curriYear);
			 sql.setInteger(curriinfo.curriTerm);
			 sql.setInteger(contentsresid);
	         sql.setString(userid);
	    	 sql.setString(systemcode);
			 sql.setInteger(resid);
			 sql.setInteger(resno[i]);

			 sqls[i] = sql;
        }


		 log.debug("[moveBankContents]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public int delResearchContents(String systemcode,CurriMemDTO curriinfo, int resid, int resno)  throws DAOException{
		 int retVal 			= 	0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from curri_res_contents ");
		 sb.append(" where  system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and res_id = ? and res_no = ? ");
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 sql.setInteger(resid);
		 sql.setInteger(resno);
		 sql.setSql(sb.toString());

		 log.debug("[delResearchBankContents]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}
}
