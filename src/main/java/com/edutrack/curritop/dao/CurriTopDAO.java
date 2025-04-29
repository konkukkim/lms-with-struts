package com.edutrack.curritop.dao;


import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.curritop.dto.CurriTopDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


public class CurriTopDAO extends AbstractDAO {

	/**
	 * 과정의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(curri_code) as cnt from curri_top where system_code= ?");

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
	 * 분류에 해당하는 과정의 카운트를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int getCategoryCount(String SystemCode, String CateCode) throws DAOException {
		int totCount = 0;

		StringBuffer sb = new StringBuffer();

		sb.append("select count(curri_code) as cnt from curri_top where system_code= ? and cate_code= ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CateCode);

		log.debug("[getTotCount]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);

			if (rs.next()) {
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
	 * 과정의 카운트를 가져온다.(코드 존재 유무 확인용)
	 * @param SystemCode
	 * @param CurriCode
	 * @return
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CurriCode) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(curri_code) as cnt from curri_top where system_code= ? and curri_code= ? ");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);

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
	 * ==================================================
	 * 					과정 리스트
	 * ==================================================
	 * @param curpage
	 * @param SystemCode
	 * @param Property1
	 * @param Target
	 * @param Word
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCurriTopList(int curpage, String SystemCode, String Property1, String Property2, String Target, String Word) throws DAOException {
		ListDTO retVal = null;
		try{

			String addWhere = "";
			ListStatement sql = new ListStatement();

			if(!Target.equals("") && !Word.equals(""))
				addWhere = " and "+Target+" like ?";

			sql.setTotalCol(" X.curri_code ");
			sql.setCutCol(	" X.curri_code ");
			sql.setSelect(	" X.curri_code, X.pare_code1, X.pare_code2, X.cate_code" +
							", X.curri_name, X.credit, X.curri_property1, X.curri_property2" +
							", X.property2_name, X.course_cnt, X.reg_id, X.reg_date" +
							", CASE X.cate_depth WHEN '2' THEN pare_name2 WHEN '3' THEN cate_name3 END AS cate_name ");

			StringBuffer	sbFrom	=	new StringBuffer();
			sbFrom.append(" ( ");
			sbFrom.append(		" SELECT" +
									" a.system_code, a.curri_code, a.pare_code1, a.pare_code2" +
									", a.cate_code, a.curri_name, a.credit, a.curri_property1" +
									", a.curri_property2, c.so_name as property2_name, b.course_cnt" +
									", a.reg_id" +
									", CONCAT(SUBSTRING(a.reg_date,1,4),'-',SUBSTRING(a.reg_date,5,2),'-',SUBSTRING(a.reg_date,7,2)) AS REG_DATE" +
									", (SELECT cate_name " +
										" FROM curri_pcategory2 " +
										" WHERE system_code = a.system_code AND pare_code1 = a.pare_code1" +
										" AND pare_code2 = a.pare_code2) AS pare_name2 " +
									", (SELECT cate_name " +
										" FROM curri_category " +
										" WHERE system_code = a.system_code AND pare_code1 = a.pare_code1 " +
										" AND pare_code2 = a.pare_code2 AND cate_code = a.cate_code) AS cate_name3 " +
									", (SELECT cate_depth " +
										" FROM curri_pcategory1 " +
										" WHERE system_code = a.system_code AND pare_code1 = a.pare_code1) AS cate_depth   ");
			sbFrom.append(		" FROM" +
									" curri_top a LEFT OUTER JOIN " +
									" (SELECT " +
										" system_code, curri_code, count(course_id) as course_cnt " +
										" FROM curri_top_course " +
										" GROUP BY system_code, curri_code) b " +
									" ON (a.system_code = b.system_code AND a.curri_code = b.curri_code) " +
									", code_so c ");
			sbFrom.append(		" WHERE " +
									" a.system_code = c.system_code AND a.curri_property1 = c.code_dae " +
									" AND a.curri_property2 = c.code_so AND a.system_code = ? " +
									" AND a.curri_property1 = ? ");			
			sql.setString(SystemCode);
			sql.setString(Property1);
			
			if(!Property2.equals("")) {
				sbFrom.append(	" AND a.curri_property2 = ? ");
				sql.setString(Property2);
			}

			sbFrom.append(		" ORDER BY a.reg_date DESC ");
			sbFrom.append(" ) X  ");

			//-- FROM절 세팅
			sql.setFrom(sbFrom.toString());
			

			//-- 검색어 있을때에 WHERE 절 세팅
			if(!Target.equals("") && !Word.equals("")) {
				StringBuffer	sbWhere	=	new StringBuffer();
				sbWhere.append(" X."+Target+" like ? ");
				sql.setWhere(sbWhere.toString());

				sql.setString("%"+Word+"%");
			}


			log.debug("[getCurriTopList]" + sql.toString());
			//System.out.println("[getCurriTopList]" + sql.toString());
			

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
	 * 과정 리스트를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriTopListAll(String SystemCode, String CateCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		RowSet rs = null;

		sb.append(	"SELECT curri_code, curri_name " +
					"FROM curri_top " +
					"WHERE system_code=? and (cate_code=? or pare_code2 = ?) " +
					"ORDER BY curri_name");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CateCode);
		sql.setString(CateCode);

		log.debug("[getCategoryListAll]" + sql.toString());
		try{
			rs = broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 과정 리스트를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @param pProperty1
	 * @param pProperty2
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriTopListAll(String SystemCode, String CateCode, String pProperty1, String pProperty2) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		RowSet rs = null;

		sb.append("select curri_code, curri_name from curri_top where system_code=? ");
		if(!CateCode.equals(""))
			sb.append(" and cate_code = '"+CateCode+"' ");
		if(!pProperty1.equals(""))
			sb.append(" and curri_property1 = '"+pProperty1+"' ");
		if(!pProperty2.equals(""))
			sb.append(" and curri_property2 = '"+pProperty2+"' ");
		sql.setString(SystemCode);
		//sb.append(" order by curri_code");
		sb.append(" order by curri_name");
		sql.setSql(sb.toString());
		log.debug("[getCategoryListAll]" + sql.toString());
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	public CurriTopDTO getCurriTopInfo(String systemCode, String curriCode) throws DAOException {
		CurriTopDTO curriTopDto	= null;
		QueryStatement sql		= new QueryStatement();
		StringBuffer sb 		= new StringBuffer();

		sb.append(" select /*+ FULL (t) */ ");
		sb.append(" t.system_code, ");
		sb.append(" (select p1.cate_name from curri_pcategory1 p1 where p1.pare_code1=t.pare_code1) as pare_code1_name, ");
		sb.append(" (select p2.cate_name from curri_pcategory2 p2 where p2.pare_code1=t.pare_code1 and p2.pare_code2=t.pare_code2) as pare_code2_name, ");
		sb.append(" (select c.cate_name from curri_category c where c.pare_code1=t.pare_code1 and c.pare_code2=t.pare_code2 and c.cate_code=t.cate_code) as cate_name, ");
		sb.append(" t.curri_code, t.pare_code1, t.pare_code2, t.cate_code, t.curri_property1, t.curri_property2, t.curri_name, ");
		sb.append(" t.credit, t.curri_goal, t.curri_info, t.curri_env, t.assessment, t.before_info, t.learning_time, ");
		sb.append(" t.curri_target, t.curri_contents, t.curri_contents_text, ifnull(t.curri_img1,'') as curri_img1, ");
		sb.append(" ifnull(t.curri_img2 , '') curri_img2, t.reg_id, t.reg_date ");
		sb.append(" FROM curri_top t ");
		sb.append(" WHERE t.system_code = ? AND t.curri_code = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);

		//---- 디버그 출력
		log.debug("[getCurriTopInfo]" + sql.toString());
		ResultSet rs				= null;
	    StringBuffer output = new StringBuffer();

		try{
			rs		= broker.executeQuery(sql);

			if(rs.next()){
				curriTopDto = new CurriTopDTO();
				curriTopDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				curriTopDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				curriTopDto.setPareCode1((StringUtil.nvl(rs.getString("pare_code1"))));
				curriTopDto.setPareCode2((StringUtil.nvl(rs.getString("pare_code2"))));
				curriTopDto.setCateCode(StringUtil.nvl(rs.getString("cate_code")));
				curriTopDto.setPareCode1Name((StringUtil.nvl(rs.getString("pare_code1_name"))));
				curriTopDto.setPareCode2Name((StringUtil.nvl(rs.getString("pare_code2_name"))));
				curriTopDto.setCateCodeName((StringUtil.nvl(rs.getString("cate_name"))));
				curriTopDto.setCurriProperty1(StringUtil.nvl(rs.getString("curri_property1")));
				curriTopDto.setCurriProperty2(StringUtil.nvl(rs.getString("curri_property2")));
				curriTopDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				curriTopDto.setCredit(rs.getInt("credit"));
				curriTopDto.setCurriGoal(StringUtil.nvl(rs.getString("curri_goal")));
				curriTopDto.setCurriInfo(StringUtil.nvl(rs.getString("curri_info")));
				curriTopDto.setCurriEnv(StringUtil.nvl(rs.getString("curri_env")));
				curriTopDto.setAssessment(StringUtil.nvl(rs.getString("assessment")));
				curriTopDto.setBeforeInfo(StringUtil.nvl(rs.getString("before_info")));
				curriTopDto.setLearningTime(StringUtil.nvl(rs.getString("learning_time")));
				curriTopDto.setCurriTarget(StringUtil.nvl(rs.getString("curri_target")));
				curriTopDto.setCurriContentsText(StringUtil.nvl(rs.getString("curri_contents_text")));
				curriTopDto.setCurriImg1(StringUtil.nvl(rs.getString("curri_img1")));
				curriTopDto.setCurriImg2(StringUtil.nvl(rs.getString("curri_img2")));
				curriTopDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				curriTopDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				if (!StringUtil.nvl(rs.getString("curri_contents_text")).equals("")) {
					curriTopDto.setCurriContents(StringUtil.nvl(rs.getString("curri_contents")));
				}
				rs.close();
			}
		 } catch(Exception e) {
			 log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }
		log.debug("------ getCurriTopInfo End   --------");
		return curriTopDto;
	}


