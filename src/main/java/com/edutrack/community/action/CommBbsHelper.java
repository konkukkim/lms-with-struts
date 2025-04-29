/*
 * Created on 2004. 12. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.community.action;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.community.dto.CommBbsInfoDTO;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommBbsHelper {

	/**
	 * 
	 */
	public CommBbsHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

    public void getBbsInfoParam(HttpServletRequest request, CommBbsInfoDTO bbsinfo){
        bbsinfo.setBbsId(StringUtil.nvl(request.getParameter("pBbsId"),0));
        bbsinfo.setBbsName(StringUtil.nvl(request.getParameter("pBbsName")));
        bbsinfo.setBbsType(StringUtil.nvl(request.getParameter("pBbsType")));
        bbsinfo.setUseYn(StringUtil.nvl(request.getParameter("pUseYn")));
        bbsinfo.setEditorYn(StringUtil.nvl(request.getParameter("pEditorYn")));
        bbsinfo.setVoiceYn(StringUtil.nvl(request.getParameter("pVoiceYn")));
        bbsinfo.setFileUseYn(StringUtil.nvl(request.getParameter("pFileUseYn")));
        bbsinfo.setFileLimit(StringUtil.nvl(request.getParameter("pFileLimit"),2));
        bbsinfo.setDispLine(StringUtil.nvl(request.getParameter("pDispLine"),10));
        bbsinfo.setDispPage(StringUtil.nvl(request.getParameter("pDispPage"),10));
        bbsinfo.setCommentUseYn(StringUtil.nvl(request.getParameter("pCommentUseYn")));
    }
}
