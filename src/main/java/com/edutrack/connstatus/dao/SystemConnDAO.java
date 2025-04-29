/*
 * Created on 2004. 10. 27.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.connstatus.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.connstatus.dto.ConnectResDTO;
import com.edutrack.connstatus.dto.CourseConnectDTO;
import com.edutrack.connstatus.dto.CurriConnectDTO;
import com.edutrack.connstatus.dto.SystemConnectDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SystemConnDAO extends AbstractDAO{

	/**
	 *
	 */
	public SystemConnDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * system 접속 통계를 가져온다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getSystemConCnt(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		/*
		 * systemConnectDto.getSelectGb()
		 * 		1	시간별
		 * 		2	일별
		 * 		3	월별
		 * 		4	년도별
		 * 		5	요일별
		 * 		6	연령별
		 * 		7	성별
		 * 		8	지역별
		 *
		 *
		 */
		return getSystemSelect(systemcode, systemConnectDto);

//		 if(systemConnectDto.getSelectGb().equals("1")){
//---------------------------------------------------------------------------------------------시간별 검색 종료
//		 	return getSystemSelect(systemcode, systemConnectDto);
//---------------------------------------------------------------------------------------------연령별 검색 시작
//		 }else if(systemConnectDto.getSelectGb().equals("2")){
////---------------------------------------------------------------------------------------------연령별 검색 종료
//		 	return getSystemSelectGubun2(systemcode, systemConnectDto);
////---------------------------------------------------------------------------------------------성별 검색 시작
//		 }else if(systemConnectDto.getSelectGb().equals("3")){
////---------------------------------------------------------------------------------------------성별 검색 종료
//		 	return getSystemSelectGubun3(systemcode, systemConnectDto);
////---------------------------------------------------------------------------------------------지역별 검색 시작
//		 }else if(systemConnectDto.getSelectGb().equals("4")){
//		 	return getSystemSelectGubun4(systemcode, systemConnectDto);
////---------------------------------------------------------------------------------------------지역별 검색 끝
//		 }else if(systemConnectDto.getSelectGb().equals("5")){
////---------------------------------------------------------------------------------------------계층별 검색 시작
//		 	return getSystemSelectGubun5(systemcode, systemConnectDto);
////---------------------------------------------------------------------------------------------계층별 검색 끝
//		 }else if(systemConnectDto.getSelectGb().equals("6")){
////---------------------------------------------------------------------------------------------요일별 검색 시작
//		 	return getSystemSelectGubun6(systemcode, systemConnectDto);
////---------------------------------------------------------------------------------------------요일별 검색 끝
//		 }

//		 return null;
	}


	public int getTotalCnt(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
        int retVal = 0;

        String startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	    String endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

        sb.append(" select ifnull(count(user_id),0) as cnt  ");
		 sb.append(" from system_connect ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and conn_time BETWEEN ? AND ? ");
		 sql.setString(systemcode);
		 sql.setString(startVal);
		 sql.setString(endVal);


		 sql.setSql(sb.toString());
		 log.debug("[getTotalCnt]" + sql.toString());
	     String[] result = null;
		 RowSet rs = null;
		 int totalCnt = 0;
		 int curCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);
			 rs.next();
			 retVal = rs.getInt("cnt");
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

	protected String getStartVal(String year, String month, String day){
		String startVal = "";

		if(year.equals("all")){
	        startVal = "0000";
	    }else{
	        startVal = year;
	    }
	    if(month.equals("all")){
	        startVal = startVal + "00";
	    }else{
	        startVal = startVal + month;
	    }
	    if(day.equals("all")){
	        startVal = startVal + "00";
	    }else{
	        startVal = startVal + day;
	    }

	    return startVal+"000001";
	}

	protected String getEndVal(String year, String month, String day){
		String endVal = "";

		if(year.equals("all")){
	        endVal = "9999";
	    }else{
	        endVal = endVal + year;
	    }
	    if(month.equals("all")){
	        endVal = endVal + "13";
	    }else{
	        endVal = endVal + month;
	    }
	    if(day.equals("all")){
	        endVal = endVal + "32";
	    }else{
	        endVal = endVal + day;
	    }

	    return endVal+"235959";
	}


	protected ArrayList getSystemSelect(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
         ArrayList retArray = null;

         String startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
 	     String endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

 	     if(systemConnectDto.getSelectGb().equals("1"))
 	     {
	         sb.append(" select concat(co.code_so,' 시') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join system_connect s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,9,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=24 ");
			 sb.append(" group by concat(co.code_so,' 시') ");
 	     }
 	    if(systemConnectDto.getSelectGb().equals("2"))
	     {
	         sb.append(" select concat(co.code_so,' 일') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join system_connect s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,7,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=31 ");
			 sb.append(" group by concat(co.code_so,' 일') ");
	     }
 	    if(systemConnectDto.getSelectGb().equals("3"))
	     {
	         sb.append(" select concat(co.code_so,' 월') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join system_connect s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,5,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=12 ");
			 sb.append(" group by concat(co.code_so,' 월') ");
	     }
 	   	if(systemConnectDto.getSelectGb().equals("4"))
	     {
	         sb.append(" select concat('20',co.code_so,' 년') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join system_connect s ");
			 sb.append(" on co.system_code = s.system_code and concat('20',co.code_so)=substring(s.conn_time,1,4)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so BETWEEN '05' AND CAST(DATE_FORMAT(NOW(),'%y') AS CHAR) ");
			 sb.append(" group by concat('20',co.code_so,' 년') ");
	     }

 	   if(systemConnectDto.getSelectGb().equals("5"))
	     {
	         sb.append(" select concat(co.code_so,' 요일') as get_time, co.CODE_COMMENT , ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join system_connect s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=CAST(ELT(DATE_FORMAT(SUBSTRING(s.conn_time,1,8),'%w')+1,'일','월','화','수','목','금','토')AS CHAR) ");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='998'  ");
			 sb.append(" group by concat(co.code_so,' 요일')  , co.CODE_COMMENT");
			 sb.append(" order by co.CODE_COMMENT");
	     }

 	  if(systemConnectDto.getSelectGb().equals("6"))
	     {
	         sb.append(" select concat(co.code_so,' 대') as get_time,  ifnull(count(su.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join ( ");
			 sb.append(" 	select s.system_code as system_code, s.conn_time as conn_time");
			 sb.append(" 	, CONCAT(SUBSTRING(CAST(DATE_FORMAT(NOW(),'%Y') AS CHAR)-SUBSTRING(u.jumin_no,1,2), 3,1),'0') AS age   ");
		     sb.append(" 	, s.user_id as user_id  ");
			 sb.append(" 	from system_connect s, users u ");
			 sb.append(" 	where s.system_code=u.system_code and s.user_id=u.user_id and s.system_code=? )su");
			 sb.append(" on co.system_code = su.system_code and co.code_so=su.age");
			 sb.append(" and su.conn_time BETWEEN ? AND ? ");
			 sb.append(" where co.system_code = ? and co.code_dae='996'");
			 sb.append(" group by concat(co.code_so,' 대') ");
			 sql.setString(systemcode);
	     }

 	  if(systemConnectDto.getSelectGb().equals("7"))
	     {
	         sb.append(" select co.code_so as get_time,  ifnull(count(su.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join ( ");
			 sb.append(" 	select s.system_code as system_code, s.conn_time as conn_time");
	     	 sb.append(" 	, case when (substring(u.jumin_no,8,1) = '1' or substring(u.jumin_no,8,1) = '3')  then '남' else '여' END AS sex_type  ");
		     sb.append(" 	, s.user_id as user_id  ");
			 sb.append(" 	from system_connect s, users u ");
			 sb.append(" 	where s.system_code=u.system_code and s.user_id=u.user_id and s.system_code=? )su");
			 sb.append(" on co.system_code = su.system_code and co.code_so=su.sex_type");
			 sb.append(" and su.conn_time BETWEEN ? AND ? ");
			 sb.append(" where co.system_code = ? and co.code_dae='997'");
			 sb.append(" group by co.code_so ");
			 sql.setString(systemcode);
	     }

 	 if(systemConnectDto.getSelectGb().equals("8"))
     {
         sb.append(" select co.so_name as get_time, co.code_so,  ifnull(count(su.user_id),0) as cnt  ");
		 sb.append(" from code_so co left outer join ( ");
		 sb.append(" 	select s.system_code as system_code, s.conn_time as conn_time");
     	 sb.append(" 	, case when (substring(u.post_code,1,3) = '417') then '11'/*인천(강화)*/  ");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 100 and 199) then '10' /*서울*/  ");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 200 and 299) then '24' /*강원*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 300 and 309) then '21' /*대전*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 310 and 359) then '22' /*충남*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 360 and 399) then '23' /*충북*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 400 and 409) then '11' /*인천*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 410 and 499) then '12' /*경기*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 500 and 509) then '18' /*광주*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 510 and 559) then '19' /*전남*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 560 and 599) then '20' /*전북*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 600 and 619) then '13' /*부산*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 620 and 679) then '15' /*경남*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 680 and 689) then '14' /*울산*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 690 and 699) then '25' /*제주*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 700 and 711) then '16' /*대구*/");
	     sb.append(" 		 when (substring(u.post_code,1,3) between 712 and 799) then '17' /*경북*/");
	     sb.append(" 		 else '99' end as area");
	     sb.append(" 	, s.user_id as user_id  ");
		 sb.append(" 	from system_connect s, users u ");
		 sb.append(" 	where s.system_code=u.system_code and s.user_id=u.user_id and s.system_code=? )su");
		 sb.append(" on co.system_code = su.system_code and co.code_so=su.area");
		 sb.append(" and su.conn_time BETWEEN ? AND ? ");
		 sb.append(" where co.system_code = ? and co.code_dae='401'");
		 sb.append(" group by co.so_name, co.code_so ");
		 sb.append(" order by co.code_so ");
		 sql.setString(systemcode);
     }


 	   	/*
 	   	 * select co.code_so,  ifnull(count(su.user_id),0) as cnt
from code_so co left outer join (
select s.system_code as system_code
, case when (substring(u.jumin_no,8,1) = '1' or substring(u.jumin_no,8,1) = '3')  then '남' else '여' END AS sex_type
, s.user_id as user_id
from system_connect s, users u
where s.system_code=u.system_code and s.user_id=u.user_id)su
on co.system_code = su.system_code and co.code_so=su.sex_type
where co.system_code = '1' and co.code_dae='997'
group by co.code_so
 	   	 */

		 sql.setString(startVal);
		 sql.setString(endVal);
		 sql.setString(systemcode);
		 retArray = new ArrayList();


		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun1]" + sql.toString());
	     String[] result = null;
		 RowSet rs = null;
		 int curCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);

			 while(rs.next()){
		        curCnt = rs.getInt("cnt");
		        result = new String[]{"","0"};
		        result[0] =  StringUtil.nvl(rs.getString("get_time"));
		        result[1] =  ""+curCnt;
		        retArray.add(result);
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
		 //log.debug("++++++++++++++ retArray = "+retArray.size());
		 return retArray;
	}
	/*
	protected ArrayList getSystemSelectGubun1(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
        ArrayList retArray = null;

        String startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	     String endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

	         sb.append(" select co.code_so||' 시' as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join system_connect s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,9,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=24 ");
			 sb.append(" group by co.code_so||' 시' ");

		 sql.setString(startVal);
		 sql.setString(endVal);
		 sql.setString(systemcode);
		 retArray = new ArrayList();


		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun1]" + sql.toString());
	     String[] result = null;
		 RowSet rs = null;
		 int curCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);

			 while(rs.next()){
		        curCnt = rs.getInt("cnt");
		        result = new String[]{"","0"};
		        result[0] =  StringUtil.nvl(rs.getString("get_time"));
		        result[1] =  ""+curCnt;
		        retArray.add(result);
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
		 //log.debug("++++++++++++++ retArray = "+retArray.size());
		 return retArray;
	}

	protected ConnectResDTO getSystemSelectGubun2(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
        String result[] = null;
        ArrayList yearArray = null;
        ConnectResDTO resultDto = new ConnectResDTO();

	    String startVal = "";		//검색 조건 날짜 범위 시작점
	    String endVal = "";		//검색 조건 날짜 범위 종료점

        startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	    endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

	    sb.append(" select age, sum(conn_cnt) as cnt ");
		sb.append(" from system_connect ");
	    sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ? ");
		sb.append(" group by age ");
		sql.setString(systemcode);
		sql.setString(startVal);
		sql.setString(endVal);

		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun2]" + sql.toString());

		 RowSet rs = null;
		 int curCnt = 0;
		 int totalCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);
			 result  = new String[]{"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
			 while(rs.next()){
			 	 curCnt = 0;
			 	 curCnt = rs.getInt("cnt");
			 	 totalCnt += curCnt;
		         result[rs.getInt("age")/10] = ""+curCnt;
			 }
			 rs.close();

			 resultDto.setTotalCnt(totalCnt);
			 resultDto.setDateData(result);
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

		 return resultDto;
	}

	protected ConnectResDTO getSystemSelectGubun3(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
        String result[] = null;
        ArrayList yearArray = null;
        ConnectResDTO resultDto = new ConnectResDTO();

        String startVal = "";		//검색 조건 날짜 범위 시작점
	    String endVal = "";		//검색 조건 날짜 범위 종료점

        startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	    endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

         sb.append(" select sex_type, sum(conn_cnt) as cnt ");
		 sb.append(" from system_connect ");
	     sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ? ");
		 sb.append(" group by sex_type ");
		 sql.setString(systemcode);
		 sql.setString(startVal);
		 sql.setString(endVal);

		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun3]" + sql.toString());

		 RowSet rs = null;
		 int curCnt = 0;
		 int totalCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);
			 result  = new String[]{"0","0","0"};
			 while(rs.next()){
			 	curCnt = 0;
			 	curCnt = rs.getInt("cnt");
			 	totalCnt += curCnt;
		        result[Integer.parseInt(StringUtil.nvl(rs.getString("sex_type")))] = ""+curCnt;
			 }
			 rs.close();

			 resultDto.setTotalCnt(totalCnt);
			 resultDto.setDateData(result);
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


		return resultDto;
	}

	protected ConnectResDTO getSystemSelectGubun4(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
        String result[] = null;
        ArrayList yearArray = null;
        ConnectResDTO resultDto = new ConnectResDTO();

        String startVal = "";		//검색 조건 날짜 범위 시작점
	    String endVal = "";		//검색 조건 날짜 범위 종료점

        startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	    endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

        sb.append(" SELECT   c.code_so as areacode, c.so_name as areaname, ifnull(sum(conn_cnt),0) as cnt ");
		 sb.append(" FROM code_so c left outer join system_connect s on c.system_code = s.system_code and c.code_so = s.area ");
	     sb.append(" and s.conn_year||s.conn_month||s.conn_day BETWEEN ? AND ? ");
		 sb.append(" WHERE c.system_code = ? and c.code_dae = '401' ");
		 sb.append(" GROUP BY c.code_so, c.so_name ");
		 sql.setString(startVal);
		 sql.setString(endVal);
		 sql.setString(systemcode);

		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun4]" + sql.toString());

		 RowSet rs = null;
		 int curCnt = 0;
		 int totalCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);
			 yearArray = new ArrayList();

			 while(rs.next()){
			 	 curCnt = 0;
			 	 curCnt = rs.getInt("cnt");
			 	 totalCnt += curCnt;
			 	 result = new String[]{"","0"};
		         result[0] = StringUtil.nvl(rs.getString("areaname"));
		         result[1] = ""+curCnt;

		         yearArray.add(result);
			 }

			 resultDto.setTotalCnt(totalCnt);
			 resultDto.setYearData(yearArray);
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

		return resultDto;
	}

	protected ConnectResDTO getSystemSelectGubun5(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
         String result[] = null;
         ArrayList yearArray = null;
         ConnectResDTO resultDto = new ConnectResDTO();

         String startVal = "";		//검색 조건 날짜 범위 시작점
	     String endVal = "";		//검색 조건 날짜 범위 종료점

         startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	     endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

         sb.append(" SELECT   c.dept_socode, c.dept_soname, ifnull(sum(s.conn_cnt) ,0)as cnt ");
		 sb.append(" FROM dept_socode c left outer join system_connect s on c.system_code = s.system_code and c.dept_socode = s.dept ");
	     sb.append(" and s.conn_year||s.conn_month||s.conn_day BETWEEN ? AND ? ");
		 sb.append(" WHERE c.system_code = ? and c.dept_daecode = '100' ");
		 sb.append(" GROUP BY c.dept_socode, c.dept_soname ");
		 sql.setString(startVal);
		 sql.setString(endVal);
		 sql.setString(systemcode);

		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun5]" + sql.toString());

		 RowSet rs = null;
		 int curCnt = 0;
		 int totalCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);
			 yearArray = new ArrayList();

			 while(rs.next()){
			 	 curCnt = 0;
			 	 curCnt = rs.getInt("cnt");
			 	 totalCnt += curCnt;
			 	 result = new String[]{"","0"};
		         result[0] = StringUtil.nvl(rs.getString("dept_soname"));
		         result[1] = ""+curCnt;

		         yearArray.add(result);
			 }

			 resultDto.setTotalCnt(totalCnt);
			 resultDto.setYearData(yearArray);
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

		return resultDto;
	}

	protected ConnectResDTO getSystemSelectGubun6(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
        String result[] = null;
        ArrayList yearArray = null;
        ConnectResDTO resultDto = new ConnectResDTO();

        String startVal = "";		//검색 조건 날짜 범위 시작점
	    String endVal = "";		//검색 조건 날짜 범위 종료점

        startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	    endVal = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

//         sb.append(" select datepart(dw,conn_year + conn_month +  conn_day) as weekname, sum(conn_cnt) as cnt ");
//		 sb.append(" from system_connect ");
//	     sb.append(" where system_code = ? and conn_year + conn_month +  conn_day BETWEEN ? AND ? ");
//		 sb.append(" group by datepart(dw,conn_year + conn_month +  conn_day) ");
		 sb.append(" select  to_char(to_date(conn_year||conn_month||conn_day,'yyyy-mm-dd'),'D') as weekname, sum(conn_cnt) as cnt ");
		 sb.append(" from system_connect ");
	     sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ? ");
		 sb.append(" group by to_char(to_date(conn_year||conn_month||conn_day,'yyyy-mm-dd'),'D') ");
		 sql.setString(systemcode);
		 sql.setString(startVal);
		 sql.setString(endVal);

		 sql.setSql(sb.toString());
		 log.debug("[getSystemSelectGubun6]" + sql.toString());

		 RowSet rs = null;
		 int curCnt = 0;
		 int totalCnt = 0;
		 try{
			 rs = broker.executeQuery(sql);
			 result  = new String[]{"0","0","0","0","0","0","0","0"};
			 while(rs.next()){
			 	curCnt = 0;
			 	curCnt = rs.getInt("cnt");
			 	totalCnt += curCnt;
		        result[Integer.parseInt(rs.getString("weekname"))] = ""+curCnt;
			 }
			 rs.close();

			 resultDto.setTotalCnt(totalCnt);
			 resultDto.setDateData(result);
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


		return resultDto;
	}


	public ArrayList getCourseConCnt(String systemcode, String userid, CourseConnectDTO courseConnectDto) throws DAOException{

	     ArrayList courseConCntList = null;
	     CourseConnectDTO courseConnectResultDto = null;
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();

	     String startVal = "";		//검색 조건 날짜 범위 시작점
		 String endVal = "";		//검색 조건 날짜 범위 종료점

	     startVal = getStartVal(courseConnectDto.getStartYear(),courseConnectDto.getStartMon(),courseConnectDto.getStartDay());
		 endVal = getEndVal(courseConnectDto.getEndYear(),courseConnectDto.getEndMon(),courseConnectDto.getEndDay());

	     if(courseConnectDto.getSelectGb().equals("1")){
             if(courseConnectDto.getStartYear().equals("all")){
                 sb.append(" select course_id, conn_year, sum(conn_cnt) as cnt ");
				 	sb.append(" from course_connect ");
				 	if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sb.append(" where course_id = ? ");}
				 sb.append(" group by course_id, conn_year ");

				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sql.setString(courseConnectDto.getCourseId());}
             }else if(courseConnectDto.getStartMon().equals("all")){
                 sb.append(" select course_id, conn_year, conn_month, sum(conn_cnt) as cnt ");
				 	sb.append(" from course_connect ");
				 sb.append(" where conn_year = ? ");
				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sb.append(" and course_id = ? ");}
				 sb.append(" group by course_id, conn_year, conn_month ");

				 sql.setString(courseConnectDto.getStartYear());
				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sql.setString(courseConnectDto.getCourseId());}
			}else if(courseConnectDto.getStartDay().equals("all")){
                 sb.append(" select course_id, conn_year, conn_month, conn_day, sum(conn_cnt) as cnt ");
				 	sb.append(" from course_connect ");
				 sb.append(" where conn_year = ? ");
				 sb.append(" and conn_month = ? ");
				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sb.append(" and course_id = ? ");}
				 sb.append(" group by course_id, conn_year, conn_month, conn_day ");

				 sql.setString(courseConnectDto.getStartYear());
				 sql.setString(courseConnectDto.getStartMon());
				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sql.setString(courseConnectDto.getCourseId());}
             }else{
                 sb.append(" select course_id, conn_year, conn_month, conn_day, conn_hour, sum(conn_cnt) as cnt ");
				 	sb.append(" from course_connect ");
				 sb.append(" where conn_year = ? ");
				 sb.append(" and conn_month = ? ");
				 sb.append(" and conn_day = ? ");
				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sb.append(" and course_id = ? ");}
				 sb.append(" group by course_id, conn_year, conn_month, conn_day, conn_hour ");

				 sql.setString(courseConnectDto.getStartYear());
				 sql.setString(courseConnectDto.getStartMon());
				 sql.setString(courseConnectDto.getStartDay());
				 if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){sql.setString(courseConnectDto.getCourseId());}
             }
	     }else if(courseConnectDto.getSelectGb().equals("2") || courseConnectDto.getSelectGb().equals("3") || courseConnectDto.getSelectGb().equals("4")){
	         if(courseConnectDto.getCourseId().equals("all") || courseConnectDto.getCourseId().equals("") || courseConnectDto.getCourseId() == null){
	             sb.append(" select course_id, sum(conn_cnt) as cnt ");
				 	sb.append(" from course_connect ");
				 sb.append(" group by course_id ");
	         }else{
	             if(courseConnectDto.getSelectGb().equals("2")){
	                 sb.append(" select course_id, age, sum(conn_cnt) as cnt ");
				 		sb.append(" from course_connect ");
					 sb.append(" where conn_year||conn_month||conn_day BETWEEN ? AND ? ");
					 sb.append(" and course_id = ? ");
					 sb.append(" group by course_id, age ");
	             }else if(courseConnectDto.getSelectGb().equals("3")){
	                 sb.append(" select course_id, sex_type, sum(conn_cnt) as cnt ");
				 		sb.append(" from course_connect ");
					 sb.append(" where conn_year||conn_month||conn_day BETWEEN ? AND ?  ");
					 sb.append(" and course_id = ? ");
					 sb.append(" group by course_id, sex_type ");
	             }else {
	                 sb.append(" select course_id, area, sum(conn_cnt) as cnt ");
				 		sb.append(" from course_connect ");
					 sb.append(" where conn_year||conn_month||conn_day BETWEEN ? AND ?  ");
					 sb.append(" and course_id = ? ");
					 sb.append(" group by course_id, area ");
	             }
	             sql.setString(startVal);
		         sql.setString(endVal);
		         sql.setString(courseConnectDto.getCourseId());
	        }
	    }else{
	        sb.append(" select course_id, sum(conn_cnt) as cnt ");
	       	sb.append(" from course_connect ");
		    sb.append(" group by course_id ");
	    }

		sql.setSql(sb.toString());
		log.debug("[getCourseConCnt]" + sql.toString());

		RowSet rs = null;
		try{
			 rs = broker.executeQuery(sql);
			 courseConCntList = new ArrayList();

			 while(rs.next()){
			     courseConnectResultDto = new CourseConnectDTO();

			     if(courseConnectDto.getSelectGb().equals("1")){
log.debug("-------------------------------------------------------1");
			         if(courseConnectDto.getStartYear().equals("all")){
						 courseConnectResultDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			         	 courseConnectResultDto.setConnYear(rs.getInt("conn_year"));
						 courseConnectResultDto.setCnt(rs.getInt("cnt"));
			         }else if(courseConnectDto.getStartMon().equals("all")){
			             courseConnectResultDto.setConnYear(rs.getInt("conn_year"));
			             courseConnectResultDto.setConnMonth(rs.getInt("conn_month"));
			         }else if(courseConnectDto.getStartDay().equals("all")){
			             courseConnectResultDto.setConnYear(rs.getInt("conn_year"));
			             courseConnectResultDto.setConnMonth(rs.getInt("conn_month"));
			             courseConnectResultDto.setConnDay(rs.getInt("conn_day"));
			         }else{
			             courseConnectResultDto.setConnYear(rs.getInt("conn_year"));
			             courseConnectResultDto.setConnMonth(rs.getInt("conn_month"));
			             courseConnectResultDto.setConnDay(rs.getInt("conn_day"));
			             courseConnectResultDto.setConnHour(rs.getInt("conn_hour"));
			         }
			    }else{
			        if(!courseConnectDto.getCourseId().equals("all") && !courseConnectDto.getCourseId().equals("") && courseConnectDto.getCourseId() != null){
log.debug("-------------------------------------------------------2");
					     if(courseConnectDto.getSelectGb().equals("2")){
					         courseConnectResultDto.setAge(rs.getInt("age"));
					     }else if(courseConnectDto.getSelectGb().equals("3")){
						     courseConnectResultDto.setSexType(StringUtil.nvl(rs.getString("sex_type")));
					     }else if(courseConnectDto.getSelectGb().equals("4")){
						     courseConnectResultDto.setArea(StringUtil.nvl(rs.getString("area")));
					     }
			        }
			    }
log.debug("-------------------------------------------------------3");
		        courseConCntList.add(courseConnectResultDto);
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
		return courseConCntList;
	}


	public ArrayList getCurriConCnt(String systemcode, String userid, CurriConnectDTO curriConnectDto) throws DAOException{

	     ArrayList curriConCntList = null;
	     CurriConnectDTO curriConnectResultDto = null;
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
         String result[] = null;
         ArrayList yearArray = null;
         ConnectResDTO resultDto = new ConnectResDTO();

	     String startVal = "";		//검색 조건 날짜 범위 시작점
	     String endVal = "";		//검색 조건 날짜 범위 종료점

	     startVal = getStartVal(curriConnectDto.getStartYear(),curriConnectDto.getStartMon(),curriConnectDto.getStartDay());
		 endVal = getEndVal(curriConnectDto.getEndYear(),curriConnectDto.getEndMon(),curriConnectDto.getEndDay());

	     if(curriConnectDto.getCurriCode().equals("all") || curriConnectDto.getCurriCode().equals("") || curriConnectDto.getCurriCode() == null){
	         if(curriConnectDto.getSelectGb() == null || curriConnectDto.getSelectGb().equals("")){
		         sb.append(" select sum(conn_cnt) as cnt ");
				 sb.append(" from curri_connect ");
				 sb.append(" where system_code = ? ");
				 sql.setString(systemcode);
	         }else{
		         sb.append(" select sum(conn_cnt) as cnt ");
				 sb.append(" from curri_connect ");
				 sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ?  ");
				 sql.setString(systemcode);
				 sql.setString(startVal);
		         sql.setString(endVal);
	         }
	     }else{
	         if(curriConnectDto.getSelectGb().equals("1")){
		         sb.append(" select curri_code, age, sum(conn_cnt) as cnt ");
				 sb.append(" from curri_connect ");
			     sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ?  ");
			     sb.append(" and curri_code = ? ");
				 sb.append(" group by curri_code, age ");
		     }else if(curriConnectDto.getSelectGb().equals("2")){
		         sb.append(" select curri_code, sex_type, sum(conn_cnt) as cnt ");
				 sb.append(" from curri_connect ");
			     sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ?  ");
			     sb.append(" and curri_code = ? ");
				 sb.append(" group by curri_code, sex_type ");
		     }else if(curriConnectDto.getSelectGb().equals("3")){
		         sb.append(" select curri_code, area, sum(conn_cnt) as cnt ");
				 sb.append(" from curri_connect ");
			     sb.append(" where system_code = ? and conn_year||conn_month||conn_day BETWEEN ? AND ?  ");
			     sb.append(" and curri_code = ? ");
				 sb.append(" group by curri_code, area ");
		     }
		     sql.setString(systemcode);
	         sql.setString(startVal);
	         sql.setString(endVal);
	         sql.setString(curriConnectDto.getCurriCode());
	     }

		 sql.setSql(sb.toString());
		 log.debug("[getCurriConCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 curriConCntList = new ArrayList();

			 while(rs.next()){
			     curriConnectResultDto = new CurriConnectDTO();

			     if(!curriConnectDto.getCurriCode().equals("all") && !curriConnectDto.getCurriCode().equals("") && curriConnectDto.getCurriCode() != null){
				     if(curriConnectDto.getSelectGb().equals("1")){
				         curriConnectResultDto.setAge(rs.getInt("age"));
				     }else if(curriConnectDto.getSelectGb().equals("2")){
				         curriConnectResultDto.setSexType(StringUtil.nvl(rs.getString("sex_type")));
				     }else if(curriConnectDto.getSelectGb().equals("3")){
				         curriConnectResultDto.setArea(StringUtil.nvl(rs.getString("area")));
				     }
			     }
			     curriConnectResultDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			     curriConnectResultDto.setCnt(rs.getInt("cnt"));
			     curriConCntList.add(curriConnectResultDto);
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
		 return curriConCntList;
	}
	*/
}