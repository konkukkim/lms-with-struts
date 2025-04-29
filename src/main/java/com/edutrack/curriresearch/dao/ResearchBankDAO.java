/*
 * Created on 2004. 10. 27.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.dto.CodeDTO;
import com.edutrack.curriresearch.dto.ResBkContentsDTO;
import com.edutrack.curriresearch.dto.ResBkInfoDTO;
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
public class ResearchBankDAO extends AbstractDAO {

	/**
	 * 
	 */
	public ResearchBankDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
/*	
	public ArrayList getResearchBankInfoCodeList(String systemcode,String userid, String share) throws DAOException{
		ArrayList codeList = new ArrayList();
		RowSet rs = null;
		try{
			// List Sql문 생성 
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();	
			sb.append(" select res_id, subject ");
			sb.append(" from res_bk_info ");
			sb.append(" where system_code = ? ");
			if(!share.equals("N")) sb.append(" and ( user_id = ? or share_yn = 'Y') ");
			else sb.append(" and user_id = ? ");
			sb.append(" order by res_id ");
			sql.setSql(sb.toString());		
			sql.setString(systemcode);
			sql.setString(userid);
	
			log.debug("[getResearchBankInfoCodeList]" + sql.toString());
			 
			rs = broker.executeQuery(sql);

			CodeDTO codeDto = null;
            int count = 0;
			while(rs.next()){
				codeDto = new CodeDTO();
				codeDto.setCode(String.valueOf(rs.getInt("res_id")));
				codeDto.setName(StringUtil.nvl(rs.getString("subject")));
				codeList.add(codeDto);
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

		 return codeList;   
	}
*/
	public RowSet getResearchBankInfoCodeList(String systemCode,String userId, String share) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		

		sb.append(" select res_id, subject ");
		sb.append(" from res_bk_info ");
		sb.append(" where system_code = ? ");
		if(!share.equals("N")) sb.append(" and ( user_id = ? or share_yn = 'Y') ");
		else sb.append(" and user_id = ? ");
		sb.append(" order by res_id ");
		sql.setSql(sb.toString());		
		sql.setString(systemCode);
		sql.setString(userId);
		
		log.debug("[getResearchBankInfoCodeList]" + sql.toString());
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;	
	}
	
	
	public int addResearchBankInfo(String systemcode,ResBkInfoDTO bankinfo)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into res_bk_info(system_code,res_id,user_id,share_yn,subject,reg_id,reg_date) ");
		 sb.append(" select ?,ifnull(max(res_id),0)+1 as res_id, ?, ?,?,?,getCurrentDate() ");
		 sb.append(" from res_bk_info ");
		 sb.append(" where system_code = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(bankinfo.getUserId());
		 sql.setString(bankinfo.getShareYn());
		 sql.setString(bankinfo.getSubject());
		 sql.setString(bankinfo.getUserId());
		 sql.setString(systemcode);
		 
		 log.debug("[addResearchBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	}

	/**
	 * 설문항목을 수정한다.
	 * @param systemCode
	 * @param userId
	 * @param courseId
	 * @param bankName
	 */
	public int editResearchBankInfo(String systemcode,ResBkInfoDTO bankinfo)  throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update  res_bk_info ");
		 sb.append(" set share_yn = ?,subject = ?,mod_id = ?,mod_date = getCurrentDate() ");
		 sb.append(" where system_code = ? and res_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(bankinfo.getShareYn());
		 sql.setString(bankinfo.getSubject());
		 sql.setString(bankinfo.getUserId());
		 sql.setString(systemcode);
		 sql.setInteger(bankinfo.getResId());
		 
		 log.debug("[editResearchBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;		
	}

	public boolean delResearchBankInfo(String systemcode, int resid)  throws DAOException{
		 boolean retVal 			= 	false;
		 QueryStatement[] sqls 	= 	new QueryStatement[2];
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from res_bk_info ");
		 sb.append(" where  system_code = ? and res_id = ? ");
		 sql.setString(systemcode);
		 sql.setInteger(resid);	
		 sql.setSql(sb.toString());
		 
		 sqls[0] = sql;
		 
		 QueryStatement sql2 	= 	new QueryStatement();		 
		 StringBuffer sb2 = new StringBuffer();
		 sb2.append(" delete from res_bk_contents ");
		 sb2.append(" where  system_code = ? and res_id = ? ");
		 sql2.setString(systemcode);
		 sql2.setInteger(resid);
		 sql2.setSql(sb2.toString());
		 
		 sqls[1] = sql2;
		 
		 log.debug("[delResearchBankInfo]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;		
	}
	
	public int delResearchBankContents(String systemcode, int resid, int resno)  throws DAOException{
		 int retVal 			= 	0;
	
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from res_bk_contents ");
		 sb.append(" where  system_code = ? and res_id = ? and res_no = ? ");
		 sql.setString(systemcode);
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
	
	public ResBkInfoDTO getResearchBankInfo(String systemcode, int resid)  throws DAOException{
		ResBkInfoDTO bankInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성 
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();	
			sb.append(" select  share_yn,subject ");
			sb.append(" from res_bk_info ");
			sb.append(" where  system_code = ? and res_id = ? ");
			sql.setSql(sb.toString());		
			sql.setString(systemcode);
			sql.setInteger(resid);
	
			log.debug("[getExamBankInfo]" + sql.toString());
			 
			rs = broker.executeQuery(sql);
		
			bankInfo = new ResBkInfoDTO(); 
			if(rs.next()){
				bankInfo.setShareYn(StringUtil.nvl(rs.getString("share_yn")));
				bankInfo.setSubject(StringUtil.nvl(rs.getString("subject")));
				
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

		 return bankInfo;   
	}
	
	public RowSet getResearchBankContentsList(String systemcode, int resid, int resno) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성 
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();	
			sb.append(" select res_no,contents,contents_type,example1,example2,example3,example4,example5, ");
			sb.append(" example6,example7,example8,example9,example10,example_num,reg_id,reg_date ");
			sb.append(" from res_bk_contents ");
			sb.append(" where system_code = ? and res_id = ? ");
			sql.setString(systemcode);
			sql.setInteger(resid);

			if(resno > 0) {
				sb.append(" and res_no = ? ");
				sql.setInteger(resno);
			}else
				sb.append(" order by res_no ");
			
			sql.setSql(sb.toString());		
			
			log.debug("[getResearchBankContentsList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return rs;   
	}
	
	public ResBkContentsDTO getResearchBankContentsInfo(String systemcode,int resid,int resno) throws DAOException{
		ResBkContentsDTO resInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성 
            rs = getResearchBankContentsList(systemcode, resid, resno);
			resInfo = new ResBkContentsDTO();
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
	
	public int addResearchBankContentsInfo(String systemcode,ResBkContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 int examOrder          =   0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into res_bk_contents(system_code,res_id,res_no,contents_type,contents, ");
		 sb.append(" example1,example2,example3,example4,example5,example6,example7,example8,example9,example10,example_num,reg_id,reg_date) ");
		 sb.append(" select ?,?,ifnull(max(res_no),0)+1 as res_no,?,?,?,?,?,?,?,?,?,?,?,?,?,?,getCurrentDate() ");
		 sb.append(" from res_bk_contents ");
		 sb.append(" where system_code = ? and res_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
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
		 sql.setInteger(contentsinfo.getResId());
		 
		 log.debug("[addResearchBankContentsInfo]" + sql.toString());
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
	public int editResearchBankContentsInfo(String systemcode, ResBkContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 int examOrder          =   0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update res_bk_contents ");
		 sb.append(" set contents_type = ?,contents = ?,example1 = ?,example2 = ?,example3 = ?,example4 = ?,example5 = ?,example6 = ?,example7 = ?,example8 = ?,example9 = ?,example10 = ?,example_num = ?, ");
		 sb.append(" mod_id = ?,mod_date = getCurrentDate() ");
		 sb.append(" where system_code = ? and res_id = ? and res_no = ? ");
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
		 sql.setInteger(contentsinfo.getResId());
		 sql.setInteger(contentsinfo.getResNo());
		 
		 log.debug("[editResearchBankContentsInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	}
}
