/*
 * Created on 2004. 10. 9.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.message.dao;

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

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.message.dto.MessageDTO;
import com.edutrack.user.dao.UserDAO;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessageDAO extends AbstractDAO {
	/**
	 * 받은쪽지 리스트를 페이징객체로 가져온다.
	 * @param curpage
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public ListDTO ReceiveMessagePagingList(int curpage, String systemcode, String userid) throws DAOException{
		ListDTO retVal 		= 	null;
		try{
			ListStatement sql = new ListStatement();

			sql.setTotalCol("seq_no");
			sql.setCutCol("seq_no");
			sql.setAlias("system_code, sender_id, seq_no, sender_name, send_date, subject,  keyword, " +
						  "rfile_name, sfile_name, file_path, file_size, receive_date, reg_id, reg_date");
			sql.setSelect("system_code, sender_id, seq_no, sender_name, send_date, subject, keyword, " +
						  "rfile_name, sfile_name, file_path, file_size, receive_date, reg_id, reg_date");
			sql.setFrom("message");
			sql.setWhere("system_code = ? and receiver_id = ? and receiver_del_yn = 'N' ");
			sql.setString(systemcode);
			sql.setString(userid);
			sql.setOrderby("receive_date desc");

			log.debug("[ReceiveMessagePagingList]" + sql.toString());
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
	 * 보낸쪽지 리스트를 페이징객체로 가져온다.
	 * @param curpage
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public ListDTO SendMessagePagingList(int curpage, String systemcode, String userid) throws DAOException{
		ListDTO retVal 		= 	null;
		try{
			ListStatement sql = new ListStatement();

			sql.setTotalCol("seq_no");
			sql.setCutCol("seq_no");
			sql.setAlias("seq_no, receiver_id, receive_date, sender_id, send_date, receiver_name, subject," +
					      "  keyword, rfile_name, sfile_name, file_path, file_size,  reg_id," +
					      " reg_date");
			sql.setSelect("seq_no, receiver_id, receive_date, sender_id, send_date, receiver_name, subject," +
					      "  keyword, rfile_name, sfile_name, file_path, file_size,  reg_id," +
					      " reg_date");
			sql.setFrom("message");
			sql.setWhere("system_code = ? and sender_id = ? and sender_del_yn = 'N' ");
			sql.setString(systemcode);
			sql.setString(userid);
			sql.setOrderby("send_date desc");

			log.debug("[SendPagingList]" + sql.toString());
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
	 * 쪽지 정보를  가져온다.
	 * @param systemcode
	 * @param userid
	 * @param seqno
	 * @return
	 * @throws DAOException
	 */
	public MessageDTO getMessage(String systemcode, String userid, int seqno) throws DAOException{
		MessageDTO messageDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select receiver_id, receiver_name, sender_id, sender_name, subject, contents,");
		sb.append(" keyword, send_date, receive_date, rfile_name, sfile_name, file_path, file_size ");
		sb.append(" from message  ");
		sb.append(" where system_code = ? and sender_id = ? and seq_no =? ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(userid);
		sql.setInteger(seqno);

		log.debug("[getMessage]" + sql.toString());
		ResultSet rs = null;
//		Connection conn = null;
//	    DBConnectionManager pool = null;
	    StringBuffer output = new StringBuffer();
		try{
//			pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if(rs.next()){
				messageDto 		= 	new MessageDTO();
				messageDto.setReceiverId(StringUtil.nvl(rs.getString("receiver_id")));
				messageDto.setReceiverName(StringUtil.nvl(rs.getString("receiver_name")));
				messageDto.setSenderId(StringUtil.nvl(rs.getString("sender_id")));
				messageDto.setSenderName(StringUtil.nvl(rs.getString("sender_name")));
				messageDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				messageDto.setContents(StringUtil.nvl(rs.getString("contents")));
				messageDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				messageDto.setSendDate(StringUtil.nvl(rs.getString("send_date")));
				messageDto.setReceiveDate(StringUtil.nvl(rs.getString("receive_date")));
				messageDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				messageDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				messageDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				messageDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        messageDto.setContents(StringUtil.nvl(output.toString()));
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
		return messageDto;
	}

	/**
	 * SeqNo 최대값을 가져온다
	 * @param systemcode
	 * @param senderid
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String systemcode, String senderid) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(max(seq_no),0)+1 as seq_no ");
	 sb.append(" from message ");
	 sb.append(" where system_code = ? and sender_id = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setString(senderid);

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
	 * 쪽지를 전송한다.
	 * @param message
	 * @return
	 * @throws DAOException
	 */
	public int sendMessage(MessageDTO message) throws DAOException{
		 int retVal = 0;
		 ArrayList userlist = null;

		 UserDAO userDao = new UserDAO();
		 MessageDAO messageDao = new MessageDAO();
		 UsersDTO usersDto = null;
		 int seq_no = messageDao.getMaxSeqNo(message.getSystemCode(),message.getRegId());
		 int size = 0;
		 QueryStatement sql = null;
		 StringBuffer sb = null;
		 ISqlStatement[] sqlArray = null;

		 // 개인이 아닐경우
		 if(!message.getReceiveType().equals("A")){
		 	userlist = userDao.userList(message.getSystemCode(),message.getReceiveType(),"","","","");
		 	sqlArray = new ISqlStatement[userlist.size()];
			size = userlist.size();
		 }else{
		 	usersDto = userDao.getUserInfo(message.getSystemCode(),message.getReceiveId());
		 	usersDto.setUserId(usersDto.getUserId());
		 	usersDto.setUserName(usersDto.getUserName());
			sqlArray = new ISqlStatement[1];
			size = 1;
		 }
//		 Connection conn = null;
//		 DBConnectionManager pool = null;
		 ResultSet rs = null;
		 QueryStatement sql2 = null;
		 StringBuffer sb2 = null;
		 try{
//		 	pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
//			conn.setAutoCommit( false );


			 for(int i=0; i < size; i++){
				 sql = new QueryStatement();
				 sb = new StringBuffer();
				 sql2 = new QueryStatement();
				 sb2 = new StringBuffer();

			 	 if(!message.getReceiveType().equals("A")){
			 		usersDto = (UsersDTO)userlist.get(i);
			 	 }
			 	 //순번

				 sb.append(" insert into message ");
				 sb.append(" (system_code, sender_id, seq_no, sender_name, receiver_id,");
				 sb.append(" receiver_name, editor_type, subject, keyword, contents, ");
				 sb.append(" rfile_name,sfile_name, file_path, file_size, send_date,  ");
				 sb.append(" sender_del_yn, receiver_del_yn, receive_date, reg_id, reg_date ) ");
				 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,? , ?, ");
				 sb.append(" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),");
				 sb.append(" 'N', 'N', '',?, ");
				 sb.append(" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))");
				 sql.setSql(sb.toString());

				 sql.setString(message.getSystemCode());
				 sql.setString(message.getRegId());
				 sql.setInteger(seq_no+i);
				 sql.setString(message.getSenderName());
				 sql.setString(usersDto.getUserId());
				 sql.setString(usersDto.getUserName());
				 sql.setString(message.getEditorType());
				 sql.setString(message.getSubject());
				 sql.setString(message.getKeyword());
				 sql.setString(message.getContents());
				 sql.setString(message.getRfileName());
				 sql.setString(message.getSfileName());
				 sql.setString(message.getFilePath());
				 sql.setString(message.getFileSize());
				 sql.setString(message.getRegId());

				 log.debug("[sendMessage]" + sql.toString());
				 retVal = broker.executeUpdate(sql);
			 }//end for
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

//				 sb2.append(" select contents from message ");
//				 sb2.append(" where system_code = ? and sender_id = ? and seq_no = ? FOR UPDATE");
//				 sql2.setSql(sb2.toString());
//				 sql2.setString(message.getSystemCode());
//				 sql2.setString(message.getRegId());
//				 sql2.setInteger(seq_no+i);
//				 log.debug("[UseAdd_sendMessage]" + sql2.toString());
//
//				 rs = broker.executeQuery(sql2,conn);
//		     	if(rs.next()){
////		     		log.debug("______ rs "+rs.getClob(1));
//		     		CLOB clob = (CLOB)rs.getClob("contents");
//		     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//		     		Writer writer = clob.getCharacterOutputStream();
//		     		Reader src = new CharArrayReader(message.getContents().toCharArray());
//		     		char[] buffer = new char[1024];
//		     		int read = 0;
//		     		while ( (read = src.read(buffer,0,1024)) != -1) {
//		     			writer.write(buffer, 0, read);
//		     		}
//		     		//clob.close();
//		     		src.close();
//		     		writer.close();
//		     	}
//				conn.commit();

//			 }//end for
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

	public boolean sendMessage_old(MessageDTO message) throws DAOException{
		 boolean retVal = false;
		 ArrayList userlist = null;

		 UserDAO userDao = new UserDAO();
		 MessageDAO messageDao = new MessageDAO();
		 UsersDTO usersDto = null;
		 int seq_no = messageDao.getMaxSeqNo(message.getSystemCode(),message.getRegId());
		 int size = 0;
		 QueryStatement sql = null;
		 StringBuffer sb = null;
		 ISqlStatement[] sqlArray = null;

		 // 개인이 아닐경우
		 if(!message.getReceiveType().equals("A")){
		 	userlist = userDao.userList(message.getSystemCode(),message.getReceiveType(),"","","","");
		 	sqlArray = new ISqlStatement[userlist.size()];
			size = userlist.size();
		 }else{
		 	usersDto = userDao.getUserInfo(message.getSystemCode(),message.getReceiveId());
		 	usersDto.setUserId(usersDto.getUserId());
		 	usersDto.setUserName(usersDto.getUserName());
			sqlArray = new ISqlStatement[1];
			size = 1;
		 }

		 for(int i=0; i < size; i++){
			 sql = new QueryStatement();
			 sb = new StringBuffer();

		 	 if(!message.getReceiveType().equals("A")){
		 		usersDto = (UsersDTO)userlist.get(i);
		 	 }
		 	 //순번

			 sb.append(" insert into message ");
			 sb.append(" (system_code, sender_id, seq_no, sender_name, receiver_id,");
			 sb.append(" receiver_name, editor_type, subject, keyword, contents, ");
			 sb.append(" rfile_name,sfile_name, file_path, file_size, send_date,  ");
			 sb.append(" sender_del_yn, receiver_del_yn, receive_date, reg_id, reg_date ) ");
			 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,? , ?, ");
			 sb.append(" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),");
			 sb.append(" 'N', 'N', '',?, ");
			 sb.append(" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))");
			 sql.setSql(sb.toString());

			 sql.setString(message.getSystemCode());
			 sql.setString(message.getRegId());
			 sql.setInteger(seq_no+i);
			 sql.setString(message.getSenderName());
			 sql.setString(usersDto.getUserId());
			 sql.setString(usersDto.getUserName());
			 sql.setString(message.getEditorType());
			 sql.setString(message.getSubject());
			 sql.setString(message.getKeyword());
			 sql.setString(message.getContents());
			 sql.setString(message.getRfileName());
			 sql.setString(message.getSfileName());
			 sql.setString(message.getFilePath());
			 sql.setString(message.getFileSize());
			 sql.setString(message.getRegId());

			 log.debug("[sendMessage]" + sql.toString());
			 sqlArray[i] =  sql;

		 }//end for

		try{
			retVal				=	broker.executeUpdate(sqlArray);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 삭제플래그가 있는지 가져온다
	 * @param systemcode
	 * @param senderid
	 * @return
	 * @throws DAOException
	 */
	public int getCntMessageDel(String systemcode, String senderid, int seqno) throws DAOException{
	 int  cnt = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(count(seq_no),0) as cnt ");
	 sb.append(" from message ");
	 sb.append(" where system_code = ? and sender_id = ?  and seq_no = ? ");
	 sb.append(" and (receiver_del_yn='Y' or sender_del_yn ='Y') ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setString(senderid);
	 sql.setInteger(seqno);

	 log.debug("[getCntMessageDel]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	cnt = rs.getInt("cnt");
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

	 return cnt;
	}


	/**
	 *  받은쪽지를 삭제한다(플래그변경-receiver_del_yn='Y')
	 * @param systemcode
	 * @param chk
	 * @return
	 * @throws DAOException
	 */
	public boolean delReceiveMessage(String systemcode, String[] chk) throws DAOException{
		boolean  retVal = false;
		ISqlStatement[] sqlArray = new ISqlStatement[chk.length];
		 QueryStatement sql = null;
		 StringBuffer sb = null;
	     String[] chkValue = new String[2];

	    MessageDAO	messageDao	=	new MessageDAO();
	    int del_cnt  = 0;

		for(int i=0; i < chk.length; i++){
			chkValue = StringUtil.split(chk[i],"|");
			sql = new QueryStatement();
			sb = new StringBuffer();

			del_cnt  = messageDao.getCntMessageDel(systemcode, chkValue[0], Integer.parseInt(chkValue[1]));

			//해당 쪽지의 삭제 플래그가 존재하면
			if(del_cnt >= 1){
				sb.append(" delete from message ");
				sb.append(" where system_code = ? and sender_id = ? and seq_no = ? ");
				sql.setSql(sb.toString());
				sql.setString(systemcode);
				sql.setString(chkValue[0]);	//user_id
				sql.setInteger(chkValue[1]); //seq_no

			}else{
				sb.append(" update message set receiver_del_yn='Y' ");
				sb.append(" where system_code = ? and sender_id = ? and seq_no = ? ");
				sql.setSql(sb.toString());
				sql.setString(systemcode);
				sql.setString(chkValue[0]);	//user_id
				sql.setInteger(chkValue[1]); //seq_no
			}

			log.debug("[delReceiveMessage]" + sql.toString());

			sqlArray[i] = sql;
		}

		try{
			retVal				=	broker.executeUpdate(sqlArray);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 *  보낸쪽지를 삭제한다(플래그변경-sender_del_yn='Y')
	 * @param systemcode
	 * @param chk
	 * @return
	 * @throws DAOException
	 */
	public boolean delSendMessage(String systemcode, String[] chk) throws DAOException{
		boolean  retVal = false;
		ISqlStatement[] sqlArray = new ISqlStatement[chk.length];
		 QueryStatement sql = null;
		 StringBuffer sb = null;
	     String[] chkValue = new String[2];

	    MessageDAO	messageDao	=	new MessageDAO();
	    int del_cnt  = 0;

		for(int i=0; i < chk.length; i++){
			chkValue = StringUtil.split(chk[i],"|");
			sql = new QueryStatement();
			sb = new StringBuffer();

			del_cnt  = messageDao.getCntMessageDel(systemcode, chkValue[0], Integer.parseInt(chkValue[1]));

			//해당 쪽지의 삭제 플래그가 존재하면
			if(del_cnt >= 1){
				sb.append(" delete from message ");
				sb.append(" where system_code = ? and sender_id = ? and seq_no = ? ");
				sql.setSql(sb.toString());
				sql.setString(systemcode);
				sql.setString(chkValue[0]);	//user_id
				sql.setInteger(chkValue[1]); //seq_no

			}else{

				sb.append(" update message set sender_del_yn='Y' ");
				sb.append(" where system_code = ? and sender_id = ? and seq_no = ? ");
				sql.setSql(sb.toString());
				sql.setString(systemcode);
				sql.setString(chkValue[0]);	//user_id
				sql.setInteger(chkValue[1]); //seq_no
			}

			log.debug("[delSendMessage]" + sql.toString());

			sqlArray[i] = sql;
		}

		try{
			retVal				=	broker.executeUpdate(sqlArray);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 쪽지를 삭제한다.
	 * @param systemcode
	 * @param senderid
	 * @param seqno
	 * @return
	 * @throws DAOException
	 */
	public int delMessage(String systemcode, String senderid, int seqno) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from message ");
		 sb.append(" where system_code = ? and sender_id = ? and seq_no = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(senderid);	//user_id
		 sql.setInteger(seqno); //seq_no

		 log.debug("[delMessage]" + sql.toString());

		 try{
			retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 족지를 수신일을 입력한다.
	 * @param systemcode
	 * @param sendid
	 * @param seqno
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int editReceiveDate(String systemcode, String sendid, int seqno, String userid) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update message ");
		 sb.append(" set receive_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 sb.append(" mod_id = ?, ");
		 sb.append(" mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		 sb.append(" where system_code = ? and sender_id = ? and seq_no=? ");

		 sql.setSql(sb.toString());
		 sql.setString(userid);
		 sql.setString(systemcode);
		 sql.setString(sendid);
		 sql.setInteger(seqno);

		 log.debug("[editReceiveDate]" + sql.toString());
		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}



	/**
	 * 읽지 않는 쪽지 갯수를 가져온다
	 * @param systemcode
	 * @param senderid
	 * @return
	 * @throws DAOException
	 */
	public int getUnReadCnt(String systemcode, String receiverid) throws DAOException{
	 int  cnt = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(Count(seq_no),0) as cnt ");
	 sb.append(" from message ");
	 sb.append(" where system_code = ? and receiver_id = ?  and (receive_date = '' or receive_date is null)");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setString(receiverid);

	 log.debug("[getUnReadCnt]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	cnt = rs.getInt("cnt");
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

	 return cnt;
	}

}
