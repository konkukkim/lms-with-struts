/*
 * Created on 2004. 9. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.dto.CodeDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.PostCodeDTO;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonDAO extends AbstractDAO {

	/**
	 *
	 */
	public CommonDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 현재 Date(YYYYMMDDHHMISS)를 가져온다.
	 * @return
	 * @throws DAOException
	 */
	public String getCurrentDate() throws DAOException{
		 String curDate = "";
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 //sb.append("select c o n v e r t(varchar(8), getdate(), 112) +replace(convert(varchar(8), getdate(), 108),':','') as curdate");
		 sb.append(" select CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) as curdate  from dual");
		 sql.setSql(sb.toString());

		 log.debug("[getCurrentDate]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				curDate = StringUtil.nvl(rs.getString("curdate"));
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

		 return curDate;
	}
	

	/**
	 * 타입별 사용자 리스트를 가져온다.
	 * @param systemcode
	 * @param userType
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getUserList(String systemcode, String userType) throws DAOException{
	     ArrayList codeList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select user_id,user_name ");
		 sb.append(" from users ");
		 sb.append(" where system_code = ? and user_type=?");
		 sql.setString(systemcode);
		 sql.setString(userType);

		 sb.append(" order by user_name ");
		 sql.setSql(sb.toString());

		 log.debug("[getUserList]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;
			 codeList = new ArrayList();
			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("user_id")));
				code.setName(StringUtil.nvl(rs.getString("user_name")));

				codeList.add(code);
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

		 return codeList;
	}

	/**
	 * 타입별 사용자 리스트를 가져온다.
	 * @param systemcode
	 * @param userType
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getUserList(String systemcode, String userType, String userName) throws DAOException{
	     ArrayList codeList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select user_id,user_name ");
		 sb.append(" from users ");
		 sb.append(" where system_code = ? and user_type=? and user_name like '"+userName+"%'");
		 sql.setString(systemcode);
		 sql.setString(userType);

		 sb.append(" order by user_name ");
		 sql.setSql(sb.toString());

		 log.debug("[getUserList]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;
			 codeList = new ArrayList();
			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("user_id")));
				code.setName(StringUtil.nvl(rs.getString("user_name")));

				codeList.add(code);
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

		 return codeList;
	}


	/**
	 * 코드 리스트를 가져온다.
	 * @param systemcode
	 * @param daecode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getSoCode(String systemcode, String daecode) throws DAOException{
	     ArrayList codeList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select code_so, so_name ");
		 sb.append(" from code_so ");
		 sb.append(" where system_code = ? ");
		 sb.append("   and use_yn = 'Y' ");
		 sql.setString(systemcode);

		 if(daecode != null && !daecode.equals("")){
		 	sb.append(" and code_dae = ? ");
		 	sql.setString(daecode);
		 }
		 sb.append(" order by code_so asc ");
		 sql.setSql(sb.toString());

		 log.debug("[getSoCode]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;
			 codeList = new ArrayList();
			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("code_so")));
				code.setName(StringUtil.nvl(rs.getString("so_name")));

				codeList.add(code);
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

		 return codeList;
	}
	/**
	 * 지정 소코드의 값(so_name)을 가져온다.
	 * @param systemcode
	 * @param daecode
	 * @param socode
	 * @return
	 * @throws DAOException
	 */
	public String getSoName(String systemcode, String daecode,String socode) throws DAOException{
	     String soName = "";
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select  so_name ");
		 sb.append(" from code_so ");
		 sb.append(" where system_code = ? and code_dae = ? and code_so = ? ");
		 //sb.append("   and use_yn = 'Y' ");
		 sql.setString(systemcode);
	 	 sql.setString(daecode);
	 	sql.setString(socode);
		 sql.setSql(sb.toString());

		 log.debug("[getSoName]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 rs.next();
			 soName = 	StringUtil.nvl(rs.getString("so_name"));
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

		 return soName;
	}

	/**
	 * 소속 대코드를 가져온다.
	 * @author HerSunJa
	 * @param systemcode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getDeptDaeCode(String systemcode) throws DAOException{

	    ArrayList codeList = null;
	    String flag ="1";

	    codeList = this.getDeptDaeCode(systemcode, flag);

	    return codeList;
	}


	/**
	 * 소속 대코드를 가져온다.
	 * @author HerSunJa
	 * @param  systemcode
	 * @param  flag  : (String) 2 -소속기관, (String) 1 -전체 대코드
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getDeptDaeCode(String systemcode, String flag) throws DAOException{
	     ArrayList codeList = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select dept_daecode, dept_daename ");
		 sb.append(" from dept_daecode ");
		 sb.append(" where system_code = ? and use_yn = 'Y' ");

		 if( ("2").equals(flag) )	// 단체관리자일경우 소속기관을 가져올 시
			 sb.append("  and dept_daecode in ('201','202','203','204','299') ");
		     //sb.append("  and dept_daecode between '201' and '299' ");

		 sb.append(" order by dept_daecode ");
		 sql.setString(systemcode);

		 sql.setSql(sb.toString());

		 log.debug("[getDeptDaeCode]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;
			 codeList = new ArrayList();
			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("dept_daecode")));
				code.setName(StringUtil.nvl(rs.getString("dept_daename")));

				codeList.add(code);
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

		 return codeList;
	}

	/**
	 * 소속코드 소코드를 가져온다.
	 * @param systemcode
	 * @param daecode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getDeptSoCode(String systemcode, String daecode) throws DAOException{
	     ArrayList codeList = new ArrayList();

		 if(daecode.equals("")) return codeList;

	     QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select dept_socode, dept_soname ");
		 sb.append(" from dept_socode ");
		 sb.append(" where system_code = ? and dept_daecode = ? and use_yn = 'Y' ");
		 sql.setString(systemcode);
		 sql.setString(daecode);
		 sb.append(" order by dept_socode ");
		 sql.setSql(sb.toString());

		 log.debug("[getDeptSoCode]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;

			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("dept_socode")));
				code.setName(StringUtil.nvl(rs.getString("dept_soname")));

				codeList.add(code);
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

		 return codeList;
	}

	/**
	 * 우편번호를 검색한다.
	 * @param dongname
	 * @return
	 * @throws DAOException
	 */
	public ArrayList  searchPost(String dongname) throws DAOException{
	     ArrayList postList = new ArrayList();
   	     QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT   post_code, concat(sido,'시',' ',gugun,' ',dong )as addr, concat( sido , '시 ' , gugun , ' ' , dong , ' ' , ifnull(bunji,'')) AS fulladdr ");
		 sb.append(" FROM     post_code ");
		 sb.append(" WHERE   (dong LIKE ? ) ");
		 sb.append(" ORDER BY dong ");
		 sql.setSql(sb.toString());
		 sql.setString("%"+dongname+"%");

		 log.debug("[searchPost]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 PostCodeDTO postCode = null;
			 while(rs.next()){
			     postCode = new PostCodeDTO();
			     //postCode.setPostId(rs.getInt("post_id"));
			     postCode.setPostCode(StringUtil.nvl(rs.getString("post_code")));
			     postCode.setAddr(StringUtil.nvl(rs.getString("addr")));
			     postCode.setFullAddr(StringUtil.nvl(rs.getString("fulladdr")));

			     postList.add(postCode);
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

		 return postList;
	}
	public ArrayList getCurriCourse(String SystemCode, String CurriCode, int CurriYear,int CurriTerm, String ProfId) throws DAOException{
	    ArrayList codeList = new ArrayList();
		if(CurriCode.equals("")) return codeList;

		StringBuffer sb = new StringBuffer();
		sb.append("select s.course_id as course_id, c.course_name as course_name ");
		sb.append(" from curri_sub_course s, course c ");
		sb.append(" where s.system_code = c.system_code and s.course_id = c.course_id ");
		sb.append(" and s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term = ? ");
		if(!ProfId.equals(""))
			sb.append("and s.prof_id = ? ");
		sb.append(" order by s.reg_date ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		if(!ProfId.equals(""))
			sql.setString(ProfId);
		log.debug("[getCurriCourse]" + sql.toString());
		 RowSet rs = null;
		 try{

			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;
			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("course_id")));
				code.setName(StringUtil.nvl(rs.getString("course_name")));
				codeList.add(code);
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
		return codeList;
	}


	public String getCourseName(String systemcode, String courseid){
		 String courseName = "";
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT   course_name ");
		 sb.append(" FROM      course ");
		 sb.append(" WHERE   system_code = ? AND course_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(courseid);

		 log.debug("[getCourseName]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	courseName = StringUtil.nvl(rs.getString("course_name"));
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
			 }
		 }

		 return courseName;
	}

	public String getCurriSubName(String systemcode, String curriCode, int curriYear, int curriTerm){
		 String curriSubName = "";
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(	" SELECT   curri_name ");
		 sb.append(	" FROM     curri_sub ");
		 sb.append(	" WHERE   system_code = ? AND curri_code = ?" +
		 			" AND curri_year = ? AND curri_term = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriCode);
		 sql.setInteger(curriYear);
		 sql.setInteger(curriTerm);

		 log.debug("[getCurriSubName]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 curriSubName = StringUtil.nvl(rs.getString("curri_name"));
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
			 }
		 }

		 return curriSubName;
	}

	public static void main(String[] args) throws Exception{
		CommonDAO cd = new CommonDAO();
		String a = cd.getCurrentDate();
		int b = 1;
	}

	public String getEmail(String systemCode, String userId){
	     String email = "";
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT   email ");
		 sb.append(" FROM      users ");
		 sb.append(" WHERE   system_code = ? AND user_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(userId);

		 log.debug("[getEmail]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	email = StringUtil.nvl(rs.getString("email"));
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
			 }
		 }

		 return email;

	}

	/**
	 * 현재 진행중인 개설과정 리스트를 가져온다. enroll_start ~ enroll_end or enroll_start ~ service_end
	 * @param SystemCode
	 * @param Property1
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getCurrentCurriList(String SystemCode, String CateCode, String Property1) throws DAOException {
		ArrayList codeList = new ArrayList();
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		if(!CateCode.equals("")) addWhere += " and b.cate_code = ?";
		if(!Property1.equals("")) addWhere += " and b.curri_property1 = ?";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		sb.append("select concat(a.curri_code,'|',cast(a.curri_year AS CHAR),'|',cast(a.curri_term AS CHAR)) as sel_curri_code ");
		//sb.append(" concat(b.curri_name,' [',cast(a.curri_year AS CHAR),'년 ',cast(a.curri_term AS CHAR),'기]') as sel_curri_name"	);
		sb.append(", a.curri_name as sel_curri_name  ");
		sb.append(" from curri_sub a, curri_top b");
		sb.append(" where a.system_code = b.system_code and a.curri_code=b.curri_code and a.system_code=? ");
		sb.append(" and ((a.enroll_start <= "+currentDate+" and a.service_end >= "+currentDate+") or b.curri_property2='O') ");
		if(!addWhere.equals("")) sb.append(addWhere);
		
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!CateCode.equals("")) sql.setString(CateCode);
		if(!Property1.equals("")) sql.setString(Property1);
		log.debug("[getCurrentCurriList]" + sql.toString());
		RowSet rs = null;
		try{

			 rs = broker.executeQuery(sql);
			 CodeDTO code = null;
			 while(rs.next()){
				code = new CodeDTO();
				code.setCode(StringUtil.nvl(rs.getString("sel_curri_code")));
				code.setName(StringUtil.nvl(rs.getString("sel_curri_name")));
				codeList.add(code);
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
		return codeList;
	}
	/**
	 * 수료처리 진행된 학생 수를 가져온다.
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 */
	public int getCompleteStudentCnt(String systemCode, String curriCode,int curriYear, int curriTerm){
	     int cnt = 0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" SELECT   ifnull(count(user_id),0) as cnt ");
		 sb.append(" FROM      student ");
		 sb.append(" WHERE   system_code = ? AND curri_code = ? AND curri_year = ? AND curri_term = ? AND enroll_status in ('C','F') ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(curriCode);
		 sql.setInteger(curriYear);
		 sql.setInteger(curriTerm);

		 log.debug("[getCompleteStudentCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	cnt = rs.getInt("cnt");
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
			 }
		 }

		 return cnt;

	}


	public String getSoCodeName(String systemcode, String daecode, String socode) throws DAOException {
		String soName = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select so_name ");
		sb.append(" from code_so ");
		sb.append(" where system_code = ? ");
		sb.append("   and use_yn = 'Y' ");
		sql.setString(systemcode);
		if ( !daecode.equals("")) {
			sb.append(" and code_dae = ? ");
			sql.setString(daecode);
		}
		if ( !socode.equals("")) {
			sb.append(" and code_so = ? ");
			sql.setString(socode);
		}
		sql.setSql(sb.toString());

		log.debug("[getSoName]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if(rs.next()){
				soName = StringUtil.nvl(rs.getString("so_name"));
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

		return soName;
	}


}
