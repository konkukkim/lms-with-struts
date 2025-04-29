/*
 * Created on 2004. 10. 19.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.courseexam.dao.ExamAnswerDAO;
import com.edutrack.courseexam.dao.ExamContentsDAO;
import com.edutrack.courseexam.dto.ExamAnswerDTO;
import com.edutrack.courseexam.dto.ExamContentsDTO;
import com.edutrack.courseexam.dto.ExamOrderDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamAnswerAction extends StrutsDispatchAction {
	/**
	 *
	 */
	public ExamAnswerAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward examAnswerTimeEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		int pConCnt = StringUtil.nvl(request.getParameter("pConCnt"),0);
		String userId = UserBroker.getUserId(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

        ExamAnswerDAO answerDao = new ExamAnswerDAO();
		if(pConCnt > 0) answerDao.editExamAnswerTime(systemCode,curriInfo,pCourseId,pExamId, userId);

        String remainTime = answerDao.getExamRemainTime(systemCode,curriInfo,pCourseId,pExamId, userId);

        log.debug("reaminTime =>"+remainTime);

        model.put("remainTime",remainTime);
        model.put("pCourseId",pCourseId);
        model.put("pExamId",""+pExamId);
        model.put("pConCnt",""+pConCnt);

		return forward(request, model,"/courseexam/exam_connect_check.jsp");
	}

	/**
	 * 답안을 등록한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examAnswerRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		int maxOrder = StringUtil.nvl(request.getParameter("pMaxOrder"),0);
		String userId = UserBroker.getUserId(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		log.debug("maxOrder =>"+maxOrder);

		String conInfo = "";
		String[] answerInfo = null;
		String[] pArray = null;
		String pArrayStr = "";
		String examOrder = "";
		String examScore = "";
		String examAnswer = "";
		String realAnswer = "";
		String answerScore = "";
		String pAnswer = "";
		int totScore =0; // 자동채점한 총점 저장
		int checkCount = 0; // 주관식 여부확인
		// 시험 답안지 정보를 가져온다.
		ExamContentsDAO contentsDao = new ExamContentsDAO();
		HashMap scoreInfo = contentsDao.getExamContentsScore(systemCode, curriInfo,pCourseId, pExamId);
		ExamContentsDTO contentsDto = null;
		ExamAnswerDTO answerDto = new ExamAnswerDTO();
		ExamOrderDTO order = null;
		int count = 0;
		for(int i =1; i <= maxOrder; i++){
			conInfo = StringUtil.nvl(request.getParameter("pAnswerInfo"+i));
			if(!conInfo.equals("")){
				if(count > 0){
					examOrder += CommonUtil.DELI;
					examAnswer += CommonUtil.DELI;
					examScore +=  CommonUtil.DELI;
					answerScore += CommonUtil.DELI;
					realAnswer += CommonUtil.DELI;
				}
				answerInfo = StringUtil.split(conInfo,"/");
				examOrder += i+"/"+answerInfo[0];
				if(answerInfo[1].equals("K")){
					pArray = request.getParameterValues("pAnswer"+i);
					if(pArray != null){
						pArrayStr = "";
						for(int j =0; j < pArray.length; j++){
							if(j > 0) pArrayStr += "/";
							pArrayStr += pArray[j];
							log.debug("pAnswer==>"+j+"번째,값 ==>"+ pArray[j]);
						}
					}
					contentsDto = (ExamContentsDTO)scoreInfo.get(i+"/"+answerInfo[0]);
					realAnswer += contentsDto.getMultiAnswer();
					if(pArrayStr.equals(contentsDto.getMultiAnswer())){
						answerScore += ""+contentsDto.getScore();
						totScore += contentsDto.getScore();
					}
					else answerScore += "0";

					examAnswer += pArrayStr;
				}else if(answerInfo[1].equals("S")){
					pAnswer = StringUtil.nvl(request.getParameter("pAnswer"+i));
					contentsDto = (ExamContentsDTO)scoreInfo.get(i+"/"+answerInfo[0]);
					realAnswer += contentsDto.getAnswer();
					if(pAnswer.equals(contentsDto.getAnswer())){
						answerScore += ""+contentsDto.getScore();
						totScore += contentsDto.getScore();
					}else answerScore += "0";

					examAnswer += pAnswer;
				}else{
					contentsDto = (ExamContentsDTO)scoreInfo.get(i+"/"+answerInfo[0]);
					realAnswer += contentsDto.getAnswer();

					answerScore +="0";
					examAnswer +=  StringUtil.nvl(request.getParameter("pAnswer"+i));
					checkCount++;
				}

                examScore += answerInfo[2];
				count++;
			}
		}

		if(checkCount == 0) answerDto.setResultCheck("Y");

		answerDto.setCourseId(pCourseId);
		answerDto.setExamId(pExamId);
		answerDto.setUserId(userId);
		answerDto.setExamScore(examScore);
		answerDto.setExamAnswer(realAnswer);
		answerDto.setAnswer(examAnswer);
		answerDto.setExamOrder(examOrder);
		answerDto.setAnswerScore(answerScore);
		answerDto.setTotalScore(totScore);

		ExamAnswerDAO answerDao = new ExamAnswerDAO();

		int retVal = answerDao.editExamAnswerInfo(systemCode, curriInfo,answerDto);

		String msg = "답안제출에 실패하였습니다. 해당과목을 클릭하여 시험상태를 확인하세요.";
		if(retVal > 0) msg = "답안제출에 성공하였습니다.";

	    return closePopupReload(systemCode, model, msg,"O","POPUP");//alertPopupCloseResponse(systemCode, model,msg,LECTURE);
	}

	/**
	 * 남은 시험시간을 알린다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examAlertShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    int remainTime = StringUtil.nvl(request.getParameter("remainTime"),0);

	    model.put("remainTime",""+remainTime);

	    return forward(request, model,"/courseexam/exam_st_alert.jsp");
	}

	/**
	 * 사용자 답안지를 재시험 처리한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examRetestEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		String[] userIds = request.getParameterValues("retest");
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		String modId = UserBroker.getUserId(request);

		boolean resultVar = false;

        ExamAnswerDAO answerDao = new ExamAnswerDAO();
		if(userIds != null) resultVar = answerDao.editExamRetest(systemCode,curriInfo,pCourseId,pExamId, userIds,modId);

        String msg = "선택된 사용자를 재시험 처리하는데 실패하였습니다.";

        if(resultVar) msg = "선택된 사용자를 재시험 처리하는데 성공하였습니다.";

		return alertAndExit(systemCode,model, msg, "/ExamResult.cmd?cmd=examUserList&pCourseId="+pCourseId+"&pExamId="+pExamId,LECTURE);
	}

}