	/**
	 * ==================================================
	 * 					과정정보 등록
	 * ==================================================
	 * @param curriInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCurriTopInfo(CurriTopDTO curriInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" INSERT INTO curri_top ");
		sb.append(" (system_code, curri_code, pare_code1, pare_code2, cate_code, curri_property1, curri_property2, ");
		sb.append(" curri_name, credit, curri_goal, curri_info, curri_env, assessment, before_info, learning_time, ");
		sb.append(" curri_target, curri_contents, curri_contents_text, curri_img1, curri_img2, reg_id, reg_date, mod_id, mod_date) ");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ");
		sb.append(" ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriInfo.getSystemCode());
		sql.setString(curriInfo.getCurriCode());
		sql.setString(curriInfo.getPareCode1());
		sql.setString(curriInfo.getPareCode2());
		sql.setString(curriInfo.getCateCode());
		sql.setString(curriInfo.getCurriProperty1());
		sql.setString(curriInfo.getCurriProperty2());

		sql.setString(curriInfo.getCurriName());
		sql.setInteger(curriInfo.getCredit());
		sql.setString(curriInfo.getCurriGoal());
		sql.setString(curriInfo.getCurriInfo());
		sql.setString(curriInfo.getCurriEnv());
		sql.setString(curriInfo.getAssessment());
		sql.setString(curriInfo.getBeforeInfo());
		sql.setString(curriInfo.getLearningTime());

		sql.setString(curriInfo.getCurriTarget());
		sql.setString(curriInfo.getCurriContents());
		sql.setString(curriInfo.getCurriContentsText());
		sql.setString(curriInfo.getCurriImg1());
		sql.setString(curriInfo.getCurriImg2());
		sql.setString(curriInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		//---- 디버그 출력
		log.debug("[addCurriTopInfo]" + sql.toString());

		Connection conn 			= null;
		DBConnectionManager pool 	= null;
		ResultSet rs 				= null;
		QueryStatement sql2 	= new QueryStatement();
		StringBuffer sb2 		= new StringBuffer();

		try{
			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );

		    retVal = broker.executeUpdate(sql,conn);

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

		return retVal;
	}


	/**
	 * ==================================================
	 * 					과정정보 수정
	 * ==================================================
	 *
	 * @param curriInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCurriTopInfo(CurriTopDTO curriInfo, String pChkEditCode) throws DAOException{
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" UPDATE curri_top ");
		sb.append(" SET curri_code = ?, ");
		if (pChkEditCode.equals("T"))
			sb.append(" pare_code1 = ?, pare_code2 = ?, cate_code = ?, ");
		sb.append(" curri_property1 = ?, curri_property2 = ?, curri_name = ?, ");
		sb.append(" credit = ?, curri_goal = ?, curri_info = ?, curri_env = ?, assessment = ?, before_info = ?, learning_time = ?, curri_target = ?, ");
		sb.append(" curri_contents = ?, curri_contents_text = ?, curri_img1 = ?, curri_img2 = ?, mod_id = ?, mod_date = ? ");
		sb.append(" WHERE system_code = ? AND curri_code = ? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriInfo.getCurriCode());
		if (pChkEditCode.equals("T")) {
			sql.setString(curriInfo.getPareCode1());
			sql.setString(curriInfo.getPareCode2());
			sql.setString(curriInfo.getCateCode());
		}
		sql.setString(curriInfo.getCurriProperty1());
		sql.setString(curriInfo.getCurriProperty2());
		sql.setString(curriInfo.getCurriName());
		sql.setInteger(curriInfo.getCredit());
		sql.setString(curriInfo.getCurriGoal());
		sql.setString(curriInfo.getCurriInfo());
		sql.setString(curriInfo.getCurriEnv());
		sql.setString(curriInfo.getAssessment());
		sql.setString(curriInfo.getBeforeInfo());
		sql.setString(curriInfo.getLearningTime());
		sql.setString(curriInfo.getCurriTarget());
		sql.setString(curriInfo.getCurriContents());
		sql.setString(curriInfo.getCurriContentsText());
		sql.setString(curriInfo.getCurriImg1());
		sql.setString(curriInfo.getCurriImg2());
		sql.setString(curriInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		//--
		sql.setString(curriInfo.getSystemCode());
		sql.setString(curriInfo.getCurriCode());

		//---- 디버그 출력
		log.debug("[editCurriTopInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}


	/**
	 * ==================================================
	 * 					과정정보 삭제
	 * ==================================================
	 * @param SystemCode
	 * @param CurriCode
	 * @return
	 * @throws DAOException
	 */
	public int delCurriTopInfo(String SystemCode, String CurriCode) throws DAOException{
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" DELETE FROM curri_top ");
		sb.append(" WHERE system_code = ? AND curri_code = ? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);

		log.debug("[delCategoryInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
}
