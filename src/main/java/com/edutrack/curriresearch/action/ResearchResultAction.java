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
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.DateSetter;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.curriresearch.dao.ResearchInfoDAO;
import com.edutrack.curriresearch.dao.ResearchResultDAO;
import com.edutrack.curriresearch.dto.CurriResInfoDTO;
import com.edutrack.curriresearch.dto.ResResultDataDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchResultAction  extends StrutsDispatchAction{

	/**
	 *
	 */
	public ResearchResultAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward researchResultList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ResearchResultDAO resultDao = new ResearchResultDAO();
		RowSet resList = null;

		resList = resultDao.getResearchResultList(systemCode, curriInfo);

		model.put("resList", resList);

		new CurriSiteNavigation(LECTURE).add(request,"설문결과리스트").link(model);

		return forward(request, model, "/research/resh_result_list.jsp");
	}


	public ActionForward researchResultInfoShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		CurriResInfoDTO resInfo = null;
		ResearchHelper helper = new ResearchHelper();
		DateSetter resTerm = null;
		ResearchInfoDAO resDao = new ResearchInfoDAO();
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		resInfo = resDao.getResearchInfo(systemCode,curriInfo ,pResId);

		model.put("resInfo",resInfo);
		model.put("pResId",""+pResId);

		return forward(request, model, "/research/resh_result_info_show.jsp");
	}

	public ActionForward getResearchUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
        int pResId = StringUtil.nvl(request.getParameter("pResId"),0);

		ResearchResultDAO resultDao = new ResearchResultDAO();
		RowSet userList = null;

		userList = resultDao.getResearchUserList(systemCode, curriInfo, pResId);

		model.put("userList", userList);

		new CurriSiteNavigation(LECTURE).add(request,"설문결과관리").link(model);

		return forward(request, model, "/research/resh_result_st_list.jsp");
	}

	public ActionForward getResearchResult(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
        int pResId = StringUtil.nvl(request.getParameter("pResId"),0);

 		ResearchResultDAO resultDao = new ResearchResultDAO();
		ArrayList resultList = null;

		resultList = resultDao.getResearchResult(systemCode, curriInfo, pResId);

		model.put("resultList", resultList);
		model.put("pResId", ""+pResId);

		new CurriSiteNavigation(LECTURE).add(request,"설문결과관리").link(model);

		return forward(request, model, "/research/resh_as_list.jsp");
	}

	public ActionForward getResearchResultAnswer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pResNo = StringUtil.nvl(request.getParameter("pResNo"),0);

		ResearchResultDAO resultDao = new ResearchResultDAO();
		RowSet answerList = null;

		answerList = resultDao.getResearchResultAnswer(systemCode, curriInfo, pResId, pResNo);

		model.put("answerList", answerList);
		model.put("pResId", ""+pResId);
		model.put("pResNo", ""+pResNo);

		new CurriSiteNavigation(LECTURE).add(request,"설문결과관리").link(model);

		return forward(request, model, "/research/resh_as_list_jinsul.jsp");
	}

	public ActionForward getResearchResultExcel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
        int pResId = StringUtil.nvl(request.getParameter("pResId"),0);

 		ResearchResultDAO resultDao = new ResearchResultDAO();
		ResResultDataDTO resultData = null;

		resultData = resultDao.getResearchResultExcel(systemCode, curriInfo, pResId);

        model.put("resultData",resultData);

		return forward(request, model, "/research/resh_result_excel.jsp");
	}

}
