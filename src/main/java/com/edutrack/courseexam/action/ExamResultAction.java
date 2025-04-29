/*
 * Created on 2004. 10. 20.
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
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.dao.ExamAdminDAO;
import com.edutrack.courseexam.dao.ExamAnswerDAO;
import com.edutrack.courseexam.dao.ExamContentsDAO;
import com.edutrack.courseexam.dao.ExamResultDAO;
import com.edutrack.courseexam.dto.ExamAnswerDTO;
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
public class ExamResultAction extends StrutsDispatchAction{
	 /**
	 *
	 */
	public ExamResultAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 시험평가 시험리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examResultList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		model.put("pCourseId",pCourseId);

		new CurriSiteNavigation(LECTURE).add(request,"시험결과리스트").link(model);
		return forward(request, model, "/courseexam/exam_result_list.jsp");
	}

	/**
	 * [AJAX] 시험평가 시험리스트
	 * @param pCourseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList examResultAutoList(String pCourseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

			arrayList.add(examDto);
		}
		return arrayList;
	}

	/**
	 * 시험결과 사용자 시험리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
        int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);

		ExamResultDAO resultDao = new ExamResultDAO();
		RowSet userList = null;

		log.debug("PCOURSEID =>"+pCourseId);

		userList = resultDao.getExamUserList(systemCode, curriInfo, pCourseId,pExamId);

		model.put("userList", userList);
		model.put("pCourseId",pCourseId);
		model.put("pExamId",""+pExamId);

		new CurriSiteNavigation(LECTURE).add(request,"시험결과").link(model);

		return forward(request, model, "/courseexam/exam_result_st_list.jsp");
	}

	/**
	 * 시험지 채점 화면
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examUserResult(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		CurriMemDTO curriInfo = userInfo.curriInfo;
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
        int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
        String pUserId = StringUtil.nvl(request.getParameter("pUserId"));
        String userType = userInfo.userType;

        ExamAdminDAO adminDao = new ExamAdminDAO();
        ExamInfoDTO examInfo = adminDao.getExamInfo(systemCode, curriInfo,pCourseId, pExamId);

        ExamAnswerDAO answerDao = new ExamAnswerDAO();
        ExamAnswerDTO answerInfo = answerDao.getExamAnswerInfo(systemCode, curriInfo,pCourseId, pExamId,pUserId);

		ExamContentsDAO contentsDao = new ExamContentsDAO();
		ArrayList contentsList = null;

		contentsList = contentsDao.getExamContentsList(systemCode, curriInfo, pCourseId,pExamId, answerInfo.getExamOrder());

		model.put("examInfo",examInfo);
		model.put("contentsList", contentsList);
		model.put("answerInfo", answerInfo);
		model.put("pCourseId",pCourseId);
		model.put("pExamId",""+pExamId);
		model.put("pUserId",pUserId);

		new CurriSiteNavigation(LECTURE).add(request,"시험채점").link(model);
		String returnUrl = "/courseexam/exam_result_st_score_list.jsp";

		if(userType.equals("S")) returnUrl = "/courseexam/exam_st_result.jsp";

		return forward(request, model, returnUrl);
	}

	/**
	 * 시험 채점 등록
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examResultRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
		String	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 	pExamId 	= 	StringUtil.nvl(request.getParameter("pExamId"),0);
		int 	maxOrder 	= 	StringUtil.nvl(request.getParameter("pMaxOrder"),0);
		String 	pUserId 	= 	StringUtil.nvl(request.getParameter("pUserId"));
		String 	modeId 		= 	UserBroker.getUserId(request);
		String 	pFeedback 	= 	StringUtil.nvl(request.getParameter("pFeedback"));

		CurriMemDTO		curriInfo	=	UserBroker.getCurriInfo(request);
		ExamAnswerDTO	answerDto 	= 	new ExamAnswerDTO();

		String 	score 		=	"";
		String 	answerScore =	"";
		int 	count 		= 	0;
		int 	totalScore 	=	0;
	//System.out.println("==============>> maxOrder : "+maxOrder);
		for(int i =1; i <= maxOrder; i++){
			score = StringUtil.nvl(request.getParameter("pScore"+i));
	//System.out.println("==============>> i : "+i+" || score : "+score);
			if(!score.equals("")){
				if(count > 0){
					answerScore += CommonUtil.DELI;
				}
			    answerScore += score;
				totalScore += Integer.parseInt(score);
				count++;
	//System.out.println("==============>> i : "+i+" || answerScore : "+answerScore);
			}
		}

		log.debug("answerScore =>"+answerScore);
		log.debug("totalScore =>"+totalScore);

		answerDto.setResultCheck("Y");
		answerDto.setTotalScore(totalScore);
		answerDto.setAnswerScore(answerScore);
		answerDto.setCourseId(pCourseId);
		answerDto.setExamId(pExamId);
		answerDto.setUserId(pUserId);
		answerDto.setModId(modeId);
		answerDto.setFeedBack(pFeedback);

		ExamResultDAO resultDao = new ExamResultDAO();

		int retVal = resultDao.editExamResult(systemCode, curriInfo,answerDto);

		String msg = "답안채점 정보를 입력하는데 실패하였습니다.";
		if(retVal > 0) msg = "답안채점 정보를 입력하는데  성공하였습니다.";

	    return alertAndExit(systemCode,model, msg, "/ExamResult.cmd?cmd=examUserList&pCourseId="+pCourseId+"&pExamId="+pExamId,LECTURE);
	}


}
