package com.edutrack.currisub.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.DateTimeUtil;

/*
 * @author Bschoi
 *
 * 목차 관리
 */
public class CurriContentsDAO extends AbstractDAO {


	/**
	 * 과목의 전체 컨텐츠 수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public int getTotalCurriContentsCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId) throws DAOException{
		 int  totalCnt = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(contents_id),0) as totalCnt ");
		 sb.append(" from curri_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 log.debug("[getTotalCurriContentsCnt]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	totalCnt = rs.getInt("totalCnt");
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

		 return totalCnt;
	}
	/**
	 * 과정 학습 대상 컨텐츠 갯수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */
	public int getFCurriContentsCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String LectureGubun) throws DAOException{
		 int  totalCnt = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(contents_id),0) as totalCnt ");
		 sb.append(" from curri_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and contents_type = 'F' ");
		 if(LectureGubun !=null && !("").equals(LectureGubun)){
			sb.append("and lecture_gubun ='"+LectureGubun+"' ");
		}
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);

		 log.debug("[getFCurriContentsCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	totalCnt = rs.getInt("totalCnt");
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

		 return totalCnt;
	}
	/**
	 * 교재 목차 번호를 생성한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ParentOrder
	 * @return
	 * @throws DAOException
	 */
	public String getMakeCurriContentsOrder(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ParentOrder) throws DAOException{
		String retOrder = "";
		String maxOrder = "";
		int maxOrderLength = 0;
		int i = 0;
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
		if(ParentOrder.equals("")){
		 	sb.append(" select ifnull(max(substring(contents_order,1,3)),0) as maxOrder ");
			sb.append(" from curri_contents ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and contents_order like ? ");
			sql.setSql(sb.toString());
			sql.setString(SystemCode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(CourseId);
			sql.setString("%.000.000.000.000.000.000.000.000.000");

		}else{
		 	int par_cnt = ParentOrder.length()+2;
		 	sb.append(" select max(substring(contents_order,"+par_cnt+",3)) as maxOrder ");
			sb.append(" from curri_contents ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and contents_order like ? ");
			sql.setSql(sb.toString());
			sql.setString(SystemCode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			sql.setString(CourseId);
			sql.setString(ParentOrder+"%");

		 }

		 log.debug("[getMakeCurriContentsOrder]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	maxOrder = String.valueOf(Integer.parseInt(StringUtil.nvl(rs.getString("maxOrder")))+1);
			 	maxOrderLength = maxOrder.length();
			 	for(i=0;i<(3-maxOrderLength);i++){
			 		maxOrder = "0"+maxOrder;
				}
			 	if(ParentOrder.equals(""))
			 		retOrder = maxOrder;
				else
					retOrder = ParentOrder+"."+maxOrder;
			 	String[] arrOrder = StringUtil.split(retOrder,".");
			 	int splitCnt = arrOrder.length;
			 	for(i=0;i<(10-splitCnt);i++){
			 		retOrder = retOrder+".000";
				}
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

		 return retOrder;
	}


	/**
	 * 과목 목차 리스트전체를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriContentsList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String LectureGubun) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(	" SELECT " +
					" cc.system_code, cc.curri_code, cc.curri_year, cc.curri_term" +
					", cc.course_id, cc.contents_id, cc.contents_order, cc.contents_name");
		sb.append(	", cc.file_path, cc.asffile_path, cc.server_path, cc.contents_type" +
					", (SELECT COUNT(*) FROM curri_quiz " +
						" WHERE system_code = cc.system_code AND curri_code = cc.curri_code " +
						" AND curri_year = cc.curri_year AND curri_term = cc.curri_term " +
						" AND course_id = cc.course_id AND contents_id = cc.contents_id) AS quiz_count" +
					", cc.quiz_point, cc.show_time, cc.contents_width, cc.contents_height" +
					", cc.size_app, cc.lecture_gubun, cc.start_date, cc.end_date" +
					", cc.reg_date ");
		sb.append(	", (SELECT user_name FROM users WHERE system_code = cc.system_code AND user_id = csc.prof_id) AS prof_name  ");
		//sb.append(",lecture_gubun, getDateText(start_date, null) as start_date, concat(date_format(if(length(start_date)=0,null,start_date),'%H:%i'),'~',date_format(if(length(end_date)=0,null,end_date),'%H:%i')) as end_date ");

		sb.append(	" FROM curri_sub_course csc, curri_contents cc ");
		sb.append(	" WHERE " +
					" cc.system_code = csc.system_code AND cc.curri_code = csc.curri_code " +
					" AND cc.curri_year = csc.curri_year AND cc.curri_term = csc.curri_term " +
					" AND cc.course_id = csc.course_id AND cc.system_code=? ");
		sb.append(" AND cc.curri_code = ? ");
		sb.append(" AND cc.curri_year = ? ");
		sb.append(" AND cc.curri_term = ? ");
		sb.append(" AND cc.course_id=? ");
//		if(LectureGubun !=null && !("").equals(LectureGubun)){
//			sb.append("and lecture_gubun ='"+LectureGubun+"' ");
//		}
		sb.append("order by cc.contents_order");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);

		log.debug("[getCurriContentsList]" + sql.toString());

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
	 * 샘플 강좌 가져오기
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriContentsSample(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append("select system_code,curri_code, curri_year, curri_term, course_id, contents_id, contents_order");
		sb.append(",contents_name,file_path, server_path,contents_type,quiz_count, quiz_point, show_time ");
		sb.append("from curri_contents ");
		sb.append("where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and contents_type='F' ");
		sb.append(" order by contents_order ");
		//sb.append(") where limit 1");
		sb.append(") limit 1 ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		log.debug("[getCurriContentsSample]" + sql.toString());
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
	 * 개별 컨텐츠 정보를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriContents(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, course_id, contents_id, contents_order, contents_name,file_path, asffile_path, server_path");
		sb.append(",contents_type,quiz_count, quiz_point, prerequisite, pre_type, timelimitaction, datafromlms");
		sb.append(", masteryscore, maxtimeallowed , show_time, size_app, contents_width, contents_height");
		sb.append(", lecture_gubun, start_date, end_date ");
		sb.append("from curri_contents ");
		sb.append("where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id=? and contents_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsId);

		log.debug("[getCurriContents]" + sql.toString());
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
	 * 개별 컨텐츠 정보를 저장한다.
	 * @param contentsDto
	 * @return int
	 * @throws DAOException
	 */
	public int addCurriContents(CurriContentsDTO contentsDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into curri_contents(");
		sb.append("system_code, curri_code, curri_year, curri_term, course_id, contents_id, contents_order");
		sb.append(", contents_name,file_path, asffile_path, server_path,contents_type,quiz_count, quiz_point, show_time");
		sb.append(", prerequisite, pre_type, timelimitaction, datafromlms, masteryscore, maxtimeallowed");
		sb.append(", size_app, contents_width, contents_height, reg_id, reg_date");
		sb.append(",lecture_gubun, start_date, end_date )");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  0, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?,?,? )");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(contentsDto.getSystemCode());
		sql.setString(contentsDto.getCurriCode());
		sql.setInteger(contentsDto.getCurriYear());
		sql.setInteger(contentsDto.getCurriTerm());
		sql.setString(contentsDto.getCourseId());
		sql.setString(contentsDto.getContentsId());
		sql.setString(contentsDto.getContentsOrder());
		sql.setString(contentsDto.getContentsName());
		sql.setString(contentsDto.getFilePath());
		sql.setString(contentsDto.getAsfFilePath());
		sql.setString(contentsDto.getServerPath());
		sql.setString(contentsDto.getContentsType());
		sql.setInteger(contentsDto.getShowTime());
		sql.setString(contentsDto.getPreRequisite());
		sql.setString(contentsDto.getPreType());
		sql.setString(contentsDto.getTimeLimitAction());
		sql.setString(contentsDto.getDataFromLms());
		sql.setString(contentsDto.getMasteryScore());
		sql.setString(contentsDto.getMaxTimeAllowed());
		sql.setString(contentsDto.getSizeApp());
		sql.setInteger(contentsDto.getContentsWidth());
		sql.setInteger(contentsDto.getContentsHeight());
		sql.setString(contentsDto.getRegId());
		//강의 타입 추가(온/오프 여부)
		sql.setString(contentsDto.getLectureGubun());
		sql.setString(contentsDto.getStartDate());
		sql.setString(contentsDto.getEndDate());

		log.debug("[addCurriContents]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개별 컨텐츠 정보를 수정한다.
	 * @param contentsDto
	 * @return	int
	 * @throws DAOException
	 */
	public int editCurriContents(CurriContentsDTO contentsDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update curri_contents set contents_name=?, file_path=?, asffile_path=?, server_path=?");
		sb.append(",contents_type=?, quiz_count=?, quiz_point=?, show_time=?, size_app=?, contents_width=?, contents_height=?, mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(",lecture_gubun=?, start_date=?, end_date=?");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id=? and contents_id=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(contentsDto.getContentsName());
		sql.setString(contentsDto.getFilePath());
		sql.setString(contentsDto.getAsfFilePath());
		sql.setString(contentsDto.getServerPath());
		sql.setString(contentsDto.getContentsType());
		sql.setInteger(contentsDto.getQuizCount());
		sql.setInteger(contentsDto.getQuizPoint());
		sql.setInteger(contentsDto.getShowTime());
		sql.setString(contentsDto.getSizeApp());
		sql.setInteger(contentsDto.getContentsWidth());
		sql.setInteger(contentsDto.getContentsHeight());
		sql.setString(contentsDto.getModId());
		//강의 타입 추가(온/오프 여부)
		sql.setString(contentsDto.getLectureGubun());
		sql.setString(contentsDto.getStartDate());
		sql.setString(contentsDto.getEndDate());
		//where
		sql.setString(contentsDto.getSystemCode());
		sql.setString(contentsDto.getCurriCode());
		sql.setInteger(contentsDto.getCurriYear());
		sql.setInteger(contentsDto.getCurriTerm());
		sql.setString(contentsDto.getCourseId());
		sql.setString(contentsDto.getContentsId());

		log.debug("[editCurriContents]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개별 컨텐츠 정보를 삭제한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsOrder
	 * @return
	 * @throws DAOException
	 */
	public int delCurriContents(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsOrder) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_contents where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id=? and contents_order like ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsOrder+"%");

		log.debug("[delCurriContents]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 * 컨텐츠 이월
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int addCurriContentsAuto(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String UserId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO curri_contents ( ");
		sb.append(	" system_code,curri_code,curri_year, curri_term" +
					", course_id, contents_id, contents_order, contents_name");
		sb.append(	", file_path, server_path, contents_type, quiz_count" +
					", quiz_point, show_time, prerequisite, pre_type");
		sb.append(	", timelimitaction, datafromlms, masteryscore, maxtimeallowed");
		sb.append(	", reg_id, reg_date, size_app, contents_width" +
					", contents_height, lecture_gubun) ");
		sb.append(" SELECT " +
					" system_code, ?, ?, ?" +
					", course_id, contents_id,contents_order, contents_name" +
					", file_path, server_path, contents_type, quiz_count");
		sb.append(	", quiz_point, show_time, prerequisite, pre_type");
		sb.append(	", timelimitaction, datafromlms, masteryscore, maxtimeallowed");
		sb.append(	", ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), size_app, contents_width" +
					", contents_height, lecture_gubun ");
		sb.append(" FROM contents ");
		sb.append(" WHERE system_code = ? AND course_id = ? ");
		sql.setSql(sb.toString());

		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(UserId);
		sql.setString(SystemCode);
		sql.setString(CourseId);

		log.debug("[addCurriContentsAutoMake]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 단원평가 설정값을 변경한다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizCount
	 * @param QuizPoint
	 * @param ModId
	 * @return
	 * @throws DAOException
	 */
	public int editCurriContentsQuizConfig(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ContentsId, int QuizCount, int QuizPoint,String ModId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update curri_contents set quiz_count=?, quiz_point=?, mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id=? and contents_id=? ");
		sql.setSql(sb.toString());

		sql.setInteger(QuizCount);
		sql.setInteger(QuizPoint);
		sql.setString(ModId);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ContentsId);

		log.debug("[editCurriContentsQuizConfig]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과목 컨텐츠 리스트를 가져온다. bookmarking 정보도 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getBookmarkContentsList(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String UserId) throws DAOException{
		log.debug("-------------------  getBookmarkContentsList  Start ----------------------");
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT " +
					"a.course_id" +
					", ( SELECT course_name FROM course WHERE system_code = a.system_code and course_id = a.course_id ) AS course_name, ");
		sb.append(	" a.contents_id, a.contents_order, a.contents_name, a.contents_type" +
					",  IFNULL(a.file_path,'') as file_path,  IFNULL(a.asffile_path,'') as asffile_path, IFNULL(a.server_path,'') as server_path, a.quiz_count, a.quiz_point, ");
		sb.append(	" IFNULL(a.show_time,0) as show_time, ");
		sb.append(	" CASE WHEN (a.quiz_count = 0 or a.quiz_count = '') THEN 'N' ELSE 'Y' END AS quiz_yn, ");
		sb.append(	" CASE WHEN b.open_chk = '' THEN 'N' ELSE b.open_chk END AS open_chk, ");
		sb.append(	" CASE WHEN a.quiz_count = 0 THEN '' WHEN b.quiz_pass is null THEN 'N' ELSE b.quiz_pass END AS quiz_pass, ");
		sb.append(	" CASE WHEN b.total_time = '' THEN '0' ELSE CAST(b.total_time AS CHAR) END AS total_time, ");
		sb.append(	" CASE WHEN b.join_count = '' THEN '0' ELSE CAST(b.join_count AS CHAR) END AS join_count, ");
		sb.append(	" open_date, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) AS cur_date, ");
		sb.append(	" size_app, contents_width, contents_height ");	// 컨텐츠 사이즈 까지 가져온다  sangsang 2007.05.18
		sb.append(	", a.start_date, a.end_date, a.lecture_gubun, b.attendance ");	// offline 시 출결여부...2007.08.03
		
		sb.append(	" FROM curri_contents a LEFT OUTER JOIN bookmark b ");
		sb.append(	" ON a.system_code = b.system_code AND a.curri_code=b.curri_code " +
					" AND a.curri_year=b.curri_year AND a.curri_term=b.curri_term " +
					" AND a.course_id = b.course_id AND a.contents_id = b.contents_id " +
					" AND b.user_id = ? ");
		sb.append(	" WHERE a.system_code = ? AND a.curri_code= ? " +
					" AND a.curri_year= ? AND a.curri_term= ? ");

		sql.setString(UserId);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);

		if(!CourseId.equals("")){
			sb.append(" and a.course_id = ? ");
			sql.setString(CourseId);
		}

		sb.append(" order by  a.course_id, a.contents_order");

		sql.setSql(sb.toString());

		log.debug("[getBookmarkContentsList]" + sql.toString());

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		log.debug("-------------------  getBookmarkContentsList  End ----------------------");
		return rs;
	}

	/**
	 * 과정별/ 과목별 진도율, 컨텐츠 갯수, 수강 컨텐츠 갯수를 가져온다.
	 * 진도율 - (하나의 컨텐츠수강시간 / 하나의 컨텐츠기준수강시간) * 100 의 합계 / (컨텐츠 총 갯수 * 100) * 100
	 *
	 * @return
	 * @throws DAOException
	 */
	public CurriContentsDTO getMainAttendShow(String systemcode, String curricode, int curriyear, int curriterm,String courseid, String userid) throws DAOException{
		CurriContentsDTO curriContentsDto 	= 	new CurriContentsDTO();
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT " +
						" SUM(X.open_cnt) AS contents_cnt, COUNT(X.contents_id) AS all_contents_cnt, SUM(show_time) AS attend_time " +
						//-- 진도율
						", CASE WHEN COUNT(X.contents_id) = 0 THEN 0 " +
						" ELSE " +
							" CASE WHEN sum(ifnull(X.show_time,0)) = 0 OR sum(ifnull(X.total_time,0)) = 0 THEN 0 " +
							" ELSE round((sum(round((show_time / total_time) * 100, 2)) / (COUNT(X.contents_id)*100)) * 100, 1) END " +
						" END AS pro_rate  ");
		sb.append(" FROM ");
		sb.append(" ( ");
		sb.append(		" SELECT " +
							" cc.system_code, cc.curri_code, cc.curri_year, cc.curri_term" +
							", cc.contents_id, cc.course_id, b.user_id" +
							", IFNULL(cc.show_time, 0) AS total_time " +
							", CASE WHEN cc.show_time <= IFNULL(b.total_time, 0) THEN cc.show_time  ELSE IFNULL(b.total_time, 0) END AS show_time " +
							", CASE WHEN b.open_chk = 'Y' THEN 1 ELSE 0 END AS open_cnt  ");
		sb.append(		" FROM " +
							" curri_contents cc LEFT OUTER JOIN bookmark b " +
							" ON b.system_code = cc.system_code AND b.curri_code = cc.curri_code " +
							" AND b.curri_year = cc.curri_year AND b.curri_term = cc.curri_term " +
							" AND b.course_id = cc.course_id AND b.contents_id = cc.contents_id " +
							" AND b.user_id = ? ");
		sb.append(		" WHERE " +
							" cc.system_code = ? AND cc.curri_code = ? " +
							" AND cc.curri_year = ? AND cc.curri_term = ? " +
							" AND cc.lecture_gubun = '1' AND cc.contents_type = 'F' ");
		if(!courseid.equals(""))
			sb.append(		" AND cc.course_id = ?  ");

		sb.append(" ) X ");
		sb.append(" GROUP BY X.system_code, X.curri_code, X.curri_year, X.curri_term ");
		if(!courseid.equals(""))
			sb.append(", X.course_id ");

  	    sql.setSql(sb.toString());
  	    sql.setString(userid);
  	    sql.setString(systemcode);
  	    sql.setString(curricode);
		sql.setInteger(curriyear);
		sql.setInteger(curriterm);
		if(!courseid.equals(""))
			sql.setString(courseid);

		log.debug("[getMainAttendShow]" + sql.toString());


		RowSet rs 	= 	null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next()){
				curriContentsDto.setAllContentsCnt(rs.getInt("all_contents_cnt"));
				curriContentsDto.setContentsCnt(rs.getInt("contents_cnt"));
				curriContentsDto.setTotalTime(rs.getInt("attend_time"));
				curriContentsDto.setProcessRate(rs.getDouble("pro_rate"));				//진도율
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
		return curriContentsDto;
	}


	/**
	 * 학생들의 진도 현황을 가져온다(페이징처리)
	 * @param curpage
	 * @param DispLine
	 * @param DispPage
	 * @param Systemcode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param searchKey
	 * @param keyWord
	 * @param order
	 * @return
	 * @throws DAOException
	 */
/*	public ListDTO getLecStudyStatusPagingList(int curpage,int DispLine, int DispPage, String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String searchKey, String keyWord , String order) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" users u,student s left outer join bookmark b");
			sb.append(" on s.system_code = b. system_code and s.curri_code=b.curri_code ");
			sb.append(" and s.curri_year = b.curri_year and s.curri_term = b.curri_term and s.user_id=b.user_id and b.attendance is null ");


			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("s.user_id");
			sql.setCutCol("s.user_id");
			sql.setAlias("user_id , user_name, study_cnt, study_time");
			sql.setSelect("s.user_id , u.user_name, ifnull(count(b.contents_id),0) as study_cnt, ifnull(sum(b.total_time),0) as study_time");
			sql.setFrom(sb.toString());
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(" s.system_code = u.system_code and s.user_id=u.user_id and s.system_code = ? ");
			sbWhere.append(" and s.curri_code=? and s.curri_year=? and s.curri_term=? and s.enroll_status in ('S','C','F')");
			sql.setString(Systemcode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);
			String strWhere = "";
			if(!keyWord.equals("")){
				sbWhere.append(" and "+searchKey+" like ? ");
				sql.setString("%"+keyWord+"%");
			}
			sql.setWhere(sbWhere.toString());

			if(order.equals(""))
				sql.setOrderby(" u.user_name");
			else
				sql.setOrderby(" "+order);
            sql.setGroupby(" s.user_id , u.user_name");

            // 파라미터 셋팅
			log.debug("[getLecStudyStatusPagingList]" + sql.toString());

			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);


			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}
*/

	public ListDTO getAutoLecStudyStatusPagingList(int curpage,int DispLine, int DispPage, String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String searchKey, String keyWord , String order) throws DAOException{
		ListDTO retVal = null;
		try{
			//--	SELECT문 세팅
			StringBuffer sbSelect = new StringBuffer();
			sbSelect.append(	" Y.user_id, Y.user_name " +
								", SUM(Y.total_time) AS total_time, SUM(Y.all_offline) AS all_attend, SUM(Y.attendance) AS attendance " +
								", CASE WHEN COUNT(Y.contents_id) = 0 THEN 0 " +
									" ELSE " +
										" CASE WHEN sum(ifnull(Y.show_time,0)) = 0 OR sum(ifnull(Y.total_time,0)) = 0 THEN 0 " +
										" ELSE round((sum(round((Y.total_time / Y.show_time) * 100, 2)) / (SUM(Y.all_online)*100)) * 100, 2) END " +
									" END AS pro_rate " +
								", (SELECT COUNT(report_id) FROM report_info " +
									" WHERE system_code = Y.system_code AND curri_code = Y.curri_code " +
									" AND curri_year = Y.curri_year AND curri_term = Y.curri_term " +
									" AND report_regist_yn = 'Y') AS all_report_cnt " +
								", (SELECT COUNT(report_id) FROM report_send " +
									" WHERE system_code = Y.system_code AND curri_code = Y.curri_code " +
									" AND curri_year = Y.curri_year AND curri_term = Y.curri_term " +
									" AND user_id = Y.user_id and send_count > 0 ) AS report_cnt " +
								", (SELECT COUNT(exam_id) FROM exam_info " +
									" WHERE system_code = Y.system_code AND curri_code = Y.curri_code " +
									" AND curri_year = Y.curri_year  AND curri_term = Y.curri_term " +
									" AND flag_use = 'Y') AS all_exam_cnt " +
								", (SELECT COUNT(exam_id) FROM exam_answer " +
									" WHERE system_code = Y.system_code AND curri_code = Y.curri_code " +
									" AND curri_year = Y.curri_year  AND curri_term = Y.curri_term " +
									" AND user_id = Y.user_id) AS exam_cnt " +
								", (SELECT COUNT(forum_id) FROM course_forum_info " +
									" WHERE system_code = Y.system_code AND curri_code = Y.curri_code " +
									" AND curri_year = Y.curri_year AND curri_term = Y.curri_term " +
									" AND regist_yn = 'Y') AS all_forum_cnt " +
								", (SELECT COUNT(seq_no) FROM course_forum_contents " +
									" WHERE system_code = Y.system_code AND curri_code = Y.curri_code " +
									" AND curri_year = Y.curri_year  AND curri_term = Y.curri_term " +
									" AND reg_id = Y.user_id) AS forum_cnt ");

			//--	FROM문 세팅
			StringBuffer	sbFrom	=	new StringBuffer();
			sbFrom.append(	" ( ");
			sbFrom.append(			" SELECT " +
										" X.system_code, X.curri_code, X.curri_year, X.curri_term " +
										", X.contents_id, X.user_id , X.show_time, X.lecture_gubun " +
										", CASE WHEN X.lecture_gubun = '1' AND X.show_time <= IFNULL(b.total_time, 0) THEN X.show_time  " +
											" WHEN X.lecture_gubun = '1' AND X.show_time > IFNULL(b.total_time, 0) THEN IFNULL(b.total_time, 0) " +
											" ELSE 0 " +
										" END AS total_time " +
										", CASE WHEN X.lecture_gubun = '2' AND b.attendance = 'O' THEN 1 ELSE 0 END AS attendance " +
										", CASE WHEN X.lecture_gubun = '2' THEN 1 ELSE 0 END AS all_offline " +
										", CASE WHEN X.lecture_gubun = '1' THEN 1 ELSE 0 END AS all_online " +
										", (SELECT user_name FROM users WHERE system_code = X.system_code AND user_id = X.user_id) AS user_name  ");
			sbFrom.append(			" FROM  ");
			sbFrom.append(			" ( ");
			sbFrom.append(					" SELECT " +
												" s.user_id , s.system_code, s.curri_code, s.curri_year " +
												", s.curri_term , cc.contents_id, cc.contents_type, cc.lecture_gubun " +
												", cc.show_time ");
			sbFrom.append(					" FROM " +
												" student s LEFT OUTER JOIN curri_contents cc " +
												" ON s.system_code = cc.system_code AND s.curri_code = cc.curri_code " +
												" AND s.curri_year = cc.curri_year AND s.curri_term = cc.curri_term " +
												" AND cc.contents_type = 'F' ");
			sbFrom.append(					" WHERE " +
												" s.system_code = ? AND s.curri_code = ? " +
												" AND s.curri_year = ? AND s.curri_term = ? " +
												" AND s.enroll_status in ('S','C','F') ");
			sbFrom.append(			" ) X LEFT OUTER JOIN bookmark b " +
									" ON X.system_code = b.system_code AND X.curri_code = b.curri_code " +
									" AND X.curri_year = b.curri_year AND X.curri_term = b.curri_term " +
									" AND X.contents_id = b.contents_id AND X.user_id = b.user_id  ");
			sbFrom.append(" ) Y ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol(" Y.user_id ");
			sql.setCutCol(	" Y.user_id ");
			sql.setAlias(	" user_id, total_time, all_attend, attendance" +
							", pro_rate, user_name, all_report_cnt, report_cnt" +
							", all_exam_cnt, exam_cnt, all_forum_cnt, forum_cnt ");
			sql.setSelect(sbSelect.toString());
			sql.setFrom(sbFrom.toString());

			sql.setString(Systemcode);
			sql.setString(CurriCode);
			sql.setInteger(CurriYear);
			sql.setInteger(CurriTerm);

			StringBuffer	sbWhere		=	new StringBuffer();
			String			strWhere	=	"";

			sbWhere.append(" 1=1 ");

			if(!keyWord.equals("")){
				sbWhere.append(" AND Y."+searchKey+" like ? ");
				sql.setString("%"+keyWord+"%");
			}
			sql.setWhere(sbWhere.toString());

			sql.setGroupby(" Y.system_code, Y.curri_code, Y.curri_year, Y.curri_term, Y.user_id ");

			if(order.equals(""))
				sql.setOrderby(" Y.user_name ");
			else
				sql.setOrderby(" "+order);

			//-- 파라미터 셋팅
			log.debug("[getAutoLecStudyStatusPagingList]" + sql.toString());
			
			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);

		} catch (SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch (Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}






	/**
	 * 학생들의 진도 현황을 가져온다(페이징없음)
	 * @param Systemcode
	 * @param BbsId
	 * @param addWhere
	 * @param order
	 * @return
	 * @throws DAOException
	 */
	public RowSet getLecStudyStatusList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String searchKey, String keyWord , String order) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select s.user_id , u.user_name, ifnull(count(b.contents_id),0) as study_cnt, ifnull(sum(b.total_time),0) as study_time");
		 sb.append(" from users u,student s left outer join bookmark b");
		 sb.append(" ON s.curri_code=b.curri_code and s.curri_year = b.curri_year and s.curri_term = b.curri_term and s.user_id=b.user_id and b.attendance is null ");
		 sb.append(" where s.system_code = u.system_code and s.user_id=u.user_id and s.system_code=? ");
		 sb.append(" and s.curri_code=? and s.curri_year=? and s.curri_term=? and s.enroll_status in ('S','C','F')");
		 if(!keyWord.equals(""))
		 	sb.append(" and "+searchKey+" like '%"+keyWord+"%'");

		sb.append(" group by s.user_id, u.user_name");
		if(order.equals(""))
			sb.append(" order by u.user_name ");
		else
			sb.append(" "+order);

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);

		 log.debug("[getLecStudyStatusList]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 //-- rs.close() 는 반드시 JSP 에서 해 줄
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return rs;
		}

	/**
	 * contents_order 를 의해 개별 컨텐츠명을 가져온다.(sangsang 2007.04.26)
	 * @param systemCode
	 * @param courseId
	 * @param contentsOrder
	 * @return
	 * @throws DAOException
	 */
	public String getCurriContentsNameByOrder(String systemCode, String courseCode, int curriYear, int curriTerm, String courseId, String contentsOrder) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	" SELECT contents_name " +
					" FROM curri_contents " +
					" WHERE system_code=? AND curri_code=? " +
					" AND curri_year=? AND curri_term=? " +
					" AND course_id=? AND contents_order=? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(courseCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(courseId);
		sql.setString(contentsOrder);

		log.debug("[getCurriContentsNameByOrder]" + sql.toString());
		String contentsName = "";
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			if(rs.next())
				contentsName = rs.getString("contents_name");
			rs.close();

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return contentsName;
	}

	/**
	 * 메인에서 상시과정 팝업창으로 보기
	 *
	 * @return
	 * @throws DAOException
	 */
	public RowSet ordinaryCourseContentsList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException{
		RowSet	rs	=	null;

		QueryStatement	sql	=	new QueryStatement();
		StringBuffer	sb	=	new StringBuffer();
		sb.append(	" SELECT " +
					" csc.course_id, co.course_name, co.contents_width, co.contents_height" +
					", cc.contents_id, cc.contents_order, cc.contents_name, cc.file_path" +
					", IFNULL(cc.file_path,'') as file_path,  IFNULL(cc.asffile_path,'') as asffile_path" +
					", cc.server_path, cc.contents_type, cc.contents_type ");
		sb.append(	" FROM curri_sub_course csc, curri_contents cc, course co ");
		sb.append(	" WHERE 1=1 " +
					" AND co.system_code = csc.system_code AND co.course_id = csc.course_id " +
					" AND cc.system_code = csc.system_code AND cc.course_id = csc.course_id " +
					" AND cc.curri_code = csc.curri_code AND cc.curri_year = csc.curri_year AND cc.curri_term = csc.curri_term " +
					" AND cc.contents_type = 'F' " +
					//" AND cc.lecture_gubun = '1' " +
					" AND csc.system_code = ? AND csc.curri_code = ? " +
					" AND csc.curri_year = ? AND csc.curri_term = ? ");
		sb.append(	" ORDER BY csc.course_id, cc.contents_order ");

		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);

		try {
			 rs	=	broker.executeQuery(sql);
			 //-- rs.close() 는 반드시 JSP 에서 해 줄
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 메인화면의 정규강좌 소개정보
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param contentsId
	 * @return
	 * @throws DAOException
	 */
	public CurriContentsDTO getCurriContentsIntroduce(String systemCode, String curriCode, int curriYear, int curriTerm, String courseId, String contentsId) throws DAOException {
		CurriContentsDTO	contentsDto	=	null;
		StringBuffer	sb		=	new StringBuffer();
		QueryStatement	sql		=	new QueryStatement();
		sb.append(" SELECT ");
		sb.append(	" ct.system_code, ct.curri_info, cs.prof_id, cs.curri_name, cs.credit " + 
					" , (SELECT user_name FROM users WHERE system_code = cs.system_code AND user_id = cs.prof_id) AS prof_name " + 
					" , cc.contents_id, substring(cc.contents_order, 1, 3) AS contents_order, cc.contents_name, cc.file_path " + 
					" , cc.contents_type, cc.lecture_gubun, cc.start_date, cc.end_date ");
		sb.append(" FROM curri_top ct, curri_sub cs, curri_contents cc ");
		sb.append(" WHERE " +
				" cs.system_code = ct.system_code AND cs.curri_code = ct.curri_code " +
				" AND cc.system_code = cs.system_code AND cc.curri_code = cs.curri_code " +
				" AND cc.curri_year = cs.curri_year AND cc.curri_term = cs.curri_term " +
				" AND cc.system_code = ? AND cc.curri_code = ? " +
				" AND cc.curri_year = ?  AND cc.curri_term = ? " +
				" AND cc.course_id = ? AND cc.contents_id = ? " +
				" AND cc.contents_type = 'F' AND cc.lecture_gubun = '1' ");
		
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);
		sql.setString(courseId);
		sql.setString(contentsId);
		
		log.debug(" [getCurriContentsIntroduce] "+sql.toString());
		
		RowSet	rs	=	null;
		try {
			 rs				=	broker.executeQuery(sql);
			 contentsDto	=	new CurriContentsDTO();
			 if(rs.next()) {
				 contentsDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				 contentsDto.setCurriInfo(StringUtil.nvl(rs.getString("curri_info")));
				 contentsDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				 contentsDto.setCredit(StringUtil.nvl(rs.getString("credit"), 0));
				 contentsDto.setProfName(StringUtil.nvl(rs.getString("prof_name")));
				 contentsDto.setContentsOrder(StringUtil.nvl(rs.getString("contents_order")));
				 contentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
				 contentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				 contentsDto.setStartDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("start_date"))));
				 contentsDto.setEndDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("end_date"))));
			 }
			 rs.close();
			 
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return contentsDto;
	}
	
	
}