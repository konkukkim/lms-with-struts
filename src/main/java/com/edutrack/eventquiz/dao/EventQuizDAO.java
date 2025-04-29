/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.eventquiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.eventquiz.dto.EventQuizAnswerDTO;
import com.edutrack.eventquiz.dto.EventQuizInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
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
public class EventQuizDAO extends AbstractDAO {

	/**
	 * 이벤트 퀴즈 리스트 가져오기
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getEventQuizList(String Systemcode) throws DAOException{

		 ArrayList eventList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" select quiz_id, start_date, end_date, status, subject, contents, ");
		 sb.append(" ( select count(user_id) from event_quiz_answer b ");
		 sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id) as quiz_cnt ");
		 sb.append(" from event_quiz_info a ");
		 sb.append(" where a.system_code = ? ");
		 sb.append(" order by a.quiz_id desc ");

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 EventQuizInfoDTO eventquizDto =	null;
			 eventList = new ArrayList();

			 while(rs.next()){
			 	eventquizDto = new EventQuizInfoDTO();
			 	eventquizDto.setQuizId(rs.getInt("quiz_id"));
			 	eventquizDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			 	eventquizDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			 	eventquizDto.setStatus(StringUtil.nvl(rs.getString("status")));
			 	eventquizDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	eventquizDto.setContents(StringUtil.nvl(rs.getString("contents")));
			 	eventquizDto.setQuizCnt(rs.getInt("quiz_cnt"));
			 	eventList.add(eventquizDto);
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

		 return eventList;
	}


	/**
	 * 이벤트 퀴즈 정보를 등록한다.
	 * @param poll
	 * @return
	 * @throws DAOException
	 */
	public int addEventQuiz(EventQuizInfoDTO quizinfo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" insert into event_quiz_info ");
	 sb.append(" (system_code, quiz_id, status, subject, contents, ");
	 sb.append(" example_cnt, example1, example2,example3, example4, example5,");
	 sb.append(" quiz_answer, reg_id, reg_date ) ");
	 sb.append(" values( ?, ?, 'N', ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ) ");
	 sql.setSql(sb.toString());

	 sql.setString(quizinfo.getSystemCode());
	 sql.setInteger(quizinfo.getQuizId());
	 sql.setString(quizinfo.getSubject());
	 sql.setString(quizinfo.getContents());
	 sql.setInteger(quizinfo.getExample().length);

	 int i = 0;
	 for(i=0 ; i < quizinfo.getExample().length ; i++) {
	 	sql.setString(quizinfo.getExample()[i]);
	 }

	 if(i < 5 ) {
	 	for(int j=i ; j < 5; j++ ){
	 		sql.setString("");
	 	}
	 }

	 sql.setInteger(quizinfo.getQuizAnswer());
	 sql.setString(quizinfo.getRegId() );

