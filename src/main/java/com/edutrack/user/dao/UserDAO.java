/*
 * Created on 2004. 9. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.user.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.UserConnectDTO;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserDAO extends AbstractDAO {

	/**
	 *
	 */
	public UserDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 사용자 정보를 가져온다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public UsersDTO getUserInfo(String systemcode, String userid) throws DAOException{
		 return getUserInfo(systemcode,userid, "", "");
	}

	/**
	 * ==================================================
	 * 					사용자 정보
	 * --------------------------------------------------
	 * 프로젝트명 : 한국디지안진흥원 (KIDP)
	 * 작성일		: 2006. 06. 09
	 * ==================================================
	 *
	 * @param systemcode
	 * @param userid
	 * @param username
	 * @param regident
	 * @return
	 * @throws DAOException
	 */
	public UsersDTO getUserInfo(String systemcode, String userid, String username, String regident) throws DAOException{
		UsersDTO userInfo = null;

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT " +
					"u.system_code, u.user_id, u.dept_daecode, u.dept_socode" +
					",d.dept_soname, u.user_name, u.jumin_no, u.passwd" +
					",u.user_type, u.use_status, u.phone_home, u.phone_mobile" +
					",u.post_code, u.address, u.email, u.sex_type" +
					", u.job_code, u.reg_date,  u.user_name_eng, u.school" +
					", u.major, u.career, u.comp_name, u.recv_mail" +
					", u.recv_sms, u.rfile_name, u.sfile_name, u.file_path" +
					", u.file_size, u.school_year, u.comp_phone, u.comp_post_code" +
					", u.comp_address ");
		sb.append(	" FROM users u, dept_socode d");
		sb.append(	" WHERE u.system_code = d.system_code and u.dept_daecode = d.dept_daecode " +
					" and u.dept_socode = d.dept_socode and u.system_code = ? ");

		QueryStatement sql = new QueryStatement();

		sql.setString(systemcode);

		if(!userid.equals("")){
			sb.append(" and u.user_id = ? ");
		 	sql.setString(userid);
		}

		if(!username.equals("")){
			sb.append(" and u.user_name = ? ");
		 	sql.setString(username);
		}

		if(!regident.equals("")){
			sb.append(" and u.jumin_no = ? ");
		 	sql.setString(regident);
		}

		sql.setSql(sb.toString());

		log.debug("[getUserInfo]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
	        userInfo = new UsersDTO();

			if (rs.next()) {
				userInfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				userInfo.setUserId(StringUtil.nvl(rs.getString("user_id")));
				userInfo.setDeptDaecode(StringUtil.nvl(rs.getString("dept_daecode")));
				userInfo.setDeptSocode(StringUtil.nvl(rs.getString("dept_socode")));
				userInfo.setDeptSoname(StringUtil.nvl(rs.getString("dept_soname")));
				userInfo.setUserName(StringUtil.nvl(rs.getString("user_name")));
				userInfo.setJuminNo(StringUtil.nvl(rs.getString("jumin_no")));
				userInfo.setPasswd(StringUtil.nvl(rs.getString("passwd")));
				userInfo.setUserType(StringUtil.nvl(rs.getString("user_type")));
				userInfo.setUseStatus(StringUtil.nvl(rs.getString("use_status")));
				userInfo.setPhoneHome(StringUtil.nvl(rs.getString("phone_home")));
				userInfo.setPhoneMobile(StringUtil.nvl(rs.getString("phone_mobile")));
				userInfo.setPostCode (StringUtil.nvl(rs.getString("post_code")));
				userInfo.setAddress  (StringUtil.nvl(rs.getString("address")));
				userInfo.setEmail    (StringUtil.nvl(rs.getString("email")));
				userInfo.setSexType  (StringUtil.nvl(rs.getString("sex_type")));
				userInfo.setJobCode  (StringUtil.nvl(rs.getString("job_code")));
				userInfo.setRegDate  (StringUtil.nvl(rs.getString("reg_date")));
				userInfo.setUserNameEng(StringUtil.nvl(rs.getString("user_name_eng")));
				userInfo.setSchool(StringUtil.nvl(rs.getString("school")));
				userInfo.setMajor(StringUtil.nvl(rs.getString("major")));
				userInfo.setCareer(StringUtil.nvl(rs.getString("career")));
				
				userInfo.setCompName(StringUtil.nvl(rs.getString("comp_name")));
				userInfo.setCompPhone(StringUtil.nvl(rs.getString("comp_phone")));
				userInfo.setCompPostCode(StringUtil.nvl(rs.getString("comp_post_code")));
				userInfo.setCompAddress(StringUtil.nvl(rs.getString("comp_address")));
				
				userInfo.setRecvMail(StringUtil.nvl(rs.getString("recv_mail")));
				userInfo.setRecvSms(StringUtil.nvl(rs.getString("recv_sms")));
				userInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				userInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				userInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				userInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
				userInfo.setSchoolYear(StringUtil.nvl(rs.getString("school_year")));
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
		return userInfo;
	}



	/**
	 * 사용자 존재여부를 체크한다.
	 * @param systemcode
	 * @param userid
	 * @return
	 */
	public int userIdCheck(String systemcode, String userid) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(*) cnt  ");
		 sb.append(" from users ");
		 sb.append(" where system_code = ? and user_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(userid);

		 log.debug("[userIdCheck]" + sql.toString());

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

	/**
	 * 주민번호의 존재여부 확인
	 * @param systemcode
	 * @param juminno
	 * @return
	 * @throws DAOException
	 */
	public int juminNoYn(String systemcode, String juminno) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(*) cnt  ");
		 sb.append(" from users ");
		 sb.append(" where system_code = ? and jumin_no = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(juminno);

		 log.debug("[juminNoYn]" + sql.toString());

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


	/**
	 * ==================================================
	 * 					사용자 등록
	 * --------------------------------------------------
	 * 프로젝트명 : 한국디지안진흥원 (KIDP)
	 * 작성일		: 2006. 06. 08
	 * ==================================================
	 *
	 * @param users
	 * @return
	 * @throws DAOException
	 */
	public int addUsers(UsersDTO users) throws DAOException{
		int retVal 			= 0;
		QueryStatement sql 	= new QueryStatement();
		StringBuffer sb 	= new StringBuffer();

		sb.append(" INSERT INTO users "
				+ " (system_code, user_id, dept_daecode, dept_socode, user_name, "
				+ " USER_NAME_ENG, JUMIN_NO, PASSWD, USER_TYPE, USE_STATUS, "
				+ " PHONE_HOME, PHONE_MOBILE, POST_CODE, ADDRESS, EMAIL, "
				+ " SEX_TYPE, JOB_CODE, COMP_TYPE, COMP_NAME, COMP_NO, "
				+ " COMP_POST_CODE, COMP_ADDRESS, COMP_PHONE, COMP_GRADE, CAREER, "
				+ " MAJOR, SCHOOL, RECV_MAIL, RECV_SMS, REG_ID, "
				+ " REG_DATE, rfile_name, sfile_name, file_path, file_size, school_year) ");
		sb.append(" VALUES(?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, ?, "
				+ " CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?, ?, ?, ?, ? )");

		sql.setSql(sb.toString());

		sql.setString(users.getSystemCode());
		sql.setString(users.getUserId());
		sql.setString(users.getDeptDaecode());
		sql.setString(users.getDeptSocode());
		sql.setString(users.getUserName());

		sql.setString(users.getUserNameEng());
		sql.setString(users.getJuminNo());
		sql.setString(users.getPasswd());
		sql.setString(users.getUserType());
		sql.setString(users.getUseStatus());

		sql.setString(users.getPhoneHome());
		sql.setString(users.getPhoneMobile());
		sql.setString(users.getPostCode());
		sql.setString(users.getAddress());
		sql.setString(users.getEmail());

		sql.setString(users.getSexType());
		sql.setString(users.getJobCode());
		sql.setString(users.getCompType());
		sql.setString(users.getCompName());
		sql.setString(users.getCompNo());

		sql.setString(users.getCompPostCode());
		sql.setString(users.getCompAddress());
		sql.setString(users.getCompPhone());
		sql.setString(users.getCompGrade());
		sql.setString(users.getCareer());

		sql.setString(users.getMajor());
		sql.setString(users.getSchool());
		sql.setString(users.getRecvMail());
		sql.setString(users.getRecvSms());
	 	sql.setString(users.getRegId());

		sql.setString(users.getRfileName());
		sql.setString(users.getSfileName());
		sql.setString(users.getFilePath());
		sql.setString(users.getFileSize());

		sql.setString(users.getSchoolYear());
//		sql.setString(users.getProfCareer());
//		sql.setString(users.getProfSchool());

		
		log.debug("[addUsers]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 사용자 접속 정보를 생성해준다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int addUserConnect(String systemcode, String userid, String sessionid, String remoteip) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into user_connect(system_code,user_id,first_con,last_con,session_id, remote_ip,connect_no,reg_id,reg_date) ");
		 sb.append(" values(?,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 sb.append(" ?,?,1,?,CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");
		 sql.setSql(sb.toString());

		 sql.setString(systemcode);
		 sql.setString(userid);
		 sql.setString(sessionid);
		 sql.setString(remoteip);
		 sql.setString(userid);

		 log.debug("[addUserConnect]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 사용자 접속 정보를 수정해준다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int editUserConnect(String systemcode, String userid, String sessionid, String remoteip, String mode) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update user_connect ");
		 sb.append(" set ");
		 if(!mode.equals("C")){
		 	sb.append(" first_con = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		 	sb.append(" connect_no = connect_no + 1, ");
		 }

		 if(mode.equals("O")) sb.append(" last_con = '19500101010101',session_id = ?, remote_ip = ?  ");
		 else  sb.append(" last_con = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),session_id = ?, remote_ip = ?  ");

		 sb.append(" where system_code = ? and user_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(sessionid);
		 sql.setString(remoteip);
		 sql.setString(systemcode);
		 sql.setString(userid);

		 log.debug("[editUserConnect]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 종료 시간을 업데이트 해준다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
//	public int lastUserConnect(String systemcode, String userid) throws DAOException{
//		 int retVal 			= 	0;
//		 QueryStatement sql 	= 	new QueryStatement();
//
//		 StringBuffer sb = new StringBuffer();
//		 sb.append(" update user_connect ");
//		 sb.append(" set last_con = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
//		 sb.append(" where system_code = ? and user_id = ? ");
//		 sql.setSql(sb.toString());
//		 sql.setString(systemcode);
//		 sql.setString(userid);
//
//		 log.debug("[lastUserConnect]" + sql.toString());
//		 try{
//		     retVal 		= 	broker.executeUpdate(sql);
//		 }catch(Exception e){
//			  log.error(e.getMessage());
//			 throw new DAOException(e.getMessage());
//		 }
//		 return retVal;
//	}

	/**
	 * 사용자 접속 정보에 대한 존재여부
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public UserConnectDTO getUserConnect(String systemcode, String userid) throws DAOException{
		 UserConnectDTO conInfo = new UserConnectDTO();
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select user_id, session_id, remote_ip, connect_no, ");
		 //sb.append(" round((sysdate-to_date(last_con,'yyyymmddhh24miss'))*24*60,0) as last_con ");
		 sb.append(" CAST(ROUND( (( NOW() - DATE_FORMAT(last_con,'%Y%m%d%H%i%s') )/600), 0) AS SIGNED) AS last_con ");
		 sb.append(" from user_connect ");
		 sb.append(" where system_code = ? and user_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(userid);

		 log.debug("[getUserConnect]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 conInfo = new UserConnectDTO();
			 if(rs.next()){
			 	conInfo.setUserId(StringUtil.nvl(rs.getString("user_id")));
			 	conInfo.setConnectNo(rs.getInt("connect_no"));
			 	conInfo.setSessionId(StringUtil.nvl(rs.getString("session_id")));
			 	conInfo.setRemoteIp(StringUtil.nvl(rs.getString("remote_ip")));
			 	conInfo.setLastCon(rs.getLong("last_con"));
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

		 return conInfo;
	}


	/**
	 * ==================================================
	 * 					사용자 정보 수정
	 * --------------------------------------------------
	 * 프로젝트명 : 한국디지안진흥원 (KIDP)
	 * 작성일		: 2006. 06. 09
	 * ==================================================
	 *
	 * @param users
	 * @return
	 * @throws DAOException
	 */
	public int editUsers(UsersDTO users) throws DAOException{
		int retVal 			= 	0;
		QueryStatement sql 	= 	new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" UPDATE users SET user_id = ?, dept_daecode = ?" +
									", dept_socode = ?, user_name = ?" +
									", jumin_no = ?, passwd = ?" +
									", user_type = ?, use_status = ?, ");
		sb.append(					" phone_home = ?, phone_mobile = ?" +
									", post_code= ?, address = ?" +
									",  email = ?, job_code = ?" +
									", sex_type = ?, mod_id = ? " +
									", mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ");
		sb.append(					" recv_mail = ?, recv_sms = ?" +
									", school =?, major = ?" +
									", career = ?, comp_name = ?, ");
		sb.append(					" rfile_name = ?, sfile_name = ?" +
									", file_path = ?, file_size = ?" +
									", comp_phone = ?, comp_post_code = ?" +
									", comp_address = ? , school_year=?");
		sb.append(" where system_code = ? and user_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(users.getUserId());
		sql.setString(users.getDeptDaecode());
		sql.setString(users.getDeptSocode());
		sql.setString(users.getUserName());
		sql.setString(users.getJuminNo());
		sql.setString(users.getPasswd());
		sql.setString(users.getUserType());
		sql.setString(users.getUseStatus());
		sql.setString(users.getPhoneHome());
		sql.setString(users.getPhoneMobile());
		sql.setString(users.getPostCode());
		sql.setString(users.getAddress());
		sql.setString(users.getEmail());
		sql.setString(users.getJobCode());
		sql.setString(users.getSexType());
		sql.setString(users.getModId());
		sql.setString(users.getRecvMail());
		sql.setString(users.getRecvSms());
		sql.setString(users.getSchool());
		sql.setString(users.getMajor());
		sql.setString(users.getCareer());
		sql.setString(users.getCompName());
		sql.setString(users.getRfileName());
		sql.setString(users.getSfileName());
		sql.setString(users.getFilePath());
		sql.setString(users.getFileSize());
		
		sql.setString(users.getCompPhone());
		sql.setString(users.getCompPostCode());
		sql.setString(users.getCompAddress());

		sql.setString(users.getSchoolYear());

		//where
		sql.setString(users.getSystemCode());
		sql.setString(users.getUserId());

		log.debug("[editUsers]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


    /**
     * 사용자 정보를 널로 수정한다.(회원탈퇴)
     * @param users
     * @return
     * @throws DAOException
     */
	public int dropUsers(UsersDTO users) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update users set passwd = ' ',use_status='D', jumin_no = ? , ");
		 sb.append(" phone_home= '',phone_mobile= '',address= '',email= '',mod_id= ?,mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and user_id = ?");

		 sql.setSql(sb.toString());
		 sql.setString(users.getJuminNo());
		 sql.setString(users.getModId());
		 sql.setString(users.getSystemCode());
		 sql.setString(users.getUserId());



		 log.debug("[dropUsersssss]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}


	/**
     * 검색된 사용자를 리스트 컬랙션으로 가져온다.
     * @param curpage
     * @param systemcode
     * @param usertype
     * @param daecode
     * @param socode
     * @param pfield
     * @param pvalue
     * @return
     * @throws DAOException
     */
	public ArrayList userList(String systemcode, String usertype, String daecode, String socode,String pfield, String pvalue) throws DAOException{
		ArrayList userList = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();

			sb.append(" select " +
							" user_id, passwd, dept_daecode, dept_socode" +
							", user_name, jumin_no, use_status, phone_home" +
							", phone_mobile, post_code, address, email" +
							", comp_name, comp_post_code, comp_address, comp_phone" +
							", user_type, reg_date ");
			sb.append(" from users ");
			sb.append(" where system_code = ? and user_type = ? ");
			sql.setString(systemcode);
			sql.setString(usertype);

			if(!daecode.equals("")){
				sb.append(" and dept_daecode = ? ");
				sql.setString(daecode);
			}

			if(!socode.equals("")){
				sb.append(" and dept_socode = ? ");
				sql.setString(socode);
			}

			if(!pfield.equals("") && !pvalue.equals("")){
				sb.append(" and "+pfield+" like ? ");
				sql.setString("%"+pvalue+"%");
				sb.append(" order by " + pfield);
			}else sb.append(" order by user_name");

			sql.setSql(sb.toString());

			 rs = broker.executeQuery(sql);
			 UsersDTO ui = null;
			 userList = new ArrayList();
			 while(rs.next()){
				ui = new UsersDTO();

				ui.setUserId(StringUtil.nvl(rs.getString("user_id")));
				ui.setPasswd(StringUtil.nvl(rs.getString("passwd")));
				ui.setDeptDaecode(StringUtil.nvl(rs.getString("dept_daecode")));
				ui.setDeptSocode(StringUtil.nvl(rs.getString("dept_socode")));
				ui.setUserName(StringUtil.nvl(rs.getString("user_name")));
				ui.setJuminNo(StringUtil.nvl(rs.getString("jumin_no")));
				ui.setUserType(StringUtil.nvl(rs.getString("user_type")));
				ui.setUseStatus(StringUtil.nvl(rs.getString("use_status")));
				ui.setPhoneHome(StringUtil.nvl(rs.getString("phone_home")));
				ui.setPhoneMobile(StringUtil.nvl(rs.getString("phone_mobile")));
				ui.setPostCode(StringUtil.nvl(rs.getString("post_code")));
				ui.setAddress(StringUtil.nvl(rs.getString("address")));
				ui.setCompName(StringUtil.nvl(rs.getString("comp_name")));
				ui.setCompPhone(StringUtil.nvl(rs.getString("comp_phone")));
				ui.setCompPostCode(StringUtil.nvl(rs.getString("comp_post_code")));
				ui.setCompAddress(StringUtil.nvl(rs.getString("comp_address")));
				ui.setEmail(StringUtil.nvl(rs.getString("email")));
				ui.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				userList.add(ui);
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

	     return userList;
	}
	
	/**
	 * 사용자 정보 엑셀다운로드 용
	 * @param systemcode
	 * @param usertype
	 * @param daecode
	 * @param socode
	 * @param pfield
	 * @param pvalue
	 * @return
	 * @throws DAOException
	 */
	public RowSet userExcelList(String systemcode, String usertype, String daecode, String socode, String pfield, String pvalue) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT" +
						" user_id, passwd, dept_daecode, dept_socode" +
						", user_name, jumin_no, use_status, phone_home" +
						", phone_mobile, post_code, address, email" +
						", comp_name, comp_post_code, comp_address, comp_phone ");
		sb.append(" FROM users ");
		sb.append(" WHERE system_code = ? ");
		sql.setString(systemcode);
		
		if(!usertype.equals("")) {
			sb.append(" AND user_type = ? ");
			sql.setString(usertype);
		}
		if(!daecode.equals("")) {
			sb.append(" AND dept_daecode = ? ");
			sql.setString(daecode);
		}
		if(!socode.equals("")) {
			sb.append(" AND dept_socode = ? ");
			sql.setString(socode);
		}
		if(!pfield.equals("") && !pvalue.equals("")){
			sb.append(" and "+pfield+" like ? ");
			sql.setString("%"+pvalue+"%");
			sb.append(" order by " + pfield);
		} 
		else sb.append(" order by user_name");
		
		sql.setSql(sb.toString());
		
		RowSet rs = null;
		try{
			rs	=	broker.executeQuery(sql);
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
		return rs;
	}

	/**
	 * 현재 접속중인 사용자를 구한다.
	 * @param systemcode
	 * @param checktime
	 * @return
	 * @throws DAOException
	 */
	public int getUseUserCnt(String systemcode, int checktime) throws DAOException{
		 int userCnt = 0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(user_id) as concnt ");
		 sb.append(" from user_connect ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and round((sysdate-to_date(last_con,'yyyymmddhh24miss'))*24*60,0) < ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setInteger(checktime);

		 log.debug("[getUseUserCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	userCnt = rs.getInt("concnt");
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

		 return userCnt;
	}

	/**
	 * 사용자를 검색한다.
	 * @param systemcode
	 * @param op
	 * @param search
	 * @return
	 * @throws DAOException
	 */
	public ArrayList userSearchList(String systemcode, String op, String search) throws DAOException{
		ArrayList userList = null;

		try{

		    userList = userSearchList(systemcode, op, search, null, null, null);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return userList;
	}


	/**
	 * 사용자를 검색한다... 그룹별루 사용자를 가져올경우...
	 * @param systemcode
	 * @param op
	 * @param search
	 * @param pDeptDaeCode
	 * @param pDeptSoCode
	 * @param pUserType
	 * @return
	 * @throws DAOException
	 */
	public ArrayList userSearchList(String systemcode, String op, String search, String pDeptDaeCode, String pDeptSoCode, String pUserType) throws DAOException{
		ArrayList userList = null;
	    UsersDTO ui = null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select user_id, user_name,user_type , dept_daecode, dept_socode  ");
		sb.append(" from users ");
		sb.append(" where system_code = ? ");
		if(op.equals("user_id")){
			sb.append(" and user_id like ? ");
		}else if(op.equals("user_name")){
			sb.append(" and user_name like ? ");
		}

		if( pDeptDaeCode!=null ) sb.append(" and dept_daecode = ? ");
		if( pDeptSoCode !=null ) sb.append(" and dept_socode = ? ");
		if( pUserType   !=null ) sb.append(" and user_type = ?  ");

		sb.append(" order by user_name");

		sql.setSql(sb.toString());
		sql.setString(systemcode);
		if(!op.equals("")){
			sql.setString("%"+search+"%");
		}

		if( pDeptDaeCode!=null ) sql.setString( pDeptDaeCode  );
		if( pDeptSoCode !=null ) sql.setString( pDeptSoCode );
		if( pUserType   !=null ) sql.setString( pUserType );

		log.debug("[userSearchList]" + sql.toString());
		RowSet rs = null;

		try{

			 rs = broker.executeQuery(sql);
			 userList = new ArrayList();

			 while(rs.next()){
				ui = new UsersDTO();
				ui.setUserId(StringUtil.nvl(rs.getString("user_id")));
				ui.setUserName(StringUtil.nvl(rs.getString("user_name")));
				ui.setUserType(StringUtil.nvl(rs.getString("user_type")));
				ui.setDeptDaecode(StringUtil.nvl(rs.getString("dept_daecode")));
				ui.setDeptSocode(StringUtil.nvl(rs.getString("dept_socode")));

				userList.add(ui);
			 }

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return userList;
	}
	/**
	 * 나이를 반환
	 * @param systemcode
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public String getUserAge(String systemcode, String userId) throws DAOException{
		log.debug("+++++++++++++++++ getUserAge start");
		 String  age = "";
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select SUBSTRING(CAST(DATE_FORMAT(NOW(), '%Y') AS CHAR) - SUBSTRING(jumin_no,1,2), 3,2) as age ");
		 sb.append(" from users ");
		 sb.append(" where system_code = ? and user_id = ?");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(userId);

		 log.debug("[getUserAge]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	age = rs.getString("age");
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
		 log.debug("+++++++++++++++++ getUserAge end");

		 return age;
	}

	/**
     * 시스템 접속 정보(통계용)를 입력한다.
     * @param users
     * @return
     * @throws DAOException
     */
	public int getUserConnectCnt(String systemcode, String userid, String remoteIp) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 try{
			     sb.append(" insert into system_connect (system_code, conn_time, user_id, remote_ip) ");
			     sb.append(" values (?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR),?,?)");
	       		 sql.setSql(sb.toString());
	       		 sql.setString(systemcode);
				 sql.setString(userid);
				 sql.setString(remoteIp);

        		log.debug("[getUserConnectCnt]" + sql.toString());

        		try{
        		    retVal 		= 	broker.executeUpdate(sql);
       		 	}catch(Exception e){
       		 	    log.error(e.getMessage());
       		 	    throw new DAOException(e.getMessage());
       		 	}

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}


	// 파일삭제
	public int delUserFile(String systemCode, String userId, String FileNo) throws DAOException{

		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" UPDATE users SET ");
		sb.append(" rfile_name = '', sfile_name = '', file_path = '', file_size = '' ");
		sb.append(" WHERE system_code = ? and user_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(userId);

		log.debug("==========> delAspContentsFile : "+sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

}