/*
 * Created on 2004. 9. 17.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.main.action;

import java.util.ArrayList;
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
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.dao.ExamInfoDAO;
import com.edutrack.courseforum.dao.CourseForumContentsDAO;
import com.edutrack.coursereport.dao.ReportAdminDAO;
import com.edutrack.curribbs.dao.CurriBbsContentsDAO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dto.CurriCourseListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.main.dao.LectureMainDAO;
import com.edutrack.resultcourse.dao.ResultCourseDAO;

/**
 * @author bschoi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LectureMainAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public LectureMainAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 강의실 입장시 과정, 과목 정보 셋팅 처리를 해준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward goLecture(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String systemCode = UserBroker.getSystemCode(request);
        UserMemDTO userInfo = UserBroker.getUserInfo(request);
        String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
        //--	파라미터를 한글변환 해줌.
        String pCurriName = StringUtil.nvl(request.getParameter("pCurriName"));

        String pCurriYear = StringUtil.nvl(request.getParameter("pCurriYear"));
        String pCurriTerm = StringUtil.nvl(request.getParameter("pCurriTerm"));
        String pCurriType = StringUtil.nvl(request.getParameter("pCurriType"));

        int stCheck = 0;
        StudentDAO	studentDao = new StudentDAO();
        String returnUrl = "/Main.cmd?cmd=mainShow&pMode=Home";
		String msg = "강의실에 입장할 수 없습니다. <br> 초기페이지로 돌아갑니다.";

        if(userInfo.userType.equals("S")){
        	//-- 접근권한 체크(수강생인지 확인)
        	stCheck = studentDao.isStudentChung(systemCode,userInfo.userId,pCurriCode, Integer.parseInt(pCurriYear), Integer.parseInt(pCurriTerm), pCurriType);
        	log.debug("_______________ stCheck = " + stCheck);
        	if(stCheck <= 0 ){
                return alertAndExit(systemCode, model, msg, returnUrl, HOME);
        	}
        }

        CurriMemDTO curriInfo =  new CurriMemDTO();
		curriInfo.curriCode = pCurriCode;
		curriInfo.curriName = pCurriName;
		curriInfo.curriYear = pCurriYear;
		curriInfo.curriTerm = pCurriTerm;
		curriInfo.curriType = pCurriType;

		UserBroker.setCurriInfo(request,curriInfo);

		log.debug("과정 정보 셋팅 완료");

		//과목, 과정별 접속 통계 insert, update
		LectureMainDAO LectureMainDao = new LectureMainDAO();
		String courseId = "";
		int sucessYn1 = 0;

		//과정에 속한 과목의 리스트를 가져온다.
		CurriSubCourseDAO curriSubCoruseDao = new CurriSubCourseDAO();
		ArrayList COURSELIST = new ArrayList();
		CurriCourseListDTO CourseListDto = null;
		COURSELIST = curriSubCoruseDao.getCurriCourseList(systemCode,pCurriCode,Integer.parseInt(pCurriYear),Integer.parseInt(pCurriTerm),"");
		//과목 갯수는 COURSELIST.size()

		for(int i=0; i < COURSELIST.size(); i++){
			CourseListDto = (CurriCourseListDTO)COURSELIST.get(i);
			courseId = CourseListDto.getCourseId();
			sucessYn1 = LectureMainDao.getCourseConnectCnt(systemCode, pCurriCode, pCurriYear, pCurriTerm, courseId, userInfo.userId, request.getRemoteAddr());
		}

		if(userInfo.userType.equals("S")) {
			sucessYn1 = LectureMainDao.getCurriConnectCnt(systemCode, pCurriCode, pCurriYear, pCurriTerm, userInfo.userId, request.getRemoteAddr());
		}

		//-- 상시과정일 때에는 모두 강의목차 페이지로 보낸다.
		String goLectureUrl = "";
		if(pCurriType.equals("O")) {
			goLectureUrl = "/CurriContents.cmd?cmd=lecContentsList&pCourseId=&pCourseName=&MENUNO=0&pMode="+LECTURE;
		} else {
			goLectureUrl = "/LectureMain.cmd?cmd=LectureMainShow&MENUNO=0&pMode="+LECTURE;
		}


		//--강의실 메인 화면으로
		//return redirect("/LectureMain.cmd?cmd=LectureMainShow&pMode="+LECTURE);
		//String serverUrl = CommonUtil.SERVERURL;
//		String serverUrl = "http://"+request.getServerName();
//		log.debug("serverUtl = "+serverUrl);
//		String drmServer = CommonUtil.DigCapDRMServerDoamin;
//		return redirect(drmServer+"/setDRMCookies.asp?UserID="+userInfo.userId+"&cmd=LectureMainShow&pMode="+LECTURE+"&retURL="+serverUrl+"/LectureMain.cmd");

		//--강의실 메인 화면으로
		//return redirect("/LectureMain.cmd?cmd=LectureMainShow&pMode="+LECTURE);
		return redirect(goLectureUrl);

	}

	/**
	 * 사용자 타입별 강의실 메인 페이지를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward LectureMainShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("_______________ LectureMainShow start");
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		String userId = userMemDto.userId;
		if(userId.equals("") || userId.equals("@")) return alertAndExit(userMemDto.systemCode, model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home",HOME);

		String systemCode = userMemDto.systemCode;
		String userType = userMemDto.userType;
//		String classUserType = userType;//-- 강의실 안에서의 권한을 위해

		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		String pCurriName = curriMemDto.curriName;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCurriType = StringUtil.nvl(curriMemDto.curriType,"R");

		CurriBbsContentsDAO curribbsContentsDao = new CurriBbsContentsDAO();

		String mappingUrl = "main_lectureP.jsp";
		if(userType.equals("S") && pCurriType.equals("R")){
			CurriContentsDAO contentsDao = new CurriContentsDAO();
			CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
			//ReportSendDAO reportsendDao = new ReportSendDAO();
			ExamInfoDAO examinfoDao = new ExamInfoDAO();
			ReportAdminDAO	reportAdminDao	=	new ReportAdminDAO();
			ResultCourseDAO resultcourseDao = new ResultCourseDAO();

			model.put("noticeList", curribbsContentsDao.getCurriBbsContentsPagingList(1,5,10,systemCode,pCurriCode,pCurriYear,pCurriTerm,"notice","","","",""));

			//과정공지사항
			RowSet contentsRs = curribbsContentsDao.getCurriBbsContentsList(systemCode,pCurriCode,pCurriYear,pCurriTerm, "notice", "", "",5);
			model.put("contentsRs", contentsRs);

			//출석
			model.put("attendView", contentsDao.getMainAttendShow(systemCode,pCurriCode,pCurriYear,pCurriTerm,"",userId));

			//토론방정보
			model.put("forumList", courseForumContentsDao.getMainForumList(systemCode,pCurriCode,pCurriYear,pCurriTerm, userId));

			//과제정보
			//model.put("reportList", reportsendDao.getMainReportList(systemCode,pCurriCode,pCurriYear,pCurriTerm, userId, now_date));

			String now_date = CommonUtil.getCurrentDate();

			//과제정보
			model.put("reportList", reportAdminDao.getMainReportList(systemCode,pCurriCode,pCurriYear,pCurriTerm, userId, now_date));

			//시험정보
			model.put("examList", examinfoDao.getMainExamList(systemCode,pCurriCode,pCurriYear,pCurriTerm, userId, now_date));

			//성적현황
			model.put("courseresult", resultcourseDao.getMainCourseResultView(systemCode,pCurriCode,pCurriYear,pCurriTerm, userId));

			mappingUrl = "main_lectureS.jsp";//-- 학생 정규 과정 view jsp

		}else{	//-- 학생타입이면서 공개과정이거나 학생타입이 아닌 경우    모두 교수자 권한을 가짐
//			classUserType = "P";//-- 교수자 권한으로 셋팅
			CurriSubCourseDAO 	curriSubCoruseDao 	= 	new CurriSubCourseDAO();
			ArrayList curriCourseList = new ArrayList();
			curriCourseList = curriSubCoruseDao.getCurriCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,"");
			model.put("curriList",curriCourseList);
			model.put("noticeList", curribbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, "notice", "", "",5));
			model.put("pdsList", curribbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, "pds", "", "",5));
			model.put("qnaList", curribbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, "qna", "", "",5));

		}
		model.put("bbsList", curribbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, "bbs", "", "",5));


		new SiteNavigation(LECTURE).link(model);
		log.debug("_______________ LectureMainShow end");
		return forward(request, model, "/main/"+mappingUrl);
    }
}
