/*
 * Created on 2004. 12. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.community.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.community.dto.CommInviteDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommInviteDAO extends AbstractDAO {

	/**
	 *
	 */
	public CommInviteDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList inviteUserList(String systemcode, String op, String search, String commid) throws DAOException{
		ArrayList userList = null;
	    UsersDTO ui = null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select u.user_id, u.user_name ");
		sb.append(" from users u ");
		sb.append(" where u.system_code = ? ");
		sb.append(" and user_id not in (select user_id from comm_members where system_code = u.system_code and comm_id = ? ) ");
		sb.append(" and u.user_type <> 'M' ");

		if(op.equals("user_id")){
			sb.append(" and u.user_id like ? ");
		}else if(op.equals("user_name")){
			sb.append(" and u.user_name like ? ");
		}
		sb.append(" order by u.user_name");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		if(!op.equals("")){
			sql.setString("%"+search+"%");
		}

		log.debug("[inviteUserList]" + sql.toString());
		RowSet rs = null;

		try{

			 rs = broker.executeQuery(sql);
			 userList = new ArrayList();

			 while(rs.next()){
				ui = new UsersDTO();
				ui.setUserId(StringUtil.nvl(rs.getString("user_id")));
				ui.setUserName(StringUtil.nvl(rs.getString("user_name")));
				userList.add(ui);
			 }

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return userList;
	}

	/**
	 * @param systemCode
	 * @param commId
	 * @param userId
	 * @param inviteUserList
	 */
	public boolean inviteUserRegist(String systemCode, String commId, String commName, String userId, String userName,String[] inviteUserList, String contents) throws DAOException{
		 boolean retVal = false;
		 QueryStatement[] sqls = new QueryStatement[inviteUserList.length];
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into comm_invite(system_code, user_id, seq_no, sender_id, subject,contents, read_yn, comm_id, comm_name, reg_id, reg_date) ");
		 sb.append(" select ?, ?, ifnull(max(seq_no),0)+1 as seq_no, ?, ?, ?,'N',?,?,?,date_format(now(), '%Y%m%d%H%i%s') ");
		 sb.append(" from comm_invite  ");
		 sb.append(" where system_code = ? and user_id = ? ");
		 String subjectFront = userName+StringUtil.outDataConverter("님께서 <br>");
		 String subjectBack = StringUtil.outDataConverter("님을 커뮤니티 : ")+commName+StringUtil.outDataConverter("로 초대를 하였습니다.");

         // 학생님이 교수님을 커뮤니티 : jsp강좌로 초대를 하였습니다.
		 // contents
		 // 커뮤니티에 방문하실려면 이동을 눌러주세요.  <a href="goCommunity("<%=commid%>")">이동</a>
		 QueryStatement sql = null;
		 String[] userInfo = null;
		 for(int i =0; i < inviteUserList.length;i++){
		 	userInfo = StringUtil.split(inviteUserList[i],"/");
		 	sql = new QueryStatement();
		 	sql.setSql(sb.toString());

		 	sql.setString(systemCode);
		 	sql.setString(userInfo[0]);
		 	sql.setString(userId);
		 	sql.setString(subjectFront+userInfo[1]+subjectBack);
		 	sql.setString(contents);
		 	sql.setInteger(commId);
		 	sql.setString(commName);
		 	sql.setString(userId);
		 	sql.setString(systemCode);
		 	sql.setString(userInfo[0]);

		 	sqls[i] = sql;
		 }

		 log.debug("[inviteUserRegist]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	public int getInviteMsgCnt(String systemcode,String userid) throws DAOException{
		 int  retVal = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(seq_no) as cnt ");
		 sb.append(" from comm_invite ");
		 sb.append(" where system_code = ? and user_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(userid);

		 log.debug("[getInviteMsgCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	retVal = rs.getInt("cnt");
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

		 return retVal;
	}

	public ArrayList getInviteMsg(String systemcode, String userid) throws DAOException{
		 ArrayList  msgList = new ArrayList();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select v.seq_no, v.sender_id, ");
		 sb.append(" (select user_name from users where system_code = v.system_code and  user_id = v.sender_id ) as sender_name, ");
		 sb.append(" subject, contents, comm_id, comm_name ");
		 sb.append(" from comm_invite v ");
		 sb.append(" where v.system_code = ? and v.user_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(userid);

		 log.debug("[getInviteMsg]" + sql.toString());
		 CommInviteDTO inviteDto = null;
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 while(rs.next()){
			 	inviteDto = new CommInviteDTO();

			 	inviteDto.setSeqNo(rs.getInt("seq_no"));
			 	inviteDto.setSenderId(rs.getString("sender_id"));
			 	inviteDto.setSenderName(rs.getString("sender_name"));
			 	inviteDto.setSubject(rs.getString("subject"));
			 	inviteDto.setContents(rs.getString("contents"));
			 	inviteDto.setCommId(rs.getInt("comm_id"));
			 	inviteDto.setCommName(rs.getString("comm_name"));

			 	msgList.add(inviteDto);
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

		 return msgList;
	}

	/**
	 * @param systemCode
	 * @param userId
	 * @param seqNo
	 * @return
	 */
	public int delInviteMsg(String systemCode, String userId, String seqNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from comm_invite ");
		 sb.append(" where system_code = ? and user_id = ? ");
		 sql.setString(systemCode);
		 sql.setString(userId);

		 if(!seqNo.equals("")){
		 	sb.append("  and seq_no = ? ");
			sql.setInteger(seqNo);
		 }

		 sql.setSql(sb.toString());

		 log.debug("[delInviteMsg]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

}
