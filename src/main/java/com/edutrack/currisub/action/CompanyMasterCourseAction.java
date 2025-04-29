/**
 *
 */
package com.edutrack.currisub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.currisub.dao.CompanyMasterCourseDAO;
import com.edutrack.currisub.dto.CurriSubDTO;
import com.edutrack.dept.dao.DeptDaeDAO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dao.UserAdminDAO;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author famlilia
 *
 */
public class CompanyMasterCourseAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CompanyMasterCourseAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 위탁과정관리자의 과정별 현황 목록
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward companyMasterCurriList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);
		String	curriYear = CommonUtil.getCurrentDate().substring(0, 4);

		model.put("pMode", pMode);
		model.put("pCurriYear", curriYear);

		new SiteNavigation(MYPAGE).add(request,"과정별 현황").link(model);
		return forward(request, model, "/companymaster/company_master_curri_list.jsp");
	}

	/**
	 * 위탁과정관리자의 과정별 현황 목록 AJAX화
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList autoCompanyMasterCurriList(int pCurriYear, int pCurriTerm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		CompanyMasterCourseDAO cmCourseDao	=	new CompanyMasterCourseDAO();
		CurriSubDTO	curriSubDto	=	null;
		RowSet		curriList	=	null;
		ArrayList	arrList		=	null;

		try {
			curriList	=	cmCourseDao.getCompanyMasterCurriList(systemCode, userId, pCurriYear, pCurriTerm);
			arrList		=	new ArrayList();
			long	serviceStart	=	0;
			long	serviceEnd		=	0;
			long	curDate			=	0;

			while(curriList.next()) {
				curriSubDto	=	new CurriSubDTO();

				curriSubDto.setSystemCode(StringUtil.nvl(curriList.getString("system_code")));
				curriSubDto.setCurriCode(StringUtil.nvl(curriList.getString("curri_code")));
				curriSubDto.setCurriYear(curriList.getInt("curri_year"));
				curriSubDto.setCurriTerm(curriList.getInt("curri_term"));
				curriSubDto.setCurriName(StringUtil.nvl(curriList.getString("curri_name")));
				curriSubDto.setPareCode1(StringUtil.nvl(curriList.getString("pare_code1")));
				curriSubDto.setPareCode2(StringUtil.nvl(curriList.getString("pare_code2")));
				curriSubDto.setCateCode(StringUtil.nvl(curriList.getString("cate_code")));
				curriSubDto.setCurriType(StringUtil.nvl(curriList.getString("curri_type")));
				curriSubDto.setCateName(StringUtil.nvl(curriList.getString("cate_name")));
				curriSubDto.setProfId(StringUtil.nvl(curriList.getString("prof_id")));
				curriSubDto.setProfName(StringUtil.nvl(curriList.getString("prof_name")));
				curriSubDto.setEnrollStart(StringUtil.nvl(curriList.getString("enroll_start")));
				curriSubDto.setEnrollEnd(StringUtil.nvl(curriList.getString("enroll_end")));
				curriSubDto.setServiceStart(StringUtil.nvl(curriList.getString("service_start")));
				curriSubDto.setServiceEnd(StringUtil.nvl(curriList.getString("service_end")));
				curriSubDto.setCredit(curriList.getInt("credit"));
				curriSubDto.setHakgiTerm(curriList.getInt("hakgi_term"));
				curriSubDto.setProcessStudentCnt(curriList.getInt("process_stu_cnt"));
				curriSubDto.setAllStudentCnt(curriList.getInt("all_stu_cnt"));
				curriSubDto.setNoPassStudentCnt(curriList.getInt("nopass_stu_cnt"));
				curriSubDto.setPassStudentCnt(curriList.getInt("pass_stu_cnt"));
				curriSubDto.setUserId(StringUtil.nvl(curriList.getString("user_id")));
				curriSubDto.setUserName(StringUtil.nvl(curriList.getString("user_name")));

				serviceStart	=	StringUtil.nvl(StringUtil.nvl(curriList.getString("service_start")).substring(0, 8), 0);
				serviceEnd		=	StringUtil.nvl(StringUtil.nvl(curriList.getString("service_end")).substring(0, 8), 0);
				curDate			=	StringUtil.nvl(StringUtil.nvl(CommonUtil.getCurrentDate()).substring(0, 8), 0);

				if(serviceStart <= curDate &&  curDate <= serviceEnd)	curriSubDto.setConnPoint("ING");
				else if(curDate > serviceEnd)	curriSubDto.setConnPoint("END");
				else if(serviceStart > curDate)	curriSubDto.setConnPoint("BEFORE");
				else	curriSubDto.setConnPoint("");

				arrList.add(curriSubDto);
			}
			curriList.close();
//System.out.println("===================== arrList"+arrList.size());
		} catch(Exception e) {
			log.debug(e);
		} finally {
			if(curriList!=null) curriList.close();
		}
		return arrList;
	}

	/**
	 * 과정별현황 - 셀렉트 바에 들어갈 과정 리스트
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public Map getCompanySelectCourseList(int curriYear, int curriTerm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		CompanyMasterCourseDAO cmCourseDao	=	new CompanyMasterCourseDAO();
		CurriSubDTO	curriSubDto	=	null;
		RowSet		curriList	=	null;
		Map			map			=	new HashMap();

		try {
			curriList	=	cmCourseDao.getCompanySelectCourseList(systemCode, userId, curriYear, curriTerm);

			while(curriList.next()) {
				map.put(StringUtil.nvl(curriList.getString("curri_code")), StringUtil.nvl(curriList.getString("curri_name")));
			}
			curriList.close();

		} catch(Exception e) {
			log.debug(e);
		} finally {
			if(curriList!=null) curriList.close();
		}
		return map;
	}

	/**
	 * 수강생 현황
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCompanyStudentCurriInfo(int curriYear, int curriTerm, String curriCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		CompanyMasterCourseDAO cmCourseDao	=	new CompanyMasterCourseDAO();
		StudentDTO	stuDto	=	null;
		RowSet		rs		=	null;
		ArrayList	arrList	=	null;
//System.out.println("================= check01 ");
		try {
			rs		=	cmCourseDao.getStudentCurriInfo(systemCode, curriCode, curriYear, curriTerm);
			arrList	=	new ArrayList();

			while(rs.next()) {
				stuDto	=	new StudentDTO();

				stuDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				stuDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				stuDto.setGrade(StringUtil.nvl(rs.getString("grade")));
				stuDto.setGetCredit(StringUtil.nvl(rs.getString("get_credit")));
				stuDto.setCredit(rs.getInt("credit"));
				stuDto.setScoreGubun(StringUtil.nvl(rs.getString("score_gubun")));
				stuDto.setEnrollStats(StringUtil.nvl(rs.getString("enroll_status")));
				stuDto.setCompleteDate(StringUtil.nvl(rs.getString("complete_date")));
				stuDto.setScoreExam(rs.getDouble("score_exam"));
				stuDto.setScoreReport(rs.getDouble("score_report"));
				stuDto.setScoreAttend(rs.getDouble("score_attend"));
				stuDto.setScoreForum(rs.getDouble("score_forum"));
				stuDto.setScoreEtc1(rs.getDouble("score_etc1"));
				stuDto.setScoreEtc2(rs.getDouble("score_etc2"));
				stuDto.setTotalScore(rs.getDouble("score_total"));


				arrList.add(stuDto);

			}
			rs.close();
		} catch(Exception e) {
			log.debug(e);
		} finally {
			if(rs!=null) rs.close();
		}
		return arrList;
	}

	/**
	 * 소속 회원 목록을 수강 관련 정보와 함께 불러온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward deptUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String deptDaeCode = UserBroker.getDeptDaeCode(request);
		String deptSoCode  = UserBroker.getDeptSoCode(request);

 		UserAdminDAO userAdminDao = new UserAdminDAO();
 		int memberCnt = userAdminDao.getUserCount(systemCode,"S",deptDaeCode,deptSoCode);
 		model.put("memberCnt", String.valueOf(memberCnt));

		new SiteNavigation(MYPAGE).add(request,"소속회원현황").link(model);
		return forward(request, model, "/companymaster/deptUserList.jsp");
	}

	/**
	 * 위탁과정의 회원현황
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList autoDeptUserList(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
//System.out.println("========================>> check01 ");
		String systemCode = UserBroker.getSystemCode(request);
		String deptDaeCode = UserBroker.getDeptDaeCode(request);
		String deptSoCode  = UserBroker.getDeptSoCode(request);

		CompanyMasterCourseDAO cmCourseDao	=	new CompanyMasterCourseDAO();
		RowSet		rs			=	null;
		ArrayList	arrList		=	null;
		UsersDTO	usersDto	=	null;

		try {
			rs		=	cmCourseDao.getMemberList(systemCode, deptDaeCode, deptSoCode);
			arrList	=	new ArrayList();

			while(rs.next()) {
				usersDto	=	new UsersDTO();

				usersDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				usersDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				usersDto.setDeptDaecode(StringUtil.nvl(rs.getString("dept_daecode")));
				usersDto.setDeptSocode(StringUtil.nvl(rs.getString("dept_socode")));
				usersDto.setCurriCount(rs.getInt("curri_count"));
				usersDto.setCompleteCount(rs.getInt("complete_cnt"));
				usersDto.setConnectCount(rs.getInt("con_cnt"));
				usersDto.setRegDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("reg_date"))));

				arrList.add(usersDto);
			}
			rs.close();
		} catch(Exception e) {
			log.debug(e);
		} finally {
			if(rs!=null) rs.close();
		}
		return arrList;
	}


	/**
	 * 위탁과정 수강생 별 수강현황을 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward deptStuCurriInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String deptDaeCode = UserBroker.getDeptDaeCode(request);
		String deptSoCode  = UserBroker.getDeptSoCode(request);

		new SiteNavigation(MYPAGE).add(request,"회원별 수강현황").link(model);
		return forward(request, model, "/companymaster/deptStuCurriInfoList.jsp");
	}

	/**
	 * 각 회원 수강내역
	 * @param userId
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList autoDeptStuCurriInfoList(String userId, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
//		System.out.println("========================>> check01 ");
		String systemCode = UserBroker.getSystemCode(request);

		CompanyMasterCourseDAO cmCourseDao	=	new CompanyMasterCourseDAO();
		RowSet		rs			=	null;
		ArrayList	arrList		=	null;
		StudentDTO	stuDto		=	null;

		try {
			rs		=	cmCourseDao.getStudentCurriInfoList(systemCode, userId);
			arrList	=	new ArrayList();

			while(rs.next()) {
				stuDto	=	new StudentDTO();

				stuDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				stuDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				stuDto.setServicestartDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("servicestart_date"))));
				stuDto.setServiceendDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("serviceend_date"))));
				stuDto.setGrade(StringUtil.nvl(rs.getString("grade")));
				stuDto.setGetCredit(StringUtil.nvl(rs.getString("get_credit")));
				stuDto.setEnrollStats(StringUtil.nvl(rs.getString("enroll_status")));
				stuDto.setCompleteDate(StringUtil.nvl(rs.getString("complete_date")));
				stuDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				stuDto.setScoreGubun(StringUtil.nvl(rs.getString("score_gubun")));
				stuDto.setTotalScore(rs.getDouble("score_total"));

				arrList.add(stuDto);

			}
			rs.close();
		} catch(Exception e) {
			log.debug(e);
		} finally {
			if(rs!=null) rs.close();
		}
		return arrList;
	}

}
