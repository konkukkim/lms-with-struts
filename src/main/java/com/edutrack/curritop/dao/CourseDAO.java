package com.edutrack.curritop.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;



/*
 * @author JamFam
 *
 * 과정 관리
 */
public class CourseDAO extends AbstractDAO {

	/**
	 *
	 */
	public CourseDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과목의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(course_id) as cnt from course where system_code= ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);

		 log.debug("[getTotCount]" + sql.toString());

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
	/**
	 * 과목의 카운트를 가져온다.(코드 존재 유무 확인용)
	 * @param SystemCode
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CourseId) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(course_id) as cnt from course where system_code= ? and course_id=?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CourseId);

		 log.debug("[getTotCount]" + sql.toString());

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


	/**
	 * 과목 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @param Target
	 * @param Word
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCourseList(int curpage, String SystemCode, String Target, String Word) throws DAOException {
		ListDTO retVal = null;
		try{
			String addWhere = "";
			if(!Target.equals("") && !Word.equals("")) addWhere = " and "+Target+" like ?";
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("course_id");
			sql.setCutCol("course_id");
			sql.setAlias(	"course_id, course_name, prof_id, flag_use" +
							", flag_use_name, contents_type, online_yn, reg_id, reg_date");
			sql.setSelect(	"course_id, course_name, prof_id, flag_use" +
							", case flag_use when 'Y' then '<font color=blue>사용</font>' else '<font color=red>사용안함</font>' end as flag_use_name" +
							", contents_type, online_yn, reg_id" +
							", getDateText(reg_date,null) as reg_date");
			sql.setFrom(" course");
			sql.setWhere(" system_code=? "+addWhere);
			sql.setString(SystemCode);
			if(!Target.equals("") && !Word.equals("")) sql.setString("%"+Word+"%");
			//sql.setOrderby(" course_id asc");
			sql.setOrderby(" reg_date desc");
			//---- 디버그 출력
			log.debug("[getCourseList]" + sql.toString());

			//---- 쿼리실행 및 반환값 설정
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
	 * 과목의 리스트를 가져온다.
	 * @param SystemCode
	 * @param Target
	 * @param Word
	 * @return Rowset
	 * @throws DAOException
	 */
	public RowSet getCourseListAll(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String Target, String Word) throws DAOException {
		RowSet rs = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		if(!Target.equals("") && !Word.equals("")) addWhere = " and "+Target+" like ?";
		if(CurriYear > 0 && CurriTerm > 0) {
			sb.append(	" SELECT " +
							" a.system_code, a.course_id, a.course_name, a.prof_id" +
							", a.contents_type, a.online_yn, b.user_name as prof_name " +
						" FROM course a, users b " +
						" WHERE " +
							" a.system_code=b.system_code " +
							" AND a.system_code=? AND a.prof_id=b.user_id " +
							addWhere +
							" AND a.course_id NOT IN (SELECT course_id " +
													" FROM curri_sub_course " +
													" WHERE " +
													" system_code=? AND curri_code=? " +
													" AND curri_year=? AND curri_term=?) " +
							" AND a.flag_use='Y' " +
						" ORDER BY a.course_id ");
		} else {
			sb.append(	" SELECT " +
							" a.system_code, a.course_id, a.course_name, a.prof_id" +
							", a.contents_type, a.online_yn, b.user_name as prof_name " +
							" FROM course a, users b " +
						" WHERE " +
							" a.system_code=b.system_code AND a.system_code=? " +
							" AND a.prof_id=b.user_id " +
							addWhere +
							" AND a.course_id NOT IN (SELECT course_id " +
													" FROM curri_top_course " +
													" WHERE system_code=? AND curri_code=?) " +
							" AND a.flag_use='Y' " +
						" ORDER BY a.course_id ");
		}
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!Target.equals("") && !Word.equals("")) sql.setString("%"+Word+"%");
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		if(CurriYear > 0 && CurriTerm > 0) {
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
		}
		
		
		log.debug("[getCourseListAll]" + sql.toString());
		
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	public RowSet getCourseListAll(String SystemCode) throws DAOException {
		RowSet rs = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select course_id,course_name from course where system_code=? and flag_use='Y'  order by course_name");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		log.debug("[getCourseListAll]" + sql.toString());
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 개별 과목 정보를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCourseInfo(String SystemCode, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select " +
				" system_code, course_id, course_name, prof_id, course_img1, course_img2, contents_width"+
				", contents_height, contents_type, online_yn, flag_use, flag_navi, reg_id, reg_date");
		sb.append(" from course");
		sb.append(" where system_code=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);

		//---- 디버그 출력
		log.debug("[getCourseInfo]" + sql.toString());
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 개별 과목 정보를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @return RowSet
	 * @throws DAOException
	 */
	public String getCourseInfoXML(String SystemCode, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, course_id, course_name, prof_id, course_img1, course_img2, contents_width"+
				", contents_height, contents_type, flag_use, flag_navi, reg_id, reg_date");
		sb.append(" from course");
		sb.append(" where system_code=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);

		//---- 디버그 출력
		log.debug("[getCourseInfoXML]" + sql.toString());
		String strXML = "";
		try{
			strXML = broker.executeXML(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return strXML;
	}

	/**
	 * 과목 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCourseInfo(CourseDTO courseInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into course(system_code, course_id, course_name, prof_id" +
									", course_img1, course_img2, contents_width, contents_height"+
									", contents_type, online_yn, flag_use, flag_navi" +
									", reg_id, reg_date, mod_id, mod_date)");
		sb.append(" values (?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?)");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(courseInfo.getSystemCode());
		sql.setString(courseInfo.getCourseId());
		sql.setString(courseInfo.getCourseName());
		sql.setString(courseInfo.getProfId());
		sql.setString(courseInfo.getCourseImg1());
		sql.setString(courseInfo.getCourseImg2());
		sql.setString(courseInfo.getContentsWidth());
		sql.setString(courseInfo.getContentsHeight());
		sql.setString(courseInfo.getContentsType());
		sql.setString(courseInfo.getOnlineYn());
		sql.setString(courseInfo.getFlagUse());
		sql.setString(courseInfo.getFlagNavi());
		sql.setString(courseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(courseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		//---- 디버그 출력
		log.debug("[addCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과목 정보를 수정한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCourseInfo(CourseDTO courseInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update course set course_name=?, prof_id=?" +
									", contents_width=?, contents_height=?" +
									", contents_type=?, online_yn = ?" +
									", flag_use=?, flag_navi=?" +
									", mod_id=?, mod_date=?");
		if(courseInfo.getCourseImg1() != null )
			sb.append(" , course_img1=? ");
		if(courseInfo.getCourseImg2() != null )
			sb.append(" , course_img2=?" );
		sb.append(" where system_code=? and course_id=?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(courseInfo.getCourseName());
		sql.setString(courseInfo.getProfId());
		sql.setString(courseInfo.getContentsWidth());
		sql.setString(courseInfo.getContentsHeight());
		sql.setString(courseInfo.getContentsType());
		sql.setString(courseInfo.getOnlineYn());
		sql.setString(courseInfo.getFlagUse());
		sql.setString(courseInfo.getFlagNavi());
		sql.setString(courseInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		if(courseInfo.getCourseImg1() != null )
			sql.setString(courseInfo.getCourseImg1());
		if(courseInfo.getCourseImg2() != null )
			sql.setString(courseInfo.getCourseImg2());
		sql.setString(courseInfo.getSystemCode());
		sql.setString(courseInfo.getCourseId());


		//---- 디버그 출력
		log.debug("[editCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과목 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int delCourseInfo(String SystemCode, String CourseId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from course where system_code=? and course_id=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);

		//---- 디버그 출력
		log.debug("[delCourseInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개별 과목 컬럼  정보를 가져온다.
	 * sangsang 2007.05.08
	 * @param systemCode
	 * @param courseId
	 * @param columnName
	 * @return
	 * @throws DAOException
	 */
	public String getColumnData(String systemCode, String courseId, String columnName ) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select "+columnName+" from course where system_code=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(courseId);

		//---- 디버그 출력
		log.debug("[getCourseColumnData]" + sql.toString());
		String result = "";
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next())
				result = rs.getString(columnName);
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return result;
	}

	/**
	 * 개별 과목 컬럼 정보를 업데이트한다.
	 * sangsang 2007.05.08
	 * @param systemCode
	 * @param courseId
	 * @param columnName
	 * @return
	 * @throws DAOException
	 */
	public int columnUpdate(String systemCode, String courseId, String columnName, String columnData ) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("update course set "+columnName+"='"+columnData+"' where system_code=? and course_id=? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(courseId);

		//---- 디버그 출력
		log.debug("[getCourseColumnData]" + sql.toString());
		int retVal = 0;
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

}