/**
 *
 */
package com.edutrack.currisub.dao;

import javax.sql.RowSet;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author famlilia
 *
 */
public class CompanyMasterCourseDAO extends AbstractDAO {


	/**
	 * 위탁관리자가 담당하고 있는 강의목록을 가져온다.
	 * @param SystemCode
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCompanyMasterCurriList(String SystemCode, String userId, int curriYear, int curriTerm) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT " +
						" Y.*, ct.pare_code1, ct.pare_code2, ct.cate_code" +
						", curri_property2 AS curri_type " +
						", CASE WHEN ct.cate_code is null OR ct.cate_code = '' THEN (SELECT cate_name FROM curri_pcategory2 " +
																					" WHERE system_code = Y.system_code AND pare_code2 = ct.pare_code2) " +
						" ELSE (SELECT cate_name FROM curri_category " +
								" WHERE system_code = Y.system_code AND cate_code = ct.cate_code) END AS cate_name ");
		sb.append(" FROM ");
		sb.append(" ( ");
		sb.append(		" SELECT " +
							" cc.system_code, cc.curri_code, cc.curri_year, cc.curri_term " +
							", cs.curri_name, cs.enroll_start, cs.enroll_end, cs.service_start " +
							", cs.service_end, cs.prof_id, cs.credit, cs.hakgi_term " +
							", (SELECT user_name FROM users WHERE system_code = cc.system_code AND user_id = prof_id) AS prof_name " +
							", (SELECT COUNT(user_id) FROM student " +
								" WHERE system_code = cc.system_code AND curri_code = cc.curri_code " +
								" AND curri_year = cc.curri_year AND curri_term = cc.curri_term " +
								" AND CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) >= servicestart_date " +
								" AND CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) <= serviceend_date) AS process_stu_cnt" +
							", (SELECT COUNT(user_id) FROM student " +
								" WHERE system_code = cc.system_code AND curri_code = cc.curri_code " +
								" AND curri_year = cc.curri_year AND curri_term = cc.curri_term) AS all_stu_cnt " +
							", (SELECT COUNT(user_id) FROM student " +
								" WHERE system_code = cc.system_code AND curri_code = cc.curri_code " +
								" AND curri_year = cc.curri_year AND curri_term = cc.curri_term " +
								" AND (enroll_status in ('F', 'S') OR serviceend_date <= CAST( DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))) AS nopass_stu_cnt " +
							", (SELECT COUNT(user_id) FROM student " +
								" WHERE system_code = cc.system_code AND curri_code = cc.curri_code " +
								" AND curri_year = cc.curri_year AND curri_term = cc.curri_term " +
								" AND enroll_status = 'C') AS pass_stu_cnt " +
							", X.user_id, X.user_name ");
		sb.append(			" FROM " +
								" company_course cc, curri_sub cs " +
								", (SELECT user_id, user_name, dept_daecode, dept_socode FROM users " +
									" WHERE system_code = ? AND user_id = ?) X ");
		sql.setString(SystemCode);
		sql.setString(userId);
		sb.append(			" WHERE " +
								" cc.dept_daecode = X.dept_daecode AND cc.dept_socode = X.dept_socode " +
								" AND cs.system_code = cc.system_code AND cs.curri_code = cc.curri_code " +
								" AND cs.curri_year = cc.curri_year AND cs.curri_term = cc.curri_term ");
		if(curriYear != 0) {
			sb.append(" AND cc.curri_year = ? ");
			sql.setInteger(curriYear);
		}
		if(curriTerm != 0) {
			sb.append(" AND cc.curri_term = ? ");
			sql.setInteger(curriTerm);
		}
		sb.append(" ) Y, curri_top ct ");
		sb.append(" WHERE ct.system_code = Y.system_code AND ct.curri_code = Y.curri_code ");
		sb.append(" ORDER BY Y.curri_year ASC, Y.curri_term ASC ");
		//sb.append("  ");

		sql.setSql(sb.toString());

		//---- 디버그 출력
		log.debug("[getTrustMasterCurriList]" + sql.toString());
		//System.out.println("[getTrustMasterCurriList]" + sql.toString());

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
	 * CURRI_TERM 그룹
	 * @param systemCode
	 * @param curriYear
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriTermGroup(String systemCode, int curriYear) throws DAOException {
		RowSet	rs	=	null;
		try {
			if(curriYear == 0) {
				curriYear = 2007;
			}

			QueryStatement	sql	=	new QueryStatement();
			StringBuffer	sb	=	new StringBuffer();
			sb.append(" SELECT IFNULL(curri_term, 0) as curri_term ");
			sb.append(" FROM curri_sub ");
			sb.append(" WHERE system_code = ? AND curri_year = ? ");
			sb.append(" GROUP BY curri_term ");

			sql.setSql(sb.toString());
			sql.setString(systemCode);
			sql.setInteger(curriYear);

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 과정별현황 - 셀렉트 바에 들어갈 과정 리스트
	 * @param systemCode
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCompanySelectCourseList(String systemCode, String userId, int curriYear, int curriTerm) throws DAOException {
		RowSet	rs	=	null;
		try {
			QueryStatement	sql	=	new QueryStatement();
			StringBuffer	sb	=	new StringBuffer();

			sb.append(" SELECT cc.curri_code, cs.curri_name  ");
			sb.append(" FROM " +
							" company_course cc, curri_sub cs " +
							", (SELECT user_id, user_name, dept_daecode, dept_socode FROM users " +
							" WHERE system_code = ? AND user_id = ?) X ");
			sql.setString(systemCode);
			sql.setString(userId);
			sb.append(" WHERE" +
							" cc.dept_daecode = X.dept_daecode AND cc.dept_socode = X.dept_socode " +
							" AND cs.system_code = cc.system_code AND cs.curri_code = cc.curri_code " +
							" AND cs.curri_year = cc.curri_year AND cs.curri_term = cc.curri_term ");
			if(curriYear != 0) {
				sb.append(" AND cc.curri_year = ? ");
				sql.setInteger(curriYear);
			}
			if(curriTerm != 0) {
				sb.append(" AND cc.curri_term = ? ");
				sql.setInteger(curriTerm);
			}

			sql.setSql(sb.toString());

			log.debug("[getCompanySelectCourseList] : "+sql.toString());
			//System.out.println("[getCompanySelectCourseList] : "+sql.toString());

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 과정현황에서 수강생 현황 리스트
	 * @param systemCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws DAOException
	 */
	public RowSet getStudentCurriInfo(String systemCode, String curriCode, int curriYear, int curriTerm) throws DAOException {
		RowSet	rs	=	null;
		try {
			QueryStatement	sql	=	new QueryStatement();
			StringBuffer	sb	=	new StringBuffer();

			sb.append(" SELECT " +
							" csc.system_code, csc.curri_code, csc.curri_year, csc.curri_term" +
							", s.user_id " +
							", MAX(s.grade) AS grade " +
							", MAX(s.get_credit) AS get_credit " +
							", MAX(cs.credit) AS credit " +
							", MAX(cs.score_gubun) AS score_gubun " +
							", MAX(s.enroll_status) AS enroll_status " +
							", MAX(s.complete_date) AS complete_date " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_exam) ELSE ROUND(SUM(rc.score_exam)/csc.course_cnt, 1) END AS score_exam " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_report) ELSE ROUND(SUM(rc.score_report)/csc.course_cnt, 1) END AS score_report " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_attend) ELSE ROUND(SUM(rc.score_attend)/csc.course_cnt, 1) END AS score_attend " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_forum) ELSE ROUND(SUM(rc.score_forum)/csc.course_cnt, 1) END AS score_forum " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_etc1) ELSE ROUND(SUM(rc.score_etc1)/csc.course_cnt, 1) END AS score_etc1 " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_etc2) ELSE ROUND(SUM(rc.score_etc2)/csc.course_cnt, 1) END AS score_etc2 " +
							", CASE WHEN csc.course_cnt = 1 THEN MAX(rc.score_total) ELSE ROUND(SUM(rc.score_total)/csc.course_cnt, 1) END AS score_total " +
							", (SELECT user_name FROM users WHERE system_code = csc.system_code AND user_id = s.user_id) AS user_name  ");
			sb.append(" FROM " +
							" curri_sub cs, student s LEFT OUTER JOIN result_course rc " +
							" ON (rc.system_code = s.system_code AND rc.curri_code = s.curri_code " +
							" AND rc.curri_year = s.curri_year AND rc.curri_term = s.curri_term AND rc.user_id = s.user_id) " +
							", (SELECT " +
									" system_code, curri_code, curri_year, curri_term" +
									", COUNT(course_id) as course_cnt " +
								" FROM curri_sub_course " +
								" WHERE system_code = ? AND curri_code = ? AND curri_year = ? AND curri_term = ? " +
								" GROUP BY system_code, curri_code, curri_year, curri_term) csc ");
			sb.append(" WHERE " +
							" s.system_code = cs.system_code AND s.curri_code = cs.curri_code " +
							" AND s.curri_year = cs.curri_year AND s.curri_term = cs.curri_term ");
			sb.append(" GROUP BY csc.system_code, csc.curri_code, csc.curri_year, csc.curri_term, s.user_id ");

			sql.setSql(sb.toString());
			sql.setString(systemCode);
			sql.setString(curriCode);
			sql.setInteger(curriYear);
			sql.setInteger(curriTerm);


			log.debug(" [getStudentCurriInfo] : "+sql.toString());
			//System.out.println(" [getStudentCurriInfo] : "+sql.toString());

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 위탁기관의 학생이력 가져오기
	 * @param SystemCode
	 * @param DeptDaeCode
	 * @param DeptSoCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getMemberList(String SystemCode, String DeptDaeCode, String DeptSoCode ) throws DAOException {
		RowSet	rs	=	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		try {
			sb.append(" SELECT " +
					" u.user_id, u.reg_date, u.user_name, u.dept_daecode" +
					", u.dept_socode " +
					", (SELECT count(curri_code) FROM student " +
						" WHERE system_code = u.system_code and user_id = u.user_id " +
						" and enroll_status = 'S' AND chungend_date >= CAST( DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)) as curri_count " +
					", (SELECT count(curri_code) FROM student " +
						" WHERE system_code = u.system_code AND user_id = u.user_id " +
						" AND (enroll_status = 'C' or chungend_date < CAST( DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR))) as complete_cnt " +
					", (SELECT count(*) FROM system_connect " +
						" WHERE system_code = u.system_code AND user_id = u.user_id) AS con_cnt ");
			sb.append(" FROM users u ");
			sb.append(" WHERE system_code = ? AND dept_daecode = ? AND dept_socode = ? AND user_type = 'S' ");
			sb.append(" ORDER BY u.reg_date DESC, u.user_name ASC  ");

			sql.setSql(sb.toString());
			sql.setString(SystemCode);
			sql.setString(DeptDaeCode);
			sql.setString(DeptSoCode);

			log.debug("[CompanyMasterCourse : getMemberList] ; "+sql.toString());
			//System.out.println("[CompanyMasterCourse : getMemberList] ; "+sql.toString());

			rs	=	broker.executeQuery(sql);

		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 수강생 별 강좌현황
	 * @param SystemCode
	 * @param DeptDaeCode
	 * @param DeptSoCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getStudentCurriInfoList(String SystemCode, String userId) throws DAOException {
		RowSet rs	=	null;

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT " +
						" s.user_id, s.curri_code, s.servicestart_date, s.serviceend_date " +
						", s.grade, s.get_credit, s.enroll_status, s.complete_date " +
						", cs.curri_name, cs.score_gubun , round(rc.score_total, 1) AS score_total, s.enroll_date ");
		sb.append(" FROM curri_sub cs, student s LEFT OUTER JOIN result_course rc " +
					" ON rc.system_code = s.system_code AND rc.curri_code = s.curri_code " +
					" AND rc.curri_year = rc.curri_year AND rc.curri_term = s.curri_term " +
					" AND rc.user_id = ? ");
		sb.append(" WHERE " +
						" cs.system_code = s.system_code AND cs.curri_code = s.curri_code " +
						" AND cs.curri_year = s.curri_year AND cs.curri_term = s.curri_term " +
						" AND s.enroll_status in ('S', 'C') AND s.user_id = ? ");
		sb.append(" GROUP BY s.curri_code, s.curri_year, s.curri_term ");
		sb.append(" ORDER BY s.enroll_date DESC, cs.curri_name ASC ");

		sql.setSql(sb.toString());
		sql.setString(userId);
		sql.setString(userId);

		log.debug("[CompanyMasterCourse : getStudentCurriInfoList] ; "+sql.toString());
		//System.out.println("[CompanyMasterCourse : getStudentCurriInfoList] ; "+sql.toString());

		try {
			rs	=	broker.executeQuery(sql);
		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

}
