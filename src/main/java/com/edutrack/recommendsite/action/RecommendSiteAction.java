/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.recommendsite.action;

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
import com.edutrack.recommendsite.dao.RecommendSiteDAO;
import com.edutrack.recommendsite.dto.RecommendSiteDTO;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecommendSiteAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public RecommendSiteAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 추천사이트를 open 한다..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward recommendSiteList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		//String userType = UserBroker.getUserType(request);
        String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
        
		new SiteNavigation(pMode).add(request,"추천사이트").link(model);
		return forward(request, model,"/recommend_site/recommendSiteList.jsp");
	}

	/**
	 * 추천사이트를 가져온다..2depth 로 가져온다..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList recommendSiteTwoDepthAjax(String pMasterCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ArrayList aList = new ArrayList();
		
        RecommendSiteDAO recommendSiteDao = new RecommendSiteDAO();
        ArrayList siteList = recommendSiteDao.getRecommendSiteTwoDepth(systemCode, pMasterCode );

        aList.add(pMasterCode);
        aList.add(siteList);

		return aList;
	}
	
	/**
	 * 추천사이트를 가져온다....select box
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList recommendSiteListAuto(String pMasterCode, String pRecommCode, String pRecommDepth, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
        
        RecommendSiteDAO recommendSiteDao = new RecommendSiteDAO();
        ArrayList siteList = recommendSiteDao.getRecommendSiteList(systemCode, pMasterCode, pRecommCode ,pRecommDepth);
        
		return siteList;
	}

	/**
	 * 추천사이트를 저장한다....Ajax
	 * @param pGUBUN
	 * @param pMasterCode
	 * @param pRecommCode
	 * @param pRecommName
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String recommendSiteRegistAjax(String pGUBUN, String pRecommDepth, String pMasterCode, String pRecommCode, String pRecommName, String pSiteUrl, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");

		String userId = UserBroker.getUserId(request);

		RecommendSiteDAO recommendSiteDao = new RecommendSiteDAO();
		RecommendSiteDTO recommendSiteDto = new RecommendSiteDTO();
		
		recommendSiteDto.setSystemCode(systemCode);
		recommendSiteDto.setMasterCode(pMasterCode);
		recommendSiteDto.setRecommCode(pRecommCode);
		recommendSiteDto.setRecommName(pRecommName);
		recommendSiteDto.setSiteUrl(pSiteUrl);
		recommendSiteDto.setRecommDepth(pRecommDepth);
		
		String msg = "";
		int retVal = 0;

		try{
			if(pGUBUN.equals("write")) {
				recommendSiteDto.setRegId(userId);
				retVal= recommendSiteDao.addRecommendSite(recommendSiteDto);
				msg = "추천사이트를 추가하는데 ";
			} else if(pGUBUN.equals("edit")){
				recommendSiteDto.setModId(userId);
				retVal= recommendSiteDao.editRecommendSite(recommendSiteDto);
				msg = "추천사이트를 수정하는데 ";
			}

			if(retVal>0) msg +="성공하였습니다.";
			else msg +="실패하였습니다.";

		}catch(Exception e){
			log.debug(e);
		}
		return msg;
	}


	/**
	 * 추천사이트를 삭제한다...Ajax
	 * @param pMasterCode
	 * @param pRecommCode
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public String recommendSiteDeleteAjax(String pMasterode,  String pRecommCode, String pRecommDepth, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		
		RecommendSiteDAO recommendSiteDao = new RecommendSiteDAO();
		RecommendSiteDTO recommendSiteDto = new RecommendSiteDTO();
		
		recommendSiteDto.setSystemCode(systemCode);
		recommendSiteDto.setMasterCode(pMasterode);
		recommendSiteDto.setRecommCode(pRecommCode);
		recommendSiteDto.setRecommDepth(pRecommDepth);
		
		String msg = "";
		int iRecommCnt = 0;
		try{
			
			iRecommCnt = recommendSiteDao.getCntRecommendSite(systemCode, pRecommCode, pRecommDepth);
			
			if(iRecommCnt>0) throw new Exception();
			
			if(recommendSiteDao.deleteRecommendSite(recommendSiteDto)<=0) throw new Exception("Db Error");
			msg = "추천사이트를 삭제하는데 성공하였습니다.";
		}catch(Exception e){
			//log.debug(e);

			if(iRecommCnt>0) msg = "추천사이트를 삭제하는데 실패하였습니다. \n\n하위단의 데이타가 존재합니다. \n\n하위단을 먼저 삭제하신 후 다시 진행하시기 바랍니다.";
			else msg = "추천사이트를 삭제하는데 실패하였습니다.["+ e +"]";

		}
		return msg;
	}


}
