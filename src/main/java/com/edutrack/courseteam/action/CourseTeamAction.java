/*
 * Created on 2004. 10. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseteam.action;

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
import com.edutrack.courseteam.dao.CourseTeamDAO;
import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.courseteam.dto.CourseTeamMemberDTO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dto.CurriCourseListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseTeamAction extends StrutsDispatchAction{


	/**
	 * 과목 팀 리스트 페이지로 이동합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseTeamList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String systemCode  = 	UserBroker.getSystemCode(request);
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		//---- 과목의 정보를 가져옵니다.
		CurriSubCourseDAO curriSubCoruseDao = new CurriSubCourseDAO();
		ArrayList curriCourseList = new ArrayList();
		curriCourseList = curriSubCoruseDao.getCurriCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,"");
		CurriCourseListDTO list = null;
		if(pCourseId.equals("")) { //---- 과목 아이디가 안넘어왔을경우 처음 과목을 셋팅
			if(curriCourseList.size() > 0){
				list = (CurriCourseListDTO)curriCourseList.get(0);
				pCourseId = list.getCourseId();
			}
		}
		model.put("pCourseId", pCourseId);
		new SiteNavigation(LECTURE).add(request,"팀관리").link(model);
		return forward(request, model, "/courseteam/courseTeamList.jsp");
	}

	/**
	 * 과목 팀 리스트 (ajax)
	 * 2007.04.16 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList courseTeamListAuto(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseTeamDAO courseTeamDao	= new CourseTeamDAO();
		CourseTeamInfoDTO courseTeamInfoDto	= null;

		arrayList = courseTeamDao.getCourseTeamList(systemCode,curriCode, curriYear, curriTerm, courseId, null);

		return arrayList;
	}

	/**
	 * 팀구성원 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseTeamMemList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//-- 일반변수들 값을 받아옵니다
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int teamNo = Integer.parseInt(StringUtil.nvl(request.getParameter("pTeamNo"),"0"));

		model.put("pCourseId", pCourseId);
		model.put("pTeamNo", Integer.toString(teamNo));

		new SiteNavigation(LECTURE).add(request,"팀관리").link(model);
		return forward(request, model, "/courseteam/courseTeamMemberList.jsp");
	}

	/**
	 * 팀 대기자 명단을 보여줍니다(Ajax)
	 * 2007.05.22 sangsang
	 * @param courseId
	 * @param column
	 * @param orderBy
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList courseStudentList(String courseId, String column, String orderBy, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		column = StringUtil.nvl(column);
		orderBy = StringUtil.nvl(orderBy);

		if(column.equals("name"))
			column = "u.user_name";
		else if(column.equals("id"))
			column = "s.user_id";

		if(orderBy.equals("a"))
			orderBy = "asc";
		else if(orderBy.equals("d"))
			orderBy = "desc";

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseTeamDAO courseTeamDao	=	new CourseTeamDAO();
		UsersDTO usersDto = null;
		RowSet rs = courseTeamDao.getCurriStudentList(systemCode,curriCode, curriYear, curriTerm, courseId, column, orderBy);
		while(rs.next()){
			usersDto	= new UsersDTO();
			usersDto.setUserId(rs.getString("user_id"));
			usersDto.setUserName(StringUtil.nvl(rs.getString("user_name")));

			arrayList.add(usersDto);
		}
		rs.close();
		return arrayList;
	}

	/**
	 * 팀별 팀원명단을 보여줍니다(Ajax)
	 * 2007.05.22 sangsang
	 * @param courseId
	 * @param teamNoStr
	 * @param column
	 * @param orderBy
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList teamMemberList(String courseId, String teamNoStr, String column, String orderBy, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		int teamNo = StringUtil.nvl(teamNoStr,0);
		column = StringUtil.nvl(column);
		orderBy = StringUtil.nvl(orderBy);

		if(column.equals("name"))
			column = "u.user_name";
		else if(column.equals("id"))
			column = "m.user_id";

		if(orderBy.equals("a"))
			orderBy = "asc";
		else if(orderBy.equals("d"))
			orderBy = "desc";

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseTeamDAO courseTeamDao	=	new CourseTeamDAO();
		CourseTeamMemberDTO courseTeamMemberDto = null;

		RowSet rs = courseTeamDao.getCourseTeamMemberList(systemCode,curriCode, curriYear, curriTerm, courseId, teamNo, column, orderBy);
		while(rs.next()){
			courseTeamMemberDto	= new CourseTeamMemberDTO();
			courseTeamMemberDto.setUserId(rs.getString("user_id"));
			courseTeamMemberDto.setUserName(StringUtil.nvl(rs.getString("user_name")));

			arrayList.add(courseTeamMemberDto);
		}
		rs.close();
		return arrayList;
	}

	/**
	 * 팀 멤버 등룩/삭제를 합니다.(Ajax)
	 * 2007.05.22 sangsang
	 * @param courseId
	 * @param teamNoStr
	 * @param selectedStuId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean teamMemberRegist(String courseId, String teamNoStr, String[] selectedStuId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		courseId = StringUtil.nvl(courseId);
		int teamNo = StringUtil.nvl(teamNoStr,0);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		boolean retVal = false;

		// 기존 팀구성원 삭제
		CourseTeamDAO courseTeamDao	=	new CourseTeamDAO();
		int ret = courseTeamDao.deleteCourseTeamMember(systemCode,curriCode,curriYear,curriTerm,courseId,teamNo);

		if(selectedStuId != null && selectedStuId.length > 0){

			CourseTeamMemberDTO courseTeamMemberDto	=	new CourseTeamMemberDTO();
			courseTeamMemberDto.setSystemCode(systemCode);
			courseTeamMemberDto.setCurriCode(curriCode);
			courseTeamMemberDto.setCurriYear(curriYear);
			courseTeamMemberDto.setCurriTerm(curriTerm);
			courseTeamMemberDto.setCourseId(courseId);
			courseTeamMemberDto.setTeamNo(teamNo);
			courseTeamMemberDto.setRegId(userId);
			courseTeamMemberDto.setChkId(selectedStuId);

			retVal = courseTeamDao.addCourseTeamMember(courseTeamMemberDto);
		}

		if(retVal == false){
			if(ret > 0)
				retVal = true;
		}

	    return retVal;
	}

	/**
	 * 팀 등록/수정/삭제를 합니다(Ajax)
	 * 2007.05.22 sangsang
	 * @param regMode
	 * @param courseId
	 * @param teamNo
	 * @param teamName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String courseTeamRegist(String regMode, String courseId, int teamNo, String teamName, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		courseId = StringUtil.nvl(courseId);
		teamName = AjaxUtil.ajaxDecoding(teamName);

		CourseTeamDAO courseTeamDao	=	new CourseTeamDAO();
		CourseTeamInfoDTO courseTeamDto = new CourseTeamInfoDTO();

		courseTeamDto.setSystemCode(systemCode);
		courseTeamDto.setCurriCode(curriCode);
		courseTeamDto.setCurriYear(curriYear);
		courseTeamDto.setCurriTerm(curriTerm);
		courseTeamDto.setCourseId(courseId);
		courseTeamDto.setTeamName(teamName);
		courseTeamDto.setRegId(userId);
		courseTeamDto.setModId(userId);

	    int	retVal = 0;
		if(regMode.equals("ADD")){
			retVal = courseTeamDao.addCourseTeam(courseTeamDto);
		}else if(regMode.equals("EDIT")){
			courseTeamDto.setTeamNo(teamNo);
			retVal = courseTeamDao.editCourseTeam(courseTeamDto);
		}else if(regMode.equals("DEL")){
			courseTeamDto.setTeamNo(teamNo);
			retVal = courseTeamDao.delCourseTeam(courseTeamDto);
		}

		return String.valueOf(retVal);
	}
}
