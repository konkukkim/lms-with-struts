/**
 *
 */
package com.edutrack.resultcourse.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dto.ExamAnswerDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.resultcourse.dto.ResultCourseDTO;

/**
 * @author jangms
 *
 */
public class ResultCourseScoreDAO extends AbstractDAO {


	/**
	 * RESULT_COURSE 테이블에 자료가 있는지 확인
	 * @param commonMap
	 * @return
	 * @throws DAOException
	 */
	public int countResultCourse(Map commonMap) throws DAOException {
		int	retVal	=	0;
		RowSet	rs	=	null;
		try {
			String	pSystemCode	=	StringUtil.nvl(commonMap.get("pSystemCode"));
			String	pCurriCode	=	StringUtil.nvl(commonMap.get("pCurriCode"));
			int		pCurriYear	=	StringUtil.nvl(commonMap.get("pCurriYear"), 0);
			int		pCurriTerm	=	StringUtil.nvl(commonMap.get("pCurriTerm"), 0);
			String	pCourseId	=	StringUtil.nvl(commonMap.get("pCourseId"));
			String	pUserId		=	StringUtil.nvl(commonMap.get("pUserId"));

			StringBuffer	sb	=	new StringBuffer();
			QueryStatement	sql	=	new QueryStatement();
			sb.append(" SELECT COUNT(*) AS cnt FROM result_course ");
			sb.append(" WHERE system_code = ? AND curri_code = ? " +
						"AND curri_year = ? AND curri_term = ? " +
						"AND course_id = ? AND user_id = ? ");
			sql.setSql(sb.toString());
			sql.setString(pSystemCode);
			sql.setString(pCurriCode);
			sql.setInteger(pCurriYear);
			sql.setInteger(pCurriTerm);
			sql.setString(pCourseId);
			sql.setString(pUserId);

			log.debug("[countResultCourse]"+sql.toString());
			//System.out.println("[countResultCourse]"+sql.toString());

			rs	=	broker.executeQuery(sql);
			if(rs.next()) {
				retVal	=	StringUtil.nvl(rs.getString("cnt"), 0);
			}
			rs.close();
		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 시험점수를 RESULT_COURSE 테이블에 입력하기 전에 변환한다.
	 * @param commonMap
	 * @return
	 * @throws DAOException
	 */
	public int getConvertExamScore(Map commonMap) throws DAOException {
		int	retVal	=	0;
		CurriMemDTO curriInfo	=	null;
		ExamAnswerDTO answerinfo	=	null;
		RowSet	rs	=	null;
		try {
			curriInfo	=	(CurriMemDTO)commonMap.get("CurriMemDTO");
			answerinfo	=	(ExamAnswerDTO)commonMap.get("ExamAnswerDTO");

			String	pSystemCode	=	StringUtil.nvl(commonMap.get("pSystemCode"));
			String	pCurriCode	=	StringUtil.nvl(curriInfo.curriCode);
			int		pCurriYear	=	StringUtil.nvl(curriInfo.curriYear, 0);
			int		pCurriTerm	=	StringUtil.nvl(curriInfo.curriTerm, 0);
			String	pUserId		=	StringUtil.nvl(answerinfo.getUserId());
			String	pCourseId	=	StringUtil.nvl(answerinfo.getCourseId());


			StringBuffer	sb	=	new StringBuffer();
			QueryStatement	sql	=	new QueryStatement();
			sb.append(" SELECT " +
						"ROUND(exam_result_score * z.exam_score_01 / Z.exam_score, 1) AS exam_result_score ");
			sb.append(" FROM ");
			sb.append(" ( ");
			sb.append(		" SELECT " +
							"X.exam_score, X.exam_result_score" +
							", round(exam_score*Y.exam_base/exam_score, 0) AS exam_score_01	 ");
			sb.append(		" FROM ");
			sb.append(		" ( ");

			sb.append(			" SELECT " +
								" ei.system_code, ei.curri_code, ei.curri_year, ei.curri_term" +
								", 100 * count(*) as exam_score, sum(IFNULL(ea.total_score,0)) as exam_result_score  " +
								"FROM exam_info ei left outer join exam_answer ea  " +
								"on ei.system_code = ea.system_code and ei.curri_code = ea.curri_code  " +
								"and ei.curri_year = ea.curri_year and ei.curri_term = ea.curri_term  " +
								"and ei.course_id = ea.course_id and ei.exam_id=ea.exam_id  " +
								"and ea.user_id= ? " +
								"WHERE " +
								"1=1	" +
								"and ei.course_id = ?  and ei.flag_use = 'Y' " +
								"and ei.exam_type = 'Y' " +
								"GROUP BY ei.system_code, ei.curri_code, ei.curri_year, ei.curri_term ");
			sql.setString(pUserId);
			sql.setString(pCourseId);
			sb.append(		" ) X, ");
			sb.append(		" ( ");
			sb.append(			" SELECT " +
								" a.system_code, a.curri_code, a.curri_year, a.curri_term" +
								", a.exam_base " +
								"FROM curri_sub_course a, curri_top b, course c " +
								"WHERE  " +
								"a.system_code=b.system_code and a.system_code=c.system_code " +
								"and a.curri_code=b.curri_code and a.course_id=c.course_id " +
								"and a.course_id = ? ");
			sql.setString(pCourseId);
			sb.append(		" ) Y ");
			sb.append(		" WHERE " +
							"X.system_code = Y.system_code AND X.curri_code = Y.curri_code " +
							"AND X.curri_year = Y.curri_year AND X.curri_term = Y.curri_term " +
							"AND X.system_code = ? AND X.curri_code = ? " +
							"AND X.curri_year = ? AND X.curri_term = ?   ");
			sb.append(" ) Z ");
			sql.setString(pSystemCode);
			sql.setString(pCurriCode);
			sql.setInteger(pCurriYear);
			sql.setInteger(pCurriTerm);

			sql.setSql(sb.toString());

			log.debug("[getConvertExamScore]"+sql.toString());
			//System.out.println("[getConvertExamScore]"+sql.toString());

			rs	=	broker.executeQuery(sql);
			if(rs.next()) {
				retVal	=	StringUtil.nvl(rs.getString("exam_result_score"), 0);
			}
			rs.close();

		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 각 평가항목들의 점수가 입력되면서 RESULT_COURSE 테이블에 점수를 입력한다.
	 * 평가관리 리스트로 곧장 점수를 보기 위해서이다.
	 * 수강생에게 자신의 점수내역을 곧장 보여주기 위해서이다.
	 * @param commonMap
	 * @return
	 * @throws DAOException
	 */
	public boolean registScore(Map commonMap) throws DAOException {
		boolean		retVal	=	false;
		try {
			String	pScoreGubun	=	StringUtil.nvl(commonMap.get("pScoreGubun"));

			StringBuffer	sb	=	new StringBuffer();
			QueryStatement	sqls[]	=	new QueryStatement[2];
			QueryStatement sql1 = null;

			String	pSystemCode	=	"";
			String	pCurriCode	=	"";
			String	pCourseId	=	"";
			String	pUserId		=	"";
			int		pCurriYear	=	0;
			int		pCurriTerm	=	0;


			//--	시험 점수 등록시
			if(pScoreGubun.equals("Exam")) {
				pSystemCode		=	StringUtil.nvl(commonMap.get("pSystemCode"));
				CurriMemDTO		curriInfo		=	(CurriMemDTO)commonMap.get("CurriMemDTO");
				ExamAnswerDTO	answerinfo		=	(ExamAnswerDTO)commonMap.get("ExamAnswerDTO");
				sql1	=	new QueryStatement();

				sb.append(" update exam_answer ");
				sb.append(" set answer_score=?,total_score=?,result_check=?, feed_back=?, ");
				sb.append(" mod_id=?,mod_date=CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
				sb.append(" where system_code = ? and curri_code = ? and curri_year = ?	and curri_term = ? ");
				sb.append(" and course_id = ? and exam_id = ? and user_id = ? ");

				sql1.setSql(sb.toString());
				sql1.setString(answerinfo.getAnswerScore(),"R");
			   	sql1.setInteger(answerinfo.getTotalScore());
			   	sql1.setString(answerinfo.getResultCheck());
			   	sql1.setString(answerinfo.getFeedBack());
			   	sql1.setString(answerinfo.getModId());
			   	sql1.setString(pSystemCode);
				sql1.setString(curriInfo.curriCode);
				sql1.setInteger(curriInfo.curriYear);
				sql1.setInteger(curriInfo.curriTerm);
				sql1.setString(answerinfo.getCourseId());
				sql1.setInteger(answerinfo.getExamId());
			   	sql1.setString(answerinfo.getUserId());

			   	log.debug("[registScore - exam]"+sql1.toString());
				//System.out.println("[registScore - exam]"+sql1.toString());

				pCurriCode	=	StringUtil.nvl(curriInfo.curriCode);
				pCurriYear	=	StringUtil.nvl(curriInfo.curriYear, 0);
				pCurriTerm	=	StringUtil.nvl(curriInfo.curriTerm, 0);
				pCourseId	=	StringUtil.nvl(answerinfo.getCourseId());
				pUserId		=	StringUtil.nvl(answerinfo.getUserId());

			   	sb.setLength(0);
			}

			sqls[0]	=	sql1;



			//----------------------------------------------------------------------------------------------------------------------

			//--	RESULT_COURSE에 데이타 있는지 여부 체크
			Map	cntMap	=	new HashMap();
			cntMap.put("pSystemCode", pSystemCode);
			cntMap.put("pCurriCode", pCurriCode);
			cntMap.put("pCourseId", pCourseId);
			cntMap.put("pUserId", pUserId);
			cntMap.put("pCurriYear", ""+pCurriYear);
			cntMap.put("pCurriTerm", ""+pCurriTerm);
			int	cntResultCourse	=	countResultCourse(cntMap);

			//----------------------------------------------------------------------------------------------------------------------

			//--	RESULT_COURSE에 입력 또는 수정
			QueryStatement sql2 = null;

			if(cntResultCourse > 0) {
				//--	INSERT
				ResultCourseDTO resultCourse	=	(ResultCourseDTO)commonMap.get("ResultCourseDTO");
				sql2	=	new QueryStatement();

				sb.append(" insert into result_course(system_code, curri_code, curri_year, curri_term, course_id, user_id");
				sb.append(", score_exam, score_report, score_attend, score_forum, score_etc1, score_etc2, score_total");
				sb.append(", reg_id, reg_date) ");
				sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");

				sql2.setSql(sb.toString());

				sql2.setString(resultCourse.getSystemCode());
				sql2.setString(resultCourse.getCurriCode());
				sql2.setInteger(resultCourse.getCurriYear());
				sql2.setInteger(resultCourse.getCurriTerm());
				sql2.setString(resultCourse.getCourseId());
				sql2.setString(resultCourse.getUserId());
				sql2.setDouble(resultCourse.getScoreExam());
				sql2.setDouble(resultCourse.getScoreReport());
				sql2.setDouble(resultCourse.getScoreAttend());
				sql2.setDouble(resultCourse.getScoreForum());
				sql2.setDouble(resultCourse.getScoreEtc1());
				sql2.setDouble(resultCourse.getScoreEtc2());
				sql2.setDouble(resultCourse.getScoreTotal());
				sql2.setString(resultCourse.getRegId());

				log.debug("[registScore - result_score - insert]"+sql1.toString());
				//System.out.println("[registScore - result_score - insert]"+sql1.toString());

				sb.setLength(0);

			} else {
				//--	UPDATE
				ResultCourseDTO resultCourse	=	(ResultCourseDTO)commonMap.get("ResultCourseDTO");
				sql2	=	new QueryStatement();

				sb.append(" update result_course set score_exam = ? , score_report = ? , score_attend = ? ");
				sb.append(", score_forum = ? , score_etc1 = ? , score_etc2 = ? , score_total = ?");
				sb.append(", mod_id = ?, reg_date = CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ");
				sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ? and user_id = ? ");
				sql2.setSql(sb.toString());

				sql2.setDouble(resultCourse.getScoreExam());
				sql2.setDouble(resultCourse.getScoreReport());
				sql2.setDouble(resultCourse.getScoreAttend());
				sql2.setDouble(resultCourse.getScoreForum());
				sql2.setDouble(resultCourse.getScoreEtc1());
				sql2.setDouble(resultCourse.getScoreEtc2());
				sql2.setDouble(resultCourse.getScoreTotal());
				sql2.setString(resultCourse.getModId());
				sql2.setString(resultCourse.getSystemCode());
				sql2.setString(resultCourse.getCurriCode());
				sql2.setInteger(resultCourse.getCurriYear());
				sql2.setInteger(resultCourse.getCurriTerm());
				sql2.setString(resultCourse.getCourseId());
				sql2.setString(resultCourse.getUserId());

				log.debug("[registScore - result_score - update]"+sql1.toString());
				//System.out.println("[registScore - result_score - update]"+sql1.toString());

				sb.setLength(0);

			}

			sqls[1]	=	sql2;



			retVal	=	broker.executeUpdate(sqls);


		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


}
