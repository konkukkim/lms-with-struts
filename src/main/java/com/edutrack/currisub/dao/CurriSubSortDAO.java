/*
 * Created on 2004. 11. 24.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.currisub.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriSubSortDAO extends AbstractDAO {

	/**
	 *
	 */
	public CurriSubSortDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList getCurriSortList(String SystemCode, String CurriCode, int CurriYear,int CurriTerm, int firstnum) throws DAOException {
		ArrayList curriCourseList = null;

		StringBuffer sb = new StringBuffer();
		sb.append(" select  c.course_id, c.contents_id,( select course_name from course where system_code = c.system_code and course_id = c.course_id ) as course_name, ");
		sb.append(" c.contents_name, c.seq_no ");
		sb.append(" from curri_contents c ");
		sb.append(" where c.system_code= ? and c.curri_code= ? ");
		sb.append(" and c.curri_year=? and c.curri_term= ? and c.contents_type = 'F' ");

		if(firstnum > 0)
			sb.append(" order by  seq_no ");
        else
			sb.append(" order by course_id, contents_order ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);

		log.debug("[getCurriSortList]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 CurriContentsDTO curriContentsDto = null;
			 curriCourseList = new ArrayList();

			 while(rs.next()){
			 	curriContentsDto = new  CurriContentsDTO();
			 	curriContentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			 	curriContentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			 	curriContentsDto.setCourseName(StringUtil.nvl(rs.getString("course_name")));
			 	curriContentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
			 	curriContentsDto.setSeqNo(rs.getInt("seq_no"));
			 	curriCourseList.add(curriContentsDto);
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
		return curriCourseList;
	}

	public int getCurriSortFirst(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		int firstNum = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append(" select seq_no ");
		 sb.append(" from curri_contents c ");
		 sb.append(" where c.system_code= ? and c.curri_code= ? ");
		 sb.append(" and c.curri_year= ? and c.curri_term= ? and c.contents_type = 'F' ");	//and limit 1
		 sb.append(" limit 1 ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);


		 log.debug("[getCurriSortFirst]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	firstNum = rs.getInt("seq_no");
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
		return firstNum;
	}

	public boolean editCurriSortList(String systemCode, String curriCode, int curriYear, int curriTerm, ArrayList contentlist) throws DAOException{
		boolean retVal = false;
		QueryStatement sql = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" update curri_contents set seq_no = ? ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and contents_id = ? ");

		ISqlStatement[] stateArray = new ISqlStatement[contentlist.size()];
		String[] info = null;
		for(int i =0; i < contentlist.size(); i++){
			sql = new QueryStatement();
			sql.setSql(sb.toString());
            info = (String[])contentlist.get(i);
            sql.setInteger(info[2]);
            sql.setString(systemCode);
            sql.setString(curriCode);
            sql.setInteger(curriYear);
            sql.setInteger(curriTerm);
            sql.setString(info[0]);
            sql.setString(info[1]);

            stateArray[i] = sql;
		}

		log.debug("[editCurriSortList]" + sql.toString());
		try{
			retVal = broker.executeUpdate(stateArray);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


}
