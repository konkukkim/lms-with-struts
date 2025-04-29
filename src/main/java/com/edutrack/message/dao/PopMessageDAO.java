/*
 * Created on 2004. 10. 9.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.message.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.message.dto.MessageDTO;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author pearlm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PopMessageDAO extends AbstractDAO {


	/**
	 * 쪽지를 전송한다.
	 * @param message
	 * @return
	 * @throws DAOException
	 */
	public boolean sendMessage(MessageDTO message, ArrayList userlist) throws DAOException{
		 boolean retVal = false;

		 PopMessageDAO popmessageDao = new PopMessageDAO();
		 UsersDTO usersDto = null;
		 int seq_no = popmessageDao.getMaxSeqNo(message.getSystemCode(),message.getRegId());

		 QueryStatement sql = null;
		 StringBuffer sb = null;
		 ISqlStatement[] sqlArray = null;
		 sqlArray = new ISqlStatement[userlist.size()];

		 for(int i=0; i < userlist.size(); i++){
		     sql = new QueryStatement();
			 sb = new StringBuffer();
			 usersDto = (UsersDTO)userlist.get(i);

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

			 log.debug("[sendMessage]" + sql);
			 sqlArray[i] =  sql;

		 }//end for

		try{
			retVal		=	broker.executeUpdate(sqlArray);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
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


}
