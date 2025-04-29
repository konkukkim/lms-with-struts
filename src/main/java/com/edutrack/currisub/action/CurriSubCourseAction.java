package com.edutrack.currisub.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dto.CurriSubCourseDTO;

import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.curritop.dto.CurriTopDTO;

import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/*
 * @author Jamfam
 *
 * 과정 과목 연결 관리
 */

public class CurriSubCourseAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CurriSubCourseAction() {
		super();
		// TODO Auto-generated constructor stub
	}

    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 과정명을 가져옵니다.
	 * @param systemCode
	 * @param curriCode
	 * @return curriName
	 * @throws
	 */
	public String getCurriName(String systemCode, String curriCode) throws Exception{
		String curriName = "";
		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);
		curriName = StringUtil.nvl(curriTopDto.getCurriName());
		return curriName;
	}

	/**
	 * 개설과정과목정보 받아오기(Ajax)
	 * 2007.06.11 sangsang
	 * @param curriCode
	 * @param curriYear
	 * @param curriTeam
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public CurriSubCourseDTO curriSubCourseInfo(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		CurriSubCourseDTO curriSubCourseDto = new CurriSubCourseDTO();
		CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();

		RowSet rs = curriSubCourseDao.getCurriSubCourseInfo(systemCode, curriCode, curriYear, curriTerm, courseId);
		if(rs.next()){
			curriSubCourseDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			curriSubCourseDto.setCourseName(StringUtil.nvl(rs.getString("course_name")));
			curriSubCourseDto.setProfId(StringUtil.nvl(rs.getString("prof_id")));
			curriSubCourseDto.setExamBase(rs.getInt("exam_base"));
			curriSubCourseDto.setReportBase(rs.getInt("report_base"));
			curriSubCourseDto.setAttendBase(rs.getInt("attend_base"));
			curriSubCourseDto.setForumBase(rs.getInt("forum_base"));
			curriSubCourseDto.setEtc1Base(rs.getInt("etc1_base"));
			curriSubCourseDto.setEtc2Base(rs.getInt("etc2_base"));
		}
		rs.close();

		return curriSubCourseDto;
	}

	/**
	 * 과목 연결 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubCourseList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO curriTopList = null;
		String cateCode = request.getParameter("pCateCode");
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int curriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));

		//---- 페이징 설정
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		
		// add date : 2008.01.07
		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);
		String pCurriProperty2 = (String) curriTopDto.getCurriProperty2();
		
		model.put("pCurriProperty2", pCurriProperty2);

		CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();

		//---- 검색 설정
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"),"");
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));

		totCnt = curriSubCourseDao.getTotCount(systemCode, curriCode, curriYear, curriTerm);
		model.put("pCateCode", cateCode);
		model.put("pCurriCode", curriCode);
		model.put("pCurriYear", Integer.toString(curriYear));
		model.put("pCurriTerm", Integer.toString(curriTerm));
		model.put("curriName", getCurriName(systemCode, curriCode));
		model.put("totCnt", String.valueOf(totCnt));
		model.put("curriSubCourseList", curriSubCourseDao.getCurriSubCourseList(curPage, systemCode, curriCode, curriYear, curriTerm, pSTarget, pSWord, 0));
		model.put("pSWord", pSWord);
		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);
		return forward(request, model, "/curri_sub/curriSubCourseList.jsp");
	}

	/**
	 * 과목 연결 정보  생성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubCourseWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("  -------------- curriSubCourseWrite Start -------------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = request.getParameter("pCurriCode");
		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int curriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));
		String courseId = request.getParameter("pCourseId");
		//log.debug("  request.getParameter  End");
		CourseDAO courseDao = new CourseDAO();
		RowSet Rs1 = courseDao.getCourseInfo(systemCode, courseId);
		Rs1.next();
		String courseName = StringUtil.nvl(Rs1.getString("course_name"));
		String profId = StringUtil.nvl(Rs1.getString("prof_id"));
		String courseType = StringUtil.nvl(Rs1.getString("contents_type"));
		Rs1.close();
		//log.debug("  courseDao.getCourseInfo  End");
		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);

		String 	curriName 	= 	StringUtil.nvl(curriTopDto.getCurriName());
		String	curriType	=	StringUtil.nvl(curriTopDto.getCurriProperty2());
		//log.debug("  curriTopDao.getCurriTopInfo  End");

		model.put("pCurriCode", curriCode);
		model.put("pCurriYear", Integer.toString(curriYear));
		model.put("pCurriTerm", Integer.toString(curriTerm));
		model.put("pCourseId", courseId);
		model.put("pCurriType", curriType);
		model.put("courseName", courseName);
		model.put("profId",profId);
		model.put("courseType", courseType);
		model.put("curriName",curriName);
		DateSetter ds = new DateSetter("").link(model);
		model.put("ds", ds);

		log.debug("  -------------- curriSubCourseWrite End   -------------------------");
		return forward(request, model, "/curri_sub/curriSubCourseWrite.jsp");
	}

	/**
	 * 과목 정보 수정 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubCourseEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("  -------------- curriSubCourseEdit Start -------------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = request.getParameter("pCurriCode");

		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int curriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));
		String courseId = request.getParameter("pCourseId");
		CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();
		RowSet rs = curriSubCourseDao.getCurriSubCourseInfo(systemCode, curriCode, curriYear, curriTerm, courseId);
		rs.next();
		//-- 강의실에서도 평가비율 변경을 위해 사용하기 위해
		String pMode ="MyPage";
		if(request.getParameter("pMode") != null) pMode = request.getParameter("pMode");
		model.put("pMode", pMode);
		///---------------------------------------
		model.put("pCurriCode", curriCode);
		model.put("pCurriYear", Integer.toString(curriYear));
		model.put("pCurriTerm", Integer.toString(curriTerm));
		model.put("pCourseId", courseId);
		model.put("pCurriType", StringUtil.nvl(rs.getString("curri_type")));
		model.put("pCourseType", StringUtil.nvl(rs.getString("course_type"),"R"));
		model.put("pCourseName", StringUtil.nvl(rs.getString("course_name")));
		model.put("pCurriName", StringUtil.nvl(rs.getString("curri_name")));
		model.put("pProfId", StringUtil.nvl(rs.getString("prof_id")));
		model.put("pExamBase", Integer.toString(rs.getInt("exam_base")));
		model.put("pReportBase", Integer.toString(rs.getInt("report_base")));
		model.put("pAttendBase", Integer.toString(rs.getInt("attend_base")));
		model.put("pForumBase", Integer.toString(rs.getInt("forum_base")));
		model.put("pEtc1Base", Integer.toString(rs.getInt("etc1_base")));
		model.put("pEtc2Base", Integer.toString(rs.getInt("etc2_base")));
		rs.close();
		log.debug("  -------------- curriSubCourseEdit End -------------------------");
		return forward(request, model, "/curri_sub/curriSubCourseEdit.jsp");
	}

	/**
	 * 과목 연결 정보를 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubCourseRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("  -------------- curriSubCourseRegist Start -------------------------");
		String systemCode 	= UserBroker.getSystemCode(request);
		String regId 		= UserBroker.getUserId(request);
		String regMode 		= StringUtil.nvl(request.getParameter("pRegMode"),"Add");

		CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();
		CurriSubCourseDTO curriSubCourseDto = new CurriSubCourseDTO();

		int retVal = 0;
		String pCurriCode = request.getParameter("pCurriCode");
		int curriYear = StringUtil.nvl(request.getParameter("pCurriYear"), 2007);
		int curriTerm = StringUtil.nvl(request.getParameter("pCurriTerm"), 1);
		String pCourseId = StringUtil.nvl(request.getParameter("PCourseId"));

		curriSubCourseDto.setSystemCode(systemCode);
		curriSubCourseDto.setCurriCode(pCurriCode);
		curriSubCourseDto.setCurriYear(curriYear);
		curriSubCourseDto.setCurriTerm(curriTerm);
		curriSubCourseDto.setCourseId(request.getParameter("pCourseId"));
		curriSubCourseDto.setProfId(request.getParameter("pProfId"));
		curriSubCourseDto.setCourseType(StringUtil.nvl(request.getParameter("pCourseType"),"R"));
		curriSubCourseDto.setOnlineYn(StringUtil.nvl(request.getParameter("pOnlineYn")));
		curriSubCourseDto.setExamBase(StringUtil.nvl(request.getParameter("pExamBase"), 0));
		curriSubCourseDto.setReportBase(StringUtil.nvl(request.getParameter("pReportBase"), 0));
		curriSubCourseDto.setAttendBase(StringUtil.nvl(request.getParameter("pAttendBase"), 0));
		curriSubCourseDto.setForumBase(StringUtil.nvl(request.getParameter("pForumBase"), 0));
		curriSubCourseDto.setEtc1Base(StringUtil.nvl(request.getParameter("pEtc1Base"), 0));
		curriSubCourseDto.setEtc2Base(StringUtil.nvl(request.getParameter("pEtc2Base"), 0));
		curriSubCourseDto.setCompleteScore(StringUtil.nvl(request.getParameter("pCompleteScore"),"N"));
		curriSubCourseDto.setRegId(regId);

		String msg = "";

		model.put("pCurriCode", pCurriCode);
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = curriSubCourseDao.addCurriSubCourseInfo(curriSubCourseDto);
			msg = "등록완료";
			if(retVal <= 0){
				msg = "등록오류 다시 진행해 주세요";
			}
		}else if(regMode.equals("Edit")){
			retVal = curriSubCourseDao.editCurriSubCourseInfo(curriSubCourseDto);
			msg = "수정완료";
			if(retVal <= 0){
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		log.debug("  -------------- curriSubCourseRegist End -------------------------");
		//return closePopupReload(systemCode, model, msg, "N","O","");
		return notifyCloseReload(systemCode, model, msg, "");

	}

	/**
	 * 고목 정보를 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubCourseDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		String cateCode = request.getParameter("pCateCode");
		String curriCode = request.getParameter("pCurriCode");
		int curriYear = StringUtil.nvl(request.getParameter("pCurriYear"), 2007);
		int curriTerm = StringUtil.nvl(request.getParameter("pCurriTerm"), 1);
		String courseId = request.getParameter("pCourseId");
		CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();
		//---- 개설과정 정보 검색후 삭제 루틴 만들기....

		//---- 과정분류 디비 정보 삭제
		int retVal = 0;
		int retVal2 = 0;
		int retVal3 = 0;
		String msg = "";
		String returnUrl = "";

		// 개설 과정 - 과목 정보 삭제
		retVal = curriSubCourseDao.delCurriSubCourseInfo(systemCode, curriCode, curriYear, curriTerm, courseId);

		// contents 정보 삭제
		retVal2 = curriSubCourseDao.delCurriContents(systemCode, curriCode, curriYear, curriTerm, courseId);

		// curri_quiz 정보 삭제
		retVal3 = curriSubCourseDao.delCurriQuiz(systemCode, curriCode, curriYear, curriTerm, courseId);

		returnUrl = "/CurriSubCourse.cmd?cmd=curriSubCourseList&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm;

		if(retVal > 0) //---- 디비정보 삭제 성공했을 경우
		{
			msg = "과목연결을 삭제하였습니다.";
		}else{
			msg = "과목연결 삭제를 실패 하였습니다.";
		}
		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}
}