/*
 * Created on 2005. 8. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.onlinequiz.action;

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
import com.edutrack.onlinequiz.dao.OnlineQuizDAO;
import com.edutrack.onlinequiz.dto.OnlineQuizDTO;

/**
 * @author HerSunJa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OnlineQuizAction extends StrutsDispatchAction{

    public ActionForward onlineQuizList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String pSystemCode    = UserBroker.getSystemCode(request);
        String pKeyWord       = StringUtil.nvl(request.getParameter("pKeyWord"));
        String pSearchKey     = StringUtil.nvl(request.getParameter("pSearchKey"));

        OnlineQuizDAO onlineQuizDAO	=	new OnlineQuizDAO();

		new SiteNavigation(MYPAGE).add(request,"온라인 퀴즈 관리 ").link(model);
		model.put("onlineQuizList", onlineQuizDAO.getOnlineQuizPagingList(pSystemCode, pKeyWord, pSearchKey));

        return forward(request, model, "/onlineQuiz/onlineQuizList.jsp");
    }


    public ActionForward onlineQuizWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String pSystemCode    = UserBroker.getSystemCode(request);
        String pSeqNo         = StringUtil.nvl(request.getParameter("pSeqNo"),"0");

        OnlineQuizDAO onlineQuizDAO	=	new OnlineQuizDAO();

		new SiteNavigation(MYPAGE).add(request,"온라인 퀴즈 관리 ").link(model);

		model.put("onlineQuizInfo", onlineQuizDAO.getOnlineQuizInfo(pSystemCode, pSeqNo ));
		model.put("curriSubList",   onlineQuizDAO.getCurriSubList(pSystemCode  ));

        return forward(request, model, "/onlineQuiz/onlineQuizWrite.jsp");
    }

    /**
     * @desc  메인화면에서 링크시 호출
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward onlineQuizShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String pSystemCode    = UserBroker.getSystemCode(request);
        String pSeqNo         = StringUtil.nvl(request.getParameter("pSeqNo"),"0");
        String pMode          = StringUtil.nvl(request.getParameter("pMode") , MYPAGE );

        OnlineQuizDAO onlineQuizDAO	=	new OnlineQuizDAO();

        new SiteNavigation(pMode).add(request,"온라인 퀴즈 관리 ").link(model);

		model.put("onlineQuizDto", onlineQuizDAO.getOnlineQuizInfo(pSystemCode, pSeqNo ));

        return forward(request, model, "/main/main_onlineQuizShow.jsp");
    }


    public ActionForward onlineQuizRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String pSystemCode    = UserBroker.getSystemCode(request);
        String userId		  = UserBroker.getUserId(request);
       // request Param
        String pSeqNo          = StringUtil.nvl(request.getParameter("pSeqNo"),"0");
        String pSubject        = StringUtil.nvl(request.getParameter("pSubject"),"0");
        String pQuizType       = StringUtil.nvl(request.getParameter("pQuizType"),"S");
        String pExample1       = StringUtil.nvl(request.getParameter("pExample1"),"");
        String pExample2       = StringUtil.nvl(request.getParameter("pExample2"),"");
        String pExample3       = StringUtil.nvl(request.getParameter("pExample3"),"");
        String pExample4       = StringUtil.nvl(request.getParameter("pExample4"),"");
        String pExample5       = StringUtil.nvl(request.getParameter("pExample5"),"");
        String pAnswer         = StringUtil.nvl(request.getParameter("pAnswer"),"0");
        String pQuizComment    = StringUtil.nvl(request.getParameter("pQuizComment"),"0");
        String pQuizCurriUrl   = StringUtil.nvl(request.getParameter("pQuizCurriUrl"),"0");
        String pQuizLinkUrl    = StringUtil.nvl(request.getParameter("pQuizLinkUrl"),"0");
        String pUseYn          = StringUtil.nvl(request.getParameter("pUseYn"),"N");
        String pRegMode        = StringUtil.nvl(request.getParameter("pRegMode"),"W");

        OnlineQuizDAO onlineQuizDAO	= new OnlineQuizDAO();
        OnlineQuizDTO onlineQuizDTO	= new OnlineQuizDTO();


		String returnUrl = "/OnlineQuiz.cmd?cmd=onlineQuizList&pMode=MyPage" ;
		String msg = "";

		onlineQuizDTO.setSystemCode   ( pSystemCode   );
		onlineQuizDTO.setSeqNo        ( pSeqNo        );  // OnEdit Or OnDelete
		onlineQuizDTO.setSubject      ( pSubject      );
		onlineQuizDTO.setQuizType     ( pQuizType     );
		onlineQuizDTO.setExample1     ( pExample1     );
		onlineQuizDTO.setExample2     ( pExample2     );
		onlineQuizDTO.setExample3     ( pExample3     );
		onlineQuizDTO.setExample4     ( pExample4     );
		onlineQuizDTO.setExample5     ( pExample5     );
		onlineQuizDTO.setAnswer       ( pAnswer       );
		onlineQuizDTO.setQuizComment  ( pQuizComment  );
		onlineQuizDTO.setQuizCurriUrl ( pQuizCurriUrl );
		onlineQuizDTO.setQuizLinkUrl  ( pQuizLinkUrl  );
		onlineQuizDTO.setUseYn        ( pUseYn        );
		onlineQuizDTO.setRegId        ( userId );
		onlineQuizDTO.setModId        ( userId );

		int retVal = 0;

		//	 입력모드
		if(pRegMode.equals("W"))
		{
			retVal = onlineQuizDAO.addOnlineQuiz(onlineQuizDTO);

			if(retVal > 0)
				msg = "온라인 퀴즈를 성공적으로 등록하였습니다.";
			else
				msg = "Error!!온라인 퀴즈를 등록하는데 에러가 발생하였습니다.";

		}
		// 수정 모드
		else if(pRegMode.equals("E"))
		{
		    retVal = onlineQuizDAO.editOnlineQuiz(onlineQuizDTO);

			if(retVal > 0)
				msg = "온라인 퀴즈를 성공적으로 수정하였습니다.";
			else
				msg = "Error!!온라인 퀴즈를 수정하는데 에러가 발생하였습니다.";

		}
		// 삭제 모드
		else if(pRegMode.equals("D"))
		{
			retVal = onlineQuizDAO.delOnlineQuiz(onlineQuizDTO);

			if(retVal > 0)
				msg = "온라인 퀴즈 를 성공적으로 삭제 하였습니다.";
			else
				msg = "Error!!온라인 퀴즈를 삭제하는데 에러가 발생하였습니다.";
		}


		new SiteNavigation(MYPAGE).add(request,"온라인 퀴즈 관리 ").link(model);
		return notifyAndExit(pSystemCode, model, msg, returnUrl, MYPAGE);

    }

}
