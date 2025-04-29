/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.action;

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
import com.edutrack.curriresearch.dao.ResearchInfoDAO;
import com.edutrack.curriresearch.dto.CurriResInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchInfoAction  extends StrutsDispatchAction{

	/**
	 *
	 */
	public ResearchInfoAction() {
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
	public ActionForward researchInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ResearchInfoDAO resDao = new ResearchInfoDAO();
		RowSet resList = null;

		resList = resDao.getResearchInfoList(systemCode, curriInfo, 0);

		model.put("resList", resList);

		new CurriSiteNavigation(LECTURE).add(request,"설문리스트").link(model);

		return forward(request, model, "/research/resh_mg_list.jsp");
	}

    /**
     * 설문정보입력, 수정 페이지를 보여준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward researchInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		CurriResInfoDTO resInfo = null;
		ResearchHelper helper = new ResearchHelper();
		DateSetter resTerm = null;
		if(pMODE.equals("write")){
			 String curDate = StringUtil.substring(CommonUtil.getCurrentDate(),0,8);
		     resInfo = new CurriResInfoDTO();
		   	 resTerm = new DateSetter(curDate,curDate).link(model);
		}else{
			ResearchInfoDAO resDao = new ResearchInfoDAO();
			CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
			resInfo = resDao.getResearchInfo(systemCode,curriInfo ,pResId);
			resTerm = new DateSetter(resInfo.getStartDate(),resInfo.getEndDate()).link(model);
		}

		model.put("resInfo",resInfo);
		model.put("resTerm", resTerm);
    	model.put("pMODE", pMODE);
		model.put("pResId",""+pResId);

        String navMsg = "설문등록";

        if(!pMODE.equals("write")) navMsg = "설문수정";

		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);

		return forward(request, model, "/research/resh_mg_write.jsp");
	}

	/**
	 * 설문을 등록, 수정 작업을 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		ResearchInfoDAO resDao = new ResearchInfoDAO();
		ResearchHelper helper = new ResearchHelper();
		CurriResInfoDTO resInfo = new CurriResInfoDTO();
		helper.getResearchParam(request, resInfo);
		if(pMODE.equals("write")){
	        resInfo.setRegId(userInfo.userId);
			resDao.addResearchInfo(systemCode, userInfo.curriInfo, resInfo);
		}else{
			resInfo.setModId(userInfo.userId);
			resInfo.setResId(pResId);
			resDao.editResearchInfo(systemCode, userInfo.curriInfo, resInfo);
		}

		new CurriSiteNavigation(LECTURE).add(request,"설문리스트").link(model);

		return redirect("/ResearchInfo.cmd?cmd=researchInfoList");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchOpen(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		ResearchInfoDAO resDao = new ResearchInfoDAO();
		CurriResInfoDTO resInfo = new CurriResInfoDTO();
		resInfo.setOpenYn("Y");
		resInfo.setModId(userInfo.userId);
		resInfo.setResId(pResId);
		resDao.editResearchOpenYn(systemCode, userInfo.curriInfo, resInfo);

		new CurriSiteNavigation(LECTURE).add(request,"설문리스트").link(model);

		return redirect("/ResearchInfo.cmd?cmd=researchInfoList&pResId="+pResId);
	}

	public ActionForward researchStInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
        String userId = UserBroker.getUserId(request);
		ResearchInfoDAO resDao = new ResearchInfoDAO();
		RowSet resList = null;

		resList = resDao.getResearchStInfoList(systemCode, curriInfo, userId);

		model.put("resList", resList);

		new CurriSiteNavigation(LECTURE).add(request,"설문리스트").link(model);

		return forward(request, model, "/research/resh_st_list.jsp");
	}

	public ActionForward researchStInfoShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		CurriResInfoDTO resInfo = null;
		ResearchInfoDAO resDao = new ResearchInfoDAO();
			resInfo = resDao.getResearchInfo(systemCode,curriInfo ,pResId);

		model.put("resInfo",resInfo);

		return forward(request, model, "/research/resh_st_info_show.jsp");
	}

	public ActionForward researchInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pResId = StringUtil.nvl(request.getParameter("pResId"),"0");
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		String pCurriCode = curriInfo.curriCode;
		int pCurriYear = StringUtil.nvl(curriInfo.curriYear, 0);
		int pCurriTerm = StringUtil.nvl(curriInfo.curriTerm, 0);

		int retVal = 0;
		int retVal2 = 0;
		int retVal3 = 0;

		ResearchInfoDAO researchInfoDao = new ResearchInfoDAO();

		retVal = researchInfoDao.delResAnswer(systemCode, pCurriCode, pCurriYear, pCurriTerm, pResId);
		retVal2 = researchInfoDao.delResContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pResId);
		retVal3 = researchInfoDao.delResInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pResId);

		String msg = "";
		String returnUrl = "/ResearchInfo.cmd?cmd=researchInfoList";

		if(retVal3 > 0){
			msg = "설문 정보를 삭제하였습니다.";
		} else {
			msg = "설문 정보 삭제에 실패하였습니다." ;
		}
		return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);

	}
}
