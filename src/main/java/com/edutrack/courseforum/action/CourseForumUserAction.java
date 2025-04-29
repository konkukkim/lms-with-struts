/*
 * Created on 2004. 10. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dao.CourseForumUserDAO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.courseforum.dto.CourseForumUserDTO;
import com.edutrack.courseteam.dao.CourseTeamDAO;
import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseForumUserAction  extends StrutsDispatchAction{

	/**
	 * 수강생의 팀을 지정합니다 (전체/팀)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumUserAllRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
//		-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String systemCode 	= 	UserBroker.getSystemCode(request);
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId		= 	StringUtil.nvl(request.getParameter("pForumId"), 0);

		String[] pSubForumId 	= 	request.getParameterValues("pSubForumId");//팀번호를 가져오지 말고 서브포럼번호를 가져아야 됨.
		String[] pUserId		= 	request.getParameterValues("pUserId");
		String pRegId			=	UserBroker.getUserId(request);
		int  retVal = 0;

		CourseForumUserDAO courseForumUserDao 	= 	new CourseForumUserDAO();
		CourseForumUserDTO courseForumUser	=	new CourseForumUserDTO();
			courseForumUser.setSystemCode(systemCode);
			courseForumUser.setCurriCode(pCurriCode);
			courseForumUser.setCurriYear(pCurriYear);
			courseForumUser.setCurriTerm(pCurriTerm);
			courseForumUser.setCourseId(pCourseId);
			courseForumUser.setForumId(pForumId);
			courseForumUser.setScore(0);
			courseForumUser.setConnectNo(0);
			courseForumUser.setRegId(pRegId);
			courseForumUser.setModId(pRegId);

		//구성원이 등록되어 있음 수정을 미등록시는 삽입을.
		for(int i=0; i < pUserId.length; i++){
			int cnt = 0;
			courseForumUser.setUserId(pUserId[i]);
			courseForumUser.setSubForumId(Integer.parseInt(pSubForumId[i]));
			cnt = courseForumUserDao.getUserIdRegistCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , pForumId , pUserId[i]);
			if(cnt == 0){//삽입
				retVal = courseForumUserDao.addCourseForumUser(courseForumUser);
			}else{//수정
				retVal = courseForumUserDao.editCourseForumUser(courseForumUser);
			}
		}
		String returnurl = "/CourseForumUser.cmd?cmd=courseForumUserAllWrite&pCourseId="+pCourseId+"&pForumId="+pForumId;
		String msg = "ERROR!!팀원을 등록하는데 에러가 발생하였습니다.";

		if(retVal > 0){
			msg = "팀원을 성공적으로 등록하였습니다.";
		}

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return notifyAndExit(systemCode, model, msg, returnurl, LECTURE);
	}

	/**
	 * 토론의 팀 구성원을 자동으로 등록합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumUserAutoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String systemCode 	= 	UserBroker.getSystemCode(request);
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId		= 	StringUtil.nvl(request.getParameter("pForumId"), 0);
		int pParentForumId		= 	StringUtil.nvl(request.getParameter("pParentForumId"), 0);

		String[] pUserId		= 	request.getParameterValues("pUserId");
		String pRegId			=	UserBroker.getUserId(request);
		int	retVal		= 0;

		String returnurl = "/CourseForumUser.cmd?cmd=courseForumUserAutoWrite&pCourseId="+pCourseId+"&pForumId="+pForumId;
		String msg = "ERROR!!팀원을  등록하는데 에러가 발생하였습니다.";

		CourseForumUserDAO courseForumUserDao 	= 	new CourseForumUserDAO();
		CourseForumUserDTO courseForumUser	=	new CourseForumUserDTO();
			courseForumUser.setSystemCode(systemCode);
			courseForumUser.setCurriCode(pCurriCode);
			courseForumUser.setCurriYear(pCurriYear);
			courseForumUser.setCurriTerm(pCurriTerm);
			courseForumUser.setCourseId(pCourseId);
			courseForumUser.setForumId(pParentForumId); //user의 forumId엔 parentforumid가 들어가함.
			courseForumUser.setSubForumId(pForumId); //subforumid엔 forumId가 들어감( 자동사용자등록은 분임조의 경우임)
			courseForumUser.setScore(0);
			courseForumUser.setConnectNo(0);
			courseForumUser.setRegId(pRegId);
			courseForumUser.setModId(pRegId);

		//구성원이 등록되어 있음 수정을 미등록시는 삽입을.
		for(int i=0; i < pUserId.length; i++){
			int cnt = 0;
			courseForumUser.setUserId(pUserId[i]);
			cnt = courseForumUserDao.getUserIdRegistCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , pParentForumId , pUserId[i]);
			if(cnt == 0){//삽입
				retVal = courseForumUserDao.addCourseForumUser(courseForumUser);
			}else{//수정
				retVal = courseForumUserDao.editCourseForumUser(courseForumUser);
			}
		}

		if( retVal > 0 ){
			msg = "팀원을 성공적으로 등록하였습니다.";
		}

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return notifyAndExit(systemCode, model, msg, returnurl, LECTURE);
	}


	/**
	 * 토론의 팀 구성원을 점수를 등록합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumUserScoreRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String systemCode 	= 	UserBroker.getSystemCode(request);

		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId		= 	StringUtil.nvl(request.getParameter("pForumId"), 0);
		//int pParentForumId		= 	StringUtil.nvl(request.getParameter("pParentForumId"), 0);
		String pOneUserId	= 	StringUtil.nvl(request.getParameter("pOneUserId"));
		String pGubun	= 	StringUtil.nvl(request.getParameter("pGubun"));

		//if(pParentForumId == 0)
		//	pParentForumId = pForumId;

		String[] pUserId		= 	request.getParameterValues("pUserId");
		String[] pScore			= 	request.getParameterValues("pScore");
		boolean  retVal = false;

		String returnurl = ""; //"/CourseForumInfo.cmd?cmd=courseForumScoreWrite&pCourseId="+pCourseId+"&pForumId="+pForumId;
		String msg = "ERROR!!점수를  등록하는데 에러가 발생하였습니다.";

		CourseForumUserDAO courseForumUserDao 	= 	new CourseForumUserDAO();
		int cnt 	= 0;
		String[] score	= new String[1];
		String[] userId	= new String[1];

		// 개별적용..
		for(int i=0; i < pUserId.length; i++){
			userId[0]	= pUserId[i];
			score[0]	= pScore[i];

			if(pUserId[i].equals(pOneUserId)){
				retVal = courseForumUserDao.editCourseForumUserScore(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , pForumId, score, userId);
			}
		}

		// 일괄적용
		if(("ALL").equals(pGubun)){
			retVal = courseForumUserDao.editCourseForumUserScore(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , pForumId, pScore, pUserId);
		}

		if( retVal  ){
			msg = "점수를 성공적으로 등록하였습니다.";
		}

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return alertAndExit(systemCode, model, msg, returnurl, LECTURE);
	}

	/**
	 * 토론방정보 정보 리스트를 가져온다.(결과)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumResultUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId	= userMemDto.userId;

	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		CourseForumUserDAO courseForumUserDao 	= new CourseForumUserDAO();
		CourseForumUserDTO courseForumUserList 	= new CourseForumUserDTO();

		CourseForumInfoDAO courseForumInfoDao 	= new CourseForumInfoDAO();
		model.put("forumInfoCnt", String.valueOf(courseForumInfoDao.getCourseForumInfoCount(systemCode, pCurriCode,  pCurriYear, pCurriTerm, pCourseId , " and parent_forum_id ='0' ")));
		model.put("CourseForumUserList", courseForumUserDao.getCourseForumResultUserList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId));
		model.put("totCnt", String.valueOf(courseForumUserDao.getCourseForumUserDistinctCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , "" )));
		model.put("pCourseId", pCourseId);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumResultUserList.jsp");
	}

	/**
	 * 토론방정보 정보 리스트를 가져온다.(결과)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumResultUserPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId	= userMemDto.userId;

	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int curPage = 1;
		curPage = Integer.parseInt(StringUtil.nvl(request.getParameter("curPage"),"1"));
		CourseForumUserDAO courseForumUserDao = new CourseForumUserDAO();
		CourseForumInfoDAO courseForumInfoDao 	= new CourseForumInfoDAO();

		model.put("forumInfoCnt", String.valueOf(courseForumInfoDao.getCourseForumInfoCount(systemCode, pCurriCode,  pCurriYear, pCurriTerm, pCourseId , " and parent_forum_id ='0' ")));
		model.put("CourseForumUserList", courseForumUserDao.getCourseForumResultUserPagingList(curPage, systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId));
		model.put("totCnt", String.valueOf(courseForumUserDao.getCourseForumUserDistinctCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , "" )));

		model.put("pCourseId", pCourseId);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumResultUserPagingList.jsp");
	}
}
