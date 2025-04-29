/*
 * Created on 2007. 8. 9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.score.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.score.dao.ScoreGradeDAO;
import com.edutrack.score.dto.ScoreGradeDTO;

/**
 * @author HerSunJa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScoreGradeAction extends StrutsDispatchAction{

	/**
	 * 평가등급 목록 화면단을 호출한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward scoreGradeList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		new SiteNavigation(MYPAGE).add(request,"평가등급관리").link(model);

		return forward(request, model, "/score_grade/scoreGradeList.jsp");

	}

	/**
	 * 평가등급 목록을 가져온다 ....Ajax
	 * @param pCurriYear
	 * @param pHakgiTerm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList scoreGradeListAuto(int pCurriYear, int pHakgiTerm, String pGradeCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		ScoreGradeDAO scoreGradeDao = new ScoreGradeDAO();
		ScoreGradeDTO scoreGradeDto = new ScoreGradeDTO();
		ArrayList scoreGradeList = scoreGradeDao.getScoreGradeList(systemCode, pCurriYear,  pHakgiTerm,  pGradeCode);

		return scoreGradeList;
	}



	/**
	 * 평가등급 입력폼을 보여준다.(수정일 경우에는 데이타와 함께)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward scoreGradeWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int pCurriYear = StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int pHakgiTerm = StringUtil.nvl(request.getParameter("pHakgiTerm"),0);
		String pGradeCode = StringUtil.nvl(request.getParameter("pGradeCode"));

		ScoreGradeDAO scoreGradeDao = new ScoreGradeDAO();
		ScoreGradeDTO scoreGradeDto = new ScoreGradeDTO();
		ArrayList scoreGradeList = scoreGradeDao.getScoreGradeList(systemCode, pCurriYear,  pHakgiTerm,  pGradeCode);

		model.put("scoreGradeList" , scoreGradeList);
		model.put("pCurriYear" , String.valueOf(pCurriYear));
		model.put("pHakgiTerm" , String.valueOf(pHakgiTerm));
		model.put("pGradeCode" , pGradeCode);
		return forward(request, model, "/score_grade/scoreGradeWrite.jsp");
	}

	/**
	 * 평가등급을 입력/수정한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward scoreGradeRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
		String callBackMethod = StringUtil.nvl(request.getParameter("callBackMethod"));

		String pRegMode  = StringUtil.nvl(request.getParameter("pRegMode"));
		int  pCurriYear = StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int  pHakgiTerm = StringUtil.nvl(request.getParameter("pHakgiTerm"),0);
		String pGradeCode = StringUtil.nvl(request.getParameter("pGradeCode"));
		String pGradeName	= StringUtil.nvl(request.getParameter("pGradeName"));
		double pGradePoint	= Double.parseDouble(StringUtil.nvl(request.getParameter("pGradePoint"),"0"));
		double pFrPoint		= Double.parseDouble(StringUtil.nvl(request.getParameter("pFrPoint"),"0"));
		double pToPoint		= Double.parseDouble(StringUtil.nvl(request.getParameter("pToPoint"),"0"));
		double pFrRate		= Double.parseDouble(StringUtil.nvl(request.getParameter("pFrRate"),"0"));
		double pToRate		= Double.parseDouble(StringUtil.nvl(request.getParameter("pToRate"),"0"));
		String pUseYn	= StringUtil.nvl(request.getParameter("pUseYn"));

		ScoreGradeDAO scoreGradeDao = new ScoreGradeDAO();
		ScoreGradeDTO scoreGradeDto = new ScoreGradeDTO();
		String msg = "";
		int retVal = 0;

		scoreGradeDto.setSystemCode (systemCode);
		scoreGradeDto.setCurriYear(pCurriYear);
		scoreGradeDto.setHakgiTerm(pHakgiTerm);
		scoreGradeDto.setGradeCode(pGradeCode);	// 수정시 사용
		scoreGradeDto.setGradeName(pGradeName);
		scoreGradeDto.setGradePoint(pGradePoint);
		scoreGradeDto.setFrPoint(pFrPoint);
		scoreGradeDto.setToPoint(pToPoint);
		scoreGradeDto.setFrRate (pFrRate);
		scoreGradeDto.setToRate (pToRate);
		scoreGradeDto.setUseYn  (pUseYn);
		scoreGradeDto.setRegId  (userId);
		scoreGradeDto.setModId  (userId);


		String errMsg = "오류가 발생하였습니다. \\n\\n확인 후 다시 시도하세요.";
		try{

			if(("W").equals(pRegMode)){
				if(scoreGradeDao.addScoreGrade(scoreGradeDto)<=0) throw new Exception(errMsg);
				msg = "저장 되었습니다.";
			}else if(("E").equals(pRegMode)){
				if(scoreGradeDao.editScoreGrade(scoreGradeDto)<=0) throw new Exception(errMsg);
				msg = "수정 되었습니다.";
			}else {
				msg = "정상적인 경로가 아닙니다. \\n\\n확인 후 다시 시도하세요.";
			}

		}catch (Exception e){
			log.debug(e);
			msg = e.toString();
		}

		return alertAndExit(systemCode, model, msg, CALLBACKURL+callBackMethod, pMode);
	}



	/**
	 * 평가등급을 삭제한다.....Ajax
	 * @param pCurriYear
	 * @param pHakgiTerm
	 * @param pGradeCode
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public String scoreGradeDeleteAjax(int pCurriYear, int pHakgiTerm, String pGradeCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		ScoreGradeDAO scoreGradeDao = new ScoreGradeDAO();
		ScoreGradeDTO scoreGradeDto = new ScoreGradeDTO();
		int retVal = 0;
		String msg = "";
		try{
			retVal = scoreGradeDao.delScoreGrade(systemCode, pCurriYear,  pHakgiTerm,  pGradeCode);
			if(retVal<=0) throw new Exception();
		}catch (Exception e){
			log.debug(e);
			msg = "오류가 발생하였습니다. \n\n확인 후 다시 시도하세요.";
		}

		return msg;
	}
}
