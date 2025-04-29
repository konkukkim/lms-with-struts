/*
 * Created on 2004. 11. 24.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.currisub.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.currisub.dao.CurriSubSortDAO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriSubSortAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public CurriSubSortAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward curriSubSortList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ArrayList curriList = null;
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int curriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));

		CurriSubSortDAO sortDao = new CurriSubSortDAO();

		int firstCnt = sortDao.getCurriSortFirst(systemCode, curriCode, curriYear, curriTerm);

		curriList = sortDao.getCurriSortList(systemCode, curriCode, curriYear, curriTerm, firstCnt);

		model.put("pCurriCode", curriCode);
		model.put("pCurriYear", Integer.toString(curriYear));
		model.put("pCurriTerm", Integer.toString(curriTerm));
		model.put("curriSubSortList", curriList);

		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);

		return forward(request, model, "/curri_sub/curriSubSortList.jsp");
	}

	public ActionForward curriSubSortRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ArrayList curriList = null;
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int curriYear = StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int curriTerm = StringUtil.nvl(request.getParameter("pCurriTerm"),0);
        int totSize = StringUtil.nvl(request.getParameter("pTotSize"),0);

		CurriSubSortDAO sortDao = new CurriSubSortDAO();
		ArrayList contentList = new ArrayList();
		String[] info = null;
		for(int i =0; i < totSize; i++){
            info = new String[]{"","",""};
			info[0] = StringUtil.nvl(request.getParameter("pCourseId"+i));
			info[1] = StringUtil.nvl(request.getParameter("pCotentsId"+i));
            info[2] = StringUtil.nvl(request.getParameter("pSeqNo"+i));
			contentList.add(info);
		}

		boolean retVal = sortDao.editCurriSortList(systemCode, curriCode, curriYear, curriTerm, contentList);

		String msg = "과제 정렬 정보를 등록하였습니다.";

		if(retVal == false) msg = "과제 정렬 정보를 등록는데 실패하였습니다.";

		String returnUrl = "/CurriSubCourse.cmd?cmd=curriSubCourseList&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm;
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}


}
