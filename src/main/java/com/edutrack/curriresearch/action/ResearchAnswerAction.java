/*
 * Created on 2004. 10. 26.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curriresearch.dao.ResearchAnswerDAO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchAnswerAction  extends StrutsDispatchAction{

	/**
	 * 
	 */
	public ResearchAnswerAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 답변을 등록해준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchAnswerRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{    
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pResNo = StringUtil.nvl(request.getParameter("pResNo"),0);
		String pContentsType = StringUtil.nvl(request.getParameter("pContentsType"));
		
		ResearchAnswerDAO answerDao = new ResearchAnswerDAO();
		ResearchHelper helper = new ResearchHelper();
		ArrayList answerList = new ArrayList();
		helper.getResearchAnswerParam(request, answerList);
	    boolean retVal = answerDao.addResearchAnswer(systemCode, userInfo.curriInfo,userInfo.userId, pResId, answerList);
		String msg = "설문을 등록에 성공하였습니다.";
	    if(!retVal) msg = "설문을 등록에 실패하였습니다.";
	    
		return closePopupReload(systemCode, model, msg,"O","POPUP");
	}
}
