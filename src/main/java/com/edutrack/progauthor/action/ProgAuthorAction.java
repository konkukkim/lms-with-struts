/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.progauthor.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.progauthor.dao.ProgAuthorDAO;
import com.edutrack.progauthor.dto.ProgAuthorDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgAuthorAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ProgAuthorAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward getProgAuthorList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
        String userType = UserBroker.getUserType(request);
        String pSoCode = StringUtil.nvl(request.getParameter("pSoCode"));
		String pFields = StringUtil.nvl(request.getParameter("pFields"));
		String pValue = StringUtil.nvl(request.getParameter("pValue"));

        if(!userType.equals("M"))
        	notifyAndExit(systemCode,model, "프로그램 접근 권한이 없습니다.", "/Main.cmd?cmd=mainShow&pMode="+pMode ,pMode);

		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		ProgAuthorDAO authorDao = new ProgAuthorDAO();

		model.put("authorList", authorDao.progAuthorPagingList(curPage, systemCode, pSoCode, pFields, pValue));
		model.put("pSoCode",pSoCode);
		model.put("pFields",pFields);
		model.put("pValue",pValue);

		new SiteNavigation(pMode).add(request,"프로그램 권한관리").link(model);
		return forward(request, model,"/progauthor/progAuthorList.jsp");
	}


	public ActionForward progAuthorWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception {
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
		String pGUBUN = StringUtil.nvl(request.getParameter("pGUBUN"));
        String pSoCode = StringUtil.nvl(request.getParameter("pSoCode"));
        String pProgSeq = StringUtil.nvl(request.getParameter("pProgSeq"));

		String navigationMsg = "";
		String navigationWhere = pMode;
		ProgAuthorDTO authorInfo = null;

		if(pGUBUN.equals("write")) {
			navigationMsg = "프로그램권한등록";
			authorInfo = new ProgAuthorDTO();
			authorInfo.setWorkGubun(pSoCode);
		} else {
			navigationMsg = "프로그램권한수정";
			ProgAuthorDAO authorDao = new ProgAuthorDAO();
			String systemCode = UserBroker.getSystemCode(request);
			authorInfo = authorDao.getProgAuthorInfo(systemCode,pSoCode,pProgSeq);
		}

		model.put("pGUBUN",pGUBUN);
		model.put("pSoCode",pSoCode);
		model.put("authorInfo", authorInfo);

		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);
		return forward(request, model,"/progauthor/progAuthorWrite.jsp");
	}


	public ActionForward progAuthorRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");

		String pGUBUN = StringUtil.nvl(request.getParameter("pGUBUN"));
		String userId = UserBroker.getUserId(request);
		ProgAuthorDAO authorDao = new ProgAuthorDAO();
		ProgAuthorDTO authorDto = new ProgAuthorDTO();
		String pSoCode = StringUtil.nvl(request.getParameter("pSoCode"));
		authorDto.setSystemCode(systemCode);
		authorDto.setWorkGubun(pSoCode);
		authorDto.setProgSeq(StringUtil.nvl(request.getParameter("pProgSeq"),0));
		authorDto.setProgId(StringUtil.nvl(request.getParameter("pProgId")));
		authorDto.setProgName(StringUtil.nvl(request.getParameter("pProgName")));
		authorDto.setProgComment(StringUtil.nvl(request.getParameter("pProgComment")));
		String[] progArr = request.getParameterValues("pViewLevel");
		String pViewLevel = "";
		if(progArr != null) {
			for(int i =0; i<progArr.length; i++) {
				if(i > 0) pViewLevel += ",";
				pViewLevel += progArr[i];
			}
		}
		authorDto.setViewLevel(pViewLevel);

		String msg = "";
		if(pGUBUN.equals("write")) {
			authorDto.setRegId(userId);
			authorDao.addProgAuthorInfo(authorDto);
			msg = "프로그램 권한 등록에 성공하였습니다.";
		} else {
			authorDto.setModId(userId);
			authorDao.editProgAuthorInfo(authorDto);
			msg = "프로그램 권한 수정에 성공하였습니다.";
		}
		return notifyAndExit(systemCode, model, msg, "/ProgAuthor.cmd?cmd=getProgAuthorList&pSoCode="+pSoCode, pMode);
	}
}
