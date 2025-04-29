/**
 * 
 */
package com.edutrack.curritop.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.config.dao.ConfigSubDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author famlilia
 *
 */
public class WebTreeEszAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	protected ArrayList WCMfileList = null;
	
	/**
	 * 자이닉스 컨텐츠 업로드
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward EszContentsUpload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode		=	UserBroker.getSystemCode(request);
		String	pCurriCode		=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int		pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int		pCurriTerm		=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));
		String	pCourseName		=	StringUtil.nvl(request.getParameter("pCourseName"));

		ConfigSubDAO	configDao	=	new ConfigSubDAO();
		RowSet			configList	=	configDao.getConfigSubList(systemCode, "310");
		model.put("ConfigList", configList);		
		
		model.put("pCourseId", pCourseId);
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", ""+pCurriYear);
		model.put("pCurriTerm", ""+pCurriTerm);
		model.put("pCourseName", pCourseName);
		
		new SiteNavigation(MYPAGE).add(request,"자이닉스 컨텐츠 업로드").link(model);
		return forward(request, model, "/contents/webtree_esz_connect.jsp");
	}
	

}
