package com.edutrack.curritop.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.curritop.dao.CurriTopCourseDAO;
import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dto.CurriTopCourseDTO;
import com.edutrack.curritop.dto.CurriTopDTO;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;

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

public class CurriTopCourseAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CurriTopCourseAction() {
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
	 * 과목 연결 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriTopCourseList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO curriTopList = null;
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		String	curriProperty2 = StringUtil.nvl(request.getParameter("pCurriProperty2"));

		//---- 페이징 설정
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		CurriTopCourseDAO	curriTopCourseDao	=	new CurriTopCourseDAO();
		totCnt = curriTopCourseDao.getTotCount(systemCode, curriCode);
		
		CurriTopDAO			curriTopDao			=	new CurriTopDAO();
		CurriTopDTO			curriTopDto			=	curriTopDao.getCurriTopInfo(systemCode, curriCode);
		
		//---- 검색 설정
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"),"");
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));
		
		model.put("pCurriCode", curriCode);
		model.put("curriName", StringUtil.nvl(curriTopDto.getCurriName()));
		model.put("pCurriProperty2", StringUtil.nvl(curriTopDto.getCurriProperty2()));
		model.put("totCnt", String.valueOf(totCnt));
		model.put("curriTopCourseList", curriTopCourseDao.getCurriTopCourseList(curPage, systemCode, curriCode, pSTarget, pSWord));
		model.put("pSWord", pSWord);

		new SiteNavigation(MYPAGE).add(request,"과정관리").link(model);
		return forward(request, model, "/curri_top/curriTopCourseList.jsp");
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
	public ActionForward curriTopCourseWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = request.getParameter("pCurriCode");
		String courseId = request.getParameter("pCourseId");

		CourseDAO courseDao = new CourseDAO();
		RowSet Rs1 = courseDao.getCourseInfo(systemCode, courseId);
		Rs1.next();
		String courseName = StringUtil.nvl(Rs1.getString("course_name"));
		String profId = StringUtil.nvl(Rs1.getString("prof_id"));
		String courseType = StringUtil.nvl(Rs1.getString("contents_type"));
		String onlineYn = StringUtil.nvl(Rs1.getString("online_yn"));
		Rs1.close();
		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);

		String 	curriName 	= 	StringUtil.nvl(curriTopDto.getCurriName());
		String	curriType	=	StringUtil.nvl(curriTopDto.getCurriProperty2());

		model.put("pCurriCode", curriCode);
		model.put("pCourseId", courseId);
		model.put("pCurriType", curriType);
		model.put("courseName", courseName);
		model.put("profId",profId);
		model.put("courseType", courseType);
		model.put("onlineYn", onlineYn);
		model.put("curriName",curriName);

		return forward(request, model, "/curri_top/curriTopCourseWrite.jsp");
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
	public ActionForward curriTopCourseEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//---- 추가해야할 사항
		//---- config 설정에서 코드값 자동 등록인지 입력인지 확인해서 cateCode 넘겨 주는 부분 설정 해야함

		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = request.getParameter("pCurriCode");
		String courseId = request.getParameter("pCourseId");
		
		CurriTopCourseDAO curriTopCourseDao = new CurriTopCourseDAO();
		RowSet rs = curriTopCourseDao.getCurriTopCourseInfo(systemCode, curriCode, courseId);
		
		rs.next();
		model.put("pCurriCode", curriCode);
		model.put("pCourseId", courseId);
		model.put("pCurriType", StringUtil.nvl(rs.getString("curri_type")));
		model.put("pCourseType", StringUtil.nvl(rs.getString("course_type")));
		model.put("pOnlineYn", StringUtil.nvl(rs.getString("online_yn")));
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
		return forward(request, model, "/curri_top/curriTopCourseEdit.jsp");
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
	public ActionForward curriTopCourseRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String regMode = StringUtil.nvl(request.getParameter("pRegMode"),"Add");

		CurriTopCourseDAO curriTopCourseDao = new CurriTopCourseDAO();
		CurriTopCourseDTO curriTopCourseDto = new CurriTopCourseDTO();

		int retVal = 0;
		String pCurriCode = request.getParameter("pCurriCode");

		curriTopCourseDto.setSystemCode(systemCode);
		curriTopCourseDto.setCurriCode(pCurriCode);
		curriTopCourseDto.setCourseId(request.getParameter("pCourseId"));
		curriTopCourseDto.setProfId(StringUtil.nvl(request.getParameter("pProfId")));
		curriTopCourseDto.setCourseType(StringUtil.nvl(request.getParameter("pCourseType"),"R"));
		curriTopCourseDto.setOnlineYn(StringUtil.nvl(request.getParameter("pOnlineYn")));
		curriTopCourseDto.setExamBase(StringUtil.nvl(request.getParameter("pExamBase"), 0));
		curriTopCourseDto.setReportBase(StringUtil.nvl(request.getParameter("pReportBase"), 0));
		curriTopCourseDto.setAttendBase(StringUtil.nvl(request.getParameter("pAttendBase"), 0));
		curriTopCourseDto.setForumBase(StringUtil.nvl(request.getParameter("pForumBase"), 0));
		curriTopCourseDto.setEtc1Base(StringUtil.nvl(request.getParameter("pEtc1Base"), 0));
		curriTopCourseDto.setEtc2Base(StringUtil.nvl(request.getParameter("pEtc2Base"), 0));
		curriTopCourseDto.setRegId(regId);

		String msg = "";

		model.put("pCurriCode", pCurriCode);
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = curriTopCourseDao.addCurriTopCourseInfo(curriTopCourseDto);
			msg = "등록완료";
			if(retVal <= 0){
				msg = "등록오류 다시 진행해 주세요";
			}
		}else if(regMode.equals("Edit")){
			retVal = curriTopCourseDao.editCurriTopCourseInfo(curriTopCourseDto);
			msg = "수정완료";
			if(retVal <= 0){
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		return notifyCloseReload(systemCode, model, msg, "N","POPUP");
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
	public ActionForward curriTopCourseDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String courseId = request.getParameter("pCourseId");
		String curriCode = request.getParameter("pCurriCode");
		CurriTopCourseDAO curriTopCourseDao = new CurriTopCourseDAO();
		//---- 개설과정 정보 검색후 삭제 루틴 만들기....

		//---- 과정분류 디비 정보 삭제
		int retVal = 0;
		String msg = "";
		String returnUrl = "";
		retVal = curriTopCourseDao.delCurriTopCourseInfo(systemCode, curriCode, courseId);
		returnUrl = "/CurriTopCourse.cmd?cmd=curriTopCourseList&pCurriCode="+curriCode;
		if(retVal > 0) //---- 디비정보 삭제 성공했을 경우
		{
			msg = "과목연결을 삭제하였습니다.";
		}else{
			msg = "과목연결 삭제를 실패 하였습니다.";
		}
		new SiteNavigation(MYPAGE).add(request,"과정관리").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}
}