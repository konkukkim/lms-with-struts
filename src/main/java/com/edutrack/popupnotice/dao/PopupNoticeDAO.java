/*
 * Created on 2004. 10. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.popupnotice.dao;

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
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.popupnotice.dto.PopupNoticeDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PopupNoticeDAO extends AbstractDAO {


	/**
	 * 팝업공지 리스트를 페이징객체로 가져온다.
	 * @param curpage
	 * @param systemcode
	 * @param op
	 * @param search
	 * @return
	 * @throws DAOException
	 */
	public ListDTO PopupNoticePagingList(int curpage, String systemcode, String op, String search) throws DAOException{
		ListDTO retVal 		= 	null;
		try{
			ListStatement sql = new ListStatement();

			sql.setTotalCol("seq_no");
			sql.setCutCol("seq_no");
			sql.setAlias("seq_no, subject, keyword, win_width, win_height, "+
	 					  "win_xposition, win_yposition, use_yn, reg_id, reg_date, popup_target  ");
			sql.setSelect("seq_no, subject, keyword, win_width, win_height, "+
	 					  "win_xposition, win_yposition, use_yn, reg_id, reg_date, popup_target  ");
			sql.setFrom("popup_notice");
			sql.setWhere("system_code = "+"'"+systemcode+"' ");
			if (!search.equals("")) {
				sql.setWhere("and "+op+" like %"+search+"% ");
			}
			sql.setOrderby("seq_no desc");

			log.debug("[PopupNoticePagingList]" + sql.toString());
			//System.out.println("[PopupNoticePagingList]" + sql.toString());
			
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
	 * 팝업공지 정보를  가져온다.
	 * @param systemcode
	 * @param seqno
	 * @return
	 * @throws DAOException
	 */
	public PopupNoticeDTO getPopupNotice(String systemcode, int seqno) throws DAOException{
		PopupNoticeDTO popupnoticeDto 	= 	null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select seq_no, subject, keyword, contents, win_width, win_height, ");
		sb.append(" win_xposition, win_yposition, use_yn, reg_id, reg_date, popup_target ");
		sb.append(" from popup_notice ");
		sb.append(" where system_code = ? and seq_no = ? ");
		sql.setSql(sb.toString());

		sql.setString(systemcode);
		sql.setInteger(seqno);
		log.debug("[getPopupNotice]" + sql.toString());

		ResultSet rs = null;
//		Connection conn = null;
//	    DBConnectionManager pool = null;
//	    StringBuffer output = new StringBuffer();
		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if(rs.next()){
				popupnoticeDto 		= 	new PopupNoticeDTO();
				popupnoticeDto.setSeqNo(rs.getInt("seq_no"));
				popupnoticeDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				popupnoticeDto.setContents(StringUtil.nvl(rs.getString("contents")));
				popupnoticeDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				popupnoticeDto.setWinWidth(rs.getInt("win_width"));
				popupnoticeDto.setWinHeight(rs.getInt("win_height"));
				popupnoticeDto.setWinXposition(rs.getInt("win_xposition"));
				popupnoticeDto.setWinYposition(rs.getInt("win_yposition"));
				popupnoticeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
				popupnoticeDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				popupnoticeDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				popupnoticeDto.setPopupTarget(StringUtil.nvl(rs.getString("popup_target")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        popupnoticeDto.setContents(StringUtil.nvl(output.toString()));
//		        output.delete(0,output.length());
				rs.close();
			}

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
		return popupnoticeDto;
	}

	/**
	 * SeqNo 최대값을 가져온다
	 * @param systemcode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String systemcode) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(max(seq_no),0)+1 as seq_no ");
	 sb.append(" from popup_notice ");
	 sb.append(" where system_code = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);

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
	 * 팝업공지를 등록한다.
	 * @param popupnotice
	 * @return
	 * @throws DAOException
	 */
	public int addPopupNotice(PopupNoticeDTO popupnotice) throws DAOException{
		 int retVal = 0;

		 //seq_no 최대값
		 int seq_no = getMaxSeqNo(popupnotice.getSystemCode());

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into popup_notice ");
		 sb.append(" (system_code, seq_no, subject, contents, keyword, win_width, win_height, ");
		 sb.append(" win_xposition, win_yposition, use_yn, reg_id, reg_date, popup_target ) ");
		 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
		 sql.setSql(sb.toString());

		 sql.setString(popupnotice.getSystemCode());
		 sql.setInteger(seq_no);
		 sql.setString(popupnotice.getSubject());
		 sql.setString(popupnotice.getContents());
		 sql.setString(popupnotice.getKeyword());
		 sql.setInteger(popupnotice.getWinWidth());
		 sql.setInteger(popupnotice.getWinHeight());
		 sql.setInteger(popupnotice.getWinXposition());
		 sql.setInteger(popupnotice.getWinYposition());
		 sql.setString(popupnotice.getUseYn());
		 sql.setString(popupnotice.getRegId());
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(popupnotice.getPopupTarget());

		 log.debug("[addPopupNotice]" + sql.toString());

		 Connection conn = null;
		 DBConnectionManager pool = null;
		 ResultSet rs = null;
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 try{
		 	pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql,conn);

		    /*
			 sb2.append(" select contents from popup_notice ");
			 sb2.append(" where system_code = ?  and seq_no = ? FOR UPDATE");
			 sql2.setSql(sb2.toString());
			 sql2.setString(popupnotice.getSystemCode());
			 sql2.setInteger(seq_no);
			 log.debug("[UseAdd_editPopupNotice]" + sql2.toString());

			 rs = broker.executeQuery(sql2,conn);
	     	if(rs.next()){
//	     		log.debug("______ rs "+rs.getClob(1));
	     		CLOB clob = (CLOB)rs.getClob("contents");
	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
	     		Writer writer = clob.getCharacterOutputStream();
	     		Reader src = new CharArrayReader(popupnotice.getContents().toCharArray());
	     		char[] buffer = new char[1024];
	     		int read = 0;
	     		while ( (read = src.read(buffer,0,1024)) != -1) {
	     			writer.write(buffer, 0, read);
	     		}
	     		//clob.close();
	     		src.close();
	     		writer.close();
	     	}
	     	*/
			conn.commit();

		 }catch(Exception e){
		 	e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch(SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			throw new DAOException(e.getMessage());
		 } finally {
			try {
				if(conn != null){
					conn.setAutoCommit( true );
					pool.freeConnection(conn); // 컨넥션 해제
				}
			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
		 return retVal;
	}

	/**
	 * 팝업공지를 수정한다.
	 * @param popupnotice
	 * @return
	 * @throws DAOException
	 */
	public int editPopupNotice(PopupNoticeDTO popupnotice) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update popup_notice set ");
		 sb.append(" subject=?, contents=?, keyword=?, win_width=?, win_height=?,  ");
		 sb.append(" win_xposition=?, win_yposition=?, use_yn=?, mod_id=?, mod_date= ?, popup_target=?  ");
		 sb.append(" where system_code =? and seq_no =? ");
		 sql.setSql(sb.toString());

		 sql.setString(popupnotice.getSubject());
		 sql.setString(popupnotice.getContents());
		 sql.setString(popupnotice.getKeyword());
		 sql.setInteger(popupnotice.getWinWidth());
		 sql.setInteger(popupnotice.getWinHeight());
		 sql.setInteger(popupnotice.getWinXposition());
		 sql.setInteger(popupnotice.getWinYposition());
		 sql.setString(popupnotice.getUseYn());
		 sql.setString(popupnotice.getModId());
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(popupnotice.getPopupTarget());
		 sql.setString(popupnotice.getSystemCode());
		 sql.setInteger(popupnotice.getSeqNo());

		 log.debug("[editPopupNotice]" + sql.toString());

		 Connection conn = null;
		 DBConnectionManager pool = null;
		 ResultSet rs = null;
		 QueryStatement sql2 = new QueryStatement();
		 //StringBuffer sb2 = new StringBuffer();
		 try{
		 	pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql,conn);

		    /*
			 sb2.append(" select contents from popup_notice ");
			 sb2.append(" where system_code = ?  and seq_no = ? FOR UPDATE");
			 sql2.setSql(sb2.toString());
			 sql2.setString(popupnotice.getSystemCode());
			 sql2.setInteger(popupnotice.getSeqNo());
			 log.debug("[UseAdd_editPopupNotice]" + sql2.toString());

			 rs = broker.executeQuery(sql2,conn);
	     	if(rs.next()){
//	     		log.debug("______ rs "+rs.getClob(1));
	     		CLOB clob = (CLOB)rs.getClob("contents");
	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
	     		Writer writer = clob.getCharacterOutputStream();
	     		Reader src = new CharArrayReader(popupnotice.getContents().toCharArray());
	     		char[] buffer = new char[1024];
	     		int read = 0;
	     		while ( (read = src.read(buffer,0,1024)) != -1) {
	     			writer.write(buffer, 0, read);
	     		}
	     		//clob.close();
	     		src.close();
	     		writer.close();
	     	}
	     	*/
			conn.commit();

		 }catch(Exception e){
		 	e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch(SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			throw new DAOException(e.getMessage());
		 } finally {
			try {
				if(conn != null){
					conn.setAutoCommit( true );
					pool.freeConnection(conn); // 컨넥션 해제
				}
			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
		 return retVal;
	}


	/**
	 * 팝업공지를 삭제한다.
	 * @param popupnotice
	 * @return
	 * @throws DAOException
	 */
	public int delPopupNotice(PopupNoticeDTO popupnotice) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from popup_notice  ");
		 sb.append(" where system_code =? and seq_no =? ");

		 sql.setSql(sb.toString());
		 sql.setString(popupnotice.getSystemCode());
		 sql.setInteger(popupnotice.getSeqNo());

		 log.debug("[delPopupNotice]" + sql.toString());

		 try{
			retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 사용중인 팝업공지 한개를 가져온다.
	 * @param systemcode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getUsePopupNotice(String systemcode,String popupTarget) throws DAOException{
		ArrayList popupnoticeList		=	null;
		PopupNoticeDTO popupnoticeDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select seq_no, subject, keyword, contents, win_width, win_height, ");
		sb.append(" win_xposition, win_yposition, use_yn, reg_id, reg_date, popup_target ");
		sb.append(" from popup_notice ");
		sb.append(" where system_code = ? and use_yn='Y' and popup_target = ? ");
		sb.append(" order by seq_no desc ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(popupTarget);

		log.debug("[getUsePopupNotice]" + sql.toString());
		ResultSet rs = null;
//		Connection conn = null;
//	    DBConnectionManager pool = null;
//	    StringBuffer output = new StringBuffer();
		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			popupnoticeList		= 	new ArrayList();
			while(rs.next()){
				popupnoticeDto 		= 	new PopupNoticeDTO();
				popupnoticeDto.setSeqNo(rs.getInt("seq_no"));
				popupnoticeDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				popupnoticeDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				popupnoticeDto.setContents(StringUtil.nvl(rs.getString("contents")));
				popupnoticeDto.setWinWidth(rs.getInt("win_width"));
				popupnoticeDto.setWinHeight(rs.getInt("win_height"));
				popupnoticeDto.setWinXposition(rs.getInt("win_xposition"));
				popupnoticeDto.setWinYposition(rs.getInt("win_yposition"));
				popupnoticeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
				popupnoticeDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				popupnoticeDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				popupnoticeDto.setPopupTarget(StringUtil.nvl(rs.getString("popup_target")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        popupnoticeDto.setContents(StringUtil.nvl(output.toString()));
//		        output.delete(0,output.length());

				popupnoticeList.add(popupnoticeDto);
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
		return popupnoticeList;
	}


}
