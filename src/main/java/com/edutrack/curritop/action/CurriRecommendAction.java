package com.edutrack.curritop.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.curritop.dao.CurriRecommendDAO;
import com.edutrack.common.UserBroker;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/*
 * @author Jamfam
 *
 * 추천 과정 관리 
 */

public class CurriRecommendAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	
	/**
	 * 추천 과정 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriRecommendView(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("--------------------- start curriRecommendView ---------------------");
		String systemCode = UserBroker.getSystemCode(request);
		RowSet recommendList = null;
		RowSet noneRecommendList = null;
		String pProperty1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		String pUpdate = StringUtil.nvl(request.getParameter("pUpdate"));
		CurriRecommendDAO curriRecommendDao = new CurriRecommendDAO();
		recommendList = curriRecommendDao.getRecomendCurriList(systemCode, pProperty1);
		noneRecommendList = curriRecommendDao.getDontRecomendCurriList(systemCode, pProperty1);
		model.put("pUpdate",pUpdate);
		model.put("pProperty1", pProperty1);
		model.put("recommendList", recommendList);
		model.put("noneRecommendList", noneRecommendList);
		log.debug("---------------------   end curriRecommendView ---------------------");
		return forward(request, model, "/curri_top/curriRecommendView.jsp");
	}
	
	public ActionForward curriRecommendWork(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String msg = "";
		String pProperty1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		String curriCodes = request.getParameter("pSelCurriCode");
		log.debug("curriCodes : "+curriCodes);
		String[] curriArrays = curriCodes.split("\\|");
		//---- 과정 코드를 배열에 담기
		CurriRecommendDAO curriRecommedDao = new CurriRecommendDAO();
		int result = curriRecommedDao.setRecommendCurri(systemCode, curriArrays, pProperty1);
		return redirect("/CurriRecommend.cmd?cmd=curriRecommendView&pProperty1="+pProperty1+"&pUpdate=Ok");
	}
}