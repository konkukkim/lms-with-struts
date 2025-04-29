/*
 * Created on 2004. 9. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.user.dao;

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
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.ProfInfoDTO;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserAdminDAO extends AbstractDAO {

	/**
	 *
	 */
	public UserAdminDAO()  {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 검색된 사용자 리스트를 리스트 형태로 가져온다.
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
	public ListDTO userPagingList(int curpage,String systemCode, String usertype, String daecode, String socode,String pfield, String pvalue) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append("system_code,user_id,dept_daecode,dept_socode,dept_soname,user_name,jumin_no,user_type, use_status, email, reg_date ");
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			StringBuffer where = new StringBuffer(" u.system_code=d.system_code and u.dept_daecode=d.dept_daecode and u.dept_socode=d.dept_socode and u.system_code = ? and u.user_type = ? ");

			sql.setTotalCol("u.user_id");
			sql.setCutCol("u.user_id");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(" u.system_code,u.user_id,u.dept_daecode,u.dept_socode,d.dept_soname,u.user_name,u.jumin_no,u.user_type, u.use_status, u.email, getDateText(u.reg_date,'.') as reg_date ");
			sql.setFrom(" users u, dept_socode d ");
			sql.setString(systemCode);
			sql.setString(usertype);
//			sql.setOrderby(" u.user_name asc ");
			sql.setOrderby(" u.reg_date desc ");

			if(!daecode.equals("")){
				where.append(" and u.dept_daecode = ? ");
				sql.setString(daecode);
			}

			if(!socode.equals("")){
				where.append(" and u.dept_socode = ? ");
				sql.setString(socode);
			}

			if(!pfield.equals("")){
				where.append(" and u."+pfield+" like ? ");
				sql.setString("%"+pvalue+"%");
			}

			sql.setWhere(where.toString());
			log.debug("[userPagingList]==>" + sql.toString() );

			retVal = broker.executeListQuery(sql, curpage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}

	/**
	 * 사용자의 사용 상태를 변경한다.
	 * @param systemcode
	 * @param userid
	 * @param status
	 * @param modid
	 * @return
	 * @throws DAOException
	 */
	public int changeUserStatus(String systemcode, String userid, String status, String modid) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update users set use_status=?,mod_id= ?,mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and user_id = ?");
		 sql.setSql(sb.toString());
		 sql.setString(status);
		 sql.setString(modid);
		 sql.setString(systemcode);
		 sql.setString(userid);

		 log.debug("[changeUserStatus]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 교수 정보를 가져온다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public ProfInfoDTO getProfInfo(String systemcode, String userid) throws DAOException{
		 ProfInfoDTO profInfo = null;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select user_photo,photo_path,phone_number,career,major,books,intro,reg_id,reg_date ");
		 sb.append(" from prof_info ");
		 sb.append(" where system_code = ? and user_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
         sql.setString(userid);

		 sql.setSql(sb.toString());

		 log.debug("[getProfInfo]" + sql.toString());

		 ResultSet rs = null;
/*
		 Connection conn = null;
	     DBConnectionManager pool = null;
	     StringBuffer output = new StringBuffer();
*/
		 try{
//		 	pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
	         profInfo = new ProfInfoDTO();
			 if(rs.next()){
				profInfo.setUserId(userid);
				profInfo.setUserPhoto(StringUtil.nvl(rs.getString("user_photo")));
				profInfo.setOldUserPhoto(StringUtil.nvl(rs.getString("user_photo")));
				profInfo.setPhotoPath(StringUtil.nvl(rs.getString("photo_path")));
				profInfo.setPhoneNumber(StringUtil.nvl(rs.getString("phone_number")));
				profInfo.setBooks(StringUtil.nvl(rs.getString("books")));
				profInfo.setCareer(StringUtil.nvl(rs.getString("career")));
				//profInfo.setIntro(StringUtil.nvl(rs.getString("intro")));
				profInfo.setMajor(StringUtil.nvl(rs.getString("major")));
				profInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				profInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				if(!StringUtil.nvl(rs.getString("reg_date")).equals(""))
				{
/*					Reader input = rs.getCharacterStream("intro");
			        char[] buffer = new char[1024];
			        int byteRead;
			        while((byteRead=input.read(buffer,0,1024))!=-1)
			        {
			            output.append(buffer,0,byteRead);
			        }
			        input.close();

			        profInfo.setIntro(StringUtil.nvl(output.toString()));
			        output.delete(0,output.length());
*/
			        profInfo.setIntro(StringUtil.nvl(rs.getString("intro")));
				}

			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			try{
				if(rs != null) rs.close();
/*
 			    if(conn != null){
			        conn.setAutoCommit( true );
			        pool.freeConnection(conn); // 컨넥션 해제
			    }
*/
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }

		 return profInfo;
	}


	/**
	 * 교수 정보를 생성한다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int addProfInfo(ProfInfoDTO profinfo) throws DAOException{
		/*
		System.out.println("profinfo.getUserId()		: "+profinfo.getUserId());
		System.out.println("profinfo.getUserPhoto()		: "+profinfo.getUserPhoto());
		System.out.println("profinfo.getPhotoPath()		: "+profinfo.getPhotoPath());
		System.out.println("profinfo.getPhoneNumber()	: "+profinfo.getPhoneNumber());
		System.out.println("profinfo.getCareer()		: "+profinfo.getCareer());
		System.out.println("profinfo.getMajor()			: "+profinfo.getMajor());
		System.out.println("profinfo.getBooks()			: "+profinfo.getBooks());
		System.out.println("profinfo.getIntro()			: "+profinfo.getIntro());
		*/
		int retVal 			= 	0;

		QueryStatement sql 	= 	new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" insert into prof_info(system_code, user_id, user_photo, photo_path, phone_number, career, major, books, intro, reg_id, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) ");

		sql.setSql(sb.toString());

		sql.setString(profinfo.getSystemCode());
		sql.setString(profinfo.getUserId());
		sql.setString(profinfo.getUserPhoto());
		sql.setString(profinfo.getPhotoPath());
		sql.setString(profinfo.getPhoneNumber());
		sql.setString(profinfo.getCareer());
		sql.setString(profinfo.getMajor());
		sql.setString(profinfo.getBooks());
		sql.setString(profinfo.getIntro());
		sql.setString(profinfo.getRegId());

		log.debug("[addProfInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

/*
		Connection conn = null;
		DBConnectionManager pool = null;
		ResultSet rs = null;
		QueryStatement sql2 = new QueryStatement();
		StringBuffer sb2 = new StringBuffer();

		try {
			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql,conn);

			sb2.append(" select intro from prof_info ");
			sb2.append(" where system_code = ? and user_id = ? FOR UPDATE");
			sql2.setSql(sb2.toString());
			sql2.setString(profinfo.getSystemCode());
			sql2.setString(profinfo.getUserId());
			log.debug("[UseAdd_addProfInfo]" + sql2.toString());

			rs = broker.executeQuery(sql2,conn);

			if (rs.next()) {
//	     		log.debug("______ rs "+rs.getClob(1));
				CLOB clob = (CLOB)rs.getClob("intro");
	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("intro");
	     		Writer writer = clob.getCharacterOutputStream();
	     		Reader src = new CharArrayReader(profinfo.getIntro().toCharArray());
	     		char[] buffer = new char[1024];
	     		int read = 0;

				while ((read = src.read(buffer,0,1024)) != -1) {
	     			writer.write(buffer, 0, read);
	     		}
	     		//clob.close();
	     		src.close();
	     		writer.close();
	     	}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				if (conn != null)	conn.rollback();
			} catch (SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (conn != null){
					conn.setAutoCommit( true );
					pool.freeConnection(conn); // 컨넥션 해제
				}
			} catch (Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
*/
		return retVal;
	}


	/**
	 * 교수 정보를 수정한다.
	 * @param users
	 * @return
	 * @throws DAOException
	 */
	public int editProfInfo(ProfInfoDTO prof) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update prof_info set photo_path = ?, user_photo = ?" +
		 								", phone_number = ?,career = ?" +
		 								",major = ?,books = ?" +
		 								",intro = ?,mod_id = ?" +
		 								",mod_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and user_id = ?");

		 sql.setSql(sb.toString());
		 sql.setString(prof.getPhotoPath());
		 sql.setString(prof.getUserPhoto());
		 sql.setString(prof.getPhoneNumber());
		 sql.setString(prof.getCareer());
		 sql.setString(prof.getMajor());
		 sql.setString(prof.getBooks());
		 sql.setString(prof.getIntro());
		 sql.setString(prof.getModId());
		 sql.setString(prof.getSystemCode());
		 sql.setString(prof.getUserId());

		 log.debug("[editProfInfo]" + sql.toString());
		 try{
			retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }

/*
		 Connection conn = null;
		 DBConnectionManager pool = null;
		 ResultSet rs = null;
		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 try{
		 	pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql,conn);

			 sb2.append(" select intro from prof_info ");
			 sb2.append(" where system_code = ? and user_id = ? FOR UPDATE");
			 sql2.setSql(sb2.toString());
			 sql2.setString(prof.getSystemCode());
			 sql2.setString(prof.getUserId());
			 log.debug("[UseAdd_editProfInfo]" + sql2.toString());

			 rs = broker.executeQuery(sql2,conn);
	     	if(rs.next()){
	     		log.debug("______ rs "+rs.getClob(1));
	     		CLOB clob = (CLOB)rs.getClob("intro");
	     		//CLOB clob = ((OracleResultSet)rs).getCLOB("intro");
	     		Writer writer = clob.getCharacterOutputStream();
	     		Reader src = new CharArrayReader(prof.getIntro().toCharArray());
	     		char[] buffer = new char[1024];
	     		int read = 0;
	     		while ( (read = src.read(buffer,0,1024)) != -1) {
	     			writer.write(buffer, 0, read);
	     		}
	     		//clob.close();
	     		src.close();
	     		writer.close();
	     	}
			conn.commit();

		 }catch(Exception e){
		 	e.printStackTrace();
			try {
				if(conn != null) conn.rollback();
			} catch(SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			throw new DAOException(e.getMessage());
		 } finally {
			try {
				if(conn != null){
					conn.setAutoCommit( true );
					pool.freeConnection(conn); // 컨넥션 해제
				}
			} catch(Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}
		}
*/
		 return retVal;
	}

	/**
	 * 교수 정보가 기존에 있는지를 체크한다.
	 * @param systemcode
	 * @param userid
	 * @return
	 * @throws DAOException
	 */
	public int existProfInfo(String systemcode, String userid) throws DAOException{
		 int check = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select count(user_id) cnt ");
		 sb.append(" from prof_info ");
		 sb.append(" where system_code = ? and user_id = ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
         sql.setString(userid);

		 sql.setSql(sb.toString());

		 log.debug("[existProfInfo]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
                check = rs.getInt("cnt");
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

		 return check;
	}

	/**
	 * 사용자 숫자 가져오기
	 * @param SystemCode
	 * @param userType
	 * @param deptDaeCode
	 * @param deptSoCode
	 * @return
	 * @throws DAOException
	 */
	public int getUserCount(String SystemCode, String userType, String deptDaeCode, String deptSoCode) throws DAOException {
		int totCount = 0;
		StringBuffer sb = new StringBuffer();
		String addWhere = "";

		if(!userType.equals("")) {
			addWhere += " and a.user_type = ?";
		}

		if(!deptDaeCode.equals("")) {
			addWhere += " and a.dept_daecode = ?";
		}
		if(!deptSoCode.equals("")) {
			addWhere += " and a.dept_socode = ?";
		}

		sb.append("select count(user_id) as cnt from users a where system_code= ?"+addWhere);
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!userType.equals("")) sql.setString(userType);
		if(!deptDaeCode.equals("")) sql.setString(deptDaeCode);
		if(!deptSoCode.equals("")) sql.setString(deptSoCode);

		log.debug("[getUserCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	totCount = rs.getInt("cnt");
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
		return totCount;
	}

	public ArrayList userList(String systemcode, String usertype, String daecode, String socode, String pfield, String pvalue) throws DAOException{
		UsersDTO usersDto = null;
		ArrayList userList = new ArrayList();
		ResultSet rs = null;

		try{
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			StringBuffer where = new StringBuffer(" system_code = ? and user_type = ? ");

			sql.setSelect(" user_id,user_name, email ");
			sql.setFrom(" users ");
			sql.setString(systemcode);
			sql.setString(usertype);
			sql.setOrderby(" user_name asc ");

			if(!daecode.equals("")){
				where.append(" and dept_daecode = ? ");
				sql.setString(daecode);
			}

			if(!socode.equals("")){
				where.append(" and dept_socode = ? ");
				sql.setString(socode);
			}

			if(!pfield.equals("")){
				where.append(" and "+pfield+" like ? ");
				sql.setString("%"+pvalue+"%");
			}

			sql.setWhere(where.toString());

			rs = broker.executeQuery(sql);
            while(rs.next()){
                usersDto = new UsersDTO();
			    usersDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
			    usersDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
			    usersDto.setEmail(StringUtil.nvl(rs.getString("email")));
			    userList.add(usersDto);
            }
			return userList;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}

	/**
	 * 사용자 유형관리에 등록 되어 있는지 확인
	 * @param systemcode
	 * @param userId
	 * @return
	 * @throws DAOException
	 */

	public int setUserType(String systemcode, String userId, String userType, String regId) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();

		  // user_type --> users
		 sb.append(" select count(user_id) as cnt  from users  where system_code = ?  and user_id= ?");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(userId);

		 log.debug("[setUserType]" + sql.toString());
		 RowSet rs = null;

		try{
			 rs = broker.executeQuery(sql);

			 if(rs.next()){
			     retVal = rs.getInt("cnt");
			 }

			 rs.close();

			 sb.delete(0,sb.length());
			 sql.clearParam();

			 if(retVal == 0){
			     sb.append(" insert into users (system_code, user_id, user_type, reg_id, reg_date) ");  // user_type --> users
			     sb.append(" values(?,?,?,?,?)");
	       		 sql.setSql(sb.toString());
	       		 sql.setString(systemcode);
	       		 sql.setString(userId);
	       		 sql.setString(userType);
				 sql.setString(regId);
				 sql.setString(CommonUtil.getCurrentDate());

       		log.debug("[setUserType]" + sql.toString());

       		try{
       		    retVal 		= 	broker.executeUpdate(sql);
      		 	}catch(Exception e){
      		 	    log.error(e.getMessage());
      		 	    throw new DAOException(e.getMessage());
      		 	}
			 }else{
			     sb.append(" UPDATE users ");  // user_type --> users
		     	 sb.append(" SET user_type = ?, mod_id = ? , mod_date = ? ");
				 sb.append(" WHERE SYSTEM_CODE = ?  AND USER_ID = ? ");

				 sql.setSql(sb.toString());
				 sql.setString(userType);
				 sql.setString(regId);
				 sql.setString(CommonUtil.getCurrentDate());
	       		 sql.setString(systemcode);
				 sql.setString(userId);

       		 log.debug("[setUserType]" + sql.toString());

       		try{
       		    retVal 		= 	broker.executeUpdate(sql);
      		 	}catch(Exception e){
      		 	    log.error(e.getMessage());
      		 	    throw new DAOException(e.getMessage());
      		 	}
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

		return retVal;
	}


	/**
	 * 사용자의 사용 상태를 변경한다.
	 * @param systemcode
	 * @param userid
	 * @param status
	 * @param modid
	 * @return
	 * @throws DAOException
	 */
	public int changeUserDeptCode(String systemcode, UsersDTO usersDto) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update users set dept_daecode =?,dept_socode = ?, mod_id=? , mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" where system_code = ? and user_id = ?");

		 sql.setSql(sb.toString());

		 sql.setString(usersDto.getDeptDaecode() );
		 sql.setString(usersDto.getDeptSocode()  );
		 sql.setString(usersDto.getModId());
		 sql.setString(usersDto.getSystemCode());
		 sql.setString(usersDto.getUserId() );

		 log.debug("[changeUserDeptCode]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

}