	 log.debug("[addEventQuiz]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 이벤트 퀴즈를 수정한다.
	 * @param poll
	 * @return
	 * @throws DAOException
	 */
	public int editEventQuiz(EventQuizInfoDTO quizinfo) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update event_quiz_info set subject = ?, contents = ?, example_cnt = ? ,");
		 sb.append(" example1 = ?, example2 = ?, example3 = ?, example4 = ?, example5 = ?,");
		 sb.append(" quiz_answer =? , mod_id = ?,  ");
		 sb.append(" mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and quiz_id = ?");
		 sql.setSql(sb.toString());

		 sql.setString(quizinfo.getSubject());
		 sql.setString(quizinfo.getContents());
		 sql.setInteger(quizinfo.getExample().length);

		 int i = 0;
		 for(i=0 ; i < quizinfo.getExample().length ; i++) {
		 	sql.setString(quizinfo.getExample()[i]);
		 }

		 if(i < 5 ) {
		 	for(int j=i ; j < 5; j++ ){
		 		sql.setString("");
		 	}
		 }
		 sql.setInteger(quizinfo.getQuizAnswer());
		 sql.setString(quizinfo.getRegId());
		 sql.setString(quizinfo.getSystemCode());
		 sql.setInteger(quizinfo.getQuizId());


		 log.debug("[editEventQuiz]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 이벤트 퀴즈 최대값을 가져온다
	 * @param SystemCode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxQuizId(String SystemCode) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(quiz_id),0)+1 as max_id ");
	  sb.append(" from event_quiz_info ");
	  sb.append(" where system_code = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);

	 log.debug("[getMaxQuizId]" + sql.toString());

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
	 * 이벤트 퀴즈 정보를  가져온다.
	 * @param systemcode
	 * @param quizid
	 * @return
	 * @throws DAOException
	 */
	public EventQuizInfoDTO getEventQuizShow(String systemcode, int quizid) throws DAOException{
		EventQuizInfoDTO eventquizDto 		= 	null;

		StringBuffer sb = new StringBuffer();

		sb.append(" select quiz_id, subject, contents, example_cnt, status, ");
		sb.append(" example1, example2,example3, example4, example5, quiz_answer, ");
		sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	    sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id) as quiz_cnt ");
		sb.append(" from event_quiz_info a ");
		sb.append(" where a.system_code = ? ");
		if(quizid == 0) {
			sb.append(" and a.status = 'Y' ");
		}else{
			sb.append(" and a.quiz_id = ? ");
		}
		sb.append(" order by a.quiz_id desc ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		if(quizid != 0) {
		sql.setInteger(quizid);
		}
		log.debug("[getEventQuizShow]" + sql.toString());

		String[] exam 	=	new String[5];

		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				eventquizDto 		= 	new EventQuizInfoDTO();

			 	eventquizDto.setQuizId(rs.getInt("quiz_id"));
			 	eventquizDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	eventquizDto.setContents(StringUtil.nvl(rs.getString("contents")));
			 	eventquizDto.setExampleCnt(rs.getInt("example_cnt"));
			 	eventquizDto.setStatus(StringUtil.nvl(rs.getString("status")));

			 	//항목
			 	exam[0] = (StringUtil.nvl(rs.getString("example1")));
			 	exam[1] = (StringUtil.nvl(rs.getString("example2")));
			 	exam[2] = (StringUtil.nvl(rs.getString("example3")));
			 	exam[3] = (StringUtil.nvl(rs.getString("example4")));
			 	exam[4] = (StringUtil.nvl(rs.getString("example5")));
			 	eventquizDto.setExample(exam);

			 	eventquizDto.setQuizAnswer(rs.getInt("quiz_answer"));
			 	eventquizDto.setQuizCnt(rs.getInt("quiz_cnt"));
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
		return eventquizDto;
	}

	/**
	 * 이벤트 퀴즈를 삭제한다.
	 * @param systemcode
	 * @param quizid
	 * @return
	 * @throws DAOException
	 */
	public int delEventQuiz(String systemcode, int quizid) throws DAOException{
	 int retVal = 0;
	 boolean retVal2 = false;
	 ISqlStatement[] sqlArray = new ISqlStatement[2];

	 //이벤트 퀴즈 정보 삭제
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from event_quiz_info ");
	 sb.append(" where system_code = ? and quiz_id = ?");
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setInteger(quizid);

	 //이벤트 참여자 정보 삭제
	 QueryStatement sql2 = new QueryStatement();
	 StringBuffer sb2 = new StringBuffer();
	 sb2.append(" delete from event_quiz_answer ");
	 sb2.append(" where system_code = ? and quiz_id = ?");
	 sql2.setSql(sb2.toString());
	 sql2.setString(systemcode);
	 sql2.setInteger(quizid);

	 log.debug("[delEventQuizInfo]" + sql.toString());
	 log.debug("[delEventQuizAnswer]" + sql2.toString());

	 sqlArray[0] = sql;
	 sqlArray[1] = sql2;

	 try{
		 retVal2 = broker.executeUpdate(sqlArray);

		 if(retVal2)
		 	retVal = 1;
		 else
		 	retVal = 0;
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 진행중인 이벤트 퀴즈 정보를  가져온다.
	 * @param systemcode
	 * @param quizid
	 * @return
	 * @throws DAOException
	 */
	public EventQuizInfoDTO getEventQuizStartShow(String systemcode) throws DAOException{
		EventQuizInfoDTO eventquizDto 		= 	null;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select quiz_id, subject, contents, example_cnt, status,  ");
		 sb.append(" example1, example2, example3, example4, example5, ");
		 sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	     sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id) as quiz_cnt ");
 	 	 sb.append(" from event_quiz_info a  ");
		 sb.append(" where a.system_code = ?  and a.status ='Y'");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);

		log.debug("[getEventQuizStartShow]" + sql.toString());

		String[] exam 	=	new String[10];
		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				eventquizDto 		= 	new EventQuizInfoDTO();

			 	eventquizDto.setQuizId(rs.getInt("quiz_id"));
			 	eventquizDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	eventquizDto.setContents(StringUtil.nvl(rs.getString("contents")));
			 	eventquizDto.setExampleCnt(rs.getInt("example_cnt"));
			 	eventquizDto.setStatus(StringUtil.nvl(rs.getString("status")));

			 	exam[0] = (StringUtil.nvl(rs.getString("example1")));
			 	exam[1] = (StringUtil.nvl(rs.getString("example2")));
			 	exam[2] = (StringUtil.nvl(rs.getString("example3")));
			 	exam[3] = (StringUtil.nvl(rs.getString("example4")));
			 	exam[4] = (StringUtil.nvl(rs.getString("example5")));
			 	eventquizDto.setExample(exam);
			 	eventquizDto.setQuizCnt(rs.getInt("quiz_cnt"));

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
		return eventquizDto;
	}

	/**
	 * 진행중인 이벤트 퀴즈수를  가져온다.
	 * @param systemcode
	 * @param quizid
	 * @return
	 * @throws DAOException
	 */
	public int getEventQuizStartCount(String systemcode) throws DAOException{

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(*) as cnt ");
	 	 sb.append(" from event_quiz_info  ");
		 sb.append(" where system_code = ?  and status ='Y'");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);

		log.debug("[getEventQuizStartCount]" + sql.toString());

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
	 * 이벤트 퀴즈 상태를 수정한다.
	 * @param systemcode
	 * @param quizid
	 * @param status
	 * @pram quiz
	 * @return
	 * @throws DAOException
	 */
	public int statEditEventQuiz(String systemcode, int quizid, String status) throws DAOException{
		 boolean retVal = false;
		 int retVal2 = 0;

		 ISqlStatement[] sqlArray = new ISqlStatement[2];
		 QueryStatement sql = new QueryStatement();
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 StringBuffer sb2 = new StringBuffer();

		 int scount = 0;
		 int old_quiz_id = 0;

		//실시로 변경할경우
		if(status.equals("Y")){
			EventQuizInfoDTO eventquizDto = new EventQuizInfoDTO();
			scount  = getEventQuizStartCount(systemcode);

		 	//실시중인 투표가 있을경우
			if (scount > 0) {
				eventquizDto = getEventQuizStartShow(systemcode);
				old_quiz_id = eventquizDto.getQuizId();

				if(!Integer.toString(old_quiz_id).equals(null) && old_quiz_id != quizid){
					//응답자 있을경우

					if(eventquizDto.getQuizCnt() > 0) {
						 sb.append(" update event_quiz_info set status = 'E',");
						 sb.append(" end_date = ? ");
						 sb.append(" where system_code = ?  and quiz_id = ?");
						 sql.setSql(sb.toString());
						 sql.setString(CommonUtil.getCurrentDate());
						 sql.setString(systemcode);
						 sql.setInteger(old_quiz_id);
					}
					else {

						 sb.append(" update event_quiz_info set status = 'N',");
						 sb.append(" start_date = '', end_date = '' ");
						 sb.append(" where system_code = ?  and quiz_id = ?");
						 sql.setSql(sb.toString());
						 sql.setString(systemcode);
						 sql.setInteger(old_quiz_id);

					}

				}//end if
			}

			sb2.append(" update event_quiz_info set status = 'Y',");
			sb2.append(" start_date = ? ");
			sb2.append(" where system_code = ?  and quiz_id = ?");
			sql2.setSql(sb2.toString());
			sql2.setString(CommonUtil.getCurrentDate());
			sql2.setString(systemcode);
			sql2.setInteger(quizid);

		}else if(status.equals("N")){
			sb2.append(" update event_quiz_info set status = 'N',");
			sb2.append(" start_date = '' ");
			sb2.append(" where system_code = ?  and quiz_id = ?");
			sql2.setSql(sb2.toString());
			sql2.setString(systemcode);
			sql2.setInteger(quizid);

		}else{
			sb2.append(" update event_quiz_info set status = 'E',");
			sb2.append(" end_date = ? ");
			sb2.append(" where system_code = ?  and quiz_id = ?");
			sql2.setSql(sb2.toString());
			sql2.setString(CommonUtil.getCurrentDate());
			sql2.setString(systemcode);
			sql2.setInteger(quizid);
		}

		try{

			if(status.equals("Y") && scount > 0 && old_quiz_id != quizid){
				 sqlArray[0] = sql;
				 sqlArray[1] = sql2;

				 log.debug("[oldStatEditEventQuiz]" + sql.toString());
				 log.debug("[statEditEventQuiz]" + sql2.toString());

				retVal = broker.executeUpdate(sqlArray);

				if(retVal)
					retVal2 = 1;
				else
					retVal2 = 0;

			}else{
				log.debug("[statEditEventQuiz]" + sql2.toString());
				retVal2 = broker.executeUpdate(sql2);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal2;
	}

	/**
	 * 이벤트 참여자 리스트 가져오기
	 * @param curpage
	 * @param systemcode
	 * @param quizid
	 * @param etype (A:전체, B:당첨자, C:정답자)
	 * @param answer
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getEventQuizAnswerPagingList(int curpage, String systemcode, int quizid, String etype, int answer) throws DAOException{
		ListDTO retVal 		= 	null;
		try{
			ListStatement sql = new ListStatement();

			sql.setTotalCol("a.user_id");
			sql.setCutCol(" concat(cast(quiz_id AS CHAR), a.user_id) ");
			sql.setAlias("quiz_id, a.user_id, answer_no, prize_yn, b.user_name, a.reg_id, a.reg_date ");
			sql.setSelect("quiz_id, a.user_id, answer_no, prize_yn, b.user_name, a.reg_id, a.reg_date ");
			sql.setFrom("event_quiz_answer a, users b");

			StringBuffer sb = new StringBuffer();
			sb.append("a.system_code=? and a.quiz_id=? and a.system_code=b.system_code and a.user_id=b.user_id ");

			if (etype.equals("B")) {
				sb.append(" and prize_yn = 'Y' ");
			}else if(etype.equals("C")) {
				sb.append(" and answer_no = ? ");
			}
			sql.setWhere(sb.toString());
			sql.setString(systemcode);
			sql.setInteger(quizid);

			if(etype.equals("C")) {
				sql.setInteger(answer);
			}
			sql.setOrderby(" b.user_name desc");

			log.debug("[getEventQuizAnswerPagingList]" + sql.toString());

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
	 *  이벤트 당첨자 상태를 변경합니다.
	 * @param systemcode
	 * @param chk
	 * @param quizid
	 * @param prizeyn
	 * @return
	 * @throws DAOException
	 */
	public boolean editEventQuizAnswerFlag(String systemcode, String[] chk, int quizid, String prizeyn) throws DAOException{
		boolean  retVal = false;
		ISqlStatement[] sqlArray = new ISqlStatement[chk.length];
		QueryStatement sql = null;
		StringBuffer sb = null;

	    EventQuizDAO	eventquizDao	=	new EventQuizDAO();
	    int del_cnt  = 0;

		for(int i=0; i < chk.length; i++){
			sql = new QueryStatement();
			sb = new StringBuffer();

			sb.append(" update event_quiz_answer set prize_yn = ? ");
			sb.append(" where system_code = ? and quiz_id = ? and user_id = ? ");
			sql.setSql(sb.toString());
			sql.setString(prizeyn);
			sql.setString(systemcode);
			sql.setInteger(quizid);
			sql.setString(chk[i]); //user_id

			log.debug("[editEventQuizAnswerFlag]" + sql.toString());

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
	 * 이벤트 참여 결과를 저장한다.
	 * @param eventquiz
	 * @return
	 * @throws DAOException
	 */
	public int addEventQuizAnswer(EventQuizAnswerDTO eventquiz) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" insert into event_quiz_answer (system_code, quiz_id, user_id ,");
		 sb.append(" answer_no, prize_yn, reg_id, reg_date) ");
		 sb.append(" values( ?, ?, ?, ?, 'N', ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");
		 sql.setSql(sb.toString());

		 sql.setString(eventquiz.getSystemCode());
		 sql.setInteger(eventquiz.getQuizId());
		 sql.setString(eventquiz.getUserId());
		 sql.setInteger(eventquiz.getAnswerNo());
		 sql.setString(eventquiz.getRegId());

		 log.debug("[addEventQuizComment]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 이벤트 참여 건수를 가져온다
	 * @param systemcode
	 * @param quizid
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int getCountEventQuizAnswer(String systemcode, int quizid, String userid) throws DAOException{
	 int  count = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select count(user_id) as cnt ");
	 sb.append(" from event_quiz_answer ");
	 sb.append(" where system_code = ? and quiz_id = ? and user_id = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setInteger(quizid);
	 sql.setString(userid);

	 log.debug("[getCountEventQuizAnswer]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	count = rs.getInt("cnt");
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

	 return count;
	}

	/**
	 * 이벤트 퀴즈 정보와 참여결과를 가져온다.
	 * @param systemcode
	 * @param quizid
	 * @return
	 * @throws DAOException
	 */
	public EventQuizInfoDTO getEventQuizResultShow(String systemcode, int quizid) throws DAOException{
		EventQuizInfoDTO eventquizDto 		= 	null;

		StringBuffer sb = new StringBuffer();

		sb.append(" select quiz_id, subject, contents, example_cnt, status, ");
		sb.append(" example1, example2,example3, example4, example5, quiz_answer, ");
		sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	    sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id and b.answer_no =1) as  answer_cnt1, ");
		sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	    sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id and b.answer_no =2) as  answer_cnt2, ");
		sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	    sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id and b.answer_no =3) as  answer_cnt3, ");
		sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	    sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id and b.answer_no =4) as  answer_cnt4, ");
		sb.append(" ( select count(user_id) from event_quiz_answer b ");
 	    sb.append(" where a.system_code = b.system_code and a.quiz_id =b.quiz_id and b.answer_no =5) as  answer_cnt5 ");
 	    sb.append(" from event_quiz_info a ");
		sb.append(" where a.system_code = ? and a.quiz_id = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(quizid);

		log.debug("[getEventQuizResultShow]" + sql.toString());

		String[] exam 	=	new String[5];
		int[] answer	=	new int[5];

		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				eventquizDto 		= 	new EventQuizInfoDTO();

			 	eventquizDto.setQuizId(rs.getInt("quiz_id"));
			 	eventquizDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			 	eventquizDto.setContents(StringUtil.nvl(rs.getString("contents")));
			 	eventquizDto.setExampleCnt(rs.getInt("example_cnt"));
			 	eventquizDto.setStatus(StringUtil.nvl(rs.getString("status")));
			 	eventquizDto.setQuizAnswer(rs.getInt("quiz_answer"));

			 	//항목
			 	exam[0] = (StringUtil.nvl(rs.getString("example1")));
			 	exam[1] = (StringUtil.nvl(rs.getString("example2")));
			 	exam[2] = (StringUtil.nvl(rs.getString("example3")));
			 	exam[3] = (StringUtil.nvl(rs.getString("example4")));
			 	exam[4] = (StringUtil.nvl(rs.getString("example5")));
			 	eventquizDto.setExample(exam);

			 	answer[0] = rs.getInt("answer_cnt1");
			 	answer[1] = rs.getInt("answer_cnt2");
			 	answer[2] = rs.getInt("answer_cnt3");
			 	answer[3] = rs.getInt("answer_cnt4");
			 	answer[4] = rs.getInt("answer_cnt5");
			 	eventquizDto.setAnswerCnt(answer);
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
		return eventquizDto;
	}


}
