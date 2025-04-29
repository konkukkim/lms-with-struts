/*
 * Created on 2004. 11. 8.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.connstatus.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.connstatus.dto.ConnectResDTO;
import com.edutrack.connstatus.dto.CurriConnectDTO;
import com.edutrack.connstatus.dto.SystemConnectDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriConnDAO  extends AbstractDAO{
	private String connTable = "";

	/**
	 *
	 */
	public CurriConnDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 *  접속 통계를 가져온다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCurriConCnt(String systemcode, CurriConnectDTO curriConnectDto) throws DAOException{
		/*
		 * curriConnectDto.getSelectGb()
		 * 		1	시간별
		 * 		2	일별
		 * 		3	월별
		 * 		4	년도별
		 * 		5	요일별
		 * 		6	연령별
		 * 		7	성별
		 * 		8	지역별
		 */
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select cc.system_code as system_code , cc.conn_time as conn_time, cc.user_id as user_id  ");
		 sb.append(" from curri_connect cc, curri_top ct ");
		 sb.append(" where cc.system_code=ct.system_code and cc.curri_code = ct.curri_code");
		 sb.append(" and cc.system_code = '"+systemcode+"' ");
		 sb.append(getPlusWhere(curriConnectDto));
		 connTable = "( "+sb.toString()+" ) ";

		return getCurriSelect(systemcode, curriConnectDto);
	}

	private String getPlusWhere(CurriConnectDTO curriConnectDto) throws DAOException{
		String retStr = "";
		 if(!curriConnectDto.getSelectCate().equals(""))
		 	retStr +=" and ct.cate_code= '"+curriConnectDto.getSelectCate()+"' ";

		 if(!curriConnectDto.getSelectType().equals(""))
		 	retStr +=" and ct.curri_property2= '"+curriConnectDto.getSelectType()+"' ";

		 if(!curriConnectDto.getCurriCode().equals(""))
		 	retStr +=" and ct.curri_code= '"+curriConnectDto.getCurriCode()+"' ";

		return retStr;
	}

	public int getTotalCnt(String systemcode, CurriConnectDTO curriConnectDto) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
       int retVal = 0;

        String startVal = getStartVal(curriConnectDto.getStartYear(),curriConnectDto.getStartMon(),curriConnectDto.getStartDay());
	    String endVal = getEndVal(curriConnectDto.getEndYear(),curriConnectDto.getEndMon(),curriConnectDto.getEndDay());

         sb.append(" select ifnull(count(cc.user_id),0) as cnt  ");
		 sb.append(" from curri_connect cc, curri_top ct ");
		 sb.append(" where cc.system_code=ct.system_code and cc.curri_code = ct.curri_code");
		 sb.append(" and cc.system_code = ? ");
		 sb.append(getPlusWhere(curriConnectDto));
		 sql.setString(systemcode);
		 sb.append(" and cc.conn_time BETWEEN ? AND ? ");
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

	protected ArrayList getCurriSelect(String systemcode, CurriConnectDTO curriConnectDto) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 QueryStatement sql = new QueryStatement();
        ArrayList retArray = null;

        String startVal = getStartVal(curriConnectDto.getStartYear(),curriConnectDto.getStartMon(),curriConnectDto.getStartDay());
	     String endVal = getEndVal(curriConnectDto.getEndYear(),curriConnectDto.getEndMon(),curriConnectDto.getEndDay());

	     if(curriConnectDto.getSelectGb().equals("1"))
	     {
	         sb.append(" select concat(co.code_so,' 시') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join "+connTable+" s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,9,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=24 ");
			 sb.append(" group by concat(co.code_so,' 시') ");
	     }
	    if(curriConnectDto.getSelectGb().equals("2"))
	     {
	         sb.append(" select concat(co.code_so,' 일') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join "+connTable+" s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,7,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=31 ");
			 sb.append(" group by concat(co.code_so,' 일') ");
	     }
	    if(curriConnectDto.getSelectGb().equals("3"))
	     {
	         sb.append(" select concat(co.code_so,' 월') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join "+connTable+" s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=substring(s.conn_time,5,2)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so <=12 ");
			 sb.append(" group by concat(co.code_so,' 월') ");
	     }
	   	if(curriConnectDto.getSelectGb().equals("4"))
	     {
	         sb.append(" select concat('20',co.code_so,' 년') as get_time, ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join "+connTable+" s ");
			 sb.append(" on co.system_code = s.system_code and concat('20',co.code_so)=substring(s.conn_time,1,4)");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='999' and co.code_so BETWEEN '05' AND CAST(DATE_FORMAT(NOW(), '%y') AS CHAR) ");
			 sb.append(" group by concat('20',co.code_so,' 년') ");
	     }

	   if(curriConnectDto.getSelectGb().equals("5"))
	     {
	         sb.append(" select concat(co.code_so,' 요일') as get_time, co.CODE_COMMENT , ifnull(count(s.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join "+connTable+" s ");
			 sb.append(" on co.system_code = s.system_code and co.code_so=CAST(ELT(DATE_FORMAT(SUBSTRING(s.conn_time,1,8),'%w')+1,'일','월','화','수','목','금','토')AS CHAR) ");
	     	 sb.append(" and s.conn_time BETWEEN ? AND ? ");
		     sb.append(" where co.system_code = ? and co.code_dae='998'  ");
			 sb.append(" group by concat(co.code_so,' 요일')  , co.CODE_COMMENT");
			 sb.append(" order by co.CODE_COMMENT");
	     }

	  if(curriConnectDto.getSelectGb().equals("6"))
	     {
	         sb.append(" select concat(co.code_so,' 대') as get_time,  ifnull(count(su.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join ( ");
			 sb.append(" 	select s.system_code as system_code, s.conn_time as conn_time");
			 sb.append(" 	, CONCAT(SUBSTRING(CAST(DATE_FORMAT(NOW(),'%Y') AS CHAR)-SUBSTRING(u.jumin_no,1,2), 3,1),'0') AS age   ");
		     sb.append(" 	, s.user_id as user_id  ");
			 sb.append(" 	from "+connTable+" s, users u ");
			 sb.append(" 	where s.system_code=u.system_code and s.user_id=u.user_id and s.system_code=? )su");
			 sb.append(" on co.system_code = su.system_code and co.code_so=su.age");
			 sb.append(" and su.conn_time BETWEEN ? AND ? ");
			 sb.append(" where co.system_code = ? and co.code_dae='996'");
			 sb.append(" group by concat(co.code_so,' 대') ");
			 sql.setString(systemcode);
	     }

	  if(curriConnectDto.getSelectGb().equals("7"))
	     {
	         sb.append(" select co.code_so as get_time,  ifnull(count(su.user_id),0) as cnt  ");
			 sb.append(" from code_so co left outer join ( ");
			 sb.append(" 	select s.system_code as system_code, s.conn_time as conn_time");
	     	 sb.append(" 	, case when (substring(u.jumin_no,8,1) = '1' or substring(u.jumin_no,8,1) = '3')  then '남' else '여' END AS sex_type  ");
		     sb.append(" 	, s.user_id as user_id  ");
			 sb.append(" 	from "+connTable+" s, users u ");
			 sb.append(" 	where s.system_code=u.system_code and s.user_id=u.user_id and s.system_code=? )su");
			 sb.append(" on co.system_code = su.system_code and co.code_so=su.sex_type");
			 sb.append(" and su.conn_time BETWEEN ? AND ? ");
			 sb.append(" where co.system_code = ? and co.code_dae='997'");
			 sb.append(" group by co.code_so ");
			 sql.setString(systemcode);
	     }

	 if(curriConnectDto.getSelectGb().equals("8"))
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
		 sb.append(" 	from "+connTable+" s, users u ");
		 sb.append(" 	where s.system_code=u.system_code and s.user_id=u.user_id and s.system_code=? )su");
		 sb.append(" on co.system_code = su.system_code and co.code_so=su.area");
		 sb.append(" and su.conn_time BETWEEN ? AND ? ");
		 sb.append(" where co.system_code = ? and co.code_dae='401'");
		 sb.append(" group by co.so_name, co.code_so ");
		 sb.append(" order by co.code_so ");
		 sql.setString(systemcode);
    }



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
}
