package com.edutrack.currisub.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.currisub.dto.CurriSubDTO;
import com.edutrack.currisub.dto.CurriSubInfoDTO;
import com.edutrack.curritop.dto.QuizDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


/*
 * @author JamFam
 *
 * 개설과정 관리
 */
public class CurriSubDAO extends AbstractDAO {

	/**
	 * 개설과정의 카운트를 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public int getTotCount(String SystemCode, String CateCode, String CurriCode, int CurriYear) throws DAOException {
		int totCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(a.curri_term),0) as cnt from curri_sub a , curri_top b");
		sb.append(" where a.system_code=b.system_code and a.curri_code=b.curri_code ");
		sb.append(" and a.system_code= ?  ");
		if(!CateCode.equals(""))
			sb.append(" and b.cate_code=? ");
		if(!CurriCode.equals(""))
			sb.append(" and a.curri_code=? ");
		if(CurriYear > 0)
			sb.append(" and a.curri_year=? ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!CateCode.equals(""))
			sql.setString(CateCode);
		if(!CurriCode.equals(""))
			sql.setString(CurriCode);
		if(CurriYear > 0)
			sql.setInteger(CurriYear);

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
	 * 과정 유형에 따른 현재 진행중인 개설 과정 수를 반환한다.
	 * @param SystemCode
	 * @param curriProperty2
	 * @return
	 * @throws DAOException
	 */
	public int getCurriCount(String SystemCode, String curriProperty2, String pareCode1, String pareCode2, String CateCode, String chkAll) throws DAOException {
		int totCount = 0;
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(a.curri_term),0) as cnt from curri_sub a , curri_top b");
		sb.append(" where a.system_code=b.system_code and a.curri_code=b.curri_code ");
		sb.append(" and a.system_code = ?  ");
		if (!curriProperty2.equals(""))	sb.append(" and b.curri_property2 = ? ");
		if (!pareCode1.equals(""))		sb.append(" and b.pare_code1 = ? ");
		if (!pareCode2.equals(""))		sb.append(" and b.pare_code2 = ? ");
		if (!CateCode.equals(""))		sb.append(" and b.cate_code = ? ");
		if (!chkAll.equals("A")) {
			sb.append(" and a.enroll_start <= "+currentDate+" and a.service_end >= "+currentDate+" ");
		}
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!curriProperty2.equals(""))	sql.setString(curriProperty2);
		if(!pareCode1.equals(""))		sql.setString(pareCode1);
		if(!pareCode2.equals(""))		sql.setString(pareCode2);
		if(!CateCode.equals(""))		sql.setString(CateCode);

		log.debug("[getCurriCount]" + sql.toString());

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
	 * 단체 수강시 수강 인원에 맞는 할인 가격을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param DeptCnt
	 * @return
	 * @throws DAOException
	 */
	public int getDeptSaleFee(String SystemCode, String CurriCode, int CurriYear, int CurriTerm , int DeptCnt, int DefaultPrice) throws DAOException {
		int retVal = DefaultPrice;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(sale_fee,?) as sale_fee from curri_sale_info");
		sb.append(" where system_code= ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" and from_cnt <= ? and ? <= to_cnt ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setInteger(DefaultPrice);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setInteger(DeptCnt);
		sql.setInteger(DeptCnt);

		log.debug("[getDeptSaleFee]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	retVal = rs.getInt("sale_fee");
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
		return retVal;
	}


	/**
	 * 단체 수강 할인 가격 첫번째 것을 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @return
	 * @throws DAOException
	 */
	public int getDeptSaleFee1(String SystemCode, String CurriCode, int CurriYear, int CurriTerm ) throws DAOException {
		int retVal = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(sale_fee,0) as sale_fee from curri_sale_info");
		sb.append(" where system_code= ? and curri_code = ? and curri_year = ? and curri_term = ? ");
		sb.append(" order by  from_cnt ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);

		log.debug("[getDeptSaleFee]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	retVal = rs.getInt("sale_fee");
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
		return retVal;
	}


	/**
	 * 개설과정 리스트를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriSubList(String SystemCode, String CateCode, String CurriCode, int CurriYear, String pProfId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(	"SELECT " +
					" a.system_code, t.cate_code, a.curri_code, a.curri_year" +
					", a.curri_term, a.curri_name");
		
		sb.append(	", a.price, a.credit, a.enroll_start, a.enroll_end");
		sb.append(	", CONCAT(getDateText(a.enroll_start ,null) ,' ~ ', getDateText(a.enroll_end ,null)) as enroll_date ");
		//정규과정일 경우에는 기간을 상시과정일 경우에는 기간을 표시
		sb.append(	", CASE WHEN a.service_start != '' AND a.service_end != '' THEN  " +
					" CONCAT(getDateText(a.service_start,null) ,' ~ ', getDateText(a.service_end,null))" +
					" ELSE CASE WHEN a.service_start != '' THEN CONCAT(a.service_start, ' 일') ELSE '' END END AS service_date ");
		sb.append(	", IFNULL(b.cnt,0) as course_count, a.hakgi_term  " +
					", (SELECT cate_depth FROM curri_pcategory1 WHERE system_code = a.system_code AND pare_code1 = t.pare_code1) AS cate_depth  ");
		sb.append(" FROM curri_top t, curri_sub a LEFT OUTER JOIN (SELECT system_code, curri_code, curri_year, curri_term" +
																		", COUNT(course_id) as cnt " +
																	"FROM curri_sub_course " +
																	"GROUP BY system_code, curri_code, curri_year, curri_term) b " +
					"ON (a.system_code=b.system_code AND a.curri_code=b.curri_code " +
						"AND a.curri_year=b.curri_year AND a.curri_term=b.curri_term)");
		sb.append(" WHERE t.system_code=a.system_code and t.curri_code=a.curri_code and a.system_code=? ");

		if(!CateCode.equals(""))
			sb.append(" and (t.cate_code=? or t.pare_code2 = ?) ");
		if(!CurriCode.equals(""))
			sb.append(" and a.curri_code=?" );
		if(!pProfId.equals("")) sb.append(" and a.prof_id='"+pProfId+"'");
		sb.append(" and a.curri_year=?");
		//sb.append(" order by t.curri_name, a.curri_term desc");
		sb.append(" order by a.reg_date desc, t.curri_name");
		sql.setSql(sb.toString());

		sql.setString(SystemCode);
		if(!CateCode.equals("")) {
			sql.setString(CateCode);
			sql.setString(CateCode);
		}
		if(!CurriCode.equals(""))
			sql.setString(CurriCode);
		sql.setInteger(CurriYear);

		//---- 디버그 출력
		log.debug("[getCurriSubList]" + sql.toString());


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
	 * 개별 개설과정 정보를 가져온다.
	 * @param SystemCode
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public CurriSubInfoDTO getCurriSubInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException {
		CurriSubInfoDTO curriSubInfo = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select " +
				"a.system_code, a.curri_code, a.curri_name, a.curri_year" +
				", a.curri_term, a.prof_id, a.price, a.credit, a.curri_type"+
				", a.enroll_start, SUBSTRING(a.enroll_start,1,4) as enroll_start_year, SUBSTRING(a.enroll_start,5,2) as enroll_start_month, SUBSTRING(a.enroll_start,7,2) as enroll_start_day"+
				", a.enroll_end, SUBSTRING(a.enroll_end,1,4) as enroll_end_year, SUBSTRING(a.enroll_end,5,2) as enroll_end_month, SUBSTRING(a.enroll_end,7,2) as enroll_end_day"+
				", a.cancel_start, SUBSTRING(a.cancel_start,1,4) as cancel_start_year, SUBSTRING(a.cancel_start,5,2) as cancel_start_month, SUBSTRING(a.cancel_start,7,2) as cancel_start_day"+
				", a.cancel_end, SUBSTRING(a.cancel_end,1,4) as cancel_end_year, SUBSTRING(a.cancel_end,5,2) as cancel_end_month, SUBSTRING(a.cancel_end,7,2) as cancel_end_day"+
				", a.service_start, SUBSTRING(a.service_start,1,4) as service_start_year, SUBSTRING(a.service_start,5,2) as service_start_month, SUBSTRING(a.service_start,7,2) as service_start_day"+
				", a.service_end, SUBSTRING(a.service_end,1,4) as service_end_year, SUBSTRING(a.service_end,5,2) as service_end_month, substring(a.service_end,7,2) as service_end_day"+
				", a.chung_end, SUBSTRING(a.chung_end,1,4) as chung_end_year, SUBSTRING(a.chung_end,5,2) as chung_end_month, SUBSTRING(a.chung_end,7,2) as chung_end_day"+
				", a.flag_limit, a.limit_num, a.complete_average, a.complete_course"+
				", a.assessment_end, SUBSTRING(a.assessment_end,1,4) as assessment_end_year, SUBSTRING(a.assessment_end,5,2) as assessment_end_month, SUBSTRING(a.assessment_end,7,2) as assessment_end_day"+
				", a.complete_end, SUBSTRING(a.complete_end,1,4) as complete_end_year, SUBSTRING(a.complete_end,5,2) as complete_end_month, SUBSTRING(a.complete_end,7,2) as complete_end_day"+
				", a.complete_score, a.reg_id, "+CommonUtil.getDbDateFormat("a.reg_date")+" as reg_date, a.best_yn "+
				", b.curri_info, b.curri_goal, b.curri_target, b.curri_env, b.assessment, b.before_info, b.learning_time, b.curri_property2"+
				", c.code_so, c.so_name as curri_type_name " +
				", i.user_id, i.user_photo, i.photo_path , i.phone_number, i.career, i.major, i.books, i.intro" +
				", a.sample_contents, a.cancel_config_day, a.cancel_config_study, b.curri_contents, b.curri_contents_text"+
				", b.pare_code1, b.pare_code2, b.cate_code, b.pare_code1 " +
				", (SELECT cate_status FROM curri_pcategory1 WHERE system_code = a.system_code AND pare_code1 = b.pare_code1) AS cate_status " +
				", (SELECT cate_depth FROM curri_pcategory1 WHERE system_code = a.system_code AND pare_code1 = b.pare_code1) AS cate_depth " +
				", (SELECT cate_name FROM curri_pcategory1 WHERE system_code = a.system_code AND pare_code1 = b.pare_code1) AS cate_name1 " +
				", (SELECT cate_name FROM curri_pcategory2 WHERE system_code = a.system_code AND pare_code1 = b.pare_code1 AND pare_code2 = b.pare_code2) AS cate_name2 " +
				", (SELECT cate_name FROM curri_category WHERE system_code = a.system_code AND pare_code1 = b.pare_code1 AND pare_code2 = b.pare_code2 AND cate_code = b.cate_code) AS cate_name3 " +
				", a.government, a.print_img, a.support_add, a.hakgi_term " +
				", a.score_gubun, a.eval_gubun " +

				" FROM curri_sub a LEFT OUTER JOIN prof_info i " +
				" ON a.system_code =i.system_code  and a.prof_id = i.user_id " +
				" INNER JOIN curri_top b  " +
				" ON a.system_code=b.system_code and a.curri_code=b.curri_code  " +
				" INNER JOIN  code_so c " +
				" ON a.system_code=c.system_code  " +
				" and b.curri_property1=c.code_dae and b.curri_property2=c.code_so " +
				" where  a.system_code=?  and a.curri_code=? and a.curri_year=? and a.curri_term=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);

		//---- 디버그 출력
		log.debug("[getCurriSubInfo]" + sql.toString());
		ResultSet rs = null;
		log.debug("________________");
		
	     StringBuffer output = new StringBuffer();
	     try{

	    	rs = broker.executeQuery(sql);
			if(rs.next()){
				curriSubInfo = new CurriSubInfoDTO();
				curriSubInfo.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				curriSubInfo.setPareCode1(StringUtil.nvl(rs.getString("pare_code1"), ""));
				curriSubInfo.setPareCode2(StringUtil.nvl(rs.getString("pare_code2"), ""));
				curriSubInfo.setCateCode(StringUtil.nvl(rs.getString("cate_code"), ""));
				curriSubInfo.setCateStatus(StringUtil.nvl(rs.getString("cate_status"), "0"));
				curriSubInfo.setCateDepth(rs.getInt("cate_depth"));
				curriSubInfo.setCateName1(StringUtil.nvl(rs.getString("cate_name1"), ""));
				curriSubInfo.setCateName2(StringUtil.nvl(rs.getString("cate_name2"), ""));
				curriSubInfo.setCateName3(StringUtil.nvl(rs.getString("cate_name3"), ""));
				curriSubInfo.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				curriSubInfo.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				curriSubInfo.setCurriYear(rs.getInt("curri_year"));
				curriSubInfo.setCurriTerm(rs.getInt("curri_term"));
				curriSubInfo.setProfId(StringUtil.nvl(rs.getString("prof_id")));
				curriSubInfo.setPrice(rs.getInt("price"));
				curriSubInfo.setCredit(rs.getInt("credit"));
				curriSubInfo.setEnrollStart(StringUtil.nvl(rs.getString("enroll_start")));
				curriSubInfo.setEnrollStartYear(StringUtil.nvl(rs.getString("enroll_start_year")));
				curriSubInfo.setEnrollStartMonth(StringUtil.nvl(rs.getString("enroll_start_month")));
				curriSubInfo.setEnrollStartDay(StringUtil.nvl(rs.getString("enroll_start_day")));
				curriSubInfo.setEnrollEnd(StringUtil.nvl(rs.getString("enroll_end")));
				curriSubInfo.setEnrollEndYear(StringUtil.nvl(rs.getString("enroll_end_year")));
				curriSubInfo.setEnrollEndMonth(StringUtil.nvl(rs.getString("enroll_end_month")));
				curriSubInfo.setEnrollEndDay(StringUtil.nvl(rs.getString("enroll_end_day")));
				curriSubInfo.setCancelStart(StringUtil.nvl(rs.getString("cancel_start")));
				curriSubInfo.setCancelStartYear(StringUtil.nvl(rs.getString("cancel_start_year")));
				curriSubInfo.setCancelStartMonth(StringUtil.nvl(rs.getString("cancel_start_month")));
				curriSubInfo.setCancelStartDay(StringUtil.nvl(rs.getString("cancel_start_day")));
				curriSubInfo.setCancelEnd(StringUtil.nvl(rs.getString("cancel_end")));
				curriSubInfo.setCancelEndYear(StringUtil.nvl(rs.getString("cancel_end_year")));
				curriSubInfo.setCancelEndMonth(StringUtil.nvl(rs.getString("cancel_end_month")));
				curriSubInfo.setCancelEndDay(StringUtil.nvl(rs.getString("cancel_end_day")));
				curriSubInfo.setServiceStart(StringUtil.nvl(rs.getString("service_start")));
				curriSubInfo.setServiceStartYear(StringUtil.nvl(rs.getString("service_start_year")));
				curriSubInfo.setServiceStartMonth(StringUtil.nvl(rs.getString("service_start_month")));
				curriSubInfo.setServiceStartDay(StringUtil.nvl(rs.getString("service_start_day")));
				curriSubInfo.setServiceEnd(StringUtil.nvl(rs.getString("service_end")));
				curriSubInfo.setServiceEndYear(StringUtil.nvl(rs.getString("service_end_year")));
				curriSubInfo.setServiceEndMonth(StringUtil.nvl(rs.getString("service_end_month")));
				curriSubInfo.setServiceEndDay(StringUtil.nvl(rs.getString("service_end_day")));
				curriSubInfo.setChungEnd(StringUtil.nvl(rs.getString("chung_end")));
				curriSubInfo.setChungEndYear(StringUtil.nvl(rs.getString("chung_end_year")));
				curriSubInfo.setChungEndMonth(StringUtil.nvl(rs.getString("chung_end_month")));
				curriSubInfo.setChungEndDay(StringUtil.nvl(rs.getString("chung_end_day")));
				curriSubInfo.setFlagLimit(StringUtil.nvl(rs.getString("flag_limit")));
				curriSubInfo.setLimitNum(rs.getInt("limit_num"));
				curriSubInfo.setCompleteAverage(rs.getInt("complete_average"));
				curriSubInfo.setCompleteCourse(rs.getInt("complete_course"));
				curriSubInfo.setAssessmentEnd(StringUtil.nvl(rs.getString("assessment_end")));
				curriSubInfo.setAssessmentEndYear(StringUtil.nvl(rs.getString("assessment_end_year")));
				curriSubInfo.setAssessmentEndMonth(StringUtil.nvl(rs.getString("assessment_end_month")));
				curriSubInfo.setAssessmentEndDay(StringUtil.nvl(rs.getString("assessment_end_day")));
				curriSubInfo.setCompleteEnd(StringUtil.nvl(rs.getString("complete_end")));
				curriSubInfo.setCompleteEndYear(StringUtil.nvl(rs.getString("complete_end_Year")));
				curriSubInfo.setCompleteEndMonth(StringUtil.nvl(rs.getString("complete_end_month")));
				curriSubInfo.setCompleteEndDay(StringUtil.nvl(rs.getString("complete_end_day")));
				curriSubInfo.setCompleteScore(StringUtil.nvl(rs.getString("complete_score")));
				curriSubInfo.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				curriSubInfo.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				curriSubInfo.setBestYn(StringUtil.nvl(rs.getString("best_yn")));
				curriSubInfo.setCurriInfo(StringUtil.nvl(rs.getString("curri_info")));
				curriSubInfo.setCurriGoal(StringUtil.nvl(rs.getString("curri_goal")));
				curriSubInfo.setCurriTarget(StringUtil.nvl(rs.getString("curri_target")));
				curriSubInfo.setCurriEnv(StringUtil.nvl(rs.getString("curri_env")));
				curriSubInfo.setAssessment(StringUtil.nvl(rs.getString("assessment")));
				curriSubInfo.setBeforeInfo(StringUtil.nvl(rs.getString("before_info")));
				curriSubInfo.setLearningTime(StringUtil.nvl(rs.getString("learning_time")));
				curriSubInfo.setCurriProperty2(StringUtil.nvl(rs.getString("curri_property2")));
				curriSubInfo.setCodeSo(StringUtil.nvl(rs.getString("code_so")));
				curriSubInfo.setCurriTypeName(StringUtil.nvl(rs.getString("curri_type_name")));
				curriSubInfo.setUserPhoto(StringUtil.nvl(rs.getString("user_photo")));
				curriSubInfo.setPhotoPath(StringUtil.nvl(rs.getString("photo_path")));
				curriSubInfo.setPhoneNumber(StringUtil.nvl(rs.getString("phone_number")));
				curriSubInfo.setCareer(StringUtil.nvl(rs.getString("career")));
				curriSubInfo.setMajor(StringUtil.nvl(rs.getString("major")));
				curriSubInfo.setBooks(StringUtil.nvl(rs.getString("books")));
				curriSubInfo.setSampleContents(StringUtil.nvl(rs.getString("sample_contents")));
				curriSubInfo.setCancelConfigDay(rs.getInt("cancel_config_day"));
				curriSubInfo.setCancelConfigStudy(rs.getInt("cancel_config_study"));
				curriSubInfo.setCurriContentsText(StringUtil.nvl(rs.getString("curri_contents_text")));
				curriSubInfo.setGovernment(StringUtil.nvl(rs.getString("government")));
				curriSubInfo.setPrintImg(StringUtil.nvl(rs.getString("print_img")));
				curriSubInfo.setSupportAdd(StringUtil.nvl(rs.getString("support_add")));
				curriSubInfo.setCurriType(StringUtil.nvl(rs.getString("curri_type")));
				curriSubInfo.setHakgiTerm(rs.getInt("hakgi_term"));
				curriSubInfo.setScoreGubun(StringUtil.nvl(rs.getString("score_gubun")));
				curriSubInfo.setEvalGubun(StringUtil.nvl(rs.getString("eval_gubun")));

				if(!StringUtil.nvl(rs.getString("user_id")).equals(""))
				{
					curriSubInfo.setIntro(StringUtil.nvl(rs.getString("intro")));
				}

				if(!StringUtil.nvl(rs.getString("curri_contents_text")).equals(""))
				{
		        	curriSubInfo.setCurriContents(StringUtil.nvl(rs.getString("curri_contents")));
				}
		        output.delete(0,output.length());
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
		return curriSubInfo;
	}

	/**
	 * CURRI_TERM 의 MAX값 가져오기
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCurriTerm(String systemCode, String curriCode, int curriYear) throws DAOException {
		int		retVal	=	0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT IFNULL(MAX(curri_term), 0)+1 max_cnt FROM curri_sub ");
		sb.append(" WHERE system_code = ? AND curri_code = ? AND curri_year = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);

		RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 retVal = rs.getInt("max_cnt");
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
		return retVal;
	}


	/**
	 * 개설과정 정보를 저장한다.
	 * @param cateInfo
	 * @return
	 * @throws DAOException
	 */
	public int addCurriSubInfo(CurriSubDTO curriInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO curri_sub(system_code, curri_code, curri_name, curri_year" +
										", curri_term, prof_id, price, credit"+
										", enroll_start, enroll_end, cancel_start, cancel_end" +
										", service_start, service_end, chung_end, flag_limit"+
										", limit_num, complete_average, complete_course, assessment_end" +
										", complete_end, reg_id, reg_date, mod_id"+
										", mod_date, best_yn, sample_contents, cancel_config_day" +
										", cancel_config_study, government, print_img, support_add" +
										", curri_type, hakgi_term, score_gubun, eval_gubun) ");
		sb.append(" VALUES (?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?" +
						", ?, ?, ?, ?)");
		sql.setSql(sb.toString());


		//---- 입력된 값을 가져 온다.
		sql.setString(curriInfo.getSystemCode());
		sql.setString(curriInfo.getCurriCode());
		sql.setString(curriInfo.getCurriName());
		sql.setInteger(curriInfo.getCurriYear());

		//sql.setInteger(curriInfo.getCurriTerm());
		sql.setInteger(getMaxCurriTerm(StringUtil.nvl(curriInfo.getSystemCode()), StringUtil.nvl(curriInfo.getCurriCode()), curriInfo.getCurriYear() ));

		sql.setString(curriInfo.getProfId());
		sql.setInteger(curriInfo.getPrice());
		sql.setInteger(curriInfo.getCredit());
		sql.setString(curriInfo.getEnrollStart());
		sql.setString(curriInfo.getEnrollEnd());
		sql.setString(curriInfo.getCancelStart());
		sql.setString(curriInfo.getCancelEnd());
		sql.setString(curriInfo.getServiceStart());
		sql.setString(curriInfo.getServiceEnd());
		sql.setString(curriInfo.getChungEnd());
		sql.setString(curriInfo.getFlagLimit());
		sql.setInteger(curriInfo.getLimitNum());
		sql.setInteger(curriInfo.getCompleteAverage());
		sql.setInteger(curriInfo.getCompleteCourse());
		sql.setString(curriInfo.getAssessmentEnd());
		sql.setString(curriInfo.getCompleteEnd());
		sql.setString(curriInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriInfo.getBestYn());
		sql.setString(curriInfo.getSampleContents());
		sql.setInteger(curriInfo.getCancelConfigDay());
		sql.setInteger(curriInfo.getCancelConfigstudy());
		sql.setString(curriInfo.getGovernment());
		sql.setString(curriInfo.getPrintImg());
		sql.setString(curriInfo.getSupportAdd());
		sql.setString(curriInfo.getCurriType());
		sql.setInteger(curriInfo.getHakgiTerm());
		sql.setString(curriInfo.getScoreGubun());
		sql.setString(curriInfo.getEvalGubun());


		//---- 디버그 출력
		log.debug("[addCurriSubInfo]" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개설과정 정보를 수정한다.
	 * @param curriInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCurriSubInfo(CurriSubDTO curriInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE curri_sub SET curri_name = ?, prof_id=?, price=?" +
										", credit=?, enroll_start=?" +
										", enroll_end=?, cancel_start = ? "+
										", cancel_end=?, service_start=?" +
										", service_end=?, chung_end=?" +
										", flag_limit=?, limit_num=?" +
										", complete_average=?, complete_course=?"+
										", assessment_end=?, complete_end=?" +
										", mod_id=?, mod_date=?" +
										", best_yn=?, sample_contents = ?"+
										", cancel_config_day = ? , cancel_config_study = ? " +
										", government = ?, print_img = ?" +
										", support_add = ?, curri_type = ? " +
										", score_gubun=?, eval_gubun=? ");
		sb.append(" WHERE " +
					"system_code=? and curri_code=? " +
					"and curri_year=? and curri_term=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(curriInfo.getCurriName());
		sql.setString(curriInfo.getProfId());
		sql.setInteger(curriInfo.getPrice());
		sql.setInteger(curriInfo.getCredit());
		sql.setString(curriInfo.getEnrollStart());
		sql.setString(curriInfo.getEnrollEnd());
		sql.setString(curriInfo.getCancelStart());
		sql.setString(curriInfo.getCancelEnd());
		sql.setString(curriInfo.getServiceStart());
		sql.setString(curriInfo.getServiceEnd());
		sql.setString(curriInfo.getChungEnd());
		sql.setString(curriInfo.getFlagLimit());
		sql.setInteger(curriInfo.getLimitNum());
		sql.setInteger(curriInfo.getCompleteAverage());
		sql.setInteger(curriInfo.getCompleteCourse());
		sql.setString(curriInfo.getAssessmentEnd());
		sql.setString(curriInfo.getCompleteEnd());
		sql.setString(curriInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(curriInfo.getBestYn());
		sql.setString(curriInfo.getSampleContents());
		sql.setInteger(curriInfo.getCancelConfigDay());
		sql.setInteger(curriInfo.getCancelConfigstudy());
		sql.setString(curriInfo.getGovernment());
		sql.setString(curriInfo.getPrintImg());
		sql.setString(curriInfo.getSupportAdd());
		sql.setString(curriInfo.getCurriType());
		sql.setString(curriInfo.getScoreGubun());
		sql.setString(curriInfo.getEvalGubun());

		// where
		sql.setString(curriInfo.getSystemCode());
		sql.setString(curriInfo.getCurriCode());
		sql.setInteger(curriInfo.getCurriYear());
		sql.setInteger(curriInfo.getCurriTerm());

		//---- 디버그 출력
		log.debug("[editCurriSubInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	/**
	 * 과정명만 변경 하여 준다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriName
	 * @param ModId
	 * @return
	 * @throws DAOException
	 */
	public int editCurriSubCurriName(String SystemCode, String CurriCode, String CurriName, String ModId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update curri_sub set curri_name=?, mod_id= ? , mod_date = ?");
		sb.append(" where system_code=? and curri_code=? ");
		sql.setSql(sb.toString());

		//---- 입력된 값을 가져 온다.
		sql.setString(CurriName);
		sql.setString(ModId);
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(SystemCode);
		sql.setString(CurriCode);

		//---- 디버그 출력
		log.debug("[editCurriSubCurriName]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 개설과정 정보를 삭제한다.
	 * @param SystemCode
	 * @param CateCode
	 * @return
	 * @throws DAOException
	 */
	public int delCurriSubInfo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from curri_sub where system_code=? and curri_code=? and curri_year=? and curri_term=?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);

		//---- 디버그 출력
		log.debug("[delCurriSubInfo]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;

	}

	/**
	 * 현재 진행중인 개설과정 리스트를 가져온다. enroll_start ~ enroll_end or enroll_start ~ service_end
	 * @param SystemCode
	 * @param Property1
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet currentCurriList(String SystemCode, String pareCode1, String pareCode2, String CateCode, String Property1, String Property2, String chkAll, int limit) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		if (!pareCode1.equals("")) addWhere += " and ct.pare_code1 = ?";
		if (!pareCode2.equals("")) addWhere += " and ct.pare_code2 = ?";
		if (!CateCode.equals("")) addWhere += " and ct.cate_code = ?";
		if (!Property1.equals("")) addWhere += " and ct.curri_property1 = ?";
		if (!Property2.equals("")) addWhere += " and ct.curri_property2 = ?";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		sb.append("select " +
				" ct.pare_code2, ct.curri_info" +
				", cs.curri_code, cs.curri_year, cs.curri_term, cs.hakgi_term" +
				", cs.curri_name" +
				", ct.curri_property2, cs.enroll_start, cs.enroll_end, cs.service_start" +
				", cs.service_end, cs.reg_date, cs.price, cs.sample_contents  ");
		
		sb.append(" from curri_sub cs, curri_top ct ");
		sb.append(" where cs.system_code = ct.system_code and cs.curri_code=ct.curri_code and cs.system_code=? ");
		if (!chkAll.equals("A")) {
			if (Property2.equals("R"))
				sb.append(" and cs.enroll_start <= "+currentDate+" and cs.service_end >= "+currentDate+" ");
		}
		if (!addWhere.equals("")) sb.append(addWhere);
		sb.append(" order by cs.reg_date desc ");
		
		sql.setString(SystemCode);
		if (!pareCode1.equals("")) sql.setString(pareCode1);
		if (!pareCode2.equals("")) sql.setString(pareCode2);
		if (!CateCode.equals("")) sql.setString(CateCode);
		if (!Property1.equals("")) sql.setString(Property1);
		if (!Property2.equals("")) sql.setString(Property2);

		
		if (limit > 0) {
			sb.append(" limit ? ");
			sql.setInteger(limit);
		}
		sql.setSql(sb.toString());

		//---- 디버그 출력
		log.debug("[currentCurriList]" + sql.toString());

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
	 * 종료된 과정 가져오기
	 * @param SystemCode
	 * @param CateCode
	 * @param Property1
	 * @return
	 * @throws DAOException
	 */
	public RowSet completeCurriList(String SystemCode, String CateCode, String Property1, String Property2, int CurriYear, int HakgiTerm) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		if(!CateCode.equals("")) addWhere += " and b.cate_code = ?";
		if(!Property1.equals("")) addWhere += " and b.curri_property1 = ?";
		if(!Property2.equals("")) addWhere += " and b.curri_property2 = ?";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		sb.append(	" SELECT " +
					" a.curri_code, a.curri_year, a.curri_term, a.hakgi_term" +
					", a.curri_name"+
					//", concat( a.curri_name,' [',CAST(a.curri_year AS CHAR),'년 ',CAST(a.curri_term AS CHAR),'기]') AS sel_curri_name" +
					", b.curri_property2 AS curri_type, a.enroll_start AS enroll_start, a.enroll_end AS enroll_end, a.service_start AS service_start" +
					", a.service_end AS service_end, a.price, a.sample_contents ");

		sb.append(	" FROM curri_sub a, curri_top b");
		sb.append(	" WHERE a.system_code = b.system_code and a.curri_code=b.curri_code " +
					" and a.system_code=? ");
		if(Property2.equals("R"))
			sb.append("  and a.service_end < "+currentDate+" ");
		
		if(CurriYear > 0 || HakgiTerm>0){
			addWhere += "  and a.curri_year= ? and a.hakgi_term= ? ";
		}
		
		//sb.append(" and a.chung_end < "+currentDate+" and a.service_end < "+currentDate+" ");
		if(!addWhere.equals("")) sb.append(addWhere);
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!CateCode.equals("")) sql.setString(CateCode);
		if(!Property1.equals("")) sql.setString(Property1);
		if(!Property2.equals("")) sql.setString(Property2);
		if(CurriYear > 0 || HakgiTerm>0){
			sql.setInteger(CurriYear);
			sql.setInteger(HakgiTerm);
		}
		
		//---- 디버그 출력
		log.debug("[completeCurriList]" + sql.toString());

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
	 * 검색된 개설과정 리스트를 가져온다. enroll_start ~ enroll_end or enroll_start ~ service_end
	 * @param SystemCode
	 * @param Property1
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriSearchList(String SystemCode, String CateCode, String Search) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		sb.append(	" SELECT " +
					" a.curri_code, a.curri_year, a.curri_term, a.hakgi_term" +
					", b.curri_name"+
					//", concat(b.curri_name,' [',CAST(a.curri_year AS CHAR),'년 ',CAST(a.curri_term AS CHAR),'기]') AS sel_curri_name" +
					", b.curri_property2 AS curri_type, a.enroll_start AS enroll_start, a.enroll_end AS enroll_end, a.service_start AS service_start" +
					", a.service_end AS service_end ");
		sb.append(	" FROM curri_sub a, curri_top b ");
		sb.append(	" WHERE a.system_code = b.system_code and a.curri_code=b.curri_code " +
					" and a.system_code=? ");
		sb.append(	" and a.enroll_start <= "+currentDate+" and a.service_end >= "+currentDate+" ");
		//sb.append(" and b.cate_code = ? and b.curri_name like ? ");
		sb.append(	" and LOWER( concat(b.curri_name,b.curri_goal,b.curri_info,b.curri_env,b.assessment,b.before_info,b.curri_target)) like LOWER(?) ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		//sql.setString(CateCode);
		sql.setString("%"+Search+"%");

					//---- 디버그 출력
		log.debug("[getCurriSearchList]" + sql.toString());
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
	 * 과정검색 추천리스트
	 * @param SystemCode
	 * @param keyword
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriSearch(String SystemCode, String keyword) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		sb.append("select b.curri_name ");
		sb.append(" from curri_sub a, curri_top b");
		sb.append(" where a.system_code = b.system_code and a.curri_code=b.curri_code and a.system_code=? ");
		sb.append(" and a.enroll_start <= "+currentDate+" and a.service_end >= "+currentDate+" ");
		//sb.append(" and b.cate_code = ? and b.curri_name like ? ");
		sb.append(" and LOWER( concat(b.curri_name,b.curri_goal,b.curri_info,b.curri_env,b.assessment,b.before_info,b.curri_target)) like LOWER(?) ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		//sql.setString(CateCode);
		sql.setString("%"+keyword+"%");

		//---- 디버그 출력
		log.debug("[getCurriSearch]" + sql.toString());
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
	 * 청강 종료되지 않는 개설과정 리스트를 가져온다. (메인에서 사용)
	 * @param SystemCode
	 * @param CateCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet MainCurriList(String SystemCode, String CateCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		if(!CateCode.equals("")) addWhere += " and b.cate_code = ?";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		sb.append(	" SELECT " +
					" a.curri_code, a.curri_year, a.curri_term, a.hakgi_term" +
					", b.curri_name"+
					//", concat(b.curri_name,' [',CAST(a.curri_year AS CHAR),'년 ',CAST(a.curri_term AS CHAR),'기]') AS sel_curri_name"+
					", b.curri_property2 AS curri_type" +
					", "+CommonUtil.getDbDateFormat("a.enroll_start")+" AS enroll_start" +
					", "+CommonUtil.getDbDateFormat("a.enroll_end")+" AS enroll_end" +
					", "+CommonUtil.getDbDateFormat("a.service_start")+" AS service_start" +
					", "+CommonUtil.getDbDateFormat("a.service_end")+" AS service_end ");
		sb.append(	" FROM curri_sub a, curri_top b, curri_category c");
		sb.append(	" WHERE a.system_code = b.system_code and b.system_code=c.system_code ");
		sb.append(	" and a.curri_code=b.curri_code and b.cate_code=c.cate_code " +
					" and c.use_yn='Y' and a.system_code=? ");
		sb.append(	" and a.chung_end >= "+currentDate+" ");
		if(!addWhere.equals("")) sb.append(addWhere);
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		if(!CateCode.equals("")) sql.setString(CateCode);

		//---- 디버그 출력
		log.debug("[MainCurriList]" + sql.toString());
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
	 * 교수자의 해당 과정 리스트를 가져온다.
	 * @param SystemCode
	 * @param CurriStatus	E -> 수강신청기간 S -> 수강기간(청강기간포함) C -> 수강종료 ES -> 수강신청~청강종료
	 * @return
	 * @throws DAOException
	 */
	public RowSet getProfCurriList(String SystemCode,String ProfId, String CurriStatus, int top) throws DAOException {
		QueryStatement	sql			=	new QueryStatement();
		StringBuffer 	sb 			= 	new StringBuffer();
		String 			addWhere 	= 	"";
		String 			currentDate =	"CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		String			pOrder		=	" ORDER BY c.curri_name ASC ";
		if(CurriStatus.equals("ES")) addWhere += " AND c.enroll_start <= "+currentDate+" AND c.chung_end >= "+currentDate+" ";
		if(CurriStatus.equals("E")) addWhere += " AND c.enroll_start <= "+currentDate+" AND c.enroll_end >= "+currentDate+" ";
		if(CurriStatus.equals("S")) addWhere += " AND c.service_start <= "+currentDate+" AND c.service_end >= "+currentDate+" ";
		if(CurriStatus.equals("C")) addWhere += " AND c.service_start < "+currentDate+" ";
		if(CurriStatus.equals("CF")) {
			addWhere += " AND c.service_end <= "+currentDate+" ";
			pOrder	=	" ORDER BY c.service_start DESC ";
		}

		sb.append("SELECT ");
		sb.append(" c.curri_code, c.curri_year, c.curri_term, c.hakgi_term, ");
		sb.append(" c.curri_name, t.curri_property1, ");
		sb.append(" t.curri_property2, c.prof_id, ");
		sb.append("c.enroll_start AS enroll_start, ");
		sb.append("c.enroll_end AS enroll_end, ");
		sb.append("c.service_start AS service_start, ");
		sb.append("c.service_end AS service_end, ");
		sb.append("c.chung_end AS chung_end ");
		sb.append(" FROM  curri_sub c,curri_top t, curri_sub_course p ");
		sb.append(" WHERE t.system_code=c.system_code AND t.system_code=p.system_code AND t.system_code=? ");
		sb.append(" AND t.curri_code=c.curri_code AND t.curri_code=p.curri_code AND c.curri_year=p.curri_year ");
		sb.append(" AND c.curri_term=p.curri_term AND (p.prof_id=? OR c.prof_id=? ) ");

		if(!addWhere.equals("")) sb.append(addWhere);
		sb.append(" GROUP BY c.curri_code, c.curri_year, c.curri_term,c.curri_name, t.curri_property1,");
		sb.append(" t.curri_property2, c.prof_id,c.enroll_start,c.enroll_end, c.service_start, c.service_end,");
		sb.append(" c.chung_end");
		sb.append(pOrder);
		if(top > 0) {
			sb.append(" limit 5 ");
		}

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(ProfId);
		sql.setString(ProfId);

		//---- 디버그 출력
		log.debug("[getProfCurriList]" + sql.toString());
		//System.out.println("[getProfCurriList]" + sql.toString());

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
	 * 조교의 해당 과정 리스트를 가져온다.
	 * 
	 * @param SystemCode
	 * @param jogyoId
	 * @param CurriStatus	E -> 수강신청기간 S -> 수강기간(청강기간포함) C -> 수강종료 ES -> 수강신청~청강종료
	 * @param top
	 * @return
	 * @throws DAOException
	 */
	public RowSet getJogyoCurriList(String SystemCode,String jogyoId, String CurriStatus, int top) throws DAOException {
		QueryStatement	sql			=	new QueryStatement();
		StringBuffer 	sb 			= 	new StringBuffer();
		String 			addWhere 	= 	"";
		String 			currentDate =	"CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		String			pOrder		=	" ORDER BY cs.curri_name ASC ";
		if(CurriStatus.equals("ES")) addWhere += " AND cs.enroll_start <= "+currentDate+" AND cs.chung_end >= "+currentDate+" ";
		if(CurriStatus.equals("E")) addWhere += " AND cs.enroll_start <= "+currentDate+" AND cs.enroll_end >= "+currentDate+" ";
		if(CurriStatus.equals("S")) addWhere += " AND cs.service_start <= "+currentDate+" AND cs.service_end >= "+currentDate+" ";
		if(CurriStatus.equals("C")) addWhere += " AND cs.service_start < "+currentDate+" ";
		if(CurriStatus.equals("CF")) {
			addWhere += " AND cs.service_end <= "+currentDate+" ";
			pOrder	=	" ORDER BY cs.service_start DESC ";
		}

		sb.append(" SELECT " +
						"ct.curri_code, ct.curri_property1, ct.curri_property2, cs.curri_year " +
						", cs.curri_term, cs.hakgi_term, cs.curri_name, cs.prof_id " +
						", cs.enroll_start, cs.enroll_end, cs.service_start, cs.service_end " +
						", cs.chung_end ");
		sb.append(" FROM  curri_sub cs, curri_top ct, jogyo jg ");
		sb.append(" WHERE " +
						" cs.system_code = ct.system_code AND ct.curri_code = cs.curri_code " +
						" AND jg.system_code = cs.system_code AND jg.curri_code = cs.curri_code " +
						" AND jg.curri_year = cs.curri_year AND jg.curri_term = cs.curri_term " +
						" AND ct.system_code = ? AND jg.jogyo_id = ?  ");
		if(!addWhere.equals("")) sb.append(addWhere);
		sb.append(pOrder);
		
		if(top > 0) {
			sb.append(" limit 5 ");
		}

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(jogyoId);
		
		//---- 디버그 출력
		log.debug("[getJogyoCurriList]" + sql.toString());
		//System.out.println("[getJogyoCurriList]" + sql.toString());

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
	 * 학습자의 수강  과정 리스트를 가져온다.
	 * @param SystemCode
	 * @param StId
	 * @param CurriStatus		E -> 수강신청기간 S -> 수강기간(청강기간포함) C -> 수강종료 ES -> 수강신청~청강종료
	 * @return
	 * @throws DAOException
	 */
	public RowSet getStCurriListOld(String SystemCode,String StId, String CurriStatus, int top) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		if(CurriStatus.equals("EN")){
			addWhere += " and (s.enroll_status='E' or s.enroll_status='N')";
			addWhere += " and c.enroll_start <= "+currentDate+" and c.chung_end >= "+currentDate+" ";
		}
		if(CurriStatus.equals("ES")){
			addWhere += " and (s.enroll_status='E' or s.enroll_status='S')";
			addWhere += " and c.enroll_start <= "+currentDate+" and c.chung_end >= "+currentDate+" ";
		}
		if(CurriStatus.equals("E")){
			addWhere += " and s.enroll_status='E' ";
			addWhere += " and c.enroll_start <= "+currentDate+" and c.enroll_end >= "+currentDate+" ";
		}
		if(CurriStatus.equals("S")){
			addWhere += " and s.enroll_status='S' ";
			//addWhere += " and c.service_start <= "+currentDate+" and c.service_end >= "+currentDate+" ";
		}
		if(CurriStatus.equals("C")){
			addWhere += " and (s.enroll_status='C' or c.service_end < "+currentDate+") ";
			addWhere += " and c.chung_end < "+currentDate+" ";
		}


		sb.append("select ");

		sb.append(" s.curri_code AS curri_code, s.curri_year AS curri_year, s.curri_term AS curri_term, ");
		sb.append(" c.curri_name AS curri_name, t.curri_property1 AS curri_property1, ");
		sb.append("  t.curri_property2 AS curri_type, c.prof_id AS prof_id, s.enroll_status, ");
		sb.append(CommonUtil.getDbDateFormat("c.enroll_start")+" AS enroll_start, ");
		sb.append(CommonUtil.getDbDateFormat("c.enroll_end")+" AS enroll_end, ");
		sb.append(CommonUtil.getDbDateFormat("c.service_start")+" AS service_start, ");
		sb.append(CommonUtil.getDbDateFormat("c.service_end")+" AS service_end, ");
		sb.append(" c.service_start AS servicestart, c.chung_end AS chung_end, c.hakgi_term ");
		sb.append(" from  student s, curri_sub c,curri_top t ");
		sb.append(" where s.system_code=c.system_code and s.system_code=t.system_code and t.system_code=? ");
		sb.append(" and s.curri_code=c.curri_code and s.curri_code=t.curri_code and s.curri_year=c.curri_year ");
		sb.append(" and s.curri_term=c.curri_term and s.user_id = ?  ");
//		if(top > 0) {
//			sb.append(" and limit 5 ");
//		}
		if(!addWhere.equals("")) sb.append(addWhere);
		//sb.append(" GROUP BY c.curri_code, c.curri_year, c.curri_term,c.curri_name, t.curri_property1,");
		//sb.append(" t.curri_property2, c.prof_id,c.enroll_start,c.enroll_end, c.service_start, c.service_end,");
		//sb.append(" c.chung_end");
		sb.append(" order by s.enroll_status desc, c.curri_name asc ");
		if(top > 0) {
			sb.append(" LIMIT 5 ");
		}
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(StId);

		//---- 디버그 출력
		log.debug("[getStCurriList]" + sql.toString());
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
	 * 학생이 수강하는 총 강좌수
	 */
	public int getTotalCurriCount(String SystemCode, String CurriStatus, String stuId) throws DAOException {
		int 	retVal 		=	0;
		String	currentDate =	" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ";
		String	addWhere	=	"";
		if(CurriStatus.equals("EN")){
			addWhere = " and (enroll_status='E' or enroll_status='N')";
		}
		if(CurriStatus.equals("ES")){
			addWhere = " and (enroll_status='E' or enroll_status='S')";
		}
		if(CurriStatus.equals("E")){
			addWhere = " and enroll_status='E' ";
		}
		if(CurriStatus.equals("S")){
			addWhere = " and enroll_status='S' AND serviceend_date > "+currentDate+"  ";
		}
		if(CurriStatus.equals("C")){
			addWhere = " and enroll_status='C'";
		}
		if(CurriStatus.equals("CF")){
			addWhere = " and ( enroll_status='C' or enroll_status='F' or serviceend_date <= "+currentDate+" )";
		}

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT count(*) AS cnt FROM student  ");
		sb.append(" WHERE system_code = ? AND user_id = ? "+addWhere);

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(stuId);

		RowSet	rs	= null;
		try {
			rs = broker.executeQuery(sql);
			if(rs.next()) {
				retVal = rs.getInt("cnt");
			}
			rs.close();
		} catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 수강생의 수강신청, 수강, 수료 현황을 가져온다.
	 * @param SystemCode
	 * @param StId
	 * @param CurriStatus			E -> 수강신청기간 S -> 수강기간(청강기간포함) C -> 수강종료 ES -> 수강신청~청강종료
	 * @param top
	 * @return
	 * @throws DAOException
	 */
	public RowSet getStCurriList(String SystemCode,String StId, String CurriStatus, int top, String pPareCode1) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		String addSelect = "";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		String	dateGubun = "";
		String	reportDateGubun = "";

		if(CurriStatus.equals("EN")){
			addWhere = " and (s.enroll_status='E' or s.enroll_status='N')";
		}
		if(CurriStatus.equals("ES")){
			addWhere = " and (s.enroll_status='E' or s.enroll_status='S')";
			addSelect = " , getBookmarkCnt(s.system_code, s.curri_code, s.curri_year, s.curri_term, s.user_id) AS bookmark_cnt";
		}
		if(CurriStatus.equals("E")){
			addWhere = " and s.enroll_status='E' ";
		}
		if(CurriStatus.equals("S")){
			addWhere = " and s.enroll_status='S' AND s.serviceend_date > X.cur_date  ";
			addSelect = " , getBookmarkCnt(s.system_code, s.curri_code, s.curri_year, s.curri_term, s.user_id) AS bookmark_cnt";
		}
		if(CurriStatus.equals("C")){
			addWhere = " and s.enroll_status='C'";
		}
		if(CurriStatus.equals("CF")){
			addWhere = " and ( s.enroll_status='C' or s.enroll_status='F' or s.serviceend_date <= "+currentDate+" )";
		} else {
			dateGubun = " AND end_date >= X.cur_date ";
			reportDateGubun = " AND report_end_date >= X.cur_date ";
		}

		sb.append(" SELECT " +
						" AA.*" +
						", BB.total_time, BB.show_time, BB.open_cnt, BB.show_contents_cnt" +
						", BB.contents_cnt, BB.pro_rate, CC.off_total_time, CC.off_show_time" +
						", CC.off_contents_cnt, CC.off_attend_cnt ");
		sb.append(" FROM ");

		//-- 수강강좌의 기본정보를 가져온다.
		sb.append(" ( ");
		sb.append(		" SELECT " +
							" s.system_code, s.curri_code, s.curri_year, s.curri_term" +
							", s.user_id, s.payment_idx " +
							", (SELECT COUNT(report_id) FROM report_info " +
								" WHERE system_code = c.system_code AND curri_code = c.curri_code " +
								" AND curri_year = s.curri_year AND curri_term = c.curri_term AND report_regist_yn = 'Y' " +
								" AND report_end_date >= X.cur_date) AS report_cnt " +				//과제 갯수
							", (SELECT COUNT(exam_id) FROM exam_info " +
								" WHERE system_code = c.system_code AND curri_code = c.curri_code  " +
								" AND curri_year = s.curri_year AND curri_term = c.curri_term AND flag_use = 'Y' " +
								" AND end_date >= X.cur_date ) AS exam_cnt " +						//시험 갯수
							", (SELECT COUNT(forum_id) FROM course_forum_info " +
								" WHERE system_code = c.system_code AND curri_code = c.curri_code " +
								" AND curri_year = s.curri_year AND curri_term = c.curri_term AND regist_yn = 'Y' " +
								" AND end_date >= X.cur_date ) AS forum_cnt " +						//토론방 갯수
							", s.enroll_no , s.enroll_status, s.servicestart_date, s.serviceend_date " +
							", s.chungend_date, s.enroll_date, s.get_credit, c.credit " +
							", c.curri_name , t.curri_property2 , c.cancel_start , c.cancel_end" +
							", c.service_start , c.service_end, c.chung_end , c.cancel_config_day" +
							", c.cancel_config_study , c.hakgi_term , substring(X.cur_date, 1, 8) AS cur_date, t.curri_info" +
							", t.cate_code  ");
		if(pPareCode1 != null)
			sb.append(		", (SELECT cate_name FROM curri_pcategory1 WHERE system_code = c.system_code AND pare_code1 = '"+pPareCode1+"' ) AS cate_name ");
		if (!addSelect.equals("")) sb.append(addSelect);

		sb.append(		" FROM " +
							" student s , curri_top t, curri_sub c " +
							", (SELECT "+currentDate+" AS cur_date FROM dual) X ");
		sb.append(		" WHERE " +
							" s.system_code=t.system_code AND s.curri_code=t.curri_code " +
							" AND s.system_code=c.system_code AND s.curri_code=c.curri_code " +
							" AND s.curri_year=c.curri_year AND s.curri_term=c.curri_term " +
							" AND s.user_id = ? ");
		sql.setString(StId);

		if(pPareCode1 != null) sb.append(" and t.pare_code1='"+pPareCode1+"' ");
		if(!addWhere.equals("")) sb.append(addWhere);

		sb.append(" ) AA ");

		//-- 진도율과 컨테츠 갯수, 수강 컨텐츠 갯수를 가져온다.
		sb.append(" LEFT OUTER JOIN ( ");
		sb.append(		" SELECT " +
							" X.* " +
							", SUM(X.open_cnt) AS show_contents_cnt, COUNT(X.contents_id) AS contents_cnt " +
							", CASE WHEN COUNT(X.contents_id) = 0 THEN 0 " +
							" ELSE " +
								" CASE WHEN sum(X.show_time) = 0 OR sum(X.total_time) = 0 THEN 0 " +
								" ELSE round((sum(round((show_time / total_time) * 100, 2)) / (COUNT(X.contents_id)*100)) * 100, 1) END " +
							" END AS pro_rate ");
		sb.append(		" FROM ");
		sb.append(		" ( ");
		sb.append(				" SELECT " +
								" cc.system_code, cc.curri_code, cc.curri_year, cc.curri_term" +
								", cc.contents_id, b.user_id" +
								", IFNULL(cc.show_time, 0) AS total_time " +
								", CASE WHEN cc.show_time <= IFNULL(b.total_time, 0) THEN cc.show_time ELSE IFNULL(b.total_time, 0) END AS show_time " +
								", CASE WHEN b.open_chk = 'Y' THEN 1 ELSE 0 END AS open_cnt ");
		sb.append(				" FROM " +
									" curri_contents cc LEFT OUTER JOIN bookmark b " +
									" ON b.system_code = cc.system_code AND b.curri_code = cc.curri_code " +
									" AND b.curri_year = cc.curri_year AND b.curri_term = cc.curri_term " +
									" AND b.course_id = cc.course_id AND b.contents_id = cc.contents_id " +
									" AND b.user_id = ? ");
		sql.setString(StId);

		sb.append(				" WHERE cc.lecture_gubun = '1' AND cc.contents_type = 'F' ");
		sb.append(		" ) X ");
		sb.append(		" GROUP BY X.system_code, X.curri_code, X.curri_year, X.curri_term ");
		sb.append(" ) BB ");
		sb.append(" ON " +
					" (AA.system_code = BB.system_code AND AA.curri_code = BB.curri_code " +
					" AND AA.curri_year = BB.curri_year AND AA.curri_term = BB.curri_term " +
					" AND BB.system_code = ?) ");
		sql.setString(SystemCode);
		
		//-- 오프라인 과목의 출석과 동영상 수강시간
		sb.append(" LEFT OUTER JOIN  ");
		sb.append(" ( ");
		sb.append(		" SELECT " +
							" csc.system_code, csc.curri_code, csc.curri_year, csc.curri_term" +
							", csc.course_id " +
							", SUM(CASE WHEN cc.lecture_gubun = '1' THEN cc.show_time ELSE 0 END) AS off_total_time " +
							", SUM(CASE WHEN cc.lecture_gubun = '1' THEN IFNULL(b.total_time, 0) ELSE 0 END) AS off_show_time " +
							", SUM(CASE WHEN cc.lecture_gubun = '2' THEN 1 ELSE 0 END) AS off_contents_cnt " +
							", SUM(CASE WHEN cc.lecture_gubun = '2' AND (b.attendance IS NOT NULL OR b.attendance != '') then 1  ELSE 0 END) AS off_attend_cnt ");
		sb.append(		" FROM " +
							" curri_sub_course csc, curri_contents cc LEFT OUTER JOIN bookmark b " +
							" ON b.system_code = cc.system_code AND b.curri_code = cc.curri_code " +
							" AND b.curri_year = cc.curri_year AND b.curri_term = cc.curri_term " +
							" AND b.course_id = cc.course_id AND b.contents_id = cc.contents_id " +
							" AND b.user_id = ? ");
		sql.setString(StId);
		sb.append(		" WHERE " +
							" cc.system_code = csc.system_code AND cc.curri_code = csc.curri_code " +
							" AND cc.curri_year = csc.curri_year AND cc.curri_term = csc.curri_term " +
							" AND cc.course_id = csc.course_id AND csc.online_yn = 'N' " +
							" AND cc.contents_type='F' ");
		sb.append(		" GROUP BY csc.system_code, csc.curri_code, csc.curri_year, csc.curri_term, csc.course_id ");
		sb.append(" ) CC ");
		sb.append(" ON " +
					" (AA.system_code = CC.system_code AND AA.curri_code = CC.curri_code " +
					" AND AA.curri_year = CC.curri_year AND AA.curri_term = CC.curri_term " +
					" AND CC.system_code = ?) ");
		sql.setString(SystemCode);
		

		sb.append(" ORDER BY AA.cate_code ASC, AA.curri_name  ASC  ");

		if(top > 0) sb.append(" limit 5 ");

		//--, ifnull(s.team_no,0) AS dept_enroll_idx, s.reg_id, s.reg_date, s.mod_id, s.mod_date, '' AS payment_idx
		sql.setSql(sb.toString());


		//---- 디버그 출력
		log.debug("[getStCurriList]" + sql.toString());
		//System.out.println("[getStCurriList]" + sql.toString());

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
	 * 과정운영자자의 개설 과정 리스트를 가져온다.
	 * @param SystemCode
	 * @param StId
	 * @param CurriStatus		E -> 수강신청기간 S -> 수강기간(청강기간포함) C -> 수강종료 ES -> 수강신청~청강종료
	 * @return
	 * @throws DAOException
	 */
	public RowSet getManagerCurriList(String SystemCode,String ManagerId, String CurriStatus, int top) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		if(CurriStatus.equals("ES")) addWhere += " and c.enroll_start <= "+currentDate+" and c.chung_end >= "+currentDate+" ";
		if(CurriStatus.equals("E")) addWhere += " and c.enroll_start <= "+currentDate+" and c.enroll_end >= "+currentDate+" ";
		if(CurriStatus.equals("S")) addWhere += " and c.service_start <= "+currentDate+" and c.service_end >= "+currentDate+" ";
		if(CurriStatus.equals("C")) addWhere += " and c.service_start < "+currentDate+" ";


		sb.append("SELECT ");

		sb.append(	" c.curri_code AS curri_code, c.curri_year AS curri_year, c.curri_term AS curri_term," +
					" c.hakgi_term, ");
		sb.append(	" c.curri_name AS curri_name, t.curri_property1 AS curri_property1, ");
		sb.append(	" t.curri_property2 AS curri_type, c.prof_id AS prof_id, ");
		sb.append(	"c.enroll_start AS enroll_start, ");
		sb.append(	"c.enroll_end AS enroll_end, ");
		sb.append(	"c.service_start AS service_start, ");
		sb.append(	"c.service_end AS service_end, ");
		sb.append(	"c.chung_end AS chung_end ");
		sb.append(	" FROM   curri_sub c, curri_top t ");
		sb.append(	" WHERE c.system_code=t.system_code and c.system_code=? ");
		sb.append(	" and c.curri_code=t.curri_code and c.prof_id = ?  ");
//		if(top > 0){
//			sb.append(" and limit 5 ");
//		}
		if(!addWhere.equals("")) sb.append(addWhere);
		sb.append(" order by c.curri_name asc ");
		if(top > 0){
			sb.append(" LIMIT 5 ");
		}
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(ManagerId);

		//---- 디버그 출력
		log.debug("[getManagerCurriList]" + sql.toString());
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
	 * 수료처리 해당과정을 가져온다.
	 * @param curpage
	 * @param SystemCode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCompleteCurriList(int curpage, String systemCode, String property1) throws DAOException {
		ListDTO retVal = null;
		try{
//			---- List Sql 문생성
			String addWhere = "";
			ListStatement sql = new ListStatement();

			sql.setTotalCol(" a.curri_code ");
			sql.setCutCol(	" concat(a.curri_code,CAST(a.curri_year AS CHAR), CAST(a.curri_term AS CHAR)) ");
			sql.setAlias(	" curri_code, curri_name, curri_property2, so_name" +
							", curri_year, curri_term, complete_score, complete_yn" +
							", score_gubun, curri_sub_name, service_start, service_end" +
							", complete_average, complete_course,mod_date" +
							", non_complete_cnt, hakgi_term ");
			sql.setSelect(	" a.curri_code, a.curri_name, b.curri_property2, c.so_name , a.curri_year" +
							", a.curri_term,a.service_start, a.service_end, a.complete_score" +
							", a.complete_yn, a.score_gubun" +
							//", concat(b.curri_name ,' [',CAST(a.curri_year AS CHAR),'년 ',CAST(a.curri_term AS CHAR),'기]') AS curri_sub_name" +
							",a.complete_average, a.complete_course,a.mod_date, ifnull(d.non_complete_cnt,0) AS non_complete_cnt" +
							", a.hakgi_term ");

			sql.setFrom(	" curri_top b, code_so c,curri_sub a LEFT OUTER JOIN  (select system_code, curri_code, curri_year, curri_term" +
																					", ifnull(count(course_id),0) AS non_complete_cnt " +
																					"from curri_sub_course " +
																					"where complete_score='N' " +
																					"group by system_code, curri_code, curri_year, curri_term) d " +
							"ON   a.system_code=d.system_code and a.curri_code=d.curri_code " +
							"and a.curri_year=d.curri_year and a.curri_term=d.curri_term ");

			sql.setWhere(	" a.system_code=b.system_code and  a.curri_code=b.curri_code " +
							" and a.system_code=? and (a.service_end < CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) or b.curri_property2='O') " +
							" and b.system_code=c.system_code  and b.curri_property2 = c.code_so  and ifnull(a.score_gubun,'')  != '1'  ");

			sql.setOrderby(" a.service_start desc");

			sql.setGroupby(	" a.curri_code, b.curri_name,b.curri_property2,c.so_name" +
							", a.curri_year, a.curri_term,a.service_start, a.service_end" +
							", a.complete_score, a.complete_yn, a.score_gubun, a.complete_average" +
							", a.complete_course,a.mod_date,d.non_complete_cnt ");
			sql.setString(systemCode);


			//---- 디버그 출력
			log.debug("[getCompleteCurriList]" + sql.toString());

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
	 * 과정소개 메인의 위치 표시 기능
	 * @param SystemCode
	 * @param pare_code1
	 * @param pare_code2
	 * @param cate_code
	 * @return
	 * @throws DAOException
	 */
	public RowSet curriWis(String SystemCode, String pare_code1, String pare_code2, String cate_code) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");
		sb.append(" a.cate_name AS name1, b.cate_name AS name2, c.cate_name AS name3 ");
		sb.append(" FROM ");
		sb.append(" CURRI_PCATEGORY1 a, CURRI_PCATEGORY2 b, CURRI_CATEGORY c ");
		sb.append(" WHERE ");
		sb.append(" a.pare_code1 = ? AND b.pare_code2 = ? AND c.cate_code = ? ");
		sb.append(" AND a.system_code = b.system_code AND a.system_code = c.system_code AND a.system_code = ? ");

		sql.setSql(sb.toString());
		sql.setString(pare_code1);
		sql.setString(pare_code2);
		sql.setString(cate_code);
		sql.setString(SystemCode);

		//---- 디버그 출력
		log.debug("[curriWis]" + sql.toString());
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
	 * 과정기수 중복 체크
	 * @param systemCode
	 * @param cateCode
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @return
	 * @throws DAOException
	 */
	public int getCurriSubTermCount(String systemCode, String cateCode, String curriCode, int curriYear, int curriTerm) throws DAOException {
		int curriSubTermCount = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select ifnull(count(a.curri_term),0) AS cnt from curri_sub a , curri_top b");
		sb.append(" where a.system_code=b.system_code and a.curri_code=b.curri_code ");
		sb.append(" and a.system_code= ? and b.cate_code=? and a.curri_code=? and a.curri_year=? and a.curri_term=?");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(cateCode);
		sql.setString(curriCode);
		sql.setInteger(curriYear);
		sql.setInteger(curriTerm);

		log.debug("[getCurriSubTermCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 curriSubTermCount = rs.getInt("cnt");
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
		 return curriSubTermCount;
	}
	
	
	/**
	 * 학습자가 수강한 년도/학기를 가져온다..
	 * @param systemcode
	 * @param pUserId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriYearTerm(String systemcode, String pUserId) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 if(pUserId!=null && !("").equals(pUserId)){
			 sb.append(" select a.curri_year	");
			 sb.append(" , a.hakgi_term		");
			 sb.append(" from curri_sub a, student b	");
			 sb.append(" where a.system_code=b.system_code	");
			 sb.append(" and a.curri_code=b.curri_code	");
			 sb.append(" and a.curri_year=b.curri_year	");
			 sb.append(" and a.curri_term=b.curri_term	");
			 sb.append(" and b.system_code=?	");
			 sb.append(" and b.user_id=? 	");
			 sb.append(" and b.enroll_status in ('S','C','F')	"); // 수강, 수료, 미수료..
			 sb.append(" group by a.curri_year, a.hakgi_term	");
			 sb.append(" order by a.curri_year desc, a.hakgi_term desc	");
			 
		 }
		 else
		 {
			 sb.append(" select a.curri_year ");
			 sb.append(" , a.hakgi_term		");
			 sb.append(" from curri_sub a	");
			 sb.append(" where a.system_code=?	");
			 sb.append(" group by a.curri_year, a.hakgi_term	");
			 sb.append(" order by a.curri_year desc, a.hakgi_term desc	");
		 } 

		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 
		 if(pUserId!=null && !("").equals(pUserId)){
				sql.setString(pUserId);
		 }
		 
		 log.debug("[getCurriYearTerm]" + sql.toString());
		 
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
	 * 메인에서 보여지는 정규강의 강의목록
	 *
	 * @return
	 * @throws DAOException
	 */
	public RowSet currentCurriContentsList(String SystemCode, String pareCode1, String pareCode2, String CateCode, String Property1, String Property2, String chkAll, int limit) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		String addWhere = "";
		
		if (!pareCode1.equals("")) addWhere += " AND ct.pare_code1 = ? ";
		if (!pareCode2.equals("")) addWhere += " AND ct.pare_code2 = ?";
		if (!CateCode.equals("")) addWhere += " AND ct.cate_code = ? ";
		if (!Property1.equals("")) addWhere += " AND ct.curri_property1 = ? ";
		if (!Property2.equals("")) addWhere += " AND ct.curri_property2 = ? ";
		String currentDate = " CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ";
		
		sb.append(	" SELECT " +
						" cs.system_code, cs.curri_code, cs.curri_year, cs.curri_term" +
						", cs.hakgi_term, cs.curri_name, csc.course_id , cc.contents_id" +
						", cc.contents_name , cc.start_date, cc.end_date" +
						", (SELECT user_name FROM users WHERE system_code = cs.system_code AND user_id = cs.prof_id) AS prof_name ");
		sb.append(	" FROM curri_sub cs, curri_top ct, curri_sub_course csc, curri_contents cc  ");
		sb.append(	" WHERE " +
					" cs.system_code = ct.system_code and cs.curri_code = ct.curri_code " +
					" AND csc.system_code = cs.system_code AND csc.curri_code = cs.curri_code " +
					" AND csc.curri_year = cs.curri_year AND csc.curri_term = cs.curri_term " +
					" AND cc.system_code = csc.system_code AND cc.curri_code = csc.curri_code " +
					" AND cc.curri_year = csc.curri_year AND cc.curri_term = csc.curri_term " +
					" AND cc.course_id = csc.course_id AND cs.system_code = ? ");
		if (!addWhere.equals("")) sb.append(addWhere);
		sb.append(	" AND cc.contents_type = 'F' " +
				   // cc.start_date 이 널이면 무조건 보여주고, 날짜가 있으면 현재날짜와 컨텐츠 시작일을 보여줌....
					" AND if(cc.start_date='',1, (DATEDIFF(cc.start_date, "+ currentDate +") = 7 OR ("+ currentDate +" >= cc.start_date AND "+ currentDate +" <= cc.end_date)) )");
				
		if (!chkAll.equals("A")) {
			if (Property2.equals("R"))
				sb.append(" AND cs.enroll_start <= "+currentDate+" AND cs.service_end >= "+currentDate+" ");
		}
		
		sql.setString(SystemCode);
		if (!pareCode1.equals("")) sql.setString(pareCode1);
		if (!pareCode2.equals("")) sql.setString(pareCode2);
		if (!CateCode.equals("")) sql.setString(CateCode);
		if (!Property1.equals("")) sql.setString(Property1);
		if (!Property2.equals("")) sql.setString(Property2);

		sb.append(" ORDER BY cc.curri_year desc,  cc.curri_term desc, cc.contents_id desc  ");
		if (limit > 0) {
			sb.append(" limit ? ");
			sql.setInteger(limit);
		}
		sql.setSql(sb.toString());

		//---- 디버그 출력
		log.debug("[currentCurriContentsList]" + sql.toString());

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
	 * 정규강좌, 공개강좌
	 * 
	 * @return
	 * @throws DAOException
	 */
	public ListDTO currentCurriContentsPagingList(int curpage, int dispLine, int dispPage, String SystemCode, String pareCode1, String pareCode2) throws DAOException {
		ListDTO retVal = null;
		try{
			//---- List Sql 문생성
			String	addWhere	=	"";
			if (!pareCode1.equals("")) addWhere += " AND ct.pare_code1 = ? ";
			if (!pareCode2.equals("")) addWhere += " AND ct.pare_code2 = ?";
			String	currentDate	=	" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) ";
						
			ListStatement 	sql 		= 	new ListStatement();
			StringBuffer	sbWhere		=	new StringBuffer();

			sql.setTotalCol(" cc.contents_id ");
			sql.setCutCol(" cc.contents_id ");
			sql.setAlias(	" system_code, curri_code, curri_year, curri_term" +
							", hakgi_term, curri_name, course_id, contents_id" +
							", contents_name, start_date, end_date, prof_name ");
			
			sql.setSelect(	" cs.system_code, cs.curri_code, cs.curri_year, cs.curri_term" +
							", cs.hakgi_term, cs.curri_name, csc.course_id , cc.contents_id" +
							", cc.contents_name , cc.start_date, cc.end_date" +
							", (SELECT user_name FROM users WHERE system_code = cs.system_code AND user_id = cs.prof_id) AS prof_name ");
			
			sql.setFrom(	" curri_sub cs, curri_top ct, curri_sub_course csc, curri_contents cc ");
			
			
			sbWhere.append(	" cs.system_code = ct.system_code and cs.curri_code = ct.curri_code " +
							" AND csc.system_code = cs.system_code AND csc.curri_code = cs.curri_code " +
							" AND csc.curri_year = cs.curri_year AND csc.curri_term = cs.curri_term " +
							" AND cc.system_code = csc.system_code AND cc.curri_code = csc.curri_code " +
							" AND cc.curri_year = csc.curri_year AND cc.curri_term = csc.curri_term " +
							" AND cc.course_id = csc.course_id AND cs.system_code = ? ");
			if (!addWhere.equals("")) sbWhere.append(addWhere);
			sbWhere.append(	" AND cc.contents_type = 'F' " +
					        // cc.start_date 이 널이면 무조건 보여주고, 날짜가 있으면 현재날짜와 컨텐츠 시작일을 보여줌....
							" AND if(cc.start_date='',1, (DATEDIFF(cc.start_date, "+ currentDate +") = 7 OR ("+ currentDate +" >= cc.start_date AND "+ currentDate +" <= cc.end_date)) ) ");
						
			sql.setWhere(sbWhere.toString());			
			
			sql.setString(SystemCode);
			if (!pareCode1.equals("")) sql.setString(pareCode1);
			if (!pareCode2.equals("")) sql.setString(pareCode2);
						
			sql.setOrderby(" cc.curri_year desc,  cc.curri_term desc, cc.contents_id desc   ");

			//---- 디버그 출력
			log.debug("[currentCurriContentsPagingList]" + sql.toString());
			//System.out.println("[currentCurriContentsPagingList]" + sql.toString());
			
			retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
			
		} catch(SQLException e) {
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}
	
}