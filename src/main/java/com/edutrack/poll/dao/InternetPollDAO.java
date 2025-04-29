/*
 * Created on 2004. 9. 20.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.poll.dao;

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
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.poll.dto.InternetPollCommentDTO;
import com.edutrack.poll.dto.InternetPollDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InternetPollDAO extends AbstractDAO {

	/**
	 * 인터넷 투표 리스트 가져오기
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getPollList(String Systemcode) throws DAOException{

		 ArrayList pollList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" select poll_id, start_date, end_date, status, subject, ");
		 sb.append(" hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8, hit9, hit10 ");
		 sb.append(" from internet_poll ");
		 sb.append(" where system_code = ? ");
		 sb.append(" order by poll_id desc ");

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 InternetPollDTO pollDto =	null;
			 pollList = new ArrayList();

			 while(rs.next()){
			 	pollDto = new InternetPollDTO();
			 	pollDto.setPollId(rs.getInt("poll_id"));
			 	pollDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	pollDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	pollDto.setStatus(StringUtil.nvl(rs.getString("status")));
			 	pollDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	//pollDto.setContents(StringUtil.nvl(rs.getString("contents")));

			 	//응답수
			 	int hit_count = rs.getInt("hit1")+rs.getInt("hit2")+rs.getInt("hit3")+rs.getInt("hit4")
				+rs.getInt("hit5")+rs.getInt("hit6")+rs.getInt("hit7")+rs.getInt("hit8")
				+rs.getInt("hit9")+rs.getInt("hit10");

			 	pollDto.setHitCnt(hit_count);
			 	pollList.add(pollDto);
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

		 return pollList;
	}


	/**
	 * 인터넷 투표 정보를 등록한다.
	 * @param poll
	 * @return
	 * @throws DAOException
	 */
	public int addPoll(InternetPollDTO poll) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" insert into internet_poll ");
	 sb.append(" (system_code, poll_id,start_date,end_date, status, subject, contents, ");
	 sb.append(" example_cnt, example1, example2,example3, example4, example5,");
	 sb.append(" example6, example7, example8, example9, example10, ");
	 sb.append(" hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8, hit9, hit10, reg_id, reg_date)");
	 sb.append(" values( ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
	 sb.append(" 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ?, ?)");
	 sql.setSql(sb.toString());

	 sql.setString(poll.getSystemCode());
	 sql.setInteger(poll.getPollId());
	 sql.setString(poll.getStartDate());
	 sql.setString(poll.getEndDate());
	 sql.setString(poll.getStatus());
	 sql.setString(poll.getSubject());
	 sql.setString(poll.getContents());
	 sql.setInteger(poll.getExample().length);

	 int i = 0;
	 for(i=0 ; i < poll.getExample().length ; i++) {
	 	sql.setString(poll.getExample()[i]);
	 }

	 if(i < 10 ) {
	 	for(int j=i ; j < 10; j++ ){
	 		sql.setString("");
	 	}
	 }

	 sql.setString(poll.getRegId() );
	 sql.setString(CommonUtil.getCurrentDate());

	 log.debug("[addPoll]" + sql.toString());
	 try{
		retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		log.error(e.getMessage());
		throw new DAOException(e.getMessage());
	 }

//	 Connection conn = null;
//	 DBConnectionManager pool = null;
//	 ResultSet rs = null;
//	 QueryStatement sql2 = new QueryStatement();
//	 StringBuffer sb2 = new StringBuffer();
//	 try{
//	 	pool = DBConnectionManager.getInstance();
//		conn = pool.getConnection();
//		conn.setAutoCommit( false );
//
//	    retVal = broker.executeUpdate(sql,conn);
//
//		 sb2.append(" select contents from internet_poll ");
//		 sb2.append(" where system_code = ? and poll_id = ? FOR UPDATE");
//		 sql2.setSql(sb2.toString());
//		 sql2.setString(poll.getSystemCode());
//		 sql2.setInteger(poll.getPollId());
//		 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//		 rs = broker.executeQuery(sql2,conn);
//     	if(rs.next()){
////     		log.debug("______ rs "+rs.getClob(1));
//     		CLOB clob = (CLOB)rs.getClob("contents");
//     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//     		Writer writer = clob.getCharacterOutputStream();
//     		Reader src = new CharArrayReader(poll.getContents().toCharArray());
//     		char[] buffer = new char[1024];
//     		int read = 0;
//     		while ( (read = src.read(buffer,0,1024)) != -1) {
//     			writer.write(buffer, 0, read);
//     		}
//     		//clob.close();
//     		src.close();
//     		writer.close();
//     	}
//		conn.commit();
//
//	 }catch(Exception e){
//	 	e.printStackTrace();
//		try {
//			if(conn != null) conn.rollback();
//		} catch(SQLException ignore) {
//			log.error(ignore.getMessage(), ignore);
//		}
//		throw new DAOException(e.getMessage());
//	 } finally {
//		try {
//			if(conn != null){
//				conn.setAutoCommit( true );
//				pool.freeConnection(conn); // 컨넥션 해제
//			}
//		} catch(Exception ignore) {
//			log.error(ignore.getMessage(),ignore);
//		}
//	}

	 return retVal;
	}

	/**
	 * 인터넷 투표를 수정한다.
	 * @param poll
	 * @return
	 * @throws DAOException
	 */
	public int editPoll(InternetPollDTO poll) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update internet_poll set start_date=?, end_date=?, status=?, subject = ?, contents = ?, example_cnt = ? ,");
		 sb.append(" example1 = ?, example2 = ?, example3 = ?, example4 = ?, example5 = ?,");
		 sb.append(" example6 = ?, example7 = ?, example8 = ?, example9 = ?, example10 = ?,");
		 sb.append(" mod_id = ? , mod_date = ?  ");
		 sb.append(" where system_code = ? and poll_id = ?");
		 sql.setSql(sb.toString());

		 sql.setString(poll.getStartDate());
		 sql.setString(poll.getEndDate());
		 sql.setString(poll.getStatus());
		 sql.setString(poll.getSubject());
		 sql.setString(poll.getContents());
		 sql.setInteger(poll.getExample().length);

		 int i = 0;
		 for(i=0 ; i < poll.getExample().length ; i++) {
		 	sql.setString(poll.getExample()[i]);
		 }

		 if(i < 10 ) {
		 	for(int j=i ; j < 10; j++ ){
		 		sql.setString("");
		 	}
		 }

		 sql.setString(poll.getRegId() );
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(poll.getSystemCode());
		 sql.setInteger(poll.getPollId());


		 log.debug("[editPoll]" + sql.toString());
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
//			 sb2.append(" select contents from internet_poll ");
//			 sb2.append(" where system_code = ? and poll_id = ? FOR UPDATE");
//			 sql2.setSql(sb2.toString());
//			 sql2.setString(poll.getSystemCode());
//			 sql2.setInteger(poll.getPollId());
//			 log.debug("[UseAdd_getBbsContents]" + sql2.toString());
//
//			 rs = broker.executeQuery(sql2,conn);
//	     	if(rs.next()){
////	     		log.debug("______ rs "+rs.getClob(1));
//	     		CLOB clob = (CLOB)rs.getClob("contents");
//	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("contents");
//	     		Writer writer = clob.getCharacterOutputStream();
//	     		Reader src = new CharArrayReader(poll.getContents().toCharArray());
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
	 * 인터넷 투표 최대값을 가져온다
	 * @param SystemCode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxPollId(String SystemCode) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(poll_id),0)+1 as max_id ");
	  sb.append(" from internet_poll ");
	  sb.append(" where system_code = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);

	 log.debug("[getMaxPollId]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
			maxId = rs.getInt("max_id");
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
	 * 투표에 참여 했는지 체크하기 위해 poll 갯수 리턴
	 * @param systemCode
	 * @param pollId
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public int chkPollJoin(String systemCode, int pollId, String userId) throws DAOException{
		 int  chkCnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(poll_id),0)as chkCnt ");
		  sb.append(" from internet_poll_join ");
		  sb.append(" where system_code = ? and poll_id = ? and user_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setInteger(pollId);
		 sql.setString(userId);

		 log.debug("[chkPollJoin]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	chkCnt = rs.getInt("chkCnt");
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

		 return chkCnt;
		}


	/**
	 * 인터넷 투표 정보를  가져온다.
	 * @param systemcode
	 * @param pollid
	 * @return
	 * @throws DAOException
	 */

	public InternetPollDTO getPollShow(String systemcode, int pollid, String userId) throws DAOException{
		InternetPollDTO pollDto 		= 	null;

		StringBuffer sb = new StringBuffer();
		sb.append(" select " +
					" poll_id, start_date, end_date, status" +
					", subject, contents, example_cnt, example1" +
					", example2,example3, example4, example5");
		sb.append(" , example6, example7, example8, example9, example10 ");
		sb.append(" , hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8, hit9, hit10, status ");
		sb.append(" from internet_poll ");
		sb.append(" where system_code = ? ");
		if(pollid == 0) {
			sb.append(" and start_date <= CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) and end_date >= CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		}else{
			sb.append(" and poll_id = ? ");
		}
		sb.append(" order by poll_id desc ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		if(pollid != 0) {
		sql.setInteger(pollid);
		}
		log.debug("[getPollShow]" + sql.toString());

		String[] exam 	=	new String[10];
		int[] hit 	=	new int[10];

		 ResultSet rs = null;
	     StringBuffer output = new StringBuffer();
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				pollDto 		= 	new InternetPollDTO();

			 	pollDto.setPollId(rs.getInt("poll_id"));
			 	pollDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	pollDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	pollDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	pollDto.setContents(StringUtil.nvl(rs.getString("contents")));
			 	pollDto.setExampleCnt(rs.getInt("example_cnt"));
			 	pollDto.setStatus(StringUtil.nvl(rs.getString("status")));

			 	//항목
			 	exam[0] = (StringUtil.nvl(rs.getString("example1")));
			 	exam[1] = (StringUtil.nvl(rs.getString("example2")));
			 	exam[2] = (StringUtil.nvl(rs.getString("example3")));
			 	exam[3] = (StringUtil.nvl(rs.getString("example4")));
			 	exam[4] = (StringUtil.nvl(rs.getString("example5")));
			 	exam[5] = (StringUtil.nvl(rs.getString("example6")));
			 	exam[6] = (StringUtil.nvl(rs.getString("example7")));
			 	exam[7] = (StringUtil.nvl(rs.getString("example8")));
			 	exam[8] = (StringUtil.nvl(rs.getString("example9")));
			 	exam[9] = (StringUtil.nvl(rs.getString("example10")));
			 	pollDto.setExample(exam);

			 	//항목별 응답
			 	hit[0]	= rs.getInt("hit1");
			 	hit[1]	= rs.getInt("hit2");
			 	hit[2]	= rs.getInt("hit3");
			 	hit[3]	= rs.getInt("hit4");
			 	hit[4]	= rs.getInt("hit5");
			 	hit[5]	= rs.getInt("hit6");
			 	hit[6]	= rs.getInt("hit7");
			 	hit[7]	= rs.getInt("hit8");
			 	hit[8]	= rs.getInt("hit9");
			 	hit[9]	= rs.getInt("hit10");
			 	pollDto.setHit(hit);

			 	//응답수
			 	int hit_count = hit[0]+hit[1]+hit[2]+hit[3]+hit[4]+hit[5]+hit[6]+hit[7]+hit[8]
				+hit[9];
			 	pollDto.setHitCnt(hit_count);

		        pollDto.setChkCnt(chkPollJoin(systemcode,rs.getInt("poll_id"),userId));

				rs.close();
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
		return pollDto;
	}


	/**
	 * 인터넷 투표를 삭제한다.(트랜젝션 처리)
	 * @param systemcode
	 * @param pollid
	 * @param conn
	 * @return
	 * @throws DAOException
	 */
	public int delPoll(String systemcode, int pollid, Connection conn) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from internet_poll ");
	 sb.append(" where system_code = ? and poll_id = ?");
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setInteger(pollid);

	 log.debug("[delPoll]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql,conn);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 진행중인 인터넷 투표 정보를  가져온다.
	 * @param systemcode
	 * @param pollid
	 * @return
	 * @throws DAOException
	 */
	public InternetPollDTO getPollStartShow(String systemcode) throws DAOException{
		InternetPollDTO pollDto 		= 	null;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select poll_id, subject, contents, example_cnt, status,  ");
		 sb.append(" example1, example2, example3, example4, example5,");
		 sb.append(" example6, example7, example8, example9, example10,");
		 sb.append(" hit1, hit2, hit3, hit4, hit5, hit6, hit7, hit8, hit9, hit10 ");
	 	 sb.append(" from internet_poll  ");
		 sb.append(" where system_code = ?  and status ='Y'");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);

		log.debug("[getPollStartShow]" + sql.toString());

		String[] exam 	=	new String[10];
		ResultSet rs = null;
		StringBuffer output = new StringBuffer();
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				pollDto 		= 	new InternetPollDTO();

			 	pollDto.setPollId(rs.getInt("poll_id"));
			 	pollDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	pollDto.setContents(StringUtil.nvl(rs.getString("contents")));
			 	pollDto.setExampleCnt(rs.getInt("example_cnt"));
			 	pollDto.setStatus(StringUtil.nvl(rs.getString("status")));

			 	exam[0] = (StringUtil.nvl(rs.getString("example1")));
			 	exam[1] = (StringUtil.nvl(rs.getString("example2")));
			 	exam[2] = (StringUtil.nvl(rs.getString("example3")));
			 	exam[3] = (StringUtil.nvl(rs.getString("example4")));
			 	exam[4] = (StringUtil.nvl(rs.getString("example5")));
			 	exam[5] = (StringUtil.nvl(rs.getString("example6")));
			 	exam[6] = (StringUtil.nvl(rs.getString("example7")));
			 	exam[7] = (StringUtil.nvl(rs.getString("example8")));
			 	exam[8] = (StringUtil.nvl(rs.getString("example9")));
			 	exam[9] = (StringUtil.nvl(rs.getString("example10")));
			 	pollDto.setExample(exam);

			 	//응답수
			 	int hit_count = rs.getInt("hit1")+rs.getInt("hit2")+rs.getInt("hit3")+rs.getInt("hit4")
				+rs.getInt("hit5")+rs.getInt("hit6")+rs.getInt("hit7")+rs.getInt("hit8")
				+rs.getInt("hit9")+rs.getInt("hit10");
			 	pollDto.setHitCnt(hit_count);

				rs.close();
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
		return pollDto;
	}

	/**
	 * 진행중인 인터넷 투표수를  가져온다.
	 * @param systemcode
	 * @param pollid
	 * @return
	 * @throws DAOException
	 */
	public int getPollStartCount(String systemcode) throws DAOException{

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(*) as cnt ");
	 	 sb.append(" from internet_poll  ");
		 sb.append(" where system_code = ?  and start_date <= ? and end_date >= ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(CommonUtil.getCurrentDate());

		log.debug("[getPollStartCount]" + sql.toString());

		int count = 0;
		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
			 	count = rs.getInt("cnt");
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
		return count;
	}

	/**
	 * 진행중인 투표의 투표 아이디를 가져온다.
	 * @param systemcode
	 * @return
	 * @throws DAOException
	 */
	public int getUsingPollId(String systemcode) throws DAOException{

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select poll_id ");
	 	 sb.append(" from internet_poll  ");
		 sb.append(" where system_code = ?  and start_date <= ? and end_date >= ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(CommonUtil.getCurrentDate());

		log.debug("[getPollStartCount]" + sql.toString());

		int retVal = 0;
		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				retVal = rs.getInt("poll_id");
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
		return retVal;
	}

	/**
	 * 인터넷 투표 상태를 수정한다.
	 * @param systemcode
	 * @param pollid
	 * @param status
	 * @return
	 * @throws DAOException
	 */
	public int statEditPoll(String systemcode, int pollid, String status) throws DAOException{
		 boolean retVal = false;
		 int retVal2 = 0;

		 ISqlStatement[] sqlArray = new ISqlStatement[2];
		 QueryStatement sql = new QueryStatement();
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 StringBuffer sb2 = new StringBuffer();

 		 int scount = 0;
 	     int old_poll_id = 0;

 		//실시로 변경할경우
 		if(status.equals("Y")){
 			InternetPollDTO pollDto = new InternetPollDTO();
 			scount  = getPollStartCount(systemcode);

 		 	//실시중인 투표가 있을경우
 			if (scount > 0) {
 				pollDto = getPollStartShow(systemcode);
 				old_poll_id = pollDto.getPollId();

 				if(!Integer.toString(pollDto.getPollId()).equals(null) && old_poll_id != pollid){
 					//응답자 있을경우
 					if(pollDto.getHitCnt() > 0) {
 						 sb.append(" update internet_poll set status = 'E',");
 						 sb.append(" end_date = ? ");
 						 sb.append(" where system_code = ?  and poll_id = ?");
 						 sql.setSql(sb.toString());
 						 sql.setString(CommonUtil.getCurrentDate());
 						 sql.setString(systemcode);
 						 sql.setInteger(pollDto.getPollId());
 					}
 					else {
 						 sb.append(" update internet_poll set status = 'N',");
 						 sb.append(" start_date = '', end_date = '' ");
 						 sb.append(" where system_code = ?  and poll_id = ?");
 						 sql.setSql(sb.toString());
 						 sql.setString(systemcode);
 						 sql.setInteger(pollDto.getPollId());
 					}
 				}//end if
 			}

 			sb2.append(" update internet_poll set status = 'Y',");
 			sb2.append(" start_date = ? ");
 			sb2.append(" where system_code = ?  and poll_id = ?");
 			sql2.setSql(sb2.toString());
 			sql2.setString(CommonUtil.getCurrentDate());
 			sql2.setString(systemcode);
 			sql2.setInteger(pollid);

 		}else if(status.equals("N")){
 			sb2.append(" update internet_poll set status = 'N',");
 			sb2.append(" start_date = '' ");
 			sb2.append(" where system_code = ?  and poll_id = ?");
 			sql2.setSql(sb2.toString());
 			sql2.setString(systemcode);
 			sql2.setInteger(pollid);

 		}else{
 			sb2.append(" update internet_poll set status = 'E',");
 			sb2.append(" end_date = ? ");
 			sb2.append(" where system_code = ?  and poll_id = ?");
 			sql2.setSql(sb2.toString());
 			sql2.setString(CommonUtil.getCurrentDate());
 			sql2.setString(systemcode);
 			sql2.setInteger(pollid);
 		}

 		try{

 			if(status.equals("Y") && scount > 0 && old_poll_id != pollid){
 				 sqlArray[0] = sql;
 				 sqlArray[1] = sql2;

 				 log.debug("[oldStatEditPoll]" + sql.toString());
 				 log.debug("[statEditPoll]" + sql2.toString());

 				retVal = broker.executeUpdate(sqlArray);

 				if(retVal)
 					retVal2 = 1;
 				else
 					retVal2 = 0;

 			}else{
 				log.debug("[statEditPoll]" + sql2.toString());
 				retVal2 = broker.executeUpdate(sql2);
 			}
 		}catch(Exception e){
 			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal2;
	}

	/**
	 * 인터넷 투표결과를 저장한다. (사용자)
	 * @param poll
	 * @return
	 * @throws DAOException
	 */
	public int userAddPoll(String systemcode, int pollid,String userId, int hitno) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();

		 sb.append(" update internet_poll set hit1=hit1+? ,hit2=hit2+? ,hit3=hit3+? ,");
		 sb.append(" hit4 = hit4+? ,hit5=hit5+? ,hit6=hit6+? ,hit7=hit7+? ,hit8=hit8+?, ");
		 sb.append(" hit9=hit9+? ,hit10=hit10+? ");
		 sb.append(" where system_code = ? and poll_id = ?");
		 sql.setSql(sb.toString());

		 for(int i=1 ; i <= 10 ; i++) {
		 	if(hitno == i) {
		 		sql.setInteger(1);
		 	}else{
		 		sql.setInteger(0);
		 	}
		 }

		 sql.setString(systemcode);
		 sql.setInteger(pollid);

		 log.debug("[userAddPoll]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
//			 투표 성공시 투표 참여 체크
			 //sb2.append("insert into internet_poll_join(system_code, poll_id, user_id,hit_no, reg_date)");
			 sb2.append("insert into internet_poll_join(system_code, poll_id, user_id, reg_date)");
			 sb2.append(" values(?,?,?,?)");
			 sql2.setSql(sb2.toString());
			 sql2.setString(systemcode);
			 sql2.setInteger(pollid);
			 sql2.setString(userId);
		   //sql2.setInteger(hitno);
			 sql2.setString(CommonUtil.getCurrentDate());
			 log.debug("[userAddPollJoin]" + sql2.toString());
			 retVal = broker.executeUpdate(sql2);

		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}


	/**
	 * 한줄답변 최대값을 가져온다
	 * @param SystemCode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String SystemCode) throws DAOException{
	 int  seqNo = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(seq_no),0)+1 as seq_no ");
	  sb.append(" from internet_poll_comment ");
	  sb.append(" where system_code = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);

	 log.debug("[getMaxSeqNo]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	seqNo = rs.getInt("seq_no");
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

	 return seqNo;
	}

	/**
	 * 한줄답변을 저장한다.
	 * @param poll
	 * @return
	 * @throws DAOException
	 */
	public int addPollComment(InternetPollCommentDTO commentDto) throws DAOException{
		 int retVal = 0;
		 int seqNo = getMaxSeqNo(commentDto.getSystemCode());

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" insert into internet_poll_comment (system_code, poll_id, seq_no ,");
		 sb.append(" comment_contents, emoticon_num,reg_id, reg_name, reg_email, reg_passwd, reg_date) ");
		 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
		 sql.setSql(sb.toString());

		 sql.setString(commentDto.getSystemCode());
		 sql.setInteger(commentDto.getPollId());
		 sql.setInteger(seqNo);
		 sql.setString(commentDto.getContents());
		 sql.setString(commentDto.getEmoticonNum());
		 sql.setString(commentDto.getRegId());
		 sql.setString(commentDto.getRegName());
		 sql.setString(commentDto.getRegEmail());
		 sql.setString(commentDto.getRegPasswd());
		 sql.setString(CommonUtil.getCurrentDate());

		 log.debug("[addPollComment]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}


	/**
	 * 한줄답변 리스트 가져오기
	 * @param systemcode
	 * @param pollid
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getPollCommentList(String systemcode, int pollid) throws DAOException{

		 ArrayList commentList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" select ipc.seq_no, ipc.comment_contents, ipc.emoticon_num, ipc.reg_id, ipc.reg_email ");
		 sb.append(", (SELECT user_name FROM users WHERE system_code = ipc.system_code AND user_id = ipc.reg_id) AS reg_name ");
		 sb.append(", ipc.reg_passwd, ipc.reg_date, ipc.mod_id, ipc.mod_date ");
		 sb.append(" from internet_poll_comment ipc ");
		 sb.append(" where ipc.system_code = ? and ipc.poll_id = ? ");
		 sb.append(" order by ipc.seq_no desc ");

		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setInteger(pollid);

		 log.debug("[getPollCommentList]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 InternetPollCommentDTO commentDto =	null;
			 commentList = new ArrayList();

			 while(rs.next()){
			 	commentDto = new InternetPollCommentDTO();
			 	commentDto.setSeqNo(rs.getInt("seq_no"));
			 	commentDto.setContents(StringUtil.nvl(rs.getString("comment_contents")));
			 	commentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num")));
			 	commentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 	commentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
			 	commentDto.setRegEmail(StringUtil.nvl(rs.getString("reg_email")));
			 	commentDto.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));
			 	commentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 	commentDto.setModId(StringUtil.nvl(rs.getString("mod_id")));
			 	commentDto.setModDate(StringUtil.nvl(rs.getString("mod_date")));
			 	commentList.add(commentDto);
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

		 return commentList;
	}

	/**
	 * 한줄답변 정보를 가져온다.
	 * @param systemcode
	 * @param pollid
	 * @param seqno
	 * @return
	 * @throws DAOException
	 */
	public InternetPollCommentDTO getPollCommentShow(String systemcode, int pollid, int seqno) throws DAOException{
		InternetPollCommentDTO commentDto 		= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select comment_contents, emoticon_num, reg_id, reg_name, reg_email, ");
		sb.append(" reg_passwd, reg_date, mod_id, mod_date ");
		sb.append(" from internet_poll_comment ");
		sb.append(" where system_code = ? and poll_id = ? and seq_no  = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(pollid);
		sql.setInteger(seqno);

		log.debug("[getPollCommentShow]" + sql.toString());

		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
			 	commentDto = new InternetPollCommentDTO();
			 	commentDto.setContents(StringUtil.nvl(rs.getString("comment_contents")));
			 	commentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num")));
			 	commentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
			 	commentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
			 	commentDto.setRegEmail(StringUtil.nvl(rs.getString("reg_email")));
			 	commentDto.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));
			 	commentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
			 	commentDto.setModId(StringUtil.nvl(rs.getString("mod_id")));
			 	commentDto.setModDate(StringUtil.nvl(rs.getString("mod_date")));
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
		return commentDto;
	}


	/**
	 * 한줄답변을 삭제한다.
	 * @param systemcode
	 * @param pollid
	 * @param seqno
	 * @return
	 * @throws DAOException
	 */
	public int delPollComment(String systemcode, int pollid, int seqno) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from internet_poll_comment ");
	 sb.append(" where system_code = ? and poll_id = ? and seq_no = ? ");
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setInteger(pollid);
	 sql.setInteger(seqno);

	 log.debug("[delPollComment]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 투표 정보 삭제시 해당 투표와 관련된 한줄 답변을 삭제 한다.(트랜젝션 처리함)
	 * @param systemcode
	 * @param pollid
	 * @param conn
	 * @return
	 * @throws DAOException
	 */
	public int delPollComment(String systemcode, int pollid,  Connection conn) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from internet_poll_comment ");
		 sb.append(" where system_code = ? and poll_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setInteger(pollid);


		 log.debug("[delPollComment]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql, conn);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 투표 정보 삭제시 해당 투표에 참여한 정보를 삭제 한다.(트랜젝션 처리함)
	 * @param systemcode
	 * @param pollid
	 * @param conn
	 * @return
	 * @throws DAOException
	 */
	public int delPollJoin(String systemcode, int pollid,  Connection conn) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from internet_poll_join ");
		 sb.append(" where system_code = ? and poll_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setInteger(pollid);


		 log.debug("[delPollJoin]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql, conn);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}



}
