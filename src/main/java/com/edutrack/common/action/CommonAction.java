/*
 * Created on 2004. 9. 21.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dao.CommonDAO;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.AjaxUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public CommonAction() {
		super();
		// TODO Auto-generated constructor stub
	}

    /**
     * 우편번호를 검색한다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward searchPost(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pForm = StringUtil.nvl(request.getParameter("pForm"),"");
		String pZip = StringUtil.nvl(request.getParameter("pZip"),"");
		String pAddr = StringUtil.nvl(request.getParameter("pAddr"),"");
		String pDong = StringUtil.nvl(request.getParameter("pDong"),"");

		model.put("pForm",pForm);
		model.put("pZip",pZip);
		model.put("pAddr",pAddr);
		model.put("pDong",pDong);

		CommonDAO commonDAO 	= 	new CommonDAO();

		ArrayList postList =  new ArrayList();;
		if(!pDong.equals(""))
			postList =  commonDAO.searchPost(pDong);

        model.put("postList", postList);

        return forward(request, model, "/users/users_post_search.jsp");
	}



/*
	public ActionForward goProfInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(ENROLL).add(request,"교수자안내").link(model);

        return forward(request, model, "/users/prof_info.jsp");
	}

	public ActionForward goCurriInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
       String page = StringUtil.nvl(request.getParameter("pPage"),"1");

	   new SiteNavigation(ENROLL).add(request,"과정안내").link(model);

       return forward(request, model, "/curriInfo/curri_info"+page+".jsp");
	}
*/
	public ActionForward goInfoHistory(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(INFO).add(request,"주요제공서비스").link(model);

       return forward(request, model, "/info/info_history.jsp");
	}
	public ActionForward goInfoOrgani(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(INFO).add(request,"목적과 역할").link(model);

      return forward(request, model, "/info/info_organi.jsp");
	}
	public ActionForward goInfoManage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(INFO).add(request,"운영계획").link(model);

      return forward(request, model, "/info/info_manage.jsp");
	}
	public ActionForward goInfoMap(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(INFO).add(request,"찾아오시는길").link(model);

      return forward(request, model, "/info/info_map.jsp");
	}
	public ActionForward goSiteMap(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(HOME).add(request,"Site Map").link(model);

     return forward(request, model, "/info/site_map.jsp");
	}
/*
	public ActionForward goLectureInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(ENROLL).add(request,"수강절차").link(model);

    return forward(request, model, "/info/lecture_info.jsp");
	}
*/
	public ActionForward goUseInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		 new SiteNavigation(HOME).add(request,"이용안내").link(model);

       return forward(request, model, "/info/use_info.jsp");
	}


	public void userListAjax(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		PrintWriter out = response.getWriter();
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);

		String pUserType = StringUtil.nvl(request.getParameter("pUserType"));
		String pUserName = StringUtil.nvl(request.getParameter("names"));
		pUserName = AjaxUtil.ajaxDecoding(pUserName);
		CommonDAO commonDAO 	= 	new CommonDAO();
		CodeDTO codeDto = null;
		ArrayList userList =  new ArrayList();;
		userList =  commonDAO.getUserList(systemCode,pUserType,pUserName);

	    response.setContentType("application/xml");
		StringBuffer strXML = new StringBuffer();
		strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
		strXML.append("<userList>");

		int length = userList.size();

		for(int i=0;i<length;i++){
			codeDto = (CodeDTO)userList.get(i);
			strXML.append("<entry>");
			strXML.append("<userName>"+codeDto.getName()+"</userName>");
			strXML.append("<userId>"+codeDto.getCode()+"</userId>");
			strXML.append("</entry>");
		}
		strXML.append("</userList>");

		out.println(strXML.toString());
	}

}
