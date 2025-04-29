/**
 *
 */
package com.edutrack.connstatus.dao;

import java.util.Map;

import javax.sql.RowSet;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author famlilia
 *
 */
public class CurriSubStaticDAO extends AbstractDAO{

	/**
	 * 학기, 기수 등을 모두 받아온다.
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
	 * 교육과정분야 대코드목록를 가져온다.
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriPcategoryList(String systemCode) throws DAOException {
		RowSet	rs	=	null;
		try {

			QueryStatement	sql	=	new QueryStatement();
			StringBuffer	sb	=	new StringBuffer();
			sb.append(" SELECT system_code, pare_code1, cate_name  ");
			sb.append(" FROM curri_pcategory1 ");
			sb.append(" WHERE system_code = ? AND use_yn = 'Y' ");

			sql.setSql(sb.toString());
			sql.setString(systemCode);

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 교육과정별 통계 - 과정정보
	 * @param ParamMap
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCateCurriSubInfo(Map ParamMap) throws DAOException {
		ListDTO	listDto	=	null;
		try {
			String	pSystemCode	=	StringUtil.nvl(ParamMap.get("pSystemCode"));
			String	pCurDate	=	StringUtil.nvl(ParamMap.get("pCurDate"));
			String	pPareCode1	=	StringUtil.nvl(ParamMap.get("pPareCode1"));
			String	pCurriType	=	StringUtil.nvl(ParamMap.get("pCurriType"));
			int		pCurriYear	=	StringUtil.nvl(ParamMap.get("pCurriYear"), 2007);
			int		pCurriTerm	=	StringUtil.nvl(ParamMap.get("pCurriTerm"), 0);
			int		curPage		=	StringUtil.nvl(ParamMap.get("curPage"), 1);
			int		DispLine	=	StringUtil.nvl(ParamMap.get("DispLine"), 10);
			int		DispPage	=	StringUtil.nvl(ParamMap.get("DispPage"), 10);


			StringBuffer	sbAlias	=	new StringBuffer();
			StringBuffer	sb	=	new StringBuffer();

			sbAlias.append(" sys_code, curri_code, curri_year, curri_term" +
							", curri_name, pare_code1, pare_code2, cate_code" +
							", user_id, enroll_status, contents_cnt, jumin_no" +
							", complete_man_cnt, complete_women_cnt, no_man_cnt, no_women_cnt" +
							", complete_total_cnt, no_total_cnt, man_total_cnt, women_total_cnt" +
							", total_cnt ");

			sb.append(  " Y.system_code, Y.curri_code, Y.curri_year, Y.curri_term " +
						", Y.curri_name, Y.pare_code1, Y.pare_code2, Y.cate_code" +
						", Y.user_id, Y.enroll_status, Y.contents_cnt, Y.jumin_no" +
						", Y.complete_man_cnt, Y.complete_women_cnt, Y.no_man_cnt, Y.no_women_cnt" +
						", Y.complete_man_cnt + Y.complete_women_cnt as complete_total_cnt" +
						", Y.no_man_cnt + Y.no_women_cnt as no_total_cnt" +
						", Y.complete_man_cnt + Y.no_man_cnt as man_total_cnt" +
						", Y.complete_women_cnt + Y.no_women_cnt as women_total_cnt" +
						", Y.complete_man_cnt + Y.no_man_cnt + Y.complete_women_cnt + Y.no_women_cnt as total_cnt ");


			//List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol(" Y.system_code AND Y.curri_code ");
			sql.setCutCol(" ");

			sql.setAlias(sbAlias.toString());
			sql.setSelect(sb.toString());

			//--	FROM문 생성시작
			StringBuffer	sbFrom	=	new StringBuffer();

			sbFrom.append(" ( ");
			sbFrom.append(	" SELECT " +
							" X.*" +
							", SUM(CASE WHEN X.enroll_status = 'C' AND (X.jumin_no = '1' or X.jumin_no = '3') THEN 1 ELSE 0 END) as complete_man_cnt" +
							", SUM(CASE WHEN X.enroll_status = 'C' AND (X.jumin_no = '2' or X.jumin_no = '4') THEN 1 ELSE 0 END) as complete_women_cnt" +
							", SUM(CASE WHEN (X.enroll_status = 'F' or X.enroll_status = 'S') AND (X.jumin_no = '1' or X.jumin_no = '3') THEN 1 ELSE 0 END) as no_man_cnt" +
							", SUM(CASE WHEN (X.enroll_status = 'F' or X.enroll_status = 'S') AND (X.jumin_no = '2' or X.jumin_no = '4') THEN 1 ELSE 0 END) as no_women_cnt ");
			sbFrom.append(		" FROM ");
			sbFrom.append(		" ( ");
			sbFrom.append(			" SELECT " +
									" cs.system_code, cs.curri_code, cs.curri_year, cs.curri_term" +
									", cs.curri_name, ct.pare_code1, ct.pare_code2, ct.cate_code" +
									", st.user_id, st.enroll_status, cs.curri_type" +
									", (SELECT COUNT(*) " +
										" FROM curri_contents " +
										" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
										" AND curri_year = cs.curri_year AND curri_term = cs.curri_term" +
										" AND contents_type='F' AND lecture_gubun ='1') as contents_cnt" +
									", SUBSTRING((SELECT jumin_no " +
												" FROM users " +
												" WHERE system_code = st.system_code AND user_id = st.user_id), 8, 1) as jumin_no ");
			sbFrom.append(				" FROM curri_sub cs, curri_top ct, student st ");
			sbFrom.append(				" WHERE 1=1 " +
									" AND ct.system_code = cs.system_code AND ct.curri_code = cs.curri_code " +
									" AND st.system_code = cs.system_code AND st.curri_code = cs.curri_code " +
									" AND st.curri_year = cs.curri_year AND st.curri_term = cs.curri_term" +
									" AND ct.curri_property2 = 'R' ");
			sbFrom.append(		" ) X ");
			sbFrom.append(		" WHERE 1=1 AND X.system_code = ? ");
			sql.setString(pSystemCode);

			if(!pCurriType.equals("")) {
				sbFrom.append(	"  AND X.curri_type = ? ");
				sql.setString(pCurriType);
			}
			if(!pPareCode1.equals("")) {
				sbFrom.append(	"  AND X.pare_code1 = ? ");
				sql.setString(pPareCode1);
			}
			if(pCurriYear > 0) {
				sbFrom.append(	"  AND X.curri_year = ? ");
				sql.setInteger(pCurriYear);
			}
			if(pCurriTerm > 0) {
				sbFrom.append(	"  AND X.curri_term = ? ");
				sql.setInteger(pCurriTerm);
			}

			sbFrom.append(	" GROUP BY X.system_code, X.curri_code, X.curri_year, X.curri_term" +
							", X.curri_name, X.pare_code1, X.pare_code2, X.cate_code" +
							", X.enroll_status, X.curri_type ");
			sbFrom.append(" ) Y ");

			sql.setFrom(sbFrom.toString());

			//--	FROM문 생성끝

			log.debug(" =================== [getCateCurriSubInfo] : "+sql.toString());

			listDto	=	broker.executeListQuery(sql, curPage, DispLine, DispPage);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return listDto;
	}

	/**
	 * 교육과정별통계 - 아래의 통계 테이블
	 * @param ParamMap
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCateCurriSubStatic(Map ParamMap) throws DAOException {
		RowSet	rs	=	null;
		try {
			String	pSystemCode	=	StringUtil.nvl(ParamMap.get("pSystemCode"));
			String	pCurriCode	=	StringUtil.nvl(ParamMap.get("pCurriCode"));
			String	pPareCode1	=	StringUtil.nvl(ParamMap.get("pPareCode1"));

			String	pAgeYn		=	StringUtil.nvl(ParamMap.get("pAgeYn"));
			int		pCurriYear	=	StringUtil.nvl(ParamMap.get("pCurriYear"), 0);
			int		pCurriTerm	=	StringUtil.nvl(ParamMap.get("pCurriTerm"), 0);

			StringBuffer	sb	=	new StringBuffer();
			QueryStatement	sql	=	new QueryStatement();

			sb.append(" SELECT " +
							"Y.*" +
							", np_man + np_women AS np_total, pass_man + pass_women AS pass_total" +
							", np_man + pass_man AS total_man, np_women + pass_women AS total_women" +
							", np_man + pass_man + np_women + pass_women AS total_cnt ");
			sb.append(" FROM ");
			sb.append(" ( ");
			sb.append(		" SELECT " +
							" X.curri_code, X.curri_year, X.curri_term, X.pare_code1, co.so_name");

			if(pAgeYn.equals("Y")) {
				sb.append(		", X.age");
			}
			else {
				sb.append(		", X.area");
			}

			sb.append(		", SUM(CASE WHEN X.enroll = 'NP' AND X.sex_type = 'M' THEN 1 ELSE 0 END) AS np_man" +
							", SUM(CASE WHEN X.enroll = 'NP' AND X.sex_type = 'W' THEN 1 ELSE 0 END) AS np_women" +
							", SUM(CASE WHEN X.enroll = 'P' AND X.sex_type = 'M' THEN 1 ELSE 0 END) AS pass_man" +
							", SUM(CASE WHEN X.enroll = 'P' AND X.sex_type = 'W' THEN 1 ELSE 0 END) AS pass_women ");
			sb.append(		" FROM code_so co LEFT OUTER JOIN  ");
			sb.append(		" ( ");
			sb.append(				" SELECT " +
									" cs.system_code, cs.curri_code, cs.curri_year, cs.curri_term" +
									", cs.curri_name, ct.pare_code1, st.user_id" +
									", CASE WHEN st.enroll_status = 'F' or st.enroll_status = 'S' THEN 'NP' " +
										" WHEN st.enroll_status = 'C' THEN 'P' ELSE '' END AS enroll" +
									", CASE WHEN substring(u.jumin_no, 8, 1) = '1' or substring(u.jumin_no, 8, 1) = '3' THEN 'M' " +
										" WHEN substring(u.jumin_no, 8, 1) = '2' or substring(u.jumin_no, 8, 1) = '4' THEN 'W' ELSE '' END AS sex_type ");

			if(pAgeYn.equals("Y")) {
				sb.append(			" , CONCAT(SUBSTRING(CAST(DATE_FORMAT(NOW(),'%Y') AS CHAR)-SUBSTRING(u.jumin_no,1,2), 3,1),'0') AS age ");
			}
			else {

				sb.append(			", CASE WHEN (substring(post_code,1,3) = '417') then '11'/*인천(강화)*/  " +
										" when (substring(post_code,1,3) between 100 and 199) then '10' /*서울*/ " +
										" when (substring(post_code,1,3) between 200 and 299) then '24' /*강원*/ " +
										" when (substring(post_code,1,3) between 300 and 309) then '21' /*대전*/ " +
										" when (substring(post_code,1,3) between 310 and 359) then '22' /*충남*/ " +
										" when (substring(post_code,1,3) between 360 and 399) then '23' /*충북*/ " +
										" when (substring(post_code,1,3) between 400 and 409) then '11' /*인천*/ " +
										" when (substring(post_code,1,3) between 410 and 499) then '12' /*경기*/ " +
										" when (substring(post_code,1,3) between 500 and 509) then '18' /*광주*/ " +
										" when (substring(post_code,1,3) between 510 and 559) then '19' /*전남*/ " +
										" when (substring(post_code,1,3) between 560 and 599) then '20' /*전북*/ " +
										" when (substring(post_code,1,3) between 600 and 619) then '13' /*부산*/ " +
										" when (substring(post_code,1,3) between 620 and 679) then '15' /*경남*/ " +
										" when (substring(post_code,1,3) between 680 and 689) then '14' /*울산*/ " +
										" when (substring(post_code,1,3) between 690 and 699) then '25' /*제주*/ " +
										" when (substring(post_code,1,3) between 700 and 711) then '16' /*대구*/ " +
										" when (substring(post_code,1,3) between 712 and 799) then '17' /*경북*/ " +
										" else '99' end as area  ");
			}

			sb.append(				" FROM curri_sub cs, curri_top ct, student st left outer join users u " +
									" ON (u.system_code = st.system_code AND u.user_id = st.user_id) ");
			sb.append(				" WHERE 1=1 " +
									" AND ct.system_code = cs.system_code AND ct.curri_code = cs.curri_code " +
									" AND st.system_code = cs.system_code AND st.curri_code = cs.curri_code " +
									" AND st.curri_year = cs.curri_year AND st.curri_term = cs.curri_term " +
									" AND ct.curri_property2 = 'R' AND ct.system_code = ? ");
			sql.setString(pSystemCode);

			if(!pCurriCode.equals("")) {
				sb.append(			"  AND cs.curri_code = ? ");
				sql.setString(pCurriCode);
			}
			if(!pPareCode1.equals("")) {
				sb.append(			"  AND ct.pare_code1 = ? ");
				sql.setString(pPareCode1);
			}
			if(pCurriYear > 0) {
				sb.append(			"  AND cs.curri_year = ? ");
				sql.setInteger(pCurriYear);
			}
			if(pCurriTerm > 0) {
				sb.append(			"  AND cs.curri_term = ? ");
				sql.setInteger(pCurriTerm);
			}

			sb.append(		" ) X ");
			sb.append(		" ON X.system_code = co.system_code ");

			if(pAgeYn.equals("Y")) {
				sb.append(	" AND X.age = co.code_so ");
				sb.append(	" WHERE co.code_dae = '996' AND co.code_so != '00'  ");
				sb.append(	" GROUP BY X.curri_year, X.curri_term, X.pare_code1, X.age, co.code_so ");
			}
			else {
				sb.append(	" AND X.area = co.code_so ");
				sb.append(	" WHERE co.code_dae = '401'  ");
				sb.append(	" GROUP BY X.curri_year, X.curri_term, X.pare_code1, X.area, co.code_so ");
			}

			sb.append(" ) Y ");


			sql.setSql(sb.toString());

			log.debug(" ================== [getCateCurriSubGraph] : "+sql.toString());

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	/**
	 * 수강현황 통계
	 * @param ParamMap
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriSubCourseStatic(Map ParamMap) throws DAOException {
		RowSet	rs	=	null;
		try {
			String	pSystemCode	=	StringUtil.nvl(ParamMap.get("pSystemCode"));
			int		pCurriYear	=	StringUtil.nvl(ParamMap.get("pCurriYear"), 0);
			int		pCurriTerm	=	StringUtil.nvl(ParamMap.get("pCurriTerm"), 0);

			StringBuffer	sb	=	new StringBuffer();
			QueryStatement	sql	=	new QueryStatement();

			sb.append(" SELECT " +
						" ct.system_code, ct.curri_code, ct.pare_code1, ct.pare_code2" +
						", cs.curri_year, cs.curri_term, cs.curri_name" +
						", CASE complete_score WHEN 'Y' then '완료' else '미평가' end AS complete_yn " +
						", COUNT(wrr.seq_no) AS req_cnt" +
						", CASE WHEN wrr.res_reg_id != '' AND wrr.res_reg_date != '' THEN 1 ELSE 0 END AS res_cnt" +
						", (SELECT COUNT(*) FROM student " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term" +
							" AND enroll_status IN ('C', 'F', 'S')) AS student_cnt " +
						", (SELECT user_name FROM users " +
							" WHERE system_code = ct.system_code AND user_id = cs.prof_id " +
							" AND user_type = 'P') AS prof_name" +
						", (SELECT COUNT(*) FROM curri_bbs_contents " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
							" AND bbs_type = 'notice') AS notice_cnt " +
						", (SELECT COUNT(*) FROM curri_bbs_contents " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
							" AND bbs_type = 'qna') AS qna_cnt " +
						", (SELECT COUNT(*) FROM course_forum_info " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
							" AND parent_forum_id = 0 AND open_yn = 'Y' AND regist_yn = 'Y') AS forum_cnt " +
						", (SELECT COUNT(*) FROM course_forum_contents " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term) AS forum_contents_cnt " +
						", (SELECT COUNT(*) FROM report_info " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
							" AND report_regist_yn = 'Y') AS report_cnt " +
						", (SELECT COUNT(*) FROM exam_info " +
							" WHERE system_code = ct.system_code AND curri_code = cs.curri_code " +
							" AND curri_year = cs.curri_year AND curri_term = cs.curri_term AND flag_use = 'Y') AS exam_cnt  ");
			sb.append(	" FROM curri_sub cs " +
						" LEFT OUTER JOIN work_request_response wrr " +
						" ON wrr.system_code = cs.system_code AND wrr.curri_code = cs.curri_code " +
						" AND wrr.curri_year = cs.curri_year and wrr.curri_term = cs.curri_term " +
						", (SELECT system_code, curri_code, pare_code1, pare_code2 FROM curri_top WHERE system_code = ? AND curri_property2='R') ct  ");
			sql.setString(pSystemCode);

			sb.append(" WHERE ct.system_code = cs.system_code AND ct.curri_code = cs.curri_code  ");
			if(pCurriYear >= 2007) {
				sb.append(" AND cs.curri_year = ? ");
				sql.setInteger(pCurriYear);
			}
			if(pCurriTerm > 0) {
				sb.append(" AND cs.curri_term = ? ");
				sql.setInteger(pCurriTerm);
			}

			sb.append(" GROUP BY ct.system_code, ct.curri_code, ct.pare_code1, ct.pare_code2, cs.curri_year, cs.curri_term ");
			sb.append(" ORDER BY cs.system_code, cs.curri_year, cs.curri_term, cs.curri_code ");

			sql.setSql(sb.toString());

			log.debug("[getCurriSubCourseStatic] : "+sql.toString());
			//System.out.println("[getCurriSubCourseStatic] : "+sql.toString());

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 과정의 수강생 통계정보
	 * @param ParamMap
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCourseStudentStatic(Map ParamMap) throws DAOException {
		RowSet	rs	=	null;
		try {
			String	pSystemCode	=	StringUtil.nvl(ParamMap.get("pSystemCode"));
			String	pCurriCode	=	StringUtil.nvl(ParamMap.get("pCurriCode"));
			int		pCurriYear	=	StringUtil.nvl(ParamMap.get("pCurriYear"), 0);
			int		pCurriTerm	=	StringUtil.nvl(ParamMap.get("pCurriTerm"), 0);

			StringBuffer	sb	=	new StringBuffer();
			QueryStatement	sql	=	new QueryStatement();

			sb.append(" SELECT " +
						" X.curri_name, X.user_name, X.all_contents_cnt, X.qna_cnt" +
						", X.forum_contents_cnt, X.report_cnt, X.exam_cnt, X.result" +
						", X.contents_cnt" +
						", COUNT(wrr.seq_no) AS req_cnt" +
						", SUM(CASE WHEN wrr.res_reg_id IS NOT NULL THEN 1 ELSE 0 END) AS res_cnt " +
						", IFNULL(ROUND(CASE WHEN IFNULL(X.all_contents_cnt, 0) = 0 OR IFNULL(X.contents_cnt, 0) = 0 THEN 0 " +
							" ELSE (X.contents_cnt/X.all_contents_cnt)*100 END, 1), 0) course_rate ");
			sb.append(" FROM ");
			sb.append(" ( ");
			sb.append(		" SELECT " +
							" cs.system_code, cs.curri_code, cs.curri_year, cs.curri_term" +
							", cs.curri_name, s.user_id" +
							", (SELECT COUNT(contents_id) FROM curri_contents " +
								" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
								" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
								" AND contents_type='F' AND lecture_gubun ='1') AS all_contents_cnt" +
							", (SELECT user_name FROM users " +
								" WHERE user_id = s.user_id) AS user_name" +
							", (SELECT COUNT(seq_no) FROM curri_bbs_contents " +
								" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
								" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
								" AND bbs_type = 'qna' AND reg_id = s.user_id) as qna_cnt" +
							", (SELECT COUNT(seq_no) FROM course_forum_contents " +
								" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
								" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
								" AND reg_id = s.user_id) as forum_contents_cnt" +
							", (SELECT COUNT(report_id) FROM report_send " +
								" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
								" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
								" AND user_id = s.user_id) as report_cnt" +
							", (SELECT COUNT(exam_id) FROM exam_answer " +
								" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
								" AND curri_year = cs.curri_year AND curri_term = cs.curri_term " +
								" AND user_id = s.user_id) as exam_cnt" +
//							", (SELECT SCORE_TOTAL FROM RESULT_COURSE " +
//								" WHERE SYSTEM_CODE = CS.SYSTEM_CODE AND CURRI_CODE = CS.CURRI_CODE " +
//								" AND CURRI_YEAR = CS.CURRI_YEAR AND CURRI_TERM = CS.CURRI_TERM " +
//								" AND USER_ID = S.USER_ID) AS SCORE_TOTAL" +
							", CASE WHEN cs.score_gubun = '1' THEN concat(s.grade, ' (', s.get_credit, ')') " +
							" ELSE " +
								" CASE WHEN s.enroll_status = 'C' OR s.complete_date != '' OR s.complete_date IS NOT NULL THEN '수료' ELSE '미수료' END " +
							" END AS result " +
							",( SELECT IFNULL(COUNT(b.contents_id), 0) FROM curri_contents c LEFT OUTER JOIN bookmark b " +
									" ON (c.system_code = b.system_code AND c.curri_code = b.curri_code " +
									" AND c.curri_year = b.curri_year AND c.curri_term = b.curri_term " +
									" AND c.contents_id = b.contents_id  AND b.open_chk='Y') " +
								" WHERE c.system_code = cs.system_code AND c.curri_code=cs.curri_code " +
								" AND c.curri_year=cs.curri_year AND c.curri_term = cs.curri_term " +
								" AND c.contents_type='F' AND c.lecture_gubun ='1' " +
								" AND b.user_id = s.user_id " +
								" GROUP BY c.system_code, c.curri_code, c.curri_year, c.curri_term, b.user_id) AS contents_cnt ");
			sb.append(		" FROM curri_sub cs, student s ");
			sb.append(		" WHERE s.system_code = cs.system_code AND s.curri_code = cs.curri_code " +
							" AND s.curri_year = cs.curri_year AND s.curri_term = cs.curri_term " +
							" AND s.enroll_status IN ('C', 'F', 'S') ");

			sb.append(		" AND cs.system_code = ? AND cs.curri_code = ? " +
							" AND cs.curri_year = ? AND cs.curri_term = ? ");
			sql.setString(pSystemCode);
			sql.setString(pCurriCode);
			sql.setInteger(pCurriYear);
			sql.setInteger(pCurriTerm);

			sb.append(" ) X LEFT OUTER JOIN work_request_response wrr  ");
			sb.append(		" ON (wrr.system_code = X.system_code AND wrr.curri_code = X.curri_code " +
							" AND wrr.curri_year = X.curri_year AND wrr.curri_term = X.curri_term " +
							" AND wrr.student_id = X.user_id) ");
			sb.append(" GROUP BY X.system_code, X.curri_code, X.curri_year, X.curri_term, X.user_id ");
			sb.append(" ORDER BY X.user_name ");

			sql.setSql(sb.toString());


			log.debug("[getCourseStudentStatic] : "+sql.toString());
			//System.out.println("[getCourseStudentStatic] : "+sql.toString());

			rs	=	broker.executeQuery(sql);

		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


}
