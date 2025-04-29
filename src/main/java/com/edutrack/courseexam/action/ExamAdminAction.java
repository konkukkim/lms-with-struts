/*
 * Created on 2004. 10. 8.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.DateSetter;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.dao.ExamAdminDAO;
import com.edutrack.courseexam.dto.ExamInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamAdminAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ExamAdminAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 시험 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		model.put("pCourseId",pCourseId);

		new CurriSiteNavigation(LECTURE).add(request,"시험리스트").link(model);
		return forward(request, model, "/courseexam/exam_mg_list.jsp");
	}

	/**
	 * [2007.09.27] AJAX 적용.
	 * @param pCourseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList examListAuto(String pCourseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String 		systemCode 	=	UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo 	= 	UserBroker.getCurriInfo(request);
		pCourseId	=	StringUtil.nvl(pCourseId);

		ArrayList		arrayList 	= 	new ArrayList();
		ExamAdminDAO	examDao 	= 	new ExamAdminDAO();
		ExamInfoDTO		examDto		=	null;

		RowSet	rs	=	examDao.getExamList(systemCode, curriInfo, pCourseId, 0);
		while(rs.next()) {
			examDto	=	new ExamInfoDTO();

			examDto.setSystemCode(systemCode);
			examDto.setCurriCode(curriInfo.curriCode);
			examDto.setCurriYear(StringUtil.nvl(curriInfo.curriYear, 0));
			examDto.setCurriTerm(StringUtil.nvl(curriInfo.curriTerm, 0));
			examDto.setCourseId(pCourseId);

			examDto.setExamId(rs.getInt("exam_id"));
			examDto.setSubject(StringUtil.nvl(rs.getString("subject")));
			examDto.setStartDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("start_date"))));
			examDto.setEndDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("end_date"))));
			examDto.setFlagTime(StringUtil.nvl(rs.getString("flag_time")));
			examDto.setRunTime(rs.getInt("run_time"));
			examDto.setFlagUse(StringUtil.nvl(rs.getString("flag_use")));

			arrayList.add(examDto);
		}
		rs.close();

		return arrayList;
	}

    /**
     * 시험 등록,수정 폼을 띄워준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward examWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		ExamInfoDTO examInfo = null;
		ExamHelper helper = new ExamHelper();
		DateSetter examTerm = null;
		DateSetter examResult = null;
		if(pMODE.equals("write")){
			String curDate = CommonUtil.getCurrentDate();
			examInfo = new ExamInfoDTO();
		   	examTerm = new DateSetter(curDate,curDate).link(model);
		   	examResult = new DateSetter(curDate).link(model);
		}else{
			ExamAdminDAO examDao = new ExamAdminDAO();
			CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
			examInfo = examDao.getExamInfo(systemCode,curriInfo ,pCourseId,pExamId);
			examTerm = new DateSetter(examInfo.getStartDate(),examInfo.getEndDate()).link(model);
			examResult = new DateSetter(examInfo.getResultDate()).link(model);
		}

		model.put("examInfo",examInfo);
		model.put("examTerm", examTerm);
		model.put("examResult",examResult);
		model.put("pMODE", pMODE);
        model.put("pCourseId", pCourseId);
		model.put("pExamId",""+pExamId);

        String navMsg = "시험등록";

        if(!pMODE.equals("write")) navMsg = "시험수정";

		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);

		return forward(request, model, "/courseexam/exam_mg_write.jsp");
	}

	/**
	 * 시험정보를 등록한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pCourseName = StringUtil.nvl(request.getParameter("pCourseName"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		ExamAdminDAO examDao = new ExamAdminDAO();
		ExamHelper helper = new ExamHelper();
		ExamInfoDTO examInfo = new ExamInfoDTO();
		helper.getExamParam(request, examInfo);
		if(pMODE.equals("write")){
	        examInfo.setRegId(userInfo.userId);
			examDao.addExamInfo(systemCode, userInfo.curriInfo, examInfo);
		}else{
			examInfo.setModId(userInfo.userId);
			examInfo.setExamId(pExamId);
			examDao.editExamInfo(systemCode, userInfo.curriInfo, examInfo);
		}

		new CurriSiteNavigation(LECTURE).add(request,"시험리스트").link(model);

		return redirect("/ExamAdmin.cmd?cmd=examList&pCourseId="+pCourseId+"&pCourseName="+pCourseName);
	}

	public ActionForward examStList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriInfo = userDto.curriInfo;
		String userId = userDto.userId;
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		ExamAdminDAO examDao = new ExamAdminDAO();
		RowSet examList = null;

		log.debug("PCOURSEID =>"+pCourseId);

		examList = examDao.getStExamList(systemCode, curriInfo,userId);

		model.put("examList", examList);
		model.put("pCourseId",pCourseId);

		new CurriSiteNavigation(LECTURE).add(request,"시험리스트").link(model);

		return forward(request, model, "/courseexam/exam_st_list.jsp");
	}

	public ActionForward examStShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		ExamInfoDTO examInfo = null;
		ExamHelper helper = new ExamHelper();
		ExamAdminDAO examDao = new ExamAdminDAO();
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		examInfo = examDao.getExamInfo(systemCode,curriInfo ,pCourseId,pExamId);

        model.put("pCourseId", pCourseId);
		model.put("pExamId",""+pExamId);
		model.put("examInfo",examInfo);

		return forward(request, model, "/courseexam/exam_st_info.jsp");
	}

	/**
	 * 시험 삭제
	 * @author 권영수
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public ActionForward examDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pExamId = StringUtil.nvl(request.getParameter("pExamId"));

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriterm = StringUtil.nvl(curriMemDto.curriTerm,0);

		int retVal = 0;
		int retVal2 = 0;
		int retVal3 =0;
		ExamAdminDAO examAdminDao = new ExamAdminDAO();

		retVal = examAdminDao.delExamAnswer(systemCode, pCourseId, pExamId, pCurriCode, pCurriYear, pCurriterm);
		retVal2 = examAdminDao.delExamContents(systemCode, pCurriCode, pCurriYear, pCurriterm, pCourseId, pExamId);
		retVal3 = examAdminDao.delExamInfo(systemCode, pCurriCode, pCurriYear, pCurriterm, pCourseId, pExamId);

		String msg = "";

		String returnUrl = "/ExamAdmin.cmd?cmd=examList&pCourseId="+pCourseId;
		if (retVal3 > 0 ) {
			msg = "시험을 삭제하였습니다.";
		} else {
			msg = "시험 정보 삭제에 실패하였습니다.";
		}
		return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

}
