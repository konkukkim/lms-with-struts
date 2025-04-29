/*
 * Created on 2004. 9. 23.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.faq.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.common.CommonUtil;
import com.edutrack.faq.dto.FaqCategoryDTO;
import com.edutrack.faq.dto.FaqContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FaqDAO extends AbstractDAO {

	/**
	 * 사용자 - FAQ정보 리스트를  가져온다.
	 * @param systemcode
	 * @param cateid
	 * @param keyword
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getFaqContentsList(String systemcode, String cateid, String search ) throws DAOException{
		ArrayList faqcontentsList			=	null;
		FaqContentsDTO faqcontentsDto 	= 	null;

		ResultSet rs = null;
//		Connection conn = null;
//	    DBConnectionManager pool = null;
	    StringBuffer output = new StringBuffer();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select seq_no, subject, contents, keyword, rfile_name, sfile_name, ");
	 	sb.append(" file_path, file_size, reg_id, reg_date, mod_id, mod_date ");
		sb.append(" from faq_contents ");
		sb.append(" where system_code = ? ");

		//검색이 있을경우(전체 카테고리에서 제목, 내용검색)
		if(!search.equals("")){
			sb.append(" and ( subject like ? or keyword like ? ) ");
		}else{
			sb.append(" and cate_id = ? ");
		}

		sb.append(" order by seq_no desc ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		if(!search.equals("")){
			 sql.setString("%"+search+"%");
			 sql.setString("%"+search+"%");
		}else{
			sql.setString(cateid);
		}

		log.debug("[getFaqContentsList]" + sql.toString());
		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);

			faqcontentsList		= 	new ArrayList();

  			while(rs.next()){
				faqcontentsDto 		= 	new FaqContentsDTO();
				faqcontentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				faqcontentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				faqcontentsDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				faqcontentsDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				faqcontentsDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				faqcontentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				faqcontentsDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));
				faqcontentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				faqcontentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        faqcontentsDto.setContents(StringUtil.nvl(output.toString()));
//		        output.delete(0,output.length());


				faqcontentsList.add(faqcontentsDto);
			}
  			rs.close();
		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
		 	try{
				if(rs != null) rs.close();
//			    if(conn != null){
//			        conn.setAutoCommit( true );
//			        pool.freeConnection(conn); // 컨넥션 해제
//			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }
		return faqcontentsList;
	}

	/**
	 * FAQ 정보 리스트를  가져온다.
	 * @param systemcode
	 * @param userid
	 * @param workfg
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getFaqCategoryList(String systemcode) throws DAOException{
		ArrayList faqcategoryList			=	null;
		FaqCategoryDTO faqcategoryDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select cate_id, cate_name, cate_comment, reg_id, reg_date, mod_id, mod_date, ");
		sb.append(" ( select  count(*) from faq_contents b ");
		sb.append(" where a.system_code=b.system_code and a.cate_id=b.cate_id) as con_cnt ");
		sb.append(" from faq_category a ");
		sb.append(" where system_code = ? ");
		sb.append(" order by cate_id desc ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);

		log.debug("[getFaqCategoryList]" + sql.toString());
		RowSet rs 				= 	null;
		try{
			rs 					= 	broker.executeQuery(sql);

			faqcategoryList		= 	new ArrayList();
			while(rs.next()){
				faqcategoryDto	= 	new FaqCategoryDTO();
				faqcategoryDto.setCateId(rs.getInt("cate_id"));
				faqcategoryDto.setCateName(StringUtil.nvl(rs.getString("cate_name")));
				faqcategoryDto.setXcomment(StringUtil.nvl(rs.getString("cate_comment")));
				faqcategoryDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				faqcategoryDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				faqcategoryDto.setConCnt(rs.getInt("con_cnt"));
				faqcategoryList.add(faqcategoryDto);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return faqcategoryList;
	}

	/**
	 * FAQ 정보를  가져온다.
	 * @param systemcode
	 * @param cateid
	 * @return
	 * @throws DAOException
	 */
	public FaqCategoryDTO getFaqCategory(String systemcode, String cateid) throws DAOException{
		FaqCategoryDTO faqcategoryDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select cate_name, cate_comment, reg_id, reg_date ");
		sb.append(" from faq_category  ");
		sb.append(" where system_code = ? and cate_id = ?");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(cateid);

		log.debug("[getFaqCategory]" + sql.toString());
		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				faqcategoryDto 		= 	new FaqCategoryDTO();
				faqcategoryDto.setCateName(StringUtil.nvl(rs.getString("cate_name")));
				faqcategoryDto.setXcomment(StringUtil.nvl(rs.getString("cate_comment")));
				faqcategoryDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				faqcategoryDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
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
		return faqcategoryDto;
	}

	/**
	 * FAQ 카테고리 최대값을 가져온다
	 * @param systemcode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCateId(String systemcode) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(max(cate_id),0)+1 as cate_id ");
	 sb.append(" from faq_category ");
	 sb.append(" where system_code = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);

	 log.debug("[getCateId]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
			maxId = rs.getInt("cate_id");
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

	 return maxId;
	}

	/**
	 * FAQ정보를 입력한다.
	 * @param faqcategory
	 * @return
	 * @throws DAOException
	 */
	public int addFaqCategory(FaqCategoryDTO faqcategory) throws DAOException{
		 int retVal = 0;

		 //카테고리아이디 최대값
		 int cate_id = getMaxCateId(faqcategory.getSystemCode());

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into faq_category ");
		 sb.append(" (system_code, cate_id, cate_name, cate_comment, reg_id, reg_date)");
		 sb.append(" values( ?, ?, ?, ?, ?, ? ) ");
		 sql.setSql(sb.toString());

		 sql.setString(faqcategory.getSystemCode());
		 sql.setInteger(cate_id);
		 sql.setString(faqcategory.getCateName());
		 sql.setString(faqcategory.getXcomment());
		 sql.setString(faqcategory.getRegId());
		 sql.setString(CommonUtil.getCurrentDate());

		 log.debug("[addFaqCategory]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * FAQ정보를 수정한다.
	 * @param faqcategory
	 * @return
	 * @throws DAOException
	 */
	public int editFaqCategory(FaqCategoryDTO faqcategory) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update faq_category ");
		 sb.append(" set cate_name= ?, cate_comment=?, mod_id=?, mod_date=? ");
		 sb.append(" where system_code = ? and cate_id = ? ");

		 sql.setSql(sb.toString());
		 sql.setString(faqcategory.getCateName());
		 sql.setString(faqcategory.getXcomment());
		 sql.setString(faqcategory.getModId());
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(faqcategory.getSystemCode());
		 sql.setInteger(faqcategory.getCateId());

		 log.debug("[editFaqCategory]" + sql.toString());
		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * FAQ정보를 삭제한다.
	 * @param faqcategory
	 * @return
	 * @throws DAOException
	 */
	public int delFaqCategory(FaqCategoryDTO faqcategory) throws DAOException{
		 boolean retVal = false;
		 int retVal2 = 0;

		 //contents 삭제
		 QueryStatement sql1 = new QueryStatement();
		 StringBuffer sb1 = new StringBuffer();
		 sb1.append(" delete from faq_contents ");
		 sb1.append(" where system_code = ? and cate_id = ? ");
		 sql1.setSql(sb1.toString());
		 sql1.setString(faqcategory.getSystemCode());
		 sql1.setInteger(faqcategory.getCateId());

		 //카테고리삭제
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 sb2.append(" delete from faq_category ");
		 sb2.append(" where system_code = ? and cate_id = ? ");
		 sql2.setSql(sb2.toString());
		 sql2.setString(faqcategory.getSystemCode());
		 sql2.setInteger(faqcategory.getCateId());

		 ISqlStatement[] sqlArray = new ISqlStatement[2];

		 sqlArray[0] = sql1;
		 sqlArray[1] = sql2;


		 log.debug("[FAQCONTENTSDELETE]" + sql1.toString());
		 log.debug("[FAQCATEGORYDELETE]" + sql2.toString());

		 try{
			retVal = broker.executeUpdate(sqlArray);

			/**
			 * 반환값을 1, 0으로 성공(1), 실패(0)를 판단한다.
			 * DAO에 있는 반환값을 int로 하기때문에 아래와 같이 변환한다.
			 */
			if(retVal)
				retVal2 = 1;
			else
				retVal2 = 0;
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal2;
	}

	/**
	 * 게시판 리스트를 페이징객체로 가져온다.
	 * @param curpage
	 * @param systemcode
	 * @param cateid
	 * @param op
	 * @param search
	 * @return
	 * @throws DAOException
	 */
	public ListDTO FaqContentsPagingList(int curpage, String systemcode, String cateid, String op, String search) throws DAOException{
		ListDTO retVal 		= 	null;
		try{
			ListStatement sql = new ListStatement();

			sql.setTotalCol("seq_no");
			sql.setCutCol(" concat(cast(cate_id AS CHAR), cast(seq_no AS CHAR))");
			sql.setAlias("system_code, cate_id, seq_no, subject,  keyword, rfile_name, "+
	 					  "sfile_name, file_path, file_size, reg_id, reg_date, mod_id, mod_date ");
			sql.setSelect("system_code, cate_id, seq_no, subject, contents, keyword, rfile_name, "+
	 					  "sfile_name, file_path, file_size, reg_id, reg_date, mod_id, mod_date ");
			sql.setFrom("faq_contents");
			sql.setWhere("system_code = "+"'"+systemcode+"' and cate_id= "+"'"+cateid+"' ");
			if (!search.equals("")) {
				sql.setWhere("and "+op+" like %"+search+"% ");
			}
			sql.setOrderby("seq_no desc");

			log.debug("[FaqContentsPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 게시물 정보를  가져온다.
	 * @param systemcode
	 * @param cateid
	 * @param seqno
	 * @return
	 * @throws DAOException
	 */
	public FaqContentsDTO getFaqContents(String systemcode, int cateid, int seqno) throws DAOException{
		FaqContentsDTO faqcontentsDto 	= 	null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select subject, contents, keyword, rfile_name, sfile_name, ");
		sb.append(" file_path, file_size, reg_id, reg_date ");
		sb.append(" from faq_contents ");
		sb.append(" where system_code = ? and cate_id = ? and seq_no = ? ");
		sql.setSql(sb.toString());

		sql.setString(systemcode);
		sql.setInteger(cateid);
		sql.setInteger(seqno);
		log.debug("[getFaqContents]" + sql.toString());

		ResultSet rs = null;
//        Connection conn = null;
//        DBConnectionManager pool = null;
		StringBuffer output = new StringBuffer();

		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if(rs.next()){
				faqcontentsDto 		= 	new FaqContentsDTO();
				faqcontentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				faqcontentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				faqcontentsDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				faqcontentsDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				faqcontentsDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				faqcontentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				faqcontentsDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));
				faqcontentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				faqcontentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
			}
//	        faqcontentsDto.setContents(StringUtil.nvl(output.toString()));
//	        output.delete(0,output.length());
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
//			    if(conn != null){
//			        conn.setAutoCommit( true );
//			        pool.freeConnection(conn); // 컨넥션 해제
//			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return faqcontentsDto;
	}

	/**
	 * SeqNo 최대값을 가져온다
	 * @param systemcode
	 * @param cateid
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String systemcode, int cate_id) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(max(seq_no),0)+1 as seq_no ");
	 sb.append(" from faq_contents ");
	 sb.append(" where system_code = ? and cate_id = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setInteger(cate_id);

	 log.debug("[getMaxSeqNo]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
			maxId = rs.getInt("seq_no");
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

	 return maxId;
	}

	/**
	 * 게시물을 등록한다.
	 * @param faqcontents
	 * @return
	 * @throws DAOException
	 */
	public int addFaqContents(FaqContentsDTO faqcontents) throws DAOException{
		 int retVal = 0;

		 //seq_no 최대값
		 int seq_no = getMaxSeqNo(faqcontents.getSystemCode(), faqcontents.getCateId());

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into faq_contents ");
		 sb.append(" (system_code, cate_id, seq_no, subject, contents, keyword, rfile_name, ");
		 sb.append(" sfile_name, file_path, file_size, reg_id, reg_date ) ");
		 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
		 sql.setSql(sb.toString());

		 sql.setString(faqcontents.getSystemCode());
		 sql.setInteger(faqcontents.getCateId());
		 sql.setInteger(seq_no);
		 sql.setString(faqcontents.getSubject());
		 sql.setString(faqcontents.getContents());
		 sql.setString(faqcontents.getKeyword());
		 sql.setString(faqcontents.getRfileName());
		 sql.setString(faqcontents.getSfileName());
		 sql.setString(faqcontents.getFilePath());
		 sql.setString(faqcontents.getFileSize());
		 sql.setString(faqcontents.getRegId());
		 sql.setString(CommonUtil.getCurrentDate());

		 log.debug("[addFaqContents]" + sql.toString());
		 try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

//		 Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select contents from faq_contents ");
//			 sb2.append(" where system_code =? and cate_id =? and seq_no =? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(faqcontents.getSystemCode());
//			 sql2.setInteger(faqcontents.getCateId());
//			 sql2.setInteger(seq_no);
//			 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(faqcontents.getContents().toCharArray());
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		 return retVal;
	}

	/**
	 * 게시물을 수정한다.
	 * @param faqcontents
	 * @return
	 * @throws DAOException
	 */
	public int editFaqContents(FaqContentsDTO faqcontents) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update faq_contents set ");
		 sb.append(" subject=?, contents=?, keyword=?, rfile_name=?, sfile_name=?,  ");
		 sb.append(" file_path=?, file_size=?, mod_id=?, mod_date= ?  ");
		 sb.append(" where system_code =? and cate_id =? and seq_no =? ");
		 sql.setSql(sb.toString());

		 sql.setString(faqcontents.getSubject());
		 sql.setString(faqcontents.getContents());
		 sql.setString(faqcontents.getKeyword());
		 sql.setString(faqcontents.getRfileName());
		 sql.setString(faqcontents.getSfileName());
		 sql.setString(faqcontents.getFilePath());
		 sql.setString(faqcontents.getFileSize());
		 sql.setString(faqcontents.getModId());
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(faqcontents.getSystemCode());
		 sql.setInteger(faqcontents.getCateId());
		 sql.setInteger(faqcontents.getSeqNo());

		 log.debug("[editFaqContents]" + sql.toString());
		 try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

//		 Connection conn = null;
//		 DBConnectionManager pool = null;
//		 ResultSet rs = null;
//		 QueryStatement sql2 = new QueryStatement();
//		 StringBuffer sb2 = new StringBuffer();
//		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );
//
//		    retVal = broker.executeUpdate(sql,conn);
//
//			 sb2.append(" select contents from faq_contents ");
//			 sb2.append(" where system_code =? and cate_id =? and seq_no =? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(faqcontents.getSystemCode());
//			 sql2.setInteger(faqcontents.getCateId());
//			 sql2.setInteger(faqcontents.getSeqNo());
//			 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(faqcontents.getContents().toCharArray());
//	     		char[] buffer = new char[1024];
//	     		int read = 0;
//	     		while ( (read = src.read(buffer,0,1024)) != -1) {
//	     			writer.write(buffer, 0, read);
//	     		}
//	     		//clob.close();
//	     		src.close();
//	     		writer.close();
//	     	}
//			conn.commit();
//
//		 }catch(Exception e){
//		 	e.printStackTrace();
//			try {
//				if(conn != null) conn.rollback();
//			} catch(SQLException ignore) {
//				log.error(ignore.getMessage(), ignore);
//			}
//			throw new DAOException(e.getMessage());
//		 } finally {
//			try {
//				if(conn != null){
//					conn.setAutoCommit( true );
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
//			} catch(Exception ignore) {
//				log.error(ignore.getMessage(),ignore);
//			}
//		}
		 return retVal;
	}


	/**
	 * 게시물을 삭제한다.
	 * @param foruminfo
	 * @return
	 * @throws DAOException
	 */
	public int delFaqContents(FaqContentsDTO faqcontents) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from faq_contents  ");
		 sb.append(" where system_code =? and cate_id =? and seq_no =? ");

		 sql.setSql(sb.toString());
		 sql.setString(faqcontents.getSystemCode());
		 sql.setInteger(faqcontents.getCateId());
		 sql.setInteger(faqcontents.getSeqNo());

		 log.debug("[FAQCONTENTSDELETE]" + sql.toString());

		 try{
			retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * cate_id 값을 가져온다
	 * @param systemcode
	 * @return
	 * @throws DAOException
	 */
	public String getMinCateId(String systemcode) throws DAOException{
		String categoryid = "";

		StringBuffer sb = new StringBuffer();
		sb.append(" select cate_id ");
		sb.append(" from faq_category ");
		sb.append(" where system_code = ? ");
		sb.append(" order by cate_id desc ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);

		log.debug("[getMinCateId]" + sql.toString());
		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			if(rs.next())
				categoryid = Integer.toString(rs.getInt("cate_id"));
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return categoryid;
	}


}
