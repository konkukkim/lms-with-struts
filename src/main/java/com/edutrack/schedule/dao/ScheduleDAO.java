/*
 * Created on 2004. 10. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.schedule.dao;

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
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.schedule.dto.ScheduleDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScheduleDAO extends AbstractDAO {


	/**
	 * 개인별 등록일정을 가져온다.
	 * 2 : 학습일정, 99 : 학사일정, 4 : 학습관일정
	 * 위의 세 가지 경우일 때는 모든 사용자들의 일정관리 화면에서 보여진다.
	 *  
	 * @param systemCode
	 * @param userId
	 * @param stYear
	 * @param stMon
	 * @param pVMode : 1이면 관리자 화면
	 * @return
	 * @throws DAOException
	 */
	public ScheduleDTO scheduleView(String systemCode, String userId, String stYear, String stMon, String pVMode) throws DAOException{
		log.debug("start view DAO555555555555555");
		ScheduleDTO scheduleDto = null;
		
		StringBuffer sb     = new StringBuffer();
		StringBuffer sb2    = new StringBuffer();
		StringBuffer sb3    = new StringBuffer();
		QueryStatement sql  = new QueryStatement();
		QueryStatement sql2  = new QueryStatement();
		QueryStatement sql3 = new QueryStatement();
		RowSet rs 		= null;
		ResultSet rs2 	= null;
		
		int Tcnt = 0;
		String[][] Tschid 	 = new String[31][20];
		String[][] Tschtype  = new String[31][20];
		String[][] Tcontents = new String[31][20];
		String[] CCNT 	= new String[31];
		
		int		id_con_cnt	=	0;
		int		type2_cnt	=	0;
		int 	con_cnt		=	0;
		String	stDay 		=	"";
		String	stDate 		=	"";
		
		if(stMon.length() == 1 ){
			stMon = "0"+stMon;
		}

		//컨텐츠 수 구하기 쿼리....
		sb2.append(" SELECT IFNULL(COUNT(*), 0) as cnt ");
		sb2.append(" FROM schedule  ");
		if (pVMode.equals("1")) {
			sb2.append(" WHERE system_code = ?  AND user_id='A' ");
		}
		else {
			sb2.append(" WHERE system_code = ?  AND user_id= ? ");
		}
		sb2.append(" AND ? >=  start_date AND ? <=  end_date ");
		
		sb3.append(" SELECT IFNULL(COUNT(*), 0) as cnt ");
		sb3.append(" FROM schedule ");
		sb3.append(" WHERE system_code = ?  AND sch_type in ('2', '99', '4')  ");
		sb3.append(" AND ? >=  start_date AND ? <=  end_date ");
		
		
		//일정정보 쿼리....
		sb.append(" SELECT sch_id, sch_type, contents ");
		sb.append(" FROM schedule  ");
		if (pVMode.equals("1")) {
			sb.append(" WHERE system_code = ?  AND user_id = 'A' ");
		}
		else {
			sb.append(" WHERE system_code = ?  AND user_id = ? ");
		}
		sb.append(" AND ? >=  start_date AND ? <=  end_date ");
		sb.append(" union ");
		sb.append(" SELECT sch_id, sch_type, contents ");
		sb.append(" FROM schedule ");
		sb.append(" WHERE system_code = ? AND sch_type in ('2', '99', '4') ");
		sb.append(" AND ? >=  start_date AND ? <=  end_date ");

		sql.setSql(sb.toString());
		sql2.setSql(sb2.toString());
		sql3.setSql(sb3.toString());
		
		
		try{
			
			for (int i = 1; i <= 31; i++) {
    			scheduleDto = new ScheduleDTO();


				stDay = Integer.toString(i);
    			if(stDay.length() == 1 ){
    				stDay = "0"+stDay;
    			}

    			stDate = stYear +  stMon + stDay ;
    			log.debug(stDate);


    			// 컨텐츠 수 구하기 //////////////////
     			sql2.clearParam();
     			sql2.setString(systemCode);
    			if (!pVMode.equals("1")) {
    				sql2.setString(userId);
    			}
			   	sql2.setString(stDate);
       			sql2.setString(stDate);

    			rs 	= 	null;
    			rs = broker.executeQuery(sql2);
				if(rs.next()){
					id_con_cnt = rs.getInt("cnt");
				}
				rs.close();
				
				sql3.clearParam();
				sql3.setString(systemCode);
				sql3.setString(stDate);
       			sql3.setString(stDate);
       			
       			rs 	= 	null;
    			rs = broker.executeQuery(sql3);
				if(rs.next()){
					type2_cnt = rs.getInt("cnt");
				}
				rs.close();
								
				//-- 총 일정수
				if (pVMode.equals("1")) {
					con_cnt	=	id_con_cnt;
				} else {
					con_cnt	=	id_con_cnt + type2_cnt;	
				}
				
//System.out.println("============== id_con_cnt : "+id_con_cnt);
//System.out.println("============================ type2_cnt : "+type2_cnt);
//System.out.println("============== con_cnt : "+con_cnt);
//System.out.println("--------------------------------------------------------");
				
    			// 일정정보  //////////
				sql.clearParam();
     			sql.setString(systemCode);
    			if (!pVMode.equals("1")) {
    				sql.setString(userId);
    			}
			   	sql.setString(stDate);
       			sql.setString(stDate);
       			sql.setString(systemCode);
       			sql.setString(stDate);
       			sql.setString(stDate);

       			rs2	= 	null;       			
       			rs2 = broker.executeQuery(sql);

    			int kk = 0;
 
     			while(rs2.next())
    			{
    				Tschid[i-1][kk] = Integer.toString(rs2.getInt("sch_id"));
    				Tschtype[i-1][kk] = StringUtil.nvl(rs2.getString("sch_type"));
    				Tcontents[i-1][kk] = StringUtil.nvl(rs2.getString("contents"));

    				kk++;
     			}

    			Tcnt += con_cnt;
     			CCNT[i-1] = Integer.toString(con_cnt);
     			
      			rs2.close();
     		}
 			scheduleDto.setTcnt(Tcnt); // 전체 컨텐츠수
			scheduleDto.setConCnt(CCNT); // 일자별컨텐츠 수
			scheduleDto.setTschId(Tschid);
			scheduleDto.setTschType(Tschtype);
			scheduleDto.setTschContents(Tcontents); //일자별 컨텐츠 리스트
			
 		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}finally{
			try{
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return scheduleDto;
	}


	/**
	 * 일정 최대값을 가져온다
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSchId(String systemcode, String userid) throws DAOException{
	 int  schId = 0;

	 StringBuffer sb = new StringBuffer();
	 sb.append(" select ifnull(max(sch_id),0)+1 as sch_id ");
	 sb.append(" from schedule ");
	 sb.append(" where system_code = ? and user_id = ?  ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(systemcode);
	 sql.setString(userid);

	 log.debug("[getMaxSchId]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	schId = rs.getInt("sch_id");
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

	 return schId;
	}

	/**
	 * 일정을 입력한다.
	 * @param schedule
	 * @return
	 * @throws DAOException
	 */
	public int addSchedule(ScheduleDTO schedule) throws DAOException{
		 int retVal = 0;

		 //일정관리 아이디 최대값
		 int sch_id = getMaxSchId(schedule.getSystemCode(), schedule.getUserId());

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into schedule ");
		 sb.append(" (system_code,user_id,sch_id,sch_type,start_date,end_date,contents, ");
		 sb.append(" reg_id,reg_date) values( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
		 sql.setSql(sb.toString());
		 sql.setString(schedule.getSystemCode());
		 sql.setString(schedule.getUserId());
		 sql.setInteger(sch_id);
		 sql.setString(schedule.getSchType());
		 sql.setString(schedule.getStartDate());
		 sql.setString(schedule.getEndDate());
		 sql.setString(schedule.getContents());
		 sql.setString(schedule.getRegId());
		 sql.setString(CommonUtil.getCurrentDate());

		 log.debug("[addSchedule]" + sql.toString());
		 try{
			retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }


		return retVal;
	}

	/**
	 * 일정을 수정한다.
	 * @param schedule
	 * @return
	 * @throws DAOException
	 */
	public int editSchedule(ScheduleDTO schedule) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update schedule ");
		 sb.append(" set sch_type= ?, start_date=?, end_date=?, contents=?, mod_id=?, mod_date=? ");
		 sb.append(" where system_code = ? and user_id=? and sch_id = ?  ");

		 sql.setSql(sb.toString());
		 sql.setString(schedule.getSchType());
		 sql.setString(schedule.getStartDate());
		 sql.setString(schedule.getEndDate());
		 sql.setString(schedule.getContents());
		 sql.setString(schedule.getModId());
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(schedule.getSystemCode());
		 sql.setString(schedule.getUserId());
		 sql.setInteger(schedule.getSchId());

		 log.debug("[editSchedule]" + sql.toString());
		 try{
			retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }


		 return retVal;
	}

	/**
	 * 일정을 수정한다.
	 * @param schedule
	 * @return
	 * @throws DAOException
	 */
	public int editSchedule(ScheduleDTO schedule, String pVMode) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append(" update schedule ");
		 sb.append(" set sch_type= ?, start_date=?, end_date=?, contents=?, mod_id=?, mod_date=? ");
		 if (!pVMode.equals("1")) {
		 	sb.append(" where system_code = ? and user_id=? and sch_id = ?  ");
		 }
		 else {
		 	sb.append(" where system_code = ? and user_id='A' and sch_id = ?  ");
		 }

		 sql.setSql(sb.toString());
		 sql.setString(schedule.getSchType());
		 sql.setString(schedule.getStartDate());
		 sql.setString(schedule.getEndDate());
		 sql.setString(schedule.getContents());
		 sql.setString(schedule.getModId());
		 sql.setString(CommonUtil.getCurrentDate());
		 sql.setString(schedule.getSystemCode());
		 if (!pVMode.equals("1")) {
		 	sql.setString(schedule.getUserId());
		 }
		 sql.setInteger(schedule.getSchId());

		 log.debug("[editSchedule]" + sql.toString());
		 try{
			retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }


		 return retVal;
	}

	/**
	 * 일정을 삭제한다.
	 * @param systemcode
	 * @param userid
	 * @param schid
	 * @return
	 * @throws DAOException
	 */
	public int delSchedule(String systemcode, String userid, String schid) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from schedule ");
		 sb.append(" where system_code = ? and user_id=? and sch_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(userid);
		 sql.setInteger(schid);

		 log.debug("[delSchedule]" + sql.toString());

		 try{
			retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 일정을 삭제한다.
	 * @param systemcode
	 * @param userid
	 * @param schid
	 * @return
	 * @throws DAOException
	 */
	public int delSchedule(String systemcode, String userid, String schid, String pVMode) throws DAOException{
		 int retVal = 0;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from schedule ");
		 if (!pVMode.equals("1")) {
		 	sb.append(" where system_code = ? and user_id=? and sch_id = ? ");
		 }
		 else {
		 	sb.append(" where system_code = ? and user_id='A' and sch_id = ? ");
		 }
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 if (!pVMode.equals("1")) {
		 	sql.setString(userid);
		 }
		 sql.setInteger(schid);

		 log.debug("[delSchedule]" + sql.toString());

		 try{
			retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 일정정보를  가져온다.
	 * @param systemcode
	 * @param userid
	 * @param schid
	 * @return
	 * @throws DAOException
	 */
	public ScheduleDTO getSchedule(String systemcode, String userid, String schid) throws DAOException{
		ScheduleDTO ScheduleDTO 	= 	null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select sch_type, start_date, end_date, contents ");
		sb.append(" from schedule ");
		sb.append(" where system_code = ? and user_id = ? and sch_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(userid);
		sql.setInteger(schid);

		log.debug("[getSchedule]" + sql.toString());

		ResultSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				ScheduleDTO 		= 	new ScheduleDTO();
				ScheduleDTO.setSchType(StringUtil.nvl(rs.getString("sch_type")));
				ScheduleDTO.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				ScheduleDTO.setEndDate(StringUtil.nvl(rs.getString("end_date")));
				ScheduleDTO.setContents(StringUtil.nvl(rs.getString("contents")));

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
		return ScheduleDTO;
	}

	/**
	 * 일정정보를  가져온다.
	 * @param systemcode
	 * @param userid
	 * @param schid
	 * @return
	 * @throws DAOException
	 */
	public ScheduleDTO getSchedule(String systemcode, String userid, String schid, String pVMode) throws DAOException{
		ScheduleDTO ScheduleDTO 	= 	null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select sch_type, start_date, end_date, contents ");
		sb.append(" from schedule ");
		if (!pVMode.equals("1")) {
			sb.append(" where system_code = ? and user_id = ? and sch_id = ? ");
		}
		else {
			sb.append(" where system_code = ? and user_id='A' and sch_id = ? ");
		}
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		if (!pVMode.equals("1")) {
			sql.setString(userid);
		}
		sql.setInteger(schid);

		log.debug("[getSchedule]" + sql.toString());

		ResultSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				ScheduleDTO 		= 	new ScheduleDTO();
				ScheduleDTO.setSchType(StringUtil.nvl(rs.getString("sch_type")));
				ScheduleDTO.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				ScheduleDTO.setEndDate(StringUtil.nvl(rs.getString("end_date")));
				ScheduleDTO.setContents(StringUtil.nvl(rs.getString("contents")));

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
		return ScheduleDTO;
	}

	/**
	 * 오늘의 일정리스트를 가져온다 (마이페이지)
	 * @param systemcode
	 * @param userid
	 * @param date
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getScheduleList(String systemcode, String userid, int date) throws DAOException{
		ArrayList scheduleList		=	null;
		ScheduleDTO scheduleDto 	= 	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select sch_type, start_date, end_date, contents ");
		sb.append(" from schedule ");
		sb.append(" where system_code = ? and user_id = ? ");
		sb.append(" and ? >=  start_date and ? <=  end_date ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(userid);
		sql.setInteger(date);
		sql.setInteger(date);

		log.debug("[getScheduleList]" + sql.toString());
		ResultSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			scheduleList		= 	new ArrayList();

  			while(rs.next()){
  				scheduleDto 		= 	new ScheduleDTO();
  				scheduleDto.setSchType(StringUtil.nvl(rs.getString("sch_type")));
  				scheduleDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
  				scheduleDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
  				scheduleDto.setContents(StringUtil.nvl(rs.getString("contents")));

				scheduleList.add(scheduleDto);
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
		return scheduleList;
	}
	
	
	/**
	 * 해당날짜에 등록된 스케쥴이 있는지 없는지 체크
	 * 학생지원센터 > 학사일정 화면에서 쓰임
	 * 
	 * @param systemcode
	 * @param curriYear
	 * @param curriMonth
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getScheduleYnList(String systemcode, int curriYear, int curriMonth) throws DAOException{
		ArrayList scheduleList		=	null;
		ScheduleDTO scheduleDto 	= 	null;
		String	pMonth	=	"";

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		
		if(curriMonth < 10) {
			pMonth	=	"0"+curriMonth;
		} else {
			pMonth	=	""+curriMonth;
		}
		
		sb.append(" SELECT x.system_code, x.code_so ");
		sb.append(" , CASE WHEN sc.start_date <= CONCAT('"+curriYear+"', '"+pMonth+"', x.code_so) AND sc.end_date >= CONCAT('"+curriYear+"', '"+pMonth+"', x.code_so) THEN 1 ELSE 0 END schdule_yn ");
		sb.append(" FROM code_so x LEFT OUTER JOIN schedule sc on sc.system_code = x.system_code AND sc.sch_type = '99' ");
		sb.append(" WHERE x.system_code = ? AND x.code_dae = '999' ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		
		log.debug(" [getScheduleYnList] :  "+sql.toString());
		//System.out.println(" [getScheduleYnList] :  "+sql.toString());
		
		RowSet	rs	=	null;
		try {
			rs	=	broker.executeQuery(sql);
			scheduleList		= 	new ArrayList();
			
			while(rs.next()) {
				scheduleDto 		= 	new ScheduleDTO();
				scheduleDto.setTcnt(StringUtil.nvl(rs.getString("schdule_yn"), 0));
				
				scheduleList.add(scheduleDto);
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
		return scheduleList;
	}
	
	/**
	 * 월별 일정을 받아온다.
	 * 학생지원센터 > 학사일정 화면에서 쓰임
	 * 
	 * @param systemcode
	 * @param curriYear
	 * @param curriMonth
	 * @param schType
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getScheduleList(String systemcode, int curriYear, int curriMonth, String schType) throws DAOException{
		ArrayList 	scheduleList	=	null;
		ScheduleDTO scheduleDto 	= 	null;
		String		pMonth			=	"";
		String		pYearMonth		=	"";
		if(curriMonth < 10) {
			pMonth	=	"0"+curriMonth;
		} else {
			pMonth	=	""+curriMonth;
		}
		pYearMonth		=	""+curriYear+pMonth;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT start_date, end_date, contents ");
		sb.append(" FROM schedule  ");
		sb.append(" WHERE system_code = ? AND sch_type = ? ");
		sql.setString(systemcode);
		sql.setString(schType);
		
		if(!pYearMonth.equals("") && pYearMonth.length() == 6) {
			sb.append(" AND substring(start_date, 1, 6) = ? ");
			sql.setString(pYearMonth);
		}
		
		sb.append(" ORDER BY start_date ASC ");
		
		sql.setSql(sb.toString());
		
		log.debug(" [getScheduleList] : "+sql.toString());
		//System.out.println(" [getScheduleList] : "+sql.toString());
		
		RowSet	rs	=	null;
		try {
			rs	=	broker.executeQuery(sql);
			scheduleList		= 	new ArrayList();
			
			while(rs.next()) {
				scheduleDto 		= 	new ScheduleDTO();
				scheduleDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				scheduleDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
				scheduleDto.setContents(StringUtil.nvl(rs.getString("contents")));
				
				scheduleList.add(scheduleDto);
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
		return scheduleList;
	}


}
