package com.edutrack.curritop.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.curritop.dto.ContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/*
 * @author Bschoi
 *
 * 목차 관리
 */
public class ContentsDAO extends AbstractDAO {

	/**
	 * 과목의 전체 컨텐츠 수를 가져온다.
	 * @param SystemCode
	 * @param CourseId
	 * @return
	 * @throws DAOException
	 */
	public int getTotalContentsCnt(String SystemCode, String CourseId) throws DAOException{
		 int  totalCnt = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(count(contents_id),0) as totalCnt ");
		 sb.append(" from contents ");
		 sb.append(" where system_code = ? and course_id = ?");
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CourseId);
		 log.debug("[getTotalContentsCnt]" + sql.toString());
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
	 * @param CourseId
	 * @param ParentOrder
	 * @return
	 * @throws DAOException
	 */
	public String getMakeContentsOrder(String SystemCode, String CourseId, String ParentOrder) throws DAOException{
		String retOrder = "";
		String maxOrder = "";
		int maxOrderLength = 0;
		int i = 0;
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
		if(ParentOrder.equals("")){
		 	sb.append(" select ifnull(max(substring(contents_order,1,3)),0) as maxOrder ");
			sb.append(" from contents ");
			sb.append(" where system_code = ? and course_id = ? and contents_order like ? ");
			sql.setSql(sb.toString());
			sql.setString(SystemCode);
			sql.setString(CourseId);
			sql.setString("%.000.000.000.000.000.000.000.000.000");

		}else{
		 	int par_cnt = ParentOrder.length()+2;
		 	sb.append(" select max(substring(contents_order,"+par_cnt+",3)) as maxOrder ");
			sb.append(" from contents ");
			sb.append(" where system_code = ? and course_id = ? and contents_order like ? ");
			sql.setSql(sb.toString());
			sql.setString(SystemCode);
			sql.setString(CourseId);
			sql.setString(ParentOrder+"%");

		 }

		 log.debug("[getMakeContentsOrder]" + sql.toString());
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
	 * @param CourseId
	 * @return RowSet
	 * @throws DAOException
	 */

	public RowSet getContentsList(String SystemCode, String CourseId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	" SELECT " +
					" c.system_code, c.course_id, c.contents_id, c.contents_order" +
					", c.contents_name, c.file_path, c.server_path, c.contents_type");
		sb.append(	//", c.quiz_count" +
					", (SELECT COUNT(*) FROM quiz WHERE system_code = c.system_code AND contents_id = c.contents_id AND course_id = c.course_id) AS quiz_count" +
					", c.quiz_point, c.show_time, c.contents_width" +
					", c.contents_height, c.size_app, c.progress_rate, c.lecture_gubun");
		
		sb.append(	" FROM contents c ");
		sb.append(	" WHERE system_code=? and course_id=? " +
					" ORDER BY contents_order ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);

		log.debug("[getContentsList]" + sql.toString());

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
	 * @param CourseId
	 * @param ContentsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getContents(String SystemCode, String CourseId, String ContentsId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	"select " +
					" system_code, course_id, contents_id, contents_order" +
					", contents_name,file_path, server_path, contents_type");
		sb.append(	", quiz_count, quiz_point, show_time, contents_width" +
					", contents_height, size_app, prerequisite,  pre_type" +
					", timelimitaction, datafromlms, masteryscore, maxtimeallowed ");
		sb.append(	", lecture_gubun, asffile_path ");
		sb.append(	" from contents ");
		sb.append(	" where system_code=? and course_id=? and contents_id=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsId);

		log.debug("[getContents]" + sql.toString());
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
	 * contents_order 를 의해 개별 컨텐츠명을 가져온다.(sangsang 2007.04.26)
	 * @param systemCode
	 * @param courseId
	 * @param contentsOrder
	 * @return
	 * @throws DAOException
	 */
	public String getContentsNameByOrder(String systemCode, String courseId, String contentsOrder) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	"select contents_name " +
					"from contents " +
					"where system_code=? and course_id=? and contents_order=? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(courseId);
		sql.setString(contentsOrder);

		log.debug("[getContentsNameByOrder]" + sql.toString());
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
	 * 개별 컨텐츠 정보를 저장한다.
	 * @param contentsDto
	 * @return int
	 * @throws DAOException
	 */
	public int addContents(ContentsDTO contentsDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO contents(");
		sb.append(	" system_code, course_id, contents_id, contents_order" +
					", contents_name, file_path, server_path, contents_type" +
					", quiz_count, quiz_point, show_time, size_app" +
					", contents_width, contents_height, prerequisite, pre_type" +
					", timelimitaction, datafromlms, masteryscore, maxtimeallowed" +
					", reg_id, reg_date, lecture_gubun, asffile_path )");

		sb.append(	" VALUES (?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							",  0, 0, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR), ?, ?)");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(contentsDto.getSystemCode());
		sql.setString(contentsDto.getCourseId());
		sql.setString(contentsDto.getContentsId());
		sql.setString(contentsDto.getContentsOrder());
		sql.setString(contentsDto.getContentsName());
		sql.setString(contentsDto.getFilePath());
		sql.setString(contentsDto.getServerPath());
		sql.setString(contentsDto.getContentsType());
		sql.setInteger(contentsDto.getShowTime());
		sql.setString(contentsDto.getSizeApp());
		sql.setInteger(contentsDto.getContentsWidth());
		sql.setInteger(contentsDto.getContentsHeight());
		sql.setString(contentsDto.getPreRequisite());
		sql.setString(contentsDto.getPreType());
		sql.setString(contentsDto.getTimeLimitAction());
		sql.setString(contentsDto.getDataFromLms());
		sql.setString(contentsDto.getMasteryScore());
		sql.setString(contentsDto.getMaxTimeAllowed());
		sql.setString(contentsDto.getRegId());
		sql.setString(contentsDto.getLectureGubun());		
		sql.setString(contentsDto.getAsfFilePath());		

		log.debug("[addContents]" + sql.toString());
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
	public int editContents(ContentsDTO contentsDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(	" UPDATE contents SET " +
					" contents_name=?, file_path=?" +
					", server_path=?, contents_type=?");
		sb.append(	", show_time=?, size_app=?" +
					", contents_width=?, contents_height=?" +
					", mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(	", lecture_gubun=?, asffile_path=? ");
		sb.append(" WHERE system_code=? and course_id=? and contents_id=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(contentsDto.getContentsName());
		sql.setString(contentsDto.getFilePath());
		sql.setString(contentsDto.getServerPath());
		sql.setString(contentsDto.getContentsType());
		sql.setInteger(contentsDto.getShowTime());
		sql.setString(contentsDto.getSizeApp());
		sql.setInteger(contentsDto.getContentsWidth());
		sql.setInteger(contentsDto.getContentsHeight());
		sql.setString(contentsDto.getModId());
		sql.setString(contentsDto.getLectureGubun());
		sql.setString(contentsDto.getAsfFilePath());
		
		// where
		sql.setString(contentsDto.getSystemCode());
		sql.setString(contentsDto.getCourseId());
		sql.setString(contentsDto.getContentsId());

		log.debug("[editContents]" + sql.toString());
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
	 * @param CourseId
	 * @param ContentsId
	 * @param QuizCount
	 * @param QuizPoint
	 * @param ModId
	 * @return
	 * @throws DAOException
	 */
	public int editContentsQuizConfig(String SystemCode, String CourseId, String ContentsId, int QuizCount, int QuizPoint,String ModId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE contents SET quiz_count=?, quiz_point=?" +
										", mod_id=?, mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" WHERE system_code=? and course_id=? and contents_id=? ");
		sql.setSql(sb.toString());

		sql.setInteger(QuizCount);
		sql.setInteger(QuizPoint);
		sql.setString(ModId);
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsId);

		log.debug("[editContentsQuizConfig]" + sql.toString());
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
	 * @param CourseId
	 * @param ContentsId
	 * @return int
	 * @throws DAOException
	 */
	public int delContents(String SystemCode, String CourseId, String ContentsOrder) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from contents where system_code=? and course_id=? and contents_order like ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CourseId);
		sql.setString(ContentsOrder+"%");

		log.debug("[delContents]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 *
	 * @return
	 * @throws DAOException
	 */
	public RowSet getSiCMManiList(String SystemCode, String keyWord) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select v.mani_identifier,v.mani_xmlbase,  v.mani_url, v.mani_userid,  v.title_value,");
		sb.append(" v.keyword_value, v.description_value,u.user_name");
		sb.append("  from v_manifest v, users u");
		sb.append("  where v.mani_userid = u.user_id");
		sb.append("  and u.system_code = ? ");
		sb.append("  and concat(v.title_value,v.keyword_value,v.description_value) like ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString("%"+keyWord+"%");

		log.debug("[getSiCMContentsList]" + sql.toString());
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
}