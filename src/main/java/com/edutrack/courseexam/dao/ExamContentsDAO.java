/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamContentsDTO;
import com.edutrack.courseexam.dto.ExamOrderDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamContentsDAO extends AbstractDAO {

	/**
	 *
	 */
	public ExamContentsDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 시험문제 리스트를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param examno
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getExamContentsList(String systemcode, CurriMemDTO curriinfo,String courseid,int examid, int examorder) throws DAOException{
		ArrayList contentsList = new ArrayList();
		ExamContentsDTO contentsInfo = null;
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select exam_order, exam_no,exam_type,contents_text,example1,example2,example3,example4,example5, ");
			sb.append(" answer,multianswer,exam_comment,score,rfile_name,sfile_name,file_path,file_size,reg_id,reg_date ");
			sb.append(" from exam_contents ");
			sb.append(" where system_code= ? and curri_code= ? and curri_year= ? and curri_term= ? and course_id= ? and exam_id= ? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(courseid);
			sql.setInteger(examid);

			if(examorder > 0){
				sb.append(" and exam_order = ? ");
				sql.setInteger(examorder);
			}

			sb.append(" order by exam_order, exam_no");

			sql.setSql(sb.toString());

			log.debug("[getExamContentsList]" + sql.toString());

			rs = broker.executeQuery(sql);
			int preExamOrder =0;
			ExamOrderDTO orderDto = null;
			ArrayList orderList = null;
            int count = 0;
			while(rs.next()){
				contentsInfo = new ExamContentsDTO();
				contentsInfo.setExamOrder(rs.getInt("exam_order"));
				contentsInfo.setExamNo(rs.getInt("exam_no"));
				contentsInfo.setExamType(StringUtil.nvl(rs.getString("exam_type")));
				//contentsInfo.setContents(StringUtil.nvl(rs.getString("contents")));
				contentsInfo.setContentsText(StringUtil.nvl(rs.getString("contents_text")));
				contentsInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
				contentsInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
				contentsInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
				contentsInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
				contentsInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
				contentsInfo.setAnswer(StringUtil.nvl(rs.getString("answer")));
				contentsInfo.setScore(rs.getInt("score"));
				contentsInfo.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
				contentsInfo.setComment(StringUtil.nvl(rs.getString("exam_comment")));
				contentsInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
				contentsInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
				contentsInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
				contentsInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
				contentsInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
		        contentsInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				if(contentsInfo.getExamOrder() == preExamOrder){
					orderList.add(contentsInfo);
				}else{
					if(preExamOrder != 0){
						orderDto.setContentsList(orderList);
						contentsList.add(orderDto);
					}
					orderDto = new ExamOrderDTO(contentsInfo.getExamOrder(),contentsInfo.getScore());
					orderList = new ArrayList();
					orderList.add(contentsInfo);
				}

				preExamOrder = contentsInfo.getExamOrder();
				count++;
			}

			if(count > 0){
				orderDto.setContentsList(orderList);
				contentsList.add(orderDto);
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

		 return contentsList;
	}

	public ArrayList getExamContentsList(String systemcode, CurriMemDTO curriinfo,String courseid,int examid, String contentslist) throws DAOException{
		ArrayList contentsList = new ArrayList();
		ExamContentsDTO contentsInfo = null;
		ResultSet rs = null;
	    try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select exam_order, exam_no,exam_type,contents,contents_text,example1,example2,example3,example4,example5, ");
			sb.append(" answer,multianswer,exam_comment,score,rfile_name,sfile_name,file_path,file_size,reg_id,reg_date ");
			sb.append(" from exam_contents ");
			sb.append(" where system_code= ? and curri_code= ? and curri_year= ? and curri_term= ? and course_id= ? and exam_id= ? ");
			sb.append(" and CONCAT(CAST(exam_order AS CHAR),'/',CAST(exam_no AS CHAR)) in ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(courseid);
			sql.setInteger(examid);

			String[] examContents = StringUtil.split(contentslist,CommonUtil.DELI);
	        sb.append(" ( ");
	        for(int i =0; i < examContents.length; i++){
	        	if(i > 0) sb.append(",");
	        	sb.append("?");
	        	sql.setString(examContents[i]);
            }
	        sb.append(" ) ");

			sb.append(" order by exam_order, exam_no");
			sql.setSql(sb.toString());

			log.debug("[getExamContentsList]" + sql.toString());


			rs = broker.executeQuery(sql);
			int preExamOrder =0;
			ExamOrderDTO orderDto = null;
			ArrayList orderList = null;
            int count = 0;
			while(rs.next()){
				contentsInfo = new ExamContentsDTO();
				contentsInfo.setExamOrder(rs.getInt("exam_order"));
        		contentsInfo.setExamNo(rs.getInt("exam_no"));
        		contentsInfo.setExamType(StringUtil.nvl(rs.getString("exam_type")));
        		contentsInfo.setContents(StringUtil.nvl(rs.getString("contents")));
        		contentsInfo.setContentsText(StringUtil.nvl(rs.getString("contents_text")));
        		contentsInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
        		contentsInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
        		contentsInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
        		contentsInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
        		contentsInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
        		contentsInfo.setAnswer(StringUtil.nvl(rs.getString("answer")));
        		contentsInfo.setScore(rs.getInt("score"));
        		contentsInfo.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
        		contentsInfo.setComment(StringUtil.nvl(rs.getString("exam_comment")));
        		contentsInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
        		contentsInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
        		contentsInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
        		contentsInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
        		contentsInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
                contentsInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				contentsList.add(contentsInfo);
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

		 return contentsList;
	}

	public ExamContentsDTO getExamContentsInfo(String systemCode, CurriMemDTO curriinfo,String courseId,int examId, int examOrder, int examNo) throws DAOException{
		ExamContentsDTO examContentsDto = null;
		ResultSet rs = null;
	    StringBuffer output = new StringBuffer();
		try{
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select exam_order, exam_no,exam_type,contents,contents_text,example1,example2,example3,example4,example5, ");
			sb.append(" answer,multianswer,exam_comment,score,rfile_name,sfile_name,file_path,file_size,reg_id,reg_date ");
			sb.append(" from exam_contents ");
			sb.append(" where system_code= ? and curri_code= ? and curri_year= ? and curri_term= ? and course_id= ? and exam_id= ? ");
			sql.setString(systemCode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(courseId);
			sql.setInteger(examId);

			if(examOrder > 0){
				sb.append(" and exam_order = ? ");
				sql.setInteger(examOrder);
			}

			if(examNo > 0){
				sb.append(" and exam_no = ? ");
				sql.setInteger(examNo);
			}

			sql.setSql(sb.toString());

			log.debug("[getExamContentsInfo]" + sql.toString());

			rs = broker.executeQuery(sql);
			examContentsDto = new ExamContentsDTO();
            if(rs.next()){
            	examContentsDto.setSystemCode(systemCode);
            	examContentsDto.setCurriCode(curriinfo.curriCode);
            	examContentsDto.setCurriYear(Integer.parseInt(curriinfo.curriYear));
            	examContentsDto.setCurriTerm(Integer.parseInt(curriinfo.curriTerm));
            	examContentsDto.setCourseId(courseId);
            	examContentsDto.setExamId(examId);
            	examContentsDto.setExamOrder(rs.getInt("exam_order"));
            	examContentsDto.setExamNo(rs.getInt("exam_no"));
            	examContentsDto.setExamType(StringUtil.nvl(rs.getString("exam_type")));
        		examContentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
            	examContentsDto.setContentsText(StringUtil.nvl(rs.getString("contents_text")));
            	examContentsDto.setExample1(StringUtil.nvl(rs.getString("example1")));
            	examContentsDto.setExample2(StringUtil.nvl(rs.getString("example2")));
            	examContentsDto.setExample3(StringUtil.nvl(rs.getString("example3")));
            	examContentsDto.setExample4(StringUtil.nvl(rs.getString("example4")));
            	examContentsDto.setExample5(StringUtil.nvl(rs.getString("example5")));
            	examContentsDto.setAnswer(StringUtil.nvl(rs.getString("answer")));
        		examContentsDto.setScore(rs.getInt("score"));
        		examContentsDto.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
        		examContentsDto.setComment(StringUtil.nvl(rs.getString("exam_comment")));
        		examContentsDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
        		examContentsDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
        		examContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
        		examContentsDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));
        		examContentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
                examContentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

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

		return examContentsDto;
	}

	public int getMaxExamOrder(String systemcode, CurriMemDTO curriinfo,String courseid,int examid) throws DAOException{
		 int userCnt = 0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(exam_order),0)+1 as exam_order ");
		 sb.append(" from exam_contents ");
		 sb.append(" where system_code= ? and curri_code= ? and curri_year= ? and curri_term= ? and course_id= ? and exam_id= ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);

		 log.debug("[getMaxExamOrder]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	userCnt = rs.getInt("exam_order");
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
	public int getExamContentsMaxExamNo(String systemCode,CurriMemDTO curriInfo, ExamContentsDTO contentsinfo) throws DAOException{
		int retVal = 0;
		RowSet rs = null;
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(exam_no),0)+1 as max_no from exam_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and exam_id = ? and exam_order = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemCode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setInteger(contentsinfo.getExamId());
		 sql.setInteger(contentsinfo.getExamOrder());
		 log.debug("[getExamContentsMaxExamNo]" + sql.toString());
		 try{
		 	rs = broker.executeQuery(sql);

			if(rs.next()){
				retVal = rs.getInt("max_no");
			}
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		return retVal;
	}
	/**
	 * 시험 문제 등록
	 * @param systemcode
	 * @param curriInfo
	 * @param contentsinfo
	 * @return
	 * @throws DAOException
	 */
	public int addExamContentsInfo(String systemcode,CurriMemDTO curriInfo, ExamContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 int examOrder          =   0;
		 int exam_no = getExamContentsMaxExamNo(systemcode,curriInfo,contentsinfo);
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_contents(system_code, curri_code, curri_year, curri_term" +
		 										", course_id, exam_id, exam_order, exam_no" +
		 										", exam_type, contents, contents_text, example1 ");
		 sb.append(								", example2, example3, example4, example5" +
		 										", answer, multianswer, exam_comment, rfile_name" +
		 										", sfile_name, file_path, file_size, reg_id" +
		 										",reg_date) ");
		 sb.append(" values( ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					", ?, ?, ?, ?" +
		 					",CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");

		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setInteger(contentsinfo.getExamId());
		 sql.setInteger(contentsinfo.getExamOrder());
		 sql.setInteger(exam_no);
		 sql.setString(contentsinfo.getExamType());
		 sql.setString(contentsinfo.getContents());
		 sql.setString(contentsinfo.getContentsText());
		 sql.setString(contentsinfo.getExample1());
		 sql.setString(contentsinfo.getExample2());
		 sql.setString(contentsinfo.getExample3());
		 sql.setString(contentsinfo.getExample4());
		 sql.setString(contentsinfo.getExample5());
		 sql.setString(contentsinfo.getAnswer());
		 sql.setString(contentsinfo.getMultiAnswer());
		 sql.setString(contentsinfo.getComment());
		 sql.setString(contentsinfo.getRfileName());
		 sql.setString(contentsinfo.getSfileName());
		 sql.setString(contentsinfo.getFilePath());
		 sql.setString(contentsinfo.getFileSize());
		 sql.setString(contentsinfo.getRegId());

		 log.debug("[addExamContentsInfo]" + sql.toString());
		 try{
		    retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
		 	e.printStackTrace();
			throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}


	public int editExamContentsInfo(String systemcode,CurriMemDTO curriInfo, ExamContentsDTO contentsinfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_contents ");
		 sb.append(" set  exam_type = ? ,contents = ?,contents_text= ?,example1 = ?,example2 = ?,example3 = ?,example4 = ?,example5 = ?, ");
		 sb.append(" answer = ?,multianswer = ?,exam_comment = ?,rfile_name = ?,sfile_name = ?,file_path = ?,file_size = ?,mod_id = ?, ");
		 sb.append(" mod_date= getCurrentDate() ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and exam_id = ? and exam_order = ? and exam_no = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(contentsinfo.getExamType());
		 sql.setString(contentsinfo.getContents());
		 sql.setString(contentsinfo.getContentsText());
		 sql.setString(contentsinfo.getExample1());
		 sql.setString(contentsinfo.getExample2());
		 sql.setString(contentsinfo.getExample3());
		 sql.setString(contentsinfo.getExample4());
		 sql.setString(contentsinfo.getExample5());
		 sql.setString(contentsinfo.getAnswer());
		 sql.setString(contentsinfo.getMultiAnswer());
		 sql.setString(contentsinfo.getComment());
		 sql.setString(contentsinfo.getRfileName());
		 sql.setString(contentsinfo.getSfileName());
		 sql.setString(contentsinfo.getFilePath());
		 sql.setString(contentsinfo.getFileSize());
		 sql.setString(contentsinfo.getModId());
		 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(contentsinfo.getCourseId());
		 sql.setInteger(contentsinfo.getExamId());
		 sql.setInteger(contentsinfo.getExamOrder());
		 sql.setInteger(contentsinfo.getExamNo());

		 log.debug("[editExamContentsInfo]" + sql.toString());

		 try{
		    retVal = broker.executeUpdate(sql);

		 }catch(Exception e){
		 	e.printStackTrace();
			throw new DAOException(e.getMessage());
		 } finally {
		}
		 return retVal;
	}
	/*
	private void getExamContentsData(RowSet rs, ExamContentsDTO contentsInfo) throws SQLException{
    	contentsInfo.setExamOrder(rs.getInt("exam_order"));
		contentsInfo.setExamNo(rs.getInt("exam_no"));
		contentsInfo.setExamType(StringUtil.nvl(rs.getString("exam_type")));
		//contentsInfo.setContents(StringUtil.nvl(rs.getString("contents")));
		contentsInfo.setContentsText(StringUtil.nvl(rs.getString("contents_text")));
		contentsInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
		contentsInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
		contentsInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
		contentsInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
		contentsInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
		contentsInfo.setAnswer(StringUtil.nvl(rs.getString("answer")));
		contentsInfo.setScore(rs.getInt("score"));
		contentsInfo.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
		contentsInfo.setComment(StringUtil.nvl(rs.getString("exam_comment")));
		contentsInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
		contentsInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
		contentsInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
		contentsInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
		contentsInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
        contentsInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
	}
	*/
	public boolean editExamScore(String systemcode,CurriMemDTO curriInfo,String courseid, int examid, ArrayList scorelist) throws DAOException{
		 boolean retVal 			= 	false;
		 int sqlSize = scorelist.size();
		 QueryStatement sql 	= 	null;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update exam_contents ");
		 sb.append(" set score = ? ");
		 sb.append(" WHERE   system_code = ? AND curri_code = ? AND curri_year = ? AND curri_term = ? ");
		 sb.append(" AND course_id = ? AND exam_id = ? and exam_order =? ");

		 QueryStatement[] sqls = new QueryStatement[sqlSize];
         ExamOrderDTO order = null;
		 for(int i =0; i < sqlSize; i++){
		 	 order = (ExamOrderDTO)scorelist.get(i);
		 	 sql 	= 	new QueryStatement();
		   	 sql.setSql(sb.toString());
		   	 sql.setInteger(order.getScore());
		   	 sql.setString(systemcode);
			 sql.setString(curriInfo.curriCode);
			 sql.setInteger(curriInfo.curriYear);
			 sql.setInteger(curriInfo.curriTerm);
			 sql.setString(courseid);
			 sql.setInteger(examid);
			 sql.setInteger(order.getExamOrder());

			 sqls[i] = sql;
		 }

		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 랜덤하게 문제를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param courseid
	 * @param examid
	 * @param examorder
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getRandExamContentsList(String systemcode, CurriMemDTO curriinfo,String courseid,int examid) throws DAOException{
		ArrayList contentsList = new ArrayList();
		ExamContentsDTO contentsInfo = null;
		ResultSet rs = null;
	    StringBuffer output = new StringBuffer();
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();

			sb.append(" SELECT * ");
			sb.append(" FROM ");
			sb.append(" ( ");
			sb.append(" 	SELECT " +
								" exam_order, exam_type, contents, contents_text" +
								",example1, example2, example3, example4" +
								", example5, answer, multianswer, exam_comment" +
								", score, rfile_name, sfile_name, file_path" +
								", file_size, reg_id, reg_date ");
			//--	하나의 번호에 등록된 후보문제를 랜덤으로 돌리기
			sb.append(			", CASE WHEN (SELECT IFNULL(count(*), 1) as cnt " +
												" FROM  exam_contents " +
												" WHERE system_code = a.system_code AND curri_code = a.curri_code " +
												" AND curri_year = a.curri_year AND curri_term = a.curri_term " +
												" AND course_id =a.course_id AND exam_id = a.exam_id " +
												" AND exam_order = a.exam_order) = 1 THEN exam_no   " +
								" ELSE (SELECT ROUND(exam_no) " +
										" FROM exam_contents " +
										" WHERE system_code = a.system_code AND curri_code = a.curri_code " +
										" AND curri_year = a.curri_year AND curri_term = a.curri_term " +
										" AND course_id =a.course_id AND exam_id = a.exam_id " +
										" AND exam_order = a.exam_order " +
										" ORDER BY RAND() LIMIT 1 )  END AS exam_no ");
			sb.append(		" FROM exam_contents a ");
			sb.append(		" WHERE a.system_code = ? AND a.curri_code = ? " +
							" AND a.curri_year = ?  AND a.curri_term = ? " +
							" AND a.course_id = ? AND a.exam_id = ? ");
			sb.append(		" GROUP BY a.exam_order  ");
			sb.append(" ) X ");
			sb.append(" ORDER BY RAND() ");

			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setString(courseid);
			sql.setInteger(examid);

			sql.setSql(sb.toString());

			log.debug("[getRandExamContentsList]" + sql.toString());

			rs = broker.executeQuery(sql);
			while(rs.next()){
				contentsInfo = new ExamContentsDTO();
				contentsInfo.setExamOrder(rs.getInt("exam_order"));
        		contentsInfo.setExamNo(rs.getInt("exam_no"));
        		contentsInfo.setExamType(StringUtil.nvl(rs.getString("exam_type")));
        		contentsInfo.setContents(StringUtil.nvl(rs.getString("contents")));
        		contentsInfo.setContentsText(StringUtil.nvl(rs.getString("contents_text")));
        		contentsInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
        		contentsInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
        		contentsInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
        		contentsInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
        		contentsInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
        		contentsInfo.setAnswer(StringUtil.nvl(rs.getString("answer")));
        		contentsInfo.setScore(rs.getInt("score"));
        		contentsInfo.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
        		contentsInfo.setComment(StringUtil.nvl(rs.getString("exam_comment")));
        		contentsInfo.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
        		contentsInfo.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
        		contentsInfo.setFilePath(StringUtil.nvl(rs.getString("file_path")));
        		contentsInfo.setFileSize(StringUtil.nvl(rs.getString("file_size")));
        		contentsInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
                contentsInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

            	contentsList.add(contentsInfo);
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

		 return contentsList;
	}

	public HashMap getExamContentsScore(String systemcode, CurriMemDTO curriinfo,String courseid,int examid) throws DAOException{
        HashMap answerList = new HashMap();
		QueryStatement sql 	= 	new QueryStatement();

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select exam_order,exam_no,exam_type,answer,multianswer,score ");
		 sb.append(" from exam_contents ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? ");
		 sb.append(" and curri_term = ? and course_id = ? and exam_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setString(curriinfo.curriCode);
		 sql.setInteger(curriinfo.curriYear);
		 sql.setInteger(curriinfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);

		 log.debug("[getExamContentsScore]" + sql.toString());
         ExamContentsDTO contentsDto = null;
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 while(rs.next()){
			 	contentsDto = new ExamContentsDTO();
			 	contentsDto.setExamOrder(rs.getInt("exam_order"));
			 	contentsDto.setExamNo(rs.getInt("exam_no"));
			 	contentsDto.setExamType(StringUtil.nvl(rs.getString("exam_type")));
			 	contentsDto.setAnswer(StringUtil.nvl(rs.getString("answer")));
			 	contentsDto.setMultiAnswer(StringUtil.nvl(rs.getString("multianswer")));
			 	contentsDto.setScore(rs.getInt("score"));

			 	answerList.put(contentsDto.getExamOrder()+"/"+contentsDto.getExamNo(),contentsDto);
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

		 return answerList;
	}

	/**
	 * @param systemCode
	 * @param courseId
	 * @param bankCode
	 * @param examNo
	 * @param examId
	 * @param examOrder
	 * @param userId
	 */
	public boolean moveBankContents(String systemcode,CurriMemDTO curriinfo, String courseid, long bankcode, String[] examno, int examid, int examorder, String userid) throws DAOException{
		 boolean retVal 		= 	false;
		 QueryStatement[] sqls 	= 	new QueryStatement[examno.length];
		 QueryStatement sql = null;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into exam_contents" +
		 			"(system_code,curri_code,curri_year,curri_term" +
		 			",course_id,exam_id,exam_order,exam_no" +
		 			",exam_type,contents,contents_text,example1 ");
		 sb.append(" ,example2,example3,example4,example5" +
		 			",answer,multianswer,exam_comment,rfile_name" +
		 			",sfile_name,file_path,file_size, reg_id" +
		 			",reg_date) ");
		 sb.append(" select ?,?,?,?,?,?,");

		 if(examorder > 0){
			 sb.append("?,( select IFNULL(max(exam_no),0)+1 as exam_no " +
			 				"from exam_contents " +
			 				"where system_code = ? and curri_code = ? " +
			 				"and curri_year = ? and curri_term = ? ");
			 sb.append(		"and course_id = ? and exam_id = ? and exam_order = ?) ");
		 }else{
			 sb.append("	( select IFNULL(max(exam_order),0)+1 as exam_order " +
			 				"from exam_contents " +
			 				"where system_code = ? and curri_code = ? " +
			 				"and curri_year = ? and curri_term = ? ");
			 sb.append(		" and course_id = ? and exam_id = ? ),1 ");
		 }
		 sb.append(" ,exam_type,contents,contents_text,example1" +
		 			",example2,example3,example4,example5, ");
		 sb.append(" answer,multianswer,exam_comment,rfile_name" +
		 			",sfile_name,file_path,file_size,?" +
		 			",CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
		 sb.append(" from exam_bk_contents " +
		 			"where system_code = ? and course_id = ? " +
		 			"and exam_bk_code = ? and exam_no = ? ");

		 for(int i =0; i < examno.length; i++){
			 sql = new QueryStatement();
			 sql.setSql(sb.toString());
			 sql.setString(systemcode);
			 sql.setString(curriinfo.curriCode);
			 sql.setInteger(curriinfo.curriYear);
			 sql.setInteger(curriinfo.curriTerm);
			 sql.setString(courseid);
			 sql.setInteger(examid);
			 if(examorder > 0) sql.setInteger(examorder);
			 sql.setString(systemcode);
			 sql.setString(curriinfo.curriCode);
			 sql.setInteger(curriinfo.curriYear);
			 sql.setInteger(curriinfo.curriTerm);
			 sql.setString(courseid);
			 sql.setInteger(examid);
			 if(examorder > 0) sql.setInteger(examorder);
	         sql.setString(userid);
	    	 sql.setString(systemcode);
			 sql.setString(courseid);
			 sql.setLong(bankcode);
			 sql.setInteger(examno[i]);

			 sqls[i] = sql;
         }


		 log.debug("[moveBankContents]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	public boolean delExamContents(String systemcode,CurriMemDTO curriInfo,String courseid, int examid, int examorder, int examno) throws DAOException{
		 boolean retVal 			= 	false;
		 QueryStatement[] sqls = new QueryStatement[2];

		 QueryStatement sql 	= 	new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from exam_contents ");
		 sb.append(" WHERE  system_code = ? AND curri_code = ? AND curri_year = ? AND curri_term = ? ");
		 sb.append(" AND course_id = ? AND exam_id = ? and exam_order =? and exam_no = ?");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);
		 sql.setInteger(examorder);
		 sql.setInteger(examno);
		 sqls[0] = sql;

		 sql 	= 	new QueryStatement();
		 sb.delete(0,sb.length());
		 sb.append(" update  exam_contents ");
		 sb.append(" set exam_no  = exam_no - 1 ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		 sb.append(" and course_id = ? and exam_id = ? and exam_order = ? and exam_no > ? ");
	   	 sql.setSql(sb.toString());
	   	 sql.setString(systemcode);
		 sql.setString(curriInfo.curriCode);
		 sql.setInteger(curriInfo.curriYear);
		 sql.setInteger(curriInfo.curriTerm);
		 sql.setString(courseid);
		 sql.setInteger(examid);
		 sql.setInteger(examorder);
		 sql.setInteger(examno);
		 sqls[1] = sql;

		 try{
		     retVal 		= 	broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}

	/**
	 * 시험 첨부 파일을 삭제한다.
	 * 2007.06.19 sangsang
	 * @param examContentsDto
	 * @return
	 * @throws DAOException
	 */
	public int delExamContentsFile(ExamContentsDTO examContentsDto) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update exam_contents set rfile_name='', sfile_name='', file_path='', file_size='', mod_id=?, mod_date=getCurrentDate()");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ? and course_id=? and exam_id=? and exam_no = ? and exam_order = ?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(examContentsDto.getModId());
		sql.setString(examContentsDto.getSystemCode());
		sql.setString(examContentsDto.getCurriCode());
		sql.setInteger(examContentsDto.getCurriYear());
		sql.setInteger(examContentsDto.getCurriTerm());
		sql.setString(examContentsDto.getCourseId());
		sql.setInteger(examContentsDto.getExamId());
		sql.setInteger(examContentsDto.getExamNo());
		sql.setInteger(examContentsDto.getExamOrder());

		log.debug("[delExamContentsFile]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}
