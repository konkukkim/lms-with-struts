package com.edutrack.curritop.dao;


import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.curritop.dto.CurriCategoryDTO;
import com.edutrack.curritop.dto.CurriCategoryTotalDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


public class CurriCategoryDAO extends AbstractDAO {

	/**
	 * 과정분류의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(cate_code) as cnt from curri_category where system_code= ?");

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
	 * ==================================================
	 * 					과정분류코드 중복검사
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
	 * ==================================================
	 *
	 * @param SystemCode
	 * @param CateCode
	 * @param pCateStatus
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public int checkCode (String systemCode, String cateCode, String pCateStatus, String pStep) throws DAOException {
		int count = 0;

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");

		if (pStep.equals("1")) {
			sb.append(" COUNT(pare_code1) AS cnt ");
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? ");
		} else if (pStep.equals("2")) {
			sb.append(" COUNT(pare_code2) AS cnt ");
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND pare_code2 = ? ");
		} else {
			sb.append(" COUNT(cate_code) AS cnt ");
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND cate_code = ? ");
		}
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(cateCode);

		log.debug("[checkCode]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);

			if(rs.next()){
				count = rs.getInt("cnt");
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
		return count;
	}



	/**
	 * 과정분류의 카운트를 가져온다.(코드 존재 유무 확인용)
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CateCode) throws DAOException {
		int totCount = 0;

		 StringBuffer sb = new StringBuffer();
		 sb.append("select count(cate_code) as cnt from curri_category where system_code= ? and cate_code= ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CateCode);

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
	 * 과정분류 리스트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCategoryList(int curpage, String SystemCode, String pSec) throws DAOException {
		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("cate_code");
			sql.setCutCol("cate_code");
			sql.setAlias("cate_code, pare_code, cate_name, use_yn, use_name , reg_id, reg_date");
			sql.setSelect("cate_code, ifnull(pare_code,'0000') as pare_code, cate_name, use_yn, case use_yn when 'Y' then '<font color=blue>사용</font>' else '<font color=red>사용안함</font>' end as use_name , reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date");
			sql.setFrom(" curri_category");
			sql.setWhere(" system_code='"+SystemCode+"'");
			sql.setOrderby(" cate_code asc");

			//---- 디버그 출력
			log.debug("[getCategoryList]" + sql.toString());

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
	 * ==================================================
	 * 					과정분류 리스트
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2006.05.19
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ListDTO getPcategory1List(int curpage, String systemCode, String pStep) throws DAOException {
		ListDTO retVal = null;

		try{
			ListStatement sql = new ListStatement();

			if (pStep.equals("1")){
				sql.setTotalCol("pare_code1");
				sql.setCutCol("pare_code1");
				sql.setAlias(" pare_code1, cate_name, cate_depth, cate_status, cate_img1, cate_img2, cate_img3, use_name, reg_id, reg_date ");
				sql.setSelect(" pare_code1, cate_name, cate_depth, cate_status, cate_img1, cate_img2, cate_img3, "
						+ " 	use_yn, case use_yn when 'Y' then '<font color=blue>사용</font>' else '<font color=red>사용안함</font>' end as use_name, "
						+ " 	reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date ");
				sql.setFrom(" curri_pcategory1 ");
				sql.setWhere(" system_code = '"+systemCode+"' ");
				sql.setOrderby(" pare_code1 asc ");

			} else if (pStep.equals("2")){
				sql.setTotalCol("pare_code2");
				sql.setCutCol("pare_code2");
				sql.setAlias(" system_code, pare_code2, cate_name, cate_img1, cate_img2, cate_img3, use_name, reg_id, reg_date, pare_code1, step1_name ");
				sql.setSelect(" system_code, pare_code2, cate_name, cate_img1, cate_img2, cate_img3, "
						+ " 	use_yn, case use_yn when 'Y' then '<font color=blue>사용</font>' else '<font color=red>사용안함</font>' end as use_name, "
						+ " 	reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2) ) as reg_date, pare_code1, step1_name ");
				sql.setFrom(" (SELECT system_code, pare_code2, cate_name, cate_img1, cate_img2, cate_img3, use_yn, reg_id, reg_date, pare_code1, "
						+   " (SELECT cate_name FROM curri_pcategory1 WHERE system_code = a.system_code AND pare_code1 = a.pare_code1) AS step1_name "
						+   " FROM curri_pcategory2 a) b");
				sql.setWhere(" system_code = '"+systemCode+"' ");
				sql.setOrderby(" pare_code2 asc ");

			} else {
				sql.setTotalCol("cate_code");
				sql.setCutCol("cate_code");
				sql.setAlias(" system_code, pare_code1, pare_code2, cate_code, cate_name, cate_img1, cate_img2, cate_img3, use_name, reg_id, reg_date, step1_name, step2_name ");
				sql.setSelect(" system_code, pare_code1, pare_code2, cate_code, cate_name, cate_img1, cate_img2, cate_img3, "
						+     " use_yn, case use_yn when 'Y' then '<font color=blue>사용</font>' else '<font color=red>사용안함</font>' end as use_name, "
						+     " reg_id, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date, step1_name, step2_name ");
				sql.setFrom(" (SELECT system_code, pare_code1, pare_code2, cate_code, cate_name, cate_img1, cate_img2, cate_img3, use_yn, reg_id, reg_date, "
						+	" (SELECT cate_name FROM curri_pcategory1 WHERE system_code = a.system_code AND pare_code1 = a.pare_code1) AS step1_name, "
						+	" (SELECT cate_name FROM curri_pcategory2 WHERE system_code = a.system_code AND pare_code2 = a.pare_code2) AS step2_name "
						+	" FROM curri_category a) b");
				sql.setWhere(" system_code = '"+systemCode+"' ");
				sql.setOrderby(" cate_code asc ");
			}
			log.debug("[getPcategory1List]" + sql.toString());

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
	 * 과정분류 리스트전체를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public RowSet getCategoryListAll(String SystemCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
/*
		sb.append("select system_code, cate_code, cate_name from curri_category where system_code=? and use_yn='Y' ");
		sb.append(" order by cate_name");
*/
		sb.append(" SELECT X.* FROM ( ");
		sb.append(		" SELECT cp2.system_code, cp2.pare_code2 AS cate_code, cp2.cate_name " +
						" FROM curri_pcategory1 cp1, curri_pcategory2 cp2 ");
		sb.append(		" WHERE cp1.system_code = cp2.system_code AND cp1.pare_code1 = cp2.pare_code1	" +
						" AND cp2.use_yn = 'Y' and cp1.cate_depth = '2' ");
		sb.append(		" GROUP BY cp2.pare_code2 ");
		sb.append(		" UNION ");
		sb.append(		" SELECT cc.system_code, cc.cate_code, concat(cp2.cate_name, ' ', cc.cate_name) AS cate_name " +
						" FROM curri_pcategory1 cp1, curri_pcategory2 cp2 LEFT OUTER JOIN curri_category cc " +
						" ON cp2.system_code = cc.system_code AND cp2.pare_code1 = cc.pare_code1 " +
						" AND cp2.pare_code2 = cc.pare_code2 ");
		sb.append(		" WHERE cp1.system_code = cp2.system_code AND cp1.pare_code1 = cp2.pare_code1 " +
						" AND cc.use_yn = 'Y' AND cp1.cate_depth = '3' ");
		//sb.append(		" GROUP BY cc.pare_code2 ");
		sb.append(" ) X, curri_top ct ");
		sb.append(" WHERE X.system_code = ct.system_code " +
					" AND (X.cate_code = ct.pare_code2 OR X.cate_code = ct.cate_code) " +
					" AND X.system_code = ? ");
		sb.append(" GROUP BY X.cate_code ");
		sb.append(" ORDER BY X.cate_name ASC ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);

		log.debug("[getCategoryListAll]" + sql.toString());
		

		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}



