/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.eventquiz.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.eventquiz.dao.EventQuizDAO;
import com.edutrack.eventquiz.dto.EventQuizAnswerDTO;
import com.edutrack.eventquiz.dto.EventQuizInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EventQuizAction extends StrutsDispatchAction{

	/**
	 * 이벤트 퀴즈 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		 String systemCode = UserBroker.getSystemCode(request);

		 EventQuizDAO eventquizDao = new EventQuizDAO();

		 model.put("quizinfoList", eventquizDao.getEventQuizList(systemCode));

		 new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);

         return forward(request, model, "/event_quiz/eventQuizList.jsp");
	}

	/**
	 * 이벤트 퀴즈 생성/수정 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String pRegMode		= StringUtil.nvl(request.getParameter("pRegMode"));

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);
		model.put("pRegMode", pRegMode);


		if(pRegMode.equals("E")){
			String systemCode 	= UserBroker.getSystemCode(request);
			String pQuizId 		= StringUtil.nvl(request.getParameter("pQuizId"));

			EventQuizDAO	eventquizDao	= new EventQuizDAO();
			EventQuizInfoDTO	quizinfoDto	= new EventQuizInfoDTO();

			quizinfoDto = eventquizDao.getEventQuizShow(systemCode, Integer.parseInt(pQuizId));

			model.put("pQuizId", pQuizId);
			model.put("quizinfoShow",quizinfoDto);
		}

		return forward(request, model, "/event_quiz/eventQuizWrite.jsp");
	}

	/**
	 * 이벤트 퀴즈를 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);
		String regMode		= StringUtil.nvl(request.getParameter("pRegMode"));
		String userId		= UserBroker.getUserId(request);
		String example[]	= request.getParameterValues("pExample");
		int quizAnswer		= Integer.parseInt(request.getParameter("pQuizAnswer"));

		EventQuizDAO		eventquizDao= new EventQuizDAO();
		EventQuizInfoDTO	quizinfoDto	= new EventQuizInfoDTO();

		String pQuizId = "";
		if(regMode.equals("W"))// 입력모드
		{
			pQuizId = Integer.toString(eventquizDao.getMaxQuizId(systemCode));
		}else{
			pQuizId = StringUtil.nvl(request.getParameter("pQuizId"));
		}


		quizinfoDto.setSystemCode(systemCode);
		quizinfoDto.setQuizId(Integer.parseInt(pQuizId));
		quizinfoDto.setSubject(StringUtil.nvl(request.getParameter("pSubject")));
		quizinfoDto.setContents(StringUtil.nvl(request.getParameter("pContents")));
		quizinfoDto.setExample(example);
		quizinfoDto.setQuizAnswer(quizAnswer);
		quizinfoDto.setRegId(userId);

		String msg = "Error!!이벤트 퀴즈를 등록하는데 에러가 발생하였습니다.";
		String returnUrl = "/EventQuiz.cmd?cmd=eventQuizWrite&pRegMode=W";

		int retVal = 0;

		if(regMode.equals("W"))// 입력모드
		{
			retVal = eventquizDao.addEventQuiz(quizinfoDto);

			if(retVal > 0){
				msg = "이벤트퀴즈를 성공적으로 등록하였습니다.";
				returnUrl = "/EventQuiz.cmd?cmd=eventQuizList";
			}
		}else{
			retVal = eventquizDao.editEventQuiz(quizinfoDto);

			if(retVal > 0){
				returnUrl = "/EventQuiz.cmd?cmd=eventQuizList";
				msg = "이벤트퀴즈를 성공적으로 수정하였습니다.";
			}else{
				returnUrl = "/EventQuiz.cmd?cmd=eventQuizWrite&pRegMode=E";
				msg = "Error!!이벤트 퀴즈를 수정하는데 에러가 발생하였습니다.";

			}
		}

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 이벤트 퀴즈를 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		int pQuizId = Integer.parseInt(request.getParameter("pQuizId"));

		EventQuizDAO	eventquizDao	= new EventQuizDAO();

		int retVal = 0;
		retVal = eventquizDao.delEventQuiz(systemCode,pQuizId);

		String msg = "Error!!이벤트 퀴즈를  삭제하는데 에러가 발생하였습니다.";
		String returnUrl = "/EventQuiz.cmd?cmd=eventQuizWrite&pRegMode=E&pQuizId="+pQuizId;

		if(retVal > 0){
			msg = "이벤트 퀴즈를  삭제하였습니다.";
			returnUrl = "/EventQuiz.cmd?cmd=eventQuizList";
		}

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 이벤트 퀴즈 상태 변경 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizStatEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);
		int pQuizId = Integer.parseInt(request.getParameter("pQuizId"));

		EventQuizDAO	eventquizDao	= new EventQuizDAO();
		EventQuizInfoDTO	quizinfoDto	= new EventQuizInfoDTO();

		quizinfoDto = eventquizDao.getEventQuizShow(systemCode, pQuizId);

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);

		model.put("pQuizId", Integer.toString(pQuizId));
		model.put("quizinfoShow",quizinfoDto);

		return forward(request, model, "/event_quiz/eventQuizStatEdit.jsp");
	}

	/**
	 * 이벤트 퀴즈 상태를 변경한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizStatRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		int pQuizId 	= Integer.parseInt(request.getParameter("pQuizId"));
		String status 	= StringUtil.nvl(request.getParameter("pStatus"));

		EventQuizDAO	eventquizDao	= new EventQuizDAO();

		int retVal = 0;
		retVal = eventquizDao.statEditEventQuiz(systemCode,pQuizId, status);

		String msg = "Error!!이벤트 퀴즈 상태를 변경하는데 에러가 발생하였습니다.";
		String returnUrl = "/EventQuiz.cmd?cmd=eventQuizStatEdit&pQuizId="+pQuizId;

		if(retVal > 0){
			msg = "이벤트 퀴즈 상태를  변경하였습니다.";
			returnUrl = "/EventQuiz.cmd?cmd=eventQuizList";
		}
		return notifyCloseReload(systemCode, model, msg, "N");
	}


	/**
	 * 이벤트 퀴즈 참여결과 리스트를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizAnswerList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode 	= UserBroker.getSystemCode(request);
	    String pMode = StringUtil.nvl(request.getParameter("pMode"),HOME);
		int pQuizId = Integer.parseInt(request.getParameter("pQuizId"));
		String pEtype =  StringUtil.nvl(request.getParameter("pEtype"),"A");
		int pAnswer = StringUtil.nvl(request.getParameter("pAnswer"),0);

		int curPage = 1;
		if(StringUtil.nvl(request.getParameter("curPage")) != "") {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}

		EventQuizDAO	eventquizDao	= new EventQuizDAO();

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);

		model.put("pEtype", pEtype);
		model.put("curPage", Integer.toString(curPage));
		model.put("pQuizId", Integer.toString(pQuizId));
		model.put("quizinfoShow", eventquizDao.getEventQuizShow(systemCode, pQuizId));
		model.put("answerList", eventquizDao.getEventQuizAnswerPagingList(curPage, systemCode, pQuizId, pEtype, pAnswer));

		return forward(request, model, "/event_quiz/eventQuizAnswerList.jsp");
	}

	/**
	 * 이벤트 당첨자 선정/취소합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizAnswerEditFlag(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String[] pChk = request.getParameterValues("pCHK");
		String pSystemCode = UserBroker.getSystemCode(request);
		int pQuizId = Integer.parseInt(request.getParameter("pQuizId"));
		String pEtype =  StringUtil.nvl(request.getParameter("pEtype"),"A");
		String pPrizeYn =  StringUtil.nvl(request.getParameter("pPrizeYn"));

		EventQuizDAO	eventquizDao	= new EventQuizDAO();

		String msg = "Error!!이벤트 참여자 상태를 변경하는데 에러가 발생하였습니다.";
		String returnUrl = "/EventQuiz.cmd?cmd=eventQuizAnswerList&pQuizId="+pQuizId+"&pEtype="+pEtype;

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);

		boolean retVal =eventquizDao.editEventQuizAnswerFlag(pSystemCode,pChk,pQuizId,pPrizeYn);

		if(retVal){
			msg = "이벤트 참여자 상태를 성공적으로 변경하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 이벤트 참여 결과를 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizAnswerRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId	= UserBroker.getUserId(request);
		int pQuizId 		= Integer.parseInt(request.getParameter("pQuizId"));
		int answerNo 		= Integer.parseInt(request.getParameter("pNO"));

		EventQuizDAO	eventquizDao	= new EventQuizDAO();
		EventQuizAnswerDTO quizanswerDto = new EventQuizAnswerDTO();

		quizanswerDto.setSystemCode(systemCode);
		quizanswerDto.setQuizId(pQuizId);
		quizanswerDto.setUserId(userId);
		quizanswerDto.setRegId(userId);
		quizanswerDto.setAnswerNo(answerNo);

		int retVal = 0;
		retVal = eventquizDao.addEventQuizAnswer(quizanswerDto);

		String msg = "이벤트 참여에  실패하였습니다";
		String returnUrl = "/Main.cmd?cmd=mainShow&pMode=Home";

		if(retVal > 0){
			msg = "이벤트 참여에 성공하였습니다 ";
			returnUrl = "/Main.cmd?cmd=mainShow&pMode=Home";
		}

		return alertAndExit(systemCode, model, msg, returnUrl, "N");
	}

	/**
	 * 이벤트 참여 결과를 보여준다. (사용자)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward eventQuizAnswerShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);
		int pQuizId 		= Integer.parseInt(request.getParameter("pQuizId"));

		EventQuizDAO	eventquizDao	= new EventQuizDAO();

		new SiteNavigation(MYPAGE).add(request,"이벤트퀴즈").link(model);

		model.put("quizresultShow", eventquizDao.getEventQuizResultShow(systemCode,pQuizId));
		return forward(request, model, "/event_quiz/eventQuizResultShow.jsp");
	}

}
