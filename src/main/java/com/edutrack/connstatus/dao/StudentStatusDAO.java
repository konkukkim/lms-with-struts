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
public class StudentStatusDAO extends AbstractDAO{

	/**
	 *
	 */
	public StudentStatusDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}



	public ArrayList getCurriSelect(String systemcode, SystemConnectDTO systemConnectDto) throws DAOException{
		/*
		 * curriConnectDto.getSelectGb()
		 * 		5	요일별
		 * 		6	연령별
		 * 		7	성별
		 * 		8	지역별
		 * systemConnectDto.getStudentGb()
		 * 		E	수강신청자
		 * 		S	수강생 ...현재는 수강생만 사용함(2005.09.10)...향후를 대비해서 수강신청자와 수료생도 모두 DAO 에는 반영되어있
		 * 		C	수료생
		 */

		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
         ArrayList retArray = null;

         String startVal = getStartVal(systemConnectDto.getStartYear(),systemConnectDto.getStartMon(),systemConnectDto.getStartDay());
	     String endVal   = getEndVal(systemConnectDto.getEndYear(),systemConnectDto.getEndMon(),systemConnectDto.getEndDay());

	     String tmpSql = "";

	     if(systemConnectDto.getStudentGb().equals("E")){
             tmpSql  = "		and substring(s.enroll_date,1,8) BETWEEN '"+startVal+"' AND '"+endVal+"'  ";
         }
	     else if(systemConnectDto.getStudentGb().equals("S")){
             tmpSql  = " 		and ( '"+startVal+"' BETWEEN  substring(s.servicestart_date,1,8)  AND  substring(s.serviceend_date,1,8) OR ";
             tmpSql += " 		      '"+endVal  +"' BETWEEN  substring(s.servicestart_date,1,8)  AND  substring(s.serviceend_date,1,8) OR ";
             tmpSql += " 		    ( '"+startVal+"' <=  substring(S.SERVICESTART_DATE,1,8) AND '"+endVal  +"' >= substring(S.SERVICEEND_DATE,1,8))) ";
             tmpSql += " 		and s.enroll_status <> 'N' ";
         }
         else {
             tmpSql = " 		and substring(s.complete_date,1,8) BETWEEN '"+startVal+"' AND '"+endVal+"'  ";
         }

		 if(!systemConnectDto.getCateCode().equals(""))
		     tmpSql +=" and s.curri_code in (select curri_code from curri_top where system_code='"+systemcode+"' and cate_code ='"+systemConnectDto.getCateCode()+"' ) ";

		if(!systemConnectDto.getProperty2().equals(""))
		    tmpSql +=" and s.curri_code in (select curri_code from curri_top where system_code='"+systemcode+"' and curri_property2='"+systemConnectDto.getProperty2()+"') ";

		if(!systemConnectDto.getCurriCode().equals(""))
		    tmpSql +=" and s.curri_code= '"+systemConnectDto.getCurriCode()+"' ";


//         // 요일별
//         if(systemConnectDto.getSelectGb().equals("5"))
//         {
//
//             sb.append(" select co.code_so||' 요일' as get_time,  ifnull(s.cnt,0)  as cnt ");
//             sb.append(" 	from code_so co left outer join         ");
//             sb.append(" 		(                                   ");
//             sb.append(" 		select to_char(to_date(substring(s.enroll_date,1,8),'yyyy-mm-dd'),'DY') as weekname, count(s.user_id) as cnt , system_code");
//             sb.append(" 		from student s           ");
//             sb.append(" 		where  s.system_code = ? ");
//             sb.append( tmpSql );
//             sb.append(" 		group by to_char(to_date(substring(s.enroll_date,1,8),'yyyy-mm-dd'),'DY'),system_code ");
//             sb.append(" 		) s            ");
//             sb.append(" 		on co.system_code = s.system_code          ");
//             sb.append(" 		and co.code_so=s.weekname                  ");
//             sb.append(" 	where co.system_code = ? and co.code_dae='998' ");
//             sb.append(" 	order by co.code_comment                       ");
//
//         }

	     // 연령별
         if(systemConnectDto.getSelectGb().equals("6"))
         {

             sb.append(" select concat(co.code_so,' 대') as get_time,  ifnull(su.cnt,0) as cnt  ");
             sb.append(" from code_so co left outer join  ");
             sb.append("       (    ");
             sb.append(" 		select concat(SUBSTRING(CAST(DATE_FORMAT(NOW(), '%Y') AS CHAR)-SUBSTRING(u.jumin_no,1,2), 3,1),'0') as age, count(s.user_id) as cnt, s.system_code ");
             sb.append(" 		from users u, student s ");
             sb.append(" 		where u.system_code = s.system_code and u.user_id = s.user_id  and u.system_code = ?");
             sb.append( tmpSql );
             sb.append(" 		group by  concat(SUBSTRING(CAST(DATE_FORMAT(NOW(), '%y') AS CHAR)-SUBSTRING(u.jumin_no,1,2), 3,1),'0') ,s.system_code ");
             sb.append("        ) su  ");
             sb.append(" on co.system_code = su.system_code and co.code_so=su.age");
             sb.append(" where co.system_code = ? and co.code_dae='996'");
             sb.append(" order by get_time ");

         }
         // 성별
         if(systemConnectDto.getSelectGb().equals("7"))
         {

             sb.append(" select co.code_so as get_time, ifnull(cnt,0) as cnt ");
             sb.append(" from code_so co left outer join ");
             sb.append(		" ( ");
             sb.append(		" select " +
							//" decode( u.sex_type,'1','1','3','1',2) sex_type " +
							" case u.sex_type when '1' then '1' when '3' then '1' else 2 end as sex_type " +
							", count(s.user_id) as cnt ,s.system_code ");
             sb.append(		" from users u, student s ");
             sb.append(		" where u.system_code = s.system_code and u.user_id = s.user_id ");
             sb.append(		" and s.system_code = ? ");
             sb.append( tmpSql );
             //sb.append(		" group by decode( u.sex_type,'1','1','3','1','2') ,s.system_code ");
			 sb.append(		" group by case u.sex_type when '1' then '1' when '3' then '1' else 2 end as sex_type, s.system_code ");
             sb.append(		" ) su ");
             sb.append(" on co.system_code = su.system_code and co.so_name =su.sex_type ");
             sb.append(" where co.system_code = ? and co.code_dae='997' ");
             sb.append(" order by co.code_so ");

         }

         // 지역별
         if(systemConnectDto.getSelectGb().equals("8"))
         {

             sb.append("  select co.so_name as get_time, co.code_so,  ifnull(cnt,0) as cnt                     ");
             sb.append("  from code_so co left outer join (                                                 ");
             sb.append("  	select s.system_code as system_code, count(s.user_id) as cnt                   ");
             sb.append("  	, case when (substring(u.post_code,1,3) = '417') then '11'/*인천(강화)*/          ");
             sb.append("  		 when (substring(u.post_code,1,3) between 100 and 199) then '10' /*서울*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 200 and 299) then '24' /*강원*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 300 and 309) then '21' /*대전*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 310 and 359) then '22' /*충남*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 360 and 399) then '23' /*충북*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 400 and 409) then '11' /*인천*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 410 and 499) then '12' /*경기*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 500 and 509) then '18' /*광주*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 510 and 559) then '19' /*전남*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 560 and 599) then '20' /*전북*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 600 and 619) then '13' /*부산*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 620 and 679) then '15' /*경남*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 680 and 689) then '14' /*울산*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 690 and 699) then '25' /*제주*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 700 and 711) then '16' /*대구*/     ");
             sb.append("  		 when (substring(u.post_code,1,3) between 712 and 799) then '17' /*경북*/     ");
             sb.append("  		 else '99' end as area                                                     ");
             sb.append("  	from student s, users u                                                        ");
             sb.append("  	where s.system_code=u.system_code and s.user_id=u.user_id  and s.system_code=? ");
             sb.append( tmpSql );
             sb.append("  	group by s.system_code, u.post_code                                            ");
             sb.append("  	)su                                                                            ");
             sb.append("  on co.system_code = su.system_code and co.code_so=su.area                         ");
             sb.append("  where co.system_code = ? and co.code_dae='401'                                    ");
             sb.append("  order by co.code_so                                                               ");

         }

 		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(systemcode);

		 retArray = new ArrayList();

		 log.debug("[getCurriSelect]" + sql.toString());

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


	protected String getStartVal(String year, String month, String day){
		String startVal = "";

		if(year.equals("all")){
	        startVal = "0000";
	    }else{
	        startVal = year;
	    }
	    if(month.equals("all")){
	        startVal = startVal + "0";
	    }else{
	        startVal = startVal + month;
	    }
	    if(day.equals("all")){
	        startVal = startVal + "00";
	    }else{
	        startVal = startVal + day;
	    }

	    return startVal;
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

	    return endVal;
	}

}