	public RowSet getCategoryList1(String systemCode) throws DAOException {
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" SELECT ");
		sb.append(" system_code, pare_code1, cate_name ");
		sb.append(" FROM curri_pcategory1 ");
		sb.append(" WHERE system_code = ? AND use_yn='Y' ");
		sb.append(" ORDER BY pare_code1 ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);

		log.debug("[getCategoryListAll]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	public RowSet getCategoryCntList1(String systemCode, String userId) throws DAOException {
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" SELECT AA.system_code, AA.pare_code1, AA.cate_name, BB.cnt ");
		sb.append(" FROM " +
					" ( " +
							" SELECT " +
								" system_code, pare_code1, cate_name " +
							" FROM curri_pcategory1 " +
							" WHERE system_code = ? AND use_yn='Y'" +
					" ) AA LEFT OUTER JOIN " +
					" ( " +
							" SELECT " +
								" X.system_code, X.pare_code1, count(X.curri_code) AS cnt " +
							" FROM " +
							" ( " +
									" SELECT " +
										" s.system_code, s.curri_code " +
										", (SELECT pare_code1 FROM curri_top WHERE system_code = s.system_code AND curri_code = s.curri_code) AS pare_code1 " +
									" FROM student s, curri_sub cs " +
									" WHERE cs.system_code = s.system_code AND cs.curri_code = s.curri_code " +
									" AND cs.curri_year = s.curri_year AND cs.curri_term = s.curri_term " +
									" AND s.user_id = ? " +
							" ) X " +
							" GROUP BY X.system_code, X.pare_code1" +
					") BB ON AA.system_code = BB.system_code AND AA.pare_code1 = BB.pare_code1 ");
		sb.append(" ORDER BY AA.pare_code1 ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(userId);


		log.debug("[getCategoryCntList1]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	public RowSet getCategoryList2(String systemCode) throws DAOException {
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" SELECT ");
		sb.append(" system_code, pare_code1, pare_code2, cate_name ");
		sb.append(" FROM curri_pcategory2 ");
		sb.append(" WHERE system_code = ? AND use_yn='Y' ");
		sb.append(" ORDER BY pare_code2 ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);

		log.debug("[getCategoryListAll]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}




	/**
	 * ==================================================
	 * 						과정분류 정보
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
	 * ==================================================
	 *
	 * @param SystemCode
	 * @param CateCode
	 * @param pType
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCategoryInfo(String SystemCode, String CateCode, String pStep) throws DAOException {
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" SELECT ");

		if (pStep.equals("1")) {
			sb.append(" system_code, pare_code1, cate_name, cate_depth, cate_status, ");
		}
		else if (pStep.equals("2")) {
			sb.append(" system_code, pare_code1, pare_code2, cate_name, cate_info, ");
		}
		else if (pStep.equals("3")) {
			sb.append(" system_code, pare_code1, pare_code2, cate_code, cate_name, ");
		}
		sb.append(" cate_img1, case when cate_img1 is not null then concat(cate_img1,'<input type=hidden name=pOldFile[1] value=',cate_img1,'><br>') end as cate_img1_path, ");
		sb.append(" cate_img2, case when cate_img2 is not null then concat(cate_img2,'<input type=hidden name=pOldFile[2] value=',cate_img2,'><br>') end as cate_img2_path, ");
		sb.append(" cate_img3, case when cate_img3 is not null then concat(cate_img3,'<input type=hidden name=pOldFile[3] value=',cate_img3,'><br>') end as cate_img3_path, ");
		sb.append(" use_yn, reg_id, reg_date ");

		if (pStep.equals("1")) {
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? ");
		} else if (pStep.equals("2")) {
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND pare_code2 = ? ");
		} else {
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND cate_code = ? ");
		}

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CateCode);

		log.debug("[getCategoryInfo]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	/**
	 * ==================================================
	 * 						과정분류 상세정보 NEW
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
	 * ==================================================
	 *
	 * @param SystemCode
	 * @param CateCode
	 * @param pType
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCategoryInfo(String systemCode, String pStep, String pareCode1, String pareCode2, String cateCode) throws DAOException {
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" SELECT system_code, pare_code1, cate_name, ");

		if (pStep.equals("1")) {
			sb.append(" cate_status, cate_depth, ");
		}
		else if (pStep.equals("2")) {
			sb.append(" pare_code2, cate_info, ");
		}
		else if (pStep.equals("3")) {
			sb.append(" pare_code2, cate_code, ");
		}

		sb.append(" cate_img1, case when cate_img1 is not null then concat(cate_img1,'<input type=hidden name=pOldFile[1] value=',cate_img1,'><br>') end as cate_img1_path, ");
		sb.append(" cate_img2, case when cate_img2 is not null then concat(cate_img2,'<input type=hidden name=pOldFile[2] value=',cate_img2,'><br>') end as cate_img2_path, ");
		sb.append(" cate_img3, case when cate_img3 is not null then concat(cate_img3,'<input type=hidden name=pOldFile[3] value=',cate_img3,'><br>') end as cate_img3_path, ");
		sb.append(" use_yn, reg_id, reg_date, mod_id, mod_date ");

		if (pStep.equals("1")) {
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? ");
		}
		else if (pStep.equals("2")) {
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? AND pare_code2 = ? ");
		}
		else if (pStep.equals("3")) {
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? AND pare_code2 = ? AND cate_code = ? ");
		}

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		if (pStep.equals("1")) {
			sql.setString(pareCode1);
		}
		else if (pStep.equals("2")) {
			sql.setString(pareCode1);
			sql.setString(pareCode2);
		}
		else if (pStep.equals("3")) {
			sql.setString(pareCode1);
			sql.setString(pareCode2);
			sql.setString(cateCode);
		}

		log.debug("[getCategoryInfo]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	/**
	 * 개별 과정분류 정보를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCategoryInfo(String SystemCode, String CateCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, cate_code, pare_code, cate_name, cate_img1,case when cate_img1 is not null then concat(3cate_img1,'<input type=hidden name=pOldFile[1] value=',cate_img1,'><br>')  end as cate_img1_path, cate_img2, case when cate_img2 is not null then concat(cate_img2,'<input type=hidden name=pOldFile[2] value=',cate_img2,'><br>')  end as cate_img2_path, cate_img3, case when cate_img3 is not null then concat(cate_img3,'<input type=hidden name=pOldFile[3] value=',cate_img3,'><br>')  end as cate_img3_path, use_yn, reg_id, reg_date");
		sb.append(" from curri_category");
		sb.append(" where system_code=? and cate_code=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CateCode);

		//---- 디버그 출력
		log.debug("[getCategoryInfo]" + sql.toString());
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
	 * ==================================================
	 * 					과정분류 등록
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
	 * ==================================================
	 *
	 * @param curriPCategoryDto
	 * @param pType
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public int addPCategoryInfo (CurriCategoryTotalDTO curriCategoryTotalDto, String pStep) throws DAOException {
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		if (pStep.equals("1")) {
			sb.append(" INSERT INTO curri_pcategory1 (system_code, pare_code1, cate_name, cate_depth, cate_status, "
					+ " cate_img1, cate_img2, cate_img3, use_yn, reg_id, reg_date, mod_id, mod_date) ");
			sb.append(" VALUES (?, ?, ?, ?, ?, "
					+ " ?, ?, ?, ?, ?, ?, ?, ?) ");

		} else if (pStep.equals("2")) {
			sb.append(" INSERT INTO curri_pcategory2 (system_code, pare_code1, pare_code2, cate_info, cate_name, "
					+ " cate_img1, cate_img2, cate_img3, use_yn, reg_id, reg_date, mod_id, mod_date) ");
			sb.append(" VALUES (?, ?, ?, ?, ?, "
					+ " ?, ?, ?, ?, ?, ?, ?, ?) ");

		} else {
			sb.append(" INSERT INTO curri_category (system_code, pare_code1, pare_code2, cate_code, cate_name, "
					+ " cate_img1, cate_img2, cate_img3, use_yn, reg_id, reg_date, mod_id, mod_date) ");
			sb.append(" VALUES (?, ?, ?, ?, ?, "
					+ " ?, ?, ?, ?, ?, ?, ?, ?) ");
		}

		sql.setSql(sb.toString());
		sql.setString(curriCategoryTotalDto.getSystemCode());
		sql.setString(curriCategoryTotalDto.getPareCode1());

		if (pStep.equals("2")) {
			sql.setString(curriCategoryTotalDto.getPareCode2());
			sql.setString(curriCategoryTotalDto.getCateInfo());
		}
		else if (pStep.equals("3")) {
			sql.setString(curriCategoryTotalDto.getPareCode2());
			sql.setString(curriCategoryTotalDto.getCateCode());
		}
		sql.setString(curriCategoryTotalDto.getCateName());

		if (pStep.equals("1")) {
			sql.setInteger(curriCategoryTotalDto.getCateDepth());
			sql.setString(curriCategoryTotalDto.getCateStatus());
		}
		sql.setString(curriCategoryTotalDto.getCateImg1());
		sql.setString(curriCategoryTotalDto.getCateImg2());
		sql.setString(curriCategoryTotalDto.getCateImg3());
		sql.setString(curriCategoryTotalDto.getUseYn());
		sql.setString(curriCategoryTotalDto.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriCategoryTotalDto.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addCategoryInfo]" + sql.toString());

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
	 * 					과정분류정보 수정
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
	 * ==================================================
	 *
	 * @param curriPCategoryDto
	 * @param pType
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public int editPCategoryInfo(CurriCategoryTotalDTO curriCategoryTotalDto, String pStep) throws DAOException{
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		if (pStep.equals("1")) {
			sb.append(" UPDATE curri_pcategory1 SET pare_code1 = ?, cate_name = ?, cate_depth = ?, cate_status = ?, cate_img1 = ?, cate_img2 = ?, cate_img3 = ?, use_yn = ?, mod_id = ?, mod_date = ? ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? ");

		} else if (pStep.equals("2")) {
			sb.append(" UPDATE curri_pcategory2 SET pare_code1 = ?, pare_code2 = ?, cate_info = ?, cate_name = ?, cate_img1 = ?, cate_img2 = ?, cate_img3 = ?, use_yn = ?, mod_id = ?, mod_date = ? ");
			sb.append(" WHERE system_code = ? AND pare_code2 = ? ");

		} else {
			sb.append(" UPDATE curri_category SET pare_code1 = ?, pare_code2 = ?, cate_code = ?, cate_name = ?, cate_img1 = ?, cate_img2 = ?, cate_img3 = ?, use_yn = ?, mod_id = ?, mod_date = ? ");
			sb.append(" WHERE system_code = ? AND cate_code = ? ");
		}
		sql.setSql(sb.toString());
		sql.setString(curriCategoryTotalDto.getPareCode1());

		if (pStep.equals("2")) {
			sql.setString(curriCategoryTotalDto.getPareCode2());
			sql.setString(curriCategoryTotalDto.getCateInfo());
		}
		else if (pStep.equals("3")) {
			sql.setString(curriCategoryTotalDto.getPareCode2());
			sql.setString(curriCategoryTotalDto.getCateCode());
		}
		sql.setString(curriCategoryTotalDto.getCateName());

		if (pStep.equals("1")) {
			sql.setInteger(curriCategoryTotalDto.getCateDepth());
			sql.setString(curriCategoryTotalDto.getCateStatus());
		}

		sql.setString(curriCategoryTotalDto.getCateImg1());
		sql.setString(curriCategoryTotalDto.getCateImg2());
		sql.setString(curriCategoryTotalDto.getCateImg3());
		sql.setString(curriCategoryTotalDto.getUseYn());
		sql.setString(curriCategoryTotalDto.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(curriCategoryTotalDto.getSystemCode());

		if (pStep.equals("1")) {
			sql.setString(curriCategoryTotalDto.getPareCode1());
		} else if (pStep.equals("2")) {
			sql.setString(curriCategoryTotalDto.getPareCode2());
		} else {
			sql.setString(curriCategoryTotalDto.getCateCode());
		}
		log.debug("[editPCategoryInfo]" + sql.toString());

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
	 *
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.24
	 * ==================================================
	 *
	 * @param systemCode
	 * @param cateCode
	 * @return
	 * @throws DAOException
	 */
	public int getCategoryCount(String systemCode, String cateCode, String pStep) throws DAOException {
		int cnt = 0;

		StringBuffer sb = new StringBuffer();

		if (pStep.equals("1")) {
			sb.append(" SELECT ");
			sb.append(" COUNT(pare_code1) AS cnt ");
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? ");
		} else if (pStep.equals("2")) {
			sb.append(" SELECT ");
			sb.append(" COUNT(pare_code2) AS cnt ");
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND pare_code2 = ? ");
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(cateCode);

		log.debug("[getCategoryCount]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);

			if (rs.next()) {
				cnt = rs.getInt("cnt");
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
		return cnt;
	}


	/**
	 * ==================================================
	 * 					소분류 개수
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.24
	 * ==================================================
	 *
	 * @param systemCode
	 * @param cateCode
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public int getCount(String systemCode, String cateCode, String pStep) throws DAOException {
		int cnt = 0;

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append(" COUNT(pare_code1) AS cnt ");
		sb.append(" FROM curri_category ");
		sb.append(" WHERE system_code = ? AND pare_code1 = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(cateCode);

		log.debug("[getCount]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);

			if (rs.next()) {
				cnt = rs.getInt("cnt");
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
		return cnt;
	}


	/**
	 * ==================================================
	 * 					과정분류 개수
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.24
	 * ==================================================
	 * @param systemCode
	 * @param cateCode
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public int getCategoryCount(String systemCode, String pStep) throws DAOException {
		int cnt = 0;

		StringBuffer sb = new StringBuffer();

		if (pStep.equals("2")) {
			sb.append(" SELECT ");
			sb.append(" COUNT(pare_code1) AS cnt ");
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? ");
		} else if (pStep.equals("3")) {
			sb.append(" SELECT ");
			sb.append(" COUNT(pare_code2) AS cnt ");
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? ");
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);

		log.debug("[getCategoryCount]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);

			if (rs.next()) {
				cnt = rs.getInt("cnt");
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
		return cnt;
	}


	/**
	 * 과정분류 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCategoryInfo(CurriCategoryDTO cateInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into curri_category(system_code, cate_code, pare_code, cate_name, cate_img1, cate_img2, cate_img3, "
				+ " use_yn, reg_id, reg_date, mod_id, mod_date)");
		sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(cateInfo.getSystemCode());
		sql.setString(cateInfo.getCateCode());
		sql.setString(cateInfo.getPareCode());
		sql.setString(cateInfo.getCateName());
		sql.setString(cateInfo.getCateImg1());
		sql.setString(cateInfo.getCateImg2());
		sql.setString(cateInfo.getCateImg3());
		sql.setString(cateInfo.getUseYn());
		sql.setString(cateInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(cateInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		//---- 디버그 출력
		log.debug("[addCategoryInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 과정분류 정보를 수정한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCategoryInfo(CurriCategoryDTO cateInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update curri_category set pare_code=?, cate_name=?, cate_img1=?, cate_img2=?, cate_img3=?, use_yn=?, mod_id=?, mod_date=?");
		sb.append(" where system_code=? and cate_code=?");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(cateInfo.getPareCode());
		sql.setString(cateInfo.getCateName());
		sql.setString(cateInfo.getCateImg1());
		sql.setString(cateInfo.getCateImg2());
		sql.setString(cateInfo.getCateImg3());
		sql.setString(cateInfo.getUseYn());
		sql.setString(cateInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(cateInfo.getSystemCode());
		sql.setString(cateInfo.getCateCode());

		//---- 디버그 출력
		log.debug("[editCategoryInfo]" + sql.toString());
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
	 * 						과정분류 정보
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
	 * ==================================================
	 *
	 * @param systemCode
	 * @param cateCode
	 * @param pType
	 * @param pStep
	 * @return
	 * @throws DAOException
	 */
	public int deleteCategoryInfo (String systemCode, String cateCode, String pStep) throws DAOException {
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		if (pStep.equals("1")) {
			sb.append(" DELETE FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND pare_code1 = ? ");

		} else if (pStep.equals("2")) {
			sb.append(" DELETE FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND pare_code2 = ? ");

		} else {
			sb.append(" DELETE FROM curri_category ");
			sb.append(" WHERE system_code = ? AND cate_code = ? ");
		}

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(cateCode);

		log.debug("[deleteCategoryInfo]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 과정분류 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int delCategoryInfo(String SystemCode, String CateCode) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_category where system_code=? and cate_code=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CateCode);

		//---- 디버그 출력
		log.debug("[delCategoryInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	//-- DAO
	public RowSet getPCategory1ListAll(String systemCode) throws DAOException {
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" SELECT ");
		sb.append(" system_code, pare_code1, cate_name, cate_status ");
		sb.append(" FROM curri_pcategory1 ");
		sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");
		sb.append(" ORDER BY pare_code1 ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);

		log.debug("[getPCategory1ListAll]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	/**
	 * ==================================================
	 * 					상위 과정분류 리스트
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2006.05.19
	 * ==================================================
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCategory (String systemCode, String pStep) throws DAOException {
		ArrayList pCategory = new ArrayList();
//		CurriPCategoryDTO curriPCategoryDto = null;

		StringBuffer sb = new StringBuffer();

		if (pStep.equals("2")) {
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, cate_name, cate_status, cate_depth ");
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");
			sb.append(" ORDER BY pare_code1 ");
		} else if (pStep.equals("3")) {
			sb.append(" SELECT ");
			sb.append(" p2.system_code, p1.cate_name as pare_code1_name, p2.pare_code1, p2.pare_code2, ");
			sb.append(" p2.cate_name as pare_code2_name, p2.cate_info ");
			sb.append(" FROM curri_pcategory2 p2, curri_pcategory1 p1 ");
			sb.append(" WHERE p2.system_code = ? ");
			sb.append(" AND p2.use_yn = 'Y' ");
			sb.append(" AND p1.system_code = p2.system_code ");
			sb.append(" AND p1.pare_code1 = p2.pare_code1 ");
			sb.append(" AND p1.cate_status='0' ");
			sb.append(" ORDER BY p2.pare_code1, p2.pare_code2 ");
		} else {
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, pare_code2, cate_code, cate_name ");
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");
			sb.append(" ORDER BY cate_code ");
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);

		log.debug("[getPCategory]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			CodeDTO code = null;

			while (rs.next()) {
				if (pStep.equals("2")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
				} else if (pStep.equals("3")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString("system_code"), rs.getString("pare_code1"), rs.getString("pare_code2"), "",
							rs.getString("pare_code1_name"), rs.getString("pare_code2_name"), "" ));

				} else {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return pCategory;
	}



	public ArrayList getCategory2 (String systemCode, String pStep, String cateCode) throws DAOException {
		ArrayList pCategory = new ArrayList();
//		CurriPCategoryDTO curriPCategoryDto = null;

		StringBuffer sb = new StringBuffer();

		if (pStep.equals("3")) {
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, pare_code2, cate_name ");
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");
			if (!cateCode.equals(""))
				sb.append(" AND pare_code1 = ? ");
			sb.append(" ORDER BY pare_code1, pare_code2 ");
		} else if (pStep.equals("4")) {
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, pare_code2, cate_code, cate_name ");
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");
			if (!cateCode.equals(""))
				sb.append(" AND pare_code2 = ? ");
			sb.append(" ORDER BY pare_code1, pare_code2, cate_code ");
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		if (!cateCode.equals(""))
			sql.setString(cateCode);


		log.debug("[getPCategory]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			CodeDTO code = null;

			while (rs.next()) {
				if (pStep.equals("3")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				} else if (pStep.equals("4")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return pCategory;
	}

	public ArrayList getCategory3 (String systemCode, String pStep, String pare_code1, String pare_code2) throws DAOException {
		ArrayList pCategory = new ArrayList();
//		CurriPCategoryDTO curriPCategoryDto = null;

		StringBuffer sb = new StringBuffer();

		if (pStep.equals("1")) {				// 대분류 추출
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, cate_name ");
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");
			sb.append(" ORDER BY pare_code1 ");
		}
		else if (pStep.equals("2")) {			// 중분류 추출
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, pare_code2, cate_name ");
			sb.append(" FROM curri_pcategory2 ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' AND pare_code1 = ? ");
			sb.append(" ORDER BY pare_code2 ");
		}
		else if (pStep.equals("3")) {			// 소분류 추출
			sb.append(" SELECT ");
			sb.append(" system_code, pare_code1, pare_code2, cate_code, cate_name ");
			sb.append(" FROM curri_category ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' AND pare_code1 = ? AND pare_code2 = ? ");
			sb.append(" ORDER BY cate_code ");
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);

		if (pStep.equals("2")) {
			sql.setString(pare_code1);
		}
		else if (pStep.equals("3")) {
			sql.setString(pare_code1);
			sql.setString(pare_code2);
		}

		log.debug("[getPCategory]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			CodeDTO code = null;

			while (rs.next()) {
				if (pStep.equals("1")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3)));
				}
				else if (pStep.equals("2")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				}
				else if (pStep.equals("3")) {
					pCategory.add(new CurriCategoryTotalDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return pCategory;
	}


	public String getPcategory1List(String systemCode, String pStep, String pare_code1, String pare_code2) throws DAOException {
		String	fCode	=	"";

		StringBuffer sb = new StringBuffer();

		if (pStep.equals("1")) {				// 대분류코드 추출 (최상위 1개)
			sb.append(" SELECT ");
			sb.append(" pare_code1 ");
			sb.append(" FROM curri_pcategory1");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y'");
			sb.append(" ORDER BY pare_code1 ");
		}
		else if (pStep.equals("2")) {			// 중분류 추출 (최상위 1개)
			sb.append(" SELECT ");
			sb.append(" pare_code2 ");
			sb.append(" FROM curri_pcategory2");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' AND pare_code1 = ?");
			sb.append(" ORDER BY pare_code2 ");
		}
		else if (pStep.equals("3")) {			// 소분류 추출 (최상위 1개)
			sb.append(" SELECT ");
			sb.append(" cate_code ");
			sb.append(" FROM curri_category");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' AND pare_code1 = ? AND pare_code2 = ?  ");
			sb.append(" ORDER BY cate_code ");
		}
		sb.append(" LIMIT 1");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);

		if (pStep.equals("2")) {
			sql.setString(pare_code1);
		}
		else if (pStep.equals("3")) {
			sql.setString(pare_code1);
			sql.setString(pare_code2);
		}

		log.debug("[getPCategory]" + sql.toString());

		RowSet rs = null;

		try{
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				fCode	=	StringUtil.nvl(rs.getString(1), "");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try{
				if(rs != null) rs.close();
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		}
		return fCode;
	}



}